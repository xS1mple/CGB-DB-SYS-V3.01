package com.cy.pj.sys.service;

import com.cy.pj.common.pojo.PageObject;
import com.cy.pj.sys.pojo.SysLog;

public interface SysLogService {
	
	
	void saveObject(SysLog entity);
	/**
	  *   基于记录id删除日志信息
	 * @param ids
	 * @return 删除的行数
	 */
	int deleteObjects(Integer... ids);
     
	/**
	 * 	基于条件进行分页查询
	 * @param userame 查询条件
	 * @param pageCurrent 当前页面
	 * @return 查询到记录信息以及分析信息
	 */
	PageObject<SysLog> findPageObjects(String username,Integer pageCurrent);
	
}
