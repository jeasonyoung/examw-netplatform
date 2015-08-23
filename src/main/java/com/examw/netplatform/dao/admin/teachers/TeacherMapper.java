package com.examw.netplatform.dao.admin.teachers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.examw.netplatform.domain.admin.teachers.Teacher;

/**
 * 主讲教师数据接口。
 * 
 * @author jeasonyoung
 * @since 2015年8月23日
 */
public interface TeacherMapper {
	/**
	 * 获取教师。
	 * @param id
	 * 教师ID。
	 * @return
	 * 教师数据。
	 */
	Teacher getTeacher(String id);
	/**
	 * 查询主讲教师数据集合。
	 * @param info
	 * @return
	 */
	List<Teacher> findTeachers(Teacher info);
	/**
	 * 新增教师数据。
	 * @param data
	 */
	void insertTeacher(Teacher data);
	/**
	 * 更新教师数据。
	 * @param data
	 */
	void updateTeacher(Teacher data);
	/**
	 * 删除数据。
	 * @param id
	 */
	void deleteTeacher(String id);
	/**
	 * 是否存在教师班级。
	 * @param teacherId
	 * @param classId
	 * @return
	 */
	boolean hasTeacherClass(@Param("teacherId")String teacherId,@Param("classId") String classId);
	/**
	 * 新增教师班级。
	 * @param teacherId
	 * @param classId
	 */
	void insertTeacherClass(@Param("teacherId")String teacherId,@Param("classId") String classId);
	/**
	 * 删除教师班级。
	 * @param teacherId
	 */
	void deleteTeacherClass(String teacherId);
}