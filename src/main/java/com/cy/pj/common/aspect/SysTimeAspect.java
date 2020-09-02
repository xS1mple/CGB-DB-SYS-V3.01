package com.cy.pj.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(2)
@Aspect
@Component
public class SysTimeAspect {

	  @Pointcut("bean(sysUserServiceImpl)")
	  public void doTimeAspect() {}
	  
	  @Before("doTimeAspect()")
	  public void doBefore() {
		  System.out.println("@Before");
	  }
	  @After("doTimeAspect()")
	  public void doAfter() {
		  System.out.println("@After");
	  }
	  @AfterReturning("doTimeAspect()")
	  public void doAfterReturning() {
		  System.out.println("@AfterReturning");
	  }
	  @AfterThrowing("doTimeAspect()")
	  public void doAfterThrowing() {
		  System.out.println("@AfterThrowing");
	  }
	  @Around("doTimeAspect()")
	  public Object doAround(ProceedingJoinPoint jp)throws Throwable {
		  System.out.println("SysTimeAspect.@Around.before");
		  try {
		  Object result=jp.proceed();//执行本类@Before(假如有),后续其它切面方法(假如有)，最后目标方法
		  System.out.println("SysTimeAspect.@Around.after");
		  return result;
		  }catch (Throwable e) {
		  System.out.println("@Around.exception");
		  throw e;
		  }
	  } 
	  
}
