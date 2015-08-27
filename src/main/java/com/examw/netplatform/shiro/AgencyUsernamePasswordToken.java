package com.examw.netplatform.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;
/**
 * 机构用户密码令牌。
 * 
 * @author jeasonyoung
 * @since 2015年8月27日
 */
public class AgencyUsernamePasswordToken extends UsernamePasswordToken {
	private static final long serialVersionUID = 1L;
	private String agencyId;
	/**
	 * 获取所属培训机构ID。
	 * @return 所属培训机构ID。
	 */
	public String getAgencyId() {
		return agencyId;
	}
	/**
	 * 构造函数。
	 * @param agencyId
	 * 所属培训机构ID。
	 * @param username
	 * 用户名。
	 * @param password
	 * 密码。
	 */
	public AgencyUsernamePasswordToken(String agencyId, String username, String password){
		super(username, password);
		this.agencyId = agencyId;
	}
}