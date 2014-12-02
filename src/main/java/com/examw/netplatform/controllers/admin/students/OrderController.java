package com.examw.netplatform.controllers.admin.students;

import java.util.Arrays;
import java.util.Map;

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
import com.examw.netplatform.model.admin.students.OrderInfo;
import com.examw.netplatform.service.admin.settings.IAgencyUserService;
import com.examw.netplatform.service.admin.students.IOrderService;
import com.examw.netplatform.service.admin.students.OrderSource;
import com.examw.netplatform.support.EnumMapUtils;
import com.examw.service.Status;
/**
 * 订单控制器。
 * 
 * @author yangyong
 * @since 2014年12月2日
 */
@Controller
@RequestMapping(value = "/admin/students/order")
public class OrderController implements IUserAware {
	private static final Logger logger = Logger.getLogger(OrderController.class);
	private String current_user_id,current_user_name;
	//注入机构用户服务接口。
	@Resource
	private IAgencyUserService agencyUserService;
	//注入订单服务接口。
	@Resource
	private IOrderService orderService;
	/*
	 * 设置当前用户ID。
	 * @see com.examw.aware.IUserAware#setUserId(java.lang.String)
	 */
	@Override
	public void setUserId(String userId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("设置当前用户ID:%s ...", userId));
		this.current_user_id = userId;
	}
	/*
	 * 设置当前用户名称。
	 * @see com.examw.aware.IUserAware#setUserName(java.lang.String)
	 */
	@Override
	public void setUserName(String userName){
		if(logger.isDebugEnabled()) logger.debug(String.format("设置当前用户名称:%s ...", userName));
		this.current_user_name = userName;
	}
	/*
	 * 设置当前用户昵称。
	 * @see com.examw.aware.IUserAware#setUserNickName(java.lang.String)
	 */
	@Override
	public void setUserNickName(String userNickName){}
	/**
	 * 加载列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.STUDENTS_ORDER + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		
		model.addAttribute("PER_UPDATE", ModuleConstant.STUDENTS_ORDER + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.STUDENTS_ORDER + ":" + Right.DELETE);
		
		String current_agency_id = this.agencyUserService.loadAgencyIdByUser(this.current_user_id);
	    if(StringUtils.isEmpty(current_agency_id)){
	    	return "error/user_not_agency";
	    }
	    model.addAttribute("current_agency_id", current_agency_id);//当前机构ID
		
		return "students/student_order_list";
	}
	/**
	 * 加载列表数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.STUDENTS_ORDER + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid/{agencyId}", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<OrderInfo> datagrid(@PathVariable String agencyId, OrderInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载列表数据...");
		info.setAgencyId(agencyId);
		return this.orderService.datagrid(info);
	}
	/**
	 * 加载编辑页面
	 * @param agencyId
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.STUDENTS_ORDER + ":" + Right.UPDATE})
	@RequestMapping(value = "/edit/{agencyId}", method = RequestMethod.GET)
	public String edit(@PathVariable String agencyId,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载编辑页面...");
		model.addAttribute("current_agency_id", agencyId);//当前机构ID
		
		Map<String, String> sourceMap = EnumMapUtils.createTreeMap(),statusMap = EnumMapUtils.createTreeMap();
		//来源
		for(OrderSource source : OrderSource.values()){
			sourceMap.put(String.format("%d", source.getValue()), this.orderService.loadSourceName(source.getValue()));
		}
		model.addAttribute("sourceMap", sourceMap);
		//状态
		for(Status status : Status.values()){
			statusMap.put(String.format("%d", status.getValue()), this.orderService.loadStatusName(status.getValue()));
		}
		model.addAttribute("statusMap", statusMap);
		
		return "students/student_order_edit";
	}
	/**
	 * 创建机构订单号码
	 * @param agencyId
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.STUDENTS_ORDER + ":" + Right.VIEW})
	@RequestMapping(value = "/number/{agencyId}", method = RequestMethod.GET)
	@ResponseBody
	public String[] createOrderNumber(@PathVariable String agencyId){
		if(logger.isDebugEnabled()) logger.debug(String.format("创建机构［%s］订单号码...", agencyId));
		return new String[]{ this.orderService.createOrderNumber(agencyId)};
	}
	/**
	 * 更新数据。
	 * @param agencyId
	 * 机构ID。
	 * @param info
	 * 订单信息。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.STUDENTS_ORDER + ":" + Right.UPDATE})
	@RequestMapping(value="/update/{agencyId}", method = RequestMethod.POST)
	@ResponseBody
	public Json update(@PathVariable String agencyId,OrderInfo info){
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		Json result = new Json();
		try {
			info.setAgencyId(agencyId);
			info.setUserId(this.current_user_id);
			info.setUserName(this.current_user_name);
			result.setData(this.orderService.update(info));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新数据发生异常", e);
		}
		return result;
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.STUDENTS_ORDER + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(@RequestBody String[] ids){
		if(logger.isDebugEnabled()) logger.debug(String.format("删除数据...",Arrays.toString(ids)));
		Json result = new Json();
		try {
			this.orderService.delete(ids);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("删除数据时发生异常:%s", e.getMessage()), e);
		}
		return result;
	}
}