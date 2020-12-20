package edu.bpm.carbon.service.impl;

import com.alibaba.fastjson.JSONObject;
import edu.bpm.carbon.constant.Constant;
import edu.bpm.carbon.dao.CompanyDao;
import edu.bpm.carbon.dao.TravelRecordDao;
import edu.bpm.carbon.entity.Company;
import edu.bpm.carbon.entity.TravelRecord;
import edu.bpm.carbon.service.CompanyService;
import edu.bpm.carbon.service.TravelRecordService;
import edu.bpm.carbon.utils.msgutils.Msg;
import edu.bpm.carbon.utils.msgutils.MsgCode;
import edu.bpm.carbon.utils.msgutils.MsgUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    CompanyDao companyDao;

    @Autowired
    TravelRecordDao travelRecordDao;

    @Override
    public Msg queryTravelRecordByCompanyID(long companyId) {
        log.info("queryTravelRecordByCompanyID: companyId=[{}]", companyId);

        Company company = companyDao.queryCompanyById(companyId);
        if (0 == company.getId()) {
            log.warn("company ID not exist");
            return MsgUtil.makeMsg(MsgCode.ERROR, "公司ID不存在");
        }

        String type = company.getType();

        List<TravelRecord> TRs = travelRecordDao.queryTravelRecord(new HashMap<String, Object>(){{put(Constant.TR_VEHICLETYPE, type);}});

        // reverse
        Collections.reverse(TRs);

        JSONObject travelRecords = new JSONObject();
        travelRecords.put("TravelRecords", TRs);

        Msg msg = MsgUtil.makeMsg(MsgCode.SUCCESS, "获取本公司出行记录", travelRecords);

        return msg;
    }
}
