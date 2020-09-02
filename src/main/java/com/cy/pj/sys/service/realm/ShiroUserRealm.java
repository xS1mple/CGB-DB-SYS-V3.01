package com.cy.pj.sys.service.realm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cy.pj.sys.dao.SysMenuDao;
import com.cy.pj.sys.dao.SysRoleMenuDao;
import com.cy.pj.sys.dao.SysUserDao;
import com.cy.pj.sys.dao.SysUserRoleDao;
import com.cy.pj.sys.pojo.SysUser;
//@Service
public class ShiroUserRealm extends AuthorizingRealm {
	@Autowired
    private SysUserDao sysUserDao;
	
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	
	@Autowired
	private SysMenuDao sysMenuDao;
	/**负责完成授权信息的获取和封装*/
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("===doGetAuthorizationInfo===");
		//1.获取登陆用户id
		SysUser user=(SysUser)principals.getPrimaryPrincipal();
		//2.基于用户id查找用户拥有的角色id
		List<Integer> roleIds=sysUserRoleDao.findRoleIdsByUserId(user.getId());
		if(roleIds==null||roleIds.size()==0)
			throw new AuthorizationException();
		//3.基于角色id查找对应的菜单id
		List<Integer> menuIds=sysRoleMenuDao.findMenuIdsByRoleIds(roleIds);
		if(menuIds==null||menuIds.size()==0)
			throw new AuthorizationException();
		//4.基于菜单id查找其授权标识
		List<String> permissions=sysMenuDao.findPermissions(menuIds);
		if(permissions==null||permissions.size()==0)
			throw new AuthorizationException();
		//5.封装信息并返回
		Set<String> stringPermissions=new HashSet<>();
		for(String per:permissions) {
			if(!StringUtils.isEmpty(per)) {
			   stringPermissions.add(per);
			}
		}
		System.out.println("stringPermissions="+stringPermissions);
		SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
		info.setStringPermissions(stringPermissions);
		return info;
	}
	/**负责完成认证信息的获取和封装*/
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
	    //1.获取用户提交的认证信息
		UsernamePasswordToken upToken=(UsernamePasswordToken)token;
		//2.基于用户名查找用户信息
		SysUser user=sysUserDao.findUserByUserName(upToken.getUsername());
		//3.判定用户是否存在
		if(user==null)
			throw new UnknownAccountException();
		//4.判定用户是否已被禁用(被禁用则不允许登陆)
		if(user.getValid()==0)
			throw new LockedAccountException();
		//5.封装认证信息并返回
		ByteSource credentialsSalt=ByteSource.Util.bytes(user.getSalt());
		SimpleAuthenticationInfo info=
		new SimpleAuthenticationInfo(
				user,//principal 用户身份(传什么，将来取出来就是什么)
				user.getPassword(),//hashedCredentials (已加密的密码)
				credentialsSalt,//credentialsSalt
				this.getName());//realmName
		return info;//返回值会传递给SecurityManager，此对象基于认证信息进行认证。
	}
	/**
	  * 设置加密算法
	 */
//	@Override
//	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
//		//构建加密匹配器对象
//		HashedCredentialsMatcher cMatcher=
//		new HashedCredentialsMatcher("MD5");
//		//设置加密次数
//		cMatcher.setHashIterations(1);
//		super.setCredentialsMatcher(cMatcher);
//	}
	//设置加密对象也可以使用getCredentialsMatcher方法进行封装
	@Override
	public CredentialsMatcher getCredentialsMatcher() {
		//构建加密匹配器对象
		HashedCredentialsMatcher cMatcher=
				new HashedCredentialsMatcher("MD5");
		//设置加密次数
		cMatcher.setHashIterations(1);
		return cMatcher;
	}

}
