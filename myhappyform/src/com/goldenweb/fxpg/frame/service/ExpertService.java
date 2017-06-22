package com.goldenweb.fxpg.frame.service;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.goldenweb.fxpg.frame.pojo.Expert;
import com.goldenweb.sys.pojo.SysUserinfo;


public interface ExpertService {
	/**
	 * 查询列表展示的json数据
	 * @Title expertJson
	 * @param xm
	 * @param yjly
	 * @param zyyjfx
	 * @param page
	 * @param rows
	 * @return
	 * @author zpj
	 * @time 2015-10-19 上午10:11:25
	 */
	public String expertJson(String xm,String yjly,String zyyjfx,int page,int rows,SysUserinfo user,String orgid,String nianling,String danwei,String zhiwu,String startcsny,String endcsny);
	
	
	
	/**
	 * 保存专家数据
	 * @Title saveExpert
	 * @param expert
	 * @author zpj
	 * @time 2015-10-20 上午8:49:14
	 */
	public void saveExpert(Expert expert);
	
	/**
	 * 根据主键查询数据
	 * @Title findExpertById
	 * @param id
	 * @return
	 * @author zpj
	 * @time 2015-10-20 上午9:27:22
	 */
	public Expert findExpertById(String id);
	
	/**
	 * 修改
	 * @Title merge
	 * @param expert
	 * @param id
	 * @author zpj
	 * @time 2015-10-20 上午10:05:04
	 */
	public void merge(Expert expert,String id);
	
	/**
	 *  导出到word
	 * @Title dzWord
	 * @param id
	 * @param path
	 * @return
	 * @author zpj
	 * @time 2015-11-3 下午2:05:34
	 */
	public String dzWord(String id,String path);
	
	/**
	 * 上传照片的路径
	 * @Title findImagePath
	 * @param id
	 * @return
	 * @author zpj
	 * @time 2015-11-3 上午11:06:33
	 */
	public String findImagePath(String id);
	
	/**
	 * 删除一项纪录
	 * @Title removeExpert
	 * @param id
	 * @author zpj
	 * @time 2015-11-3 下午1:42:13
	 */
	public void removeExpert(String id);
	
	/**
	 * 批量导入excel数据
	 * @Title importExcel
	 * @param map
	 * @author zpj
	 * @time 2015-11-24 上午10:03:43
	 */
	public void importExcel(List list,String user,String layername,SysUserinfo u) throws Exception;
	
	/**
	 * 导出到excel
	 * @Title exportExcel
	 * @param id
	 * @author zpj
	 * @time 2015-12-16 下午3:40:55
	 */
	public void exportExcel(String id,String selectXINGMING,String selectYANJIULINGYU,String selectZHUYAOYANJIUFANGXIANG,String selectNIANLING,String selectDANWEI,String selectZHIWU,HttpServletRequest request,HttpServletResponse response,SysUserinfo u,String orgid);
}
