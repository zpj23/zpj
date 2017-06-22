package com.jl.sys.service.impl;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.jl.sys.dao.ManualInfoDao;
import com.jl.sys.pojo.CheckInfo;
import com.jl.sys.pojo.UserInfo;
import com.jl.sys.service.ManualInfoService;
@Service
public class ManualInfoServiceImpl implements ManualInfoService {
	@Autowired
	private ManualInfoDao mDao;
	
	public void saveInfo(CheckInfo cInfo){
		try{
			mDao.saveInfo(cInfo);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();  
		}
	}
	
	public Map findList(UserInfo user,int page,int rows,Map<String,String> param){
		List list=mDao.findList(user,page,rows,param);
		int count=mDao.findCount(user,param); 
		Map map=new HashMap();
		map.put("list", list);
		map.put("count", count);
		return map;
	}
	
	public CheckInfo findById(String id){
		return mDao.findById(id);
	}
	
	public void delInfo(String id){
		try{
			mDao.delInfo(id);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();  
		}
	}
	
	public void exportExcel(Map<String,String> param,HttpServletRequest request, HttpServletResponse response,UserInfo user){
		String str="姓名,性别,出生年月,年龄,单位,职务,民族,政治面貌,专家类别,职称,单位职务,研究领域,毕业院校及专业,最终学历,通讯地址,邮政编码,办公电话,传真号码,手机号码,E-mail,简要工作经历,主要研究方向,主要研究方向,主管部门意见,备注";
		List list=mDao.findList(user,1,1000,param);
		HSSFWorkbook wb = new HSSFWorkbook();
		// 生成Excel的sheet
		HSSFSheet sheet1 = wb.createSheet("考勤信息");
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		String[] title=str.split(",");
		// Create a row and put some cells in it. Rows are 0 based.
		HSSFRow row1 = sheet1.createRow((short) 0);
		
		for(int m=0;m<title.length;m++){
			if(title[m].equalsIgnoreCase(""))
			sheet1.setColumnWidth(m, 60 * 256);
			HSSFCell cell0 = row1.createCell((short) m);		
			cell0.setCellStyle(cellStyle);
			cell0.setCellValue(title[m]);
		}
		for (int j = 1; j <= list.size(); j++) {
		    HSSFRow row2 = sheet1.createRow((short) j);
		    for (int i = 0; i < title.length; i++) {
		    	HSSFCell cell = row2.createCell((short) i);	
		    	if(title[i].equalsIgnoreCase("性别")||title[i].equalsIgnoreCase("民族")||title[i].equalsIgnoreCase("政治面貌")||title[i].equalsIgnoreCase("专家类别")){
		    		cell.setCellValue(String.valueOf(((Object[])list.get(j-1))[i].toString()));
		    	}else{
		    		if(null!=((Object[])list.get(j-1))[i]){
		    			cell.setCellValue(String.valueOf(((Object[])list.get(j-1))[i]));
		    		}else{
		    			cell.setCellValue("");
		    		}
		    	}
		    }
		}
		try {
		    response.reset();
		    response.setContentType("application/vnd.ms-excel;charset=utf-8");
		    response.setHeader("Content-Disposition", "attachment;filename="
			    + new String(("专家信息表"+".xls").getBytes(), "iso-8859-1"));
		    OutputStream  output = response.getOutputStream();
		    wb.write(output);
		    output.flush();
		    output.close();
		} catch (Exception e) {
		    e.printStackTrace();

		}
	}
}
