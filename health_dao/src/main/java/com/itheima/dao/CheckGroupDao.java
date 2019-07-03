package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckGroupDao {
    void add(CheckGroup checkGroup);

    void addCheckGroupAssociation(@Param("checkGroupId") Integer checkGroupId, @Param("checkitemIds") Integer[] checkitemIds);

    Page<CheckGroup> findPage(@Param("queryString") String queryString);

    CheckGroup findCheckGroupById(@Param("id") Integer id);

    List<Integer> findCheckitemIdsByCheckGroupId(@Param("id") Integer id);

    void updateCheckGroup(CheckGroup checkGroup);

    void deleteCheckGroupAssociation(@Param("id") Integer id);

    void deleteCheckGroupById(@Param("id") Integer id);
}
