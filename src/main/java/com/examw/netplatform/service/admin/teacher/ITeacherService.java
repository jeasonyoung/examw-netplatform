package com.examw.netplatform.service.admin.teacher;

import java.util.List;
import java.util.Map;

import com.examw.model.TreeNode;
import com.examw.netplatform.model.admin.teacher.TeacherInfo;
import com.examw.netplatform.service.IBaseDataService;

/**
 * 教师服务接口
 * @author fengwei.
 * @since 2014年5月29日 下午3:25:45.
 */
public interface ITeacherService extends IBaseDataService<TeacherInfo>{
	/**
	 * 加载用户状态名称。
	 * @param status
	 * 状态值。
	 * @return
	 * 状态名称。
	 */
	String loadUserStatusName(Integer status);
	/**
	 * 加载性别名称。
	 * @param gender
	 * 性别值。
	 * @return
	 * 性别名称。
	 */
	String loadGenderName(Integer gender);
	/**
	 * 加载用户类型集合。
	 * @return
	 * 用户类型集合。
	 */
	Map<String, String> loadUserTypes();
	/**
	 * 加载机构,考试下教师树数据。
	 * @return
	 * 教师树数据。
	 */
	List<TreeNode> loadAllTeachers(String agencyId);
}
