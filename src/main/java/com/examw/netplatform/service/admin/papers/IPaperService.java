package com.examw.netplatform.service.admin.papers;

import java.util.List;
import java.util.Map;

import com.examw.model.DataGrid;
import com.examw.netplatform.domain.admin.papers.Paper;
import com.examw.netplatform.model.admin.papers.PaperInfo;
import com.examw.netplatform.model.admin.papers.PaperPreview;
import com.examw.netplatform.model.admin.papers.StructureInfo;
import com.examw.netplatform.model.admin.papers.StructureItemInfo;
import com.examw.netplatform.service.IBaseDataService;

/**
 * 试卷服务接口
 * @author fengwei.
 * @since 2014年5月4日 上午11:08:20.
 */
public interface IPaperService extends IBaseDataService<PaperInfo> {
	/**
	 * 返回试卷类型的映射
	 * @return
	 */
	Map<String,String> loadTypes();
	/**
	 * 加载试卷状态名称
	 * @param status
	 * @return
	 */
	String getStatusName(Integer status);
	/**
	 * 审核试卷
	 * @param paperId
	 * 试卷ID。
	 * @return
	 */
	PaperInfo updateAudit(String paperId);
	/**
	 * 根据ID查找试卷
	 * @param paperId
	 * @return
	 */
	Paper find(String paperId);
	/**
	 * 加载试卷。
	 * @param paperId
	 * 试卷ID。
	 * @return
	 * 试卷预览对象。
	 */
	PaperPreview loadPaperPreview(String paperId);
	/**
	 * 根据试卷ID加载试卷结构数据。
	 * @param paperId
	 * 试卷ID
	 * @return
	 */
	List<StructureInfo> loadStructure(String paperId);
	/**
	 * 更新试卷结构。
	 * @param paperId
	 *  试卷ID。
	 * @param info
	 * 试卷结构对象。
	 */
	void updateStructure(String paperId, StructureInfo info);
	/**
	 * 删除试卷结构。
	 * @param structureId
	 * 试卷结构ID。
	 */
	void deleteStructure(String structureId);
	/**
	 * 查询试卷题目数据。
	 * @param paperId
	 * @param info
	 * @return
	 */
	DataGrid<StructureItemInfo> findStructureItems(String paperId, StructureItemInfo info);
	/**
	 * 加载试卷题目。
	 * @param structureItemId
	 * 试卷题目ID。
	 * @return
	 */
	StructureItemInfo loadPaperItem(String structureItemId);
	/**
	 * 加载试卷题目的最大排序号。
	 * @param paperId
	 * @return
	 */
	int loadPaperItemMaxOrderNo(String paperId);
	/**
	 * 更新试卷题目数据。
	 * @param paperId
	 * 试卷ID。
	 * @param info
	 * 试卷题目。
	 * @return
	 */
	StructureItemInfo updateStructureItem(String paperId, StructureItemInfo info);
	/**
	 * 删除试卷题目。
	 * @param ids
	 * 试卷题目ID。
	 */
	void deleteStructureItems(String[] ids);
}