package com.cy.pj.common.aspect;
import java.lang.reflect.Method;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cy.pj.common.annotation.RequiredLog;
import com.cy.pj.common.util.IPUtils;
import com.cy.pj.common.util.ShiroUtil;
import com.cy.pj.sys.pojo.SysLog;
import com.cy.pj.sys.pojo.SysUser;
import com.cy.pj.sys.service.SysLogService;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * @Aspect 注解描述的类型为Spring AOP中的切面对象类型。此对象中可以封装：
 * 1)切入点(定义在哪些目标对象的哪些方法上进行功能扩展)
 * 2)通知(封装功能扩展的业务逻辑)
 */
//@Slf4j
//@Order(1) //优先级比较高
@Aspect
@Component
public class SysLogAspect {
	
	  //假如使用了lombok注解@Slf4j描述类，默认会在类的内部生成如下语句。
	  private static final Logger log = 
			  LoggerFactory.getLogger(SysLogAspect.class); 
       /**
        * PointCut注解用于定义切入点，具体方式可以基于特定表达式进行实现。例如
        * 1)bean为一种切入点表达式类型
        * 2)sysUserServiceImpl 为spring容器中的一个bean的名字
        * 	这里的涵义是当sysUserServiceImpl对象中的任意方法执行时，都由本切面
        * 	对象的通知方法做功能增强。
        */
	  //@Pointcut("bean(sysUserServiceImpl)")
	  //注解方式的切入点表达式
	  @Pointcut("@annotation(com.cy.pj.common.annotation.RequiredLog)")
	  public void doLogPointCut() {}//此方法中不需要写任何代码
	   
	   /**由@Around注解描述的方法为一个环绕通知方法，我们可以在此方法内部
	          * 手动调用目标方法(通过连接点对象ProceedingJoinPoint的proceed方法进行调用)
	          * 环绕通知：此环绕通知使用的切入点为bean(sysUserServiceImpl)
	          * 环绕通知特点：
	     	1)编写：
	           a)方法的返回值为Object.
	           b)方法参数为ProceedingJoinPoint类型.
	           c)方法抛出异常为throwable.
	        2)应用：
	           a)目标方法执行之前或之后都可以进行功能拓展
	           b)相对于其它通知优先级最高。
	     @param jp 为一个连接对象(封装了正在要执行的目标方法信息)
	     @return 目标方法的执行结果
	     */
	   @Around(value="doLogPointCut()")
	   public Object around(ProceedingJoinPoint jp)throws Throwable{
		    long start=System.currentTimeMillis();
		    log.info("method start {}",start);
		   try {
		    Object result=jp.proceed();//本类@Before，下一个切面，目标方法
		    long end=System.currentTimeMillis();
		    log.info("method end {}",end);
		    //将用户的正常的行为信息写入到数据库
		    saveUserLog(jp,(end-start));
		    return result;
		   }catch(Throwable e) {
		    log.error("method error {},error msg is {}",
				     System.currentTimeMillis(),e.getMessage());
		    //saveUserExpLog(jp,e,(end-start));
		    throw e;
		   }
	   }
	   @Autowired
	   private SysLogService sysLogService;
	   private void saveUserLog(ProceedingJoinPoint jp,long time)throws Exception {
		   //1.获取用户行为日志信息
		   //获取目标对象(要执行的那个目标业务对象)类
		   Class<?> targetCls=jp.getTarget().getClass();
		   //获取方法签名对象(此对象中封装了要执行的目标方法信息)
		   MethodSignature ms=(MethodSignature) jp.getSignature();
		   //获取目标方法对象，基于此对象获取方法上的RequiredLog注解，进而取到操作名
		   //Method method=ms.getMethod();//假如是jdk代码默认取到的是接口中的方法对象
		   Method targetMethod=
		   targetCls.getMethod(ms.getName(),ms.getParameterTypes());
		   RequiredLog required=targetMethod.getAnnotation(RequiredLog.class);
		   //获取操作名
		   String operation="operation";
		   if(required!=null) {//注解方式的切入点无须做此判断
			   operation=required.value();
		   }
		   //获取目标方法类全名
		   String targetMethodName=targetCls.getName()+"."+ms.getName();
		   //2.构建用户行为日志对象封装用户行为信息
		   SysLog log=new SysLog();
		   log.setIp(IPUtils.getIpAddr());
		   log.setUsername(ShiroUtil.getUsername());//将来的登陆用户，现在可以先写个假数据
		   log.setOperation(operation);//不知道该写什么
		   log.setMethod(targetMethodName);//类全名.方法名
		   //log.setParams(Arrays.toString(jp.getArgs()));//将参数对象转换为普通串
		   //将参数对象尽量转换为json格式字符串。
		   log.setParams(new ObjectMapper().writeValueAsString(jp.getArgs()));
		   log.setTime(time);
		   log.setCreatedTime(new java.util.Date());
		   //3.用户行为日志写入到数据库
		   sysLogService.saveObject(log);
		   
//		   new Thread() { //高并发环境下可能很快就会出现内存溢出
//			   public void run() {
//				   sysLogService.saveObject(log);
//			   };
//		   }.start();
		   
	   }
	   
}


