package edu.bpm.carbon.dao.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import edu.bpm.carbon.constant.Constant;
import edu.bpm.carbon.dao.ExchangeRewardDao;
import edu.bpm.carbon.entity.ExchangeRecord;
import edu.bpm.carbon.entity.Reward;
import edu.bpm.carbon.utils.httputils.HttpUtil;
import edu.bpm.carbon.utils.httputils.Map2Param;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class ExchangeRewardDaoImpl implements ExchangeRewardDao {

    @Value("${rmp.resource.exchangerecord.url}")
    String ER_URL;

    @Override
    public List<ExchangeRecord> queryExchangeRecord(Map<String, Object> params) {
        log.info("queryExchangeRecord: paramsMap[{}]", params.toString());

        String rmpParam = Map2Param.genRmpParam(Constant.ER_RESOURCE, params);

        JSONObject jsonObject = HttpUtil.httpGetJSON(ER_URL, rmpParam);
        JSONArray erFound = jsonObject.getJSONArray(Constant.ER_RESOURCE);

        List<ExchangeRecord> result = new ArrayList<>();
        if (erFound == null) {
            return result;
        }

        JSONObject jsonER;
        ExchangeRecord er;
        for (int i = 0; i < erFound.size(); i++) {
            jsonER = erFound.getJSONObject(i);
            er = new Gson().fromJson(jsonER.toString(), ExchangeRecord.class);
            result.add(er);
        }
        return result;
    }

    @Override
    public ExchangeRecord postExchangeRecord(ExchangeRecord er) {
        log.info("postExchangeRecord: [{}]", er.toString());
        // construct param map
        Map<String, Object> postParam = new HashMap<String, Object>(){{
            put(Constant.ER_USERID, er.getUserid());
            put(Constant.ER_USERNAME, er.getUsername());
            put(Constant.ER_REWARDID, er.getRewardid());
            put(Constant.ER_QUANTITY, er.getQuantity());
            put(Constant.ER_REWARDUNIT, er.getRewardunit());
            put(Constant.ER_CREDITCOST, er.getCreditcost());
            put(Constant.ER_EXCHANGETIMEd, er.getExchangetime());
            put(Constant.ER_REWARDNAME, er.getRewardname());
        }};
        // post request
        JSONObject postResponse = HttpUtil.httpPostJSON(ER_URL, postParam);
        log.info(postResponse.toString());
        // transfer to JavaObject
        ExchangeRecord result = new Gson().fromJson(postResponse.toString(), ExchangeRecord.class);

        return result;
    }

    @Override
    public ExchangeRecord putExchangeRecord(ExchangeRecord er) {
        log.info("putExchangeRecord: [{}]", er.toString());
        // construct param map
        Map<String, Object> postParam = new HashMap<String, Object>(){{
            put(Constant.ER_ID, er.getId());
            put(Constant.ER_USERID, er.getUserid());
            put(Constant.ER_USERNAME, er.getUsername());
            put(Constant.ER_REWARDID, er.getRewardid());
            put(Constant.ER_QUANTITY, er.getQuantity());
            put(Constant.ER_REWARDUNIT, er.getRewardunit());
            put(Constant.ER_CREDITCOST, er.getCreditcost());
            put(Constant.ER_EXCHANGETIMEd, er.getExchangetime());
            put(Constant.ER_REWARDNAME, er.getRewardname());
        }};
        // put request
        String putURL = ER_URL + er.getId();
        JSONObject putResponse = HttpUtil.httpPutJSON(putURL, postParam);
        log.info(putResponse.toString());
        // transfer to JavaObject
        ExchangeRecord result = new Gson().fromJson(putResponse.toString(), ExchangeRecord.class);

        return result;
    }
}
