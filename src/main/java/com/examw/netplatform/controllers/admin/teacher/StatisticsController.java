package com.examw.netplatform.controllers.admin.teacher;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.aware.IUserAware;
import com.examw.model.DataGrid;
import com.examw.netplatform.model.admin.teacher.StatisticsInfo;
import com.examw.netplatform.service.admin.teacher.IAnswersQuestionStatisticsService;

/**
 * 答疑统计控制器
 * @author fengwei.
 * @since 2014年6月17日 下午4:47:16.
 */
@Controller
@RequestMapping("/admin/teacher/statistics")
public class StatisticsController implements IUserAware{
	private String userId;
	//private static Logger logger = Logger.getLogger(StatisticsController.class);
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
	@Resource
	private IAnswersQuestionStatisticsService statisticsService;
	
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		return "teacher/answersquestion_statistics";
	}
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<StatisticsInfo> datagrid(StatisticsInfo info){
		if(info == null) info = new StatisticsInfo();
		info.setUserId(userId);
		return this.statisticsService.datagrid(info);
	}
}
