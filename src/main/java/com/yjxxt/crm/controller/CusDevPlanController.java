package com.yjxxt.crm.controller;

import com.yjxxt.crm.base.BaseController;
import com.yjxxt.crm.bean.Module;
import com.yjxxt.crm.bean.SaleChance;
import com.yjxxt.crm.query.CusDevPlanQuery;
import com.yjxxt.crm.service.CusDevPlanService;
import com.yjxxt.crm.service.SaleChanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("cus_dev_plan")
public class CusDevPlanController extends BaseController {

    @Autowired
    private SaleChanceService saleChanceService;


    @Autowired
    private CusDevPlanService cusDevPlanService;


    @RequestMapping("index")
    public String index() {
        return "cusDevPlan/cus_dev_plan";
    }


    @RequestMapping("toCusDevPlanDataPage")
    public String sayCusDev(Integer sid, Model model) {
        //根据id查询信息
        SaleChance saleChance = saleChanceService.selectByPrimaryKey(sid);
        //存储到作用域
        model.addAttribute("saleChance", saleChance);
        //返回页面
        return "cusDevPlan/cus_dev_plan_data";
    }

    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> list(CusDevPlanQuery query) {
        //获取目标数据
        Map<String, Object> map = cusDevPlanService.findCusDevPlanByItem(query);
        //返回页面
        return map;
    }
}
