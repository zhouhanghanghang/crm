package com.yjxxt.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yjxxt.crm.base.BaseService;
import com.yjxxt.crm.bean.Permission;
import com.yjxxt.crm.bean.Role;
import com.yjxxt.crm.mapper.ModuleMapper;
import com.yjxxt.crm.mapper.PermissionMapper;
import com.yjxxt.crm.mapper.RoleMapper;
import com.yjxxt.crm.query.RoleQuery;
import com.yjxxt.crm.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


import javax.validation.constraints.AssertTrue;
import java.util.*;

@Service
public class RoleService extends BaseService<Role, Integer> {

    @Autowired(required = false)
    private RoleMapper roleMapper;
    /*查询所有角色*/
    @Autowired(required = false)
    private PermissionMapper permissionMapper;


    @Autowired(required = false)
    private ModuleMapper moduleMapper;

    public List<Map<String, Object>> findAllRoles(Integer userId) {
        return roleMapper.selectAllRoles(userId);
    }


    public Map<String, Object> findAll(RoleQuery query) {
        //实例化Map
        Map<String, Object> map = new HashMap<>();
//        初始化分页数据
        PageHelper.startPage(query.getPage(), query.getLimit());
        //查询数据
        List<Role> rlist = roleMapper.selectParams(query);
        //开始分页
        PageInfo<Role> plist = new PageInfo<>(rlist);
        //构建数据
        map.put("code", 0);
        map.put("msg", "success");
        map.put("count", plist.getTotal());
        map.put("data", plist.getList());
        //返回目标数据
        return map;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void removeRoleById(Integer roleId){
        //roleId非空
        //获取当前对象
        Role temp = roleMapper.selectByPrimaryKey(roleId);
        //验证
        AssertUtil.isTrue(temp==null,"待删除的角色不存在");
        //失效
        temp.setIsValid(0);
        //删除是否成功
        AssertUtil.isTrue(roleMapper.updateByPrimaryKeySelective(temp)<1,"角色删除失败");
        //删除角色的权限的关系数据
        Integer count=permissionMapper.countPermissionByRoleId(roleId);
        if(count>0){
            //删除原来的权限，重新添加权限，核心表，t_role,t_permission
            AssertUtil.isTrue(  permissionMapper.deletePermissionByRoleId(roleId)!=count,"授权失败");
        }
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void addRole(Role role){
        //角色名非空
        AssertUtil.isTrue(role.getRoleName()==null,"请输入角色名称");
        //角色名不能重复
       Role temp= roleMapper.selectRoleByRoleName(role.getRoleName());
       AssertUtil.isTrue(temp!=null,"角色名已经存在");
        //默认值设定
        role.setIsValid(1);
        role.setCreateDate(new Date());
        role.setUpdateDate(new Date());
        //添加是否成功
        AssertUtil.isTrue(roleMapper.insertSelective(role)<1,"角色添加失败");
    }



    @Transactional(propagation = Propagation.REQUIRED)
    public void changeRole(Role role){
        //角色roleId
        AssertUtil.isTrue(role.getId()==null || null==roleMapper.selectByPrimaryKey(role.getId()),"待修改角色不存在");
        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()),"请输入角色名称");
        //角色名称已经存在
        Role temp = roleMapper.selectRoleByRoleName(role.getRoleName());
        AssertUtil.isTrue(temp!=null &&!(temp.getId().equals(role.getId())),"角色已经存在");
        //默认值
        role.setUpdateDate(new Date());
        //修改是否成功
        AssertUtil.isTrue(roleMapper.updateByPrimaryKeySelective(role)<1,"角色修改失败");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void addGrand(Integer roleId,Integer [] mids){
        //roleId非空
        Role temp = roleMapper.selectByPrimaryKey(roleId);
        AssertUtil.isTrue(temp==null,"待授权的角色不存在");
        //原始是否有权限
        Integer count=permissionMapper.countPermissionByRoleId(roleId);
        if(count>0){
            //删除原来的权限，重新添加权限，核心表，t_role,t_permission
            AssertUtil.isTrue(  permissionMapper.deletePermissionByRoleId(roleId)!=count,"授权失败");
        }
        //授权
       if(mids!=null && mids.length>0){
           List<Permission> plist=new ArrayList<Permission>();
           //遍历
           for (Integer mid: mids ) {
               //实例化permission
               Permission permission=new Permission();
               permission.setRoleId(roleId);
               permission.setModuleId(mid);
               permission.setCreateDate(new Date());
               permission.setUpdateDate(new Date());
               permission.setAclValue(moduleMapper.selectByPrimaryKey(mid).getOptValue());
               //添加集合对象
               plist.add(permission);
           }
           //批量授权
           AssertUtil.isTrue(permissionMapper.insertBatch(plist)!=plist.size(),"批量授权失败了");
       }
    }
}
