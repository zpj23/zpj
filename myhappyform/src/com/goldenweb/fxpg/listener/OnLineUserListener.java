package com.goldenweb.fxpg.listener;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import com.goldenweb.fxpg.frame.pojo.OnLineUser;
import com.goldenweb.sys.pojo.SysUserinfo;

/**  
 * @Description: TODO(在线用户监听 )
 * @author ljn  
 * @date 2014-6-10 上午10:06:46
 */
public class OnLineUserListener implements HttpSessionAttributeListener {
	     
	    /**
	     * 存放在线用户列表
	     */
	    //public static <Integer> onLineUserList = new LinkedList<Integer>();
	 
		public static Map<Integer, OnLineUser> onLineUserList = new HashMap<Integer, OnLineUser>();   
	    
	    /**
	     * 根据用户ID，查询用户是否在线
	     * @param userId  用户ID
	     * @return  true:表示用户在线   false:表示用户离线
	     */
	    public static boolean findUserOnLine(Integer userId){
	        return onLineUserList.containsKey(userId);
	    }
	     
	 
	    /**
	     * 用户登录时候，把用户的信息存到userSession里
	     * UserSession类的结构很简单，只有userId,userName两个属性
	     */
	    @Override
	    public void attributeAdded(HttpSessionBindingEvent se) {
	        if("iuserinfo".equals(se.getName())){
	            /**
	             * 用户上线的话，把用户的ID，添加到onLineUserList里
	             */
	        	SysUserinfo user = (SysUserinfo)se.getValue();
	            Integer userId = (user).getId();
	            
	            OnLineUser onlineUser = new OnLineUser();
	            onlineUser.setId(userId);
	            onlineUser.setName(user.getUsername());
	            onlineUser.setLoginTime(new Date());
	            onlineUser.setLoginIp(user.getCurrentLoginIp());
	            onLineUserList.put(userId, onlineUser);//(SysUserinfo)se.getValue()
	            //System.out.println("用户ID："+userId + " 上线了。。。");
	        }
	    }
	 
	    @Override
	    public void attributeRemoved(HttpSessionBindingEvent se) {
	        if ("iuserinfo".equals(se.getName())){
	            /**
	             * 用户下线的话，把用户的ID，从onLineUserList中移除
	             */
	            Integer userId = ((SysUserinfo)se.getValue()).getId();
	            onLineUserList.remove(userId);
	            //System.out.println("用户ID："+userId + " 下线了。。。");
	        }
	    }
	 
	    @Override
	    public void attributeReplaced(HttpSessionBindingEvent se) {
	        // TODO Auto-generated method stub
	         
	    }
}
