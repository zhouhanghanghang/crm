package com.yjxxt.crm.mapper;

import com.yjxxt.crm.base.BaseMapper;
import com.yjxxt.crm.bean.UserRole;

public interface UserRoleMapper extends BaseMapper<UserRole,Integer> {

    //统计当前用户拥有的角色
    Integer countUserRoleByUserId(Integer userId);

    //删除当前用户的的所有角色信息
    Integer deleteUserRoleByUserId(Integer userId);
}