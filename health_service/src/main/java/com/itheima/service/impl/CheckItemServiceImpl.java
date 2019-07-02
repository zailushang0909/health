package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckItemDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;

    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        Page<CheckItem> page = PageHelper.startPage(currentPage, pageSize);
        List<CheckItem> checkItemList = checkItemDao.findPage(queryString);
        return  new PageResult(page.getTotal(), checkItemList);
    }

    @Override
    public CheckItem findById(Integer id) {
       return checkItemDao.findById(id);
    }

    @Override
    public void updateById(CheckItem checkItem) {
        checkItemDao.updateById(checkItem);
    }

    @Override
    public void deleteById(Integer id) {
        checkItemDao.deleteById(id);
    }
}
