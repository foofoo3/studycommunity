/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : localhost:3306
 Source Schema         : community

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 03/11/2022 21:21:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for like_and_star
-- ----------------------------
DROP TABLE IF EXISTS `like_and_star`;
CREATE TABLE `like_and_star`  (
  `type` int(16) NOT NULL,
  `uid` int(16) NOT NULL,
  `target_id` bigint(20) NOT NULL,
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `gmt_create` bigint(255) NOT NULL,
  `parent_id` int(32) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 59 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
