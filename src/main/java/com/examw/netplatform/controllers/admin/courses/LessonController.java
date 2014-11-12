package com.examw.netplatform.controllers.admin.courses;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.netplatform.domain.admin.security.Right;
import com.examw.netplatform.model.admin.courses.LessonInfo;
import com.examw.netplatform.service.admin.courses.ILessonService;
/**
 * 课时控制器
 * @author fengwei.
 * @since 2014年5月22日 下午1:58:48.
 */
@Controller
@RequestMapping(value = "/admin/courses/lesson")
public class LessonController {
	private static final Logger logger  = Logger.getLogger(LessonController.class);
	//课时资源数据接口。
	//@Resource
	private ILessonService lessonService;
	/**
	 * 列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.COURSES_RESOURCES + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.COURSES_RESOURCES + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.COURSES_RESOURCES + ":" + Right.DELETE);
		return "courses/lesson_list";
	}
	/**
	 * 课时编辑页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.COURSES_RESOURCES + ":" + Right.UPDATE})
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public String edit(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载编辑页面...");
//		model.addAttribute("VIDEO_NOTFREE_VALUE", Lesson.VIDEO_NOTFREE);
//		model.addAttribute("VIDEO_NOTFREE_NAME", this.lessonService.loadVideoModeName(Lesson.VIDEO_NOTFREE));
//		model.addAttribute("VIDEO_FREE_VALUE", Lesson.VIDEO_FREE);
//		model.addAttribute("VIDEO_FREE_NAME", this.lessonService.loadVideoModeName(Lesson.VIDEO_FREE));
		return "courses/lesson_edit";
	}
	/**
	 * 查询数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.COURSES_RESOURCES + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<LessonInfo> datagrid(LessonInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载列表数据...");
		return this.lessonService.datagrid(info);
	}
	/**
	 * 更新数据。
	 * @param info
	 * 更新源数据。
	 * @return
	 * 更新后数据。
	 */
	@RequiresPermissions({ModuleConstant.COURSES_RESOURCES + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(LessonInfo info){
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		Json result = new Json();
		try {
			result.setData(this.lessonService.update(info));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新考试类型数据发生异常", e);
		}
		return result;
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.COURSES_RESOURCES + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(String id){
		if(logger.isDebugEnabled()) logger.debug("删除数据［"+ id +"］...");
		Json result = new Json();
		try {
			this.lessonService.delete(id.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除数据["+id+"]时发生异常:", e);
		}
		return result;
	}
}