package com.cy.pj.common.aspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
/**
    * 定义一个异常切面对象，在此切面的通知方法中实现异常信息的记录
 * @author qilei
 * 记住：一个切面对象中不是一定要写上所有的通知方法，具体用什么方法由业务而定。
 */
@Slf4j
@Component
@Aspect
public class SysExceptionAspect {
	  /**
	       * 通过这个通知方法实现一个简易的异常信息的记录
	   * @param jp 连接点对象，此对象封装了你要执行的目标方法对象
	   * @param e 执行目标方法时出现的异常,这个名字一定要
	      *   与@AfterThrowing中的throwing属性值相同。
	   */
	  @AfterThrowing(value="bean(*ServiceImpl)",throwing = "e")
	  public void doRecordExceptionMsg(JoinPoint jp,Throwable e) {
		  //通过连接点对象获取正在执行的目标对象类型名称
		  String targetClassName=jp.getTarget().getClass().getName();
		  //通过连接点对象获取正在执行的方法的方法签名
		  MethodSignature ms=(MethodSignature)jp.getSignature();
		  log.error("{} invoke exception msg is {}",targetClassName+"."+ms.getName(),e.getMessage());
	  }
}
