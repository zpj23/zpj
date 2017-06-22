package com.goldenweb.fxpg.frame.pojo;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.goldenweb.fxpg.frame.tools.DBManager;
import com.goldenweb.fxpg.weboffice.service.impl.OfficeServiceImpl;

public class SQLFormatter implements Serializable {

	// 表名称
	private String tableName_;
	
	// 子表名称
	private String childTableName_;
	
	// 列名称
	private String tableColumns_;
	
	// 列值
	private String tableValues_;
	
	// SQL条件
	private String tableWhere_;
	
	// SQL关键字
	private String tableKeyWork_;
	
	// 当前登录人ID
	private String loginNameGuid;
	
	// 当前登录人orgid
	private String loginOrgid;
	
	private Map tableMap_;
	
	// 大字段列
	private String [] blobObjArray_;
	
	// 日期数组
	private String [] dateObjArray_;
	
	// 子表SQL
	private String childSql_;
	
    // 表数据ID
	private String dataGuid;
	
	// 涉稳人员主键 
	private String personGuid;
	
	public SQLFormatter(){}
	
	public SQLFormatter(String loginNameGuid, String [] blobObjArray_, String [] dateObjArray_){
		this.loginNameGuid = loginNameGuid;
		this.blobObjArray_ = blobObjArray_;
		this.dateObjArray_ = dateObjArray_;
	}
	
	public SQLFormatter(String loginNameGuid,String loginOrgid, String [] blobObjArray_, String [] dateObjArray_){
		this.loginNameGuid = loginNameGuid;
		this.loginOrgid = loginOrgid ;
		this.blobObjArray_ = blobObjArray_;
		this.dateObjArray_ = dateObjArray_;
	}
	
	public String getTableName_() {
		return tableName_;
	}

	public void setTableName_(String tableName_) {
		this.tableName_ = tableName_;
	}

	public String getChildTableName_() {
		return childTableName_;
	}

	public void setChildTableName_(String childTableName_) {
		this.childTableName_ = childTableName_;
	}

	public String getTableColumns_(){
		return tableColumns_;
	}

	public void setTableColumns_(String tableColumns_) {
		this.tableColumns_ = tableColumns_;
	}

	public String getTableValues_() {
		tableValues_ = tableValues_.replace("CREATE_USER", loginNameGuid).replace("CREATE_DATE","sysdate");
		return tableValues_;
	}

	public void setTableValues_(String tableValues_) {
		this.tableValues_ = tableValues_;
	}

	public String getTableWhere_() {
		return tableWhere_;
	}

	public void setTableWhere_(String tableWhere_) {
		this.tableWhere_ = tableWhere_;
	}

	public String getTableKeyWork_() {
		return tableKeyWork_;
	}

	public void setTableKeyWork_(String tableKeyWork_) {
		this.tableKeyWork_ = tableKeyWork_;
	}

	public Map getTableMap_() {
		return tableMap_;
	}

	public void setTableMap_(Map tableMap_) {
		this.tableMap_ = tableMap_;
	}
	
	public String getChildSql_() {
		childSql_ = childSql_.replace("CREATE_USER_DATA", loginNameGuid)
		.replace("'CREATE_DATE_DATA'","sysdate")
		.replace("UPDATE_USER_DATA", loginNameGuid)
		.replace("'UPDATE_DATE_DATA'", "sysdate")
		.replace("ORG_GUID_DATA",dataGuid);
		return childSql_;
	}
	
	
	public String getChildSql_linkid() {
		childSql_ = childSql_.replace("CREATE_USER_DATA", loginNameGuid)
		.replace("'CREATE_DATE_DATA'","sysdate")
		.replace("UPDATE_USER_DATA", loginNameGuid)
		.replace("'UPDATE_DATE_DATA'", "sysdate")
		.replace("ORG_GUID_DATA",loginOrgid)
		.replace("LINK_GUID_DATA",dataGuid);
		return childSql_;
	}

	public void setChildSql_(String childSql_) {
		this.childSql_ = childSql_;
	}

	public String getDataGuid() {
		return dataGuid;
	}

	public void setDataGuid(String dataGuid) {
		this.dataGuid = dataGuid;
	}

	public String getPersonGuid() {
		return personGuid;
	}

	public void setPersonGuid(String personGuid) {
		this.personGuid = personGuid;
	}

	/**
	 * byte数组转换成16进制String
	 * @Title bytesToHexString
	 * @param bArray
	 * @return String  
	 * @author Lee
	 * @time 2014-4-11 下午03:56:12
	 */
	private String bytesToHexString(byte [] bArray){
		String hexString="0123456789ABCDEF";
		StringBuilder sb=new StringBuilder(bArray.length*2);
		// 将字节数组中每个字节拆解成2位16进制整数
		for(int i=0;i<bArray.length;i++){
			sb.append(hexString.charAt((bArray[i]&0xf0)>>4));
			sb.append(hexString.charAt((bArray[i]&0x0f)>>0));
		}
		return sb.toString();
	}
	
