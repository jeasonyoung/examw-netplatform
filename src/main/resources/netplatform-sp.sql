#----------------------------------------------------------------------------------------------
#--网校系统存储过程
#----------------------------------------------------------------------------------------------
-- 用户菜单权限查询
drop procedure if exists sp_Netplatform_Security_UserMenuRights;
DELIMITER //
create procedure sp_Netplatform_Security_UserMenuRights(
	userId	varchar(64) -- 用户ID
)
begin
	--
	select b.`id`, b.`menu_id` menuId, c.`name` menuName, b.`right_id` rightId, d.`name` rightName, b.`code`
	from tbl_Netplatform_Security_RoleRight a
	inner join tbl_Netplatform_Security_MenuRights b on b.`id` = a.`menu_right_id`
	inner join tbl_Netplatform_Security_Menus c on c.`id` = b.`menu_id`
	inner join tbl_Netplatform_Security_Rights d on d.`id` = b.`right_id`
	where a.`role_id` in (select `role_id` from tbl_Netplatform_Security_UserRoles where `user_id` = userId)
	order by b.`code`;
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
		and (data.`id` not in (select `id` from temp_categories_v));

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
		`status`		int default 0,
		`orderNo`		int default 0,
		`pid`			varchar(64)
	);
	-- 使用前先清空临时表
	truncate table temp_chapters_v; 
	-- 插入数据
	insert into temp_chapters_v(`id`,`name`,`description`,`status`,`orderNo`,`pid`)
	select `id`,`name`,`description`,`status`,`orderNo`,`pid`
	from tbl_Netplatform_Settings_Chapters
	where (`subject_id` = ifnull(subjectId,'')) and ifnull(`pid`,'') = '';

	-- 删除应忽略的数据
	if(ifnull(ignoreId,'') <> '' and exists(select 0 from temp_chapters_v where `id` = ifnull(ignoreId,''))) then
		delete from temp_chapters_v where `id` = ifnull(ignoreId,'');
	end if;
	
	-- 循环插入
	while row_count() do
		
		insert into temp_chapters_v(`id`,`name`,`description`,`status`,`orderNo`,`pid`)
		select a.`id`,a.`name`,a.`description`,a.`status`,a.`orderNo`,a.`pid`
		from tbl_Netplatform_Settings_Chapters a
		inner join temp_chapters_v tmp on a.`pid` = tmp.`id`
		where (a.`id` != ifnull(ignoreId,''))
		and (a.`id` not in (select `id` from temp_chapters_v));

	end while;
	
	-- 返回数据
	select `id`,`name`,`description`,`status`,`orderNo`,`pid`
	from temp_chapters_v
	order by `orderNo`;
end; //
DELIMITER ;
#----------------------------------------------------------------------------------------------
-- 用户ID订单中的套餐/班级
drop procedure if exists sp_Netplatform_UserOrders;
DELIMITER //
create procedure sp_Netplatform_UserOrders(
	userId 	varchar(64) -- 用户ID
)
begin

	(-- 订单套餐
		select null pid,a.`package_id` id,b.`name`,'package' type,b.`orderNo` * 100 orderNo
		from tbl_Netplatform_Students_OrderPackages a
		inner join tbl_Netplatform_Courses_Packages b on b.`id` = a.`package_id`
		where b.`status` = 1 and (a.`order_id` in (select `order_id` from tbl_Netplatform_Students_OrderStudents where `student_id` = userId))
	)
	union
	(-- 订单套餐下班级
		select a.`package_id` pid,a.`class_id` id,b.`name`,'class' type,c.`orderNo` * 100 + b.`orderNo` orderNo
		from tbl_Netplatform_Courses_PackageClasses a
		inner join tbl_Netplatform_Courses_Classes b on b.`id` = a.`class_id`
		inner join tbl_Netplatform_Courses_Packages c on c.`id` = a.`package_id`
		where b.`status` = 1 and c.`status` = 1 and (a.`package_id` in (select `package_id` from tbl_Netplatform_Students_OrderPackages where `order_id` in (
			select `order_id` from tbl_Netplatform_Students_OrderStudents where `student_id` = userId)))
	)
	union
	(-- 订单班级
		select null pid,a.`class_id` id,b.`name`,'class' type,b.`orderNo` * 100 orderNo
		from tbl_Netplatform_Students_OrderClasses a
		inner join tbl_Netplatform_Courses_Classes b on b.`id` = a.`class_id`
		where b.`status` = 1 and (a.`order_id` in (select `order_id` from tbl_Netplatform_Students_OrderStudents where `student_id` = userId))
	)
	order by ifnull(`pid`,''),`orderNo`;

end; //
DELIMITER ;
#----------------------------------------------------------------------------------------------
