package com.examw.netplatform.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.examw.netplatform.domain.admin.security.User;
import com.examw.netplatform.service.admin.security.IUserAuthorization;
import com.examw.netplatform.support.PasswordHelper;
import com.examw.service.Status;

/**
 * 用户认证。
 * @author yangyong.
 * @since 2014-05-13.
 */
public class UserRealm extends AuthorizingRealm implements IUserCache {
	private static final Logger logger = Logger.getLogger(UserRealm.class);
	private IUserAuthorization userAuthorization;
	private PasswordHelper passwordHelper;
	/**
	 * 设置用户授权服务接口。
	 * @param userAuthorization
	 * 用户授权服务接口。
	 */
	public void setUserAuthorization(IUserAuthorization userAuthorization) {
		logger.debug("注入用户授权服务接口...");
		this.userAuthorization = userAuthorization;
	}
	/**
	 * 设置密码工具。
	 * @param passwordHelper
	 * 密码工具。
	 */
	public void setPasswordHelper(PasswordHelper passwordHelper) {
		logger.debug("注入密码工具...");
		this.passwordHelper = passwordHelper;
	}
	/*
	 * 执行获取授权信息。
	 * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		logger.debug("执行获取授权信息...");
		final String userId = (String)principals.fromRealm(this.getName()).iterator().next();
		logger.debug("获取当前用户ID:" + userId);
		if(StringUtils.isNotBlank(userId)){
			//初始化
			final SimpleAuthorizationInfo authorInfo = new SimpleAuthorizationInfo();
			//加载角色集合
			authorInfo.setRoles(this.userAuthorization.findRolesByUser(userId));
			//加载权限集合
			authorInfo.setStringPermissions(this.userAuthorization.findPermissionsByUser(userId));
			//返回
			return authorInfo;
		}
		return null;
	}
	/*
	 * 执行获得认证信息。
	 * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		logger.debug("执行获得认证信息...");
		//机构用户令牌
		final AgencyUsernamePasswordToken aupToken = (AgencyUsernamePasswordToken)token;
		if(aupToken == null) throw new AuthenticationException("用户密码令牌不存在!");
		//检查用户
		if(StringUtils.isBlank(aupToken.getAgencyId())) throw new UnknownAccountException("未设置机构ID!");
		final User user = this.userAuthorization.loadUserByAccount(aupToken.getAgencyId(), aupToken.getUsername());
		if(user == null) throw new UnknownAccountException("账号不存在!");
		//用户检查
		if(user.getStatus() == Status.DISABLE.getValue()){
			throw new LockedAccountException("账号已暂停使用!");
		}
		//用户密码
		final String pwd = this.passwordHelper.encryptPassword(user);
		//交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配。
	   return new SimpleAuthenticationInfo(user.getId(), pwd, ByteSource.Util.bytes(user.getId()), this.getName());
	}
    /*
     * 移除用户缓存。
     * @see com.examw.netplatform.shiro.IShiroUserCache#removeUserCache(java.lang.String)
     */
	@Override
	public void removeUserCache(String userId) {
		logger.debug(String.format("移除用户ID［userId ＝ %s］缓存...", userId));
		if(StringUtils.isEmpty(userId)) return;
		//
		SimplePrincipalCollection pc = new SimplePrincipalCollection();
		pc.add(userId, this.getName());
		//删除认证缓存
		this.clearCachedAuthenticationInfo(pc);
		//删除授权缓存
		this.clearCachedAuthorizationInfo(pc);
	}
	/*
	 * 移除全部权限集合。
	 * @see com.examw.netplatform.shiro.IUserCache#removeAllPermissions()
	 */
	@Override
	public void removeAllPermissions() {
		logger.debug("移除全部权限集合...");
		this.getAuthorizationCache().clear();
	}
}