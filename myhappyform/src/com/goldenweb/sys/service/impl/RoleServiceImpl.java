package com.goldenweb.sys.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.goldenweb.sys.dao.RoleHibernate;
import com.goldenweb.sys.dao.RoleOrgHibernate;
import com.goldenweb.sys.dao.UserRoleHibernate;
import com.goldenweb.sys.pojo.SysRole;
import com.goldenweb.sys.pojo.SysRoleorganization;
import com.goldenweb.sys.pojo.SysUserrole;
import com.goldenweb.sys.service.RoleService;
import com.goldenweb.fxpg.comm.BaseService.MethodLog2;

@Service
@Component("roleService")
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleHibernate roleDao;
	@Autowired
	private RoleOrgHibernate roleOrgDao;
	@Autowired
	private UserRoleHibernate userRoleDao;
	
	/****************************************************************************************/

	/**
	 * 角色列表，分页
	 * @param request
	 * @param selectName
	 * @param page
	 * @param pageSize
	 */
	@Override
	public void findRoleList(HttpServletRequest request, String selectName,
			String page, int pageSize) {
		StringBuffer hql = new StringBuffer();
		hql.append("select sr.id,sr.rolename,sr.rolecode,sr.remark "
				+ "  from SysRole sr where sr.id >0 ");
		if (null != selectName && !"".equals(selectName.trim())) {
			hql.append(" and sr.rolename like '%" + selectName.trim() + "%') ");
		}			
		// 传递分页数据
		roleDao.setPageData(request, hql.toString(), null, page, pageSize);
	}


	/**
	 * 依据主键查询单个角色信息
	 * @param parseInt
	 * @return
	 */
	@Override
	public SysRole findOneRoleInfo(int id) {
		try {
			return roleDao.getEntity(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}


	/**
	 * 保存修改角色
	 * @param role
	 * @param userids 
	 * @param orgids 
	 */
	@Override
	@MethodLog2(remark="编辑角色信息",type="编辑")
	public void saveRole(SysRole role, String orgids, String userids) {
		try {
			
				//删除
				int isExist = roleDao.isExistsGroup(role.getRolecode());
			    
			
			
			//角色
			int roleid = roleDao.saveOrUpdateRole(role);

			//角色-机构			
			SysRoleorganization roleorg;
			if(!"".equals(orgids)){
				roleDao.delRoleOrg(roleid);

				String orgid [] = orgids.split(",");
				for(int i=0;i<orgid.length;i++){
					roleorg = new SysRoleorganization();
					roleorg.setRoleid(roleid);
					roleorg.setOrgid(Integer.parseInt(orgid[i]));
					roleOrgDao.save(roleorg);
				}
			}

			//角色-人员
			SysUserrole userrole;
			if(!"".equals(userids)){
				roleDao.delRoleUser(roleid);

				String userid [] = userids.split(",");
				for(int i=0;i<userid.length;i++){
					userrole = new SysUserrole();
					userrole.setRoleid(roleid);
					userrole.setUserid(Integer.parseInt(userid[i]));
					userRoleDao.save(userrole);
					
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}


	/**
	 * 删除角色
	 * @param id
	 */
	@Override
	@MethodLog2(remark="删除角色信息",type="删除")
	public void delRole(String id) {
		SysRole role;
		try {
			if(id.indexOf(",")>-1){
				//多个
				String idarr[] = id.split(",");
				for(int i=0;i<idarr.length;i++){
					role = roleDao.getEntity(Integer.parseInt(idarr[i]));
									    
				}
			}else{
				//单个
				role = roleDao.getEntity(Integer.parseInt(id));	
				
			}
		    roleDao.delRole(id);
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}


	/**
	 * 查询该角色关联的机构
	 * @param id
	 * @return
	 */
	@Override
	public List<Object[]> findRoleOrg(String id) {		
		List<Object[]> list = roleDao.findRoleOrg(id);
		return list;
	}


	/**
	 * 查询该角色关联的人员
	 * @param id
	 * @return
	 */
	@Override
	public List<Object[]> findRoleUser(String id) {		
		List<Object[]> list = roleDao.findRoleUser(id);
		return list;
	}


	@Override
	public List<SysRole> findAllRole() {		
		try {
			return roleDao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * 依据code查询该角色下的人员
	 * @param id
	 * @return
	 */
	@Override
	public List<Object[]> findRoleUserByCode(String code) {		
		List<Object[]> list = roleDao.findRoleUserByCode(code);
		return list;
	}


	/**
	 * 检查角色代码是否唯一
	 * @param roleid
	 * @param rolecode
	 * @return
	 */
	@Override
	public List<Object[]> checkRoleCode(String roleid, String rolecode) {
		List<Object[]> list=roleDao.checkRoleCode(roleid, rolecode);
		return list;
	}


	/**
	 * 获取角色下的人员
	 * @param rolecode
	 * @return json  {userid:xx,username:xx}
	 */
	@Override
	public String findUsersOfRole(String rolecode) {
		try {
			List<Object[]> list = roleDao.findUsersOfRole(rolecode);
			StringBuffer str =new StringBuffer("[");			
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					if(i==(list.size()-1)){
						str.append("{userid:\"").append(list.get(i)[0]).append("\",username:\"")
						.append(list.get(i)[1]).append("\"}");
					}else{
						str.append("{userid:\"").append(list.get(i)[0]).append("\",username:\"")
						.append(list.get(i)[1]).append("\"},");
					}				
				}			
			}
			str.append("]");
			return str.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * 查询所有的流程角色
	 * @return
	 */
	@Override
	public List<Object[]> findProcessRoles() {
		return roleDao.findProcessRoles();
	}


	/**
	 * 角色列表的json数据
	 */
	@Override
	public String findRoleinfoJson(String rolename, Integer page, Integer rows) {
		List<Object[]> list = roleDao.findRolePageList(rolename,page,rows);
		int countNumber  = roleDao.findCountNumber(rolename);
		if(list!=null&&list.size()>0){
		  StringBuffer str =new StringBuffer();
		  str.append("{\"total\":\"").append(countNumber).append("\",\"rows\":[");
		  for(int i=0;i<list.size();i++){
			  str.append("{");
			  str.append("\"id\":\"").append(list.get(i)[0]).append("\",");
			  str.append("\"roleid\":\"").append(list.get(i)[0]).append("\",");
			  str.append("\"rolename\":\"").append(list.get(i)[1]).append("\",");
			  str.append("\"rolecode\":\"").append(list.get(i)[2]).append("\",");
			  str.append("\"remark\":\"").append(list.get(i)[3]==null?"":list.get(i)[3]).append("\"");			 
			  str.append("},");			  
		  }
		  str.delete(str.toString().length()-1, str.toString().length());
		  str.append("]}");
		  return str.toString();
		}
		return "[]";
	}


	@Override
	@MethodLog2(remark="编辑角色人员关系",type="编辑")
	public void saveSetRoles(String userid, String roleids) {
		//删除原关系
		userRoleDao.delLinkByUserid(userid);		
		//新增关系
		String roleid [] =roleids.split(",");
		int uid =  Integer.parseInt(userid);
		SysUserrole userrole;
		for(int i=0;i<roleid.length;i++){
			if(!"".equals(roleid[i])){
				userrole = new SysUserrole();
				userrole.setUserid(uid);
				userrole.setRoleid(Integer.parseInt(roleid[i].toString()));
				userRoleDao.save(userrole);
			}
		}
		
	}

	//查询角色id下的用户json数据
	@Override
	public String findUserJsonByRoleid(String roleid) {
		List<Object[]> list = findUserByRoleid(roleid);
		if(list!=null&&list.size()>0){
			StringBuffer str = new StringBuffer("");
			str.append("{\"total\":\"").append(10).append("\",\"rows\":[");
			for(int i=0;i<list.size();i++){
				str.append("{\"userid\":\"").append(list.get(i)[0]).append("\", \"username\":\"")
				.append(list.get(i)[1]==null?"":list.get(i)[1]).append("\" },");	
			}
			 str.delete(str.toString().length()-1, str.toString().length());
			 str.append("]}");
			return str.toString();
		}else{
			return "[]";
		}
	}
	
	//查询角色id下的机构json数据
	@Override
	public String findOrgJsonByRoleid(String roleid) {
		List<Object[]> list = findOrgByRoleid(roleid);
		if(list!=null&&list.size()>0){
			StringBuffer str = new StringBuffer("");
			str.append("{\"total\":\"").append(10).append("\",\"rows\":[");
			for(int i=0;i<list.size();i++){
				str.append("{\"orgid\":\"").append(list.get(i)[0]).append("\", \"orgname\":\"")
				.append(list.get(i)[1]==null?"":list.get(i)[1]).append("\" },");	
			}
			 str.delete(str.toString().length()-1, str.toString().length());
			 str.append("]}");
			return str.toString();
		}else{
			return "[]";
		}
	}

	//查询角色id下的用户list
	@Override
	public List<Object[]> findUserByRoleid(String roleid) {
		return userRoleDao.findUserByRoleid(roleid);
	}
	
	//查询角色id下的机构list
	@Override
	public List<Object[]> findOrgByRoleid(String roleid) {
		return roleOrgDao.findOrgByRoleid(roleid);
	}


	@Override
	@MethodLog2(remark="编辑角色用户关系",type="编辑")
	public void saveSetUser(String roleid, String userids) {
		//删除原有关联
		userRoleDao.delLinkByRoleid(roleid);
		
		//保存最新关联数据
		String userid [] = userids.split(",");
		SysUserrole ur =null;
		for(int i=0;i<userid.length;i++){
			if(!"".equals(userid[i])){
			ur = new SysUserrole();
			ur.setRoleid(Integer.parseInt(roleid));
			ur.setUserid(Integer.parseInt(userid[i]));
			userRoleDao.save(ur);
			}
		}		
	}

	@Override
	@MethodLog2(remark="编辑角色机构关系",type="编辑")
	public void saveSetOrg(String roleid, String orgids) {
		//删除原有关联
		roleOrgDao.delLinkByRoleid(roleid);
		
		//保存最新关联数据
		String orgid [] = orgids.split(",");
		SysRoleorganization ro = null;
		for(int i=0;i<orgid.length;i++){
			if(!"".equals(orgid[i])){
				ro = new SysRoleorganization();
				ro.setRoleid(Integer.parseInt(roleid));
				ro.setOrgid(Integer.parseInt(orgid[i]));
				roleOrgDao.save(ro);
			}
		}
		
	}


	
	@Override
	public String findAllRoleJson(String rolename) {
		List<SysRole> list = roleDao.findRoleByName(rolename);
		if(list!=null&&list.size()>0){
		  StringBuffer str =new StringBuffer();
		  str.append("{\"total\":\"").append(list.size()).append("\",\"rows\":[");
		  for(int i=0;i<list.size();i++){
			  str.append("{");
			  str.append("\"id\":\"").append(list.get(i).getId()).append("\",");
			  str.append("\"rolename\":\"").append(list.get(i).getRolename()).append("\",");
			  str.append("\"rolecode\":\"").append(list.get(i).getRolecode()).append("\" ");			 
			  str.append("},");			  
		  }
		  str.delete(str.toString().length()-1, str.toString().length());
		  str.append("]}");
		  return str.toString();
		}
		return "[]";
	}
}
