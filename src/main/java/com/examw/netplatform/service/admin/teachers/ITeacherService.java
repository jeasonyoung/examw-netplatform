package com.examw.netplatform.service.admin.teachers;

import com.examw.model.DataGrid;
import com.examw.netplatform.model.admin.teachers.TeacherInfo;

/**
 * 主讲教师服务接口。
 * 
 * @author jeasonyoung
 * @since 2015年8月23日
 */
public interface ITeacherService {
	/**
	 * 查询数据。
	 * @param info
	 * @return
	 */
	DataGrid<TeacherInfo> datagrid(TeacherInfo info);
	/**
	 * 更新数据。
	 * @param info
	 * @return
	 */
	TeacherInfo update(TeacherInfo info);
	/**
	 * 删除数据。
	 * @param ids
	 */
	void delete(String[] ids);
}