package edu.bpm.carbon.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class User {

    private long id;

    private long userid;

    private String username;

    private String password;

    private int age;

    private int gender;

    private int credit;

    private UserDesc userdesc;

    private List<TravelRecord> usertravelrecords;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public void addCredit(int creditGained) {
        this.credit += creditGained;
    }

    public UserDesc getUserdesc() {
        return userdesc;
    }

    public void setUserdesc(UserDesc userdesc) {
        this.userdesc = userdesc;
    }

    public List<TravelRecord> getUsertravelrecords() {
        return usertravelrecords;
    }

    public void setUsertravelrecords(List<TravelRecord> usertravelrecords) {
        this.usertravelrecords = usertravelrecords;
    }

    public void addTravelrecords(TravelRecord travelRecord) {
        this.usertravelrecords.add(travelRecord);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
