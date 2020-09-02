package com.cy.pj.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cy.pj.common.pojo.JsonResult;
import com.cy.pj.common.pojo.PageObject;
import com.cy.pj.sys.pojo.SysLog;
import com.cy.pj.sys.service.SysLogService;

//@Controller
//@ResponseBody
@RestController  //RestController注解等效于@Controller+@ResponseBody
@RequestMapping("/log/")
public class SysLogController {

	@Autowired
	private SysLogService sysLogService;
	
	@RequestMapping("doDeleteObjects")
	//@ResponseBody
	public JsonResult doDeleteObjects(Integer... ids) {
		sysLogService.deleteObjects(ids);
		return new JsonResult("delete ok");
	}
	
	@RequestMapping("doFindPageObjects")
	//@ResponseBody
	public JsonResult doFindPageObjects(String username,Integer pageCurrent) {
		PageObject<SysLog> pageObject=sysLogService.findPageObjects(username, pageCurrent);
		//控制层对业务数据再次封装
		//方法1
		//JsonResult jr=new JsonResult();
		//jr.setData(pageObject);
		//方法2
		return new JsonResult(pageObject);//此位置封装为业务的正常数据
		//1)此值会返回给DispatcherServlet对象
		//2)DispatcherServlet对象会将JsonResult转换为json格式字符串然后响应到客户端
		//说明：转换过程是DispatcherServlet对象调用了jackson api来实现的
	}
	
}






