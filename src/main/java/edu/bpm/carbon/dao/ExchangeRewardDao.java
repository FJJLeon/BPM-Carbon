package edu.bpm.carbon.dao;

import edu.bpm.carbon.entity.ExchangeRecord;

import java.util.List;
import java.util.Map;

public interface ExchangeRewardDao {

    /**
     * 普通查询， GET
     * @param params Map 形式的参数
     * @return 查询结果，ExchangeRecord 列表
     */
    List<ExchangeRecord> queryExchangeRecord(Map<String, Object> params);

    /**
     * 新增奖励物品，POST
     * @param er 实体对象，其中 id 为空
     * @return RMP Post 返回的 ExchangeRecord 对象，给定 id
     */
    ExchangeRecord postExchangeRecord(ExchangeRecord er);

    /**
     * 修改奖励物品，PUT
     * 如修改库存量
     * @param er 实体对象
     * @return 修改后的 ExchangeRecord 对象
     */
    ExchangeRecord putExchangeRecord(ExchangeRecord er);
}
