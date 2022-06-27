package com.example.clouddisk.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * 文件信息表(File)实体类
 *
 * @author makejava
 * @since 2022-06-16 11:33:19
 */
public class File implements Serializable {
    private static final long serialVersionUID = 551659522789052367L;

    private Long id;
    /**
     * 文件id:/group1/M00/00/00/xxx.png
     */
    private String fileId;
    /**
     * 文件url 192.168.52.139:80/group1/M00/00/00/xxx.png
     */
    private String url;
    /**
     * 文件大小, 以字节为单位
     */
    private Long size;
    /**
     * 文件类型： png, zip, mp4……
     */
    private String type;
    /**
     * 文件创建时间
     */
    private Date createTime;
    /**
     * 文件名字
     */
    private String fileName;
    /**
     * 共享状态, 0为没有共享， 1为共享
     */
    private Integer sharedStatus;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 文件是否收藏 0没有收藏
     */
    private Integer iscollect;
    /**
     * 0进回收站
     */
    private Integer recycle;
    /**
     * 上级目录
     */
    private String parentDir;
    /**
     * 文件所属用户
     */
    private String userName;
    /**
     *文件属于哪种类型，图片0，视频1，文档2，音频3，其他4,文件夹5
     */
    private Integer attribute;

    public Integer getAttribute() {
        return attribute;
    }

    public void setAttribute(Integer attribute) {
        this.attribute = attribute;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getSharedStatus() {
        return sharedStatus;
    }

    public void setSharedStatus(Integer sharedStatus) {
        this.sharedStatus = sharedStatus;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIscollect() {
        return iscollect;
    }

    public void setIscollect(Integer iscollect) {
        this.iscollect = iscollect;
    }

    public Integer getRecycle() {
        return recycle;
    }

    public void setRecycle(Integer recycle) {
        this.recycle = recycle;
    }

    public String getParentDir() {
        return parentDir;
    }

    public void setParentDir(String parentDir) {
        this.parentDir = parentDir;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", fileId='" + fileId + '\'' +
                ", url='" + url + '\'' +
                ", size=" + size +
                ", type='" + type + '\'' +
                ", createTime=" + createTime +
                ", fileName='" + fileName + '\'' +
                ", sharedStatus=" + sharedStatus +
                ", updateTime=" + updateTime +
                ", iscollect=" + iscollect +
                ", recycle=" + recycle +
                ", parentDir='" + parentDir + '\'' +
                ", userName='" + userName + '\'' +
                ", attribute=" + attribute +
                '}';
    }
}

