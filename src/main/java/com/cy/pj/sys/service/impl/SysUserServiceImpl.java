package com.cy.pj.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.cy.pj.common.annotation.RequiredLog;
import com.cy.pj.common.pojo.PageObject;
import com.cy.pj.common.util.AssertUtil;
import com.cy.pj.common.util.ShiroUtil;
import com.cy.pj.sys.dao.SysUserDao;
import com.cy.pj.sys.dao.SysUserRoleDao;
import com.cy.pj.sys.pojo.SysUser;
import com.cy.pj.sys.pojo.SysUserDept;
import com.cy.pj.sys.service.SysUserService;
/**
 * 当一个类使用了@Transactional注解以后表示类中所有方法都要进行事务控制，类上
 * 实用@Transactional注解用于定义方法上的事务共性，假如还有事务特性，可以在对应
 * 的方法上继续使用@Transactional注解定义事务特性。
 */
@Transactional(readOnly = false,
               rollbackFor =Throwable.class,
               timeout =-1,
               isolation = Isolation.READ_COMMITTED)
@Service
public class SysUserServiceImpl implements SysUserService {
    
	//@Autowired
	private SysUserDao sysUserDao;
	//@Autowired
	private SysUserRoleDao sysUserRoleDao;
	
	@Autowired
	public SysUserServiceImpl(SysUserDao sysUserDao,SysUserRoleDao sysUserRoleDao) {
		this.sysUserDao=sysUserDao;
		this.sysUserRoleDao=sysUserRoleDao;
	}
	
	@Override
	public int updatePassword(String sourcePassword, String newPassword, String cfgPassword) {
		//1.参数校验
		AssertUtil.isArgumentValid(StringUtils.isEmpty(sourcePassword), "原密码不能为空");
		AssertUtil.isArgumentValid(StringUtils.isEmpty(newPassword), "新密码不能为空");
		AssertUtil.isArgumentValid(!newPassword.equals(cfgPassword), "两次密码输入不一致");
		//2.判断原密码是否正确。
		SysUser user=ShiroUtil.getUser();
		String sourceSalt=user.getSalt();//获取数据库中登陆用户的密码盐值
		SimpleHash sh=new SimpleHash("MD5", sourcePassword, sourceSalt, 1);
		AssertUtil.isArgumentValid(!user.getPassword().equals(sh.toHex()), "原密码不正确");
		//3.修改密码
		String newSalt=UUID.randomUUID().toString();
		sh=new SimpleHash("MD5", newPassword, newSalt, 1);
		int rows=sysUserDao.updatePassword(sh.toHex(), newSalt, user.getId());
		return rows;
	}
	
	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> findObjectById(Integer id) {
		//1.参数校验
		AssertUtil.isArgumentValid(id==null||id<1, "参数无效");
		//2.基于用户id查询用户以及用户对应的部门信息
		SysUserDept user=sysUserDao.findById(id);
		AssertUtil.isResultValid(user==null, "对象可能已经不存在了");
		//3.基于用户id查询用户对应的角色信息
		List<Integer> roleIds=sysUserRoleDao.findRoleIdsByUserId(id);
		//4.封装结果并返回
		Map<String,Object> map=new HashMap<>();
		map.put("user", user);
		map.put("roleIds", roleIds);
		return map;
	}
	
