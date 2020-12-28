package edu.bpm.carbon.dao;

import edu.bpm.carbon.entity.mongo.SearchRecord;
import org.bson.types.ObjectId;

public interface SearchRecordDao {

    void saveSearchRecord(SearchRecord searchRecord);

    void removeSearchRecord(ObjectId id);

    void updateSearchRecord(SearchRecord searchRecord);

    SearchRecord findSearchRecordByUserId(Long userid);
}
