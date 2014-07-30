package com.examw.netplatform.controllers.admin.agency;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.model.TreeNode;
import com.examw.netplatform.domain.admin.agency.Agency;
import com.examw.netplatform.domain.admin.security.Right;
import com.examw.netplatform.domain.admin.security.Role;
import com.examw.netplatform.model.admin.agency.AgencyInfo;
import com.examw.netplatform.model.admin.security.RoleInfo;
import com.examw.netplatform.service.admin.agency.IAgencyService;
import com.examw.netplatform.service.admin.security.IRoleService;

/**
 * 培训机构控制器。
 * @author yangyong.
 * @since 2014-04-28.
 */
@Controller
@RequestMapping(value = "/admin/agency/register")
public class AgencyController {
	private static final Logger logger  = Logger.getLogger(AgencyController.class);
	//培训机构服务接口。
	@Resource
	private IAgencyService agencyService;
	//角色服务接口。
	@Resource
	private IRoleService roleService;
	/**
	 * 培训机构列表页面。
	 * @return
	 */
	@RequiresPermissions(ModuleConstant.AGENCY_REGISTER + ":" +Right.VIEW)
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled())logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.AGENCY_REGISTER + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.AGENCY_REGISTER + ":" + Right.DELETE);
		return "agency/register_list";
	}
	/**
	 * 培训机构编辑页面。
	 * @return
	 */
	@RequiresPermissions(ModuleConstant.AGENCY_REGISTER + ":" +Right.UPDATE)
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public String edit(Model model){
		if(logger.isDebugEnabled())logger.debug("加载编辑页面...");
		
		model.addAttribute("STATUS_ENABLED", this.agencyService.loadStatusName(Agency.STATUS_ENABLED));
		model.addAttribute("STATUS_DISABLE", this.agencyService.loadStatusName(Agency.STATUS_DISABLE));
		
		model.addAttribute("ROLES", this.roleService.loadAll());
		
		return "agency/register_edit";
	}
	/**
	 * 查询数据。
	 * @return
	 */
	@RequiresPermissions(ModuleConstant.AGENCY_REGISTER + ":" +Right.VIEW)
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<AgencyInfo> datagrid(AgencyInfo info){
		if(logger.isDebugEnabled())logger.debug("加载列表数据...");
		return this.agencyService.datagrid(info);
	}
	/**
	 * 培训机构树结构数据。
	 * @return
	 */
	@RequestMapping(value = "/tree", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public synchronized List<TreeNode> tree(){
		if(logger.isDebugEnabled())logger.debug("加载培训机构树结构数据...");
		List<TreeNode> result = new ArrayList<>();
		List<AgencyInfo> list = this.all();
		if(list != null && list.size() > 0){
			for(int i = 0; i < list.size(); i++){
				TreeNode tv = this.createTreeNode(list.get(i));
				if(tv != null){
					result.add(tv);
				}
			}
		}
		return result;
	}
	/**
	 * 构造树
	 * @param info
	 * @return
	 */
	private TreeNode createTreeNode(AgencyInfo info){
		if(info == null) return null;
		TreeNode tv = new TreeNode();
		tv.setId(info.getId());
		tv.setText(info.getName());
		return tv;
	}
	/**
	 * 更新数据。
	 * @param info
	 * 更新源数据。
	 * @return
	 * 更新后数据。
	 */
	@RequiresPermissions(ModuleConstant.AGENCY_REGISTER + ":" +Right.UPDATE)
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(AgencyInfo info){
		if(logger.isDebugEnabled())logger.debug("更新数据...");
		Json result = new Json();
		try {
			result.setData(this.agencyService.update(info));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新考试类型数据发生异常", e);
		}
		return result;
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions(ModuleConstant.AGENCY_REGISTER + ":" +Right.DELETE)
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(String id){
		if(logger.isDebugEnabled())logger.debug("删除数据...");
		Json result = new Json();
		try {
			this.agencyService.delete(id.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除数据["+id+"]时发生异常:", e);
		}
		return result;
	}
	/**
	 * 查询数据。
	 * @return
	 * @author fengwei
	 */
	@RequestMapping(value="/all", method = RequestMethod.POST)
	@ResponseBody
	public List<AgencyInfo> all(){
		if(logger.isDebugEnabled())logger.debug("加载全部培训机构数据...");
		 return this.agencyService.datagrid(new AgencyInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public Integer getPage(){return null;}
			@Override
			public Integer getRows(){return null;}
			@Override
			public String getSort(){return "name";}
			@Override
			public String getOrder(){return "asc";}
		 }).getRows();
	}
	/**
	 * 加载机构角色集合。
	 * @return
	 */
	@RequestMapping(value = "/roles/{agencyId}", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public List<RoleInfo> loadRoles(@PathVariable String agencyId){
		if(logger.isDebugEnabled())logger.debug(" 加载机构角色集合...");
		List<RoleInfo> list = new ArrayList<>();
		if(StringUtils.isEmpty(agencyId)) return list;
		 Role[] roles = this.agencyService.loadRoles(agencyId);
		 if(roles != null && roles.length > 0){
			 for(int i = 0; i < roles.length; i++){
				 if(roles[i] == null) continue;
				 RoleInfo info = new RoleInfo();
				 BeanUtils.copyProperties(roles[i], info);
				 list.add(info);
			 }
		 }
		return list;
	}
}