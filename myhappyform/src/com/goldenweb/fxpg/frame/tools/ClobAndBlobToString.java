package com.goldenweb.fxpg.frame.tools;

import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialClob;

public class ClobAndBlobToString {
  
	/**
	 * @Description: TODO(Clob转String)
	 * @Title ClobToString
	 * @param clob
	 * @return String
	 * @throws SQLException
	 * @author Lee
	 * @time 2014-2-13 下午4:12:40
	 */
	public static String ClobToString(Clob clob) throws SQLException {
		Clob serialclob = new SerialClob(clob);
		String clobStr="";
		if(serialclob.length()!=0){
		 clobStr = serialclob.getSubString(1, (int) serialclob.length());
		}
		return clobStr;
	}
	
    /**
     * @Description: TODO(blob转String)
     * @Title BlobToString
     * @param blob
     * @return String
     * @throws UnsupportedEncodingException
     * @throws SQLException
     * @author Lee
     * @time 2014-2-13 下午4:12:43
     */
    public static String BlobToString(Blob blob) throws UnsupportedEncodingException, SQLException {
    	Blob serialblob = new SerialBlob(blob);
    	if(serialblob.length()!=0){
	    	String blobStr = new String(serialblob.getBytes(1, (int)serialblob.length()),Constant.getSysEncode());
	    	return blobStr.replaceAll("/r/n", "").replaceAll("<[^>]+>", "");
    	}
    	return "";
	}
    
    public static String BlobToStringAnd(Blob blob) throws UnsupportedEncodingException, SQLException {
    	Blob serialblob = new SerialBlob(blob);
    	if(serialblob.length()!=0){
	    	String blobStr = new String(serialblob.getBytes(1, (int)serialblob.length()),Constant.getSysEncode());
	    	return blobStr;
    	}
    	return "";
	}
}
