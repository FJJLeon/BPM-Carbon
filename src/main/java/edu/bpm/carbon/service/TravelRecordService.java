package edu.bpm.carbon.service;

import edu.bpm.carbon.utils.msgutils.Msg;

import java.util.Map;

public interface TravelRecordService {

    /**
     * 普通查询出行记录
     * @param params Map 形式的参数
     * @return 查询结果消息
     */
    Msg queryExchangeRecord(Map<String, Object> params);

    /**
     * 开始出行，新建出行记录
     * @param userid 用户id
     * @param toolType 出行工具类型
     * @return 开始出行的消息，新建的 TravelRecord
     */
    Msg startTravel(long userid, String toolType);

    /**
     * 结束出行，修改出行记录
     * @param travelRecordID 开始出行时创建的出行记录 id
     * @param userid 用户 id
     * @return 结束出行的消息，修改后的 TravelRecord
     */
    Msg endTravel(long travelRecordID, long userid);

}
