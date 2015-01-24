package com.examw.netplatform.model.front;

import com.examw.netplatform.model.admin.courses.LessonInfo;

/**
 * 课时资源信息
 * @author fengwei.
 * @since 2015年1月23日 上午10:46:41.
 */
public class FrontLessonInfo extends LessonInfo{
	private static final long serialVersionUID = 1L;
	
	private Integer learningStatus;	//学习状态

	/**
	 * 获取 学习状态[0:未学,1:在学,2:学完]
	 * @return learningStatus
	 * 
	 */
	public Integer getLearningStatus() {
		return learningStatus;
	}

	/**
	 * 设置 学习状态
	 * @param learningStatus
	 * 
	 */
	public void setLearningStatus(Integer learningStatus) {
		this.learningStatus = learningStatus;
	}
	
	
	
	
}
