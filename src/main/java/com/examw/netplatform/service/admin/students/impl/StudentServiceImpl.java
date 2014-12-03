package com.examw.netplatform.service.admin.students.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.netplatform.model.admin.settings.AgencyUserInfo;
import com.examw.netplatform.model.admin.settings.IAccountPassword;
import com.examw.netplatform.model.admin.students.BatchStudentInfo;
import com.examw.netplatform.model.admin.students.OrderInfo;
import com.examw.netplatform.service.admin.security.UserType;
import com.examw.netplatform.service.admin.settings.AgencyUserIdentity;
import com.examw.netplatform.service.admin.settings.impl.AgencyUserServiceImpl;
import com.examw.netplatform.service.admin.students.IOrderService;
import com.examw.netplatform.service.admin.students.IStudentService;
import com.examw.netplatform.service.admin.students.OrderSource;
import com.examw.service.Gender;
import com.examw.service.Status;
/**
 * 学员服务接口实现类
 * @author fengwei.
 * @since 2014年5月24日 下午2:07:39.
 */
public class StudentServiceImpl extends AgencyUserServiceImpl implements IStudentService{
	 private static final Logger logger = Logger.getLogger(StudentServiceImpl.class);
	 private IOrderService orderService;
	/**
	 * 设置订单服务接口。
	 * @param orderService 
	 *	  订单服务接口。
	 */
	public void setOrderService(IOrderService orderService) {
		if(logger.isDebugEnabled()) logger.debug("注册订单服务接口...");
		this.orderService = orderService;
	}
	/*
	  * 显示用户密码。
	  * @see com.examw.netplatform.service.admin.settings.impl.AgencyUserServiceImpl#isViewPwd()
	  */
	 @Override
	 protected boolean isViewPwd() {
		return true;
	 }
	 /*
	  * 更新学员用户信息。
	  * @see com.examw.netplatform.service.admin.settings.impl.AgencyUserServiceImpl#update(com.examw.netplatform.model.admin.settings.AgencyUserInfo)
	  */
	 @Override
	public AgencyUserInfo update(AgencyUserInfo info) {
		 if(logger.isDebugEnabled()) logger.debug("更新学员用户信息...");
		 if(info != null){
			if(StringUtils.isEmpty(info.getAgencyId())) throw new RuntimeException("所属培训机构ID不能为空！");
			AgencyUserIdentity identity = AgencyUserIdentity.conversion(info.getIdentity());
			if(identity != AgencyUserIdentity.STUDENT && identity != AgencyUserIdentity.CARDSTUDENT){
				throw new RuntimeException(String.format("身份［%s］不符合学生身份！", identity));
			}
			info.setType(UserType.FRONT.getValue());//学员用户应为前台用户； 
		 }
		return super.update(info);
	}
	/*
	 * 批量创建学员用户。
	 * @see com.examw.netplatform.service.admin.students.IStudentService#updateBatchUsers(com.examw.netplatform.model.admin.students.BatchStudentInfo)
	 */
	@Override
	public List<IAccountPassword> updateBatchUsers(BatchStudentInfo info) {
		if(logger.isDebugEnabled()) logger.debug("批量创建机构学员用户...");
		if(StringUtils.isEmpty(info.getAgencyId())) throw new RuntimeException("所属机构ID为空！");
		List<IAccountPassword> list = new ArrayList<>();
		int count = 0;
		if(info.getCount() != null && (count = info.getCount()) > 0){
			AgencyUserIdentity identity = AgencyUserIdentity.conversion(info.getIdentity());
			for(int i = 0; i < count; i++){
				AgencyUserInfo agencyUserInfo = new AgencyUserInfo();
				
				agencyUserInfo.setAgencyId(info.getAgencyId());
				agencyUserInfo.setAccount(String.format("%1$s%2$02d", info.getPrefix(), i + 1));
				agencyUserInfo.setName(agencyUserInfo.getAccount());
				agencyUserInfo.setGender(Gender.NONE.getValue());
				agencyUserInfo.setIdentity(identity.getValue());
				agencyUserInfo.setPassword(this.loadRandomCode(info.getPasswordLength()));
				agencyUserInfo.setStatus(info.getStatus());
				
				AgencyUserInfo userInfo = this.update(agencyUserInfo);
				if(userInfo != null){
					if((info.getPackageId() != null && info.getPackageId().length > 0) || (info.getClassId() != null && info.getClassId().length > 0)){
						OrderInfo orderInfo = new OrderInfo();
						orderInfo.setAgencyId(info.getAgencyId());
						orderInfo.setStudentId(userInfo.getUserId());
						orderInfo.setNumber(this.orderService.createOrderNumber(info.getAgencyId()));
						orderInfo.setName(orderInfo.getNumber());
						orderInfo.setPackageId(info.getPackageId());
						orderInfo.setClassId(info.getClassId());
						orderInfo.setUserId(info.getUserId());
						orderInfo.setUserName(info.getUserName());
						orderInfo.setSource(OrderSource.AGENCY.getValue());
						orderInfo.setStatus(Status.ENABLED.getValue());
						this.orderService.update(orderInfo);
					}
					list.add(new AccountPassword(userInfo));
				}
			}
		}
		return list;
	}
	/**
	 * 账号密码数据。
	 * 
	 * @author yangyong
	 * @since 2014年11月29日
	 */
	class AccountPassword implements IAccountPassword  {
		private static final long serialVersionUID = 1L;
		private String account,password;
		/**
		 * 构造函数。
		 * @param export
		 */
		public AccountPassword(IAccountPassword export){
			if(export != null){
				this.account = export.getAccount();
				this.password = export.getPassword();
			}
		}
		/*
		 * 获取账号。
		 * @see com.examw.netplatform.model.admin.settings.IExportAccountPassword#getAccount()
		 */
		@Override
		public String getAccount() {
			return this.account;
		}
		/*
		 * 设置账号。
		 * @see com.examw.netplatform.model.admin.settings.IAccountPassword#setAccount(java.lang.String)
		 */
		@Override
		public void setAccount(String account) {
			this.account = account;
		}
		/*
		 * 获取密码。
		 * @see com.examw.netplatform.model.admin.settings.IExportAccountPassword#getPassword()
		 */
		@Override
		public String getPassword() {
			return this.password;
		}
		/*
		 * 设置密码。
		 * @see com.examw.netplatform.model.admin.settings.IAccountPassword#setPassword(java.lang.String)
		 */
		@Override
		public void setPassword(String password) {
			this.password = password;
		}
	}
}