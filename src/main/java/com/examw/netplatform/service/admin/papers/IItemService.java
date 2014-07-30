package com.examw.netplatform.service.admin.papers;
 
import java.util.Map;

import com.examw.netplatform.domain.admin.papers.Item;
import com.examw.netplatform.model.admin.papers.ItemInfo;
import com.examw.netplatform.service.IBaseDataService;

/**
 * 试题服务接口
 * @author fengwei.
 * @since 2014年5月6日 上午9:59:24.
 */
public interface IItemService extends IBaseDataService<ItemInfo>{
	/**
	 * 加载题型集合。
	 * @return 题型集合。
	 */
	Map<String,String> loadTypes();
	/**
	 *  加载全部的选择题类型集合；
	 * @return 
	 * 选择题类型集合；
	 */
	Map<String, String> loadAllChoiceTypes();
	/**
	 * 加载状态名称。
	 * @param status
	 * @return
	 */
	String loadStatusName(Integer status);
	/**
	 * 加载判断题名称。
	 * @param judge
	 * @return
	 */
	String loadJudgeTypeName(Integer judge);
	/**
	 * 更新题目。
	 * @param info
	 * @return
	 */
	Item updateItem(ItemInfo info);
	/**
	 * 类型转换。
	 * @param item
	 * @return
	 */
	ItemInfo conversion(Item item);
}