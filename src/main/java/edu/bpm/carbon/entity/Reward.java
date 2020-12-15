package edu.bpm.carbon.entity;

import com.alibaba.fastjson.JSON;

public class Reward {

    private long id;

    private String name;

    private int credit;

    private int inventory;

    private int exchanged;

    private double price;

    private String image;

    public void exchange(int quantity) {
        this.inventory -= quantity;
        this.exchanged += quantity;
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

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public int getExchanged() {
        return exchanged;
    }

    public void setExchanged(int exchanged) {
        this.exchanged = exchanged;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
