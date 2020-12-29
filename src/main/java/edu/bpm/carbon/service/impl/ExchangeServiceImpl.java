package edu.bpm.carbon.service.impl;

import com.alibaba.fastjson.JSONObject;
import edu.bpm.carbon.constant.Constant;
import edu.bpm.carbon.dao.ExchangeRewardDao;
import edu.bpm.carbon.dao.FluctuationDao;
import edu.bpm.carbon.dao.RewardDao;
import edu.bpm.carbon.dao.UserDao;
import edu.bpm.carbon.entity.ExchangeRecord;
import edu.bpm.carbon.entity.Reward;
import edu.bpm.carbon.entity.User;
import edu.bpm.carbon.entity.mongo.Fluctuation;
import edu.bpm.carbon.service.ExchangeService;
import edu.bpm.carbon.utils.msgutils.Msg;
import edu.bpm.carbon.utils.msgutils.MsgCode;
import edu.bpm.carbon.utils.msgutils.MsgUtil;
import edu.bpm.carbon.utils.timeutils.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ExchangeServiceImpl implements ExchangeService {

    @Autowired
    UserDao userDao;

    @Autowired
    RewardDao rewardDao;

    @Autowired
    ExchangeRewardDao exchangeRewardDao;

    @Autowired
    FluctuationDao fluctuationDao;

    @Override
    public Msg queryExchangeRecord(Map<String, Object> params) {
        log.info("queryExchangeRecord: params[{}]", params.toString());

        List<ExchangeRecord> exchangeRecords = exchangeRewardDao.queryExchangeRecord(params);

        JSONObject data = new JSONObject();
        data.put("ExchangeRecords", exchangeRecords);

        return MsgUtil.makeMsg(MsgCode.SUCCESS, data);
    }

    @Override
    public Msg makeExchange(long userid, long rewardid, int quantity) {
        log.info("makeExchange userid=[{}], rewardid=[{}], quantity=[{}]", userid, rewardid, quantity);

        // 获取对应的用户
        List<User> userList = userDao.queryUser(new HashMap<String, Object>(){{put(Constant.USER_ID, userid);}});
        if (userList.isEmpty()) {
            log.warn("userid: {} not exist", userid);
            return MsgUtil.makeMsg(MsgCode.ERROR, "用户id不存在");
        }
        User u = userList.get(0);
        // 获取对应的奖励物品
        List<Reward> rewardList = rewardDao.queryReward(new HashMap<String, Object>(){{put(Constant.REWARD_ID, rewardid);}});
        if (rewardList.isEmpty()) {
            log.warn("rewardid: {} not exist", rewardid);
            return MsgUtil.makeMsg(MsgCode.ERROR, "兑换物品id不存在");
        }
        Reward reward = rewardList.get(0);
        // 检查物品库存数量
        if (reward.getInventory() < quantity) {
            log.warn("reward inventory not enough, remain [{}], need [{}]", reward.getInventory(), quantity);
            return MsgUtil.makeMsg(MsgCode.ERROR, "兑换物品库存不足");
        }
        // 检查用户积分
        int creditneed = reward.getCredit() * quantity;
        if (u.getCredit() < creditneed) {
            log.warn("user credit not enough, remain [{}], need [{}]", u.getCredit(), creditneed);
            return MsgUtil.makeMsg(MsgCode.ERROR, "用户积分不足");
        }
        // 兑换条件成立，写入兑换请求
        // TODO, lock
        ExchangeRecord er = new ExchangeRecord();
        er.setUserid(userid);
        er.setUsername(u.getUsername());
        er.setRewardid(rewardid);
        er.setQuantity(quantity);
        er.setUnitcredit(reward.getCredit());
        er.setTotalcredit(creditneed);
        er.setExchangetime(TimeUtil.getTimeRmpFormat());
        er.setRewardname(reward.getName());
        er.setRewardinfo(reward);
        ExchangeRecord result = exchangeRewardDao.postExchangeRecord(er);
        log.info("makeExchange success: [{}]", result.toString());
        // 更新用户积分，添加对应记录
        u.setCredit(u.getCredit() - creditneed);
        u.addExchangeRecord(result);
        u = userDao.putUser(u);
        log.info("update user: [{}]", u.toString());
        // 更新奖励物品库存
        reward.exchange(quantity);
        reward = rewardDao.putReward(reward);
        log.info("update reward: [{}]",reward.toString());

        // 记录到 价格波动所需中
        Fluctuation fluctuation = fluctuationDao.findOneFluctuation();
        fluctuation.addExchangedCredit(creditneed);
        fluctuation.addExchangedTotalPrice(quantity * reward.getPrice());
        fluctuationDao.updateFluctuation(fluctuation);

        return MsgUtil.makeMsg(MsgCode.SUCCESS, "兑换成功", (JSONObject) JSONObject.toJSON(result));
    }

    @Override
    public Msg queryReward(Map<String, Object> params) {
        log.info("queryReward: params[{}]", params.toString());

        List<Reward> rewards = rewardDao.queryReward(params);

        JSONObject data = new JSONObject();
        data.put("Rewards", rewards);

        return MsgUtil.makeMsg(MsgCode.SUCCESS, data);
    }
}
