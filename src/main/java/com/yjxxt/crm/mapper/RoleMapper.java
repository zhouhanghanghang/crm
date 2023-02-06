package com.yjxxt.crm.mapper;

import com.yjxxt.crm.base.BaseMapper;
import com.yjxxt.crm.bean.Role;
import com.yjxxt.crm.query.RoleQuery;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

public interface RoleMapper extends BaseMapper<Role,Integer> {

    /*查询所有的角色*/

    @MapKey("")
    public List<Map<String,Object>> selectAllRoles(Integer userId);

    /*条件查询角色*/
    public List<Role> selectParams(RoleQuery query);

    /**
     * 根据角色名称查询角色信息
     * @param roleName
     * @return
     */
    Role selectRoleByRoleName(String roleName);
}