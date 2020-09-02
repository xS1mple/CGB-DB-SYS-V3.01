package com.cy.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cy.pj.sys.pojo.SysUser;
import com.cy.pj.sys.pojo.SysUserDept;

@Mapper
public interface SysUserDao {
	/**
	 *   
	 *   修改密码
	 * @param newHashedPassword 已加密的密码
	 * @param newSalt 新的盐值
	 * @param id 登陆用户id
	 * @return
	 */
	@Update("update sys_users set password=#{newHashedPassword},salt=#{newSalt},modifiedTime=now() where id=#{id}")
	int updatePassword(String newHashedPassword,String newSalt,Integer id);
	
	@Select("select * from sys_users where username=#{username}")
	SysUser findUserByUserName(String username);
	/**
	  * 基于用户id查询用户以及用户对应的部门信息
	 * @param id
	 * @return
	 */
	SysUserDept findById(Integer id);
	/**
	  *   基于此方法保存用户自身信息
	 * @param entity
	 * @return
	 */
	int updateObject(SysUser entity);
	/**
	 *   基于此方法保存用户自身信息
	 * @param entity
	 * @return
	 */
	int insertObject(SysUser entity);
	/**
	 *   修改用户状态
	 * @param id
	 * @param valid
	 * @param modifiedUser
	 * @return
	 */
	@Update("update sys_users set valid=#{valid},modifiedUser=#{modifiedUser},modifiedTime=now() where id=#{id}")
	int validById(Integer id,Integer valid,String modifiedUser);

	/**
	 * 基于条件查询记录总数
	 * @param username
	 * @return
	 */
	int getRowCount(String username);
	/**
	 * 基于条件查询当前页要呈现的记录
	 * @param username
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	List<SysUserDept> findPageObjects(String username,
			                          Integer startIndex,
			                          Integer pageSize);
}
