package com.yjxxt.crm.service;

import com.github.pagehelper.PageHelper;
import com.yjxxt.crm.base.BaseService;
import com.yjxxt.crm.bean.Permission;
import com.yjxxt.crm.dto.TreeDto;
import com.yjxxt.crm.mapper.ModuleMapper;
import com.yjxxt.crm.bean.Module;
import com.yjxxt.crm.mapper.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ModuleService extends BaseService<Module,Integer> {

    @Autowired(required = false)
    private ModuleMapper moduleMapper;

    @Autowired(required = false)
    private PermissionMapper permissionMapper;





    /***
     * 查询所有的资源信息
     * @return
     */
//    public List<TreeDto> findAllModules(){
//        return moduleMapper.selectAllModules();
//    }
//
    public List<TreeDto> findAllModules(Integer roleId){
        //所有的资源信息
        List<TreeDto> modules = moduleMapper.selectAllModules();
        //当前角色拥有的资源id的集合
         List<Integer> mlist= permissionMapper.selectModuleByRoleId(roleId);
         //遍历
        //checkecd=true,checked=false;
        for (TreeDto treeDto: modules) {
            if(mlist.contains(treeDto.getId())){
                //拥有模块的id,则对象的checked=true,否则checked=false;
                treeDto.setChecked(true);
            }
        }
        return  modules;
    }


    public Map<String,Object> findModules(){
        Map<String,Object> map=new HashMap<>();
        //查询
       List<Module> mlist= moduleMapper.selectModules();
        //准备数据
        map.put("code",0);
        map.put("msg","success");
        map.put("count",mlist.size());
        map.put("data",mlist);
        //返回目标map
        return  map;
    }
}
