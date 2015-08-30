package com.examw.netplatform.service.admin.settings.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.examw.model.DataGrid;
import com.examw.netplatform.dao.admin.security.UserMapper;
import com.examw.netplatform.dao.admin.settings.AgencyMapper;
import com.examw.netplatform.dao.admin.settings.MsgBodyMapper;
import com.examw.netplatform.domain.admin.settings.MsgBody;
import com.examw.netplatform.model.admin.settings.MsgBodyInfo;
import com.examw.netplatform.service.admin.settings.IMsgService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 消息服务接口实现。
 * 
 * @author jeasonyoung
 * @since 2015年8月30日
 */
public class MsgServiceImpl implements IMsgService{
	private static final Logger logger = Logger.getLogger(MsgServiceImpl.class);
	private Map<Integer, String> statusMap,typeMap;
	private MsgBodyMapper msgDao;
	private UserMapper userDao;
	private AgencyMapper agencyDao;
	/**
	 * 设置消息数据接口。
	 * @param msgDao 
	 *	  消息数据接口。
	 */
	public void setMsgDao(MsgBodyMapper msgDao) {
		logger.debug("注入消息数据接口...");
		this.msgDao = msgDao;
	}
	/**
	 * 设置用户数据接口。
	 * @param userDao 
	 *	  用户数据接口。
	 */
	public void setUserDao(UserMapper userDao) {
		logger.debug("注入用户数据接口...");
		this.userDao = userDao;
	}
	/**
	 * 设置机构数据接口。
	 * @param agencyDao 
	 *	  机构数据接口。
	 */
	public void setAgencyDao(AgencyMapper agencyDao) {
		logger.debug("注入机构数据接口...");
		this.agencyDao = agencyDao;
	}
	/**
	 * 设置状态枚举名称集合。
	 * @param statusMap 
	 *	  状态枚举名称集合。
	 */
	public void setStatusMap(Map<Integer, String> statusMap) {
		logger.debug("注入状态枚举名称集合...");
		this.statusMap = statusMap;
	}
	/**
	 * 设置类型枚举名称集合。
	 * @param typeMap 
	 *	  类型枚举名称集合。
	 */
	public void setTypeMap(Map<Integer, String> typeMap) {
		logger.debug("注入类型枚举名称集合...");
		this.typeMap = typeMap;
	}
	/*
	 * 加载状态名称。
	 * @see com.examw.netplatform.service.admin.settings.IMsgService#loadStatusName(java.lang.Integer)
	 */
	@Override
	public String loadStatusName(Integer status) {
		logger.debug("加载状态["+status+"]名称...");
		if(this.statusMap != null && this.statusMap.size() > 0){
			return this.statusMap.get(status);
		}
		return null;
	}
	/*
	 * 加载类型名称。
	 * @see com.examw.netplatform.service.admin.settings.IMsgService#loadTypeName(java.lang.Integer)
	 */
	@Override
	public String loadTypeName(Integer type) {
		logger.debug("加载类型["+type+"]名称...");
		if(this.typeMap != null && this.typeMap.size() > 0){
			return this.typeMap.get(type);
		}
		return null;
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.admin.settings.IMsgService#datagrid(com.examw.netplatform.model.admin.settings.MsgBodyInfo)
	 */
	@Override
	public DataGrid<MsgBodyInfo> datagrid(MsgBodyInfo info) {
		logger.debug("查询数据...");
		//分页排序
		PageHelper.startPage(info.getPage(), info.getRows(), StringUtils.trimToEmpty(info.getSort()) + " " + StringUtils.trimToEmpty(info.getOrder()));
		//查询数据
		final List<MsgBody> list = this.msgDao.findMsgBodies(info);
		//分页结果
		final PageInfo<MsgBody> pageInfo = new PageInfo<MsgBody>(list);
		//初始化结果
		final DataGrid<MsgBodyInfo> grid = new DataGrid<MsgBodyInfo>();
		grid.setRows(this.changeModel(list));
		grid.setTotal(pageInfo.getTotal());
		//返回
		return grid;
	}
	//批量数据类型转换
	private List<MsgBodyInfo> changeModel(List<MsgBody> msgBodies){
		final List<MsgBodyInfo> list = new ArrayList<MsgBodyInfo>();
		if(msgBodies != null && msgBodies.size() > 0){
			for(MsgBody msgBody : msgBodies){
				if(msgBody == null) continue;
				list.add(this.conversion(msgBody));
			}
		}
		return list;
	}
	//数据类型转换
	private MsgBodyInfo conversion(MsgBody data){
		if(data != null){
			final MsgBodyInfo info = new MsgBodyInfo();
			//
			BeanUtils.copyProperties(data, info);
			//类型
			if(info.getType() != null) info.setTypeName(this.loadTypeName(info.getType()));
			//状态
			if(info.getStatus() != null) info.setStatusName(this.loadStatusName(info.getStatus()));
			//用户集合
			final List<String> students = this.msgDao.findMsgUser(info.getId());
			if(students != null && students.size() > 0){
				info.setStudentIds(students.toArray(new String[0]));
			}
			//返回
			return info;
		}
		return null;
	}
	/*
	 * 更新数据。
	 * @see com.examw.netplatform.service.admin.settings.IMsgService#update(com.examw.netplatform.model.admin.settings.MsgBodyInfo)
	 */
	@Override
	public MsgBodyInfo update(MsgBodyInfo info) {
		logger.debug("更新数据...");
		if(info == null) return null;
		//机构
		if(StringUtils.isBlank(info.getAgencyId()) ||  this.agencyDao.getAgency(info.getAgencyId()) == null){
			  throw new RuntimeException("所属机构ID不存在!");
		}
		//用户
		if(StringUtils.isBlank(info.getUserId()) || this.userDao.getUser(info.getUserId()) == null){
			throw new RuntimeException("所属用户ID不存在!");
		}
		//加载数据
		MsgBody data = StringUtils.isBlank(info.getId()) ? null : this.msgDao.getMsgBody(info.getId());
		boolean isAdded = false;
		if(isAdded = (data == null)){
			if(StringUtils.isBlank(info.getId())) info.setId(UUID.randomUUID().toString());
			
			data = new MsgBody();
		}
		//数据复制
		BeanUtils.copyProperties(info, data);
		
		if(isAdded){
			logger.debug("新增消息内容...");
			this.msgDao.insertMsgBody(data);
		}else {
			logger.debug("更新消息内容...");
			//删除消息用户
			this.msgDao.deleteMsgAllUser(info.getId());
			//更新数据
			this.msgDao.updateMsgBody(data);
		}
		//插入消息用户
		if(info.getStudentIds() != null && info.getStudentIds().length > 0){
			for(String studentId : info.getStudentIds()){	
				if(StringUtils.isBlank(studentId) || this.userDao.getUser(studentId) == null) continue;
				this.msgDao.insertMsgUser(info.getId(), studentId);
			}
		}
		//数据类型转换
		return this.conversion(data);
	}
	/*
	 * 删除数据。
	 * @see com.examw.netplatform.service.admin.settings.IMsgService#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(ids == null || ids.length == 0) return;
		for(String id : ids){
			if(StringUtils.isBlank(id)) continue;
			//删除消息用户
			this.msgDao.deleteMsgAllUser(id);
			//删除消息
			this.msgDao.deleteMsgBody(id);
		}
	}
}