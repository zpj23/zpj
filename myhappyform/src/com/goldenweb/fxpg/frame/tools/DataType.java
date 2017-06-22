package com.goldenweb.fxpg.frame.tools;

public class DataType {
	
	/**
	 * 获取数据库字段类型
	 * @param type
	 * @param length
	 * @return
	 */
	public static String getDateType(String type,int length){		
		if(length!=0){
			return type+"("+length+")";
		}else{
			return type;
		}
	}
	
	
	/**
	 * 当前用户等级
	 * @param orgcode   机构编码 (mc.....)
	 * @return   1-省   2-市 3区  4镇  5 村  6网格
	 */
	public static String toDeptLv(String orgcode) {
		//机构code组成方式 MC2+2+2+3+3+3共17位
		//从后向前依次截取
		String a = "";
		//网格
		a =orgcode.substring(14,orgcode.length());
		if(!"000".equals(a)){
			return "MCLevel06";
		}
		//村
		a =orgcode.substring(11,14);
		if(!"000".equals(a)){
			return "MCLevel05";
		}
		//镇
		a =orgcode.substring(8,11);
		if(!"000".equals(a)){
			return "MCLevel04";
		}
		//区
		a =orgcode.substring(6,8);
		if(!"00".equals(a)){
			return "MCLevel03";
		}
		//市
		a =orgcode.substring(4,6);
		if(!"00".equals(a)){
			return "MCLevel02";
		}
		//省
		a =orgcode.substring(2,4);
		if(!"00".equals(a)){
			return "MCLevel01";
		}
		return "0";
	}

}
