package com.examw.netplatform.service.admin.teachers;
/**
 * 题型枚举。
 * 
 * @author yangyong
 * @since 2014年8月7日
 */
public enum ItemType {
	/**
	 * 单选。
	 */
	SINGLE(0x01),
	/**
	 * 多选。
	 */
	MULTY(0x02),
	/**
	 * 不定向选。
	 */
	UNCERTAIN(0x03),
	/**
	 * 判断题。
	 */
	JUDGE(0x04),
	/**
	 * 问答题。
	 */
	QANDA(0x05),
	/**
	 * 共享题干题。
	 */
	SHARE_TITLE(0x06),
	/**
	 * 共享答案题。
	 */
	SHARE_ANSWER(0x07);
	
	private int value;
	//私有构造函数。
	private ItemType(int value){
		this.value = value;
	}
	/**
	 * 获取枚举值。
	 * @return 枚举值。
	 */
	public Integer getValue(){
		return this.value;
	}
	/**
	 * 枚举值转换。
	 * @param value
	 * @return
	 */
	public static ItemType convert(int value){
		switch(value){
			//单选
			case 0x01 : return ItemType.SINGLE;
			//多选
			case 0x02: return ItemType.MULTY;
			//不定向选
			case 0x03:return ItemType.UNCERTAIN;
			//判断题目
			case 0x04:return ItemType.JUDGE;
			//问答题
			case 0x05:return ItemType.QANDA;
			//共享题干题
			case 0x06:return ItemType.SHARE_TITLE;
			//共享答案题
			case 0x07:return ItemType.SHARE_ANSWER;
		}
		throw new RuntimeException("不存在［value="+value+"］！");
	}
}