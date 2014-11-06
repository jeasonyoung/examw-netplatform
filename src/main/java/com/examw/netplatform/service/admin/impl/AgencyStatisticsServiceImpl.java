package com.examw.netplatform.service.admin.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.examw.netplatform.dao.admin.courses.IPackageDao;
import com.examw.netplatform.dao.admin.settings.IAgencyDao;
import com.examw.netplatform.dao.admin.students.IStudentOrderDao; 
import com.examw.netplatform.dao.admin.teacher.ITeacherDao;
import com.examw.netplatform.domain.admin.settings.Agency;
import com.examw.netplatform.domain.admin.students.StudentOrder;
import com.examw.netplatform.model.admin.agency.AgencyStatisticsInfo;
import com.examw.netplatform.model.admin.courses.PackageInfo;
import com.examw.netplatform.model.admin.settings.AgencyInfo;
import com.examw.netplatform.model.admin.students.StudentOrderInfo;
import com.examw.netplatform.service.admin.IAgencyStatisticsService;
import com.examw.netplatform.service.impl.BaseDataServiceImpl;

/**
 * 机构统计
 * @author fengwei.
 * @since 2014年6月24日 上午10:41:48.
 */
public class AgencyStatisticsServiceImpl extends BaseDataServiceImpl<Agency, AgencyStatisticsInfo> implements IAgencyStatisticsService{
	private IAgencyDao agencyDao;
	//private IStudentDao studentDao;
	//private ITeacherDao teacherDao;
	private IPackageDao packageDao;
	private IStudentOrderDao orderDao;
	/**
	 * 设置 机构数据接口
	 * @param agencyDao
	 * 
	 */
	public void setAgencyDao(IAgencyDao agencyDao) {
		this.agencyDao = agencyDao;
	}

//	/**
//	 * 设置 学员数据接口
//	 * @param studentDao
//	 * 
//	 */
//	public void setStudentDao(IStudentDao studentDao) {
//		//this.studentDao = studentDao;
//	}

	/**
	 * 设置 老师数据接口
	 * @param teacherDao
	 * 
	 */
	public void setTeacherDao(ITeacherDao teacherDao) {
		//this.teacherDao = teacherDao;
	}

	/**
	 * 设置 套餐数据接口
	 * @param packageDao
	 * 
	 */
	public void setPackageDao(IPackageDao packageDao) {
		this.packageDao = packageDao;
	}
	/**
	 * 设置 订单数据接口
	 * @param orderDao
	 * 订单数据接口
	 */
	public void setOrderDao(IStudentOrderDao orderDao) {
		this.orderDao = orderDao;
	}

	@Override
	protected List<Agency> find(AgencyStatisticsInfo info) {
		if(info == null) return null;
		AgencyInfo agencyInfo = new AgencyInfo();
		agencyInfo.setPage(info.getPage());
		agencyInfo.setOrder(info.getOrder());
		agencyInfo.setSort(info.getSort());
		agencyInfo.setRows(info.getRows());
		agencyInfo.setName(info.getName());
		return this.agencyDao.findAgencies(agencyInfo);
	}

	@Override
	protected AgencyStatisticsInfo changeModel(final Agency data) {
		if(data == null) return null;
		AgencyStatisticsInfo info = new AgencyStatisticsInfo();
		BeanUtils.copyProperties(data, info);
		info.setPackageAddCount(this.packageDao.total(new PackageInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public String getAgencyId() {return data.getId();}
		}).intValue());
//		info.setStudentCount(this.studentDao.total(new StudentInfo(){
//			private static final long serialVersionUID = 1L;
//			//@Override
//			//public String[] getAgencyId() {return new String[]{data.getId()};}
//		}).intValue());
//		info.setTeacherCount(this.teacherDao.total(new TeacherInfo(){
//			private static final long serialVersionUID = 1L;
//			@Override
//			public String[] getAgencyId() {return new String[]{data.getId()};}
//		}).intValue());
		info.setPackageUseCount(this.orderDao.total(new StudentOrderInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public String getAgencyId() {return data.getId();}
			@Override
			public Integer getStatus() {return StudentOrder.STATUS_BUY;}	//订购状态的
		}).intValue());
		return info;
	}

	@Override
	protected Long total(AgencyStatisticsInfo info) {
		if(info == null) return 0L;
		AgencyInfo agencyInfo = new AgencyInfo();
		agencyInfo.setPage(info.getPage());
		agencyInfo.setOrder(info.getOrder());
		agencyInfo.setSort(info.getSort());
		agencyInfo.setRows(info.getRows());
		agencyInfo.setName(info.getName());
		return this.agencyDao.total(agencyInfo);
	}

	@Override
	public AgencyStatisticsInfo update(AgencyStatisticsInfo info) {
		return null;
	}

	@Override
	public void delete(String[] ids) {
	}
	
}
