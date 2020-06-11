/*
 Navicat Premium Data Transfer

 Source Server         : mac_docker_mysql5728
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : localhost:3306
 Source Schema         : cup

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 30/03/2020 20:14:37
*/

USE cup;
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
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
   `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
   `type` varchar(1) DEFAULT NULL COMMENT '日志类型 1:用户日志 2菜单日志 3角色日志',
   `title` varchar(30) DEFAULT NULL COMMENT '日志标题',
   `ip` varchar(30) DEFAULT NULL COMMENT '操作ip地址',
   `request_uri` varchar(30) DEFAULT NULL COMMENT '请求uri',
   `method` varchar(10) DEFAULT NULL COMMENT '请求方式(POST,GET,PUT,DELETE)',
   `params` varchar(100) DEFAULT NULL COMMENT '请求提交参数',
   `time` bigint(20) DEFAULT NULL COMMENT '请求时间',
   `response` varchar(2000) DEFAULT NULL COMMENT '响应数据',
   `create_by` varchar(20) DEFAULT NULL COMMENT '操作人',
   `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   `del_flag` int(1) DEFAULT '0' COMMENT '是否删除,1已删0未删',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统日志表';


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
    `permission` varchar(64) DEFAULT NULL COMMENT '权限标识',
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
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` VALUES (1, 0, '0', '系统管理', '/', NULL, 0, 1, 'el-icon-setting', NULL, '0', NULL, 1, 1, '2020-03-24 00:25:57', '2020-04-13 17:40:14', 0);
INSERT INTO `sys_menu` VALUES (2, 1, '1', '用户管理', '/users', '', 1, 2, 'el-icon-user', NULL, '0', NULL, 1, 1, '2020-03-24 00:41:33', '2020-06-10 19:02:04', 0);
INSERT INTO `sys_menu` VALUES (3, 1, '1', '日志管理', '/logs', '', 1, 2, 'el-icon-tickets', NULL, '0', NULL, 1, 2, '2020-03-24 00:42:09', '2020-04-13 17:45:20', 0);
INSERT INTO `sys_menu` VALUES (7, 2, '1,2', '用户查询', '', 'sys:user:view', 1, NULL, NULL, NULL, '1', NULL, 1, NULL, '2020-03-24 16:24:46', '2020-04-12 19:34:02', 0);
INSERT INTO `sys_menu` VALUES (8, 2, '1,2', '用户新增', '', 'sys:user:add', 1, 2, NULL, NULL, '1', NULL, 1, NULL, '2020-03-28 01:58:00', '2020-04-12 19:34:03', 0);
INSERT INTO `sys_menu` VALUES (9, 1, '1', '菜单管理', '/menus/tree', NULL, 1, 2, 'el-icon-menu', NULL, '0', NULL, 1, 5, '2020-03-30 17:27:06', '2020-04-13 17:44:24', 0);
INSERT INTO `sys_menu` VALUES (14, 2, NULL, '更新用户', '', 'sys:user:update', 1, 2, '', NULL, '1', NULL, 1, NULL, NULL, '2020-06-08 17:47:36', NULL);
INSERT INTO `sys_menu` VALUES (16, 9, NULL, '菜单查询', NULL, 'sys:menu:view', 1, 2, NULL, NULL, '1', NULL, 1, NULL, '2020-06-08 16:50:32', '2020-06-08 16:50:32', 0);
INSERT INTO `sys_menu` VALUES (18, 9, NULL, '菜单新增', NULL, 'sys:menu:add', 1, 2, NULL, NULL, '1', NULL, 1, NULL, '2020-06-08 16:56:22', '2020-06-08 16:56:22', 0);
INSERT INTO `sys_menu` VALUES (21, 9, NULL, '菜单修改', '', 'sys:menu:update', 1, 2, '', NULL, '1', NULL, 1, NULL, NULL, '2020-06-08 17:37:05', NULL);
INSERT INTO `sys_menu` VALUES (22, 9, NULL, '菜单删除', '', 'sys:menu:delete', 1, 2, '', NULL, '1', NULL, 1, NULL, NULL, '2020-06-08 18:03:20', NULL);
INSERT INTO `sys_menu` VALUES (29, 1, NULL, '角色管理', '/roles', '', 1, NULL, 'el-icon-s-custom', NULL, '0', NULL, NULL, NULL, NULL, '2020-06-10 09:44:32', NULL);
INSERT INTO `sys_menu` VALUES (30, 29, NULL, '角色查询', '', 'sys:role:view', 1, NULL, '', NULL, '1', NULL, NULL, NULL, NULL, '2020-06-10 09:44:33', NULL);
INSERT INTO `sys_menu` VALUES (31, 29, NULL, '角色修改', '', 'sys:role:update', 1, NULL, '', NULL, '1', NULL, NULL, NULL, NULL, '2020-06-10 09:44:34', NULL);
INSERT INTO `sys_menu` VALUES (32, 29, NULL, '角色删除', '', 'sys:role:delete', 1, NULL, '', NULL, '1', NULL, NULL, NULL, NULL, '2020-06-10 09:44:35', NULL);
INSERT INTO `sys_menu` VALUES (33, 29, NULL, '角色新增', '', 'sys:role:add', 1, NULL, '', NULL, '1', NULL, NULL, NULL, NULL, '2020-06-10 09:44:36', NULL);
INSERT INTO `sys_menu` VALUES (34, 2, NULL, '用户删除', '', 'sys:user:delete', NULL, NULL, '', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (35, 29, NULL, '分配权限', '', 'sys:permission:update', NULL, NULL, '', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (36, 3, NULL, '日志查询', '', 'sys:log:view', NULL, NULL, '', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (37, 0, NULL, '测试菜单', 'http://www.baidu.com', '', NULL, NULL, 'el-icon-eleme', NULL, '0', NULL, NULL, NULL, NULL, '2020-06-11 15:11:55', NULL);
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
INSERT INTO `sys_org` VALUES (1, 0, '0', 0, '总部', '北京海淀', 1, 1, 1, '2020-03-24 00:34:37', '2020-03-24 00:34:40', 0);
INSERT INTO `sys_org` VALUES (2, 1, '1', 0, '研发部', '北京海淀', 1, 1, 2, '2020-03-24 00:36:31', '2020-03-24 00:36:31', 0);
INSERT INTO `sys_org` VALUES (3, 2, '1,2', 1, '研发一部', '北京海淀', 1, 1, 3, '2020-03-24 00:37:36', '2020-03-24 00:37:36', 0);
INSERT INTO `sys_org` VALUES (4, 2, '1,2', 1, '研发二部', '北京海淀', 1, 2, 3, '2020-03-24 00:38:09', '2020-03-24 00:38:09', 0);
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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES (1, '管理员', 'admin', 1, 1, '2020-06-11 15:31:44', '2020-03-24 00:32:26', 0);
INSERT INTO `sys_role` VALUES (2, '普通用户', 'common', 2, 1, '2020-06-09 17:06:19', '2020-03-24 00:33:15', 0);
INSERT INTO `sys_role` VALUES (4, '测试', 'test', 0, 1, NULL, '2020-06-09 17:13:50', NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=490 DEFAULT CHARSET=utf8 COMMENT='角色和菜单关联表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_menu` VALUES (419, 2, 7);
INSERT INTO `sys_role_menu` VALUES (420, 2, 3);
INSERT INTO `sys_role_menu` VALUES (421, 2, 36);
INSERT INTO `sys_role_menu` VALUES (422, 2, 1);
INSERT INTO `sys_role_menu` VALUES (423, 2, 2);
INSERT INTO `sys_role_menu` VALUES (447, 4, 8);
INSERT INTO `sys_role_menu` VALUES (448, 4, 37);
INSERT INTO `sys_role_menu` VALUES (449, 4, 1);
INSERT INTO `sys_role_menu` VALUES (450, 4, 2);
INSERT INTO `sys_role_menu` VALUES (470, 1, 1);
INSERT INTO `sys_role_menu` VALUES (471, 1, 2);
INSERT INTO `sys_role_menu` VALUES (472, 1, 7);
INSERT INTO `sys_role_menu` VALUES (473, 1, 8);
INSERT INTO `sys_role_menu` VALUES (474, 1, 14);
INSERT INTO `sys_role_menu` VALUES (475, 1, 34);
INSERT INTO `sys_role_menu` VALUES (476, 1, 3);
INSERT INTO `sys_role_menu` VALUES (477, 1, 36);
INSERT INTO `sys_role_menu` VALUES (478, 1, 9);
INSERT INTO `sys_role_menu` VALUES (479, 1, 16);
INSERT INTO `sys_role_menu` VALUES (480, 1, 18);
INSERT INTO `sys_role_menu` VALUES (481, 1, 21);
INSERT INTO `sys_role_menu` VALUES (482, 1, 22);
INSERT INTO `sys_role_menu` VALUES (483, 1, 29);
INSERT INTO `sys_role_menu` VALUES (484, 1, 30);
INSERT INTO `sys_role_menu` VALUES (485, 1, 31);
INSERT INTO `sys_role_menu` VALUES (486, 1, 32);
INSERT INTO `sys_role_menu` VALUES (487, 1, 33);
INSERT INTO `sys_role_menu` VALUES (488, 1, 35);
INSERT INTO `sys_role_menu` VALUES (489, 1, 37);
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES (1, 3, 'admin', '$2a$10$BqmyJhzbUTwAlW6JPQINZufIgEBVvUs/Go6CjDPXPiI3bvSOJrWaW', '23xxx@qq.com', '18911483365', NULL, 1, 1, NULL, '2020-06-01 17:36:04', 0);
INSERT INTO `sys_user` VALUES (2, NULL, 'yonghu1', '$2a$10$BqmyJhzbUTwAlW6JPQINZufIgEBVvUs/Go6CjDPXPiI3bvSOJrWaW', '22@qq.com', '189222222', NULL, 1, NULL, NULL, '2020-06-10 12:27:30', NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='用户和角色关联表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES (1, 1, 1);
INSERT INTO `sys_user_role` VALUES (2, 2, 2);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
