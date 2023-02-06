package com.yjxxt.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yjxxt.crm.base.BaseService;
import com.yjxxt.crm.bean.CusDevPlan;
import com.yjxxt.crm.mapper.CusDevPlanMapper;
import com.yjxxt.crm.query.CusDevPlanQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CusDevPlanService extends BaseService<CusDevPlan, Integer> {

    @Autowired(required = false)
    private CusDevPlanMapper cusDevPlanMapper;


    /**
     * 条件查询
     * @param query
     * @return
     */
    public Map<String, Object> findCusDevPlanByItem(CusDevPlanQuery query) {
        //实例化目标Map
        Map<String, Object> map = new HashMap<String, Object>();
        //初始化
        PageHelper.startPage(query.getPage(), query.getLimit());
        //查询所有的数据信息
        List<CusDevPlan> clist = cusDevPlanMapper.selectByParams(query);
        //实例化PageInfo
        PageInfo<CusDevPlan> plist = new PageInfo<CusDevPlan>();
        //构建数据
        map.put("code", 0);
        map.put("msg", "success");
        map.put("count", plist.getTotal());
        map.put("data", plist.getList());
        //返回map
        return map;
    }

}
