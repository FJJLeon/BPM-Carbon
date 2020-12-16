package edu.bpm.carbon.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import edu.bpm.carbon.constant.Constant;
import edu.bpm.carbon.dao.TravelRecordDao;
import edu.bpm.carbon.dao.UserDao;
import edu.bpm.carbon.entity.TravelRecord;
import edu.bpm.carbon.entity.User;
import edu.bpm.carbon.service.TravelRecordService;
import edu.bpm.carbon.service.UserService;
import edu.bpm.carbon.service.credit.CreditService;
import edu.bpm.carbon.utils.msgutils.Msg;
import edu.bpm.carbon.utils.msgutils.MsgCode;
import edu.bpm.carbon.utils.msgutils.MsgUtil;
import edu.bpm.carbon.utils.timeutils.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class TravelRecordServiceImpl implements TravelRecordService {

    @Autowired
    UserDao userDao;

    @Autowired
    TravelRecordDao travelRecordDao;

    @Autowired
    CreditService creditService;

    @Override
    public Msg queryExchangeRecord(Map<String, Object> params) {
        log.info("queryExchangeRecord: params[{}]", params.toString());

        List<TravelRecord> travelRecords = travelRecordDao.queryTravelRecord(params);

        JSONObject data = new JSONObject();
        data.put("TravelRecords", travelRecords);

        return MsgUtil.makeMsg(MsgCode.SUCCESS, data);
    }

    @Override
    public Msg startTravel(long userid, String vehicleType) {
        log.info("startTravel: userid[{}], vehicleType[{}]", userid, vehicleType);
        // make start time
        String startTime = TimeUtil.getTimeRmpFormat();
        // check userid exist
        List<User> userList = userDao.queryUser(new HashMap<String, Object>(){{put(Constant.USER_ID, userid);}});
        if (userList.isEmpty()) {
            log.warn("userid: {} not exist", userid);
            return MsgUtil.makeMsg(MsgCode.ERROR, "用户id不存在");
        }
        User u = userList.get(0);
        // TODO: check user previous startTravel

        // travel record dao create
        TravelRecord tr = travelRecordDao.startTravelRecord(userid, u.getUsername(), vehicleType, startTime);
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(tr);
        // start travel flag
        u.setIstraveling(1);
        // user dao update
        u.addTravelrecords(tr);
        User updateU = userDao.putUser(u);
        log.info("insert travel record to user: {}", updateU.toString());

        return MsgUtil.makeMsg(MsgCode.SUCCESS, "开始出行", jsonObject);
    }

    @Override
    public Msg endTravel(long travelRecordID, long userid) {
        log.info("endTravel: userid[{}], travelRecordID[{}]", userid, travelRecordID);
        // get origin record created by startTravel
        List<TravelRecord> trList = travelRecordDao.queryTravelRecord(new HashMap<String, Object>(){{put(Constant.TR_ID, travelRecordID);}});
        if (trList.isEmpty()) {
            log.warn("recordId: {} not exist", travelRecordID);
            return MsgUtil.makeMsg(MsgCode.ERROR, "出行记录不存在");
        }
        TravelRecord origin = trList.get(0);
        log.info("get origin travel record: {}", origin.toString());
        Assert.isTrue(userid == origin.getUserid(), "出行记录不匹配");
        // TODO: check not finished
        if (origin.getIsfinished() == 1) {
            log.warn("try endTravel with finished travel record");
            return MsgUtil.makeMsg(MsgCode.ERROR, "当此出行已结束", (JSONObject) JSONObject.toJSON(origin));
        }
        // make end time
        String endTime = TimeUtil.getTimeRmpFormat();
        origin.setEndtime(endTime);
        origin.setIsfinished(1);
        // TODO, gain credit
        Date start = TimeUtil.rmpSDF2Date(origin.getStarttime());
        Date end = TimeUtil.rmpSDF2Date(origin.getEndtime());
        int creditGained = creditService.gainCredit(start, end, origin.getVehicletype());
        origin.setCredit(creditGained);
        // travel record dao put
        TravelRecord tr = travelRecordDao.putTravelRecord(origin);
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(tr);

        // update user credit
        List<User> userList = userDao.queryUser(new HashMap<String, Object>(){{put(Constant.USER_ID, userid);}});
        if (userList.isEmpty()) {
            log.warn("userid: {} not exist", userid);
            return MsgUtil.makeMsg(MsgCode.ERROR, "用户id不存在");
        }
        User u = userList.get(0);
        u.addCredit(creditGained);
        // end travel flag
        u.setIstraveling(0);
        // user dao put
        User updatedU = userDao.putUser(u);
        log.info("endTravel updated user: {}", updatedU.toString());

        return MsgUtil.makeMsg(MsgCode.SUCCESS, "结束出行", jsonObject);
    }
}
