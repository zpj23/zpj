package com.goldenweb.sys.action;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import com.goldenweb.sys.pojo.SysOrganization;
import com.goldenweb.sys.service.OrganizationService;
import com.goldenweb.sys.service.PurviewService;
import com.goldenweb.sys.service.RoleService;
import com.goldenweb.sys.service.UserinfoService;
import com.goldenweb.sys.util.IAction;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @ClassName: OrganizationAction
 * @author Lee 
 * @date 2014-2-22 上午09:17:20
 *
 */
@Namespace("/")
@Scope("prototype")
@Component("orgAction")
@ParentPackage("json-default")
@Controller
public class OrganizationAction extends IAction{	
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PurviewService purviewService;
	@Autowired
	private UserinfoService userinfoService;
		
	private String htmlValue;	
	public String getHtmlValue() {
		return htmlValue;
	}
	public void setHtmlValue(String htmlValue) {
		this.htmlValue = htmlValue;
	}
	public SysOrganization orginfo;
		 
	public SysOrganization getOrginfo() {
		return orginfo;
	}
	public void setOrginfo(SysOrganization orginfo) {
		this.orginfo = orginfo;
	}
	/**
	 * @throws IOException *******************************************************************************************/
 
	
	@Action(value="orgAction_showOrgJson",results={
			@Result(name="success",location="sys/organization/orgTree.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public void showOrgJson() throws IOException {
		String id = request.getParameter("id");
		if("".equals(id) || id==null){
//			id = String.valueOf(this.getCurrentUser().getMainOrgid());
		}
		htmlValue = organizationService.showOrgJson(id);		
		this.jsonWrite(htmlValue);		
	}
	
	/**
	 * 初始化组织机构页面
	 */	
	@Action(value="orgAction_init",results={
			@Result(name="success",location="sys/organization/orgTree.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String buildOrgTree(){
		try {
			//first 为1级部门数据
			// request.setAttribute("orgjson", organizationService.findDeptJson("first"));
//			request.setAttribute("orgjson", organizationService.findDeptJson(String.valueOf(this.getCurrentUser().getMainOrgid())));
			request.setAttribute("tagnodeid", request.getParameter("openid"));//要展开的节点id
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}


	/**
	 * 选择组织机构(单选)
	 * @return
	 */
	@Action(value="orgAction_chooseOrg",results={
			@Result(name="success",location="sys/organization/chooseOrg.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String chooseOrg(){
		try {
			String superOrgId = request.getParameter("superOrgId");
			if(null == superOrgId || "".equals(superOrgId)){
				superOrgId = "first";
			}
			String orgjson = organizationService.findDeptJson(superOrgId);		
			request.setAttribute("orgjson", orgjson);
			String orgid = request.getParameter("selectid");
			if(!"".equals(orgid)){
				orgid+=",";
			}
			request.setAttribute("selectid",orgid );
			String orgname = URLDecoder.decode(request.getParameter("selectname"),"UTF-8");
			if(!"".equals(orgname)){
				orgname+=",";
			}
			request.setAttribute("selectname", orgname);
			
			request.setAttribute("tagid", request.getParameter("tagid"));
			request.setAttribute("tagname", request.getParameter("tagname"));
			request.setAttribute("isone", request.getParameter("isone"));			
			//parentDeptname=new String(parentDeptname.getBytes("ISO-8859-1"),"UTF-8");			
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}


	/**
	 * 选择组织机构(多选)
	 * @return
	 */
	@Action(value="orgAction_chooseOrgs",results={
			@Result(name="success",location="sys/organization/chooseOrgs.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String chooseOrgs(){
		try {
			String roleid = request.getParameter("roleid");
			if(roleid!=null&&!"".equals(roleid)){
				//将已选择的机构显示(角色-机构)
				List<Object[]> selectorgs=roleService.findRoleOrg(roleid);
				request.setAttribute("selectorgs", selectorgs);

				if(selectorgs!=null&&selectorgs.size()>0){
					String orgids="";
					String orgnames="";
					for(int i=0;i<selectorgs.size();i++){
						orgids=orgids+selectorgs.get(i)[0]+",";
						orgnames=orgnames+selectorgs.get(i)[1]+",";
					}
					request.setAttribute("orgids",orgids );
					request.setAttribute("orgnames",orgnames );
				}
			}
			String funid = request.getParameter("funid");
			if(funid!=null&&!"".equals(funid)){
				//将已选择的机构显示(权限-机构)
				List<Object[]> selectorgs=purviewService.findSomeOrg(funid);
				request.setAttribute("selectorgs", selectorgs);

				if(selectorgs!=null&&selectorgs.size()>0){
					String orgids="";
					String orgnames="";
					for(int i=0;i<selectorgs.size();i++){
						orgids=orgids+selectorgs.get(i)[0]+",";
						orgnames=orgnames+selectorgs.get(i)[1]+",";
					}
					request.setAttribute("orgids",orgids );
					request.setAttribute("orgnames",orgnames );
				}
			}

			request.setAttribute("orgjson", organizationService.findDeptsJson());

			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}


	/**
	 * 选择组织机构下用户
	 * @return
	 */
	@Action(value="orgAction_chooseOrgUsers",results={
			@Result(name="success",location="sys/organization/chooseUser.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String chooseOrgUsers(){
		try {
			//机构树  1947308
			request.setAttribute("orgjson", organizationService.findDeptJson("1947308")); //first
			//所有人员
			List<Object[]> userList = organizationService.findUsers();
			request.setAttribute("userList",userList);

			String tagid = request.getParameter("tagid");
			String tagname = request.getParameter("tagname");

			String userids = request.getParameter("selectid");						
			String usernames = request.getParameter("selectname");

			if(!"".equals(userids)){
				request.setAttribute("userids", userids+",");
			}
			if(!"".equals(usernames)){
				request.setAttribute("usernames", usernames+",");
			}
			String isone = request.getParameter("isone");// one-单选，many-多选
			request.setAttribute("isone", isone);

			request.setAttribute("tagid",tagid);
			request.setAttribute("tagname",tagname);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}


	/**
	 * 选择流程节点用户
	 * @return
	 */
	@Action(value="orgAction_chooseNodeUsers",results={
			@Result(name="success",location="sys/organization/chooseUser.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String chooseNodeUsers(){
		try {
			//机构树
			request.setAttribute("orgjson", organizationService.findDeptJson("first"));
			//所有人员
			List<Object[]> userList = organizationService.findProcessUsers();
			request.setAttribute("userList",userList);

			String tagid = request.getParameter("tagid");
			String tagname = request.getParameter("tagname");

			String userids = request.getParameter("userids");						
			String usernames = request.getParameter("usernames");

			if(!"".equals(userids)){
				request.setAttribute("userids", userids+",");
			}
			if(!"".equals(usernames)){
				request.setAttribute("usernames", usernames+",");
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
     * 获取单个部门信息
     * @return
     */
	@Action(value="orgAction_getOneDeptInfo",results={
			@Result(name="ajax",location="/ajax.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String getOneDeptInfo(){
		try {
			String id = request.getParameter("id");
			htmlValue = organizationService.showUserOfOrg(Integer.parseInt(id));

			return "ajax";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}


	/**
	 * 选择组织机构(多选)
	 * @return
	 */
	@Action(value="orgAction_chooseNodeOrgs",results={
			@Result(name="success",location="sys/organization/chooseNodeOrgs.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String chooseNodeOrgs(){
		try {
			request.setAttribute("orgjson", organizationService.findDeptsJson());

			String tagid = request.getParameter("tagid");
			String tagname = request.getParameter("tagname");

			String orgids = request.getParameter("orgids");						
			String orgnames = request.getParameter("orgnames");

			if(!"".equals(orgids)){
				request.setAttribute("orgids", orgids+",");
			}
			if(!"".equals(orgnames)){
				request.setAttribute("orgnames", orgnames+",");
			}
			String isone = request.getParameter("isone");// one-单选，many-多选
			request.setAttribute("isone", isone);

			request.setAttribute("tagid",tagid);
			request.setAttribute("tagname",tagname);

			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	/**
	 * 新增数据( sys_resource_item )   
	 * @return
	 */
	@Action(value="orgAction_doAddOrg",results={
			@Result(name="ajax",location="/ajax.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String doAddOrg(){
		try {
			SysOrganization org = new SysOrganization();
			String id = request.getParameter("id");
			String orgName = request.getParameter("orgName");
			String orgCode = request.getParameter("orgCode");
			String orgType = request.getParameter("orgType");
			String parentOrgid = request.getParameter("parentOrgid");
			String parentDeptid = request.getParameter("parentDeptid");
			
			//检查code唯一			
			List<Object[]> checklist= organizationService.checkOrgCode(id,orgCode);
			if(checklist!=null&&checklist.size()>0){
				htmlValue = "{\"result\":false}";
			}else{			
				if(id!=null&&!"".equals(id)){
					org.setId(Integer.parseInt(id));
				}			
				org.setOrgName(orgName);
				if(orgType!=null&&!"".equals(orgType)){
				 org.setOrgType(Integer.parseInt(orgType));
				}				
				org.setOrgCode(orgCode);
				org.setParentOrgid(parentOrgid);
				org.setParentDeptid(parentDeptid);
				
				org = organizationService.saveOrg(org);			
	            htmlValue = "{\"result\":true,\"id\":"+org.getId()+",\"parentDeptname\":\""+org.getParentDeptname()+"\",\"parentDeptid\":\""+org.getParentDeptid()+"\"}";
			}
		} catch (Exception e) {
			e.printStackTrace();
			htmlValue="";			
		}
		return "ajax";
	}

	
	/**
	 * 删除数据
	 * @return
	 */
	@Action(value="orgAction_delOrg",results={
			@Result(name="success",location="/ajax.jsp")
	})
	public String delOrg(){
		try {
			String id = request.getParameter("id");
			//该数据下是否存在下级数据
			List<Object[]> list = organizationService.checkChildOrgData(id);
			if(list!=null&&list.size()>0){
				htmlValue="2";
			}else{
				organizationService.deleleOrg(id);
				htmlValue="1";
			}	
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			htmlValue="0";
			return "error";
		}
	}
	
	
	@Action(value="orgAction_chooseOrgJson",results={
			@Result(type="json", params={"root","htmlValue"})
	})
	public void chooseOrgJson() throws IOException{
			String id = request.getParameter("id");
			if(id==null||"".equals(id)){
				//id = "first";
				id= "1947308";//"1947308"; //南通市
			}
			htmlValue = organizationService.findDeptJsonEasyui(id,null);			
			response.setCharacterEncoding("utf-8");//指定为utf-8
			response.getWriter().write(htmlValue);//转化为JSOn格式		
	}
	
	
	
	@Action(value="orgAction_chooseOrgJsonOfNT",results={
			@Result(type="json", params={"root","htmlValue"})
	})
	public void chooseOrgJsonOfNT() throws IOException{
			String id = request.getParameter("id");
			if(id==null||"".equals(id)){
				StringBuffer str = new StringBuffer("");
				str.append("[{\"id\":\"").append("").append("\", \"text\":\"")
				.append("全部").append("\" ");
				String proOrCity = request.getParameter("proOrCity");
				if(null == proOrCity || "".equals(proOrCity)){
					id = "1947308" ;
					str.append(", \"state\" : \"open\",\"checked\":true},{\"id\":\"").append(id).append("\", \"text\":\"")
					.append("山东省").append("\" ");
				}
				if(null != proOrCity && proOrCity.equals("SDS")){
					id = "1947308" ;
					str.append(", \"state\" : \"open\",\"checked\":true},{\"id\":\"").append(id).append("\", \"text\":\"")
					.append("山东省").append("\" ");
				}
				//.append("海南省").append("\" ");
				str.append(", \"state\" : \"closed\",\"checked\":true}]");
				htmlValue = str.toString();
			}else{
			htmlValue = organizationService.findDeptJsonEasyui(id,null);
			}
			response.setCharacterEncoding("utf-8");//指定为utf-8
			response.getWriter().write(htmlValue);//转化为JSOn格式		
	}
	
	
	
	/**
	 * 查询本身及以下机构
	 * @throws IOException
	 */
	@Action(value="orgAction_chooseChildOrgJson",results={
			@Result(type="json", params={"root","htmlValue"})
	})
	public void chooseChildOrgJson() throws IOException{
			
			String orgid = request.getParameter("orgid");
			String selectid = request.getParameter("selectid");
			
			htmlValue = organizationService.findOrgCodeJsonEasyui(orgid,selectid);			
			response.setCharacterEncoding("utf-8");//指定为utf-8
			response.getWriter().write(htmlValue);//转化为JSOn格式		
	}
	
	/**
	 * 同级机构的部门json
	 * @throws IOException
	 */
	@Action(value="orgAction_findDeptsByOrgidJson",results={
			@Result(type="json", params={"root","htmlValue"})
	})
	public void findDeptsByOrgidJson() throws IOException{
			String code = request.getParameter("code");
			String selectid = request.getParameter("selectid");
			List<Object []> list = userinfoService.findDeptByOrgcode(code);
			htmlValue = organizationService.findDeptsByOrgidJson(list,selectid);			
			response.setCharacterEncoding("utf-8");//指定为utf-8
			response.getWriter().write(htmlValue);//转化为JSOn格式		
	}
	
	
	/**
	 * 选择部门
	 * @return
	 */
	@Action(value="orgAction_chooseDept",results={
			@Result(name="success",location="sys/organization/chooseDept.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String chooseDept(){
		try {
			String orgid = request.getParameter("orgid");
			//机构同级部门
			List<Object[]> deptList = userinfoService.findDeptByOrgid(Integer.parseInt(orgid));
			request.setAttribute("deptList",deptList);

			String tagid = request.getParameter("tagid");
			String tagname = request.getParameter("tagname");

			String selectid = request.getParameter("selectid");						
			String selectname = request.getParameter("selectname");

			if(!"".equals(selectid)){
				request.setAttribute("selectid", selectid+",");
			}
			if(!"".equals(selectname)){
				request.setAttribute("selectname", selectname+",");
			}
			String isone = request.getParameter("isone");// one-单选，many-多选
			request.setAttribute("isone", isone);

			request.setAttribute("tagid",tagid);
			request.setAttribute("tagname",tagname);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	
	@Action(value="orgAction_chooseDeptData",results={
			@Result(name="ajax",location="/ajax.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String chooseDeptData(){
		try {
			String orgid = request.getParameter("orgid");
			htmlValue = userinfoService.findDeptDataJson(orgid);

			return "ajax";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
		
	@Action(value="orgAction_searchUser",results={
			@Result(name="ajax",location="/ajax.jsp")
	})
	public String searchUser(){
		try {
			String searchname = request.getParameter("searchname");
			htmlValue = organizationService.showUserOfSearchname(searchname);

			return "ajax";
		} catch (Exception e) {
			e.printStackTrace();
			return "ajax";
		}
	}
	
	
	@Action(value = "orgAction_searchorg", results = {
			@Result(name="success",type="json", params={"root","jsonData"})
	})
	public void searchoorg()throws IOException {			
			String selectword = request.getParameter("selectword");			
			jsonData = organizationService.searchoorg(selectword);
			this.jsonWrite(jsonData);
	}
	
	
	
	
	@Action(value="orgAction_toAddOrg",results={
			@Result(name="success",location="sys/organization/toAddOrg.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String toAddOrg(){
		try {
			String pid = request.getParameter("pid");
			orginfo = new SysOrganization();
			orginfo.setParentOrgid(pid);
			request.setAttribute("orginfo", orginfo);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	@Action(value = "orgAction_addOrginfo", results = {
			@Result(name="success",type="json", params={"root","jsonData"})
	})
	public void addOrginfo()throws IOException {		
		    organizationService.saveOrg(orginfo);			
			jsonData = "";
			this.jsonWrite(jsonData);
	}
	
	
	@Action(value = "orgAction_checkOrgcode", results = {
			@Result(name="success",type="json", params={"root","jsonData"})
	})
	public void checkOrgcode()throws IOException {	
		String orgCode = request.getParameter("orgCode");
		//检查code唯一			
		List<Object[]> checklist= organizationService.checkOrgCode(null,orgCode);
		if(checklist!=null&&checklist.size()>0){
			jsonData = "1"; //已存在
		}else{
			jsonData = "0";
		}
		this.jsonWrite(jsonData);
	}
	
	
		
	
	/**
	 * 选择组织机构和部门
	 * @return
	 */
	@Action(value="orgAction_chooseOrgAndDept",results={
			@Result(name="success",location="sys/organization/chooseOrgDept.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String chooseOrgAndDept(){
		try {
			String proOrCity = request.getParameter("proOrCity");
			if(null == proOrCity || "".equals(proOrCity)){
				proOrCity = "1830889" ;
			}
			if(null != proOrCity && proOrCity.equals("SDS")){
				proOrCity = "1947308" ;
			}
			request.setAttribute("orgjson", organizationService.findDeptJson(proOrCity));
			String orgid = request.getParameter("selectid");
			if(!"".equals(orgid)){
				orgid+=",";
			}
			request.setAttribute("selectid",orgid );
			String orgname = request.getParameter("selectname") ;
			if(!"".equals(orgname)){
				orgname+=",";
			}
			request.setAttribute("selectname", orgname);
			String isopen = request.getParameter("isopen");
			if(null == isopen || "".equals(isopen)){
				isopen = "n";
			}
			request.setAttribute("isopen", isopen);
			request.setAttribute("tagid", request.getParameter("tagid"));
			request.setAttribute("tagname", request.getParameter("tagname"));
			request.setAttribute("isone", request.getParameter("isone"));
			request.setAttribute("ischild", request.getParameter("ischild"));
			//parentDeptname=new String(parentDeptname.getBytes("ISO-8859-1"),"UTF-8");			
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	
	@Action(value="orgAction_chooseLplb",results={
			@Result(name="success",location="sys/organization/chooseLplb.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String chooseLplb(){
		try {					
			request.setAttribute("orgjson", organizationService.findDeptJson("1947308"));
			String orgid = request.getParameter("selectid");
			if(!"".equals(orgid)){
				orgid+=",";
			}
			request.setAttribute("selectid",orgid );
			String orgname = request.getParameter("selectname");
			if(!"".equals(orgname)){
				orgname+=",";
			}
			request.setAttribute("selectname", orgname);
			
			request.setAttribute("tagid", request.getParameter("tagid"));
			request.setAttribute("tagname", request.getParameter("tagname"));
			request.setAttribute("isone", request.getParameter("isone"));
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	
}
