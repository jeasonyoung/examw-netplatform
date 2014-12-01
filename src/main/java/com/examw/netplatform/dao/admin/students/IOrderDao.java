package com.examw.netplatform.dao.admin.students;

import java.util.List;

import com.examw.netplatform.dao.IBaseDao;
import com.examw.netplatform.domain.admin.students.Order;
import com.examw.netplatform.model.admin.students.OrderInfo;
/**
 * 学生订单数据接口
 * @author fengwei.
 * @since 2014年5月26日 上午8:17:05.
 */
public interface IOrderDao extends IBaseDao<Order>{
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<Order> findOrders(OrderInfo info);
	/**
	 * 查询数据总数。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据总数。
	 */
	Long total(OrderInfo info);
}