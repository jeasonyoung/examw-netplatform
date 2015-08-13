package com.examw.netplatform.service.admin.settings.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.examw.model.DataGrid;
import com.examw.model.TreeNode;
import com.examw.netplatform.dao.admin.settings.ChapterMapper;
import com.examw.netplatform.domain.admin.settings.Chapter;
import com.examw.netplatform.model.admin.settings.ChapterInfo;
import com.examw.netplatform.service.admin.settings.IChapterService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 科目章节服务。
 * @author fengwei.
 * @since 2014年4月30日 下午3:33:55.
 */
public class ChapterServiceImpl  implements IChapterService {
	private static final Logger logger = Logger.getLogger(ChapterServiceImpl.class);
	private ChapterMapper chapterDao;
	private Map<Integer, String> statusMap;
	/**
	 * 设置章节数据接口。
	 * @param chapterDao
	 * 章节数据接口。
	 */
	public void setChapterDao(ChapterMapper chapterDao) {
		logger.debug("注入章节数据接口...");
		this.chapterDao = chapterDao;
	}
	/**
	 * 设置状态值名称集合。
	 * @param statusMap 
	 *	  状态值名称集合。
	 */
	public void setStatusMap(Map<Integer, String> statusMap) {
		logger.debug("注入状态值名称集合...");
		this.statusMap = statusMap;
	}
	/*
	 * 加载状态值名称。
	 * @see com.examw.netplatform.service.admin.settings.IChapterService#loadStatusName(java.lang.Integer)
	 */
	@Override
	public String loadStatusName(Integer status) {
		logger.debug(String.format("加载状态值［%d］名称...", status));
		if(status == null || this.statusMap == null || this.statusMap.size() == 0) return null;
		return this.statusMap.get(status);
	}
	/*
	 * (non-Javadoc)
	 * @see com.examw.netplatform.service.admin.settings.IChapterService#loadChapters(java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public List<TreeNode> loadChapters(String parentChapterId,String ignoreChapterId, boolean isSelf) {
		// TODO Auto-generated method stub
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see com.examw.netplatform.service.admin.settings.IChapterService#loadChapters(java.lang.String)
	 */
	@Override
	public List<TreeNode> loadChapters(String subjectId) {
		// TODO Auto-generated method stub
		return null;
	}
	/*
	 * 加载最大排序号。
	 * @see com.examw.netplatform.service.admin.settings.IChapterService#loadMaxOrder(java.lang.String)
	 */
	@Override
	public Integer loadMaxOrder(String parentChapterId) {
		logger.debug("加载最大排序号...");
		return this.chapterDao.loadMaxOrder(parentChapterId);
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.admin.settings.IChapterService#datagrid(com.examw.netplatform.model.admin.settings.ChapterInfo)
	 */
	@Override
	public DataGrid<ChapterInfo> datagrid(ChapterInfo info) {
		logger.debug("查询数据...");
		//分页排序
		PageHelper.startPage(info.getPage(), info.getRows(), StringUtils.trimToEmpty(info.getOrder()) + " " + StringUtils.trimToEmpty(info.getSort()));
		//查询数据
		final List<Chapter> list = this.chapterDao.findChapters(info);
		//分页信息
		final PageInfo<Chapter> pageInfo = new PageInfo<Chapter>(list);
		//初始化
		final DataGrid<ChapterInfo> grid = new DataGrid<ChapterInfo>();
		grid.setRows(this.changeModel(list));
		grid.setTotal(pageInfo.getTotal());
		//返回
		return grid;
	}
	//类型批量转换。
	private List<ChapterInfo> changeModel(List<Chapter> chapters){
		final List<ChapterInfo> list = new ArrayList<ChapterInfo>();
		if(chapters != null && chapters.size() > 0){
			for(Chapter chapter : chapters){
				if(chapter == null) continue;
				list.add(this.conversion(chapter));
			}
		}
		return list;
	}
	//类型转换
	private ChapterInfo conversion(Chapter data){
		ChapterInfo info = (ChapterInfo)data;
		info.setStatusName(this.loadStatusName(data.getStatus()));
		return info;
	}
	/*
	 *  更新数据。
	 * @see com.examw.netplatform.service.admin.settings.IChapterService#update(com.examw.netplatform.model.admin.settings.ChapterInfo)
	 */
	@Override
	public ChapterInfo update(ChapterInfo info) {
		logger.debug("更新数据....");
		if(info == null) return null;
		Chapter data = StringUtils.isBlank(info.getId()) ? null : this.chapterDao.getChapter(info.getId());
		boolean isAdded = false;
		if(isAdded = (data == null)){
			if(StringUtils.isBlank(info.getId())) info.setId(UUID.randomUUID().toString());
			data = new Chapter();
		}
		//赋值
		BeanUtils.copyProperties(info, data);
		//判断
		if(!StringUtils.isBlank(data.getPid())){
		    final	 Chapter parent = this.chapterDao.getChapter(data.getPid());
		    if(parent == null) throw new RuntimeException("上级章节ID["+data.getPid()+"]不存在！");
		    if(StringUtils.equalsIgnoreCase(parent.getId(), data.getId())){
		    	throw new RuntimeException("自己不能是自己的上级节点！");
		    }
		}
		//保存数据
		if(isAdded){
			logger.debug("新增章节...");
			this.chapterDao.insertChapter(data);
		}else {
			logger.debug("更新章节...");
			this.chapterDao.updateChapter(data);
		}
		//返回
		return this.conversion(data);
	}
	/*
	 * 删除数据。
	 * @see com.examw.netplatform.service.admin.settings.IChapterService#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		logger.debug("删除数据..." + StringUtils.join(ids,","));
		if(ids != null && ids.length > 0){
			for(String id : ids){
				if(StringUtils.isBlank(id)) continue;
				this.chapterDao.deleteChapter(id);
			}
		}
	}  
//	/*
//	 * 加载章节树。
//	 * @see com.examw.netplatform.service.admin.settings.IChapterService#loadChapters(java.lang.String, java.lang.String, boolean)
//	 */
//	@Override
//	public List<TreeNode> loadChapters(String parentChapterId,String ignoreChapterId, boolean isSelf) {
//		if(logger.isDebugEnabled()) logger.debug("加载章节集合...");
//		List<TreeNode> nodes = new ArrayList<>();
//		List<Chapter> chapters = this.chapterDao.loadChapters(parentChapterId,isSelf);
//		if(chapters != null && chapters.size() > 0){
//			for(Chapter chapter : chapters){
//				 TreeNode node = this.createChapterNode(chapter, ignoreChapterId);
//				 if(node != null) nodes.add(node);
//			}
//		}
//		return nodes;
//	}
//	/*
//	 * 加载章节树。
//	 * @see com.examw.netplatform.service.admin.settings.IChapterService#loadChapters(java.lang.String)
//	 */
//	@Override
//	public List<TreeNode> loadChapters(final String subjectId) {
//		if(logger.isDebugEnabled()) logger.debug("加载章节树集合...");
//		List<TreeNode> nodes = new ArrayList<>();
//		List<Chapter> chapters = this.chapterDao.findTopChapters(new ChapterInfo(){
//			private static final long serialVersionUID = 1L;
//			@Override
//			public String getSubjectId() {return subjectId;}
//		});
//		if(chapters != null && chapters.size() > 0){
//			for(Chapter chapter : chapters){
//				 TreeNode node = this.createChapterNode(chapter, null);
//				 if(node != null) nodes.add(node);
//			}
//		}
//		return nodes;
//	}
//	//创建章节节点。
//	private TreeNode createChapterNode(Chapter chapter, String ignoreChapterId){
//		if(chapter == null || (!StringUtils.isEmpty(ignoreChapterId) && chapter.getId().equalsIgnoreCase(ignoreChapterId))) return null;
//		TreeNode node = new TreeNode(chapter.getId(), chapter.getName());
//		Map<String, Object> attributes = new HashMap<>();
//		attributes.put("id", chapter.getId());
//		attributes.put("name", chapter.getName());
//		attributes.put("description", chapter.getDescription());
//		attributes.put("orderNo", chapter.getOrderNo());
//		if(chapter.getParent() != null){
//			attributes.put("pid", chapter.getParent().getId());
//		}
//		node.setAttributes(attributes);
//		if(chapter.getChildren() != null && chapter.getChildren().size() > 0){
//			List<TreeNode> childrenNodes = new ArrayList<>();
//			for(Chapter child : chapter.getChildren()){
//				if(child == null) continue;
//				TreeNode e = this.createChapterNode(child, ignoreChapterId);
//				if(e != null) childrenNodes.add(e);
//			}
//			if(childrenNodes.size() > 0) node.setChildren(childrenNodes);
//		}
//		return node;
//	}
}