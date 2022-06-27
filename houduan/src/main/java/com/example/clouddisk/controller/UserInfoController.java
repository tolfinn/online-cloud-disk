package com.example.clouddisk.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.clouddisk.entity.ResponseData;
import com.example.clouddisk.entity.UserInfo;
import com.example.clouddisk.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * 用户信息表(UserInfo)表控制层
 *
 * @author makejava
 * @since 2022-06-14 09:55:27
 */
@RestController
@RequestMapping("userInfo")
public class UserInfoController {


    /**
     * 服务对象
     */
    @Resource
    private UserInfoService userInfoService;

    /**
     * 分页查询
     *
     * @param userInfo 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<UserInfo>> queryByPage(UserInfo userInfo, PageRequest pageRequest) {
        return ResponseEntity.ok(this.userInfoService.queryByPage(userInfo, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<UserInfo> queryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.userInfoService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param userInfo 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<UserInfo> add(UserInfo userInfo) {
        return ResponseEntity.ok(this.userInfoService.insert(userInfo));
    }

    /**
     * 编辑数据
     *
     * @param userInfo 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<UserInfo> edit(UserInfo userInfo) {
        return ResponseEntity.ok(this.userInfoService.update(userInfo));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Long id) {
        return ResponseEntity.ok(this.userInfoService.deleteById(id));
    }

    /**
     * 注册
     * @param userInfo
     * @return
     */
   @PostMapping("userRegister")
    public ResponseData userRegister(UserInfo userInfo){
       System.out.println(userInfo.toString());
        return userInfoService.userRegister(userInfo);

   }

    /**
     * 登录
     * @param userName
     * @param password
     * @return
     */
   @PostMapping("userLogin")
    public ResponseData userLogin(String userName,String password){

       System.out.println(userName+" "+ password);
        return userInfoService.userLogin(userName,password);
   }



}

