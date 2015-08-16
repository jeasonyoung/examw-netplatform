package com.examw.netplatform.controllers.admin.security;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.examw.model.Json;
import com.examw.netplatform.domain.admin.security.Right;
import com.examw.netplatform.model.admin.security.MenuInfo;
import com.examw.netplatform.service.admin.security.IMenuService;

/**
 * 菜单数据控制器。
 * 
 * @author jeasonyoung
 * @since 2015年8月16日
 */
@RestController
@RequestMapping(value = "/admin/security/menu/data")
public class MenuDataController {
	private static final Logger logger = Logger.getLogger(MenuDataController.class);
	//菜单服务接口。
	@Resource
	private IMenuService menuService;
	/**
	 * 初始化菜单数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_MENU + ":" + Right.UPDATE})
	@RequestMapping(value = "/init", method = {RequestMethod.POST, RequestMethod.GET})
	public Json init(){
		logger.debug("初始化菜单数据...");
		Json result = new Json();
		try {
			this.menuService.init();
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("初始化菜单时发生异常", e);
		}
		return result;
	}
	/**
	 * 列表数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_MENU + ":" + Right.VIEW})
	@RequestMapping(value = "/datagrid", method = RequestMethod.POST)
	public List<MenuInfo> datagrid(MenuInfo info){
		logger.debug("加载列表数据...");
		return this.menuService.datagrid(info);
	}
	/**
	 * 菜单树结构数据。
	 * @return
	 */
	@RequestMapping(value = "/tree", method = {RequestMethod.GET,RequestMethod.POST})
	public List<MenuInfo> tree(){
		logger.debug("加载全部菜单数据..");
		return this.menuService.loadAllMenus();
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_MENU + ":" + Right.DELETE})
	@RequestMapping(value= "/delete", method = RequestMethod.POST)
	public Json delete(@RequestBody String[] ids){
		if(logger.isDebugEnabled()) logger.debug(String.format("删除数据:%s...", StringUtils.join(ids,",")));
		Json result = new Json();
		try {
			this.menuService.delete(ids);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("删除数据时发生异常:%s", e.getMessage()), e);
		}
		return result;
	}
}