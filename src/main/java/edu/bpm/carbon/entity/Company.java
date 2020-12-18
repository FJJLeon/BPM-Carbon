package edu.bpm.carbon.entity;

import com.alibaba.fastjson.JSON;

import java.util.List;

public class Company {

    private long id;

    private String name;

    /**
     * three type:
     * subway, bus, bike
     */
    private String type;

    private String address;

    private String phone;

    private long remaincarbonemission;

    /**
     * only one pendingOrder available for each company
     */
    private CarbonOrder pendingorder;

    private List<CarbonOrder> acceptorders;

    private List<CarbonOrder> rejectorders;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getRemaincarbonemission() {
        return remaincarbonemission;
    }

    public void setRemaincarbonemission(long remaincarbonemission) {
        this.remaincarbonemission = remaincarbonemission;
    }

    public CarbonOrder getPendingorder() {
        return pendingorder;
    }

    public void setPendingorder(CarbonOrder pendingorder) {
        this.pendingorder = pendingorder;
    }

    public List<CarbonOrder> getAcceptorders() {
        return acceptorders;
    }

    public void setAcceptorders(List<CarbonOrder> acceptorders) {
        this.acceptorders = acceptorders;
    }

    public void addAcceptorders(CarbonOrder acceptorder) {
        this.acceptorders.add(acceptorder);
    }

    public List<CarbonOrder> getRejectorders() {
        return rejectorders;
    }

    public void setRejectorders(List<CarbonOrder> rejectorders) {
        this.rejectorders = rejectorders;
    }

    public void addRejectorders(CarbonOrder rejectorder) {
        this.rejectorders.add(rejectorder);
    }
}
