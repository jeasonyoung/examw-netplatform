package com.examw.netplatform.service.admin.teachers;

import com.examw.netplatform.model.admin.teachers.ItemInfo;
import com.examw.netplatform.service.IBaseDataService;

/**
 * 随堂练习试题服务接口。
 * 
 * @author yangyong
 * @since 2014年11月24日
 */
public interface IItemService extends IBaseDataService<ItemInfo> {
	/**
	 * 加载题型值名称。
	 * @param type
	 * 题型值。
	 * @return
	 * 值名称。
	 */
	String loadTypeName(Integer type);
	/**
	 * 加载试题信息。
	 * @param itemId
	 * 试题ID。
	 * @return
	 * 试题信息。
	 */
	ItemInfo loadItem(String itemId);
}