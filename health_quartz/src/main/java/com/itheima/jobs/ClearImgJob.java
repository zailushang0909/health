package com.itheima.jobs;


import com.itheima.entity.RedisConstant;
import com.itheima.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

import java.util.Iterator;
import java.util.Set;


@Component("clearImgJob")
public class ClearImgJob {

    @Autowired
    JedisPool jedisPool;

    public void deleteImg() {
        System.out.println("jedisPool = " + jedisPool);
        Set<String> sdiff = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES,RedisConstant.SETMEAL_PIC_DB_RESOURCES);
       /* Iterator<String> iterator = sdiff.iterator();*/
        /*while (iterator.hasNext()) {
        }*/
        for (String fileName : sdiff) {
            System.out.println("fileName = " + fileName);
            QiniuUtils.deleteFileFromQiniu(fileName);
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES, fileName);
        }
    }
}
