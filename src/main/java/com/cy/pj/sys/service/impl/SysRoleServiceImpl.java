package com.cy.pj.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cy.pj.common.pojo.CheckBox;
import com.cy.pj.common.pojo.PageObject;
import com.cy.pj.common.util.AssertUtil;
import com.cy.pj.sys.dao.SysRoleDao;
import com.cy.pj.sys.dao.SysRoleMenuDao;
import com.cy.pj.sys.dao.SysUserRoleDao;
import com.cy.pj.sys.pojo.SysRole;
import com.cy.pj.sys.pojo.SysRoleMenu;
import com.cy.pj.sys.service.SysRoleService;

@Service
public class SysRoleServiceImpl implements SysRoleService {

	@Autowired
	private SysRoleDao sysRoleDao;
	
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	
	@Override
	public List<CheckBox> findObjects() {
		// TODO Auto-generated method stub
		return sysRoleDao.findObjects();
	}
	
	//方案2：业务层查询1次
	@Override
	public SysRoleMenu findById(Integer id) {
		//1.参数校验
		AssertUtil.isArgumentValid(id==null||id<1, "id值无效");
		//2.查询角色自身信息
		SysRoleMenu sysRoleMenu=sysRoleDao.findById(id);
		System.out.println("sysRoleMenu="+sysRoleMenu);
		return sysRoleMenu;
	}
	
	//方案1：业务层多次查询
//	@Override
//	public SysRoleMenu findById(Integer id) {
//		//1.参数校验
//		AssertUtil.isArgumentValid(id==null||id<1, "id值无效");
//		//2.查询角色自身信息
//		SysRoleMenu sysRoleMenu=sysRoleDao.findById(id);
//		//3.查询角色对应的菜单id
//		List<Integer> menuIds=sysRoleMenuDao.findMenuIdsByRoleId(id);
//		//4.封装查询结果并返回
//		sysRoleMenu.setMenuIds(menuIds);
//		return sysRoleMenu;
//	}
	
	@Override
	public int saveObject(SysRole entity, Integer[] menuIds) {
		//1.参数校验
		AssertUtil.isArgumentValid(entity==null, "保存对象不能为空");
		AssertUtil.isArgumentValid(StringUtils.isEmpty(entity.getName()), "角色名不能为空");
		AssertUtil.isArgumentValid(menuIds==null||menuIds.length==0, "必须为角色分配权限");
		//...
		//2.保存角色自身信息
		int rows=sysRoleDao.insertObject(entity);//执行完此语句以后，entity对象中会存储一个id值
		//3.保存角色菜单关系数据
		sysRoleMenuDao.insertObjects(entity.getId(), menuIds);
		return rows;
	}
	@Override
	public int updateObject(SysRole entity, Integer[] menuIds) {
		//1.参数校验
		AssertUtil.isArgumentValid(entity==null, "保存对象不能为空");
		AssertUtil.isArgumentValid(StringUtils.isEmpty(entity.getName()), "角色名不能为空");
		AssertUtil.isArgumentValid(menuIds==null||menuIds.length==0, "必须为角色分配权限");
		//...
		//2.更新角色自身信息
		int rows=sysRoleDao.updateObject(entity);//执行完此语句以后，entity对象中会存储一个id值
		AssertUtil.isResultValid(rows==0, "记录可能已经不存在了");
		//3.更新角色菜单关系数据
		//3.1删除原有关系数据
		sysRoleMenuDao.deleteObjectsByRoleId(entity.getId());
		//3.2再写入新的关系数据
		sysRoleMenuDao.insertObjects(entity.getId(), menuIds);
		return rows;
	}
	
	@Override
	public int deleteObject(Integer id) {
		//1.参数校验
		AssertUtil.isArgumentValid(id==null||id<1, "参数无效");
		//2.删除关系表中关系数据
		//2.1删除角色菜单关系数据
		sysRoleMenuDao.deleteObjectsByRoleId(id);
		//2.2删除用户角色关系数据
		sysUserRoleDao.deleteObjectsByRoleId(id);
		//3.删除角色自身信息
		int rows=sysRoleDao.deleteObject(id);
		//4.结果检验
		AssertUtil.isResultValid(rows==0, "记录可能已经不存在");
		return 0;
	}
	
	@Override
	public PageObject<SysRole> findPageObjects(String name, Integer pageCurrent) {
		//1.参数校验
		//if(pageCurrent==null||pageCurrent<1)
			//throw new IllegalArgumentException("页码值不正确");
		AssertUtil.isArgumentValid(pageCurrent==null||pageCurrent<1, "页码值不正确");
		//2.基于条件查询总记录数并校验
		int rowCount=sysRoleDao.getRowCount(name);
		//if(rowCount==0)
			//throw new ServiceException("没有对应记录");
		AssertUtil.isResultValid(rowCount==0, "没有对应记录");
		//3.查询当前页要呈现的角色记录
		int pageSize=2;
		int startIndex=(pageCurrent-1)*pageSize;
		List<SysRole> records=
		sysRoleDao.findPageObjects(name, startIndex, pageSize);
		//4.封装查询结果并返回。
		return new PageObject<>(rowCount, pageSize, pageCurrent, records);
	}

}
