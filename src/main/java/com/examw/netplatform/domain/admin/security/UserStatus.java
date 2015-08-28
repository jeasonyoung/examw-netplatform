package com.examw.netplatform.domain.admin.security;
/**
 * 用户状态枚举。
 * 
 * @author jeasonyoung
 * @since 2015年8月18日
 */
public enum UserStatus {
	/**
	 * 已删除。
	 */
	DELETE(-1),
	/**
	 * 停用。
	 */
	DISABLE(0),
	/**
	 * 启用。
	 */
	ENABLED(1);
	private int value;
	/**
	 * 构造函数。
	 * @param value
	 */
	private UserStatus(int value){
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
	 * @return
	 */
	public static UserStatus parse(int value){
		for(UserStatus s : UserStatus.values()){
			if(s.getValue() == value) return s;
		}
		return UserStatus.DISABLE;
	}
}