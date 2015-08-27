package com.examw.netplatform.controllers.admin;
 
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examw.netplatform.service.admin.security.IMenuService;
import com.examw.netplatform.service.admin.security.IUserService;
import com.examw.netplatform.service.admin.settings.IAgencyService;
import com.examw.netplatform.support.UserAware;

/**
 * 管理后台首页。
 * @author yangyong.
 * @since 2014-04-25.
 */
@Controller
@RequestMapping(value={"/admin"})
public class IndexController implements UserAware {
	private static final Logger logger = Logger.getLogger(IndexController.class);
	private String systemName,current_agency_id,current_user_id;
	//注入菜单服务接口。
	@Resource
	private IMenuService menuService;
	//注入机构服务接口。
	@Resource
	private IAgencyService agencyService;
	//注入用户服务接口
	@Resource
	private IUserService userService;
	/*
	 * 注入当前用户所属机构ID。
	 * @see com.examw.netplatform.support.UserAware#setAgencyId(java.lang.String)
	 */
	@Override
	public void setAgencyId(String agencyId) {
		logger.debug("注入当前用户所属机构ID..." + agencyId);
		this.current_agency_id = agencyId;
	}
	/*
	 * 注入当前用户ID.
	 * @see com.examw.aware.IUserAware#setUserId(java.lang.String)
	 */
	@Override
	public void setUserId(String userId) {
		logger.debug("注入当前用户ID..." + userId);
		this.current_user_id = userId;
	}
	/**
	 * 加载首页。
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"","index","/"}, method = RequestMethod.GET)
	public String index(Model model){ 
		logger.debug("加载首页..");
		//系统名称
		if(StringUtils.isBlank(this.systemName)){
			this.systemName = this.menuService.loadSystemName();
			logger.debug("惰性加载系统[index]名称:" + this.systemName);
		}
		model.addAttribute("systemName", this.systemName);
		return "/index";
	}
	/**
	 * 获取顶部
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "top", method = RequestMethod.GET)
	public String top(Model model){
		logger.debug("加载首页top..");
		final Map<String, Object> dataMap = new HashMap<String, Object>();
		//系统名称
		if(StringUtils.isBlank(this.systemName)){
			this.systemName = this.menuService.loadSystemName();
			logger.debug("惰性加载系统[top]名称:" + this.systemName);
		}
		dataMap.put("systemName", this.systemName);
		//当前机构ID
		dataMap.put("agencyName", this.agencyService.loadAgencyName(this.current_agency_id));
		//当前用户ID
		dataMap.put("userName", this.userService.loadUserName(this.current_user_id));
		//
		model.addAllAttributes(dataMap);
		return "/top";
	}
	/**
	 * 获取左边
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "left", method = RequestMethod.GET)
	public String left(Model model){
		logger.debug("加载首页left....");
		final Map<String, Object> dataMap = new HashMap<String, Object>();
		//当前机构ID
		dataMap.put("agencyId", this.current_agency_id);
		//当前用户ID
		dataMap.put("userId", this.current_user_id);
		//
		model.addAllAttributes(dataMap);
		return "/left";
	}
	/**
	 * 获取默认工作页面。
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "center", method = RequestMethod.GET)
	public String defaultWorkspace(Model model){
		logger.debug("加载首页workspace...");
		model.addAttribute("userId", this.current_user_id);
		return "/workspace";
	}
}