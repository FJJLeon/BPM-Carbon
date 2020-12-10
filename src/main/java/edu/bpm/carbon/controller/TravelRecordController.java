package edu.bpm.carbon.controller;

import edu.bpm.carbon.entity.TravelRecord;
import edu.bpm.carbon.service.TravelRecordService;
import edu.bpm.carbon.utils.msgutils.Msg;
import edu.bpm.carbon.utils.msgutils.MsgCode;
import edu.bpm.carbon.utils.msgutils.MsgUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class TravelRecordController {

    @Autowired
    TravelRecordService travelRecordService;

    @PostMapping(value = "/startTravel", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Msg startTravel(@RequestBody TravelRecord travelRecord) {
        log.info(travelRecord.toString());

        Assert.notNull(travelRecord.getUserid(), "travel record userid missing");
        Assert.notNull(travelRecord.getVehicletype(), "travel record vehicle type missing");

        Msg msg = travelRecordService.startTravel(travelRecord.getUserid(), travelRecord.getVehicletype());

        return msg;
    }
}
