package com.example.clouddisk.dao;

import com.example.clouddisk.entity.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 用户信息表(UserInfo)表数据库访问层
 *
 * @author makejava
 * @since 2022-06-14 09:55:27
 */
public interface UserInfoDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    UserInfo queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param userInfo 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<UserInfo> queryAllByLimit(UserInfo userInfo, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param userInfo 查询条件
     * @return 总行数
     */
    long count(UserInfo userInfo);

    /**
     * 新增数据
     *
     * @param userInfo 实例对象
     * @return 影响行数
     */
    int insert(UserInfo userInfo);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<UserInfo> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<UserInfo> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<UserInfo> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<UserInfo> entities);

    /**
     * 修改数据
     *
     * @param userInfo 实例对象
     * @return 影响行数
     */
    int update(UserInfo userInfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 通过用户名查询用户
     * @param userName
     * @return
     */
    UserInfo queryByUserName(String userName);

    /**
     * 通过用户名和密码查询
     * @param userName
     * @param password
     * @return
     */
    UserInfo queryByUserNameAndPwd(String userName,String password);

    List<UserInfo> queryAll();

    List<UserInfo> adminQuery(String name);



}

