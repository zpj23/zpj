package com.goldenweb.fxpg.weboffice.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**  
 * @Description: TODO(用一句话描述该文件做什么)
 * @author ljn  
 * @date 2014-3-10 下午4:20:43
 */
@Entity
@Table(name = "WEBOFFICE_DOCUMENT_SIGNATURE")
public class DocumentSignature implements java.io.Serializable {
	private Integer SignatureId;
	private String RecordId;
	private String MarkName;
	private String UserName;
	private Date DateTime;
	private String HostName;
	private String MarkGuid;
	
	@Id
    @Column(name = "SignatureId", nullable = false, length=36)
	public Integer getSignatureId() {
		return SignatureId;
	}
	public void setSignatureId(Integer signatureId) {
		SignatureId = signatureId;
	}
	
	@Column(name = "RecordId", length=50)
	public String getRecordId() {
		return RecordId;
	}
	public void setRecordId(String recordId) {
		RecordId = recordId;
	}
	
	@Column(name = "MarkName", length=64)
	public String getMarkName() {
		return MarkName;
	}
	public void setMarkName(String markName) {
		MarkName = markName;
	}
	
	@Column(name = "UserName", length=64)
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DateTime", length=7)
	public Date getDateTime() {
		return DateTime;
	}
	public void setDateTime(Date dateTime) {
		DateTime = dateTime;
	}
	
	@Column(name = "HostName", length=50)
	public String getHostName() {
		return HostName;
	}
	public void setHostName(String hostName) {
		HostName = hostName;
	}
	
	@Column(name = "MarkGuid", length=128)
	public String getMarkGuid() {
		return MarkGuid;
	}
	public void setMarkGuid(String markGuid) {
		MarkGuid = markGuid;
	}
}
