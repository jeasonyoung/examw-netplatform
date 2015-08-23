package com.examw.netplatform.controllers.admin.teachers;

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
import com.examw.netplatform.model.admin.teachers.TeacherInfo;
import com.examw.netplatform.service.admin.teachers.ITeacherService;
import com.examw.netplatform.support.UserAware;

/**
 * 教师数据控制器。
 * 
 * @author jeasonyoung
 * @since 2015年8月23日
 */
@RestController
@RequestMapping("/admin/teachers/teacher/data")
public class TeacherDataController implements UserAware {
	private static final Logger logger = Logger.getLogger(TeacherDataController.class);
	private String current_agency_id;
	//注入主讲教师服务接口。
	@Resource
	private ITeacherService teacherService;
	/*
	 * 设置当前用户机构ID。
	 * @see com.examw.netplatform.support.UserAware#setAgencyId(java.lang.String)
	 */
	@Override
	public void setAgencyId(String agencyId) {
		logger.debug("注入当前用户机构ID..." + agencyId);
		this.current_agency_id = agencyId;
	}
	/*
	 * 设置当前用户ID。
	 * @see com.examw.netplatform.support.UserAware#setUserId(java.lang.String)
	 */
	@Override
	public void setUserId(String userId) {
		logger.debug("注入当前用户ID...");
	}
	/**
	 * 查询数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHERS_USER + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	public DataGrid<TeacherInfo> datagrid(TeacherInfo info){
		logger.debug("查询数据...");
		info.setAgencyId(this.current_agency_id);//当前机构ID
		return this.teacherService.datagrid(info);
	}
	/**
	 * 更新数据。
	 * @param info
	 * 更新源数据。
	 * @return
	 * 更新后数据。
	 */
	@RequiresPermissions({ModuleConstant.TEACHERS_USER + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	public Json update(TeacherInfo info){
		logger.debug("更新数据...");
		Json result = new Json();
		try {
			if(StringUtils.isBlank(info.getAgencyId())){
				info.setAgencyId(this.current_agency_id);
			}
			result.setData(this.teacherService.update(info));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("更新数据时发生异常:%s", e.getMessage()), e);
		}
		return result;
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHERS_USER + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	public Json delete(@RequestBody String[] ids){
		if(logger.isDebugEnabled()) logger.debug(String.format("删除数据:%s ...", Arrays.toString(ids)));
		Json result = new Json();
		try {
			this.teacherService.delete(ids);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("删除数据时发生异常:%s", e.getMessage()), e);
		}
		return result;
	}
}