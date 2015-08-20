package com.examw.netplatform.controllers.admin.settings;

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
import com.examw.netplatform.domain.admin.security.Right;
import com.examw.netplatform.domain.admin.settings.ExamSubjectView;
import com.examw.netplatform.model.EnumValueName;
import com.examw.netplatform.model.admin.settings.AreaInfo;
import com.examw.netplatform.model.admin.settings.SubjectInfo;
import com.examw.netplatform.service.admin.settings.IAreaService;
import com.examw.netplatform.service.admin.settings.ISubjectService;
import com.examw.service.Status;

/**
 * 科目数据控制器。
 * 
 * @author jeasonyoung
 * @since 2015年8月19日
 */
@RestController
@RequestMapping(value = "/admin/settings/subject/data")
public class SubjectDataController {
	private static final Logger logger = Logger.getLogger(SubjectDataController.class);
	private List<EnumValueName> statusList;
	//注入科目服务接口
	@Resource
	private ISubjectService subjectService;
	//注入地区服务接口
	@Resource
	private IAreaService areaService;
	/**
	 * 加载考试下的科目数据。
	 * @param examId
	 * 所属考试ID。
	 * @return
	 */
	@RequestMapping(value="/all")
	public List<SubjectInfo> loadSubjects(String examId){
		logger.debug(String.format("加载考试［examId = %s］下的科目数据...", examId));
		if(StringUtils.isBlank(examId)) return new ArrayList<SubjectInfo>();
		return this.subjectService.loadAllSubjects(examId);
	}
	/**
	 * 加载考试科目下地区数据。
	 * @param subjectId
	 * 考试科目ID。
	 * @return
	 */
	@RequestMapping(value = {"/areas"})
	public List<AreaInfo> loadSubjectAreas(String subjectId){
		logger.debug(String.format("加载考试科目［subjectId = %s］下地区数据..", subjectId));
		if(StringUtils.isBlank(subjectId)) return new ArrayList<AreaInfo>();
		return this.areaService.loadAreasBySubject(subjectId);
	}
	/**
	 * 加载考试科目树视图数据集合。
	 * @return
	 */
	@RequestMapping(value = {"/exam_subject_treeviews"})
	public List<ExamSubjectView> findExamSubjectViews(){
		logger.debug("加载考试科目树视图数据集合...");
		return this.subjectService.findExamSubjectViews();
	}
	/**
	 * 加载科目代码值。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_AREA + ":" + Right.VIEW})
	@RequestMapping(value="/code", method = RequestMethod.GET)
	public Integer code(){
		logger.debug("加载科目代码值...");
		Integer max = this.subjectService.loadMaxCode();
		if(max == null) max = 0;
		return max + 1;
	}
	/**
	 * 加载科目状态数据集合。
	 * @return
	 */
	@RequestMapping(value = "/subjectstatus")
	public List<EnumValueName> getSubjectStatus(){
		logger.debug("加载科目状态数据集合...");
		if(this.statusList == null || this.statusList.size() == 0){
			this.statusList = new ArrayList<EnumValueName>();
			for(Status status : Status.values()){
				this.statusList.add(new EnumValueName(status.getValue(), this.subjectService.loadStatusName(status.getValue())));
			}
			Collections.sort(this.statusList);
		}
		return this.statusList;
	}
	/**
	 * 查询数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_SUBJECT + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	public DataGrid<SubjectInfo> datagrid(SubjectInfo info){
		logger.debug("查询数据...");
		return this.subjectService.datagrid(info);
	}
	/**
	 * 更新数据。
	 * @param info
	 * 更新源数据。
	 * @return
	 * 更新后数据。
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_SUBJECT + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	public Json update(SubjectInfo info){
		logger.debug("更新数据...");
		Json result = new Json();
		try {
			result.setData(this.subjectService.update(info));
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
	@RequiresPermissions({ModuleConstant.SETTINGS_SUBJECT + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	public Json delete(@RequestBody String[] ids){
		logger.debug(String.format("删除数据 %s...", Arrays.toString(ids)));
		Json result = new Json();
		try {
			this.subjectService.delete(ids);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("删除数据时发生异常:%s", e.getMessage()), e);
		}
		return result;
	}
}