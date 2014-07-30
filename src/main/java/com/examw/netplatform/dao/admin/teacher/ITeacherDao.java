package com.examw.netplatform.dao.admin.teacher;

import java.util.List;

import com.examw.netplatform.dao.IBaseDao;
import com.examw.netplatform.domain.admin.security.User;
import com.examw.netplatform.domain.admin.teacher.Teacher;
import com.examw.netplatform.model.admin.teacher.TeacherInfo;

/**
 * 教师数据接口
 * @author fengwei.
 * @since 2014年5月29日 下午3:21:28.
 */
public interface ITeacherDao extends IBaseDao<Teacher>{
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<Teacher> findTeachers(TeacherInfo info);
	/**
	 * 查询数据总数。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据总数。
	 */
	Long total(TeacherInfo info);
	/**
	 * 批量添加
	 * @param list
	 */
	void batchAdd(List<Teacher> list);
	/**
	 * 根据学员Id删除关联关系
	 * @param userId
	 */
	void delete(String userId);
	/**
	 * 根据ID找
	 * @param userId
	 * @return
	 */
	List<Teacher> find(String userId);
	/**
	 * 查找老师[构造树的数据]
	 * @param agencyId
	 * @param examId
	 * @return
	 */
	List<User> loadTeacher(String agencyId);
}
