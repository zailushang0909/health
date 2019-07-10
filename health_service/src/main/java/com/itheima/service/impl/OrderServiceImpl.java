package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.OrderSettingDao;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderInfo;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderSettingDao orderSettingDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;
    @Override
    public Result addOrder(OrderInfo orderInfo) {
        //1、判断当天是否有预约设置
        OrderSetting orderSetting = orderSettingDao.findOrderSettingByDate(orderInfo.getOrderDate());
        if (orderSetting==null) {
            return new Result(false, "当天没有预约设置，请选择别的日期后继续提交");
        }
        //2、判断当天预约人数是否上线
        if (orderSetting.getNumber()<=orderSetting.getReservations()) {
            return new Result(false, "当天预约人数已满，请选择别的日期后继续提交");
        }
        //3、判断用户是否是会员
        Member member = memberDao.findMemberByTelephone(orderInfo.getTelephone());
        if (null==member) {
            member = new Member();
            member.setName(orderInfo.getName());
            member.setPhoneNumber(orderInfo.getTelephone());
            member.setIdCard(orderInfo.getIdCard());
            member.setRegTime(orderInfo.getOrderDate());
            member.setSex(String.valueOf(orderInfo.getSex()));
            memberDao.addMember(member);
        }
        //4、判断当前用户在在当天是否已预约当前套餐
        Order order = orderDao.findOrderByMemberIdAndSetmealId(member.getId(),orderInfo.getSetmealId());
        if (order!=null) {
            return new Result(false, "当天已经预约该套餐，不能重复预约");
        }
        //5、添加订单表信息
        order  = new Order();
        order.setMemberId(member.getId());
        order.setOrderDate(orderInfo.getOrderDate());
        order.setOrderType(Order.ORDERTYPE_WEIXIN);
        order.setSetmealId(orderInfo.getSetmealId());
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        orderDao.addOrder(order);
        //5、预约设置已预约人数+1
        orderSetting.setReservations(orderSetting.getReservations()+1);
        int i= orderSettingDao.updateReservations(orderSetting);
        if (i == 0) {
            throw new RuntimeException("版本不一致，添加数据失败");
        }
        //6、返回预约成功返回值
        return new Result(true, "预约成功");
    }
}
