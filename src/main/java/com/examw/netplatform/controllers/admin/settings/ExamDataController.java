package com.examw.netplatform.controllers.admin.settings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
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
import com.examw.netplatform.model.admin.settings.AreaInfo;
import com.examw.netplatform.model.admin.settings.ExamInfo;
import com.examw.netplatform.service.admin.settings.IExamService;
import com.examw.service.Status;

/**
 * 考试数据控制器。
 * 
 * @author jeasonyoung
 * @since 2015年8月19日
 */
@RestController
@RequestMapping(value = "/admin/settings/exam/data")
public class ExamDataController {
	private static final Logger logger = Logger.getLogger(ExamDataController.class);
	private List<EnumValueName> statusList;
	//注入考试数据接口.
	@Resource
	private IExamService examService;
	/**
	 * 加载考试类别下的考试集合。
	 * @return
	 */
	@RequestMapping(value = {"/all"})
	public List<ExamInfo> all(String categoryId){
		logger.debug(String.format("加载考试类别［categoryId = %s］下的考试集合...", categoryId));
		if(StringUtils.isBlank(categoryId)) return new ArrayList<ExamInfo>();
		return this.examService.loadExams(categoryId,null);
	}
	/**
	 * 加载考试类别下的考试集合。
	 * @param categoryId
	 * @return
	 */
	@RequestMapping(value = {"/all/{categoryId}"})
	public List<ExamInfo> allExams(@PathVariable String categoryId){
		logger.debug(String.format("加载考试类别［categoryId = %s］下的考试集合...", categoryId));
		return this.examService.loadExams(categoryId,null);
	}
	/**
	 * 加载考试所属地区集合。
	 * @param examId
	 * 所属考试ID。
	 * @return
	 */
	@RequestMapping(value = {"/areas/${examId}"})
	public List<AreaInfo> loadArea(@PathVariable String examId){
		logger.debug(String.format("加载考试［examId = %s］所属地区集合...", examId));
		return this.examService.loadExamAreas(examId);
	}
	/**
	 * 加载最大考试代码。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_AREA + ":" + Right.VIEW})
	@RequestMapping(value="/code", method = RequestMethod.GET)
	public Integer code(){
		logger.debug("加载最大考试代码...");
		Integer max = this.examService.loadMaxCode();
		if(max == null) max = 0;
		return max+1;
	}
	/**
	 * 获取考试状态集合。
	 * @return
	 */
	@RequestMapping(value = "/examstatus")
	public List<EnumValueName> getExamStatus(){
		logger.debug("获取考试状态集合...");
		if(this.statusList == null || this.statusList.size() == 0){
			this.statusList = new ArrayList<EnumValueName>();
			for(Status status : Status.values()){
				this.statusList.add(new EnumValueName(status.getValue(), this.examService.loadStatusName(status.getValue())));
			}
			Collections.sort(this.statusList);
		}
		return this.statusList;
	}
	/**
	 * 加载列表数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_EXAM + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	public DataGrid<ExamInfo> datagrid(ExamInfo info){
		logger.debug("加载列表数据...");
		return this.examService.datagrid(info);
	}
	/**
	 * 更新数据。
	 * @param info
	 * 更新源数据。
	 * @return
	 * 更新后数据。
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_EXAM + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	public Json update(ExamInfo info){ 
		logger.debug("更新数据...");
		Json result = new Json();
		try {
			result.setData(this.examService.update(info));
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
	@RequiresPermissions({ModuleConstant.SETTINGS_EXAM + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	public Json delete(@RequestBody String[] ids){
		logger.debug(String.format(String.format("删除数据［%s］...", Arrays.toString(ids))));
		Json result = new Json();
		try {
			this.examService.delete(ids);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("删除数据时发生异常:%s", e.getMessage()),e);
		}
		return result;
	}
}