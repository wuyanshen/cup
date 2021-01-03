/*
 Navicat Premium Data Transfer

 Source Server         : 49.233.209.183
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : 49.233.209.183:3306
 Source Schema         : cup

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 03/01/2021 19:38:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `type` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日志类型 1:用户日志 2菜单日志 3角色日志',
  `title` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日志标题',
  `ip` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作ip地址',
  `addr` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作地址',
  `request_uri` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求uri',
  `method` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方式(POST,GET,PUT,DELETE)',
  `params` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求提交参数',
  `time` bigint(20) NULL DEFAULT NULL COMMENT '请求时间',
  `response` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '响应数据',
  `create_by` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作人',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `del_flag` int(1) NULL DEFAULT 0 COMMENT '是否删除,1已删0未删',
  `tenant_id` int(11) NULL DEFAULT 1 COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1235 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统日志表' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `menu_pid` int(11) NULL DEFAULT NULL COMMENT '父id',
  `menu_pids` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所有的父id',
  `menu_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单路径',
  `permission` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `component` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'VUE页面',
  `type` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单类型(0菜单1按钮)',
  `keep_alive` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '路由缓冲(1开启0关闭)',
  `status` int(2) NULL DEFAULT 1 COMMENT '状态,1可用0不可用',
  `sort` int(4) NULL DEFAULT NULL COMMENT '排序',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `del_flag` int(2) NULL DEFAULT 0 COMMENT '是否删除,1已删0未删',
  `tenant_id` int(11) NULL DEFAULT 1 COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 42 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 0, '0', '系统管理', '/', NULL, 'el-icon-setting', NULL, '0', NULL, 1, 1, '2020-03-24 00:25:57', '2020-04-13 17:40:14', 0, 1);
INSERT INTO `sys_menu` VALUES (2, 1, '1', '用户管理', '/user', '', 'el-icon-user', NULL, '0', NULL, 1, 1, '2020-03-24 00:41:33', '2020-06-10 19:02:04', 0, 1);
INSERT INTO `sys_menu` VALUES (3, 1, '1', '日志管理', '/log', '', 'el-icon-tickets', NULL, '0', NULL, 1, 2, '2020-03-24 00:42:09', '2020-04-13 17:45:20', 0, 1);
INSERT INTO `sys_menu` VALUES (7, 2, '1,2', '用户查询', '', 'sys:user:view', NULL, NULL, '1', NULL, 1, NULL, '2020-03-24 16:24:46', '2020-06-12 11:18:57', 0, 1);
INSERT INTO `sys_menu` VALUES (8, 2, '1,2', '用户新增', '', 'sys:user:add', NULL, NULL, '1', NULL, 1, NULL, '2020-03-28 01:58:00', '2020-04-12 19:34:03', 0, 1);
INSERT INTO `sys_menu` VALUES (9, 1, '1', '菜单管理', '/menu', NULL, 'el-icon-menu', NULL, '0', NULL, 1, 5, '2020-03-30 17:27:06', '2020-04-13 17:44:24', 0, 1);
INSERT INTO `sys_menu` VALUES (14, 2, NULL, '更新用户', '', 'sys:user:update', '', NULL, '1', NULL, 1, NULL, NULL, '2020-06-08 17:47:36', NULL, 1);
INSERT INTO `sys_menu` VALUES (16, 9, NULL, '菜单查询', NULL, 'sys:menu:view', NULL, NULL, '1', NULL, 1, NULL, '2020-06-08 16:50:32', '2020-06-08 16:50:32', 0, 1);
INSERT INTO `sys_menu` VALUES (18, 9, NULL, '菜单新增', NULL, 'sys:menu:add', NULL, NULL, '1', NULL, 1, NULL, '2020-06-08 16:56:22', '2020-06-08 16:56:22', 0, 1);
INSERT INTO `sys_menu` VALUES (21, 9, NULL, '菜单修改', '', 'sys:menu:update', '', NULL, '1', NULL, 1, NULL, NULL, '2020-06-08 17:37:05', NULL, 1);
INSERT INTO `sys_menu` VALUES (22, 9, NULL, '菜单删除', '', 'sys:menu:delete', '', NULL, '1', NULL, 1, NULL, NULL, '2020-06-08 18:03:20', NULL, 1);
INSERT INTO `sys_menu` VALUES (29, 1, NULL, '角色管理', '/role', '', 'el-icon-s-custom', NULL, '0', NULL, NULL, NULL, NULL, '2020-06-10 09:44:32', NULL, 1);
INSERT INTO `sys_menu` VALUES (30, 29, NULL, '角色查询', '', 'sys:role:view', '', NULL, '1', NULL, NULL, NULL, NULL, '2020-06-10 09:44:33', NULL, 1);
INSERT INTO `sys_menu` VALUES (31, 29, NULL, '角色修改', '', 'sys:role:update', '', NULL, '1', NULL, NULL, NULL, NULL, '2020-06-10 09:44:34', NULL, 1);
INSERT INTO `sys_menu` VALUES (32, 29, NULL, '角色删除', '', 'sys:role:delete', '', NULL, '1', NULL, NULL, NULL, NULL, '2020-06-10 09:44:35', NULL, 1);
INSERT INTO `sys_menu` VALUES (33, 29, NULL, '角色新增', '', 'sys:role:add', '', NULL, '1', NULL, NULL, NULL, NULL, '2020-06-10 09:44:36', NULL, 1);
INSERT INTO `sys_menu` VALUES (34, 2, NULL, '用户删除', '', 'sys:user:delete', '', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (35, 29, NULL, '分配权限', '', 'sys:permission:update', '', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (36, 3, NULL, '日志查询', '', 'sys:log:view', '', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (37, 0, NULL, '测试菜单', 'http://www.baidu.com', '', 'el-icon-eleme', NULL, '0', NULL, NULL, NULL, NULL, '2020-06-11 17:36:36', NULL, 1);
INSERT INTO `sys_menu` VALUES (38, 0, NULL, '企鹅网', 'http://qq.com', '', 'el-icon-eleme', NULL, '0', NULL, 1, NULL, '2020-06-12 11:19:50', '2020-11-05 15:00:17', 0, 1);
INSERT INTO `sys_menu` VALUES (39, 1, '1', '租户管理', '/tenant', '', 'el-icon-s-custom', NULL, '0', NULL, 1, NULL, '2020-09-02 16:26:38', '2020-09-02 16:26:52', 0, 1);
INSERT INTO `sys_menu` VALUES (41, 1, '1', '机构管理', '/org', '', 'el-icon-s-home', NULL, '0', NULL, 1, NULL, '2020-11-05 14:46:26', '2020-11-05 14:55:54', 0, 1);

-- ----------------------------
-- Table structure for sys_org
-- ----------------------------
DROP TABLE IF EXISTS `sys_org`;
CREATE TABLE `sys_org`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `org_pid` int(11) NULL DEFAULT NULL COMMENT '父节点id',
  `org_pids` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所有的父节点',
  `is_leaf` int(2) NULL DEFAULT NULL COMMENT '是否叶子节点',
  `org_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组织机构名称',
  `address` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `status` int(2) NULL DEFAULT 1 COMMENT '状态,1启用0无效',
  `sort` int(4) NULL DEFAULT NULL COMMENT '排序',
  `level` int(4) NULL DEFAULT NULL COMMENT '菜单的层级',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `del_flag` int(2) NULL DEFAULT 0 COMMENT '是否删除,1已删0未删',
  `tenant_id` int(11) NULL DEFAULT 1 COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '组织机构表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_org
-- ----------------------------
INSERT INTO `sys_org` VALUES (1, 0, '0', 0, 'cup总部', '北京海淀', 1, 1, 1, '2020-03-24 00:34:37', '2020-11-05 14:52:09', 0, 1);
INSERT INTO `sys_org` VALUES (2, 1, '1', 0, '研发部', '北京海淀', 1, 1, 2, '2020-03-24 00:36:31', '2020-03-24 00:36:31', 0, 1);
INSERT INTO `sys_org` VALUES (3, 2, '1,2', 1, '研发一部', '北京海淀', 1, 1, 3, '2020-03-24 00:37:36', '2020-03-24 00:37:36', 0, 1);
INSERT INTO `sys_org` VALUES (4, 2, '1,2', 1, '研发二部', '北京海淀', 1, 2, 3, '2020-03-24 00:38:09', '2020-03-24 00:38:09', 0, 1);
INSERT INTO `sys_org` VALUES (5, 0, NULL, 1, '测试机构', '', 1, 0, 1, '2020-11-05 14:48:15', '2020-11-05 14:48:15', 0, 1);
INSERT INTO `sys_org` VALUES (6, 1, '1', 1, '业务部', '北京海淀', 1, 1, 2, '2020-11-05 14:52:36', '2020-11-05 14:52:36', 0, 1);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `role_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色英文名称',
  `sort` int(4) NULL DEFAULT NULL COMMENT '排序',
  `status` int(2) NULL DEFAULT 1 COMMENT '状态,1可用0不可用',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `del_flag` int(2) NULL DEFAULT 0 COMMENT '是否删除,1已删0未删',
  `tenant_id` int(11) NULL DEFAULT 1 COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '管理员', 'admin', 1, 1, '2020-06-11 15:31:44', '2020-03-24 00:32:26', 0, 1);
INSERT INTO `sys_role` VALUES (2, '普通用户', 'common', 2, 1, '2020-06-09 17:06:19', '2020-03-24 00:33:15', 0, 1);
INSERT INTO `sys_role` VALUES (4, '测试', 'test', 0, 1, NULL, '2020-06-09 17:13:50', NULL, 1);
INSERT INTO `sys_role` VALUES (10, '系统管理员', 'ty_admin', 0, 1, '2020-11-06 04:41:38', '2020-08-31 17:30:57', NULL, 2);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_id` int(11) NULL DEFAULT NULL COMMENT '角色id',
  `menu_id` int(11) NULL DEFAULT NULL COMMENT '菜单id',
  `tenant_id` int(11) NULL DEFAULT 1 COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 634 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (419, 2, 7, 1);
INSERT INTO `sys_role_menu` VALUES (420, 2, 3, 1);
INSERT INTO `sys_role_menu` VALUES (421, 2, 36, 1);
INSERT INTO `sys_role_menu` VALUES (422, 2, 1, 1);
INSERT INTO `sys_role_menu` VALUES (423, 2, 2, 1);
INSERT INTO `sys_role_menu` VALUES (447, 4, 8, 1);
INSERT INTO `sys_role_menu` VALUES (448, 4, 37, 1);
INSERT INTO `sys_role_menu` VALUES (449, 4, 1, 1);
INSERT INTO `sys_role_menu` VALUES (450, 4, 2, 1);
INSERT INTO `sys_role_menu` VALUES (590, 1, 1, 1);
INSERT INTO `sys_role_menu` VALUES (591, 1, 2, 1);
INSERT INTO `sys_role_menu` VALUES (592, 1, 7, 1);
INSERT INTO `sys_role_menu` VALUES (593, 1, 8, 1);
INSERT INTO `sys_role_menu` VALUES (594, 1, 14, 1);
INSERT INTO `sys_role_menu` VALUES (595, 1, 34, 1);
INSERT INTO `sys_role_menu` VALUES (596, 1, 3, 1);
INSERT INTO `sys_role_menu` VALUES (597, 1, 36, 1);
INSERT INTO `sys_role_menu` VALUES (598, 1, 9, 1);
INSERT INTO `sys_role_menu` VALUES (599, 1, 16, 1);
INSERT INTO `sys_role_menu` VALUES (600, 1, 18, 1);
INSERT INTO `sys_role_menu` VALUES (601, 1, 21, 1);
INSERT INTO `sys_role_menu` VALUES (602, 1, 22, 1);
INSERT INTO `sys_role_menu` VALUES (603, 1, 29, 1);
INSERT INTO `sys_role_menu` VALUES (604, 1, 30, 1);
INSERT INTO `sys_role_menu` VALUES (605, 1, 31, 1);
INSERT INTO `sys_role_menu` VALUES (606, 1, 32, 1);
INSERT INTO `sys_role_menu` VALUES (607, 1, 33, 1);
INSERT INTO `sys_role_menu` VALUES (608, 1, 35, 1);
INSERT INTO `sys_role_menu` VALUES (609, 1, 39, 1);
INSERT INTO `sys_role_menu` VALUES (610, 1, 41, 1);
INSERT INTO `sys_role_menu` VALUES (611, 1, 37, 1);
INSERT INTO `sys_role_menu` VALUES (612, 1, 38, 1);
INSERT INTO `sys_role_menu` VALUES (613, 10, 1, 1);
INSERT INTO `sys_role_menu` VALUES (614, 10, 2, 1);
INSERT INTO `sys_role_menu` VALUES (615, 10, 7, 1);
INSERT INTO `sys_role_menu` VALUES (616, 10, 8, 1);
INSERT INTO `sys_role_menu` VALUES (617, 10, 14, 1);
INSERT INTO `sys_role_menu` VALUES (618, 10, 34, 1);
INSERT INTO `sys_role_menu` VALUES (619, 10, 3, 1);
INSERT INTO `sys_role_menu` VALUES (620, 10, 36, 1);
INSERT INTO `sys_role_menu` VALUES (621, 10, 9, 1);
INSERT INTO `sys_role_menu` VALUES (622, 10, 16, 1);
INSERT INTO `sys_role_menu` VALUES (623, 10, 18, 1);
INSERT INTO `sys_role_menu` VALUES (624, 10, 21, 1);
INSERT INTO `sys_role_menu` VALUES (625, 10, 22, 1);
INSERT INTO `sys_role_menu` VALUES (626, 10, 29, 1);
INSERT INTO `sys_role_menu` VALUES (627, 10, 30, 1);
INSERT INTO `sys_role_menu` VALUES (628, 10, 31, 1);
INSERT INTO `sys_role_menu` VALUES (629, 10, 32, 1);
INSERT INTO `sys_role_menu` VALUES (630, 10, 33, 1);
INSERT INTO `sys_role_menu` VALUES (631, 10, 35, 1);
INSERT INTO `sys_role_menu` VALUES (632, 10, 39, 1);
INSERT INTO `sys_role_menu` VALUES (633, 10, 41, 1);

-- ----------------------------
-- Table structure for sys_tenant
-- ----------------------------
DROP TABLE IF EXISTS `sys_tenant`;
CREATE TABLE `sys_tenant`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `tenant_id` int(11) NULL DEFAULT NULL COMMENT '租户编号',
  `tenant_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '租户名称',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注信息',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否已删除(0:未删除1:已删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '租户信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_tenant
-- ----------------------------
INSERT INTO `sys_tenant` VALUES (1, 1, '卡普公司', '一家科技公司', '2020-08-28 13:48:16', '2020-08-28 13:48:16', '0');
INSERT INTO `sys_tenant` VALUES (3, 22, '测试公司', '测试公司', '2020-08-28 14:58:35', '2020-08-28 14:58:35', '0');
INSERT INTO `sys_tenant` VALUES (5, NULL, '系统管理员', '系统管理员', '2020-09-03 11:27:28', '2020-09-03 11:27:28', '0');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `org_id` int(11) NULL DEFAULT NULL COMMENT '组织机构id',
  `username` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `email` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
  `avatar` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `status` int(2) NULL DEFAULT 1 COMMENT '状态,1可用0不可用',
  `sort` int(4) NULL DEFAULT NULL COMMENT '排序',
  `is_admin` int(11) NULL DEFAULT NULL COMMENT '是否是系统管理员',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `del_flag` int(2) NULL DEFAULT 0 COMMENT '是否删除,1已删0未删',
  `tenant_id` int(11) NULL DEFAULT 1 COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 3, 'sadmin', '$2a$10$BqmyJhzbUTwAlW6JPQINZufIgEBVvUs/Go6CjDPXPiI3bvSOJrWaW', '23xxx@qq.com', '18911483365', NULL, 1, 1, 1, NULL, '2020-11-02 12:10:29', 0, NULL);
INSERT INTO `sys_user` VALUES (2, NULL, 'admin', '$2a$10$BqmyJhzbUTwAlW6JPQINZufIgEBVvUs/Go6CjDPXPiI3bvSOJrWaW', '22@qq.com', '189222222', NULL, 1, NULL, NULL, NULL, '2020-11-02 14:28:04', NULL, 2);
INSERT INTO `sys_user` VALUES (8, NULL, 'user2', '$2a$10$ai82o.Rn5W5u6hVonBUcNuwgcYVKEQey8R3g4QAip954i9spaZcDm', NULL, '18888888888', NULL, 1, NULL, NULL, '2020-08-28 16:06:39', '2020-11-05 19:08:21', 0, 1);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `role_id` int(11) NULL DEFAULT NULL COMMENT '角色id',
  `tenant_id` int(11) NULL DEFAULT 1 COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户和角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (17, 1, 1, 1);
INSERT INTO `sys_user_role` VALUES (35, 2, 10, 1);
INSERT INTO `sys_user_role` VALUES (37, 8, 10, 1);

SET FOREIGN_KEY_CHECKS = 1;
