package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {
    void add(CheckGroup checkGroup, Integer[] checkitemIds);

    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    CheckGroup findCheckGroupById(Integer id);

    List<Integer> findCheckitemIdsByCheckGroupId(Integer id);

    void updateCheckGroup(CheckGroup checkGroup, Integer[] checkitemIds);

    void deleteById(Integer id);

    List<CheckGroup> findAll();

}
