package com.examw.netplatform.service.admin.students;
/**
 * 订单状态枚举
 * @author fengwei.
 * @since 2015年2月2日 下午1:52:29.
 */
public enum OrderStatus {
	/**
	 * 开通
	 */
	OPENED(1),
	/**
	 * 正常
	 */
	ENABLED(0),
	/**
	 * 取消
	 */
	DISABLE(-1);
	
	private int value;
	/**
	 * 构造函数。
	 * @param value
	 * 枚举值。
	 */
	private OrderStatus(int value){
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
	public static OrderStatus conversion(int value){
		 for(OrderStatus mode : OrderStatus.values()){
			 if(mode.getValue() == value) return mode;
		 }
		 throw new RuntimeException(String.format("%d － 未知类型", value));
	}
}
