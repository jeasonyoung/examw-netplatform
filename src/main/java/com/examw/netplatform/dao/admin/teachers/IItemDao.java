package com.examw.netplatform.dao.admin.teachers;

import java.util.List;

import com.examw.netplatform.dao.IBaseDao;
import com.examw.netplatform.domain.admin.teachers.Item;
import com.examw.netplatform.model.admin.teachers.ItemInfo;

/**
 * 随堂练习试题数据接口。
 * 
 * @author yangyong
 * @since 2014年11月22日
 */
public interface IItemDao extends IBaseDao<Item> {
	/**
	 * 加载试题最大排序号。
	 * @param structureId
	 * 随堂练习结构ID。
	 * @return
	 */
	Integer loadMaxOrder(String structureId);
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 试题数据。
	 */
	List<Item> findItems(ItemInfo info);
	/**
	 * 查询数据统计。
	 * @param info
	 * 查询条件。
	 * @return
	 */
	Long total(ItemInfo info);
}