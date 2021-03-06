package com.examw.netplatform.support;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import com.examw.netplatform.domain.admin.security.User;
import com.examw.netplatform.model.admin.security.UserInfo;
import com.examw.utils.AESUtil;
import com.examw.utils.HexUtil;
import com.examw.utils.MD5Util;

/**
 * 密码AES加密。
 * @author yangyong.
 * @since 2014-05-09.
 */
public final class PasswordHelper {
	private static final Logger logger = Logger.getLogger(PasswordHelper.class);
	private String algorithmName = "md5";
	private int hashIterations = 2;
	/**
	 * 设置密码验证算法名称。
	 * @param algorithmName
	 */
	public void setAlgorithmName(String algorithmName) {
		logger.debug("注入密码验证算法名称:" + algorithmName);
		this.algorithmName = algorithmName;
	}
	/**
	 * 设置迭代次数。
	 * @param hashIterations
	 */
	public void setHashIterations(int hashIterations) {
		logger.debug("注入迭代次数:" + hashIterations);
		this.hashIterations = hashIterations;
	}
	/**
	 * 创建AES对称加密密钥。
	 * @param account
	 * 用户账号。
	 * @return
	 * 密钥 md5(account+md5(account))。
	 */
	private static String createAESPasswordKey(String account){
		logger.debug("创建AES对称加密密钥..." + account);
		if(StringUtils.isBlank(account)) return null;
		return MD5Util.MD5(account + MD5Util.MD5(account));
	}
	/**
	 * 加密验证密码。
	 * @param user
	 */
	public String encryptPassword(User user){
		logger.debug("加密验证密码...");
		if(user == null || StringUtils.isBlank(user.getAccount())  || StringUtils.isBlank(user.getPassword())) return null;
		final String pwd = this.decryptAESPassword(user);
		
		return new SimpleHash(this.algorithmName, 
												 pwd, 
												 ByteSource.Util.bytes(user.getId()), 
												 hashIterations).toHex();
	}
	/**
	 * 加密用户密码。
	 * @param user
	 * 用户信息。
	 * @return
	 * 加密后的密码。
	 */
	public String encryptAESPassword(UserInfo info){
		logger.debug("加密用户密码...");
		if(info == null || StringUtils.isBlank(info.getAccount())  ||StringUtils.isBlank(info.getPassword())) return null;
		final String key = createAESPasswordKey(info.getAccount());
		final byte[] encrypts = AESUtil.encrypt(info.getPassword(), key);
		if(encrypts == null || encrypts.length == 0)return null;
		return HexUtil.parseBytesHex(encrypts);
	}
	/**
	 * 解密用户密码。
	 * @param user
	 * 用户。
	 * @return 
	 *  解密后的密码。
	 */
	public String decryptAESPassword(User user){
		logger.debug("解密用户密码...");
		if(user == null || StringUtils.isBlank(user.getAccount())  || StringUtils.isBlank(user.getPassword())) return null;
		
		final byte[] encrypts = HexUtil.parseHexBytes(user.getPassword());
		if(encrypts == null || encrypts.length == 0) return null;
		
		final String key = createAESPasswordKey(user.getAccount());
		if(StringUtils.isBlank(key)) return null;
		
		return AESUtil.decrypt(encrypts, key);
	}
}