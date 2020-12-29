package edu.bpm.carbon.service.credit;


import edu.bpm.carbon.constant.Constant;
import edu.bpm.carbon.dao.CompanyDao;
import edu.bpm.carbon.dao.FluctuationDao;
import edu.bpm.carbon.entity.Company;
import edu.bpm.carbon.entity.TravelRecord;
import edu.bpm.carbon.entity.mongo.Fluctuation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 积分与减排相关
 */
@Service
@Slf4j
public class CreditService {
    public enum VEHICLE_TYPE{
        BIKE(Constant.VEHICLETYPE_BIKE),
        BUS(Constant.VEHICLETYPE_BUS),
        SUBWAY(Constant.VEHICLETYPE_SUBWAY);

        private String type;

        private VEHICLE_TYPE(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    @Autowired
    CompanyDao companyDao;

    @Autowired
    FluctuationDao fluctuationDao;

    /**
     * 各出行工具类型每分钟能获取的积分数量，单位：个
     */
    public Map<String, Integer> CreditPerMin = new HashMap<String, Integer>(){{
       put(Constant.VEHICLETYPE_BIKE,   20);
       put(Constant.VEHICLETYPE_BUS,    8);
       put(Constant.VEHICLETYPE_SUBWAY, 15);
    }};

    /**
     * 各出行工具类型每分钟能减排量，单位：g
     */
    public Map<String, Integer> DecEmissionPerMin = new HashMap<String, Integer>(){{
        put(Constant.VEHICLETYPE_BIKE,   200);
        put(Constant.VEHICLETYPE_BUS,    100);
        put(Constant.VEHICLETYPE_SUBWAY, 160);
    }};

    /**
     * 计算以 type 类型出行工具出行一段时间所获得的积分
     * @param start 出行开始时间，Date
     * @param end 出行结束时间，Date
     * @param type 出行交通工具类型
     * @return 所能获得的积分
     */
    public int gainCredit(Date start, Date end, String type) {
        int diffMin = (int) ((end.getTime() - start.getTime()) / 1000 + 59) / 60;

        // consume carbon emission
        consumeCE(diffMin, type);

        return diffMin * CreditPerMin.get(type);
    }

    /**
     * 对应出行工具公司消耗 减排量
     * @param diffMin 出行工具使用时间
     * @param type 出行工具类型
     */
    public void consumeCE(int diffMin, String type) {
        Company company = companyDao.queryCompanyByType(type);
        // company Dao 减少碳排放量
        long consumed = diffMin * DecEmissionPerMin.get(type);
        company.setRemaincarbonemission(company.getRemaincarbonemission() - consumed);
        companyDao.putCompany(company);
        // 把减排量上传到 mongoDB
        recordConsumeToFluctuation(consumed);
    }

    /**
     * 用于价格波动
     * @param consumed 新增的 减排量
     */
    public void recordConsumeToFluctuation(long consumed) {
        Fluctuation fluctuation = fluctuationDao.findOneFluctuation();

        log.info("fluction: [{}]", fluctuation.toString());
        fluctuation.addConsumedEmission(consumed);

        fluctuationDao.updateFluctuation(fluctuation);
        return;
    }
}
