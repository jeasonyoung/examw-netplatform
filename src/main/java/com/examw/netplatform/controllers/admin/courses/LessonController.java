package com.examw.netplatform.controllers.admin.courses;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.aware.IUserAware;
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
public class LessonController implements IUserAware {
	private static final Logger logger  = Logger.getLogger(LessonController.class);
	//private String current_user_id;
	//注入课时资源数据接口。
	@Resource
	private ILessonService lessonService;
//	//注入机构用户服务接口。
//	@Resource
//	private IAgencyUserService agencyUserService;
	/*
	 * 设置当前用户ID。
	 * @see com.examw.aware.IUserAware#setUserId(java.lang.String)
	 */
	@Override
	public void setUserId(String userId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("注入当前用户ID:%s", userId));
		//this.current_user_id = userId;
	}
	/*
	 * 设置当前用户名称。
	 * @see com.examw.aware.IUserAware#setUserName(java.lang.String)
	 */
	@Override
	public void setUserName(String userName){}
	/*
	 * 设置当前用户昵称。
	 * @see com.examw.aware.IUserAware#setUserNickName(java.lang.String)
	 */
	@Override
	public void setUserNickName(String userNickName) {}
	/**
	 * 列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.COURSES_RESOURCES + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(String classId,String subjectId, Model model){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.COURSES_RESOURCES + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.COURSES_RESOURCES + ":" + Right.DELETE);
		
//		String current_agency_id = this.agencyUserService.loadAgencyIdByUser(this.current_user_id);
//	    if(StringUtils.isEmpty(current_agency_id)){
//	    	return "error/user_not_agency";
//	    }
//	    model.addAttribute("current_agency_id", current_agency_id);//当前机构ID
		model.addAttribute("current_class_id", classId);//当前班级ID
		model.addAttribute("current_subject_id", subjectId);//当前科目ID
		return "courses/lesson_list";
	}
	/**
	 *  加载班级下的最大排序号。
	 * @param classId
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.COURSES_RESOURCES + ":" + Right.VIEW})
	@RequestMapping(value="/order", method = RequestMethod.GET)
	@ResponseBody
	public Integer loadMaxOrder(String classId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载班级［%s］下的排序号...", classId));
		Integer max = this.lessonService.loadMaxOrder(classId);
		if(max == null) max = 0;
		return max + 1;
	}
	/**
	 * 课时编辑页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.COURSES_RESOURCES + ":" + Right.UPDATE})
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public String edit(String agencyId,String subjectId, Model model){
		if(logger.isDebugEnabled()) logger.debug("加载编辑页面...");
		model.addAttribute("current_agency_id", agencyId);//当前培训机构ID。
		model.addAttribute("current_subject_id", subjectId);//当前科目ID。
		
//		Map<String, String> handoutModeMap = EnumMapUtils.createTreeMap(), videoModeMap = EnumMapUtils.createTreeMap();
//		for(HandoutMode mode : HandoutMode.values()){
//			handoutModeMap.put(String.format("%d", mode.getValue()), this.lessonService.loadHandoutModeName(mode.getValue()));
//		}
//		model.addAttribute("handoutModeMap", handoutModeMap);
//		
//		for(VideoMode mode : VideoMode.values()){
//			videoModeMap.put(String.format("%d", mode.getValue()), this.lessonService.loadVideoModeName(mode.getValue()));
//		}
//		model.addAttribute("videoModeMap", videoModeMap);
		
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
	 * 加载班级下的课时资源集合。
	 * @param classId
	 * 班级ID。
	 * @return
	 * 课时资源集合。
	 */
	@RequiresPermissions({ModuleConstant.COURSES_RESOURCES + ":" + Right.VIEW})
	@RequestMapping(value="/all/{classId}", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public List<LessonInfo> loadLessons(@PathVariable String classId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载班级［%s］下课时资源集合...", classId));
		return this.lessonService.loadLessons(classId);
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
	public Json delete(@RequestBody String[] ids){
		if(logger.isDebugEnabled()) logger.debug(String.format("删除数据:%s...",Arrays.toString(ids)));
		Json result = new Json();
		try {
			this.lessonService.delete(ids);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("删除数据时发生异常:", e.getMessage()), e);
		}
		return result;
	}
}