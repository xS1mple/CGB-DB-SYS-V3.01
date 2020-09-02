package com.cy.pj.sys.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cy.pj.common.pojo.JsonResult;
import com.cy.pj.common.pojo.Node;
import com.cy.pj.sys.pojo.SysMenu;
import com.cy.pj.sys.service.SysMenuService;

@RequestMapping("/menu/")
@RestController

public class SysMenuController {
	@Autowired
	private SysMenuService sysMenuService;
	

	
	@RequestMapping("doSaveObject")
	public JsonResult doSaveObject(SysMenu entity) {
		sysMenuService.saveObject(entity);
		return new JsonResult("save ok");
	}
	@RequestMapping("doUpdateObject")
	public JsonResult doUpdateObject(SysMenu entity) {
		sysMenuService.updateObject(entity);
		return new JsonResult("update ok");
	}
	
	@RequestMapping("doFindZtreeMenuNodes")
	public JsonResult doFindZtreeMenuNodes() {
		List<Node> list=sysMenuService.findZtreeMenuNodes();
		return new JsonResult(list);
	}

	@RequestMapping("doDeleteObject")
	public JsonResult doDeleteObject(Integer id){
		sysMenuService.deleteObject(id);//假如在调用业务方法时出现了异常，下面的return肯不执行了
		return new JsonResult("delete ok");
	}


	@RequestMapping("doFindObjects")
	public JsonResult doFindObjects() {
		return new  JsonResult(sysMenuService.findObjects());
	}//系统底层会将对象转换为json格式字符串
	//底层是通过DispatcherServlet对象调用jackson api将对象转换为json串
	//说明：jackson api 将对象转换为json时会调用对象的get方法取值，并且将
	//get方法的get单词后面的名字作为key，方法的返回值作为value进行数据的转换。



}