	@Override
	public int updateObject(SysUser entity, Integer[] roleIds) {
		//1.参数校验
		AssertUtil.isArgumentValid(entity==null, "保存对象不允许为空");
		AssertUtil.isArgumentValid(
		StringUtils.isEmpty(entity.getUsername()), "用户名不能为空");
		AssertUtil.isArgumentValid(roleIds==null||roleIds.length==0, "必须为用户指定角色");
		//....
		//2.更新用户自身信息
		int rows=sysUserDao.updateObject(entity);
		//3.保存用户角色关系数据
		sysUserRoleDao.deleteObjectsByUserId(entity.getId());
		sysUserRoleDao.insertObjects(entity.getId(), roleIds);
		return rows;
	}
	@RequiredLog("保存用户行为信息")
	@Override
	public int saveObject(SysUser entity, Integer[] roleIds) {
		//1.参数校验
		AssertUtil.isArgumentValid(entity==null, "保存对象不允许为空");
		AssertUtil.isArgumentValid(
				StringUtils.isEmpty(entity.getUsername()), "用户名不能为空");
		AssertUtil.isArgumentValid(
				StringUtils.isEmpty(entity.getPassword()), "密码不能为空");
		AssertUtil.isArgumentValid(roleIds==null||roleIds.length==0, "必须为用户指定角色");
		//....
		//2.保存用户自身信息
		//2.1密码加密(本次算法使用md5加密->特点：不可逆，相同内容加密结果一定相同)
		String salt=UUID.randomUUID().toString();//让随机字符串作为加密盐
		System.out.println("salt="+salt);
		//spring框架内置的DigestUtils工具类实现其加密
		//String hashedPwd=DigestUtils.md5DigestAsHex((salt+entity.getPassword()).getBytes());
		//基于Shiro框架中的api实现盐值加密
		SimpleHash simpleHash=new SimpleHash(
				"MD5",//algorithmName 算法名称
				entity.getPassword(), 
				salt, 
				1);//1表示加密次数，加密次数越多安全就越好。
		String hashedPwd=simpleHash.toHex();//将加密结果转换为16进制
		System.out.println("hashedPwd="+hashedPwd);
		entity.setSalt(salt);
		entity.setPassword(hashedPwd);
		//2.2保存用户信息
		int rows=sysUserDao.insertObject(entity);
		//3.保存用户角色关系数据
		sysUserRoleDao.insertObjects(entity.getId(), roleIds);
		return rows;
	}
	/**
	 * @RequiresPermissions 这个注解描述的方法为一个切入点方法。此方法在执行之前
	  *    需要进行权限检测(负责这个过程的方法是一个通知方法)，假如用户权限中包含
	 * @RequiresPermissions 注解value属性指定的值，则授权访问，不包含则抛出异常。
	  *   思考：假如你去设计这个切入点对应的通知方法，你会做什么？
	  *1)目标方法执行之前获取方法上的@RequiresPermissions注解，进而取到注解中内容。
	  *2)将注解中内容提交(subject.checkPermission(perstr))给SecurityManager对象(此对象负责授权操作)
	  *3)SecurityManager会基于realm去查找用户拥有的权限(这部分我们自己实现)。
	  *4)SecurityManager会判断用户拥有权限中是否包含RequiresPermissions注解中的内容
	  *5)SecurityManager基于用户权限进行授权或抛出异常。
	 */
  //@RequiresPermissions(value="sys:user:update")
	@RequiresPermissions("sys:user:update")//value可以省略
	@RequiredLog("用户禁用启用")
	@Override
	public int validById(Integer id, Integer valid) {
		//1.参数校验
		AssertUtil.isArgumentValid(id==null||id<1, "id值无效");
		AssertUtil.isArgumentValid(valid==null||(valid!=0&&valid!=1), "状态值不正确");
		//2.修改状态
		int rows=sysUserDao.validById(id, valid, "admin");//将来admin为登陆用户，现在就是一个假数据
		//3.验证结果
		AssertUtil.isResultValid(rows==0, "记录可能已经不存在");
		return rows;
	}
	
	@Transactional(readOnly = true)//默认事务的优先级会相对比较高
	@RequiredLog("用户分页查询")
	@Override
	public PageObject<SysUserDept> findPageObjects(String username, 
			Integer pageCurrent) {
		String tName=Thread.currentThread().getName();
		System.out.println("SysUserServiceImpl.findPageObjects.thread.name="+tName);
		//try{Thread.sleep(20000);}catch(Exception e) {}
		//1.参数校验
		AssertUtil.isArgumentValid(pageCurrent==null||pageCurrent<1, "页码值不正确");
		//2.查询总记录数并校验
		int rowCount=sysUserDao.getRowCount(username);
		AssertUtil.isResultValid(rowCount==0, "没有对应的记录");
		//3.查询当前页记录
		int pageSize=3;
		int startIndex=(pageCurrent-1)*pageSize;
		List<SysUserDept> records=
		sysUserDao.findPageObjects(username, startIndex, pageSize);
		//4.封装查询结果
		PageObject<SysUserDept> pageObject= new PageObject<>(rowCount, pageSize, pageCurrent, records);
		return pageObject;
	}
}
