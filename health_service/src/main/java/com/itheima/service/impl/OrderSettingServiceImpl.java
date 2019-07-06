package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.OrderSettingDao;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    public void add(Date orderDate, int number) {
        int count = orderSettingDao.findOrderSettingByorderDate(orderDate);
        if (count>0) {
            orderSettingDao.update(orderDate,number);
            return;
        }
        orderSettingDao.add(orderDate,number);
    }

    @Override
    public List<OrderSetting> findOrderSettingByCurrentMonth(String current) {
        String startDate = current + "-01";
        String endDate = current + "-31";
        return orderSettingDao.findOrderSettingByCurrentMonth(startDate,endDate);
    }

    @Override
    public void addBatch(List<String[]> strings) {
        for (String[] rows : strings) {
            if (rows == null || rows.length > 2) {
                continue;
            }
            add(new Date(rows[0]),Integer.parseInt(rows[1]));
        }
    }
}
