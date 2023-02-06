package com.yjxxt.crm.query;

import com.yjxxt.crm.base.BaseQuery;

public class CusDevPlanQuery extends BaseQuery {
    //营销机会ID
    private Integer sid;

    public CusDevPlanQuery() {
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    @Override
    public String toString() {
        return "CusDevPlanQuery{" +
                "sid=" + sid +
                '}';
    }
}
