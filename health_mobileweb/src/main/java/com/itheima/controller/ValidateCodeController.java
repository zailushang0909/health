package com.itheima.controller;

import com.itheima.entity.MessageConstant;
import com.itheima.entity.RedisConstant;
import com.itheima.entity.Result;
import com.itheima.utils.JuHeUtils;
import com.itheima.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/send4Order")
    public Result send4Order(String telephone) {
        try {
            /*//1、调用工具类生成验证码
            String validateCode = ValidateCodeUtils.generateValidateCode4String(4);
            //2、调用工具类发送验证码
            JuHeUtils.sendValidateCode(telephone,validateCode);
            //3、将验证码存入redis缓存
            jedisPool.getResource().setex(RedisConstant.ORDERVALIDATEKEY_PREFIX + telephone, 60 * 60, validateCode);*/
            jedisPool.getResource().setex(RedisConstant.ORDERVALIDATEKEY_PREFIX + telephone, 60 * 60,"123456");
            //4、响应成功结果
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            //5、响应失败结果
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }

    @RequestMapping("/send4Login")
    public Result send4Login(String telephone) {
        try {
            String loginValidateCode = ValidateCodeUtils.generateValidateCode4String(6);
            jedisPool.getResource().setex(RedisConstant.LOGINVALIDATEKEY_PREFIX + telephone, 60 * 60, loginValidateCode);
            JuHeUtils.sendLogin(telephone,loginValidateCode);
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }

}
