package com.examw.netplatform.model.admin.agency;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.examw.model.Paging;

/**
 * 机构统计信息
 * @author fengwei.
 * @since 2014年6月24日 上午10:25:24.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class AgencyStatisticsInfo extends Paging {
	private static final long serialVersionUID = 1L; 
	private String id,name;
	private int accountCount,studentCount,teacherCount,packageCount,packageAddCount,packageUseCount;
	/**
	 * 获取 ID
	 * @return id
	 * 
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置 ID
	 * @param id
	 * 
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取 机构名称
	 * @return name
	 * 机构名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置 机构名称
	 * @param name
	 * 机构名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取 学员上限
	 * @return accountCount
	 * 学员上限
	 */
	public int getAccountCount() {
		return accountCount;
	}
	/**
	 * 设置 学员上限
	 * @param accountCount
	 * 学员上限
	 */
	public void setAccountCount(int accountCount) {
		this.accountCount = accountCount;
	}
	/**
	 * 获取 实际学员个数
	 * @return studentCount
	 * 实际学员个数
	 */
	public int getStudentCount() {
		return studentCount;
	}
	/**
	 * 设置 实际学员个数
	 * @param studentCount
	 * 实际学员个数
	 */
	public void setStudentCount(int studentCount) {
		this.studentCount = studentCount;
	}
	/**
	 * 获取 老师个数
	 * @return teacherCount
	 * 老师个数
	 */
	public int getTeacherCount() {
		return teacherCount;
	}
	/**
	 * 设置 老师个数
	 * @param teacherCount
	 * 老师个数
	 */
	public void setTeacherCount(int teacherCount) {
		this.teacherCount = teacherCount;
	}
	/**
	 * 获取 套餐上限
	 * @return packageCount
	 * 套餐上限
	 */
	public int getPackageCount() {
		return packageCount;
	}
	/**
	 * 设置 套餐上限
	 * @param packageCount
	 * 套餐上限
	 */
	public void setPackageCount(int packageCount) {
		this.packageCount = packageCount;
	}
	/**
	 * 获取 添加的套餐数
	 * @return packageAddCount
	 * 添加的套餐数
	 */
	public int getPackageAddCount() {
		return packageAddCount;
	}
	/**
	 * 设置 添加的套餐数
	 * @param packageAddCount
	 * 添加的套餐数
	 */
	public void setPackageAddCount(int packageAddCount) {
		this.packageAddCount = packageAddCount;
	}
	/**
	 * 获取 套餐使用数
	 * @return packageUseCount
	 * 套餐使用数
	 */
	public int getPackageUseCount() {
		return packageUseCount;
	}
	/**
	 * 设置 套餐使用数
	 * @param packageUseCount
	 * 套餐使用数
	 */
	public void setPackageUseCount(int packageUseCount) {
		this.packageUseCount = packageUseCount;
	}
}
