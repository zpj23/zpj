package com.goldenweb.sys.action;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.goldenweb.sys.pojo.SysFunction;
import com.goldenweb.sys.pojo.SysRole;
import com.goldenweb.sys.service.FunctionService;
import com.goldenweb.sys.service.PurviewService;
import com.goldenweb.sys.service.RoleService;
import com.goldenweb.sys.util.IAction;

@Namespace("/")
@Scope("prototype")
@Component("purviewAction")
@ParentPackage("json-default")
public class PurviewAction extends IAction{
	@Autowired
	private PurviewService purviewService;
	@Autowired
	private FunctionService functionService;
	@Autowired
	private RoleService roleService;
	private String htmlValue;

	public String getHtmlValue() {
		return htmlValue;
	}
	public void setHtmlValue(String htmlValue) {
		this.htmlValue = htmlValue;
	}
	
	/*******************************************************************************************/

	@Action(value="purviewAction_functionPurview",results={
			@Result(name="success",location="sys/purview/functionPurview.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String functionPurview(){
		try {
			//加载角色
			List<SysRole> roleList = roleService.findAllRole();
			request.setAttribute("roleList", roleList);
			

			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}		
	}
	
	
	@Action(value="purviewAction_toAllRoleJson",results={
			@Result(name="success",type="json", params={"root","jsonData"})
	})
	public void toAllRoleJson() throws IOException{	
		String rolename =request.getParameter("rolename");
		jsonData = roleService.findAllRoleJson(rolename);
		this.jsonWrite(jsonData);
	}
	
	
	
	@Action(value="purviewAction_findMenuJsonByRoleid",results={
			@Result(name="success",type="json", params={"root","jsonData"})
	})
	public void findMenuJsonByRoleid() throws IOException{	
		String roleid =request.getParameter("roleid");
		String pid = request.getParameter("id");
		jsonData = purviewService.findMenuJson(roleid);
		this.jsonWrite(jsonData);
	}
	
	/**
	 * 保存角色功能菜单数据
	 * @return
	 */
	@Action(value="purviewAction_saveSetData",results={
			@Result(name="ajax",location="/ajax.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String saveSetData(){
		try {
			String roleid = request.getParameter("roleid");
			String menuid = request.getParameter("menuid");
			purviewService.saveSetData(roleid,menuid);	
			htmlValue="1";

			return "ajax";
		} catch (Exception e) {
			e.printStackTrace();
			htmlValue="0";
			return "ajax";
		}
	}
	
	/**
	 * 生成权限配置数据
	 * @return
	 */
	@Action(value="purviewAction_configData",results={
			@Result(name="ajax",location="/ajax.jsp")
	})
	public String configData(){
		try {
			purviewService.saveConfigData();			
			htmlValue="1";
			return "ajax";
		} catch (Exception e) {
			e.printStackTrace();
			htmlValue="0";
			return "ajax";
		}
	}
	
		
	/**
	 * 查询当前人某菜单下的按钮权限
	 * @return
	 */
	@Action(value="purviewAction_findButtonByMenuid",results={
			@Result(name="ajax",location="/ajax.jsp")
	})
	public String findButtonByMenuid(){
		try {
			Map<String, List<SysFunction>> map = (Map<String, List<SysFunction>>) request.getSession().getAttribute("buttonpurview");
			String menuid = request.getParameter("menuid");
			htmlValue = purviewService.findButtonByMenuid(map,menuid);	
			return "ajax";
		} catch (Exception e) {
			e.printStackTrace();
			htmlValue="0";
			return "ajax";
		}
	}
	
	/**
	 * 功能树页面    
	 *//*
	@Action(value="purviewAction_initPurview",results={
			@Result(name="success",location="sys/purview/purviewTree.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String initPurview(){
		try {
			//加载功能树
			request.setAttribute("functionjson", functionService.bulidFunctionTree());

			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}		
	}

	
    *//**
     * 查询操作人员by功能id
     * @return
     *//*
	@Action(value="purviewAction_findOperateByFunid",results={
			@Result(name="ajax",location="/ajax.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String findOperateByFunid(){
		try {
			String id = request.getParameter("id");
			//加载功能树
			htmlValue = purviewService.findOperateByFunid(id);

			return "ajax";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	
	*//**
	 * 点击操作时，加载该功能项的各个关联信息
	 * @return
	 *//*
	@Action(value="purviewAction_initOperateInfo",results={
			@Result(name="ajax",location="/ajax.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String initOperateInfo(){
		try {
			String funid = request.getParameter("funid");//功能id，单个

			//与角色的关系
			String roleids="";
			String rolenames="";
			List<Object[]> selectroles=purviewService.findSomeRole(funid);
			if(selectroles!=null&&selectroles.size()>0){				
				for(int i=0;i<selectroles.size();i++){
					roleids=roleids+selectroles.get(i)[0]+",";
					rolenames=rolenames+selectroles.get(i)[1]+",";
				}
				roleids=roleids.substring(0,roleids.length()-1);
				rolenames=rolenames.substring(0,rolenames.length()-1);
			}
			//与机构的关系
			List<Object[]> selectorgs=purviewService.findSomeOrg(funid);
			String orgids="";
			String orgnames="";
			if(selectorgs!=null&&selectorgs.size()>0){				
				for(int i=0;i<selectorgs.size();i++){
					orgids=orgids+selectorgs.get(i)[0]+",";
					orgnames=orgnames+selectorgs.get(i)[1]+",";
				}
				orgids=orgids.substring(0,orgids.length()-1);
				orgnames=orgnames.substring(0,orgnames.length()-1);
			}
			//与用户的关系
			String userids="";
			String usernames="";
			List<Object[]> selectusers=purviewService.findSomeUser(funid);
			if(selectusers!=null&&selectusers.size()>0){				
				for(int i=0;i<selectusers.size();i++){
					userids=userids+selectusers.get(i)[0]+",";
					usernames=usernames+selectusers.get(i)[1]+",";
				}
				userids=userids.substring(0,userids.length()-1);
				usernames=usernames.substring(0,usernames.length()-1);
			}

			htmlValue = roleids+"_"+rolenames+"_"+orgids+"_"+orgnames+"_"+userids+"_"+usernames;

			return "ajax";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}


	*//**
	 * 保存功能-机构关系
	 * @return
	 *//*
	@Action(value="purviewAction_saveOrgs",results={
			@Result(name="ajax",location="/ajax.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String saveOrgs(){
		try {
			String orgids = request.getParameter("orgids");
			String funids = request.getParameter("funids");
			//加载功能树
			purviewService.saveOrgs(orgids,funids);

			htmlValue = "1";
			return "ajax";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}


	*//**
	 * 保存功能-用户关系
	 * @return
	 *//*
	@Action(value="purviewAction_saveUsers",results={
			@Result(name="ajax",location="/ajax.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String saveUsers(){
		try {
			String userids = request.getParameter("userids");
			String funids = request.getParameter("funids");
			//加载功能树
			purviewService.saveUsers(userids,funids);

			htmlValue = "1";
			return "ajax";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}


	*//**
	 * 保存功能-角色关系
	 * @return
	 *//*
	@Action(value="purviewAction_saveRoles",results={
			@Result(name="ajax",location="/ajax.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String saveRoles(){
		try {
			String roleids = request.getParameter("roleids");
			String funids = request.getParameter("funids");
			//加载功能树
			purviewService.saveRoles(roleids,funids);

			htmlValue = "1";
			return "ajax";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}


	*//**
	 * 保存功能-(角色,机构,人员)3者的关系
	 * @return
	 *//*
	@Action(value="purviewAction_saveAll",results={
			@Result(name="ajax",location="/ajax.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String saveAll(){
		try {
			String roleids = request.getParameter("roleids");System.out.println(roleids);
			String orgids = request.getParameter("orgids");
			String userids = request.getParameter("userids");
			String funids = request.getParameter("funids");
			//加载功能树
			purviewService.saveAll(roleids,orgids,userids,funids);

			htmlValue = "1";
			return "ajax";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}*/

	
	
	@Action(value="purviewAction_quickmenu",results={
			@Result(name="success",location="sys/purview/quickmenu.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String quickmenu(){
		try {
			//加载角色
			List<SysRole> roleList = roleService.findAllRole();
			request.setAttribute("roleList", roleList);
			

			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}		
	}
	
	
	@Action(value="purviewAction_findMenuJsonByRoleid2",results={
			@Result(name="success",type="json", params={"root","jsonData"})
	})
	public void findMenuJsonByRoleid2() throws IOException{	
		String roleid =request.getParameter("roleid");
		String pid = request.getParameter("id");
		jsonData = purviewService.findRoleMenuJson(roleid);
		this.jsonWrite(jsonData);
	}
	
	
	@Action(value="purviewAction_saveQuickmenu",results={
			@Result(name="ajax",location="/ajax.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String saveQuickmenu(){
		try {
			String roleid = request.getParameter("roleid");
			String menuid = request.getParameter("menuid");
			String picpath = request.getParameter("picpath");
			purviewService.saveQuickmenu(roleid,menuid,picpath);	
			htmlValue="1";

			return "ajax";
		} catch (Exception e) {
			e.printStackTrace();
			htmlValue="0";
			return "ajax";
		}
	}


}
