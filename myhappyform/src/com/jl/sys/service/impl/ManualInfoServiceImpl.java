package com.jl.sys.service.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jl.common.BaseService.MethodLog2;
import com.jl.sys.dao.HistoryStaffNameDao;
import com.jl.sys.dao.ManualInfoDao;
import com.jl.sys.pojo.CheckInfo;
import com.jl.sys.pojo.HistoryStaffname;
import com.jl.sys.pojo.UserInfo;
import com.jl.sys.service.ManualInfoService;
@Service
public class ManualInfoServiceImpl implements ManualInfoService {
	@Autowired
	private ManualInfoDao mDao;
	@Autowired
	private HistoryStaffNameDao hsDao;
	
	@MethodLog2(remark="保存考勤信息",type="新增/编辑")
	public void saveInfo(CheckInfo cInfo){
		try{
			cInfo.setStaffname(cInfo.getStaffname().trim());
			mDao.saveInfo(cInfo);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();  
		}
		HistoryStaffname hs=new HistoryStaffname();
		hs.setId(UUID.randomUUID().toString());
		hs.setStaffName(cInfo.getStaffname());
		hs.setUserId(cInfo.getCreateuserid());
		hsDao.saveHistoryStaffName(hs);
	}
	
	public Map findList(UserInfo user,int page,int rows,Map<String,String> param){
		List<Map> list=mDao.findList(user,page,rows,param);
		double wzgs=0;
		double ozgs=0;
		double workduringtime=0;
		double overtime=0;
		for(int i=0,length=list.size();i<length;i++){
			workduringtime=0;
			overtime=0;
			list.get(i).put("workdate", ((Map)(list.get(i))).get("workdate").toString().substring(0, 10));
			Object str=((Map)(list.get(i))).get("workduringtime");
			workduringtime=Double.valueOf(str.toString());
			overtime=Double.valueOf(((Map)(list.get(i))).get("overtime").toString());
			wzgs+=workduringtime;
			ozgs+=overtime;
		}
//		System.out.println(zgs+">>>>>>>循环数组");
//		System.out.println(System.currentTimeMillis());
		int count=mDao.findCount(user,param); 
		Map map=new HashMap();
		map.put("list", list);
		map.put("count", count);
		map.put("wzgs", wzgs);
		map.put("ozgs", ozgs);
		map.put("zgs", wzgs+ozgs);
		return map;
	}
	
	public List findStaffNameList(UserInfo user){
		return hsDao.findStaffNameList(user.getId());
	}
	
	
	public CheckInfo findById(String id){
		return mDao.findById(id);
	}
	@MethodLog2(remark="删除考勤信息",type="删除")
	public void delInfo(String id){
		try{
			mDao.delInfo(id);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();  
		}
	}
	@MethodLog2(remark="导出考勤",type="导出")
	public void exportExcel(Map<String,String> param,HttpServletRequest request, HttpServletResponse response,UserInfo user){
		String str="施工日期,施工人员,所属区域,施工项目,施工区域,工作内容,出勤时间,加班时间,备注";
		List list=mDao.findListObjectArray(user,param);
		HSSFWorkbook wb = new HSSFWorkbook();
		// 生成Excel的sheet
		HSSFSheet sheet1 = wb.createSheet("考勤信息");
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		String[] title=str.split(",");
		// Create a row and put some cells in it. Rows are 0 based.
		HSSFRow row1 = sheet1.createRow((short) 0);
		HSSFCell cell0=null;
		for(int m=0;m<title.length;m++){
			if(title[m].equalsIgnoreCase(""))
				sheet1.setColumnWidth(m,10* 512);
			sheet1.setDefaultColumnWidth(15);  
			sheet1.setDefaultRowHeight((short) (2 * 256)); //设置默认行高，表示2个字符的高度
			cell0=null;
			cell0 = row1.createCell((short) m);		
			cell0.setCellStyle(cellStyle);
			cell0.setCellValue(title[m]);
		}
		HSSFRow row2=null;
		HSSFCell cell=null;
		for (int j = 1; j <= list.size(); j++) {
			row2=null;
		    row2 = sheet1.createRow((short) j);
		    for (int i = 0; i < title.length; i++) {
		    	cell=null;
		    	cell = row2.createCell((short) i);	
//		    	if(title[i].equalsIgnoreCase("性别")||title[i].equalsIgnoreCase("民族")||title[i].equalsIgnoreCase("政治面貌")||title[i].equalsIgnoreCase("专家类别")){
//		    		cell.setCellValue(String.valueOf(((Object[])list.get(j-1))[i].toString()));
//		    	}else{
		    		if(null!=((Object[])list.get(j-1))[i]){
		    			cell.setCellValue(String.valueOf(((Object[])list.get(j-1))[i]));
		    		}else{
		    			cell.setCellValue("");
		    		}
//		    	}
		    }
		}
		OutputStream  output=null;
		try {
		    response.reset();
		    response.setContentType("application/vnd.ms-excel;charset=utf-8");
		    response.setHeader("Content-Disposition", "attachment;filename="
			    + new String(("考勤信息表"+".xls").getBytes(), "iso-8859-1"));
		    output = response.getOutputStream();
		    wb.write(output);
		    output.flush();
		    
		} catch (Exception e) {
		    e.printStackTrace();

		}finally{
			if(output!=null){
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public List findChartByUser(Map param){
		boolean isYF=false;
		if(null!=param.get("yuefen")&&!"".equalsIgnoreCase(param.get("yuefen").toString())){
			isYF=true;
		}
		if(isYF){
			return mDao.findChartByDay(param);
		}else{
			return mDao.findChartByUser(param);
		}
		
	}
	
	public int getWshNum(UserInfo user){
		return mDao.getWshNum(user);
	}
	@MethodLog2(remark="审核考勤",type="审核")
	public int saveShenhe(String ids){
		return mDao.saveShenhe(ids);
	}
}
