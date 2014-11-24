package com.examw.netplatform.service.admin.teachers;

import com.examw.netplatform.model.admin.teachers.PracticeInfo;
import com.examw.netplatform.service.IBaseDataService;

/**
 * 随堂练习服务接口。
 * 
 * @author yangyong
 * @since 2014年11月24日
 */
public interface IPracticeService extends IBaseDataService<PracticeInfo> {
	/**
	 * 加载状态值名称。
	 * @param status
	 * 状态值。
	 * @return
	 * 值名称。
	 */
	String loadStatusName(Integer status);
}