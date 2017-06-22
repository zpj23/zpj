package com.goldenweb.sys.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.goldenweb.sys.pojo.SysFunction;

public interface FunctionService {

	//构建角色功能树
	public String bulidFunctionTree();
	
	//构建角色功能树
	public String bulidRoleFunctionTree(HttpServletRequest request,String roleid);
	
	//查询单个功能信息 by 主键
	public SysFunction showOneFunction(int id);
	
	//保存/编辑
	public Object saveFunction(List<SysFunction> list);
	
	//删除
	public String delFunction(int id);
	
	//保存角色和功能的关系
	public void saveRoleFunction(String roleid, String menuids);

	//左边导航栏显示子菜单
	public String showChildMenu(String pid,String userid);

	//功能菜单json
	public String showFunctionJson(String id);

	//保存功能菜单
	public SysFunction saveFunction(SysFunction menu);

	//插件是否存在下级菜单
	public List<Object[]> checkChildFunctionData(String id);

	//删除功能菜单
	public void deleleFunction(String id);
}
