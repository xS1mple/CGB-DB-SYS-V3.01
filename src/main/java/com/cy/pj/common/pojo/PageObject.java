package com.cy.pj.common.pojo;
import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 	基于此对象封装业务执行结果
 * 	在Java语言，可以简单将内存中的对象分为两大类：
 *  1)存储数据的对象(设计的关键在属性上)-典型的POJO对象(VO,BO,DO)
 *  2)执行业务的对象(设计的关键在方法上)-典型的controller,service,dao
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageObject<T> implements Serializable{//pojo中的bo对象,new PageObject<SysLog>
	 private static final long serialVersionUID = -3130527491950235344L;
	 /**总记录数(从数据库查询出来的)*/
	 private Integer rowCount;
	 /**总页数(基于rowCount和页面大小计算出来的)*/
	 private Integer pageCount;
	 /**页面大小(每页最多可以呈现的记录总数)*/
	 private Integer pageSize;
	 /**当前页码值(客户端传递)*/
	 private Integer pageCurrent;
	 /**当前页记录,list集合中的T由PageObject类上定义的泛型决定*/
	 private List<T> records;
	 public PageObject(Integer rowCount, Integer pageSize, Integer pageCurrent, List<T> records) {
		super();
		this.rowCount = rowCount;
		this.pageSize = pageSize;
		this.pageCurrent = pageCurrent;
		this.records = records;
		//计算总页数的方法一:
		//this.pageCount=this.rowCount/this.pageSize;
		//if(this.rowCount%this.pageSize!=0)pageCount++;
		//计算总页数的方法二：
		this.pageCount=(rowCount-1)/pageSize+1;
	 }
}



