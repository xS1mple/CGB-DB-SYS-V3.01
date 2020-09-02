package com.cy.pj.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.pojo.Node;
import com.cy.pj.sys.dao.SysMenuDao;
import com.cy.pj.sys.dao.SysRoleMenuDao;
import com.cy.pj.sys.dao.SysUserRoleDao;
import com.cy.pj.sys.pojo.SysMenu;
import com.cy.pj.sys.pojo.SysUserMenu;
import com.cy.pj.sys.service.SysMenuService;
@Service
public class SysMenuServiceImpl implements SysMenuService {

	@Autowired
	private SysMenuDao sysMenuDao;
	
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	
	
	@Override
	public List<SysUserMenu> findUserMenusByUserId(Integer id) {
	    //1.基于用户id获取用户对应的角色id
		List<Integer> roleIds=sysUserRoleDao.findRoleIdsByUserId(id);
		//2.基于角色id获取角色对应的菜单id
		List<Integer> menuIds=sysRoleMenuDao.findMenuIdsByRoleIds(roleIds);
		//3.基于菜单id获取对应的菜单信息(一级和二级菜单)
		List<SysUserMenu> userMenus=sysMenuDao.findMenusByIds(menuIds);
		return userMenus;
	}
	
	
    /**@CacheEvict 注解描述的方法，表示在方法业务执行过程中要清除缓存
     * value属性：用于指定要清除的缓存对象
     * allEntries属性：表示要清除缓存对象中的哪些属性(true为所有)
     * beforeInvocation属性：表示清除缓存的动作在方法执行之前还是之后执行。
     */
	@CacheEvict(value = "menuCache",allEntries = true,beforeInvocation = false)
	@Override
	public int updateObject(SysMenu entity) {
		//1.参数校验
		if(entity==null)
			throw new IllegalArgumentException("更新对象不能为空");
		if(StringUtils.isEmpty(entity.getName()))
			throw new IllegalArgumentException("菜单名不能为空");
		//......
		//2.保存对象
		int rows=sysMenuDao.updateObject(entity);
		return rows;
	}
	@CacheEvict(value = "menuCache",allEntries = true,beforeInvocation = false)
	@Override
	public int saveObject(SysMenu entity) {
		//1.参数校验
		if(entity==null)
			throw new IllegalArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getName()))
			throw new IllegalArgumentException("菜单名不能为空");
		//......
		//2.保存对象
		int rows=sysMenuDao.insertObject(entity);
		return rows;
	}
	
	@Override
	public List<Node> findZtreeMenuNodes() {
		return sysMenuDao.findZtreeMenuNodes();
	}
	@CacheEvict(value = "menuCache",allEntries = true,beforeInvocation = false)
	@Override
	public int deleteObject(Integer id) {
		//1.参数校验
		if(id==null||id<1)
			throw new IllegalArgumentException("参数值无效");
		//2.统计菜单子元素并校验。
		int childCount=sysMenuDao.getChildCount(id);
		if(childCount>0)
			throw new ServiceException("请先删除子菜单");
		//3.删除关系数据(角色和菜单关系数据)
		sysRoleMenuDao.deleteObjectsByMenuId(id);
		//4.删除自身信息。
		int rows=sysMenuDao.deleteObject(id);
		if(rows==0)
			throw new ServiceException("记录可能已经不存在");
		return rows;
	}
	//对应spring框架而言，他的内部可以基于不同的模块创建不同的cache
	//@Cacheable 对象用户告诉spring框架,目标方法的返回值应存储到cache对象。
	//Map<String,Cache>
	@Cacheable(value = "menuCache")//menuCache为缓存对象的名称(自己起)
	@Override
	public List<SysMenu> findObjects() {
		System.out.println("==sysMenuServiceImpl.findObjects==");
		List<SysMenu> list=
				sysMenuDao.findObjects();
		if(list==null||list.size()==0)
			throw new ServiceException("没有对应的菜单信息");
		return list;
		//在配置了@Cacheable以后，这个方法返回值会存储到menuCache对应的缓存中。
		//对于menuCache这个名字对应的缓存对象的key为实际参数组合构成的SimpleKey对象。
	}


}
