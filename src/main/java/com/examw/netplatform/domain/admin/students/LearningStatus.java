package com.examw.netplatform.domain.admin.students;
/**
 * 进度状态枚举。
 * 
 * @author jeasonyoung
 * @since 2015年8月26日
 */
public enum LearningStatus {
	/**
	 * 未学完。
	 */
	None(0),
	/**
	 * 已学完。
	 */
	Finish(1);
	private int value;
	/**
	 * 构造函数。
	 * @param value
	 */
	private LearningStatus(int value){
		this.value = value;
	}
	/**
	 * 获取进度状态枚举值。
	 * @return value
	 */
	public int getValue() {
		return value;
	}
	/**
	 * 枚举类型转换。
	 * @param value
	 * @return
	 */
	public static LearningStatus parse(int value){
		for(LearningStatus s : LearningStatus.values()){
			if(s.getValue() == value) return s;
		}
		return None;
	}
}