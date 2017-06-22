package com.goldenweb.sys.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.goldenweb.fxpg.cache.impl.UserMenuCache;
import com.goldenweb.fxpg.comm.BaseService;
import com.goldenweb.sys.dao.FunctionHibernate;
import com.goldenweb.sys.dao.FunctionRoleHibernate;
import com.goldenweb.sys.pojo.SysFunction;
import com.goldenweb.sys.pojo.SysRolefunction;
import com.goldenweb.sys.service.FunctionService;

@Service
@Component("functionService")
public class FunctionServiceImpl extends BaseService<SysFunction> implements FunctionService{

	@Autowired
	private FunctionHibernate functionDao;
	@Autowired
	private FunctionRoleHibernate functionRoleDao;
			
	/*****************************************************************************/
	/**
	 * 构建功能树
	 */
	@Override
	public String bulidFunctionTree() {		
		try{
			//查询功能list
			List<Object[]> list = functionDao.findFunction();

			StringBuffer str = new StringBuffer("");
			if (list != null && list.size() > 0) {
				str.append("[");
				// 次节点下的隐藏
				String sign = "";
				String ischeck = "";
				for (int i = 0; i < list.size(); i++) {
					sign = "click:\"getFunid('" + list.get(i)[0] + "')\" ";	
					str.append("{id:\"").append(list.get(i)[0]).append("\", pId:\"")
					.append(list.get(i)[2]).append("\", name:\"")
					.append(list.get(i)[1]).append("\" ,").append(sign)
					.append(", open : true },");
				}
			} else {
				return "0";
			}
			String s = str.toString();
			if (!s.equals("")) {
				s = s.substring(0, s.length() - 1) + "]";
			}   
			return s;
		}catch (Exception e) {
			return "[]";
		}
	}


    /**
     * 构建角色功能树
     * @param request
     * @param roleid
     * @return
     */
	@Override
	public String bulidRoleFunctionTree(HttpServletRequest request,String roleid) {

		try{
			//查询功能list
			List<Object[]> list = functionDao.findFunction();

			List<Object[]> selectedList = functionDao.findFunRole(roleid);
			String functionstr=",";
			if(selectedList!=null){
				for(int i=0;i<selectedList.size();i++){
					functionstr +=selectedList.get(i)[1]+",";
				}
			}			
			request.setAttribute("menuids", functionstr.substring(0,functionstr.length()-1));

			StringBuffer str = new StringBuffer("");
			if (list != null && list.size() > 0) {
				str.append("[");
				// 次节点下的隐藏
				String sign = "";
				String ischeck = "";
				for (int i = 0; i < list.size(); i++) {
					sign = "click:\"getFunid('" + list.get(i)[0] + "')\" ";	
					if(functionstr.indexOf((","+list.get(i)[0]+","))>-1){
						ischeck="checked:true";
					}else{
						ischeck="checked:false";
					}
					str.append("{id:\"").append(list.get(i)[0]).append("\", pId:\"")
					.append(list.get(i)[2]).append("\", name:\"")
					.append(list.get(i)[1]).append("\" ,").append(sign)
					.append(",").append(ischeck)
					.append(", open : true },");
				}
			} else {
				return "0";
			}
			String s = str.toString();
			if (!s.equals("")) {
				s = s.substring(0, s.length() - 1) + "]";
			}   
			return s;
		}catch (Exception e) {
			return "[]";
		}
	}


