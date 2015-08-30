package com.examw.netplatform.domain.admin.settings;
/**
 * 消息类型。
 * 
 * @author jeasonyoung
 * @since 2015年8月30日
 */
public enum MsgType {
	/**
	 * 系统消息。
	 */
	SYS(0),
	/**
	 * 机构消息。
	 */
	AGENCY(1);
	
	private int value;
	/**
	 * 获取类型值。
	 * @return 类型值。
	 */
	public int getValue() {
		return value;
	}
	/**
	 * 构造函数。
	 * @param value
	 */
	private MsgType(int value){
		this.value = value;
	}
	/**
	 * 类型转换。
	 * @param value
	 * @return
	 */
	public static MsgType parse(int value){
		for(MsgType t : MsgType.values()){
			if(t.value == value) return t;
		}
		return SYS;
	}
}