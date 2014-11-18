package com.examw.netplatform.service.admin.teacher.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.examw.netplatform.domain.admin.settings.AgencyUser;
import com.examw.netplatform.model.admin.settings.AgencyUserInfo;
import com.examw.netplatform.service.admin.settings.AgencyUserIdentity;
import com.examw.netplatform.service.admin.settings.impl.AgencyUserServiceImpl;
import com.examw.netplatform.service.admin.teacher.ITeacherService;

/**
 * 教师服务接口实现类。
 * 
 * @author fengwei.
 * @since 2014年5月29日 下午3:27:21.
 */
public class TeacherServiceImpl extends AgencyUserServiceImpl implements ITeacherService {
	private static final Logger logger = Logger.getLogger(TeacherServiceImpl.class);
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.admin.settings.impl.AgencyUserServiceImpl#find(com.examw.netplatform.model.admin.settings.AgencyUserInfo)
	 */
	@Override
	protected List<AgencyUser> find(AgencyUserInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		info.setIdentity(AgencyUserIdentity.TEACHER.getValue());
		return super.find(info);
	}
}