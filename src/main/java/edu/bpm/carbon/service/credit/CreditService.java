package edu.bpm.carbon.service.credit;


import edu.bpm.carbon.constant.Constant;
import edu.bpm.carbon.entity.TravelRecord;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
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

    public Map<String, Integer> CreditPerMin = new HashMap<String, Integer>(){{
       put(Constant.VEHICLETYPE_BIKE,   1);
       put(Constant.VEHICLETYPE_BUS,    5);
       put(Constant.VEHICLETYPE_SUBWAY, 10);
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
        return diffMin * CreditPerMin.get(type);
    }
}
