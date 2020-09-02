package com.cy.pj.common.pojo;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *     基于此对象封装服务端要响应到客户端的数据，这个数据包含:
 *  1)状态码 (表示这个响应结果是正确的还是错误)
 *  2)状态信息(状态码对象的状态消息)
 *  3)正常的响应数据(例如一个查询结果)
 *  POJO:(VO-View Object 封装了表现层要呈现的数据)
 */
@Data
@NoArgsConstructor
public class JsonResult implements Serializable{
	 private static final long serialVersionUID = 5110901796917551720L;
	/**状态吗*/
	 private Integer state=1;
	 /**状态信息*/
	 private String message="ok";
	 /**正常的响应数据*/
	 private Object data;
	 
	 public JsonResult(String message){
		 this.message=message;
	 }
	 public JsonResult(Object data){
		 this.data=data;
	 }
	 public JsonResult(Throwable e){//Throwable为所有异常类的父类
		 this.state=0;
		 this.message=e.getMessage();
	 }
}
