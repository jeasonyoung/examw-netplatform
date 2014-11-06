package com.examw.netplatform.controllers.admin.papers;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.aware.IUserAware;
import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.netplatform.domain.admin.security.Right;
import com.examw.netplatform.model.admin.papers.NoteInfo;
import com.examw.netplatform.service.admin.papers.INoteService;

/**
 * 试卷笔记控制器
 * 
 * @author fengwei.
 * @since 2014年5月13日 下午12:03:01.
 */
@Controller
@RequestMapping(value = "/admin/papers/note")
public class NoteController implements IUserAware {
	private static final Logger logger = Logger.getLogger(PaperController.class);
	//试卷笔记服务接口。
	//@Resource
	private INoteService noteService;
	private String userId,userName;
	/*
	 * 设置当前用户ID。
	 * @see com.examw.aware.IUserAware#setUserId(java.lang.String)
	 */
	@Override
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/*
	 * 设置当前用户名称。
	 * @see com.examw.aware.IUserAware#setUserName(java.lang.String)
	 */
	@Override
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/*
	 * 设置当前用户昵称。
	 * @see com.examw.aware.IUserAware#setUserNickName(java.lang.String)
	 */
	@Override
	public void setUserNickName(String userNickName){}
	/**
	 * 试卷列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PAPERS_NOTE + ":" + Right.VIEW})
	@RequestMapping(value = { "", "/list" }, method = RequestMethod.GET)
	public String list(Model model) {
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.PAPERS_NOTE + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.PAPERS_NOTE + ":" + Right.DELETE);
		return "papers/note_list";
	}
	/**
	 * 试卷编辑页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PAPERS_NOTE + ":" + Right.UPDATE})
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(String paperId,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载编辑页面...");
		model.addAttribute("CURRENT_PAPER_ID", StringUtils.isEmpty(paperId) ? "": paperId);
		return "papers/note_edit";
	}
	/**
	 * 查询数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PAPERS_NOTE + ":" + Right.VIEW})
	@RequestMapping(value = "/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<NoteInfo> datagrid(NoteInfo info) {
		if(logger.isDebugEnabled()) logger.debug("加载列表数据...");
		return this.noteService.datagrid(info);
	}
	/**
	 * 数据更新。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PAPERS_NOTE + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(NoteInfo info){
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		Json result = new Json();
		try {
			info.setUserId(this.userId);
			info.setUsername(this.userName);
			result.setData(this.noteService.update(info));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新试卷笔记数据发生异常", e);
		}
		return result;
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PAPERS_NOTE + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(String id){
		if(logger.isDebugEnabled()) logger.debug("删除数据［"+id+"］...");
		Json result = new Json();
		try {
			this.noteService.delete(id.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除数据["+id+"]时发生异常:", e);
		}
		return result;
	}
}