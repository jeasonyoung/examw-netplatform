#----------------------------------------------------------------------------------------------
#--网校系统－基础设置模块表结构
#----------------------------------------------------------------------------------------------
#-- 须前置删除的关联关系表结构
drop table if exists tbl_Netplatform_Courses_LessonChapters;-- 课时资源章节
drop table if exists tbl_Netplatform_Courses_Lessons;-- 课时资源
drop table if exists tbl_Netplatform_Courses_PackageClasses;-- 套餐班级
drop table if exists tbl_Netplatform_Courses_ClassAreas;-- 班级地区
drop table if exists tbl_Netplatform_Courses_Classes;-- 班级

drop table if exists tbl_Netplatform_Settings_Chapters;-- 章节
drop table if exists tbl_Netplatform_Settings_SubjectAreas;-- 科目地区
drop table if exists tbl_Netplatform_Settings_Subjects;-- 科目
drop table if exists tbl_Netplatform_Settings_ExamAreas;-- 考试地区
drop table if exists tbl_Netplatform_Courses_Packages;-- 套餐
drop table if exists tbl_Netplatform_Settings_Exams;-- 考试

drop table if exists tbl_Netplatform_Settings_ClassTypes;-- 班级类型

drop table if exists tbl_Netplatform_Settings_MsgUsers;-- 消息用户
drop table if exists tbl_Netplatform_Settings_MsgBody;-- 消息内容
drop table if exists tbl_Netplatform_Courses_Notes;-- 随堂笔记
#----------------------------------------------------------------------------------------------
-- 考试分类(tbl_Netplatform_Settings_Categories)
drop table if exists tbl_Netplatform_Settings_Categories;
create table tbl_Netplatform_Settings_Categories(
	`id`		varchar(64) NOT NULL,-- 考试类别ID
	`code`		int	default 0,-- 考试类别代码
	`name`		varchar(64) NOT NULL,-- 考试类别名称
	`abbr`		varchar(10) NOT NULL,-- 考试类别EN简称
	
	`pid`		varchar(64),-- 上级考试类别ID
	
	constraint pk_tbl_Netplatform_Settings_Categories primary key(`id`),-- 主键约束
	constraint uk_tbl_Netplatform_Settings_Categories_code unique key(`code`),-- 考试类别代码唯一约束
	constraint uk_tbl_Netplatform_Settings_Categories_name unique key(`name`),-- 考试类别名称唯一约束
	constraint uk_tbl_Netplatform_Settings_Categories_abbr unique key(`abbr`),-- 考试类别EN简称唯一约束
	constraint fk_tbl_Netplatform_Settings_Categories_pid foreign key(`pid`) references tbl_Netplatform_Settings_Categories(`id`)-- 上级考试类别ID外键约束
);
-- 考试(tbl_Netplatform_Settings_Exams)
drop table if exists tbl_Netplatform_Settings_Exams;
create table tbl_Netplatform_Settings_Exams(
	`id`			varchar(64) NOT NULL,-- 考试ID
	`code`			int	default 0,-- 考试代码
	`name`			varchar(32) NOT NULL,-- 考试名称
	`abbr`			varchar(10) NOT NULL,-- 考试EN简称
	`status`		int	default 0,-- 考试状态:0-停用,1-启用
	
	`category_id`	varchar(64) NOT NULL,-- 所属考试类别ID
	
	constraint pk_tbl_Netplatform_Settings_Exams primary key(`id`),-- 主键约束
	constraint uk_tbl_Netplatform_Settings_Exams_code unique key(`code`),-- 考试代码唯一约束
	constraint uk_tbl_Netplatform_Settings_Exams_abbr unique key(`abbr`),-- EN简称唯一约束
	constraint fk_tbl_Netplatform_Settings_Exams_category_id foreign key(`category_id`) references tbl_Netplatform_Settings_Categories(`id`)-- 所属考试类别ID外键约束
);
-- 科目(tbl_Netplatform_Settings_Subjects)
drop table if exists tbl_Netplatform_Settings_Subjects;
create table tbl_Netplatform_Settings_Subjects(
	`id`		varchar(64) NOT NULL,-- 科目ID
	`code` 		int default 0,-- 科目代码
	`name`		varchar(32) NOT NULL,-- 科目名称
	`status`	int default 0,-- 科目状态:0-停用,1-启用
	
	`exam_id`	varchar(64) NOT NULL,-- 所属考试ID
	
	constraint pk_tbl_Netplatform_Settings_Subjects primary key(`id`),-- 主键约束
	constraint uk_tbl_Netplatform_Settings_Subjects_code unique key(`code`),-- 科目代码唯一约束
	constraint fk_tbl_Netplatform_Settings_Subjects_exam_id foreign key(`exam_id`) references tbl_Netplatform_Settings_Exams(`id`)-- 所属考试ID外键约束
);
-- 地区设置(tbl_Netplatform_Settings_Areas)
drop table if exists tbl_Netplatform_Settings_Areas;
create table tbl_Netplatform_Settings_Areas(
	`id`	varchar(64) NOT NULL,-- 地区ID
	`code`	int	default 0,-- 地区代码
	`name`	varchar(32) NOT NULL,-- 地区名称
	`abbr`	varchar(10) NOT NULL,-- EN简称
	
	constraint pk_tbl_Netplatform_Settings_Areas primary key(`id`),-- 主键约束
	constraint uk_tbl_Netplatform_Settings_Areas_code unique key(`code`),-- 地区代码唯一约束
	constraint uk_tbl_Netplatform_Settings_Areas_abbr unique key(`abbr`)-- EN简称唯一约束
);
-- 考试地区(tbl_Netplatform_Settings_ExamAreas)
drop table if exists tbl_Netplatform_Settings_ExamAreas;
create table tbl_Netplatform_Settings_ExamAreas(
	`exam_id`	varchar(64) NOT NULL,-- 考试ID
	`area_id`	varchar(64) NOT NULL,-- 地区ID
	
	constraint pk_tbl_Netplatform_Settings_ExamAreas primary key(`exam_id`,`area_id`),-- 主键约束
	constraint fk_tbl_Netplatform_Settings_ExamAreas_exam_id foreign key(`exam_id`) references tbl_Netplatform_Settings_Exams(`id`),-- 所属考试ID外键约束
	constraint fk_tbl_Netplatform_Settings_ExamAreas_area_id foreign key(`area_id`) references tbl_Netplatform_Settings_Areas(`id`)-- 所属地区ID外键约束
);
-- 科目地区(tbl_Netplatform_Settings_SubjectAreas)
drop table if exists tbl_Netplatform_Settings_SubjectAreas;
create table tbl_Netplatform_Settings_SubjectAreas(
	`subject_id`	varchar(64) NOT NULL,-- 科目ID
	`area_id`		varchar(64) NOT NULL,-- 地区ID
	
	constraint pk_tbl_Netplatform_Settings_SubjectAreas primary key(`subject_id`,`area_id`),-- 主键约束
	constraint fk_tbl_Netplatform_Settings_SubjectAreas_subject_id foreign key(`subject_id`) references tbl_Netplatform_Settings_Subjects(`id`),-- 所属科目ID外键约束
	constraint fk_tbl_Netplatform_Settings_SubjectAreas_area_id foreign key(`area_id`) references tbl_Netplatform_Settings_Areas(`id`)-- 所属地区ID外键约束
);
-- 章节(tbl_Netplatform_Settings_Chapters)
drop table if exists tbl_Netplatform_Settings_Chapters;
create table tbl_Netplatform_Settings_Chapters(
	`id`			varchar(64) NOT NULL,-- 章节ID
	`name`			varchar(128) NOT NULL,-- 章节名称
	`description`	varchar(1024),-- 章节描述
	`status`		int default 0,-- 状态:0-停用,1-启用
	`orderNo`		int default 0,-- 排序号
	
	`subject_id`	varchar(64) NULL,-- 所属科目ID
	
	`pid`			varchar(64) NULL,-- 上级章节ID
	
	constraint pk_tbl_Netplatform_Settings_Chapters primary key(`id`),-- 主键约束
	constraint fk_tbl_Netplatform_Settings_Chapters_subject_id foreign key(`subject_id`) references tbl_Netplatform_Settings_Subjects(`id`),-- 所属科目ID外键约束
	constraint fk_tbl_Netplatform_Settings_Chapters_pid foreign key(`pid`) references tbl_Netplatform_Settings_Chapters(`id`)-- 上级章节ID外键约束
);
-- 培训机构(tbl_Netplatform_Settings_Agencies)
drop table if exists tbl_Netplatform_Settings_Agencies;
create table tbl_Netplatform_Settings_Agencies(
	`id`			varchar(64) NOT NULL,-- 培训机构ID
	`name`			varchar(100) NOT NULL,-- 培训机构名称
	`abbr_cn`		varchar(20) NOT NULL,-- 中文简称
	`abbr_en`		varchar(15) NOT NULL,-- 英文简称

	`host`			varchar(1024),-- 域名地址
	
	`keywords` 		varchar(45),-- 关键字
	`address`		varchar(512),-- 培训机构地址
	`tel`			varchar(45),-- 联系电话
	`fax`			varchar(45),-- 传真号码 
	`introduction`	text,-- 机构简介
	`remarks`		text,-- 备注
	`logo_url`		varchar(1024),-- logoURL
	`status`		int default 0,-- 机构状态:0-停用,1-启用
	
	`package_count`	int default -1,-- 套餐上限:-1-无上限
	`account_count`	int default -1,-- 用户上限:-1-无上限
	
	`createTime`	timestamp default CURRENT_TIMESTAMP,-- 创建时间
	`lastTime`		timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,-- 最新修改时间
	
	constraint pk_tbl_Netplatform_Settings_Agencies primary key(`id`),-- 主键约束
	constraint uk_tbl_Netplatform_Settings_Agencies_abbr_cn unique key(`abbr_cn`),-- 中文简称唯一约束
	constraint uk_tbl_Netplatform_Settings_Agencies_abbr_en unique key(`abbr_en`)-- 英文简称唯一约束
);
-- 用户机构(tbl_Netplatform_Settings_UserAgencies)
drop table if exists tbl_Netplatform_Settings_UserAgencies;
create table tbl_Netplatform_Settings_UserAgencies(
	`user_id`		varchar(64) NOT NULL,-- 所属用户ID
	`agency_id`		varchar(64) NOT NULL,-- 所属机构ID
	
	`identity`		int	default 0,-- 机构用户身份(0:未知,1:管理员,2:教师,3:学员)
	
	`createTime`	timestamp default CURRENT_TIMESTAMP,-- 创建时间
	
	constraint pk_tbl_Netplatform_Settings_AgencyUsers primary key(`user_id`,`agency_id`),-- 主键约束
	constraint fk_tbl_Netplatform_Settings_UserAgencies_user_id foreign key(`user_id`) references tbl_Netplatform_Security_Users(`id`),-- 所属用户ID外键约束
	constraint fk_tbl_Netplatform_Settings_UserAgencies_agency_id foreign key(`agency_id`) references tbl_Netplatform_Settings_Agencies(`id`)-- 所属结构ID外键约束
);
-- 班级类型(tbl_Netplatform_Settings_ClassTypes)
drop table if exists tbl_Netplatform_Settings_ClassTypes;
create table tbl_Netplatform_Settings_ClassTypes(
	`id`		varchar(64) NOT NULL,-- 班级类型ID
	`code`		int	default 0,-- 班级类型代码
	`name`		varchar(64) NOT NULL,-- 班级类型名称
	
	`agency_id`	varchar(64) NULL,-- 所属机构ID
	
	constraint pk_tbl_Netplatform_Settings_ClassTypes primary key(`id`),-- 主键约束
	constraint uk_tbl_Netplatform_Settings_ClassTypes unique key(`code`),-- 班级类型代码唯一约束
	constraint fk_tbl_Netplatform_Settings_ClassTypes_agency_id foreign key(`agency_id`) references tbl_Netplatform_Settings_Agencies(`id`)-- 所属机构ID外键约束
);
-- 消息内容(tbl_Netplatform_Settings_MsgBody)
drop table if exists tbl_Netplatform_Settings_MsgBody;
create table tbl_Netplatform_Settings_MsgBody(
	`id`	varchar(64) NOT NULL,-- 消息ID
	`title`	varchar(512) NOT NULL,-- 消息标题
	`type`	int default 0,-- 类型:0-系统消息,1-机构消息

	`content` 	text,-- 消息内容

	`agency_id` varchar(64) NULL,-- 机构ID(发消息的机构ID)
	`user_id`	varchar(64) NOT NULL,-- 用户ID(发消息的用户ID)

	constraint pk_tbl_Netplatform_Settings_MsgBody primary key(`id`),-- 主键约束
	constraint fk_tbl_Netplatform_Settings_MsgBody_agency_id foreign key(`agency_id`) references tbl_Netplatform_Settings_Agencies(`id`),-- 所属机构ID
	constraint fk_tbl_Netplatform_Settings_MsgBody_user_id foreign key(`user_id`) references tbl_Netplatform_Security_Users(`id`) -- 所属用户ID
);
-- 消息用户(tbl_Netplatform_Settings_MsgUsers)
drop table if exists tbl_Netplatform_Settings_MsgUsers;
create table tbl_Netplatform_Settings_MsgUsers(
	`user_id`	 varchar(64) NOT NULL,-- 用户ID(接收消息的用户ID)
	`msg_id`	 varchar(64) NOT NULL,-- 消息ID
	`status`	 int default 0,-- 状态:0-未读,1-已读
	`createTime` timestamp default CURRENT_TIMESTAMP,-- 创建时间

	constraint pk_tbl_Netplatform_Settings_MsgUsers primary key(`user_id`,`msg_id`),-- 主键约束
	constraint fk_tbl_Netplatform_Settings_MsgUsers_user_id foreign key(`user_id`) references tbl_Netplatform_Security_Users(`id`),-- 所属用户ID
	constraint fk_tbl_Netplatform_Settings_MsgUsers_msg_id foreign key(`msg_id`) references tbl_Netplatform_Settings_MsgBody(`id`)-- 所属消息ID
);
#----------------------------------------------------------------------------------------------
-- 班级(tbl_Netplatform_Courses_Classes)
drop table if exists tbl_Netplatform_Courses_Classes;
create table tbl_Netplatform_Courses_Classes(
	`id`				varchar(64) NOT NULL,-- 班级ID 
	`name`				varchar(512) NOT NULL,-- 班级名称
	
	`class_type_id`		varchar(64) NOT NULL,-- 所属班级类型ID
	`agency_id`			varchar(64) NOT NULL,-- 所属机构ID
	`subject_id`		varchar(64) NOT NULL,-- 所属科目ID
	
	`description`		text,-- 班级描述 
	`imgUrl`			varchar(1024),-- 宣传图片
	`videoUrl`			varchar(1024),-- 试听地址
	`price`				decimal(8,2) default 0,-- 原价
	`discountPrice`		decimal(8,2) default 0,-- 优惠价 
	`wholesalePrice`	decimal(8,2) default 0,-- 批发价
	`useYear`			int default 0,-- 使用年份
	`totalHours`		int default 0,-- 总课时
	`handoutMode`		int default 0,-- 讲义模式(0-无，1-下载，2－在线)
	`videoMode`			int default 0,-- 视频模式(0-免费，1-收费)
	
	`startTime`			timestamp default CURRENT_TIMESTAMP,-- 开班时间
	`endTime`			timestamp default CURRENT_TIMESTAMP,-- 结班时间 
	
	`status`			int default 0,-- 状态(0-停用，1-启用)
	
	`createTime`		timestamp default CURRENT_TIMESTAMP,-- 创建时间 
	`lastTime`			timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,-- 最后修改时间
	
	`orderNo`			int default 0,-- 排序号
	
	constraint pk_tbl_Netplatform_Courses_Classes primary key(`id`),-- 主键约束
	constraint fk_tbl_Netplatform_Courses_Classes_class_type_id foreign key(`class_type_id`) references tbl_Netplatform_Settings_ClassTypes(`id`),-- 班级类型ID外键约束
	constraint fk_tbl_Netplatform_Courses_Classes_agency_id foreign key(`agency_id`) references tbl_Netplatform_Settings_Agencies(`id`),-- 所属结构ID外键约束
	constraint fk_tbl_Netplatform_Courses_Classes_subject_id foreign key(`subject_id`) references tbl_Netplatform_Settings_Subjects(`id`)-- 所属科目ID外键约束
); 
-- 班级地区(tbl_Netplatform_Courses_ClassAreas)
drop table if exists tbl_Netplatform_Courses_ClassAreas;
create table tbl_Netplatform_Courses_ClassAreas(
	`class_id` 	varchar(64) NOT NULL,-- 班级ID
	`area_id`	varchar(64) NOT NULL,-- 所属地区ID

	constraint pk_tbl_Netplatform_Courses_ClassAreas primary key(`class_id`,`area_id`),-- 主键约束
	constraint fk_tbl_Netplatform_Courses_ClassAreas_class_id foreign key(`class_id`) references tbl_Netplatform_Courses_Classes(`id`),-- 所属班级ID外键约束
	constraint fk_tbl_Netplatform_Courses_ClassAreas_area_id foreign key(`area_id`) references tbl_Netplatform_Settings_Areas(`id`)-- 所属地区ID外键约束
);
-- 课时资源(tbl_Netplatform_Courses_Lessons)
drop table if exists tbl_Netplatform_Courses_Lessons;
create table tbl_Netplatform_Courses_Lessons(
	`id`				varchar(64) NOT NULL,-- 课时资源ID
	`name`				varchar(512) NOT NULL,-- 课时资源名称
	`time` 				int default 0,-- 视频时长
	`description`		text,-- 课时资源描述
	
	`class_id`			varchar(64) NOT NULL,-- 所属班级ID
	
	`videoMode`			int default 0,-- 视频模式(0-免费，1-收费)
	`videoUrl`			varchar(1024),-- 视频地址 
	`highVideoUrl`		varchar(1024),-- 高清视频地址
	`superVideoUrl`		varchar(1024),-- 超清视频地址
	`handoutMode`		int default 0,-- 讲义模式(0-无，1-下载，2－在线)
	
	`handoutContent`	text,-- 讲义内容 
	`handoutAttachUrl`	varchar(1024),-- 讲义下载地址
	
	`orderNo`			int default 0,-- 排序号 
	
	`createTime`		timestamp default CURRENT_TIMESTAMP,-- 创建时间
	`lastTime`			timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,-- 最后修改时间
	
	constraint pk_tbl_Netplatform_Courses_Lessons primary key(`id`),-- 主键约束
	constraint fk_tbl_Netplatform_Courses_Lessons_class_id foreign key(`class_id`) references tbl_Netplatform_Courses_Classes(`id`)-- 所属班级ID外键约束
);
-- 课时资源章节(tbl_Netplatform_Courses_LessonChapters)
drop table if exists tbl_Netplatform_Courses_LessonChapters;
create table tbl_Netplatform_Courses_LessonChapters(
	`lesson_id`		varchar(64) NOT NULL,-- 课时资源ID
	`chapter_id`		varchar(64) NOT NULL,-- 所属章节ID
	
	constraint pk_tbl_Netplatform_Courses_LessonChapters primary key(`lesson_id`,`chapter_id`),-- 主键约束
	constraint fk_tbl_Netplatform_Courses_LessonChapters_lesson_id foreign key(`lesson_id`) references tbl_Netplatform_Courses_Lessons(`id`),-- 所属课时资源ID外键约束
	constraint fk_tbl_Netplatform_Courses_LessonChapters_chapter_id foreign key(`chapter_id`) references tbl_Netplatform_Settings_Chapters(`id`)-- 所属章节ID外键约束
);
-- 随堂笔记(tbl_Netplatform_Courses_Notes)
drop table if exists tbl_Netplatform_Courses_Notes;
create table tbl_Netplatform_Courses_Notes(
	`id` 			varchar(64) NOT NULL,-- 笔记ID
	`lesson_id`		varchar(64) NOT NULL,-- 所属课时资源ID
	`playTime`		float default 0,-- 播放时刻
	`content`		text,-- 笔记内容

	`user_id`		varchar(64) NOT NULL,-- 所属用户ID

	`createTime`	timestamp default CURRENT_TIMESTAMP,-- 创建时间
	`lastTime`		timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,-- 最后修改时间

	constraint pk_tbl_Netplatform_Courses_Notes primary key(`id`),-- 主键约束
	constraint fk_tbl_Netplatform_Courses_Notes_lesson_id foreign key(`lesson_id`) references tbl_Netplatform_Courses_Lessons(`id`),-- 所属课时资源ID外键约束
	constraint fk_tbl_Netplatform_Courses_Notes_user_id foreign key(`user_id`) references tbl_Netplatform_Security_Users(`id`) -- 所属用户ID外键约束
);
-- 套餐(tbl_Netplatform_Courses_Packages)
drop table if exists tbl_Netplatform_Courses_Packages;
create table tbl_Netplatform_Courses_Packages(
	`id`				varchar(64) NOT NULL,-- 套餐ID
	`name`				varchar(512) NOT NULL,-- 套餐名称
	
	`agency_id`			varchar(64) NOT NULL,-- 所属机构ID
	`exam_id`			varchar(64) NOT NULL,-- 所属考试ID
	
	`description`		text,-- 套餐描述
	
	`imgUrl`			varchar(1024),-- 宣传图片
	`videoUrl`			varchar(1024),-- 试听URL
	
	`status`			int default 0,-- 状态:0-停用，1-启用
	`orderNo`			int default 0,-- 排序号 
	
	`price`				decimal(8,2) default 0,-- 原价
	`discountPrice`		decimal(8,2) default 0,-- 优惠价
	`wholesalePrice`	decimal(8,2) default 0,-- 批发价
	
	`startTime`			timestamp default CURRENT_TIMESTAMP,-- 套餐报名开始时间
	`endTime`			timestamp default CURRENT_TIMESTAMP,-- 套餐报名结束时间
	`expireTime`		timestamp default CURRENT_TIMESTAMP,-- 套餐到期时间 
	
	`createTime`		timestamp default CURRENT_TIMESTAMP,-- 创建时间
	`lastTime`			timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,-- 最后修改时间
	
	constraint pk_tbl_Netplatform_Courses_Packages primary key(`id`),-- 主键约束
	constraint fk_tbl_Netplatform_Courses_Packages_agency_id foreign key(`agency_id`) references tbl_Netplatform_Settings_Agencies(`id`),-- 所属机构ID外键约束
	constraint fk_tbl_Netplatform_Courses_Packages_exam_id foreign key(`exam_id`) references tbl_Netplatform_Settings_Exams(`id`)-- 所属考试ID外键约束
);
-- 套餐班级(tbl_Netplatform_Courses_PackageClasses)
drop table if exists tbl_Netplatform_Courses_PackageClasses;
create table tbl_Netplatform_Courses_PackageClasses(
	`package_id`	varchar(64) NOT NULL,-- 套餐ID
	`class_id`		varchar(64) NOT NULL,-- 班级ID
	
	constraint pk_tbl_Netplatform_Courses_PackageClasses primary key(`package_id`,`class_id`),-- 主键约束
	constraint fk_tbl_Netplatform_Courses_PackageClasses_package_id foreign key(`package_id`) references tbl_Netplatform_Courses_Packages(`id`),-- 所属套餐ID外键约束
	constraint fk_tbl_Netplatform_Courses_PackageClasses_class_id foreign key(`class_id`) references tbl_Netplatform_Courses_Classes(`id`)-- 所属班级ID外键约束
);
-- 订单(tbl_Netplatform_Students_Orders)
drop table if exists tbl_Netplatform_Students_Orders;
create table tbl_Netplatform_Students_Orders(
	`id`				varchar(64) NOT NULL,-- 订单ID
	`number`			varchar(64) NOT NULL,-- 订单号码
	`name`				varchar(512),-- 订单名称
	
	`price`				decimal(8,2) default 0,-- 价格
	
	`source`			int default 0,-- 订单来源(0-未知, 1-机构预订，2-学员自选)
	`status`			int default 0,-- 订单状态(-1-取消,0-正常,1-开通)
	
	`agency_id`			varchar(64) NOT NULL,-- 所属机构ID
	`create_user_id`	varchar(64) NULL,-- 创建用户ID
	
	`createTime`		timestamp default CURRENT_TIMESTAMP,-- 创建时间
	`lastTime`			timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,-- 最后修改时间
	
	constraint pk_tbl_Netplatform_Students_Orders primary key(`id`),-- 主键约束
	constraint uk_tbl_Netplatform_Students_Orders_number unique key(`number`),-- 订单号唯一约束
	constraint fk_tbl_Netplatform_Students_Orders_agency_id foreign key(`agency_id`) references tbl_Netplatform_Settings_Agencies(`id`),-- 所属机构ID外键约束
	constraint fk_tbl_Netplatform_Students_Orders_create_user_id foreign key(`create_user_id`) references tbl_Netplatform_Security_Users(`id`)-- 用户ID外键约束
);
-- 订单学员(tbl_Netplatform_Students_OrderStudents)
drop table if exists tbl_Netplatform_Students_OrderStudents;
create table tbl_Netplatform_Students_OrderStudents(
	`order_id`		varchar(64) NOT NULL,-- 所属订单ID
	`student_id`	varchar(64) NOT NULL,-- 所属学员用户ID
	
	constraint pk_tbl_Netplatform_Students_OrderStudents primary key(`order_id`,`student_id`),-- 主键约束
	constraint fk_tbl_Netplatform_Students_OrderStudents_order_id foreign key(`order_id`) references tbl_Netplatform_Students_Orders(`id`),-- 所属订单ID外键约束
	constraint fk_tbl_Netplatform_Students_OrderStudents_student_id foreign key(`student_id`) references tbl_Netplatform_Security_Users(`id`)-- 用户ID外键约束
);
-- 订单班级(tbl_Netplatform_Students_OrderClasses)
drop table if exists tbl_Netplatform_Students_OrderClasses;
create table tbl_Netplatform_Students_OrderClasses(
	`order_id`	varchar(64) NOT NULL,-- 所属订单ID
	`class_id`	varchar(64) NOT NULL,-- 所属班级ID
	
	constraint pk_tbl_Netplatform_Students_OrderClasses primary key(`order_id`,`class_id`),-- 主键约束
	constraint fk_tbl_Netplatform_Students_OrderClasses_order_id foreign key(`order_id`) references tbl_Netplatform_Students_Orders(`id`),-- 所属订单ID外键约束
	constraint fk_tbl_Netplatform_Students_OrderClasses_class_id foreign key(`class_id`) references tbl_Netplatform_Courses_Classes(`id`)-- 所属班级ID外键约束
);
-- 订单套餐(tbl_Netplatform_Students_OrderPackages)
drop table if exists tbl_Netplatform_Students_OrderPackages;
create table tbl_Netplatform_Students_OrderPackages(
	`order_id`		varchar(64) NOT NULL,-- 所属订单ID
	`package_id`	varchar(64) NOT NULL,-- 所属套餐ID
	
	constraint pk_tbl_Netplatform_Students_Orders_Packages primary key(`order_id`,`package_id`),-- 主键约束
	constraint fk_tbl_Netplatform_Students_OrderPackages_order_id foreign key(`order_id`) references tbl_Netplatform_Students_Orders(`id`),-- 所属订单ID外键约束
	constraint fk_tbl_Netplatform_Students_OrderPackages_package_id foreign key(`package_id`) references tbl_Netplatform_Courses_Packages(`id`)-- 所属套餐ID外键约束
);
-- 学员学习进度(tbl_Netplatform_Students_Learning)
drop table if exists tbl_Netplatform_Students_Learning;
create table tbl_Netplatform_Students_Learning(
	`student_id`	varchar(64) NOT NULL,-- 所属学员用户ID
	`lesson_id`		varchar(64) NOT NULL,-- 所属课时资源ID

	`status`		int default 0,-- 状态:0-未学完,1-已学完
	
	`agency_id`		varchar(64) NOT NULL,-- 所属机构ID
	
	`createTime`	timestamp default CURRENT_TIMESTAMP,-- 创建时间
	`lastTime`		timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,-- 最后修改时间
	
	constraint pk_tbl_Netplatform_Students_Learning primary key(`student_id`,`lesson_id`),-- 主键约束
	constraint fk_tbl_Netplatform_Students_Learning_student_id foreign key(`student_id`) references tbl_Netplatform_Security_Users(`id`),-- 所属学员用户ID外键约束
	constraint fk_tbl_Netplatform_Students_Learning_lesson_id foreign key(`lesson_id`)  references tbl_Netplatform_Courses_Lessons(`id`),-- 所属课时资源ID外键约束
	constraint fk_tbl_Netplatform_Students_Learning_agency_id foreign key(`agency_id`) references tbl_Netplatform_Settings_Agencies(`id`)-- 所属机构ID外键约束
);
#----------------------------------------------------------------------------------------------
-- 主讲教师(tbl_Netplatform_Teachers)
drop table if exists tbl_Netplatform_Teachers;
create table tbl_Netplatform_Teachers(
	`id` 			varchar(64) NOT NULL,-- 教师ID
	`name`    		varchar(32) NOT NULL,-- 教师姓名
	`title` 		varchar(128),-- 职务
	`imgUrl` 		varchar(1024),-- 照片URL
	`description`	text,-- 描述

	`agency_id`		varchar(64) NOT NULL,-- 所属机构ID

	`createTime`	timestamp default CURRENT_TIMESTAMP,-- 创建时间
	`lastTime`		timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,-- 最后修改时间

	constraint pk_tbl_Netplatform_Teachers primary key(`id`),-- 主键约束
	constraint fk_tbl_Netplatform_Teachers_agency_id foreign key(`agency_id`) references tbl_Netplatform_Settings_Agencies(`id`)-- 所属机构ID外键约束
);
-- 主键教师班级(tbl_Netplatform_TeacherClasses)
drop table if exists tbl_Netplatform_TeacherClasses;
create table tbl_Netplatform_TeacherClasses(
	`teacher_id` 	varchar(64) NOT NULL,-- 教师ID
	`class_id` 		varchar(64) NOT NULL,-- 班级ID

	constraint pk_tbl_Netplatform_TeacherClasses primary key(`teacher_id`,`class_id`),-- 主键约束
	constraint fk_tbl_Netplatform_TeacherClasses_teacher_id foreign key(`teacher_id`) references tbl_Netplatform_Teachers(`id`),-- 所属教师ID外键约束
	constraint fk_tbl_Netplatform_TeacherClasses_class_id foreign key(`class_id`) references tbl_Netplatform_Courses_Classes(`id`)-- 所属班级ID外键约束
);
-- 教师答疑主题(tbl_Netplatform_Teachers_AnswerQuestionTopics)
drop table if exists tbl_Netplatform_Teachers_AnswerQuestionTopics;
create table tbl_Netplatform_Teachers_AnswerQuestionTopics(
	`id`			varchar(64) NOT NULL,-- 主题ID
	`title` 		varchar(1024) NOT NULL,-- 主题标题
	`content` 		text,-- 主题内容
	`status`		int default 0,-- 状态（0 - 未完结，1 - 已完结）
	
	`agency_id`		varchar(64) NOT NULL,-- 所属机构ID
	`lesson_id`		varchar(64) NOT NULL,-- 所属课时资源 ID
	`student_id`	varchar(64) NOT NULL,-- 所属学员用户ID
	
	`createTime`	timestamp default CURRENT_TIMESTAMP,-- 创建时间 
	`lastTime`		timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,-- 最新修改时间
	
	constraint pk_tbl_Netplatform_Teachers_AnswerQuestionTopics primary key(`id`),-- 主键约束
	constraint fk_tbl_Netplatform_Teachers_AnswerQuestionTopics_agency_id foreign key(`agency_id`) references tbl_Netplatform_Settings_Agencies(`id`),-- 所属机构ID外键约束
	constraint fk_tbl_Netplatform_Teachers_AnswerQuestionTopics_lesson_id foreign key(`lesson_id`) references tbl_Netplatform_Courses_Lessons(`id`),-- 所属课时资源ID外键约束
	constraint fk_tbl_Netplatform_Teachers_AnswerQuestionTopics_user_id foreign key(`student_id`) references tbl_Netplatform_Security_Users(`id`)-- 所属学员用户ID外键约束
);
-- 教师答疑明细(tbl_Netplatform_Teachers_AnswerQuestionDetails)
drop table if exists tbl_Netplatform_Teachers_AnswerQuestionDetails;
create table tbl_Netplatform_Teachers_AnswerQuestionDetails(
	`id` 			varchar(64) NOT NULL,-- 明细ID
	`content` 		text,-- 明细内容
	
	`user_id`		varchar(64) NOT NULL,-- 所属用户ID 
	`topic_id`		varchar(64) NOT NULL,-- 所属主题ID
	
	`createTime`	timestamp default CURRENT_TIMESTAMP,-- 创建时间
	
	constraint pk_tbl_Netplatform_Teachers_AnswerQuestionDetails primary key(`id`),-- 主键约束
	constraint fk_tbl_Netplatform_Teachers_AnswerQuestionDetails_user_id foreign key(`user_id`) references tbl_Netplatform_Security_Users(`id`),-- 所属用户ID外键约束
	constraint fk_tbl_Netplatform_Teachers_AnswerQuestionDetails_topic_id foreign key(`topic_id`) references tbl_Netplatform_Teachers_AnswerQuestionTopics(`id`)-- 所属用户ID外键约束
);