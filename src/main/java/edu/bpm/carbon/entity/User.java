package edu.bpm.carbon.entity;

import com.alibaba.fastjson.annotation.JSONField;

public class User {

    private int id;

    private int userid;

    private String username;

    private String password;

    private int age;

    private int gender;

    private UserDesc userdesc;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
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

    public UserDesc getUserdesc() {
        return userdesc;
    }

    public void setUserdesc(UserDesc userdesc) {
        this.userdesc = userdesc;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userid=" + userid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", userdesc=" + userdesc +
                '}';
    }
}
