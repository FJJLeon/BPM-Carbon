package edu.bpm.carbon.service.impl;

import com.alibaba.fastjson.JSONObject;
import edu.bpm.carbon.constant.Constant;
import edu.bpm.carbon.dao.TravelRecordDao;
import edu.bpm.carbon.dao.UserDao;
import edu.bpm.carbon.entity.TravelRecord;
import edu.bpm.carbon.entity.User;
import edu.bpm.carbon.service.TravelRecordService;
import edu.bpm.carbon.service.UserService;
import edu.bpm.carbon.utils.msgutils.Msg;
import edu.bpm.carbon.utils.msgutils.MsgCode;
import edu.bpm.carbon.utils.msgutils.MsgUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class TravelRecordServiceImpl implements TravelRecordService {

    @Autowired
    UserDao userDao;

    @Autowired
    TravelRecordDao travelRecordDao;

    @Override
    public Msg startTravel(long userid, String vehicleType) {
        log.info("startTravel: userid[{}], vehicleType[{}]", userid, vehicleType);
        // make start time
        Date datetime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTime = sdf.format(datetime);
        // check userid exist
        List<User> userList = userDao.queryUser(new HashMap<String, Object>(){{put(Constant.USER_ID, userid);}});
        if (userList.isEmpty()) {
            log.warn("userid: " + userid + " not exist");
            return MsgUtil.makeMsg(MsgCode.ERROR, "用户id不存在");
        }
        User u = userList.get(0);
        // dao create
        TravelRecord tr = travelRecordDao.startTravelRecord(userid, u.getUsername(), vehicleType, startTime);
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(tr);

        return MsgUtil.makeMsg(MsgUtil.SUCCESS, "开始出行", jsonObject);
    }

    @Override
    public Msg endTravel(long recordID, long userid) {
        return null;
    }
}
