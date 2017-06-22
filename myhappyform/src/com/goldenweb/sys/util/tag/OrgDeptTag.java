package com.goldenweb.sys.util.tag;


import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;


@SuppressWarnings("serial")
public class OrgDeptTag extends TagSupport {
    private String orgId;
    private String value;
    private String name;
    private String style;
    private String type;
    private String orgwidth;
    private String deptwidth;
    private String isLinkUser;
    private String isOfNotice;
      
	public String getIsOfNotice() {
		return isOfNotice;
	}
	public void setIsOfNotice(String isOfNotice) {
		this.isOfNotice = isOfNotice;
	}
	public String getIsLinkUser() {
		return isLinkUser;
	}
	public void setIsLinkUser(String isLinkUser) {
		this.isLinkUser = isLinkUser;
	}
	public String getOrgwidth() {
		return orgwidth;
	}
	public void setOrgwidth(String orgwidth) {
		this.orgwidth = orgwidth;
	}
	public String getDeptwidth() {
		return deptwidth;
	}
	public void setDeptwidth(String deptwidth) {
		this.deptwidth = deptwidth;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	

	@Override
	public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    @Override
	public int doStartTag() throws JspException {
    	 if (null == this.value) {
             this.value = "";
         }
    	 if (null == this.orgwidth) {//机构默认150长度
             this.orgwidth = "150";
         }
    	 if (null == this.deptwidth) {//部门默认150长度
             this.deptwidth = "150";
         }
    	 boolean linkUser;
    	 if(isLinkUser==null||!"true".equals(isLinkUser)){
    		 linkUser =false;
    	 }else{
    		 linkUser = true;
    	 }
    	 boolean linkNotice;
    	 if(isOfNotice==null||!"true".equals(isOfNotice)){
    		 linkNotice =false;
    	 }else{
    		 linkNotice = true;
    	 }
        try {
        	/* 插入取得当前SELECTBOX的值 */
            StringBuffer buffer = new StringBuffer();
            StringBuffer option = new StringBuffer();
            String orgcode="";String orgname="";String deptcode="";String deptname="";
            
            String name1=orgId; String name2=orgId;
            if(this.value!=null&&!"".equals(this.value)){
            if(this.value.indexOf(",")>-1){
            	//多个
            	String arr [] = value.split(",");
            	//机构
            	String str [] =arr[0].split("~");
            	orgcode = str[0].split("-")[0];
            	orgname = str[0].split("-")[1];
            	//部门
            	for(int i=0;i<arr.length;i++){
            		String deptarr [] = arr[i].split("~");
            		deptcode+="'"+deptarr[1].split("-")[0]+"',";
                	deptname+="'"+deptarr[1].split("-")[1]+"',";
            	}
            	deptcode = deptcode.substring(0, deptcode.length()-1);
            	deptname = deptname.substring(0, deptname.length()-1);
            }else{
            	//单个            	
            	String str [] =value.split("~");
            	orgcode = str[0].split("-")[0];
            	orgname = str[0].split("-")[1];
            	
            	deptcode="'"+str[1].split("-")[0]+"'";
            	deptname="'"+str[1].split("-")[1]+"'";
            }
        }
            boolean typestr=false; //单/多选
            if(this.type.equals("multiple")){
            	typestr = true;
            }else if(this.type.equals("single")){
            	typestr = false;
            }else{
            	//异常
            	String str= "<script type=\"text/javascript\"> alert('机构部门设置异常'); </script>";
            	this.pageContext.getOut().write(str);
            }
            
            buffer.append(" <script type=\"text/javascript\">  ");
            buffer.append(" var orgstr; var select_init_num=0; ");
            buffer.append(" window.onload = function() ");
            buffer.append(" {   ");
            buffer.append(" $('#orgselect"+name1+"').combotree({   ");
            buffer.append(" 	url : 'orgAction_chooseChildOrgJson?orgid="+this.orgId+"',  ");
            buffer.append(" 	cascadeCheck : true,  ");
            buffer.append(" 	width : "+this.orgwidth+",   ");
            if(!linkNotice){
            buffer.append(" 	required : true,  ");
            }
            buffer.append(" 	onSelect: function(rec){   ");
            buffer.append(" 		orgstr =rec.id+'-'+rec.text;   ");
            buffer.append(" 		$('#deptselect"+name2+"').combobox('clear');$(\"#backvalue"+name1+"\").val(\"\");   ");
            buffer.append(" 		var url = 'orgAction_findDeptsByOrgidJson?code='+rec.id;     ");
            buffer.append("        $('#deptselect"+name2+"').combobox('reload', url);  ");
                        
            if(false){
            buffer.append(" var  icode =  $(\"#noticeobjcode"+name+"\").val();  var icodearr = icode.split(','); ");
            //buffer.append(" $('#orgselect"+name1+"').combobox('select', rec.id); ");
            buffer.append("  for(var i=0;icodearr.length;i++){ ");
            buffer.append("   if(rec.id==icodearr[i].split(':')[0]){ var cc=icodearr[i].split(':')[1]; $('#deptselect"+name2+"').combobox('select', cc); }");
            buffer.append("  }");
            
            }
            
           /* buffer.append("        if(select_init_num==0){    ");
            if(!"".equals(deptcode)){
            buffer.append("        $('#deptselect"+name2+"').combobox('setValue',["+deptcode+"]);");
            }
            if(this.value!=null&&!"".equals(this.value)){
                buffer.append(" $('#backvalue"+name1+"').val(\""+this.value+"\");  ");
                }
            buffer.append("        } select_init_num++;    ");*/
            buffer.append("    }   ");
            buffer.append("  });  ");
            buffer.append("  $('#deptselect"+name2+"').combobox({   ");
            buffer.append(" 	multiple:"+typestr+",  ");
            buffer.append(" 	required : true,  ");
            
            if(typestr){
            buffer.append(" 	onSelect: function(rec){  ");
            buffer.append(" 		var val = $(\"#backvalue"+name1+"\").val();  if(val!=''){val+=',';} ");
            buffer.append(" 		$(\"#backvalue"+name1+"\").val(val+orgstr+'~'+rec.id+'-'+rec.text);    ");
            if(linkUser){
            buffer.append(" var a = document.getElementsByName(\""+this.name+"\")[0].value; var url = 'mailAction_findSendPerson?val='+a;  $(\"#sendperson\").combobox('reload', url); ");
            }
            if(linkNotice){
            	buffer.append(" var val2 = $(\"#noticeobjname"+name+"\").val();  if(val2!=''){val2+=',';} ");
            	buffer.append("       val2 = val2.replace((orgstr.split('-')[1]+rec.text+\",\"),'');");
            	buffer.append(" 		$(\"#noticeobjname"+name+"\").val(val2+orgstr.split('-')[1]+rec.text);    ");
            	buffer.append(" var val3 = $(\"#noticeobjcode"+name+"\").val();  if(val3!=''){val3+=',';} ");
            	buffer.append("       val3 = val3.replace((orgstr.split('-')[0]+':'+rec.id+\",\"),'');");
            	buffer.append(" 		$(\"#noticeobjcode"+name+"\").val(val3+orgstr.split('-')[0]+':'+rec.id);    ");
            }
            buffer.append("     },	");
            }else{
            buffer.append(" 	onSelect: function(rec){  ");
            buffer.append(" 		$(\"#backvalue"+name1+"\").val(orgstr+'~'+rec.id+'-'+rec.text);    ");
            if(linkUser){
            buffer.append(" var a = document.getElementsByName(\""+this.name+"\")[0].value; var url = 'mailAction_findSendPerson?val='+a;  $(\"#sendperson\").combobox('reload', url); ");
            }
            if(linkNotice){
            	buffer.append(" 		$(\"#noticeobjname"+name+"\").val(orgstr.split('-')[1]+rec.text);    ");
            	buffer.append(" 		$(\"#noticeobjcode"+name+"\").val(orgstr.split('-')[0]+':'+rec.id);    ");
            }
            buffer.append("     },	");
            }
            
            buffer.append("     onUnselect:function(rec){   ");
            buffer.append("	      var val = $(\"#backvalue"+name1+"\").val()+',';    ");
            buffer.append("       val = val.replace((orgstr+'~'+rec.id+'-'+rec.text+\",\"),'');");
            buffer.append("	      $(\"#backvalue"+name1+"\").val(val.substring(0, val.length-1));  ");
            if(linkNotice){
            	 buffer.append("	      var val2 = $(\"#noticeobjname"+name+"\").val()+',';    ");
                 buffer.append("       val2 = val2.replace((orgstr.split('-')[1]+rec.text+\",\"),'');");
                 buffer.append("	    $(\"#noticeobjname"+name+"\").val(val2.substring(0, val2.length-1));  ");
                 
                 buffer.append("	      var val3 = $(\"#noticeobjcode"+name+"\").val()+',';    ");
                 buffer.append("       val3 = val3.replace((orgstr.split('-')[0]+':'+rec.id+\",\"),'');");
                 buffer.append("	    $(\"#noticeobjcode"+name+"\").val(val3.substring(0, val3.length-1));  ");
            }
            buffer.append("       }     ");
            buffer.append(" });    ");

            
            if(!"".equals(this.value)){
            buffer.append(" $('#orgselect"+name1+"').combotree('setValue','"+orgcode+"'); ");
            buffer.append(" 		var url = 'orgAction_findDeptsByOrgidJson?code="+orgcode+"';     ");
            buffer.append("        $('#deptselect"+name2+"').combobox('reload', url);  ");
            buffer.append("        $('#deptselect"+name2+"').combobox('setValue',["+deptcode+"]);");
            buffer.append(" $('#backvalue"+name1+"').val(\""+this.value+"\");  ");
            buffer.append(" orgstr ='"+orgcode+"';  ");
            }
         /* $('#deptselectMC320600000000000').combobox('setValue',['AA0114010101']);  
           var url = '/Core/orgAction_findDeptsByOrgidJson?code='+'MC320683000000000';     
       	 $('#deptselectMC320600000000000').combobox('reload', url);  
       	 $('#backvalueMC320600000000000').val("MC320683000000000-通州市~AA0114010101-国土资源局");
       	 orgstr = "MC320683000000000-通州市";*/
            
            
            
           
            buffer.append(" }   ");
            buffer.append("  </script>   ");
            
            
            
            
            
            
           
            
            /* 插入名称和ID */
            buffer.append("<input name='orgselect" + name1 + "'  ");
            buffer.append(" id='orgselect" + name1 + "'");

            /* 插入样式 */
            if (null != this.style && !"".equals(this.style)) {
                buffer.append(" style='" + this.style + "'");
            } 
            buffer.append(" class='easyui-combotree' />");
            
            
            buffer.append(" <input id=\"deptselect"+name2+"\" class=\"easyui-combobox\" data-options=\"valueField:'id',textField:'text'\"  style=\"width: "+this.deptwidth+"\" />  ");

            buffer.append(" <input type=\"hidden\" id=\"backvalue"+name1+"\" name=\""+this.name+"\" size=\"100\"   />  ");
            /* 插入JSP画面 */
            this.pageContext.getOut().write(buffer.toString());
        	            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EVAL_BODY_INCLUDE;
    }
}
