package com.goldenweb.sys.action;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.goldenweb.sys.pojo.Porlet;
import com.goldenweb.sys.pojo.SysRole;
import com.goldenweb.sys.service.OrganizationService;
import com.goldenweb.sys.service.PorletService;
import com.goldenweb.sys.service.PurviewService;
import com.goldenweb.sys.service.RoleService;
import com.goldenweb.sys.service.UserinfoService;
import com.goldenweb.sys.util.DateHelper;
import com.goldenweb.sys.util.IAction;

@Namespace("/")
@Scope("prototype")
@Component("roleAction")
@ParentPackage("json-default")
public class RoleAction extends IAction{
	@Autowired
	private RoleService roleService;
	@Autowired
	private PurviewService purviewService;	
	@Autowired
	private UserinfoService userinfoService;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private PorletService porletService;
	
	private SysRole role;
		
	public SysRole getRole() {
		return role;
	}
	public void setRole(SysRole role) {
		this.role = role;
	}
	
	private String htmlValue;
	public String getHtmlValue() {
		return htmlValue;
	}
	public void setHtmlValue(String htmlValue) {
		this.htmlValue = htmlValue;
	}
	/****************************************************************************************/

	/**
	 * 角色信息列表
	 */
	@Action(value="roleAction_toRoleList",results={
			@Result(name="success",location="sys/role/roleList.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String toRoleList(){		
		try {
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	
	@Action(value="roleAction_toRoleJson",results={
			@Result(name="success",type="json", params={"root","htmlValue"})
	})
	public void toRoleJson() throws IOException{	
		String rolename =request.getParameter("rolename");
		String pageIndex = request.getParameter("page");
		if (pageIndex == null || pageIndex.equals("")) {
			page = 1; // 设置为第1页
		}
		htmlValue = roleService.findRoleinfoJson(rolename,page,rows);
		response.setCharacterEncoding("utf-8");//指定为utf-8
		response.getWriter().write(htmlValue);//转化为JSOn格式
	}


	/**
	 * 跳转到新增修改页面
	 * @return
	 */
	@Action(value="roleAction_toAddRole",results={
			@Result(name="success",location="sys/role/toAddRole.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String toAddRole(){
		try {
			String id = request.getParameter("id");
			if(id!=null&&!"".equals(id)&&!"0".equals(id)){
				//修改时，将原来userinfo 带出
				role = roleService.findOneRoleInfo(Integer.parseInt(id));
				request.setAttribute("role", role);

				//角色-机构
				List<Object[]> orglist=roleService.findRoleOrg(id);
				if(orglist!=null&&orglist.size()>0){
					String orgids="";
					String orgnames="";
					for(int i=0;i<orglist.size();i++){
						orgids=orgids+orglist.get(i)[0]+",";
						orgnames=orgnames+orglist.get(i)[1]+",";
					}
					request.setAttribute("orgids",orgids.substring(0,orgids.length()-1) );
					request.setAttribute("orgnames",orgnames.substring(0,orgnames.length()-1) );
				}

				//角色-人员
				List<Object[]> userlist=roleService.findRoleUser(id);
				if(userlist!=null&&userlist.size()>0){
					String userids="";
					String usernames="";
					for(int i=0;i<userlist.size();i++){
						userids=userids+userlist.get(i)[0]+",";
						usernames=usernames+userlist.get(i)[1]+",";
					}
					request.setAttribute("userids", userids.substring(0,userids.length()-1));
					request.setAttribute("usernames", usernames.substring(0,usernames.length()-1));
				}
			}else{
				role = new SysRole();
				role.setRolecode("JS"+DateHelper.getDateString(new Date(),"yyyyMMddHHmmss"));
				request.setAttribute("role", role);
			}			
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}


	/**
	 * 新增数据     
	 * @return
	 */
	@Action(value="roleAction_doAddRole",results={
			@Result(name="success",location="/success.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String doAddRole(){		
		try {
			String orgids =request.getParameter("orgids");
			String userids =request.getParameter("userids");
			//保存角色，以及与机构,人员的关系
			roleService.saveRole(role,orgids,userids);	

			request.setAttribute("msg", "保存成功");
			request.setAttribute("whichPage", "self");
			request.setAttribute("nextURL","roleAction_toRoleList");
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}


	/**
	 * 查看数据
	 * @return
	 */
	@Action(value="roleAction_viewRole",results={
			@Result(name="success",location="sys/role/viewRole.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String viewRole(){
		try {
			String id = request.getParameter("id");
			role = roleService.findOneRoleInfo(Integer.parseInt(id)); 			
			request.setAttribute("role", role);	

			//角色-机构
			List<Object[]> orglist=roleService.findRoleOrg(id);
			if(orglist!=null&&orglist.size()>0){
				String orgnames="";
				for(int i=0;i<orglist.size();i++){
					orgnames=orgnames+orglist.get(i)[1]+",";
				}
				request.setAttribute("orgnames",orgnames.substring(0,orgnames.length()-1) );
			}

			//角色-人员
			List<Object[]> userlist=roleService.findRoleUser(id);
			if(userlist!=null&&userlist.size()>0){
				String usernames="";
				for(int i=0;i<userlist.size();i++){
					usernames=usernames+userlist.get(i)[1]+",";
				}
				request.setAttribute("usernames", usernames.substring(0,usernames.length()-1));
			}

			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

	}

	
	/**
	 * 删除数据
	 * @return
	 */
	@Action(value="roleAction_delRole",results={
			@Result(name="success",location="/success.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String delRole(){		
		try {
			String id = request.getParameter("id");
			//该角色下是否还存在人员
			//List<Object[]> list = roleService.findUsersByRoleid(id);			
			roleService.delRole(id);

			request.setAttribute("msg", "删除成功");
			request.setAttribute("whichPage", "self");
			request.setAttribute("nextURL","roleAction_toRoleList");
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}		
	}



    /**
     * 选择角色
     * @return
     */
	@Action(value="roleAction_chooseRoles",results={
			@Result(name="success",location="sys/role/chooseRoles.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String chooseRoles(){		
		try {
			String funid = request.getParameter("funid");
			if(funid!=null&&!"".equals(funid)){
				//将已选择的机构显示(权限-机构)
				List<Object[]> selectroles=purviewService.findSomeRole(funid);
				request.setAttribute("selectroles", selectroles);

				if(selectroles!=null&&selectroles.size()>0){
					String roleids="";
					String rolenames="";
					for(int i=0;i<selectroles.size();i++){
						roleids=roleids+selectroles.get(i)[0]+",";
						rolenames=rolenames+selectroles.get(i)[1]+",";
					}
					request.setAttribute("roleids",roleids );
					request.setAttribute("rolenames",rolenames );
				}
			}
			List<SysRole> roleList = roleService.findAllRole();
			request.setAttribute("roleList", roleList);

			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}


	/**
	 * 选择流程角色(单/多选)
	 * @return
	 */
	@Action(value="roleAction_chooseNodeRoles",results={
			@Result(name="success",location="sys/role/chooseNodeRoles.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String chooseNodeRoles(){		
		try {			
			List<Object[]> roleList = roleService.findProcessRoles();
			request.setAttribute("roleList", roleList);

			String tagid = request.getParameter("tagid");
			String tagname = request.getParameter("tagname");

			String roleids = request.getParameter("roleids");						
			String rolenames = request.getParameter("rolenames");

			if(!"".equals(roleids)){
				request.setAttribute("roleids", roleids+",");
			}
			if(!"".equals(rolenames)){
				request.setAttribute("rolenames", rolenames+",");
			}
			String isone = request.getParameter("isone");// one-单选，many-多选
			request.setAttribute("isone", isone);

			request.setAttribute("tagid",tagid);
			request.setAttribute("tagname",tagname);
			request.setAttribute("isopen",request.getParameter("isopen"));

			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}


	/**
	 * 检查角色下是否有人员
	 * @return 0-没有
	 */
	@Action(value="roleAction_checkRoleUser",results={
			@Result(name="ajax",location="/ajax.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String checkRoleUser(){
		try {
			String rolecode = request.getParameter("rolecode");
			List<Object[]> list= roleService.findRoleUserByCode(rolecode);
			if(list!=null&&list.size()>0){
				htmlValue="1";
			}else{
				htmlValue="0";
			}			 
			return "ajax";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}


	/**
	 * 检查角色code唯一
	 * @return 1-有重复
	 */
	@Action(value="roleAction_checkSamerolecode",results={
			@Result(name="ajax",location="/ajax.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String checkSamerolecode(){
		try {
			String roleid = request.getParameter("roleid");
			String rolecode = request.getParameter("rolecode");

			List<Object[]> list= roleService.checkRoleCode(roleid,rolecode);
			if(list!=null&&list.size()>0){
				htmlValue="1";
			}else{
				htmlValue="0";
			}

			return "ajax";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}


	/**
	 * 获取角色下的人员
	 * @return json
	 */
	@Action(value="roleAction_findUsersOfRole",results={
			@Result(name="ajax",location="/ajax.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String findUsersOfRole(){
		try {
			String rolecode = request.getParameter("rolecode");
			htmlValue = roleService.findUsersOfRole(rolecode);					 
			return "ajax";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	
	/**
	 * 选择角色
	 */
	 @Action(value="roleAction_toSetRoles",results={
				@Result(name="success",location="sys/role/toSetRoles.jsp"),
				@Result(name="error",location="/error.jsp")
		})
		public String toSetRoles(){		
			try {
				String userid = request.getParameter("userid");
				//该人员已有角色
				List<Object[]> list = userinfoService.findRolesByUserid(Integer.parseInt(userid));
				String roleid=",";
				if(list!=null&&list.size()>0){
					for(int i=0;i<list.size();i++){
						roleid+=list.get(i)[0]+",";
					}
				}
				request.setAttribute("roleid", roleid);
				//所有角色
				List<SysRole> roleList = roleService.findAllRole();
				request.setAttribute("roleList", roleList);
				request.setAttribute("userid", userid);

				return "success";
			} catch (Exception e) {
				e.printStackTrace();
				return "error";
			}
		}
	 
	    /**
		 * 保存人员角色关系
		 */
		 @Action(value="roleAction_doSetRoles",results={
					@Result(name="success",location="/ajax"),
					@Result(name="error",location="/error.jsp")
			})
			public String doSetRoles(){		
				try {
					String userid = request.getParameter("userid");
					String roleids = request.getParameter("roleids");
					roleService.saveSetRoles(userid,roleids);					

					return "success";
				} catch (Exception e) {
					e.printStackTrace();
					return "error";
				}
			}
		 
		 
		 /**
		  * 角色列表  查看关联用户
		  * @throws IOException
		  */
		 @Action(value="roleAction_findUserByRoleid",results={
					@Result(name="success",type="json", params={"root","jsonData"})
			})
			public void findUserByRoleid() throws IOException{	
				String roleid = request.getParameter("roleid");
				jsonData = roleService.findUserJsonByRoleid(roleid);
				this.jsonWrite(jsonData);
			}
		 
		 /**
		  * 角色列表  查看关联机构
		  * @throws IOException
		  */
		 @Action(value="roleAction_findOrgByRoleid",results={
					@Result(name="success",type="json", params={"root","jsonData"})
			})
			public void findOrgByRoleid() throws IOException{	
				String roleid = request.getParameter("roleid");
				jsonData = roleService.findOrgJsonByRoleid(roleid);
				this.jsonWrite(jsonData);
			}
		 		 
			
		/**
		 * 角色列表  设置关联用户
		 * @return
		 */
		@Action(value="roleAction_toSetUser",results={
				@Result(name="success",location="sys/role/toSetUser.jsp"),
				@Result(name="error",location="/error.jsp")
		})
		public String toSetUser(){
			try {
				//机构树
				request.setAttribute("orgjson", organizationService.findDeptJson("first"));
				//所有人员
				List<Object[]> userList = organizationService.findUsers();
				request.setAttribute("userList",userList);
				
				String roleid = request.getParameter("roleid");
				List<Object[]> selectlist = roleService.findUserByRoleid(roleid);
				String userid="",username="";
				if(selectlist!=null&&selectlist.size()>0){					
					for(int i=0;i<selectlist.size();i++){
						userid+=selectlist.get(i)[0]+",";
						username+=selectlist.get(i)[1]+",";
					}
				}
				request.setAttribute("userids",userid);
				request.setAttribute("usernames",username);
				request.setAttribute("roleid", roleid);
				
				return "success";
			} catch (Exception e) {
				e.printStackTrace();
				return "error";
			}
		}
		
		/**
		 * 保存设置用户		
		 * @return
		 */
		@Action(value="roleAction_saveSetUser",results={
				@Result(name="success",location="/ajax.jsp"),
				@Result(name="error",location="/error.jsp")
		})
		public String saveSetUser(){
			try {
				String roleid = request.getParameter("roleid");
				String userids = request.getParameter("userids");
				roleService.saveSetUser(roleid,userids);
				
				return "success";
			} catch (Exception e) {
				e.printStackTrace();
				return "error";
			}
		}
		
		
		/**
		 * 角色列表  设置关联机构
		 * @return
		 */
		@Action(value="roleAction_toSetOrg",results={
				@Result(name="success",location="sys/role/toSetOrg.jsp"),
				@Result(name="error",location="/error.jsp")
		})
		public String toSetOrg(){
			try {						
				request.setAttribute("orgjson", organizationService.findDeptJson("first"));
				String roleid = request.getParameter("roleid");
				List<Object[]> selectlist = roleService.findOrgByRoleid(roleid);
				String selectid="",selectname="";
				if(selectlist!=null&&selectlist.size()>0){					
					for(int i=0;i<selectlist.size();i++){
						selectid+=selectlist.get(i)[0]+",";
						selectname+=selectlist.get(i)[1]+",";
					}
				}
				request.setAttribute("selectid",selectid);
				request.setAttribute("selectname",selectname);
					
				request.setAttribute("roleid", roleid);
				return "success";
			} catch (Exception e) {
				e.printStackTrace();
				return "error";
			}
		}
		
		
		/**
		 * 保存设置机构
		 * @return
		 */
		@Action(value="roleAction_saveSetOrg",results={
				@Result(name="success",location="/ajax.jsp"),
				@Result(name="error",location="/error.jsp")
		})
		public String saveSetOrg(){
			try {
				String roleid = request.getParameter("roleid");
				String orgids = request.getParameter("orgids");
				roleService.saveSetOrg(roleid,orgids);
				
				return "success";
			} catch (Exception e) {
				e.printStackTrace();
				return "error";
			}
		}
		
		
		
		@Action(value="roleAction_setIndex",results={
				@Result(name="success",location="sys/role/toSetIndex.jsp"),
				@Result(name="error",location="/error.jsp")
		})
		public String setIndex(){
			try {						
				String roleid = request.getParameter("roleid");
				request.setAttribute("roleid", roleid);
				
				//所有的首页模块list
				List<Porlet> porletsall = porletService.getAll();
				request.setAttribute("porletall", porletsall);
				
				//角色下的模块
				String myporlet = porletService.findProletStrByRoleid(roleid);
				request.setAttribute("myporlet", myporlet);
				
				return "success";
			} catch (Exception e) {
				e.printStackTrace();
				return "error";
			}			
		}
		
		@Action(value="roleAction_doSetPorlet",results={
				@Result(name="success",location="/ajax.jsp"),
				@Result(name="error",location="/error.jsp")
		})
		public String doSetPorlet(){
			try {
				String roleid = request.getParameter("roleid");
				String porlets = request.getParameter("porlets");				
				porletService.saveSetPorlet(roleid,porlets);
				
				return "success";
			} catch (Exception e) {
				e.printStackTrace();
				return "error";
			}
		}

}
