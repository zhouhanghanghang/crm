package com.yjxxt.crm.mapper;

import com.yjxxt.crm.base.BaseMapper;
import com.yjxxt.crm.bean.Module;
import com.yjxxt.crm.dto.TreeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface ModuleMapper  extends BaseMapper<Module,Integer> {

    public List<TreeDto> selectAllModules();
    //查询模块表中的所有的数据
    List<Module> selectModules();
}