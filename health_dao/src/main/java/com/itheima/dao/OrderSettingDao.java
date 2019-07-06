package com.itheima.dao;

import com.itheima.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface OrderSettingDao {
    void add(@Param("orderDate") Date orderDate, @Param("number") int number);

    int findOrderSettingByorderDate(@Param("orderDate") Date orderDate);

    void update(@Param("orderDate")Date orderDate, @Param("number")int number);

    List<OrderSetting> findOrderSettingByCurrentMonth(@Param("startDate") String startDate, @Param("endDate")String endDate);
}
