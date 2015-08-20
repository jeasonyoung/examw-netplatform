package com.examw.netplatform.controllers.admin.settings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.examw.model.Json;
import com.examw.netplatform.domain.admin.security.Right;
import com.examw.netplatform.model.EnumValueName;
import com.examw.netplatform.model.admin.settings.ChapterInfo;
import com.examw.netplatform.service.admin.settings.IChapterService;
import com.examw.service.Status;

/**
 * 章节数据控制器。
 * 
 * @author jeasonyoung
 * @since 2015年8月20日
 */
@RestController
@RequestMapping(value = "/admin/settings/chapter/data")
public class ChapterDataController {
	private static final Logger logger = Logger.getLogger(ChapterDataController.class);
	private List<EnumValueName> statusList;
	//注入章节服务接口
	@Resource
	private IChapterService chapterService;
	/**
	 * 加载科目下全部的章节数据。
	 * @param subjectId
	 * 所属科目ID。
	 * @param ignoreId
	 * 应忽略的章节ID及其子孙。
	 * @return
	 */
	@RequestMapping(value = "/all")
	public List<ChapterInfo> getAllChapters(String subjectId, String ignoreId){
		logger.debug("加载科目["+subjectId+"]下全部的章节[应忽略的章节["+ignoreId+"]及其子孙]数据...");
		if(StringUtils.isBlank(subjectId)) return new ArrayList<ChapterInfo>();
		return this.chapterService.loadAllChapters(subjectId, ignoreId);
	}
	/**
	 * 加载排序号。
	 * @param parentChapterId
	 * 上级章节ID。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_CHAPTER + ":" + Right.VIEW})
	@RequestMapping(value = "/order", method = { RequestMethod.GET })
	public Integer loadChapterOrder(String pid){
		logger.debug("加载章节["+pid+"]排序号...");
		Integer order = this.chapterService.loadMaxOrder(pid);
		if(order == null) order = 0;
		return order + 1;
	}
	/**
	 * 加载章节状态。
	 * @return
	 */
	@RequestMapping(value = "/chapterstatus")
	public List<EnumValueName> getChapterStatus(){
		logger.debug("加载章节状态...");
		if(this.statusList == null || this.statusList.size() == 0){
			this.statusList = new ArrayList<EnumValueName>();
			for(Status s : Status.values()){
				this.statusList.add(new EnumValueName(s.getValue(), this.chapterService.loadStatusName(s.getValue())));
			}
			Collections.sort(this.statusList);
		}
		return this.statusList;
	}
	/**
	 * 章节树形列表数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_CHAPTER + ":" + Right.VIEW})
	@RequestMapping(value = "/datagrid", method = RequestMethod.POST)
	public List<ChapterInfo> datagrid(String subjectId){
		logger.debug("加载列表数据...");
		if(StringUtils.isBlank(subjectId)) return new ArrayList<ChapterInfo>();
		return this.chapterService.loadAllChapters(subjectId);
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
	public Json delete(@RequestBody String[] ids){
		logger.debug(String.format("删除数据：%s...", Arrays.toString(ids)));
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