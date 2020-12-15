package edu.bpm.carbon.controller;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import edu.bpm.carbon.entity.ExchangeRecord;
import edu.bpm.carbon.service.ExchangeService;
import edu.bpm.carbon.utils.msgutils.Msg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
public class ExchangeController {

    @Autowired
    ExchangeService exchangeService;

    @PostMapping(value = "/makeExchange", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Msg makeExchange(@RequestBody ExchangeRecord er) {
        log.info("makeExchange: [{}]", er.toString());

        Assert.isTrue(er.getUserid() != 0, "Exchange userid missing");
        Assert.isTrue(er.getRewardid() != 0, "Exchange rewardid missing");
        Assert.isTrue(er.getQuantity() != 0, "Exchange quantity missing");

        Msg msg = exchangeService.makeExchange(er.getUserid(), er.getRewardid(), er.getQuantity());

        return msg;
    }

}
