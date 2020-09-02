package com.cy.pj.sys.pojo;
import java.io.Serializable;
import java.util.List;
import lombok.Data;
/**
   * 基于此对象封装用户菜单信息.
 */
@Data
public class SysUserMenu implements Serializable{
	 private static final long serialVersionUID = 2152789115079901409L;
	 private Integer id;
	 private String name;
	 private String url;
	 private List<SysUserMenu> childs;
}
