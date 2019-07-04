package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.SetmealDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.RedisConstant;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.List;

@Transactional
@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealDao setmealDao;
    @Autowired
    private JedisPool jedisPool;

    @Override
    public void add(Setmeal setmeal) {
        //1、添加套餐记录
        setmealDao.add(setmeal);
        //2、添加中间表记录
        if (setmeal.getCheckGroupIds()!=null && setmeal.getCheckGroupIds().size()>0) {
            setmealDao.addSetMealAssociation(setmeal.getId(),setmeal.getCheckGroupIds());
        }
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());
    }

    @Override
    public PageResult findPage(Integer currentPage, String queryString, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        Page<Setmeal> setmeals =setmealDao.findPage(queryString);
        return new PageResult(setmeals.getTotal(), setmeals.getResult());
    }
}
