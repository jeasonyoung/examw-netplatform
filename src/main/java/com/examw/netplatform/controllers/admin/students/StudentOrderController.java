package com.examw.netplatform.controllers.admin.students;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.aware.IUserAware;
import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.netplatform.domain.admin.security.Right;
import com.examw.netplatform.domain.admin.students.StudentOrder;
import com.examw.netplatform.domain.admin.students.StudentOrderDetail;
import com.examw.netplatform.model.admin.students.StudentOrderInfo;
import com.examw.netplatform.service.admin.students.IStudentOrderService;
/**
 * 机构学员订单控制器
 * @author fengwei.
 * @since 2014年5月26日 上午11:23:07.
 */
@Controller
@RequestMapping("/admin/students/order")
public class StudentOrderController implements IUserAware{
	private static final Logger logger = Logger.getLogger(StudentOrderController.class);
	private String current_user_id;
	//机构学员订单服务接口。
	//@Resource
	private IStudentOrderService studentOrderService;
	/*
	 * 设置当前用户ID。
	 * @see com.examw.aware.IUserAware#setUserId(java.lang.String)
	 */
	@Override
	public void setUserId(String userId) {
		this.current_user_id = userId;
	}
	/*
	 * 设置当前用户名称。
	 * @see com.examw.aware.IUserAware#setUserName(java.lang.String)
	 */
	@Override
	public void setUserName(String userName) {}
	/*
	 * 设置当前用户昵称。
	 * @see com.examw.aware.IUserAware#setUserNickName(java.lang.String)
	 */
	@Override
	public void setUserNickName(String userNickName) {}
	/**
	 * 列表页面。
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.STUDENTS_COURSE + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.STUDENTS_COURSE + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.STUDENTS_COURSE + ":" + Right.DELETE);
		
		model.addAttribute("TYPE_PACKAGE_VALUE", StudentOrderDetail.TYPE_PACKAGE);
		model.addAttribute("TYPE_PACKAGE_NAME", this.studentOrderService.loadDetailTypeName(StudentOrderDetail.TYPE_PACKAGE));
		
		model.addAttribute("TYPE_CLASS_VALUE", StudentOrderDetail.TYPE_CLASS);
		model.addAttribute("TYPE_CLASS_NAME", this.studentOrderService.loadDetailTypeName(StudentOrderDetail.TYPE_CLASS));
		return "students/student_order_list";
	}
	/**
	 * 编辑页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.STUDENTS_COURSE + ":" + Right.UPDATE})
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载编辑页面...");
		model.addAttribute("CURRENT_STATUS", StudentOrder.STATUS_APPOINT);
		
		model.addAttribute("TYPE_PACKAGE_VALUE", StudentOrderDetail.TYPE_PACKAGE);
		model.addAttribute("TYPE_CLASS_VALUE", StudentOrderDetail.TYPE_CLASS);
		
		model.addAttribute("CURRENT_NEW_ORDER_NO", new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
		
		return "students/student_order_edit";
	}
	/**
	 * 添加订单明细。
	 * @param type
	 * 明细类型。
	 * @param agencyId
	 * 机构ID。
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.STUDENTS_COURSE + ":" + Right.UPDATE})
	@RequestMapping(value = "/edit/{type}/{agencyId}", method = RequestMethod.GET)
	public String addOrderDetail(@PathVariable Integer type,@PathVariable String agencyId, Model model){
		model.addAttribute("CURRENT_AGENCY_ID", agencyId);
		model.addAttribute("CURRENT_TYPE_VALUE",type);
		if(type == StudentOrderDetail.TYPE_PACKAGE) 
			return "students/student_order_addpackage";
		return "students/student_order_addclasses";
	}
	/**
	 * 查询数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.STUDENTS_COURSE + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<StudentOrderInfo> datagrid(StudentOrderInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载列表数据...");
		info.setCurrentUserId(this.current_user_id);
		return this.studentOrderService.datagrid(info);
	}
	/**
	 * 更新数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.STUDENTS_COURSE + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(StudentOrderInfo info){
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		Json result = new Json();
		try{
			 result.setData(this.studentOrderService.update(info));
			 result.setSuccess(true);
		}catch (Exception e) {
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
	@RequiresPermissions({ModuleConstant.STUDENTS_COURSE + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(String id){
		if(logger.isDebugEnabled()) logger.debug("删除数据...");
		Json result = new Json();
		try {
			this.studentOrderService.delete(id.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除数据["+id+"]时发生异常:", e);
		}
		return result;
	} 
}