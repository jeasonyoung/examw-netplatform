package com.examw.netplatform.service.admin.courses;
/**
 * 视频模式枚举。
 * 
 * @author yangyong
 * @since 2014年11月12日
 */
public enum VideoMode {
	/**
	 * 免费。
	 */
	Free(0),
	/**
	 * 收费。
	 */
	Charge(1);
	private int value;
	/**
	 * 构造函数。
	 * @param value
	 * 枚举值。
	 */
	private VideoMode(int value){
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
	public static VideoMode conversion(int value){
		 for(VideoMode mode : VideoMode.values()){
			 if(mode.getValue() == value) return mode;
		 }
		 throw new RuntimeException(String.format("%d － 未知类型", value));
	}
}