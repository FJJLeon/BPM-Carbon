package edu.bpm.carbon.dao;

import edu.bpm.carbon.entity.Reward;

import java.util.List;
import java.util.Map;

public interface RewardDao {

    /**
     * 普通查询， GET
     * @param params Map 形式的参数
     * @return 查询结果，Reward 列表
     */
    List<Reward> queryReward(Map<String, Object> params);

    /**
     * 新增奖励物品，POST
     * @param reward 实体对象，其中 id 为空
     * @return RMP Post 返回的 Reward 对象，给定 id
     */
    Reward postReward(Reward reward);

    /**
     * 修改奖励物品，PUT
     * 如修改库存量
     * @param reward 实体对象
     * @return 修改后的 Reward 对象
     */
    Reward putReward(Reward reward);
}
