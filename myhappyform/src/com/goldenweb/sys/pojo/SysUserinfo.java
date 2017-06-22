package com.goldenweb.sys.pojo;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * SysUserinfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SYS_USERINFO")
public class SysUserinfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private String loginname;
	private String pwd;
	private String username;
	private Integer mainOrgid;
	private String mainOrgname;
	private Integer isWebuser=0;
	private Integer isAdmin=0;
	private Integer isDel=0;
	private Date createTime;
	private Integer createUserid;
	private String img;
	private Integer num;
	private String email;
	private String phone;
	private String address;
	private String remark;
	private Integer updateUserid;
	private Date updateTime = new Date();
	private String isProcess; // y/n
	private String deptcode;
	private String deptname;
	private String thirdorgid;
	private String thirdorgname;
	
	private List<Object[]> userRoleList;

	// Constructors

	

	

	/** default constructor */
	public SysUserinfo() {
	}

	/** minimal constructor */
	public SysUserinfo(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public SysUserinfo(Integer id, String loginname, String pwd,
			String username, Integer mainOrgid,String mainOrgname,Integer isWebuser,
			Integer isAdmin, Integer isDel, Date createTime,Integer createUserid,
			String img,Integer num,String email,String phone,String address,
			String remark,Integer updateUserid,Date updateTime,String isProcess,
			String deptname,String deptcode) {
			this.id = id;
			this.loginname = loginname;
			this.pwd = pwd;
			this.username = username;
			this.mainOrgid = mainOrgid;
			this.mainOrgname =mainOrgname;
			this.isWebuser = isWebuser;
			this.isAdmin = isAdmin;
			this.isDel = isDel;
			this.createTime = createTime;
			this.createUserid = createUserid;
			this.img = img;
			this.num = num;
			this.email = email;
			this.phone = phone;
			this.address = address;
			this.remark =remark;
			this.updateUserid = updateUserid;
			this.updateTime = updateTime;
			this.isProcess = isProcess;
			this.deptname=deptname;
			this.deptcode=deptcode;
			this.thirdorgid = thirdorgid;
			this.thirdorgname = thirdorgname;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name = "id",  nullable = false, precision = 22, scale = 0)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "Loginname", length = 100)
	public String getLoginname() {
		return this.loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	@Column(name = "Pwd", length = 100)
	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Column(name = "Username", length = 100)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "Main_Orgid", precision = 22, scale = 0)
	public Integer getMainOrgid() {
		return this.mainOrgid;
	}

	public void setMainOrgid(Integer mainOrgid) {
		this.mainOrgid = mainOrgid;
	}

	@Column(name = "Is_webuser", columnDefinition="number(10,0) default 0 ")
	public Integer getIsWebuser() {
		return this.isWebuser;
	}

	public void setIsWebuser(Integer isWebuser) {		
		this.isWebuser = isWebuser;
	}

	@Column(name = "Is_Admin", columnDefinition="number(10,0) default 0 ")
	public Integer getIsAdmin() {
		return this.isAdmin;
	}

	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}

	@Column(name = "Is_Del", columnDefinition="number(10,0) default 0 ")
	public Integer getIsDel() {
		return this.isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Create_Time", length=7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Column(name = "create_userid", precision = 22, scale = 0)
	public Integer getCreateUserid() {
		return this.createUserid;
	}

	public void setCreateUserid(Integer createUserid) {
		this.createUserid = createUserid;
	}

	@Column(name = "Img", length = 100)
	public String getImg() {
		return this.img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	
	
	@Column(name = "MAIN_ORGNAME", length = 100)
	public String getMainOrgname() {
		return mainOrgname;
	}
	public void setMainOrgname(String mainOrgname) {
		this.mainOrgname = mainOrgname;
	}
	
	@Column(name = "NUM", precision = 22, scale = 0)
	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {		
		this.num = num;
	}
	
	@Column(name = "email", length = 50)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name = "phone", length = 50)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Column(name = "address", length = 200)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Column(name = "remark", length = 2000)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "update_userid", precision = 22, scale = 0)
	public Integer getUpdateUserid() {
		return this.updateUserid;
	}

	public void setUpdateUserid(Integer updateUserid) {
		this.updateUserid = updateUserid;
	}	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATE_Time", length=7)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@Column(name = "is_process", length = 10)
	public String getIsProcess() {
		return this.isProcess;
	}

	public void setIsProcess(String isProcess) {
		this.isProcess = isProcess;
	}
	
	@Column(name = "deptcode", length = 50)
	public String getDeptcode() {
		return deptcode;
	}
	public void setDeptcode(String deptcode) {
		this.deptcode = deptcode;
	}

	@Column(name = "deptname", length = 50)
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	
	
	@Column(name = "thirdorgid", length = 50)
	public String getThirdorgid() {
		return thirdorgid;
	}
	public void setThirdorgid(String thirdorgid) {
		this.thirdorgid = thirdorgid;
	}

	@Column(name = "thirdorgname", length = 100)
	public String getThirdorgname() {
		return thirdorgname;
	}
	public void setThirdorgname(String thirdorgname) {
		this.thirdorgname = thirdorgname;
	}

	/***************************************************************/
	
	@Transient
	public List<Object[]> getUserRoleList() {
		return userRoleList;
	}
	public void setUserRoleList(List<Object[]> userRoleList) {
		this.userRoleList = userRoleList;
	}
	
	
	private List<Object[]> sameDeptList;       // 数组：0--部门数据主键；1--部门名称；2--部门编码	   
	private List<Object[]> sameDeptListNoSelf;       // 数组：0--部门数据主键；1--部门名称；2--部门编码  (不包含自身所在部门)
	private List<Object[]>  childOrgList;  // 数组：0--机构id ； 1--机构名称；2--机构编码
	private List<Object[]>  childDeptList;   //当前下级机构的同级部门   数组：0--部门数据主键；1--部门名称；2--部门编码	

	
	@Transient
	public List<Object[]> getSameDeptList() {
		return sameDeptList;
	}
	public void setSameDeptList(List<Object[]> sameDeptList) {
		this.sameDeptList = sameDeptList;
	}

	@Transient
	public List<Object[]> getSameDeptListNoSelf() {
		return sameDeptListNoSelf;
	}
	public void setSameDeptListNoSelf(List<Object[]> sameDeptListNoSelf) {
		this.sameDeptListNoSelf = sameDeptListNoSelf;
	}

	@Transient
	public List<Object[]> getChildOrgList() {
		return childOrgList;
	}
	public void setChildOrgList(List<Object[]> childOrgList) {
		this.childOrgList = childOrgList;
	}
	
	@Transient
	public List<Object[]> getChildDeptList() {
		return childDeptList;
	}
	public void setChildDeptList(List<Object[]> childDeptList) {
		this.childDeptList = childDeptList;
	}
	
	private String roles;   //角色，以“,”隔开
	@Transient
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	
	private String  mainOrgcode;  //机构code  “mc.....”
	@Transient
	public String getMainOrgcode() {
		return mainOrgcode;
	}
	public void setMainOrgcode(String mainOrgcode) {
		this.mainOrgcode = mainOrgcode;
	}
	
	private String currentLoginIp;
	@Transient
	public String getCurrentLoginIp() {
		return currentLoginIp;
	}

	public void setCurrentLoginIp(String currentLoginIp) {
		this.currentLoginIp = currentLoginIp;
	}
}