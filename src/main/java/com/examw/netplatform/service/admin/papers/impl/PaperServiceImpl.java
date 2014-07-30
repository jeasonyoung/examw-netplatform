package com.examw.netplatform.service.admin.papers.impl;
 
import java.util.ArrayList; 
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.model.DataGrid;
import com.examw.netplatform.dao.admin.agency.IAgencyDao;
import com.examw.netplatform.dao.admin.papers.IItemDao;
import com.examw.netplatform.dao.admin.papers.IPaperDao;
import com.examw.netplatform.dao.admin.papers.IStructureDao; 
import com.examw.netplatform.dao.admin.papers.IStructureItemDao;
import com.examw.netplatform.dao.admin.settings.ISubjectDao;
import com.examw.netplatform.domain.admin.agency.Agency;
import com.examw.netplatform.domain.admin.papers.Item;
import com.examw.netplatform.domain.admin.papers.Paper;
import com.examw.netplatform.domain.admin.papers.Structure; 
import com.examw.netplatform.domain.admin.papers.StructureItem;
import com.examw.netplatform.domain.admin.papers.StructureItemScore;
import com.examw.netplatform.domain.admin.settings.Subject;
import com.examw.netplatform.model.admin.papers.ItemInfo;
import com.examw.netplatform.model.admin.papers.PaperInfo;
import com.examw.netplatform.model.admin.papers.PaperPreview;
import com.examw.netplatform.model.admin.papers.StructureInfo;
import com.examw.netplatform.model.admin.papers.StructureItemInfo;
import com.examw.netplatform.model.admin.papers.StructureItemScoreInfo;
import com.examw.netplatform.model.admin.papers.StructurePreview;
import com.examw.netplatform.service.admin.papers.IItemService;
import com.examw.netplatform.service.admin.papers.IPaperService;
import com.examw.netplatform.service.impl.BaseDataServiceImpl;

/**
 * 试卷服务实现类
 * @author fengwei.
 * @since 2014年5月4日 上午11:09:56.
 */
public class PaperServiceImpl extends BaseDataServiceImpl<Paper, PaperInfo> implements IPaperService{
	private static Logger logger = Logger.getLogger(PaperServiceImpl.class);
	private IPaperDao paperDao;
	private IAgencyDao agencyDao;
	private ISubjectDao subjectDao;
	private IStructureDao structureDao;
	private IStructureItemDao structureItemDao;
	private IItemDao itemDao;
	
	private IItemService itemService;
	
