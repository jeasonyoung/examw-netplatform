package com.examw.netplatform.controllers.admin.teachers;

import java.util.Arrays;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.aware.IUserAware;
import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.netplatform.domain.admin.security.Right;
import com.examw.netplatform.model.admin.teachers.AnswerQuestionDetailInfo;
import com.examw.netplatform.model.admin.teachers.AnswerQuestionTopicInfo;
import com.examw.netplatform.service.admin.settings.IAgencyUserService;
import com.examw.netplatform.service.admin.teachers.IAnswerQuestionDetailService;
import com.examw.netplatform.service.admin.teachers.IAnswerQuestionTopicService;
import com.examw.netplatform.support.EnumMapUtils;
import com.examw.service.Status;
/**
 * 教师答疑控制器
 * @author fengwei.
 * @since 2014年5月31日 上午11:59:02.
 */
@Controller
@RequestMapping("/admin/teachers/answerquestion")
public class AnswerQuestionController implements IUserAware{
	private static final Logger logger = Logger.getLogger(AnswerQuestionController.class);
	private String current_user_id;
	//注入机构用户服务接口。
	@Resource
	private IAgencyUserService agencyUserService;
	//注入教师答疑主题服务接口。
	@Resource
	private IAnswerQuestionTopicService answerQuestionTopicService;
	//注入教师答疑明细服务接口。
	@Resource
	private IAnswerQuestionDetailService answerQuestionDetailService;
	/*
	 * 设置当前用户ID。
	 * @see com.examw.aware.IUserAware#setUserId(java.lang.String)
	 */
	@Override
	public void setUserId(String userId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("注入当前用户ID：%s", userId));
		this.current_user_id = userId;
	}
	/*
	 * 设置当前用户名称。
	 * @see com.examw.aware.IUserAware#setUserName(java.lang.String)
	 */
	@Override
	public void setUserName(String userName) {}
	/*
	 * 设置当前用户昵称。
	 * @see com.examw.aware.IUserAware#setUserNickName(java.lang.String)
	 */
	@Override
	public void setUserNickName(String userNickName) {}
	/**
	 * 加载列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHERS_ANSWERS + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.TEACHERS_ANSWERS + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.TEACHERS_ANSWERS + ":" + Right.DELETE);
		
		String current_agency_id = this.agencyUserService.loadAgencyIdByUser(this.current_user_id);
	    if(StringUtils.isEmpty(current_agency_id)){
	    	return "error/user_not_agency";
	    }
	    model.addAttribute("current_agency_id", current_agency_id);//当前机构ID
		
	    Map<String, String> statusMap = EnumMapUtils.createTreeMap();
	    for(Status status : Status.values()){
	    	statusMap.put(String.format("%d", status.getValue()), this.answerQuestionTopicService.loadStatusName(status.getValue()));
	    }
	    model.addAttribute("statusMap", statusMap);
	    
		return "teachers/answerquestion_list";
	}
	/**
	 * 加载编辑页面。
	 * @param agencyId
	 * 所属培训机构。
	 * @param model
	 * 数据绑定。
	 * @return
	 * 编辑页面地址。
	 */
	@RequiresPermissions({ModuleConstant.TEACHERS_ANSWERS + ":" + Right.UPDATE})
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(String agencyId,String topicId,String classId, Model model){
		if(logger.isDebugEnabled()) logger.debug("加载编辑页面...");
		model.addAttribute("current_agency_id", agencyId);
		model.addAttribute("current_topic_id", topicId);
		model.addAttribute("current_class_id", classId);
		
		Map<String, String> statusMap = EnumMapUtils.createTreeMap();
	    for(Status status : Status.values()){
	    	statusMap.put(String.format("%d", status.getValue()), this.answerQuestionTopicService.loadStatusName(status.getValue()));
	    }
	    model.addAttribute("statusMap", statusMap);
		
		return "teachers/answerquestion_edit";
	}
	/**
	 * 加载列表页面数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHERS_ANSWERS + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<AnswerQuestionTopicInfo> datagridTopic(AnswerQuestionTopicInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面数据...");
		return this.answerQuestionTopicService.datagrid(info);
	}
	/**
	 * 加载教师答疑主题下的明细。
	 * @param topicId
	 * 主题ID。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHERS_ANSWERS + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid/{topicId}", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<AnswerQuestionDetailInfo> datagridDetails(@PathVariable String topicId,AnswerQuestionDetailInfo info){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载教师答疑主题［%s］明细集合...", topicId));
		if(!StringUtils.isEmpty(topicId)) info.setTopicId(topicId);
		return this.answerQuestionDetailService.datagrid(info);
	}
	/**
	 * 更新教师答疑主题。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHERS_ANSWERS + ":" + Right.UPDATE})
	@RequestMapping(value = "/update/topic", method = RequestMethod.POST)
	@ResponseBody
	public Json updateTopic(AnswerQuestionTopicInfo info){
		if(logger.isDebugEnabled()) logger.debug("更新加载教师答疑主题数据...");
		Json result = new Json();
		try {
			result.setData(this.answerQuestionTopicService.update(info));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("更新数据时发生异常:%s", e.getMessage()), e);
		}
		return result;
	}
	/**
	 * 更新教师答疑主题明细。
	 * @param topicId
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHERS_ANSWERS + ":" + Right.UPDATE})
	@RequestMapping(value = "/update/topic/{topicId}", method = RequestMethod.POST)
	@ResponseBody
	public Json updateDetail(@PathVariable String topicId, AnswerQuestionDetailInfo info){
		if(logger.isDebugEnabled()) logger.debug("更新加载教师答疑明细数据...");
		Json result = new Json();
		try {
			info.setTopicId(topicId);
			result.setData(this.answerQuestionDetailService.update(info));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("更新数据时发生异常:%s", e.getMessage()), e);
		}
		return result;
	}
	/**
	 * 更新教师答疑主题状态。
	 * @param topicId
	 * 主题ID。
	 * @param status
	 * 状态。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHERS_ANSWERS + ":" + Right.UPDATE})
	@RequestMapping(value = "/update/{topicId}/status", method = RequestMethod.POST)
	@ResponseBody
	public Json updateStatus(@PathVariable String topicId,int status){
		Json result = new Json();
		try{
			this.answerQuestionTopicService.updateStatus(topicId, Status.conversion(status));
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
	@ResponseBody
	public Json delete(@RequestBody String[] ids){
		if(logger.isDebugEnabled()) logger.debug(String.format("删除数据:%s ...", Arrays.toString(ids)));
		Json result = new Json();
		try {
			this.answerQuestionTopicService.delete(ids);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("删除数据时发生异常:%s", e.getMessage()), e);
		}
		return result;
	}
}