package com.examw.netplatform.controllers.admin.courses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import com.examw.netplatform.domain.admin.courses.HandoutMode;
import com.examw.netplatform.domain.admin.courses.VideoMode;
import com.examw.netplatform.domain.admin.security.Right;
import com.examw.netplatform.model.EnumValueName;
import com.examw.netplatform.model.admin.courses.ClassPlanInfo;
import com.examw.netplatform.service.admin.courses.IClassService;
import com.examw.netplatform.support.UserAware;
import com.examw.service.Status;

/**
 * 班级管理数据控制器。
 * 
 * @author jeasonyoung
 * @since 2015年8月21日
 */
@RestController
@RequestMapping(value = "/admin/courses/class/data")
public class ClassDataController implements UserAware {
	private static final Logger logger = Logger.getLogger(ClassDataController.class);
	private List<EnumValueName> handoutModeList, videoModeList,statusList;
	private String current_agency_id;//, userId;
	//注入班级管理服务接口。
	@Resource
	private IClassService classService;
	/*
	 * 设置当前用户所属机构ID。
	 * @see com.examw.netplatform.support.UserAware#setAgencyId(java.lang.String)
	 */
	@Override
	public void setAgencyId(String agencyId) {
		logger.debug("注入当前用户所属机构ID:" + agencyId);
		this.current_agency_id = agencyId;
	}
	/*
	 * 设置当前用户ID。
	 * @see com.examw.netplatform.support.UserAware#setUserId(java.lang.String)
	 */
	@Override
	public void setUserId(String userId) {
		logger.debug("注入当前用户ID:" + userId);
		//this.userId = userId;
	}
	/**
	 * 加载班级集合。
	 * @param agencyId
	 * @return
	 */
	@RequestMapping(value="/all")
	public List<ClassPlanInfo> loadAll(String subjectId){
		logger.debug(String.format("加载机构[%s]["+subjectId+"]下班级集合...", this.current_agency_id));
		return this.classService.loadClasses(this.current_agency_id, subjectId);
	}
	/**
	 * 加载讲义模式集合。
	 * @return
	 */
	@RequestMapping(value = "/handoutmodes")
	public List<EnumValueName> getHandoutModes(){
		logger.debug("加载讲义模式集合...");
		if(this.handoutModeList == null || this.handoutModeList.size() == 0){
			this.handoutModeList = new ArrayList<EnumValueName>();
			for(HandoutMode mode : HandoutMode.values()){
				this.handoutModeList.add(new EnumValueName(mode.getValue(), this.classService.loadHandoutModeName(mode.getValue())));
			}
			Collections.sort(this.handoutModeList);
		}
		return this.handoutModeList;
	}
	/**
	 * 加载视频模式集合。
	 * @return
	 */
	@RequestMapping(value = "/videomodes")
	public List<EnumValueName> getVideoModes(){
		logger.debug("加载视频模式集合...");
		if(this.videoModeList == null || this.videoModeList.size() == 0){
			this.videoModeList = new ArrayList<EnumValueName>();
			for(VideoMode mode : VideoMode.values()){
				this.videoModeList.add(new EnumValueName(mode.getValue(), this.classService.loadVideoModeName(mode.getValue())));
			}
			Collections.sort(this.videoModeList);
		}
		return this.videoModeList;
	}
	/**
	 * 加载状态集合。
	 * @return
	 */
	@RequestMapping(value = "/status")
	public List<EnumValueName> getStatus(){
		logger.debug("加载状态集合...");
		if(this.statusList == null || this.statusList.size() == 0){
			this.statusList = new ArrayList<EnumValueName>();
			for(Status s : Status.values()){
				this.statusList.add(new EnumValueName(s.getValue(), this.classService.loadStatusName(s.getValue())));
			}
			Collections.sort(this.statusList);
		}
		return this.statusList;
	}
	/**
	 * 加载机构下班级最大排序号。
	 * @param agencyId
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.COURSES_CLASS + ":" + Right.VIEW})
	@RequestMapping(value="/order")
	public Integer loadMaxOrder(){
		logger.debug(String.format("加载机构［%s］排序号...", this.current_agency_id));
		Integer max = this.classService.loadMaxOrder(this.current_agency_id);
		if(max == null) max = 0;
		return max + 1;
	}
	/**
	 * 加载列表数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.COURSES_CLASS + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	public DataGrid<ClassPlanInfo> datagrid(ClassPlanInfo info){
		logger.debug("加载机构["+this.current_agency_id+"]下列表数据...");
		info.setAgencyId(this.current_agency_id);
		return this.classService.datagrid(info);
	}
	/**
	 * 更新数据。
	 * @param info
	 * 更新源数据。
	 * @return
	 * 更新后数据。
	 */
	@RequiresPermissions({ModuleConstant.COURSES_CLASS + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	public Json update(ClassPlanInfo info){
		logger.debug("更新数据...");
		Json result = new Json();
		try {
			if(info.getStartTime().getTime() - info.getEndTime().getTime() > 0){
				result.setSuccess(false);
				result.setMsg("［开班时间］必须早于［结班时间］！");
				return result;
			}
			
			//设置默认机构
			if(StringUtils.isBlank(info.getAgencyId())){
				info.setAgencyId(this.current_agency_id);
			}
			
			result.setData(this.classService.update(info));
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
	@RequiresPermissions({ModuleConstant.COURSES_CLASS + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	public Json delete(@RequestBody String[] ids){
		logger.debug(String.format("删除数据:%s...", Arrays.toString(ids)));
		Json result = new Json();
		try {
			this.classService.delete(ids);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("删除数据时发生异常:%s", e.getMessage()), e);
		}
		return result;
	}
}