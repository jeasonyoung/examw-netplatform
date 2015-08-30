package com.examw.netplatform.service.admin.security.impl;
 
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.examw.model.DataGrid;
import com.examw.netplatform.dao.admin.security.MenuRightMapper;
import com.examw.netplatform.dao.admin.security.RoleMapper;
import com.examw.netplatform.dao.admin.security.UserMapper;
import com.examw.netplatform.dao.admin.settings.AgencyMapper;
import com.examw.netplatform.domain.admin.security.MenuRight;
import com.examw.netplatform.domain.admin.security.Role;
import com.examw.netplatform.domain.admin.security.User;
import com.examw.netplatform.domain.admin.security.UserIdentity;
import com.examw.netplatform.domain.admin.security.UserType;
import com.examw.netplatform.domain.admin.settings.Agency;
import com.examw.netplatform.model.admin.security.UserInfo;
import com.examw.netplatform.service.admin.security.IUserAuthorization;
import com.examw.netplatform.service.admin.security.IUserService;
import com.examw.netplatform.shiro.IUserCache;
import com.examw.netplatform.support.PasswordHelper;
import com.examw.service.Gender;
import com.examw.service.Status;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 用户服务接口实现。
 * @author yangyong.
 * @since 2014-05-08.
 */
public class UserServiceImpl implements IUserService, IUserAuthorization {
	private static final Logger logger = Logger.getLogger(UserServiceImpl.class);
	private UserMapper userDao;
	private RoleMapper roleDao; 
	private AgencyMapper agencyDao;
	private MenuRightMapper menuRightDao;
	private IUserCache userCache;
	private Map<Integer, String> genderNameMap,typeNameMap,statusNameMap,identityNameMap;
	private PasswordHelper passwordHelper;
	/**
	 * 设置用户数据接口。
	 * @param userDao
	 * 用户数据接口。
	 */
	public void setUserDao(UserMapper userDao) {
		logger.debug("注入用户数据接口...");
		this.userDao = userDao;
	}
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
	 * 设置机构数据接口。
	 * @param agencyDao 
	 *	  机构数据接口。
	 */
	public void setAgencyDao(AgencyMapper agencyDao) {
		logger.debug("注入机构数据接口...");
		this.agencyDao = agencyDao;
	}
	/**
	 * 设置菜单权限数据接口。
	 * @param menuRightDao 
	 *	  菜单权限数据接口。
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
	 * 设置密码工具。
	 * @param passwordHelper
	 */
	public void setPasswordHelper(PasswordHelper passwordHelper) {
		logger.debug("注入密码工具...");
		this.passwordHelper = passwordHelper;
	}
	/**
	 * 设置性别名称集合。
	 * @param genderNameMap
	 * 性别名称集合。
	 */
	public void setGenderNameMap(Map<Integer, String> genderNameMap) {
		logger.debug("注入性别名称集合...");
		this.genderNameMap = genderNameMap;
	}
	/**
	 * 设置用户类型名称集合。
	 * @param typeNameMap 
	 *	  用户类型名称集合。
	 */
	public void setTypeNameMap(Map<Integer, String> typeNameMap) {
		logger.debug("注入用户类型名称集合...");
		this.typeNameMap = typeNameMap;
	}
	/**
	 * 设置状态名称集合。
	 * @param statusNameMap
	 * 状态名称集合。
	 */
	public void setStatusNameMap(Map<Integer, String> statusNameMap) {
		logger.debug("注入状态名称集合...");
		this.statusNameMap = statusNameMap;
	}
	/**
	 * 设置身份名称集合。
	 * @param identityNameMap 
	 *	  身份名称集合。
	 */
	public void setIdentityNameMap(Map<Integer, String> identityNameMap) {
		logger.debug("注入身份名称集合...");
		this.identityNameMap = identityNameMap;
	}
	/*
	 * 加载性别名称。
	 * @see com.examw.netplatform.service.admin.IUserService#loadGenderName(java.lang.Integer)
	 */
	@Override
	public String loadGenderName(Integer gender) {
		logger.debug(String.format("加载性别［%d］名称...", gender));
		if(this.genderNameMap == null || this.genderNameMap.size() == 0) return null;
		return this.genderNameMap.get(gender);
	}
	/*
	 * 加载用户类型名称。
	 * @see com.examw.netplatform.service.admin.security.IUserService#loadUserTypeName(java.lang.Integer)
	 */
	@Override
	public String loadTypeName(Integer type) {
		logger.debug(String.format("用户类型［%d］名称...", type));
		if(this.typeNameMap == null || this.typeNameMap.size() == 0) return null;
		return this.typeNameMap.get(type);
	}
	/*
	 * 加载用户状态名称。
	 * @see com.examw.netplatform.service.admin.security.IUserService#loadStatusName(java.lang.Integer)
	 */
	@Override
	public String loadStatusName(Integer status) {
		logger.debug(String.format("加载用户状态［%d］名称...", status));
		if(this.statusNameMap == null || this.statusNameMap.size() == 0) return null;
		return this.statusNameMap.get(status);
	}
	/*
	 * 加载身份名称。
	 * @see com.examw.netplatform.service.admin.security.IUserService#loadIdentityName(java.lang.Integer)
	 */
	@Override
	public String loadIdentityName(Integer identity){
		logger.debug("加载身份["+identity+"]名称...");
		if(this.identityNameMap == null || this.identityNameMap.size() == 0) return null;
		return this.identityNameMap.get(identity);
	}
	/*
	 * 加载用户名称。
	 * @see com.examw.netplatform.service.admin.security.IUserService#loadUserName(java.lang.String)
	 */
	@Override
	public String loadUserName(String userId) {
		logger.debug("加载用户["+userId+"]名称...");
		if(StringUtils.isNotBlank(userId)){
			final User user = this.userDao.getUser(userId);
			if(user != null) return user.getName();
		}
		return null;
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.admin.security.IUserService#datagrid(com.examw.netplatform.model.admin.security.UserInfo)
	 */
	@Override
	public DataGrid<UserInfo> datagrid(UserInfo info) {
		logger.debug("查询数据...");
		//初始化
		final DataGrid<UserInfo> grid = new DataGrid<UserInfo>();
		//分页/排序
		PageHelper.startPage(info.getPage(), info.getRows(), StringUtils.trimToEmpty(info.getSort()) + " " + StringUtils.trimToEmpty(info.getOrder()));
		//查询数据
		final List<User> list = this.userDao.findUsers(info);
		//分页信息
		final PageInfo<User> pageInfo = new PageInfo<User>(list);
		//设置数据
		grid.setRows(this.changeModel(list));
		grid.setTotal(pageInfo.getTotal());
		//返回
		return grid;
	}
	//类型转换
	private List<UserInfo> changeModel(List<User> users){
		final List<UserInfo> list = new ArrayList<UserInfo>();
		if(users != null && users.size() > 0){
			for(User user : users){
				if(user == null) continue;
				list.add(this.conversion(user, true));
			}
		}
		return list;
	}
	//数据类型转换。
	private UserInfo conversion(User data, boolean isViewPwd) {
		logger.debug("数据类型转换[User => UserInfo]..");
		if(data != null){
			final UserInfo info = new UserInfo();
			BeanUtils.copyProperties(data, info); 
			//性别
			info.setGenderName(this.loadGenderName(info.getGender()));
			//状态
			info.setStatusName(this.loadStatusName(info.getStatus()));
			//类型
			info.setTypeName(this.loadTypeName(info.getType()));
			//身份
			info.setIdentityName(this.loadIdentityName(info.getIdentity()));
			//所属机构处理
			if(StringUtils.isNotBlank(info.getAgencyId()) && StringUtils.isBlank(info.getAgencyName())){
				final Agency agency = this.agencyDao.getAgency(info.getAgencyId());
				if(agency != null) info.setAgencyName(agency.getName());
			}
			//密码处理
			if(isViewPwd){
				//密文转明文
				info.setPassword(this.passwordHelper.decryptAESPassword(data));
			}else {
				//密码重置为空
				info.setPassword(null);
			} 
			//加载用户角色
			final List<String> userRoleIds = new ArrayList<String>();
			final List<Role> roles = this.roleDao.findRolesByUser(info.getId());
			if(roles != null && roles.size() > 0){
				for(Role role : roles){
					if(role == null) continue;
					userRoleIds.add(role.getId());
				}
			}
			info.setRoleIds(userRoleIds.toArray(new String[0]));
			//
			return info;
		}
		return null;
	}
	/*
	 * 查询订单下用户集合。
	 * @see com.examw.netplatform.service.admin.security.IUserService#findUsersByOrder(java.lang.String)
	 */
	@Override
	public List<UserInfo> findUsersByOrder(String orderId) {
		logger.debug("查询订单["+orderId+"]下用户集合...");
		final List<UserInfo> list = new ArrayList<UserInfo>();
		if(StringUtils.isNotBlank(orderId)){
			final List<User> users = this.userDao.findUsersByOrder(orderId);
			if(users != null && users.size() > 0){
				for(User user : users){
					if(user == null) continue;
					list.add(this.conversion(user, false));
				}
			}
		}
		return list;
	}
	/*
	 * 查询消息用户集合。
	 * @see com.examw.netplatform.service.admin.security.IUserService#findUsersByMsg(java.lang.String)
	 */
	@Override
	public List<UserInfo> findUsersByMsg(String msgId) {
		logger.debug("查询消息["+msgId+"]用户集合...");
		final List<UserInfo> list = new ArrayList<UserInfo>();
		if(StringUtils.isNotBlank(msgId)){
			final List<User> users = this.userDao.findUsersByMsg(msgId);
			if(users != null && users.size() > 0){
				for(User user : users){
					if(user == null) continue;
					list.add(this.conversion(user, false));
				}
			}
		}
		return list;
	}
	/*
	 * 更新数据。
	 * @see com.examw.netplatform.service.admin.security.IUserService#update(com.examw.netplatform.model.admin.security.UserInfo)
	 */
	@Override
	public UserInfo update(UserInfo info) {
		logger.debug("更新数据...");
		return this.conversion(this.updateUser(info), false);
	}
	/*
	 * 更新用户数据。
	 * @see com.examw.netplatform.service.admin.security.IUserService#updateUser(com.examw.netplatform.model.admin.security.UserInfo)
	 */
	@Override
	public User updateUser(UserInfo info) {
		logger.debug("更新用户数据...");
		if(info == null) return null;
		boolean isAdded = false;
		User user = StringUtils.isBlank(info.getId()) ? null : this.userDao.getUser(info.getId());
		if(isAdded = (user == null)){
			if(StringUtils.isBlank(info.getId())) 
				info.setId(UUID.randomUUID().toString());
			user = new User();
		}
		//赋值
		BeanUtils.copyProperties(info, user);
		//密码加密
		if(StringUtils.isNotBlank(info.getPassword())){
			user.setPassword(this.passwordHelper.encryptAESPassword(info));
		}
		//检查机构信息是否存在
		final boolean hasAgency = StringUtils.isBlank(user.getAgencyId()) ?  false : this.userDao.hasAgencyAccount(user.getAgencyId(), user.getAccount());
		//用户处理
		if(isAdded){
			if(hasAgency){
				throw new RuntimeException("机构下已存在该账号["+user.getAccount()+"]!");
			}else {
				if(StringUtils.isNotBlank(user.getAgencyId())){
					this.userDao.insertUser(user);
					if(this.agencyDao.getAgency(user.getAgencyId()) != null){
						this.userDao.insertUserAgency(user.getId(), user.getAgencyId(), user.getIdentity());
					}
					logger.debug("新增用户账号["+user.getAccount()+"]...");
				}else if(user.getType() == UserType.BACKGROUND.getValue()) {
					if(this.userDao.hasUserByAccount(user.getAccount())){
						throw new RuntimeException("用户账号已存在，不能新增为不属于机构的后台用户!");
					}
					this.userDao.insertUser(user);
					logger.debug("用户账号["+user.getAccount()+"]新增为不属于机构的后台用户...");
				}	
			}
		}else {
			logger.debug("更新用户信息...");
			this.userDao.updateUser(user);
			if(!hasAgency && StringUtils.isNotBlank(user.getAgencyId()) && this.agencyDao.getAgency(user.getAgencyId()) != null){ 
				logger.debug("将用户["+user.getAccount()+"]添加到机构["+user.getAgencyId()+"]..");
				this.userDao.insertUserAgency(user.getId(), user.getAgencyId(), user.getIdentity());
			}
			if(hasAgency && user.getIdentity() != null){
				logger.debug("更新用户["+user.getAccount()+"]机构["+user.getAgencyId()+"]身份...");
				this.userDao.updateUserAgencyIdentity(user.getId(), user.getAgencyId(), user.getIdentity());
			}
		}
		//用户角色
		// 删除用户角色
		this.userDao.deleteUserAllRoles(user.getId());
		// 添加用户角色
		if(info.getRoleIds() != null && user.getRoleIds().length > 0){
			for(String roleId : user.getRoleIds()){
				if(StringUtils.isBlank(roleId)) continue;
				if(this.roleDao.getRole(roleId) != null && !this.userDao.hasUserRole(user.getId(), roleId)){
					this.userDao.insertUserRole(user.getId(), roleId);
				}
			}
		}
		return user;
	}
	/*
	 * 修改密码。
	 * @see com.examw.netplatform.service.admin.security.IUserService#modifyPassword(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void modifyPassword(String userId, String oldPassword, String newPassword) throws Exception {
		logger.debug(String.format("更新用户［%1$s］密码:［%1$s］ =>［％2$s］", userId,oldPassword,newPassword));
		if(StringUtils.isBlank(userId)) throw new Exception("用户ID为空！");
		if(StringUtils.isBlank(newPassword)) throw new Exception("新密码为空！");
		final User user = this.userDao.getUser(userId);
		if(user == null) throw new Exception(String.format("用户［％s］不存在！", userId));
		if(!StringUtils.isEmpty(oldPassword)){//验证旧密码。
			String old_pwd = this.passwordHelper.decryptAESPassword(user);
			if(!oldPassword.equalsIgnoreCase(old_pwd)) throw new Exception("旧密码错误！");
		}
		//设置新密码
		user.setPassword(this.passwordHelper.encryptAESPassword((UserInfo)user));
		logger.debug("密码修改成功！");
		this.userCache.removeUserCache(user.getAccount());//清除用户缓存。
	}
	/*
	 * 批量删除数据。
	 * @see com.examw.netplatform.service.admin.security.IUserService#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		logger.debug("批量删除数据..." + StringUtils.join(ids,","));
		if(ids != null && ids.length > 0){
			for(String id : ids){
				if(StringUtils.isBlank(id)) continue;
				this.deleteUser(id);
			}
		}
	}
	/*
	 * 删除用户。
	 * @see com.examw.netplatform.service.admin.security.IUserService#deleteUser(java.lang.String)
	 */
	@Override
	public void deleteUser(String userId) {
		logger.debug("删除用户..." + userId);
		this.userDao.deleteUser(userId);
	}
	/*
	 * 初始化用户。
	 * @see com.examw.netplatform.service.admin.security.IUserService#init(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void init(String roleId, String account, String password) throws Exception {
		logger.debug(String.format("初始化用户［roleId = %1$s,account=%2$s,password=%3$s］...",roleId,account,password));
		if(StringUtils.isBlank(roleId)) throw new Exception("角色ID为空！");
		if(StringUtils.isEmpty(account) || StringUtils.isEmpty(password)) 
			throw new Exception("账号或密码为空！");
		//验证角色是否存在
		final Role role = this.roleDao.getRole(roleId);
		if(role == null) throw new Exception("角色["+roleId+"]不存在!");
		
		final User user = new User();
		user.setAccount(account);
		user.setName(account);
		user.setNickName(account);
		user.setPassword(password);
		
		user.setGender(Gender.NONE.getValue());
		user.setStatus(Status.ENABLED.getValue());
		user.setType(UserType.BACKGROUND.getValue());
		user.setIdentity(UserIdentity.ADMIN.getValue());
		
		//添加用户
		final UserInfo info = new UserInfo();
		BeanUtils.copyProperties(user, info);
		this.updateUser(info);
		//插入用户角色
		this.userDao.insertUserRole(user.getId(), roleId);
		logger.debug("初始化用户完成。");
		if(this.userCache != null) this.userCache.removeUserCache(user.getId());
	}
	/*
	 * 加载机构/账号下用户。
	 * @see com.examw.netplatform.service.admin.security.IUserAuthorization#loadUserByAccount(java.lang.String, java.lang.String)
	 */
	@Override
	public User loadUserByAccount(String agencyId, String account) {
		logger.debug("加载机构["+agencyId+"]/账号["+account+"]下用户...");
		if(StringUtils.isBlank(agencyId) || StringUtils.isBlank(account)) return null;
		return this.userDao.findByAccount(agencyId, account);
	}
	/*
	 * 加载用户ID数据。
	 * @see com.examw.netplatform.service.admin.security.IUserAuthorization#getUser(java.lang.String)
	 */
	@Override
	public User getUser(String userId) {
		logger.debug("加载用户ID["+userId+"]数据...");
		if(StringUtils.isBlank(userId)) return null;
		return this.userDao.getUser(userId);
	}
	/*
	 * 加载用户ID下角色集合。
	 * @see com.examw.netplatform.service.admin.security.IUserAuthorization#findRolesByUser(java.lang.String)
	 */
	@Override
	public Set<String> findRolesByUser(String userId) {
		logger.debug("加载用户ID["+userId+"]下角色集合...");
		final Set<String> roles = new HashSet<String>();
		if(StringUtils.isNotBlank(userId) && this.roleDao != null){
			final List<Role> list = this.roleDao.findRolesByUser(userId);
			if(list != null && list.size() > 0){
				for(Role role : list){
					if(role == null) continue;
					roles.add(role.getId());
				}
			}
		}
		return roles;
	}
	/*
	 * 加载用户ID下权限集合。
	 * @see com.examw.netplatform.service.admin.security.IUserAuthorization#findPermissionsByUser(java.lang.String)
	 */
	@Override
	public Set<String> findPermissionsByUser(String userId) {
		logger.debug("加载用户ID["+userId+"]下权限集合...");
		final Set<String> permissions = new HashSet<String>();
		if(StringUtils.isNotBlank(userId) && this.menuRightDao != null){
			final List<MenuRight> list = this.menuRightDao.findMenuPermissionsByUser(userId);
			if(list != null && list.size() > 0){
				for(MenuRight mr : list){
					if(mr == null) continue;
					permissions.add(mr.getCode());
				}
			}
		}
		return permissions;
	}
}