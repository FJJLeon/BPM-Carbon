package edu.bpm.carbon.dao.impl;

import edu.bpm.carbon.dao.CompanyDao;
import edu.bpm.carbon.entity.Company;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class CompanyDaoImpl implements CompanyDao {
    @Override
    public List<Company> queryCompany(Map<String, Object> params) {
        return null;
    }

    @Override
    public Company queryCompanyByType(String type) {
        return null;
    }

    @Override
    public Company putCompany(Company company) {
        return null;
    }
}
