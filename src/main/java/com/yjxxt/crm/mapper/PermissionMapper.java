package com.yjxxt.crm.mapper;

import com.yjxxt.crm.base.BaseMapper;
import com.yjxxt.crm.bean.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PermissionMapper extends BaseMapper<Permission,Integer> {

    Integer countPermissionByRoleId(Integer roleId);

    Integer deletePermissionByRoleId(Integer roleId);

    List<Integer> selectModuleByRoleId(Integer roleId);

    List<String> queryUserHasRolesPermissions(int userId);
}