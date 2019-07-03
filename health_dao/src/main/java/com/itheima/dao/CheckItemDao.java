package com.itheima.dao;

import com.itheima.pojo.CheckItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckItemDao {
    void add(CheckItem checkItem);

    List<CheckItem> findPage(@Param("queryString") String queryString);

    CheckItem findById(@Param("id") Integer id);

    void updateById(CheckItem checkItem);

    void deleteById(@Param("id") Integer id);

    List<CheckItem> findAll();
}
