package com.examw.netplatform.controllers.admin.finances;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.DataGrid;
import com.examw.netplatform.model.admin.agency.AgencyStatisticsInfo;
import com.examw.netplatform.service.admin.IAgencyStatisticsService;

/**
 * 机构统计控制器
 * @author fengwei.
 * @since 2014年6月24日 上午11:31:37.
 */
@Controller
@RequestMapping("/admin/finance/agencystatistics")
public class AgencyStatisticsController {
	
	//@Resource
	private IAgencyStatisticsService agencyStatisticsService;
	
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		return "finance/agency_statistics";
	}
	
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<AgencyStatisticsInfo> datagrid(AgencyStatisticsInfo info){
		return this.agencyStatisticsService.datagrid(info);
	}
}	
