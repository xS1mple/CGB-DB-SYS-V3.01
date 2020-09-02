package com.cy.pj.common.cache;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
/**
 * 简单Cache对象的实现：SimpleCache
 *  产品级Cache要考虑：
* 1)存储结构,存储内容(存储对象字节还是存储对象引用)
* 2)缓存淘汰策略(缓存满的时是否要淘汰数据)
* 3)GC策略(JVM内存不足时，是否允许清除Cache中数据)
* 4)任务调度策略(是否需要每隔一段时间刷新一下缓存)
* 5)日志记录方式(记录命中次数)
* 6)线程并发安全策略
* 7).......
 */

@Component
public class SimpleCache {

	  private Map<Object,Object> cache=new ConcurrentHashMap<>();
	  
	  public boolean putObject(Object key,Object value) {
		  cache.put(key, value);
		  return true;
	  }
	  public Object getObject(Object key) {
		  return cache.get(key);
	  }
	  public void clearObject() {
		  cache.clear();
	  }
	  //.......
}
