package com.examw.netplatform.support;

import java.util.Map;

import org.springframework.ui.Model;

import com.examw.netplatform.service.admin.teachers.IItemService;
import com.examw.netplatform.service.admin.teachers.ItemJudgeAnswer;
import com.examw.netplatform.service.admin.teachers.ItemType;

/**
 * 试卷/试题工具类。
 * 
 * @author yangyong
 * @since 2014年9月24日
 */
public final class ItemUtils {
	/**
	 * 加载试题题型值和名称集合。
	 * @param itemService
	 * @param ignores
	 * @return
	 */
	public static Map<String, String> loadItemTypeValueMap(IItemService itemService){
		if(itemService == null) return null;
		Map<String, String> itemTypeMap = EnumMapUtils.createTreeMap();
		for(ItemType type : ItemType.values()){
			if(type == null) continue;
			itemTypeMap.put(String.format("%d", type.getValue()), itemService.loadTypeName(type.getValue()));
		}
		return itemTypeMap;
	}
	/**
	 * 加载试题题型值和名称集合。
	 * @param itemService
	 * @param types
	 * @return
	 */
	public static Map<String, String> loadItemTypeValueMap(IItemService itemService, ItemType[] types){
		if(itemService == null || types == null || types.length == 0) return null;
		Map<String, String> itemTypeMap = EnumMapUtils.createTreeMap();
		for(ItemType type : types){
			if(type == null) continue;
			itemTypeMap.put(String.format("%d", type.getValue()), itemService.loadTypeName(type.getValue()));
		}
		return itemTypeMap;
	}
	/**
	 * 添加题型数据。
	 * @param itemService
	 * @param model
	 */
	public static void addAllItemType(IItemService itemService,Model model){
		if(itemService == null || model == null)return;
		model.addAttribute("ItemTypeMaps", loadItemTypeValueMap(itemService));
	}
	/**
	 * 添加普通题型。
	 * @param itemService
	 * @param model
	 */
	public static void addNormalItemType(IItemService itemService,Model model){
		if(itemService == null || model == null) return;
		model.addAttribute("ItemTypeMaps", loadItemTypeValueMap(itemService, new ItemType[]{ ItemType.SINGLE, 
																																						  ItemType.MULTY,
																																						  ItemType.UNCERTAIN,
																																						  ItemType.JUDGE,
																																						  ItemType.QANDA}));
	}
	/**
	 * 添加选择题型。
	 * @param itemService
	 * @param model
	 */
	public static void addChoiceItemType(IItemService itemService,Model model){
		if(itemService == null || model == null) return;
		model.addAttribute("ItemTypeMaps", loadItemTypeValueMap(itemService, new ItemType[]{ ItemType.SINGLE, 
																																						  ItemType.MULTY,
																																						  ItemType.UNCERTAIN}));
	}
	/**
	 * 加载判断题答案值和名称集合。
	 * @param itemService
	 * @return
	 */
	public static Map<String, String> loadItemJudgeAnswerValueMap(IItemService itemService){
		if(itemService == null) return null;
		Map<String, String> answers = EnumMapUtils.createTreeMap();
		for(ItemJudgeAnswer answer : ItemJudgeAnswer.values()){
			if(answer == null) continue;
			answers.put(String.format("%d", answer.getValue()), itemService.loadJudgeAnswerName(answer.getValue()));
		}
		return answers;
	}
	/**
	 * 添加判断题答案值和名称集合。
	 * @param itemService
	 * @param model
	 */
	public static void addItemJudgeAnswers(IItemService itemService,Model model){
		if(itemService == null || model == null) return;
		model.addAttribute("ItemJudgeAnswers", loadItemJudgeAnswerValueMap(itemService));
	}
}