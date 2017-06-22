package com.goldenweb.sys.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.goldenweb.fxpg.comm.BaseDao;
import com.goldenweb.fxpg.frame.tools.MD5;
import com.goldenweb.sys.dao.UserinfoHibernate;
import com.goldenweb.sys.pojo.SysUserinfo;
import com.goldenweb.sys.util.DESCipherCrossoverDelphi;

@Repository
public class UserinfoHibernateDao extends BaseDao<SysUserinfo> implements UserinfoHibernate{
	
/*********************************************************************************************/
	
	/**
	 * 保存并返回主键
	 */
	@Override
	public int saveUserinfo(SysUserinfo userinfo) {
		Session session =null;
		try {
			 session=sessionFactory.getCurrentSession();
			 Serializable id = session.save(userinfo);
			 return (Integer) id;
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return 0;
	}
	
	
	/**
	 * 检查登陆用户是否存在
	 * @param loginname
	 * @param pwd
	 * @return
	 */
	@Override
	public List<Object[]> findLogin(String loginname,String pwd){		
		try {
			pwd = MD5.md5s((loginname+"{"+pwd+"}"));//加密
						
			String sql ="select a.id,a.username,a.is_admin,a.main_Orgid,a.main_Orgname,a.loginname,a.img,a.deptcode,a.deptname,item_code, " +
			" a.phone,a.email,a.address,a.remark,a.is_Process,a.num,a.thirdorgid,a.thirdorgname "+
			" from Sys_Userinfo a left join Sys_Organization b  on a.main_Orgid=b.id where a.is_Del=0 and " +
			" a.loginname=? and  a.pwd = ?";
			return this.findBySql(sql, loginname,pwd);
			} catch (Exception e) {
				return null;
			}
	}
	
	
	/**
	 * 删除
	 * @param id
	 */
	@Override
	public void delUserInfo(String id){
		try {
			//String sql = "update sys_userinfo set is_del =1 where id in ("+id+")";
			String sql = "delete from sys_userinfo where id in ("+id+")";
			this.executeUpdateOrDelete(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 通过人员id查询其角色
	 * @param userid
	 * @return
	 */
	@Override
	public List<Object[]> findRolesByUserid(Integer userid) {
		try {
			String sql = " select a.roleid,b.rolename,b.rolecode from sys_userrole  a  " +
			" inner join sys_role  b on a.roleid=b.id where a.userid = ?";
			List<Object[]> list = this.findBySql(sql, userid);
			return list; 			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 查询人员可见的功能菜单list
	 * @param id
	 * @return
	 */
	@Override
	public List<Object[]> findFunctionByUserid(Integer userid) {
		try {
			String sql = " select distinct a.id,a.title,a.url,a.parent_funid,a.fun_order,a.picture,a.is_menu from sys_function a  left join sys_rolefunction b on a.id=b.functionid "+
			" left join sys_role c on c.id=b.roleid  left join sys_userrole d on d.roleid=c.id  where   d.userid="+userid+" order by fun_order";
			/*String sql = "  select  a.id,a.title,a.url,a.parent_funid,a.fun_order,a.picture from sys_function a inner join sys_functionuser b " +
			" on a.id=b.functionid and b.userid ="+userid+" union  " +
			" select  a.id,a.title,a.url,a.parent_funid,a.fun_order,a.picture from sys_function a  left join sys_functionorganization b  on a.id=b.functionid "+
			" left join sys_userinfo c on c.main_orgid=b.orgid where c.id = "+userid+
			" union  select  a.id,a.title,a.url,a.parent_funid,a.fun_order,a.picture from sys_function a  left join sys_rolefunction b on a.id=b.functionid "+
			" left join sys_role c on c.id=b.roleid  left join sys_userrole d on d.roleid=c.id  where d.userid="+userid+" order by fun_order";*/
			List<Object[]> list = this.findBySql2(sql);
			return list; 			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
		
	/**
	 * 依据角色code查询人员
	 * @param string
	 * @return
	 */
	@Override
	public List<Object[]> findUserByRolecode(String code) {
		try {
			String sql = " select a.id,a.username from sys_userinfo a  " +
			" left join sys_userrole b on a.id =b.userid "+
			" left join sys_role c on c.id=b.roleid "+
			" where c.rolecode=? ";
			List<Object[]> list = this.findBySql(sql, code);
			return list;					
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 更改当前登录人密码
	 * @param id
	 * @param pwd1
	 */
	@Override
	public void updatePwd(Integer id, String pwd) {
		pwd = new DESCipherCrossoverDelphi().getEncString(pwd);
		String sql = "update sys_userinfo set pwd = '"+pwd+"' where id ="+id;
		this.executeUpdateOrDelete(sql);
	}
	
	
	/**
	 * 依据人员id查询角色code & id
	 * @param userid
	 * @return
	 */
	@Override
	public List<Object []> findRoleByUserid(Integer userid){
		String sql = "select b.rolecode,b.id from sys_userrole  a  left join " +
		" sys_role b on a.roleid =b.id  where a.userid = "+userid;
		return this.findBySql2(sql);
	}
	
	
	/**
	 * 依据角色code查询人员id和角色id
	 * @param rolecode
	 * @return
	 */
	@Override
	public List<Object []> findUserRoleByRolecode(String rolecode){
		String sql = "select a.userid,b.id from sys_userrole  a  left join " +
		" sys_role b on a.roleid =b.id  where b.rolecode = ?";
		return this.findBySql(sql,rolecode);
	}
	
	
	/**
	 * 编辑上传头像
	 * @param id
	 * @param imgpath
	 */
	@Override
	public void updateImg(Integer id, String imgpath) {
		String sql = "update sys_userinfo set img = '"+imgpath+"' where id ="+id;
		this.executeUpdateOrDelete(sql);
	}
	@Override
	public SysUserinfo getEntity(int id) {		
		return get(id);
	}
	
	
	//分页
	@Override
	public List<Object[]> findUserinfoList(String selectName,String useroforg,String userofdept,int page,int rows) {
		String sql = " select id,username,loginname,email,phone,mainOrgname,deptname,isDel from SysUserinfo ";
		if(selectName!=null){
			sql+=" where (loginname like '%"+selectName+"%'  or username like '%"+selectName+"%' )  ";
		}
		if(useroforg!=null&&!"".equals(useroforg)){
			sql+=" and  main_orgid = "+useroforg;
		}
		if(userofdept!=null&&!"".equals(userofdept)){
			sql+=" and  deptcode = '"+userofdept+"' ";
		}
		sql+=" order by updateTime desc,num  ";
		return this.findListToPageByHql(sql, null, page, rows);
	}
	
	//记录总数
	@Override
	public int findCountNumber(String selectName,String useroforg,String userofdept) {
		String hql = " select count(id) from SysUserinfo  ";
		if(selectName!=null){
			hql+=" where (loginname like '%"+selectName+"%'  or username like '%"+selectName+"%' ) ";
		}
		if(useroforg!=null&&!"".equals(useroforg)){
			hql+=" and  mainOrgid = "+useroforg;
		}
		if(userofdept!=null&&!"".equals(userofdept)){
			hql+=" and  deptcode = '"+userofdept+"' ";
		}
		return this.findListCount(hql, null);
	}


	@Override
	public List<Object[]> checkSameUser(String userid, String loginname) {
		String hql = " from SysUserinfo where isDel=0 and loginname=? ";
		return findByHql(hql, loginname);
	}


	@Override
	public List<Object[]> findOrgCodeByUserid(Integer userid) {
		String sql = "select b.item_code,b.id from sys_userinfo a left join sys_organization b on a.main_orgid=b.id where a.id = ?";
		return findBySql(sql, userid);
	}


	@Override
	public List<Object[]> findSameLvDept(Integer deptLv) {
		String sql =" select t1.id,t1.item_name,t1.item_code from sys_resource_item  t1 inner join sys_resource_type t2 on t1.typeid =t2.id "+
		 "  where t2.type_code ='AA01'  and  item_code like 'AA%' and length(item_code)=? " ;
		return findBySql(sql, deptLv);
	}
	
	@Override
	public List<Object[]> findSameLvDeptNoSelf(Integer deptLv,String deptcodeself) {
		String sql =" select t1.id,t1.item_name,t1.item_code from sys_resource_item  t1 inner join sys_resource_type t2 on t1.typeid =t2.id "+
		 "  where t2.type_code ='AA01'  and  item_code like 'AA%' and length(item_code)=? and ? not like  '%'||t1.item_code||'%' " ;
		return findBySql(sql, deptLv,deptcodeself);
	}


	@Override
	public List<Object[]> findChindOrg(String connstr) {
		String sql = "select id,org_name,item_code from sys_organization  where  item_code  like ? and item_code <> ?";
		return findBySql(sql, connstr,connstr.replace("_", "0"));
	}


	@Override
	public void updateUserStatus(String statusflag, String userid) {
		String sql = "update sys_userinfo set is_del="+statusflag+" where id="+userid;
		executeUpdateOrDelete(sql);
	}


	@Override
	public List<Object[]> findUserRole(String userid) {
		String sql = "select a.rolecode,a.rolename from sys_userrole b left join sys_role a  "+
		" on a.id=b.roleid where b.userid = "+userid;
		return this.findBySql2(sql);
	}


	
	@Override
	public List<Object[]> findCuibanUser(String orgCode, String deptCode,
			String roleCode) {
		String sql = "  select a.id,a.username from sys_userinfo  a  inner join sys_userrole b  on a.id=b.userid  "+
			" inner join sys_organization c on c.id= a.main_orgid inner join sys_role d on d.id=b.roleid  "+
			" where c.item_code =?  and a.deptcode=?  and d.rolecode =? ";
		return this.findBySql(sql, orgCode,deptCode,roleCode);
	}


	
	@Override
	public void delUserRoleLinkData(String id) {
		String sql = "delete from sys_userrole where  userid in ("+id+")";
		this.executeUpdateOrDelete(sql);
	}


	
	@Override
	public List<Object[]> findStartUser(String starter) {
		String sql = "select id,username from sys_userinfo where id=?";
		return this.findBySql(sql, starter);
	}

	
	@Override
	public List<Object[]> findSameDeptUserList(String starter) {
		String sql ="select a.id,a.username from sys_userinfo a "+
        " where exists (select  main_orgid,deptcode from sys_userinfo where a.main_orgid=main_orgid and a.deptcode=deptcode and  id = ?) ";
		return this.findBySql(sql, starter);
	}


	@Override
	public List<SysUserinfo> findUsersWithCondition(String orgcode,
			String deptcode, String rolecode) {
		StringBuffer hql = new StringBuffer();
		hql.append(" select distinct a from SysUserinfo a ,SysUserrole b,SysOrganization c ,SysRole d ")
		.append(" where a.id =b.userid and b.roleid = d.id and a.mainOrgid=c.id ");
		if(orgcode!=null&&!"".equals(orgcode)){
			hql.append(" and c.orgCode = '").append(orgcode).append("' ");
		}
		if(deptcode!=null&&!"".equals(deptcode)){
			hql.append(" and a.deptcode = '").append(deptcode).append("' ");
		}
		if(rolecode!=null&&!"".equals(rolecode)){
			hql.append(" and d.rolecode = '").append(rolecode).append("' ");
		}
		return this.find(hql.toString(), null);
	}
	
	
	@Override
	public List<SysUserinfo> findUsersWithCondition(String thirdorgid,
			String rolecode) {
		StringBuffer hql = new StringBuffer();
		hql.append(" select distinct a from SysUserinfo a ,SysUserrole b ,SysRole d ")
		.append(" where a.id =b.userid and b.roleid = d.id  ");
		if(thirdorgid!=null&&!"".equals(thirdorgid)){
			hql.append(" and a.thirdorgid = '").append(thirdorgid).append("' ");
		}		
		if(rolecode!=null&&!"".equals(rolecode)){
			hql.append(" and d.rolecode = '").append(rolecode).append("' ");
		}
		return this.find(hql.toString(), null);
	}


	
	@Override
	public List<Object[]> findThirdorglist() {
		//String sql = "select stable_risk_thirdorg_guid,danweimingchen from STABLE_RISK_THIRDORG where status = '审核通过'";
		String sql = "select id,name from hn_fxpg_thirdorg  where status = '2'";
		return this.findBySql2(sql);
	}

	@Override
	public List<Object[]> fingThirdOrgnameById(String thirdorgid) {
		String sql = " select danweimingchen,stable_risk_thirdorg_guid  from   STABLE_RISK_THIRDORG  where stable_risk_thirdorg_guid= ?";
		return findBySql(sql,thirdorgid);
	}	
	
	
	public List<SysUserinfo> findUserIdsForHnlplbBak(String userid,
			String deptname, String rolecode) {
		SysUserinfo sui = get(Integer.parseInt(userid));
		
		StringBuffer hql = new StringBuffer();
		hql.append(" select distinct a from SysUserinfo a ,SysUserrole b,SysOrganization c ,SysRole d ")
		.append(" where a.id =b.userid and b.roleid = d.id and a.mainOrgid=c.id ");
		if(userid!=null&&!"".equals(userid)){
			hql.append(" and a.mainOrgid = '").append(sui.getMainOrgid()).append("' ");
		}
		if(deptname!=null&&!"".equals(deptname)){
			hql.append(" and a.deptname = '").append(deptname).append("' ");
		}
		if(rolecode!=null&&!"".equals(rolecode)){
			hql.append(" and d.rolecode = '").append(rolecode).append("' ");
		}
		return this.find(hql.toString(), null);
	}
	
	//部门或机构
	@Override
	public List<SysUserinfo> findUserIdsForHnlplb(String userid,
			String deptname, String rolecode) {
		SysUserinfo sui = get(Integer.parseInt(userid));
		
		StringBuffer hql = new StringBuffer();
		hql.append(" select distinct a from SysUserinfo a ,SysUserrole b,SysOrganization c ,SysRole d ")
		.append(" where a.id =b.userid and b.roleid = d.id and a.mainOrgid=c.id ");
		if(userid!=null&&!"".equals(userid)){
			hql.append(" and a.mainOrgid = '").append(sui.getMainOrgid()).append("' ");
		}
		if(deptname!=null&&!"".equals(deptname)){
			hql.append(" and a.deptname = '").append(deptname).append("' ");
		}
		if(rolecode!=null&&!"".equals(rolecode)){
			hql.append(" and d.rolecode = '").append(rolecode).append("' ");
		}
		hql.append("union all select distinct a from SysUserinfo a ,SysUserrole b,SysOrganization c ,SysRole d ")
		.append(" where a.id =b.userid and b.roleid = d.id and a.mainOrgid=c.id ");
		if(userid!=null&&!"".equals(userid)){
			hql.append(" and a.mainOrgid = '").append(sui.getMainOrgid()).append("' ");
		}
		if(deptname!=null&&!"".equals(deptname)){
			hql.append(" and c.orgName = '").append(deptname).append("' ");
		}
		if(rolecode!=null&&!"".equals(rolecode)){
			hql.append(" and d.rolecode = '").append(rolecode).append("' ");
		}
		
		return this.find(hql.toString(), null);
	}
	
	
	@Override
	public List<Object[]> findUserIdsForHnlplb2(String userid,
			String deptname, String rolecode) {
		SysUserinfo sui = get(Integer.parseInt(userid));
		
		StringBuffer hql = new StringBuffer();
		hql.append(" select distinct * from ( ");
		hql.append(" select  a.id,a.username from Sys_Userinfo a ,Sys_Userrole b,Sys_Organization c ,Sys_Role d ")
		.append(" where a.id =b.userid and b.roleid = d.id and a.main_Orgid=c.id ");
		if(userid!=null&&!"".equals(userid)){
			hql.append(" and a.main_Orgid = '").append(sui.getMainOrgid()).append("' ");
		}
		if(deptname!=null&&!"".equals(deptname)){
			hql.append(" and a.deptname = '").append(deptname).append("' ");
		}
		if(rolecode!=null&&!"".equals(rolecode)){
			hql.append(" and d.rolecode = '").append(rolecode).append("' ");
		}
		hql.append("union all select  a.id,a.username from Sys_Userinfo a ,Sys_Userrole b,Sys_Organization c ,Sys_Role d ")
		.append(" where a.id =b.userid and b.roleid = d.id and a.main_Orgid=c.id ");
		if(userid!=null&&!"".equals(userid)){
			hql.append(" and a.main_Orgid = '").append(sui.getMainOrgid()).append("' ");
		}
		if(deptname!=null&&!"".equals(deptname)){
			hql.append(" and c.org_Name||a.deptname = '").append(deptname).append("' ");
		}
		if(rolecode!=null&&!"".equals(rolecode)){
			hql.append(" and d.rolecode = '").append(rolecode).append("' ");
		}
		hql.append(" ) as t ");
		return this.findBySql2(hql.toString());
	}
	
	
	@Override
	public List<SysUserinfo> findUserIdsForSs(String orgcode,String deptcode) {		
		StringBuffer hql = new StringBuffer();
		hql.append(" select distinct a from SysUserinfo a ,SysOrganization c  ")
		.append(" where  a.mainOrgid=c.id ");
		if(orgcode!=null&&!"".equals(orgcode)){
			hql.append(" and c.orgCode = '").append(orgcode).append("' ");
		}
		if(deptcode!=null&&!"".equals(deptcode)){
			hql.append(" and a.deptcode = '").append(deptcode).append("' ");
		}
		
		return this.find(hql.toString(), null);
	}


	
	@Override
	public List<SysUserinfo> findUserIdsForThird(String thirdorgid) {
		StringBuffer hql = new StringBuffer();
		hql.append("  from SysUserinfo where  thirdorgid ='").append(thirdorgid).append("' ");		
		return this.find(hql.toString(), null);
	}


	
	@Override
	public List<SysUserinfo> findUserIdsForZr(String orgcode, String deptcode) {
		StringBuffer hql = new StringBuffer();
		hql.append(" select distinct a from SysUserinfo a ,SysOrganization c  ")
		.append(" where  a.mainOrgid=c.id ");
		if(orgcode!=null&&!"".equals(orgcode)){
			hql.append(" and c.orgCode = '").append(orgcode).append("' ");
		}
		if(deptcode!=null&&!"".equals(deptcode)){
			hql.append(" and a.deptcode = '").append(deptcode).append("' ");
		}else{
			hql.append(" and a.deptcode is null ");
		}
		return this.find(hql.toString(), null);
	}
	
}
