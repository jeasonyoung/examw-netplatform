package com.examw.netplatform.model.admin.agency;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.examw.model.Paging; 
import com.examw.support.CustomDateSerializer;
/**
 * 培训机构信息
 * @author yangyong.
 * @since 2014-04-29.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class AgencyInfo extends Paging {
	private static final long serialVersionUID = 1L; 
	private String id,name,statusName,abbr_cn,abbr_en,keywords,address,tel,fax,introduction,remarks,logo_url;
	private int status,packageCount,accountCount;
	private String[] roleId;
	private Date createTime,lastTime;
	/**
	 * 获取培训机构ID。
	 * @return
	 * 培训机构ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置培训机构ID。
	 * @param id
	 * 培训机构ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取培训机构名称。
	 * @return
	 * 培训机构名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置培训机构名称。
	 * @param name
	 * 培训机构名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取中文简称。
	 * @return
	 * 中文简称。
	 */
	public String getAbbr_cn() {
		return abbr_cn;
	}
	/**
	 * 设置中文简称。
	 * @param abbr_cn
	 */
	public void setAbbr_cn(String abbr_cn) {
		this.abbr_cn = abbr_cn;
	}
	/**
	 * 获取英文简称。
	 * @return
	 * 英文简称。
	 */
	public String getAbbr_en() {
		return abbr_en;
	}
	/**
	 * 设置英文简称。
	 * @param abbr_en
	 * 英文简称。
	 */
	public void setAbbr_en(String abbr_en) {
		this.abbr_en = abbr_en;
	}
	/**
	 * 获取关键字。
	 * @return
	 * 关键字。
	 */
	public String getKeywords() {
		return keywords;
	}
	/**
	 * 设置关键字。
	 * @param keywords
	 * 关键字。
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	/**
	 * 获取机构地址。
	 * @return
	 * 机构地址。
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 设置机构地址。
	 * @param address
	 * 机构地址。
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取联系电话。
	 * @return 
	 * 联系电话。
	 */
	public String getTel() {
		return tel;
	}
	/**
	 * 设置联系电话。
	 * @param tel
	 * 联系电话。
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}
	/**
	 * 获取传真电话。
	 * @return
	 * 传真电话。
	 */
	public String getFax() {
		return fax;
	}
	/**
	 * 设置传真电话。
	 * @param fax
	 * 传真电话。
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}
	/**
	 * 获取机构简介。
	 * @return
	 * 机构简介。
	 */
	public String getIntroduction() {
		return introduction;
	}
	/**
	 * 设置机构简介。
	 * @param introduction
	 * 机构简介。
	 */
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	/**
	 * 获取备注。
	 * @return
	 * 备注。
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * 设置备注。
	 * @param remarks
	 * 备注。
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * 获取Logo图片地址。
	 * @return
	 * Logo图片地址。
	 */
	public String getLogo_url() {
		return logo_url;
	}
	/**
	 * 设置Logo图片地址。
	 * @param logo_url
	 * Logo图片地址。
	 */
	public void setLogo_url(String logo_url) {
		this.logo_url = logo_url;
	}
	/**
	 * 获取套餐上限。
	 * @return
	 * 套餐上限。
	 */
	public int getPackageCount() {
		return packageCount;
	}
	/**
	 * 设置套餐上限。
	 * @param packageCount
	 * 套餐上限。
	 */
	public void setPackageCount(int packageCount) {
		this.packageCount = packageCount;
	}
	/**
	 * 获取用户账号上限。
	 * @return
	 * 用户账号上限。
	 */
	public int getAccountCount() {
		return accountCount;
	}
	/**
	 * 设置用户账号上限。
	 * @param accountCount
	 * 用户账号上限。
	 */
	public void setAccountCount(int accountCount) {
		this.accountCount = accountCount;
	}
	/**
	 * 获取机构状态。
	 * @return 
	 * 机构状态。
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * 设置机构状态。
	 * @param status
	 * 机构状态。
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	/**
	 * 获取状态名称。
	 * @return
	 * 状态名称。
	 */
	public String getStatusName() {
		return statusName;
	}
	/**
	 * 设置状态名称。
	 * @param statusName
	 * 状态名称。
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	/**
	 * 获取授权角色ID集合。
	 * @return 授权角色ID集合。
	 */
	public String[] getRoleId() {
		return roleId;
	}
	/**
	 * 设置授权角色ID集合。
	 * @param roleId
	 * 授权角色ID集合。
	 */
	public void setRoleId(String[] roleId) {
		this.roleId = roleId;
	}
	/**
	 * 获取创建时间。
	 * @return 创建时间。
	 */
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置创建时间。
	 * @param createTime
	 * 创建时间。
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取最新修改时间。
	 * @return 最新修改时间。
	 */
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getLastTime() {
		return lastTime;
	}
	/**
	 * 设置最新修改时间。
	 * @param lastTime
	 * 最新修改时间。
	 */
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
}