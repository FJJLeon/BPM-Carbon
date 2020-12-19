package edu.bpm.carbon.dao;

import edu.bpm.carbon.entity.Company;

import java.util.List;
import java.util.Map;

public interface CompanyDao {

    /**
     * 普通查询， GET
     * @param params Map 形式的参数
     * @return 查询结果，Company 列表
     */
    List<Company> queryCompany(Map<String, Object> params);

    /**
     * 通过 type 查询公司， GET
     * @param type 出行工具公司类似
     *             注：本项目中每类公司只有一个，故只返回单个公司对象
     * @return 查询结果，对应类型的公司
     */
    Company queryCompanyByType(String type);

    // NO POST

    /**
     * 修改奖励物品，PUT
     * 如修改 order 信息
     * @param company 实体对象
     * @return 修改后的 Company 对象
     */
    Company putCompany(Company company);
}
