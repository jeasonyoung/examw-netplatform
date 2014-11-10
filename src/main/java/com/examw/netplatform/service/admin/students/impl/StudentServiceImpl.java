package com.examw.netplatform.service.admin.students.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.examw.model.DataGrid;
import com.examw.netplatform.model.admin.settings.AgencyUserInfo;
import com.examw.netplatform.model.admin.students.StudentInfo;
import com.examw.netplatform.service.admin.settings.IAgencyUserService;
import com.examw.netplatform.service.admin.students.IStudentService;
/**
 * 学员服务接口实现类
 * @author fengwei.
 * @since 2014年5月24日 下午2:07:39.
 */
public class StudentServiceImpl implements IStudentService{
	private IAgencyUserService agencyUserService; 
	/**
	 * 设置机构用户服务接口。
	 * @param agencyUserService
	 */
	public void setAgencyUserService(IAgencyUserService agencyUserService) {
		this.agencyUserService = agencyUserService;
	}
	/*
	 * 查询数据。
	 * @see com.examw.service.IDataService#datagrid(java.lang.Object)
	 */
	@Override
	public DataGrid<StudentInfo> datagrid(StudentInfo info) {
		DataGrid<StudentInfo> grid = new DataGrid<StudentInfo>();
		DataGrid<AgencyUserInfo> dgs = this.agencyUserService.datagrid(info);
		grid.setTotal(dgs.getTotal());
		if(dgs.getRows() != null){
			List<StudentInfo> rows = new ArrayList<>();
			for(AgencyUserInfo aui: dgs.getRows()){
				StudentInfo stu = new StudentInfo();
				BeanUtils.copyProperties(aui, stu);
				rows.add(stu);
			}
			grid.setRows(rows);
		}
		return grid;
	}
	/*
	 * 更新数据。
	 * @see com.examw.service.IDataService#update(java.lang.Object)
	 */
	@Override
	public StudentInfo update(StudentInfo info) {
		return (StudentInfo)this.agencyUserService.update((AgencyUserInfo)info);
	}
	/*
	 *删除数据。
	 * @see com.examw.service.IDataService#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(ids == null || ids.length == 0) return;
		this.agencyUserService.delete(ids);
	}
}