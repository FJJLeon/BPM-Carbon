package edu.bpm.carbon.entity;

import com.alibaba.fastjson.JSON;

public class CarbonOrder {

    private long id;

    private long companyid;

    private Company fromcompany;

    private long amount;

    // RMB per kg
    private double unitprice;

    /**
     * operation == status
     * pending, accept, reject
     */
    private String status;

    private String createtime;

    private String reviewtime;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public long getId() {
        return id;
    }

    public long getCompanyid() {
        return companyid;
    }

    public void setCompanyid(long companyid) {
        this.companyid = companyid;
    }

    public Company getFromcompany() {
        return fromcompany;
    }

    public void setFromcompany(Company fromcompany) {
        this.fromcompany = fromcompany;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public double getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(double unitprice) {
        this.unitprice = unitprice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getReviewtime() {
        return reviewtime;
    }

    public void setReviewtime(String reviewtime) {
        this.reviewtime = reviewtime;
    }
}
