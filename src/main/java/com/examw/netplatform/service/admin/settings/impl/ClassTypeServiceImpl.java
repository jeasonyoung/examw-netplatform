package com.examw.netplatform.service.admin.settings.impl;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.settings.IClassTypeDao;
import com.examw.netplatform.domain.admin.settings.ClassType;
import com.examw.netplatform.model.admin.settings.ClassTypeInfo;
import com.examw.netplatform.service.admin.settings.IClassTypeService;
import com.examw.netplatform.service.impl.BaseDataServiceImpl;
/**
 * 班级类型服务接口实现。
 * @author yangyong.
 * @since 2014-05-20.
 */
public class ClassTypeServiceImpl extends BaseDataServiceImpl<ClassType, ClassTypeInfo> implements IClassTypeService {
	private static final Logger logger = Logger.getLogger(ClassTypeServiceImpl.class);
	private IClassTypeDao classTypeDao;
	/**
	 * 设置班级类型数据接口。
	 * @param 班级类型数据接口。
	 */
	public void setClassTypeDao(IClassTypeDao classTypeDao) {
		if(logger.isDebugEnabled()) logger.debug("注入班级类型数据接口...");
		this.classTypeDao = classTypeDao;
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<ClassType> find(ClassTypeInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		return this.classTypeDao.findClassTypes(info);
	}
	/*
	 * 数据模型转换。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected ClassTypeInfo changeModel(ClassType data) {
		if(logger.isDebugEnabled()) logger.debug("数据模型转换 ClassType => ClassTypeInfo ...");
		if(data == null) return null;
		ClassTypeInfo info = new ClassTypeInfo();
		BeanUtils.copyProperties(data, info);
		return info;
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(ClassTypeInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		return this.classTypeDao.total(info);
	}
	/*
	 * 更新数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public ClassTypeInfo update(ClassTypeInfo info) {
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		if(info == null) return null;
		boolean isAdded = false;
		ClassType data = StringUtils.isEmpty(info.getId()) ? null : this.classTypeDao.load(ClassType.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())){
				info.setId(UUID.randomUUID().toString());
			}
			data = new ClassType();
		}
		BeanUtils.copyProperties(info, data);
		if(isAdded) this.classTypeDao.save(data);
		return info;
	}
	/*
	 * 删除数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled()) logger.debug(String.format("删除数据 %s ...", Arrays.toString(ids)));
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			if(StringUtils.isEmpty(ids[i])) continue;
			ClassType data = this.classTypeDao.load(ClassType.class, ids[i]);
			if(data != null){
				this.classTypeDao.delete(data);
			}
		}
	}
	/*
	 * 加载全部数据。
	 * @see com.examw.netplatform.service.admin.IClassTypeService#loadAll()
	 */
	@Override
	public List<ClassTypeInfo> loadAll() {
		if(logger.isDebugEnabled()) logger.debug("加载全部数据...");
		return this.changeModel(this.classTypeDao.findClassTypes(new ClassTypeInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public String getSort() {return "orderNo";}
			@Override
			public String getOrder() { return "asc";}
		}));
	}
}