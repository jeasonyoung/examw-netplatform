package com.examw.netplatform.controllers.admin.settings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import com.examw.netplatform.domain.admin.settings.MsgType;
import com.examw.netplatform.model.EnumValueName;
import com.examw.netplatform.model.admin.settings.MsgBodyInfo;
import com.examw.netplatform.service.admin.settings.IMsgService;
import com.examw.netplatform.support.UserAware;

/**
 * 消息数据处理控制器。
 * 
 * @author jeasonyoung
 * @since 2015年8月30日
 */
@RestController
@RequestMapping(value = "/admin/settings/msg/data")
public class MsgDataController implements UserAware {
	private static final Logger logger = Logger.getLogger(MsgDataController.class);
	private String current_agency_id,current_user_id;
	private List<EnumValueName> typeList;
	//注入消息服务集合。
	@Resource
	private IMsgService msgService;
	/*
	 * 设置当前所属机构ID。
	 * @see com.examw.netplatform.support.UserAware#setAgencyId(java.lang.String)
	 */
	@Override
	public void setAgencyId(String agencyId) {
		logger.debug("注入当前所属机构ID..." + agencyId);
		this.current_agency_id = agencyId;
	}
	/*
	 * 设置当前所属用户ID。
	 * @see com.examw.netplatform.support.UserAware#setUserId(java.lang.String)
	 */
	@Override
	public void setUserId(String userId) {
		logger.debug("注入当前所属用户ID..." + userId);
		this.current_user_id = userId;
	}
	/**
	 * 加载消息类型数据集合。
	 * @return
	 */
	@RequestMapping(value = "/types")
	public List<EnumValueName> getTypes(){
		logger.debug("加载消息类型数据集合...");
		if(this.typeList == null || this.typeList.size() == 0){
			this.typeList = new ArrayList<EnumValueName>();
			for(MsgType type : MsgType.values()){
				this.typeList.add(new EnumValueName(type.getValue(), this.msgService.loadTypeName(type.getValue())));
			}
		}
		return this.typeList;
	}
	/**
	 * 查询数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_MSG + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	public DataGrid<MsgBodyInfo> datagrid(MsgBodyInfo info){
		logger.debug("查询数据...");
		info.setAgencyId(this.current_agency_id);
		return this.msgService.datagrid(info);
	}
	/**
	 * 更新数据。
	 * @param info
	 * 更新源数据。
	 * @return
	 * 更新后数据。
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_MSG + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	public Json update(MsgBodyInfo info){
		logger.debug("更新数据...");
		final Json result = new Json();
		try {
			//当前所属机构ID。
			if(StringUtils.isBlank(info.getAgencyId())) info.setAgencyId(this.current_agency_id);
			//当前用户ID
			info.setUserId(this.current_user_id);
			//更新数据。
			result.setData(this.msgService.update(info));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("更新数据发生异常:%s", e.getMessage()), e);
		}
		return result;
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_MSG + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	public Json delete(@RequestBody String[] ids){
		logger.debug(String.format("删除数据 %s...", Arrays.toString(ids)));
		final Json result = new Json();
		try {
			this.msgService.delete(ids);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("删除数据时发生异常:%s", e.getMessage()), e);
		}
		return result;
	}
}