package com.examw.netplatform.service.admin.students;

import com.examw.netplatform.model.admin.students.StudentOrderInfo;
import com.examw.netplatform.service.IBaseDataService;
/**
 * 机构学员订单服务接口。
 * @author fengwei.
 * @since 2014年5月26日 上午8:19:12.
 */
public interface IStudentOrderService extends IBaseDataService<StudentOrderInfo> {
	/**
	 * 加载订单状态名称。
	 * @param status
	 * 订单状态值。
	 * @return
	 * 订单状态名称。
	 */
	String loadOrderStatusName(Integer status);
	/**
	 * 加载订单类型名称。
	 * @param type
	 * 订单类型值。
	 * @return
	 * 订单类型名称。
	 */
	String loadDetailTypeName(Integer type);
}