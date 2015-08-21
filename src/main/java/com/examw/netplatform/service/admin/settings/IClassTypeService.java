package com.examw.netplatform.service.admin.settings;

import java.util.List;

import com.examw.model.DataGrid;
import com.examw.netplatform.model.admin.settings.ClassTypeInfo;

/**
 * 班级类型服务接口。
 * @author yangyong.
 * @since 2014-05-20.
 */
public interface IClassTypeService {
	/**
	 * 查询数据。
	 * @param info
	 * @return
	 */
	DataGrid<ClassTypeInfo> datagrid(ClassTypeInfo info);
	/**
	 * 加载全部班级类型。
	 * @param agencyId
	 * 所属机构ID。
	 * @return
	 * 全部班级类型集合。
	 */
	List<ClassTypeInfo> loadAll(String agencyId);
	/**
	 * 加载最大排序号。
	 * @return
	 */
	Integer loadMaxOrder();
	/**
	 * 更新数据。
	 * @param info
	 * @return
	 */
	ClassTypeInfo update(ClassTypeInfo info);
	/**
	 * 删除数据。
	 * @param ids
	 */
	void delete(String[] ids);
}