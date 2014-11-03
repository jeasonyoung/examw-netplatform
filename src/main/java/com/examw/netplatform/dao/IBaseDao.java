package com.examw.netplatform.dao;

import java.io.Serializable;

import org.hibernate.LobHelper;
/**
 * 数据操作接口。
 * @author yangyong.
 * @since 2014-04-28.
 */
public interface IBaseDao<T> {
	/**
	 * 加载数据对象。
	 * @param c
	 * 	数据对象类型。
	 * @param id
	 * 	数据对象主键。
	 * @return 对象。
	 * */
	T load(Class<T> c,Serializable id);
	/**
	 * 保存数据对象。
	 * @param data
	 * 	数据对象。
	 * @return 主键值。
	 * */
	Serializable save(T data);
	/**
	 * 更新数据对象。
	 * @param data
	 * 	数据对象。
	 * */
	void update(T data);
	/**
	 * 保存或更新数据对象。
	 * @param data
	 * 	数据对象。
	 * */
	void saveOrUpdate(T data);
	/**
	 * 删除数据对象。
	 * @param data
	 * 	数据对象。
	 * */
	void delete(T data);
	/**
	 * 手动清除实体对象的二级缓存。
	 * @param persistentClass
	 * 实体对象类型。
	 */
	void evict(Class<?> persistentClass);
	/**
	 * 对象状态融合。
	 * @param obj
	 */
	void merge(Object obj);
	/**
	 * 获取二进制数据工具对象。
	 * @return
	 */
	LobHelper getLobHelper();
}