package com.examw.netplatform.service.admin.students;
/**
 * 订单来源枚举。
 * 
 * @author yangyong
 * @since 2014年12月1日
 */
public enum OrderSource {
	/**
	 * 机构预订。
	 */
	AGENCY(1),
	/**
	 * 学员自选。
	 */
	CHOICE(2);
	private int value;
	/**
	 * 构造函数。
	 * @param value
	 * 枚举值。
	 */
	private OrderSource(int value){
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
	public static OrderSource conversion(int value){
		 for(OrderSource mode : OrderSource.values()){
			 if(mode.getValue() == value) return mode;
		 }
		 throw new RuntimeException(String.format("%d － 未知类型", value));
	}
}