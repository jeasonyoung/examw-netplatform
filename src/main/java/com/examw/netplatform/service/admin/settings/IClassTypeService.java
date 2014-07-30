package com.examw.netplatform.service.admin.settings;

import java.util.List;

import com.examw.netplatform.model.admin.settings.ClassTypeInfo;
import com.examw.netplatform.service.IBaseDataService;

/**
 * 班级类型服务接口。
 * @author yangyong.
 * @since 2014-05-20.
 */
public interface IClassTypeService extends IBaseDataService<ClassTypeInfo> {
	/**
	 * 加载全部班级类型。
	 * @return
	 * 全部班级类型集合。
	 */
	List<ClassTypeInfo> loadAll();
}