package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {
    @Override
    public User getUser(String id) {
        User user = new User();
        user.setUserName("ZhangTianCi");
        user.setAge(24);
        return user;
    }
}
