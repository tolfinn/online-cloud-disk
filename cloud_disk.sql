/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 8.0.21 : Database - cloud_disk
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`cloud_disk` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `cloud_disk`;

/*Table structure for table `file` */

DROP TABLE IF EXISTS `file`;

CREATE TABLE `file` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `file_id` varchar(256) NOT NULL COMMENT '文件id:/group1/M00/00/00/xxx.png',
  `url` varchar(512) NOT NULL COMMENT '文件url 192.168.52.139:80/group1/M00/00/00/xxx.png',
  `size` bigint DEFAULT '0' COMMENT '文件大小, 以字节为单位',
  `type` varchar(32) DEFAULT '' COMMENT '文件类型： png, zip, mp4……',
  `user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件所属用户',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '文件创建时间',
  `file_name` varchar(128) DEFAULT NULL COMMENT '文件名字',
  `shared_status` tinyint DEFAULT NULL COMMENT '共享状态, 0为没有共享， 1为共享',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `iscollect` tinyint(1) DEFAULT '0' COMMENT '文件是否收藏 0没有收藏',
  `recycle` tinyint(1) DEFAULT '1' COMMENT '0进回收站',
  `parent_dir` varchar(256) NOT NULL COMMENT '上级目录',
  `attribute` tinyint DEFAULT '4' COMMENT '图片0，视频1，文档2，音频3，其他4',
  PRIMARY KEY (`id`),
  UNIQUE KEY `file_id` (`file_id`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8 COMMENT='文件信息表';

/*Data for the table `file` */

insert  into `file`(`id`,`file_id`,`url`,`size`,`type`,`user_name`,`create_time`,`file_name`,`shared_status`,`update_time`,`iscollect`,`recycle`,`parent_dir`,`attribute`) values (9,'/june1234/999.txt','192.168.88.129:555.txt',6,'txt','june1234','2022-06-17 10:54:43','999.txt',0,'2022-06-21 15:19:28',0,1,'/june1234',2),(11,'/june1234/dw/aaa/001.jpg','192.168.88.129:001.jpg',47642,'jpg','june1234','2022-06-17 21:03:49','001.jpg',0,'2022-06-24 11:33:41',1,2,'/june1234/dw/aaa',0),(13,'/june1234/QQ短视频20220617210843.mp4','192.168.88.129:QQ短视频20220617210843.mp4',1604159,'mp4','june1234','2022-06-17 21:13:31','QQ短视频20220617210843.mp4',0,'2022-06-20 15:07:57',1,1,'/june1234',1),(34,'/june1234/77c6a7efce1b9d168781be0ff0deb48f8c54641d.jpg','192.168.88.129:9000/77c6a7efce1b9d168781be0ff0deb48f8c54641d.jpg',4734,'jpg','june1234','2022-06-20 14:49:24','77c6a7efce1b9d168781be0ff0deb48f8c54641d.jpg',0,'2022-06-21 09:56:48',1,1,'/june1234',0),(35,'/june1234/53f3e57b-f48b-4e41-8a01-298d9999f7a9.png','192.168.88.129:9000/53f3e57b-f48b-4e41-8a01-298d9999f7a9.png',210888,'png','june1234','2022-06-20 14:50:34','53f3e57b-f48b-4e41-8a01-298d9999f7a9.png',0,'2022-06-23 19:32:28',1,1,'/june1234',0),(36,'/june1234/dw/aaa/QQ图片20200122145839.jpg','192.168.88.129:9000/QQ图片20200122145839.jpg',13746,'jpg','june1234','2022-06-20 14:51:58','QQ图片20200122145839.jpg',0,'2022-06-24 11:33:41',1,2,'/june1234/dw/aaa',0),(51,'/june1234/dw/test1 - 副本 (2).txt','192.168.88.129:9000/test1 - 副本 (2).txt',0,'txt','june1234','2022-06-21 10:41:13','test1 - 副本 (2).txt',0,'2022-06-21 16:16:53',0,0,'/june1234/dw',2),(52,'/june1234/dw/aaa/12121.txt','192.168.88.129:9000/12121.txt',0,'txt','june1234','2022-06-21 10:41:55','12121.txt',0,'2022-06-21 16:16:53',0,0,'/june1234/dw/aaa',2),(53,'/june1234/test005.txt','192.168.88.129:9000/test1 - 副本 (2).txt',0,'txt','june1234','2022-06-21 10:41:55','test005.txt',0,'2022-06-21 11:05:59',0,1,'/june1234',2),(54,'/june1234/test1.txt','192.168.88.129:9000/test1.txt',0,'txt','june1234','2022-06-21 10:42:08','test1.txt',0,'2022-06-21 11:05:59',0,1,'/june1234',2),(55,'/june1234/test1 - 副本.txt','192.168.88.129:9000/test1 - 副本.txt',0,'txt','june1234','2022-06-21 10:42:08','test1 - 副本.txt',0,'2022-06-21 16:16:53',0,0,'/june1234',2),(56,'/june1234/dw/aaa','192.168.88.129:9000/aaa',-1,'dir','june1234','2022-06-21 10:43:01','aaa',0,'2022-06-24 11:33:41',0,2,'/june1234/dw',5),(59,'/june1234/dw/bbb','192.168.88.129:9000/bbb',-1,'dir','june1234','2022-06-21 10:57:20','bbb',0,'2022-06-24 11:33:41',0,2,'/june1234/dw',5),(60,'/june1234/dw/12121.txt','192.168.88.129:9000/12121.txt',0,'txt','june1234','2022-06-21 10:58:20','12121.txt',0,'2022-06-24 11:33:41',0,2,'/june1234/dw',2),(61,'/june1234/test212.txt','192.168.88.129:9000/test212.txt',0,'txt','june1234','2022-06-21 11:04:45','test212.txt',0,'2022-06-21 11:04:45',0,1,'/june1234',2),(62,'/june1234/au.mp3','192.168.88.129:9000/au.mp3',0,'mp3','june1234','2022-06-21 14:26:58','au.mp3',0,'2022-06-21 14:26:58',0,1,'/june1234',3),(63,'/june1234/hello.xc','192.168.88.129:9000/hello.xc',0,'xc','june1234','2022-06-21 14:54:50','hello.xc',0,'2022-06-21 14:54:50',0,1,'/june1234',5),(64,'/june1234/1.md','192.168.88.129:9000/1.md',0,'md','june1234','2022-06-21 14:55:18','1.md',0,'2022-06-21 14:55:18',0,1,'/june1234',5),(65,'/june1234/test2.doc','192.168.88.129:9000/test2.doc',0,'doc','june1234','2022-06-21 15:25:13','test2.doc',0,'2022-06-21 15:25:13',0,1,'/june1234',2),(66,'/june1234/test5.xlsx','192.168.88.129:9000/test5.xlsx',6610,'xlsx','june1234','2022-06-21 15:25:19','test5.xlsx',0,'2022-06-21 15:25:19',0,1,'/june1234',5),(67,'/june1234/test3.ppt','192.168.88.129:9000/test3.ppt',0,'ppt','june1234','2022-06-21 15:25:23','test3.ppt',0,'2022-06-21 15:25:23',0,1,'/june1234',5),(69,'/june1234/test1.rar','192.168.88.129:9000/test1.rar',0,'rar','june1234','2022-06-21 15:33:41','test1.rar',0,'2022-06-21 15:33:41',0,1,'/june1234',4),(70,'/june1234/test2.7z','192.168.88.129:9000/test2.7z',22,'7z','june1234','2022-06-21 15:33:44','test2.7z',0,'2022-06-21 15:33:44',0,1,'/june1234',4),(71,'/june1234/sz','192.168.88.129:9000/sz',-1,'dir','june1234','2022-06-21 15:43:05','sz',0,'2022-06-23 18:48:55',1,1,'/june1234',5),(72,'/june1234/dyk','192.168.88.129:9000/dyk',-1,'dir','june1234','2022-06-22 13:38:42','dyk',0,'2022-06-22 13:38:42',0,1,'/june1234',5),(73,'/june1234/sz/4.txt','192.168.88.129:9000/4.txt',0,'txt','june1234','2022-06-23 18:45:36','4.txt',0,'2022-06-23 18:48:55',0,2,'/june1234/sz',2),(74,'/june1234/sz/wallhaven-ym1wp7.jpg','192.168.88.129:9000/wallhaven-ym1wp7.jpg',558155,'jpg','june1234','2022-06-23 18:47:14','wallhaven-ym1wp7.jpg',0,'2022-06-23 18:48:55',0,2,'/june1234/sz',0),(78,'/june1234/实验四：插值法实验报告要求.docx','192.168.88.129:9000/实验四：插值法实验报告要求.docx',14458,'docx','june1234','2022-06-23 19:08:23','实验四：插值法实验报告要求.docx',0,'2022-06-23 20:13:27',0,1,'/june1234',2),(79,'/june1234/实验二：矩阵分解实验报告要求.docx','192.168.88.129:9000/实验二：矩阵分解实验报告要求.docx',15099,'docx','june1234','2022-06-23 19:26:58','实验二：矩阵分解实验报告要求.docx',0,'2022-06-23 19:30:10',0,0,'/june1234',2),(82,'/june1234/haokangde','192.168.88.129:9000/haokangde',-1,'dir','june1234','2022-06-23 19:39:44','haokangde',0,'2022-06-23 19:39:44',0,1,'/june1234',5),(84,'/june1234/haokangde/大作业报告要求和封面.docx','192.168.88.129:9000/大作业报告要求和封面.docx',197087,'docx','june1234','2022-06-23 19:58:20','大作业报告要求和封面.docx',0,'2022-06-23 19:58:20',0,1,'/june1234/haokangde',2);

/*Table structure for table `user_info` */

DROP TABLE IF EXISTS `user_info`;

CREATE TABLE `user_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户序号，自动递增，主键',
  `user_name` varchar(32) NOT NULL DEFAULT '' COMMENT '用户名称',
  `nick_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '''昵称''' COMMENT '用户昵称',
  `password` varchar(32) NOT NULL DEFAULT '' COMMENT '密码',
  `phone` varchar(16) NOT NULL DEFAULT '' COMMENT '手机号码',
  `email` varchar(64) DEFAULT '' COMMENT '邮箱',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '时间',
  `level` tinyint NOT NULL DEFAULT '1' COMMENT '0管理员 1用户',
  `status` tinyint DEFAULT '1' COMMENT '用户访问权限，1可访问',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_nick_name` (`nick_name`),
  UNIQUE KEY `uq_user_name` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COMMENT='用户信息表';

