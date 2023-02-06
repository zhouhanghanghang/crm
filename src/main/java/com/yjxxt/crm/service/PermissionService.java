package com.yjxxt.crm.service;

import com.yjxxt.crm.base.BaseService;
import com.yjxxt.crm.bean.Permission;
import com.yjxxt.crm.mapper.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PermissionService extends BaseService<Permission,Integer> {

    @Autowired(required = false)
    private PermissionMapper permissionMapper;


    public List<String> queryUserHasRolesHasPermissions(int userId) {
        return permissionMapper.queryUserHasRolesPermissions(userId);
    }
}
