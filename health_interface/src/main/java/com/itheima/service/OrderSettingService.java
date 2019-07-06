package com.itheima.service;

import com.itheima.pojo.OrderSetting;

import java.util.Date;
import java.util.List;

public interface OrderSettingService {
    void add(Date orderDate, int number);

    List<OrderSetting> findOrderSettingByCurrentMonth(String current);

    void addBatch(List<String[]> strings);
}
