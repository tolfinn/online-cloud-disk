package com.example.clouddisk.dao;

import com.example.clouddisk.entity.File;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 文件信息表(File)表数据库访问层
 *
 * @author makejava
 * @since 2022-06-16 11:33:19
 */
public interface FileDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    File queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param file 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<File> queryAllByLimit(File file, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param file 查询条件
     * @return 总行数
     */
    long count(File file);

    /**
     * 新增数据
     *
     * @param file 实例对象
     * @return 影响行数
     */
    int insert(File file);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<File> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<File> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<File> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<File> entities);

    /**
     * 修改数据
     *
     * @param file 实例对象
     * @return 影响行数
     */
    int update(File file);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 通过上级目录查找当前目录下面的文件和文件夹
     * @param userName
     * @param parentDir
     * @return
     */
    List<File> queryByPDir(String userName,String parentDir);

    /**
     * 首页，所有文件，不包括文件夹
     * @param userName
     * @param parentDir
     * @param type
     * @return
     */
    List<File> queryByPDir1(String userName,String parentDir,String type);

    /**
     * 回收站恢复用
     * @param userName
     * @param parentDir
     * @return
     */
    List<File> queryByPDir2(String userName,String parentDir);
    /**
     * 按文件类别查询
     * @param userName
     * @param type
     * @return
     */
    List<File> queryByType(String userName,String type);

    /**
     * 收藏
     * @param id
     * @return
     */
    int updateCol(Long id,Integer iscollect);

    /**
     * 回收站
     * @param id
     * @param recycle
     * @return
     */
    int updateRec(Long id,Integer recycle);

    /**
     * 查询回收站
     * @param userName
     * @return
     */
    List<File> queryRecycle(String userName);

    /**
     * 根据大的类型查找
     * @param userName
     * @param attribute
     * @return
     */
    List<File> queryByAttr(String userName,Integer attribute);

    /**
     * 根据fileId查询
     * @param fileId
     * @return
     */
    File queryByFileId(String fileId);

    /**
     * 查询收藏
     * @param userName
     * @return
     */
    List<File> queryCollect(String userName);

    /**
     * 清空回收站
     * @param userName
     * @return
     */
    int deleteAll(String userName);



    List<File> queryAllDir();


}

