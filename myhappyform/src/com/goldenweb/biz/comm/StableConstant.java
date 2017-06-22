package com.goldenweb.biz.comm;
/**  
 * @Description: TODO(用一句话描述该文件做什么)
 * @author ljn  
 * @date 2014-4-18 下午1:52:19
 */
public class StableConstant {

	//字典
	public static String DCDB_TYPE = "DB01";  //督查督办类型
	public static String DCDB_MODE = "DB02";  //督查督办形式
	public static String DCDB_LEVEL = "DB03"; //督查督办等级
	public static String DCDB_FEEDBACK_TYPE = "DB04";  //督查督办反馈类型
	public static String DCDB_FEEDBACK_SUBTYPE_01 = "DB040101"; //督办子类型-随时
	public static String DCDB_FEEDBACK_SUBTYPE_02 = "DB040102"; //督办子类型-按天
	public static String DCDB_FEEDBACK_SUBTYPE_03 = "DB040103"; //督办子类型-按周
	public static String DCDB_FEEDBACK_SUBTYPE_04 = "DB040104"; //督办子类型-按月
	public static String DCDB_PINGJIA_RESULT = "DB05";  //评价结果
	
	public static String RISK_TYPE="FX01";
	public static String RISK_START_CONDITION = "FX02";
	public static String RISK_PROPERTY="FX06";
	public static String RISK_POINT="FX07";
	public static String RISK_LEVEL="FX08";

	//角色
	/*public static String ROLE_DCDBZY = "DCDBZY";
	public static String ROLE_LEADER_CHU = "LEADER_CHU";
	public static String ROLE_LEADER_OFFICE = "LEADER_OFFICE";*/
	public static String ROLE_RESDEPTS = "RESDEPTS";
	public static String ROLE_ADMIN = "admin";
	//public static String ROLE_RISK_PGZT = "RISK_PGZT";
	public static String ROLE_RISK_JDZT = "RISK_JDZT";
	public static String ROLE_HNPG_LPLB = "HNPG_LPLB";
	
	public static String ROLE_DCDBZY = "HNWWB";  //省级
	public static String ROLE_DCDBZY2 = "JS20150326092034"; //市级
	public static String ROLE_LEADER_CHU = "JS20150421165600";
	public static String ROLE_LEADER_CHU2 = "JS20150525165715";
	public static String ROLE_LEADER_OFFICE = "JS20150421165635";
	public static String ROLE_LEADER_OFFICE2 = "JS20150629111159";
	public static String ROLE_RISK_PGZT = "JS20150427171035";
	
	
	//部门
	public static String DEPT_WWB = "AA01250101";
	
	//public static String AUDIT_RESULTS = "[{\"key\": \"1\",\"value\": \"同意\"},{\"key\": \"0\",\"value\": \"不同意\"}]";
	
	
	
	//分隔符
	public static String SEPARATOR_MINUS = "-";
	public static String SEPARATOR_WAVE = "~";
	
	public static String WORD_PLEASECHOOSE = "------请选择------";
}
