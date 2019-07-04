package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckGroupDao;
import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;

    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        //1、将checkGroup保存数据库t_checkgroup
        checkGroupDao.add(checkGroup);
        Integer checkGroupId = checkGroup.getId();
        //2、遍历将checkitemIds插入到t_checkgroup_checkitem
        checkGroupDao.addCheckGroupAssociation(checkGroup.getId(),checkitemIds);
    }

    /*public void setCheckGroupAssociation(Integer id ,Integer[] checkitemIds) {
        //2、遍历将checkitemIds插入到t_checkgroup_checkitem
        if(checkitemIds!=null&&checkitemIds.length>0){
            for (Integer checkitemId : checkitemIds) {
                checkGroupDao.addCheckGroupAssociation(id,checkitemId);
            }
        }

    }*/

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        Page<CheckGroup> page = checkGroupDao.findPage(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public CheckGroup findCheckGroupById(Integer id) {
        return checkGroupDao.findCheckGroupById(id);
    }

    @Override
    public List<Integer> findCheckitemIdsByCheckGroupId(Integer id) {
        return checkGroupDao.findCheckitemIdsByCheckGroupId(id);
    }

    @Override
    public void updateCheckGroup(CheckGroup checkGroup, Integer[] checkitemIds) {
        checkGroupDao.updateCheckGroup(checkGroup);
        checkGroupDao.deleteCheckGroupAssociation(checkGroup.getId());
        checkGroupDao.addCheckGroupAssociation(checkGroup.getId(),checkitemIds);
    }

    @Override
    public void deleteById(Integer id) {
        checkGroupDao.deleteCheckGroupById(id);
        checkGroupDao.deleteCheckGroupAssociation(id);
    }

    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }
}
