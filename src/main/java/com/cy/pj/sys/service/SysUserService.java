package com.cy.pj.sys.service;

import java.util.Map;

import com.cy.pj.common.pojo.PageObject;
import com.cy.pj.sys.pojo.SysUser;
import com.cy.pj.sys.pojo.SysUserDept;

public interface SysUserService {
	/**
	 * 修改密码
	 * @param sourcePassword 原密码(数据库中存储的密码)
	 * @param newPassword 新密码
	 * @param cfgPassword 密码确认
	 * @return
	 */
	int updatePassword(String sourcePassword,String newPassword,String cfgPassword);
	
	Map<String,Object> findObjectById(Integer id);
	/**
	 * 保存用户自身信息以及用户和角色的关系数据
	 * @param entity
	 * @param roleIds
	 * @return
	 */
	int updateObject(SysUser entity,Integer[]roleIds);
	/**
	 * 保存用户自身信息以及用户和角色的关系数据
	 * @param entity
	 * @param roleIds
	 * @return
	 */
	int saveObject(SysUser entity,Integer[]roleIds);
	/**
	 * 基于id修改用户状态
	 * @param id
	 * @param valid
	 * @return
	 */
	int validById(Integer id,Integer valid);

	PageObject<SysUserDept> findPageObjects(String username,Integer pageCurrent);
}
