package edu.bpm.carbon.dao.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import edu.bpm.carbon.constant.Constant;
import edu.bpm.carbon.dao.CompanyDao;
import edu.bpm.carbon.entity.Company;
import edu.bpm.carbon.utils.httputils.HttpUtil;
import edu.bpm.carbon.utils.httputils.Map2Param;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class CompanyDaoImpl implements CompanyDao {

    @Value("${rmp.resource.company.url}")
    String COMP_URL;

    @Override
    public List<Company> queryCompany(Map<String, Object> params) {
        log.info("queryCompany: paramMap[{}]", params.toString());

        String rmpParam = Map2Param.genRmpParam(Constant.COMP_RESOURCE, params);

        JSONObject jsonObject = HttpUtil.httpGetJSON(COMP_URL, rmpParam);
        JSONArray companyFound = jsonObject.getJSONArray(Constant.COMP_RESOURCE);

        List<Company> result = new ArrayList<>();
        if (companyFound == null) {
            return result;
        }

        JSONObject jsonCompany;
        Company company;
        for (int i = 0; i < companyFound.size(); i++) {
            jsonCompany = companyFound.getJSONObject(i);
            company = new Gson().fromJson(jsonCompany.toString(), Company.class);
            result.add(company);
        }

        return result;
    }

    @Override
    public Company queryCompanyByType(String type) {
        log.info("queryCompanyByType: type=[{}]", type);

        String param = String.format("%s.%s=%s",
                Constant.COMP_RESOURCE, Constant.COMP_TYPE, type);
        JSONObject jsonObject = HttpUtil.httpGetJSON(COMP_URL, param);
        JSONArray companyFound = jsonObject.getJSONArray(Constant.COMP_RESOURCE);

        if (companyFound != null && companyFound.size() != 1) {
            JSONObject jsonCompany = companyFound.getJSONObject(0);
            return new Gson().fromJson(jsonCompany.toString(), Company.class);
        }
        else {
            log.warn("Something wrong in RMP Company Resource");
            return new Company();
        }
    }

    @Override
    public Company queryCompanyById(long companyId) {
        log.info("queryCompanyById: companyId=[{}]", companyId);

        List<Company> companies = queryCompany(new HashMap<String, Object>(){{put(Constant.COMP_ID, companyId);}});
        if (companies.size() != 1) {
            log.warn("Something wrong in RMP Company Resource");
            return new Company();
        }
        else {
            return companies.get(0);
        }
    }

    @Override
    public Company putCompany(Company company) {
        log.info("putCompany: [{}]", company.toString());

        Map<String, Object> putParam = new HashMap<String, Object>(){{
            put(Constant.COMP_ID, company.getId());
            put(Constant.COMP_NAME, company.getName());
            put(Constant.COMP_TYPE, company.getType());
            put(Constant.COMP_ADDRESS, company.getAddress());
            put(Constant.COMP_PHONE, company.getPhone());
            put(Constant.COMP_REMAINCE, company.getRemaincarbonemission());
            put(Constant.COMP_FUNDS, company.getFunds());
            put(Constant.COMP_PENDINGORDER, company.getPendingorder());
            put(Constant.COMP_ACORDERS, company.getAcceptorders());
            put(Constant.COMP_RJORDERS, company.getRejectorders());
        }};

        String putURL = COMP_URL + company.getId();
        JSONObject putResponse = HttpUtil.httpPutJSON(putURL, putParam);
        log.info("putResponse: {}", putResponse.toString());

        Company result = new Gson().fromJson(putResponse.toString(), Company.class);
        return result;

    }
}
