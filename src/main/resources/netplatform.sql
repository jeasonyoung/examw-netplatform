#----------------------------------------------------------------
#-- 网校平台 数据库脚本(mysql)
#----------------------------------------------------------------
-- 须潜质删除的关联表

-- 删除机构消息学员
drop table if exists tbl_netschool_agency_message_students;
-- 删除机构消息
drop table if exists tbl_netschool_agency_messages;
-- 删除答疑明细
drop table if exists tbl_netschool_agency_details;
-- 删除机构订单
drop table if exists tbl_netschool_agency_orders;
-- 删除机构用户
drop table if exists tbl_netschool_common_agency_users;
-- 删除机构充值
drop table if exists tbl_netschool_common_agency_charges;
#----------------------------------------------------------------
-- 删除用户登录登录日志
drop table if exists tbl_netschool_sys_user_logs;
-- 删除用户角色
drop table if exists tbl_netschool_sys_user_roles;
-- 删除角色权限
drop table if exists tbl_netschool_sys_role_rights;
-- 删除菜单权限
drop table if exists tbl_netschool_sys_menu_rights;
#----------------------------------------------------------------
-- 菜单数据表结构(tbl_netschool_sys_menus)
drop table if exists tbl_netschool_sys_menus;
create table tbl_netschool_sys_menus(
    `id`       varchar(64) not null     comment '菜单ID',
    `icon`     varchar(32)              comment '菜单图标',
    `name`     varchar(32) not null     comment '菜单名称',
    `uri`      varchar(1024)             comment '菜单URI',
    `order_no` int default 0            comment '排序',
    `pid`      varchar(64) default null comment '上级菜单ID',

    constraint pk_tbl_netschool_sys_menus primary key(`id`), -- 主键约束
    constraint fk_tbl_netschool_sys_menus_pid foreign key(`pid`) 
               references tbl_netschool_sys_menus(`id`) -- 上级菜单ID递归约束
) comment '菜单数据表结构';
-- 基础权限(tbl_netschool_sys_rights)
drop table if exists tbl_netschool_sys_rights;
create table tbl_netschool_sys_rights(
	`id`       varchar(64) not null comment '权限ID',
	`name`     varchar(32) not null comment '权限名称',
	`value`    int default 0        comment '权限值',
	`order_no` int default 0        comment '排序',

	constraint pk_tbl_netschool_sys_rights primary key(`id`), -- 主键约束
	constraint uk_tbl_netschool_sys_rights_name unique key(`name`) -- 唯一约束
) comment '基础权限';
-- 菜单权限(tbl_netschool_sys_menu_rights)
drop table if exists tbl_netschool_sys_menu_rights;
create table tbl_netschool_sys_menu_rights(
	`menu_id`  varchar(64) not null  comment '菜单ID',
	`right_id` varchar(64) not null  comment '权限ID',
	`code`     varchar(128) not null comment '菜单权限代码(菜单ID:权限值)',

	constraint pk_tbl_netschool_sys_menu_rights primary key(`menu_id`,`right_id`),-- 联合主键
	constraint uk_tbl_netschool_sys_menu_rights_code unique key(`code`),-- 菜单权限代码唯一约束
	constraint fk_tbl_netschool_sys_menu_rights_menu_id foreign key(`menu_id`)
	           references tbl_netschool_sys_menus(`id`),-- 菜单外键约束
	constraint fk_tbl_netschool_sys_menu_rights_right_id foreign key(`right_id`)
	           references tbl_netschool_sys_rights(`id`) -- 权限外键约束
	
) comment '菜单权限';
-- 角色(tbl_netschool_sys_role)
drop table if exists tbl_netschool_sys_role;
create table tbl_netschool_sys_role(
	`id`     varchar(64) not null comment '角色ID',
	`name`   varchar(32) not null comment '角色名称',
	`desc`   varchar(255)         comment '描述信息',
	`status` int default 1        comment '状态(0:禁用,1:启用)',

	constraint pk_tbl_netschool_sys_role primary key(`id`),-- 主键约束
	constraint uk_tbl_netschool_sys_role_name unique key(`name`) -- 唯一约束

) comment '角色';
-- 角色权限(tbl_netschool_sys_role_rights)
drop table if exists tbl_netschool_sys_role_rights;
create table tbl_netschool_sys_role_rights(
	`role_id`         varchar(64) not null  comment '角色ID',
	`menu_right_code` varchar(128) not null comment '菜单权限代码',

	constraint pk_tbl_netschool_sys_role_rights primary key(`role_id`,`menu_right_code`),-- 主键约束
	constraint fk_tbl_netschool_sys_role_rights_role_id foreign key(`role_id`)
	           references tbl_netschool_sys_role(`id`),-- 角色ID外键约束
	constraint fk_tbl_netschool_sys_role_rights_menu_right_code foreign key(`menu_right_code`)
	           references tbl_netschool_sys_menu_rights(`code`)-- 菜单权限代码外键约束
) comment '角色权限';
-- 用户(tbl_netschool_sys_users)
drop table if exists tbl_netschool_sys_users;
create table tbl_netschool_sys_users(
	`id`          varchar(64) not null comment '用户ID',
	`account`     varchar(32) not null comment '用户账号',
	`name`        varchar(32) not null comment '用户名称',
	`password`    varchar(64)          comment '用户密码',
	`nick_name`   varchar(32)          comment '用户昵称',
	`user_type`   int default 0        comment '用户类型：0-系统用户，1-机构用户',
	`phone`       varchar(32)          comment '手机号码',
	`email`       varchar(256)         comment '邮箱地址',
	`img_url`     varchar(256)         comment '头像图片URL',
	`status`      int default 0        comment '状态(0:未激活,1:已激活,-1:已禁用,-2:已删除)',
	`create_time` timestamp default current_timestamp comment '创建时间',
	`last_time`   timestamp default current_timestamp on update current_timestamp comment '最后修改时间',

	constraint pk_tbl_netschool_sys_users primary key(`id`),-- 主键约束
	constraint uk_tbl_netschool_sys_users_account unique key(`account`)-- 账号唯一约束
) comment '用户';
-- 用户角色(tbl_netschool_sys_user_roles)
drop table if exists tbl_netschool_sys_user_roles;
create table tbl_netschool_sys_user_roles(
	`user_id` varchar(64) not null comment '用户ID',
	`role_id` varchar(64) not null comment '角色ID',

	constraint pk_tbl_netschool_sys_user_roles primary key(`user_id`,`role_id`),-- 主键约束
	constraint fk_tbl_netschool_sys_user_roles_user_id foreign key(`user_id`)
	           references tbl_netschool_sys_users(`id`),-- 用户ID外键约束
	constraint fk_tbl_netschool_sys_user_roles_role_id foreign key(`role_id`)
	           references tbl_netschool_sys_role(`id`)-- 角色ID外键约束
) comment '用户角色';
-- 用户登录登录日志(tbl_netschool_sys_user_logs)
drop table if exists tbl_netschool_sys_user_logs;
create table tbl_netschool_sys_user_logs(
	`id`          varchar(64) not null comment '日志ID',
	`user_id`     varchar(64) not null comment '所属用户ID',
	`ip`          varchar(128)         comment '登录IP地址',
	`browser`     text                 comment '浏览器内容',
	`create_time` timestamp default current_timestamp comment '登录时间',

	constraint pk_tbl_netschool_sys_user_logs primary key(`id`),-- 主键约束
	constraint fk_tbl_netschool_sys_user_logs_user_id foreign key(`user_id`)
	           references tbl_netschool_sys_users(`id`)-- 所属用户ID外键约束
) comment '用户登录登录日志';
#----------------------------------------------------------------
-- 删除附件信息
drop table if exists tbl_netschool_sys_attachments;
-- 附件存储(tbl_netschool_sys_attachment_storages)
drop table if exists tbl_netschool_sys_attachment_storages;
create table tbl_netschool_sys_attachment_storages(
	`id`          varchar(64) not null                comment '存储ID(附件摘要码)',
	`size`        bigint default 0                    comment '附件大小(字节)',
	`content`     longblob null                       comment '附件内容(最大4GB)',
	`create_time` timestamp default current_timestamp comment '创建时间',

	constraint pk_tbl_netschool_sys_attachment_storages primary key(`id`)-- 主键约束
) comment '附件存储';
-- 附件信息(tbl_netschool_sys_attachments)
drop table if exists tbl_netschool_sys_attachments;
create table tbl_netschool_sys_attachments(
	`id`   varchar(64) not null  comment '附件ID',
	`name` varchar(256) not null comment '附件名称',
	`ext`  varchar(32)           comment '附件扩展名',
	`type` varchar(64)           comment '附件内容类型(content-Type)',

	`digest_code` varchar(64) not null                comment '摘要码',
	`create_time` timestamp default current_timestamp comment '创建时间',

	constraint pk_tbl_netschool_sys_attachments primary key(`id`),-- 主键约束
	constraint fk_tbl_netschool_sys_attachments_digest_code foreign key(`digest_code`)
	           references tbl_netschool_sys_attachment_storages(`id`)-- 摘要码外键约束
) comment '附件信息';
#----------------------------------------------------------------
-- 删除学员笔记
drop table if exists tbl_netschool_agency_notes;
-- 删除学员建议
drop table if exists tbl_netschool_agency_suggests;
-- 删除答疑主题
drop table if exists tbl_netschool_agency_topics;
-- 删除学习进度
drop table if exists tbl_netschool_agency_learnings;
-- 删除机构套餐试听
drop table if exists tbl_netschool_agency_group_auditions;
-- 删除机构套餐班级
drop table if exists tbl_netschool_agency_group_classes;
-- 删除机构套餐(自定义套餐)
drop table if exists tbl_netschool_agency_groups;
-- 删除机构班级试听资源(自定义班级试听列表)
drop table if exists tbl_netschool_agency_class_auditions;
-- 删除机构班级(自定义班级)
drop table if exists tbl_netschool_agency_classes;
-- 删除机构学员
drop table if exists tbl_netschool_agency_students;
#----------------------------------------------------------------
-- 删除机构考试授权
drop table if exists tbl_netschool_common_agency_exams;
-- 删除套餐班级
drop table if exists tbl_netschool_common_group_classes;
-- 删除套餐地区
drop table if exists tbl_netschool_common_group_areas;
-- 删除套餐
drop table if exists tbl_netschool_common_groups;
-- 删除班级课程章节
drop table if exists tbl_netschool_common_lesson_chapters;
-- 删除班级课程
drop table if exists tbl_netschool_common_lessons;
-- 删除班级产品使用年份
drop table if exists tbl_netschool_common_class_uses;
-- 删除班级产品地区
drop table if exists tbl_netschool_common_class_areas;
-- 删除班级产品
drop table if exists tbl_netschool_common_classes;
-- 删除科目章节
drop table if exists tbl_netschool_common_subject_chapters;
-- 删除考试科目
drop table if exists tbl_netschool_common_exam_subjects;
-- 删除考试有效期设置
drop table if exists tbl_netschool_common_exam_validity;
-- 删除考试地区
drop table if exists tbl_netschool_common_exam_areas;
-- 删除考试设置
drop table if exists tbl_netschool_common_exams;

