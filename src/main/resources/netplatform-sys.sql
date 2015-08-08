#----------------------------------------------------------------------------------------------
#--网校系统－系统管理模块表结构
#----------------------------------------------------------------------------------------------
-- 须前置删除的关联关系表结构
drop table if exists tbl_Netplatform_Students_Learning;-- 学员学习进度
drop table if exists tbl_Netplatform_Settings_UserAgencies;-- 用户机构
drop table if exists tbl_Netplatform_Students_OrderClasses;-- 订单班级
drop table if exists tbl_Netplatform_Students_OrderPackages;-- 订单套餐
drop table if exists tbl_Netplatform_Students_OrderStudents;-- 订单学员
drop table if exists tbl_Netplatform_Students_Orders;-- 订单
drop table if exists tbl_Netplatform_Teachers_AnswerQuestionDetails;-- 答疑明细
drop table if exists tbl_Netplatform_Teachers_AnswerQuestionTopics;-- 答疑主题
#----------------------------------------------------------
drop table if exists tbl_Netplatform_Security_RoleRight;-- 角色菜单权限
drop table if exists tbl_Netplatform_Security_MenuRights;-- 菜单权限
drop table if exists tbl_Netplatform_Security_UserRoles;-- 用户角色
drop table if exists tbl_Netplatform_Security_LoginLog;-- 用户登录日志
#----------------------------------------------------------------------------------------------
-- 菜单数据表结构(tbl_Netplatform_Security_Menus)
drop table if exists tbl_Netplatform_Security_Menus;
create table tbl_Netplatform_Security_Menus
(
	id 				varchar(64) NOT NULL,-- 菜单ID
	icon			varchar(32),-- 菜单图标
	name		varchar(32) NOT NULL,-- 菜单名称
	uri			varchar(255),-- 菜单URI
	orderNO	int DEFAULT 0,-- 排序
	
	pid			varchar(64)  DEFAULT NULL,-- 上级菜单ID
	
	constraint pk_tbl_Netplatform_Security_Menus primary key(id),-- 主键约束
	constraint fk_tbl_Netplatform_Security_Menus_pid foreign key(pid) references tbl_Netplatform_Security_Menus(id)-- 上级菜单ID外键约束
);
-- 基础权限(tbl_Netplatform_Security_Rights)
drop table if exists tbl_Netplatform_Security_Rights;
create table tbl_Netplatform_Security_Rights(
	id				varchar(64) NOT NULL,-- 权限ID
	name		varchar(45),-- 权限名称
	`value`		int DEFAULT 0,-- 权限值
	orderNo	int DEFAULT 0,-- 排序号
	
	constraint pk_tbl_Netplatform_Security_Rights primary key(id)-- 主键约束
);
-- 菜单权限(tbl_Netplatform_Security_MenuRights)
drop table if exists tbl_Netplatform_Security_MenuRights;
create table tbl_Netplatform_Security_MenuRights(
	id				varchar(64) NOT NULL,-- 菜单权限ID
	menu_id	varchar(64) NOT NULL,-- 所属菜单ID
	right_id		varchar(64) NOT NULL,-- 所属权限ID
	code			varchar(128) NOT NULL,-- 菜单权限代码
	
	constraint pk_tbl_Netplatform_Security_MenuRights primary key(id),-- 主键约束
	constraint uk_tbl_Netplatform_Security_MenuRights unique key(menu_id,right_id),-- 联合唯一约束
	constraint fk_tbl_Netplatform_Security_MenuRights_Menus_menu_id foreign key(menu_id) references tbl_Netplatform_Security_Menus(id),-- 外键菜单ID约束
	constraint fk_tbl_Netplatform_Security_MenuRights_Rights_right_id foreign key(right_id) references tbl_Netplatform_Security_Rights(id)-- 外键权限ID约束
);
-- 角色(tbl_Netplatform_Security_Role)
drop table if exists tbl_Netplatform_Security_Role;
create table tbl_Netplatform_Security_Role(
	id					varchar(64) NOT NULL,-- 角色ID
	name			varchar(64) NOT NULL,-- 角色名称
	description	varchar(255),-- 描述信息
	status			int	DEFAULT 0,-- 状态
	
	constraint pk_tbl_Netplatform_Security_Role primary key(id)-- 主键约束
);
-- 角色菜单权限(tbl_Netplatform_Security_RoleRight)
drop table if exists tbl_Netplatform_Security_RoleRight;
create table tbl_Netplatform_Security_RoleRight(
	role_id					varchar(64) NOT NULL,-- 角色ID
	menu_right_id		varchar(64) NOT NULL,-- 菜单权限ID
	
	constraint pk_tbl_Netplatform_Security_RoleRight primary key(role_id,menu_right_id),-- 主键约束
	constraint fk_tbl_Netplatform_Security_RoleRight_role_id foreign key(role_id)  references tbl_Netplatform_Security_Role(id),-- 外键角色ID约束
	constraint fk_tbl_Netplatform_Security_RoleRight_menu_right_id foreign key(menu_right_id)  references tbl_Netplatform_Security_MenuRights(id)-- 外键菜单权限ID约束
);
-- 用户(tbl_Netplatform_Security_Users)
drop table if exists tbl_Netplatform_Security_Users;
create table tbl_Netplatform_Security_Users(
	id					varchar(64) NOT NULL,-- 用户ID
	name			varchar(20) NOT NULL,-- 用户姓名
	account		varchar(32) NOT NULL,-- 用户账号
	nickName		varchar(20),-- 用户昵称
	imgUrl			varchar(255),-- 头像图片URL
	gender			int	default 0,-- 性别:0-未知,1-男,2-女
	`type`			int	default 0,-- 类型:0-未知,1-后台用户,2-前台用户
	status			int	default 0,-- 状态:0-停用,1-启用
	phone			varchar(20),-- 手机号码
	qq				varchar(20),-- QQ
	email			varchar(45),-- Email
	createTime	timestamp default CURRENT_TIMESTAMP,-- 用户创建时间
	
	constraint pk_tbl_Netplatform_Security_Users primary key(id)-- 唯一约束
	#--constraint uk_tbl_Netplatform_Security_Users unique key(account)--唯一约束
);
-- 用户角色(tbl_Netplatform_Security_UserRoles)
drop table if exists tbl_Netplatform_Security_UserRoles;
create table tbl_Netplatform_Security_UserRoles(
	user_id		varchar(64) NOT NULL,-- 用户ID
	role_id		varchar(64) NOT NULL,-- 角色ID
	
	constraint pk_tbl_Netplatform_Security_UserRoles primary key(user_id,role_id),-- 主键约束
	constraint fk_tbl_Netplatform_Security_UserRoles_user_id foreign key(user_id) references tbl_Netplatform_Security_Users(id),-- 外键用户ID约束
	constraint fk_tbl_Netplatform_Security_UserRoles_role_id foreign key(role_id) references tbl_Netplatform_Security_Role(id)-- 外键角色ID约束
);
-- 用户登录日志(tbl_Netplatform_Security_LoginLog)
drop table if exists tbl_Netplatform_Security_LoginLog;
create table tbl_Netplatform_Security_LoginLog(
	id					varchar(64) NOT NULL,-- 日志ID
	user_id			varchar(64) NOT NULL,-- 登录用户ID
	ip					varchar(64),-- 登录IP
	browser		varchar(255),-- 登录浏览器
	createTime	timestamp	default CURRENT_TIMESTAMP,-- 登录时间
	
	constraint pk_tbl_Netplatform_Security_LoginLog primary key(id),-- 主键约束
	constraint fk_tbl_Netplatform_Security_LoginLog_user_id foreign key(user_id) references tbl_Netplatform_Security_Users(id)-- 外键用户ID约束
);
#----------------------------------------------------------------------------------------------