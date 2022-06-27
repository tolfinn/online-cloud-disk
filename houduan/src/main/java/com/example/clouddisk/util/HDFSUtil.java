package com.example.clouddisk.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class HDFSUtil {

    private String hdfsURI="hdfs://192.168.88.129:9000";

    /**
     * 获取 HDFS 文件系统对象
     */
    public FileSystem getFileSystem() {
        System.setProperty("HADOOP_USER_NAME", "root");
        FileSystem fs = null;
        try {
            fs = FileSystem.get(URI.create(hdfsURI), new Configuration());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return fs;
    }

    /**
     * 创建文件夹
     */
    public boolean mkdirs(String dirs) {
        boolean flag = false;
        try {
            flag = this.getFileSystem().mkdirs(new Path(dirs));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return flag;
    }

    /**
     * 创建文件
     * @param path  /zz/a.txt
     */
    public boolean upload(String path, InputStream input) {
        boolean flag = false;
        FSDataOutputStream fos = null;
        try {
            fos = this.getFileSystem().create(new Path(path));
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = input.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            IOUtils.closeStream(fos);
            flag = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return flag;
    }

    /**
     * 删除文件夹 or 文件
     */
    public boolean delete(String fileName) {
        boolean flag = false;
        try {
            flag = this.getFileSystem().delete(new Path(fileName), true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return flag;
    }

    /**
     * 重命名文件夹 or 文件
     */
    public boolean rename(String oldStr, String newStr) {
        boolean flag = false;
        try {
            flag = this.getFileSystem().rename(new Path(oldStr), new Path(newStr));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return flag ;
    }

    /**
     * 文件是否存在
     */
    public boolean exists(String fileName) {
        boolean flag = false;
        try {
            flag = this.getFileSystem().exists(new Path(fileName));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return flag ;
    }

    /**
     * 下载
     */
    public InputStream download(String fileName) {
        FSDataInputStream fis = null;
        try {
            fis = this.getFileSystem().open(new Path(fileName));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return fis;
    }




    /**
     * 多文件下载
     *
     * @param cloudPath
     * @return
     */
    public OutputStream down2(String cloudPath) {
        // 1获取对象
        ByteArrayOutputStream out = null;
        try {
            Configuration conf = new Configuration();
            FileSystem fs = FileSystem.get(new URI(hdfsURI), conf);
            out = new ByteArrayOutputStream();
            ZipOutputStream zos = new ZipOutputStream(out);
            compress(cloudPath, zos, fs);
            zos.close();
        } catch (IOException e) {
            System.out.println("----error:{}----" + e.getMessage());
        } catch (URISyntaxException e) {
            System.out.println("----error:{}----" + e.getMessage());
        }
        return out;
    }

    /**
     * @param fileId
     * @param zipOutputStream
     * @param fs
     * @throws IOException
     */
    public void compress(String fileId, ZipOutputStream zipOutputStream, FileSystem fs) throws IOException {

        try {
            FileStatus[] fileStatulist = fs.listStatus(new Path(fileId));
            System.out.println("basedir = " + fileId);
            String[] strs = fileId.split("/");
            //lastName代表路径最后的单词
            String lastName = strs[strs.length - 1];

            for (int i = 0; i < fileStatulist.length; i++) {

                String name = fileStatulist[i].getPath().toString();
                name = name.substring(name.indexOf("/" + lastName));

                if (fileStatulist[i].isFile()) {
                    Path path = fileStatulist[i].getPath();
                    FSDataInputStream inputStream = fs.open(path);
                    zipOutputStream.putNextEntry(new ZipEntry(name.substring(1)));
                    IOUtils.copyBytes(inputStream, zipOutputStream, Integer.parseInt("1024"));
                    inputStream.close();
                } else {
                    zipOutputStream.putNextEntry(new ZipEntry(fileStatulist[i].getPath().getName() + "/"));
                    System.out.println("fileStatulist[i].getPath().toString() = " + fileStatulist[i].getPath().toString());
                    compress(fileStatulist[i].getPath().toString(), zipOutputStream, fs);
                }
            }
        } catch (IOException e) {
            System.out.println("----error:{}----" + e.getMessage());
        }
    }

    /**
     * 文件截切（移动）
     * <p>
     * //     * @param IDc 用户ID
     *
     * @param src hdfs源文件完整路径
     * @param dsc hdfs目的文件完整路径
     * @return 返回json格式字符串，包含operation和log两个属性
     * @throws IOException
     * @throws InterruptedException
     * @throws URISyntaxException
     */
    public boolean Move(String src, String dsc) throws IOException, InterruptedException, URISyntaxException {
        // 1获取对象
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI(hdfsURI), conf, "root");

        //判断文件是否存在

        // 2执行更名操作
        fs.rename(new Path(src), new Path(dsc));//前旧后新(新的文件名路径)
        return true;
        // 3 关闭资源
//        return "{\"operation\":\"Move\",\"log\":{\"IDc\":\"" + IDc + "\",\"ticket\":\"null\",\"file\":\"" + "from" + src + " To " + dsc + "\","
//                + "\"operation\":\"Move\",\"time\":\"" + System.currentTimeMillis() + "\"}}";
    }

    /**
     *
     //     * @param file
     //     * @param level
     */
//
//    public void printFile(File file, int level) throws IOException{
//        FileSystem fs = this.getFileSystem();
//
//        File f = new File("");
//        printFile(f, 0);
//        //打印树状结构的层级关系
//        for (int i = 0; i < level; i++) {
//            System.out.print("-");
//        }
//        //输出目录或文件的名称
//        System.out.println(file.getName());
//        if (file.isDirectory()) {//判断File对象是否是目录
//            File[] listFiles = file.listFiles();
//            for (File temp : listFiles) {
//                //自己调用自己
//                printFile(temp, level + 1);
//            }
//        }
//    }
    public void listFileAndFolder() throws IOException {
        //1:获取FileSystem实例
        FileSystem fileSystem = this.getFileSystem();

        //2:调用方法listFiles 获取 /目录下所有的文件信息，，参数true代表递归遍历
        RemoteIterator<LocatedFileStatus> iterator = fileSystem.listFiles(new Path("/"), true);

        //3:遍历迭代器
        while (iterator.hasNext()) {
            LocatedFileStatus fileStatus = iterator.next();
            //获取文件的绝对路径 : hdfs://node01:8020/xxx
            //getPath()方法就是获取绝对路径
            System.out.println(fileStatus.getPath());
            //文件的block信息
        }
    }
    public List<String> getAllFilePath(Path filePath, FileSystem fs) throws FileNotFoundException, IOException {
        fs=this.getFileSystem();
        List<String> fileList = new ArrayList<String>();
        FileStatus[] fileStatus = fs.listStatus(filePath);
        for (FileStatus fileStat : fileStatus) {
            if (fileStat.isDirectory()) {
                fileList.addAll(getAllFilePath(fileStat.getPath(), fs));
            } else {
                fileList.add(fileStat.getPath().toString());
            }
        }
        return fileList;
    }
}






