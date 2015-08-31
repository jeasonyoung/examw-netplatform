package com.examw.netplatform.controllers.api;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.examw.model.Json;
import com.examw.netplatform.domain.admin.students.BaseLearning;
import com.examw.netplatform.service.MobileAPIService;
/**
 * 移动API控制器。
 * 
 * @author jeasonyoung
 * @since 2015年8月28日
 */
@RestController
@RequestMapping("/api/m")
public class MobileController {
	private static final Logger logger = Logger.getLogger(MobileController.class);
	//注入API服务接口。
	@Resource
	private MobileAPIService moblieApiService;
	/**
	 * 验证学员登录。
	 * @param agencyId
	 * 所属机构ID。
	 * @param username
	 * 用户名。
	 * @param pwd
	 * 密码[加密方式:md5(md5(agencyId + username) + password)]。
	 * @return
	 */
	@RequestMapping(value = "/login")
	public Json userLogin(String agencyId, String username, String pwd){
		logger.debug("验证机构["+agencyId+"]学员["+username+","+pwd+"]登录...");
		final Json result = new Json();
		try {
			if(StringUtils.isBlank(agencyId)) throw new Exception("学员所属机构为空!");
			if(StringUtils.isBlank(username)) throw new Exception("学员账号为空!");
			if(StringUtils.isBlank(pwd)) throw new Exception("学员账号密码为空!");
			result.setData(this.moblieApiService.authen(agencyId, username, pwd));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
		}
		return result;
	}
	/**
	 * 加载用户订单套餐/班级集合。
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/courses/{userId}")
	public Json loadUserOrders(@PathVariable("userId") String userId){
		logger.debug("加载用户["+userId+"]订单套餐/班级集合...");
		final Json result = new Json();
		try {
			result.setData(this.moblieApiService.ordersByUser(userId));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
		}
		return result;
	}
	/**
	 * 加载班级下课程资源集合。
	 * @param classId
	 * @return
	 */
	@RequestMapping(value = "/lessons/{classId}")
	public Json loadClassLessons(String classId){
		logger.debug("加载班级["+classId+"]下课程资源集合...");
		final Json result = new Json();
		try {
			result.setData(this.moblieApiService.loadLessonsByClass(classId));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
		}
		return result;
	}
	/**
	 * 加载班级下免费课程资源集合。
	 * @param classId
	 * @return
	 */
	@RequestMapping(value = "/lessons/{classId}/free")
	public Json loadClassFreeLessons(@PathVariable("classId") String classId){
		logger.debug("加载班级["+classId+"]下免费课程资源集合...");
		final Json result = new Json();
		try {
			result.setData(this.moblieApiService.loadFreeLessonsByClass(classId));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
		}
		return result;
	}
	/**
	 * 加载考试类别集合。
	 * @return
	 */
	@RequestMapping(value = "/categories")
	public Json getCategories(){
		logger.debug("加载考试类别集合...");
		final Json result = new Json();
		try {
			result.setData(this.moblieApiService.getCategories());
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
		}
		return result;
	}
	/**
	 * 加载考试类别下的考试集合。
	 * @param categoryId
	 * 考试类别ID。
	 * @return
	 */
	@RequestMapping(value = "/exams/{categoryId}")
	public Json loadExamsByCategory(@PathVariable("categoryId") String categoryId){
		logger.debug("加载考试类别["+categoryId+"]下的考试集合...");
		final Json result = new Json();
		try {
			result.setData(this.moblieApiService.loadExamsByCategory(categoryId));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
		}
		return result;
	}
	/**
	 * 加载机构考试下的套餐/班级集合。
	 * @param agencyId
	 * 机构ID。
	 * @param examId
	 * 考试ID。
	 * @return
	 */
	@RequestMapping(value = "/packages/{agencyId}/{examId}")
	public Json loadPackageAndClassesByAgencyExam(@PathVariable("agencyId")String agencyId, @PathVariable("examId")String examId){
		logger.debug("加载机构["+agencyId+"]考试["+examId+"]下的套餐/班级集合...");
		final Json result = new Json();
		try {
			result.setData(this.moblieApiService.loadPackageAndClassesByAgencyExam(agencyId, examId));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
		}
		return result;
	}
	/**
	 * 更新学习记录。
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/learning", method = RequestMethod.POST)
	public Json updateLearning(@RequestBody BaseLearning data){
		logger.debug("更新学习记录...");
		final Json result = new Json();
		try {
			this.moblieApiService.pushLearning(data);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
		}
		return result;
	}
}