	/**
	 * 查询单个功能信息 by 主键
	 * @param id
	 * @return
	 */
	@Override
	public SysFunction showOneFunction(int id){				
		try {
			SysFunction fun = functionDao.getEntity(id);
			return fun;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 保存/编辑
	 * @param list
	 * @return
	 */
	@Override
	@MethodLog2(remark="编辑功能菜单信息",type="编辑")
	public Object saveFunction(List<SysFunction> list){
		try {
			SysFunction fun = list.get(0);

			int itemid;
			String operateFlag="";
			if(fun.getId()==null){
				//新增
				itemid = functionDao.saveFunction(fun);
				//新增子功能项(1-增，2-改，3-删，4-查，5-看)
				String str = fun.getOperateType();
				if(str!=null&&!"".equals(str)){
					//当选择了子项才执行
					SysFunction sf;
					String title= fun.getTitle();
					if(str.indexOf(",1,")>-1){
						sf=new SysFunction();
						sf.setTitle(title+"_新增");
						sf.setParentFunid(itemid);
						sf.setOperateType("1");
						functionDao.save(sf);
					}
					if(str.indexOf(",2,")>-1){
						sf=new SysFunction();
						sf.setTitle(title+"_修改");
						sf.setParentFunid(itemid);
						sf.setOperateType("2");
						functionDao.save(sf);
					}
					if(str.indexOf(",3,")>-1){
						sf=new SysFunction();
						sf.setTitle(title+"_删除");
						sf.setParentFunid(itemid);
						sf.setOperateType("3");
						functionDao.save(sf);
					}
					if(str.indexOf(",4,")>-1){
						sf=new SysFunction();
						sf.setTitle(title+"_查询");
						sf.setParentFunid(itemid);
						sf.setOperateType("4");
						functionDao.save(sf);
					}
					if(str.indexOf(",5,")>-1){
						sf=new SysFunction();
						sf.setTitle(title+"_查看");
						sf.setParentFunid(itemid);
						sf.setOperateType("5");
						functionDao.save(sf);
					}
				}
				operateFlag="add";
			}else{
				//修改
				functionDao.update(fun);
				itemid=fun.getId();

				String str = fun.getOperateType();
				if(str!=null&&!"".equals(str)){
					//当选择了子项才执行
					SysFunction sf;
					String title= fun.getTitle();
					
					if(str.indexOf(",1,")>-1){
						sf=new SysFunction();
						sf.setTitle(title+"_新增");
						sf.setParentFunid(itemid);
						sf.setOperateType("1");
						//查看该项是否已存在，存在就修改，不存在就新增
						List<Object[]> lt = functionDao.findChildFun(itemid,"1");
						if(lt!=null&&lt.size()>0){							
							int num = Integer.parseInt(lt.get(0)[0].toString());
							sf.setId(num);
							functionDao.update(sf);
						}else{
							functionDao.save(sf);
						}						 
					}else{
						//新增并未在页面选择，而数据库原理已保存则删除						
						List<Object[]> lt = functionDao.findChildFun(itemid,"1");
						if(lt!=null&&lt.size()>0){
							SysFunction sfn = functionDao.getEntity(Integer.parseInt(lt.get(0)[0].toString()));
							functionDao.delete(sfn);
						}
					}					
					if(str.indexOf(",2,")>-1){
						sf=new SysFunction();
						sf.setTitle(title+"_修改");
						sf.setParentFunid(itemid);
						sf.setOperateType("2");
						//查看该项是否已存在，存在就修改，不存在就新增
						List<Object[]> lt = functionDao.findChildFun(itemid,"2");
						if(lt!=null&&lt.size()>0){
							int num = Integer.parseInt(lt.get(0)[0].toString());
							sf.setId(num);
							functionDao.update(sf);
						}else{
							functionDao.save(sf);
						}						 
					}else{
						//新增并未在页面选择，而数据库里已保存则删除						
						List<Object[]> lt = functionDao.findChildFun(itemid,"2");
						if(lt!=null&&lt.size()>0){
							SysFunction sfn = functionDao.getEntity(Integer.parseInt(lt.get(0)[0].toString()));
							functionDao.delete(sfn);
						}
					}

					if(str.indexOf(",3,")>-1){
						sf=new SysFunction();
						sf.setTitle(title+"_删除");
						sf.setParentFunid(itemid);
						sf.setOperateType("3");
						//查看该项是否已存在，存在就修改，不存在就新增
						List<Object[]> lt = functionDao.findChildFun(itemid,"3");
						if(lt!=null&&lt.size()>0){
							int num = Integer.parseInt(lt.get(0)[0].toString());
							sf.setId(num);
							functionDao.update(sf);
						}else{
							functionDao.save(sf);
						}						 
					}else{
						//新增并未在页面选择，而数据库原理已保存则删除						
						List<Object[]> lt = functionDao.findChildFun(itemid,"3");
						if(lt!=null&&lt.size()>0){
							SysFunction sfn = functionDao.getEntity(Integer.parseInt(lt.get(0)[0].toString()));
							functionDao.delete(sfn);
						}
					}

					if(str.indexOf(",4,")>-1){
						sf=new SysFunction();
						sf.setTitle(title+"_查询");
						sf.setParentFunid(itemid);
						sf.setOperateType("4");
						//查看该项是否已存在，存在就修改，不存在就新增
						List<Object[]> lt = functionDao.findChildFun(itemid,"4");
						if(lt!=null&&lt.size()>0){
							int num = Integer.parseInt(lt.get(0)[0].toString());
							sf.setId(num);
							functionDao.update(sf);
						}else{
							functionDao.save(sf);
						}						 
					}else{
						//新增并未在页面选择，而数据库原理已保存则删除						
						List<Object[]> lt = functionDao.findChildFun(itemid,"4");
						if(lt!=null&&lt.size()>0){
							SysFunction sfn = functionDao.getEntity(Integer.parseInt(lt.get(0)[0].toString()));
							functionDao.delete(sfn);
						}
					}

					if(str.indexOf(",5,")>-1){
						sf=new SysFunction();
						sf.setTitle(title+"_查看");
						sf.setParentFunid(itemid);
						sf.setOperateType("5");
						//查看该项是否已存在，存在就修改，不存在就新增
						List<Object[]> lt = functionDao.findChildFun(itemid,"5");
						if(lt!=null&&lt.size()>0){
							int num = Integer.parseInt(lt.get(0)[0].toString());
							sf.setId(num);
							functionDao.update(sf);
						}else{
							functionDao.save(sf);
						}						 
					}else{
						//新增并未在页面选择，而数据库原理已保存则删除						
						List<Object[]> lt = functionDao.findChildFun(itemid,"5");
						if(lt!=null&&lt.size()>0){
							SysFunction sfn = functionDao.getEntity(Integer.parseInt(lt.get(0)[0].toString()));
							functionDao.delete(sfn);
						}
					}
				}
				operateFlag="update";
			}			
			return itemid+"_"+operateFlag;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@Override
	@MethodLog2(remark="删除功能菜单信息",type="删除")
	public String delFunction(int id){
		return functionDao.delFunction(id);
	}



	/**
	 * 保存角色和功能的关系
	 * @param roleid
	 * @param menuids
	 */
	@Override
	@MethodLog2(remark="编辑菜单角色关系",type="编辑")
	public void saveRoleFunction(String roleid, String menuids) {
		//删除原关联，新建新关联
		functionDao.delRoleFun(roleid);
		//新增
		SysRolefunction srf = null;
		String arr [] = menuids.split(",");
		for(int i=0;i<arr.length;i++){
			srf = new SysRolefunction();
			srf.setRoleid(Integer.parseInt(roleid));
			srf.setFunctionid(Integer.parseInt(arr[i]));
			functionRoleDao.save(srf);
		}		
	}


	/**
	 * 左边导航栏显示子菜单
	 */
	@Override
	public String showChildMenu(String pid,String userid) {
		StringBuffer str = new StringBuffer();	
		List<SysFunction> list = (List<SysFunction>) new UserMenuCache().get(userid);
		
		
		str.append("[");
		for(int i=0;i<list.size();i++){  
			if(pid.equals(list.get(i).getParentFunid().toString())){	
				
				String state = "open";
				List<Object[]> list1 = this.functionDao.findFunByParentid(list.get(i).getId()+"");
				if(list1!=null)
					state = list1.size() >0 ? "closed":"open";
				
				str.append("{\"id\":").append(list.get(i).getId()).append(",\"text\":\"").append(list.get(i).getTitle())
				.append("\",\"iconCls\":\"").append("icon-menudefault").append("\" ");  //"iconCls":"icon-remove"
				
				if(list.get(i).getIsPopup()!=null&&"1".equals(list.get(i).getIsPopup())){					
					str.append(" ,\"attributes\":{\"isPopup\":\"").append(list.get(i).getIsPopup()).append("\"} ");
				}else{
					str.append(" ,\"attributes\":{\"isPopup\":\"2\"} ");
				}
				
				str.append(",\"url\":\"").append(list.get(i).getUrl()).append("\",\"state\":\""+state+"\"},");
					
			}
		}		
		str.delete(str.toString().length()-1, str.toString().length()).append("]");	
		return str.toString();
	}


	@Override
	public String showFunctionJson(String pid) {
		if(pid==null||"".equals(pid)){
			pid="1";
		}		
		List<SysFunction> list = functionDao.findFunctionByPid(Integer.parseInt(pid));
		if(list!=null){		
		StringBuffer str = new StringBuffer();	
		str.append("[");
		for(int i=0;i<list.size();i++){				
			String state = "open";
			List<SysFunction> list2 = functionDao.findFunctionByPid(list.get(i).getId());
			if(list2!=null&&list2.size()>0){
				state = "closed";
			}
			str.append("{\"id\":\"").append(list.get(i).getId()).append("\",\"title\":\"").append(list.get(i).getTitle());	
			if(list.get(i).getParentFunid()==null){
				str.append("\",\"parentFunid\":\"").append("1"); 
			}else{
				str.append("\",\"parentFunid\":\"").append(list.get(i).getParentFunid());
			}
			str.append("\",\"remark\":\"").append(list.get(i).getRemark()==null?"":list.get(i).getRemark())
			.append("\",\"url\":\"").append(list.get(i).getUrl()==null?"":list.get(i).getUrl())
			.append("\",\"funnum\":\"").append(list.get(i).getFunOrder()==null?"":list.get(i).getFunOrder())
			.append("\",\"operateType\":\"").append(list.get(i).getOperateType()==null?"":list.get(i).getOperateType())
			.append("\",\"picture\":\"").append(list.get(i).getPicture()==null?"":list.get(i).getPicture())
			.append("\",\"isPopup\":\"").append(list.get(i).getIsPopup()==null?"2":list.get(i).getIsPopup())
			.append("\",\"state\":\""+state+"\" },");		
		}
		if(!"[".equals(str.toString())){
			str.delete(str.toString().length()-1, str.toString().length());
		}
			str.append("]");
		return str.toString();
		}else{
			return "[]";
		}
	}


	@Override
	@MethodLog2(remark="编辑菜单信息",type="编辑")
	public SysFunction saveFunction(SysFunction fun) {
		/*functionDao.saveOrUpdateFunction(menu);
		return menu;*/		
		try {
			int itemid;
			String str = fun.getOperateType();
			if(str!=null&&!"".equals(str)){
				str=","+str+",";
			}
			if(fun.getId()==null){
				//新增
				itemid = functionDao.saveFunction(fun);
				//新增子功能项(add-增，update-改，del-删，query-查，view-看)				
				if(str!=null&&!"".equals(str)){
					//当选择了子项才执行
					SysFunction sf;
					String title= fun.getTitle();
					if(str.indexOf(",add,")>-1){
						sf=new SysFunction();
						sf.setTitle(title+"_新增");
						sf.setParentFunid(itemid);
						sf.setOperateType("add");
						sf.setIsMenu(0);
						functionDao.save(sf);
					}
					if(str.indexOf(",update,")>-1){
						sf=new SysFunction();
						sf.setTitle(title+"_修改");
						sf.setParentFunid(itemid);
						sf.setOperateType("update");
						sf.setIsMenu(0);
						functionDao.save(sf);
					}
					if(str.indexOf(",del,")>-1){
						sf=new SysFunction();
						sf.setTitle(title+"_删除");
						sf.setParentFunid(itemid);
						sf.setOperateType("del");
						sf.setIsMenu(0);
						functionDao.save(sf);
					}
					if(str.indexOf(",query,")>-1){
						sf=new SysFunction();
						sf.setTitle(title+"_查询");
						sf.setParentFunid(itemid);
						sf.setOperateType("query");
						sf.setIsMenu(0);
						functionDao.save(sf);
					}
					if(str.indexOf(",view,")>-1){
						sf=new SysFunction();
						sf.setTitle(title+"_查看");
						sf.setParentFunid(itemid);
						sf.setOperateType("view");
						sf.setIsMenu(0);
						functionDao.save(sf);
					}
					if(str.indexOf(",export,")>-1){
						sf=new SysFunction();
						sf.setTitle(title+"_导出");
						sf.setParentFunid(itemid);
						sf.setOperateType("export");
						sf.setIsMenu(0);
						functionDao.save(sf);
					}
					if(str.indexOf(",banli,")>-1){
						sf=new SysFunction();
						sf.setTitle(title+"_办理");
						sf.setParentFunid(itemid);
						sf.setOperateType("banli");
						sf.setIsMenu(0);
						functionDao.save(sf);
					}
					if(str.indexOf(",shangbao,")>-1){
						sf=new SysFunction();
						sf.setTitle(title+"_上报");
						sf.setParentFunid(itemid);
						sf.setOperateType("shangbao");
						sf.setIsMenu(0);
						functionDao.save(sf);
					}
					if(str.indexOf(",guanzhu,")>-1){
						sf=new SysFunction();
						sf.setTitle(title+"_关注");
						sf.setParentFunid(itemid);
						sf.setOperateType("guanzhu");
						sf.setIsMenu(0);
						functionDao.save(sf);
					}
				}
			}else{
				//修改
				functionDao.update(fun);
				itemid=fun.getId();

				if(str!=null&&!"".equals(str)){
					//当选择了子项才执行
					SysFunction sf;
					String title= fun.getTitle();
					
					if(str.indexOf(",add,")>-1){
						sf=new SysFunction();
						sf.setTitle(title+"_新增");
						sf.setParentFunid(itemid);
						sf.setOperateType("add");
						sf.setIsMenu(0);
						//查看该项是否已存在，存在就修改，不存在就新增
						List<Object[]> lt = functionDao.findChildFun(itemid,"add");
						if(lt!=null&&lt.size()>0){							
							int num = Integer.parseInt(lt.get(0)[0].toString());
							sf.setId(num);
							functionDao.update(sf);
						}else{
							functionDao.save(sf);
						}						 
					}else{
						//新增并未在页面选择，而数据库已保存则删除						
						List<Object[]> lt = functionDao.findChildFun(itemid,"add");
						if(lt!=null&&lt.size()>0){
							SysFunction sfn = functionDao.getEntity(Integer.parseInt(lt.get(0)[0].toString()));
							functionDao.delete(sfn);
						}
					}					
					if(str.indexOf(",update,")>-1){
						sf=new SysFunction();
						sf.setTitle(title+"_修改");
						sf.setParentFunid(itemid);
						sf.setOperateType("update");
						sf.setIsMenu(0);
						//查看该项是否已存在，存在就修改，不存在就新增
						List<Object[]> lt = functionDao.findChildFun(itemid,"update");
						if(lt!=null&&lt.size()>0){
							int num = Integer.parseInt(lt.get(0)[0].toString());
							sf.setId(num);
							functionDao.update(sf);
						}else{
							functionDao.save(sf);
						}						 
					}else{
						//新增并未在页面选择，而数据库里已保存则删除						
						List<Object[]> lt = functionDao.findChildFun(itemid,"update");
						if(lt!=null&&lt.size()>0){
							SysFunction sfn = functionDao.getEntity(Integer.parseInt(lt.get(0)[0].toString()));
							functionDao.delete(sfn);
						}
					}

					if(str.indexOf(",del,")>-1){
						sf=new SysFunction();
						sf.setTitle(title+"_删除");
						sf.setParentFunid(itemid);
						sf.setOperateType("del");
						sf.setIsMenu(0);
						//查看该项是否已存在，存在就修改，不存在就新增
						List<Object[]> lt = functionDao.findChildFun(itemid,"del");
						if(lt!=null&&lt.size()>0){
							int num = Integer.parseInt(lt.get(0)[0].toString());
							sf.setId(num);
							functionDao.update(sf);
						}else{
							functionDao.save(sf);
						}						 
					}else{
						//新增并未在页面选择，而数据库原理已保存则删除						
						List<Object[]> lt = functionDao.findChildFun(itemid,"del");
						if(lt!=null&&lt.size()>0){
							SysFunction sfn = functionDao.getEntity(Integer.parseInt(lt.get(0)[0].toString()));
							functionDao.delete(sfn);
						}
					}

					if(str.indexOf(",query,")>-1){
						sf=new SysFunction();
						sf.setTitle(title+"_查询");
						sf.setParentFunid(itemid);
						sf.setOperateType("query");
						sf.setIsMenu(0);
						//查看该项是否已存在，存在就修改，不存在就新增
						List<Object[]> lt = functionDao.findChildFun(itemid,"query");
						if(lt!=null&&lt.size()>0){
							int num = Integer.parseInt(lt.get(0)[0].toString());
							sf.setId(num);
							functionDao.update(sf);
						}else{
							functionDao.save(sf);
						}						 
					}else{
						//新增并未在页面选择，而数据库原理已保存则删除						
						List<Object[]> lt = functionDao.findChildFun(itemid,"query");
						if(lt!=null&&lt.size()>0){
							SysFunction sfn = functionDao.getEntity(Integer.parseInt(lt.get(0)[0].toString()));
							functionDao.delete(sfn);
						}
					}

					if(str.indexOf(",view,")>-1){
						sf=new SysFunction();
						sf.setTitle(title+"_查看");
						sf.setParentFunid(itemid);
						sf.setOperateType("view");
						sf.setIsMenu(0);
						//查看该项是否已存在，存在就修改，不存在就新增
						List<Object[]> lt = functionDao.findChildFun(itemid,"view");
						if(lt!=null&&lt.size()>0){
							int num = Integer.parseInt(lt.get(0)[0].toString());
							sf.setId(num);
							functionDao.update(sf);
						}else{
							functionDao.save(sf);
						}						 
					}else{
						//新增并未在页面选择，而数据库原理已保存则删除						
						List<Object[]> lt = functionDao.findChildFun(itemid,"view");
						if(lt!=null&&lt.size()>0){
							SysFunction sfn = functionDao.getEntity(Integer.parseInt(lt.get(0)[0].toString()));
							functionDao.delete(sfn);
						}
					}
					
					if(str.indexOf(",export,")>-1){
						sf=new SysFunction();
						sf.setTitle(title+"_导出");
						sf.setParentFunid(itemid);
						sf.setOperateType("export");
						sf.setIsMenu(0);
						//查看该项是否已存在，存在就修改，不存在就新增
						List<Object[]> lt = functionDao.findChildFun(itemid,"export");
						if(lt!=null&&lt.size()>0){
							int num = Integer.parseInt(lt.get(0)[0].toString());
							sf.setId(num);
							functionDao.update(sf);
						}else{
							functionDao.save(sf);
						}						 
					}else{
						//新增并未在页面选择，而数据库原理已保存则删除						
						List<Object[]> lt = functionDao.findChildFun(itemid,"export");
						if(lt!=null&&lt.size()>0){
							SysFunction sfn = functionDao.getEntity(Integer.parseInt(lt.get(0)[0].toString()));
							functionDao.delete(sfn);
						}
					}
					
					if(str.indexOf(",banli,")>-1){
						sf=new SysFunction();
						sf.setTitle(title+"_办理");
						sf.setParentFunid(itemid);
						sf.setOperateType("banli");
						sf.setIsMenu(0);
						//查看该项是否已存在，存在就修改，不存在就新增
						List<Object[]> lt = functionDao.findChildFun(itemid,"banli");
						if(lt!=null&&lt.size()>0){
							int num = Integer.parseInt(lt.get(0)[0].toString());
							sf.setId(num);
							functionDao.update(sf);
						}else{
							functionDao.save(sf);
						}						 
					}else{
						//新增并未在页面选择，而数据库原理已保存则删除						
						List<Object[]> lt = functionDao.findChildFun(itemid,"banli");
						if(lt!=null&&lt.size()>0){
							SysFunction sfn = functionDao.getEntity(Integer.parseInt(lt.get(0)[0].toString()));
							functionDao.delete(sfn);
						}
					}
					//上报
					if(str.indexOf(",shangbao,")>-1){
						sf=new SysFunction();
						sf.setTitle(title+"_上报");
						sf.setParentFunid(itemid);
						sf.setOperateType("shangbao");
						sf.setIsMenu(0);
						//查看该项是否已存在，存在就修改，不存在就新增
						List<Object[]> lt = functionDao.findChildFun(itemid,"shangbao");
						if(lt!=null&&lt.size()>0){
							int num = Integer.parseInt(lt.get(0)[0].toString());
							sf.setId(num);
							functionDao.update(sf);
						}else{
							functionDao.save(sf);
						}						 
					}else{
						//新增并未在页面选择，而数据库原理已保存则删除						
						List<Object[]> lt = functionDao.findChildFun(itemid,"shangbao");
						if(lt!=null&&lt.size()>0){
							SysFunction sfn = functionDao.getEntity(Integer.parseInt(lt.get(0)[0].toString()));
							functionDao.delete(sfn);
						}
					}
					
					//关注
					if(str.indexOf(",guanzhu,")>-1){
						sf=new SysFunction();
						sf.setTitle(title+"_关注");
						sf.setParentFunid(itemid);
						sf.setOperateType("guanzhu");
						sf.setIsMenu(0);
						//查看该项是否已存在，存在就修改，不存在就新增
						List<Object[]> lt = functionDao.findChildFun(itemid,"guanzhu");
						if(lt!=null&&lt.size()>0){
							int num = Integer.parseInt(lt.get(0)[0].toString());
							sf.setId(num);
							functionDao.update(sf);
						}else{
							functionDao.save(sf);
						}						 
					}else{
						//新增并未在页面选择，而数据库原理已保存则删除						
						List<Object[]> lt = functionDao.findChildFun(itemid,"guanzhu");
						if(lt!=null&&lt.size()>0){
							SysFunction sfn = functionDao.getEntity(Integer.parseInt(lt.get(0)[0].toString()));
							functionDao.delete(sfn);
						}
					}
				}
			}			
			return fun;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}


	@Override
	public List<Object[]> checkChildFunctionData(String id) {
		
		return functionDao.findFunByParentid(id);
	}


	@Override
	@MethodLog2(remark="删除菜信息",type="删除")
	public void deleleFunction(String id) {
		functionDao.delFunction(Integer.parseInt(id));
	}

}
