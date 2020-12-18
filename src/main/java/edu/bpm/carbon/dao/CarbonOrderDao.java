package edu.bpm.carbon.dao;

import edu.bpm.carbon.entity.CarbonOrder;
import edu.bpm.carbon.entity.Reward;

import java.util.List;
import java.util.Map;

public interface CarbonOrderDao {

    /**
     * 普通查询， GET
     * @param params Map 形式的参数
     * @return 查询结果，CarbonOrder 列表
     */
    List<CarbonOrder> queryCarbonOrder(Map<String, Object> params);

    /**
     * 新增奖励物品，POST
     * @param carbonOrder 创建订单，其中 id 为空
     * @return RMP Post 返回的 CarbonOrder 对象，给定 id
     */
    CarbonOrder postCarbonOrder(CarbonOrder carbonOrder);

    /**
     * 修改奖励物品，PUT
     * 如修改库存量
     * @param carbonOrder 实体对象
     * @return 修改后的 CarbonOrder 对象
     */
    CarbonOrder putCarbonOrder(CarbonOrder carbonOrder);
}
