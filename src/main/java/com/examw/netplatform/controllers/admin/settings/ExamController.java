package com.examw.netplatform.controllers.admin.settings;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.netplatform.domain.admin.security.Right;
import com.examw.netplatform.model.admin.settings.ExamInfo;
import com.examw.netplatform.service.admin.settings.IExamService;
/**
 * 考试控制器。
 * @author fengwei.
 * @since 2014-04-29.
 * 
 * @author yangyong
 * @since 2014-05-22.
 * 重构代码：
 * 1.优化结构；
 * 2.添加权限控制；
 */
@Controller
@RequestMapping(value = "/admin/settings/exam")
public class ExamController {
	private static final Logger logger  = Logger.getLogger(ExamController.class);
	//考试数据接口
	@Resource
	private IExamService examService;
	/**
	 * 考试列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_EXAM + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.SETTINGS_EXAM + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.SETTINGS_EXAM + ":" + Right.DELETE);
		return "settings/exam_list";
	}
	/**
	 * 考试编辑页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_EXAM + ":" + Right.UPDATE})
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public String edit(String catalogId, Model model){
		if(logger.isDebugEnabled()) logger.debug("加载编辑页面...");
		model.addAttribute("CURRENT_CATALOG_ID", StringUtils.isEmpty(catalogId) ? "" : catalogId);
		return "settings/exam_edit";
	}
	/**
	 * 查询数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_EXAM + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<ExamInfo> datagrid(ExamInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载列表数据...");
		return this.examService.datagrid(info);
	}
	/**
	 * 返回考试类别下的所有考试
	 * @return
	 */
	@RequestMapping(value={"/all"}, method = RequestMethod.POST)
	@ResponseBody
	public List<ExamInfo> all(final String catalogId){
		if(logger.isDebugEnabled()) logger.debug("加载考试类别下的所有考试数据...");
		if(StringUtils.isEmpty(catalogId)) return new ArrayList<ExamInfo>();
		 return this.examService.datagrid(new ExamInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public Integer getPage(){return null;}
			@Override
			public Integer getRows(){return null;}
			@Override
			public String getCatalogId(){return catalogId;}
		 }).getRows();
	}
	/**
	 * 更新数据。
	 * @param info
	 * 更新源数据。
	 * @return
	 * 更新后数据。
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_EXAM + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(ExamInfo info){
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		Json result = new Json();
		try {
			result.setData(this.examService.update(info));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新考试数据发生异常", e);
		}
		return result;
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_EXAM + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(String id){
		if(logger.isDebugEnabled()) logger.debug("删除数据...");
		Json result = new Json();
		try {
			this.examService.delete(id.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除数据["+id+"]时发生异常:", e);
		}
		return result;
	}
}