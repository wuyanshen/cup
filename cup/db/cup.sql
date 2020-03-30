/*
 Navicat Premium Data Transfer

 Source Server         : mac_docker_mysql5728
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : localhost:3306
 Source Schema         : vue-boot-back

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 30/03/2020 20:14:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for persistent_logins
-- ----------------------------
DROP TABLE IF EXISTS `persistent_logins`;
CREATE TABLE `persistent_logins` (
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `menu_pid` int(11) DEFAULT NULL COMMENT '父id',
  `menu_pids` varchar(64) DEFAULT NULL COMMENT '所有的父id',
  `menu_name` varchar(20) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(64) DEFAULT NULL COMMENT '菜单路径',
  `permission` varchar(20) DEFAULT NULL COMMENT '权限标识',
  `is_leaf` int(2) DEFAULT NULL COMMENT '是否叶子节点，1:是，0:不是',
  `level` int(2) DEFAULT NULL COMMENT '菜单第几级',
  `icon` varchar(20) DEFAULT NULL COMMENT '菜单图标',
  `component` varchar(20) DEFAULT NULL COMMENT 'VUE页面',
  `type` varchar(5) DEFAULT NULL COMMENT '菜单类型(0菜单1按钮)',
  `keep_alive` varchar(5) DEFAULT NULL COMMENT '路由缓冲(1开启0关闭)',
  `status` int(2) DEFAULT '1' COMMENT '状态,1可用0不可用',
  `sort` int(4) DEFAULT NULL COMMENT '排序',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` int(2) DEFAULT '0' COMMENT '是否删除,1已删0未删',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` VALUES (1, 0, '0', '系统管理', '/', NULL, 0, 1, NULL, NULL, NULL, NULL, 1, 1, '2020-03-23 16:25:57', '2020-03-30 09:42:13', 0);
INSERT INTO `sys_menu` VALUES (2, 1, '1', '用户管理', '/user.html', '', 1, 2, NULL, NULL, NULL, NULL, 1, 1, '2020-03-23 16:41:33', '2020-03-24 08:04:09', 0);
INSERT INTO `sys_menu` VALUES (3, 1, '1', '日志管理', '/log.html', '', 1, 2, NULL, NULL, NULL, NULL, 1, 2, '2020-03-23 16:42:09', '2020-03-24 08:04:12', 0);
INSERT INTO `sys_menu` VALUES (4, 1, '1', '业务一', '/biz1.html', '', 1, 2, NULL, NULL, NULL, NULL, 1, 3, '2020-03-23 16:44:55', '2020-03-24 08:15:20', 0);
INSERT INTO `sys_menu` VALUES (5, 1, '1', '业务二', '/biz2.html', '', 1, 2, NULL, NULL, NULL, NULL, 1, 4, '2020-03-23 16:45:18', '2020-03-24 08:15:26', 0);
INSERT INTO `sys_menu` VALUES (7, 2, '1,2', '用户查询', '/users/**', '', NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '2020-03-24 08:24:46', '2020-03-27 18:17:58', 0);
INSERT INTO `sys_menu` VALUES (8, 2, '1,2', '用户新增', '/users', NULL, 1, 2, NULL, NULL, NULL, NULL, 1, NULL, '2020-03-27 17:58:00', '2020-03-27 17:58:10', 0);
INSERT INTO `sys_menu` VALUES (9, 1, '1', '菜单管理', '/menus/tree', NULL, 1, 2, NULL, NULL, NULL, NULL, 1, 5, '2020-03-30 09:27:06', '2020-03-30 09:27:53', 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_org
-- ----------------------------
DROP TABLE IF EXISTS `sys_org`;
CREATE TABLE `sys_org` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `org_pid` int(11) DEFAULT NULL COMMENT '父节点id',
  `org_pids` varchar(20) DEFAULT NULL COMMENT '所有的父节点',
  `is_leaf` int(2) DEFAULT NULL COMMENT '是否叶子节点',
  `org_name` varchar(20) DEFAULT NULL COMMENT '组织机构名称',
  `address` varchar(64) DEFAULT NULL COMMENT '地址',
  `status` int(2) DEFAULT '1' COMMENT '状态,1启用0无效',
  `sort` int(4) DEFAULT NULL COMMENT '排序',
  `level` int(4) DEFAULT NULL COMMENT '菜单的层级',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` int(2) DEFAULT '0' COMMENT '是否删除,1已删0未删',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='组织机构表';

-- ----------------------------
-- Records of sys_org
-- ----------------------------
BEGIN;
INSERT INTO `sys_org` VALUES (1, 0, '0', 0, '总部', '北京海淀', 1, 1, 1, '2020-03-23 16:34:37', '2020-03-23 16:34:40', 0);
INSERT INTO `sys_org` VALUES (2, 1, '1', 0, '研发部', '北京海淀', 1, 1, 2, '2020-03-23 16:36:31', '2020-03-23 16:36:31', 0);
INSERT INTO `sys_org` VALUES (3, 2, '1,2', 1, '研发一部', '北京海淀', 1, 1, 3, '2020-03-23 16:37:36', '2020-03-23 16:37:36', 0);
INSERT INTO `sys_org` VALUES (4, 2, '1,2', 1, '研发二部', '北京海淀', 1, 2, 3, '2020-03-23 16:38:09', '2020-03-23 16:38:09', 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_name` varchar(20) DEFAULT NULL COMMENT '角色名称',
  `role_code` varchar(20) DEFAULT NULL COMMENT '角色英文名称',
  `sort` int(4) DEFAULT NULL COMMENT '排序',
  `status` int(2) DEFAULT '1' COMMENT '状态,1可用0不可用',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `del_flag` int(2) DEFAULT '0' COMMENT '是否删除,1已删0未删',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES (1, '管理员', 'admin', 1, 1, '2020-03-23 16:32:26', '2020-03-23 16:32:26', 0);
INSERT INTO `sys_role` VALUES (2, '普通用户', 'common', 2, 1, '2020-03-23 16:33:15', '2020-03-23 16:33:15', 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `menu_id` int(11) DEFAULT NULL COMMENT '菜单id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='角色和菜单关联表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_menu` VALUES (1, 1, 2);
INSERT INTO `sys_role_menu` VALUES (2, 1, 3);
INSERT INTO `sys_role_menu` VALUES (3, 2, 4);
INSERT INTO `sys_role_menu` VALUES (4, 2, 5);
INSERT INTO `sys_role_menu` VALUES (5, 1, 6);
INSERT INTO `sys_role_menu` VALUES (6, 2, 6);
INSERT INTO `sys_role_menu` VALUES (7, 1, 7);
INSERT INTO `sys_role_menu` VALUES (8, 1, 8);
INSERT INTO `sys_role_menu` VALUES (9, 1, 9);
INSERT INTO `sys_role_menu` VALUES (10, 1, 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `org_id` int(11) DEFAULT NULL COMMENT '组织机构id',
  `username` varchar(10) DEFAULT NULL COMMENT '用户名',
  `password` varchar(200) DEFAULT NULL COMMENT '密码',
  `email` varchar(20) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) DEFAULT NULL COMMENT '电话',
  `avatar` varchar(50) DEFAULT NULL COMMENT '头像',
  `status` int(2) DEFAULT '1' COMMENT '状态,1可用0不可用',
  `sort` int(4) DEFAULT NULL COMMENT '排序',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` int(2) DEFAULT '0' COMMENT '是否删除,1已删0未删',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES (1, 3, 'admin', '$2a$10$RcT2uUd7h2tRKt1WoZNfcuBl3jYptVklF1Q1cydNuO800xRaH6Xla', NULL, NULL, NULL, 1, 1, NULL, '2020-03-25 14:02:41', 0);
INSERT INTO `sys_user` VALUES (2, 1, 'yanfa1', '$2a$10$RcT2uUd7h2tRKt1WoZNfcuBl3jYptVklF1Q1cydNuO800xRaH6Xla', NULL, NULL, NULL, 1, 2, '2020-03-23 16:53:46', '2020-03-24 08:14:03', 0);
INSERT INTO `sys_user` VALUES (3, 3, 'user1', '$2a$10$RcT2uUd7h2tRKt1WoZNfcuBl3jYptVklF1Q1cydNuO800xRaH6Xla', NULL, NULL, NULL, 1, 1, NULL, '2020-03-27 18:15:20', NULL);
INSERT INTO `sys_user` VALUES (5, 3, 'user1', '$2a$10$RcT2uUd7h2tRKt1WoZNfcuBl3jYptVklF1Q1cydNuO800xRaH6Xla', NULL, NULL, NULL, 1, 1, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户和角色关联表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES (1, 1, 1);
INSERT INTO `sys_user_role` VALUES (2, 2, 2);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
