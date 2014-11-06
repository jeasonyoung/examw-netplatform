package com.examw.netplatform.dao.admin.security.impl;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.security.IMenuDao;
import com.examw.netplatform.dao.impl.BaseDaoImpl;
import com.examw.netplatform.domain.admin.security.Menu;
import com.examw.netplatform.model.admin.security.MenuInfo;

/**
 * 菜单数据操作实现类。
 * @author yangyong.
 * @since 2014-04-28.
 */
public class MenuDaoImpl extends BaseDaoImpl<Menu> implements IMenuDao{
	private static final Logger logger = Logger.getLogger(MenuDaoImpl.class);
	/*
	 * 加载一级菜单。
	 * @see com.examw.netplatform.dao.admin.security.IMenuDao#loadTopMenus()
	 */
	@Override
	public List<Menu> loadTopMenus() {
		if(logger.isDebugEnabled()) logger.debug("加载一级菜单...");
		final String hql = "from Menu m where (m.parent is null) order by m.orderNo asc";
		return this.find(hql, null, null, null);
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.dao.admin.security.IMenuDao#findMenus(com.examw.netplatform.model.admin.security.MenuInfo)
	 */
	@Override
	public List<Menu> findMenus(MenuInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		String hql = "from Menu m where (m.parent is null) ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(hql, info, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(StringUtils.isEmpty(info.getOrder())) info.setOrder("asc");
			hql += " order by m." + info.getSort() + " " + info.getOrder();
		}
		return this.find(hql, parameters, null, null);
	}
	//添加查询条件。
	private String addWhere(String hql,MenuInfo info, Map<String, Object> parameters){
		if(!StringUtils.isEmpty(info.getName())){
			hql += " and ((m.id like :name) or (m.name like :name) or (m.uri like :name)) ";
			parameters.put("name", "%"+ info.getName() +"%");
		}
		return hql;
	}
	/*
	 * 删除数据。
	 * @see com.examw.netplatform.dao.impl.BaseDaoImpl#delete(java.lang.Object)
	 */
	@Override
	public void delete(Menu data) {
		if(logger.isDebugEnabled()) logger.debug("删除数据...");
		if(data == null) return;
		int count = 0;
		if(data.getRights() != null && (count = data.getRights().size()) > 0){
			throw new RuntimeException(String.format("菜单已与［%d］权限相关联！", count));
		}
		if(data.getChildren() != null && (count = data.getChildren().size()) > 0){
			throw new RuntimeException(String.format("菜单［%1$s］下有［%2$d］子菜单！",data.getName(), count));
		}
		super.delete(data);
	}
}