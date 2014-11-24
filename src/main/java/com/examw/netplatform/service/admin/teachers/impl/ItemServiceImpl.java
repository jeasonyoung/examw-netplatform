package com.examw.netplatform.service.admin.teachers.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.teachers.IItemDao;
import com.examw.netplatform.dao.admin.teachers.IStructureDao;
import com.examw.netplatform.domain.admin.teachers.Item;
import com.examw.netplatform.domain.admin.teachers.Practice;
import com.examw.netplatform.domain.admin.teachers.Structure;
import com.examw.netplatform.model.admin.teachers.ItemInfo;
import com.examw.netplatform.service.admin.teachers.IItemService;
import com.examw.netplatform.service.admin.teachers.ItemParser;
import com.examw.netplatform.service.impl.BaseDataServiceImpl;
/**
 * 随堂练习试题服务接口实现类。
 * 
 * @author yangyong
 * @since 2014年11月24日
 */
public class ItemServiceImpl extends BaseDataServiceImpl<Item, ItemInfo> implements IItemService {
	private static final Logger logger = Logger.getLogger(ItemServiceImpl.class);
	private IItemDao itemDao;
	private IStructureDao structureDao;
	private Map<Integer, ItemParser> itemParsers;
	/**
	 * 设置试题数据接口。
	 * @param itemDao 
	 *	  试题数据接口。
	 */
	public void setItemDao(IItemDao itemDao) {
		if(logger.isDebugEnabled()) logger.debug("注入试题数据接口...");
		this.itemDao = itemDao;
	}
	/**
	 * 设置随堂练习结构数据接口。
	 * @param structureDao 
	 *	  随堂练习结构数据接口。
	 */
	public void setStructureDao(IStructureDao structureDao) {
		if(logger.isDebugEnabled()) logger.debug("注入随堂练习结构数据接口...");
		this.structureDao = structureDao;
	}
	/**
	 * 设置试题题型解析集合。
	 * @param itemParsers 
	 *	  试题题型解析集合。
	 */
	public void setItemParsers(Map<Integer, ItemParser> itemParsers) {
		if(logger.isDebugEnabled()) logger.debug("注入试题题型解析集合...");
		this.itemParsers = itemParsers;
	}
	/*
	 * 加载题型值名称。
	 * @see com.examw.netplatform.service.admin.teachers.IItemService#loadTypeName(java.lang.Integer)
	 */
	@Override
	public String loadTypeName(Integer type) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载题型值［%d］名称...", type));
		if(this.itemParsers == null || this.itemParsers.size() == 0) throw new RuntimeException("未配置题型解析集合！");
		ItemParser parser = this.itemParsers.get(type);
		if(parser == null) throw new RuntimeException(String.format("题型［%d］未配置解析！", type));
		return parser.getTypeName();
	}
	/**
	 * 加载题型解析接口。
	 * @param type
	 * 题型值。
	 * @return
	 * 题型解析接口。
	 */
	private ItemParser loadParser(Integer type){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载题型［%d］解析接口...", type));
		if(this.itemParsers == null || this.itemParsers.size() == 0) throw new RuntimeException("未配置题型解析集合！");
		ItemParser parser = this.itemParsers.get(type);
		if(parser == null) throw new RuntimeException(String.format("题型［%d］未配置解析！", type));
		return parser;
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Item> find(ItemInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		return this.itemDao.findItems(info);
	}
	/*
	 * 加载试题信息。
	 * @see com.examw.netplatform.service.admin.teachers.IItemService#loadItem(java.lang.String)
	 */
	@Override
	public ItemInfo loadItem(String itemId) {
		if(logger.isDebugEnabled()) logger.debug("加载试题信息...");
		if(StringUtils.isEmpty(itemId)) throw new RuntimeException("试题ID为空！");
		Item item = this.itemDao.load(Item.class, itemId);
		if(item == null) throw new RuntimeException(String.format("试题［%s］不存在！", itemId));
		ItemParser parser = this.loadParser(item.getType());
		return parser.conversion(item, true);
	}
	/*
	 * 数据模型转换。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected ItemInfo changeModel(Item data) {
		if(logger.isDebugEnabled()) logger.debug("数据模型转换 Item => ItemInfo ...");
		if(data == null) return null;
		ItemParser parser = this.loadParser(data.getType());
		ItemInfo info = parser.conversion(data, false);
		Structure structure = null;
		if((structure = data.getStructure()) != null){
			info.setStructureId(structure.getId());
			Practice practice = null;
			if((practice = structure.getPractice()) != null){
				info.setPracticeId(practice.getId());
			}
		}
		if(info.getType() != null){
			info.setTypeName(this.loadTypeName(info.getType()));
		}
		return info;
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(ItemInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		return this.itemDao.total(info);
	}
	/*
	 * 更新数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public ItemInfo update(ItemInfo info) {
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		boolean isAdded = false;
		Item item = StringUtils.isEmpty(info.getId()) ? null : this.itemDao.load(Item.class, info.getId());
		if(isAdded = (item == null)){
			if(StringUtils.isEmpty(info.getId())) info.setId(UUID.randomUUID().toString());
			item = new Item();
		}
		if(StringUtils.isEmpty(info.getStructureId())) throw new RuntimeException("所属随堂练习结构ID为空！");
		Structure structure = this.structureDao.load(Structure.class, info.getStructureId());
		if(structure == null) throw new RuntimeException(String.format("所属随堂练习结构［%s］不存在！", info.getStructureId()));
		item.setStructure(structure);
		
		ItemParser parser = this.loadParser(info.getType());
		parser.parser(info, item);
		if(isAdded)this.itemDao.save(item);
		
		return this.changeModel(item);
	}
	/*
	 * 删除数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled()) logger.debug(String.format("删除数据:%s...", Arrays.toString(ids)));
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length;i++){
			if(StringUtils.isEmpty(ids[i])) continue;
			Item item = this.itemDao.load(Item.class, ids[i]);
			if(item != null){
				if(logger.isDebugEnabled()) logger.debug(String.format("删除数据:%s ...", ids[i]));
				this.itemDao.delete(item);
			}
		}
	}
}