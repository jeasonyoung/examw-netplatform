package com.examw.netplatform.controllers.admin.papers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.aware.IUserAware;
import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.model.TreeNode;
import com.examw.netplatform.domain.admin.papers.Item;
import com.examw.netplatform.domain.admin.security.Right;
import com.examw.netplatform.model.admin.papers.PaperInfo;
import com.examw.netplatform.model.admin.papers.StructureInfo;
import com.examw.netplatform.model.admin.papers.StructureItemInfo;
import com.examw.netplatform.service.admin.papers.IItemService;
import com.examw.netplatform.service.admin.papers.IPaperService;
/**
 * 试卷控制器
 * @author fengwei.
 * @since 2014年5月4日 下午3:15:18.
 */
@Controller
@RequestMapping(value = "/admin/papers/paper")
public class PaperController implements IUserAware{
	private static final Logger logger  = Logger.getLogger(PaperController.class);
	private String userName;
	//试卷服务接口。
	//@Resource
	private IPaperService paperService; 
	//科目服务接口。
	//@Resource
	//private ISubjectService subjectService;
	//考试服务接口。
	//@Resource
	//private IExamService examService;
	//试题服务接口。
	//@Resource
	private IItemService itemService;
	/*
	 * 注入当前用户id。
	 * @see com.examw.aware.IUserAware#setUserId(java.lang.String)
	 */
	@Override
	public void setUserId(String userId) {}
	/*
	 * 注入当前用户名称。
	 * @see com.examw.aware.IUserAware#setUserName(java.lang.String)
	 */
	@Override
	public void setUserName(String userName) { 
		this.userName = userName;
	}
	/*
	 * 注入当前用户昵称。
	 * @see com.examw.aware.IUserAware#setUserNickName(java.lang.String)
	 */
	@Override
	public void setUserNickName(String userNickName){}
	/**
	 * 试卷列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PAPERS_PAPER + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.PAPERS_PAPER + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.PAPERS_PAPER + ":" + Right.DELETE);
		return "papers/paper_list";
	}
	/**
	 * 试卷编辑页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PAPERS_PAPER + ":" + Right.UPDATE})
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public String edit(String catalogId,String examId,String subjectId,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载编辑页面...");
		if(!StringUtils.isEmpty(subjectId)){
		//  ExamInfo examInfo =	 this.subjectService.loadExam(subjectId);
		  //if(examInfo != null){
//			  catalogId = examInfo.getCatalogId();
//			  examId = examInfo.getId();
		  //}
		}else if(!StringUtils.isEmpty(examId)){
//			 CatalogInfo catalogInfo = this.examService.loadCatalog(examId);
//			 if(catalogInfo != null){
//				 catalogId = catalogInfo.getId();
//			 }
		}
		model.addAttribute("CURRENT_CATALOG_ID", catalogId);
		model.addAttribute("CURRENT_EXAM_ID", examId);
		model.addAttribute("CURRENT_SUBJECT_ID", subjectId);
		
		model.addAttribute("PAPER_TYPES", this.paperService.loadTypes());
		return "papers/paper_edit";
	}
	/**
	 * 添加试卷题目。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PAPERS_PAPER + ":" + Right.UPDATE})
	@RequestMapping(value="/add/{paperId}", method = RequestMethod.GET)
	public String add(@PathVariable String paperId, Model model){
		if(logger.isDebugEnabled()) logger.debug("加载试卷题目页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.PAPERS_PAPER + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.PAPERS_PAPER + ":" + Right.DELETE);
		
		model.addAttribute("CURRENT_PAPER_ID", paperId);
		return "papers/paper_add";
	}
	/**
	 * 查询试卷数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PAPERS_PAPER + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<PaperInfo> datagrid(PaperInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载列表数据...");
		return this.paperService.datagrid(info);
	}
	/**
	 * 获取全部试卷信息。
	 * @return
	 */
	@RequestMapping(value="/all", method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public List<PaperInfo> all(){
		if(logger.isDebugEnabled()) logger.debug("加载全部试卷数据...");
		return this.paperService.datagrid(new PaperInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public String getSort(){return "name";}
			@Override
			public String getOrder(){return "";}
		}).getRows();
	}
	/**
	 * 更新试卷数据。
	 * @param info
	 * 更新源数据。
	 * @return
	 * 更新后数据。
	 */
	@RequiresPermissions({ModuleConstant.PAPERS_PAPER + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(PaperInfo info){
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		Json result = new Json();
		try {
			 info.setCreateUsername(this.userName);
			 info.setCreateTime(new Date());
			 info.setLastTime(new Date());
			 info.setLastUsername(this.userName); 

			 result.setData(this.paperService.update(info));
			 result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新试卷数据发生异常", e);
		}
		return result;
	}
	/**
	 * 删除试卷数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PAPERS_PAPER + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(String id){
		if(logger.isDebugEnabled()) logger.debug("删除数据...");
		Json result = new Json();
		try {
			this.paperService.delete(id.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除数据["+id+"]时发生异常:", e);
		}
		return result;
	}
	/**
	 * 加载试卷。
	 * @param paperId
	 * 试卷ID。
	 * @return
	 * 试卷预览对象。
	 */
	@RequiresPermissions({ModuleConstant.PAPERS_PAPER + ":" + Right.VIEW})
	@RequestMapping(value = "/preview/{paperId}", method = {RequestMethod.GET, RequestMethod.POST})
	public String loadPaperPreview(@PathVariable String paperId,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载预览页面...");
		model.addAttribute("Page", this.paperService.loadPaperPreview(paperId));
		
		model.addAttribute("ANSWER_JUDGE_RIGTH", Item.ANSWER_JUDGE_RIGTH);
		model.addAttribute("ANSWER_JUDGE_RIGTH_NAME", this.itemService.loadJudgeTypeName(Item.ANSWER_JUDGE_RIGTH));
		
		model.addAttribute("ANSWER_JUDGE_WRONG", Item.ANSWER_JUDGE_WRONG);
		model.addAttribute("ANSWER_JUDGE_WRONG_NAME", this.itemService.loadJudgeTypeName(Item.ANSWER_JUDGE_WRONG));
		return "papers/paper_preview";
	}
	/**
	 * 获取试卷的结构集合
	 * @param paperId
	 * @return
	 */
	@RequestMapping(value="/structures/tree/{paperId}", method = RequestMethod.POST)
	@ResponseBody
	public List<TreeNode> findStructure(@PathVariable String paperId, String ignoreStructureId) {
		if(logger.isDebugEnabled()) logger.debug("加载试卷结构数据...");
		List<TreeNode> treeNodes = new ArrayList<>();
		
		List<StructureInfo> list = this.paperService.loadStructure(paperId);
		if(list == null || list.size() == 0) return treeNodes;
		
		for(StructureInfo info : list){
			TreeNode tn = this.createStructureNode(info,ignoreStructureId);
			if(tn != null) treeNodes.add(tn);
		}
		
		return treeNodes;
	}
	/**
	 * 创建试卷结构树。
	 * @param info
	 * @return
	 */
	private TreeNode createStructureNode(StructureInfo info,String ignoreStructureId){
		if(info == null || StringUtils.isEmpty(info.getId()) || (!StringUtils.isEmpty(ignoreStructureId) && info.getId().equalsIgnoreCase(ignoreStructureId))) return null;
		
		TreeNode node = new TreeNode();
		node.setId(info.getId());
		String typeName = this.itemService.loadTypes().get(info.getType().toString());
		node.setText(info.getTitle() + "[" + typeName  + "," +  (info.getTotalScore() == null ? 0 : info.getTotalScore().intValue()) + "]");
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("id", info.getId());
		attributes.put("pid", info.getPid());
		attributes.put("title", info.getTitle());
		attributes.put("description", info.getDescription());
		attributes.put("type", info.getType());
		attributes.put("typeName", typeName);
		attributes.put("orderNo", info.getOrderNo());
		attributes.put("totalScore", info.getTotalScore());
		node.setAttributes(attributes);
		
		if(info.getChildren() != null){
			List<TreeNode> list = new ArrayList<>();
			for(StructureInfo child : info.getChildren()){
				TreeNode e = this.createStructureNode(child,ignoreStructureId);
				if(e != null) list.add(e);
			}
			if(list.size() > 0) node.setChildren(list);
		}
		
		return node;
	}
	/**
	 * 试卷结构编辑。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PAPERS_PAPER + ":" + Right.UPDATE})
	@RequestMapping(value="/structures/{paperId}/edit", method = RequestMethod.GET)
	public String editStructure(@PathVariable String paperId, String structureId, Model model){
		if(logger.isDebugEnabled()) logger.debug("加载结构编辑页面...");
		model.addAttribute("CURRENT_PAPER_ID", paperId);
		model.addAttribute("CURRENT_STRUCTURE_ID",  StringUtils.isEmpty(structureId) ? "" : structureId);
		
		model.addAttribute("ITEM_TYPES", this.itemService.loadTypes());
		return "papers/structure_edit";
	}
	/**
	 * 更新试卷结构数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PAPERS_PAPER + ":" + Right.UPDATE})
	@RequestMapping(value="/structures/{paperId}/update", method = RequestMethod.POST)
	@ResponseBody
	public Json updateStructure(@PathVariable String paperId, StructureInfo info){
		if(logger.isDebugEnabled()) logger.debug("更新试卷结构数据...");
		Json result = new Json();
		try {
			 this.paperService.updateStructure(paperId, info);
			 result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新试卷结构数据发生异常", e);
		}
		return result;
	}
	/**
	 * 删除试卷结构数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PAPERS_PAPER + ":" + Right.DELETE})
	@RequestMapping(value="/structures/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json deleteStructure(String structureId){
		if(logger.isDebugEnabled()) logger.debug("删除试卷结构数据...");
		Json result = new Json();
		try {
			this.paperService.deleteStructure(structureId); 
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除试卷结构数据["+structureId+"]时发生异常:", e);
		}
		return result;
	}
	/**
	 * 试卷下题目数据列表。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PAPERS_PAPER + ":" + Right.VIEW})
	@RequestMapping(value="/structures/items/{paperId}/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<StructureItemInfo> dgStructureItems(@PathVariable String paperId,StructureItemInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载试卷［"+paperId+"］下题目数据列表...");
		return this.paperService.findStructureItems(paperId, info);
	}
	/**
	 * 更新试卷题目数据。
	 * @param paperId
	 * 试卷ID。
	 * @param info
	 * 试卷题目信息。
	 * @return
	 * 
	 */
	@RequiresPermissions({ModuleConstant.PAPERS_PAPER + ":" + Right.UPDATE})
	@RequestMapping(value="/structures/items/{paperId}/update", method = RequestMethod.POST)
	@ResponseBody
	public Json updateStructureItems(@PathVariable String paperId,@RequestBody StructureItemInfo info){
		if(logger.isDebugEnabled()) logger.debug("更新试卷［"+ paperId +"］题目数据...");
		Json result = new Json();
		try {
			if(info.getItem() != null){
				info.getItem().setCreateUsername(this.userName);
			}
			 result.setData(this.paperService.updateStructureItem(paperId, info));
			 result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新试卷题目数据发生异常", e);
		}
		return result;
	}
	/**
	 * 试卷题目编辑页面。
	 * @param type
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PAPERS_PAPER + ":" + Right.UPDATE})
	@RequestMapping(value="/structures/items/{type}/edit/{structureId}", method = RequestMethod.GET)
	public String structureItemsEdit(@PathVariable Integer type, @PathVariable String structureId, String paperId,Boolean opts,  Model model){
		if(logger.isDebugEnabled()) logger.debug("加载试卷题目编辑页面...");
		model.addAttribute("CURRENT_PAPER_ID",StringUtils.isEmpty(paperId) ? "" : paperId);
		model.addAttribute("CURRENT_STRUCTURE_ID",  structureId);
		model.addAttribute("CURRENT_ITEM_TYPE", type);
		model.addAttribute("CURRENT_ITEM_TYPE_NAME", this.itemService.loadTypes().get(type.toString()));
		model.addAttribute("CURRENT_OPTS_STATUS", opts == null ? true : false);
		if(type == Item.TYPE_JUDGE){//判断题
			model.addAttribute("ANSWER_JUDGE_RIGTH", Item.ANSWER_JUDGE_RIGTH);
			model.addAttribute("ANSWER_JUDGE_RIGTH_NAME", this.itemService.loadJudgeTypeName(Item.ANSWER_JUDGE_RIGTH));
			
			model.addAttribute("ANSWER_JUDGE_WRONG", Item.ANSWER_JUDGE_WRONG);
			model.addAttribute("ANSWER_JUDGE_WRONG_NAME", this.itemService.loadJudgeTypeName(Item.ANSWER_JUDGE_WRONG));
		}
		if(type == Item.TYPE_SHARE_TITLE || type == Item.TYPE_SHARE_ANSWER){//共题干/选项
			model.addAttribute("ITEM_CHOICE_TYPES", this.itemService.loadAllChoiceTypes());
		}
		return "papers/item_edit";
	}
	/**
	 * 加载试卷题目最大排序号。
	 * @param paperId
	 * 试卷题目ID。
	 * @return
	 */
	@RequestMapping(value="/item/maxorder/{paperId}", method = RequestMethod.GET)
	@ResponseBody
	public int loadPaperItemOrderNo(@PathVariable String paperId){
		if(logger.isDebugEnabled()) logger.debug("加载试卷题目最大排序号...");
		return this.paperService.loadPaperItemMaxOrderNo(paperId);
	}
	/**
	 * 题目选项编辑页面。
	 * @return
	 */
	@RequestMapping(value="/item/option", method = RequestMethod.GET)
	public String structureItemsOptionEdit(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载题目选项编辑页面...");
		model.addAttribute("CURRENT_ID", UUID.randomUUID().toString());
		return "papers/item_option_dialog";
	}
	/**
	 * 加载试卷题目。
	 * @param structureItemId
	 * 试卷题目ID。
	 * @return
	 */
	@RequestMapping(value="/item/{structureItemId}", method = RequestMethod.GET)
	@ResponseBody
	public StructureItemInfo loadPaperItem(@PathVariable String structureItemId){
		if(logger.isDebugEnabled()) logger.debug("加载试卷［structureItemId="+structureItemId+"］题目数据...");
		return this.paperService.loadPaperItem(structureItemId);
	}
	/**
	 * 加载试题所有题目。
	 * @param paperId
	 * 所属试卷ID。
	 * @return
	 */
	@RequestMapping(value="/items", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<StructureItemInfo> loadItems(String paperId){
		if(logger.isDebugEnabled()) logger.debug("加载试题所有题目...");
		return this.paperService.findStructureItems(paperId, new StructureItemInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public String getSort(){return "serial";}
			@Override
			public String getOrder(){return "";}
		}).getRows();
	}
	/**
	 * 删除试卷题目。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PAPERS_PAPER + ":" + Right.DELETE})
	@RequestMapping(value="/structures/items/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json deleteStructureItems(String id){
		if(logger.isDebugEnabled()) logger.debug("删除试卷题目...");
		Json result = new Json();
		try {
			this.paperService.deleteStructureItems(id.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除试卷题目数据["+id+"]时发生异常:", e);
		}
		return result;
	}
}