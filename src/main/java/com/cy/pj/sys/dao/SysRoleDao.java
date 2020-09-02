package com.cy.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.cy.pj.common.pojo.CheckBox;
import com.cy.pj.sys.pojo.SysRole;
import com.cy.pj.sys.pojo.SysRoleMenu;

@Mapper
public interface SysRoleDao {
	
	/**
	 * 查询所有角色的id和名字
	 * @return
	 */
	@Select("select id,name from sys_roles")
	List<CheckBox> findObjects();
	
	/**
	  *  基于角色id查询角色自身信息
	 * @param id
	 * @return
	 */
	//@Select("select id,name,note from sys_roles where id=#{id}")
	SysRoleMenu findById(Integer id);
	
	/**
	 *  更新角色自身信息
	 * @param entity
	 * @return
	 */
	int updateObject(SysRole entity);
	/**
	   * 保存角色自身信息
	 * @param entity
	 * @return
	 */
	int insertObject(SysRole entity);
	
	@Delete("delete from sys_roles where id=#{id}")
	int deleteObject(Integer id);
    /**
     *	基于角色名进行角色信息的模糊查询
     * @param name 角色名
     * @return
     */
	int getRowCount(String name);
	/**
	 *    按条件分页查询角色记录
	 * @param name 角色名
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	List<SysRole> findPageObjects(String name,Integer startIndex,Integer pageSize);
	
}
