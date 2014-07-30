package com.examw.netplatform.service.admin.courses;

import com.examw.netplatform.model.admin.courses.LessonInfo;
import com.examw.netplatform.service.IBaseDataService;
/**
 * 课时资源服务接口
 * @author fengwei.
 * @since 2014年5月22日 下午1:44:58.
 */
public interface ILessonService  extends IBaseDataService<LessonInfo>{
	/**
	 * 获取是否免费的名称。
	 * @param freeVideoMode
	 * @return 是否免费的名称。
	 */
	String loadVideoModeName(Integer freeVideoMode);
}