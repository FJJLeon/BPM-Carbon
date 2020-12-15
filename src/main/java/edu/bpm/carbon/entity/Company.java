package edu.bpm.carbon.entity;

import com.alibaba.fastjson.JSON;

public class Company {

    private long id;

    private String type; // TODO, make three company service

    private String name;

    private long totalcarbonemission;


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTotalcarbonemission() {
        return totalcarbonemission;
    }

    public void setTotalcarbonemission(long totalcarbonemission) {
        this.totalcarbonemission = totalcarbonemission;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
