package com.yjxxt.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yjxxt.crm.base.BaseService;
import com.yjxxt.crm.base.ResultInfo;
import com.yjxxt.crm.bean.SaleChance;
import com.yjxxt.crm.mapper.SaleChanceMapper;
import com.yjxxt.crm.query.SaleChanceQuery;
import com.yjxxt.crm.utils.AssertUtil;
import com.yjxxt.crm.utils.PhoneUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SaleChanceService extends BaseService<SaleChance,Integer> {

    @Autowired(required = false)
    private SaleChanceMapper saleChanceMapper;

    /**
     * 条件查询
     * @param query
     * @return
     */
    public  Map<String, Object> querySaleChanceByParam(SaleChanceQuery query){
        //实例化Map
        Map<String, Object> map = new HashMap<>();
        //开启分页单位
        PageHelper.startPage(query.getPage(), query.getLimit());
        //调用方法查询所有的信息
        List<SaleChance> slist = saleChanceMapper.selectParams(query);
        //开始分页
        PageInfo<SaleChance> plist = new PageInfo<SaleChance>(slist);
        //准备数据
        map.put("code", 0);
        map.put("msg", "success");
        map.put("count", plist.getTotal());
        map.put("data", plist.getList());
        //返回目标map
        return  map;
    }

    /**
     * 营销机会数据添加
     *   1.参数校验
     *      customerName:非空
     *      linkMan:非空
     *      linkPhone:非空 11位手机号
     *   2.设置相关参数默认值
     *      state:默认未分配  如果选择分配人  state 为已分配
     *      assignTime:如果  如果选择分配人   时间为当前系统时间
     *      devResult:默认未开发 如果选择分配人devResult为开发中 0-未开发 1-开发中 2-开发成功 3-开发失败
     *      isValid:默认有效数据(1-有效  0-无效)
     *      createDate updateDate:默认当前系统时间
     *   3.执行添加 判断结果
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void save(SaleChance saleChance){
        //1.参数校验
        checkParams(saleChance.getCustomerName(),saleChance.getLinkMan(),saleChance.getLinkPhone());
        // 2.设置相关参数默认值
        //未分配人
        saleChance.setState(0);//0-未分配，1-已分配
        saleChance.setDevResult(0);//0-未开发，1-开发中，2-开发成功，3-开发失败
        //已经有分配人
        if(StringUtils.isNotBlank(saleChance.getAssignMan())){
            saleChance.setState(1);
            saleChance.setDevResult(1);
            //分配时间
            saleChance.setAssignTime(new Date());
        }
        //默认值
        saleChance.setIsValid(1);
        saleChance.setCreateDate(new Date());
        saleChance.setUpdateDate(new Date());

        //3.执行添加 判断结果
        AssertUtil.isTrue(saleChanceMapper.insertSelective(saleChance)<1,"添加失败了");
    }

    /**
     * 验证数据
     * @param customerName 非空
     * @param linkMan  非空
     * @param linkPhone 非空，合法手机号
     */
    private void checkParams(String customerName, String linkMan, String linkPhone) {
        AssertUtil.isTrue(StringUtils.isBlank(customerName),"客户名不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(linkMan),"联系人不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(linkPhone),"联系电话不能为空");
        AssertUtil.isTrue(!PhoneUtil.isMobile(linkPhone),"请输入合法的手机号");
    }

    /**
     * 营销机会数据更新
     *  1.参数校验
     *      id:记录必须存在
     *      customerName:非空
     *      linkMan:非空
     *      linkPhone:非空，11位手机号
     *  2. 设置相关参数值
     *      updateDate:系统当前时间
     *         原始记录 未分配 修改后改为已分配(由分配人决定)
     *            state 0->1
     *            assginTime 系统当前时间
     *            devResult 0-->1
     *         原始记录  已分配  修改后 为未分配
     *            state  1-->0
     *            assignTime  待定  null
     *            devResult 1-->0
     *  3.执行更新 判断结果
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void update(SaleChance saleChance){
        //id:记录必须存在
        SaleChance temp = saleChanceMapper.selectByPrimaryKey(saleChance.getId());
        //判断
        AssertUtil.isTrue(null==temp,"待修改记录不存在");
        //校验参数
        checkParams(saleChance.getCustomerName(),saleChance.getLinkMan(),saleChance.getLinkPhone());
        //2. 设置相关参数值
        //原始状态 未分配
        if(StringUtils.isBlank(temp.getAssignMan())&& StringUtils.isNotBlank(saleChance.getAssignMan())){
            saleChance.setState(1);
            saleChance.setDevResult(1);
            saleChance.setAssignTime(new Date());
        }else if(StringUtils.isNotBlank(temp.getAssignMan())&& StringUtils.isBlank(saleChance.getAssignMan())){
            //已分配
            saleChance.setAssignMan("");
            saleChance.setState(0);
            saleChance.setDevResult(0);
            //分配时间
            saleChance.setAssignTime(null);
        }
        //3.执行更新 判断结果
        AssertUtil.isTrue(saleChanceMapper.updateByPrimaryKeySelective(saleChance)<1,"修改失败了");
    }



    @Transactional(propagation = Propagation.REQUIRED)
    public void removeIds(Integer [] ids){
        //验证
       AssertUtil.isTrue(ids==null || ids.length==0,"请选择删除数据");
        //删除是否成功
       AssertUtil.isTrue(saleChanceMapper.deleteBatch(ids)<1,"批量删除失败了");
    }
}
