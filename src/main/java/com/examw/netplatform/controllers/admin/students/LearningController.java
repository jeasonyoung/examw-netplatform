package com.examw.netplatform.controllers.admin.students;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.DataGrid;
import com.examw.netplatform.model.admin.students.LearningInfo;
import com.examw.netplatform.service.admin.students.ILearningService;

/**
 * 学习进度控制器
 * @author fengwei.
 * @since 2014年5月29日 下午2:23:41.
 */
@Controller
@RequestMapping("/admin/student/learning")
public class LearningController {
	//@Resource
	private ILearningService learningService;
	/**
	 * 获取列表页面。
	 * @return
	 */
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		return "student/learning_list";
	}
	/**
	 * 查询数据。
	 * @return
	 */
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<LearningInfo> datagrid(LearningInfo info){
		return this.learningService.datagrid(info);
	}
}
