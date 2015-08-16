package com.examw.netplatform.service.admin.security.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.examw.model.DataGrid;
import com.examw.netplatform.dao.admin.security.MenuMapper;
import com.examw.netplatform.dao.admin.security.MenuRightMapper;
import com.examw.netplatform.dao.admin.security.RightMapper;
import com.examw.netplatform.domain.admin.security.MenuEntity;
import com.examw.netplatform.domain.admin.security.MenuRight;
import com.examw.netplatform.domain.admin.security.Right;
import com.examw.netplatform.model.admin.security.MenuRightInfo;
import com.examw.netplatform.service.admin.security.IMenuRightService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 菜单权限服务接口。
 * @author yangyong.
 * @since 2014-05-04.
 */
public class MenuRightServiceImpl implements IMenuRightService {
	private static final Logger logger = Logger.getLogger(MenuRightServiceImpl.class);
	private MenuRightMapper menuRightDao;
	private MenuMapper menuDao;
	private RightMapper rightDao;
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
	 * 设置菜单数据接口。
	 * @param menuDao
	 * 菜单数据接口。
	 */
	public void setMenuDao(MenuMapper menuDao) {
		logger.debug("注入菜单数据接口...");
		this.menuDao = menuDao;
	}
	/**
	 * 设置权限数据接口。
	 * @param rightDao
	 * 权限数据接口。
	 */
	public void setRightDao(RightMapper rightDao) {
		logger.debug("注入权限数据接口...");
		this.rightDao = rightDao;
	}
	/*
	 *查询数据。
	 * @see com.examw.netplatform.service.admin.security.IMenuRightService#datagrid(com.examw.netplatform.model.admin.security.MenuRightInfo)
	 */
	@Override
	public DataGrid<MenuRightInfo> datagrid(MenuRightInfo info) {
		logger.debug("查询数据...");
		//初始化
		final DataGrid<MenuRightInfo> grid = new DataGrid<MenuRightInfo>();
		//分页
		PageHelper.startPage(info.getPage(), info.getRows(), StringUtils.trimToEmpty(info.getSort()) + " " + StringUtils.trimToEmpty(info.getOrder()));
		//查询数据
		final List<MenuRight> list = this.menuRightDao.findMenuRights(info);
		//分页信息
		final PageInfo<MenuRight> pageInfo = new PageInfo<MenuRight>(list);
		//设置数据
		grid.setRows(this.changeModel(list));
		grid.setTotal(pageInfo.getTotal());
		//返回
		return grid;
	}
	//批量数据类型转换
	private List<MenuRightInfo> changeModel(List<MenuRight> menuRights){
		logger.debug("批量数据类型转换...");
		final List<MenuRightInfo> list = new ArrayList<MenuRightInfo>();
		if(menuRights != null && menuRights.size() > 0){
			for(MenuRight menuRight : menuRights){
				if(menuRight == null) continue;
				list.add(this.conversion(menuRight));
			}
		}
		return list;
	}
	//数据类型转换
	private MenuRightInfo conversion(MenuRight data){
		logger.debug("数据类型转换[MenuRight -> MenuRightInfo]...");
		if(data != null){
			MenuRightInfo info = new MenuRightInfo();
			BeanUtils.copyProperties(data, info);
			return info;
		}
		return null;
	}
	/*
	 * 更新数据。
	 * @see com.examw.netplatform.service.admin.security.IMenuRightService#update(com.examw.netplatform.model.admin.security.MenuRightInfo)
	 */
	@Override
	public void update(MenuRightInfo info) {
		logger.debug("更新数据...");
		if(info == null || StringUtils.isBlank(info.getMenuId()) || StringUtils.isBlank(info.getRightId())) return;
		boolean isAdded = false;
		if(isAdded = StringUtils.isBlank(info.getId())){
			info.setId(UUID.randomUUID().toString());
		}
		//设置菜单权限代码
		info.setCode(this.createMenuRightCode(info.getMenuId(), info.getRightId()));
		//保存
		if(isAdded){
			if(this.menuRightDao.loadMenuRight(info.getMenuId(), info.getRightId()) != null){
				throw new RuntimeException("已存在!");
			}
			logger.debug("新增数据...");
			this.menuRightDao.insertMenuRight(info);
		}else {
			logger.debug("保存数据...");
			this.menuRightDao.updateMenuRight(info);
		}
	}
	/*
	 * 删除菜单权限。
	 * @see com.examw.netplatform.service.admin.security.IMenuRightService#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		logger.debug("删除菜单权限..." + StringUtils.join(ids, ","));
		if(ids == null || ids.length == 0) return;
		for(String id : ids){
			if(StringUtils.isBlank(id)) continue;
			this.menuRightDao.deleteMenuRight(id);
		}
	}
	//创建菜单权限代码
	private String createMenuRightCode(String menuId, String rightId){
		//菜单
		final MenuEntity menu = this.menuDao.getMenu(menuId);
		if(menu == null){
			throw new RuntimeException("菜单不存在:" + menuId);
		}
		//权限
		final Right right = this.rightDao.getRight(rightId);
		if(right == null){
			throw new RuntimeException("权限不存在:" + rightId);
		}
		//
		return menu.getId() + ":" + right.getValue();
	}
	/*
	 * 初始化菜单权限。
	 * @see com.examw.netplatform.service.admin.security.IMenuRightService#init()
	 */
	@Override
	public void init() throws Exception {
		logger.debug("初始化菜单权限...");
		//加载菜单集合
		final List<MenuEntity> menuEntities = this.menuDao.findMenus(null);
		if(menuEntities == null || menuEntities.size() == 0){
			throw new Exception("无菜单...");
		}
		//加载基础权限集合
		final List<Right> rights = this.rightDao.findRights(null);
		if(rights == null || rights.size() == 0){
			throw new Exception("无基础权限...");
		}
		//循环菜单权限
		for(MenuEntity menu : menuEntities){
			if(menu == null) continue;
			logger.debug("初始化菜单权限..." + menu.getName());
			for(Right right : rights){
				if(right == null) continue;
				try {
					this.update(new MenuRightInfo(menu.getId(), right.getId()));
				} catch (Exception e) {
					logger.warn("初始化菜单["+menu.getName()+"]权限["+right.getName()+"]异常:" + e.getMessage(), e);
				}
			}
		}
	}
}