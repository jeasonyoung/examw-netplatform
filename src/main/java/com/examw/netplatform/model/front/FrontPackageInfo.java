package com.examw.netplatform.model.front;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.examw.netplatform.model.admin.courses.ClassPlanInfo;
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
	
	private List<ClassPlanInfo> classes;

	/**
	 * 获取 包含的班级信息
	 * @return classes
	 * 包含的班级信息
	 */
	public List<ClassPlanInfo> getClasses() {
		return classes;
	}

	/**
	 * 设置 包含的班级信息
	 * @param classes
	 * 包含的班级信息
	 */
	public void setClasses(List<ClassPlanInfo> classes) {
		this.classes = classes;
	}
	/**
	 * 获取过期时间
	 */
	@JsonSerialize(using = CustomDateSerializer.ShortDate.class)
	public Date getExpireTime() {
		return super.getExpireTime();
	}
}
