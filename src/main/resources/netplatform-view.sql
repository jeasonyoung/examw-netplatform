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
	select a.`id`,a.`code`,a.`name`,a.`status`,a.`exam_id` examId,b.`name` examName,b.`category_id` categoryId
    from tbl_Netplatform_Settings_Subjects a
    left outer join tbl_Netplatform_Settings_Exams b ON b.`id` = a.`exam_id`;
#----------------------------------------------------------------------------------------------
-- 考试科目树视图(vm_Netplatform_Settings_ExamSubjectTreeView)
drop view if exists vm_Netplatform_Settings_ExamSubjectTreeView;
create view vm_Netplatform_Settings_ExamSubjectTreeView
as
	(
		select null pid, `id`,`name`,`code` * 100 orderNo,'exam' type,`status`,`category_id` categoryId,`id` examId, null subjectId
		from tbl_Netplatform_Settings_Exams
		where `id` in (select distinct `exam_id` from tbl_Netplatform_Settings_Subjects)
	)
	union
	(
		select b.`id` pid,a.`id`,a.`name`,b.`code` * 100 + a.`code` orderNo,'subject' type,a.`status`,b.`category_id` categoryId,
		b.`id` examId,a.`id` subjectId
		from tbl_Netplatform_Settings_Subjects a
		inner join tbl_Netplatform_Settings_Exams b on b.`id` = a.`exam_id`
	)
	order by ifnull(pid,''),orderNO;
#----------------------------------------------------------------------------------------------
-- 章节视图(vm_Netplatform_Settings_ChapterView)
drop view if exists vm_Netplatform_Settings_ChapterView;
create view vm_Netplatform_Settings_ChapterView
as
	select a.`pid`,a.`id`,a.`name`,a.`description`,a.`status`,a.`orderNo`,a.`subject_id` subjectId,b.`name` subjectName
    from tbl_Netplatform_Settings_Chapters a
    left outer join tbl_Netplatform_Settings_Subjects b ON b.`id` = a.`subject_id`;
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
	select a.`id`,a.`name`,a.`class_type_id` typeId,b.`name` typeName,a.`agency_id` agencyId,c.`name` agencyName,
	e.`category_id` categoryId,d.`exam_id` examId,e.`name` examName,a.`subject_id` subjectId,d.`name` subjectName, 
	a.`description`,a.`imgUrl`,a.`videoUrl`,a.`useYear`,a.`totalHours`,
	a.`handoutMode`,a.`videoMode`,a.`status`,a.`orderNo`,
    a.`price`,a.`discountPrice`,a.`wholesalePrice`,
    a.`startTime`,a.`endTime`,a.`createTime`,a.`lastTime`
    from tbl_Netplatform_Courses_Classes a
    left outer join tbl_Netplatform_Settings_ClassTypes b ON b.`id` = a.`class_type_id`
    left outer join tbl_Netplatform_Settings_Agencies c ON c.`id` = a.`agency_id`
    left outer join tbl_Netplatform_Settings_Subjects d ON d.`id` = a.`subject_id`
    left outer join tbl_Netplatform_Settings_Exams e on e.`id` = d.`exam_id`;
#----------------------------------------------------------------------------------------------
-- 班级/课程资源视图
drop view if exists vm_Netplatfor_Courses_ClassLessonView;
create view vm_Netplatfor_Courses_ClassLessonView
as
   (
   	 select null pid,`id`,`name`,`agency_id` agencyId,`id` classId,null lessonId,`orderNo` * 100 orderNo
   	 from tbl_Netplatform_Courses_Classes
   )
   union
   (
   	 select b.`id` pid,a.`id`,a.`name`,b.`agency_id` agencyId,a.`class_id` classId,a.`id` lessonId,b.`orderNo` * 100 + a.`orderNo` orderNo
   	 from tbl_Netplatform_Courses_Lessons a
   	 inner join tbl_Netplatform_Courses_Classes b on b.`id` = a.`class_id`
   )
   order by orderNo;