-- 考试类别(tbl_netschool_common_categories)
drop table if exists tbl_netschool_common_categories;
create table tbl_netschool_common_categories(
	`id`   varchar(64) not null     comment '考试类别ID',
	`code` int default 0 not null   comment '考试类别代码(位数表示层级数)',
	`name` varchar(255) not null    comment '考试类别名称',
	`abbr` varchar(128)             comment '考试类别英文简称',
	`pid`  varchar(64) default null comment '上级考试类别ID',

	constraint pk_tbl_netschool_common_categories primary key(`id`),-- 主键约束
	constraint uk_tbl_netschool_common_categories_code unique key(`code`),-- 代码唯一约束
	constraint fk_tbl_netschool_common_categories_pid foreign key(`pid`)
	          references tbl_netschool_common_categories(`id`)-- 递归约束
) comment '考试类别';
-- 地区设置(tbl_netschool_common_areas)
drop table if exists tbl_netschool_common_areas;
create table tbl_netschool_common_areas(
	`id`   varchar(64) not null     comment '地区ID',
	`code` varchar(10) default '00' comment '地区代码',
	`name` varchar(32) not null     comment '地区名称',
	`abbr` varchar(32) not null     comment '英文简称',

	constraint pk_tbl_netschool_common_areas primary key(`id`),-- 主键约束
	constraint uk_tbl_netschool_common_areas_code unique key(`code`),-- 代码唯一约束
	constraint uk_tbl_netschool_common_areas_name unique key(`name`),-- 名称唯一约束
	constraint uk_tbl_netschool_common_areas_abbr unique key(`abbr`)-- 简称唯一约束
) comment '地区设置';
-- 考试设置(tbl_netschool_common_exams)
drop table if exists tbl_netschool_common_exams;
create table tbl_netschool_common_exams(
	`id`     varchar(64) not null comment '考试ID',
	`code`   int default 0        comment '考试代码',
	`name`   varchar(32) not null comment '考试名称',
	`abbr`   varchar(32) not null comment '考试英文简称',
	`status` int default 0        comment '考试状态(0:禁用,1:启用)',

	`category_id` varchar(64) not null comment '所属考试类别ID',

	constraint pk_tbl_netschool_common_exams primary key(`id`),-- 主键约束
	constraint uk_tbl_netschool_common_exams_code unique key(`code`),-- 代码唯一约束
	constraint uk_tbl_netschool_common_exams_name unique key(`name`),-- 名称唯一约束
	constraint uk_tbl_netschool_common_exams_abbr unique key(`abbr`), -- 简称唯一约束
	constraint fk_tbl_netschool_common_exams_category_id foreign key(`category_id`)
	           references tbl_netschool_common_categories(`id`)-- 考试类别ID外键约束
) comment '考试设置';
-- 考试地区(tbl_netschool_common_exam_areas)
drop table if exists tbl_netschool_common_exam_areas;
create table tbl_netschool_common_exam_areas(
	`exam_id` varchar(64) not null comment '所属考试ID',
	`area_id` varchar(64) not null comment '所属地区ID',

	constraint pk_tbl_netschool_common_exam_areas primary key(`exam_id`,`area_id`),-- 主键约束
	constraint fk_tbl_netschool_common_exam_areas_exam_id foreign key(`exam_id`)
	           references tbl_netschool_common_exams(`id`),-- 所属考试ID外键约束
	constraint fk_tbl_netschool_common_exam_areas_area_id foreign key(`area_id`)
	           references tbl_netschool_common_areas(`id`)-- 所属地区ID外键约束
) comment '考试地区';
-- 考试有效期设置(tbl_netschool_common_exam_validity)
drop table if exists tbl_netschool_common_exam_validity;
create table tbl_netschool_common_exam_validity(
	`exam_id`      varchar(64) not null  comment '所属考试ID',
	`use_year`     int         not null  comment '使用年份(yyyy)',
	`date`         datetime default null comment '考试日期',
	`type`         int default 1         comment '订单有效期类型(1:指定日期,2:固定周期)',
	`expire_date`  datetime default null comment '订单到期指定日期',
	`expire_cycle` int default 0         comment '订单有效期(单位:月)',

	constraint pk_tbl_netschool_common_exam_validity primary key(`exam_id`,`use_year`),-- 主键约束
	constraint fk_tbl_netschool_common_exam_validity_exam_id foreign key(`exam_id`)
	           references tbl_netschool_common_exams(`id`)-- 所属考试ID外键约束
) comment '考试有效期设置';
-- 科目设置(tbl_netschool_common_subjects)
drop table if exists tbl_netschool_common_subjects;
create table tbl_netschool_common_subjects(
	`id`     varchar(64) not null comment '科目ID',
	`code`   int default 0        comment '科目代码',
	`name`   varchar(32) not null comment '科目名称',
	`status` int default 1        comment '状态(0-禁用,1-启用)',

	constraint pk_tbl_netschool_common_subjects primary key(`id`),-- 主键约束
	constraint uk_tbl_netschool_common_subjects_code unique key(`code`),-- 科目代码唯一约束
	constraint uk_tbl_netschool_common_subjects_name unique key(`name`)-- 科目名称唯一约束
) comment '科目设置';
-- 考试科目(tbl_netschool_common_exam_subjects)
drop table if exists tbl_netschool_common_exam_subjects;
create table tbl_netschool_common_exam_subjects(
	`exam_id`    varchar(64) not null comment '所属考试ID',
	`subject_id` varchar(64) not null comment '所属科目ID',

    constraint pk_tbl_netschool_common_exam_subjects primary key(`exam_id`,`subject_id`),-- 主键约束
    constraint fk_tbl_netschool_common_exam_subjects_exam_id foreign key(`exam_id`)
               references tbl_netschool_common_exams(`id`),-- 考试ID外键约束
    constraint fk_tbl_netschool_common_exam_subjects_subject_id foreign key(`subject_id`)
               references tbl_netschool_common_subjects(`id`)-- 科目ID外键约束
) comment '考试科目';
-- 科目章节(tbl_netschool_common_subject_chapters)
drop table if exists tbl_netschool_common_subject_chapters;
create table tbl_netschool_common_subject_chapters(
	`id`   varchar(64) not null     comment '章节ID',
	`name` varchar(256) not null    comment '章节名称',
	`desc` varchar(1024)            comment '描述信息',
	`pid`  varchar(64) default null comment '上级章节ID',

	`subject_id` varchar(64) default null comment '所属科目ID',

	constraint pk_tbl_netschool_common_subject_chapters primary key(`id`),-- 主键约束
	constraint fk_tbl_netschool_common_subject_chapters_subject_id foreign key(`subject_id`)
	           references tbl_netschool_common_subjects(`id`),-- 科目ID外键约束
	constraint fk_tbl_netschool_common_subject_chapters_pid foreign key(`pid`)
	           references tbl_netschool_common_subject_chapters(`id`)-- 上级章节ID外键约束
) comment '科目章节';
-- 班级类型(tbl_netschool_common_class_types)
drop table if exists tbl_netschool_common_class_types;
create table tbl_netschool_common_class_types(
	`id`   varchar(64) not null  comment '班级类型ID',
	`code` int default 0         comment '班级类型代码',
	`name` varchar(128) not null comment '班级类型名称',

	constraint pk_tbl_netschool_common_class_types primary key(`id`),-- 主键约束
	constraint uk_tbl_netschool_common_class_types_code unique key(`code`),-- 班级类型代码唯一约束
	constraint uk_tbl_netschool_common_class_types_name unique key(`name`)-- 班级类型名称唯一约束
) comment '班级类型';
-- 班级产品(tbl_netschool_common_classes)
drop table if exists tbl_netschool_common_classes;
create table tbl_netschool_common_classes(
	`id`          varchar(64) not null   comment '班级产品ID',
	`name`        varchar(255) not null  comment '班级产品名称',
	`type_id`     varchar(64) not null   comment '所属班级类型',
	`img_url`     varchar(1024)          comment '图片地址URL',
	`desc`		  varchar(1024)          comment '班级描述',
	`subject_id`  varchar(64) not null   comment '所属科目ID',
	`price`       decimal(8,2) default 0 comment '基础价格',
	`status`      int default 1          comment '状态(0:禁用,1:启用)',
	`create_time` timestamp default current_timestamp comment '创建时间',
	`last_time`   timestamp default current_timestamp on update current_timestamp comment '最后修改时间',

	constraint pk_tbl_netschool_common_classes primary key(`id`),-- 主键约束
	constraint uk_tbl_netschool_common_classes_name unique key(`name`),-- 产品名称唯一约束
    constraint fk_tbl_netschool_common_classes_type_id foreign key(`type_id`)
               references tbl_netschool_common_class_types(`id`),-- 班级类型ID外键约束
    constraint fk_tbl_netschool_common_classes_subject_id foreign key(`subject_id`)
               references tbl_netschool_common_subjects(`id`)-- 科目ID外键约束
) comment '班级产品';
-- 班级产品地区(tbl_netschool_common_class_areas)
drop table if exists tbl_netschool_common_class_areas;
create table tbl_netschool_common_class_areas(
	`class_id` varchar(64) not null comment '所属班级ID',
	`area_id`  varchar(64) not null comment '所属地区ID',

	constraint pk_tbl_netschool_common_class_areas primary key(`class_id`,`area_id`),-- 主键约束
	constraint fk_tbl_netschool_common_class_areas_class_id foreign key(`class_id`)
	           references tbl_netschool_common_classes(`id`),-- 班级ID外键约束
	constraint fk_tbl_netschool_common_class_areas_area_id foreign key(`area_id`)
	           references tbl_netschool_common_areas(`id`)-- 地区ID外键约束
) comment '班级产品地区';
-- 班级产品使用年份(tbl_netschool_common_class_uses)
drop table if exists tbl_netschool_common_class_uses;
create table tbl_netschool_common_class_uses(
	`class_id`   varchar(64) not null comment '所属班级ID',
	`use_year`   int not null         comment '使用年份(yyyy)',
	`start_date` datetime not null    comment '开班日期(yyyy-MM-dd)',
	`end_date`   datetime not null    comment '结班日期(yyyy-MM-dd)',

	constraint pk_tbl_netschool_common_class_uses primary key(`class_id`,`use_year`),-- 主键约束
	constraint fk_tbl_netschool_common_class_uses_class_id foreign key(`class_id`)
	           references tbl_netschool_common_classes(`id`)-- 班级ID外键约束
) comment '班级产品使用年份';
-- 班级课程(tbl_netschool_common_lessons)
drop table if exists tbl_netschool_common_lessons;
create table tbl_netschool_common_lessons(
	`id`         varchar(64) not null  comment '课时资源ID',
	`name`       varchar(255) not null comment '课时资源名称',
	`desc`       varchar(1024)         comment '课时资源描述',
	`year_group` int default 2015      comment '年份分组',
	`duration`   int default 0         comment '视频时长(秒)',
	`audition`   int default 0         comment '试听(0:不允许,1:允许)',
	`handoutUrl`    varchar(1024)      comment '讲义地址',
	`videoUrl`      varchar(1024)      comment '流畅视频地址',
	`highVideoUrl`  varchar(1024)      comment '高清视频地址',
	`superVideoUrl` varchar(1024)      comment '超清视频地址',
	`order_no`      int default 0      comment '排序',

	`class_id` varchar(64) not null    comment '所属班级ID',

	constraint pk_tbl_netschool_common_lessons primary key(`id`),-- 主键约束
	constraint uk_tbl_netschool_common_lessons_class_id_name unique key(`class_id`,`name`),-- 联合唯一约束
	constraint fk_tbl_netschool_common_lessons_class_id foreign key(`class_id`)
	          references tbl_netschool_common_classes(`id`)-- 班级ID外键约束
) comment '班级课程';
-- 班级课程章节(tbl_netschool_common_lesson_chapters)
drop table if exists tbl_netschool_common_lesson_chapters;
create table tbl_netschool_common_lesson_chapters(
	`lesson_id`   varchar(64) not null comment '所属课时资源ID',
	`chapter_id`  varchar(64) not null comment '所属章节ID',

	constraint pk_tbl_netschool_common_lesson_chapters primary key(`lesson_id`,`chapter_id`),-- 主键约束
	constraint fk_tbl_netschool_common_lesson_chapters_lesson_id foreign key(`lesson_id`)
	           references tbl_netschool_common_lessons(`id`),-- 课时资源ID外键约束
	constraint fk_tbl_netschool_common_lesson_chapters_chapter_id foreign key(`chapter_id`)
	           references tbl_netschool_common_subject_chapters(`id`)-- 章节ID外键约束
) comment '班级课程章节';
-- 套餐类型(tbl_netschool_common_group_types)
drop table if exists tbl_netschool_common_group_types;
create table tbl_netschool_common_group_types(
	`id`       varchar(64) not null   comment '套餐类型ID',
	`code`     int default 0          comment '套餐类型代码',
	`name`     varchar(255) not null  comment '套餐类型名称',
	`discount` float(8,2) default 1.0 comment '结算折扣(默认:1.0)',
	`extended` int default 0          comment '订单有效期延长(单位:月)',
	`status`      int default 1       comment '状态(0:禁用,1:启用)',
	`create_time` timestamp default current_timestamp comment '创建时间',
	`last_time`   timestamp default current_timestamp on update current_timestamp comment '最后修改时间',

	constraint pk_tbl_netschool_common_group_types primary key(`id`),-- 主键约束
	constraint uk_tbl_netschool_common_group_types_code unique key(`code`),-- 类型代码唯一约束
	constraint uk_tbl_netschool_common_group_types_name unique key(`name`)-- 类型名称唯一约束
) comment '套餐类型';
-- 套餐(tbl_netschool_common_groups)
drop table if exists tbl_netschool_common_groups;
create table tbl_netschool_common_groups(
	`id`   varchar(64) not null    comment '套餐ID',
	`name` varchar(255) not null   comment '套餐名称',
	`desc` varchar(256)            comment '菜单描述',

	`img_url`  varchar(1024)        comment '宣传图片',
	`type_id`  varchar(64) not null comment '所属套餐类型',
	`use_year` int default 2015     comment '使用年份',

	`start_date` datetime default null comment '开班时间',
	`end_date`   datetime default null comment '结班时间',

	`order_no`    int default 0        comment '排序',
	`status`      int default 1        comment '状态(0:禁用,1:启用)',

	`create_time` timestamp default current_timestamp comment '创建时间',
	`last_time`   timestamp default current_timestamp on update current_timestamp comment '最后修改时间',

	constraint pk_tbl_netschool_common_groups primary key(`id`),-- 主键约束
	constraint uk_tbl_netschool_common_groups_name unique key(`name`),-- 套餐名称唯一约束
	constraint fk_tbl_netschool_common_groups_type_id foreign key(`type_id`)
	           references tbl_netschool_common_group_types(`id`)-- 套餐类型ID外键约束
) comment '套餐';
-- 套餐地区(tbl_netschool_common_group_areas)
drop table if exists tbl_netschool_common_group_areas;
create table tbl_netschool_common_group_areas(
	`group_id` varchar(64) not null comment '所属套餐ID',
	`area_id`  varchar(64) not null comment '所属地区ID',

	constraint pk_tbl_netschool_common_group_areas primary key(`group_id`,`area_id`),-- 主键约束
	constraint fk_tbl_netschool_common_group_areas_group_id foreign key(`group_id`)
	           references tbl_netschool_common_groups(`id`),-- 套餐ID外键约束
	constraint fk_tbl_netschool_common_group_areas_area_id foreign key(`area_id`)
               references tbl_netschool_common_areas(`id`)-- 地区ID外键约束
) comment '套餐地区';
-- 套餐班级(tbl_netschool_common_group_classes)
drop table if exists tbl_netschool_common_group_classes;
create table tbl_netschool_common_group_classes(
	`group_id` varchar(64) not null comment '所属套餐ID',
	`class_id` varchar(64) not null comment '所属班级ID',

	constraint pk_tbl_netschool_common_group_classes primary key(`group_id`,`class_id`),-- 主键约束
	constraint fk_tbl_netschool_common_group_classes_group_id foreign key(`group_id`)
	           references tbl_netschool_common_groups(`id`),-- 套餐ID外键约束
	constraint fk_tbl_netschool_common_group_classes_class_id foreign key(`class_id`)
	           references tbl_netschool_common_classes(`id`)-- 班级ID外键约束
) comment '套餐班级';
-- 机构设置(tbl_netschool_common_agencies)
drop table if exists tbl_netschool_common_agencies;
create table tbl_netschool_common_agencies(
	`id`         varchar(64) not null   comment '机构ID',
	`name`       varchar(255) not null  comment '机构名称',
	`abbr_cn`    varchar(32) not null   comment '中文简称',
	`abbr_en`    varchar(64) not null   comment '英文简称',
	`login_icon` varchar(1024)          comment '登录图标URL',
	`logo_icon`  varchar(1024)          comment '机构图标URL',
	`video_icon` varchar(1024)          comment '视频图标URL',
	`head_video` varchar(1024)          comment '片头视频URL',
	`host_url`   varchar(1024)          comment '机构网址',
	`discount`   float(8,2) default 1.0 comment '合同折扣',
	`audition_total` int default 0      comment '试听数量',
	`token`		 varchar(32)            comment '令牌密钥',
	`contact`    varchar(32)            comment '联系人',
	`tel`        varchar(32)            comment '联系电话',
	`address`    varchar(512)           comment '联系地址',
	`email`      varchar(256)           comment '邮件地址',
	`template_header` text              comment '顶部模版',
	`template_footer` text              comment '底部模版',
	`status`     int default 1          comment '状态(0:禁用,1:启用)',

	`create_time` timestamp default current_timestamp comment '创建时间',
	`last_time`   timestamp default current_timestamp on update current_timestamp comment '最后修改时间',

	constraint pk_tbl_netschool_common_agencies primary key(`id`),-- 主键约束
	constraint uk_tbl_netschool_common_agencies_name unique key(`name`),-- 机构名称唯一约束
	constraint uk_tbl_netschool_common_agencies_abbr_cn unique key(`abbr_cn`),-- 中文简称唯一约束
	constraint uk_tbl_netschool_common_agencies_abbr_en unique key(`abbr_en`)-- 英文简称唯一约束
) comment '机构设置';
-- 机构用户(tbl_netschool_common_agency_users)
drop table if exists tbl_netschool_common_agency_users;
create table tbl_netschool_common_agency_users(
	`agency_id` varchar(64) not null comment '所属机构ID',
	`user_id`   varchar(64) not null comment '所属用户ID',

	constraint pk_tbl_netschool_common_agency_users primary key(`agency_id`,`user_id`),-- 主键约束
	constraint fk_tbl_netschool_common_agency_users_agency_id foreign key(`agency_id`)
	           references tbl_netschool_common_agencies(`id`),-- 机构ID外键约束
	constraint fk_tbl_netschool_common_agency_users_user_id foreign key(`user_id`)
	           references tbl_netschool_sys_users(`id`)-- 用户ID外键约束
) comment '机构用户';
-- 机构考试授权(tbl_netschool_common_agency_exams)
drop table if exists tbl_netschool_common_agency_exams;
create table tbl_netschool_common_agency_exams(
	`agency_id` varchar(64) not null comment '所属机构ID',
	`exam_id`   varchar(64) not null comment '所属考试ID',

	constraint pk_tbl_netschool_common_agency_exams primary key(`agency_id`,`exam_id`),-- 主键约束
	constraint fk_tbl_netschool_common_agency_exams_agency_id foreign key(`agency_id`)
	           references tbl_netschool_common_agencies(`id`),-- 机构ID外键约束
	constraint fk_tbl_netschool_common_agency_exams_exam_id foreign key(`exam_id`)
	           references tbl_netschool_common_exams(`id`) -- 考试ID外键约束
) comment '机构考试授权';
-- 机构充值(tbl_netschool_common_agency_charges)
drop table if exists tbl_netschool_common_agency_charges;
create table tbl_netschool_common_agency_charges(
	`id`        varchar(64) not null   comment '充值ID',
	`code`      varchar(128) not null  comment '充值序号',
	`agency_id` varchar(64) not null   comment '所属机构',
	`type`      int default 1          comment '类型(1:现金,2:优惠)',
	`recharge`  decimal(8,2) default 0 comment '充值金额',
	`status`    int default 0          comment '状态(0:已充值,1:已激活)',
	`invoice`   int default 0          comment '发票(0:未开票,1:已开票,-1:无票)',
	`operator`  varchar(64) not null   comment '操作用户',
	`create_time` timestamp default current_timestamp comment '充值时间',
	`last_time`   timestamp default current_timestamp on update current_timestamp comment '最后修改时间',

	constraint pk_tbl_netschool_common_agency_charges primary key(`id`),-- 主键约束
	constraint uk_tbl_netschool_common_agency_charges_code unique key(`code`),-- 充值序列号唯一约束
	constraint fk_tbl_netschool_common_agency_charges_agency_id foreign key(`agency_id`)
	           references tbl_netschool_common_agencies(`id`),-- 机构ID外键约束
	constraint fk_tbl_netschool_common_agency_charges_operator foreign key(`operator`)
	           references tbl_netschool_sys_users(`id`)-- 操作用户ID外键约束
) comment '机构充值';
#----------------------------------------------------------------
-- 机构学员(tbl_netschool_agency_students)
drop table if exists tbl_netschool_agency_students;
create table tbl_netschool_agency_students(
	`id`        varchar(64) not null comment '学员ID',
	`account`   varchar(32) not null comment '学员账号',
	`name`      varchar(32) not null comment '学员姓名',
	`password`  varchar(512)         comment '学员密码',
	`agency_id` varchar(64) not null comment '所属机构ID',

	`status`     int default 1       comment '状态(0:禁用,1:启用,-1:已删除)',

	`create_time` timestamp default current_timestamp comment '创建时间',
	`last_time`   timestamp default current_timestamp on update current_timestamp comment '最后修改时间',

	constraint pk_tbl_netschool_agency_students primary key(`id`),-- 主键约束
	constraint uk_tbl_netschool_agency_students_agency_id_account unique key(`agency_id`,`account`),-- 机构账/号唯一约束
	constraint fk_tbl_netschool_agency_students_agency_id foreign key(`agency_id`)
	           references tbl_netschool_common_agencies(`id`)-- 机构ID外键约束
) comment '机构学员';
-- 机构班级(tbl_netschool_agency_classes)
drop table if exists tbl_netschool_agency_classes;
create table tbl_netschool_agency_classes(
	`id`         varchar(64) not null    comment '自定义班级ID',
	`name`       varchar(255) not null   comment '自定义班级名称',
	`desc`       varchar(512)            comment '自定义班级描述',
	`img_url`    varchar(1024)           comment '宣传图片URL',
	`class_id`   varchar(64) not null    comment '原始班级ID',
	`agency_id`  varchar(64) not null    comment '所属机构',
	`use_year`   int default 2015        comment '使用年份',
	`start_date` datetime default null   comment '开班时间',
	`end_date`   datetime default null   comment '结班时间',
	`sale_price` decimal(8,2) default 0  comment '销售价格',
	`pref_price` decimal(8,2) default -1 comment '优惠价格',
	`order_no`   int default 0           comment '排序',
	`status`     int default 1           comment '状态(0:禁用,1:启用)',

	`create_time` timestamp default current_timestamp comment '创建时间',
	`last_time`   timestamp default current_timestamp on update current_timestamp comment '最后修改时间',

	constraint pk_tbl_netschool_agency_classes primary key(`id`),-- 主键约束
	constraint uk_tbl_netschool_agency_classes_agency_id_name unique key(`agency_id`,`name`),-- 机构班级名称唯一约束
	constraint fk_tbl_netschool_agency_classes_class_id foreign key(`class_id`)
	           references tbl_netschool_common_classes(`id`),-- 原始班级ID外键约束
	constraint fk_tbl_netschool_agency_classes_agency_id foreign key(`agency_id`)
	           references tbl_netschool_common_agencies(`id`)-- 机构ID外键约束
) comment '机构班级';
-- 机构班级试听资源(tbl_netschool_agency_class_auditions)
drop table if exists tbl_netschool_agency_class_auditions;
create table tbl_netschool_agency_class_auditions(
	`class_id`  varchar(64) not null comment '自定义班级ID',
	`lesson_id` varchar(64) not null comment '课程资源ID',

	constraint pk_tbl_netschool_agency_class_auditions primary key(`class_id`,`lesson_id`),-- 主键约束
	constraint fk_tbl_netschool_agency_class_auditions_class_id foreign key(`class_id`)
	           references tbl_netschool_agency_classes(`id`),-- 自定义班级ID外键约束
	constraint fk_tbl_netschool_agency_class_auditions_lesson_id foreign key(`lesson_id`)
	           references tbl_netschool_common_lessons(`id`)-- 课时资源ID外键约束
) comment '机构班级试听资源';
-- 机构套餐(tbl_netschool_agency_groups)
drop table if exists tbl_netschool_agency_groups;
create table tbl_netschool_agency_groups(
	`id`         varchar(64) not null    comment '自定义套餐ID',
	`name`       varchar(255) not null   comment '自定义套餐名称',
	`type_id`    varchar(64) not null    comment '所属套餐类型ID',
	`desc`       varchar(512)            comment '自定义套餐描述',
	`img_url`    varchar(1024)           comment '宣传图片URL',
	`agency_id`  varchar(64) not null    comment '所属机构ID',
	`exam_id`    varchar(64) not null    comment '所属考试ID',
	`use_year`   int default 2015        comment '使用年份',
	`start_date` datetime default null   comment '开班时间',
	`end_date`   datetime default null   comment '结班时间',
	`sale_price` decimal(8,2) default 0  comment '销售价格',
	`pref_price` decimal(8,2) default -1 comment '优惠价格',
	`order_no`   int default 0           comment '排序',
	`status`     int default 1           comment '状态(0:禁用,1:启用)',

	`create_time` timestamp default current_timestamp comment '创建时间',
	`last_time`   timestamp default current_timestamp on update current_timestamp comment '最后修改时间',

	constraint pk_tbl_netschool_agency_groups primary key(`id`),-- 主键约束
	constraint uk_tbl_netschool_agency_groups_agency_id_name unique key(`agency_id`,`name`),-- 机构套餐名称唯一约束
	constraint fk_tbl_netschool_agency_groups_type_id foreign key(`type_id`)
	           references tbl_netschool_common_group_types(`id`),-- 套餐类型ID外键约束
	constraint fk_tbl_netschool_agency_groups_agency_id foreign key(`agency_id`)
	           references tbl_netschool_common_agencies(`id`),-- 所属机构ID外键约束
	constraint fk_tbl_netschool_agency_groups_exam_id foreign key(`exam_id`)
	           references tbl_netschool_common_exams(`id`) -- 所属考试ID外键约束
) comment '机构套餐';
-- 机构套餐班级(tbl_netschool_agency_group_classes)
drop table if exists tbl_netschool_agency_group_classes;
create table tbl_netschool_agency_group_classes(
	`group_id` varchar(64) not null comment '所属机构套餐ID',
	`class_id` varchar(64) not null comment '所属机构班级ID',

	constraint pk_tbl_netschool_agency_group_classes primary key(`group_id`,`class_id`),-- 主键约束
	constraint fk_tbl_netschool_agency_group_classes_group_id foreign key(`group_id`)
	           references tbl_netschool_agency_groups(`id`),-- 机构套餐ID外键约束
	constraint fk_tbl_netschool_agency_group_classes_class_id foreign key(`class_id`)
	           references tbl_netschool_agency_classes(`id`) -- 机构班级ID外键约束
) comment '机构套餐班级';
-- 机构套餐试听(tbl_netschool_agency_group_auditions)
drop table if exists tbl_netschool_agency_group_auditions;
create table tbl_netschool_agency_group_auditions(
	`group_id`  varchar(64) not null comment '所属机构套餐ID',
	`lesson_id` varchar(64) not null comment '所属课程资源ID',

	constraint pk_tbl_netschool_agency_group_auditions primary key(`group_id`,`lesson_id`),-- 主键约束
	constraint fk_tbl_netschool_agency_group_auditions_group_id foreign key(`group_id`)
	           references tbl_netschool_agency_groups(`id`),-- 机构套餐ID外键约束
	constraint fk_tbl_netschool_agency_group_auditions_lesson_id foreign key(`lesson_id`)
	           references tbl_netschool_common_lessons(`id`)-- 课时资源ID外键约束
) comment '机构套餐试听';
-- 机构订单(tbl_netschool_agency_orders)
drop table if exists tbl_netschool_agency_orders;
create table tbl_netschool_agency_orders(
	`id`           varchar(64)  not null    comment '订单ID',
	`code`         varchar(128) not null    comment '订单号',
	`agency_id`    varchar(64) not null     comment '所属机构ID',
	`exam_id`      varchar(64) not null     comment '所属考试ID',
	`type`         int default 1            comment '类型(1:班级产品,2:套餐产品)',
	`class_id`     varchar(64) default null comment '关联自定义班级ID',
	`group_id`     varchar(64) default null comment '关联自定义套餐ID',
	`final_price`  decimal(8,2) default 0   comment '结算价格',
	`sale_price`   decimal(8,2) default 0   comment '销售价格',
	`begin_date`   datetime default null    comment '生效时间',
	`expired_date` datetime default null    comment '过期时间',
	`student_id`   varchar(64) not null     comment '关联学员ID',
	`operator`     varchar(64) not null     comment '操作用户',
	`status`       int default 0            comment '状态(0:已创建,1:有效期,2:已过期)',
	`create_time`  timestamp default current_timestamp comment '创建时间',

	constraint pk_tbl_netschool_agency_orders primary key(`id`),-- 主键约束
	constraint uk_tbl_netschool_agency_orders_agency_id_code unique key(`agency_id`,`code`),-- 机构订单唯一约束
	constraint fk_tbl_netschool_agency_orders_agency_id foreign key(`agency_id`)
	           references tbl_netschool_common_agencies(`id`),-- 所属机构ID外键约束
	constraint fk_tbl_netschool_agency_orders_exam_id foreign key(`exam_id`)
	           references tbl_netschool_common_exams(`id`),-- 所属机构ID外键约束
	constraint fk_tbl_netschool_agency_orders_class_id foreign key(`class_id`)
	           references tbl_netschool_agency_classes(`id`),-- 关联自定义班级ID外键约束
	constraint fk_tbl_netschool_agency_orders_group_id foreign key(`group_id`)
	           references tbl_netschool_agency_groups(`id`),-- 关联自定义套餐ID外键约束
	constraint fk_tbl_netschool_agency_orders_student_id foreign key(`student_id`)
	           references tbl_netschool_agency_students(`id`),-- 关联学员ID外键约束
	constraint fk_tbl_netschool_agency_orders_operator foreign key(`operator`)
	           references tbl_netschool_sys_users(`id`)-- 操作用户外键约束
) comment '机构订单';
-- 学习进度(tbl_netschool_agency_learnings)
drop table if exists tbl_netschool_agency_learnings;
create table tbl_netschool_agency_learnings(
	`student_id` varchar(64) not null comment '所属学员ID',
	`lesson_id`  varchar(64) not null comment '所属课程资源ID',
	`status`     int default 0        comment '状态(0:未学完,1:已学完)',
	`pos`        int default 0        comment '观看位置(单位:秒)',

	`create_time`  timestamp default current_timestamp comment '创建时间',
	`last_time`   timestamp default current_timestamp on update current_timestamp comment '最后修改时间',

	constraint pk_tbl_netschool_agency_learnings primary key(`student_id`,`lesson_id`),-- 主键约束
	constraint fk_tbl_netschool_agency_learnings_student_id foreign key(`student_id`)
	           references tbl_netschool_agency_students(`id`),-- 所属学员ID外键约束
	constraint fk_tbl_netschool_agency_learnings_lesson_id foreign key(`lesson_id`)
	           references tbl_netschool_common_lessons(`id`)-- 所属课程资源ID外键约束
) comment '学习进度';
-- 答疑主题(tbl_netschool_agency_topics)
drop table if exists tbl_netschool_agency_topics;
create table tbl_netschool_agency_topics(
	`id`         varchar(64) not null  comment '主题ID',
	`title`      varchar(512) not null comment '主题标题',
	`content`    text                  comment '主题内容',
	`lesson_id`  varchar(64) not null  comment '所属课程资源ID',
	`student_id` varchar(64) not null  comment '所属学员ID',

	`create_time`  timestamp default current_timestamp comment '创建时间',

	constraint pk_tbl_netschool_agency_topics primary key(`id`),-- 主键约束
	constraint fk_tbl_netschool_agency_topics_lesson_id foreign key(`lesson_id`)
	           references tbl_netschool_common_lessons(`id`),-- 所属课程资源ID外键约束
	constraint fk_tbl_netschool_agency_topics_student_id foreign key(`student_id`)
	           references tbl_netschool_agency_students(`id`)-- 所属学员ID外键约束
) comment '答疑主题';
-- 答疑明细(tbl_netschool_agency_details)
drop table if exists tbl_netschool_agency_details;
create table tbl_netschool_agency_details(
	`id`       varchar(64) not null comment '答疑明细ID',
	`content`  text                 comment '答疑明细内容',
	`topic_id` varchar(64) not null comment '所属答疑主题ID',

	`user_id`    varchar(64) default null comment '回复系统用户ID',
	`student_id` varchar(64) default null comment '回复学员ID',

	`create_time`  timestamp default current_timestamp comment '创建时间',

	constraint pk_tbl_netschool_agency_details primary key(`id`),-- 主键约束
	constraint fk_tbl_netschool_agency_details_topic_id foreign key(`topic_id`)
	           references tbl_netschool_agency_topics(`id`),-- 答疑主题ID外键约束
	constraint fk_tbl_netschool_agency_details_user_id foreign key(`user_id`)
	           references tbl_netschool_sys_users(`id`),-- 系统用户ID外键约束
	constraint fk_tbl_netschool_agency_details_student_id foreign key(`student_id`)
	           references tbl_netschool_agency_students(`id`)-- 学员ID外键约束
) comment '答疑明细';
-- 学员建议(tbl_netschool_agency_suggests)
drop table if exists tbl_netschool_agency_suggests;
create table tbl_netschool_agency_suggests(
	`id`         varchar(64) not null comment '建议ID',
	`content`    text                 comment '建议内容',
	`student_id` varchar(64) not null comment '所属学员ID',

	`create_time`  timestamp default current_timestamp comment '创建时间',

	constraint pk_tbl_netschool_agency_suggests primary key(`id`),-- 主键约束
	constraint fk_tbl_netschool_agency_suggests_student_id foreign key(`student_id`)
	           references tbl_netschool_agency_students(`id`)-- 学员ID外键约束
) comment '学员建议';
-- 随堂笔记(tbl_netschool_agency_notes)
drop table if exists tbl_netschool_agency_notes;
create table tbl_netschool_agency_notes(
	`id`         varchar(64) not null comment '笔记ID',
	`lesson_id`  varchar(64) not null comment '课时资源ID',
	`duration`   int default 0        comment '播放时刻(单位:秒)',
	`content`    varchar(1024)        comment '笔记内容',
	`student_id` varchar(64) not null comment '所属学员ID',

	`create_time` timestamp default current_timestamp comment '创建时间',
	`last_time`   timestamp default current_timestamp on update current_timestamp comment '最后修改时间',

	constraint pk_tbl_netschool_agency_notes primary key(`id`),-- 主键约束
	constraint fk_tbl_netschool_agency_notes_lesson_id foreign key(`lesson_id`)
	           references tbl_netschool_common_lessons(`id`), -- 课时资源ID外键约束
	constraint fk_tbl_netschool_agency_notes_student_id foreign key(`student_id`)
	           references tbl_netschool_agency_students(`id`) -- 所属学员ID外键约束
) comment '随堂笔记';
-- 机构消息(tbl_netschool_agency_messages)
drop table if exists tbl_netschool_agency_messages;
create table tbl_netschool_agency_messages(
	`id`      varchar(64) not null  comment '消息ID',
	`title`   varchar(256) not null comment '消息标题',
	`type`    int default 1         comment '类型(1:系统消息,2:机构消息)',
	`content` varchar(1024)         comment '消息内容',

	`agency_id` varchar(64) default null comment '所属机构ID',
	`operator`  varchar(64) not null     comment '操作用户',

	`create_time` timestamp default current_timestamp comment '创建时间',
	`last_time`   timestamp default current_timestamp on update current_timestamp comment '最后修改时间',

	constraint pk_tbl_netschool_agency_messages primary key(`id`),-- 主键约束
	constraint fk_tbl_netschool_agency_messages_agency_id foreign key(`agency_id`)
	           references tbl_netschool_common_agencies(`id`), -- 机构ID外键约束
	constraint fk_tbl_netschool_agency_messages_operator foreign key(`operator`)
	           references tbl_netschool_sys_users(`id`)-- 操作用户ID外键约束
) comment '机构消息';
-- 机构消息学员(tbl_netschool_agency_message_students)
drop table if exists tbl_netschool_agency_message_students;
create table tbl_netschool_agency_message_students(
	`student_id` varchar(64) not null comment '学员ID',
	`msg_id`     varchar(64) not null comment '消息ID',
	`status`     int default 0        comment '状态(0:未读,1:已读)',

	`create_time` timestamp default current_timestamp comment '创建时间',
	`last_time`   timestamp default current_timestamp on update current_timestamp comment '最后修改时间',

	constraint pk_tbl_netschool_agency_message_students primary key(`student_id`,`msg_id`),-- 主键约束
	constraint fk_tbl_netschool_agency_message_students_student_id foreign key(`student_id`)
	           references tbl_netschool_agency_students(`id`), -- 学员ID外键约束
	constraint fk_tbl_netschool_agency_message_students_msg_id foreign key(`msg_id`)
	           references tbl_netschool_agency_messages(`id`)-- 消息ID外键约束
) comment '机构消息学员';
#----------------------------------------------------------------
