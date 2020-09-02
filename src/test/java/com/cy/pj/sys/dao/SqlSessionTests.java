package com.cy.pj.sys.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cy.pj.sys.pojo.SysDept;

@SpringBootTest
public class SqlSessionTests {

	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	@Test
	void testFindById() {
		//1.创建sqlSession
		SqlSession sqlSession=
		sqlSessionFactory.openSession();
		//2.执行会话操作
		SysDept sysDept=
		sqlSession.selectOne("com.cy.pj.sys.dao.SysDeptDao.findById", 6);
		System.out.println("sysDept="+sysDept);
		//3.释放资源
		sqlSession.close();
	}
}