#----------------------------------------------------------------------------------------------
-- 科目/班级视图(vm_Netplatform_Courses_SubjectHasClassView)
drop view if exists vm_Netplatform_Courses_SubjectHasClassView;
create view vm_Netplatform_Courses_SubjectHasClassView
as
	(
		select distinct null pid, a.`id`,a.`name`,c.`agency_id` agencyId,b.`category_id` categoryId,a.`exam_id` examId,
		a.`id` subjectId,null classId,a.`code` * 100 orderNo
		from tbl_Netplatform_Settings_Subjects a
		inner join tbl_Netplatform_Settings_Exams b on b.`id` = a.`exam_id`
		inner join tbl_Netplatform_Courses_Classes c on c.`subject_id` = a.`id`
	)
	union
	(
		select a.`subject_id` pid,a.`id`,a.`name`,a.`agency_id` agencyId,c.`category_id` categoryId,b.`exam_id` examId,
		b.`id` subjectId,a.`id` classId,b.`code` * 100 + a.orderNo orderNo
		from tbl_Netplatform_Courses_Classes a
		inner join tbl_Netplatform_Settings_Subjects b on b.`id` = a.`subject_id`
		inner join tbl_Netplatform_Settings_Exams c on c.`id` = b.`exam_id`
	)
	order by orderNo;
#----------------------------------------------------------------------------------------------
-- 考试分类视图(vm_Netplatform_Courses_CategoryHasExamView)
drop view if exists vm_Netplatform_Courses_CategoryHasExamView;
create view vm_Netplatform_Courses_CategoryHasExamView
as
	(
		select null pid,`id`,`name`,`id` categoryId,null examId,`code`*100 orderNo
		from tbl_Netplatform_Settings_Categories
		where `id` in (select `category_id` from tbl_Netplatform_Settings_Exams)
	)
	union
	(
		select a.`category_id` pid,a.`id`,a.`name`,a.`category_id` categoryId,a.`id` examId, 
		b.`code` * 100 + a.`code` orderNo
		from tbl_Netplatform_Settings_Exams a
		inner join tbl_Netplatform_Settings_Categories b on b.`id` = a.`category_id`
	)
	order by orderNo;
#----------------------------------------------------------------------------------------------
-- 套餐视图
drop view if exists vw_Netplatform_Courses_PackageView;
create view vw_Netplatform_Courses_PackageView
as
	SELECT a.`id`,a.`name`,a.`agency_id` agencyId, b.`name` agencyName,c.`category_id` categoryId,a.`exam_id` examId,c.`name` examName,
	a.`description`,a.`imgUrl`,a.`videoUrl`,
	a.`status`,a.`orderNo`,a.`price`,a.`discountPrice`,a.`wholesalePrice`,a.`startTime`,a.`endTime`,a.`expireTime`,a.`createTime`,a.`lastTime`
	FROM tbl_Netplatform_Courses_Packages a
	left outer join tbl_Netplatform_Settings_Agencies b ON b.`id` = a.`agency_id`
	left outer join tbl_Netplatform_Settings_Exams c ON c.`id` = a.`exam_id`;
#----------------------------------------------------------------------------------------------
-- 套餐/班级视图
drop view if exists vm_Netplatform_Courses_PackageClassesView;
create view vm_Netplatform_Courses_PackageClassesView
as
	(
		select null pid, `id`, `name`, 'package' type, `agency_id` agencyId, `exam_id` examId, `orderNo` * 100 orderNo
		from tbl_Netplatform_Courses_Packages a
	)
	union
	(
		select a.`package_id` pid,a.`class_id` id, b.`name`, 'class' type, c.`agency_id` agencyId,c.`exam_id` examId,
		c.`orderNo` * 100 + b.`orderNo` orderNo
		from tbl_Netplatform_Courses_PackageClasses a
		inner join tbl_Netplatform_Courses_Classes b on b.`id` = a.`class_id`
		inner join tbl_Netplatform_Courses_Packages c on c.`id` = a.`package_id`
	)
	order by ifnull(pid,''),orderNo;
