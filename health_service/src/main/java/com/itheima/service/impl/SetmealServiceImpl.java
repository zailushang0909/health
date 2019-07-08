package com.itheima.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckGroupDao;
import com.itheima.dao.SetmealDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.RedisConstant;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Transactional
@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealDao setmealDao;
    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private CheckGroupDao checkGroupDao;

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

    @Override
    public void delete(Integer id, String img) {
        //1、删除数据库中中记录
        setmealDao.delete(id);
        //2、删除关系表中数据
        setmealDao.deleteSetmealAssociation(id);
        //3、删除数据库图片缓存中数据
        jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_DB_RESOURCES, img);
    }

    @Override
    public Setmeal findSetmealById(Integer id) {
        return setmealDao.findSetmealById(id);
    }

    @Override
    public List<Integer> findCheckgroupIdsBySetmealId(Integer id) {
       return setmealDao.findCheckgroupIdsBySetmealId(id);
    }

    @Override
    public void update(Setmeal setmeal) {
        //按id查询数据库中img
        String imgName = setmealDao.getImgNameBySetmealId(setmeal.getId());
        //1、判断数据库有图片名字与传过来参数是否不一致
        if (!Objects.equals(setmeal.getImg(),imgName)) {
            //1.1不一致则删除redis中的数据
                jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_DB_RESOURCES, imgName);
        }
        //2、更新数据库中数据
        setmealDao.update(setmeal);
        //3、删除数据库中的关系
        setmealDao.deleteSetmealAssociation(setmeal.getId());
        //4、新建关系表中的关系
        if (setmeal.getCheckGroupIds()!=null&&setmeal.getCheckGroupIds().size()>0) {
            setmealDao.addSetMealAssociation(setmeal.getId(),setmeal.getCheckGroupIds());
        }
        //5、直接存到redis
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg());
    }

    @Override
    public List<Setmeal> getSetmeal() {
        return setmealDao.getSetmeal();
    }

    @Override
    public Setmeal findById(Integer id) {
        //1、根据setmeal id查询setmeal
        Setmeal setmeal = setmealDao.findSetmealById(id);
        //2、根据setmeal id查询检查组
        List<CheckGroup> checkGroups = checkGroupDao.findCheckGroupsBySetmealId(id);
        //3、遍历检查组根据检查组id查询检查项
        if (CollectionUtil.isNotEmpty(checkGroups)) {
            for (CheckGroup checkGroup : checkGroups) {
                List<CheckItem> checkitems = checkGroupDao.findCheckitemsByCheckGroupId(checkGroup.getId());
                checkGroup.setCheckItems(checkitems);
            }
        }
        setmeal.setCheckGroups(checkGroups);
        return setmeal;
    }

    @Override
    public Setmeal findByIdBatch(Integer id) {
        //1、根据setmeal id查询setmeal
        Setmeal setmeal = setmealDao.findSetmealById(id);
        //2、根据setmeal id查询检查组
        List<CheckGroup> checkGroups = checkGroupDao.findCheckGroupsBySetmealId(id);
        //3、根据setmeal id查询checkitems项
        if (CollectionUtil.isNotEmpty(checkGroups)) {
            List<CheckItem> checkItems = setmealDao.findCheckItemsBySetmealId(id);
            Map<Integer, List<CheckItem>> collect = checkItems.stream().collect(Collectors.groupingBy(CheckItem::getCheckgroupId));
            for (CheckGroup checkGroup : checkGroups) {
                checkGroup.setCheckItems(collect.get(checkGroup.getId()));
            }
        }
        setmeal.setCheckGroups(checkGroups);
        return setmeal;
    }

}
