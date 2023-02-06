package com.yjxxt.crm.mapper;

import com.yjxxt.crm.base.BaseMapper;
import com.yjxxt.crm.bean.User;
import com.yjxxt.crm.query.UserQuery;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

public interface UserMapper extends BaseMapper<User,Integer> {
    //父类有的用父类的，否则自己写
    //根据用户名查询对象信息
    public User selectUserByName(String userName);
    /*查询所有的销售*/
    @MapKey("")
    public List<Map<String,Object>> selectAllSales();
    //条件查询
    public List<User> selectByParams(UserQuery query);

}