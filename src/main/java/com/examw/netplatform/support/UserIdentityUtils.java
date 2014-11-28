package com.examw.netplatform.support;

import java.util.Map;

import org.springframework.ui.Model;

import com.examw.netplatform.service.admin.settings.AgencyUserIdentity;
import com.examw.netplatform.service.admin.settings.IAgencyUserService;

/**
 * 用户身份工具类。
 * 
 * @author yangyong
 * @since 2014年11月28日
 */
public final class UserIdentityUtils {
	/**
	 * 加载学生身份集合。
	 * @param agencyUserService
	 * 机构用户服务。
	 * @return
	 */
	public static void loadStudentIdentities(IAgencyUserService agencyUserService,Model model){
		if(agencyUserService == null) return;
		Map<String, String> studentIdentityMap = EnumMapUtils.createTreeMap();
		AgencyUserIdentity[] identities = new AgencyUserIdentity[]{ AgencyUserIdentity.STUDENT,AgencyUserIdentity.CARDSTUDENT};
		for(AgencyUserIdentity identity : identities){
			studentIdentityMap.put(String.format("%d", identity.getValue()), agencyUserService.loadIdentityName(identity.getValue()));
		}
		model.addAttribute("studentIdentityMap", studentIdentityMap);
	}
}