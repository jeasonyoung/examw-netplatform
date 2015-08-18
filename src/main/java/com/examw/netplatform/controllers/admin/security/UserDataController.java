package com.examw.netplatform.controllers.admin.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.netplatform.domain.admin.security.Right;
import com.examw.netplatform.model.EnumValueName;
import com.examw.netplatform.model.admin.security.UserIdentity;
import com.examw.netplatform.model.admin.security.UserInfo;
import com.examw.netplatform.model.admin.security.UserStatus;
import com.examw.netplatform.model.admin.security.UserType;
import com.examw.netplatform.service.admin.security.IUserService;
import com.examw.service.Gender;

/**
 * 用户管理数据控制器。
 * 
 * @author jeasonyoung
 * @since 2015年8月17日
 */
@RestController
@RequestMapping(value = "/admin/security/user/data")
public class UserDataController {
	private static final Logger logger = Logger.getLogger(UserDataController.class);
	private List<EnumValueName> genderList, userTypeList,userStatusList, userIdentityList;
	//注入用户服务接口。
	@Resource
	private IUserService userService;
	
	/**
	 * 获取性别枚举数据。
	 * @return
	 */
	@RequestMapping(value="/genders")
	public List<EnumValueName> getGenders(){
		logger.debug("加载全部的性别数据..");
		if(this.genderList == null || this.genderList.size() == 0){
			this.genderList = new ArrayList<EnumValueName>();
			for(Gender gender : Gender.values()){
				this.genderList.add(new EnumValueName(gender.getValue(), this.userService.loadGenderName(gender.getValue())));
			}
			Collections.sort(this.genderList);
		}
		return this.genderList;
	}
	/**
	 * 获取用户类型枚举数据。
	 * @return
	 */
	@RequestMapping(value="/usertypes")
	public List<EnumValueName> getUserTypes(){
		logger.debug("加载用户类型枚举数据...");
		if(this.userTypeList == null || this.userTypeList.size() == 0){
			this.userTypeList = new ArrayList<EnumValueName>();
			for(UserType userType : UserType.values()){
				this.userTypeList.add(new EnumValueName(userType.getValue(), this.userService.loadTypeName(userType.getValue())));
			}
			Collections.sort(this.userTypeList);
		}
		return this.userTypeList;
	}
	/**
	 * 获取用户状态枚举数据。
	 * @return
	 */
	@RequestMapping(value="/userstatus")
	public List<EnumValueName> getUserStatus(){
		logger.debug("加载用户状态枚举数据...");
		if(this.userStatusList == null || this.userStatusList.size() == 0){
			this.userStatusList = new ArrayList<EnumValueName>();
			for(UserStatus userStatus : UserStatus.values()){
				this.userStatusList.add(new EnumValueName(userStatus.getValue(), this.userService.loadStatusName(userStatus.getValue())));
			}
			Collections.sort(this.userStatusList);
		}
		return this.userStatusList;
	}
	/**
	 * 获取用户身份枚举数据。
	 * @return
	 */
	@RequestMapping(value="/identities")
	public List<EnumValueName> getUserIdentity(){
		logger.debug("用户身份枚举数据...");
		if(this.userIdentityList == null || this.userIdentityList.size() == 0){
			this.userIdentityList = new ArrayList<EnumValueName>();
			for(UserIdentity identity : UserIdentity.values()){
				this.userIdentityList.add(new EnumValueName(identity.getValue(), this.userService.loadIdentityName(identity.getValue())));
			}
		}
		return this.userIdentityList;
	}
	/**
	 * 查询数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	public DataGrid<UserInfo> datagrid(UserInfo info){
		logger.debug("加载列表数据...");
		return this.userService.datagrid(info);
	}
	/**
	 * 更新数据。
	 * @param info
	 * 更新源数据。
	 * @return
	 * 更新后数据。
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	public Json update(UserInfo info){
		logger.debug("更新数据...");
		Json result = new Json();
		try {
			 result.setData(this.userService.update(info));
			 result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新用户数据发生异常", e);
		}
		return result;
	}
	/**
	 * 重置用户密码。
	 * @param userId
	 * @param password
	 * @return
	 */
	@RequiresPermissions(ModuleConstant.SECURITY_USER + ":" +Right.UPDATE)
	@RequestMapping(value="/{userId}/modifyPwd", method = RequestMethod.POST)
	public Json modifyUserPwd(@PathVariable String userId,String password){
		logger.debug(String.format("重置用户［%s］密码...", userId));
		Json result = new Json();
		try {
			 this.userService.modifyPassword(userId, null, password);
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
	@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	public Json delete(@RequestBody String[] ids){
		logger.debug(String.format("删除数据: %s...", Arrays.toString(ids)));
		Json result = new Json();
		try {
			this.userService.delete(ids);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("删除数据时发生异常:", e.getMessage()), e);
		}
		return result;
	}
}