package com.examw.netplatform.service.admin.teachers.impl;

import org.apache.log4j.Logger;

import com.examw.netplatform.domain.admin.teachers.Item;
import com.examw.netplatform.model.admin.teachers.ItemInfo;
import com.examw.netplatform.service.admin.teachers.IItemService;
import com.examw.netplatform.service.admin.teachers.ItemType;

/**
 * 共享提干题默认解析器。
 * 
 * @author yangyong
 * @since 2014年9月24日
 */
public class DefaultShareTitleItemParser extends DefaultItemParser {
	private static final Logger logger = Logger.getLogger(DefaultShareTitleItemParser.class);
	private IItemService itemService;
	/**
	 * 构造函数。
	 * @param typeName
	 * 题型名称。
	 */
	public DefaultShareTitleItemParser(String typeName) {
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
	 * 计算试题数目。
	 * @see com.examw.netplatform.service.admin.teachers.impl.DefaultItemParser#calculationCount(com.examw.netplatform.model.admin.teachers.ItemInfo)
	 */
	@Override
	public Integer calculationCount(ItemInfo source) {
		if(logger.isDebugEnabled()) logger.debug("计算试题数目...");
		if(source == null || source.getChildren() == null){
			return super.calculationCount(source);
		}
		return source.getChildren().size();
	}
	/*
	 * 数据模型转换。
	 * @see com.examw.netplatform.service.admin.teachers.impl.DefaultItemParser#conversion(com.examw.netplatform.domain.admin.teachers.Item, boolean)
	 */
	@Override
	public ItemInfo conversion(Item source, boolean isAll) {
		if(logger.isDebugEnabled()) logger.debug(String.format("题型数据模型转换［%s］Item =>  ItemInfo  ...", isAll));
		ItemInfo info = super.conversion(source, isAll);
		if(isAll && (info.getType() == ItemType.SHARE_TITLE.getValue()) && info.getChildren() != null && info.getChildren().size() > 0){
			for(ItemInfo itemInfo : info.getChildren()){
				if(info == null || info.getType() == null) continue;
				itemInfo.setTypeName(this.itemService.loadTypeName(info.getType()));
			}
		}
		return info;
	}
}