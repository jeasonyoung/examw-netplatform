package com.examw.netplatform.service.admin.teachers.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.teachers.IItemDao;
import com.examw.netplatform.domain.admin.teachers.Item;
import com.examw.netplatform.domain.admin.teachers.Structure;
import com.examw.netplatform.model.admin.teachers.ItemInfo;
import com.examw.netplatform.service.admin.teachers.ItemParser;
import com.examw.netplatform.service.admin.teachers.ItemType;
/**
 * 试题解析基础类。
 * 
 * @author yangyong
 * @since 2014年9月24日
 */
public class DefaultItemParser implements ItemParser {
	private static final Logger logger = Logger.getLogger(DefaultItemParser.class);
	protected IItemDao itemDao;
	private String typeName;
	/**
	 * 构造函数。
	 * @param typeName
	 * 题型名称。
	 */
	public DefaultItemParser(String typeName){
		if(logger.isDebugEnabled()) logger.debug(String.format("注入提型名称：%s",typeName));
		this.typeName = typeName;
	}
	/**
	 * 设置试题数据接口。
	 * @param itemDao 
	 *	  试题数据接口。
	 */
	public void setItemDao(IItemDao itemDao) {
		if(logger.isDebugEnabled()) logger.debug("注入试题数据接口...");
		this.itemDao = itemDao;
	}
	/*
	 *  获取题型名称。
	 * @see com.examw.test.service.library.ItemParser#getTypeName()
	 */
	@Override
	public String getTypeName() {
		if(logger.isDebugEnabled()) logger.debug(String.format("获取题型名称：%s...", this.typeName));
		return this.typeName;
	}
	/*
	 * 计算包含的试题数量。
	 * @see com.examw.netplatform.service.admin.teachers.ItemParser#calculationCount(com.examw.netplatform.model.admin.teachers.ItemInfo)
	 */
	@Override
	public Integer calculationCount(ItemInfo source) {
		return 1;
	}
	/*
	 * 试题解析。
	 * @see com.examw.netplatform.service.admin.teachers.ItemParser#parser(com.examw.netplatform.model.admin.teachers.ItemInfo, com.examw.netplatform.domain.admin.teachers.Item)
	 */
	@Override
	public void parser(ItemInfo source,Item target){
		if(logger.isDebugEnabled()) logger.debug("ItemInfo => Item ... ");
		if(source == null || target == null) return;
		source.setCount(this.calculationCount(source));
		if(this.changeModel(source, target)){
			this.checkItemAnswers(target);
		}
	}
	/**
	 * 数据模型转换。
	 * @param source
	 * @return
	 */
	protected boolean changeModel(ItemInfo source,Item target){
		if(logger.isDebugEnabled()) logger.debug("数据模型转换 ItemInfo => Item ...");
		if(source == null || target == null) return false;
		if(target.getChildren() != null && target.getChildren().size() > 0){
			target.getChildren().clear();
		}
		BeanUtils.copyProperties(source, target, new String[]{"children"});
		if(source.getChildren() != null && source.getChildren().size() > 0){
			if(target.getChildren() == null) target.setChildren(new HashSet<Item>());
			for(ItemInfo info : source.getChildren()){
				if(info == null) continue;
				Item item = StringUtils.isEmpty(info.getId()) ? null : this.itemDao.load(Item.class, info.getId());
				if(item == null){
					if(StringUtils.isEmpty(info.getId())){
						info.setId(UUID.randomUUID().toString());
					}
					item = new Item();
				}
				item.setParent(target);
				if(this.changeModel(info, item)){
					target.getChildren().add(item);
				}
			}
		}
		return true;
	}
	/**
	 * 检查试题答案。
	 * @param target
	 */
	protected void checkItemAnswers(Item target){
		if(logger.isDebugEnabled()) logger.debug("检查试题答案...");
		if(target == null) return;
		ItemType itemType = ItemType.convert(target.getType());
		if((itemType == ItemType.SHARE_TITLE) || (itemType == ItemType.SHARE_ANSWER)){
			return;
		}
		if(StringUtils.isEmpty(target.getAnswer())){
			throw new RuntimeException("试题未有答案！");
		}
		if((itemType == ItemType.SINGLE) &&  (target.getAnswer().indexOf(",") > -1)){
			throw new RuntimeException("单选题答案只能有一个答案！");
		}
		if((itemType == ItemType.MULTY) && (target.getAnswer().indexOf(",") == -1)){
			throw new RuntimeException("多选题答案应有不只一个答案！");
		}
	}
	/*
	 * 试题数据模型转换。
	 * @see com.examw.netplatform.service.admin.teachers.ItemParser#conversion(com.examw.netplatform.domain.admin.teachers.Item, boolean)
	 */
	@Override
	public ItemInfo conversion(Item source, boolean isAll) {
		if(logger.isDebugEnabled()) logger.debug("解析试题:Item => ItemInfo ...");
		if(source == null) return null;
		ItemInfo info = new ItemInfo();
		BeanUtils.copyProperties(source, info, new String[]{"children"});
		Structure structure = null;
		if((structure = source.getStructure()) != null){
			info.setStructureId(structure.getId());
		}
		if(isAll && source.getChildren() != null && source.getChildren().size() > 0){
			Set<ItemInfo> childrens = new TreeSet<>();
			for(Item item : source.getChildren()){
				if(item == null) continue;
				ItemInfo itemInfo = this.conversion(item, isAll);
				if(itemInfo != null) childrens.add(itemInfo);
			}
			if(childrens.size() > 0) info.setChildren(childrens);
		}
		return info;
	}
}