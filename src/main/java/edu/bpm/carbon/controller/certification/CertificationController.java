package edu.bpm.carbon.controller.certification;

import edu.bpm.carbon.constant.Constant;
import edu.bpm.carbon.entity.CarbonOrder;
import edu.bpm.carbon.service.CarbonOrderService;
import edu.bpm.carbon.utils.msgutils.Msg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@Slf4j
@RequestMapping("/certification")
public class CertificationController {

    @Autowired
    CarbonOrderService orderService;

    @PostMapping(value = "reviewOrder", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Msg reviewOrder(@RequestBody CarbonOrder carbonOrder) {
        log.info("reviewOrder: carbonOrder = [{}]", carbonOrder.toString());

        Assert.isTrue(carbonOrder.getId() != 0, "carbonOrder Id missing");
        Assert.isTrue(carbonOrder.getCompanyid() != 0, "carbonOrder companyId missing");
        Assert.isTrue(carbonOrder.getStatus() != "", "carbonOrder operation/status missing");

        Msg msg = orderService.reviewCarbonOrder(carbonOrder.getId(), carbonOrder.getCompanyid(), carbonOrder.getStatus());

        return msg;
    }

    @GetMapping(value = "getAcceptOrders")
    public Msg getAcceptOrders() {
        log.info("getAcceptOrders");

        Msg msg = orderService.queryCarbonOrder(new HashMap<String, Object>(){{put(Constant.CARBOD_STATUS, Constant.ODSTATUS_ACCEPT);}});

        return msg;
    }

    @GetMapping(value = "getRejectOrders")
    public Msg getRejectOrders() {
        log.info("getRejectOrders");

        Msg msg = orderService.queryCarbonOrder(new HashMap<String, Object>(){{put(Constant.CARBOD_STATUS, Constant.ODSTATUS_REJECT);}});

        return msg;
    }
}
