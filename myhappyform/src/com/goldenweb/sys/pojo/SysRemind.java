package com.goldenweb.sys.pojo;

import java.util.Date;

public class SysRemind implements java.io.Serializable{

	// Fields	
	private String id;
	private String remindTitle;
	private String remindUrl;
	private String remindType;
	private String remindDataid;
	private String remindUserid;
	private String startUsername;  // 提醒的发起人
	private Date remindTime ;
	private String showtime;
	
	 // Constructors

    /** default constructor */
    public SysRemind() {
    }

    /** minimal constructor */
    public SysRemind(String id) {
	this.id = id;
    }

    /** full constructor */
    public SysRemind(String id,String remindTitle,String remindUrl,String startUsername,
    		String remindType,String remindUserid,Date remindTime,String remindDataid) {
    	this.id = id;
    	this.remindTitle =remindTitle;
    	this.remindType = remindType;
    	this.remindUrl =remindUrl;
    	this.remindUserid = remindUserid;
    	this.startUsername = startUsername;
    	this.remindTime = remindTime;
    	this.remindDataid = remindDataid;
        }
    
    // Property accessors

    //@Id
    //@Column(name = "ID", nullable = false, length=50)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	//@Column(name = "remind_title", length = 100)
	public String getRemindTitle() {
		return remindTitle;
	}
	public void setRemindTitle(String remindTitle) {
		this.remindTitle = remindTitle;
	}


	//@Column(name = "remind_url", length = 100)
	public String getRemindUrl() {
		return remindUrl;
	}
	public void setRemindUrl(String remindUrl) {
		this.remindUrl = remindUrl;
	}

	//@Column(name = "remind_type", length = 50)
	public String getRemindType() {
		return remindType;
	}
	public void setRemindType(String remindType) {
		this.remindType = remindType;
	}

	//@Column(name = "remind_userid", length = 50)
	public String getRemindUserid() {
		return remindUserid;
	}
	public void setRemindUserid(String remindUserid) {
		this.remindUserid = remindUserid;
	}

	//@Temporal(TemporalType.TIMESTAMP)
	//@Column(name = "OPERATE_DATE",length=7)
	public Date getRemindTime() {
		return remindTime;
	}
	public void setRemindTime(Date remindTime) {
		this.remindTime = remindTime;
	}

	//@Column(name = "start_username", length = 50)
	public String getStartUsername() {
		return startUsername;
	}
	public void setStartUsername(String startUsername) {
		this.startUsername = startUsername;
	}

	//@Column(name = "remind_dataid", length = 50)
	public String getRemindDataid() {
		return remindDataid;
	}
	public void setRemindDataid(String remindDataid) {
		this.remindDataid = remindDataid;
	}
    
    
	public String getShowtime() {
		return showtime;
	}
	public void setShowtime(String showtime) {
		this.showtime = showtime;
	}
    
}
