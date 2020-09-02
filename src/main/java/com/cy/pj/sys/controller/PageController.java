package com.cy.pj.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cy.pj.common.util.ShiroUtil;
import com.cy.pj.sys.pojo.SysUser;
import com.cy.pj.sys.pojo.SysUserMenu;
import com.cy.pj.sys.service.SysMenuService;
/**
  * 项目中所有页面的处理，计划都放在这个controller中
 */
@Controller
@RequestMapping("/")
public class PageController {
	@Autowired
	private SysMenuService sysMenuService;
	@RequestMapping("doLoginUI")
	public String doLoginUI(){
			return "login";
	}
	
	//localhost:80/doIndexUI
	@RequestMapping("doIndexUI")
	public String doIndexUI(Model model) {
		SysUser user=ShiroUtil.getUser();
		List<SysUserMenu> userMenus=
		sysMenuService.findUserMenusByUserId(user.getId());
		model.addAttribute("user", user);
		model.addAttribute("userMenus",userMenus);
		return "starter";//首页页面
	}
	/**基于此方法返回日志列表页面(记住此页面并不是完整页面)*/
//	@RequestMapping("log/log_list")
//	public String doLogUI() {
//		return "sys/log_list";
//	}
	
//	@RequestMapping("menu/menu_list")
//	public String doMenuUI() {
//		return "sys/menu_list";
//	}
	//....
	//rest 风格(一种架构风格)的url，其语法结构{变量名}/{变量}
	//PathVariable注解用于修饰方法参数，可以从rest风格的url中取和参数名对应的值
	@RequestMapping("{module}/{moduleUI}")
	public String doModuleUI(@PathVariable String moduleUI) {
			return "sys/"+moduleUI;
	}//
	/**基于此方法返回分页页面元素
	 * @throws InterruptedException */
	@RequestMapping("doPageUI")
	public String doPageUI(){
		//Thread.sleep(5000);
		return "common/page";
	}
}







