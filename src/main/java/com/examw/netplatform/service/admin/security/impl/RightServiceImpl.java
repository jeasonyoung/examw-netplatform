package com.examw.netplatform.service.admin.security.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.examw.model.DataGrid;
import com.examw.netplatform.dao.admin.security.RightMapper;
import com.examw.netplatform.domain.admin.security.Right;
import com.examw.netplatform.model.admin.security.RightInfo;
import com.examw.netplatform.service.admin.security.DefaultRightType;
import com.examw.netplatform.service.admin.security.IRightService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 基础权限服务接口实现。
 * @author yangyong.
 * @since 2014-05-03.
 */
public class RightServiceImpl implements IRightService {
	private static final Logger logger = Logger.getLogger(RightServiceImpl.class);
	private RightMapper rightDao;
	private Map<Integer, String> rightNameMap;
	/**
	 * 设置基础权限数据接口。
	 * @param rightDao
	 * 基础权限数据接口。
	 */
	public void setRightDao(RightMapper rightDao) {
		logger.debug("注入基础权限数据接口...");
		this.rightDao = rightDao;
	}
	/**
	 * 设置权限名称集合。
	 * @param rightNameMap
	 * 权限名称集合。
	 */
	public void setRightNameMap(Map<Integer, String> rightNameMap) {
		logger.debug("注入权限名称集合...");
		this.rightNameMap = rightNameMap;
	}
	/*
	 * 加载全部权限数据集合。
	 * @see com.examw.netplatform.service.admin.security.IRightService#loadAllRights()
	 */
	@Override
	public List<RightInfo> loadAllRights() {
		logger.debug("加载全部权限数据集合...");
		//排序
		PageHelper.orderBy("orderNo");
		//查询返回
		return this.changeModel(this.rightDao.findRights(null));
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.admin.security.IRightService#datagrid(com.examw.netplatform.model.admin.security.RightInfo)
	 */
	@Override
	public DataGrid<RightInfo> datagrid(RightInfo info) {
		logger.debug("查询数据...");
		//初始化
		DataGrid<RightInfo> grid = new DataGrid<RightInfo>();
		//分页排序处理
		PageHelper.startPage(info.getPage(), info.getRows(), info.getOrder() + " " + StringUtils.trimToEmpty(info.getSort()));
		//查询数据
		final List<Right> list = this.rightDao.findRights(StringUtils.trimToNull(info.getName()));
		//分页信息
		final PageInfo<Right> pageInfo = new PageInfo<Right>(list);
		//设置数据
		grid.setRows(this.changeModel(list));
		grid.setTotal(pageInfo.getTotal());
		//返回
		return grid;
	}
	//数据类型转换。
	private List<RightInfo> changeModel(List<Right> entities){
		List<RightInfo> list = new ArrayList<RightInfo>();
		if(entities != null && entities.size() > 0){
			for(Right right : entities){
				if(right == null) continue;
				RightInfo info = new RightInfo();
				BeanUtils.copyProperties(right, info);
				list.add(info);
			}
		}
		return list;
	}
	/*
	 * 删除数据
	 * @see com.examw.netplatform.service.admin.security.IRightService#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		logger.debug("删除数据..." + StringUtils.join(ids,","));
		if(ids != null && ids.length > 0){
			for(String id : ids){
				if(StringUtils.isBlank(id)) continue;
				this.rightDao.deleteRight(id);
			}
		}
	}
	/*
	 * 初始化基础权限。
	 * @see com.examw.netplatform.service.admin.security.IRightService#init()
	 */
	@Override
	public void init() throws Exception {
		logger.debug("初始化基础权限...");
		if(this.rightNameMap == null || this.rightNameMap.size() == 0)
			throw new Exception("未设置默认权限名称设置！");
		boolean isAdded = false;
		for(DefaultRightType right : DefaultRightType.values()){
			 String rightName = this.rightNameMap.get(right.getValue());
			 if(StringUtils.isEmpty(rightName)) 
				 rightName = String.format("%s", right);
			 logger.debug(String.format("初始化［%1$d］［%2$s］权限...", right.getValue(), rightName));
			 //加载权限
			 Right data = this.rightDao.getRight(String.valueOf(right.getValue()));
			 if(isAdded = (data == null)){
				 data = new Right();
				 data.setId(String.valueOf(right.getValue()));
			 }
			 //赋值
			 data.setName(rightName);
			 data.setValue(right.getValue());
			 data.setOrderNo(right.getValue());
			 //
			 if(isAdded){
				 logger.debug("插入权限..." + right);
				 this.rightDao.insertRight(data);
			 }else {
				 logger.debug("更新权限..." + right);
				 this.rightDao.updateRight(data);
			}
		}
	}
}