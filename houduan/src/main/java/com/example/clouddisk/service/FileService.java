package com.example.clouddisk.service;

import com.example.clouddisk.entity.File;
import com.example.clouddisk.entity.ResponseData;
import com.example.clouddisk.util.Node;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.LongSummaryStatistics;

/**
 * 文件信息表(File)表服务接口
 *
 * @author makejava
 * @since 2022-06-16 11:33:25
 */
public interface FileService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    File queryById(Long id);

    /**
     * 分页查询
     *
     * @param file 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<File> queryByPage(File file, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param file 实例对象
     * @return 实例对象
     */
    File insert(File file);

    /**
     * 修改数据
     *
     * @param file 实例对象
     * @return 实例对象
     */
    File update(File file);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 展示所有文件，不含文件夹
     * @param
     * @return
     */
    ResponseData index();

    /**
     *查询图片文件
     * @param userName
     * @param type
     * @return
     */
    ResponseData queryByType(String userName,String type);

    /**
     * 收藏
     * @param id
     * @return
     */
    ResponseData toCollect(Long id);

    /**
     * 取消收藏
     * @param id
     * @return
     */
    ResponseData disCollect(Long id);

    /**
     * 上传文件
     * @param file
     * @return
     */
    ResponseData upload(MultipartFile file,String fileId);

    /**
     * 下载文件
     * @param fileId
     * @return
     * @throws Exception
     */
    ResponseEntity<byte[]> download(String fileId) throws Exception;

    /**
     * 创建文件夹
     * @param
     * @return
     */
    ResponseData mkdir(String parentDir,String fileName);

    /**
     * 加入回收站
     * @param id
     * @return
     */
    ResponseData addRecycle(Long id);

    /**
     * 移除回收站
     * @param id
     * @return
     */
    ResponseData rmRecycle(Long id);
    /**
     * 加入收藏
     * @param id
     * @return
     */
    ResponseData addCollect(Long id);

    /**
     * 取消收藏
     * @param id
     * @return
     */
    ResponseData rmCollect(Long id);

    /**
     * 根目录下的所有文件和文件夹展示
     * @return
     */
    ResponseData index1();

    /**
     * 回收站展示
     * @return
     */
    ResponseData recycle();

    /**
     * 收藏展示
     * @return
     */
    ResponseData collect();

    /**
     * 模块展示
     * @return
     */
    ResponseData module(Integer attribute);

    /**
     * 重命名
     * @param fileid
     * @param filename
     * @return
     */
    ResponseData rename(String fileid,String filename);

    /**
     * 彻底删除
     * @param id
     * @return
     */
    ResponseData delete(Long id);

    /**
     * 批量删除
     * @param ids
     * @return
     */

    ResponseData deleteA(List<Long> ids);
    /**
     * 批量加入回收站
     * @param ids
     * @return
     */
     ResponseData recycleA(List<Long> ids);

    /**
     * 查询下一层
     * @param fileid
     * @return
     */
     ResponseData nextLayer(String fileid);

    /**
     * 清空回收站
     * @return
     */
     ResponseData removeAll();

    /**
     * 上传多个文件
     * @param file
     * @param couldpath
     * @return
     * @throws IOException
     */
    ResponseData uploads(MultipartFile[] file, String couldpath) ;
    ResponseEntity<byte[]> downloadFolder(String couldpath) ;

    ResponseData remove(String path, String newpath) ;

    ResponseData getalldir();

    /**
     * 模糊查询
     * @param name
     * @return
     */
    ResponseData fuzzyQuery(String name);
}
