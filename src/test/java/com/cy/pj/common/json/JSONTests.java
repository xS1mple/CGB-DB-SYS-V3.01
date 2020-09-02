package com.cy.pj.common.json;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.cy.pj.sys.pojo.SysRole;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class JSONTests {

	 @Test
	 void testJSON01() {
		System.out.println("{id:1,name:\"AA\",note:\"...\"}");
	 }
	 @Test
	 void testJSON02()throws Exception {
		 SysRole role=new SysRole();
		 role.setId(100);
		 role.setName("HR");
		 role.setNote("Hr....");
		 //将对象转换为JSON格式字符串
		 ObjectMapper om=new ObjectMapper();
		 String jsonStr=om.writeValueAsString(role);
		 System.out.println(jsonStr);
		 
		 //将字符串转换为对象
		 role=om.readValue(jsonStr, SysRole.class);
		 System.out.println(role);
		 //....
	 }
}




