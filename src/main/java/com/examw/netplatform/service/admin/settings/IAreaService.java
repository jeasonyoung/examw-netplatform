package com.examw.netplatform.service.admin.settings;

import java.util.List;

import com.examw.netplatform.domain.admin.settings.Area;
import com.examw.netplatform.model.admin.settings.AreaInfo;
import com.examw.netplatform.service.IBaseDataService;
/**
 * 行政地区服务接口
 * @author fengwei.
 * @since 2014年8月6日 下午1:48:28.
 */
public interface IAreaService extends IBaseDataService<AreaInfo>{
	/**
	 * 加载地区对象。
	 * @param areaId
	 * 地区ID。
	 * @return
	 */
	Area loadAreaById(String areaId);
	/**
	 * 加载全部地区数据。
	 * @return
	 */
	List<AreaInfo> loadAllAreas();
	/**
	 * 加载考试所属地区集合。
	 * @param examId
	 * @return
	 */
	List<AreaInfo> loadAreasByExam(String examId);
	/**
	 * 加载最大代码值
	 * @return
	 */
	Integer loadMaxCode();
}