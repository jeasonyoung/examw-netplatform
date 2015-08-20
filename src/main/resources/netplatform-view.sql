#----------------------------------------------------------------------------------------------
#--网校系统－视图
#----------------------------------------------------------------------------------------------
-- 菜单权限视图
drop view if exists vm_Netplatform_Security_MenuRightView;
create view vm_Netplatform_Security_MenuRightView
as
	SELECT a.`id`,a.`menu_id` menuId,b.`name` menuName,a.`right_id` rightId,c.`name` rightName,a.`code`
    FROM tbl_Netplatform_Security_MenuRights a
    INNER JOIN tbl_Netplatform_Security_Menus b ON b.`id` = a.`menu_id`
    INNER JOIN tbl_Netplatform_Security_Rights c ON c.`id` = a.`right_id`;
#----------------------------------------------------------------------------------------------
-- 菜单及其权限视图(扁平树结构:vw_Netplatform_Security_MenuPermissionTreeView)
drop view if exists vw_Netplatform_Security_MenuPermissionTreeView;
create view vw_Netplatform_Security_MenuPermissionTreeView
as
	(
	 	select `id`,ifnull(`name`,'') name,`id` menuId,`name` menuName, '' rightId, '' rightName, ifnull(`pid`,'') pid,'menu' type,`id` code,
	 	`orderNO` * 10 orderNO
	 	from tbl_Netplatform_Security_Menus
	)
	union
	(
		select a.`id`,concat(b.`name`,'-',c.`name`) name,a.`menu_id` menuId, b.`name` menuName,a.`right_id` rightId,c.`name` rightName,
		a.`menu_id` pid,'right' type,a.`code`,b.`orderNO` * 10 + c.`value` orderNO
		from tbl_Netplatform_Security_MenuRights a
		inner join tbl_Netplatform_Security_Menus b
		on b.`id` = a.`menu_id`
		inner join tbl_Netplatform_Security_Rights c
		on c.`id` = a.`right_id`
	)
	order by ifnull(pid,''),orderNO;
#----------------------------------------------------------------------------------------------
-- 登录日志视图(vw_Netplatform_Security_LoginLogView)
drop view if exists vw_Netplatform_Security_LoginLogView;
create view vw_Netplatform_Security_LoginLogView
as
	select a.`id`,c.`agency_id` agencyId,d.`name` agencyName,a.`user_id` userId,b.`account` userAccount,b.`name` userName,
	a.`ip`,a.`browser`,a.`createTime`
    from tbl_Netplatform_Security_LoginLog a
    left outer join tbl_Netplatform_Security_Users b on b.`id` = a.`user_id`
    left outer join tbl_Netplatform_Settings_UserAgencies c on c.`user_id` = a.`user_id`
    left outer join tbl_Netplatform_Settings_Agencies d on d.`id` = c.`agency_id`;
#----------------------------------------------------------------------------------------------
-- 考试视图(vm_Netplatform_Settings_ExamView)
drop view if exists vm_Netplatform_Settings_ExamView;
create view vm_Netplatform_Settings_ExamView
as
	select a.`category_id` categoryId,b.`name` categoryName, a.`id`,a.`code`,a.`name`,a.`abbr`,a.`status`
    from tbl_Netplatform_Settings_Exams a
    left outer join tbl_Netplatform_Settings_Categories b ON b.`id` = a.`category_id`;
#----------------------------------------------------------------------------------------------
-- 类别视图(拥有考试的类别集合vm_Netplatform_Settings_CategoriesByExamView)
drop view if exists vm_Netplatform_Settings_CategoriesByExamView;
create view vm_Netplatform_Settings_CategoriesByExamView
as
	select `id`,`code`,`name`,`abbr`,`pid`
	from tbl_Netplatform_Settings_Categories
	where `id` in (select distinct `category_id` from tbl_Netplatform_Settings_Exams);
