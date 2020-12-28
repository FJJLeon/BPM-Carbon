package edu.bpm.carbon.service.impl;

import com.alibaba.fastjson.JSONObject;
import edu.bpm.carbon.dao.RewardDao;
import edu.bpm.carbon.dao.SearchRecordDao;
import edu.bpm.carbon.entity.Reward;
import edu.bpm.carbon.entity.mongo.SearchRecord;
import edu.bpm.carbon.entity.mongo.SingleRecord;
import edu.bpm.carbon.service.SearchRecordService;
import edu.bpm.carbon.utils.msgutils.Msg;
import edu.bpm.carbon.utils.msgutils.MsgCode;
import edu.bpm.carbon.utils.msgutils.MsgUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class SearchRecordServiceImpl implements SearchRecordService {

    @Autowired
    SearchRecordDao searchRecordDao;

    @Autowired
    RewardDao rewardDao;

    @Override
    public Msg updateOrInsertRecommendRecord(SingleRecord singleRecord, int weight) {
        log.info("updateOrInsertRecommendRecord: [{}]", singleRecord.toString());

        // find recommend record by userid in mongodb
        SearchRecord searchRecord = searchRecordDao.findSearchRecordByUserId(singleRecord.getUserid());
        log.info("get mongo sr: [{}]", searchRecord.toString());
        // new user document
        if (searchRecord == null) {
            log.info("searchRecord NOT found, insert new document to mongodb");
            searchRecord = new SearchRecord();
            searchRecord.setUserId(singleRecord.getUserid());
            List<Long> records = singleRecord.getRecords();
            for (Long rewardId : records) {
                // new RewardCounts
                searchRecord.newRewardCount(rewardId, weight);
            }
        }
        // user exist, update document
        else {
            log.info(("searchRecord Found, update it"));
            List<Long> records = singleRecord.getRecords();
            for (Long rewardId : records) {
                // if the rewardId exist, increase it by weight
                // or new it with weight
                searchRecord.increaseRewardCount(rewardId, weight);
            }
        }

        searchRecordDao.saveSearchRecord(searchRecord);

        return MsgUtil.makeMsg(MsgCode.SUCCESS);
    }

    @Override
    public Msg recommendReward(long userid) {
        log.info("recommendReward for userid=[{}]", userid);
        // all rewards
        List<Reward> rewards = rewardDao.queryReward(new HashMap<>());
        // user search RewardCounts record
        SearchRecord searchRecord = searchRecordDao.findSearchRecordByUserId(userid);
        // no recommend
        if (searchRecord == null) {
            log.info("user [{}] no search record", userid);
            JSONObject data = new JSONObject();
            data.put("Rewards", rewards);
            return MsgUtil.makeMsg(MsgCode.SUCCESS, data);
        }

        log.info("11111 : [{}]", rewards.toString());
        log.info("22222 : [{}]", searchRecord.toString());
        searchRecord.sortRewardCounts();
        log.info("33333 : [{}]", searchRecord.toString());

        // sort
        List<Reward> result = new ArrayList<>();
        List<SearchRecord.RewardCount> rewardCounts = searchRecord.getRewardCounts();
        int len = rewardCounts.size();
        int rewardsLen = rewards.size();
        for(int i = 0; i < len; ++i){
            long rewardId = rewardCounts.get(i).getRewardId();
            int index = -1;
            for(int j = 0 ; j < rewardsLen; ++j){
                if(rewards.get(j).getId() == rewardId){
                    index = j;
                }
            }
            assert index != -1;
            result.add(rewards.get(index));
        }
        for(int i = 0; i < rewardsLen; ++i){
            long rewardId = rewards.get(i).getId();
            int index = -1;
            for(int j = 0; j < len; ++j){
                if(rewardCounts.get(j).getRewardId() == rewardId){
                    index = j;
                }
            }
            if(index == -1)
                result.add(rewards.get(i));
        }

        JSONObject data = new JSONObject();
        data.put("Rewards", result);
        return MsgUtil.makeMsg(MsgCode.SUCCESS, data);
    }
}
