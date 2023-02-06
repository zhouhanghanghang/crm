package com.yjxxt.crm.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yjxxt.crm.annotation.RequiredPermission;
import com.yjxxt.crm.base.BaseController;
import com.yjxxt.crm.base.ResultInfo;
import com.yjxxt.crm.bean.SaleChance;
import com.yjxxt.crm.query.SaleChanceQuery;
import com.yjxxt.crm.service.SaleChanceService;
import com.yjxxt.crm.service.UserService;
import com.yjxxt.crm.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("sale_chance")
public class SaleChanceController extends BaseController {


    @Autowired
    private SaleChanceService saleChanceService;

    @Autowired
    private UserService userService;


    @RequestMapping("list")
    @ResponseBody
    @RequiredPermission(code="101001")
    public Map<String, Object> sayList(SaleChanceQuery query,Integer flag,HttpServletRequest req) {
        //flag=1代表
        if(flag!=null && flag==1){
           //获取登录用户的Id
            int userId = LoginUserUtil.releaseUserIdFromCookie(req);
            //设定分配人
            query.setAssignMan(userId);
        }
        //返回目标map
        return saleChanceService.querySaleChanceByParam(query);
    }


    @RequestMapping("index")
    public String index() {
        return "saleChance/sale_chance";
    }

    @RequestMapping("addOrUpdateSaleChancePage")
    public String addOrUpdate(Integer saleChanceId, Model model) {
        //判断
        if (saleChanceId != null) {
            //查询对象
            SaleChance saleChance = saleChanceService.selectByPrimaryKey(saleChanceId);
            //存储
            model.addAttribute("saleChance", saleChance);
        }
        return "saleChance/add_update";
    }

    @RequestMapping("save")
    @ResponseBody
    public ResultInfo save(HttpServletRequest req, SaleChance saleChance) {
        //指定分配人
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(req);
        //查询用户信息
        String trueName = userService.selectByPrimaryKey(userId).getTrueName();
        //赋值
        saleChance.setCreateMan(trueName);
        //调佣方法添加
        saleChanceService.save(saleChance);
        //返回目标对象
        return success("销售机会添加成功了");
    }


    @RequestMapping("update")
    @ResponseBody
    public ResultInfo update(SaleChance saleChance) {
        //调用方法修改
        saleChanceService.update(saleChance);
        //提示一下信息
        return success("营销机会模块更新成功");
    }


    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo dels(Integer[] ids) {
        //调用方法修改
        saleChanceService.removeIds(ids);
        //提示一下信息
        return success("营销机会批量删除成功");
    }


}
