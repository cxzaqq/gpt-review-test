-- 초기화 구문
DROP TABLE IF EXISTS `role_menu_func`;
DROP TABLE IF EXISTS `role_menu`;
DROP TABLE IF EXISTS `menu_func`;
DROP TABLE IF EXISTS `user_role`;
DROP TABLE IF EXISTS `menu`;
DROP TABLE IF EXISTS `role`;
DROP TABLE IF EXISTS `user`;

-- 회원 테이블
CREATE TABLE `user` (
  `id` VARCHAR(50) PRIMARY KEY
, `password` VARCHAR(100) NOT NULL
, `name` VARCHAR(100) NOT NULL
, `phone` VARCHAR(20) UNIQUE NOT NULL
, `email` VARCHAR(100) UNIQUE NOT NULL
);

-- 역할 테이블
CREATE TABLE `role` (
  `role_code` VARCHAR(50) PRIMARY KEY
, `role_name` VARCHAR(100) NOT NULL
);

-- 회원_역할 매핑 테이블
CREATE TABLE `user_role` (
  `user_id` VARCHAR(50)
, `role_code` VARCHAR(50)
, PRIMARY KEY (`user_id`, `role_code`)
, FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE
, FOREIGN KEY (`role_code`) REFERENCES `role`(`role_code`) ON DELETE CASCADE
);

-- 메뉴 테이블
CREATE TABLE `menu` (
  `menu_code` VARCHAR(50) PRIMARY KEY
, `menu_name` VARCHAR(100) NOT NULL
, `parent_menu_code` VARCHAR(50)
, FOREIGN KEY (`parent_menu_code`) REFERENCES `menu`(`menu_code`) ON DELETE SET NULL
);

-- 역할_메뉴 매핑 테이블
CREATE TABLE `role_menu` (
  `role_code` VARCHAR(50)
, `menu_code` VARCHAR(50)
, PRIMARY KEY (`role_code`, `menu_code`)
, FOREIGN KEY (`role_code`) REFERENCES `role`(`role_code`) ON DELETE CASCADE
, FOREIGN KEY (`menu_code`) REFERENCES `menu`(`menu_code`) ON DELETE CASCADE
);

-- 메뉴 별 기능 코드 정의
CREATE TABLE `menu_func` (
  `menu_code` VARCHAR(50)
, `func_code` VARCHAR(50)
, PRIMARY KEY (`menu_code`, `func_code`)
, FOREIGN KEY (`menu_code`) REFERENCES `menu`(`menu_code`) ON DELETE CASCADE
);

-- 역할_메뉴 별 기능 코드 매핑
CREATE TABLE `role_menu_func` (
  `role_code` VARCHAR(50)
, `menu_code` VARCHAR(50)
, `func_code` VARCHAR(50)
, PRIMARY KEY (`role_code`, `menu_code`, `func_code`)
, FOREIGN KEY (`role_code`, `menu_code`) REFERENCES `role_menu`(`role_code`, `menu_code`) ON DELETE CASCADE
, FOREIGN KEY (`menu_code`, `func_code`) REFERENCES `menu_func`(`menu_code`, `func_code`) ON DELETE CASCADE
);