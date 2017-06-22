package com.goldenweb.fxpg.frame.dao;

import java.util.List;

import com.goldenweb.fxpg.frame.pojo.Expert;
import com.goldenweb.sys.pojo.SysUserinfo;

public interface ExpertDao {
	/**
	 * 分页查询数据
	 * @Title findExpertPageList
	 * @param xm
	 * @param yjly
	 * @param zyyjfx
	 * @param page
	 * @param rows
	 * @return
	 * @author zpj
	 * @time 2015-10-19 上午10:11:50
	 */
	public List findExpertPageList(String xm,String yjly,String zyyjfx,int page,int rows,SysUserinfo user,String orgid,String nianling,String danwei,String zhiwu,String startcsny,String endcsny);
	
	/**
	 * 查询总数
	 * @Title findExpertCountNumber
	 * @param xm
	 * @param yjly
	 * @param zyyjfx
	 * @param page
	 * @param rows
	 * @return
	 * @author zpj
	 * @time 2015-10-19 上午10:12:05
	 */
	public int findExpertCountNumber (String xm,String yjly,String zyyjfx,int page,int rows,SysUserinfo user,String orgid,String nianling,String danwei,String zhiwu,String startcsny,String endcsny);
	
	/**
	 * 保存
	 * @Title saveExpert
	 * @param expert
	 * @author zpj
	 * @time 2015-11-3 下午2:09:34
	 */
	public void saveExpert(Expert expert);
	
	/**
	 * 根据主键查询
	 * @Title findExpertById
	 * @param id
	 * @return
	 * @author zpj
	 * @time 2015-11-3 下午2:09:16
	 */
	public Expert findExpertById(String id);
	
	/**
	 * 修改
	 * @Title mergeExpert
	 * @param expert
	 * @param id
	 * @author zpj
	 * @time 2015-11-3 下午2:09:05
	 */
	public void mergeExpert(Expert expert,String id);
	
	/**
	 * 查询
	 * @Title findImagePath
	 * @param id
	 * @return
	 * @author zpj
	 * @time 2015-11-3 下午2:08:14
	 */
	public String findImagePath(String id);
	
	/**
	 * 删除
	 * @Title removeExpert
	 * @param id
	 * @author zpj
	 * @time 2015-11-3 下午2:07:56
	 */
	public void removeExpert(String id);
	
	/**
	 * 导入excel
	 * @Title importData
	 * @param sql
	 * @author zpj
	 * @time 2015-12-16 下午3:47:18
	 */
	public void importData(String sql);
	
	
	/**
	 * 根据多个条件查询数据
	 * @Title findExpertByIds
	 * @author zpj
	 * @time 2015-12-16 下午3:48:19
	 */
	public List findExpertByCondition(String selectXINGMING,String selectYANJIULINGYU,String selectZHUYAOYANJIUFANGXIANG,String selectNIANLING,String selectDANWEI,String selectZHIWU,SysUserinfo u,String orgid);
	
	
	/**
	 * 根据勾选的id查询数据
	 * @Title findExpertByIds
	 * @param id
	 * @return
	 * @author zpj
	 * @time 2015-12-19 上午9:10:59
	 */
	public List findExpertByIds(String  id);

}
