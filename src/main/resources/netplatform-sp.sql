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
drop procedure if exists sp_Netplatform_Settings_Categories_Tree;
DELIMITER //
create procedure sp_Netplatform_Settings_Categories_Tree(
	ignoreId	varchar(64) -- 应忽略分类ID及其子孙
)
begin
	-- 不存在则创建临时表
	create table if not exists temp_categories_v(
		`id` varchar(64) primary key,
		`code` int default 0,
		`name` varchar(64),
		`abbr` varchar(64),
		`pid`  varchar(64)
	);
	-- 使用前先清空临时表
	truncate table temp_categories_v;
	-- 插入数据
	insert into temp_categories_v(`id`,`code`,`name`,`abbr`,`pid`)
	select a.`id`,a.`code`,a.`name`,a.`abbr`,a.`pid`
	from tbl_Netplatform_Settings_Categories a
	where ifnull(a.`pid`,'') = '';

	-- 删除应忽略的数据
	if(ifnull(ignoreId,'') <> '' and exists(select 0 from temp_categories_v where `id` = ifnull(ignoreId,''))) then
		delete from temp_categories_v where `id` = ifnull(ignoreId,'');
	end if;

	-- 复制数据到临时表
	
	while row_count() do

		-- 插入子孙数据
		insert into temp_categories_v(`id`,`code`,`name`,`abbr`,`pid`)
		select data.`id`,data.`code`,data.`name`,data.`abbr`,data.`pid`
		from tbl_Netplatform_Settings_Categories data
		inner join temp_categories_v tmp on data.`pid` = tmp.`id`
		where (data.`id` != ifnull(ignoreId,''))
		and not exists(select 0 from temp_categories_v where `id` in (select `id` from tbl_Netplatform_Settings_Categories));

	end while;
	
	-- 返回数据
	select `id`,`code`,`name`,`abbr`,`pid`
	from temp_categories_v
	order by `code`;
end //
DELIMITER ;
#----------------------------------------------------------------------------------------------
-- 章节查询
drop procedure if exists sp_Netplatform_Settings_Chapters_Tree;
DELIMITER //
create procedure sp_Netplatform_Settings_Chapters_Tree(
	subjectId	varchar(64),-- 所属科目ID
	ignoreId	varchar(64) -- 应忽略章节ID及其子孙
)
begin
	-- 不存在则创建临时表
	create table if not exists temp_chapters_v(
		`id`			varchar(64) primary key,
		`name`			varchar(64),
		`description`	varchar(1024),
		`orderNo`		int default 0,
		`pid`			varchar(64)
	);
	-- 使用前先清空临时表
	truncate table temp_chapters_v; 
	-- 插入数据
	insert into temp_chapters_v(`id`,`name`,`description`,`orderNo`,`pid`)
	select `id`,`name`,`description`,`orderNo`,`pid`
	from tbl_Netplatform_Settings_Chapters
	where (`subject_id` = ifnull(subjectId,'')) and ifnull(`pid`,'') = '';

	-- 删除应忽略的数据
	if(ifnull(ignoreId,'') <> '' and exists(select 0 from temp_chapters_v where `id` = ifnull(ignoreId,''))) then
		delete from temp_chapters_v where `id` = ifnull(ignoreId,'');
	end if;
	
	-- 循环插入
	while row_count() do
		
		insert into temp_chapters_v(`id`,`name`,`description`,`orderNo`,`pid`)
		select a.`id`,a.`name`,a.`description`,a.`orderNo`,a.`pid`
		from tbl_Netplatform_Settings_Chapters a
		inner join temp_chapters_v tmp on a.`pid` = tmp.`id`
		where not exists(select 0 from temp_chapters_v where `id` in (select `id` from tbl_Netplatform_Settings_Chapters));
	
	end while;
	
	-- 返回数据
	select `id`,`name`,`description`,`orderNo`,`pid`
	from temp_chapters_v;
	
end; //
DELIMITER ;
#----------------------------------------------------------------------------------------------