package com.examw.netplatform.model.front;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.examw.netplatform.model.admin.courses.ClassPlanInfo;

/**
 * 前台开班计划信息
 * @author fengwei.
 * @since 2015年1月22日 下午4:11:52.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class FrontClassPlanInfo extends ClassPlanInfo{
	private static final long serialVersionUID = 1L;
	
	private Integer totalStudents; //总学员数
	
	private List<FrontLessonInfo> lessons; //课时信息
	/**
	 * 获取 总学员数
	 * @return totalStudents
	 * 总学员数
	 */
	public Integer getTotalStudents() {
		return totalStudents;
	}

	/**
	 * 设置 总学员数
	 * @param totalStudents
	 * 总学员数
	 */
	public void setTotalStudents(Integer totalStudents) {
		this.totalStudents = totalStudents;
	}

	/**
	 * 获取 课时信息
	 * @return lessons
	 * 课时信息
	 */
	public List<FrontLessonInfo> getLessons() {
		return lessons;
	}

	/**
	 * 设置 课时信息
	 * @param lessons
	 * 课时信息
	 */
	public void setLessons(List<FrontLessonInfo> lessons) {
		this.lessons = lessons;
	}
}
