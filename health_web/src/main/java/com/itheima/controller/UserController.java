package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/user")
public class UserController {
    @Reference
    private UserService userService;
    @RequestMapping("/getUser")
    @ResponseBody
    public User getUser(String id) {
        User user = userService.getUser(id);
        return user;
    }
}
