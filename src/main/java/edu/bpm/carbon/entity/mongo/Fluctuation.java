package edu.bpm.carbon.entity.mongo;

import com.alibaba.fastjson.JSON;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Document(collection = "carbon_unitprice_fluctuation_related")
public class Fluctuation implements Serializable {

    @Id
    private ObjectId id;

    @Field("consumed_emission")
    private long consumedEmission;

    @Field("exchanged_credit")
    private long exchangedCredit;

    @Field("exchanged_total_price")
    private double exchangedTotalPrice;

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

    public long getConsumedEmission() {
        return consumedEmission;
    }

    public void setConsumedEmission(long consumedEmission) {
        this.consumedEmission = consumedEmission;
    }

    public void addConsumedEmission(long consumed) {
        this.consumedEmission += consumed;
    }

    public long getExchangedCredit() {
        return exchangedCredit;
    }

    public void setExchangedCredit(long exchangedCredit) {
        this.exchangedCredit = exchangedCredit;
    }

    public void addExchangedCredit(long exchangedCredit) {
        this.exchangedCredit += exchangedCredit;
    }

    public double getExchangedTotalPrice() {
        return exchangedTotalPrice;
    }

    public void setExchangedTotalPrice(double exchangedTotalPrice) {
        this.exchangedTotalPrice = exchangedTotalPrice;
    }

    public void addExchangedTotalPrice(double exchangedTotalPrice) {
        this.exchangedTotalPrice += exchangedTotalPrice;
    }
}
