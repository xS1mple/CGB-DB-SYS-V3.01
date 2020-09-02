package com.cy.pj.sys.pojo;

import java.io.Serializable;
import java.util.List;
import lombok.Data;
/**
 *    基于此对象封装，封装基于角色id查询到的角色自身信息以及角色对应的菜单id。
 * @author qilei
 */
@Data
public class SysRoleMenu implements Serializable{
	private static final long serialVersionUID = -2671028987524519218L;
	private Integer id;
    private String name;
    private String note;
    private List<Integer> menuIds;
}





