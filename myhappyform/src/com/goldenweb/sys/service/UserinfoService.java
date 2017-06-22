package com.goldenweb.sys.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.goldenweb.sys.pojo.SysFunction;
import com.goldenweb.sys.pojo.SysUserinfo;

public interface UserinfoService {

	//查询该用户是否存在
	public SysUserinfo findLogin(SysUserinfo user);
	
	//人员列表
	public void findUserinfoList(HttpServletRequest request, String selectName,
			String page, int pageSize);
	
	//新增用户
	public void saveUser(SysUserinfo userinfo);
	
	//依据id查询出单个用户实体类	
	public SysUserinfo findOneUserInfo(int id);
	
	//删除
	public void delUserInfo(String id);
	
	//通过人员id查询其角色
	public List<Object[]> findRolesByUserid(Integer userid);
	
	//查询人员可见的功能菜单list
	public List<SysFunction> findFunctionByUserid(Integer userid);
	
	//依据角色code查询人员
	public List<Object[]> findUserByRolecode(String code);
	
	//更改当前登录人密码
	public void updatePwd(Integer id, String pwd);
	
	//权限内可见的人员ids  (str)
	public String findSeeString(Integer userid);
	
	//权限内可见的人员ids (list)
	public List findSeeList(Integer userid);
	
	//编辑上传头像
	public void updateImg(Integer id, String imgpath);
	
	//验证登陆
	public String checkLogin(String username,String pwd,HttpServletRequest request);
	
	//检查session是否存在
	public String checkSeesionExists(HttpServletRequest request);

	//获取实体类by主键
	public SysUserinfo get(Integer id);
	
	public List<?> used(String userid,String url);

	public String findUserinfoJson(String selectName,String useroforg,String userofdept,int page,int rows);

	public List<Object[]> checkSameUser(String userid, String loginname);
	
	
	//获取当前用户的同级部门
	public List<Object[]> findSameLvDept(Integer userid);
	
	//获取当前用户的下级机构
	public List<Object[]> findChindOrg(Integer userid);

	public List<Object[]> findDeptByOrgid(Integer orgid);

	public String findDeptDataJson(String orgid);

	public String updateUserStatus(String statusflag, String userid);

	public String findUserRole(String userid);

	public List<Object[]> findChildLvDept(Integer id);

	public List<Object[]> findDeptByOrgcode(String code);

	public List<Object[]> findSameLvDeptNoSelf(Integer id, String deptcode);
	
	public Integer toDeptLv(String orgcode);
	
	public List<SysUserinfo> findUsersWithCondition(String orgcode,String deptcode,String rolecode); 
	
	public String findUserIdsWithCondition(String orgcode,String deptcode,String rolecode);
    
	//第3方机构list id + name
	public List<Object[]> findThirdorglist();
    //第3方机构json
	public String findThirdorgjson(); 
	//第3方机构id+角色下人员 list
	public List<SysUserinfo> findUsersWithCondition(String thirdorgid,String rolecode);
	//第3方机构id+角色下人员 json
	public String findUserIdsWithCondition(String thirdorgid,String rolecode);
	//第3方机构id查询名称
	public String findThirdOrgnameById(String thirdorgid);

	public String findUserIdsForHnlplb(String createUserid,
			String dictNameByCode, String rOLE_HNPG_LPLB);

	public String findSameDeptJsonByOrgid(String orgid);
	
	public String findUserIdsForSs(String orgcode,String deptcode);

	public String findUserIdsForThird(String thirdorgid);

	public String findUserIdsForAssessment(String orgcode, String deptcode);
	
	public  Integer toDeptLvById(String orgid);
	
	public String selectConn(String orgid);
}
