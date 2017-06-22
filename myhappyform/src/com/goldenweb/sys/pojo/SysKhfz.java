package com.goldenweb.sys.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SYS_KHFZ")
public class SysKhfz implements java.io.Serializable{
	private String id;//主键
	private int pgxx_zf;//风险评估总分
	private int pgxx_total;//风险评估数量
	private int pgxx_total_pre;//数量少扣的分值
	private int pgxx_complete;//完成量
	private int pgxx_complete_pre;//完成量少扣的分值
	private int zdjc_zf;//重大决策总分
	private int zdjc_total;//重大决策数量
	private int zdjc_total_pre;//重大决策数量少扣的分值
	private int zdjc_quality;//重大决策质量的数量
	private int zdjc_quality_pre;//重大决策质量少扣的分值
	private int dcdb_zf;//督察督办总分
	private int dcdb_total;//督查数量
	private int dcdb_total_pre;//督察督办数量少扣的分值
	private int dcdb_time;// 督察督办时效量
	private int dcdb_time_pre;//督察督办时效少扣的分值
	
	private int wwkb_zf;//维稳快报总分
	private int wwkb_total;//维稳快报数量
	private int wwkb_total_pre;//维稳快报少扣的分值
	
	private int gw_zf;//公文总分
	private int gw_total;//公文数量
	private int gw_total_pre;//公文数量少扣的分值
	
	
	@Id
	@Column(name = "id", nullable = false, length=50)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "pgxx_zf", length=10)
	public int getPgxx_zf() {
		return pgxx_zf;
	}
	public void setPgxx_zf(int pgxx_zf) {
		this.pgxx_zf = pgxx_zf;
	}
	
	@Column(name = "pgxx_total", length=10)
	public int getPgxx_total() {
		return pgxx_total;
	}
	public void setPgxx_total(int pgxx_total) {
		this.pgxx_total = pgxx_total;
	}
	@Column(name = "pgxx_total_pre", length=10)
	public int getPgxx_total_pre() {
		return pgxx_total_pre;
	}
	public void setPgxx_total_pre(int pgxx_total_pre) {
		this.pgxx_total_pre = pgxx_total_pre;
	}
	@Column(name = "pgxx_complete", length=10)
	public int getPgxx_complete() {
		return pgxx_complete;
	}
	public void setPgxx_complete(int pgxx_complete) {
		this.pgxx_complete = pgxx_complete;
	}
	@Column(name = "pgxx_complete_pre", length=10)
	public int getPgxx_complete_pre() {
		return pgxx_complete_pre;
	}
	public void setPgxx_complete_pre(int pgxx_complete_pre) {
		this.pgxx_complete_pre = pgxx_complete_pre;
	}
	@Column(name = "zdjc_zf", length=10)
	public int getZdjc_zf() {
		return zdjc_zf;
	}
	public void setZdjc_zf(int zdjc_zf) {
		this.zdjc_zf = zdjc_zf;
	}
	
	@Column(name = "zdjc_total", length=10)
	public int getZdjc_total() {
		return zdjc_total;
	}
	public void setZdjc_total(int zdjc_total) {
		this.zdjc_total = zdjc_total;
	}
	@Column(name = "zdjc_total_pre", length=10)
	public int getZdjc_total_pre() {
		return zdjc_total_pre;
	}
	public void setZdjc_total_pre(int zdjc_total_pre) {
		this.zdjc_total_pre = zdjc_total_pre;
	}
	@Column(name = "zdjc_quality", length=10)
	public int getZdjc_quality() {
		return zdjc_quality;
	}
	public void setZdjc_quality(int zdjc_quality) {
		this.zdjc_quality = zdjc_quality;
	}
	@Column(name = "zdjc_quality_pre", length=10)
	public int getZdjc_quality_pre() {
		return zdjc_quality_pre;
	}
	public void setZdjc_quality_pre(int zdjc_quality_pre) {
		this.zdjc_quality_pre = zdjc_quality_pre;
	}
	@Column(name = "dcdb_zf", length=10)
	public int getDcdb_zf() {
		return dcdb_zf;
	}
	public void setDcdb_zf(int dcdb_zf) {
		this.dcdb_zf = dcdb_zf;
	}
	@Column(name = "dcdb_total", length=10)
	public int getDcdb_total() {
		return dcdb_total;
	}
	public void setDcdb_total(int dcdb_total) {
		this.dcdb_total = dcdb_total;
	}
	@Column(name = "dcdb_total_pre", length=10)
	public int getDcdb_total_pre() {
		return dcdb_total_pre;
	}
	public void setDcdb_total_pre(int dcdb_total_pre) {
		this.dcdb_total_pre = dcdb_total_pre;
	}
	@Column(name = "dcdb_time", length=10)
	public int getDcdb_time() {
		return dcdb_time;
	}
	public void setDcdb_time(int dcdb_time) {
		this.dcdb_time = dcdb_time;
	}
	@Column(name = "dcdb_time_pre", length=10)
	public int getDcdb_time_pre() {
		return dcdb_time_pre;
	}
	public void setDcdb_time_pre(int dcdb_time_pre) {
		this.dcdb_time_pre = dcdb_time_pre;
	}
	@Column(name = "wwkb_zf", length=10)
	public int getWwkb_zf() {
		return wwkb_zf;
	}
	public void setWwkb_zf(int wwkb_zf) {
		this.wwkb_zf = wwkb_zf;
	}
	@Column(name = "wwkb_total", length=10)
	public int getWwkb_total() {
		return wwkb_total;
	}
	public void setWwkb_total(int wwkb_total) {
		this.wwkb_total = wwkb_total;
	}
	@Column(name = "wwkb_total_pre", length=10)
	public int getWwkb_total_pre() {
		return wwkb_total_pre;
	}
	public void setWwkb_total_pre(int wwkb_total_pre) {
		this.wwkb_total_pre = wwkb_total_pre;
	}
	@Column(name = "gw_zf", length=10)
	public int getGw_zf() {
		return gw_zf;
	}
	public void setGw_zf(int gw_zf) {
		this.gw_zf = gw_zf;
	}
	@Column(name = "gw_total", length=10)
	public int getGw_total() {
		return gw_total;
	}
	public void setGw_total(int gw_total) {
		this.gw_total = gw_total;
	}
	@Column(name = "gw_total_pre", length=10)
	public int getGw_total_pre() {
		return gw_total_pre;
	}
	public void setGw_total_pre(int gw_total_pre) {
		this.gw_total_pre = gw_total_pre;
	}
	
	
}
