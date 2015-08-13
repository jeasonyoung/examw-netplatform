package com.examw.netplatform.dao.admin.settings;

import java.util.List;

import com.examw.netplatform.domain.admin.settings.Area;
/**
 * 行政地区数据接口
 * @author fengwei.
 * @since 2014年8月6日 上午11:54:12.
 */
public interface AreaMapper {
	/**
	 * 获取地区数据。
	 * @param id
	 * @return
	 */
	Area getArea(String id);
	/**
	 * 查询数据。
	 * @param info
	 * @return
	 */
	List<Area> findAreas(Area info);
	/**
	 * 查询考试地区集合。
	 * @param examId
	 * @return
	 */
	List<Area> findAreasByExam(String examId);
	/**
	 * 查询科目地区数据集合。
	 * @param subjectId
	 * @return
	 */
	List<Area> findAreasBySubject(String subjectId);
	/**
	 * 查询班级地区数据集合。
	 * @param classId
	 * @return
	 */
	List<Area> findAreasByClass(String classId);
	/**
	 * 加载最大的代码值。
	 * @return
	 */
	Integer loadMaxCode();
	/**
	 * 是否存在地区代码。
	 * @param code
	 * @return
	 */
	boolean hasAreaCode(int code);
	/**
	 * 是否存在地区EN简称。
	 * @param abbr
	 * @return
	 */
	boolean hasAreaAbbr(String abbr);
	/**
	 * 插入数据。
	 * @param data
	 */
	void insertArea(Area data);
	/**
	 * 更新数据。
	 * @param data
	 */
	void updateArea(Area data);
	/**
	 * 删除数据。
	 * @param id
	 */
	void deleteArea(String id);
}