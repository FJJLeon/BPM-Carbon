package edu.bpm.carbon.dao.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import edu.bpm.carbon.constant.Constant;
import edu.bpm.carbon.dao.TravelRecordDao;
import edu.bpm.carbon.entity.TravelRecord;
import edu.bpm.carbon.utils.httputils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.security.auth.login.Configuration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Repository
@Slf4j
public class TravelRecordDaoImpl implements TravelRecordDao {

    @Value("${rmp.project.url}")
    String PROJECT_URL;
    @Value("${rmp.resource.travelrecord.url}")
    String TR_URL = PROJECT_URL;

    @Override
    public List<TravelRecord> queryTravelRecord(Map<String, Object> params) {
        return null;
    }

    @Override
    public TravelRecord startTravelRecord(long userid, String username, String vehicleType, String startTime) {
        // construct param
        Map<String, Object> postParam = new HashMap<String, Object>(){{
           put(Constant.TR_USERID, userid);
           put(Constant.TR_USERNAME, username);
           put(Constant.TR_VEHICLETYPE, vehicleType);
           put(Constant.TR_STARTTIME, startTime);
        }};
        // post request
        JSONObject postResponse = HttpUtil.httpPostJSON(TR_URL, postParam);
        log.info(postResponse.toString());
        // transfer to JavaObject

        String test = postResponse.toString();
        TravelRecord testTR = new Gson().fromJson(test, TravelRecord.class);
        TravelRecord travelRecord = JSONObject.toJavaObject(postResponse, TravelRecord.class);

        log.info("Gson: " + testTR.toString());
        log.info("fastJson: " + travelRecord.toString());
        return testTR;
    }

    @Override
    public TravelRecord endTravelRecord(long id, long userid, String username, String toolType, String startTime, String endTime) {
        return null;
    }
}
