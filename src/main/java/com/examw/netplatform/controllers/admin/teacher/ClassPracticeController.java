package com.examw.netplatform.controllers.admin.teacher;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.aware.IUserAware;
import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.netplatform.domain.admin.papers.Paper;
import com.examw.netplatform.model.admin.courses.LessonInfo;
import com.examw.netplatform.model.admin.papers.PaperInfo;
import com.examw.netplatform.service.admin.courses.ILessonService;

/**
 * 课堂练习控制器
 * @author fengwei.
 * @since 2014年6月11日 上午10:59:42.
 */
@Controller
@RequestMapping("/admin/teacher/practice")
public class ClassPracticeController implements IUserAware{
	private String userName;
	@Override
	public void setUserId(String userId) {
	}
	@Override
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Override
	public void setUserNickName(String userNickName) {
	}
	private static Logger logger = Logger.getLogger(ClassPracticeController.class);
	//@Resource
	private ILessonService lessonService;
	//@Resource
	//private IPaperService paperService;
	/**
	 * 课堂练习页面。
	 * @return
	 */
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(){
		return "teacher/classpractice_list";
	}
	/**
	 * 查询数据。
	 * @return
	 */
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<LessonInfo> datagrid(LessonInfo info){
		return this.lessonService.datagrid(info);
	}
	/**
	 * 试卷列表供选择
	 * @return
	 */
	@RequestMapping(value="/paperlist", method = RequestMethod.GET)
	public String paperList(String id,Model model){
		//提供subjectId,agencyId,paperType
//		ClassPlan data = this.lessonService.findClassPlan(id);
//		if(data == null){
//			model.addAttribute("ex",new RuntimeException("开班计划不存在"));
//				return "error/error";
//			}
	//	model.addAttribute("SUBJECT_ID", data.getSubject().getId());
		//model.addAttribute("AGENCY_ID", data.getAgency().getId());
		model.addAttribute("PAPER_TYPE",Paper.TYPE_CLASS_PRACTICE);	//练习试卷
		model.addAttribute("LESSON_ID",id);
		return "teacher/classpractice_choose";
	}
	/**
	 * 课堂练习试卷编辑页面。
	 * @return
	 */
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public String edit(String id,Model model){
		//提供subjectId,agencyId,paperType
//		ClassPlan data = this.lessonService.findClassPlan(id);
//		if(data == null){
//			model.addAttribute("ex",new RuntimeException("开班计划不存在"));
//			return "error/error";
//		}
		//model.addAttribute("SUBJECT_ID", data.getSubject().getId());
		//model.addAttribute("AGENCY_ID", data.getAgency().getId());
		model.addAttribute("PAPER_TYPE",Paper.TYPE_CLASS_PRACTICE);	//练习试卷
		model.addAttribute("LESSON_ID",id);
		return "teacher/classpractice_edit";
	}
	/**
	 * 更新试卷数据。
	 * @param info
	 * 更新源数据。
	 * @return
	 * 更新后数据。
	 */
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(PaperInfo info,String lessonId){
		Json result = new Json();
		try {
			 info.setCreateUsername(this.userName);
			 info.setCreateTime(new Date());
			 info.setLastTime(new Date());
			 info.setLastUsername(this.userName); 
			// LessonInfo resultData = this.lessonService.update(lessonId,info);
//			 if(resultData == null){
//				 result.setSuccess(false);
//				 result.setMsg("关联试卷失败,课时或试卷可能不存在");
//			 }else{
//				 result.setSuccess(true);
//				 result.setData(resultData);
//			 }
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新试卷数据发生异常", e);
		}
		return result;
	}
	/**
	 * 删除试卷数据。
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(String id){
		Json result = new Json();
		try {
			//this.lessonService.deletePaper(id.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除数据["+id+"]时发生异常:", e);
		}
		return result;
	}
}
