package com.goldenweb.fxpg.frame.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "STABLE_EXPERT1")
public class Expert implements java.io.Serializable {
	private String id;
	private String create_user;
	private Date create_date;
	private String update_user;
	private Date update_date;
	private String org_guid;
	private String status;
	private String swdb;
	private String xingming;
	private String xingbie;
	private String zhuanjianleibie;
	private String chushengnianyue;
	private String wenhuachengdu;
	private String zhichen;
	private String danrendaoshi;
	private String minzu;
	private String gongzuodanwei;
	private String youbian;
	private String tongxundizhi;
	private String bangongdianhua;
	private String jiatingdianhua;
	private String shouji;
	private String dianziyouxiang;
	private String zhengzhimianmao;
	private String xuekefenlei;
	private String linshiziduan1;
	private String linshiziduan2;
	private String linshiziduan3;
	private String beizhu;
	private String chuchishengbujiyishangxiangmu;
	private String daibiaolunwenzhuzuojifabiaoqingkuang;
	private String huojiangchengguo;
	private String linshiziduan4;
	private String linshiziduan5;
	private String danweizhiwu;
	private String yanjiulingyu;
	private String biyeyuanxiaojizhuanye;
	private String zuizhongxueli;
	private String chuanzhenhaoma;
	private String jianyaogongzuojingli;
	private String zhuyaoyanjiufangxiang;
	private String suozaidanweiyijian;
	private String zhuguanbumenyjian;
	private String fujianbianma;
	private String danwei;//单位
	private String zhiwu;//职务
	private String nianling;//年龄
	
	
	public Expert(){
		
	}
	
	
	


	public Expert(String id, String create_user, Date create_date,
			String update_user, Date update_date, String org_guid,
			String status, String swdb, String xingming, String xingbie,
			String zhuanjianleibie, String chushengnianyue,
			String wenhuachengdu, String zhichen, String danrendaoshi,
			String minzu, String gongzuodanwei, String youbian,
			String tongxundizhi, String bangongdianhua, String jiatingdianhua,
			String shouji, String dianziyouxiang, String zhengzhimianmao,
			String xuekefenlei, String linshiziduan1, String linshiziduan2,
			String linshiziduan3, String beizhu,
			String chuchishengbujiyishangxiangmu,
			String daibiaolunwenzhuzuojifabiaoqingkuang,
			String huojiangchengguo, String linshiziduan4,
			String linshiziduan5, String danweizhiwu, String yanjiulingyu,
			String biyeyuanxiaojizhuanye, String zuizhongxueli,
			String chuanzhenhaoma, String jianyaogongzuojingli,
			String zhuyaoyanjiufangxiang, String suozaidanweiyijian,
			String zhuguanbumenyjian, String fujianbianma, String danwei,
			String zhiwu, String nianling) {
		super();
		this.id = id;
		this.create_user = create_user;
		this.create_date = create_date;
		this.update_user = update_user;
		this.update_date = update_date;
		this.org_guid = org_guid;
		this.status = status;
		this.swdb = swdb;
		this.xingming = xingming;
		this.xingbie = xingbie;
		this.zhuanjianleibie = zhuanjianleibie;
		this.chushengnianyue = chushengnianyue;
		this.wenhuachengdu = wenhuachengdu;
		this.zhichen = zhichen;
		this.danrendaoshi = danrendaoshi;
		this.minzu = minzu;
		this.gongzuodanwei = gongzuodanwei;
		this.youbian = youbian;
		this.tongxundizhi = tongxundizhi;
		this.bangongdianhua = bangongdianhua;
		this.jiatingdianhua = jiatingdianhua;
		this.shouji = shouji;
		this.dianziyouxiang = dianziyouxiang;
		this.zhengzhimianmao = zhengzhimianmao;
		this.xuekefenlei = xuekefenlei;
		this.linshiziduan1 = linshiziduan1;
		this.linshiziduan2 = linshiziduan2;
		this.linshiziduan3 = linshiziduan3;
		this.beizhu = beizhu;
		this.chuchishengbujiyishangxiangmu = chuchishengbujiyishangxiangmu;
		this.daibiaolunwenzhuzuojifabiaoqingkuang = daibiaolunwenzhuzuojifabiaoqingkuang;
		this.huojiangchengguo = huojiangchengguo;
		this.linshiziduan4 = linshiziduan4;
		this.linshiziduan5 = linshiziduan5;
		this.danweizhiwu = danweizhiwu;
		this.yanjiulingyu = yanjiulingyu;
		this.biyeyuanxiaojizhuanye = biyeyuanxiaojizhuanye;
		this.zuizhongxueli = zuizhongxueli;
		this.chuanzhenhaoma = chuanzhenhaoma;
		this.jianyaogongzuojingli = jianyaogongzuojingli;
		this.zhuyaoyanjiufangxiang = zhuyaoyanjiufangxiang;
		this.suozaidanweiyijian = suozaidanweiyijian;
		this.zhuguanbumenyjian = zhuguanbumenyjian;
		this.fujianbianma = fujianbianma;
		this.danwei = danwei;
		this.zhiwu = zhiwu;
		this.nianling = nianling;
	}





