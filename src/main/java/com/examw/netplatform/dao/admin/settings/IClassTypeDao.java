package com.examw.netplatform.dao.admin.settings;

import java.util.List;

import com.examw.netplatform.dao.IBaseDao; 
import com.examw.netplatform.domain.admin.settings.ClassType;
import com.examw.netplatform.model.admin.settings.ClassTypeInfo;

/**
 * 班级类型数据接口。
 * @author yangyong.
 * @since 2014-05-20.
 */
public interface IClassTypeDao extends IBaseDao<ClassType> {
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<ClassType> findClassTypes(ClassTypeInfo info);
	/**
	 * 查询数据总数。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据总数。
	 */
	Long total(ClassTypeInfo info);
	/**
	 * 加载最大代码。
	 * @return
	 */
	Integer loadMaxOrder();
}