	private Map<String,String> typeMap ;
	private Map<Integer,String> statusMap;
	/**
	 * 设置试卷数据接口。
	 * @param paperDao
	 * 试卷数据接口。
	 */
	public void setPaperDao(IPaperDao paperDao) {
		this.paperDao = paperDao;
	}
	/**
	 * 设置所属机构数据接口。
	 * @param agencyDao
	 * 机构数据接口。
	 */
	public void setAgencyDao(IAgencyDao agencyDao) {
		this.agencyDao = agencyDao;
	}
	/**
	 * 设置所属科目数据接口。
	 * @param subjectDao
	 * 科目数据接口。
	 */
	public void setSubjectDao(ISubjectDao subjectDao) {
		this.subjectDao = subjectDao;
	}
	/**
	 * 设置试卷结构题目数据接口。
	 * @param structureItemDao
	 * 试卷结构题目数据接口。
	 */
	public void setStructureDao(IStructureDao structureDao) {
		this.structureDao = structureDao;
	}
	/**
	 * 设置试卷题目数据接口。
	 * @param structureItemDao
	 */
	public void setStructureItemDao(IStructureItemDao structureItemDao) {
		this.structureItemDao = structureItemDao;
	}
	/**
	 * 设置题目数据接口。
	 * @param itemDao
	 * 题目数据接口。
	 */
	public void setItemDao(IItemDao itemDao){
		this.itemDao = itemDao;
	}
	/**
	 * 设置题目服务接口。
	 * @param itemService
	 * 题目服务接口。
	 */
	public void setItemService(IItemService itemService) {
		this.itemService = itemService;
	}
	/**
	 * 设置试卷类型。
	 * @param typeMap
	 * 试卷类型。
	 */
	public void setTypeMap(Map<String, String> typeMap) {
		this.typeMap = typeMap;
	}
	/**
	 * 设置试卷状态。
	 * @param statusMap
	 * 试卷状态。
	 */
	public void setStatusMap(Map<Integer, String> statusMap) {
		this.statusMap = statusMap;
	}
	/*
	 * 加载试卷类型数据。
	 * @see com.examw.netplatform.service.admin.papers.IPaperService#loadTypes()
	 */
	@Override
	public Map<String, String> loadTypes() {
		return this.typeMap;
	}
	/*
	 * 获取试卷状态名称。
	 * @see com.examw.netplatform.service.admin.papers.IPaperService#getStatusName(java.lang.Integer)
	 */
	@Override
	public String getStatusName(Integer status) {
		if(this.statusMap == null) return null;
		return this.statusMap.get(status);
	}
	/*
	 * 试卷审核。
	 * @see com.examw.netplatform.service.admin.papers.IPaperService#updateAudit(java.lang.String)
	 */
	@Override
	public PaperInfo updateAudit(String paperId) {
		logger.info("试卷审核...");
		if(StringUtils.isEmpty(paperId)) return null;
		Paper data = this.paperDao.load(Paper.class, paperId);
		if(data == null) return null;
		if(data.getStatus() == Paper.STATUS_DISABLE){
			data.setStatus(Paper.STATUS_ENABLED);
		}
		PaperInfo info = new PaperInfo();
		BeanUtils.copyProperties(data, info);
		logger.info("试卷审核完成。");
		return info;
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Paper> find(PaperInfo info) {
		return this.paperDao.findPapers(info);
	}
	/*
	 * 类型转换。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected PaperInfo changeModel(Paper data) {
		if(data == null) return null;
		PaperInfo info = new PaperInfo();
		this.changeModel(data, info);
		return info;
	}
	/**
	 * 类型转换。
	 * @param source
	 * @param target
	 */
	private void  changeModel(Paper source, PaperInfo target) {
		if(source == null || target == null) return ;
		BeanUtils.copyProperties(source, target,new String[]{"structures"});
		if(source.getType() != null && this.typeMap != null){
			target.setTypeName(this.typeMap.get(source.getType().toString()));
		}
		if(source.getStatus() != null && this.statusMap != null){
			target.setStatusName(this.statusMap.get(source.getStatus()));
		}
		if(source.getAgency() != null){
			target.setAgencyId(source.getAgency().getId());
			target.setAgencyName(source.getAgency().getName());
		}
		if(source.getSubject() != null){
			target.setSubjectId(source.getSubject().getId());
			target.setSubjectName(source.getAgency().getName());
		}
	}
	/*
	 * 查询统计。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(PaperInfo info) {
		return this.paperDao.total(info);
	}
	/*
	 * 加载试卷预览。
	 * @see com.examw.netplatform.service.admin.papers.IPaperService#loadPaperPreview(java.lang.String)
	 */
	public PaperPreview loadPaperPreview(String paperId){
		if(StringUtils.isEmpty(paperId)) return null;
		Paper paper = this.paperDao.load(Paper.class, paperId);
		if(paper == null) return null;
		PaperPreview view = new PaperPreview();
		this.changeModel(paper, view);
		if(paper.getStructures() != null && paper.getStructures().size() > 0){
			List<StructurePreview> list = new ArrayList<>();
			for(Structure data: paper.getStructures()){
				StructurePreview sp = this.createStructurePreview(data);
				if(sp != null){
					list.add(sp);
				}
			}
			view.setStructures(list);
		}
		return view;
	}
	/**
	 * 类型转换。
	 * @param data
	 * @return
	 */
	private StructurePreview createStructurePreview(Structure data){
		if(data == null) return null;
		StructurePreview preview = new StructurePreview();
		BeanUtils.copyProperties(data, preview, new String[]{"children"});
		//题型
		if(data.getType() != null && this.itemService.loadTypes() != null){
			preview.setTypeName(this.itemService.loadTypes().get(data.getType()));
		}
		//结构下的题目。
		if(data.getStructureItems() != null && data.getStructureItems().size() > 0){
			List<StructureItemInfo> items = new ArrayList<>();
			for(StructureItem si: data.getStructureItems()){
				StructureItemInfo item = this.createStructureItemInfo(si);
				if(item != null) items.add(item);
			}
			preview.setItems(items);
		}
		if(data.getChildren() != null && data.getChildren().size() > 0){
			Set<StructurePreview> children = new TreeSet<>(new Comparator<StructurePreview>(){
				@Override
				public int compare(StructurePreview o1, StructurePreview o2) {
					return o1.getOrderNo() - o2.getOrderNo();
				}
			});
			for(Structure s : data.getChildren()){
				StructurePreview view = this.createStructurePreview(s);
				if(view != null) children.add(view);
			}
			if(children.size() > 0) {
				preview.setChildren(children);
			}
		}
		return preview;
	}
	/*
	 * 更新数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public PaperInfo update(PaperInfo info) {
		if(info == null) return null;
		boolean isAdded = false;
		Paper data = StringUtils.isEmpty(info.getId()) ?  null : this.paperDao.load(Paper.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())){
				info.setId(UUID.randomUUID().toString());
			}
			data = new Paper();
		}
		if(info.getStatus() == null) info.setStatus(Paper.STATUS_DISABLE);
		
		if(!StringUtils.isEmpty(info.getAgencyId()) && (data.getAgency() == null || !data.getAgency().getId().equalsIgnoreCase(info.getAgencyId()))){
			Agency agency = this.agencyDao.load(Agency.class, info.getAgencyId());
			if(agency != null) data.setAgency(agency);
		}
		if(!StringUtils.isEmpty(info.getSubjectId()) && (data.getSubject() == null || !data.getSubject().getId().equalsIgnoreCase(info.getSubjectId()))){
			Subject subject = this.subjectDao.load(Subject.class, info.getSubjectId());
			if(subject != null) data.setSubject(subject);
		}
		
		BeanUtils.copyProperties(info, data);
		
		if(data.getAgency() != null)  info.setAgencyName(data.getAgency().getName());
		if(data.getSubject() != null) info.setSubjectName(data.getSubject().getName());
		if(info.getType() != null && this.typeMap != null) info.setTypeName(this.typeMap.get(info.getType()));
		if(info.getStatus() != null && this.statusMap != null) info.setTypeName(this.statusMap.get(info.getStatus()));
		
		if(isAdded) this.paperDao.save(data);
		
		return info;
	}
	/*
	 * 删除数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			Paper data = this.paperDao.load(Paper.class, ids[i]);
			if(data != null) this.paperDao.delete(data);
 		}
	}
	/*
	 * 根据试卷ID查询数据。
	 * @see com.examw.netplatform.service.admin.papers.IPaperService#find(java.lang.String)
	 */
	@Override
	public Paper find(String paperId) {
		logger.info("查找试卷...");
		if(StringUtils.isEmpty(paperId)) return null;
		return this.paperDao.load(Paper.class, paperId);
	}
	/*
	 * 加载试卷结构数据。
	 * @see com.examw.netplatform.service.admin.papers.IPaperService#loadStructure(java.lang.String)
	 */
	@Override
	public List<StructureInfo> loadStructure(String paperId) {
		if(StringUtils.isEmpty(paperId)) return null;
		List<Structure> list = this.structureDao.findStructures(paperId);
		if(list == null || list.size() == 0) return null;
		 List<StructureInfo> results = new ArrayList<>();
		 for(Structure data : list){
			 StructureInfo info = this.createStructureInfo(data);
			 if(info != null) results.add(info);
		 }
		return results;
	}
	/**
	 * 类型转换。
	 * @param data
	 * @return
	 */
	private StructureInfo createStructureInfo(Structure data){
		if(data == null) return null;
		StructureInfo info = new StructureInfo();
		BeanUtils.copyProperties(data, info, new String[]{"children"});
		//题型
		if(data.getType() != null && this.itemService.loadTypes() != null){
			info.setTypeName(this.itemService.loadTypes().get(data.getType()));
		}
		if(data.getChildren() != null && data.getChildren().size() > 0){
			Set<StructureInfo> children = new TreeSet<>(new Comparator<StructureInfo>(){
				@Override
				public int compare(StructureInfo o1, StructureInfo o2) {
					return o1.getOrderNo() - o2.getOrderNo();
				}
			});
			for(Structure s : data.getChildren()){
				StructureInfo si = this.createStructureInfo(s);
				if(si != null) children.add(si);
			}
			if(children.size() > 0) info.setChildren(children);
		}
		return info;
	}
	/*
	 * 更新试卷结构。
	 * @see com.examw.netplatform.service.admin.papers.IPaperService#updateStructure(java.lang.String, com.examw.netplatform.model.admin.papers.StructureInfo)
	 */
	@Override
	public void updateStructure(String paperId, StructureInfo info) {
		if(StringUtils.isEmpty(paperId) || info == null) return;
		boolean isAdded = false;
		Structure data = StringUtils.isEmpty(info.getId()) ?  null : this.structureDao.load(Structure.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())){
				info.setId(UUID.randomUUID().toString());
			}
			data = new Structure();
		}
		BeanUtils.copyProperties(info,data, new String[]{"children"});
		if(data.getPaper() == null || !data.getPaper().getId().equalsIgnoreCase(paperId)){
			data.setPaper(this.paperDao.load(Paper.class, paperId)); 
		}
		if(!StringUtils.isEmpty(info.getPid()) && (data.getParent() == null || !data.getParent().getId().equalsIgnoreCase(info.getPid()))){
			data.setParent(this.structureDao.load(Structure.class, info.getPid())); 
		}
		if(isAdded)this.structureDao.save(data);
	}
	/*
	 * 删除结构
	 * @see com.examw.netplatform.service.admin.papers.IPaperService#deleteStructure(java.lang.String)
	 */
	@Override
	public void deleteStructure(String structureId) {
		if(StringUtils.isEmpty(structureId)) return;
		 this.deleteStructure(this.structureDao.load(Structure.class, structureId));
	}
	/***
	 * 删除试卷结构。
	 * @param data
	 */
	private void deleteStructure(Structure data){
		if(data == null) return;
		//删除叶子结构。
		if(data.getChildren() != null && data.getChildren().size() > 0){
			for(Structure child: data.getChildren()){
				this.deleteStructure(child);
			}
		}
		//删除试卷题目。
		if(data.getStructureItems() != null && data.getStructureItems().size() > 0){
			for(StructureItem item : data.getStructureItems()){
				this.deleteStructureItems(item);
			}
		}
		//删除结构。
		this.structureDao.delete(data);
	}
	/*
	 * 查询试卷题目。
	 * @see com.examw.netplatform.service.admin.papers.IPaperService#findStructureItems(com.examw.netplatform.model.admin.papers.StructureItemInfo)
	 */
	@Override
	public DataGrid<StructureItemInfo> findStructureItems(String paperId,StructureItemInfo info) {
		DataGrid<StructureItemInfo> grid = new DataGrid<StructureItemInfo>();
		List<StructureItem> list = this.structureItemDao.findStructureItems(paperId, info);
		if(list != null){
			List<StructureItemInfo> rows = new ArrayList<>();
			for(StructureItem data : list){
				StructureItemInfo e = new StructureItemInfo();
				BeanUtils.copyProperties(data, e, new String[]{"item","structure","itemScores"});
				if(data.getItem() != null){
					if(e.getItem() == null) e.setItem(new ItemInfo());
					BeanUtils.copyProperties(data.getItem(), e.getItem(), new String[]{"children"});
					if(data.getItem().getAgency() != null){
						e.getItem().setAgencyId(data.getItem().getAgency().getId());
						e.getItem().setAgencyName(data.getItem().getAgency().getName());
					}
					if(data.getItem().getSubject() != null){
						e.getItem().setSubjectId(data.getItem().getSubject().getId());
						e.getItem().setSubjectName(data.getItem().getSubject().getName());
					}
					if(data.getItem().getChapter() != null){
						e.getItem().setChapterId(data.getItem().getChapter().getId());
						e.getItem().setChapterName(data.getItem().getChapter().getName());
					}
					e.setType(data.getItem().getType());
					if(e.getType() != null) {
						e.setTypeName(this.itemService.loadTypes().get(e.getType().toString()));
					}
					e.getItem().setTypeName(e.getTypeName());
				}
				if(data.getStructure() != null){
					 e.setStructureId(data.getStructure().getId());
				}
				rows.add(e);
			}
			grid.setRows(rows);
			grid.setTotal(this.structureItemDao.total(paperId, info));
		}
		return grid;
	}
	/*
	 * 加载试卷题目。
	 * @see com.examw.netplatform.service.admin.papers.IPaperService#loadPaperItem(java.lang.String)
	 */
	@Override
	public StructureItemInfo loadPaperItem(String structureItemId) {
		if(StringUtils.isEmpty(structureItemId)) return null;
		StructureItem data = this.structureItemDao.load(StructureItem.class, structureItemId);
		if(data == null){
			logger.error("加载试卷题目失败！未找到试卷题目ID的数据，structureItemId=" + structureItemId);
			return null;
		}
		return this.createStructureItemInfo(data);
	}
	/**
	 * 创建试卷题目。
	 * @param data
	 * @return
	 */
	private StructureItemInfo createStructureItemInfo(StructureItem data){
		if(data == null) return null;
		StructureItemInfo info = new StructureItemInfo();
		BeanUtils.copyProperties(data, info, new String[]{"item","itemScores"});
		if(data.getStructure() != null){
			info.setStructureId(data.getStructure().getId());
		}
		ItemInfo itemInfo = this.itemService.conversion(data.getItem());
		if(itemInfo == null){
			logger.error("加载试卷题目失败！未能加载题目内容！");
			return null;
		}
		info.setType(itemInfo.getType());
		info.setTypeName(itemInfo.getTypeName());
		info.setItem(itemInfo);
		if(data.getItemScores() != null && data.getItemScores().size() > 0){
			Set<StructureItemScoreInfo> itemScores = new TreeSet<StructureItemScoreInfo>(new Comparator<StructureItemScoreInfo>(){
				@Override
				public int compare(StructureItemScoreInfo o1, StructureItemScoreInfo o2) {
					 return o1.getOrderNo() - o2.getOrderNo();
				}
			});
			for(StructureItemScore score : data.getItemScores()){
				if(score.getItem() == null) continue;
				StructureItemScoreInfo ssiInfo = new StructureItemScoreInfo();
				BeanUtils.copyProperties(score, ssiInfo);
				if(score.getItem() != null){
					ssiInfo.setItemId(score.getItem());
				}
				itemScores.add(ssiInfo);
			}
			info.setItemScores(itemScores);
		}
		return info;
	}
	/*
	 * 更新试卷题目。
	 * @see com.examw.netplatform.service.admin.papers.IPaperService#updateStructureItem(java.lang.String, com.examw.netplatform.model.admin.papers.StructureItemInfo)
	 */
	@Override
	public StructureItemInfo updateStructureItem(String paperId, StructureItemInfo info) {
		logger.info("更新试卷题目...");
		if(StringUtils.isEmpty(paperId) || info == null || info.getItem() == null){
			logger.error("试卷ID或试卷题目为空！");
			return null;
		}
		boolean isAdded = false;
		StructureItem data = StringUtils.isEmpty(info.getId()) ? null : this.structureItemDao.load(StructureItem.class, info.getId());
		if(data == null && !StringUtils.isEmpty(info.getStructureId()) && info.getItem() != null && !StringUtils.isEmpty(info.getItem().getId())){
			data =  this.structureItemDao.loadStructureItem(info.getStructureId(), info.getItem().getId());
		} 
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())) {
				info.setId(UUID.randomUUID().toString());
			}
			data = new StructureItem();
		}
		BeanUtils.copyProperties(info, data, new String[]{"item","itemScores"});
		if(!StringUtils.isEmpty(info.getStructureId()) && (data.getStructure() == null || !data.getStructure().getId().equalsIgnoreCase(info.getStructureId()))){
			Structure structure = this.structureDao.load(Structure.class, info.getStructureId());
			if(structure == null){
				logger.error("未找到试卷结构对象:StructureId=" + info.getStructureId());
				return null;
			}
			data.setStructure(structure);
		}
		Paper paper = this.paperDao.load(Paper.class, paperId);
		if(paper == null){
			logger.error("试卷不存在！id=" +  paperId);
			return null;
		}
		if(paper.getAgency() != null){
			info.getItem().setAgencyId(paper.getAgency().getId());
			info.getItem().setAgencyName(paper.getAgency().getName());
		}
		if(paper.getSubject() != null){
			info.getItem().setSubjectId(paper.getSubject().getId());
			info.getItem().setSubjectName(paper.getSubject().getName());
		}
		Item item = this.itemService.updateItem(info.getItem());
		if(item == null){
			logger.error("保存题目内容失败！item_id=" + info.getItem().getId());
			return null;
		} 
		data.setItem(item); 
		Set<StructureItemScore> itemScores = new HashSet<>();
		if(info.getItemScores() != null && info.getItemScores().size() > 0){
			for(StructureItemScoreInfo s : info.getItemScores()){
				if(s == null || StringUtils.isEmpty(s.getItemId())) continue;
				StructureItemScore itemScore = this.findStructureItemScore(data.getItemScores(), s.getId());
				if(itemScore == null){
					if(StringUtils.isEmpty(s.getId())){
						s.setId(UUID.randomUUID().toString());
					}
					itemScore = new StructureItemScore();
					itemScore.setStructureItem(data);
				}
				BeanUtils.copyProperties(s, itemScore);
				if(!StringUtils.isEmpty(s.getItemId()) && (itemScore.getItem() == null ||!itemScore.getItem().equalsIgnoreCase(s.getItemId()))){
					Item childItem = this.findItemChildren(item, s.getItemId());
					if(childItem == null) continue;
					itemScore.setItem(childItem.getId()); 
				}
				if(itemScore.getItem() != null){
					itemScores.add(itemScore);
				}
			}
		}
		data.setItemScores(itemScores);
		if(isAdded) this.structureItemDao.save(data);
		return info;
	}
	/**
	 * 根据ID查找题目；
	 * @param parent
	 * @param id
	 * @return
	 */
	private Item findItemChildren(Item parent, String id){
		if(parent == null || StringUtils.isEmpty(id)) return null;
		if(parent.getId().equalsIgnoreCase(id)) return parent;
		if(parent.getChildren() != null && parent.getChildren().size() > 0){
			for(Item item : parent.getChildren()){
				 Item i = this.findItemChildren(item, id);
				 if(i != null) return i;
			}
		}
		return null;
	}
	/**
	 * 
	 * @param set
	 * @param id
	 * @return
	 */
	private StructureItemScore findStructureItemScore(Set<StructureItemScore> set, String id){
		if(set == null || set.size() == 0 || StringUtils.isEmpty(id)) return null;
		for(StructureItemScore data : set){
			if(data == null) continue;
			if(data.getId().equalsIgnoreCase(id)){
				return data;
			}
		}
		return null;
	}
	/*
	 * 删除试卷题目。
	 * @see com.examw.netplatform.service.admin.papers.IPaperService#deleteStructureItems(java.lang.String[])
	 */
	@Override
	public void deleteStructureItems(String[] ids) {
		if(ids == null || ids.length == 0) return;
		for(int i  = 0; i < ids.length; i++){
			if(StringUtils.isEmpty(ids[i])) continue;
			this.deleteStructureItems(this.structureItemDao.load(StructureItem.class, ids[i]));
		}
	}
	/**
	 * 删除试卷题目。
	 * @param data
	 */
	private void deleteStructureItems(StructureItem data){
		if(data == null) return;
		String itemId = (data.getItem() == null) ? null : data.getItem().getId();
		this.structureItemDao.delete(data);
		if(!StringUtils.isEmpty(itemId)){
			Item item = this.itemDao.load(Item.class, itemId);
			if(item != null && this.structureItemDao.exists(itemId) <= 0){
				this.itemDao.delete(item);
			}
		}
	}
	/*
	 * 加载试卷题目最大的排序号
	 * @see com.examw.netplatform.service.admin.papers.IPaperService#loadPaperItemMaxOrderNo(java.lang.String)
	 */
	@Override
	public int loadPaperItemMaxOrderNo(String paperId) {
		Integer order = this.structureItemDao.loadMaxOrderNo(paperId);
		return order == null ? 0 : order.intValue();
	}
}