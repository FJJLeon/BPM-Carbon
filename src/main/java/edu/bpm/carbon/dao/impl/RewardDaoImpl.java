package edu.bpm.carbon.dao.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import edu.bpm.carbon.constant.Constant;
import edu.bpm.carbon.dao.RewardDao;
import edu.bpm.carbon.entity.Reward;
import edu.bpm.carbon.entity.TravelRecord;
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
public class RewardDaoImpl implements RewardDao {

    @Value("${rmp.resource.reward.url}")
    String REWARD_URL;

    @Override
    public List<Reward> queryReward(Map<String, Object> params) {
        log.info("queryReward: paramMap[{}]", params.toString());

        String rmpParam = Map2Param.genRmpParam(Constant.REWARD_RESOURCE, params);

        JSONObject jsonObject = HttpUtil.httpGetJSON(REWARD_URL, rmpParam);
        JSONArray rewardFound = jsonObject.getJSONArray(Constant.REWARD_RESOURCE);

        List<Reward> result = new ArrayList<>();
        if (rewardFound == null) {
            return result;
        }

        JSONObject jsonReward;
        Reward reward;
        for (int i = 0; i < rewardFound.size(); i++) {
            jsonReward = rewardFound.getJSONObject(i);
            reward = new Gson().fromJson(jsonReward.toString(), Reward.class);
            result.add(reward);
        }
        return result;
    }

    @Override
    public Reward postReward(Reward reward) {
        log.info("postReward: [{}]", reward.toString());
        // construct param map
        Map<String, Object> postParam = new HashMap<String, Object>(){{
            put(Constant.REWARD_NAME, reward.getName());
            put(Constant.REWARD_CREDIT, reward.getCredit());
            put(Constant.REWARD_INVENTORY, reward.getInventory());
            put(Constant.REWARD_EXCHANGED, reward.getExchanged());
            put(Constant.REWARD_PRICE, reward.getPrice());
            put(Constant.REWARD_IMAGE, reward.getImage());
        }};
        // post request
        JSONObject postResponse = HttpUtil.httpPostJSON(REWARD_URL, postParam);
        log.info(postResponse.toString());
        // transfer to JavaObject
        String s = postResponse.toString();
        Reward result = new Gson().fromJson(s, Reward.class);

        return result;
    }

    @Override
    public Reward putReward(Reward reward) {
        log.info("putReward: [{}]", reward.toString());
        // construct param map
        Map<String, Object> putParam = new HashMap<String, Object>(){{
            put(Constant.REWARD_ID, reward.getId());
            put(Constant.REWARD_NAME, reward.getName());
            put(Constant.REWARD_CREDIT, reward.getCredit());
            put(Constant.REWARD_INVENTORY, reward.getInventory());
            put(Constant.REWARD_EXCHANGED, reward.getExchanged());
            put(Constant.REWARD_PRICE, reward.getPrice());
            put(Constant.REWARD_IMAGE, reward.getImage());
        }};
        // put request
        String putURL = REWARD_URL + reward.getId();
        JSONObject putResponse = HttpUtil.httpPutJSON(putURL, putParam);
        log.info(putResponse.toString());
        // transfer to JavaObject
        Reward result = new Gson().fromJson(putResponse.toString(), Reward.class);

        return result;
    }
}
