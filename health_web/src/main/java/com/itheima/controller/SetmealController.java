package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.*;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import com.itheima.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference
    private SetmealService setmealService;
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile) {
        try {
            //获取保存文件名
            String fileName = System.currentTimeMillis() + imgFile.getOriginalFilename();
            //4、调用工具类保存七牛云
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
            //5、成功则响应成功消息
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, fileName);
        } catch (IOException e) {
            e.printStackTrace();
            //6、失败则响应失败消息
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }

    }

    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal) {
        try {
            setmealService.add(setmeal);
            /*jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());*/
            return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
    }

    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            PageResult pageResult = setmealService.findPage(queryPageBean.getCurrentPage(),queryPageBean.getQueryString(),queryPageBean.getPageSize());
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);

        }
    }
    @RequestMapping("/delete")
    public Result delete(@RequestParam("id") Integer id, @RequestParam("img")String img) {
        try {
            setmealService.delete(id,img);
            return new Result(true, MessageConstant.DELETESETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_SETMEAL_FAIL);
        }
    }

    @RequestMapping("/findSetmealById")
    public Result findSetmealById(@RequestParam("id")Integer id) {
        try {
            Setmeal setmeal = setmealService.findSetmealById(id);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);

        }
    }
    @RequestMapping("/findCheckgroupIdsBySetmealId")
    public Result findCheckgroupIdsBySetmealId(@RequestParam("id") Integer id) {
        try {
            List<Integer> checkGroupIds = setmealService.findCheckgroupIdsBySetmealId(id);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,checkGroupIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
    @RequestMapping("/updata")
    public  Result updata(@RequestBody Setmeal setmeal) {
        try {
            setmealService.update(setmeal);
            return new Result(true, MessageConstant.EDIT_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_SETMEAL_FAIL);
        }
    }
}
