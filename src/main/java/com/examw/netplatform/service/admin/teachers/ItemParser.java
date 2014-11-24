package com.examw.netplatform.service.admin.teachers;

import com.examw.netplatform.domain.admin.teachers.Item;
import com.examw.netplatform.model.admin.teachers.ItemInfo;

/**
 * 试题解析器接口。
 * 
 * @author yangyong
 * @since 2014年9月24日
 */
public interface ItemParser {
	/**
	 * 获取题型名。
	 * @return 题型名。
	 */
	String getTypeName();
	/**
	 * 计算包含的试题数量。
	 * @param source
	 * @return
	 */
	Integer calculationCount(ItemInfo source);
	/**
	 *  解析试题。
	 * @param source
	 * @param target
	 */
	void parser(ItemInfo source,Item target);
	/**
	 * 数据模型转换。
	 * @param source
	 * @param isAll
	 * @return
	 */
	ItemInfo conversion(Item source,boolean isAll);
}