	@Id
	@Column(name = "stable_expert1_guid", nullable = false, length=50)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "create_user", length=40)
	public String getCreate_user() {
		return create_user;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
	@Column(name = "update_user", length=40)
	public String getUpdate_user() {
		return update_user;
	}
	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 26)
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date", length = 26)
	public Date getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}
	@Column(name = "org_guid", length=40)
	public String getOrg_guid() {
		return org_guid;
	}
	public void setOrg_guid(String org_guid) {
		this.org_guid = org_guid;
	}
	@Column(name = "status", length=100)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name = "swdb", length=10)
	public String getSwdb() {
		return swdb;
	}
	public void setSwdb(String swdb) {
		this.swdb = swdb;
	}
	@Column(name = "xingming", length=30)
	public String getXingming() {
		return xingming;
	}
	public void setXingming(String xingming) {
		this.xingming = xingming;
	}
	@Column(name = "xingbie", length=20)
	public String getXingbie() {
		return xingbie;
	}
	public void setXingbie(String xingbie) {
		this.xingbie = xingbie;
	}
	@Column(name = "zhuanjianleibie", length=50)
	public String getZhuanjianleibie() {
		return zhuanjianleibie;
	}
	public void setZhuanjianleibie(String zhuanjianleibie) {
		this.zhuanjianleibie = zhuanjianleibie;
	}
	@Column(name = "chushengnianyue", length = 50)
	public String getChushengnianyue() {
		return chushengnianyue;
	}
	public void setChushengnianyue(String chushengnianyue) {
		this.chushengnianyue = chushengnianyue;
	}
	@Column(name = "wenhuachengdu", length=30)
	public String getWenhuachengdu() {
		return wenhuachengdu;
	}
	public void setWenhuachengdu(String wenhuachengdu) {
		this.wenhuachengdu = wenhuachengdu;
	}
	@Column(name = "zhichen", length=50)
	public String getZhichen() {
		return zhichen;
	}
	public void setZhichen(String zhichen) {
		this.zhichen = zhichen;
	}
	@Column(name = "danrendaoshi", length=50)
	public String getDanrendaoshi() {
		return danrendaoshi;
	}
	public void setDanrendaoshi(String danrendaoshi) {
		this.danrendaoshi = danrendaoshi;
	}
	@Column(name = "minzu", length=50)
	public String getMinzu() {
		return minzu;
	}
	public void setMinzu(String minzu) {
		this.minzu = minzu;
	}
	@Column(name = "gongzuodanwei", length=100)
	public String getGongzuodanwei() {
		return gongzuodanwei;
	}
	public void setGongzuodanwei(String gongzuodanwei) {
		this.gongzuodanwei = gongzuodanwei;
	}
	@Column(name = "youbian", length=10)
	public String getYoubian() {
		return youbian;
	}
	public void setYoubian(String youbian) {
		this.youbian = youbian;
	}
	@Column(name = "tongxundizhi", length=100)
	public String getTongxundizhi() {
		return tongxundizhi;
	}
	public void setTongxundizhi(String tongxundizhi) {
		this.tongxundizhi = tongxundizhi;
	}
	@Column(name = "bangongdianhua", length=50)
	public String getBangongdianhua() {
		return bangongdianhua;
	}
	public void setBangongdianhua(String bangongdianhua) {
		this.bangongdianhua = bangongdianhua;
	}
	@Column(name = "jiatingdianhua", length=50)
	public String getJiatingdianhua() {
		return jiatingdianhua;
	}
	public void setJiatingdianhua(String jiatingdianhua) {
		this.jiatingdianhua = jiatingdianhua;
	}
	@Column(name = "shouji", length=50)
	public String getShouji() {
		return shouji;
	}
	public void setShouji(String shouji) {
		this.shouji = shouji;
	}
	@Column(name = "dianziyouxiang", length=50)
	public String getDianziyouxiang() {
		return dianziyouxiang;
	}
	public void setDianziyouxiang(String dianziyouxiang) {
		this.dianziyouxiang = dianziyouxiang;
	}
	@Column(name = "zhengzhimianmao", length=50)
	public String getZhengzhimianmao() {
		return zhengzhimianmao;
	}
	public void setZhengzhimianmao(String zhengzhimianmao) {
		this.zhengzhimianmao = zhengzhimianmao;
	}
	@Column(name = "xuekefenlei", length=50)
	public String getXuekefenlei() {
		return xuekefenlei;
	}
	public void setXuekefenlei(String xuekefenlei) {
		this.xuekefenlei = xuekefenlei;
	}
	@Column(name = "linshiziduan1", length=50)
	public String getLinshiziduan1() {
		return linshiziduan1;
	}
	public void setLinshiziduan1(String linshiziduan1) {
		this.linshiziduan1 = linshiziduan1;
	}
	@Column(name = "linshiziduan2", length=50)
	public String getLinshiziduan2() {
		return linshiziduan2;
	}
	public void setLinshiziduan2(String linshiziduan2) {
		this.linshiziduan2 = linshiziduan2;
	}
	@Column(name = "linshiziduan3", length=50)
	public String getLinshiziduan3() {
		return linshiziduan3;
	}
	public void setLinshiziduan3(String linshiziduan3) {
		this.linshiziduan3 = linshiziduan3;
	}

	@Lob 
	@Type(type = "org.hibernate.type.StringClobType") 
	@Column(name="beizhu", columnDefinition="CLOB", nullable=true)
	public String getBeizhu() {
		return beizhu;
	}
	public void setBeizhu(String beizhu) {
		this.beizhu = beizhu;
	}
	@Lob 
	@Type(type = "org.hibernate.type.StringClobType") 
	@Column(name="chuchishengbujiyishangxiangmu", columnDefinition="CLOB", nullable=true)
	public String getChuchishengbujiyishangxiangmu() {
		return chuchishengbujiyishangxiangmu;
	}
	public void setChuchishengbujiyishangxiangmu(
			String chuchishengbujiyishangxiangmu) {
		this.chuchishengbujiyishangxiangmu = chuchishengbujiyishangxiangmu;
	}
	@Lob 
	@Type(type = "org.hibernate.type.StringClobType") 
	@Column(name="daibiaolunwenzhuzuojifabiaoqingkuang", columnDefinition="CLOB", nullable=true)
	public String getDaibiaolunwenzhuzuojifabiaoqingkuang() {
		return daibiaolunwenzhuzuojifabiaoqingkuang;
	}
	public void setDaibiaolunwenzhuzuojifabiaoqingkuang(
			String daibiaolunwenzhuzuojifabiaoqingkuang) {
		this.daibiaolunwenzhuzuojifabiaoqingkuang = daibiaolunwenzhuzuojifabiaoqingkuang;
	}
	@Lob 
	@Type(type = "org.hibernate.type.StringClobType") 
	@Column(name="huojiangchengguo", columnDefinition="CLOB", nullable=true)
	public String getHuojiangchengguo() {
		return huojiangchengguo;
	}
	public void setHuojiangchengguo(String huojiangchengguo) {
		this.huojiangchengguo = huojiangchengguo;
	}
	@Column(name = "linshiziduan4", length=50)
	public String getLinshiziduan4() {
		return linshiziduan4;
	}
	public void setLinshiziduan4(String linshiziduan4) {
		this.linshiziduan4 = linshiziduan4;
	}
	@Column(name = "linshiziduan5", length=50)
	public String getLinshiziduan5() {
		return linshiziduan5;
	}
	public void setLinshiziduan5(String linshiziduan5) {
		this.linshiziduan5 = linshiziduan5;
	}
	@Column(name = "danweizhiwu", length=500)
	public String getDanweizhiwu() {
		return danweizhiwu;
	}
	public void setDanweizhiwu(String danweizhiwu) {
		this.danweizhiwu = danweizhiwu;
	}
	@Column(name = "yanjiulingyu", length=1000)
	public String getYanjiulingyu() {
		return yanjiulingyu;
	}
	public void setYanjiulingyu(String yanjiulingyu) {
		this.yanjiulingyu = yanjiulingyu;
	}
	@Column(name = "biyeyuanxiaojizhuanye", length=500)
	public String getBiyeyuanxiaojizhuanye() {
		return biyeyuanxiaojizhuanye;
	}
	public void setBiyeyuanxiaojizhuanye(String biyeyuanxiaojizhuanye) {
		this.biyeyuanxiaojizhuanye = biyeyuanxiaojizhuanye;
	}
	@Column(name = "zuizhongxueli", length=100)
	public String getZuizhongxueli() {
		return zuizhongxueli;
	}
	public void setZuizhongxueli(String zuizhongxueli) {
		this.zuizhongxueli = zuizhongxueli;
	}
	
	@Column(name = "chuanzhenhaoma", length=20)
	public String getChuanzhenhaoma() {
		return chuanzhenhaoma;
	}
	@Column(name = "fujianbianma", length=50)
	public String getFujianbianma() {
		return fujianbianma;
	}
	public void setFujianbianma(String fujianbianma) {
		this.fujianbianma = fujianbianma;
	}
	public void setChuanzhenhaoma(String chuanzhenhaoma) {
		this.chuanzhenhaoma = chuanzhenhaoma;
	}
	@Lob 
	@Type(type = "org.hibernate.type.StringClobType") 
	@Column(name="jianyaogongzuojingli", columnDefinition="CLOB", nullable=true)
	public String getJianyaogongzuojingli() {
		return jianyaogongzuojingli;
	}
	public void setJianyaogongzuojingli(String jianyaogongzuojingli) {
		this.jianyaogongzuojingli = jianyaogongzuojingli;
	}
	@Lob 
	@Type(type = "org.hibernate.type.StringClobType") 
	@Column(name="zhuyaoyanjiufangxiang", columnDefinition="CLOB", nullable=true)
	public String getZhuyaoyanjiufangxiang() {
		return zhuyaoyanjiufangxiang;
	}
	public void setZhuyaoyanjiufangxiang(String zhuyaoyanjiufangxiang) {
		this.zhuyaoyanjiufangxiang = zhuyaoyanjiufangxiang;
	}
	@Lob 
	@Type(type = "org.hibernate.type.StringClobType") 
	@Column(name="suozaidanweiyijian", columnDefinition="CLOB", nullable=true)
	public String getSuozaidanweiyijian() {
		return suozaidanweiyijian;
	}
	public void setSuozaidanweiyijian(String suozaidanweiyijian) {
		this.suozaidanweiyijian = suozaidanweiyijian;
	}
	@Lob 
	@Type(type = "org.hibernate.type.StringClobType") 
	@Column(name="zhuguanbumenyjian", columnDefinition="CLOB", nullable=true)
	public String getZhuguanbumenyjian() {
		return zhuguanbumenyjian;
	}
	public void setZhuguanbumenyjian(String zhuguanbumenyjian) {
		this.zhuguanbumenyjian = zhuguanbumenyjian;
	}

	@Column(name = "danwei", length=500)
	public String getDanwei() {
		return danwei;
	}


	public void setDanwei(String danwei) {
		this.danwei = danwei;
	}

	@Column(name = "zhiwu", length=500)
	public String getZhiwu() {
		return zhiwu;
	}


	public void setZhiwu(String zhiwu) {
		this.zhiwu = zhiwu;
	}

	@Column(name = "nianling", length=50)
	public String getNianling() {
		return nianling;
	}


	public void setNianling(String nianling) {
		this.nianling = nianling;
	}
	
	
	
	
	
	
}
