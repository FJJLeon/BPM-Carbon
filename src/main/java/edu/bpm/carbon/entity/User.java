package edu.bpm.carbon.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import edu.bpm.carbon.service.ExchangeService;

import java.util.List;

public class User {

    private long id;

    private String username;

    private String password;

    private int age;

    private int gender;

    private int credit;

    private int istraveling;

    private UserDesc userdesc;

    private List<TravelRecord> usertravelrecords;

    private List<ExchangeRecord> userexchanges;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getIstraveling() {
        return istraveling;
    }

    public void setIstraveling(int istraveling) {
        this.istraveling = istraveling;
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

    public List<ExchangeRecord> getUserexchanges() {
        return userexchanges;
    }

    public void setUserexchanges(List<ExchangeRecord> userexchanges) {
        this.userexchanges = userexchanges;
    }

    public void addExchangeRecord(ExchangeRecord exchangeRecord) {
        this.userexchanges.add(exchangeRecord);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
