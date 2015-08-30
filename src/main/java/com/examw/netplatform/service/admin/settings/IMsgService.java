package com.examw.netplatform.service.admin.settings;

import com.examw.model.DataGrid;
import com.examw.netplatform.model.admin.settings.MsgBodyInfo;

/**
 * 消息服务接口。
 * 
 * @author jeasonyoung
 * @since 2015年8月30日
 */
public interface IMsgService {
	/**
	 * 加载状态值名称。
	 * @param status
	 * 状态值。
	 * @return
	 * 状态名称。
	 */
	String loadStatusName(Integer status);
	/**
	 * 加载类型值名称。
	 * @param status
	 * 类型值。
	 * @return
	 * 类型名称。
	 */
	String loadTypeName(Integer type);
	/**
	 * 查询消息数据。
	 * @param info
	 * @return
	 */
	DataGrid<MsgBodyInfo> datagrid(MsgBodyInfo info);
	/**
	 * 更新消息数据。
	 * @param info
	 * @return
	 */
	MsgBodyInfo update(MsgBodyInfo info);
	/**
	 * 删除消息数据。
	 * @param ids
	 */
	void delete(String[] ids);
}