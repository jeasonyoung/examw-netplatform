package com.examw.netplatform.controllers.admin.settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.examw.model.TreeNode;
import com.examw.netplatform.domain.admin.security.Right;
import com.examw.netplatform.domain.admin.settings.Chapter;
import com.examw.netplatform.model.admin.settings.CatalogInfo;
import com.examw.netplatform.model.admin.settings.ChapterInfo;
import com.examw.netplatform.model.admin.settings.ExamInfo;
import com.examw.netplatform.service.admin.settings.IChapterService;
import com.examw.netplatform.service.admin.settings.IExamService;
import com.examw.netplatform.service.admin.settings.ISubjectService;
/**
 * 章节控制器
 * @author fengwei.
 * @since 2014年4月30日 下午4:25:13.
 * 
 * 重构并添加权限控制。
 * @author yangyong.
 * @since 2014-05-24.
 */
@Controller
@RequestMapping(value = "/admin/settings/chapter")
public class ChapterController {
	private static final Logger logger  = Logger.getLogger(ChapterController.class);
	//章节服务接口
	@Resource
	private IChapterService chapterService;
	//考试服务接口
	@Resource
	private IExamService examService;
	//考试科目服务接口
	@Resource
	private ISubjectService subjectService;
	/**
	 * 章节列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_CHAPTER + ":" + Right.VIEW})
	@RequestMapping(value ={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.SETTINGS_CHAPTER + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.SETTINGS_CHAPTER + ":" + Right.DELETE);
		return "settings/chapter_list";
	}
	/**
	 * 章节编辑页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_CHAPTER + ":" + Right.UPDATE})
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(String catalogId,String examId,String subjectId,String chapterId, Model model){
		if(logger.isDebugEnabled()) logger.debug("加载编辑页面...");
		if(!StringUtils.isEmpty(examId)){
			CatalogInfo catalogInfo = this.examService.loadCatalog(examId);
			if(catalogInfo != null) catalogId = catalogInfo.getId();
		}else if(!StringUtils.isEmpty(subjectId)){
			ExamInfo examInfo = this.subjectService.loadExam(subjectId);
			if(examInfo != null){
				catalogId = examInfo.getCatalogId();
				examId = examInfo.getId();
			}
		}
		model.addAttribute("CURRENT_CATALOG_ID", StringUtils.isEmpty(catalogId) ? "" : catalogId);
		model.addAttribute("CURRENT_EXAM_ID", StringUtils.isEmpty(examId) ? "" : examId);
		model.addAttribute("CURRENT_SUBJECT_ID", StringUtils.isEmpty(subjectId) ? "" : subjectId);
		model.addAttribute("CURRENT_CHAPTER_ID", StringUtils.isEmpty(chapterId) ? "" : chapterId);
		return "settings/chapter_edit";
	}
	/**
	 * 章节树形列表数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_CHAPTER + ":" + Right.VIEW})
	@RequestMapping(value = "/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<ChapterInfo> datagrid(ChapterInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载列表数据...");
		 return this.chapterService.datagrid(info);
	}
	/**
	 * 章节树行结构数据。
	 * @param chapterId
	 *  需忽略的章节子节点。
	 * @return
	 */
	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	@ResponseBody
	public List<TreeNode> tree(String ignoreChapterId){
		if(logger.isDebugEnabled()) logger.debug("加载章节树行结构数据...");
		List<TreeNode> list = new ArrayList<>();
		List<Chapter> chapters = this.chapterService.loadChapters(ignoreChapterId);
		if(chapters != null && chapters.size() > 0){
			for(Chapter data : chapters){
				TreeNode e = this.createTreeNode(data,ignoreChapterId);
				if(e != null) list.add(e);
			}
		}
		return list;
	}
	//创建树结构。
	private TreeNode createTreeNode(Chapter data, String ignoreChapterId){
		if(data == null || data.getId().equalsIgnoreCase(ignoreChapterId)) return null;
		TreeNode node = new TreeNode();
		node.setId(data.getId());
		node.setText(data.getName());
		Map<String, Object> attributes = new HashMap<>();
		if(data.getSubject() != null){
			attributes.put("subjectId", data.getSubject().getId());
			if(data.getSubject().getExam() != null){
				attributes.put("examId", data.getSubject().getExam().getId());
				if(data.getSubject().getExam().getCatalog() != null){
					attributes.put("catalogId", data.getSubject().getExam().getCatalog().getId());
				}
			}
		}
		node.setAttributes(attributes);
		if(data.getChildren() != null && data.getChildren().size() > 0){
			List<TreeNode> list = new ArrayList<>();
			for(Chapter c : data.getChildren()){
				TreeNode t = this.createTreeNode(c,ignoreChapterId);
				 if(t != null) list.add(t);
			}
			node.setChildren(list);
		}
		return node;
	}
	/**
	 * 更新数据。
	 * @param info
	 * 更新源数据。
	 * @return
	 * 更新后数据。
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_CHAPTER + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(ChapterInfo info){
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		Json result = new Json();
		try {
			result.setData(this.chapterService.update(info));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新章节数据发生异常", e);
		}
		return result;
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_CHAPTER + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(String id){
		if(logger.isDebugEnabled()) logger.debug("删除数据...");
		Json result = new Json();
		try {
			this.chapterService.delete(id.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除数据["+id+"]时发生异常:", e);
		}
		return result;
	}
}