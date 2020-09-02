package com.cy.pj.common.web;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cy.pj.common.pojo.JsonResult;

/**
 * @ControllerAdvice 注解描述的类，spring mvc会认为它是一个控制层全局异常处理对象。
 */
//@ControllerAdvice
//@ResponseBody 
@RestControllerAdvice //等效于@ControllerAdvice+@ResponseBody 
public class GlobalExceptionHandler {
	    @ExceptionHandler(ShiroException.class) 
	   //@ResponseBody
		public JsonResult doHandleShiroException(
				ShiroException e) {
			JsonResult r=new JsonResult();
			r.setState(0);
			if(e instanceof UnknownAccountException) {
				r.setMessage("账户不存在");
			}else if(e instanceof LockedAccountException) {
				r.setMessage("账户已被禁用");
			}else if(e instanceof IncorrectCredentialsException) {
				r.setMessage("密码不正确");
			}else if(e instanceof AuthorizationException) {
				r.setMessage("没有此操作权限");
			}else {
				r.setMessage("系统维护中");
			}
			e.printStackTrace();
			return r;
		}

      /**
       * @ExceptionHandler 注解描述的方法为异常处理方法(不是我们定的),
               *   此注解中定义的异常类型，为这个方法可以处理的异常类型，它可以处理
               *   此异常以及这个异常类型的子类类型的异常。
       * @param e 此参数用于接收具体异常对象，其类型一般与@ExceptionHandler
               *   注解中定义异常类型相同或者为其父类类型。
       * @return 封装了异常信息的对象
       */
	  @ExceptionHandler(RuntimeException.class)
	  //告诉spring mvc最终将响应结果转换为json格式进行输出。
	 // @ResponseBody 
	  public JsonResult doHandleRuntimeException(RuntimeException e) {
		  //方法内部实现异常处理
		  //例如：输出异常，封装异常
		  //输出或打印异常
		  e.printStackTrace();
		  //封装异常信息
		  return new JsonResult(e);
	  }
	  //。。。。。。。。。。。
}
