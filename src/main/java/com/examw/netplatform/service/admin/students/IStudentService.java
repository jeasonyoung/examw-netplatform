package com.examw.netplatform.service.admin.students;
import com.examw.netplatform.model.admin.settings.AgencyUserInfo;
import com.examw.netplatform.service.IBaseDataService;
import com.examw.netplatform.service.admin.settings.IAgencyUserService;
/**
 * 学员服务接口
 * @author fengwei.
 * @since 2014年5月24日 下午2:07:27.
 */
public interface IStudentService extends IBaseDataService<AgencyUserInfo>,IAgencyUserService {
	
}