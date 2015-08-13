package com.examw.netplatform.dao.admin.settings;

import java.util.List;

import com.examw.netplatform.domain.admin.settings.ClassType;

/**
 * 班级类型数据接口。
 * @author yangyong.
 * @since 2014-05-20.
 */
public interface ClassTypeMapper {
	/**
	 * 加载班级类型。
	 * @param id
	 * @return
	 */
	ClassType getClassType(String id);
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<ClassType> findClassTypes(ClassType info);
	/**
	 * 查询机构下的班级类型集合。
	 * @param agencyId
	 * @return
	 */
	List<ClassType> findClassTypesByAgency(String agencyId);
	/**
	 * 加载最大代码。
	 * @return
	 */
	Integer loadMaxOrder();
	/**
	 * 是否存在班级类型代码。
	 * @param code
	 * @return
	 */
	boolean hasClassTypeCode(int code);
	/**
	 * 新增班级类型。
	 * @param data
	 */
	void insertClassType(ClassType data);
	/**
	 * 更新班级类型。
	 * @param data
	 */
	void updateClassType(ClassType data);
	/**
	 * 删除班级类型。
	 * @param id
	 */
	void deleteClassType(String id);
}