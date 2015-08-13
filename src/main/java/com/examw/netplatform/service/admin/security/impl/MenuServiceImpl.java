package com.examw.netplatform.service.admin.security.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.ClassPathResource;

import com.examw.configuration.ModuleDefine;
import com.examw.configuration.ModuleParse;
import com.examw.configuration.ModuleSystem;
import com.examw.model.DataGrid;
import com.examw.netplatform.dao.admin.security.MenuMapper;
import com.examw.netplatform.domain.admin.security.MenuEntity;
import com.examw.netplatform.model.admin.security.MenuInfo;
import com.examw.netplatform.service.admin.security.IMenuService;
/**
 * 菜单服务。
 * @author yangyong
 * @since 2014-04-28.
 */
public class MenuServiceImpl  implements IMenuService {
	private static final Logger logger = Logger.getLogger(MenuServiceImpl.class);
	private MenuMapper menuDao;
	private String menuFile,systemId,systemName;
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
	 * 设置菜单文件。
	 * @param menuFile
	 * 菜单文件。
	 */
	public void setMenuFile(String menuFile) {
		logger.debug("注入菜单文件...");
		this.menuFile = menuFile;
	}
	/**
	 * 设置菜单文件中系统ID。
	 * @param systemId
	 * 系统ID。
	 */
	public void setSystemId(String systemId) {
		logger.debug("注入当前系统ID...");
		this.systemId = systemId;
	}
	/**
	 * 设置系统名称。
	 * @param systemName 
	 *	  系统名称。
	 */
	public void setSystemName(String systemName) {
		logger.debug("注入系统名称...");
		this.systemName = systemName;
	}
	/*
	 * 加载系统名称。
	 * @see com.examw.netplatform.service.admin.IMenuService#loadSystemName()
	 */
	@Override
	public String loadSystemName() {
		logger.debug(String.format("加载系统名称［%s］...", this.systemName));
		return this.systemName;
	}
	/*
	 * 加载全部菜单数据。
	 * @see com.examw.netplatform.service.admin.security.IMenuService#loadAllMenus()
	 */
	@Override
	public List<MenuInfo> loadAllMenus() {
		logger.debug("加载全部菜单数据...");
		return this.changeModel(this.menuDao.findMenus(null));
	}
	/*
	 * 重载。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#datagrid(java.lang.Object)
	 */
	@Override
	public DataGrid<MenuInfo> datagrid(MenuInfo info) {
		DataGrid<MenuInfo> grid = new DataGrid<MenuInfo>();
		grid.setRows(this.changeModel(this.menuDao.findMenus(info)));
		return grid;
	}
	//数据模型转换
	private List<MenuInfo> changeModel(List<MenuEntity> entities) {
		logger.debug("数据模型转换 MenuEntity => MenuInfo ...");
		List<MenuInfo> list = new ArrayList<MenuInfo>();
		if(entities != null && entities.size() > 0){
			for(MenuEntity entity : entities){
				if(entity == null) continue;
				MenuInfo info = new MenuInfo();
				BeanUtils.copyProperties(entity, info);
				list.add(info);
			}
		}
		return list;
	}
	/*
	 * 更新数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public MenuInfo update(MenuInfo info) {
		logger.debug("更新数据...");
		if(info != null){
			boolean isAdded = StringUtils.isBlank(info.getId());
			if(isAdded){
				info.setId(UUID.randomUUID().toString());
				this.menuDao.insertMenu(info);
			}else {
				this.menuDao.updateMenu(info);
			}
		}
		return info;
	}
	/*
	 * 删除数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		logger.debug("删除数据..." + StringUtils.join(ids,","));
		if(ids == null || ids.length == 0) return;		
		for(String id : ids){
			if(StringUtils.isBlank(id)) continue;
			if(this.menuDao.hasChildMenus(id)){
				logger.debug("菜单["+id+"]下有子菜单，不能删除!");
				continue;
			}
			this.menuDao.deleteMenu(id);
			logger.debug("删除菜单..." + id);
		}
	}
	/*
	 *  初始化菜单数据。
	 * @see com.examw.netplatform.service.admin.security.IMenuService#init()
	 */
	@Override
	public void init() throws Exception {
		logger.debug("开始初始化菜单数据...");
		if(StringUtils.isBlank(this.systemId)) throw new Exception("系统ID为空！");
		if(StringUtils.isBlank(this.menuFile)) throw new Exception("菜单配置文件为空！");
		final ClassPathResource resource = new ClassPathResource(this.menuFile);
		if(!resource.exists()) throw new Exception("菜单配置文件不存在！");
		final ModuleSystem data = ModuleParse.loadModuleSystem(resource.getInputStream(), this.systemId);
		if(data == null) throw new Exception(String.format("模块系统［%s］不存在！", this.systemId));
		if(data.getModules() != null && data.getModules().size() > 0){
			if(logger.isDebugEnabled()) logger.debug("添加菜单数据...");
			for(ModuleDefine define : data.getModules()){
				if(define == null) continue;
				this.addMenu(define, null);
			}
		}
	}
	//添加菜单数据。
	private void addMenu(ModuleDefine define, ModuleDefine parent){
		logger.debug(String.format("添加菜单：%s", define));
		if(define == null || StringUtils.isBlank(define.getId())) return;
		MenuEntity entity =  this.menuDao.getMenu(define.getId());
		boolean isAdded = false;
		if(isAdded = (entity == null)){
			entity = new MenuEntity();
			entity.setId(define.getId());
		}
		entity.setIcon(define.getIcon());
		entity.setName(define.getName());
		entity.setUri(define.getUri());
		entity.setOrderNo(define.getOrder());
		if(parent != null && !StringUtils.equalsIgnoreCase(parent.getId(), entity.getId())){
			entity.setPid(parent.getId());
		}
		if(isAdded){
			this.menuDao.insertMenu(entity);
			logger.debug("新增菜单:" + entity.getId());
		}else {
			this.menuDao.updateMenu(entity);
			logger.debug("更新菜单:" + entity.getId());
		}
		//子菜单。
		if(define.getModules() != null && define.getModules().size() > 0){
			for(ModuleDefine m : define.getModules()){
				if(m == null) continue;
				this.addMenu(m, define);
			}
		}
	}
}