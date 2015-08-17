package com.examw.netplatform.controllers.admin.security;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.netplatform.domain.admin.security.MenuPermission;
import com.examw.netplatform.domain.admin.security.Right;
import com.examw.netplatform.model.admin.security.MenuRightInfo;
import com.examw.netplatform.model.admin.security.MenuRightPost;
import com.examw.netplatform.service.admin.security.IMenuRightService;

/**
 * 菜单权限数据控制器。
 * 
 * @author jeasonyoung
 * @since 2015年8月16日
 */
@RestController
@RequestMapping(value = "/admin/security/menu/right/data")
public class MenuRightDataController {
	private static final Logger logger = Logger.getLogger(MenuRightDataController.class);
	//菜单权限服务。
	@Resource
	private IMenuRightService menuRightService;
	/**
	 * 查询数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_MENU_RIGHT + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	public DataGrid<MenuRightInfo> datagrid(MenuRightInfo info){
		logger.debug("加载列表数据...");
		return this.menuRightService.datagrid(info);
	}
	/**
	 * 加载全部的菜单及其权限
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_MENU_RIGHT + ":" + Right.VIEW})
	@RequestMapping(value="/permissions")
	public List<MenuPermission> allMenuPermissions(){
		logger.debug("加载全部的菜单及其权限...");
		return this.menuRightService.findMenuPermissions(null);
	}
	/**
	 * 更新数据。
	 * @param info
	 * 更新源数据。
	 * @return
	 * 更新后数据。
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_MENU_RIGHT + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	public Json update(@RequestBody MenuRightPost post){
		logger.debug("更新数据...");
		Json result = new Json();
		try {
			if(post.getMenuId() == null || post.getMenuId().length == 0){
				throw new Exception("菜单ID为空！");
			}
			if(post.getRightId() == null || post.getRightId().length == 0){
				throw new Exception("权限ID为空！");
			}
			for(String menuId : post.getMenuId()){
				if(StringUtils.isEmpty(menuId)) continue;
				for(String rightId : post.getRightId()){
					if(StringUtils.isEmpty(rightId)) continue;
					MenuRightInfo info = new MenuRightInfo();
					info.setMenuId(menuId);
					info.setRightId(rightId);
					this.menuRightService.update(info);
				}
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新菜单权限时发生异常", e);
		}
		return result;
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_MENU_RIGHT + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	public Json delete(@RequestBody String[] id){
		logger.debug(String.format("删除数据：%s...", Arrays.toString(id)));
		Json result = new Json();
		try {
			this.menuRightService.delete(id);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("删除数据时发生异常:%s",e.getMessage()), e);
		}
		return result;
	}
}