package com.cy.pj.common.util;

import com.cy.pj.common.exception.ServiceException;

/**
   *  通过此工具类封装对参数，执行过程，结果等业务的校验。
 * @author qilei
 *
 */
public class AssertUtil {
	 /**验证参数的有效性*/
	 public static void isArgumentValid(boolean statement,String msg) {
		 if(statement)
			 throw new IllegalArgumentException(msg);
	 }
	 public static void isResultValid(boolean statement,String msg) {
		 if(statement)
			 throw new ServiceException(msg);
	 }
}
