package edu.bpm.carbon.dao.impl;

import edu.bpm.carbon.dao.SearchRecordDao;
import edu.bpm.carbon.entity.mongo.SearchRecord;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class SearchRecordDaoImpl implements SearchRecordDao {

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public void saveSearchRecord(SearchRecord searchRecord) {
        mongoTemplate.save(searchRecord);
    }

    @Override
    public void removeSearchRecord(ObjectId id) {
        SearchRecord searchRecord = new SearchRecord();
        searchRecord.setId(id);
        mongoTemplate.remove(id);
    }

    @Override
    public void updateSearchRecord(SearchRecord searchRecord) {
        Query query = new Query(Criteria.where("id").is(searchRecord.getId()));

        Update update = new Update();
        update.set("userid", searchRecord.getUserId());
        update.set("rewardCounts", searchRecord.getRewardCounts());

        mongoTemplate.updateFirst(query, update, SearchRecord.class);
    }

    @Override
    public SearchRecord findSearchRecordByUserId(Long userid) {
        Query query = new Query(Criteria.where("userid").is(userid));
        SearchRecord searchRecord = mongoTemplate.findOne(query, SearchRecord.class);
        return searchRecord;
    }
}
