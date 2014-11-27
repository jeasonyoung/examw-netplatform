package com.examw.netplatform.service.admin.teachers.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.teachers.IPracticeDao;
import com.examw.netplatform.dao.admin.teachers.IStructureDao;
import com.examw.netplatform.domain.admin.teachers.Practice;
import com.examw.netplatform.domain.admin.teachers.Structure;
import com.examw.netplatform.model.admin.teachers.StructureInfo;
import com.examw.netplatform.service.admin.teachers.IStructureService;

/**
 * 随堂练习结构服务接口实现类。
 * 
 * @author yangyong
 * @since 2014年11月24日
 */
public class StructureServiceImpl implements IStructureService {
	private static final Logger logger = Logger.getLogger(StructureServiceImpl.class);
	private IStructureDao structureDao;
	private IPracticeDao practiceDao;
	/**
	 * 设置随堂练习结构数据接口。
	 * @param structureDao 
	 *	  随堂练习结构数据接口。
	 */
	public void setStructureDao(IStructureDao structureDao) {
		if(logger.isDebugEnabled()) logger.debug("注入随堂练习结构数据接口...");
		this.structureDao = structureDao;
	}
	/**
	 * 设置随堂练习数据接口。
	 * @param practiceDao 
	 *	  随堂练习数据接口。
	 */
	public void setPracticeDao(IPracticeDao practiceDao) {
		if(logger.isDebugEnabled()) logger.debug("注入随堂练习数据接口...");
		this.practiceDao = practiceDao;
	}
	/*
	 *  加载随堂练习结构集合。
	 * @see com.examw.netplatform.service.admin.teachers.IStructureService#loadPracticeStructures(java.lang.String)
	 */
	@Override
	public List<StructureInfo> loadPracticeStructures(String practiceId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载随堂练习［%s］结构集合...", practiceId));
		return this.changeModel(this.structureDao.findStructures(practiceId));
	}
	//数据模型转换。
	private List<StructureInfo> changeModel(List<Structure> structures){
		List<StructureInfo> list = new ArrayList<>();
		if(structures != null && structures.size() > 0){
			for(Structure structure : structures){
				if(structure == null) continue;
				StructureInfo info = this.changeModel(structure);
				if(info != null) list.add(info);
			}
			if(list.size() > 0) Collections.sort(list);
		}
		return list;
	}
	//数据模型转换
	private StructureInfo changeModel(Structure structure){
		if(structure == null) return null;
		StructureInfo info = new StructureInfo();
		BeanUtils.copyProperties(structure, info);
		Practice practice = null;
		if((practice = structure.getPractice()) != null){
			info.setPracticeId(practice.getId());
		}
		return info;
	}
	/*
	 * 加载最大排序号。
	 * @see com.examw.netplatform.service.admin.teachers.IStructureService#loadMaxOrder(java.lang.String)
	 */
	@Override
	public Integer loadMaxOrder(String practiceId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载随堂练习［%s］的最大排序号...", practiceId));
		return this.structureDao.loadMaxOrder(practiceId);
	}
	/*
	 * 更新数据。
	 * @see com.examw.netplatform.service.admin.teachers.IStructureService#update(com.examw.netplatform.model.admin.teachers.StructureInfo)
	 */
	@Override
	public StructureInfo update(StructureInfo info) {
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		boolean isAdded = false;
		Structure structure = StringUtils.isEmpty(info.getId()) ? null : this.structureDao.load(Structure.class, info.getId());
		if(isAdded = (structure == null)){
			if(StringUtils.isEmpty(info.getId())) info.setId(UUID.randomUUID().toString());
			structure = new Structure();
		}
		BeanUtils.copyProperties(info, structure);
		if(StringUtils.isEmpty(info.getPracticeId())) throw new RuntimeException("所属随堂练习ID为空！");
		Practice practice = this.practiceDao.load(Practice.class, info.getPracticeId());
		if(practice == null) throw new RuntimeException(String.format("所属随堂练习［%s］不存在！", info.getPracticeId()));
		structure.setPractice(practice);
		if(isAdded) this.structureDao.save(structure);
		return this.changeModel(structure);
	}
	/*
	 * 删除数据。
	 * @see com.examw.netplatform.service.admin.teachers.IStructureService#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled()) logger.debug(String.format("删除数据:%s...", Arrays.toString(ids)));
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length;i++){
			if(StringUtils.isEmpty(ids[i])) continue;
			Structure structure = this.structureDao.load(Structure.class, ids[i]);
			if(structure != null){
				if(logger.isDebugEnabled()) logger.debug(String.format("删除数据:%s...", ids[i]));
				this.structureDao.delete(structure);
			}
		}
	}
}