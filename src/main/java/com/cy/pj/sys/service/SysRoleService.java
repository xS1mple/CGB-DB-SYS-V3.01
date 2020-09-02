package com.cy.pj.sys.service;

import java.util.List;

import com.cy.pj.common.pojo.CheckBox;
import com.cy.pj.common.pojo.PageObject;
import com.cy.pj.sys.pojo.SysRole;
import com.cy.pj.sys.pojo.SysRoleMenu;

public interface SysRoleService {
	
	List<CheckBox> findObjects();
	
	/**
	 * 基于角色id查询角色以及角色对应的菜单id
	 * @param id
	 * @return
	 */
	SysRoleMenu findById(Integer id);
	
	/**
	 *   更新角色自身信息以及角色对应的菜单自身信息
	 * @param entity
	 * @param menuIds
	 * @return
	 */
	int updateObject(SysRole entity,Integer[] menuIds);
	/**
	  *  保存角色自身信息以及角色对应的菜单自身信息
	 * @param entity
	 * @param menuIds
	 * @return
	 */
	int saveObject(SysRole entity,Integer[] menuIds);
	/**
	 * 基于角色id删除角色以及角色对应的关系数据
	 * @param id
	 * @return
	 */
	int deleteObject(Integer id);

	PageObject<SysRole> findPageObjects(String name,Integer pageCurrent);
}
