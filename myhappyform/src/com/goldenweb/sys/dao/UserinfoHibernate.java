package com.goldenweb.sys.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.goldenweb.sys.pojo.SysUserinfo;

public interface UserinfoHibernate {

	//保存并返回主键
	public int saveUserinfo(SysUserinfo userinfo);
	
	//检查登陆用户是否存在
	public List<Object[]> findLogin(String loginname,String pwd);
	
	//删除
	public void delUserInfo(String id);
	
	//通过人员id查询其角色
	public List<Object[]> findRolesByUserid(Integer userid);
	
	//查询人员可见的功能菜单list
	public List<Object[]> findFunctionByUserid(Integer userid);
	
	//依据角色code查询人员
	public List<Object[]> findUserByRolecode(String code);
	
	//更改当前登录人密码
	public void updatePwd(Integer id, String pwd);
	
	//依据人员id查询角色code & id
	public List<Object []> findRoleByUserid(Integer userid);
	
	//依据角色code查询人员id和角色id
	public List<Object []> findUserRoleByRolecode(String rolecode);
	
	//编辑上传头像
	public void updateImg(Integer id, String imgpath);

	public void setPageData(HttpServletRequest request, String sql,
			List<String> conditions, String page, int pageSize);

	public void update(SysUserinfo userinfo);

	public SysUserinfo getEntity(int id);

	public List<Object[]> findUserinfoList(String selectName,String useroforg,String userofdept,int page,int rows);
    
	
	public int findCountNumber(String selectName,String useroforg,String userofdept);

	public List<Object[]> checkSameUser(String userid, String loginname);

	public List<Object[]> findOrgCodeByUserid(Integer userid);

	public List<Object[]> findSameLvDept(Integer deptLv);

	public List<Object[]> findChindOrg(String connstr);

	public void updateUserStatus(String statusflag, String userid);

	public List<Object[]> findUserRole(String userid);

	public List<Object[]> findSameLvDeptNoSelf(Integer deptLv,
			String deptcodeself);

	public List<Object[]> findCuibanUser(String orgCode, String deptCode,
			String roleCode);

	public void delUserRoleLinkData(String id);

	public List<Object[]> findStartUser(String starter);

	public List<Object[]> findSameDeptUserList(String starter);

	public List<SysUserinfo> findUsersWithCondition(String orgid,
			String deptcode, String rolecode);

	public List<Object[]> findThirdorglist();
	
	public List<SysUserinfo> findUsersWithCondition(String thirdorgid,
			String rolecode);
	
	public List<Object[]> fingThirdOrgnameById(String thirdorgid);

	public List<SysUserinfo> findUserIdsForHnlplb(String orgcode,
			String deptcode, String rolecode);

	public List<SysUserinfo> findUserIdsForSs(String orgcode, String deptcode);

	public List<SysUserinfo> findUserIdsForThird(String thirdorgid);

	public List<Object[]> findUserIdsForHnlplb2(String orgcode,
			String deptcode, String rolecode);

	public List<SysUserinfo> findUserIdsForZr(String orgcode, String deptcode);
}
