package com.example.clouddisk.controller;

import com.example.clouddisk.entity.File;
import com.example.clouddisk.entity.ResponseData;
import com.example.clouddisk.service.FileService;
import org.apache.catalina.Session;
import org.apache.commons.io.IOUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.SessionScope;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.xml.crypto.Data;
import java.io.InputStream;
import java.net.URLEncoder;
import java.security.PublicKey;
import java.util.List;

/**
 * 文件信息表(File)表控制层
 *
 * @author makejava
 * @since 2022-06-16 11:33:19
 */
@RestController
@RequestMapping("file")
public class FileController {
    /**
     * 服务对象
     */
    @Resource
    private FileService fileService;

    /**
     * 分页查询
     *
     * @param file 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<File>> queryByPage(File file, PageRequest pageRequest) {
        return ResponseEntity.ok(this.fileService.queryByPage(file, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<File> queryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.fileService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param file 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<File> add(File file) {
        return ResponseEntity.ok(this.fileService.insert(file));
    }

    /**
     * 编辑数据
     *
     * @param file 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<File> edit(File file) {
        return ResponseEntity.ok(this.fileService.update(file));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Long id) {
        return ResponseEntity.ok(this.fileService.deleteById(id));
    }

    /**
     * 展示所有文件，不含文件夹
     * @param
     * @return
     */
    @GetMapping("index")
    public ResponseData index(){
        return fileService.index();

    }

    /**
     * 根据类型查询
     * @param username
     * @param type
     * @return
     */
    @GetMapping("queryByType")
    public ResponseData queryByType(String username,String type){
        return fileService.queryByType(username,type);
    }


    /**
     * 收藏
     * @param id
     * @return
     */
    @GetMapping("toCollect")
    public ResponseData toCollect(Long id){
        return fileService.toCollect(id);
    }

    /**
     * 取消收藏
     * @param id
     * @return
     */
    @GetMapping("disCollect")
    public ResponseData disCollect(Long id){
        return fileService.disCollect(id);
    }

    /**
     * 上传
     * @param file
     * @return
     */
    @PostMapping("upload")
    public ResponseData upload(MultipartFile file,String fileId){

          return fileService.upload(file,fileId);
    }

    /**
     * 下载
     * @param fileid
     * @return
     * @throws Exception
     */
    @GetMapping("download")
    public ResponseEntity<byte[]> download(String fileid) throws Exception {
        System.out.println(fileid);
      return  fileService.download(fileid);
    }

    /**
     * 创建文件夹
     * @param
     * @return
     */
    @PostMapping("mkdir")
    public ResponseData mkdir(String parentDir,String fileName){
        return fileService.mkdir(parentDir,fileName);
    }

    /**
     * 加入回收站
     * @param id
     * @return
     */
    @GetMapping("addRecycle")
    public ResponseData addRecycle(Long id){

        return fileService.addRecycle(id);

    }

    /**
     * 移出回收站
     * @param id
     * @return
     */
    @GetMapping("rmRecycle")
    public ResponseData rmRecycle(Long id){
        return fileService.rmRecycle(id);
    }
    /**
     * 加入收藏
     * @param id
     * @return
     */
    @GetMapping("addCollect")
    public ResponseData addCollect(Long id){

        return fileService.addCollect(id);

    }

    /**
     * 取消收藏
     * @param id
     * @return
     */
    @GetMapping("rmCollect")
    public ResponseData rmCollect(Long id){
        return fileService.rmCollect(id);
    }
    /**
     * 根目录下所有文件和文件夹
     * @return
     */
    @GetMapping("index1")
    public ResponseData index1(){
        return fileService.index1();
}

    /**
     * 回收站展示
     * @return
     */
   @GetMapping("recycle")
    public ResponseData recycle(){
        return fileService.recycle();
   }

    /**
     * 分模块展示
     * @param attribute
     * @return
     */
   @PostMapping("module")
    public ResponseData module(Integer attribute){
        return fileService.module(attribute);
   }

    /**
     * 重命名文件
     * @param fileid
     * @param filename
     * @return
     */
   @PostMapping("rename")
    public ResponseData rename(String fileid,String filename){
        return fileService.rename(fileid,filename);
   }

   @GetMapping("collect")
    public ResponseData collect(){
       return fileService.collect();
   }
   @GetMapping("delete")
    public ResponseData delete(Long id){
       return fileService.delete(id);
   }

    /**
     * 批量加入回收站
     * @param ids
     * @return
     */
   @PostMapping("recycleA")
   public ResponseData recycleA(@RequestParam List<Long> ids){
      return fileService.recycleA(ids);
   }

    /**
     * 批量删除
     * @param ids
     * @return
     */
   @PostMapping("deleteA")

    public ResponseData deleteA(@RequestParam List<Long> ids){

       return fileService.deleteA(ids);
   }

    /**
     * 查询下一层
     * @param fileid
     * @return
     */
   @PostMapping("nextLayer")
    public ResponseData nextLayer(String fileid){
       return fileService.nextLayer(fileid);
   }

    /**
     * 清空回收站
     * @return
     */
   @GetMapping("removeAll")
    public ResponseData removeAll(){
       return fileService.removeAll();
   }

    /**
     * 获取文件夹树状结构
     * @return
     */
   @GetMapping("getalldir")
    public ResponseData getalldir(){
       return fileService.getalldir();
   }

    /**
     * 移动文件夹
     * @param fileid
     * @param parentDir
     * @return
     */
   @PostMapping("remove")
    public ResponseData remove(String fileid,String parentDir){
       return fileService.remove(fileid,parentDir);
   }

   @GetMapping("fuzzyQuery")
    public ResponseData fuzzyQuery(String name){
       return fileService.fuzzyQuery(name);
   }
}

