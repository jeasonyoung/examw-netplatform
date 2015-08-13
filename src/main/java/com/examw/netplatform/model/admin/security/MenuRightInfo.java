package com.examw.netplatform.model.admin.security;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.examw.model.IPaging;
import com.examw.netplatform.domain.admin.security.MenuRight;
/**
 * 菜单权限信息。
 * @author yangyong.
 * @since 2014-05-04
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class MenuRightInfo extends MenuRight implements IPaging {
	private static final long serialVersionUID = 1L;
	private String menuName,rightName,order,sort;
	private Integer page,rows;
	/**
	 *  构造函数。
	 */
	public MenuRightInfo(){
	}
	/**
	 * 构造函数。
	 * @param menuId
	 * 菜单ID。
	 * @param rightId
	 * 权限ID。
	 */
	public MenuRightInfo(String menuId,String rightId){
		this.setMenuId(menuId);
		this.setRightId(rightId);
	}
	/**
	 * 获取所属菜单名称。
	 * @return
	 * 所属菜单名称。
	 */
	public String getMenuName() {
		return menuName;
	}
	/**
	 * 设置所属菜单名称。
	 * @param menuName
	 * 所属菜单名称。
	 */
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	/**
	 * 获取所属权限名称。
	 * @return
	 * 所属权限名称。
	 */
	public String getRightName() {
		return rightName;
	}
	/**
	 * 设置所属权限名称。
	 * @param rightName
	 * 所属权限名称。
	 */
	public void setRightName(String rightName) {
		this.rightName = rightName;
	}
	/*
	 * 获取页码。
	 * @see com.examw.model.IPaging#getPage()
	 */
	@Override
	public Integer getPage() {
		return this.page;
	}
	/*
	 * 设置页码。
	 * @see com.examw.model.IPaging#setPage(java.lang.Integer)
	 */
	@Override
	public void setPage(Integer page) {
		this.page = page;
	}
	/*
	 * 获取页数据。
	 * @see com.examw.model.IPaging#getRows()
	 */
	@Override
	public Integer getRows() {
		return this.rows;
	}
	/*
	 * 设置页数据。
	 * @see com.examw.model.IPaging#setRows(java.lang.Integer)
	 */
	@Override
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	/*
	 * 获取排序字段。
	 * @see com.examw.model.IPaging#getOrder()
	 */
	@Override
	public String getOrder() {
		return this.order;
	}
	/*
	 * 设置排序字段。
	 * @see com.examw.model.IPaging#setOrder(java.lang.String)
	 */
	@Override
	public void setOrder(String order) {
		 this.order = order;
	}
	/*
	 * 获取排序方式。
	 * @see com.examw.model.IPaging#getSort()
	 */
	@Override
	public String getSort() {
		return this.sort;
	}
	/*
	 * 设置排序方式。
	 * @see com.examw.model.IPaging#setSort(java.lang.String)
	 */
	@Override
	public void setSort(String sort) {
		this.sort = sort;
	}
}