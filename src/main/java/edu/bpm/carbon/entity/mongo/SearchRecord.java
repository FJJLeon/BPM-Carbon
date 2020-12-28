package edu.bpm.carbon.entity.mongo;

import com.alibaba.fastjson.JSON;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Document(collection = "test_search_record")
public class SearchRecord implements Serializable {

    public static class RewardCount implements Comparable<Object> {

        private long rewardId;

        private int count;

        public RewardCount(long rewardId, int count) {
            this.rewardId = rewardId;
            this.count = count;
        }

        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }

        @Override
        public int compareTo(Object o) {
            RewardCount other = (RewardCount) o;

            return other.getCount() - this.getCount();
        }

        public long getRewardId() {
            return rewardId;
        }

        public void setRewardId(long rewardId) {
            this.rewardId = rewardId;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public void increaseCount(int weight) {
            this.count += weight;
        }

    }

    // not javax.persistence
    @Id
    private ObjectId id;

    @Field("userid")
    private long userId;

    @Field("rewardCounts")
    private List<RewardCount> rewardCounts;

    public SearchRecord() {
        this.rewardCounts = new ArrayList<>();
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public List<RewardCount> getRewardCounts() {
        return rewardCounts;
    }

    public void setRewardCounts(List<RewardCount> rewardCounts) {
        this.rewardCounts = rewardCounts;
    }

    public void newRewardCount(Long rewardId, int weight) {
        this.rewardCounts.add(new RewardCount(rewardId, weight));
    }

    public void increaseRewardCount(long rewardId, int weight) {
        for (RewardCount rc : this.rewardCounts) {
            if (rc.getRewardId() == rewardId) {
                rc.increaseCount(weight);
                return;
            }
        }
        this.newRewardCount(rewardId, weight);
    }

    public void sortRewardCounts() {
        Collections.sort(this.rewardCounts);
    }
}
