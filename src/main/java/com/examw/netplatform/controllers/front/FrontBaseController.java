package com.examw.netplatform.controllers.front;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.util.StringUtils;

import com.examw.netplatform.domain.admin.settings.Agency;
import com.examw.netplatform.exceptions.NoSuchAgencyException;
import com.examw.netplatform.service.admin.settings.IAgencyService;

/**
 * 前台控制器基类
 * 
 * @author fengwei.
 * @since 2015年1月22日 上午9:11:24.
 */
public class FrontBaseController {
	// 保存机构
	private static Map<String, Agency> agencyMap;
	@Resource
	private IAgencyService agencyService;

	public String getTemplateDir(String abbr) throws NoSuchAgencyException {
		Agency agency = getAgency(abbr);
		if (StringUtils.isEmpty(agency.getTemplate()))
			return "default";
		return agency.getTemplate();
	}

	public Agency getAgency(String abbr) throws NoSuchAgencyException {
		if (StringUtils.isEmpty(abbr)) {
			throw new NoSuchAgencyException();
		}
		if (agencyMap == null || agencyMap.get(abbr) == null) {
			// 去数据中查询
			Agency agency = this.agencyService.loadAgencyByAbbr(abbr);
			if (agency == null) {
				throw new NoSuchAgencyException();
			}
			putAgencyIntoMap(abbr, agency);
		}
		return agencyMap.get(abbr);
	}

	private void putAgencyIntoMap(String abbr, Agency agency) {
		if (agencyMap == null)
			agencyMap = new HashMap<String, Agency>();
		agencyMap.put(abbr, agency);
	}
}
