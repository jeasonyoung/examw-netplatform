package com.examw.netplatform.controllers.admin.students;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.netplatform.domain.admin.security.Right;
import com.examw.netplatform.domain.admin.security.UserIdentity;
import com.examw.netplatform.domain.admin.security.UserType;
import com.examw.netplatform.model.EnumValueName;
import com.examw.netplatform.model.admin.courses.ClassPlanInfo;
import com.examw.netplatform.model.admin.courses.PackageInfo;
import com.examw.netplatform.model.admin.security.UserInfo;
import com.examw.netplatform.model.admin.students.OrderInfo;
import com.examw.netplatform.service.admin.courses.IClassService;
import com.examw.netplatform.service.admin.courses.IPackageService;
import com.examw.netplatform.service.admin.security.IUserService;
import com.examw.netplatform.service.admin.students.IOrderService;
import com.examw.netplatform.service.admin.students.OrderSource;
import com.examw.netplatform.service.admin.students.OrderStatus;
import com.examw.netplatform.support.UserAware;

/**
 * 订单数据控制器。
 * 
 * @author jeasonyoung
 * @since 2015年8月25日
 */
@RestController
@RequestMapping(value = "/admin/students/order/data")
public class OrderDataController implements UserAware {
	private static final Logger logger = Logger.getLogger(OrderDataController.class);
	private String current_agency_id, current_user_id;
	private List<EnumValueName> statusList, sourcesList;
	//注入订单服务。
	@Resource
	private IOrderService orderService;
	//注入用户服务。
	@Resource
	private IUserService userService;
	//注入班级服务。
	@Resource
	private IClassService classService;
	//注入套餐服务。
	@Resource
	private IPackageService packageService;
	/*
	 * 设置当前机构ID。
	 * @see com.examw.netplatform.support.UserAware#setAgencyId(java.lang.String)
	 */
	@Override
	public void setAgencyId(String agencyId) {
		logger.debug("注入当前机构ID..." + agencyId);
		this.current_agency_id = agencyId;
	}
	/*
	 * 设置当前用户ID。
	 * @see com.examw.netplatform.support.UserAware#setUserId(java.lang.String)
	 */
	@Override
	public void setUserId(String userId) {
		logger.debug("注入当前用户ID..." + userId);
		this.current_user_id = userId;
	}
	/**
	 * 加载订单状态集合。
	 * @return
	 */
	@RequestMapping(value = "/orderstatus")
	public List<EnumValueName> getOrderStatus(){
		logger.debug("加载订单状态集合...");
		if(this.statusList == null || this.statusList.size() == 0){
			this.statusList = new ArrayList<EnumValueName>();
			for(OrderStatus s : OrderStatus.values()){
				this.statusList.add(new EnumValueName(s.getValue(), this.orderService.loadStatusName(s.getValue())));
			}
			Collections.sort(this.statusList);
		}
		return this.statusList;
	}
	/**
	 * 加载来源状态集合
	 * @return
	 */
	@RequestMapping(value = "/sources")
	public List<EnumValueName> getSources(){
		logger.debug("加载来源状态集合...");
		if(this.sourcesList == null || this.sourcesList.size() == 0){
			this.sourcesList = new ArrayList<EnumValueName>();
			for(OrderSource source : OrderSource.values()){
				this.sourcesList.add(new EnumValueName(source.getValue(), this.orderService.loadSourceName(source.getValue())));
			}
			Collections.sort(this.sourcesList);
		}
		return this.sourcesList;
	}
	/**
	 * 创建机构订单号码
	 * @param agencyId
	 * @return
	 */
	@RequestMapping(value = "/number") 
	public String[] createOrderNumber(){
		logger.debug("创建机构["+this.current_agency_id+"]订单号码...");
		return new String[]{ this.orderService.createOrderNumber(this.current_agency_id)};
	}
	/**
	 * 加载机构学员数据集合。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.STUDENTS_ORDER + ":" + Right.VIEW})
	@RequestMapping(value="/students", method = RequestMethod.POST)
	public DataGrid<UserInfo> loadAgencyStudents(UserInfo info){
		logger.debug("加载机构["+this.current_agency_id+"]学员数据集合...");
		//设置当前所属机构
		info.setAgencyId(this.current_agency_id);
		//设置用户类型
		info.setType(UserType.FRONT.getValue());
		//设置用户身份
		info.setIdentity(UserIdentity.STUDENT.getValue());
		//查询数据
		return this.userService.datagrid(info);
	}
	/**
	 * 加载订单下用户集合。
	 * @param orderId
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.STUDENTS_ORDER + ":" + Right.VIEW})
	@RequestMapping(value = "/orderstudents")
	public List<UserInfo> loadStudentsByOrder(String orderId){
		logger.debug("加载订单["+orderId+"]下用户集合...");
		return this.userService.findUsersByOrder(orderId);
	}
	/**
	 * 加载订单下的班级集合。
	 * @param orderId
	 * 订单ID。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.STUDENTS_ORDER + ":" + Right.VIEW})
	@RequestMapping(value = "/orderclasses")
	public List<ClassPlanInfo> loadClassByOrder(String orderId){
		logger.debug("加载订单["+orderId+"]下的班级集合...");
		return this.classService.loadClassesByOrder(orderId);
	}
	/**
	 * 加载订单下套餐集合。
	 * @param orderId
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.STUDENTS_ORDER + ":" + Right.VIEW})
	@RequestMapping(value = "/orderpackages")
	public List<PackageInfo> loadPackageByOrder(String orderId){
		logger.debug("加载订单["+orderId+"]下套餐集合...");
		return this.packageService.loadPackagesByOrder(orderId);
	}
	/**
	 * 加载列表数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.STUDENTS_ORDER + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	public DataGrid<OrderInfo> datagrid(OrderInfo info){
		logger.debug("加载列表数据...");
		info.setAgencyId(this.current_agency_id);
		return this.orderService.datagrid(info);
	}
	/**
	 * 更新数据。
	 * @param info
	 * 订单信息。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.STUDENTS_ORDER + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(OrderInfo info){
		logger.debug("更新数据...");
		Json result = new Json();
		try {
			if(StringUtils.isBlank(info.getAgencyId())){
				info.setAgencyId(this.current_agency_id);
			}
			info.setUserId(this.current_user_id);
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
	public Json delete(@RequestBody String[] ids){
		logger.debug(String.format("删除数据...",Arrays.toString(ids)));
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