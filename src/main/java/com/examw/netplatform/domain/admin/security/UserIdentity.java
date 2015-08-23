package com.examw.netplatform.domain.admin.security;
/**
 * 用户身份枚举。
 * 
 * @author jeasonyoung
 * @since 2015年8月18日
 */
public enum UserIdentity {
	/**
	 * 未知。
	 */
	NONE(0),
	/**
	 * 管理员。
	 */
	ADMIN(1),
	/**
	 * 教师。
	 */
	TEACHER(2),
	/**
	 * 学员。
	 */
	STUDENT(3);
	
	private int value;
	private UserIdentity(int value){
		this.value = value;
	}
	/**
	 * 获取枚举值。
	 * @return 枚举值。
	 */
	public int getValue() {
		return value;
	}
	/**
	 * 枚举值解析到枚举。
	 * @param value
	 * @return
	 */
	public static UserIdentity parse(int value){
		for(UserIdentity identity : UserIdentity.values()){
			if(identity.value == value){
				return identity;
			}
		}
		return NONE;
	}
}