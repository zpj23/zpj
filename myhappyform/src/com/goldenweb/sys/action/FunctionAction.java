package com.goldenweb.sys.action;

import java.io.IOException;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.goldenweb.sys.pojo.SysFunction;
import com.goldenweb.sys.pojo.SysUserinfo;
import com.goldenweb.sys.service.FunctionService;
import com.goldenweb.sys.util.IAction;

@Namespace("/")
@Scope("prototype")
@Component("functionAction")
@ParentPackage("json-default")
public class FunctionAction extends IAction{

	@Autowired
	private FunctionService functionService;
	private SysFunction function;
	
	public SysFunction getFunction() {
		return function;
	}
	public void setFunction(SysFunction function) {
		this.function = function;
	}
	private String htmlValue;
	public String getHtmlValue() {
		return htmlValue;
	}
	public void setHtmlValue(String htmlValue) {
		this.htmlValue = htmlValue;
	}
	/***************************************************************************************/

	@Action(value="functionAction_showFunctionJson",
			results={
			@Result(type="json", params={"root","jsonData"})})
	public void showFunctionJson(){
		try {
			String id = request.getParameter("id");
			this.jsonData = functionService.showFunctionJson(id);
			this.jsonWrite(jsonData);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 功能树页面      sys/funciton/funTree.jsp
	 */
	@Action(value="functionAction_initFunction",results={
			@Result(name="success",location="sys/function/funTree.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String initFunction(){
		try {
			//加载功能树
			request.setAttribute("functionjson", functionService.bulidFunctionTree());

			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}	
	}

	
	/**
	 * 设置角色菜单页面
	 * @return
	 */
	@Action(value="functionAction_toSetRoleFunction",results={
			@Result(name="success",location="sys/function/roleFunction.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String toSetRoleFunction(){
		try {
			String roleid = request.getParameter("roleid");
			request.setAttribute("roleid", roleid);
			//加载功能树
			request.setAttribute("functionjson", functionService.bulidRoleFunctionTree(request,roleid));

			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}	
	}


	/**
	 * 设置角色菜单
	 * @return
	 */
	@Action(value="functionAction_setRoleFunction",results={
			@Result(name="success",location="/ajax.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String setRoleFunction(){
		try {
			String roleid = request.getParameter("roleid");
			String menuids = request.getParameter("menuids");
			functionService.saveRoleFunction(roleid,menuids);

			htmlValue="1";
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			htmlValue="0";
			return "success";
		}	
	}

	
	
	@Action(value="functionAction_showMenu",results={
			@Result(name="ajax",location="/ajax.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String showMenu(){
		SysUserinfo user = (SysUserinfo)request.getSession().getAttribute("iuserinfo");
		try {
			String pid = request.getParameter("id") == null ? request.getParameter("pid") :request.getParameter("id");
			htmlValue= functionService.showChildMenu(pid,user.getId().toString());
			return "ajax";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	
	
	
	
	/**
	 * 新增数据( sys_resource_item )   
	 * @return
	 */
	@Action(value="functionAction_doAddFunction",results={
			@Result(name="ajax",location="/ajax.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String doAddFunction(){
		try {
			SysFunction menu = new SysFunction();
			String id = request.getParameter("id");
			String title = request.getParameter("title");
			String url = request.getParameter("url");
			String funnum = request.getParameter("funnum");
			String remark = request.getParameter("remark");
			String parentFunid = request.getParameter("parentFunid");
			String operateType = request.getParameter("operateType");
			String picture = request.getParameter("picture");
			String isPopup = request.getParameter("isPopup");
			
			if(picture!=null&&!"".equals(picture)){
				if(picture.indexOf("/myhappyform")>-1){
				picture =picture.substring(picture.indexOf("/myhappyform")+8);
				}
			}
								
			if(id!=null&&!"".equals(id)){
				menu.setId(Integer.parseInt(id));
			}			
			menu.setTitle(title);
			menu.setUrl(url);
			if(funnum!=null&&!"".equals(funnum)){
			menu.setFunOrder(Integer.parseInt(funnum));
			}
			menu.setRemark(remark);
			if("".equals(parentFunid)){
				parentFunid ="1";//1级菜单的上级菜单设置为1
			}
			menu.setParentFunid(Integer.parseInt(parentFunid));
			if("".equals(operateType)||"null".equals(operateType)){
				menu.setOperateType(null);
			}else{
				menu.setOperateType(operateType);
			}
			menu.setPicture(picture);
			menu.setIsPopup(isPopup);
			
			menu = functionService.saveFunction(menu);			
            htmlValue = "{\"result\":true,\"id\":"+menu.getId()+" }";
			
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
	@Action(value="functionAction_delFunction",results={
			@Result(name="success",location="/ajax.jsp")
	})
	public String delFunction(){
		try {
			String id = request.getParameter("id");
			//该数据下是否存在下级数据
			List<Object[]> list = functionService.checkChildFunctionData(id);
			if(list!=null&&list.size()>0){
				htmlValue="2";
			}else{
				functionService.deleleFunction(id);
				htmlValue="1";
			}	
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			htmlValue="0";
			return "error";
		}
	}
	
	

	/**
	 * 选择图片页面
	 * @return
	 */
	@Action(value="functionAction_choosepic",results={
			@Result(name="success",location="sys/function/choosepic.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String choosepic(){
		try {
			//图片的路径在 images/menuimg/shwwpxxx.png
			
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}	
	}
	
	
	
	@Action(value="functionAction_choosequickpic",results={
			@Result(name="success",location="sys/function/choosequickpic.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String choosequickpic(){
		try {
			//图片的路径在 images/menuimg/shwwpxxx.png
			String num =request.getParameter("num");
			request.setAttribute("num", num);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}	
	}
	
	

}
