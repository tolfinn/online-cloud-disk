package com.example.clouddisk.service.impl;

import com.example.clouddisk.common.TypeArray;
import com.example.clouddisk.entity.File;
import com.example.clouddisk.dao.FileDao;
import com.example.clouddisk.entity.ResponseData;
import com.example.clouddisk.service.FileService;
import com.example.clouddisk.util.FolderTree;
import com.example.clouddisk.util.HDFSUtil;
import com.example.clouddisk.util.Node;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文件信息表(File)表服务实现类
 *
 * @author makejava
 * @since 2022-06-16 11:33:25
 */
@Service("fileService")
public class FileServiceImpl implements FileService {
    @Resource
    private FileDao fileDao;
    @Resource
    private HDFSUtil hdfsUtil;
    @Autowired
    JedisPool jedisPool;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public File queryById(Long id) {
        return this.fileDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param file        筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @Override
    public Page<File> queryByPage(File file, PageRequest pageRequest) {
        long total = this.fileDao.count(file);
        return new PageImpl<>(this.fileDao.queryAllByLimit(file, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param file 实例对象
     * @return 实例对象
     */
    @Override
    public File insert(File file) {
        this.fileDao.insert(file);
        return file;
    }

    /**
     * 修改数据
     *
     * @param file 实例对象
     * @return 实例对象
     */
    @Override
    public File update(File file) {
        this.fileDao.update(file);
        return this.queryById(file.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.fileDao.deleteById(id) > 0;
    }

    /**
     * 展示所有文件，不含文件夹
     *
     * @param
     * @return
     */
    @Override
    public ResponseData index() {
        try {
            Jedis resource = jedisPool.getResource();
            String userName = resource.get("username");
            List<File> files = fileDao.queryByPDir1(userName, "/" + userName, "dir");
            return new ResponseData("6666", "请求成功", files);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseData("9999", "网络异常");
        }
    }

    /**
     * 根据类别查询
     *
     * @param userName
     * @param type
     * @return
     */
    @Override
    public ResponseData queryByType(String userName, String type) {
        try {
            List<File> files = fileDao.queryByType(userName, type);
            return new ResponseData("6666", "请求成功", files);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseData("9999", "网络异常");

        }
    }

    /**
     * 收藏
     *
     * @param id
     * @return
     */
    @Override
    public ResponseData toCollect(Long id) {
        try {
            int i = fileDao.updateCol(id, 1);
            return new ResponseData("6666", "请求成功");
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseData("9999", "网络异常");

        }
    }

    /**
     * 取消收藏
     *
     * @param id
     * @return
     */
    @Override
    public ResponseData disCollect(Long id) {
        try {
            int i = fileDao.updateCol(id, 0);
            return new ResponseData("6666", "请求成功");
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseData("9999", "网络异常");
        }
    }

    /**
     * 上传文件
     *
     * @return
     */
    @Override
    public ResponseData upload(MultipartFile file, String fileId) {

        try {
            Jedis resource = jedisPool.getResource();
            String userName = resource.get("username");
            /*    String userName="june1234";*/
            String path = fileId + "/" + file.getOriginalFilename();
            boolean upload = hdfsUtil.upload(path, file.getInputStream());
            String filename = file.getOriginalFilename();
            String suffix = filename.substring(filename.lastIndexOf(".") + 1);
            File file1 = new File();
            file1.setFileId(path);
            file1.setFileName(file.getOriginalFilename());
            file1.setParentDir(fileId);
            file1.setSize(file.getSize());
            file1.setUrl("192.168.88.129:9000/" + file.getOriginalFilename());
            file1.setType(suffix);
            if (TypeArray.getArrayList().contains(suffix)) {
                file1.setAttribute(0);
            } else if (TypeArray.getArrayList1().contains(suffix)) {
                file1.setAttribute(1);
            } else if (TypeArray.getArrayList2().contains(suffix)) {
                file1.setAttribute(2);
            } else if (TypeArray.getArrayList3().contains(suffix)) {
                file1.setAttribute(3);
            } else if (TypeArray.getArrayList4().contains(suffix)) {
                file1.setAttribute(4);
            } else {
                file1.setAttribute(5);
            }
            file1.setUserName(userName);
            file1.setSharedStatus(0);
            fileDao.insert(file1);
            return new ResponseData("6666", "请求成功");
        } catch (IOException e) {
            System.out.println(e);
            return new ResponseData("9999", "网络异常");
        }
    }

    /**
     * 下载文件
     *
     * @param fileId
     * @return
     * @throws Exception
     */
    @Override
    public ResponseEntity<byte[]> download(String fileId) throws Exception {
        String path = fileId;
        String fileName = path.split("//")[path.split("//").length - 1];
        InputStream inputStream = this.hdfsUtil.download(path);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment",
                URLEncoder.encode(fileName, "UTF-8"));
        System.out.println("下载成功");
        return new ResponseEntity<>(IOUtils.toByteArray(inputStream), headers, HttpStatus.OK);
    }

    /**
     * 创建文件夹
     *
     * @param
     * @return
     */
    @Override
    public ResponseData mkdir(String parentDir, String fileName) {
        try {
            Jedis resource = jedisPool.getResource();
            String userName = resource.get("username");
            hdfsUtil.mkdirs(parentDir + "/" + fileName);
          /*  //上级目录
            String prefix= fileId.substring(0,fileId.lastIndexOf("/"));
            //这里是获取"/"符号的位置
            Matcher slashMatcher = Pattern.compile("/").matcher(fileId);
            int mIdx = 0;
            while(slashMatcher.find()) {
                mIdx++;
                //当"/"符号第二次出现的位置
                if(mIdx == 2){
                    break;
                }
            }
            int start = slashMatcher.start();
            String suffix = fileId.substring(start+1);*/
            File file = new File();
            file.setUserName(userName);
            file.setFileId(parentDir + "/" + fileName);
            file.setType("dir");
            file.setParentDir(parentDir);
            file.setUrl("192.168.88.129:9000/" + fileName);
            file.setFileName(fileName);
            file.setSharedStatus(0);
            file.setSize(-1l);
            file.setAttribute(5);
            //size应该设置为当前目录下所有文件大小之和
            fileDao.insert(file);
            return new ResponseData("6666", "请求成功", file);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseData("9999", "网络异常");
        }

    }

    /**
     * 加入回收站
     *
     * @param id
     * @return
     */
    @Override
    public ResponseData addRecycle(Long id) {

        try {
            recycleRec(id, 0);
            return new ResponseData("6666", "请求成功");
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseData("9999", "网络异常");
        }

    }

    /**
     * 移出回收站
     *
     * @param id
     * @return
     */
    @Override
    public ResponseData rmRecycle(Long id) {
        try {

            recycleRm(id, 1);
            return new ResponseData("6666", "请求成功");
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseData("9999", "网络异常");
        }
    }

    /**
     * 加入收藏
     *
     * @param id
     * @return
     */
    @Override
    public ResponseData addCollect(Long id) {
        try {
            collect(id, 1);
            return new ResponseData("6666", "请求成功");
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseData("9999", "网络异常");
        }
    }

    /**
     * 取消收藏
     *
     * @param id
     * @return
     */
    @Override
    public ResponseData rmCollect(Long id) {
        try {
            collectRm(id, 0);
            return new ResponseData("6666", "请求成功");
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseData("9999", "网络异常");
        }
    }


    /**
     * 根目录下所有文件和文件夹
     *
     * @return
     */
    @Override
    public ResponseData index1() {

        try {
            Jedis resource = jedisPool.getResource();
            String userName = resource.get("username");
            List<File> files = fileDao.queryByPDir(userName, "/" + userName);
            files.sort(new Comparator<File>() {
                @Override
                public int compare(File file1, File file2) {
                    return (int) (file1.getSize()-file2.getSize());
                }
            });
            return new ResponseData("6666", "请求成功", files);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseData("9999", "网络异常");
        }
    }

    /**
     * 回收站展示
     *
     * @return
     */
    @Override
    public ResponseData recycle() {
        try {
            Jedis resource = jedisPool.getResource();
            String userName = resource.get("username");
            List<File> files = fileDao.queryRecycle(userName);
            return new ResponseData("6666", "请求成功", files);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseData("9999", "网络异常");
        }

    }

    /**
     * 收藏展示
     *
     * @return
     */
    @Override
    public ResponseData collect() {
        try {
            Jedis resource = jedisPool.getResource();
            String userName = resource.get("username");
            List<File> files = fileDao.queryCollect(userName);
            return new ResponseData("6666", "请求成功", files);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseData("9999", "网络异常");
        }

    }

    /**
     * 分模块展示
     *
     * @param attribute
     * @return
     */
    @Override
    public ResponseData module(Integer attribute) {
        try {
            Jedis resource = jedisPool.getResource();
            String userName = resource.get("username");
            List<File> files = fileDao.queryByAttr(userName, attribute);
            return new ResponseData("6666", "请求成功", files);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseData("9999", "网络异常");
        }
    }

    /**
     * 重命名
     *
     * @param fileid
     * @param filename
     * @return
     */
    @Override
    public ResponseData rename(String fileid, String filename) {
        try {
            String suffix = fileid.substring(fileid.lastIndexOf("."));
            String prefix = fileid.substring(0, fileid.lastIndexOf("/"));
            String newStr = prefix + "/" + filename + suffix;
            hdfsUtil.rename(fileid, newStr);
            File file = fileDao.queryByFileId(fileid);
            file.setFileName(filename + suffix);
            file.setFileId(newStr);
            fileDao.update(file);
            return new ResponseData("6666", "请求成功");
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseData("9999", "网络异常");
        }


    }

    /**
     * 彻底删除
     *
     * @param id
     * @return
     */
    @Override
    public ResponseData delete(Long id) {
        try {
            File file = fileDao.queryById(id);
            String fileId = file.getFileId();
            hdfsUtil.delete(fileId);
            deleteF(id);
            return new ResponseData("6666", "请求成功");
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseData("9999", "网络异常");
        }
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @Override
    public ResponseData deleteA(List<Long> ids) {
        try {
            for (Long value : ids) {
                hdfsUtil.delete(fileDao.queryById(value).getFileId());
                deleteF(value);
            }
            return new ResponseData("6666", "请求成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData("9999", "网络异常");
        }
    }

    /**
     * 批量加入回收站
     *
     * @param ids
     * @return
     */
    @Override
    public ResponseData recycleA(List<Long> ids) {
        try {
            for (Long value : ids) {
                recycleRec(value, 0);
            }
            return new ResponseData("6666", "请求成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData("9999", "网络异常");
        }


    }


    /**
     * 查询下一层
     *
     * @param fileid
     * @return
     */
    @Override
    public ResponseData nextLayer(String fileid) {
        try {
            Jedis resource = jedisPool.getResource();
            String userName = resource.get("username");
            List<File> files = fileDao.queryByPDir(userName, fileid);
            return new ResponseData("6666", "请求成功", files);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData("9999", "网络异常");
        }
    }

    @Override
    public ResponseData removeAll() {
        try {
            Jedis resource = jedisPool.getResource();
            String userName = resource.get("username");
            List<File> files = fileDao.queryRecycle(userName);
            for (File value : files) {
                hdfsUtil.delete(value.getFileId());
            }
            fileDao.deleteAll(userName);
            return new ResponseData("6666", "请求成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData("9999", "网络异常");
        }

    }

    //递归函数 递归把文件夹下的所有文件和文件夹加入回收站
    public void recycleRec(Long id, Integer status) {
        Jedis resource = jedisPool.getResource();
        String userName = resource.get("username");
        File file = fileDao.queryById(id);
        fileDao.updateRec(id, status);
        if (file.getType().equals("dir")) {
            //查询文件夹下的所有文件
            List<File> files = fileDao.queryByPDir(userName, file.getFileId());
            if (files != null) {
                for (File value : files) {
                    recycleRec(value.getId(), 2);
                }
            }

        }

    }

    //递归函数 递归把文件夹下的所有文件和文件夹移出回收站
    public void recycleRm(Long id, Integer status) {
        Jedis resource = jedisPool.getResource();
        String userName = resource.get("username");
        File file = fileDao.queryById(id);
        fileDao.updateRec(id, status);
        if (file.getType().equals("dir")) {
            //查询文件夹下的所有文件
            List<File> files = fileDao.queryByPDir2(userName, file.getFileId());
            if (files.size() != 0) {
                for (File value : files) {
                    recycleRec(value.getId(), status);
                }
            }

        }

    }


    //递归函数 递归把文件夹下的所有文件和文件夹加入收藏
    public void collect(Long id, Integer status) {
        Jedis resource = jedisPool.getResource();
        String userName = resource.get("username");
        File file = fileDao.queryById(id);
        fileDao.updateCol(id, status);
        if (file.getType().equals("dir")) {
            //查询文件夹下的所有文件
            List<File> files = fileDao.queryByPDir(userName, file.getFileId());
            if (files != null) {
                for (File value : files) {
                    recycleRec(value.getId(), 2);
                }
            }

        }

    }

    //递归函数 递归把文件夹下的所有文件和文件夹移除收藏
    public void collectRm(Long id, Integer status) {
        Jedis resource = jedisPool.getResource();
        String userName = resource.get("username");
        File file = fileDao.queryById(id);
        fileDao.updateCol(id, status);
        if (file.getType().equals("dir")) {
            //查询文件夹下的所有文件
            List<File> files = fileDao.queryByPDir(userName, file.getFileId());
            if (files != null) {
                for (File value : files) {
                    recycleRec(value.getId(), status);
                }
            }

        }

    }

    /**
     * 递归删除所有文件
     *
     * @param id
     */
    public void deleteF(Long id) {
        Jedis resource = jedisPool.getResource();
        String userName = resource.get("username");
        File file = fileDao.queryById(id);
        fileDao.deleteById(id);
        if (file.getType().equals("dir")) {
            //查询文件夹下的所有文件
            List<File> files = fileDao.queryByPDir(userName, file.getFileId());
            if (files != null) {
                for (File value : files) {
                    deleteF(value.getId());
                }
            }

        }

    }

    /**
     * 递归移动文件
     *

     */
    public void removeA(String fileId,String parentDir) {
        Jedis resource = jedisPool.getResource();
        String userName = resource.get("username");
        File file = fileDao.queryByFileId(fileId);
        System.out.println(fileId);
        System.out.println(parentDir);
        if (file.getType().equals("dir")) {
            //查询文件夹下的所有文件
            List<File> files = fileDao.queryByPDir(userName, file.getFileId());
            file.setFileId(parentDir+"/"+file.getFileName());
            file.setParentDir(parentDir);
            fileDao.update(file);
            if (files != null) {
                for (File value : files) {
                    removeA(value.getFileId(),file.getFileId());
                }
            }

        }else {
            file.setFileId(parentDir+"/"+file.getFileName());
            file.setParentDir(parentDir);
            fileDao.update(file);
        }

    }


    @Override
    public ResponseData uploads(MultipartFile[] file, String couldpath) {
        try {
            Jedis resource = jedisPool.getResource();
            String userName = resource.get("username");
            for (int i = 0; i < file.length; i++) {
                String path = "/" + userName + "/" + file[i].getOriginalFilename();
                InputStream in = file[i].getInputStream();
                System.out.println(file[i].getOriginalFilename());
                //上传路径
                String cloudPath = "/duyuankun/" + file[i].getOriginalFilename();
                boolean upload = hdfsUtil.upload(cloudPath, in);
                in.close();
                if (upload) {
                    String filename = file[i].getOriginalFilename();
                    String suffix = filename.substring(filename.lastIndexOf(".") + 1);
                    File file1 = new File();
                    file1.setFileId(path);
                    file1.setFileName(file[i].getOriginalFilename());
                    file1.setParentDir("/" + userName);
                    file1.setSize(file[i].getSize());
                    file1.setUrl("192.168.37.129:" + file[i].getOriginalFilename());
                    file1.setType(suffix);
                    file1.setUserName("june1234");
                    file1.setSharedStatus(0);
                    fileDao.insert(file1);
                    return new ResponseData("6666", "请求成功");
                }
            }
            return new ResponseData("7777", "云端存储失败");
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseData("7777", "云端存储失败");
        }

    }


    /**
     * 大的文件夹进行压缩下载
     * @param couldpath
     * @return
     * @throws IOException
     */
    @Override
    public ResponseEntity<byte[]> downloadFolder(String couldpath) {
        ResponseEntity<byte[]> response = null;
        try {
            response = null;
//        couldpath = "/duyuankun";
            HttpHeaders headers = new HttpHeaders();
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Content-Disposition", "attachment; filename=" + couldpath + ".zip");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            headers.add("Content-Language", "UTF-8");
            ByteArrayOutputStream zos = (ByteArrayOutputStream) hdfsUtil.down2("hdfs://192.168.37.129:9000" + couldpath);
            byte[] out = zos.toByteArray();
            zos.close();
            response = new ResponseEntity<>(out, headers, HttpStatus.OK);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return response;
        }
    }
    @Override
    public ResponseData remove(String path, String newpath)  {
        try {

            hdfsUtil.Move(path, newpath);
            System.out.println(hdfsUtil.Move(path,newpath));
            if(hdfsUtil.Move(path,newpath)){
                  removeA(path,newpath);
                return new ResponseData("6666","成功");
            }
            return new ResponseData("7777","数据库更新错误");
        } catch (Exception e) {
            System.out.println("----error:{}----" + e.getMessage());
        }
        return new ResponseData("9999","失败");
    }
    @Override
    public ResponseData getalldir(){
        try {
            Jedis resource = jedisPool.getResource();
            String userName = resource.get("username");
//        String username = "june1234";
            List<File> fileList = fileDao.queryAllDir();
//        System.out.println(fileList);
            List<Node> nodes = FolderTree.getFolderTree("/"+userName,fileList);
            System.out.println(nodes);
            return new ResponseData("6666","请求成功",nodes);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData("9999", "网络异常");
        }

    }

    @Override
    public ResponseData fuzzyQuery(String name) {
        try {
            List<File> files = fileDao.fuzzyQuery(name);
            return new ResponseData("6666","请求成功",files);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData("9999", "网络异常");
        }


    }
}

