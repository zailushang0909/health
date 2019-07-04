package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/checkGroup")
public class CheckGroupController {
    @Reference
    private CheckGroupService checkGroupService;

    @RequestMapping("add")
    public Result add(@RequestBody CheckGroup checkGroup , Integer[] checkitemIds) {
        try {
            checkGroupService.add(checkGroup,checkitemIds);
            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
    }

    @RequestMapping("/findPage")
    public Result findPage( @RequestBody QueryPageBean queryPageBean) {
        try {
            PageResult pageResult = checkGroupService.findPage(queryPageBean.getCurrentPage(),
                                        queryPageBean.getPageSize(),
                                        queryPageBean.getQueryString());
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    @RequestMapping("/findCheckGroupById")
    public Result findCheckGroupById(@RequestParam Integer id) {
        try {
            CheckGroup checkGroup = checkGroupService.findCheckGroupById(id);
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkGroup);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }
    @RequestMapping("findCheckitemIdsByCheckGroupId")
    public Result findCheckitemIdsByCheckGroupId(@RequestParam("id") Integer id) {
        try {
            List<Integer> checkItemIds= checkGroupService.findCheckitemIdsByCheckGroupId(id);
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkItemIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    @RequestMapping("/updateCheckGroup")
    public Result updateCheckGroup(@RequestBody CheckGroup checkGroup,@RequestParam("checkitemIds") Integer[] checkitemIds) {
        try {
            checkGroupService.updateCheckGroup(checkGroup, checkitemIds);
            return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL);

        }
    }
    @RequestMapping("/deleteById")
    public Result deleteById(@RequestParam("id") Integer id) {
        try {
            checkGroupService.deleteById(id);
            return new Result(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
    }

    @RequestMapping("/findAll")
    public Result findAll() {
        try {
            List<CheckGroup> groups = checkGroupService.findAll();
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, groups);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }
}
