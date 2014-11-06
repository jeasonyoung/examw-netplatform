package com.examw.netplatform.service.admin.papers.impl;
 
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date; 
import java.util.HashSet; 
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set; 
import java.util.TreeSet;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.papers.IItemDao;
import com.examw.netplatform.dao.admin.papers.IStructureItemDao;
import com.examw.netplatform.dao.admin.settings.IAgencyDao;
import com.examw.netplatform.dao.admin.settings.IChapterDao;
import com.examw.netplatform.dao.admin.settings.ISubjectDao;
import com.examw.netplatform.domain.admin.papers.Item;
import com.examw.netplatform.domain.admin.papers.Paper; 
import com.examw.netplatform.domain.admin.settings.Agency;
import com.examw.netplatform.domain.admin.settings.Chapter;
import com.examw.netplatform.domain.admin.settings.Subject;
import com.examw.netplatform.model.admin.papers.ItemInfo; 
import com.examw.netplatform.service.admin.papers.IItemService;
import com.examw.netplatform.service.impl.BaseDataServiceImpl;
import com.examw.netplatform.support.ItemCheckCodeHelper;
/**
 * 试题服务接口
 * 
 * @author fengwei.
 * @since 2014年5月6日 上午10:02:05.
 */
public class ItemServiceImpl extends BaseDataServiceImpl<Item, ItemInfo> implements IItemService {
	private static Logger logger = Logger.getLogger(ItemServiceImpl.class);
	private IItemDao itemDao;  
	private IStructureItemDao structureItemDao;
	private IAgencyDao agencyDao;
	private ISubjectDao subjectDao;
	private IChapterDao chapterDao;
	private ItemCheckCodeHelper itemCheckCodeHelper;
	private Map<String,String> typesMap;
	private Map<Integer, String> statusMap,judgeMap;
	/**
	 * 设置题目数据接口。
	 * @param itemDao
	 * 题目数据接口。
	 */
	public void setItemDao(IItemDao itemDao) {
		this.itemDao = itemDao;
	}
	/**
	 * 设置试卷题目数据接口。
	 * @param structureItemDao
	 * 试卷题目数据接口。
	 */
	public void setStructureItemDao(IStructureItemDao structureItemDao) {
		this.structureItemDao = structureItemDao;
	}
	/**
	 * 设置机构数据接口。
	 * @param agencyDao
	 * 机构数据接口。
	 */
	public void setAgencyDao(IAgencyDao agencyDao) {
		this.agencyDao = agencyDao;
	}
	/**
	 * 设置考试科目数据接口。
	 * @param subjectDao
	 * 考试科目数据接口。
	 */
	public void setSubjectDao(ISubjectDao subjectDao) {
		this.subjectDao = subjectDao;
	}
	/**
	 * 设置科目章节数据接口。
	 * @param chapterDao
	 * 科目章节数据接口。
	 */
	public void setChapterDao(IChapterDao chapterDao) {
		this.chapterDao = chapterDao;
	}
	/**
	 * 设置题目校验码工具类。
	 * @param itemCheckCodeHelper
	 */
	public void setItemCheckCodeHelper(ItemCheckCodeHelper itemCheckCodeHelper) {
		this.itemCheckCodeHelper = itemCheckCodeHelper;
	}
	/**
	 * 设置题目类型集合。
	 * @param typesMap
	 * 类型集合。
	 */
	public void setTypesMap(Map<String,String> typesMap) {
		if(typesMap == null || typesMap.size() == 0){
			this.typesMap = typesMap;
			return;
		}
		List<Map.Entry<String,String>> list = new ArrayList<>(typesMap.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String,String>>(){
			@Override
			public int compare(Entry<String, String> o1, Entry<String, String> o2) {
				return  o1.getKey().compareTo(o2.getKey());
			}
		});
		Map<String,String> map = new LinkedHashMap<>();
		for(Map.Entry<String,String> entry: list){
			map.put(entry.getKey(), entry.getValue());
		}
		this.typesMap = map;
	}
	/**
	 * 设置题目状态集合。
	 * @param statusMap
	 */
	public void setStatusMap(Map<Integer, String> statusMap) {
		this.statusMap = statusMap;
	}
	/**
	 * 设置判断题名称。
	 * @param judgeMap
	 */
	public void setJudgeMap(Map<Integer, String> judgeMap) {
		this.judgeMap = judgeMap;
	}
	/*
	 * 加载题型集合。
	 * @see com.examw.netplatform.service.admin.papers.IItemService#loadTypes()
	 */
	@Override
	public Map<String, String> loadTypes() {
		return this.typesMap;
	}
	/*
	 * 加载全部选择题类型集合。
	 * @see com.examw.netplatform.service.admin.papers.IItemService#loadAllChoiceTypes()
	 */
	@Override
	public Map<String, String> loadAllChoiceTypes() {
		Map<String,String> map = new LinkedHashMap<>();
		Integer [] choices = new Integer []{ Item.TYPE_SINGLE, Item.TYPE_MULTY,Item.TYPE_UNCERTAIN};
		for(int i = 0; i < choices.length; i++){
			if(choices[i] == null) continue;
			String value = this.typesMap.get(choices[i].toString());
			if(StringUtils.isEmpty(value)) continue;
			map.put(choices[i].toString(), value);
		}
		return map;
	}
	/*
	 * 加载题目状态名称。
	 * @see com.examw.netplatform.service.admin.papers.IItemService#loadStatusName(java.lang.Integer)
	 */
	@Override
	public String loadStatusName(Integer status) {
		if(status == null || this.statusMap == null || this.statusMap.size() == 0) return null;
		return this.statusMap.get(status);
	}
	/*
	 * 加载判断题名称。
	 * @see com.examw.netplatform.service.admin.papers.IItemService#loadJudgeTypeName(java.lang.Integer)
	 */
	@Override
	public String loadJudgeTypeName(Integer judge) {
		if(judge == null || this.judgeMap == null || this.judgeMap.size() == 0) return "";
		return this.judgeMap.get(judge);
	}
	/**
	 * 题目校验码工具。
	 * @param info
	 * @return
	 */
	protected String loadItemCheckCode(ItemInfo info) {
		return this.itemCheckCodeHelper.createCheckCode(info);
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Item> find(ItemInfo info) {
		return this.itemDao.findItems(info);
	}
	/*
	 * 类型转换。
	 * @see com.examw.netplatform.service.admin.papers.IItemService#conversion(com.examw.netplatform.domain.admin.papers.Item)
	 */
	@Override
	public ItemInfo conversion(Item item) {
		if(item == null) return null; 
		return this.changeModel(item);
	}
	/*
	 * 类型转换。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected ItemInfo changeModel(Item data) {
		if(data == null) return null;
		ItemInfo info = new ItemInfo();
		BeanUtils.copyProperties(data, info, new String[]{"children"});
		if(info.getType() != null){
			info.setTypeName(this.typesMap.get(info.getType().toString()));
		}
		if(data.getAgency() != null){
			info.setAgencyId(data.getAgency().getId());
			info.setAgencyName(data.getAgency().getName());
		}
		if(data.getSubject() != null){
			info.setSubjectId(data.getSubject().getId());
			info.setSubjectName(data.getSubject().getName());
		}
		if(data.getChapter() != null){
			info.setChapterId(data.getChapter().getId());
			info.setChapterName(data.getChapter().getName());
		}
		if(data.getChildren() != null && data.getChildren().size() > 0){
			Set<ItemInfo> set = new TreeSet<>(new Comparator<ItemInfo>(){
				@Override
				public int compare(ItemInfo o1, ItemInfo o2) {
					 return o1.getOrderNo() - o2.getOrderNo();
				}
			});
			for(Item item : data.getChildren()){
				ItemInfo itemInfo = this.changeModel(item);
				if(itemInfo != null) {
					set.add(itemInfo);
				}
			}
			if(set.size() > 0)info.setChildren(set);
		}
		return info;
	}
	/*
	 * 查询统计。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(ItemInfo info) {
		return this.itemDao.total(info);
	}
	/*
	 * 更新数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public ItemInfo update(ItemInfo info) {
		this.updateItem(info);
		return info;
	}
	@Override
	public Item updateItem(ItemInfo info){
		if(info == null) return null;
		String checkCode = this.loadItemCheckCode(info);
		Item data = this.itemDao.loadItem(checkCode);
		if(data != null){
			info.setCheckCode(checkCode);
			return data;
		}
		data = StringUtils.isEmpty(info.getId()) ? null : this.itemDao.load(Item.class, info.getId());
		boolean isAdded = false;
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())){
				info.setId(UUID.randomUUID().toString());
			}
			info.setCheckCode(checkCode);
			info.setCreateTime(new Date());
			data = new Item();
		}
		while(data.getParent() != null){
			data =  data.getParent();
		}
		if(!isAdded){
			if(data.getCreateTime() == null){
				data.setCreateTime(new Date());
			}
			info.setCreateTime(data.getCreateTime());
			if(StringUtils.isEmpty(data.getCreateUsername())){
				data.setCreateUsername(info.getCreateUsername());
			}else{
				info.setCreateUsername(data.getCreateUsername());
			}
		}
		if(StringUtils.isEmpty(data.getCheckCode()) || !data.getCheckCode().equalsIgnoreCase(checkCode)){
			if(data.getChildren() != null && data.getChildren().size() > 0){
				data.getChildren().clear();
			}
			this.copyItemProperties(info, data, isAdded);
		}
		if(isAdded) this.itemDao.save(data); 
		return data;
	}
	/**
	 * 
	 * @param source
	 * @param target
	 * @param isAdded
	 */
	private void copyItemProperties(ItemInfo source, Item target, boolean isAdded){
		if(source == null || target == null) return; 
		if(!isAdded){
			source.setId(target.getId());
			source.setCreateTime(target.getCreateTime());
			source.setCreateUsername(target.getCreateUsername());
		}
		BeanUtils.copyProperties(source, target, new String[]{"children"});
		if(!StringUtils.isEmpty(source.getAgencyId()) && (target.getAgency() == null || !target.getAgency().getId().equalsIgnoreCase(source.getAgencyId()))){
			Agency agency = this.agencyDao.load(Agency.class, source.getAgencyId());
			if(agency != null){
				target.setAgency(agency);
				source.setAgencyName(agency.getName());
			}
		}
		if(!StringUtils.isEmpty(source.getSubjectId()) && (target.getSubject() == null || !target.getSubject().getId().equalsIgnoreCase(source.getSubjectId()))){
			Subject subject = this.subjectDao.load(Subject.class, source.getSubjectId());
			if(subject != null){
				target.setSubject(subject);
				source.setSubjectName(subject.getName());
			}
		}
		if(!StringUtils.isEmpty(source.getChapterId()) && (target.getChapter() == null || ! target.getChapter().getId().equalsIgnoreCase(source.getChapterId()))){
			Chapter chapter = this.chapterDao.load(Chapter.class, source.getChapterId());
			if(chapter != null){
				target.setChapter(chapter);
				source.setChapterName(chapter.getName());
			}
		}
		if(source.getChildren() != null && source.getChildren().size() > 0){
			Set<Item> children = new HashSet<>();
			for(ItemInfo info : source.getChildren()){
				if(info == null) continue;
				Item item = StringUtils.isEmpty(info.getId()) ? null : this.itemDao.load(Item.class, info.getId());
				boolean b = false;
				if(b = (item == null)){
					if(StringUtils.isEmpty(info.getId())){
						info.setId(UUID.randomUUID().toString());
					}
					item = new Item();
				}
				this.copyItemProperties(info, item, b);
				item.setParent(target);
				children.add(item);
			}
			target.setChildren(children);
		}
	}
	/*
	 * 删除题目。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(ids == null || ids.length == 0) return;
		for(int  i = 0; i < ids.length; i++){
			if(StringUtils.isEmpty(ids[i])) continue;
			Item data = this.itemDao.load(Item.class, ids[i]);
			if(data != null){
				 Paper paper = this.structureItemDao.loadRandomPaper(data.getId());
				 if(paper == null){
					 this.itemDao.delete(data);
					 logger.info("删除题目:id=" + ids[i]);
					 continue;
				 }
				 String err = "题目［"+ data.getContent() +"］还在试卷［"+ paper.getName() +"］中，请先从试卷中删除！";
				 logger.error(err);
				 throw new RuntimeException(err);
			}
		}
	}
}