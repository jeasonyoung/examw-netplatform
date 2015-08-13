package com.examw.netplatform.service.admin.security;

import java.util.List;

import com.examw.model.DataGrid;
import com.examw.netplatform.domain.admin.security.Role;
import com.examw.netplatform.model.admin.security.RoleInfo;
/**
 * 角色服务接口。
 * @author yangyong.
 * @since 2014-05-06.
 */
public interface IRoleService {
	/**
	 * 加载状态名称。
	 * @param status
	 * 状态值。
	 * @return
	 * 状态名称。
	 */
	String loadStatusName(Integer status);
	/**
	 * 加载全部角色数据集合。
	 * @return
	 */
	List<RoleInfo> loadAll();
	/**
	 * 加载角色权限集合。
	 * @param roleId
	 * 角色ID。
	 * @return
	 */
	String[] loadRoleRightIds(String roleId);
	/**
	 * 加载角色数据。
	 * @param roleId
	 * 角色ID。
	 * @return
	 * 角色数据。
	 */
	Role loadRole(String roleId);
	/**
	 * 数据模型转换。
	 * @param role
	 * 角色数据。
	 * @return
	 * 角色信息。
	 */
	RoleInfo conversion(Role role);
	/**
	 * 查询数据。
	 * @param info
	 * @return
	 */
	DataGrid<RoleInfo> datagrid(RoleInfo info);
	/**
	 * 更新数据。
	 * @param info
	 */
	RoleInfo update(RoleInfo info);
	/**
	 * 删除数据。
	 * @param ids
	 */
	void delete(String[] ids);
	/**
	 * 初始化角色。
	 * @param roleId
	 * 角色ID。
	 * @throws Exception
	 */
	void init(String roleId) throws Exception;
	/**
	 * 更新角色权限。
	 * @param roleId
	 * 角色ID。
	 * @param rightIds
	 * 权限ID集合。
	 */
	void updateRoleRights(String roleId,String[] rightIds) throws Exception;
}