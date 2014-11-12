package com.examw.netplatform.service.admin.courses;
/**
 * 讲义模式枚举。
 * 
 * @author yangyong
 * @since 2014年11月12日
 */
public enum HandoutMode {
	/**
	 *  无。
	 */
	None(0),
	/**
	 * 下载。
	 */
	Download(1),
	/**
	 * 在线。
	 */
	Online(2);
	private int value;
	/**
	 * 构造函数。
	 * @param value
	 * 枚举值。
	 */
	private HandoutMode(int value){
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
	public static HandoutMode conversion(int value){
		 for(HandoutMode mode : HandoutMode.values()){
			 if(mode.getValue() == value) return mode;
		 }
		 throw new RuntimeException(String.format("%d － 未知类型", value));
	}
}