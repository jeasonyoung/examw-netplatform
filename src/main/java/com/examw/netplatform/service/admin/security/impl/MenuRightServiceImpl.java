package com.examw.netplatform.service.admin.security.impl;

import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.security.IMenuDao;
import com.examw.netplatform.dao.admin.security.IMenuRightDao;
import com.examw.netplatform.dao.admin.security.IRightDao;
import com.examw.netplatform.domain.admin.security.Menu;
import com.examw.netplatform.domain.admin.security.MenuRight;
import com.examw.netplatform.domain.admin.security.Right;
import com.examw.netplatform.model.admin.security.MenuRightInfo;
import com.examw.netplatform.model.admin.security.RightInfo;
import com.examw.netplatform.service.admin.security.IMenuRightService;
import com.examw.netplatform.service.impl.BaseDataServiceImpl;

/**
 * 菜单权限服务接口。
 * @author yangyong.
 * @since 2014-05-04.
 */
public class MenuRightServiceImpl extends BaseDataServiceImpl<MenuRight, MenuRightInfo> implements IMenuRightService {
	private static final Logger logger = Logger.getLogger(MenuRightServiceImpl.class);
	private IMenuRightDao menuRightDao;
	private IMenuDao menuDao;
	private IRightDao rightDao;
	/**
	 * 设置菜单权限数据接口。
	 * @param menuRightDao
	 * 菜单权限数据接口。
	 */
	public void setMenuRightDao(IMenuRightDao menuRightDao) {
		if(logger.isDebugEnabled()) logger.debug("注入菜单权限数据接口...");
		this.menuRightDao = menuRightDao;
	}
	/**
	 * 设置菜单数据接口。
	 * @param menuDao
	 * 菜单数据接口。
	 */
	public void setMenuDao(IMenuDao menuDao) {
		if(logger.isDebugEnabled()) logger.debug("注入菜单数据接口...");
		this.menuDao = menuDao;
	}
	/**
	 * 设置权限数据接口。
	 * @param rightDao
	 * 权限数据接口。
	 */
	public void setRightDao(IRightDao rightDao) {
		if(logger.isDebugEnabled()) logger.debug("注入权限数据接口...");
		this.rightDao = rightDao;
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<MenuRight> find(MenuRightInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		return this.menuRightDao.findMenuRights(info);
	}
    /*
     * 数据模型转换。
     * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
     */
	@Override
	protected MenuRightInfo changeModel(MenuRight data) {
		if(logger.isDebugEnabled()) logger.debug("数据模型转换 MenuRight => MenuRightInfo ...");
		if(data == null) return null;
		MenuRightInfo info = new MenuRightInfo();
		BeanUtils.copyProperties(data, info);
		if(data.getMenu() != null){
			info.setMenuId(data.getId());
			info.setMenuName(data.getMenu().getName());
		}
		if(data.getRight() != null){
			info.setRightId(data.getRight().getId());
			info.setRightName(data.getRight().getName());
		}
		return info;
	}
    /*
     * 查询数据统计。
     * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#total(java.lang.Object)
     */
	@Override
	protected Long total(MenuRightInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		return this.menuRightDao.total(info);
	}
	/*
	 * 更新数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public MenuRightInfo update(MenuRightInfo info) {
		if(logger.isDebugEnabled())logger.debug("更新数据...");
		if(info == null) return null;
		String err =  null;
		if(StringUtils.isEmpty(info.getMenuId())){
			err = String.format("未选择菜单ID！", info.getMenuId());
			if(logger.isDebugEnabled()) logger.error(err);
			throw new RuntimeException(err);
		}
		if(StringUtils.isEmpty(info.getRightId())){
			err = String.format("未选择权限ID！", info.getRightId());
			if(logger.isDebugEnabled()) logger.error(err);
			throw new RuntimeException(err);
		}
		boolean isAdded = false;
		MenuRight data = StringUtils.isEmpty(info.getId()) ? null : this.menuRightDao.load(MenuRight.class, info.getId());
		if(isAdded = (data == null)){
			data = this.menuRightDao.loadMenuRight(info.getMenuId(), info.getRightId());
		}
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())) info.setId(UUID.randomUUID().toString());
			data = new MenuRight();
		}
		Menu menu = this.menuDao.load(Menu.class, info.getMenuId());
		if(menu == null){
			err = String.format("菜单［%s］不存在！", info.getMenuId());
			if(logger.isDebugEnabled()) logger.error(err);
			throw new RuntimeException(err);
		}
		if(menu.getChildren() != null && menu.getChildren().size() > 0){
			err = String.format("菜单［%s］必须为叶子菜单才能赋予权限！", info.getMenuId());
			if(logger.isDebugEnabled()) logger.error(err);
			throw new RuntimeException(err);
		}
		Right right = this.rightDao.load(Right.class, info.getRightId());
		if(right == null){
			err = String.format("权限［%s］不存在！", info.getRightId());
			if(logger.isDebugEnabled()) logger.error(err);
			throw new RuntimeException(err);
		}
		data.setMenu(menu);
		data.setRight(right);
		if(isAdded) this.menuRightDao.save(data);
		return this.changeModel(data);
	}
	/*
	 * 删除数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled())logger.debug("删除数据...");
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			MenuRight data = this.menuRightDao.load(MenuRight.class, ids[i]);
			if(data != null) {
				if(logger.isDebugEnabled()) logger.debug("删除数据：" + ids[i]);
				this.menuRightDao.delete(data);
			}
		}
	}
	/*
	 * 初始化。
	 * @see com.examw.wechat.service.security.IMenuRightService#init()
	 */
	@Override
	public void init() throws Exception {
		if(logger.isDebugEnabled()) logger.debug("初始化菜单权限...");
		List<Right> rights = this.rightDao.findRights(new RightInfo());
		if(rights == null || rights.size() == 0){
			throw new RuntimeException("未有权限数据！");
		}
		List<Menu> menus = this.menuDao.loadTopMenus();
		if(menus == null || menus.size() == 0){
			throw new RuntimeException("未有菜单数据！");
		}
		//添加数据。
		for(int i = 0; i < menus.size(); i++){
			if(menus.get(i) == null) continue;
			this.addMenuRights(menus.get(i), rights);
		}
	}
	//添加菜单权限。
	private void addMenuRights(Menu menu, List<Right> rights){
		if(menu == null || rights == null || rights.size() == 0) return;
		if(menu.getChildren() != null && menu.getChildren().size() > 0){
			for(Menu child : menu.getChildren()){
				this.addMenuRights(child, rights);
			}
		}else {
			for(Right right : rights){
				if(right == null) continue;
				if(this.menuRightDao.loadMenuRight(menu.getId(), right.getId()) == null){
					MenuRight data = new MenuRight(menu, right);
					data.setId(UUID.randomUUID().toString());
					data.setCode(String.format("%1$s:%2$d",menu.getId(),right.getValue()));
					this.menuRightDao.save(data);
					if(logger.isDebugEnabled()){
						logger.debug(String.format("添加菜单[%1$s,%2$s]权限[%3$s,%4$s]",menu.getName(),menu.getId(),right.getName(),data.getCode()));
					}
				}
			}
		}
	}
}