package com.cy.pj.common.pojo;
import java.io.Serializable;
import lombok.Data;
/**
 * 借助此对象封装checkbox相关信息
 *@author qilei
 */
@Data
public class CheckBox implements Serializable{
	private static final long serialVersionUID = -3930756932197466333L;
	private Integer id;
	private String name;
}
