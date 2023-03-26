/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : sql_admin

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 26/03/2023 15:07:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `parent_id` bigint NULL DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单URL',
  `pram` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type` int NOT NULL COMMENT '类型     0：目录   1：菜单   2：按钮',
  `icon` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `order_num` int NULL DEFAULT NULL COMMENT '排序',
  `created` datetime NOT NULL,
  `updated` datetime NULL DEFAULT NULL,
  `statu` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, 0, '系统管理', '/manage', 'manage', '', 0, 'el-icon-s-operation', 1, '2021-01-15 18:58:18', '2021-01-15 18:58:20', 1);
INSERT INTO `menu` VALUES (2, 1, '用户管理', '/users', 'user:list', '/User', 1, 'el-icon-s-custom', 1, '2021-01-15 19:03:45', '2021-01-15 19:03:48', 1);
INSERT INTO `menu` VALUES (3, 1, '角色管理', '/roles', 'role:list', '/Role', 1, 'el-icon-rank', 2, '2021-01-15 19:03:45', '2021-01-15 19:03:48', 1);
INSERT INTO `menu` VALUES (4, 1, '菜单管理', '/menus', 'menu:list', '/Menu', 1, 'el-icon-menu', 3, '2021-01-15 19:03:45', '2021-01-15 19:03:48', 1);
INSERT INTO `menu` VALUES (5, 0, '系统工具', '/tools', 'tools', NULL, 0, 'el-icon-s-tools', 2, '2021-01-15 19:06:11', NULL, 1);
INSERT INTO `menu` VALUES (6, 5, '数字字典', '/dicts', 'dict:list', '/Dict', 1, 'el-icon-s-order', 1, '2021-01-15 19:07:18', '2021-01-18 16:32:13', 1);
INSERT INTO `menu` VALUES (7, 3, '添加角色', NULL, 'role:save', '', 2, '', 1, '2021-01-15 23:02:25', '2021-01-17 21:53:14', 0);
INSERT INTO `menu` VALUES (9, 2, '添加用户', NULL, 'user:save', NULL, 2, NULL, 1, '2021-01-17 21:48:32', NULL, 1);
INSERT INTO `menu` VALUES (10, 2, '修改用户', NULL, 'user:update', NULL, 2, NULL, 2, '2021-01-17 21:49:03', '2021-01-17 21:53:04', 1);
INSERT INTO `menu` VALUES (11, 2, '删除用户', NULL, 'user:delete', NULL, 2, NULL, 3, '2021-01-17 21:49:21', NULL, 1);
INSERT INTO `menu` VALUES (12, 2, '分配角色', NULL, 'user:role', NULL, 2, NULL, 4, '2021-01-17 21:49:58', NULL, 1);
INSERT INTO `menu` VALUES (13, 2, '重置密码', NULL, 'user:repass', NULL, 2, NULL, 5, '2021-01-17 21:50:36', NULL, 1);
INSERT INTO `menu` VALUES (14, 3, '修改角色', NULL, 'role:update', NULL, 2, NULL, 2, '2021-01-17 21:51:14', NULL, 1);
INSERT INTO `menu` VALUES (15, 3, '删除角色', NULL, 'role:delete', NULL, 2, NULL, 3, '2021-01-17 21:51:39', NULL, 1);
INSERT INTO `menu` VALUES (16, 3, '分配权限', NULL, 'role:pram', NULL, 2, NULL, 5, '2021-01-17 21:52:02', NULL, 1);
INSERT INTO `menu` VALUES (17, 4, '添加菜单', NULL, 'menu:save', NULL, 2, NULL, 1, '2021-01-17 21:53:53', '2021-01-17 21:55:28', 1);
INSERT INTO `menu` VALUES (18, 4, '修改菜单', NULL, 'menu:update', NULL, 2, NULL, 2, '2021-01-17 21:56:12', NULL, 1);
INSERT INTO `menu` VALUES (19, 4, '删除菜单', NULL, 'menu:delete', NULL, 2, NULL, 3, '2021-01-17 21:56:36', NULL, 1);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `remark` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `created` datetime NULL DEFAULT NULL,
  `updated` datetime NULL DEFAULT NULL,
  `statu` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE,
  UNIQUE INDEX `code`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (3, '普通用户', 'normal', '只有基本查看功能', '2021-01-04 10:09:14', '2021-01-30 08:19:52', 1);
INSERT INTO `role` VALUES (6, '超级管理员', 'admin', '系统默认最高权限，不可以编辑和任意修改', '2021-01-16 13:29:03', '2021-01-17 15:50:45', 1);

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_id` bigint NOT NULL,
  `menu_id` bigint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 115 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES (60, 6, 1);
