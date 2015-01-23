package com.examw.netplatform.model.front;

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
	
	private Integer totalLessonHour; //总课时

	/**
	 * 获取 总课时
	 * @return totalLessonHour
	 * 总课时
	 */
	public Integer getTotalLessonHour() {
		return totalLessonHour;
	}

	/**
	 * 设置 总课时
	 * @param totalLessonHour
	 * 总课时
	 */
	public void setTotalLessonHour(Integer totalLessonHour) {
		this.totalLessonHour = totalLessonHour;
	}
}
