package com.examw.netplatform.service.admin.students;
/**
 * 订单类型枚举。
 * 
 * @author yangyong
 * @since 2014年12月1日
 */
public enum OrderType {
	/**
	 * 班级。
	 */
	CLASS(1),
	/**
	 * 套餐。
	 */
	PACKAGE(2);
	private int value;
	/**
	 * 构造函数。
	 * @param value
	 * 枚举值。
	 */
	private OrderType(int value){
		this.value = value;
	}
	/**
	 * 获取枚举值。
	 * @return value
	 */
	public int getValue() {
		return value;
	}
	/**
	 * 类型转换。
	 * @param value
	 * 枚举值。
	 * @return
	 * 枚举对象。
	 */
	public static OrderType conversion(int value){
		 for(OrderType mode : OrderType.values()){
			 if(mode.getValue() == value) return mode;
		 }
		 throw new RuntimeException(String.format("%d － 未知类型", value));
	}
}