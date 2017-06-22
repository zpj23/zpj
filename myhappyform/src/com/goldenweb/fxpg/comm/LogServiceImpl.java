package com.goldenweb.fxpg.comm;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.UUID;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

import javax.servlet.http.HttpServletRequest;

import com.goldenweb.fxpg.comm.BaseService.MethodLog2;
import com.goldenweb.sys.dao.LogHibernate;
import com.goldenweb.sys.pojo.SysLog;
import com.goldenweb.sys.pojo.SysUserinfo; 
import com.goldenweb.sys.util.DateHelper;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;


public class LogServiceImpl{
	
	@Autowired
	private LogHibernate logDao;
	
	
    public void log(){
    	
    }
    
    // 记录日志内容
    public Object  log2(ProceedingJoinPoint pjp) throws Throwable{
    	String methodName = pjp.getSignature().getName();
    	if(checkMethod(methodName)){
	        String clazzName = pjp.getTarget().getClass().getSimpleName();  
	        	       
	        ActionContext ac = ActionContext.getContext();
	        SysUserinfo userinfo=null;
	        if(ac!=null){
		        HttpServletRequest request =(HttpServletRequest)ac.get(StrutsStatics.HTTP_REQUEST);
		        if(request!=null){
			         userinfo = (SysUserinfo) request.getSession().getAttribute("iuserinfo");
			        /*if(userinfo!=null){
			           System.out.println("User:" + userinfo.getUsername());
			        }*/
		        }
	        }
	        
	        // System.out.println("Time:" + DateHelper.getToday("yyyy-MM-dd HH:mm:ss"));
	        // System.out.println("Class:" + clazzName); 
	        // System.out.println("Method:" + methodName);
	       
	        /*String opContent = adminOptionContent(pjp.getArgs(), methodName);
	        System.out.println(opContent);*/ 
	        // System.out.println("中文名称："+getMthodRemark(pjp));
	        
	        SysLog loginfo = new SysLog();
	        loginfo.setId(UUID.randomUUID().toString());
	        loginfo.setOperateDate(new Date());
	        String type =getMthodType(pjp);
	        loginfo.setOperateType(type);
	        String remark = getMthodRemark(pjp);
	        
	        if(userinfo!=null){
		        loginfo.setOperateDescription(userinfo.getUsername()+"在"+DateHelper.getToday("yyyy-MM-dd HH:mm:ss")+""+remark);
		        loginfo.setOperatorId(userinfo.getId());
		        loginfo.setOperator(userinfo.getUsername());
	        }
	        logDao.save(loginfo);
    	}
        // 调用目标对象的方法  
        Object retVal = pjp.proceed();  
  
        return retVal;  
    }
    
    
    
    // 方法是否需要记录日志
    private boolean checkMethod(String methodName) {
		Boolean bool = false;
		if(methodName.startsWith("save")||methodName.startsWith("add")||methodName.startsWith("create")||
				methodName.startsWith("insert")||methodName.startsWith("update")||methodName.startsWith("merge")||
				methodName.startsWith("del")||methodName.startsWith("remove")||methodName.startsWith("deal")||
				methodName.startsWith("put")||methodName.startsWith("new")){
			bool = true;
		}		
		return bool;
	}

    //方法参数
	public String adminOptionContent(Object[] args, String mName) throws Exception{  
    	  
        if (args == null) {  
            return null;  
        }  
          
        StringBuffer rs = new StringBuffer();  
        rs.append(mName);  
        String className = null;  
        int index = 1;  
        // 遍历参数对象  
        for (Object info : args) {  
              
            //获取对象类型  
            className = info.getClass().getName();  
            className = className.substring(className.lastIndexOf(".") + 1);  
            rs.append("[参数" + index + "，类型：" + className + "，值：");  
              
            // 获取对象的所有方法  
            Method[] methods = info.getClass().getDeclaredMethods();  
              
            // 遍历方法，判断get方法  
            for (Method method : methods) {  
                  
                String methodName = method.getName();  
                // 判断是不是get方法  
                if (methodName.indexOf("get") == -1) {// 不是get方法  
                    continue;// 不处理  
                }  
                  
                Object rsValue = null;  
                try {  
                      
                    // 调用get方法，获取返回值  
                    rsValue = method.invoke(info);  
                      
                    if (rsValue == null) {//没有返回值  
                        continue;  
                    }  
                      
                } catch (Exception e) {  
                    continue;  
                }  
                  
                //将值加入内容中  
                rs.append("(" + methodName + " : " + rsValue + ")");  
            }  
              
            rs.append("]");  
              
            index++;  
        }  
          
        return rs.toString();  
    }  
      
	// 获取自定义的方法备注
    public static String getMthodRemark(ProceedingJoinPoint joinPoint)  
            throws Exception {  
        String targetName = joinPoint.getTarget().getClass().getName();  
        String methodName = joinPoint.getSignature().getName();  
        Object[] arguments = joinPoint.getArgs();  
  
        Class targetClass = Class.forName(targetName);  
        Method[] method = targetClass.getMethods();  
        String methode = "";  
        for (Method m : method) {  
            if (m.getName().equals(methodName)) {  
                Class[] tmpCs = m.getParameterTypes();  
                if (tmpCs.length == arguments.length) {  
                    MethodLog2 methodCache = m.getAnnotation(MethodLog2.class); 
                    if(methodCache!=null){
                    methode = methodCache.remark();  
                    }
                    break;  
                }  
            }  
        }  
        return methode;  
    }  
    
    // 获取自定义的方法类型
    public static String getMthodType(ProceedingJoinPoint joinPoint)  
            throws Exception {  
        String targetName = joinPoint.getTarget().getClass().getName();  
        String methodName = joinPoint.getSignature().getName();  
        Object[] arguments = joinPoint.getArgs();  
  
        Class targetClass = Class.forName(targetName);  
        Method[] method = targetClass.getMethods();  
        String methode = "";  
        for (Method m : method) {  
            if (m.getName().equals(methodName)) {  
                Class[] tmpCs = m.getParameterTypes();  
                if (tmpCs.length == arguments.length) {  
                    MethodLog2 methodCache = m.getAnnotation(MethodLog2.class); 
                    if(methodCache!=null){
                    methode = methodCache.type();  
                    }
                    break;  
                }  
            }  
        }  
        return methode;  
    }  
    
    
    
    //有参无返回值的方法
    public void logArg(JoinPoint point) {
        //此方法返回的是一个数组，数组中包括request以及ActionCofig等类对象
       Object[] args = point.getArgs();
        System.out.println("目标参数列表：");
        if (args != null) {
            for (Object obj : args) {
                System.out.println(obj + ",");                
            }
            System.out.println();            
        }
    	//SysUserinfo user = (SysUserinfo)request.getSession().getAttribute("iuserinfo");
    }

    //有参并有返回值的方法
    public void logArgAndReturn(JoinPoint point, Object returnObj) {
        //此方法返回的是一个数组，数组中包括request以及ActionCofig等类对象
        Object[] args = point.getArgs();
        System.out.println("目标参数列表：");
        if (args != null) {
            for (Object obj : args) {
                System.out.println(obj + ",");
            }
            System.out.println();
            System.out.println("执行结果是：" + returnObj);
        }
    }

    
	
	
	
}
