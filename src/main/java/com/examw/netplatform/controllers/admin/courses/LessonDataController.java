package com.examw.netplatform.controllers.admin.courses;

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
import com.examw.netplatform.domain.admin.courses.HandoutMode;
import com.examw.netplatform.domain.admin.courses.SubjectHasClassView;
import com.examw.netplatform.domain.admin.courses.VideoMode;
import com.examw.netplatform.domain.admin.security.Right;
import com.examw.netplatform.model.EnumValueName;
import com.examw.netplatform.model.admin.courses.LessonInfo;
import com.examw.netplatform.service.admin.courses.ILessonService;
import com.examw.netplatform.support.UserAware;

/**
 * 课时资源数据控制器。
 * 
 * @author jeasonyoung
 * @since 2015年8月21日
 */
@RestController
@RequestMapping(value = "/admin/courses/lesson/data")
public class LessonDataController implements UserAware {
	private static final Logger logger = Logger.getLogger(LessonDataController.class);
	private List<EnumValueName> handoutModeList, videoModeList;
	private String current_agency_id;//,current_user_id;
	//注入课时资源数据接口。
	@Resource
	private ILessonService lessonService;
	/*
	 * 设置当前用户机构ID。
	 * @see com.examw.netplatform.support.UserAware#setAgencyId(java.lang.String)
	 */
	@Override
	public void setAgencyId(String agencyId) {
		logger.debug("注入设置当前用户机构ID..." + agencyId);
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
	 * 加载班级下的课时资源集合。
	 * @param classId
	 * 班级ID。
	 * @return
	 * 课时资源集合。
	 */
	@RequestMapping(value="/all")
	public List<LessonInfo> loadLessons(String classId){
		logger.debug(String.format("加载班级［%s］下课时资源集合...", classId));
		if(StringUtils.isBlank(classId)) return new ArrayList<LessonInfo>();
		return this.lessonService.loadLessons(classId);
	}
	/**
	 *  加载班级下的最大排序号。
	 * @param classId
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.COURSES_RESOURCES + ":" + Right.VIEW})
	@RequestMapping(value="/{classId}/order", method = RequestMethod.GET)
	public Integer loadMaxOrder(@PathVariable("classId")String classId){
		logger.debug(String.format("加载班级［%s］下的排序号...", classId));
		Integer max = this.lessonService.loadMaxOrder(classId);
		if(max == null) max = 0;
		return max + 1;
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
				this.handoutModeList.add(new EnumValueName(mode.getValue(), this.lessonService.loadHandoutModeName(mode.getValue())));
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
				this.videoModeList.add(new EnumValueName(mode.getValue(), this.lessonService.loadVideoModeName(mode.getValue())));
			}
			Collections.sort(this.videoModeList);
		}
		return this.videoModeList;
	}
	/**
	 * 加载存在班级的科目班级数据。
	 * @return
	 */
	@RequestMapping(value = "subject_class_views")
	public List<SubjectHasClassView> loadSubjectClassViews(){
		logger.debug("加载机构["+this.current_agency_id+"]下存在班级的科目班级数据...");
		return this.lessonService.loadSubjectClassViews(this.current_agency_id);
	}
	/**
	 * 查询数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.COURSES_RESOURCES + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	public DataGrid<LessonInfo> datagrid(LessonInfo info){
		logger.debug("加载列表数据...");
		info.setAgencyId(this.current_agency_id);
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
	public Json update(LessonInfo info){
		logger.debug("更新数据...");
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
	public Json delete(@RequestBody String[] ids){
		logger.debug(String.format("删除数据:%s...",Arrays.toString(ids)));
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