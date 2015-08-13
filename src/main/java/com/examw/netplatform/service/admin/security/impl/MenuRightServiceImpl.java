package com.examw.netplatform.service.admin.security.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.examw.model.DataGrid;
import com.examw.model.TreeNode;
import com.examw.netplatform.dao.admin.security.MenuMapper;
import com.examw.netplatform.dao.admin.security.MenuRightMapper;
import com.examw.netplatform.dao.admin.security.RightMapper;
import com.examw.netplatform.domain.admin.security.MenuEntity;
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
		PageHelper.startPage(info.getPage(), info.getRows());
		//查询数据
		final List<MenuRightInfo> list = this.menuRightDao.findMenuRights(info);
		//分页信息
		final PageInfo<MenuRightInfo> pageInfo = new PageInfo<MenuRightInfo>(list);
		//设置数据
		grid.setRows(list);
		grid.setTotal(pageInfo.getTotal());
		//返回
		return grid;
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
	/*
	 * 加载全部的菜单权限树。
	 * @see com.examw.netplatform.service.admin.security.IMenuRightService#loadAllMenuRights()
	 */
	@Override
	public List<TreeNode> loadAllMenuRights() {
		logger.debug("加载全部的菜单权限树...");
		List<TreeNode> list = new ArrayList<TreeNode>();
		//菜单
		final List<MenuEntity>  menuEntities = this.menuDao.findMenus(null);
		if(menuEntities != null && menuEntities.size() > 0){
			list = new MenuTreeUtils(menuEntities).parse();
			if(list != null && list.size() > 0){
				 for(TreeNode node : list){
					 if(node == null) continue;
					 this.createMenuRight(node);
				 }
			}
		}
		return list;
	}
	//创建菜单权限
	private void createMenuRight(TreeNode node){
		//节点为NULL
		if(node == null) return;
		//当前节点
		if(StringUtils.isNotBlank(node.getId()) && node.getAttributes() != null && node.getAttributes().containsValue("menu")){
			//加载菜单权限
			final List<MenuRightInfo> listMenuRights = this.menuRightDao.findMenuRights(new MenuRightInfo(node.getId(), null));
			if(listMenuRights != null && listMenuRights.size() > 0){
				//初始化
				if(node.getChildren() == null){
					node.setChildren(new ArrayList<TreeNode>());
				}
				//添加菜单权限
				final Map<String, Object> right_attributes = new HashMap<>();
				right_attributes.put("type", "right");
				//
				for(MenuRightInfo info : listMenuRights){
					if(info == null) continue;
					final TreeNode rightNode = new TreeNode(info.getId(), info.getMenuName() + "-" + info.getRightName());
					rightNode.setAttributes(right_attributes);
					node.getChildren().add(rightNode);
				}
			}
		}
		//存在子节点
		if(node.getChildren() != null && node.getChildren().size() > 0){
			for(TreeNode child : node.getChildren()){
				this.createMenuRight(child);
			}
		}
	}
	
	//解析菜单树
	private class MenuTreeUtils{
		private final List<MenuEntity> menuEntities;
		private final Map<String, TreeNode> treeCacheMap;
		/**
		 * 构造函数。
		 * @param menuEntities
		 */
		public MenuTreeUtils(List<MenuEntity>  menuEntities){
			this.menuEntities = new ArrayList<MenuEntity>(menuEntities);
			this.treeCacheMap = new HashMap<String, TreeNode>(this.menuEntities.size());
		}
		/**
		 * 解析为树集合。
		 * @return
		 */
		public synchronized List<TreeNode> parse(){
			final List<TreeNode> treeNodes = new ArrayList<TreeNode>();
			if(this.menuEntities != null && this.menuEntities.size() > 0){
				//初始化
				final List<MenuEntity> addedEntities = new ArrayList<MenuEntity>(this.menuEntities.size());
				while(this.menuEntities.size() > 0){
					//清空
					addedEntities.clear();
					//循环添加
					for(MenuEntity menu : this.menuEntities){
						final TreeNode node = this.createNode(menu);
						//根节点
						if(StringUtils.isBlank(menu.getPid())){
							//添加到根节点
							treeNodes.add(node);
							//添加到缓存
							this.treeCacheMap.put(menu.getId(), node);
							//添加到已添加集合。
							addedEntities.add(menu);
						}else {
							//查找父节点
							final TreeNode parent = this.treeCacheMap.get(menu.getId());
							if(parent != null){
								//初始化子集合
								if(parent.getChildren() == null){
									parent.setChildren(new ArrayList<TreeNode>());
								}
								//添加到子集合
								parent.getChildren().add(node);
								//添加到缓存
								this.treeCacheMap.put(menu.getId(), node);
								//添加到已添加集合。
								addedEntities.add(menu);
							}
						}
					}
					//存在孤子结点，忽略
					if(addedEntities.size() == 0){
						logger.warn("存在孤子结点，忽略!");
						break;
					}
					//移除已添加的
					if(addedEntities.size() > 0 && this.menuEntities.size() > 0){
						this.menuEntities.removeAll(addedEntities);
					}
				}
			}
			//清空数据
			if(this.treeCacheMap.size() > 0)this.treeCacheMap.clear();
			//返回
			return treeNodes;
		}
		//
		private TreeNode createNode(MenuEntity menu){
			final TreeNode tv = new TreeNode(menu.getId(), menu.getName());
			final  Map<String, Object> attributes = new HashMap<>();
			attributes.put("type", "menu");
			tv.setAttributes(attributes);
			return tv;
		}
	}
}