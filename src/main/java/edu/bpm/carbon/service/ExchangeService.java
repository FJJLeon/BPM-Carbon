package edu.bpm.carbon.service;

import edu.bpm.carbon.utils.msgutils.Msg;

import java.util.Map;

public interface ExchangeService {

    /**
     * 普通查询兑换记录
     * @param params Map 形式的参数
     * @return 查询结果消息
     */
    Msg queryExchangeRecord(Map<String, Object> params);

    /**
     * 用户进行兑换请求
     * 要求对应用户有足够的 credit，对应奖励物品有足够的 inventory
     * @param userid 用户id
     * @param rewardid 兑换奖励id
     * @param quantity 兑换奖励数量
     * @return 兑换成功与否的 Msg
     */
    Msg makeExchange(long userid, long rewardid, int quantity);
}