	public String insertMapFormatter() throws UnsupportedEncodingException, SQLException{
		String sqlColumns = "";
		String sqlValues = "";
		List<String> listStr = new ArrayList<String>(Arrays.asList(blobObjArray_));
		List<String> listDateObjArray = new ArrayList<String>(Arrays.asList(dateObjArray_));
		for (Object key : tableMap_.keySet()) {
			if(!"FILEID".equalsIgnoreCase(key.toString())&&!"EDITORVALUE".equalsIgnoreCase(key.toString())){
				sqlColumns+=key+",";
				if(listStr.contains(key.toString().toUpperCase())){
					/*String btoStr =bytesToHexString(tableMap_.get(key).toString().getBytes());
					sqlValues+="'"+btoStr+"',";*/
					//String btoStr = "EMPTY_BLOB()";
					//sqlValues += ""+btoStr+",";
					sqlValues+="'"+tableMap_.get(key)+"',";
				}else{
					if(listDateObjArray.contains(key.toString().toUpperCase())){
						if(tableMap_.get(key)==null || "".equals(tableMap_.get(key))){
							sqlValues+="'',";
						}else{
							sqlValues+=tableMap_.get(key)+",";	
						}
					}else{
						if("ORG_GUID".equals(key.toString().toUpperCase())){
							sqlValues+="'"+ (int)Math.round(Double.valueOf(tableMap_.get(key).toString()))+"',";
						}else{
						    sqlValues+="'"+tableMap_.get(key)+"',";
						}
					}
				}
			}
		}
		sqlColumns+="CREATE_USER,CREATE_DATE";
		sqlValues+="'"+this.loginNameGuid+"',sysdate";
	    String sql ="insert into "+this.tableName_+" ("+sqlColumns+") values ("+sqlValues+") ";
	    return sql;
	}
	
	public String updateMapFormatter() throws UnsupportedEncodingException, SQLException{
		String sqlValues = "";
		List<String> listStr = new ArrayList<String>(Arrays.asList(blobObjArray_));
		List<String> listDateObjArray = new ArrayList<String>(Arrays.asList(dateObjArray_));
		for (Object key : tableMap_.keySet()) {
			if(!"FILEID".equalsIgnoreCase(key.toString())&&!"EDITORVALUE".equalsIgnoreCase(key.toString())){
				if(listStr.contains(key.toString().toUpperCase())){
					// TODO 转换成CLOB
					// String btoStr = tableMap_.get(key).toString();
					/*String btoStr =bytesToHexString(tableMap_.get(key).toString().getBytes());
					sqlValues+=key+"='"+btoStr+"',";*/
					sqlValues+=key+"='"+tableMap_.get(key)+"',";
				}else{
					if(listDateObjArray.contains(key.toString().toUpperCase())){
						if(tableMap_.get(key)==null || "".equals(tableMap_.get(key))){
							sqlValues+=key+"='',";
						}else{
							sqlValues+=key+"="+tableMap_.get(key)+",";
						}
					}else{						
						if("ORG_GUID".equals(key.toString().toUpperCase())){
							if(!"".equals(tableMap_.get(key).toString())){
								sqlValues+=key+"='"+(int)Math.round(Double.valueOf(tableMap_.get(key).toString()))+"',";
							}else{
								sqlValues+=key+"='',";
							}							
						}else{
							sqlValues+=key+"='"+tableMap_.get(key)+"',";
						}
					}
				}
			}
		}
		sqlValues+="UPDATE_USER='"+this.loginNameGuid+"',";
		sqlValues+="UPDATE_DATE=sysdate,";
		sqlValues = "set "+sqlValues.substring(0,sqlValues.lastIndexOf(","));
		String sql="update "+this.tableName_+" "+sqlValues+" "+this.tableWhere_;
		return sql;
	}
	
	
	
	
	
	public String insertMapFormatter2(DBManager DbaObj) throws UnsupportedEncodingException, Exception{
		String sqlValues = "";
		List<String> listStr = new ArrayList<String>(Arrays.asList(blobObjArray_));
		List<String> listDateObjArray = new ArrayList<String>(Arrays.asList(dateObjArray_));
		String mainid = "";
		for (Object key : tableMap_.keySet()) {
			if((this.tableName_+"_GUID").equals(key.toString().toUpperCase())){
				mainid = tableMap_.get(key).toString();
			}
		}
		
		for (Object key : tableMap_.keySet()) {
			if(listStr.contains(key.toString().toUpperCase())){
				String btoStr =bytesToHexString(tableMap_.get(key).toString().getBytes());
				sqlValues+=key+"='"+btoStr+"',";
				String sql = " select "+key+" from "+this.tableName_+
						" where "+this.tableName_+"_GUID = '"+mainid+"' for update";
				Statement stmt = null;
				
	            //DbaObj.Conn.setAutoCommit(false);
	            stmt = DbaObj.Conn.createStatement();
	            ResultSet update =  stmt.executeQuery(sql);
	            if (update.next()) {
				  new OfficeServiceImpl().PutAtBlob(update.getBlob(key.toString()), 10000);
	            }
				update.close();
	            stmt.close();
	            DbaObj.Conn.commit();
			}
		}		
		sqlValues = "set "+sqlValues.substring(0,sqlValues.lastIndexOf(","));
		String sql="update "+this.tableName_+" "+sqlValues+" "+this.tableWhere_;
				
		return sql;
	}
	
}
