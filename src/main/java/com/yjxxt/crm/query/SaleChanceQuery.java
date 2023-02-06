package com.yjxxt.crm.query;

import com.yjxxt.crm.base.BaseQuery;

public class SaleChanceQuery extends BaseQuery {
    //客户名称
    private String customerName;
    //创建人
    private String createMan;
    //分配状态，0，未分配，1，已经分配
    private Integer state;

    //开发结果
    private Integer devResult;
    //分配人
    private Integer assignMan;



    public SaleChanceQuery() {
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCreateMan() {
        return createMan;
    }

    public void setCreateMan(String createMan) {
        this.createMan = createMan;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getDevResult() {
        return devResult;
    }

    public void setDevResult(Integer devResult) {
        this.devResult = devResult;
    }

    public Integer getAssignMan() {
        return assignMan;
    }

    public void setAssignMan(Integer assignMan) {
        this.assignMan = assignMan;
    }

    @Override
    public String toString() {
        return "SaleChanceQuery{" +
                "customerName='" + customerName + '\'' +
                ", createMan='" + createMan + '\'' +
                ", state=" + state +
                ", devResult=" + devResult +
                ", assignMan=" + assignMan +
                '}';
    }
}
