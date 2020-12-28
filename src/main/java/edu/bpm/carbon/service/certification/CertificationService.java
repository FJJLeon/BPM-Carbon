package edu.bpm.carbon.service.certification;

import com.alibaba.fastjson.JSONObject;
import edu.bpm.carbon.utils.msgutils.Msg;
import edu.bpm.carbon.utils.msgutils.MsgCode;
import edu.bpm.carbon.utils.msgutils.MsgUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CertificationService {

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
}
