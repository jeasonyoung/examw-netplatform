package com.examw.netplatform.model.admin.students;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.examw.netplatform.domain.admin.agency.AgencyUser;
import com.examw.netplatform.model.admin.agency.AgencyUserInfo;

/**
 * 机构学员信息。
 * @author fengwei.
 * @since 2014年5月24日 上午11:16:08.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class StudentInfo extends AgencyUserInfo {
	private static final long serialVersionUID = 1L;
	/*
	 * 获取所属身份（固定为机构学员）。
	 * @see com.examw.netplatform.model.admin.agency.AgencyUserInfo#getIdentity()
	 */
	@Override
	public Integer getIdentity() {
		return AgencyUser.IDENTITY_STUDENT;
	}
	/*
	 * 设置所属身份（不支持该方法）。
	 * @see com.examw.netplatform.model.admin.agency.AgencyUserInfo#setIdentity(java.lang.Integer)
	 */
	@Override
	public void setIdentity(Integer identity){
		
	}
}