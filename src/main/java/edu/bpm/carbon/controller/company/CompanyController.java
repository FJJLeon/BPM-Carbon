package edu.bpm.carbon.controller.company;

import edu.bpm.carbon.entity.CarbonOrder;
import edu.bpm.carbon.entity.Company;
import edu.bpm.carbon.service.CarbonOrderService;
import edu.bpm.carbon.service.CompanyService;
import edu.bpm.carbon.utils.msgutils.Msg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    CarbonOrderService orderService;

    @Autowired
    CompanyService companyService;

    @PostMapping(value = "/createOrder", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Msg createOrder(@RequestBody CarbonOrder carbonOrder) {
        log.info("createOrder: carbonOrder = [{}]", carbonOrder.toString());

        Assert.isTrue(carbonOrder.getCompanyid() != 0, "carbonOrder companyId missing");
        Assert.isTrue(carbonOrder.getAmount() != 0, "carbonOrder amount missing");

        Msg msg = orderService.createCarbonOrder(carbonOrder.getCompanyid(), carbonOrder.getAmount());

        return msg;
    }

    @PostMapping(value = "/queryTravelRecord", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Msg queryTravelRecord(@RequestBody Company company) {
        log.info("queryTravelRecord: company = [{}]", company.toString());

        Assert.isTrue(company.getId() != 0, "queryTR companyId missing");

        Msg msg = companyService.queryTravelRecordByCompanyID(company.getId());

        return msg;
    }
}
