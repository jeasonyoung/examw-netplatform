package com.examw.netplatform.controllers.admin.students;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.aware.IUserAware;
import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.netplatform.model.admin.students.StudentOrderInfo;
import com.examw.netplatform.service.admin.students.IStudentOrderService;

/**
 * 更换套餐控制器
 * @author fengwei.
 * @since 2014年5月31日 下午4:46:16.
 */
@Controller
@RequestMapping("/admin/student/orderchange")
public class OrderChangeController implements IUserAware{
	private static Logger logger = Logger.getLogger(StudentOrderController.class);
	//@Resource
	private IStudentOrderService orderService;
	//private String username;
	@Override
	public void setUserId(String userId) {
	}
	@Override
	public void setUserName(String userName) {
		//this.username = userName;
	}
	@Override
	public void setUserNickName(String userNickName) {
	}
	
	/**
	 * 获取列表页面。
	 * @return
	 */
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		//model.addAttribute("TYPE_AGENCY_APPOINT", this.orderService.getStatusName(StudentOrder.TYPE_AGENCY_APPOINT));
		//model.addAttribute("statusMap",this.orderService.getStatusMap());
		return "student/change_package_list";
	}
	/**
	 * 获取编辑页面。
	 * @param agencyId
	 * 所属培训机构。
	 * @param model
	 * 数据绑定。
	 * @return
	 * 编辑页面地址。
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(String agencyId,Model model){
		//model.addAttribute("TYPE_AGENCY_APPOINT", this.orderService.getStatusName(StudentOrder.TYPE_AGENCY_APPOINT));
		//model.addAttribute("statusMap",this.orderService.getStatusMap());
		return "student/order_edit";
	}
	/**
	 * 查询数据。
	 * @return
	 */
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<StudentOrderInfo> datagrid(StudentOrderInfo info){
		return this.orderService.datagrid(info);
	}
	/**
	 * 实际退课审核操作,改变状态。
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/check", method = RequestMethod.POST)
	@ResponseBody
	public Json checkCancel(StudentOrderInfo info){
		Json result = new Json();
		try {
//			info.setCreateUsername(username);
//			if(this.orderService.modifyCheckOrder(info) == null){
//				result.setSuccess(false);
//				result.setMsg("换课失败,是已购套餐或套餐不存在");
//			}else{
//				result.setSuccess(true);
//				result.setMsg("退换成功");
//			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除数据["+info.getId()+"]时发生异常:", e);
		}
		return result;
	}
	/**
	 * 退换申请
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/check/{orderId}", method = RequestMethod.GET)
	public String check(@PathVariable String orderId,Model model){
		//model.addAttribute("ORDER", this.orderService.findById(orderId));
		//model.addAttribute("STATUS_CHANGE", StudentOrder.STATUS_CHANGE_APPLY);
		return "student/change_package_edit";
	}
}
