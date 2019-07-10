package com.itheima.controller;

import cn.hutool.core.lang.UUID;
import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.RedisConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.OrderInfo;
import com.itheima.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/order")
public class MOrderController {

    @Reference
    private OrderService orderService;

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/submit")
    public Result submit(@RequestBody OrderInfo orderInfo) {
        try {
            //1、获取表单提交验证码
            String formValidateCode = orderInfo.getValidateCode();
            String redisValidateCode = jedisPool.getResource().get(RedisConstant.ORDERVALIDATEKEY_PREFIX + orderInfo.getTelephone());
            //2、判断验证码为空或者表单验证码与缓存中验证码不一致返回验证码错误
            if (formValidateCode==null ||!formValidateCode.equals(redisValidateCode)) {
                return new Result(false, "验证码有误修改验证码重新提交");
            }
            Boolean boo = jedisPool.getResource().sismember(RedisConstant.FORMID_KEY, orderInfo.getFormId());
            if (!boo) {
                return new Result(false, "请勿重复提交");
            }
            jedisPool.getResource().srem(RedisConstant.FORMID_KEY, orderInfo.getFormId());
            //3、否则执行添加订单操作
            Result result = orderService.addOrder(orderInfo);
            //4、将返回结果响应给浏览器
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "服务器繁忙，请稍后重试");
        }
    }

    @RequestMapping("/getFormId")
    public Result getFormId() {
        String formId = UUID.randomUUID().toString();
        jedisPool.getResource().sadd(RedisConstant.FORMID_KEY, formId);
        return new Result(true, "获取表单id成功", formId);
    }
}
