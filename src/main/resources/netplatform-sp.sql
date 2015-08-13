#----------------------------------------------------------------------------------------------
#--网校系统存储过程
#----------------------------------------------------------------------------------------------
-- 删除用户
drop procedure if exists sp_Netplatform_Security_DeleteUser;
DELIMITER //
create procedure sp_Netplatform_Security_DeleteUser(
	user_id		varchar(64)
)
begin
	-- 删除用户登录日志
	delete from tbl_Netplatform_Security_LoginLog where user_id = user_id;
	-- 删除用户机构
	delete from tbl_Netplatform_Settings_UserAgencies where user_id = user_id;
	-- 删除用户角色
	delete from tbl_Netplatform_Security_UserRoles where user_id = user_id;
	-- 删除订单
	delete from tbl_Netplatform_Students_Orders  where create_user_id = user_id;
	-- 删除订单学员
	delete from tbl_Netplatform_Students_OrderStudents where student_id = user_id;
	-- 删除学员学习进度
	delete from tbl_Netplatform_Students_Learning where student_id = user_id;
	-- 删除教师答疑主题
	delete from tbl_Netplatform_Teachers_AnswerQuestionTopics where student_id = user_id;
	-- 删除教师答疑明细
	delete from tbl_Netplatform_Teachers_AnswerQuestionDetails where user_id = user_id;
	-- 删除用户
	delete from tbl_Netplatform_Security_Users where id = user_id;
end //
DELIMITER ;
#----------------------------------------------------------------------------------------------
-- 考试分类查询
drop procedure if exists sp_Netplatform_Settings_Categories_Query;
DELIMITER //
create procedure sp_Netplatform_Settings_Categories_Query(
	id			varchar(64),-- 分类ID
	code		int,-- 分类代码
	name	varchar(64),-- 分类名称
	abbr		varchar(10),-- 分类EN简称
	pid		varchar(64) -- 上级分类ID
)
begin
	-- 不存在则创建临时表
	create temporary table if not exists v_table_categories(
		`id`				varchar(64) primary key,
		`code`			int default 0,
		`name`			varchar(64),
		`fullname`	varchar(2048),
		`abbr`			varchar(10),
		`pid`				varchar(64)
	);
	-- 使用前先清空临时表
	truncate table v_table_categories;
	-- 插入数据
	insert into v_table_categories(`id`,`code`,`name`,`fullname`,`abbr`,`pid`)
	select a.`id`,a.`code`,a.`name`,a.`name`,a.`abbr`,a.`pid`
	from tbl_Netplatform_Settings_Categories a
	where  (case ifnull(pid,'') when '' then ifnull(a.`pid`,'') = '' else  a.`pid` = pid end) ;
	-- 循环插入
	while row_count() do

		 insert into v_table_categories(`id`,`code`,`name`,`fullname`,`abbr`,`pid`)
		 select a.`id`,a.`code`,a.`name`,concat(tmp.`fullname`,a.`name`),a.`abbr`,a.`pid`
		 from tbl_Netplatform_Settings_Categories a
		 inner join v_table_categories tmp on tmp.`id` = a.`pid`
		 where not exists(select 0 from v_table_categories tmp2 where a.id = tmp2.id);

	end while;
	
	-- 返回数据
	select `id`,`code`,`name`,`fullname`,`abbr`,`pid`
	from v_table_categories
	where (case ifnull(id,'') when '' then 1 = 1 else `id` = id end) AND
	(case code when -1 then 1 = 1 else `code` = code end) AND
	(case ifnull(name,'') when '' then 1 = 1 else `name` like concat('%',name,'%') end) AND
	(case ifnull(abbr,'') when '' then 1 = 1 else `abbr` like concat('%',abbr,'%') end)
	order by `fullname`,`code`;
end; //
DELIMITER ;
#----------------------------------------------------------------------------------------------
-- 章节查询
drop procedure sp_Netplatform_Settings_Chapters_Query;
DELIMITER //
create procedure sp_Netplatform_Settings_Chapters_Query(
	id				varchar(64),-- 章节ID
	name		varchar(64),-- 章节名称
	subjectId	varchar(10),-- 所属科目ID
	pid			varchar(64) -- 上级章节ID
)
begin
	-- 不存在则创建临时表
	create temporary table if not exists v_table_chapters(
		`id`					varchar(64) primary key,
		`name`				varchar(64),
		`fullname`		varchar(2048),
		`subjectId`		varchar(10),
		`description`		varchar(1024),
		`orderNo`			int default 0,
		`pid`					varchar(64)
	);
	-- 使用前先清空临时表
	truncate table v_table_chapters; 
	-- 插入数据
	insert into v_table_chapters(`id`,`name`,`fullname`,`subjectId`,`description`,`orderNo`,`pid`)
	select `id`,`name`,`name`,`subject_id`,`description`,`orderNo`,`pid`
	from tbl_Netplatform_Settings_Chapters
	where (case ifnull(pid,'') when '' then ifnull(a.`pid`,'') = '' else  a.`pid` = pid end) and (`subject_id` = subjectId)
	
	-- 循环插入
	while row_count() do
		
		insert into v_table_chapters(`id`,`name`,`fullname`,`subjectId`,`description`,`orderNo`,`pid`)
		select a.`id`,a.`name`,concat(tmp.`fullname`,a.`name`),a.`subject_id`,a.`description`,a.`orderNo`,a.`pid`
		from tbl_Netplatform_Settings_Chapters a
		inner join v_table_chapters tmp on tmp.`id` = a.`pid`
		where not exists(select 0 from v_table_chapters tmp2 where a.id = tmp2.id);
	
	end while;
	
	-- 返回数据
	select a.`id`,a.`name`,a.`fullname`,a.`subjectId`,b.`name` subject_name,a.`description`,a.`orderNo`,a.`pid`
	from v_table_chapters a
	inner join tbl_Netplatform_Settings_Subjects b on b.`id` = a.`subjectId`
	where (case ifnull(id,'') when '' then 1 = 1 else a.`id` = id end) AND
	(case ifnull(name,'') when '' then 1 = 1 else a.`name` like concat('%',name,'%') end)
	order by a.`fullname`,a.`orderNo`;
	
end; //
DELIMITER ;
#----------------------------------------------------------------------------------------------