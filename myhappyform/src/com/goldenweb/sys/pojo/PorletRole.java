package com.goldenweb.sys.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "SYS_PORTAL_ROLE")
public class PorletRole {

	private String id;
	private String roleid;
	private String portalid;
	
	public PorletRole(){
		
	}
	
	public PorletRole(String id,String roleid,String portalid){
		this.id=id;
		this.roleid=roleid;
		this.portalid=portalid;
	}
	
	
	@Id
	@Column(name = "id", nullable = false, length=50)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "roleid", length=50) 
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	
	@Column(name = "portalid", length=50) 
	public String getPortalid() {
		return portalid;
	}
	public void setPortalid(String portalid) {
		this.portalid = portalid;
	}
	
	
}
