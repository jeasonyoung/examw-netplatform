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
import com.examw.netplatform.model.admin.students.LearningInfo;
import com.examw.netplatform.service.admin.students.ILearningService;
import com.examw.netplatform.support.UserAware;

/**
 * 学习进度数据控制器。
 * 
 * @author jeasonyoung
 * @since 2015年8月26日
 */
@RestController
@RequestMapping("/admin/students/learning/data")
public class LearningDataController implements UserAware {
	private static final Logger logger = Logger.getLogger(LearningDataController.class);
	private String current_agency_id;
	//注入进度服务接口。
	@Resource
	private ILearningService learningService;
	/*
	 * 设置当前训机构ID。
	 * @see com.examw.netplatform.support.UserAware#setAgencyId(java.lang.String)
	 */
	@Override
	public void setAgencyId(String agencyId) {
		logger.debug("注入当前训机构ID..." + agencyId);
		this.current_agency_id = agencyId;
	}
	/*
	 * 设置当前用户ID。
	 * @see com.examw.netplatform.support.UserAware#setUserId(java.lang.String)
	 */
	@Override
	public void setUserId(String userId) {
		logger.debug("注入当前用户ID..." + userId);
	}
	/**
	 * 查询数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.STUDENTS_LEARNING + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	public DataGrid<LearningInfo> datagrid(LearningInfo info){
		logger.debug(String.format("查询数据［%s］...", this.current_agency_id));
		info.setAgencyId(this.current_agency_id);
		return this.learningService.datagrid(info);
	}
	/**
	 * 更新数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.STUDENTS_LEARNING + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	public Json update(LearningInfo info){
		logger.debug("更新数据...");
		Json result = new Json();
		try {
			if(StringUtils.isBlank(info.getAgencyId())){
				info.setAgencyId(this.current_agency_id);
			}
			result.setData(this.learningService.update(info));
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
	@RequiresPermissions({ModuleConstant.STUDENTS_ORDER + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	public Json delete(@RequestBody String[] ids){
		logger.debug(String.format("删除数据...",Arrays.toString(ids)));
		Json result = new Json();
		try {
			this.learningService.delete(ids);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("删除数据时发生异常:%s", e.getMessage()), e);
		}
		return result;
	}
}