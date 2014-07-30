package com.examw.netplatform.service.admin.security;

import java.util.List;

import com.examw.model.TreeNode; 
import com.examw.netplatform.model.admin.security.RoleInfo;
import com.examw.netplatform.service.IBaseDataService;
/**
 * 角色服务接口。
 * @author yangyong.
 * @since 2014-05-06.
 */
public interface IRoleService extends IBaseDataService<RoleInfo> {
	/**
	 * 获取状态名称。
	 * @param status
	 * 状态值。
	 * @return
	 * 状态名称。
	 */
	String getStatusName(int status);
	/**
	 * 加载角色权限树数据。
	 * @param roleId
	 * 角色ID。
	 * @return
	 * 角色权限树数据。
	 */
	List<TreeNode> loadRoleRightTree(String roleId);
	/**
	 * 加载全部角色数据集合。
	 * @return
	 */
	List<RoleInfo> loadAll();
	/**
	 * 添加角色权限。
	 * @param roleId
	 * 角色ID。
	 * @param menuRightIds
	 * 菜单权限ID数组。
	 */
	void addRoleRight(String roleId,String[] menuRightIds);
	/**
	 * 初始化角色。
	 * @param roleId
	 * 角色ID。
	 * @throws Exception
	 */
	void init(String roleId) throws Exception;
}