package edu.bpm.carbon.service.certification;

import com.alibaba.fastjson.JSONObject;
import edu.bpm.carbon.dao.FluctuationDao;
import edu.bpm.carbon.entity.mongo.Fluctuation;
import edu.bpm.carbon.utils.msgutils.Msg;
import edu.bpm.carbon.utils.msgutils.MsgCode;
import edu.bpm.carbon.utils.msgutils.MsgUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CertificationService {

    @Autowired
    FluctuationDao fluctuationDao;

    /**
     * 单位是 厘/千克 == 元/吨
     */
    public double CarbonUnitPrice = 40;

    public double getCarbonUnitPrice() {
        return CarbonUnitPrice;
    }


    // for Get UnitPrice API
    public Msg getCarbonUnitPriceService() {
        JSONObject data = new JSONObject();
        data.put("unitPrice", CarbonUnitPrice);
        return MsgUtil.makeMsg(MsgCode.SUCCESS, data);
    }

    public void fluctuateUnitPrice() {
        log.info("fluctuateUnitPrice old up=[{}]", CarbonUnitPrice);

        Fluctuation fluctuation = fluctuationDao.findOneFluctuation();

        long totalConsumedEmission = fluctuation.getConsumedEmission();
        long totalExchangedCredit = fluctuation.getExchangedCredit();

        double x = (totalConsumedEmission / 1000000) * CarbonUnitPrice; // Yuan
        double y = totalExchangedCredit / 1000;

        log.info("totalConsumedEmission=[{}], totalExchangedCredit=[{}]", totalConsumedEmission, totalExchangedCredit);
        log.info("x = [{}], y = [{}]", x, y);

        // 认证机构从出行工具公司拿到更多的钱，可以降低 up
        if (x > y) {
            double dec = (x - y) / y;
            if (dec > 0.2)
                dec = 0.2;
            int newUP = (int)((1 - dec) * CarbonUnitPrice * 100);
            // 保留两位小数
            CarbonUnitPrice = (double)(newUP) / 100;
            log.info("x>y, dec=[{}], newUp=[{}]", dec, newUP);
        }
        // 认证机构给了用户更多的奖励物品，需要增加 up
        else if (x < y){
            double inc = (y - x) / x;
            if (inc > 0.2)
                inc = 0.2;
            int newUP = (int)((1 + inc) * CarbonUnitPrice * 100);
            // 保留两位小数
            CarbonUnitPrice = (double)(newUP) / 100;
            log.info("x<y, inc=[{}], newUp=[{}]", inc, newUP);
        }
        // 持平
        else {
            log.info("x==y, no flu");
        }
        log.info("fluctuateUnitPrice new up=[{}]", CarbonUnitPrice);
        // 去除当此的浮动数据
        fluctuation.setConsumedEmission(0);
        fluctuation.setExchangedCredit(0);
        fluctuation.setConsumedEmission(0);
        fluctuationDao.updateFluctuation(fluctuation);
    }


}
