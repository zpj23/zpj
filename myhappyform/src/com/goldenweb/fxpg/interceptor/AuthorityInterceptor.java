package com.goldenweb.fxpg.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;



import com.goldenweb.fxpg.exception.AhCustomException;
import com.goldenweb.fxpg.exception.AhCustomException.ExcCode;
import com.goldenweb.sys.action.LoginAction;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
/**
 * 用户权限拦截器，在struts.xml中配置
 *
 */
public class AuthorityInterceptor extends AbstractInterceptor{

	private static final long serialVersionUID = 2914081148619842225L;
	private LoginAction loginAction;
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		//		String method = invocation.getProxy().getMethod();
		//		String actionName=invocation.getInvocationContext().getName();
		String userid = (String) request.getSession().getAttribute("userid");
		if(userid==null||"".equals(userid)){
			request.setAttribute("msg",AhCustomException.getExcMessage(ExcCode.Unlogined));
			return Action.ERROR;
		}
		//获取项目路径              getSession().
		String contextPath=request.getSession().getServletContext().getContextPath();
		//获取当前路径
		String uri = request.getRequestURI();
		//当前相对项目的路径
		String actionUrl=uri.replace(contextPath, "");
		boolean used = loginAction.used(userid, actionUrl);
		if(used){
			return invocation.invoke();
		}else{
			request.setAttribute("msg",AhCustomException.getExcMessage(ExcCode.InvalidRights));
			return Action.ERROR;
		}
	}
	public LoginAction getLoginAction() {
		return loginAction;
	}
	public void setLoginAction(LoginAction loginAction) {
		this.loginAction = loginAction;
	}


}
