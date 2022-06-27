package com.example.clouddisk.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.clouddisk.entity.ResponseData;
import com.example.clouddisk.dao.UserInfoDao;
import com.example.clouddisk.entity.UserInfo;
import com.example.clouddisk.service.UserInfoService;
import com.example.clouddisk.util.StringUtil;
import com.example.clouddisk.util.UuidUtil;
import com.example.clouddisk.util.ValidatorUtil;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 用户信息表(UserInfo)表服务实现类
 *
 * @author makejava
 * @since 2022-06-14 09:55:27
 */
@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserInfoDao userInfoDao;
    @Autowired
    JedisPool jedisPool;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public UserInfo queryById(Long id) {
        return this.userInfoDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param userInfo    筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @Override
    public Page<UserInfo> queryByPage(UserInfo userInfo, PageRequest pageRequest) {
        long total = this.userInfoDao.count(userInfo);
        return new PageImpl<>(this.userInfoDao.queryAllByLimit(userInfo, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param userInfo 实例对象
     * @return 实例对象
     */
    @Override
    public UserInfo insert(UserInfo userInfo) {
        this.userInfoDao.insert(userInfo);
        return userInfo;
    }

    /**
     * 修改数据
     *
     * @param userInfo 实例对象
     * @return 实例对象
     */
    @Override
    public UserInfo update(UserInfo userInfo) {
        this.userInfoDao.update(userInfo);
        return this.queryById(userInfo.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.userInfoDao.deleteById(id) > 0;
    }


    /**
     * 注册
     *
     * @param userInfo
     * @return
     */
    @Override
    public ResponseData userRegister(UserInfo userInfo) {
        userInfo.setNickName(UuidUtil.getUuid());
        String phone = userInfo.getPhone();
        String userName = userInfo.getUserName();
        String password = userInfo.getPassword();
        String email = userInfo.getEmail();
        //非空验证
        if (StringUtil.isNull(userName)) {
            return new ResponseData("9001", "用户名为空");
        }
        if (StringUtil.isNull(password)) {
            return new ResponseData("9002", "密码为空");
        }
        if (StringUtil.isNull(phone)) {
            return new ResponseData("9003", "手机号为空");
        }
        if (StringUtil.isNull(email)) {
            return new ResponseData("9004", "邮箱为空");
        }
        //格式验证
        if (!ValidatorUtil.isUsername(userName)) {
            return new ResponseData("9001", "用户名格式错误");
        }
        if (!ValidatorUtil.isPassword(password)) {
            return new ResponseData("9002", "密码格式错误");
        }
        if (!ValidatorUtil.isMobile(phone)) {
            return new ResponseData("9003", "手机号格式错误");
        }
        if (!ValidatorUtil.isEmail(email)) {
            return new ResponseData("9004", "邮箱格式错误");
        }
        try {
            //2.校验用户名是否存在
            //如何校验？  根据username查询user表   如果有数据  说明存在  返回提示信息
            UserInfo queryUser = userInfoDao.queryByUserName(userName);
            if (queryUser != null) {
                return new ResponseData("9005", "用户名已经存在");
            }
            //3.加密   source:加密的资源 s123456  salt：盐值   hashIterations：加密次数
            Md5Hash md5Hash = new Md5Hash(password, "june", 10);
            String newPassword = md5Hash.toString(); //得到加密之后的密码
            //4.保存
            userInfo.setPassword(newPassword);
            //5.调用dao层进行持久化
            userInfoDao.insert(userInfo);
            return new ResponseData("6666", "请求成功");
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseData("9999", "网络异常");
        }
    }


    /**
     * 登录
     *
     * @param userName
     * @param password
     * @return
     */
    @Override
    public ResponseData userLogin(String userName, String password) {
//1.非空校验
        if (StringUtil.isNull(userName)) {
            return new ResponseData("9001", "用户名为空");
        }
        if (StringUtil.isNull(password)) {
            return new ResponseData("9002", "密码为空");
        }
        //2.密码加密
        Md5Hash md5Hash = new Md5Hash(password, "june", 10);
        String nwePassword = md5Hash.toString();


        //3.根据用户名密码查询用户信息  user
        UserInfo user = userInfoDao.queryByUserNameAndPwd(userName, nwePassword);
        if (user == null) { //说明错误
            return new ResponseData("9005", "账号密码不匹配");
        }
        Jedis resource = jedisPool.getResource();
        resource.set("username", userName);
        return new ResponseData("6666", "请求成功", user);


    }

    @Override
    public ResponseData updatePwd(String password, String newPwd) {
        try {
            Jedis resource = jedisPool.getResource();
            String userName = resource.get("username");
            if (StringUtil.isNull(password)) {
                return new ResponseData("9002", "密码为空");
            }
            //2.密码加密
            Md5Hash md5Hash = new Md5Hash(password, "june", 10);
            String nwePassword = md5Hash.toString();
            UserInfo user = userInfoDao.queryByUserNameAndPwd(userName, nwePassword);
            if (user == null) { //说明错误
                return new ResponseData("9005", "密码错误");
            }
            if (StringUtil.isNull(newPwd)) {
                return new ResponseData("9002", "新密码为空");
            }
            if (!ValidatorUtil.isPassword(newPwd)) {
                return new ResponseData("9002", "密码格式错误");
            }
            //3.加密   source:加密的资源 s123456  salt：盐值   hashIterations：加密次数
            Md5Hash md5Hash1 = new Md5Hash(newPwd, "june", 10);
            String newPassword1 = md5Hash1.toString(); //得到加密之后的密码
            user.setPassword(newPassword1);
            userInfoDao.update(user);
            return new ResponseData("6666", "请求成功", user);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData("9999", "网络异常");
        }
    }

    @Override
    public ResponseData updateMsg(String nickname, String phone, String email) {
        try {
            if (!ValidatorUtil.isMobile(phone)) {
                return new ResponseData("9003", "手机号格式错误");
            }

            if (!ValidatorUtil.isEmail(email)) {
                return new ResponseData("9004", "邮箱格式错误");

            }

            Jedis resource = jedisPool.getResource();
            String userName = resource.get("username");
            UserInfo user = userInfoDao.queryByUserName(userName);
            user.setNickName(nickname);
            user.setPhone(phone);
            user.setEmail(email);
            userInfoDao.update(user);
            return new ResponseData("6666", "请求成功", user);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData("9999", "网络异常");
        }
    }

    @Override
    public ResponseData admin() {
        try {
            List<UserInfo> users = userInfoDao.queryAll();
            return new ResponseData("6666", "请求成功", users);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData("9999", "网络异常");
        }

    }

    @Override
    public ResponseData adminUpdate(UserInfo userInfo) {
        try {
            userInfoDao.update(userInfo);
            return new ResponseData("6666", "请求成功", userInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData("9999", "网络异常");
        }
    }

    @Override
    public ResponseData adminQuery(String name) {
        try {
            List<UserInfo> users = userInfoDao.adminQuery(name);
            return new ResponseData("6666", "请求成功", users);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData("9999", "网络异常");
        }
    }

    @Override
    public ResponseData adminDelete(Long id) {
        try {
            userInfoDao.deleteById(id);
            return new ResponseData("6666", "请求成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData("9999", "网络异常");
        }
    }


}