/*Data for the table `user_info` */

insert  into `user_info`(`id`,`user_name`,`nick_name`,`password`,`phone`,`email`,`create_time`,`level`,`status`) values (16,'june1234','june','1d9a605be7bb292cf7a03452f12d7f15','17798308788','997717421@qq.com','2022-06-14 15:12:41',1,1),(17,'Tolfin','326455d2df5d44f9a70dddcd5d00a2d5','1d9a605be7bb292cf7a03452f12d7f15','15971234567','9977121@qq.com','2022-06-21 16:12:42',1,1),(18,'admin','\'昵称\'','1d9a605be7bb292cf7a03452f12d7f15','15971234567','9977121@qq.com','2022-06-23 15:58:55',0,1),(20,'user1','123','1d9a605be7bb292cf7a03452f12d7f15','15971234567','997717421@qq.com','2022-06-23 16:01:03',1,1),(21,'user2','02211','1d9a605be7bb292cf7a03452f12d7f15','15971234567','997717421@qq.com','2022-06-23 16:01:03',1,0),(22,'user3','156','1d9a605be7bb292cf7a03452f12d7f15','15971234567','997717421@qq.com','2022-06-23 16:01:03',1,0),(23,'user4','656','1d9a605be7bb292cf7a03452f12d7f15','15971234567','997717421@qq.com','2022-06-23 16:01:03',1,1),(26,'june0000','27ceacb7294b4539a6de65ddd53f6041','1d9a605be7bb292cf7a03452f12d7f15','15971234561','998712312@qq.com','2022-06-23 16:53:43',1,1),(27,'june000','18f0896306044f0490abd339fa842816','1d9a605be7bb292cf7a03452f12d7f15','15971234123','997112312@qq.com','2022-06-23 16:54:40',1,1),(29,'dyk123','d3134ffb95a541269d9db828350d948e','1d9a605be7bb292cf7a03452f12d7f15','17677117764','123456@qq.com','2022-06-23 18:52:37',1,1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