#----------------------------------------------------------------------------------------------
-- 科目视图(vm_Netplatform_Settings_SubjectView)
drop view if exists vm_Netplatform_Settings_SubjectView;
create view vm_Netplatform_Settings_SubjectView
as
	select a.`id`,a.`code`,a.`name`,a.`status`,a.`exam_id` examId,b.`name` examName
    from tbl_Netplatform_Settings_Subjects a
    left outer join tbl_Netplatform_Settings_Exams b ON b.`id` = a.`exam_id`;
#----------------------------------------------------------------------------------------------
-- 班级类型视图(vm_Netplatform_Settings_ClassTypeView)
drop view if exists vm_Netplatform_Settings_ClassTypeView;
create view vm_Netplatform_Settings_ClassTypeView
as
	select a.`agency_id` agencyId, b.`name` agencyName, a.`id`,a.`code`,a.`name`
    from tbl_Netplatform_Settings_ClassTypes a
    left outer join tbl_Netplatform_Settings_Agencies b ON b.`id` = a.`agency_id`;
#----------------------------------------------------------------------------------------------
-- 培训机构视图(vm_Netplatform_Settings_AgenciesView)
drop view if exists vm_Netplatform_Settings_AgenciesView;
create view vm_Netplatform_Settings_AgenciesView
as
	select `id`,`name`,`abbr_cn` abbrCN,`abbr_en` abbrEN,`keywords`,`address`,`tel`,`fax`,`introduction`,`remarks`,
	`logo_url` logoUrl,`status`,`package_count` packageCount,`account_count` accountCount,`createTime`,`lastTime`
    from tbl_Netplatform_Settings_Agencies;
#----------------------------------------------------------------------------------------------
-- 用户视图(vm_Netplatform_Security_UsersView)
drop view if exists vm_Netplatform_Security_UsersView;
create view vm_Netplatform_Security_UsersView
as
	select a.`id`,a.`name`,a.`account`,a.`nickName`,a.`password`,a.`imgUrl`,a.`gender`,a.`type`,a.`status`,a.`phone`,
	a.`qq`,a.`email`,a.`createTime`,a.`lastTime`,b.`agency_id` agencyId,c.`name` agencyName,b.`identity`
	from tbl_Netplatform_Security_Users a
	left outer join tbl_Netplatform_Settings_UserAgencies b on b.`user_id` = a.`id`
	left outer join tbl_Netplatform_Settings_Agencies c on c.`id` = b.`agency_id`;
#----------------------------------------------------------------------------------------------
-- 班级视图
drop view if exists vw_Netplatform_Courses_ClassView;
create view vw_Netplatform_Courses_ClassView
as
		SELECT a.`id`,a.`name`,a.`class_type_id`,b.`name` class_type_name,a.`agency_id`,c.`name` agency_name,a.`subject_id`,d.`name` subject_name,
        a.`description`,a.`imgUrl`,a.`videoUrl`,a.`price`,a.`discountPrice`,a.`wholesalePrice`,
        a.`useYear`,a.`totalHours`,a.`handoutMode`,a.`videoMode`,a.`startTime`,a.`endTime`,a.`status`,a.`createTime`,a.`lastTime`,a.`orderNo`
        FROM tbl_Netplatform_Courses_Classes a
        INNER JOIN tbl_Netplatform_Settings_ClassTypes b ON b.`id` = a.`class_type_id`
        INNER JOIN tbl_Netplatform_Settings_Agencies c ON c.`id` = a.`agency_id`
        INNER JOIN tbl_Netplatform_Settings_Subjects d ON d.`id` = a.`subject_id`;
#----------------------------------------------------------------------------------------------
-- 套餐视图
drop view if exists vw_Netplatform_Courses_PackageView;
create view vw_Netplatform_Courses_PackageView
as
	SELECT a.`id`,a.`name`,a.`agency_id`,b.`name` agency_name,a.`exam_id`,c.`name` exam_name,a.`description`,a.`imgUrl`,a.`videoUrl`,a.`status`,
	a.`orderNo`,a.`price`,a.`discountPrice`,a.`wholesalePrice`,a.`startTime`,a.`endTime`,a.`expireTime`,a.`createTime`,a.`lastTime`
	FROM tbl_Netplatform_Courses_Packages a
	INNER JOIN tbl_Netplatform_Settings_Agencies b ON b.`id` = a.`agency_id`
	INNER JOIN tbl_Netplatform_Settings_Exams c ON c.`id` = a.`exam_id`;
