package com.examw.netplatform.controllers.admin.students;

import java.util.Arrays;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.netplatform.domain.admin.security.Right;
import com.examw.netplatform.domain.admin.security.UserIdentity;
import com.examw.netplatform.domain.admin.security.UserType;
import com.examw.netplatform.model.admin.security.UserInfo;
import com.examw.netplatform.service.admin.security.IUserService;
import com.examw.netplatform.support.UserAware;
/**
 * 机构学员数据控制器。
 * 
 * @author jeasonyoung
 * @since 2015年8月25日
 */
@RestController
@RequestMapping(value = "/admin/students/user/data")
public class StudentDataController implements UserAware {
	private static final Logger logger = Logger.getLogger(StudentDataController.class);
	private String current_agency_id;//,current_user_id;
	//注入用户服务接口。
	@Resource
	private IUserService userService;
	/*
	 * 设置当前机构ID。
	 * @see com.examw.netplatform.support.UserAware#setAgencyId(java.lang.String)
	 */
	@Override
	public void setAgencyId(String agencyId) {
		logger.debug("注入当前机构ID..." + agencyId);
		this.current_agency_id = agencyId;
	}
	/*
	 * 设置当前用户ID。
	 * @see com.examw.netplatform.support.UserAware#setUserId(java.lang.String)
	 */
	@Override
	public void setUserId(String userId) {
		logger.debug("注入当前用户ID..." + userId);
		//this.current_user_id = userId;
	}
	/**
	 * 查询数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.STUDENTS_USER + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	public DataGrid<UserInfo> datagrid(UserInfo info){
		logger.debug("加载机构学员用户集合...");
		info.setAgencyId(this.current_agency_id);
		info.setIdentity(UserIdentity.STUDENT.getValue());
		info.setType(UserType.FRONT.getValue());
		return this.userService.datagrid(info);
	}
	/**
	 * 更新数据。
	 * @param info
	 * 更新源数据。
	 * @return
	 * 更新后数据。
	 */
	@RequiresPermissions({ModuleConstant.STUDENTS_USER + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	public Json update(UserInfo info){
		logger.debug("更新数据...");
		Json result = new Json();
		try {
			//机构
			if(StringUtils.isBlank(info.getAgencyId())){
				info.setAgencyId(this.current_agency_id);
			}
			//身份
			if(info.getIdentity() == null){
				info.setIdentity(UserIdentity.STUDENT.getValue());
			}
			//类型
			if(info.getType() == null){
				info.setType(UserType.FRONT.getValue());
			}
			//更新数据
			result.setData(this.userService.update(info));
			//
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新数据发生异常", e);
		}
		return result;
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.STUDENTS_USER + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	public Json delete(@RequestBody String[] ids){
		logger.debug(String.format("删除数据...",Arrays.toString(ids)));
		Json result = new Json();
		try {
			this.userService.delete(ids);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("删除数据时发生异常:%s", e.getMessage()), e);
		}
		return result;
	}
}