package com.jl.sys.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jl.common.BaseService.MethodLog2;
import com.jl.sys.dao.SgxmDao;
import com.jl.sys.pojo.SgxmInfo;
import com.jl.sys.service.SgxmService;
@Service
public class SgxmServiceImpl implements SgxmService {
	
	Logger logger=Logger.getLogger(SgxmServiceImpl.class);
	@Autowired
	private SgxmDao sgxmDao;
	
	public Map findList(int page,int rows,Map<String,String> param){
		List<Map> list=sgxmDao.findList(page,rows,param);
		Map countMap=sgxmDao.findCount(param); 
		Map map=new HashMap();
		map.put("list", list);
		map.put("count", countMap.get("zs"));//总数
		map.put("chuqin", countMap.get("cq"));//出勤和
		map.put("jiaban", countMap.get("jb"));//加班和
		map.put("zonggongshi", countMap.get("zgs"));//总工时和
		map.put("total_zgz", countMap.get("zgz"));//总工资
		map.put("total_yfgzy", countMap.get("yfgzy"));//预发工资
		map.put("total_sygz", countMap.get("sygz"));//剩余工资
		map.put("total_yfgz", countMap.get("yfgz"));//应发工资
		map.put("total_fybt", countMap.get("fybt"));//费用补贴
		map.put("total_mq", countMap.get("mq"));//满勤
		map.put("total_qtkk", countMap.get("qtkk"));//其他扣款
		return map;
	}
	
	public SgxmInfo findById(String id){
		return sgxmDao.findById(id);
	}
	public void delInfo(String id){
		sgxmDao.delInfo(id);
	}
	public void saveInfo(SgxmInfo pi){
		sgxmDao.saveInfo(pi);
	}
	
	@MethodLog2(remark="导出项目工资汇总信息",type="导出")
	public void exportExcel(Map<String,String> param,HttpServletRequest request, HttpServletResponse response){
		OutputStream  output=null;
		try {
			String str="姓名,月份,工地,施工项目,出勤（小时）,加班（小时）,总工时（小时）,工价包月,基本工资,加班工资和奖金,应发工资,劳护补贴,费用补贴,满勤,其他扣款,总工资,预发工资,剩余工资,签字,备注";
	//		List list=payrollDao.findListByGroupUser("2019");
			List list=sgxmDao.findList(param);
			Map countMap=sgxmDao.findCount(param); 
			
			
			HSSFWorkbook wb = new HSSFWorkbook();
			// 生成Excel的sheet
			HSSFSheet sheet1 = wb.createSheet("项目工资汇总信息");
			HSSFCellStyle cellStyle = wb.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			HSSFFont hf=wb.createFont();
	//		hf.setColor(hf.COLOR_RED);
			hf.setFontHeightInPoints((short)10);
			hf.setFontName("宋体");
			cellStyle.setFont(hf);
			//合并单元格的实现
	//		CellRangeAddress cellRange1 = new CellRangeAddress(0, 0, (short) 0, (short) 10);
	//        sheet.addMergedRegion(cellRange1);
	//        CellRangeAddress
	
			
			String[] title=str.split(",");
			// Create a row and put some cells in it. Rows are 0 based.
			HSSFRow row1 = sheet1.createRow((short) 0);
			HSSFCell cell0=null;
			for(int m=0;m<title.length;m++){
				sheet1.setColumnWidth(m,10*512);
				sheet1.setDefaultColumnWidth(10000);  
				sheet1.setDefaultRowHeight((short) (3 * 256)); //设置默认行高，表示2个字符的高度
				sheet1.setDefaultRowHeightInPoints(30);
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
			//总计汇总求和  暂不做
			row2=null;
			row2 = sheet1.createRow((short) list.size()+1);
			for (int i = 0; i < title.length; i++) {
				cell=null;
				cell = row2.createCell((short) i);
				if(i==0){
					cell.setCellValue("合计");
				}else if(i==14){
					cell.setCellValue(String.valueOf(countMap.get("zgz")));
				}else if(i==15){
					cell.setCellValue(String.valueOf(countMap.get("yfgzy")));
				}else if(i==16){
					cell.setCellValue(String.valueOf(countMap.get("sygz")));
				}else{
					cell.setCellValue("");
				}
			}
			
			
			String title1=param.get("username")+"-"+param.get("yuefen")+"-"+param.get("sgxm")+"-";//String.valueOf(((Object[])list.get(0))[0]);
		    response.reset();
		    response.setContentType("application/vnd.ms-excel;charset=utf-8");
		    response.setHeader("Content-Disposition", "attachment;filename="
			    + new String((title1+"年度汇总表"+".xls").getBytes(), "iso-8859-1"));
		    output = response.getOutputStream();
		    wb.write(output);
		    output.flush();
		    
		} catch (Exception e) {
		    e.printStackTrace();
		    logger.error(e);
		}finally{
			if(output!=null){
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
					logger.error(e);
				}
			}
		}
	}
}
