package com.cy.pj.common.web;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.cy.pj.common.exception.ServiceException;

public class TimeAccessInterceptor implements HandlerInterceptor {
    /**
           * 此方法会在目标Controller方法执行之前执行.
      @return 此返回值决定请求是否放行
     */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("==preHandler==");
		//获取当前日历对象
		Calendar c=Calendar.getInstance();
		int hour=c.get(Calendar.HOUR_OF_DAY);
		if(hour<=8||hour>=23)
			throw new ServiceException("不在访问时间范围:9~18");
		return true;//true表示请求继续继续执行,false表示请求到此结束,不再继续传递
	}//对于Calendar对象,在JDK8中还可以使用LocalDateTime.
}





