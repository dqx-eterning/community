/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.17-log : Database - community
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`community` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `community`;

/*Table structure for table `comment` */

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) NOT NULL,
  `type` int(11) NOT NULL,
  `commentator` bigint(20) NOT NULL,
  `gmt_create` bigint(20) NOT NULL,
  `gmt_modified` bigint(20) NOT NULL,
  `like_count` bigint(20) DEFAULT '0',
  `content` varchar(1024) DEFAULT NULL,
  `comment_count` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;

/*Data for the table `comment` */

insert  into `comment`(`id`,`parent_id`,`type`,`commentator`,`gmt_create`,`gmt_modified`,`like_count`,`content`,`comment_count`) values (38,37,1,10,1600257285662,1600257285662,0,'12',0),(39,34,2,10,1600257415633,1600257415633,0,'111',NULL),(40,39,1,10,1600257647661,1600257647661,0,'需要了解JVM',1),(41,40,2,10,1600257662922,1600257662922,0,'还需要了解高并发',NULL),(42,39,1,10,1600257706769,1600257706769,0,'需要了解mysql引擎',0),(43,40,1,10,1600260838018,1600260838018,0,'你好',1),(44,43,2,11,1600260878611,1600260878611,0,'你也好啊',NULL),(45,40,1,11,1600260891344,1600260891344,0,'学习',0),(46,39,1,11,1600260973292,1600260973292,0,'需要了解数据结构',1),(47,46,2,10,1600261060319,1600261060319,0,'好的',NULL),(48,39,1,10,1600261083408,1600261083408,0,'需要？',0),(49,35,1,10,1600261402904,1600261402904,0,'springboot是啥',0),(50,38,1,10,1600303728526,1600303728526,0,'自己百度',0),(51,38,1,10,1600303801363,1600303801363,0,'csdn上有',0),(52,41,1,10,1600308864789,1600308864789,0,'正确！',0);

/*Table structure for table `notification` */

DROP TABLE IF EXISTS `notification`;

CREATE TABLE `notification` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `notifier` bigint(20) NOT NULL,
  `receiver` bigint(20) NOT NULL,
  `outerid` bigint(20) NOT NULL,
  `type` int(11) NOT NULL,
  `gmt_create` bigint(20) NOT NULL,
  `status` int(11) NOT NULL DEFAULT '0',
  `notifier_name` varchar(100) DEFAULT NULL,
  `outer_title` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

/*Data for the table `notification` */

insert  into `notification`(`id`,`notifier`,`receiver`,`outerid`,`type`,`gmt_create`,`status`,`notifier_name`,`outer_title`) values (3,10,10,39,1,1600257647672,1,'QXgnaD','java找工作需要了解什么？'),(4,10,10,39,2,1600257662932,1,'QXgnaD','java找工作需要了解什么？'),(5,10,10,39,1,1600257706780,1,'QXgnaD','java找工作需要了解什么？'),(6,10,11,40,1,1600260838027,1,'QXgnaD','我是舍长'),(7,11,10,40,2,1600260878623,1,NULL,'我是舍长'),(8,11,11,40,1,1600260891353,1,NULL,'我是舍长'),(9,11,10,39,1,1600260973294,1,NULL,'java找工作需要了解什么？'),(10,10,11,39,2,1600261060328,0,'QXgnaD','java找工作需要了解什么？'),(11,10,10,39,1,1600261083410,1,'QXgnaD','java找工作需要了解什么？'),(12,10,10,35,1,1600261402919,1,'QXgnaD','Spring Boot'),(13,10,10,38,1,1600303728547,1,'QXgnaD','idea咋调试？'),(14,10,10,38,1,1600303801367,1,'QXgnaD','idea咋调试？'),(15,10,10,41,1,1600308864808,1,'QXgnaD','这段代码？');

/*Table structure for table `question` */

DROP TABLE IF EXISTS `question`;

CREATE TABLE `question` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL,
  `description` text,
  `gmt_create` bigint(20) DEFAULT NULL,
  `gmt_modified` bigint(20) DEFAULT NULL,
  `creator` bigint(20) NOT NULL,
  `comment_count` int(11) DEFAULT '0',
  `view_count` int(11) DEFAULT '0',
  `like_count` int(11) DEFAULT '0',
  `tag` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;

/*Data for the table `question` */

insert  into `question`(`id`,`title`,`description`,`gmt_create`,`gmt_modified`,`creator`,`comment_count`,`view_count`,`like_count`,`tag`) values (33,'mybatis generation 11','太难了，！！！！',1600057137350,1600224928076,10,0,11,0,'Spring boot,mybatis,Java'),(34,'mybatis','mybatis 咋学',1600141141431,1600224918423,10,0,20,0,'Spring,mybatis,Java'),(35,'Spring Boot','Spring Boot',1600221118541,1600221152677,10,1,33,0,'Spring Boot,Spring,Java'),(36,'创建第一个springboot项目','咋创建？',1600235006227,1600235161103,10,0,13,0,'java,spring'),(38,'idea咋调试？','idea的使用',1600257587635,1600257587635,10,2,10,0,'java'),(39,'java找工作需要了解什么？','找工作',1600257625203,1600257625203,10,4,23,0,'java,linux,mysql,spring'),(40,'我是舍长','你好',1600260816240,1600260816240,11,2,9,0,'java'),(41,'这段代码？','```java\r\npublic class HelloWorld {\r\n    public static void main(String[] args) {\r\n        System.out.println(\"Hello World\");\r\n    }\r\n}\r\n```\r\n#### 这样正确么？',1600308085241,1600308850767,10,1,16,0,'java'),(42,'图片测试','![](http://eterning.cn-bj.ufileos.com/61bce052-e2ba-4410-887c-f5a5fbd45322.png?UCloudPublicKey=TOKEN_ca3df377-ca07-4a78-881a-31209d30c844&Signature=tX0bnF2KKPedKjYsrDde0C8z4ug%3D&Expires=1915691197)',1600331328256,1600331328256,10,0,5,0,'java');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account_id` varchar(100) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `token` char(36) DEFAULT NULL,
  `gmt_create` bigint(20) DEFAULT NULL,
  `gmt_modified` bigint(20) DEFAULT NULL,
  `bio` varchar(256) DEFAULT NULL,
  `avatar_url` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`id`,`account_id`,`name`,`token`,`gmt_create`,`gmt_modified`,`bio`,`avatar_url`) values (10,'71052450','QXgnaD','727e5210-9a88-405c-8b11-bb0e987b3c79',1599961219151,1600303706464,NULL,'https://avatars1.githubusercontent.com/u/71052450?v=4'),(11,'67615839',NULL,'8fd0ff48-d56b-41b3-af72-631403900c70',1600260758029,1600260758029,NULL,'https://avatars3.githubusercontent.com/u/67615839?v=4');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
