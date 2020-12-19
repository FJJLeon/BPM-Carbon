package edu.bpm.carbon.dao.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import edu.bpm.carbon.constant.Constant;
import edu.bpm.carbon.dao.CarbonOrderDao;
import edu.bpm.carbon.entity.CarbonOrder;
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
public class CarbonOrderDaoImpl implements CarbonOrderDao {

    @Value("${rmp.resource.carbonorder.url}")
    String CARBOD_URL;

    @Override
    public List<CarbonOrder> queryCarbonOrder(Map<String, Object> params) {
        log.info("queryCarbonOrder: paramMap[{}]", params.toString());

        String rmpParam = Map2Param.genRmpParam(Constant.CARBOD_RESOURCE, params);

        JSONObject jsonObject = HttpUtil.httpGetJSON(CARBOD_URL, rmpParam);
        JSONArray carbonFound = jsonObject.getJSONArray(Constant.CARBOD_RESOURCE);

        List<CarbonOrder> result = new ArrayList<>();
        if (carbonFound == null) {
            return result;
        }

        JSONObject jsonCarbon;
        CarbonOrder carbonOrder;
        for (int i = 0; i < carbonFound.size(); i++) {
            jsonCarbon = carbonFound.getJSONObject(i);
            carbonOrder = new Gson().fromJson(jsonCarbon.toString(), CarbonOrder.class);
            result.add(carbonOrder);
        }
        return result;
    }

    @Override
    public CarbonOrder postCarbonOrder(CarbonOrder carbonOrder) {
        log.info("postCarbonOrder: [{}]", carbonOrder.toString());
        // construct param map
        Map<String, Object> postParam = new HashMap<String, Object>(){{
            put(Constant.CARBOD_COMPID, carbonOrder.getCompanyid());
            put(Constant.CARBOD_AMOUNT, carbonOrder.getAmount());
            put(Constant.CARBOD_UNITPRICE, carbonOrder.getUnitprice());
            put(Constant.CARBOD_STATE, carbonOrder.getState());
            put(Constant.CARBOD_CREATETIME, carbonOrder.getCreatetime());
            put(Constant.CARBOD_REVIEWTIME, carbonOrder.getReviewtime());
            put(Constant.CARBOD_FROM, carbonOrder.getFromcompany());
        }};
        // post request
        JSONObject postResponse = HttpUtil.httpPostJSON(CARBOD_URL, postParam);
        log.info(postResponse.toString());
        // transfer to JavaObject
        CarbonOrder result = new Gson().fromJson(postResponse.toString(), CarbonOrder.class);

        return result;
    }

    @Override
    public CarbonOrder putCarbonOrder(CarbonOrder carbonOrder) {
        log.info("putCarbonOrder: [{}]", carbonOrder.toString());
        // construct param map
        Map<String, Object> putParam = new HashMap<String, Object>(){{
            put(Constant.CARBOD_ID, carbonOrder.getId());
            put(Constant.CARBOD_COMPID, carbonOrder.getCompanyid());
            put(Constant.CARBOD_AMOUNT, carbonOrder.getAmount());
            put(Constant.CARBOD_UNITPRICE, carbonOrder.getUnitprice());
            put(Constant.CARBOD_STATE, carbonOrder.getState());
            put(Constant.CARBOD_CREATETIME, carbonOrder.getCreatetime());
            put(Constant.CARBOD_REVIEWTIME, carbonOrder.getReviewtime());
            put(Constant.CARBOD_FROM, carbonOrder.getFromcompany());
        }};
        // post request
        String putURL = CARBOD_URL + carbonOrder.getId();
        JSONObject putResponse = HttpUtil.httpPostJSON(putURL, putParam);
        log.info(putResponse.toString());
        // transfer to JavaObject
        CarbonOrder result = new Gson().fromJson(putResponse.toString(), CarbonOrder.class);

        return result;
    }
}
