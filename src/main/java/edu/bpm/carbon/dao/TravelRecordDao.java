package edu.bpm.carbon.dao;

import edu.bpm.carbon.entity.TravelRecord;

import java.util.List;
import java.util.Map;

public interface TravelRecordDao {

    /**
     * 普通查询, GET
     * @param params Map 形式的参数表
     * @return 查询结果，TravelRecord 列表
     */
    List<TravelRecord> queryTravelRecord(Map<String, Object> params);

    /**
     * 开始出行，新建，POST
     * @param userid 该记录发起用户
     * @param toolType 用户所使用的出行工具类别
     * @param startTime 用户开始出行的时间
     * @return 新建的 TravelRecord
     */
    TravelRecord startTravelRecord(long userid, String username, String toolType, String startTime);

    /**
     * 结束出行，修改，PUT
     * @param id 对应的开始出行时新建的记录 id
     * @param userid 该记录发起用户
     * @param toolType 用户所使用的出行工具类别
     * @param startTime 用户开始出行的时间
     * @param endTime 用户结束出行的时间
     * @return 修改后的 TravelRecord
     */
    TravelRecord endTravelRecord(long id, long userid, String username, String toolType, String startTime, String endTime);
}
