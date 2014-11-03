package com.examw.netplatform.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Cache;
import org.hibernate.CacheMode;
import org.hibernate.LobHelper;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.IBaseDao;
/**
 * 数据操作接口实现类。
 * @author yangyong.
 * @since 2014-04-28.
 */
public class BaseDaoImpl<T> implements IBaseDao<T> {
	private static final Logger logger = Logger.getLogger(BaseDaoImpl.class);
	private SessionFactory sessionFactory;
	/**
	 * 设置SessionFactory。
	 * @param sessionFactory
	 * 	Hibernate Session 对象。
	 * */
	public void setSessionFactory(SessionFactory sessionFactory) {
		if(logger.isDebugEnabled()) logger.debug("注入sessionFactory...");
		this.sessionFactory = sessionFactory;
	}
	/**
	 * 获取当前会话。
	 * @return 当前会话。
	 * */
	protected Session getCurrentSession(){
		if(logger.isDebugEnabled()) logger.debug("获取当前会话对象...");
		return this.sessionFactory == null ? null : this.sessionFactory.getCurrentSession();
	}
	/**
	 * 加载指定主键对象。
	 * @param c
	 * 	对象类型。
	 * @param id
	 * 	主键值。
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public T load(Class<T> c, Serializable id) {
		if(logger.isDebugEnabled()) logger.debug("加载指定主键对象...");
		if(c != null && id != null){
			return (T)this.getCurrentSession().get(c, id);
		}
		return null;
	}
	/**
	 * 保存新增对象。
	 * @param data
	 * 	对象。
	 * */
	@Override
	public Serializable save(T data) {
		if(logger.isDebugEnabled()) logger.debug("保存新增对象...");
		if(data != null){
			return this.getCurrentSession().save(data);
		}
		return null;
	}
	/**
	 * 更新对象。
	 * @param data
	 * 	对象。
	 * */
	@Override
	public void update(T data) {
		if(logger.isDebugEnabled()) logger.debug("更新对象...");
		if(data != null){
			this.getCurrentSession().update(data);
		}
	}
	/**
	 * 保存或更新对象。
	 * @param data
	 * 	对象。
	 * */
	@Override
	public void saveOrUpdate(T data) {
		if(logger.isDebugEnabled()) logger.debug("保存或更新对象...");
		if(data != null) {
			this.getCurrentSession().saveOrUpdate(data);
		}
	}
	/**
	 * 删除对象。
	 * @param data
	 * 	对象。
	 * */
	@Override
	public void delete(T data) {
		if(logger.isDebugEnabled()) logger.debug("删除对象...");
		if(data != null){
			this.getCurrentSession().delete(data);
		}
	}
	/**
	 * 查找对象集合。
	 * @param hql
	 * 	HQL语句。
	 * @param parameters
	 * 	参数集合。
	 * @param page
	 * 	页码。
	 * @param rows
	 * 	页数据量
	 * <pre>
	 * 	当page与rows同时为null时，则查询全部数据。
	 * </pre>
	 * @return 结果数据集合。
	 * */
	@SuppressWarnings("unchecked") 
	protected List<T> find(String hql, Map<String, Object> parameters,Integer page, Integer rows) {
		if(logger.isDebugEnabled()) logger.debug("查找对象集合...");
		return this.query(hql, parameters, page, rows);
	}
	/**
	 *  查询数据集合。
	 * @param hql
	 * HQL语句。
	 * @param parameters
	 * 参数集合。
	 * @param page
	 * 页码。
	 * @param rows
	 * 页数据量。
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected List query(String hql, Map<String, Object> parameters,Integer page, Integer rows){
		if(logger.isDebugEnabled()) logger.debug(String.format("查询数据［hql = %1$s］［page = %2$d］［rows = %3$d］...", hql, page, rows));
		if(StringUtils.isEmpty(hql)) return null;
		Query query = this.getCurrentSession().createQuery(hql);
		if(query != null){
			this.addParameters(query, parameters);
			if(page == null && rows == null) return query.list();
			if(page == null || page < 1) page = 1;
			if(rows == null || rows < 10) rows = 10;
			return  query.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
		}
		return null;
	}
	/**
	 * 添加参数集合。
	 * @param query
	 * @param parameters
	 */
	protected void addParameters(Query query, Map<String, Object> parameters){
		this.addParameters(query, parameters, true);
	}
	/**
	 * 添加参数集合。
	 * @param query
	 * 查询对象。
	 * @param parameters
	 * 参数集合。
	 * @param hasCache
	 * 是否使用二级缓存中的查询缓存。
	 */
	protected void addParameters(Query query, Map<String, Object> parameters,boolean hasCache){
		if(logger.isDebugEnabled()) logger.debug(String.format("添加参数集合［hasCache = %s］...", hasCache));
		if(query == null || parameters == null || parameters.size() == 0) return;
		Object value = null;
		for(Map.Entry<String, Object> entry : parameters.entrySet()){
			if(entry == null || StringUtils.isEmpty(entry.getKey()) || (value = entry.getValue()) == null) continue;
			if(value.getClass().isArray()){
				query.setParameterList(entry.getKey(), (Object[])value);
			}else if(value instanceof Collection){
				query.setParameterList(entry.getKey(), (Collection<?>)value);
			}else {
				query.setParameter(entry.getKey(), value);
			}
		}
		if(hasCache){
			query.setCacheable(true);//是否启用缓存。
		}else {
			query.setCacheable(false);
			query.setCacheMode(CacheMode.IGNORE);
		}
	}
	/**
	 * 执行HQL语句。
	 * @param hql
	 * HQL语句。
	 * @param parameters
	 * 参数集合。
	 * @return
	 * 执行数据条数。
	 */
	protected Integer executeUpdate(String hql,Map<String, Object> parameters){
		if(logger.isDebugEnabled()) logger.debug(String.format("执行HQL语句［%s］...", hql));
		if(StringUtils.isEmpty(hql)) return null;
		Query query = this.getCurrentSession().createQuery(hql);
		if(query != null){
			this.addParameters(query, parameters);
			return query.executeUpdate();
		}
		return null;
	}
	/**
	 * 统计数据总数。
	 * @param hql
	 *  HQL语句。
	 * @param parameters
	 * 	参数集合。
	 * @return 数据总数。
	 * */
	protected Long count(String hql, Map<String, Object> parameters) {
		if(logger.isDebugEnabled()) logger.debug(String.format("统计数据［hql = %s］", hql));
		 Object  obj = this.uniqueResult(hql, parameters);
		 return obj == null ? null : (long)obj;
	}
	/**
	 * 查询唯一结果数据。
	 * @param hql
	 * HQL语句。
	 * @param parameters
	 * 参数集合。
	 * @return
	 * 结果数据。
	 */
	protected Object uniqueResult(String hql,Map<String, Object> parameters){
		if(logger.isDebugEnabled()) logger.debug(String.format("查询单个结果数据［hql = %s］", hql));
		if(StringUtils.isEmpty(hql)) return null;
		Query query = this.getCurrentSession().createQuery(hql);
		if(query != null){
			this.addParameters(query, parameters);
			return query.uniqueResult();
		}
		return null;
	}
	/*
	 * 手动清除二级缓存。
	 * @see com.examw.netplatform.dao.IBaseDao#evict(java.lang.Class)
	 */
	@Override
	public void evict(Class<?> persistentClass) {
		if(logger.isDebugEnabled()) logger.debug("手动清除二级缓存...");
		if(persistentClass != null && this.sessionFactory != null){
			Cache cache = this.sessionFactory.getCache();
			if(cache != null) cache.evictEntityRegion(persistentClass);
		}
	}
	/*
	 * 对象状态融合。
	 * @see com.examw.netplatform.dao.IBaseDao#merge(java.lang.Object)
	 */
	@Override
	public void merge(Object obj) {
		if(logger.isDebugEnabled()) logger.debug("对象状态融合...");
		this.getCurrentSession().merge(obj);
	}
	/*
	 * 获取二进制数据工具对象。
	 * @see com.examw.netplatform.dao.IBaseDao#getLobHelper()
	 */
	@Override
	public LobHelper getLobHelper() {
		if(logger.isDebugEnabled()) logger.debug("获取二进制数据工具对象...");
		return this.getCurrentSession().getLobHelper();
	}
}