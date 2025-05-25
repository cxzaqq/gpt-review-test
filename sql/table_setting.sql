-- 초기화
DROP TABLE IF EXISTS `hq_user_detail`;
DROP TABLE IF EXISTS `duty`;
DROP TABLE IF EXISTS `position`;
DROP TABLE IF EXISTS `department`;
DROP TABLE IF EXISTS `user`;

-- 회원 테이블
CREATE TABLE `user` (
  `id` INT PRIMARY KEY AUTO_INCREMENT
, `code` VARCHAR(50) UNIQUE
, `password` VARCHAR(100) NOT NULL
, `name` VARCHAR(100) NOT NULL
, `phone` VARCHAR(20) UNIQUE NOT NULL
, `email` VARCHAR(100) UNIQUE NOT NULL
, `type` VARCHAR(50) NOT NULL CHECK (type IN ('ADMIN', 'HQ', 'FRANCHISE', 'SUPPLIER'))
, `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP
, `is_deleted` BOOLEAN DEFAULT FALSE
, `is_locked` BOOLEAN DEFAULT FALSE
, `sign_url` VARCHAR(255)
);

CREATE TABLE `department`(
  `id` INT PRIMARY KEY AUTO_INCREMENT
, `name` VARCHAR(100) NOT NULL
);

CREATE TABLE `position`(
  `id` INT PRIMARY KEY AUTO_INCREMENT
, `name` VARCHAR(50) NOT NULL
);

CREATE TABLE `duty`(
  `id` INT PRIMARY KEY AUTO_INCREMENT
, `name` VARCHAR(50) NOT NULL
);


CREATE TABLE `hq_user_detail` (
  `id` INT PRIMARY KEY AUTO_INCREMENT
, `user_id` INT
, `department_id` INT
, `position_id` INT
, `duty_id` INT
, FOREIGN KEY (`user_id`) REFERENCES `user`(`id`)
, FOREIGN KEY (`department_id`) REFERENCES `department`(`id`)
, FOREIGN KEY (`position_id`) REFERENCES `position`(`id`)
, FOREIGN KEY (`duty_id`) REFERENCES `duty`(`id`)
);