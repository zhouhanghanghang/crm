package com.yjxxt.crm.controller;

import com.yjxxt.crm.base.BaseController;
import com.yjxxt.crm.base.ResultInfo;
import com.yjxxt.crm.bean.Role;
import com.yjxxt.crm.query.RoleQuery;
import com.yjxxt.crm.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("index")
    public String index() {
        return "role/role";
    }


    @RequestMapping("toGrantPage")
    public String sayGrand(Integer roleId,Model model){
        model.addAttribute("roleId",roleId);
        return "role/grant";
    }

    @RequestMapping("addOrUpdate")
    public String addOrUpdate(Integer roleId, Model model) {
        if (roleId != null) {
            //根据ID查询对象信息
            Role role = roleService.selectByPrimaryKey(roleId);
            //存储到作用域
            model.addAttribute("role", role);
        }
        return "role/add_update";
    }

    @RequestMapping("roles")
    @ResponseBody
    public List<Map<String, Object>> sayRoles(Integer userId) {
        return roleService.findAllRoles(userId);
    }


    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> list(RoleQuery query) {
        //调用方法
        return roleService.findAll(query);
    }



    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo sayDels(Integer roleId){
        roleService.removeRoleById(roleId);
        return  success("删除成功了");
    }

    @RequestMapping("save")
    @ResponseBody
    public ResultInfo save(Role role){
        roleService.addRole(role);
        return  success("角色添加成功了");
    }


    @RequestMapping("update")
    @ResponseBody
    public ResultInfo update(Role role){
        roleService.changeRole(role);
        return  success("角色修改成功了");
    }

    @RequestMapping("addGrand")
    @ResponseBody
    public ResultInfo addGrand(Integer roleId,Integer [] mids){
        roleService.addGrand(roleId,mids);
        return success("角色授权成功");
    }
}
