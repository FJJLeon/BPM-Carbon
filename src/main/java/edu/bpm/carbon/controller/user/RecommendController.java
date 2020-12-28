package edu.bpm.carbon.controller.user;

import edu.bpm.carbon.entity.mongo.SingleRecord;
import edu.bpm.carbon.service.SearchRecordService;
import edu.bpm.carbon.utils.msgutils.Msg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/user")
public class RecommendController {

    @Autowired
    SearchRecordService searchRecordService;

    @PostMapping(value = "/postSearchRecordForRecommend", consumes = MediaType.APPLICATION_JSON_VALUE)
    Msg postSearchRewardRecord(@RequestBody SingleRecord singleRecord) {
        log.info("postSearchRecord: [{}]", singleRecord.toString());

        Msg msg = searchRecordService.updateOrInsertRecommendRecord(singleRecord, 1);

        return msg;
    }

    @PostMapping(value = "/postExchangeRecordForRecommend", consumes = MediaType.APPLICATION_JSON_VALUE)
    Msg postExchangeRecord(@RequestBody Map<String, Object> body) {
        log.info("postExchangeRecord: [{}]", body.toString());

        long userid = Long.valueOf(String.valueOf(body.get("userid"))).longValue();
        long rewardid = Long.valueOf(String.valueOf(body.get("rewardid"))).longValue();

        SingleRecord singleRecord = new SingleRecord();
        singleRecord.setUserid(userid);
        singleRecord.addRecords(rewardid);

        Msg msg = searchRecordService.updateOrInsertRecommendRecord(singleRecord, 10);

        return msg;
    }

    @PostMapping(value = "/recommendRewards", consumes = MediaType.APPLICATION_JSON_VALUE)
    Msg recommendReward(@RequestBody Map<String, Object> body) {
        log.info("recommendRewards: [{}]", body.toString());

        long userid = Long.valueOf(String.valueOf(body.get("userid"))).longValue();

        Msg msg = searchRecordService.recommendReward(userid);

        return msg;
    }
}
