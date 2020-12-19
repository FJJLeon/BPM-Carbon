package edu.bpm.carbon.service.impl;

import edu.bpm.carbon.dao.CarbonOrderDao;
import edu.bpm.carbon.dao.CompanyDao;
import edu.bpm.carbon.service.CarbonOrderService;
import edu.bpm.carbon.utils.msgutils.Msg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class CarbonOrderServiceImpl implements CarbonOrderService {

    @Autowired
    CompanyDao companyDao;

    @Autowired
    CarbonOrderDao orderDao;

    @Override
    public Msg queryCarbonOrder(Map<String, Object> params) {
        return null;
    }

    @Override
    public Msg queryCarbonOrderByCompany(long companyId) {
        return null;
    }

    @Override
    public Msg queryCarbonOrderByCompanyAndStatus(long companyId, String status) {
        return null;
    }

    @Override
    public Msg createCarbonOrder(long companyId, long amount) {
        return null;
    }

    @Override
    public Msg reviewCarbonOrder(long orderId, long companyId, String operation) {
        return null;
    }
}
