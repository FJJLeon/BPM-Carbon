package edu.bpm.carbon.dao.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.sun.org.apache.xerces.internal.impl.xpath.XPath;
import edu.bpm.carbon.constant.Constant;
import edu.bpm.carbon.dao.TravelRecordDao;
import edu.bpm.carbon.entity.TravelRecord;
import edu.bpm.carbon.utils.httputils.HttpUtil;
import edu.bpm.carbon.utils.httputils.Map2Param;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.security.auth.login.Configuration;
import java.util.*;

@Repository
@Slf4j
public class TravelRecordDaoImpl implements TravelRecordDao {

    @Value("${rmp.resource.travelrecord.url}")
    String TR_URL;

    @Override
    public List<TravelRecord> queryTravelRecord(Map<String, Object> params) {
        log.info("queryTravelRecord: paramMap[{}]", params.toString());

        String rmpParam = Map2Param.genRmpParam(Constant.TR_RESOURCE, params);

        JSONObject jsonObject = HttpUtil.httpGetJSON(TR_URL, rmpParam);
        JSONArray trFound = jsonObject.getJSONArray(Constant.TR_RESOURCE);

        List<TravelRecord> result = new ArrayList<>();
        if (trFound == null) {
            return result;
        }

        JSONObject jsonTR;
        TravelRecord tr;
        for (int i = 0; i < trFound.size(); i++) {
            jsonTR = trFound.getJSONObject(i);
            tr = new Gson().fromJson(jsonTR.toString(), TravelRecord.class);
            result.add(tr);
        }
        return result;
    }

    @Override
    public TravelRecord startTravelRecord(long userid, String username, String vehicleType, String startTime) {
        log.info("startTravelRecord: userid[{}],username[{}],vehicleType[{}],startTime[{}]", userid, username, vehicleType, startTime);
        // construct param map
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
        /** TODO,fastjson resolve field missing, such as "starttime"
        String test = postResponse.toString();
        TravelRecord testTR = new Gson().fromJson(test, TravelRecord.class);
        TravelRecord travelRecord = JSONObject.toJavaObject(postResponse, TravelRecord.class);

        log.info("Gson: " + testTR.toString());
        log.info("fastJson: " + travelRecord.toString());
        */

        String s = postResponse.toString();
        TravelRecord travelRecord = new Gson().fromJson(s, TravelRecord.class);

        return travelRecord;
    }

    @Override
    public TravelRecord putTravelRecord(TravelRecord travelRecord) {
        String putURL = TR_URL + "/" + travelRecord.getId();
        log.info("putTravelRecord: [{}]", travelRecord.toString());
        // construct param map
        Map<String, Object> putParam = new HashMap<String, Object>(){{
            put(Constant.TR_ID, travelRecord.getId());
            put(Constant.TR_USERID, travelRecord.getUserid());
            put(Constant.TR_VEHICLETYPE, travelRecord.getVehicletype());
            put(Constant.TR_STARTTIME, travelRecord.getStarttime());
            put(Constant.TR_ENDTIME, travelRecord.getEndtime());
            put(Constant.TR_ISFINISHED, travelRecord.getIsfinished());
            put(Constant.TR_USERNAME, travelRecord.getUsername());
            put(Constant.TR_CREDIT, travelRecord.getCredit());
        }};
        // put request
        JSONObject putResponse = HttpUtil.httpPutJSON(putURL, putParam);
        // transfer to JavaObject
        //TravelRecord result = JSONObject.toJavaObject(putResponse, TravelRecord.class);
        TravelRecord result = new Gson().fromJson(putResponse.toString(), TravelRecord.class);

        return result;
    }
}
