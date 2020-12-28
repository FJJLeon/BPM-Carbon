package edu.bpm.carbon.entity.mongo;

import java.util.ArrayList;
import java.util.List;

public class SingleRecord {

    private long userid;

    private List<Long> records;

    public SingleRecord() {
        this.records = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "SingleRecord{" +
                "userid=" + userid +
                ", records=" + records +
                '}';
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public List<Long> getRecords() {
        return records;
    }

    public void setRecords(List<Long> records) {
        this.records = records;
    }

    public void addRecords(Long rewardId) {
        this.records.add(rewardId);
    }
}
