package com.cy.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
   * 基于此到执行角色，菜单关系数据的操作
 * @author qilei
 *
 */
@Mapper
public interface SysRoleMenuDao {
	
	/**
	 *   基于多个角色id获取对应的菜单id
	 * @param roleIds
	 * @return
	 */
	List<Integer> findMenuIdsByRoleIds(List<Integer> roleIds);
	
	 /**
	  * 保存角色和菜单的关系数据
	  * @param roleId 角色id
	  * @param menuIds 角色对应的多个菜单id
	  * @return
	  */
	 int insertObjects(Integer roleId,Integer[] menuIds);
	 /**基于角色id删除角色和菜单的关系数据*/
	 @Delete("delete from sys_role_menus where role_id=#{roleId}")
	 int deleteObjectsByRoleId(Integer roleId);
     /**基于菜单id删除角色菜单关系数据*/
	 @Delete("delete from sys_role_menus where menu_id=#{menuId}")
	 int deleteObjectsByMenuId(Integer menuId);
	 
}
