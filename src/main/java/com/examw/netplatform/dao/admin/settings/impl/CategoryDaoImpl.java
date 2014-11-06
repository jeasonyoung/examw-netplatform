package com.examw.netplatform.dao.admin.settings.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.settings.ICategoryDao;
import com.examw.netplatform.dao.impl.BaseDaoImpl;
import com.examw.netplatform.domain.admin.settings.Category;
import com.examw.netplatform.model.admin.settings.CategoryInfo;
/**
 * 考试分类数据接口实现类
 * @author fengwei.
 * @since 2014年8月6日 下午12:02:19.
 */
public class CategoryDaoImpl extends BaseDaoImpl<Category> implements ICategoryDao{
	private static final Logger logger = Logger.getLogger(CategoryDaoImpl.class);
	/*
	 * 查询考试分类数据
	 * @see com.examw.test.dao.settings.ICategoryDao#findAreas(com.examw.test.model.settings.CategoryInfo)
	 */
	@Override
	public List<Category> findCategorys(CategoryInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询[考试分类]数据...");
		String hql = "from Category c where (1=1) ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(StringUtils.isEmpty(info.getOrder())) info.setOrder("asc");
			hql += " order by c." + info.getSort() + " " + info.getOrder();
		}
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据总数
	 * @see com.examw.test.dao.settings.ICategoryDao#total(com.examw.test.model.settings.CategoryInfo)
	 */
	@Override
	public Long total(CategoryInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询[考试分类]数据统计...");
		String hql = "select count(*) from Category c where (1=1) ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		return this.count(hql, parameters);
	}
	// 添加查询条件到HQL。
	private String addWhere(CategoryInfo info, String hql,Map<String, Object> parameters) {
		if (!StringUtils.isEmpty(info.getPid())) {
			hql += " and (c.parent.id = :pid or c.id = :pid)";
			parameters.put("pid", info.getPid());
		}
		if (!StringUtils.isEmpty(info.getName())) {
			hql += " and (c.name like :name)";
			parameters.put("name", "%" + info.getName() + "%");
		}
		return hql;
	}
	/*
	 * 加载一级类别。
	 * @see com.examw.test.dao.settings.ICategoryDao#loadTopCategories()
	 */
	@Override
	public List<Category> loadTopCategories() {
		if(logger.isDebugEnabled()) logger.debug("加载一级类别...");
		final String hql = "from Category c where (c.parent is null) order by c.code";
		return this.find(hql, null, null, null);
	}
	/*
	 * 加载最大类别代码。
	 * @see com.examw.test.dao.settings.ICategoryDao#loadMaxCode(java.lang.String)
	 */
	@Override
	public Integer loadMaxCode(String parentCatalogId) {
		if(logger.isDebugEnabled()) logger.debug("加载最大类别代码...");
		StringBuilder hqlBuilder = new StringBuilder();
		hqlBuilder.append("select max(c.code) from Category c where ")
		                .append(StringUtils.isEmpty(parentCatalogId) ?  " (c.parent is null) " : " (c.parent.id = :pid) ");
		Map<String, Object> parameters = new HashMap<>();
		if(!StringUtils.isEmpty(parentCatalogId)){
			parameters.put("pid",  parentCatalogId);
		}
		Object obj = this.uniqueResult(hqlBuilder.toString(), parameters);
		if(obj == null && !StringUtils.isEmpty(parentCatalogId)){
			Category data = this.load(Category.class, parentCatalogId);
			if(data == null) return null;
			return new Integer(String.format("%d0", data.getCode()));
		}
		return obj == null ? null :  (int)obj;
	}
	/*
	 * 删除数据。
	 * @see com.examw.test.dao.impl.BaseDaoImpl#delete(java.lang.Object)
	 */
	@Override
	public void delete(Category data) {
		if(data == null) return;
		int count = 0;
		if(data.getExams() != null && (count = data.getExams().size()) > 0){
			throw new RuntimeException(String.format("考试类别［%1$s］下有［%2$d］考试，暂不能删除！", data.getName(), count));
		}
		if(data.getChildren() != null && (count = data.getChildren().size()) > 0){
			throw new RuntimeException(String.format("考试类别［%1$s］下有［%2$d］子类别，暂不能删除！", data.getName(), count));
		}
		super.delete(data);
	}
}