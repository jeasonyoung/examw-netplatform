package com.examw.netplatform.controllers.api;

import javax.annotation.Resource;

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
	 * 验证用户登录。
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
		logger.debug("验证机构["+agencyId+"]用户["+username+","+pwd+"]登录...");
		final Json result = new Json();
		try {
			result.setSuccess(this.moblieApiService.authen(agencyId, username, pwd));
			if(!result.isSuccess()){
				result.setMsg("密码错误!");
			}
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
			result.setData(this.moblieApiService.lessonsByClass(classId));
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