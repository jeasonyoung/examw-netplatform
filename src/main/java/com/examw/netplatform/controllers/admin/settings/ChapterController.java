package com.examw.netplatform.controllers.admin.settings;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.model.TreeNode;
import com.examw.netplatform.domain.admin.security.Right;
import com.examw.netplatform.model.admin.settings.ChapterInfo;
import com.examw.netplatform.service.admin.settings.IChapterService;
import com.examw.netplatform.support.EnumMapUtils;
import com.examw.service.Status;
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
	public String edit(String categoryId,String examId,String subjectId,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载编辑页面...");
		model.addAttribute("current_category_id", categoryId);
		model.addAttribute("current_exam_id", examId);
		model.addAttribute("current_subject_id", subjectId);
		
		Map<String, String> statusMap = EnumMapUtils.createTreeMap();
		for(Status status : Status.values()){
			statusMap.put(String.format("%d", status.getValue()), this.chapterService.loadStatusName(status.getValue()));
		}
		model.addAttribute("statusMap", statusMap);
		
		return "settings/chapter_edit";
	}
	/**
	 * 加载章节明细列表页面。
	 * @param parentChapterId
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_CHAPTER + ":" + Right.UPDATE})
	@RequestMapping(value = "/{parentChapterId}/detail/list", method = RequestMethod.GET)
	public String detailList(@PathVariable String parentChapterId,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载章节明细列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.SETTINGS_CHAPTER + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.SETTINGS_CHAPTER + ":" + Right.DELETE);
		
		model.addAttribute("current_parent_chapter_id", parentChapterId);
		return "settings/chapter_detail_list";
	}
	/**
	 * 加载章节明细编辑页面。
	 * @param parentChapterId
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_CHAPTER + ":" + Right.UPDATE})
	@RequestMapping(value = "/{parentChapterId}/detail/edit", method = RequestMethod.GET)
	public String detailEdit(@PathVariable String parentChapterId,String chapterId, Model model){
		model.addAttribute("current_chapter_id", chapterId);
		model.addAttribute("current_parent_chapter_id", parentChapterId);
		return "settings/chapter_detail_edit";
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
	 *  @param isSelf
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_CHAPTER + ":" + Right.VIEW})
	@RequestMapping(value = "/tree", method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public List<TreeNode> tree(String parentChapterId, String ignoreChapterId,Boolean isSelf){
		if(logger.isDebugEnabled()) logger.debug("加载章节树结构数据...");
		return this.chapterService.loadChapters(parentChapterId, ignoreChapterId, isSelf == null ? true : isSelf);
	}
	/**
	 * 加载排序号。
	 * @param parentChapterId
	 * 上级章节ID。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_CHAPTER + ":" + Right.VIEW})
	@RequestMapping(value = "/order", method = { RequestMethod.GET })
	@ResponseBody
	public Integer loadChapterOrder(String parentChapterId){
		if(logger.isDebugEnabled()) logger.debug("加载排序号...");
		Integer order = this.chapterService.loadMaxOrder(parentChapterId);
		if(order == null) order = 0;
		return order + 1;
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
			logger.error(String.format("更新数据发生异常:%s", e.getMessage()), e);
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
	public Json delete(@RequestBody String[] ids){
		if(logger.isDebugEnabled()) logger.debug(String.format("删除数据：%s...", Arrays.toString(ids)));
		Json result = new Json();
		try {
			this.chapterService.delete(ids);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("删除数据时发生异常：%s", e.getMessage()), e);
		}
		return result;
	}
}