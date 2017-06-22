package com.goldenweb.sys.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.goldenweb.fxpg.frame.service.impl.GenericManagerImpl;
import com.goldenweb.fxpg.frame.tools.UUIDGenerator;
import com.goldenweb.sys.dao.PorletHibernate;
import com.goldenweb.sys.dao.PorletRoleHibernate;
import com.goldenweb.sys.dao.PortalHibernate;
import com.goldenweb.sys.pojo.Porlet;
import com.goldenweb.sys.pojo.PorletRole;
import com.goldenweb.sys.service.PorletService;


/**  
 * @Description: TODO(用一句话描述该文件做什么)
 * @author ljn  
 * @date 2014-6-18 上午9:45:02
 */
@Service
@Component("PorletService")
public class PorletServiceImpl extends GenericManagerImpl<Porlet, Serializable> implements
		PorletService {
	PorletHibernate porletHibernate;
	@Autowired
	PorletRoleHibernate porletRoleHibernate;
	@Autowired
	PortalHibernate portalHibernate;
	
	public PorletServiceImpl() {
	}
	
	@Autowired
	public PorletServiceImpl(PorletHibernate porletHibernate) {
		super(porletHibernate);
		this.porletHibernate = porletHibernate;
	}

	@Override
	public Porlet getByCode(String code) {
		String hql = " from Porlet where code = ?  ";
		return (Porlet) this.porletHibernate.createQuery(hql, new Object[]{code}).uniqueResult();
	}

	
	@Override
	public List<Porlet> getInfoByUserid(String userid) {
		
		String config = porletHibernate.getConfiginfoByUserid(userid);
		if(config!=null && !"".equals(config)){
			//该用户设置定制首页
			config = config.replaceAll(",", "','");
			config = config.replaceAll(":", "','");
			String hql=" from Porlet where code in ('"+config+"')";
			return porletHibernate.find(hql, null);
		}
		return null;
	}

	
	@Override
	public String findProletStrByRoleid(String roleid) {
		List<PorletRole> list = porletRoleHibernate.findProletStrByRoleid(roleid);
		String str ="";
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				str+=list.get(i).getPortalid()+",";
			}
			str+=","+str;
		}
		return str;
	}

	
	@Override
	public void saveSetPorlet(String roleid, String porlets) {
		//删除之前的数据
		porletRoleHibernate.deleteByRoleid(roleid);		
		//删除该角色下的人员的首页模块自定义内容
		portalHibernate.deleteByRoleid(roleid); 
		//保存
		String arr [] = porlets.split(",");
		PorletRole pr = null;
		for(int i=0;i<arr.length;i++){
			if(!"".equals(arr[i])){
				pr = new PorletRole(UUIDGenerator.generate32UUID(),roleid,arr[i]);
				porletRoleHibernate.saveInfo(pr);
			}
		}
	}

	
	@Override
	public List<Porlet> getPorletRole(String userid) {		
		return porletHibernate.getPorletRole(userid);
	}
}
