package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.RedisConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class MLoginController {

    @Autowired
    private JedisPool jedisPool;
    @Reference
    private MemberService memberService;

    @RequestMapping("/check")
    public Result check(@RequestBody Map<String,String> map, HttpServletResponse response) {
        try {
            String redisValidateCode = jedisPool.getResource().get(RedisConstant.LOGINVALIDATEKEY_PREFIX + map.get("telephone"));
            if (map.get("validateCode")==null || !map.get("validateCode").equals(redisValidateCode)) {
                return Result.error("验证码有误请重新输入");
            }
            //1、查询有没有该会员
            Member member = memberService.findMemberByTel(map.get("telephone"));
            if (member==null) {
                //2、没有注册
                member = new Member();
                member.setPhoneNumber(map.get("telephone"));
                member.setRegTime(new Date());
                memberService.addMember(member);
            }
            Cookie cookie = new Cookie("member_tel", map.get("telephone"));
            //3、将用户手机号写入cookie跟踪用户
            response.addCookie(cookie);
            return Result.success("登陆成功",null);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("登陆失败");
        }
    }

}
