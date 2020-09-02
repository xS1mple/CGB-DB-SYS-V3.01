package com.cy.pj.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.cy.pj.common.pojo.Node;
import com.cy.pj.sys.pojo.SysMenu;
import com.cy.pj.sys.pojo.SysUserMenu;


@Mapper
public interface SysMenuDao {
	/**
	  *  基于菜单id获取用户菜单信息(这里只查询一级菜单和二级菜单)
	 * @param menuIds
	 * @return
	 */
	List<SysUserMenu> findMenusByIds(List<Integer> menuIds);
	/**
	  * 基于多个菜单id查找菜单授权标识
	 * @param menuIds
	 * @return
	 */
	List<String> findPermissions(List<Integer> menuIds);
	
	int updateObject(SysMenu entity);
	
	int insertObject(SysMenu entity);
	/**
	  * 获取所有菜单的菜单id，name，parentId。
	 * @return
	 */
	@Select("select id,name,parentId from sys_menus")
	List<Node> findZtreeMenuNodes();

	/**
	  *  基于菜单id统计菜单对应的子菜单
	 * @param id
	 * @return
	 */
	@Select("select count(*) from sys_menus where parentId=#{id}")
	int getChildCount(Integer id);
	/**
	 * 删除菜单自身信息
	 * @param id
	 * @return
	 */
	@Delete("delete from sys_menus where id=#{id}")
	int deleteObject(Integer id);
	
	List<SysMenu> findObjects();
	//....
	
}
