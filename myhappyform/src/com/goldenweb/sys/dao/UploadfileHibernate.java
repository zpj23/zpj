package com.goldenweb.sys.dao;

import java.io.Serializable;
import java.util.List;

import com.goldenweb.fxpg.frame.dao.GenericDao;
import com.goldenweb.sys.pojo.SysUploadfile;

public interface UploadfileHibernate extends GenericDao<SysUploadfile, Serializable> {

	//public List<Object[]> findCurFiles(String tableuuid);
	
	public List<SysUploadfile> getFiles(SysUploadfile queryModal);

	public List<Object[]> findUserinfoList(String filename, String modelname,
			Integer page, Integer rows);

	public Long findCountNumber(String filename, String modelname);

	/*public void save(SysUploadfile fi);

	public SysUploadfile getEntity(Integer id);

	public void delete(SysUploadfile uf);*/
}
