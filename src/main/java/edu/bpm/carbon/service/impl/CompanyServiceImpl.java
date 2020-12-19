package edu.bpm.carbon.service.impl;

import edu.bpm.carbon.dao.CompanyDao;
import edu.bpm.carbon.dao.TravelRecordDao;
import edu.bpm.carbon.service.CompanyService;
import edu.bpm.carbon.utils.msgutils.Msg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    CompanyDao companyDao;

    @Autowired
    TravelRecordDao travelRecordDao;

    @Override
    public Msg queryTravelRecordByCompanyID(long companyId) {
        return null;
    }
}
