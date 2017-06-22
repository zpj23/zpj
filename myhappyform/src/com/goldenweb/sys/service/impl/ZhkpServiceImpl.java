package com.goldenweb.sys.service.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.goldenweb.fxpg.frame.service.impl.GenericManagerImpl;
import com.goldenweb.sys.dao.ZhkpDao;
import com.goldenweb.sys.pojo.SysKhfz;
import com.goldenweb.sys.service.ZhkpService;

@Service
@Component("zhkpService")
public class ZhkpServiceImpl extends GenericManagerImpl<String, Serializable> implements ZhkpService{
	@Autowired
	private ZhkpDao zhkpDao;
	@Override
	public void saveConfig(SysKhfz khfz){
		zhkpDao.saveConfig(khfz);
	}
	@Override
	public SysKhfz findById(String id){
		return zhkpDao.findById(id);
	}
	@Override
	public String statisNzpmColumn(Map params){
		StringBuffer str = new StringBuffer("");
		List<Object[]> citylist =(List<Object[]>)params.get("cityList");
		List<Object[]> allList =(List<Object[]>)params.get("allList");
		List<Object[]> list1=(List<Object[]>)params.get("list1");
		List<Object[]> list2=(List<Object[]>)params.get("list2");
		List<Object[]> list3=(List<Object[]>)params.get("list3");
		List<Object[]> listItemSB=(List<Object[]>)params.get("listItemSB");
		List<Object[]> listItemBJ=(List<Object[]>)params.get("listItemBJ");
		List<Object[]> listDCDBFK=(List<Object[]>)params.get("listDCDBFK");
		List<Object[]> listDCDBGD=(List<Object[]>)params.get("listDCDBGD");
		List<Object[]> listWEKB=(List<Object[]>)params.get("listWEKB");
		List<Object[]> listGW=(List<Object[]>)params.get("listGW");
		
		SysKhfz khfz=(SysKhfz)params.get("scoreRatio");
		
		Map allMap=changeObj(allList);
		Map map1=changeObj(list1);
		Map map2=changeObj(list2);
		Map map3=changeObj(list3);
		Map itemsb=changeObj(listItemSB);
		Map itembj=changeObj(listItemBJ);
		Map dcdbfk=changeObj(listDCDBFK);
		Map dcdbgd=changeObj(listDCDBGD);
		Map wwkb=changeObj(listWEKB);
		Map gw=changeObj(listGW);
		//{"listnum":"5","sourcedata":[{"name":"实地督办","num":"37"},{"name":"批示问责","num":"25"},{"name":"约谈督办","num":"2"},{"name":"书面督办","num":"2"},{"name":"会议督办","num":"2"}]}
		str.append("{\"listnum\":\"").append(citylist.size()).append("\",\"sourcedata\":[");
		for(int i=0;i<citylist.size();i++){
			str.append("{\"name\":\"").append(citylist.get(i)[1]).append("\",\"num\":\"");
			//评估信息数量+ 评估信息完成数(批准、暂缓、不予批准)     +重大事项数量+重大决策质量+  督察督办数量+时效
			String code=(String)citylist.get(i)[2];
			int numb=0;
			numb=calculateScore(changeSToI(allMap.get(code)),(changeSToI(map1.get(code))+changeSToI(map2.get(code))+changeSToI(map3.get(code))),"pgxx",khfz)
				+calculateScore(changeSToI(itemsb.get(code)),changeSToI(itembj.get(code)),"zdjc",khfz)
				+calculateScore(changeSToI(dcdbfk.get(code)),changeSToI(dcdbgd.get(code)),"dcdb",khfz)
				+calculateScore(changeSToI(wwkb.get(code)),changeSToI(wwkb.get(code)),"wwkb",khfz)
				+calculateScore(changeSToI(gw.get(code)),changeSToI(gw.get(code)),"gw",khfz);
			str.append(numb).append("\"},");	        	 
		}
		str.delete(str.toString().length()-1, str.toString().length());
        str.append("]}");
		return str.toString();
	}
	public int changeSToI(Object str){
		try {
			int num=(Integer)str;
			return num;
		} catch (Exception e) {
			return 0;
		}
	}
	
	/**
	 * 计算得分
	 * @Title calculateScore
	 * @return
	 * @author zpj
	 * @time 2015-10-10 下午1:42:22
	 */
	public int calculateScore(int num1,int num2,String flag,SysKhfz scoreRatio){
		int scoreNum=0;
		if(flag.equalsIgnoreCase("pgxx")){
			scoreNum=scoreRatio.getPgxx_zf();
			if(num1>=scoreRatio.getPgxx_total()){
				
			}else{
				scoreNum=scoreNum-(scoreRatio.getPgxx_total()-num1)*(scoreRatio.getPgxx_total_pre());
			}
			if(num2>scoreRatio.getPgxx_complete()){
				
			}else{
				scoreNum=scoreNum-(scoreRatio.getPgxx_complete()-num2)*(scoreRatio.getPgxx_complete_pre());
			}
		}else if(flag.equalsIgnoreCase("zdjc")){
			scoreNum=scoreRatio.getZdjc_zf();
			if(num1>=scoreRatio.getZdjc_total()){
				
			}else{
				scoreNum=scoreNum-(scoreRatio.getZdjc_total()-num1)*(scoreRatio.getZdjc_total_pre());
			}
			if(num2>=scoreRatio.getZdjc_quality()){
				
			}else{
				scoreNum=scoreNum-(scoreRatio.getZdjc_quality()-num2)*(scoreRatio.getZdjc_quality_pre());
			}
		}else if(flag.equalsIgnoreCase("dcdb")){
			scoreNum=scoreRatio.getDcdb_zf();
			if(num1>=scoreRatio.getDcdb_total()){
				
			}else{
				scoreNum=scoreNum-(scoreRatio.getDcdb_total()-num1)*(scoreRatio.getDcdb_total_pre());
			}
			if(num2>=scoreRatio.getDcdb_time()){
				
			}else{
				scoreNum=scoreNum-(scoreRatio.getDcdb_time()-num2)*(scoreRatio.getDcdb_time_pre());
			}
		}else if(flag.equalsIgnoreCase("wwkb")){
			scoreNum=scoreRatio.getWwkb_zf();
			if(num1>=scoreRatio.getWwkb_total()){
				
			}else{
				scoreNum=scoreNum-(scoreRatio.getWwkb_total()-num1)*(scoreRatio.getWwkb_total_pre());
			}
		}else if(flag.equalsIgnoreCase("gw")){
			scoreNum=scoreRatio.getGw_zf();
			if(num1>=scoreRatio.getGw_total()){
				
			}else{
				scoreNum=scoreNum-(scoreRatio.getGw_total()-num1)*(scoreRatio.getGw_total_pre());
			}
		}
		return scoreNum;
	}
	/**
	 * list<object[]>转换成Map对象并且去掉重复的
	 * @Title changeObj
	 * @param tempList
	 * @return
	 * @author zpj
	 * @time 2015-10-10 下午1:41:19
	 */
	public Map changeObj(List<Object[]> tempList){
		Map obj =new HashMap();
		for(int m=0;m<tempList.size();m++){
			String tempStr=(String)tempList.get(m)[0];
			if(tempStr==null){
				continue;
			}
			String code=tempStr.split("-")[0];
			if(obj.get(code)!=null){
				obj.put(code,((Integer)obj.get(code))+((BigInteger)tempList.get(m)[1]).intValue());
			}else{
				obj.put(code,((BigInteger)tempList.get(m)[1]).intValue());
			}
		}
		return obj;
	}
}
