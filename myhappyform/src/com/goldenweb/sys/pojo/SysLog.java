package com.goldenweb.sys.pojo;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "SYS_LOG")
	public class SysLog implements java.io.Serializable {

	    // Fields

	    private String id;
	    private String operateType;
	    private String operator;
	    private Integer operatorId;
	    private Date operateDate = new Date();
	    private String operateDescription;

	    // Constructors

	    /** default constructor */
	    public SysLog() {
	    }

	    /** minimal constructor */
	    public SysLog(String id) {
		this.id = id;
	    }

	    /** full constructor */
	    public SysLog(String id, String operateType, String operator,
	    		Integer operatorId, Date operateDate, String operateDescription) {
		this.id = id;
		this.operateType = operateType;
		this.operator = operator;
		this.operatorId = operatorId;
		this.operateDate = operateDate;
		this.operateDescription = operateDescription;
	    }

	    // Property accessors
	    @Id
	    @Column(name = "ID", nullable = false, length=50)
	    public String getId() {
		return this.id;
	    }

	    public void setId(String id) {
		this.id = id;
	    }

	    @Column(name = "operate_type", length = 50)
	    public String getOperateType() {
		return this.operateType;
	    }

	    public void setOperateType(String operateType) {
		this.operateType = operateType;
	    }

	    @Column(name = "operator", length = 50)
	    public String getOperator() {
		return this.operator;
	    }

	    public void setOperator(String operator) {
		this.operator = operator;
	    }

	    @Column(name = "operator_id", precision = 22, scale = 0)
	    public Integer getOperatorId() {
		return this.operatorId;
	    }

	    public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	    }

	    @Temporal(TemporalType.TIMESTAMP)
		@Column(name = "OPERATE_DATE", length = 7)
	    public Date getOperateDate() {
		return this.operateDate;
	    }

	    public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	    }

	    @Column(name = "operate_description", length = 500)
	    public String getOperateDescription() {
		return this.operateDescription;
	    }

	    public void setOperateDescription(String operateDescription) {
		this.operateDescription = operateDescription;
	    }

	}
