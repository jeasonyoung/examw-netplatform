package com.examw.netplatform.service.admin.settings;
/**
 * 随机码服务接口。
 * 
 * @author yangyong
 * @since 2014年11月29日
 */
public interface IRandomCodeService {
	/**
	 * 创建随机码。
	 * @param length
	 * 随机码长度。
	 * @return
	 * 随机码。
	 */
	String loadRandomCode(Integer length);
}