package com.cy.pj.sys.dao;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cy.pj.sys.pojo.SysMenu;

//@Slf4j
@SpringBootTest
public class SysMenuDaoTests {
	//初始化日志对象，假如使用lombok中的@Slf4j,底层会自动帮我们生成如下语句
    private static final Logger log=//接口默认指向的实现类为logback
    		LoggerFactory.getLogger(SysMenuDaoTests.class);
	@Autowired
	private SysMenuDao sysMenuDao;
	
	@Test
	void testFindObjects() {
		List<SysMenu> list=sysMenuDao.findObjects();
		//System.out.println(log.getClass().getName());
		log.info("list.size {}",list.size());
		
		for(SysMenu map:list) {
			System.out.println(map);
		}
		
		//拓展：(选学)
		//list.forEach(item->System.out.println(item));//JDK8 Lamda
		//list.forEach(System.out::println);//JDK8 方法引用
	}
}







