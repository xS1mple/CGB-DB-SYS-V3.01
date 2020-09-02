package com.cy.pj.sys.service.impl;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cy.pj.common.annotation.RequiredCache;
import com.cy.pj.common.annotation.RequiredLog;
import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.pojo.PageObject;
import com.cy.pj.sys.dao.SysLogDao;
import com.cy.pj.sys.pojo.SysLog;
import com.cy.pj.sys.service.SysLogService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Transactional(isolation = Isolation.READ_COMMITTED,readOnly = false)
@Service
public class SysLogServiceImpl implements SysLogService {

	@Autowired
	private SysLogDao sysLogDao;
	/**
	 * Propagation.REQUIRES_NEW 表示写日志的操作始终会运行在一个独立的事务中
	 */
	@Async //使用此注解描述的方法会运行在由spring框架提供的一个线程中。
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public void saveObject(SysLog entity) {
		String tName=Thread.currentThread().getName();
		System.out.println("SysLogServiceImpl.saveObject.thread.name="+tName);
		//try{Thread.sleep(5000);}catch(Exception e) {}
		sysLogDao.insertObject(entity);
	}
	
	@RequiresPermissions("sys:log:delete")
	@Override
	public int deleteObjects(Integer... ids) {
		//1.参数校验
		if(ids==null||ids.length==0)
			throw new IllegalArgumentException("参数值无效");
		//2.执行删除
		int rows=sysLogDao.deleteObjects(ids);
		//3.验证结果
		if(rows==0)
			throw new ServiceException("记录可能已经不存在");
		return rows;
	}
	@Override
	public PageObject<SysLog> findPageObjects(String username, Integer pageCurrent) {
		//1.参数校验。
		if(pageCurrent==null||pageCurrent<1)
			throw new IllegalArgumentException("页码值无效");
		//2.基于用户名查询总记录数并校验。
		//3.基于pageCurrent查询当前页记录。
		int pageSize=3;//每页最多显示多少条记录
		Page<SysLog> page=PageHelper.startPage(pageCurrent, pageSize);
		List<SysLog> records=sysLogDao.findPageObjects(username);
		//4.对查询结果进行封装并返回。
		return new PageObject<>((int)page.getTotal(),
				pageSize, pageCurrent, records);
	    //return new PageObject<>((int)page.getTotal(),page.getPages(), pageSize, pageCurrent, records);
	}

}
