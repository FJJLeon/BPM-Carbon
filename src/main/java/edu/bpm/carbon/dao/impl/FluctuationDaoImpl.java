package edu.bpm.carbon.dao.impl;

import edu.bpm.carbon.dao.FluctuationDao;
import edu.bpm.carbon.entity.mongo.Fluctuation;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class FluctuationDaoImpl implements FluctuationDao {

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public void saveFluctuation(Fluctuation fluctuation) {
        mongoTemplate.save(fluctuation);
    }

    @Override
    public void removeFluctuation(ObjectId id) {
        Fluctuation fluctuation = new Fluctuation();
        fluctuation.setId(id);
    }

    @Override
    public void updateFluctuation(Fluctuation fluctuation) {
        Query query = new Query(Criteria.where("id").is(fluctuation.getId()));

        Update update = new Update();
        update.set("consumed_emission", fluctuation.getConsumedEmission());
        update.set("exchanged_credit", fluctuation.getExchangedCredit());
        update.set("exchanged_total_price", fluctuation.getExchangedTotalPrice());

        mongoTemplate.updateFirst(query, update, Fluctuation.class);
    }

    @Override
    public Fluctuation findOneFluctuation() {
        Query query = new Query();
        Fluctuation fluctuation = mongoTemplate.findOne(query, Fluctuation.class);
        return fluctuation;
    }
}
