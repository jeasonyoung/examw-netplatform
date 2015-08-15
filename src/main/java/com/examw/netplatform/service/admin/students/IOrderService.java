package com.examw.netplatform.service.admin.students;

import com.examw.model.DataGrid;
import com.examw.netplatform.model.admin.students.OrderInfo;

/**
 * 订单服务接口。
 * 
 * @author yangyong
 * @since 2014年12月1日
 */
public interface IOrderService {
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
	/**
	 * 查询数据。
	 * @param info
	 * @return
	 */
	DataGrid<OrderInfo> datagrid(OrderInfo info);
	/**
	 * 更新数据。
	 * @param info
	 * @return
	 */
	OrderInfo update(OrderInfo info);
	/**
	 * 删除数据。
	 * @param ids
	 */
	void delete(String[] ids);
}