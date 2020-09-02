package com.cy.pj.common.util;

import org.apache.shiro.SecurityUtils;

import com.cy.pj.sys.pojo.SysUser;

public class ShiroUtil {

	  public static String getUsername() {
		  return getUser().getUsername();
	  }
	  public static SysUser getUser() {
		  //从session中获取登陆用户
		  return (SysUser)SecurityUtils.getSubject().getPrincipal();
	  }
}
