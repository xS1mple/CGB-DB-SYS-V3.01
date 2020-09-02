package com.cy.pj.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.cy.pj.common.web.TimeAccessInterceptor;
/**
 * Spring Web模块的配置对象
 */
@Configuration
public class SpringWebConfig implements WebMvcConfigurer{

	/**注册拦截器*/
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new TimeAccessInterceptor())
		        .addPathPatterns("/user/doLogin");//设置要拦截的路径
		
	}
}
