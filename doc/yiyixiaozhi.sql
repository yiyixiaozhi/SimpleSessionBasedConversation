/*
SQLyog Community Edition- MySQL GUI v8.03 
MySQL - 5.7.9 : Database - yiyixiaozhi
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/`yiyixiaozhi` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `yiyixiaozhi`;

/*Table structure for table `t_operation` */

DROP TABLE IF EXISTS `t_operation`;

CREATE TABLE `t_operation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `operation` varchar(20) NOT NULL COMMENT '操作状态',
  `operation_help` varchar(150) DEFAULT NULL COMMENT '操作状态帮助',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

/*Data for the table `t_operation` */

insert  into `t_operation`(`id`,`operation`,`operation_help`) values (1,'新增商品','请输入要创建的商品，例如：\\n方便面'),(2,'删除商品','请输入要删除的商品编号，前面加d，例如：\\nd\\n1'),(3,'修改商品','请输入待修改的商品{商品编号+商品名称}，例如：\\n1\\n方便面'),(4,'新增进货记录','请输入要新增的进货记录{商品编号+进货总价+进货数量+客户名称}，例如：\\n1\\n10\\n10\\n张三'),(5,'删除进货记录','请输入要删除的进货记录编号，前面加d，例如：\\nd\\n1'),(6,'修改进货记录','请输入待修改的进货记录{进货记录编号+进货总价+进货数量+客户名称}，例如：\\n1\\n10.5\\n10\\n张三'),(7,'新增销售记录','请输入待添加销售记录{商品编号+销售总价+销售数量+客户名称}，例如：\\n1\\n10.5\\n10\\n张三'),(8,'删除销售记录','请输入要删除的销售记录编号，前面加d，例如：\\nd\\n1'),(9,'修改销售记录','请输入待修改的销售记录{销售记录编号+销售总价+销售数量+客户名称}，例如：\\n1\\n10.5\\n10\\n张三');

/*Table structure for table `t_purchase` */

DROP TABLE IF EXISTS `t_purchase`;

CREATE TABLE `t_purchase` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `purchase_time` datetime DEFAULT NULL COMMENT '进货时间',
  `purchase_price` decimal(10,2) NOT NULL COMMENT '进货价格',
  `purchase_num` int(11) NOT NULL COMMENT '进货数量',
  `client_name` varchar(20) NOT NULL COMMENT '客户名称',
  `shop_id` bigint(20) NOT NULL COMMENT '商品索引',
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_purchase` */

/*Table structure for table `t_sale` */

DROP TABLE IF EXISTS `t_sale`;

CREATE TABLE `t_sale` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sale_time` datetime DEFAULT NULL COMMENT '销售时间',
  `sale_price` decimal(10,2) NOT NULL COMMENT '销售价格',
  `sale_num` int(11) NOT NULL COMMENT '销售数量',
  `client_name` varchar(20) NOT NULL COMMENT '客户名称',
  `shop_id` bigint(20) NOT NULL COMMENT '商品索引',
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `t_sale` */

/*Table structure for table `t_shop` */

DROP TABLE IF EXISTS `t_shop`;

CREATE TABLE `t_shop` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) NOT NULL COMMENT '商品名称',
  `user_id` bigint(20) NOT NULL COMMENT '商品创建者',
  `update_time` datetime DEFAULT NULL COMMENT '创建/修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_shop` */

/*Table structure for table `t_stock` */

DROP TABLE IF EXISTS `t_stock`;

CREATE TABLE `t_stock` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `stock_time` datetime DEFAULT NULL COMMENT '更新时间',
  `stock_price` decimal(10,2) NOT NULL COMMENT '存货价格',
  `stock_num` int(11) NOT NULL COMMENT '存货数量',
  `shop_id` bigint(20) NOT NULL COMMENT '商品索引',
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_stock` */

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(20) DEFAULT NULL COMMENT '用户名',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `openid` varchar(50) NOT NULL COMMENT '微信openId',
  `nickname` varchar(20) DEFAULT NULL COMMENT '用户昵称',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `operation_id` int(11) NOT NULL DEFAULT '1' COMMENT '操作状态索引',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_user` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
