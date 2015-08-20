package com.examw.netplatform.service.admin.settings;

import java.util.List;

import com.examw.model.DataGrid;
import com.examw.netplatform.model.admin.settings.SubjectInfo;
/**
 * 科目服务接口
 * @author fengwei.
 * @since 2014年8月7日 上午10:13:56.
 */
public interface ISubjectService{
	/**
	 * 获取状态名称。
	 * @param status
	 * 状态值。
	 * @return
	 * 状态名称。
	 */
	String loadStatusName(Integer status);
	/**
	 * 加载最大代码值
	 * @return
	 */
	Integer loadMaxCode();
	/**
	 * 查询数据。
	 * @param info
	 * @return
	 */
	DataGrid<SubjectInfo> datagrid(SubjectInfo info);
	/**
	 * 加载考试下科目集合。
	 * @param examId
	 * 所属考试ID。
	 * @return
	 * 科目集合。
	 */
	List<SubjectInfo> loadAllSubjects(String examId);
	/**
	 * 更新数据。
	 * @param info
	 */
	SubjectInfo update(SubjectInfo info);
	/**
	 * 删除数据。
	 * @param ids
	 */
	void delete(String[] ids);
}