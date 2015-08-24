package com.examw.netplatform.controllers.admin.teachers;

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
import com.examw.netplatform.domain.admin.teachers.ClassLessonView;
import com.examw.netplatform.model.EnumValueName;
import com.examw.netplatform.model.admin.teachers.AnswerQuestionTopicInfo;
import com.examw.netplatform.service.admin.teachers.IAnswerQuestionService;
import com.examw.netplatform.support.UserAware;
import com.examw.service.Status;

/**
 * 教师答疑控制器。
 * 
 * @author jeasonyoung
 * @since 2015年8月24日
 */
@RestController
@RequestMapping("/admin/teachers/answerquestion/data")
public class AnswerQuestionDataController implements UserAware {
	private static final Logger logger = Logger.getLogger(AnswerQuestionDataController.class);
	private List<EnumValueName> statusList;
	private String current_agency_id;
	//注入答疑服务接口。
	@Resource
	private IAnswerQuestionService answerQuestionService;
	/*
	 * 设置当前机构ID。
	 * @see com.examw.netplatform.support.UserAware#setAgencyId(java.lang.String)
	 */
	@Override
	public void setAgencyId(String agencyId) {
		logger.debug("注入当前机构ID..." + agencyId);
		this.current_agency_id = agencyId;
	}
	/*
	 * 设置当前用户ID。
	 * @see com.examw.netplatform.support.UserAware#setUserId(java.lang.String)
	 */
	@Override
	public void setUserId(String userId) {
		logger.debug("注入当前用户ID:" + userId);
	}
	/**
	 * 获取答疑状态集合。
	 * @return
	 */
	@RequestMapping(value = "/status")
	public List<EnumValueName> getStatus(){
		logger.debug("答疑状态集合...");
		if(this.statusList == null || this.statusList.size() == 0){
			this.statusList = new ArrayList<EnumValueName>();
			for(Status s : Status.values()){
				this.statusList.add(new EnumValueName(s.getValue(), this.answerQuestionService.loadStatusName(s.getValue())));
			}
			Collections.sort(this.statusList);
		}
		return this.statusList;
	}
	/**
	 * 加载班级/课程资源数据.
	 * @return
	 */
	@RequestMapping(value = "/class_lesson_views")
	public List<ClassLessonView> loadClassLessonViews(){
		logger.debug("加载班级/课程资源数据..." + this.current_agency_id);
		return this.answerQuestionService.findClassLessonViews(this.current_agency_id);
	}
	/**
	 * 查询答疑主题数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHERS_ANSWERS + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	public DataGrid<AnswerQuestionTopicInfo> datagrid(AnswerQuestionTopicInfo info){
		logger.debug("查询答疑主题数据...");
		return this.answerQuestionService.datagrid(info);
	}
	/**
	 * 更新数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHERS_ANSWERS + ":" + Right.UPDATE})
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Json update(AnswerQuestionTopicInfo info){
		logger.debug("更新数据...");
		Json result = new Json();
		try {
			if(StringUtils.isBlank(info.getAgencyId())){
				info.setAgencyId(this.current_agency_id);
			}
			result.setData(this.answerQuestionService.update(info));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("更新数据时发生异常:%s", e.getMessage()), e);
		}
		return result;
	}
	/**
	 * 更新答疑状态。
	 * @param topicId
	 * @param status
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHERS_ANSWERS + ":" + Right.UPDATE})
	@RequestMapping(value = "/update/status/{topicId}/{status}", method = RequestMethod.POST)
	public Json updateStatus(@PathVariable("topicId")String topicId,@PathVariable("status")Integer status){
		Json result = new Json();
		try{
			this.answerQuestionService.updateStatus(topicId, Status.conversion(status));
			result.setSuccess(true);
		}catch(Exception e){
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("更新状态数据异常：%s", e.getMessage()), e);
		}
		return result;
	}
	/**
	 * 删除教师答疑主题删除。
	 * @param ids
	 * 主题ID。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHERS_ANSWERS + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	public Json delete(@RequestBody String[] ids){
		logger.debug(String.format("删除数据:%s ...", Arrays.toString(ids)));
		Json result = new Json();
		try {
			this.answerQuestionService.delete(ids);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("删除数据时发生异常:%s", e.getMessage()), e);
		}
		return result;
	}
	/**
	 * 删除教师答疑明细。
	 * @param ids
	 * 明细ID。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHERS_ANSWERS + ":" + Right.DELETE})
	@RequestMapping(value="/delete/details", method = RequestMethod.POST)
	public Json deleteDetails(@RequestBody String[] ids){
		logger.debug(String.format("删除数据:%s ...", Arrays.toString(ids)));
		Json result = new Json();
		try {
			this.answerQuestionService.deleteDetails(ids);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("删除数据时发生异常:%s", e.getMessage()), e);
		}
		return result;
	}
}