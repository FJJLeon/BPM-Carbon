package edu.bpm.carbon.service;

import edu.bpm.carbon.utils.msgutils.Msg;

public interface CompanyService {

    /**
     * 使用 companyId 查询该公司对应的交通工具所有用户的出行记录
     * @param companyId 公司 id
     * @return 对应的所用出行记录列表消息
     */
    Msg queryTravelRecordByCompanyID(long companyId);

}
