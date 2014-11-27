package com.examw.netplatform.controllers.admin.teachers;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.aware.IUserAware;
import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.netplatform.domain.admin.security.Right;
import com.examw.netplatform.model.admin.teachers.ItemInfo;
import com.examw.netplatform.model.admin.teachers.PracticeInfo;
import com.examw.netplatform.model.admin.teachers.StructureInfo;
import com.examw.netplatform.service.admin.settings.IAgencyUserService;
import com.examw.netplatform.service.admin.teachers.IItemService;
import com.examw.netplatform.service.admin.teachers.IPracticeService;
import com.examw.netplatform.service.admin.teachers.IStructureService;
import com.examw.netplatform.service.admin.teachers.ItemType;
import com.examw.netplatform.support.EnumMapUtils;
import com.examw.netplatform.support.ItemUtils;
/**
 * 随堂练习控制器。
 * 
 * @author yangyong
 * @since 2014年11月25日
 */
@Controller
@RequestMapping("/admin/teachers/practice")
public class PracticeController implements IUserAware {
	private static final Logger logger = Logger.getLogger(PracticeController.class);
	private String current_user_id,current_user_name;
	//注入机构用户服务接口。
	@Resource
	private IAgencyUserService agencyUserService;
	//注入随堂练习服务接口。
	@Resource
	private IPracticeService practiceService;
	//注入随堂练习结构服务接口。
	@Resource
	private IStructureService structureService;
	//注入试题服务接口。
	@Resource
	private IItemService itemService;
	/*
	 * 设置当前用户ID。
	 * @see com.examw.aware.IUserAware#setUserId(java.lang.String)
	 */
	@Override
	public void setUserId(String userId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("注入当前用户ID：%s", userId));
		this.current_user_id = userId;
	}
	/*
	 * 设置当前用户名称。
	 * @see com.examw.aware.IUserAware#setUserName(java.lang.String)
	 */
	@Override
	public void setUserName(String userName) {
		if(logger.isDebugEnabled()) logger.debug(String.format("注入当前用户名称：%s", userName));
		this.current_user_name = userName;
	}
	/*
	 * 设置当前用户昵称。
	 * @see com.examw.aware.IUserAware#setUserNickName(java.lang.String)
	 */
	@Override
	public void setUserNickName(String userNickName) {}
	/**
	 * 加载列表页面。
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHERS_PRACTICE + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		
		model.addAttribute("PER_UPDATE", ModuleConstant.TEACHERS_PRACTICE + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.TEACHERS_PRACTICE + ":" + Right.DELETE);
		
		String current_agency_id = this.agencyUserService.loadAgencyIdByUser(this.current_user_id);
	    if(StringUtils.isEmpty(current_agency_id)){
	    	return "error/user_not_agency";
	    }
	    model.addAttribute("current_agency_id", current_agency_id);//当前机构ID
	    
		return "teachers/practice_list";
	}
	/**
	 * 加载列表数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHERS_PRACTICE + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<PracticeInfo> datagrid(PracticeInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载列表数据...");
		return this.practiceService.datagrid(info);
	}
	/**
	 *  加载编辑页面。
	 * @param agencyId
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHERS_PRACTICE + ":" + Right.VIEW})
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public String edit(String agencyId,String classId, Model model){
		if(logger.isDebugEnabled()) logger.debug("加载编辑页面...");
		
		model.addAttribute("PER_UPDATE", ModuleConstant.TEACHERS_PRACTICE + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.TEACHERS_PRACTICE + ":" + Right.DELETE);
		
		model.addAttribute("current_agency_id", agencyId);//当前机构ID
		model.addAttribute("current_class_id", classId);//当前班级ID
		
		return "teachers/practice_edit";
	}
	/**
	 * 更新数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHERS_PRACTICE + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(PracticeInfo info){
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		Json result = new Json();
		try {
			info.setUserId(this.current_user_id);
			info.setUserName(this.current_user_name);
			result.setData(this.practiceService.update(info));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("更新数据时发生异常:%s", e.getMessage()), e);
		}
		return result;
	}
	/**
	 * 更新反转随堂练习状态。
	 * @param practiceId
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHERS_PRACTICE + ":" + Right.UPDATE})
	@RequestMapping(value="/reverse/status", method = RequestMethod.POST)
	@ResponseBody
	public Json reverseStatus(String practiceId){
		if(logger.isDebugEnabled()) logger.debug("更新随堂练习状态数据...");
		Json result = new Json();
		try {
			this.practiceService.updateReverseStatus(practiceId);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("更新随堂练习状态数据时发生异常:%s", e.getMessage()), e);
		}
		return result;
	}
	/**
	 * 删除数据。
	 * @param ids
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHERS_PRACTICE + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(@RequestBody String[] ids){
		if(logger.isDebugEnabled()) logger.debug(String.format("删除数据:%s ...", Arrays.toString(ids)));
		Json result = new Json();
		try {
			this.practiceService.delete(ids);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("删除数据时发生异常:%s", e.getMessage()), e);
		}
		return result;
	}
	/**
	 * 加载结构列表页面。
	 * @param agencyId
	 * @param practiceId
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHERS_PRACTICE + ":" + Right.VIEW})
	@RequestMapping(value="/structure/list", method = RequestMethod.GET)
	public String structureEdit(String agencyId,String practiceId,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载随堂练习结构列表页面...");
		
		model.addAttribute("PER_UPDATE", ModuleConstant.TEACHERS_PRACTICE + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.TEACHERS_PRACTICE + ":" + Right.DELETE);
		
		model.addAttribute("current_agency_id", agencyId);//当前机构ID
		model.addAttribute("current_practice_id", practiceId);//当前随堂练习ID
		
		return "teachers/practice_structure_list";
	}
	/**
	 * 加载结构编辑页面。
	 * @param practiceId
	 * 随堂练习。
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHERS_PRACTICE + ":" + Right.VIEW})
	@RequestMapping(value="/structure/edit", method = RequestMethod.GET)
	public String structureEdit(String practiceId,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载结构编辑页面...");
		model.addAttribute("current_practice_id", practiceId);//当前随堂练习ID
		Map<String, String> itemTypeMap = EnumMapUtils.createTreeMap();
		for(ItemType itemType : ItemType.values()){
			itemTypeMap.put(String.format("%d", itemType.getValue()), this.itemService.loadTypeName(itemType.getValue()));
		}
		model.addAttribute("itemTypeMap", itemTypeMap);
		return "teachers/practice_structure_edit";
	}
	/**
	 * 加载随堂练习结构数据。
	 * @param practiceId
	 * 随堂练习ID。
	 * @return
	 * 结构数据。
	 */
	@RequiresPermissions({ModuleConstant.TEACHERS_PRACTICE + ":" + Right.VIEW})
	@RequestMapping(value="/structure/{practiceId}", method = RequestMethod.POST)
	@ResponseBody
	public List<StructureInfo> loadPracticeStructures(@PathVariable String practiceId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载随堂练习［%s］结构数据...", practiceId));
		return this.structureService.loadPracticeStructures(practiceId);
	}
	/**
	 * 加载随堂练习结构排序号。
	 * @param practiceId
	 * 随堂练习ID。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHERS_PRACTICE + ":" + Right.VIEW})
	@RequestMapping(value="/structure/order/{practiceId}", method = RequestMethod.GET)
	@ResponseBody
	public Integer loadStructureMaxOrder(@PathVariable String practiceId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载随堂练习［%s］结构最大排序号...", practiceId));
		Integer order = this.structureService.loadMaxOrder(practiceId);
		if(order == null) order = 0;
		return order + 1;
	}
	/**
	 * 更新随堂练习结构数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHERS_PRACTICE + ":" + Right.UPDATE})
	@RequestMapping(value="/structure/update/{practiceId}", method = RequestMethod.POST)
	@ResponseBody
	public Json updateStructure(@PathVariable String practiceId,StructureInfo info){
		if(logger.isDebugEnabled()) logger.debug(String.format("更新随堂练习［%s］结构数据...", practiceId));
		Json result = new Json();
		try {
			info.setPracticeId(practiceId);
			this.structureService.update(info);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("更新数据时发生异常:%s", e.getMessage()), e);
		}
		return result;
	}
	/**
	 * 删除随堂练习结构数据。
	 * @param ids
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHERS_PRACTICE + ":" + Right.DELETE})
	@RequestMapping(value="/structure/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json deleteStructure(@RequestBody String[] ids){
		if(logger.isDebugEnabled()) logger.debug(String.format("删除随堂练习结构数据：%s...", Arrays.toString(ids)));
		Json result = new Json();
		try {
			this.structureService.delete(ids);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("删除随堂练习结构数据时发生异常:%s", e.getMessage()), e);
		}
		return result;
	}
	/**
	 * 加载试题数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHERS_PRACTICE + ":" + Right.VIEW})
	@RequestMapping(value="/item/datagrid/{practiceId}", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<ItemInfo> items(@PathVariable String practiceId, ItemInfo info){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载随堂练习［%s］试题数据...", practiceId));
		info.setPracticeId(practiceId);
		return this.itemService.datagrid(info);
	}
	/**
	 * 加载试题排序号。
	 * @param structureId
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHERS_PRACTICE + ":" + Right.VIEW})
	@RequestMapping(value="/item/order/{structureId}", method = RequestMethod.GET)
	@ResponseBody
	public Integer loadMaxOrder(@PathVariable String structureId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载随堂练习结构［%s］下试题排序号...", structureId));
		Integer order = this.itemService.loadMaxOrder(structureId);
		if(order == null) order = 0;
		return order + 1;
	}
	/**
	 * 加载试题界面。
	 * @param type
	 * 题型值。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHERS_PRACTICE + ":" + Right.VIEW})
	@RequestMapping(value="/item/edit/{type}", method = RequestMethod.GET)
	public String itemEdit(@PathVariable Integer type,Boolean child,Model model){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载试题［题型：%d］界面..", type));
		
		model.addAttribute("PER_UPDATE", ModuleConstant.TEACHERS_PRACTICE + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.TEACHERS_PRACTICE + ":" + Right.DELETE);
		
		model.addAttribute("current_item_child", (child == null) ? false : child);
		
		//获取当前题型。
		ItemType itemType = ItemType.convert(type);
		model.addAttribute("current_item_type_value", itemType.getValue());
		model.addAttribute("current_item_type_name", this.itemService.loadTypeName(itemType.getValue()));
		
		if(itemType == ItemType.SHARE_TITLE){
			ItemUtils.addNormalItemType(this.itemService, model);//添加普通题型。
		}else if(itemType == ItemType.SHARE_ANSWER){
			ItemUtils.addChoiceItemType(this.itemService, model);//添加选择题型。
		}else if(itemType == ItemType.JUDGE){
			ItemUtils.addItemJudgeAnswers(this.itemService, model);//添加判断题型答案。
		}
		
		return String.format("teachers/practice_item_edit_%d", itemType.getValue());
	}
	/**
	 * 加载试题选项页面。
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHERS_PRACTICE + ":" + Right.VIEW})
	@RequestMapping(value="/item/edit/option/{type}", method = RequestMethod.GET)
	public String itemOption(@PathVariable String type, Model model){
		if(logger.isDebugEnabled()) logger.debug("加载试题选项页面...");
		model.addAttribute("current_item_type_value", type);
		model.addAttribute("current_id", UUID.randomUUID().toString());
		return "teachers/practice_item_option";
	}
	/**
	 * 加载试题内容。
	 * @param itemId
	 * 试题ID。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHERS_PRACTICE + ":" + Right.VIEW})
	@RequestMapping(value="/item/{itemId}", method = RequestMethod.GET)
	@ResponseBody
	public Json loadItem(@PathVariable String itemId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载试题内容：%s ...", itemId));
		Json result = new Json();
		try{
			result.setData(this.itemService.loadItem(itemId));
			result.setSuccess(true);
		}catch(Exception e){
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("加载试题内容时发生异常：%s", e.getMessage()), e);
		}
		return result;
	}
	/**
	 * 更新试题数据。
	 * @param structureId
	 * 结构ID。
	 * @param info
	 * 试题内容。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHERS_PRACTICE + ":" + Right.UPDATE})
	@RequestMapping(value="/item/update/{structureId}", method = RequestMethod.POST)
	@ResponseBody
	public Json updateItem(@PathVariable String structureId,@RequestBody ItemInfo info){
		if(logger.isDebugEnabled()) logger.debug(String.format("更新结构［%s］试题数据...", structureId));
		Json result = new Json();
		try{
			info.setStructureId(structureId);
			result.setData(this.itemService.update(info));
			result.setSuccess(true);
		}catch(Exception e){
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("更新试题内容时发生异常：%s", e.getMessage()), e);
		}
		return result;
	}
	/**
	 * 删除试题数据。
	 * @param ids
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHERS_PRACTICE + ":" + Right.DELETE})
	@RequestMapping(value="/item/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json deleteItem(@RequestBody String[] ids){
		if(logger.isDebugEnabled()) logger.debug(String.format("删除试题数据：［%s］...", Arrays.toString(ids)));
		Json result = new Json();
		try{ 
			 this.itemService.delete(ids);
			result.setSuccess(true);
		}catch(Exception e){
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("更新试题内容时发生异常：%s", e.getMessage()), e);
		}
		return result;
	}
}