INSERT INTO `role_menu` VALUES (61, 6, 2);
INSERT INTO `role_menu` VALUES (62, 6, 9);
INSERT INTO `role_menu` VALUES (63, 6, 10);
INSERT INTO `role_menu` VALUES (64, 6, 11);
INSERT INTO `role_menu` VALUES (65, 6, 12);
INSERT INTO `role_menu` VALUES (66, 6, 13);
INSERT INTO `role_menu` VALUES (67, 6, 3);
INSERT INTO `role_menu` VALUES (68, 6, 7);
INSERT INTO `role_menu` VALUES (69, 6, 14);
INSERT INTO `role_menu` VALUES (70, 6, 15);
INSERT INTO `role_menu` VALUES (71, 6, 16);
INSERT INTO `role_menu` VALUES (72, 6, 4);
INSERT INTO `role_menu` VALUES (73, 6, 17);
INSERT INTO `role_menu` VALUES (74, 6, 18);
INSERT INTO `role_menu` VALUES (75, 6, 19);
INSERT INTO `role_menu` VALUES (76, 6, 5);
INSERT INTO `role_menu` VALUES (77, 6, 6);
INSERT INTO `role_menu` VALUES (96, 3, 1);
INSERT INTO `role_menu` VALUES (97, 3, 2);
INSERT INTO `role_menu` VALUES (98, 3, 3);
INSERT INTO `role_menu` VALUES (99, 3, 4);
INSERT INTO `role_menu` VALUES (100, 3, 5);
INSERT INTO `role_menu` VALUES (101, 3, 6);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `city` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `created` datetime NULL DEFAULT NULL,
  `updated` datetime NULL DEFAULT NULL,
  `last_login` datetime NULL DEFAULT NULL,
  `statu` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_USERNAME`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '$2a$10$KzxRcJ/ck93q57QHkVf0tupKdIL3xFoUHyQEEJOUDq9ssrwTAmrFq', 'https://pic.imgdb.cn/item/641ac93aa682492fcc7d30de.jpg', '123@qq.com', '广州', '2021-01-12 22:13:53', '2021-01-16 16:57:32', '2020-12-30 08:38:37', 1);
INSERT INTO `user` VALUES (2, 'test', '$2a$10$KzxRcJ/ck93q57QHkVf0tupKdIL3xFoUHyQEEJOUDq9ssrwTAmrFq', 'https://pic.imgdb.cn/item/641ac93aa682492fcc7d30de.jpg', 'test@qq.com', NULL, '2021-01-30 08:20:22', '2021-01-30 08:55:57', NULL, 1);
INSERT INTO `user` VALUES (3, 'mark', '$2a$10$KzxRcJ/ck93q57QHkVf0tupKdIL3xFoUHyQEEJOUDq9ssrwTAmrFq', 'https://pic.imgdb.cn/item/641ac93aa682492fcc7d30de.jpg', 'user@qq.com', NULL, '2021-01-30 08:20:22', '2021-01-30 08:55:57', NULL, 1);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (4, 1, 6);
INSERT INTO `user_role` VALUES (7, 1, 3);
INSERT INTO `user_role` VALUES (16, 2, 3);

SET FOREIGN_KEY_CHECKS = 1;
