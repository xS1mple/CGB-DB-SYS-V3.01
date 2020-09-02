package com.cy.pj.common.exception;
/**
   * 自定义非检查异常
   *   目的：对对业务中的信息进行更好的反馈和定位
 * @author qilei
  *   说明：此类中的构造方法参考父类构造方法进行实现
 */
public class ServiceException extends RuntimeException {//Throwable
	private static final long serialVersionUID = -9085326160255400760L;
	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}
	public ServiceException(String message) {
		super(message);
	}
	public ServiceException(Throwable cause) {
		super(cause);
	}
}
