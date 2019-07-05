package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.pojo.Setmeal;

import java.util.List;

public interface SetmealService {

    void add(Setmeal setmeal);

    PageResult findPage(Integer currentPage, String queryString, Integer pageSize);

    void delete(Integer id, String img);

    Setmeal findSetmealById(Integer id);

    List<Integer> findCheckgroupIdsBySetmealId(Integer id);

    void update(Setmeal setmeal);
}
