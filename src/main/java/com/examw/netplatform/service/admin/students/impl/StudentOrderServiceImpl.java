package com.examw.netplatform.service.admin.students.impl;
 
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List; 
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.courses.IClassPlanDao;
import com.examw.netplatform.dao.admin.courses.IPackageDao;
import com.examw.netplatform.dao.admin.settings.IAgencyUserDao;
import com.examw.netplatform.dao.admin.students.IStudentOrderDao;
import com.examw.netplatform.domain.admin.courses.ClassPlan;
import com.examw.netplatform.domain.admin.courses.Package;
import com.examw.netplatform.domain.admin.settings.AgencyUser;
import com.examw.netplatform.domain.admin.students.StudentOrder; 
import com.examw.netplatform.domain.admin.students.StudentOrderDetail;
import com.examw.netplatform.model.admin.students.StudentOrderDetailInfo;
import com.examw.netplatform.model.admin.students.StudentOrderInfo;
import com.examw.netplatform.service.admin.students.IStudentOrderService;
import com.examw.netplatform.service.impl.BaseDataServiceImpl;
/**
 * 机构学员订单服务接口实现。
 * @author fengwei.
 * @since 2014年5月26日 上午8:20:08.
 */
public class StudentOrderServiceImpl extends BaseDataServiceImpl<StudentOrder, StudentOrderInfo> implements IStudentOrderService{
	private static final Logger logger = Logger.getLogger(StudentOrderServiceImpl.class);
	private IStudentOrderDao studentOrderDao;
	private IAgencyUserDao agencyUserDao;
	private IClassPlanDao classPlanDao;
	private IPackageDao packageDao;
	private Map<Integer, String> orderStatusMap,detailTypeMap;
	/**
	 * 设置机构学员订单数据接口。
	 * @param studentOrderDao
	 * 机构学员订单数据接口。
	 */
	public void setStudentOrderDao(IStudentOrderDao studentOrderDao) {
		this.studentOrderDao = studentOrderDao;
	}
	/**
	 * 设置机构用户数据接口。
	 * @param agencyUserDao
	 * 机构用户数据接口。
	 */
	public void setAgencyUserDao(IAgencyUserDao agencyUserDao) {
		this.agencyUserDao = agencyUserDao;
	}
	/**
	 * 设置班级数据接口。
	 * @param classPlanDao
	 * 班级数据接口。
	 */
	public void setClassPlanDao(IClassPlanDao classPlanDao) {
		this.classPlanDao = classPlanDao;
	}
	/**
	 * 设置套餐数据接口。
	 * @param packageDao
	 * 套餐数据接口。
	 */
	public void setPackageDao(IPackageDao packageDao) {
		this.packageDao = packageDao;
	}
	/**
	 * 设置订单状态集合。
	 * @param orderStatusMap
	 * 订单状态集合。
	 */
	public void setOrderStatusMap(Map<Integer, String> orderStatusMap) {
		this.orderStatusMap = orderStatusMap;
	}
	/**
	 * 设置订单明细类型集合。
	 * @param detailTypeMap
	 */
	public void setDetailTypeMap(Map<Integer, String> detailTypeMap) {
		this.detailTypeMap = detailTypeMap;
	}
	/*
	 * 加载订单状态名称。
	 * @see com.examw.netplatform.service.admin.students.IStudentOrderService#loadOrderStatusName(java.lang.Integer)
	 */
	@Override
	public String loadOrderStatusName(Integer status) {
		if(this.orderStatusMap == null || this.orderStatusMap.size() == 0 || status == null) return null;
		return this.orderStatusMap.get(status);
	}
	/*
	 * 加载订单明细类型名称。
	 * @see com.examw.netplatform.service.admin.students.IStudentOrderService#loadDetailTypeName(java.lang.Integer)
	 */
	@Override
	public String loadDetailTypeName(Integer type) {
		if(this.detailTypeMap == null || this.detailTypeMap.size() == 0 || type == null) return null;
		return this.detailTypeMap.get(type);
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<StudentOrder> find(StudentOrderInfo info) {
		return this.studentOrderDao.findOrders(info);
	}
	/*
	 * 类型转换。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected StudentOrderInfo changeModel(StudentOrder data) {
		if(logger.isDebugEnabled()) logger.debug("订单类型转换...");
		if(data == null) return null;
		StudentOrderInfo info = new StudentOrderInfo();
		BeanUtils.copyProperties(data, info, new String[]{"details"});
		if(data.getStudent() != null){
			info.setStudentId(data.getStudent().getId());
			if(data.getStudent().getAgency() != null){
				info.setAgencyId(data.getStudent().getAgency().getId());
				info.setAgencyName(data.getStudent().getAgency().getName());
			}
			if(data.getStudent().getUser() != null){
				info.setStudentName(data.getStudent().getUser().getName());
			}
		}
		if(data.getDetails() != null){
			Set<StudentOrderDetailInfo> details = new HashSet<>();
			BigDecimal total = BigDecimal.ZERO;
			for(StudentOrderDetail detail : data.getDetails()){
				StudentOrderDetailInfo d = this.changeModel(detail);
				if(d != null) {
					total.add(detail.getPrice());
					details.add(d);
				}
			}
			info.setTotal(total);
			info.setDetails(details);
		}
		info.setStatusName(this.loadOrderStatusName(info.getStatus()));
		return info;
	}
	//类型转换。
	private StudentOrderDetailInfo changeModel(StudentOrderDetail data){
		if(logger.isDebugEnabled())logger.debug("订单明细转换...");
		if(data == null) return null;
		StudentOrderDetailInfo info = new StudentOrderDetailInfo();
		BeanUtils.copyProperties(data, info);
		if(data.getType() == StudentOrderDetail.TYPE_PACKAGE){
			if(data.getPack() != null)info.setPackId(data.getPack().getId());
		}
		if(data.getType() == StudentOrderDetail.TYPE_CLASS){
			if(data.getPlan() != null) info.setClassId(data.getPlan().getId());
		}
		info.setTypeName(this.loadDetailTypeName(info.getType()));
		return info;
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(StudentOrderInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		return this.studentOrderDao.total(info);
	}
	/*
	 * 更新数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public StudentOrderInfo update(StudentOrderInfo info) {
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		if(info == null) return null;
		boolean isAdded = false;
		StudentOrder data = StringUtils.isEmpty(info.getId()) ? null : this.studentOrderDao.load(StudentOrder.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())) info.setId(UUID.randomUUID().toString());
			data = new StudentOrder();
			info.setCreateTime(new Date());
		}
		if(!isAdded) info.setCreateTime(data.getCreateTime());
		info.setLastTime(new Date());
		BeanUtils.copyProperties(info, data);
		AgencyUser student = this.agencyUserDao.load(AgencyUser.class, info.getStudentId());
		if(student == null){
			String err = "机构用户［"+ info.getStudentId() +"］不存在！";
			logger.error(err);
			throw new RuntimeException(err);
		}
		data.setStudent(student);
		Set<StudentOrderDetail> details = new HashSet<>();
		if(info.getDetails() != null){
			 for(StudentOrderDetailInfo sod: info.getDetails()){
				 StudentOrderDetail detail = this.changeModel(data, sod);
				 if(detail != null) details.add(detail);
			 }
		}
		data.setDetails(details);
		if(data.getStudent() != null){
			if(data.getStudent().getUser() != null)info.setStudentName(data.getStudent().getUser().getName());
			if(data.getStudent().getAgency() != null){
				info.setAgencyId(data.getStudent().getAgency().getId());
				info.setAgencyName(data.getStudent().getAgency().getName());
			}
		}
		return info;
	}
	//类型转换。
	private StudentOrderDetail changeModel(StudentOrder order, StudentOrderDetailInfo info){
		if(logger.isDebugEnabled()) logger.debug("订单明细类型转换...");
		if(info == null) return null;
		
 		if(StringUtils.isEmpty(info.getId()))info.setId(UUID.randomUUID().toString());
 		info.setCreateTime(new Date());
 		
		StudentOrderDetail data = new StudentOrderDetail();
		data.setOrder(order);
		BeanUtils.copyProperties(info, data);
		
		String err  = null;
		if(info.getType() == StudentOrderDetail.TYPE_CLASS){
			ClassPlan plan = this.classPlanDao.load(ClassPlan.class, info.getClassId());
			if(plan == null){
				logger.error(err = "班级［"+info.getClassId() +"］不存在！");
				throw new RuntimeException(err);
			}
			data.setPlan(plan);
		}else if(info.getType() == StudentOrderDetail.TYPE_PACKAGE){
			Package pack = this.packageDao.load(Package.class, info.getPackId());
			if(pack == null){
				logger.error(err = "套餐［"+info.getPackId() +"］不存在！");
				throw new RuntimeException(err);
			}
			data.setPack(pack);
		}
		return data;
	}
	/*
	 * 删除数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled())logger.debug("开始删除数据...");
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			StudentOrder data = this.studentOrderDao.load(StudentOrder.class, ids[i]);
			if(data != null){
				if(logger.isDebugEnabled()) logger.info("数据［"+ ids[i]+"］状态=>已删除");
				data.setStatus(StudentOrder.STATUS_DELETE);
			}
		}
	}
}