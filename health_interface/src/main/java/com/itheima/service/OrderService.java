package com.itheima.service;

import com.itheima.entity.Result;
import com.itheima.pojo.OrderInfo;

public interface OrderService {
    Result addOrder(OrderInfo orderInfo);
}
