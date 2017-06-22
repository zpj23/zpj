package com.goldenweb.sys.util;

import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.goldenweb.fxpg.frame.tools.SpringContext;

public class LogUtil {
	private static final Log log = LogFactory.getLog(LogUtil.class);

	/**
	 * 系统操作日志
	 * 
	 * @param error
	 */
	public static void logInfo(String operateType, String description,
			String uname, int userid) {
		try {
			operateType = operateType == null ? "" : operateType;
			description = description == null ? "" : description;

			String sql = "insert into sys_log (id,operate_type,operator,operator_id," +
			" operate_date,operate_description) values ('"
			+ UUID.randomUUID().toString() + "','" + operateType + "','"
			+ uname + "'," + userid + ",sysdate,'"	    	
			+ description + "')";      
			SpringContext.jdbcTemplate.update(sql);
		} catch (Exception e) {	   

		}
	}

	
	/**
	 * 系统警告日志
	 * 
	 * @param error
	 */
	public static void logWarn(String warn) {
		log.warn(warn);
	}

	
	/**
	 * 系统错误日志
	 * 
	 * @param error
	 */
	public static void logError(String errors) {
		log.error(errors);
	}
}
