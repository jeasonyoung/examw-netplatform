package com.examw.netplatform.service.admin.security.impl;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;

import com.examw.netplatform.model.admin.security.LoginLogInfo;
import com.examw.netplatform.service.admin.security.ILoginLogService;
import com.examw.netplatform.service.admin.security.IUserAuthentication;
import com.examw.netplatform.service.admin.security.VerifyCodeType;
import com.examw.netplatform.shiro.AgencyUsernamePasswordToken;
import com.examw.utils.VerifyCodeUtil;

/**
 * 用户认证服务接口实现类。
 * 
 * @author yangyong
 * @since 2014年10月28日
 */
public class UserAuthenticationImpl implements IUserAuthentication {
	private static final Logger logger = Logger.getLogger(UserAuthenticationImpl.class);
	private static final int default_verify_code_length = 4, default_verify_code_img_width = 90, default_verify_code_img_height = 30, default_verify_code_img_inter_line = 3;
	private Integer verifyCodeType, verifyCodeLength, verifyCodeImageHeight,verifyCodeImageWidth, verifyCodeImageInterLine;
	private ILoginLogService loginLogService;
	/**
	 * 设置验证码类型。
	 * @param verifyCodeType 
	 *	  验证码类型。
	 */
	public void setVerifyCodeType(Integer verifyCodeType) {
		logger.debug(String.format("注入验证码类型[%d]...", verifyCodeType));
		this.verifyCodeType = verifyCodeType;
	}
	/**
	 * 设置验证码长度。
	 * @param verifyCodeLength 
	 *	  验证码长度。
	 */
	public void setVerifyCodeLength(Integer verifyCodeLength) {
		logger.debug(String.format("注入验证码长度[%d]...", verifyCodeLength));
		this.verifyCodeLength = verifyCodeLength;
	}
	/**
	 * 设置验证码图片高度。
	 * @param verifyCodeImageHeight 
	 *	  验证码图片高度。
	 */
	public void setVerifyCodeImageHeight(Integer verifyCodeImageHeight) {
		logger.debug(String.format("注入验证码图片高度［%d］...", verifyCodeImageHeight));
		this.verifyCodeImageHeight = verifyCodeImageHeight;
	}
	/**
	 * 设置验证码图片宽度。
	 * @param verifyCodeImageWidth 
	 *	  验证码图片宽度。
	 */
	public void setVerifyCodeImageWidth(Integer verifyCodeImageWidth) {
		logger.debug(String.format("注入验证码图片宽度［%d］...", verifyCodeImageWidth));
		this.verifyCodeImageWidth = verifyCodeImageWidth;
	}
	/**
	 * 设置验证码图片干扰线条数。
	 * @param verifyCodeImageInterLine 
	 *	  验证码图片干扰线条数。
	 */
	public void setVerifyCodeImageInterLine(Integer verifyCodeImageInterLine) {
		logger.debug(String.format("注入验证码图片干扰线条数［%d］...", verifyCodeImageInterLine));
		this.verifyCodeImageInterLine = verifyCodeImageInterLine;
	}
	/**
	 * 设置登录日志服务接口。
	 * @param loginLogService 
	 *	  登录日志服务接口。
	 */
	public void setLoginLogService(ILoginLogService loginLogService) {
		logger.debug("注入登录日志服务接口...");
		this.loginLogService = loginLogService;
	}
	/*
	 * 创建验证码。
	 * @see com.examw.netplatform.service.admin.security.IUserAuthentication#createVerifyCode()
	 */
	@Override
	public String createVerifyCode() {
		logger.debug("创建验证码...");
		VerifyCodeType type = VerifyCodeType.parse(this.verifyCodeType);
		int len = this.verifyCodeLength == null ? default_verify_code_length : this.verifyCodeLength;
		return VerifyCodeUtil.generateTextCode(type.getValue(),len, null);
	}
	/*
	 * 加载验证码图片。
	 * @see com.examw.netplatform.service.admin.security.IUserAuthentication#loadVerifyCodeImage(java.lang.String)
	 */
	@Override
	public BufferedImage loadVerifyCodeImage(String verifyCode) {
		logger.debug(String.format("加载验证码［%s］图片...", verifyCode));
		if(StringUtils.isBlank(verifyCode)) throw new RuntimeException("验证码不存在！");
		int w = (this.verifyCodeImageWidth == null) ? default_verify_code_img_width : this.verifyCodeImageWidth,
			 h = (this.verifyCodeImageHeight == null) ? default_verify_code_img_height : this.verifyCodeImageHeight,
		    lines = (this.verifyCodeImageInterLine == null) ? default_verify_code_img_inter_line : this.verifyCodeImageInterLine;
		
		return VerifyCodeUtil.generateImageCode(verifyCode, w, h, lines, true, Color.WHITE, Color.RED, null);
	}
	/*
	 * 验证用户。
	 * @see com.examw.netplatform.service.admin.security.IUserAuthentication#authentication(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void authentication(String agencyId, String account, String password,String reqIP, String reqBrowser) throws Exception {
		logger.debug(String.format("验证用户［account = %1$s］［password = %2$s］［reqIP = %3$s］［reqBrowser = %4$s］...", account, password, reqIP, reqBrowser));
		try{
			//初始化令牌
			final AgencyUsernamePasswordToken token = new AgencyUsernamePasswordToken(agencyId, account, password);
			token.setRememberMe(false);
			//获取当前的Subject
			final Subject subject = SecurityUtils.getSubject();
			logger.debug("对用户["+agencyId+","+account+"]进行登录验证.....验证开始.");
			subject.login(token);
			logger.debug("对用户["+agencyId+","+account+"]进行登录验证.....验证通过.");
			
			//验证通过写入日志；
			final String userId = (String)subject.getPrincipal();
			if(StringUtils.isNotBlank(userId)){
				final LoginLogInfo info = new LoginLogInfo();
				info.setUserId(userId);
				info.setIp(reqIP);
				info.setBrowser(reqBrowser);
				this.loginLogService.update(info);
			}
		}catch(UnknownAccountException e){
			logger.error(String.format("登录验证未通过:未知账户[%s]", e.getMessage()), e);
			throw new UnknownAccountException("未知账户", e);
		}catch(IncorrectCredentialsException e){
			logger.error(String.format("登录验证未通过:密码不正确[%s]", e.getMessage()), e);
			throw new IncorrectCredentialsException("密码不正确", e);
		}catch(LockedAccountException e){
			logger.error(String.format("登录验证未通过:账户已锁定[%s]", e.getMessage()), e);
			throw new LockedAccountException("账户已锁定", e);
		}catch(ExcessiveAttemptsException e){
			logger.error(String.format("登录验证未通过:用户名或密码错误次数过多[%s]", e.getMessage()), e);
			throw new ExcessiveAttemptsException("用户名或密码错误次数过多", e);
		}catch(AuthenticationException e){
			logger.error(String.format("登录验证未通过:用户名或密码不正确[%s]",e.getMessage()), e);
			throw new AuthenticationException("用户名或密码不正确", e);
		}catch(Exception e){
			logger.error(String.format("登录验证未通过:未知错误[%s]",e.getMessage()), e);
			throw new Exception("未知错误", e);
		}
	}
	/*
	 * 用户注销。
	 * @see com.examw.netplatform.service.admin.security.IUserAuthentication#logout()
	 */
	@Override
	public void logout() {
		final Subject subject = SecurityUtils.getSubject();
		if(subject != null){
			logger.debug("注销用户..." + subject.getPrincipal());
			subject.logout();
		}
	}
}