package edu.bpm.carbon.entity;

import com.alibaba.fastjson.JSON;

public class ExchangeRecord {

    private long id;

    private long userid;

    private String username;

    private long rewardid;

    private int quantity;

    private int unitcredit;

    private int totalcredit;

    private String exchangetime;

    private String rewardname;

    private Reward rewardinfo;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getRewardid() {
        return rewardid;
    }

    public void setRewardid(long rewardid) {
        this.rewardid = rewardid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getUnitcredit() {
        return unitcredit;
    }

    public void setUnitcredit(int unitcredit) {
        this.unitcredit = unitcredit;
    }

    public int getTotalcredit() {
        return totalcredit;
    }

    public void setTotalcredit(int totalcredit) {
        this.totalcredit = totalcredit;
    }

    public String getExchangetime() {
        return exchangetime;
    }

    public void setExchangetime(String exchangetime) {
        this.exchangetime = exchangetime;
    }

    public String getRewardname() {
        return rewardname;
    }

    public void setRewardname(String rewardname) {
        this.rewardname = rewardname;
    }

    public Reward getRewardinfo() {
        return rewardinfo;
    }

    public void setRewardinfo(Reward rewardinfo) {
        this.rewardinfo = rewardinfo;
    }
}
