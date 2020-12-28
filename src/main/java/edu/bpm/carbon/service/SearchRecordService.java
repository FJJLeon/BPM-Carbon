package edu.bpm.carbon.service;

import edu.bpm.carbon.entity.mongo.SingleRecord;
import edu.bpm.carbon.utils.msgutils.Msg;

public interface SearchRecordService {

    /**
     * 更新（或者插入）新的搜索记录
     * @param singleRecord 单个搜索记录
     * @return 消息
     */
    Msg updateOrInsertRecommendRecord(SingleRecord singleRecord, int weight);

    /**
     * 推荐奖励物品
     * @param userid 目标用户id
     * @return 给该用户 id
     */
    Msg recommendReward(long userid);
}
