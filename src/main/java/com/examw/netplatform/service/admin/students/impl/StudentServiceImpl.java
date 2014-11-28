package com.examw.netplatform.service.admin.students.impl;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.netplatform.model.admin.settings.AgencyUserInfo;
import com.examw.netplatform.service.admin.security.UserType;
import com.examw.netplatform.service.admin.settings.AgencyUserIdentity;
import com.examw.netplatform.service.admin.settings.impl.AgencyUserServiceImpl;
import com.examw.netplatform.service.admin.students.IStudentService;
/**
 * 学员服务接口实现类
 * @author fengwei.
 * @since 2014年5月24日 下午2:07:39.
 */
public class StudentServiceImpl extends AgencyUserServiceImpl implements IStudentService{
	 private static final Logger logger = Logger.getLogger(StudentServiceImpl.class);
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
}