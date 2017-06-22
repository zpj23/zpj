/** 
 * @Description: TODO(SessionFactoryUtils Connection 调用存储过程)
 * @Title: CallProcedure.java
 * @Package com.goldenweb.core.frame.tools
 * @author Lee
 * @date 2014-2-20 下午04:39:19
 * @version V1.0  
 * CopyRight (c) 江苏海盟软件
 */ 
package com.goldenweb.fxpg.frame.tools;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import oracle.jdbc.OracleTypes;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.SessionFactoryUtils;

/**
 * @Description: TODO(调用存储过程)
 * @ClassName: CallProcedure
 * @author Lee 
 * @date 2014-2-20 下午04:39:19
 *
 */
public class CallProcedure{
	 
	/**
	 * @Description TODO(调用存储过程)
	 * @Title callProcedure
	 * @param @param procString
	 * @param @param params
	 * @param @throws Exception
	 * @author Lee
	 * @time 2014-2-20 下午04:46:09
	 */
	public static void callProcedure(String procString,List<String> params) throws Exception {
		SessionFactory sessionFactory = (SessionFactory)SpringContext.getBean("sessionFactory");
		Connection connection = SessionFactoryUtils.getDataSource(sessionFactory).getConnection();
		CallableStatement stmt = null;  
	    try {  
	        stmt = connection.prepareCall(procString);  
	        if (params != null){
	            int idx = 1;  
	            for (Object obj : params) {  
	                if (obj != null) {  
	                    stmt.setObject(idx, obj);  
	                } else {  
	                    stmt.setNull(idx, Types.NULL);  
	                }  
	                idx++;  
	            }  
	        }  
	        stmt.execute();  
	    } catch (SQLException e) {  
	        e.printStackTrace();  
	        throw new Exception("调用存储过程的时候发生错误[sql = " + procString + "]", e);  
	    }  
	}
	
	/**
	 * 查询存储过程ResultSet
	 * @Title getResultSet
	 * @param sessionFactory 
	 * @param prepareCallName 存储过程
	 * @param param 数据值
	 * @return ResultSet 数组
	 * @throws SQLException
	 * @author Lee
	 * @time 2014年8月4日 下午3:53:30
	 */
	public static ResultSet [] getResultSet(SessionFactory sessionFactory,
			String prepareCallName, String[] param, String isArray) throws SQLException {
		Connection connection = SessionFactoryUtils.getDataSource(sessionFactory).getConnection();
        CallableStatement stmt = connection.prepareCall(prepareCallName);
        int idx = 1, paramLength=param.length;
        for (Object obj : param) {
          stmt.setObject(idx, obj);
          if(paramLength==idx){
        	  if("YES".equals(isArray)){
        		  stmt.registerOutParameter(++paramLength, OracleTypes.CURSOR); 
              }
        	  paramLength+=1;
        	  stmt.registerOutParameter(paramLength, OracleTypes.CURSOR);
          }
          idx++;
        }
        stmt.execute();
        ResultSet [] rs = {(ResultSet) stmt.getObject(paramLength),null};
        if("YES".equals(isArray)){
        	rs[0]=(ResultSet) stmt.getObject(paramLength-1);
        	rs[1]=(ResultSet) stmt.getObject(paramLength);
        }
		return rs;
	}
}
