package com.cy.pj.sys.dao;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cy.pj.sys.pojo.SysLog;
@Mapper
public interface SysLogDao {
	 /**
	  * 将用户行为日志写入到数据库
	  * @param entity
	  * @return
	  */
	 int insertObject(SysLog entity);
	  /**
	     * 基于多个记录id执行数据删除操作
	  * @param ids 记录id（可变参数）
	  * @return
	  */
	  int deleteObjects(Integer ...ids);// int deleteObjects(Integer[] ids)
	  
	  /**
	   * 	基于条件查询用户行为日志记录总数
	   * @param username 查询条件
	   * @return 查询到的记录总数
	   */
	  //int getRowCount(String username);
	  /**
	   * 	基于条件查询当前页记录
	   * @param username 查询条件
	   * @param startIndex 当前页数据的起始位置(用于limit 子句)
	   * @param pageSize 当前页面大小(每页最多显示多少条记录)
	   * @return 查询到的记录
	   */
	  List<SysLog> findPageObjects(String username);
}
