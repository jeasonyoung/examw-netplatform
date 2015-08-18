package com.examw.netplatform.service.admin.security.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.examw.model.DataGrid;
import com.examw.netplatform.dao.admin.security.MenuRightMapper;
import com.examw.netplatform.dao.admin.security.RoleMapper;
import com.examw.netplatform.domain.admin.security.MenuRight;
import com.examw.netplatform.domain.admin.security.Role;
import com.examw.netplatform.model.admin.security.RoleInfo;
import com.examw.netplatform.model.admin.security.RoleStatus;
import com.examw.netplatform.service.admin.security.IRoleService;
import com.examw.netplatform.shiro.IUserCache;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/**
 * 角色服务接口实现类。
 * @author yangyong.
 * @since 2014-05-06.
 */
public class RoleServiceImpl implements IRoleService {
	private static final Logger logger = Logger.getLogger(RoleServiceImpl.class);
	private RoleMapper roleDao;
	private MenuRightMapper menuRightDao;
	private IUserCache userCache;
	private Map<Integer, String> roleStatusNameMap;
	/**
	 * 设置角色数据接口。
	 * @param roleDao
	 * 角色数据接口。
	 */
	public void setRoleDao(RoleMapper roleDao) {
		logger.debug("注入角色数据接口...");
		this.roleDao = roleDao;
	}
	/**
	 * 设置菜单权限数据接口。
	 * @param menuRightDao
	 * 菜单权限数据接口。
	 */
	public void setMenuRightDao(MenuRightMapper menuRightDao) {
		logger.debug("注入菜单权限数据接口...");
		this.menuRightDao = menuRightDao;
	}
	/**
	 * 设置用户缓存。
	 * @param userCache 
	 *	  用户缓存。
	 */
	public void setUserCache(IUserCache userCache) {
		logger.debug("注入用户缓存...");
		this.userCache = userCache;
	}
	/**
	 * 设置角色状态名称。
	 * @param roleStatusNameMap
	 * 角色状态名称。
	 */
	public void setRoleStatusNameMap(Map<Integer, String> roleStatusNameMap) {
		logger.debug("注入角色状态名称...");
		this.roleStatusNameMap = roleStatusNameMap;
	}
	/*
	 *  加载状态名称。
	 * @see com.examw.netplatform.service.admin.security.IRoleService#loadStatusName(int)
	 */
	@Override
	public String loadStatusName(Integer status) {
		logger.debug(String.format("注入加载状态［%d］名称...", status));
		if(status == null || this.roleStatusNameMap == null || this.roleStatusNameMap.size() == 0) return null;
		return this.roleStatusNameMap.get(status);
	}
	/*
	 * 加载全部角色。
	 * @see com.examw.netplatform.service.admin.security.IRoleService#loadAll()
	 */
	@Override
	public List<RoleInfo> loadAll() {
		logger.debug("加载全部角色数据...");
		//排序
		PageHelper.orderBy("name");
		return this.changeModel(this.roleDao.findRoles(null));
	}
	//类型转换
	private List<RoleInfo> changeModel(List<Role> roles) {
		 final List<RoleInfo> list = new ArrayList<RoleInfo>();
		 if(roles != null && roles.size() > 0){
			 for(Role role : roles){
				 if(role == null) continue;
				 list.add(this.conversion(role));
			 }
		 }
		 return list;
	}
	/*
	 * 角色菜单权限集合。
	 * @see com.examw.netplatform.service.admin.security.IRoleService#loadRoleRightIds(java.lang.String)
	 */
	@Override
	public String[] loadRoleRightIds(String roleId) {
		logger.debug("角色["+roleId+"]菜单权限集合...");
		if(StringUtils.isNotBlank(roleId)){
			final List<MenuRight> rights = this.menuRightDao.findMenuRightsByRole(roleId);
			if(rights != null && rights.size() > 0){
				final List<String> rightList = new ArrayList<String>(rights.size());
				for(MenuRight info : rights){
					if(info == null || StringUtils.isBlank(info.getId())) continue;
					rightList.add(info.getId());
				}
				return rightList.toArray(new String[0]);
			}
		}
		return null;
	}
	/*
	 * 加载角色。
	 * @see com.examw.netplatform.service.admin.security.IRoleService#loadRole(java.lang.String)
	 */
	@Override
	public Role loadRole(String roleId) {
		logger.debug(" 加载角色["+roleId+"]...");
		return this.roleDao.getRole(roleId);
	}
	/*
	 * 角色类型转换。
	 * @see com.examw.netplatform.service.admin.security.IRoleService#conversion(com.examw.netplatform.domain.admin.security.Role)
	 */
	@Override
	public RoleInfo conversion(Role role) {
		logger.debug("角色类型转换: Role => RoleInfo...");
		if(role != null){
			RoleInfo info = new RoleInfo();
			BeanUtils.copyProperties(role, info);
			info.setStatusName(this.loadStatusName(info.getStatus()));
			return info;
		}
		return null;
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.admin.security.IRoleService#datagrid(com.examw.netplatform.model.admin.security.RoleInfo)
	 */
	@Override
	public DataGrid<RoleInfo> datagrid(RoleInfo info) {
		logger.debug("查询数据...");
		//初始化
		final DataGrid<RoleInfo> grid = new DataGrid<RoleInfo>();
		//分页/排序
		PageHelper.startPage(info.getPage(), info.getRows(), StringUtils.trimToEmpty(info.getSort()) + " " + StringUtils.trimToEmpty(info.getOrder()));
		//查询数据
		final List<Role> list = this.roleDao.findRoles(info);
		//分页信息
		final PageInfo<Role> pageInfo = new PageInfo<Role>(list);
		//设置数据
		grid.setRows(this.changeModel(list));
		grid.setTotal(pageInfo.getTotal());
		//返回
		return grid;
	}
	/*
	 * 更新角色数据。
	 * @see com.examw.netplatform.service.admin.security.IRoleService#update(com.examw.netplatform.model.admin.security.RoleInfo)
	 */
	@Override
	public RoleInfo update(RoleInfo info) {
		logger.debug("更新角色数据...");
		if(info == null) return info;
		Role data = StringUtils.isBlank(info.getId()) ? null : this.roleDao.getRole(info.getId());
		boolean isAdded = false;
		if(isAdded = (data == null)){
			if(StringUtils.isBlank(info.getId()))
				info.setId(UUID.randomUUID().toString());
			data = new Role();
		}
		BeanUtils.copyProperties(info, data);
		//
		if(isAdded){
			logger.debug("新增角色数据...");
			this.roleDao.insertRole(data);
		}else {
			logger.debug("更新角色数据...");
			this.roleDao.updateRole(data);
		}
		return this.conversion(data);
	}
	/*
	 * 删除数据。
	 * @see com.examw.netplatform.service.admin.security.IRoleService#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		logger.debug("删除数据..." + StringUtils.join(ids,","));
		if(ids != null && ids.length > 0){
			for(String id : ids){
				if(StringUtils.isBlank(id)) continue;
				this.roleDao.deleteRole(id);
			}
		}
	}
	/*
	 * 更新角色权限。
	 * @see com.examw.netplatform.service.admin.security.IRoleService#updateRoleRights(java.lang.String, java.lang.String[])
	 */
	@Override
	public void updateRoleRights(String roleId, String[] rightIds) throws Exception {
		logger.debug("更新角色["+roleId+"]权限..." + StringUtils.join(rightIds,","));
		if(StringUtils.isNotBlank(roleId)){
			//角色
			final Role role = this.roleDao.getRole(roleId);
			if(role == null) throw new Exception("角色["+roleId+"]不存在!");
			//菜单权限
			for(String menuRightId : rightIds){
				if(StringUtils.isBlank(menuRightId)) continue;
				//检查菜单权限
				final MenuRight menuRight = this.menuRightDao.getMenuRight(menuRightId);
				if(menuRight == null) continue;
				//是否存在
				if(this.roleDao.hasRoleRight(roleId, menuRightId)) continue;
				//插入角色权限表
				this.roleDao.insertRoleRight(roleId, menuRightId);
			}
			//清除用户缓存。
			if(this.userCache != null) this.userCache.removeAuthorizationCache();
		}
	}
	/*
	 * 初始化角色。
	 * @see com.examw.netplatform.service.admin.security.IRoleService#init(java.lang.String)
	 */
	@Override
	public void init(String roleId) throws Exception {
		logger.debug("初始化角色..." + roleId);
		if(StringUtils.isBlank(roleId)) throw new Exception("角色ID为空!");
		//加载角色
		Role role = this.roleDao.getRole(roleId);
		boolean isAdded = false;
		if(isAdded = (role == null)){
			role = new Role();
			role.setId(roleId);
		}
		role.setName("系统管理员");
		role.setDescription("系统管理员");
		role.setStatus(RoleStatus.ENABLED.getValue());
		//保存
		if(isAdded){
			logger.debug("新建角色:" + roleId);
			this.roleDao.insertRole(role);
		}else {
			logger.debug("更新角色:" + roleId);
			this.roleDao.updateRole(role);
		}
		//添加角色菜单权限
		final List<MenuRight> menuRights = this.menuRightDao.findMenuRights(null);
		if(menuRights != null && menuRights.size() > 0){
			logger.debug("准备初始化角色["+roleId+"]菜单权限...");
			final List<String> menuRightList = new ArrayList<String>();
			for(MenuRight info : menuRights){
				if(info == null) continue;
				menuRightList.add(info.getId());
			}
			//添加角色权限
			this.updateRoleRights(roleId, menuRightList.toArray(new String[0]));
		}
	}
}