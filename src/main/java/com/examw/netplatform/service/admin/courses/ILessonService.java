package com.examw.netplatform.service.admin.courses;

import java.util.List;

import com.examw.model.DataGrid;
import com.examw.netplatform.domain.admin.courses.SubjectHasClassView;
import com.examw.netplatform.model.admin.courses.LessonInfo;
/**
 * 课时资源服务接口
 * @author fengwei.
 * @since 2014年5月22日 下午1:44:58.
 */
public interface ILessonService{
	/**
	 * 加载讲义模式值名称。
	 * @param handoutMode
	 * 讲义模式。
	 * @return
	 * 讲义模式名称。
	 */
	String loadHandoutModeName(Integer handoutMode);
	/**
	 * 加载试听模式值名称。
	 * @param videoMode
	 * 试听模式。
	 * @return
	 * 试听模式名称。
	 */
	String loadVideoModeName(Integer videoMode);
	/**
	 * 加载班级下的最大排序号。
	 * @param classId
	 * 班级ID。
	 * @return
	 */
	Integer loadMaxOrder(String classId);
	/**
	 * 查询数据。
	 * @param info
	 * @return
	 */
	DataGrid<LessonInfo> datagrid(LessonInfo info);
	/**
	 * 加载班级课时资源集合。
	 * @param classId
	 * 班级ID。
	 * @return
	 * 课时资源集合。
	 */
	List<LessonInfo> loadLessons(String classId);
	/**
	 * 加载存在班级的科目班级数据。
	 * @param agencyId
	 * @return
	 */
	List<SubjectHasClassView> loadSubjectClassViews(String agencyId);
	/**
	 * 更新数据。
	 * @param info
	 * @return
	 */
	LessonInfo update(LessonInfo info);
	/**
	 * 删除数据。
	 * @param ids
	 */
	void delete(String[] ids);
}