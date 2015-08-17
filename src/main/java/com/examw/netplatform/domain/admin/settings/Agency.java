package com.examw.netplatform.domain.admin.settings;

import java.io.Serializable;
/**
 * 机构设置。
 * @author yangyong.
 * @since 2014-04-2
 */
public class Agency implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,name,abbrCN,abbrEN,keywords,address,tel,fax,introduction,remarks,logoUrl;
	private Integer status,packageCount,accountCount; 
	private String createTime,lastTime;
	/**
	 * 获取培训机构ID。
	 * @return 培训机构ID。
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
	 * @return 培训机构名称。
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
	 * @return 中文简称。
	 */
	public String getAbbrCN() {
		return abbrCN;
	}
	/**
	 * 设置中文简称。
	 * @param abbrCN
	 * 中文简称。
	 */
	public void setAbbrCN(String abbrCN) {
		this.abbrCN = abbrCN;
	}
	/**
	 * 获取英文简称。
	 * @return 英文简称。
	 */
	public String getAbbrEN() {
		return abbrEN;
	}
	/**
	 * 设置英文简称。
	 * @param abbrEN
	 * 英文简称。
	 */
	public void setAbbrEN(String abbrEN) {
		this.abbrEN = abbrEN;
	}
	/**
	 * 获取关键字。
	 * @return 关键字。
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
	 * @return 机构地址。
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
	 * @return  联系电话。
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
	 * @return 传真电话。
	 */
	public String getFax() {
		return fax;
	}
	/**
	 * 设置传真电话。
	 * @param fax
	 * 	传真电话。
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}
	/**
	 * 获取机构简介。
	 * @return 机构简介。
	 */
	public String getIntroduction() {
		return introduction;
	}
	/**
	 * 设置机构简介。
	 * @param introduction
	 * 	机构简介。
	 */
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	/**
	 * 获取备注。
	 * @return 备注。
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
	 * @return Logo图片地址。
	 */
	public String getLogoUrl() {
		return logoUrl;
	}
	/**
	 * 设置Logo图片地址。
	 * @param logoUrl
	 * Logo图片地址。
	 */
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
	/**
	 * 获取套餐上限。
	 * @return 套餐上限。
	 */
	public Integer getPackageCount() {
		return packageCount;
	}
	/**
	 * 设置套餐上限。
	 * @param packageCount
	 * 套餐上限。
	 */
	public void setPackageCount(Integer packageCount) {
		this.packageCount = packageCount;
	}
	/**
	 * 获取用户账号上限。
	 * @return 用户账号上限。
	 */
	public Integer getAccountCount() {
		return accountCount;
	}
	/**
	 * 设置用户账号上限。
	 * @param accountCount
	 * 用户账号上限。
	 */
	public void setAccountCount(Integer accountCount) {
		this.accountCount = accountCount;
	}
	/**
	 * 获取机构状态。
	 * @return  机构状态。
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置机构状态。
	 * @param status
	 * 机构状态。
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取创建时间。
	 * @return 创建时间。
	 */
	public String getCreateTime() {
		return createTime;
	}
	/**
	 * 设置创建时间。
	 * @param createTime
	 * 创建时间。
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取最新修改时间。
	 * @return 最新修改时间。
	 */
	public String getLastTime() {
		return lastTime;
	}
	/**
	 * 设置最新修改时间。
	 * @param lastTime
	 * 最新修改时间。
	 */
	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}
}