#----------------------------------------------------------------------------------------------
-- 课时资源视图
drop view if exists vw_Netplatform_Courses_LessonView;
create view vw_Netplatform_Courses_LessonView
as
	select a.`id`,a.`name`,a.`description`,b.`agency_id` agencyId,d.`category_id` categoryId,
	c.`exam_id` examId,b.`subject_id` subjectId,a.`class_id` classId,b.`name` className,
	a.`videoUrl`,a.`highVideoUrl`,a.`superVideoUrl`,a.`handoutContent`,a.`handoutAttachUrl`,
	a.`time`,a.`videoMode`,a.`handoutMode`,a.`orderNo`,a.`createTime`,a.`lastTime`
	from tbl_Netplatform_Courses_Lessons a
	inner join tbl_Netplatform_Courses_Classes b ON b.`id` = a.`class_id`
	left outer join tbl_Netplatform_Settings_Subjects c on c.`id` = b.`subject_id`
	left outer join tbl_Netplatform_Settings_Exams d on d.`id` = c.`exam_id`;
#----------------------------------------------------------------------------------------------
-- 主讲教师视图
drop view if exists vm_Netplatform_Teachers_TeacherView;
create view vm_Netplatform_Teachers_TeacherView
as
	select a.`agency_id` agencyId,b.`name` agencyName,a.`id`,a.`name`,a.`title`,a.`imgUrl`,a.`description`,a.`createTime`,a.`lastTime`
	from tbl_Netplatform_Teachers a
	left outer join tbl_Netplatform_Settings_Agencies b ON b.`id` = a.`agency_id`;
#----------------------------------------------------------------------------------------------
-- 学习进度视图
drop view if exists vw_Netplatform_Students_LearningView;
create view vw_Netplatform_Students_LearningView
as
	select a.`student_id` studentId,b.`name` studentName,a.`lesson_id` lessonId,c.`name` lessonName,a.`status`,
	a.`agency_id` agencyId,d.`name` agencyName,c.`class_id` classId,e.`name` className,e.`subject_id` subjectId,f.`exam_id` examId,
	g.`category_id` categoryId,a.`createTime`,a.`lastTime`
	from tbl_Netplatform_Students_Learning a
	left outer join tbl_Netplatform_Security_Users b on b.`id` = a.`student_id`
	left outer join tbl_Netplatform_Courses_Lessons c on c.`id` = a.`lesson_id`
	left outer join tbl_Netplatform_Settings_Agencies d on d.`id` = a.`agency_id`
	left outer join tbl_Netplatform_Courses_Classes e on e.`id` = c.`class_id`
	left outer join tbl_Netplatform_Settings_Subjects f on f.`id` = e.`subject_id`
	left outer join tbl_Netplatform_Settings_Exams g on g.`id` = f.`exam_id`;
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
	select a.`id`,a.`title`,a.`content`,a.`status`,a.`agency_id` agencyId,b.`name` agencyName,c.`class_id` classId,
	a.`lesson_id` lessonId,c.`name` lessonName,
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
-- 消息视图
drop view if exists vm_Netplatform_Settings_MsgBodyView;
create view vm_Netplatform_Settings_MsgBodyView
as
	select a.`id`,a.`title`,a.`type`,a.`content`,a.`agency_id` agencyId,b.`name` agencyName,a.`user_id` userId,c.`name` userName,
	a.`createTime`,a.`lastTime`
	from tbl_Netplatform_Settings_MsgBody a
	left outer join tbl_Netplatform_Settings_Agencies b on b.`id` = a.`agency_id`
	left outer join tbl_Netplatform_Security_Users c on c.`id` = a.`user_id`;
#----------------------------------------------------------------------------------------------
-- 消息用户视图
drop view if exists vm_Netplatform_Settings_MsgUserView;
create view vm_Netplatform_Settings_MsgUserView
as
	select a.`msg_id` id,b.`title`,b.`type`,b.`content`,b.`agency_id` agencyId,c.`name` agencyName,a.`status`,a.`user_id` userId,d.`name` userName,
	a.`createTime`,a.`lastTime`
	from tbl_Netplatform_Settings_MsgUsers a
	inner join tbl_Netplatform_Settings_MsgBody b on b.`id` = a.`msg_id`
	left outer join tbl_Netplatform_Settings_Agencies c on c.`id` = b.`agency_id`
	left outer join tbl_Netplatform_Security_Users d on d.`id` = a.`user_id`;
#----------------------------------------------------------------------------------------------

