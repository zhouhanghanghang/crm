package com.yjxxt.crm.controller;

import com.yjxxt.crm.base.BaseController;
import com.yjxxt.crm.base.ResultInfo;
import com.yjxxt.crm.bean.User;
import com.yjxxt.crm.exceptions.ParamsException;
import com.yjxxt.crm.model.UserModel;
import com.yjxxt.crm.query.UserQuery;
import com.yjxxt.crm.service.UserService;
import com.yjxxt.crm.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.Result;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController extends BaseController {

    @Autowired(required = false)
    private UserService userService;

    @RequestMapping("toPasswordPage")
    public String sayUpdate() {
        return "user/password";
    }


    @RequestMapping("toSettingPage")
    public String saySetting(HttpServletRequest req,Model model) {
        //获取当前用户userId
        int userId = LoginUserUtil.releaseUserIdFromCookie(req);
        //调用查询
        User user = userService.selectByPrimaryKey(userId);
        //存储到作用域
        model.addAttribute("user",user);
        //转发到目标页面
        return "user/setting";
    }

    @RequestMapping("addOrUpdatePage")
    public String addUpdate(Integer id, Model model) {
        //判断
        if(id!=null){
            //查询对象信息
            User user = userService.selectByPrimaryKey(id);
            //存储到作用域
            model.addAttribute("user",user);
        }
        return "user/add_update";
    }



    @RequestMapping("index")
    public String index() {
        return "user/user";
    }

    @PostMapping("updatePwd")
    @ResponseBody
    public ResultInfo sayUpdate(HttpServletRequest req, String oldPassword, String newPassword, String confirmPwd) {
        //实例化ResultInfo
        ResultInfo resultInfo = new ResultInfo();
        //获取用户的Id
        int userId = LoginUserUtil.releaseUserIdFromCookie(req);
        //调用方法修改
        userService.updatePassword(userId, oldPassword, newPassword, confirmPwd);
        //返回对象
        return resultInfo;
    }

    @PostMapping("login")
    @ResponseBody
    public ResultInfo sayLogin(String username, String userpwd) {
        System.out.println(username + "--->" + userpwd);
        //实例化对象
        ResultInfo resultInfo = new ResultInfo();
        //调用
        UserModel um = userService.doLogin(username, userpwd);
        resultInfo.setResult(um);
        //返回对象信息
        return resultInfo;
    }

    @RequestMapping("sales")
    @ResponseBody
    public List<Map<String, Object>> sayListSales(){
        //调用方法查询所有的销售
        List<Map<String, Object>> list = userService.findAllSales();
        //list-json
        return list;
    }

    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> list(UserQuery query){
        //调用方法查询
       return userService.findUserByParam(query);
    }


    @RequestMapping("save")
    @ResponseBody
    public ResultInfo save(User user){
        //调用方法添加
        userService.addUser(user);
        //返回目标对象
        return success("用户添加成功");
    }

    @RequestMapping("update")
    @ResponseBody
    public ResultInfo update(User user){
        //调用方法添加
        userService.changeUser(user);
        //返回目标对象
        return success("用户修改成功");
    }

    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo dels(Integer [] ids){
        //调用方法添加
        userService.removeIds(ids);
        //返回目标对象
        return success("用户删除成功");
    }
}
