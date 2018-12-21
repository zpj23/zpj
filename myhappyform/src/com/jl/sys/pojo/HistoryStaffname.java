package com.jl.sys.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


    /**
    * @ClassName: HistoryStaffname
    * @Description: TODO(录入的历史人员姓名表)
    * @author zpj
    * @date 2018年12月21日
    *
    */
    
@Entity
@Table(name = "jl_history_staffname")
public class HistoryStaffname implements java.io.Serializable{
	private String id;
	private int  userId; 
	private String staffName;
	
	@Id
	@Column(name = "id", nullable = false, length=50)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	
}
