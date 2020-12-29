package edu.bpm.carbon.dao;

import edu.bpm.carbon.entity.mongo.Fluctuation;
import org.bson.types.ObjectId;

public interface FluctuationDao {

    void saveFluctuation(Fluctuation fluctuation);

    void removeFluctuation(ObjectId id);

    void updateFluctuation(Fluctuation fluctuation);

    Fluctuation findOneFluctuation();
}
