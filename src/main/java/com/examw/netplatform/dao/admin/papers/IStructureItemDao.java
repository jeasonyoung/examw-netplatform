package com.examw.netplatform.dao.admin.papers;

import java.util.List;

import com.examw.netplatform.dao.IBaseDao;
import com.examw.netplatform.domain.admin.papers.Paper;
import com.examw.netplatform.domain.admin.papers.StructureItem;
import com.examw.netplatform.model.admin.papers.StructureItemInfo;
/**
 * 试卷题目数据接口。
 * @author fengwei.
 * @since 2014年5月11日 下午4:06:24.
 */
public interface IStructureItemDao  extends IBaseDao<StructureItem>{
	/**
	 * 查询数据。
	 * @param paperId
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据集。
	 */
	List<StructureItem> findStructureItems(String paperId, StructureItemInfo info);
	/**
	 * 查询结果数据统计。
	 * @param info
	 * 查询条件。
	 * @return
	 * 统计结果。
	 */
	Long total(String paperId,StructureItemInfo info);
	/**
	 * 加载试卷题目数据
	 * @param structureId
	 * 结构ID。
	 * @param itemId
	 * 题目ID。
	 * @return
	 */
	StructureItem loadStructureItem(String structureId, String itemId);
	/**
	 * 题目在是否在所有的试卷中存在。
	 * @param itemId
	 * 题目ID。
	 * @return
	 * 存在返回true,否则false.
	 */
	int exists(String itemId);
	/**
	 * 加载试卷下最大的排序号。
	 * @param paperId
	 * @return
	 */
	Integer loadMaxOrderNo(String paperId);
	/**
	 * 随机加载题目所在的试卷。
	 * @param itemId
	 * 题目ID。
	 * @return
	 * 试卷。
	 */
	Paper loadRandomPaper(String itemId);
}