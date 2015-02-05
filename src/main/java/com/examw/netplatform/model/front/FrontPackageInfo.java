package com.examw.netplatform.model.front;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.examw.netplatform.model.admin.courses.PackageInfo;
import com.examw.support.CustomDateSerializer;

/**
 * 前台套餐信息
 * @author fengwei.
 * @since 2015年1月29日 上午9:00:25.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class FrontPackageInfo extends PackageInfo{
	private static final long serialVersionUID = 1L;
	
	private List<FrontClassPlanInfo> classes;

	private Integer totalStudents;
	private Integer totalHours;
	private String teacherName; //主讲教师
	/**
	 * 获取 包含的班级信息
	 * @return classes
	 * 包含的班级信息
	 */
	public List<FrontClassPlanInfo> getClasses() {
		return classes;
	}

	/**
	 * 设置 包含的班级信息
	 * @param classes
	 * 包含的班级信息
	 */
	public void setClasses(List<FrontClassPlanInfo> classes) {
		this.classes = classes;
	}
	/**
	 * 获取过期时间
	 */
	@JsonSerialize(using = CustomDateSerializer.ShortDate.class)
	public Date getExpireTime() {
		return super.getExpireTime();
	}

	/**
	 * 获取 学员总数
	 * @return totalStudents
	 * 
	 */
	public Integer getTotalStudents() {
		return totalStudents;
	}

	/**
	 * 设置 学员总数
	 * @param totalStudents
	 * 
	 */
	public void setTotalStudents(Integer totalStudents) {
		this.totalStudents = totalStudents;
	}

	/**
	 * 获取 总课时
	 * @return totalHours
	 * 
	 */
	public Integer getTotalHours() {
		return totalHours;
	}

	/**
	 * 设置 总课时
	 * @param totalHours
	 * 
	 */
	public void setTotalHours(Integer totalHours) {
		this.totalHours = totalHours;
	}

	/**
	 * 获取 主讲老师
	 * @return teacherName
	 * 
	 */
	public String getTeacherName() {
		return teacherName;
	}

	/**
	 * 设置 主讲老师
	 * @param teacherName
	 * 
	 */
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
}
