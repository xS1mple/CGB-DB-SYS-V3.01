package com.cy.pj.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cy.pj.common.pojo.JsonResult;
import com.cy.pj.sys.pojo.SysRole;
import com.cy.pj.sys.service.SysRoleService;

@RestController
@RequestMapping("/role/")
public class SysRoleController {

	 @Autowired
	 private SysRoleService sysRoleService;
	 
	 @RequestMapping("doFindObjectById")
	 public JsonResult doFindObjectById(Integer id) {
		 return new JsonResult(sysRoleService.findById(id));
	 }
	 
	 @RequestMapping("doFindRoles")
	 public JsonResult doFindObjects() {
		 return new JsonResult(sysRoleService.findObjects());
	 }
	 
	 
	 @RequestMapping("doUpdateObject")
	 public JsonResult doUpdateObject(SysRole entity,Integer[] menuIds) {
		 sysRoleService.updateObject(entity,menuIds);
		 return new JsonResult("update ok");
	 }
	 @RequestMapping("doSaveObject")
	 public JsonResult doSaveObject(SysRole entity,Integer[] menuIds) {
		 sysRoleService.saveObject(entity,menuIds);
		 return new JsonResult("save ok");
	 }
	 
	 @RequestMapping("doDeleteObject")
	 public JsonResult doDeleteObject(Integer id) {
		 sysRoleService.deleteObject(id);
		 return new JsonResult("delete ok");
	 }
	 
   //@RequestMapping(value="doFindPageObjects",method = RequestMethod.GET)
//	 @GetMapping("doFindPageObjects") //服务端只能处理Get请求
//	 public String doFindPageObjects(String name,Integer pageCurrent) throws JsonProcessingException{
//		 JsonResult jr=new JsonResult(sysRoleService.findPageObjects(name, pageCurrent));
//	     ObjectMapper om=new ObjectMapper();//第三方jackson提供
//	     return om.writeValueAsString(jr);//自己将对象转换为json格式字符串。
//	 }
	 
	 @GetMapping("doFindPageObjects") //服务端只能处理Get请求
	 public JsonResult doFindPageObjects(String name,Integer pageCurrent){
		return new JsonResult(sysRoleService.findPageObjects(name, pageCurrent));
	 }
}
