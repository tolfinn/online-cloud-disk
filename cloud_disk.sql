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
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COMMENT='文件信息表';

/*Data for the table `file` */

insert  into `file`(`id`,`file_id`,`url`,`size`,`type`,`user_name`,`create_time`,`file_name`,`shared_status`,`update_time`,`iscollect`,`recycle`,`parent_dir`,`attribute`) values (1,'/june1234/img','192.168.88.129:9000/1',-1,'dir','june1234','2022-06-16 09:42:58','img',0,'2022-06-20 10:37:42',0,0,'/june1234',4),(2,'/june1234/img/img1','192.168.88.129:9000/2',0,'png','june1234','2022-06-16 09:45:33','img1.png',0,'2022-06-20 10:37:42',0,2,'/june1234/img',0),(3,'/june1234/我的视频','192.168.88.129:9000/3',-1,'dir','june1234','2022-06-16 09:45:33','我的视频',0,'2022-06-20 10:37:42',0,1,'/june1234',4),(4,'/june1234/porn','192.168.88.129:9000/3',0,'mp4','june1234','2022-06-16 09:47:24','porn.mp4',0,'2022-06-20 10:37:42',0,1,'/june1234',1),(5,'/june1234/123.txt','192.168.88.129:123.txt',9,'.txt','june1234','2022-06-16 16:27:12','123.txt',NULL,'2022-06-20 10:37:42',0,1,'/june1234',2),(6,'/june1234/456.txt','192.168.88.129:456.txt',4,'.txt','june1234','2022-06-16 16:35:29','456.txt',NULL,'2022-06-20 10:37:42',0,1,'/june1234',2),(8,'/june1234/789.txt','192.168.88.129:789.txt',15,'txt','june1234','2022-06-16 18:06:41','789.txt',0,'2022-06-20 10:37:42',0,1,'/june1234',2),(9,'/june1234/555.txt','192.168.88.129:555.txt',6,'txt','june1234','2022-06-17 10:54:43','555.txt',0,'2022-06-20 10:37:42',0,1,'/june1234',2),(10,'/june1234/wallhaven-ym1wp7.jpg','192.168.88.129:wallhaven-ym1wp7.jpg',289656,'jpg','june1234','2022-06-17 21:03:01','wallhaven-ym1wp7.jpg',0,'2022-06-20 10:37:42',0,1,'/june1234',0),(11,'/june1234/001.jpg','192.168.88.129:001.jpg',47642,'jpg','june1234','2022-06-17 21:03:49','001.jpg',0,'2022-06-20 10:37:42',0,1,'/june1234',0),(12,'/june1234/12.txt','192.168.88.129:12.txt',0,'txt','june1234','2022-06-17 21:07:01','12.txt',0,'2022-06-20 10:37:42',0,1,'/june1234',2),(13,'/june1234/QQ短视频20220617210843.mp4','192.168.88.129:QQ短视频20220617210843.mp4',1604159,'mp4','june1234','2022-06-17 21:13:31','QQ短视频20220617210843.mp4',0,'2022-06-20 10:37:42',0,1,'/june1234',1),(14,'/june1234/1212121.mp4','192.168.88.129:1212121.mp4',1604159,'mp4','june1234','2022-06-17 21:15:19','1212121.mp4',0,'2022-06-20 10:37:42',0,1,'/june1234',1),(15,'/june1234/333.mp4','192.168.88.129:333.mp4',1604159,'mp4','june1234','2022-06-17 21:24:03','333.mp4',0,'2022-06-20 10:37:42',0,1,'/june1234',1),(16,'/june1234/222.mp4','192.168.88.129:222.mp4',1604159,'mp4','june1234','2022-06-17 21:24:57','222.mp4',0,'2022-06-20 10:37:42',0,1,'/june1234',1),(18,'/june1234/wallhaven-g8olr7.jpg','192.168.88.129:wallhaven-g8olr7.jpg',221218,'jpg','june1234','2022-06-17 21:25:56','wallhaven-g8olr7.jpg',0,'2022-06-20 10:37:42',0,1,'/june1234',0),(25,'/june1234/3.txt','192.168.88.129:3.txt',0,'txt','june1234','2022-06-17 21:33:58','3.txt',0,'2022-06-20 10:37:42',0,1,'/june1234',2),(27,'/june1234/4.txt','192.168.88.129:4.txt',0,'txt','june1234','2022-06-17 21:34:58','4.txt',0,'2022-06-20 10:37:42',0,1,'/june1234',2),(28,'/june1234/img/img2','192.168.88.129:9000/4',-1,'dir','june1234','2022-06-19 13:55:46','img2',0,'2022-06-20 10:37:42',0,2,'/june1234/img',4),(29,'/june1234/img/img2/test1.jpg','192.168.88.129:9000/5',0,'jpg','june1234','2022-06-19 13:55:46','test1.jpg',0,'2022-06-20 10:37:42',0,2,'/june1234/img/img2',0);

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
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='用户信息表';

/*Data for the table `user_info` */

insert  into `user_info`(`id`,`user_name`,`nick_name`,`password`,`phone`,`email`,`create_time`,`level`,`status`) values (14,'june123','\'昵称\'','047764f448b315ae365a7e1c0093dcf0','17798308708','3308483053@qq.com','2022-06-14 14:45:53',1,1),(16,'june1234','ca112123fb32438b96a2ed859df5f643','b54391fe8820d637c25908b264e99a37','17798308708','3308483053@qq.com','2022-06-14 15:12:41',1,1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
