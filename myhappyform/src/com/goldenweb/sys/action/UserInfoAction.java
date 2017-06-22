package com.goldenweb.sys.action;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.goldenweb.sys.pojo.SysUserinfo;
import com.goldenweb.sys.service.UploadfileService;
import com.goldenweb.sys.service.UserinfoService;
import com.goldenweb.sys.util.IAction;
import com.goldenweb.sys.util.tag.ResourceCodeUtil;
import com.goldenweb.fxpg.cache.impl.OrginfoCache;
import com.goldenweb.fxpg.frame.tools.MD5;

@Namespace("/")
@Scope("prototype")
@Component("userAction")
@ParentPackage("default")
// 拦截器  
@InterceptorRefs({ @InterceptorRef("baseStack") })  
public class UserInfoAction extends IAction{

	@Autowired
	private UserinfoService userinfoService;	
	
	
	
	@Autowired
	private UploadfileService uploadfileService;
	
	private SysUserinfo userinfo;
	private File file;
	private String fileFileName;

	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}	
	
	public SysUserinfo getUserinfo() {
		return userinfo;
	}
	public void setUserinfo(SysUserinfo userinfo) {
		this.userinfo = userinfo;
	}
	private String htmlValue;
	public String getHtmlValue() {
		return htmlValue;
	}
	public void setHtmlValue(String htmlValue) {
		this.htmlValue = htmlValue;
	}	
	
	/**
	 * @throws IOException ******************************************************************************/

	@Action(value="userAction_toUserJson",results={
			@Result(name="success",type="json", params={"root","htmlValue"})
	})
	public void toUserJson() throws IOException{	
		String username =request.getParameter("username");
		String useroforg = request.getParameter("useroforg");
		String userofdept = request.getParameter("userofdept");
		
		String pageIndex = request.getParameter("page");
		if (pageIndex == null || pageIndex.equals("")) {
			page = 1; // 设置为第1页
		}
		htmlValue = userinfoService.findUserinfoJson(username,useroforg,userofdept,page,rows);
		response.setCharacterEncoding("utf-8");//指定为utf-8
		response.getWriter().write(htmlValue);//转化为JSOn格式
	}
	
	
	/**
	 * 用户信息列表
	 */
	@Action(value="userAction_toUserList",results={
			@Result(name="success",location="sys/user/userList.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String toUserList() throws Exception{
			return "success";
	}


	/**
	 * 跳转到新增修改页面
	 * @return
	 */
	@Action(value="userAction_toAddUser",results={
			@Result(name="success",location="sys/user/toAddUser.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String toAddUser(){
		try {			
			String id = request.getParameter("id");
			if(id!=null&&!"".equals(id)&&!"0".equals(id)){
				//修改时，将原来userinfo 带出
				userinfo = userinfoService.findOneUserInfo(Integer.parseInt(id));				
				request.setAttribute("userinfo", userinfo);
				if(userinfo.getDeptcode()!=null&&!"".equals(userinfo.getDeptcode())){
					//部门信息
					List<Object[]> deptList = userinfoService.findDeptByOrgid(userinfo.getMainOrgid());
					request.setAttribute("deptList", deptList);
				}
			}else{
				request.setAttribute("userinfo", userinfo);
			}
			//第三方机构信息list
			String thirdorgjson = userinfoService.findThirdorgjson();
			request.setAttribute("thirdorgjson", thirdorgjson);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}


	/**
	 * 上传附件
	 * @return
	 */
	@Action(value="userAction_uploadFile",results={
			@Result(name="success",location="sys/user/toAddUser.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String uploadFile(){
		//模块类型
		String modulename = request.getParameter("modulename");
		//业务表与附件表相关联的字段有系统自动生成的uuid
		String tableuuid = request.getParameter("tableuuid");
		modulename="user";
		tableuuid=UUID.randomUUID().toString();
		try {   		 
			String fileurl = uploadfileService.uploadFile(request, file, fileFileName, modulename, tableuuid);   		 
			userinfo.setImg(fileurl);

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
	@Action(value="userAction_doAddUser",results={
			@Result(name="success",location="/success.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String doAddUser(){		
		SysUserinfo user = (SysUserinfo)request.getSession().getAttribute("iuserinfo");
		try {
			if(userinfo.getId()==null){
				//新增
				userinfo.setCreateTime(new Date());
				userinfo.setCreateUserid(user.getId());
				userinfo.setUpdateTime(new Date());
			}else{
				//编辑
				userinfo.setUpdateTime(new Date());
				userinfo.setUpdateUserid(user.getId());
			}
			userinfoService.saveUser(userinfo);

			request.setAttribute("msg", "保存成功");
			request.setAttribute("whichPage", "self");
			request.setAttribute("nextURL","userAction_toUserList");
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
	@Action(value="userAction_viewUser",results={
			@Result(name="success",location="sys/user/viewUser.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String viewUser(){
		try {
			String id = request.getParameter("id");
			userinfo = userinfoService.findOneUserInfo(Integer.parseInt(id)); 
			//密码解密
			/*String pwd = userinfo.getPwd();
			userinfo.setPwd(new DESCipherCrossoverDelphi().getDesString(pwd));*/
			request.setAttribute("userinfo", userinfo);				
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
	@Action(value="userAction_delUser",results={
			@Result(name="success",location="/success.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String delUser(){		
		try {
			String id = request.getParameter("id");
			userinfoService.delUserInfo(id);	

			request.setAttribute("msg", "删除成功");
			request.setAttribute("whichPage", "self");
			request.setAttribute("nextURL","userAction_toUserList");
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}		
	}


	/**
	 * 更改密码页面
	 * @return
	 */
	@Action(value="userAction_toModifyPwd",results={
			@Result(name="success",location="sys/user/toModifyPwd.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String toModifyPwd(){
		try {
			SysUserinfo user = (SysUserinfo) request.getSession().getAttribute("iuserinfo");
			request.setAttribute("userid", request.getParameter("userid"));
			if(user.getRoles().contains("JS20150312110523")){
				request.setAttribute("isadmin",1);
			}else{
				request.setAttribute("isadmin",0);
			}
			request.setAttribute("oldpassword", user.getPwd());
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	

	/**
	 * 执行更改密码
	 * @return
	 */
	@Action(value="userAction_dealModifyPwd",results={
			@Result(name="ajax",location="/ajax.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String dealModifyPwd(){
		try {
			SysUserinfo user = (SysUserinfo) request.getSession().getAttribute("iuserinfo");
			String userid = request.getParameter("userid");
			String pwd = request.getParameter("pwd");
			String oldpwd = request.getParameter("oldpwd");			
			SysUserinfo sysuserinfo = userinfoService.findOneUserInfo(Integer.parseInt(userid));
			oldpwd = MD5.md5s((sysuserinfo.getLoginname()+"{"+oldpwd+"}"));
			// JS20150312110523 管理员角色code
			if(!oldpwd.equals(sysuserinfo.getPwd())&&!sysuserinfo.getPwd().equals("1")&&!user.getRoles().contains("JS20150312110523")){
				//原密码不正确
				htmlValue="2";
			}else{
			
			//加密
			pwd = MD5.md5s((sysuserinfo.getLoginname()+"{"+pwd+"}"));
			userinfoService.updatePwd(Integer.parseInt(userid),pwd);

			if(user.getId().toString().equals(userid)){
			 user.setPwd(pwd);
			}
			htmlValue="1";
			}
			return "ajax";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}


	/**
	 * 更改上传头像
	 * @return
	 */
	@Action(value="userAction_toModifyImg",results={
			@Result(name="success",location="sys/user/toModifyImg.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String toModifyImg(){
		try {			
			SysUserinfo user = (SysUserinfo) request.getSession().getAttribute("iuserinfo");
			userinfo = userinfoService.get(user.getId());
			request.setAttribute("userinfo", userinfo);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}


	/**
	 * 上传头像,主页面使用
	 * @return
	 */
	@Action(value="userAction_uploadFile2",results={
			@Result(name="success",location="sys/user/toModifyImg.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String uploadFile2(){
		//模块类型
		String modulename = request.getParameter("modulename");
		//业务表与附件表相关联的字段有系统自动生成的uuid
		String tableuuid = request.getParameter("tableuuid");
		modulename="user";
		tableuuid=UUID.randomUUID().toString();
		try {
			String fileurl = uploadfileService.uploadFile(request, file, fileFileName, modulename, tableuuid);   		 
			userinfo.setImg(fileurl);

			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}


	/**
	 * 执行更改头像路径
	 * @return
	 */
	@Action(value="userAction_dealModifyImg",results={
			@Result(name="ajax",location="/ajax.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String dealModifyImg(){
		try {
			SysUserinfo user = (SysUserinfo) request.getSession().getAttribute("iuserinfo");
			String imgpath = request.getParameter("imgpath");
			userinfoService.updateImg(user.getId(),imgpath);
			htmlValue="1";
			return "ajax";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	
	/**
	 * 检查用户名唯一
	 * @return 1-有重复
	 */
	@Action(value="userAction_checkSameUser",results={
			@Result(name="ajax",location="/ajax.jsp")
	})
	public String checkSameUser(){
		try {
			String userid = request.getParameter("userid");
			String loginname = request.getParameter("loginname");

			List<Object[]> list= userinfoService.checkSameUser(userid,loginname);
			if(list!=null&&list.size()>0&&"".equals(userid)){
				htmlValue="1";
			}else if(list!=null&&list.size()>1&&!"".equals(userid)){
				htmlValue="1";
			}else{
				htmlValue="0";
			}
			return "ajax";
		} catch (Exception e) {
			e.printStackTrace();
			return "ajax";
		}
	}
	
	
	@Action(value="userAction_updateUserStatus",results={
			@Result(name="ajax",location="/ajax.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String updateUserStatus(){
		try {
			String statusflag = request.getParameter("statusflag");
			String userid = request.getParameter("userid");
			htmlValue = userinfoService.updateUserStatus(statusflag,userid);
			return "ajax";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	
	
	@Action(value="userAction_findUserRole",results={
			@Result(name="ajax",location="/ajax.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String findUserRole(){
		try {			
			String userid = request.getParameter("userid");
			htmlValue = userinfoService.findUserRole(userid);
			return "ajax";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	@Action(value="userAction_findUserRole2",results={
			@Result(name="success",type="json", params={"root","jsonData"})
	})
	public void findUserRole2() throws IOException{	
		String userid = request.getParameter("userid");
		jsonData = userinfoService.findUserRole(userid);
		this.jsonWrite(jsonData);
	}
	
	
	@Action(value="userAction_findSameDeptByOrgid",results={
			@Result(name="success",type="json", params={"root","jsonData"})
	})
	public void findSameDeptByOrgid() throws IOException{	
		String orgid = request.getParameter("orgid");
		jsonData = userinfoService.findSameDeptJsonByOrgid(orgid);
		this.jsonWrite(jsonData);
	}
	
	
	/**
	 * 打印预览页
	 * @return
	 */
	@Action(value="userAction_printView",results={
			@Result(name="success",location="sys/user/printView.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String printView(){
		try {
			String id = request.getParameter("id");
			userinfo = userinfoService.findOneUserInfo(Integer.parseInt(id)); 
			request.setAttribute("userinfo", userinfo);				
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	
	@Action(value="userAction_findData",results={
			@Result(name="success",type="json", params={"root","jsonData"})
	})
	public void findData() throws IOException{	
		String val = request.getParameter("val");		
		jsonData = OrginfoCache.findOrgnamesByIds(val);
		if("".equals(jsonData)){
			jsonData = ResourceCodeUtil.getDictNameByCodeMany(val);
		}
		this.jsonWrite(jsonData);
	}
	
	/**********************************/
	/*
	@Action(value="userAction_printUser")
	public String printUser() {		
		DocumentHandler doch = new DocumentHandler();
		String userid = request.getParameter("userid");
		userinfo = userinfoService.findOneUserInfo(Integer.parseInt(userid));
		doch.createUserDoc(userinfo);
		
		String path = "D:/temp/user.doc";		
		
        HttpServletResponse response = null;
        ServletOutputStream out = null;
        try {
            response = ServletActionContext.getResponse();
            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            out = response.getOutputStream();
            
            File ff = new File(path);
            if(ff.exists()){
	    		FileInputStream fips = new FileInputStream(ff);  
	            byte[] btImg = readStream(fips);  
	            
	            out.write(btImg);
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (response != null) {
                try {
                    //response.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
	
	
	@Action(value="userAction_print")
	public String viewImages() {		
		DocumentHandler doch = new DocumentHandler();
		doch.createDoc();
		
		String path = "D:/temp/filetest.docx";		
		
        HttpServletResponse response = null;
        ServletOutputStream out = null;
        try {
            response = ServletActionContext.getResponse();
            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            out = response.getOutputStream();
            
            File ff = new File(path);
            if(ff.exists()){
	    		FileInputStream fips = new FileInputStream(ff);  
	            byte[] btImg = readStream(fips);  
	            
	            out.write(btImg);
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (response != null) {
                try {
                    //response.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
	
	*//** 
     * 读取管道中的流数据 
     *//*  
    private byte[] readStream(InputStream inStream) {  
        ByteArrayOutputStream bops = new ByteArrayOutputStream();  
        int data = -1;  
        try {  
            while((data = inStream.read()) != -1){  
                bops.write(data);  
            }  
            return bops.toByteArray();  
        }catch(Exception e){  
            return null;  
        }  
    }  */
	
	/***********************************/

}
