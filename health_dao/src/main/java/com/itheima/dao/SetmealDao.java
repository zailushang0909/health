package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SetmealDao {
    void add(Setmeal setmeal);

    void addSetMealAssociation(@Param("id") Integer id, @Param("checkGroupIds") List<Integer> checkGroupIds);

    Page<Setmeal> findPage(@Param("queryString") String queryString);
}
