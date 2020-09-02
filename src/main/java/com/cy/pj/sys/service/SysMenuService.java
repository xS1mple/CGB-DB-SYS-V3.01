package com.cy.pj.sys.service;

import java.util.List;
import java.util.Map;

import com.cy.pj.common.pojo.Node;
import com.cy.pj.sys.pojo.SysMenu;
import com.cy.pj.sys.pojo.SysUserMenu;

public interface SysMenuService {
	/**
	 * 基于用户id获取用户对应的菜单信息
	 * @param id
	 * @return
	 */
	List<SysUserMenu> findUserMenusByUserId(Integer id);
	
	int updateObject(SysMenu entity);
	
	int saveObject(SysMenu entity);
	
	List<Node> findZtreeMenuNodes();
	/**
	  *  基于菜单id删除角色菜单关系数据，再删除自身信息。
	 * @param id
	 * @return
	 */
	int deleteObject(Integer id);
	List<SysMenu> findObjects();
}
