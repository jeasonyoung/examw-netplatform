package com.examw.netplatform.model.admin.settings;
 
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.examw.netplatform.model.admin.security.UserInfo;
import com.examw.support.CustomDateSerializer;

/**
 * 机构用户信息。
 * @author yangyong.
 * @since 2014-07-08.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class AgencyUserInfo extends UserInfo implements IAccountPassword {
	private static final long serialVersionUID = 1L;
	private String id,identityName,userId,agencyId,agencyName,description;
	private Integer identity;
	private Date lastTime;
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
	 * 获取用户ID。
	 * @return 用户ID。
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置用户ID。
	 * @param userId 
	 *	  用户ID。
	 */
	public void setUserId(String userId) {
		this.userId = userId;
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
	 * 获取所属机构ID。
	 * @return 所属机构ID。
	 */
	public String getAgencyId() {
		return agencyId;
	}
	/**
	 * 设置所属机构ID。
	 * @param agencyId 
	 *	  所属机构ID。
	 */
	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}
	/**
	 * 获取所属机构名称。
	 * @return 所属机构名称。
	 */
	public String getAgencyName() {
		return agencyName;
	}
	/**
	 * 设置所属机构名称。
	 * @param agencyName 
	 *	  所属机构名称。
	 */
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	/**
	 * 获取描述信息。
	 * @return 描述信息。
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置描述信息。
	 * @param description 
	 *	  描述信息。
	 */
	public void setDescription(String description) {
		this.description = description;
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