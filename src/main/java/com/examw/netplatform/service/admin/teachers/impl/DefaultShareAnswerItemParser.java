package com.examw.netplatform.service.admin.teachers.impl;

import org.apache.log4j.Logger;

import com.examw.netplatform.domain.admin.teachers.Item;
import com.examw.netplatform.model.admin.teachers.ItemInfo;
import com.examw.netplatform.service.admin.teachers.IItemService;
import com.examw.netplatform.service.admin.teachers.ItemType;

/**
 * 共享答案题解析器。
 * 
 * @author yangyong
 * @since 2014年9月24日
 */
public class DefaultShareAnswerItemParser extends DefaultItemParser {
	private static final Logger logger = Logger.getLogger(DefaultShareAnswerItemParser.class);
	private IItemService itemService;
	/**
	 * 构造函数。
	 * @param typeName
	 */
	public DefaultShareAnswerItemParser(String typeName) {
		super(typeName);
	}
	/**
	 * 设置试题服务接口。
	 * @param itemService 
	 *	  试题服务接口。
	 */
	public void setItemService(IItemService itemService) {
		if(logger.isDebugEnabled()) logger.debug("注入试题服务接口...");
		this.itemService = itemService;
	}
	/*
	 * 计算试题数据。
	 * @see com.examw.netplatform.service.admin.teachers.impl.DefaultItemParser#calculationCount(com.examw.netplatform.model.admin.teachers.ItemInfo)
	 */
	@Override
	public Integer calculationCount(ItemInfo source) {
		if(logger.isDebugEnabled()) logger.debug("计算试题数据...");
		if(source == null || source.getChildren() == null){
			return super.calculationCount(source);
		}
		int max_order = 0, count = 1;
		for(ItemInfo info : source.getChildren()){
			if(info == null) continue;
			if(info.getOrderNo() > max_order){
				max_order = info.getOrderNo();
				count = (info.getChildren() == null ? count : info.getChildren().size());
			}
		}
		return count;
	}
	/*
	 * 数据模型转换。
	 * @see com.examw.netplatform.service.admin.teachers.impl.DefaultItemParser#conversion(com.examw.netplatform.domain.admin.teachers.Item, boolean)
	 */
	@Override
	public ItemInfo conversion(Item source, boolean isAll) {
		if(logger.isDebugEnabled()) logger.debug(String.format("数据模型转换:%s ...", isAll));
		ItemInfo info = super.conversion(source, isAll);
		if(isAll && source.getType() == ItemType.SHARE_ANSWER.getValue() && source.getChildren() != null && source.getChildren().size() > 0){
			int max_order = 0;
			ItemInfo itemInfo = null;
			for(ItemInfo item : info.getChildren()){
				if(item == null) continue;
				if(item.getOrderNo() > max_order){
					max_order = item.getOrderNo();
					 itemInfo = item;
				}
			}
			if(itemInfo != null && itemInfo.getChildren() != null && itemInfo.getChildren().size() > 0){
				for(ItemInfo item2 : itemInfo.getChildren()){
					if(item2 == null || item2.getType() == null) continue;
					info.setTypeName(this.itemService.loadTypeName(item2.getType()));
				}
			}
		}
		return info;
	}
}