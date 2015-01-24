package com.examw.netplatform.service.admin.students;
/**
 * 
 * @author fengwei.
 * @since 2015年1月23日 上午11:23:26.
 */
public enum LearningStatus {
	/**
	 * 未学
	 */
	NONE(0),
	/**
	 * 在学。
	 */
	LEARNING(1),
	/**
	 * 学完。
	 */
	LEARNED(2);
	private int value;
	/**
	 * 构造函数。
	 * @param value
	 * 枚举值。
	 */
	private LearningStatus(int value){
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
	public static LearningStatus conversion(int value){
		 for(LearningStatus mode : LearningStatus.values()){
			 if(mode.getValue() == value) return mode;
		 }
		 throw new RuntimeException(String.format("%d － 未知类型", value));
	}
}
