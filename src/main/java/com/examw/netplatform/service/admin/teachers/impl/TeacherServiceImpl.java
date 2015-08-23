package com.examw.netplatform.service.admin.teachers.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.examw.model.DataGrid;
import com.examw.netplatform.dao.admin.courses.ClassMapper;
import com.examw.netplatform.dao.admin.settings.AgencyMapper;
import com.examw.netplatform.dao.admin.teachers.TeacherMapper;
import com.examw.netplatform.domain.admin.courses.ClassPlan;
import com.examw.netplatform.domain.admin.settings.Agency;
import com.examw.netplatform.domain.admin.teachers.Teacher;
import com.examw.netplatform.model.admin.teachers.TeacherInfo;
import com.examw.netplatform.service.admin.teachers.ITeacherService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 主讲教师服务实现。
 * 
 * @author jeasonyoung
 * @since 2015年8月23日
 */
public class TeacherServiceImpl implements ITeacherService {
	private static final Logger logger = Logger.getLogger(TeacherServiceImpl.class);
	private TeacherMapper teacherDao;
	private AgencyMapper agencyDao;
	private ClassMapper classDao;
	/**
	 * 设置教师数据接口。
	 * @param teacherDao 
	 *	  教师数据接口。
	 */
	public void setTeacherDao(TeacherMapper teacherDao) {
		logger.debug("注入教师数据接口...");
		this.teacherDao = teacherDao;
	}
	/**
	 * 设置培训机构数据接口。
	 * @param agencyDao 
	 *	  培训机构数据接口。
	 */
	public void setAgencyDao(AgencyMapper agencyDao) {
		logger.debug("注入培训机构数据接口...");
		this.agencyDao = agencyDao;
	}
	/**
	 * 设置班级数据接口。
	 * @param classDao 
	 *	  班级数据接口。
	 */
	public void setClassDao(ClassMapper classDao) {
		logger.debug("注入班级数据接口...");
		this.classDao = classDao;
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.admin.teachers.ITeacherService#datagrid(com.examw.netplatform.model.admin.teachers.TeacherInfo)
	 */
	@Override
	public DataGrid<TeacherInfo> datagrid(TeacherInfo info) {
		logger.debug("查询数据...");
		//分页排序
		PageHelper.startPage(info.getPage(), info.getRows(), StringUtils.trimToEmpty(info.getSort()) + " " + StringUtils.trimToEmpty(info.getOrder()));
		//查询数据
		final List<Teacher> list = this.teacherDao.findTeachers(info);
		//分页信息
		final PageInfo<Teacher> pageInfo = new PageInfo<Teacher>(list);
		//初始化
		final DataGrid<TeacherInfo> grid = new DataGrid<TeacherInfo>();
		grid.setRows(this.changeModel(list));
		grid.setTotal(pageInfo.getTotal());
		//返回
		return grid;
	}
	//批量数据转换
	private List<TeacherInfo> changeModel(List<Teacher> teachers){
		final List<TeacherInfo> list = new ArrayList<TeacherInfo>();
		if(teachers != null && teachers.size() > 0){
			for(Teacher teacher : teachers){
				if(teacher == null) continue;
				list.add(this.conversion(teacher));
			}
		}
		return list;
	}
	//数据类型转换
	private TeacherInfo conversion(Teacher teacher){
		final TeacherInfo info = new TeacherInfo();
		if(teacher != null){
			//赋值
			BeanUtils.copyProperties(teacher, info);
			//所属机构
			if(StringUtils.isNotBlank(info.getAgencyId()) && StringUtils.isBlank(info.getAgencyName())){
				final Agency agency = this.agencyDao.getAgency(info.getAgencyId());
				if(agency != null) info.setAgencyName(agency.getName());
			}
			//加载教师下班级集合
			final List<String> classIdList = new ArrayList<String>(), classNameList = new ArrayList<String>();
			final List<ClassPlan> classPlans = this.classDao.findClassByTeacher(info.getId());
			if(classPlans != null && classPlans.size() > 0){
				for(ClassPlan cp : classPlans){
					if(cp == null) continue;
					classIdList.add(cp.getId());
					classNameList.add(cp.getName());
				}
			}
			info.setClassIds(classIdList.toArray(new String[0]));
			info.setClassNames(classNameList.toArray(new String[0]));
		}
		return info;
	}
	/*
	 * 更新数据。
	 * @see com.examw.netplatform.service.admin.teachers.ITeacherService#update(com.examw.netplatform.model.admin.teachers.TeacherInfo)
	 */
	@Override
	public TeacherInfo update(TeacherInfo info) {
		logger.debug("更新数据...");
		if(info == null) return null;
		//检查数据
		if(StringUtils.isBlank(info.getAgencyId()) || this.agencyDao.getAgency(info.getAgencyId()) == null)
			throw new RuntimeException("教师所属培训机构为空或不存在!");
		//
		Teacher data = StringUtils.isBlank(info.getId()) ? null : this.teacherDao.getTeacher(info.getId());
		boolean isAdded = false;
		if(isAdded = (data == null)){
			if(StringUtils.isBlank(info.getId())) info.setId(UUID.randomUUID().toString());
			data = new Teacher();
		}
		//复制
		BeanUtils.copyProperties(info, data);
		//
		if(isAdded){
			logger.debug("新增数据...");
			this.teacherDao.insertTeacher(data);
		}else {
			logger.debug("保存数据...");
			this.teacherDao.deleteTeacherClass(data.getId());
			this.teacherDao.updateTeacher(data);
		}
		//班级处理
		if(info.getClassIds() != null && info.getClassIds().length > 0){
			for(String id : info.getClassIds()){
				if(StringUtils.isBlank(id)) continue;
				ClassPlan classPlan = this.classDao.getClassPlan(id);
				if(classPlan != null){
					this.teacherDao.insertTeacherClass(info.getId(), id);
				}
			}
		}
		//返回
		return this.conversion(data);
	}

	@Override
	public void delete(String[] ids) {
		// TODO Auto-generated method stub
		
	}
}