package com.examw.netplatform.controllers.admin.security;

import java.util.Arrays;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.netplatform.domain.admin.security.Right;
import com.examw.netplatform.model.admin.security.LoginLogInfo;
import com.examw.netplatform.service.admin.security.ILoginLogService;

/**
 * 日志数据控制器。
 * 
 * @author jeasonyoung
 * @since 2015年8月17日
 */
@RestController
@RequestMapping(value = "/admin/security/log/data")
public class LogDataController {
	private static  final Logger logger = Logger.getLogger(LogDataController.class);
	//注入登录日志服务。
	@Resource
	private ILoginLogService loginLogService;
	/**
	 * 查询数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_LOGIN_LOG + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	public DataGrid<LoginLogInfo> datagrid(LoginLogInfo info){
		logger.debug("加载列表数据...");
		return this.loginLogService.datagrid(info);
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_LOGIN_LOG + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	public Json delete(@RequestBody String[] ids){
		logger.debug(String.format("删除数据 %s...", Arrays.toString(ids)));
		Json result = new Json();
		try {
			this.loginLogService.delete(ids);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("删除数据时发生异常:", e.getMessage()), e);
		}
		return result;
	}
}