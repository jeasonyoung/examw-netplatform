package com.examw.netplatform.dao.admin.settings;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.examw.netplatform.domain.admin.settings.MsgBody;

/**
 * 消息数据接口。
 * 
 * @author jeasonyoung
 * @since 2015年8月30日
 */
public interface MsgBodyMapper {
	/**
	 * 获取消息内容数据。
	 * @param id
	 * 消息内容ID。
	 * @return
	 */
	MsgBody getMsgBody(String id);
	/**
	 * 查询消息内容数据。
	 * @param info
	 * 查询条件。
	 * @return
	 */
	List<MsgBody> findMsgBodies(MsgBody info);
	/**
	 * 查询学生下的消息。
	 * @param userId
	 * 学生ID。
	 * @param status
	 * 状态。
	 * @return
	 */
	List<MsgBody> findMsgBodiesByStudent(@Param("userId")String userId,@Param("status")Integer status);
	/**
	 * 查询消息用户ID。
	 * @param msgId
	 * 消息ID。
	 * @return
	 * 用户ID集合。
	 */
	List<String> findMsgUser(String msgId);
	/**
	 * 新增消息内容数据。
	 * @param data
	 * 消息内容数据。
	 */
	void insertMsgBody(MsgBody data);
	/**
	 * 更新消息内容数据。
	 * @param data
	 */
	void updateMsgBody(MsgBody data);
	/**
	 * 删除消息内容数据。
	 * @param id
	 */
	void deleteMsgBody(String id);
	/**
	 * 是否存在消息学员。
	 * @param msgId
	 * @param userId
	 * @return
	 */
	boolean hasMsgUser(@Param("msgId")String msgId, @Param("userId")String userId);
	/**
	 * 新增消息学员。
	 * @param msgId
	 * 消息ID。
	 * @param userId
	 * 学员ID。
	 */
	void insertMsgUser(@Param("msgId")String msgId, @Param("userId")String userId);
	/**
	 * 更新消息学员。
	 * @param msgId
	 * 消息ID。
	 * @param userId
	 * 用户ID。
	 * @param status
	 * 状态。
	 */
	void updateMsgUser(@Param("msgId")String msgId, @Param("userId")String userId,@Param("status")Integer status);
	/**
	 * 删除消息学员。
	 * @param msgId
	 * 消息ID。
	 * @param userId
	 * 用户ID。
	 */
	void deleteMsgUser(@Param("msgId")String msgId, @Param("userId")String userId);
	/**
	 * 删除消息全部学员。
	 * @param msgId
	 * 消息ID。
	 */
	void deleteMsgAllUser(String msgId);
}