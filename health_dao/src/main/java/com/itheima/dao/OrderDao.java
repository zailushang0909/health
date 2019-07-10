package com.itheima.dao;

import com.itheima.pojo.Order;
import com.itheima.pojo.OrderInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface OrderDao {
    Order findOrderByMemberIdAndSetmealId(@Param("memberId") Integer memberId, @Param("setmealId")Integer setmealId);


    void addOrder(Order order);
}
