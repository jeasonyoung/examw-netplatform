package com.examw.netplatform.service.admin.settings;

/**
 * 机构用户身份枚举。
 * 
 * @author yangyong
 * @since 2014年11月10日
 */
public enum AgencyUserIdentity {
	/**
	 * 机构管理员。
	 */
	ADMIN(0x01),
	/**
	 * 机构教师。
	 */
	TEACHER(0x02),
	/**
	 * 机构学员。
	 */
	STUDENT(0x04),
	/**
	 * 学习卡学员。
	 */
	CARDSTUDENT(0x08);
	private int value;
	/**
	 * 构造函数。
	 * @param value
	 */
	private AgencyUserIdentity(int value){
		this.value = value;
	}
	/**
	 * 获取验证码类型值。
	 * @return 验证码类型值。
	 */
	public int getValue(){
		return this.value;
	}
	/**
	 * 将数字转换为美举值。
	 * @param type
	 * @return
	 */
	public static AgencyUserIdentity conversion(Integer identity){
		if(identity == null) throw new RuntimeException("身份类型值为空！");
		for(AgencyUserIdentity agencyUserIdentity : AgencyUserIdentity.values()){
			if(agencyUserIdentity.getValue() == identity) return agencyUserIdentity;
		}
		throw new RuntimeException(String.format("不能转换为机构用户身份枚举对象［%d］！", identity));
	}
}