package edu.bpm.carbon.dao.impl;

import edu.bpm.carbon.dao.CarbonOrderDao;
import edu.bpm.carbon.entity.CarbonOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class CarbonOrderDaoImpl implements CarbonOrderDao {
    @Override
    public List<CarbonOrder> queryCarbonOrder(Map<String, Object> params) {
        return null;
    }

    @Override
    public CarbonOrder postCarbonOrder(CarbonOrder carbonOrder) {
        return null;
    }

    @Override
    public CarbonOrder putCarbonOrder(CarbonOrder carbonOrder) {
        return null;
    }
}
