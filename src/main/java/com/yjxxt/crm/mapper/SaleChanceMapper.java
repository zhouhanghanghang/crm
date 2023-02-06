package com.yjxxt.crm.mapper;

import com.yjxxt.crm.base.BaseMapper;
import com.yjxxt.crm.bean.SaleChance;
import com.yjxxt.crm.query.SaleChanceQuery;

import java.util.List;

public interface SaleChanceMapper extends BaseMapper<SaleChance,Integer> {
    /**
     *新增加条件查询
     */
    public List<SaleChance> selectParams(SaleChanceQuery query);
}