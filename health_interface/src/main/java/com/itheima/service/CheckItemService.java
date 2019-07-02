package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;

public interface CheckItemService {
    void add(CheckItem checkItem);

    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    CheckItem findById(Integer id);

    void updateById(CheckItem checkItem);

    void deleteById(Integer id);
}
