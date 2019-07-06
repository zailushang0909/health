package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import com.itheima.utils.POIUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/orderSetting")
public class OrderSettingConntroller {

    @Reference
    private OrderSettingService orderSettingService;

    @RequestMapping("/add")
    public Result add(@RequestBody OrderSetting orderSetting) {
        try {
            orderSettingService.add(orderSetting.getOrderDate(), orderSetting.getNumber());
            return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ORDERSETTING_FAIL);
        }
    }

    @RequestMapping("/findOrderSettingByCurrentMonth")
    public  Result findOrderSettingByCurrentMonth(@RequestParam("current") String current) {
        try {
            List<OrderSetting> orderSettings = orderSettingService.findOrderSettingByCurrentMonth(current);
            return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS, orderSettings);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ORDER_FAIL);
        }
     }

     @RequestMapping("/upload")
    public Result upload(@RequestParam("excelFile") MultipartFile excelFile) {
         try {
             List<String[]> strings = null;
                 strings = POIUtils.readExcel(excelFile);
             orderSettingService.addBatch(strings);
             return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
         } catch (Exception e) {
             e.printStackTrace();
             return new Result(false, MessageConstant.ORDERSETTING_FAIL);
         }
     }
}
