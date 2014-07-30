package com.examw.netplatform.dao.admin.papers;

import java.util.List;

import com.examw.netplatform.dao.IBaseDao;
import com.examw.netplatform.domain.admin.papers.Item;
import com.examw.netplatform.model.admin.papers.ItemInfo;

/**
 * 试题数据接口
 * @author fengwei.
 * @since 2014年5月6日 上午9:56:32.
 */
public interface IItemDao extends IBaseDao<Item> {
	/**
	 * 根据校验码记载题目数据。
	 * @param checkCode
	 * 校验码。
	 * @return
	 * 题目数据。
	 */
	Item loadItem(String checkCode);
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<Item> findItems(ItemInfo info);
	/**
	 * 查询数据总数。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据总数。
	 */
	Long total(ItemInfo info);
//	/**
//	 * 批量加题
//	 * @param itemList
//	 */
//	int add(List<Item> itemList);
//	/**
//	 * 根据父ID查找试题集合
//	 * @param pid
//	 * @return
//	 */
//	List<Item> findByPid(String pid);
}