#----------------------------------------------------------------------------------------------
-- 课时资源视图
drop view if exists vw_Netplatform_Courses_LessonView;
create view vw_Netplatform_Courses_LessonView
as
	select a.`id`,a.`name`,a.`time`,a.`description`,a.`class_id`,b.`name` class_name,a.`videoMode`,a.`videoUrl`,a.`highVideoUrl`,
	a.`handoutMode`,a.`handoutContent`,a.`handoutAttachUrl`,a.`orderNo`,a.`createTime`,a.`lastTime`
	from tbl_Netplatform_Courses_Lessons a
	inner join tbl_Netplatform_Courses_Classes b ON b.`id` = a.`class_id`;
#----------------------------------------------------------------------------------------------
-- 学习进度视图
drop view if exists vw_Netplatform_Students_LearningView;
create view vw_Netplatform_Students_LearningView
as
	select a.`student_id` studentId,b.`name` studentName,a.`lesson_id` lessonId,c.`name` lessonName,
	a.`agency_id` agencyId, d.`name` agencyName,a.`createTime`,a.`lastTime`
	from tbl_Netplatform_Students_Learning a
	inner join tbl_Netplatform_Security_Users b on b.`id` = a.`student_id`
	inner join tbl_Netplatform_Courses_Lessons c on c.`id` = a.`lesson_id`
	inner join tbl_Netplatform_Settings_Agencies d on d.`id` = a.`agency_id`;
#----------------------------------------------------------------------------------------------
-- 订单视图
drop view if exists vw_Netplatform_Students_OrderView;
create view vw_Netplatform_Students_OrderView
as
	select a.`id`,a.`number`,a.`name`,a.`price`,a.`source`,a.`status`,a.`agency_id` agencyId, b.`name` agencyName,  
	a.`create_user_id` userId,c.`name` userName, a.`createTime`,a.`lastTime`
	from tbl_Netplatform_Students_Orders a
	left outer join tbl_Netplatform_Settings_Agencies b on b.`id` = a.`agency_id`
	left outer join tbl_Netplatform_Security_Users c on c.`id` = a.`create_user_id`;
#----------------------------------------------------------------------------------------------
-- 教师答疑主题视图
drop view if exists vw_Netplatform_Teachers_TopicView;
create view vw_Netplatform_Teachers_TopicView
as
	select a.`id`,a.`title`,a.`content`,a.`status`,a.`agency_id` agencyId,b.`name` agencyName,a.`lesson_id` lessonId,c.`name` lessonName,
	a.`student_id` studentId,d.`name` studentName,a.`createTime`,a.`lastTime`
	from tbl_Netplatform_Teachers_AnswerQuestionTopics a
	left outer join tbl_Netplatform_Settings_Agencies b on b.`id` = a.`agency_id`
	left outer join tbl_Netplatform_Courses_Lessons c on c.`id` = a.`lesson_id`
	left outer join tbl_Netplatform_Security_Users d on d.`id` = a.`student_id`;
#----------------------------------------------------------------------------------------------
-- 教师答疑明细视图
drop view if exists vw_Netplatform_Teachers_DetailView;
create view vw_Netplatform_Teachers_DetailView
as
	select a.`id`,a.`content`,a.`user_id` userId,b.`name` userName,a.`topic_id` topicId,a.`createTime`
	from tbl_Netplatform_Teachers_AnswerQuestionDetails a
	left outer join tbl_Netplatform_Security_Users b on b.`id` = a.`user_id`;
#----------------------------------------------------------------------------------------------
