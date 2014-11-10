package com.examw.netplatform.model.admin.settings;
 
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.examw.model.Paging; 
import com.examw.netplatform.model.admin.security.UserInfo;
import com.examw.support.CustomDateSerializer;

/**
 * 机构用户信息。
 * @author yangyong.
 * @since 2014-07-08.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class AgencyUserInfo extends Paging {
	private static final long serialVersionUID = 1L;
	private String id,identityName;
	private Integer identity;
	private AgencyInfo agency;
	private UserInfo user;
	private Date createTime,lastTime;
	/**
	 * 获取机构用户ID。
	 * @return 机构用户ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置机构用户ID。
	 * @param id 
	 *	 机构用户ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取机构用户身份。
	 * @return 机构用户身份。
	 */
	public Integer getIdentity() {
		return identity;
	}
	/**
	 * 设置机构用户身份。
	 * @param identity 
	 *	  机构用户身份。
	 */
	public void setIdentity(Integer identity) {
		this.identity = identity;
	}
	/**
	 * 获取机构用户身份名称。
	 * @return 机构用户身份名称。
	 */
	public String getIdentityName() {
		return identityName;
	}
	/**
	 * 设置机构用户身份名称。
	 * @param identityName 
	 *	  机构用户身份名称。
	 */
	public void setIdentityName(String identityName) {
		this.identityName = identityName;
	}
	/**
	 * 获取所属机构信息。
	 * @return 所属机构信息。
	 */
	public AgencyInfo getAgency() {
		return agency;
	}
	/**
	 * 设置所属机构信息。
	 * @param agency 
	 *	  所属机构信息。
	 */
	public void setAgency(AgencyInfo agency) {
		this.agency = agency;
	}
	/**
	 * 获取所属用户信息。
	 * @return 所属用户信息。
	 */
	public UserInfo getUser() {
		return user;
	}
	/**
	 * 设置所属用户信息。
	 * @param user 
	 *	  所属用户信息。
	 */
	public void setUser(UserInfo user) {
		this.user = user;
	}
	/**
	 * 获取创建时间。
	 * @return 创建时间。
	 */
	@JsonSerialize(using = CustomDateSerializer.LongDate.class)
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置创建时间。
	 * @param createTime 
	 *	  创建时间。
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取最后修改时间。
	 * @return 最后修改时间。
	 */
	@JsonSerialize(using = CustomDateSerializer.LongDate.class)
	public Date getLastTime() {
		return lastTime;
	}
	/**
	 * 设置最后修改时间。
	 * @param lastTime 
	 *	  最后修改时间。
	 */
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
}