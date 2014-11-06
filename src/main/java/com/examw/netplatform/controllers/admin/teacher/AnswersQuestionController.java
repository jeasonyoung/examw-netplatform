package com.examw.netplatform.controllers.admin.teacher;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.aware.IUserAware;
import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.netplatform.domain.admin.AnswersQuestion;
import com.examw.netplatform.domain.admin.security.Right;
import com.examw.netplatform.model.admin.AnswersQuestionInfo;
import com.examw.netplatform.service.admin.IAnswersQuestionService;
/**
 * 课堂答疑控制器
 * @author fengwei.
 * @since 2014年5月31日 上午11:59:02.
 */
@Controller
@RequestMapping("/admin/teacher/answersquestion")
public class AnswersQuestionController implements IUserAware{
	private static Logger logger = Logger.getLogger(AnswersQuestionController.class);
	private String userId;
	//@Resource
	private IAnswersQuestionService answersQuestionService;
	@Override
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Override
	public void setUserName(String userName) {
	}
	@Override
	public void setUserNickName(String userNickName) {
	}
	/**
	 * 获取列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHER_ANSWERS + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		model.addAttribute("PER_UPDATE", ModuleConstant.TEACHER_ANSWERS + ":" + Right.UPDATE);
		
		model.addAttribute("STATUS_ASK", this.answersQuestionService.loadStatusName(AnswersQuestion.STATUS_ASK));
		model.addAttribute("STATUS_ANSWER", this.answersQuestionService.loadStatusName(AnswersQuestion.STATUS_ANSWER));
		model.addAttribute("STATUS_END", this.answersQuestionService.loadStatusName(AnswersQuestion.STATUS_END));
		return "teacher/answersquestion_list";
	}
	/**
	 * 获取编辑页面。
	 * @param agencyId
	 * 所属培训机构。
	 * @param model
	 * 数据绑定。
	 * @return
	 * 编辑页面地址。
	 */
	@RequiresPermissions({ModuleConstant.TEACHER_ANSWERS + ":" + Right.UPDATE})
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(String id,Model model){
		model.addAttribute("QUESTION",this.answersQuestionService.findById(id));
		model.addAttribute("STATUS_ASK", this.answersQuestionService.loadStatusName(AnswersQuestion.STATUS_ASK));
		model.addAttribute("STATUS_ANSWER", this.answersQuestionService.loadStatusName(AnswersQuestion.STATUS_ANSWER));
		model.addAttribute("STATUS_END", this.answersQuestionService.loadStatusName(AnswersQuestion.STATUS_END));
		return "teacher/answersquestion_edit";
	}
	/**
	 * 查询数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.TEACHER_ANSWERS + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<AnswersQuestionInfo> datagrid(AnswersQuestionInfo info){
		return this.answersQuestionService.datagrid(info);
	}
	/**
	 * 更新数据。
	 * @param info
	 * 更新源数据。
	 * @return
	 * 更新后数据。
	 */
	@RequiresPermissions({ModuleConstant.TEACHER_ANSWERS + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(AnswersQuestionInfo info, HttpServletRequest request){
		Json result = new Json();
		try {
			 info.setUserId(userId);
			 result.setData(this.answersQuestionService.update(info));
			 result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新用户数据发生异常", e);
		}
		return result;
	}
}
