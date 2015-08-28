package com.examw.netplatform.dao.admin.students;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.examw.netplatform.domain.admin.students.Order;
import com.examw.netplatform.domain.admin.students.UserOrdersView;
/**
 * 学生订单数据接口
 * @author fengwei.
 * @since 2014年5月26日 上午8:17:05.
 */
public interface OrderMapper{
	/**
	 * 获取订单。
	 * @param id
	 * @return
	 */
	Order getOrder(String id);
	/**
	 * 获取机构订单总数。
	 * @param agencyId
	 * @return
	 */
	int getOrderTotal(String agencyId);
	/**
	 * 根据订单号查询订单。
	 * @param number
	 * @return
	 */
	Order getOrderByNumber(String number);
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<Order> findOrders(Order info);
	/**
	 * 查询机构用户下订单集合。
	 * @param agencyId
	 * @param userId
	 * @return
	 */
	List<Order> findOrdersByAgency(@Param("agencyId")String agencyId, @Param("userId")String userId);
	/**
	 * 查询用户订单下套餐班级集合。
	 * @param userId
	 * 用户ID。
	 * @return
	 */
	List<UserOrdersView> findOrdersViewsByUser(String userId);
	/**
	 * 是否存在订单号。
	 * @param number
	 * @return
	 */
	boolean hasOrderNumber(String number);
	
	/**
	 * 新增订单。
	 * @param data
	 */
	void insertOrder(Order data);
	/**
	 * 更新订单。
	 * @param data
	 */
	void updateOrder(Order data);
	/**
	 * 删除订单。
	 * @param id
	 */
	void deleteOrder(String id);
	
	/**
	 * 是否存在订单学员。
	 * @param orderId
	 * @param studentId
	 * @return
	 */
	boolean hasOrderStudent(@Param("orderId")String orderId,@Param("studentId")String studentId);
	/**
	 * 插入订单学员。
	 * @param orderId
	 * @param studentId
	 */
	void insertOrderStudent(@Param("orderId")String orderId,@Param("studentId")String studentId);
	/**
	 * 删除订单学员。
	 * @param orderId
	 * @param studentId
	 */
	void deleteOrderStudent(@Param("orderId")String orderId,@Param("studentId")String studentId);
	/**
	 * 删除订单下的所有学员。
	 * @param orderId
	 * 订单ID。
	 */
	void deleteOrderAllStudents(String orderId);
	
	/**
	 * 是否存在订单班级。
	 * @param orderId
	 * @param classId
	 * @return
	 */
	boolean hasOrderClass(@Param("orderId")String orderId,@Param("classId")String classId);
	/**
	 * 插入订单班级。
	 * @param orderId
	 * @param classId
	 */
	void insertOrderClass(@Param("orderId")String orderId,@Param("classId")String classId);
	/**
	 * 删除订单班级。
	 * @param orderId
	 * @param classId
	 */
	void deleteOrderClass(@Param("orderId")String orderId,@Param("classId")String classId);
	/**
	 * 删除订单下班级集合。
	 * @param orderId
	 */
	void deleteOrderAllClasses(String orderId);
	
	/**
	 * 是否存在订单套餐。
	 * @param orderId
	 * @param packageId
	 * @return
	 */
	boolean hasOrderPackage(@Param("orderId")String orderId,@Param("packageId")String packageId);
	/**
	 * 插入订单套餐。
	 * @param orderId
	 * @param packageId
	 */
	void insertOrderPackage(@Param("orderId")String orderId,@Param("packageId")String packageId);
	/**
	 * 删除订单套餐。
	 * @param orderId
	 * @param packageId
	 */
	void deleteOrderPackage(@Param("orderId")String orderId,@Param("packageId")String packageId);
	/**
	 * 删除订单下套餐集合。
	 * @param orderId
	 */
	void deleteOrderAllPackages(String orderId);
}