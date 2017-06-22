package com.goldenweb.fxpg.weboffice.pojo;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**  
 * @Description: TODO(用一句话描述该文件做什么)
 * @author ljn  
 * @date 2014-3-10 下午4:37:03
 */
@Entity
@Table(name = "WEBOFFICE_SIGNATURE")
public class Signature implements java.io.Serializable {
	private Integer SignatureId;
	private String UserName;
	private String Password;
	private String MarkName;
	private String MarkType;
	private byte[] MarkBody;
	private String MarkPath;
	private Integer MarkSize;
	private Date MarkDate;
		
	@Id
    @Column(name = "SignatureId", nullable = false, length=36) 
//	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SQ_SIGNATURE")   
//	@SequenceGenerator(name="SQ_SIGNATURE", allocationSize=1, initialValue=1,sequenceName="SQ_SIGNATURE")  
	public Integer getSignatureId() {
		return SignatureId;
	}
	public void setSignatureId(Integer signatureId) {
		SignatureId = signatureId;
	}
	
	@Column(name = "UserName", length=64)
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	
	@Column(name = "Password", length=64)
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	
	@Column(name = "MarkName", length=254)
	public String getMarkName() {
		return MarkName;
	}
	public void setMarkName(String markName) {
		MarkName = markName;
	}
	
	@Column(name = "MarkType", length=50)
	public String getMarkType() {
		return MarkType;
	}
	public void setMarkType(String markType) {
		MarkType = markType;
	}
	
	@Lob  
	@Basic(fetch=FetchType.LAZY)
	@Column(name = "MarkBody", columnDefinition = "BLOB") 
	public byte[] getMarkBody() {
		return MarkBody;
	}
	public void setMarkBody(byte[] markBody) {
		MarkBody = markBody;
	}
	
	@Column(name = "MarkPath", length=50)
	public String getMarkPath() {
		return MarkPath;
	}
	public void setMarkPath(String markPath) {
		MarkPath = markPath;
	}
	
	@Column(name = "MarkSize")
	public Integer getMarkSize() {
		return MarkSize;
	}
	public void setMarkSize(Integer markSize) {
		MarkSize = markSize;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MarkDate", length=7)
	public Date getMarkDate() {
		return MarkDate;
	}
	public void setMarkDate(Date markDate) {
		MarkDate = markDate;
	}
	
}
