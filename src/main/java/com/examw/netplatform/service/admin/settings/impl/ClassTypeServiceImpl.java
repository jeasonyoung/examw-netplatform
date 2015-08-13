package com.examw.netplatform.service.admin.settings.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.examw.model.DataGrid;
import com.examw.netplatform.dao.admin.settings.ClassTypeMapper;
import com.examw.netplatform.domain.admin.settings.ClassType;
import com.examw.netplatform.model.admin.settings.ClassTypeInfo;
import com.examw.netplatform.service.admin.settings.IClassTypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/**
 * 班级类型服务接口实现。
 * @author yangyong.
 * @since 2014-05-20.
 */
public class ClassTypeServiceImpl implements IClassTypeService {
	private static final Logger logger = Logger.getLogger(ClassTypeServiceImpl.class);
	private ClassTypeMapper classTypeDao;
	/**
	 * 设置班级类型数据接口。
	 * @param 班级类型数据接口。
	 */
	public void setClassTypeDao(ClassTypeMapper classTypeDao) {
		if(logger.isDebugEnabled()) logger.debug("注入班级类型数据接口...");
		this.classTypeDao = classTypeDao;
	}
	/*
	 * 加载最大排序号。
	 * @see com.examw.netplatform.service.admin.settings.IClassTypeService#loadMaxOrder()
	 */
	@Override
	public Integer loadMaxOrder() {
		if(logger.isDebugEnabled()) logger.debug("加载最大排序号...");
		return this.classTypeDao.loadMaxOrder();
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.admin.settings.IClassTypeService#datagrid(com.examw.netplatform.model.admin.settings.ClassTypeInfo)
	 */
	@Override
	public DataGrid<ClassTypeInfo> datagrid(ClassTypeInfo info) {
		logger.debug("查询数据...");
		//分页排序
		PageHelper.startPage(info.getPage(), info.getRows(), StringUtils.trimToEmpty(info.getOrder()) + " " + StringUtils.trimToEmpty(info.getSort()));
		//查询数据
		final List<ClassType> list = this.classTypeDao.findClassTypes(info);
		//分页信息
		final PageInfo<ClassType> pageInfo = new PageInfo<ClassType>(list);
		//初始化
		final DataGrid<ClassTypeInfo> grid = new DataGrid<ClassTypeInfo>();
		//赋值
		grid.setRows(this.changeModel(list));
		grid.setTotal(pageInfo.getTotal());
		//返回
		return grid;
	}
	//批量类型转换
	private List<ClassTypeInfo> changeModel(List<ClassType> classTypes){
		final List<ClassTypeInfo> list = new ArrayList<ClassTypeInfo>();
		if(classTypes != null && classTypes.size() > 0){
			for(ClassType data : classTypes){
				if(data == null) continue;
				list.add(this.conversion(data));
			}
		}
		return list;
	}
	//类型转换。
	private ClassTypeInfo conversion(ClassType data){
		return (ClassTypeInfo)data;
	}
	/*
	 * 查询全部班级类型。
	 * @see com.examw.netplatform.service.admin.settings.IClassTypeService#loadAll()
	 */
	@Override
	public List<ClassTypeInfo> loadAll() {
		logger.debug("查询全部班级类型...");
		return this.changeModel(this.classTypeDao.findClassTypes(null));
	}
	/*
	 * 更新数据。
	 * @see com.examw.netplatform.service.admin.settings.IClassTypeService#update(com.examw.netplatform.model.admin.settings.ClassTypeInfo)
	 */
	@Override
	public ClassTypeInfo update(ClassTypeInfo info) {
		logger.debug("更新数据...");
		if(info == null) return null;
		ClassType data = StringUtils.isBlank(info.getId()) ? null : this.classTypeDao.getClassType(info.getId());
		boolean isAdded = false;
		if(isAdded = (data == null)){
			if(StringUtils.isBlank(info.getId()))
				info.setId(UUID.randomUUID().toString());
			//检查唯一性
			if(this.classTypeDao.hasClassTypeCode(info.getCode())){
				throw new RuntimeException("班级类型代码["+info.getCode()+"]已存在!");
			}
			data = new ClassType();
		}
		//赋值
		BeanUtils.copyProperties(info, data);
		//保存
		if(isAdded){
			logger.debug("新增班级类型...");
			this.classTypeDao.insertClassType(data);
		}else {
			logger.debug("更新班级类型...");
			this.classTypeDao.updateClassType(data);
		}
		//返回
		return this.conversion(data);
	}
	/*
	 * 删除班级类型数据。
	 * @see com.examw.netplatform.service.admin.settings.IClassTypeService#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		logger.debug(" 删除班级类型数据..." + StringUtils.join(ids,","));
		if(ids != null && ids.length > 0){
			for(String id : ids){
				if(StringUtils.isBlank(id)) continue;
				this.classTypeDao.deleteClassType(id);
			}
		}
	}
}