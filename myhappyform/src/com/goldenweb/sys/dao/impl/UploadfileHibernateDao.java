package com.goldenweb.sys.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.goldenweb.fxpg.frame.dao.impl.GenericDaoHibernate;
import com.goldenweb.sys.dao.UploadfileHibernate;
import com.goldenweb.sys.pojo.SysUploadfile;

@Repository
public class UploadfileHibernateDao extends GenericDaoHibernate<SysUploadfile, Serializable> implements UploadfileHibernate{

	public UploadfileHibernateDao(){
		super(SysUploadfile.class);
	}
	
	@Override
	public List<SysUploadfile> getFiles(SysUploadfile queryModal) {
		List<String> conditions = new ArrayList<String>();
		String hql = " from SysUploadfile where 1=1 ";
		if(queryModal.getModuleName()!=null && !queryModal.getModuleName().isEmpty()){
			hql += " and moduleName = ?";
			conditions.add(queryModal.getModuleName());
		}
		if(queryModal.getTableUuid()!=null && !queryModal.getTableUuid().isEmpty()){
			hql += " and tableUuid = ?";
			conditions.add(queryModal.getTableUuid());
		}
		
		return this.createQuery(hql,conditions.toArray()).list();
	}

	/**
	 * 附件分页信息
	 */
	@Override
	public List<Object[]> findUserinfoList(String filename, String modelname,
			Integer page, Integer rows) {
		String sql = " select id,originalName,moduleName,uploadTime,fileType from SysUploadfile where 1=1 ";
		if(filename!=null&&!"".equals(filename)){
			sql+=" and originalName like '%"+filename+"%' ";
		}
		if(modelname!=null&&!"".equals(modelname)){
			sql+=" and moduleName ='"+modelname+"'  ";
		}	
		sql+=" order by uploadTime desc  ";
		return this.findListToPageByHql(sql, null, page, rows);
	}

	/**
	 * 附件总条数
	 */
	@Override
	public Long findCountNumber(String filename, String modelname) {
		String hql = " select count(id) from SysUploadfile where 1=1 ";
		if(filename!=null&&!"".equals(filename)){
			hql+=" and originalName like '%"+filename+"%' ";
		}
		if(modelname!=null&&!"".equals(modelname)){
			hql+=" and moduleName ='"+modelname+"'  ";
		}	
		return this.getAllCount(hql, null);
	}



	
	/*@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}*/
	
	/*******************************************************************/
	
	/**
	 * 查询当前的附件
	 * @param tableuuid
	 * @return
	 */
	/*public List<Object[]> findCurFiles(String tableuuid) {
		String hql = "select id,originalName,fileUrl,tableUuid,fileType,fileUrl from SysUploadfile where tableUuid = ? order by id ";
		try {
			List<Object[]> list = this.findByHql(hql, tableuuid);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}*/
	
}
