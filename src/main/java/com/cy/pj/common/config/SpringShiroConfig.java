package com.cy.pj.common.config;
import java.util.LinkedHashMap;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cy.pj.sys.service.realm.ShiroUserRealm;

/** @Configuration 注解描述的类为spring框架中的一个配置类  */
@Configuration
public class SpringShiroConfig {
	
	@Bean
	public Realm realm() {
	  return new ShiroUserRealm();
	}

	@Bean
	protected CacheManager shiroCacheManager() {
	    return new MemoryConstrainedCacheManager();
	}
	
	@Bean
	public ShiroFilterChainDefinition shiroFilterChainDefinition() {
	    DefaultShiroFilterChainDefinition chainDefinition =
	    		new DefaultShiroFilterChainDefinition();
	    LinkedHashMap<String,String> filterMap=new LinkedHashMap<>();
		filterMap.put("/bower_components/**","anon");//anno为shiro框架定义，会对应一个过滤器对象，这里表示允许匿名访问
		filterMap.put("/build/**","anon");
		filterMap.put("/dist/**","anon");
		filterMap.put("/plugins/**","anon");
		filterMap.put("/user/doLogin", "anon");//登陆操作允许匿名访问
		//filterMap.put("/doIndexUI", "anon");//首页页面允许匿名访问
		filterMap.put("/doLogout", "logout");//logout为登出操作，此操作执行时会进入登陆页面
		//filterMap.put("/**", "authc");//authc为设置需要认证访问的资源
		filterMap.put("/**", "user");//user表示可以通过用户端提交的cookie信息进行认证
	    chainDefinition.addPathDefinitions(filterMap);
	    return chainDefinition;
	}
}







