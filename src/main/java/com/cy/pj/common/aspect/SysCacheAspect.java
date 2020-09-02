package com.cy.pj.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cy.pj.common.cache.SimpleCache;

@Aspect
@Component
public class SysCacheAspect {
	   @Autowired
	   private SimpleCache simpleCache;
	   /**
	    * @annotation(..)为注解方式的切入点表达式，这里表示由RequiredCache注解描述
	         * 的方法为切入点方法。
	    */
	   @Pointcut("@annotation(com.cy.pj.common.annotation.RequiredCache)")
	   public void doCachePointCut() {}
	  
	   //@Pointcut("execution(* com.cy.pj.sys.service..*.updateObject(com.cy.pj.sys.pojo.SysDept))")
	   @Pointcut("@annotation(com.cy.pj.common.annotation.ClearCache)")
	   public void doClearCachePointCut() {}
	   
	   @AfterReturning("doClearCachePointCut()")
	   public void doAfterReturning() {//目标方法正常结束以后执行
		   System.out.println("==clear cache==");
		   simpleCache.clearObject();
	   }
//	   @AfterReturning("doClearCachePointCut()")
//	   public Object doAroundClearCache(ProceedingJoinPoint jp)throws Throwable{
//		   Object result=jp.proceed();
//		   simpleCache.clearObject();
//		   return result;
//	   }
	   
	   @Around("doCachePointCut()")
	   public Object around(ProceedingJoinPoint jp)throws Throwable{
		   System.out.println("Get data from cache");
		   Object obj=simpleCache.getObject("deptCache");//这个key的名字先自己写个固定值
		   if(obj!=null)return obj;
		   Object result=jp.proceed();//最终要执行目标方法
		   System.out.println("put data to cache");
		   simpleCache.putObject("deptCache", result);
		   return result;
	   }
}





