package com.examw.netplatform.domain.admin.settings;
/**
 * 消息状态。
 * 
 * @author jeasonyoung
 * @since 2015年8月30日
 */
public enum MsgStatus {
	/**
	 * 未读。
	 */
	Unread(0),
	/**
	 * 已读。
	 */
	Read(1);
	
	private int value;
	/**
	 * 构造函数。
	 * @param value
	 */
	private MsgStatus(int value){
		this.value = value;
	}
	/**
	 * 获取枚举值。
	 * @return value
	 */
	public int getValue() {
		return value;
	}
	/**
	 * 类型转换。
	 * @param value
	 * @return
	 */
	public static MsgStatus parse(int value){
		for(MsgStatus s : MsgStatus.values()){
			if(s.value == value)return s;
		}
		return Unread;
	}
}