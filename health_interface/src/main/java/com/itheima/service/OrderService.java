package com.itheima.service;

import com.itheima.entity.Result;
import com.itheima.pojo.OrderInfo;

import java.util.Map;

public interface OrderService {
    Result addOrder(OrderInfo orderInfo);

    Map findById(Integer id);
}
