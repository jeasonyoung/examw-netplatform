package com.examw.netplatform.model.front;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.beans.BeanUtils;

import com.examw.netplatform.model.admin.settings.ExamInfo;

/**
 * 前端考试信息。
 * 
 * @author yangyong
 * @since 2014年10月27日
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FrontExamInfo extends ExamInfo {
	private static final long serialVersionUID = 1L;
	private Long courseTotal;
	/**
	 * 构造函数。
	 */
	public FrontExamInfo(){
		
	}
	/**
	 * 构造函数。
	 * @param exam
	 */
	public FrontExamInfo(ExamInfo exam){
		this();
		if(exam != null){
			BeanUtils.copyProperties(exam, this);
		}
	}
	/**
	 * 获取 课程总数
	 * @return courseTotal
	 * 
	 */
	public Long getCourseTotal() {
		return courseTotal;
	}
	/**
	 * 设置 课程总数
	 * @param courseTotal
	 * 
	 */
	public void setCourseTotal(Long courseTotal) {
		this.courseTotal = courseTotal;
	}
	
}