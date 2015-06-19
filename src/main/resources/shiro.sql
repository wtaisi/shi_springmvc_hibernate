DROP TABLE IF EXISTS sys_users;
DROP TABLE IF EXISTS sys_roles;
DROP TABLE IF EXISTS sys_permissions;
DROP TABLE IF EXISTS sys_users_roles;
DROP TABLE IF EXISTS sys_roles_permissions;
-- 用户表
CREATE TABLE sys_users (
  id BIGINT AUTO_INCREMENT,
  username VARCHAR(100),
  PASSWORD VARCHAR(100),
  salt VARCHAR(100),
  locked BOOL DEFAULT FALSE,
  CONSTRAINT pk_sys_users PRIMARY KEY(id)
) CHARSET=utf8 ENGINE=INNODB;
CREATE UNIQUE INDEX idx_sys_users_username ON sys_users(username);
-- 角色表
CREATE TABLE sys_roles (
  id BIGINT AUTO_INCREMENT,
  role VARCHAR(100),
  description VARCHAR(100),
  available BOOL DEFAULT FALSE,
  CONSTRAINT pk_sys_roles PRIMARY KEY(id)
) CHARSET=utf8 ENGINE=INNODB;
CREATE UNIQUE INDEX idx_sys_roles_role ON sys_roles(role);
-- 权限表
CREATE TABLE sys_permissions (
  id BIGINT AUTO_INCREMENT,
  permission VARCHAR(100),
  description VARCHAR(100),
  available BOOL DEFAULT FALSE,
  CONSTRAINT pk_sys_permissions PRIMARY KEY(id)
) CHARSET=utf8 ENGINE=INNODB;
CREATE UNIQUE INDEX idx_sys_permissions_permission ON sys_permissions(permission);
-- 用户表色表
CREATE TABLE sys_users_roles (
  user_id BIGINT,
  role_id BIGINT,
  CONSTRAINT pk_sys_users_roles PRIMARY KEY(user_id, role_id)
) CHARSET=utf8 ENGINE=INNODB;
-- 角色权限表
CREATE TABLE sys_roles_permissions (
  role_id BIGINT,
  permission_id BIGINT,
  CONSTRAINT pk_sys_roles_permissions PRIMARY KEY(role_id, permission_id)
) CHARSET=utf8 ENGINE=INNODB;

SELECT * FROM sys_users;
SELECT * FROM sys_roles;
SELECT * FROM sys_permissions;
SELECT * FROM sys_users_roles;
SELECT * FROM sys_roles_permissions;
SELECT * FROM `hi_user`;