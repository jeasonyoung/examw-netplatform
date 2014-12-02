package com.examw.netplatform.service.admin.students;

import com.examw.netplatform.model.admin.students.OrderInfo;
import com.examw.netplatform.service.IBaseDataService;

/**
 * 订单服务接口。
 * 
 * @author yangyong
 * @since 2014年12月1日
 */
public interface IOrderService extends IBaseDataService<OrderInfo> {
	/**
	 * 加载来源名称。
	 * @param source
	 * 来源值。
	 * @return
	 * 来源名称。
	 */
	String loadSourceName(Integer source);
	/**
	 * 加载状态名称。
	 * @param status
	 * 状态值。
	 * @return
	 * 状态名称。
	 */
	String loadStatusName(Integer status);
	/**
	 * 创建订单号码。
	 * @param agencyId
	 * 机构ID。
	 * @return
	 * 订单号码。
	 */
	String createOrderNumber(String agencyId);
}