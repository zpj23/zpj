/*@version:2.1 @author:zhangbo @email:freeeob@gmail.com GPL2.1*/function ConditionTrigger(components,conditionNumber){

this.jsjava_class="jsorg.eob.component.trigger.ConditionTrigger";

this.components=components;

this.conditions=new Array(conditionNumber);

this.size=0;


} ConditionTrigger.prototype.addCondition=function(condition){

this.conditions[this.size++]=condition;


} ;

ConditionTrigger.prototype.trigger=function(conditionSeed){

var triggerCondition="ConditionTrigger_NULL";

var conditions=this.conditions;

for(var i=0;

i<conditions.length;

i++){

if(conditions[i].conditionSeed==conditionSeed){

triggerCondition=conditions[i];


} 

} if(triggerCondition=="ConditionTrigger_NULL"){

return;


} var visibles;

if(triggerCondition!=null){

visibles=triggerCondition.visibles;


} if(visibles!=null){

for(var i=0;

i<visibles.length;

i++){

this.show(visibles[i]);


} 

} var invisibles=this.getDiff(this.components,visibles);

for(var i=0;

i<invisibles.length;

i++){

this.hide(invisibles[i]);


} 

} ;

ConditionTrigger.prototype.show=function(id){

document.getElementById(id).style.display="block";


} ;

ConditionTrigger.prototype.hide=function(id){

document.getElementById(id).style.display="none";


} ;

ConditionTrigger.prototype.getDiff=function(collection,parts){

if(parts==null){

return collection;


} var res=new Array(collection.length-parts.length);

var resCount=0;

for(var i=0;

i<collection.length;

i++){

if(!this.contains(parts,collection[i])){

res[resCount]=collection[i];

resCount++;


} 

} return res;


} ;

ConditionTrigger.prototype.contains=function(parts,part){

for(var i=0;

i<parts.length;

i++){

if(parts[i]==part){

return true;


} 

} return false;


} ;

function ExclusiveGroupTrigger(){

this.jsjava_class="jsorg.eob.component.trigger.ExclusiveGroupTrigger";


} ExclusiveGroupTrigger.prototype.setForm=function(formName){

this.formName=formName;


} ;

ExclusiveGroupTrigger.prototype.getForm=function(){

return this.formName;


} ;

ExclusiveGroupTrigger.prototype.setPositiveCheckbox=function(pc){

this.pc=pc;


} ;

ExclusiveGroupTrigger.prototype.getExclusiveGroupPositiveCheckbox=function(){

return this.pc;


} ;

ExclusiveGroupTrigger.prototype.setMinusCheckbox=function(mc){

this.mc=mc;


} ;

ExclusiveGroupTrigger.prototype.getMinusCheckbox=function(){

return this.mc;


} ;

ExclusiveGroupTrigger.prototype.setPositiveAllCheckbox=function(pac){

this.pac=pac;


} ;

ExclusiveGroupTrigger.prototype.getPositiveAllCheckbox=function(){

return this.pac;


} ;

ExclusiveGroupTrigger.prototype.setMinusAllCheckbox=function(mac){

this.mac=mac;


} ;

ExclusiveGroupTrigger.prototype.getMinusAllCheckbox=function(){

return this.mac;


} ;

ExclusiveGroupTrigger.prototype.setEventSource=function(eventSource){

this.eventSource=eventSource;


} ;

ExclusiveGroupTrigger.prototype.getEventSource=function(){

return this.eventSource;


} ;

ExclusiveGroupTrigger.prototype.exclusiveGrouptrigger=function(command){

if(command=="positive"){

this.positiveEventTrigger();


} else if(command=="minus"){

this.minusEventTrigger();


} else if(command=="positiveAll"){

this.positiveAllEventTrigger();


} else if(command=="minusAll"){

this.minusAllEventTrigger();


} 

} ;

ExclusiveGroupTrigger.prototype.positiveEventTrigger=function(){

var eventSource=this.getEventSource();

var formName=this.getForm();

var pcName=this.getPositiveCheckbox();

var mcName=this.getMinusCheckbox();

var pacName=this.getPositiveAllCheckbox();

var macName=this.getMinusAllCheckbox();

var formObj=document.forms(formName);

var pcObj=formObj.elements(pcName);

var mcObj=formObj.elements(mcName);

var pacObj=formObj.elements(pacName);

var macObj=formObj.elements(macName);

if(eventSource.checked==false){

pacObj.checked=false;


} else{

var isAllSelected=true;

for(var i=0;

i<pcObj.length;

i++){

if(!pcObj[i].checked){

isAllSelected=false;


} 

} if(isAllSelected){

pacObj.checked=true;


} 

} macObj.checked=false;

var value=eventSource.value;

if(mcObj.length){

for(var i=0;

i<mcObj.length;

i++){

if(mcObj[i].value==value){

mcObj[i].checked=false;


} 

} 

} else{

mcObj.checked=false;


} return false;


} ;

ExclusiveGroupTrigger.prototype.minusEventTrigger=function(){

var eventSource=trigger.getEventSource();

var formName=this.getForm();

var pcName=this.getPositiveCheckbox();

var mcName=this.getMinusCheckbox();

var pacName=this.getPositiveAllCheckbox();

var macName=this.getMinusAllCheckbox();

var formObj=document.forms(formName);

var pcObj=formObj.elements(pcName);

var mcObj=formObj.elements(mcName);

var pacObj=formObj.elements(pacName);

var macObj=formObj.elements(macName);

if(eventSource.checked==false){

macObj.checked=false;


} else{

var isAllSelected=true;

for(var i=0;

i<mcObj.length;

i++){

if(!mcObj[i].checked){

isAllSelected=false;


} 

} if(isAllSelected){

macObj.checked=true;


} 

} pacObj.checked=false;

var value=eventSource.value;

if(formObj.allow.length){

for(var i=0;

i<pcObj.length;

i++){

if(pcObj[i].value==value){

pcObj[i].checked=false;


} 

} 

} else{

pcObj.checked=false;


} return false;


} ;

ExclusiveGroupTrigger.prototype.positiveAllEventTrigger=function(){

var eventSource=this.getEventSource();

if(eventSource.checked){

this.changeAllPositiveState(true);


} else{

this.changeAllPositiveState(false);


} return false;


} ;

ExclusiveGroupTrigger.prototype.minusAllEventTrigger=function(){

var eventSource=this.getEventSource();

if(eventSource.checked){

this.changeAllMinusState(true);


} else{

this.changeAllMinusState(false);


} return false;


} ;

ExclusiveGroupTrigger.prototype.changeAllPositiveState=function(isChecked){

var eventSource=this.getEventSource();

var formName=this.getForm();

var pcName=this.getPositiveCheckbox();

var mcName=this.getMinusCheckbox();

var pacName=this.getPositiveAllCheckbox();

var macName=this.getMinusAllCheckbox();

var formObj=document.forms(formName);

var pcObj=formObj.elements(pcName);

var mcObj=formObj.elements(mcName);

var pacObj=formObj.elements(pacName);

var macObj=formObj.elements(macName);

if(isChecked){

macObj.checked=false;

if(pcObj.length){

for(var i=0;

i<pcObj.length;

i++){

pcObj[i].checked=true;

mcObj[i].checked=false;


} 

} else{

pcObj.checked=true;

mcObj.checked=false;


} 

} else{

pacObj.checked=false;


} 

} ;

ExclusiveGroupTrigger.prototype.changeAllMinusState=function(isChecked){

var eventSource=this.getEventSource();

var formName=this.getForm();

var pcName=this.getPositiveCheckbox();

var mcName=this.getMinusCheckbox();

var pacName=this.getPositiveAllCheckbox();

var macName=this.getMinusAllCheckbox();

var formObj=document.forms(formName);

var pcObj=formObj.elements(pcName);

var mcObj=formObj.elements(mcName);

var pacObj=formObj.elements(pacName);

var macObj=formObj.elements(macName);

if(isChecked){

pacObj.checked=false;

if(mcObj.length){

for(var i=0;

i<mcObj.length;

i++){

mcObj[i].checked=true;

pcObj[i].checked=false;


} 

} else{

pcObj.checked=false;

mcObj.checked=true;


} 

} else{

macObj.checked=false;


} 

} ;

function HTMLMenu(id,capacity){

this.jsjava_class="jsorg.eob.component.menu.HTMLMenu";

this.id=id;

this.capacity=capacity;

this.children=new Array(capacity);

this.size=0;

this.isLeaf=false;

if(capacity==0){

this.isLeaf=true;


} this.parent=null;

this._isURLForbid=false;

this._isScriptForbid=false;

this._isPassURLScriptArgsOnly=false;


} HTMLMenu.prototype.setTitle=function (title){

this.title=title;


} ;

HTMLMenu.prototype.getTitle=function(){

return this.title;


} ;

HTMLMenu.prototype.addChild=function (childNode){

this.children[this.size]=childNode;

this.size=this.size+1;

childNode.parent=this;


} ;

HTMLMenu.prototype.getChildren=function (){

var children=new Array(this.size);

for(var i=0;

i<children.length;

i++){

children[i]=this.children[i];


} return children;


} ;

HTMLMenu.prototype.getParent=function (){

return this.parent;


} ;

HTMLMenu.prototype.setIcon=function (icon){

this.icon=icon;


} ;

HTMLMenu.prototype.getIcon=function (){

return this.icon;


} ;

HTMLMenu.prototype.setDefaultIcon=function (defaultIcon){

this.defaultIcon=defaultIcon;


} ;

HTMLMenu.prototype.getDefaultIcon=function (){

return this.defaultIcon;


} ;

HTMLMenu.prototype.setLink=function (url){

this.url=url;


} ;

HTMLMenu.prototype.getLink=function (){

return this.url;


} ;

HTMLMenu.prototype.setTarget=function (target){

this.target=target;


} ;

HTMLMenu.prototype.getTarget=function (){

return this.target;


} ;

HTMLMenu.prototype.setFeature=function (feature){

this.feature=feature;


} ;

HTMLMenu.prototype.getFeature=function (){

return this.feature;


} ;

HTMLMenu.prototype.setScriptName=function (scriptName){

this.scriptName=scriptName;


} ;

HTMLMenu.prototype.getScriptName=function (){

return this.scriptName;


} ;

HTMLMenu.prototype.setScriptArgs=function (scriptArgs){

this.scriptArgs=scriptArgs;


} ;

HTMLMenu.prototype.getScriptArgs=function (){

return this.scriptArgs;


} ;

HTMLMenu.prototype.setMouseover=function (mouseover){

this.mouseover=mouseover;


} ;

HTMLMenu.prototype.getMouseover=function (){

return this.mouseover;


} ;

HTMLMenu.prototype.setMouseout=function (mouseout){

this.mouseout=mouseout;


} ;

HTMLMenu.prototype.getMouseout=function (){

return this.mouseout;


} ;

HTMLMenu.prototype.setBackground=function (background){

this.background=background;


} ;

HTMLMenu.prototype.getBackground=function (){

return this.background;


} ;

HTMLMenu.prototype.setMouseRender=function (isRender){

this.mouseRender=isRender;


} ;

HTMLMenu.prototype.isMouseRender=function(){

return this.mouseRender;


} ;

HTMLMenu.prototype.getContent=function (){

var topNode=this.getTop();

var content="<table width='100%' cellpadding='3' cellspacing='0' borderColor='#316AC5' class='WindowMenu' background='"+this.getBackground()+"'>";

for(var i=0;

i<this.size;

i++){

var child=this.children[i];

var linkUrl=child.getLink();

var linkTarget=child.getTarget();

var clickStr=child.getScriptName();

var scriptArgs=child.getScriptArgs();

if(clickStr==null||clickStr==""){

clickStr=topNode.getScriptName();

scriptArgs=topNode.getScriptArgs();


} var cursor="default";

if(clickStr==null||clickStr==undefined){

clickStr="";


} else{

if(topNode.isPassURLScriptArgsOnly()){

clickStr+="(\""+linkUrl+"\","+"\""+linkTarget+"\")";


} else{

clickStr+="("+scriptArgs+")";


} cursor="hand";


} var linkLeftStr="";

var linkRightStr="";

var isURLForbid=topNode.isURLForbid();

if(linkUrl!=null&&linkUrl!=undefined&&!isURLForbid){

if(linkTarget==null||linkTarget==undefined){

linkTarget="_self";


} var openStr="\""+linkUrl+"\",\""+linkTarget+"\","+"\"\"";

linkLeftStr="<span style='text-decoration:none;cursor:hand' onclick='window.open("+openStr+")'>";

linkRightStr="</span>";


} var icon=child.getIcon();

if(icon==null||icon==undefined){

icon=topNode.getDefaultIcon();


} content+="<tr>";

content+="<td id='node_0_"+this.id+"' width='1'>";

content+="<img id='node_0_1"+this.id+"' src='"+icon+"' nowrap/>";

content+="</td>";

content+="<td id='node_1_"+this.id+"' nowrap valign='middle' style='cursor:"+cursor+"' onclick='"+clickStr+"'";

if(this.isMouseRender()){

content+=" onmouseover='parentElement.className=\"WindowMenuMouseOver\"' onmouseout='parentElement.className=\"WindowMenuMouseOut\"'";


} content+=">";

content+=linkLeftStr+child.getTitle()+linkRightStr;

content+="</td>";

content+="</tr>";


} content+="</table>";

return content;


} ;

HTMLMenu.prototype.getTop=function (){

var parentNode=this.getParent();

if(parentNode==null||parentNode==undefined){

return this;


} return parentNode.getTop();


} ;

HTMLMenu.prototype.forbidURL=function(){

this._isURLForbid=true;


} ;

HTMLMenu.prototype.enableURL=function(){

this._isURLForbid=false;


} ;

HTMLMenu.prototype.isURLForbid=function(){

return this._isURLForbid;


} ;

HTMLMenu.prototype.forbidScript=function(){

this._isScriptForbid=true;


} ;

HTMLMenu.prototype.enableScript=function(){

this._isScriptForbid=false;


} ;

HTMLMenu.prototype.isScriptForbid=function(){

return this._isScriptForbid;


} ;

HTMLMenu.prototype.passURLScriptArgsOnly=function(){

this._isPassURLScriptArgsOnly=true;


} ;

HTMLMenu.prototype.isPassURLScriptArgsOnly=function(){

return this._isPassURLScriptArgsOnly;


} ;

HTMLMenu.prototype.init=function (capacity){

this.capacity=capacity;


} ;

function HtmlPrint(){

this.jsjava_class="jsorg.eob.component.print.HtmlPrint";


} HtmlPrint.prototype.setArea=function(area){

this.area=area;


} ;

HtmlPrint.prototype.preview=function(title,preWinFeatures){

var isNoneIE=navigator.userAgent.indexOf("MSIE")==-1;

var areaId=this.area.getAreaId();

var headObj=document.getElementsByTagName("HEAD");

if(headObj){

headObj=headObj[0];


} var areaObj=document.getElementById(areaId);

var preWin=window.open("about:blank","_blank",preWinFeatures);

var preWinConent="<html><head>";

preWinConent+=headObj.innerHTML;

preWinConent+="</head><body>";

preWinConent+=areaObj.outerHTML;

preWinConent+="</body></html>";

if(isNoneIE){

preWin.document.open();

preWin.document.write(preWinConent);

reWin.document.close();


} else{

preWin.document.body.innerHTML=preWinConent;


} if(title!=undefined){

preWin.document.title=title;


} 

} ;

HtmlPrint.prototype.print=function(){

var isNoneIE=navigator.userAgent.indexOf("MSIE")==-1;

var iframeElem=null;

if(isNoneIE){

iframeElem=document.createElement("iframe");

iframeElem.setAttribute("name","jsjava_print_iframe");

iframeElem.setAttribute("src","about:blank");

iframeElem.setAttribute("style","position:absolute;top:-5;left:-5;display:inline;width:0;height:0");


} else{

iframeElem=document.createElement("<iframe name='jsjava_print_iframe' src='about:blank' style='position:absolute;top:-5;left:-5;display:inline;width:0;height:0'></iframe>");


} document.body.appendChild(iframeElem);

var areaId=this.area.getAreaId();

var headObj=document.getElementsByTagName("HEAD");

if(headObj){

headObj=headObj[0];


} var areaObj=document.getElementById(areaId);

var preWinConent="<html><head>";

preWinConent+=headObj.innerHTML;

preWinConent+="</head><body>";

preWinConent+=areaObj.outerHTML;

preWinConent+="</body></html>";

var preWin=window.jsjava_print_iframe;

if(!preWin.document||!preWin.document.body){

setTimeout("jsjava_htmlprint_print('"+areaId+"')",200);


} else{

preWin.document.body.innerHTML=preWinConent;

preWin.focus();

preWin.print();


} 

} ;

function jsjava_htmlprint_print(areaId){

var headObj=document.getElementsByTagName("HEAD");

if(headObj){

headObj=headObj[0];


} var areaObj=document.getElementById(areaId);

var preWinConent="<html><head>";

preWinConent+=headObj.innerHTML;

preWinConent+="</head><body>";

preWinConent+=areaObj.outerHTML;

preWinConent+="</body></html>";

var preWin=window.jsjava_print_iframe;

if(!preWin.document||!preWin.document.body){

setTimeout("jsjava_htmlprint_print('"+areaId+"')",200);


} else{

preWin.document.body.innerHTML=preWinConent;

preWin.focus();

preWin.print();


} 

} function HtmlPrintArea(areaId){

this.jsjava_class="jsorg.eob.component.print.HtmlPrintArea";

this.areaId=areaId;


} HtmlPrintArea.prototype.getAreaId=function(){

return this.areaId;


} ;

function HTMLSelector(selectObj,options){

this.jsjava_class="jsorg.eob.component.selector.HTMLSelector";

if(options==undefined||!(options instanceof Array)){

options=new Array(0);


} if(selectObj){ 

this.selectObject=selectObj; 
if(selectObj.options!= undefined){ //add by me

var sL=selectObj.options.length;

for(var i=0;

i<options.length;

i++){

this.selectObj.options[sL+i]=options[i];


} }

} 

} HTMLSelector.prototype.addOption=function(option,overwrite){

if(overwrite==undefined){

overwrite=true;


} if(this.selectObject){

var sObj=this.selectObject;

if(!this.contains(option)){

sObj.options[sObj.options.length]=option;


} 

} 

} ;

HTMLSelector.prototype.addOptionItem=function(value,text,overwrite){

var option = new Option(text,value);

this.addOption(option,overwrite);


} ;

HTMLSelector.prototype.addOptions=function(options,overwrite){

if(this.selectObject){

if(options==undefined||!(options instanceof Array)){

options=new Array(0);


} var sObj=this.selectObject;

var sL=sObj.options.length;

for(var i=0;i<options.length;i++){
this.addOption(options[i],overwrite);

} 

} 

} ;

HTMLSelector.prototype.insertOption=function(index,option){

if(this.selectObject){

var sObj=this.selectObject;

for(var i=sObj.options.length;

i>index;

i--){

var temp=sObj.options[i-1];

sObj.options[i-1]=new Option();

sObj.options[i]=temp;


} sObj.options[index]=option;


} 

} ;

HTMLSelector.prototype.insertOptions=function(index,options){

if(options==undefined||!(options instanceof Array)){

options=new Array(0);


} for(var i=0;

i<options.length;

i++){

var option=options[i];

insertOption.insertOption(index+i,option);


} 

} ;

HTMLSelector.prototype.removeOption=function(index){

if(this.selectObject){

var sObj=this.selectObject;

sObj.options[index]=null;


} 

} ;

HTMLSelector.prototype.removeAll=function(){

var sObj=this.selectObject;    
if(sObj.options != undefined ){                  //add by me
var sL=sObj.options.length;

for(var i=0;

i<sL;

i++){

sObj.options[0]=null;


} 
}
} ;

HTMLSelector.prototype.removeOptionBetween=function(fromIndex,toIndex){

for(var i=fromIndex;

i<toIndex;

i++){

this.removeOption(i);


} 

} ;

HTMLSelector.prototype.contains=function(option){

if(option==undefined){

return false;


} if(this.selectObject){

var sObj=this.selectObject;

var sL=sObj.options.length;

for(var i=0;

i<sL;

i++){
var opt=sObj.options[i];

if(opt.value==option.value&&opt.text==option.text){

return true;


} 

} 

} return false;


} ;

HTMLSelector.prototype.isEmpty=function(){

if(!this.selectObject||this.selectObject.options.length==0){

return true;


} return false;


} ;

HTMLSelector.prototype.getOptionByValue=function(value){

if(this.selectObject){

var sObj=this.selectObject;

var sL=sObj.options.length;

for(var i=0;

i<sL;

i++){

var option=sObj.options[i];

if(option.value=value){

return option;


} 

} 

} 

} ;

HTMLSelector.prototype.getSelectedOptions=function(){

var options = new Array();

if(this.selectObject){

var sObj=this.selectObject;

var sL=sObj.options.length;

for(var i=0;

i<sL;

i++){

var option=sObj.options[i];

if(option.selected){

options[options.length]=option;


} 

} 

} return options;


} ;

HTMLSelector.prototype.addSelectedOptionsTo=function(toSelector,overwrite){

var options=HTMLSelectorUtils.cloneOptions(this.getSelectedOptions());

if(toSelector&&toSelector.selectObject){

toSelector.addOptions(options,overwrite);


} 

} ;

HTMLSelector.prototype.addAllOptionsTo=function(toSelector,overwrite){

var options=HTMLSelectorUtils.cloneOptions(this.selectObject.options);

if(toSelector&&toSelector.selectObject){

toSelector.addOptions(options,overwrite);


} 

} ;

HTMLSelector.prototype.removeSelectedOptions=function(){

if(this.selectObject){

var sObj=this.selectObject;

var sL=sObj.options.length;

for(var i=0;

i<sL;

i++){

var option=sObj.options[i];

if(option.selected){

sObj.options[i]=null;

sL--;

i--;


} 

} 

} 

} ;

HTMLSelector.prototype.moveUp=function(){

if(this.selectObject){

var sObj=this.selectObject;

var sL=sObj.options.length;

for(var i=0;

i<sL;

i++){

var option=sObj.options[i];

if(option.selected){

if(i!=0){

var preOption=sObj.options[i-1];

if(!preOption.selected){

option.swapNode(preOption);


} 

} 

} 

} 

} 

} ;

HTMLSelector.prototype.moveDown=function(){

if(this.selectObject){

var sObj=this.selectObject;

var sL=sObj.options.length;

for(var i=sL-1;

i>=0;

i--){

var option=sObj.options[i];

if(option.selected){

if(i!=sL-1){

var nextOption=sObj.options[i+1];

if(!nextOption.selected){

nextOption.swapNode(option);


} 

} 

} 

} 

} 

} ;

function HTMLSelectorUtils(){

this.jsjava_class="jsorg.eob.component.selector.HTMLSelectorUtils";


} HTMLSelectorUtils.toOptions=function(str){ alert(str);

var options=new Array();

var arr=eval(str);   

if(!(arr instanceof Array)){

return;


} for(var i=0;i<arr.length;i++){

var info=arr[i];

if(!(info instanceof Array)){

return;


} var value=info[0];

var text=info[1];

var label=info[2];

var selected=info[3];

var option=new Option(text,value);

options[i]=option;


} return options;


} ;

HTMLSelectorUtils.cloneOptions=function(options){

if(!options||!options.length){

return;


} var arr=new Array();

for(var i=0;

i<options.length;

i++){

var option=options[i];

var cloneOpt=new Option(option.text,option.value);

arr[i]=cloneOpt;


} return arr;


} ;

function PhotoAlbum(imgObjId){

this.jsjava_class="jsorg.eob.component.album.PhotoAlbum";

this.renders=new ArrayList();

this.pid=imgObjId;

this.pointer=0;


} PhotoAlbum.ONCE_MODE=0;

PhotoAlbum.LOOP_MODE=1;

PhotoAlbum.registry=function(albumObj){

jsjava_photo_album_instances[albumObj.pid]=albumObj;


} ;

PhotoAlbum.prototype.addRender=function(render){

this.renders.add(render);

render.setAlbum(this);


} ;

PhotoAlbum.prototype.display=function(mode,span){

if(this.interval){

return;


} this.mode=mode;

var size=this.renders.size();

jsjava_photo_album_display(this.pid,size);

this.interval=setInterval("jsjava_photo_album_display('"+this.pid+"',"+size+")",span);


} ;

PhotoAlbum.prototype.setCustomRenderMethod=function(customRenderMethod){

this.customRenderMethod=customRenderMethod;


} ;

PhotoAlbum.prototype.getCustomRenderMethod=function(){

return this.customRenderMethod;


} ;

var jsjava_photo_album_instances=new Array();

function jsjava_photo_album_display(pid,size){

var album=jsjava_photo_album_instances[pid];

var imgObj=document.getElementById(pid);

if(album.mode==PhotoAlbum.LOOP_MODE&&album.pointer==size){

album.pointer=0;


} album.renders.get(album.pointer++).execute(imgObj);


} function PhotoRender(){

this.jsjava_class="jsorg.eob.component.album.PhotoRender";


} PhotoRender.prototype.execute=function(imgObj){


} ;

function PhotoRevealTransRender(url,duration,transition,userObj){

this.jsjava_class="jsorg.eob.component.album.PhotoRender";

this.url=url;

this.duration=duration;

this.transition=transition;

this.userObj=userObj;


} PhotoRevealTransRender.prototype=new PhotoRender();

PhotoRevealTransRender.prototype.constructor=PhotoRevealTransRender;

PhotoRevealTransRender.prototype.execute=function(imgObj){

imgObj.filters.revealtrans.apply();

imgObj.src=this.url;

imgObj.filters.revealtrans.Transition=this.transition;

imgObj.filters.revealtrans.Duration=this.duration;

imgObj.filters.revealtrans.play();

var customRenderMethod=this.getAlbum().getCustomRenderMethod();

if(customRenderMethod){

eval(customRenderMethod+"(this.userObj)");


} 

} ;

PhotoRevealTransRender.prototype.setURL=function(url){

this.url=url;


} ;

PhotoRevealTransRender.prototype.getURL=function(){

return this.url;


} ;

PhotoRevealTransRender.prototype.setDuration=function(duration){

this.duration=duration;


} ;

PhotoRevealTransRender.prototype.getDuration=function(){

return this.duration;


} ;

PhotoRevealTransRender.prototype.setTransition=function(transition){

this.transition=transition;


} ;

PhotoRevealTransRender.prototype.getTransition=function(){

return this.transition;


} ;

PhotoRevealTransRender.prototype.setAlbum=function(album){

this.album=album;


} ;

PhotoRevealTransRender.prototype.getAlbum=function(){

return this.album;


} ;

function TitledBorder(legend,content,borderClass,legendClass,legendAlign){

this.jsjava_class="jsorg.eob.component.border.TitledBorder";

this.legend=legend;

if(legend==undefined){

this.legend="";


} this.content=content;

this.borderClass=borderClass;

this.legendClass=legendClass;

this.legendAlign=legendAlign;


} TitledBorder.LEFT_ALIGNMENT="left";

TitledBorder.CENTER_ALIGNMENT="center";

TitledBorder.RIGHT_ALIGNMENT="right";

TitledBorder.prototype.setLegend=function(legend){

this.legend=legend;


} ;

TitledBorder.prototype.setContent=function(content){

this.content=content;


} ;

TitledBorder.prototype.setBorderClass=function(borderClass){

this.borderClass=borderClass;


} ;

TitledBorder.prototype.setLegendClass=function(legendClass){

this.legendClass=legendClass;


} ;

TitledBorder.prototype.setLegendAlignment=function(legendAlign){

this.legendAlign=legendAlign;


} ;

TitledBorder.prototype.toHTML=function(){

var html="<fieldset class='"+this.borderClass+"'><legend class='"+this.legendClass+"' align='"+this.legendAlign+"'>"+this.legend+"</legend>"+this.content+"</fieldset>";

return html;


} ;

function VisualCondition(conditionSeed,visibles){

this.jsjava_class="jsorg.eob.component.trigger.VisualCondition";

this.conditionSeed=conditionSeed;

this.visibles=visibles;


} function HTMLSplitPane(newOrientation,leftPaneId,rightPaneId,midPaneId){

this.jsjava_class="jsorg.eob.component.splitor.HTMLSplitPane";

if(!StringUtils.isNumeric(newOrientation)||newOrientation!=0&&newOrientation!=1){

throw new IllegalArgumentException(IllegalArgumentException.ERROR,"The split span's orientation must be 0 or 1!");


} this.newOrientation=newOrientation;

this.leftPaneId=leftPaneId;

this.midPaneId=midPaneId;

this.isResizing=false;

var uuid=RandomStringUtils.randomAlphabetic(5);

var leftPane=document.getElementById(leftPaneId);

var rightPane=document.getElementById(rightPaneId);

var midPane=document.getElementById(midPaneId);

if(newOrientation==0){

midPane.style.cursor="col-resize";

EventUtils.addDomEvent(midPane,"mousedown",new Function("var instance=HTMLSplitPane.instances['"+uuid+"'];var formatEvent = EventUtils.getEvent();origX=formatEvent.pageX;instance.isResizing=true;var obj = document.getElementById('jsjava_float_split_bar');obj.style.display='inline';"),false);

EventUtils.addDomEvent(document,"mousemove",new Function("var instance=HTMLSplitPane.instances['"+uuid+"'];if(instance.isResizing){var formatEvent = EventUtils.getEvent();var resizeX = formatEvent.pageX;var obj = document.getElementById('jsjava_float_split_bar');var sObj= document.getElementById('"+midPaneId+"');obj.innerHTML=sObj.innerHTML;var objRect=DocumentUtils.getElementRectangle(sObj);obj.style.top=objRect.getY()+document.body.scrollTop;obj.style.left=resizeX-objRect.getWidth()/2;obj.style.height=objRect.getHeight();}"),false);

EventUtils.addDomEvent(document,"mouseup",new Function("var instance=HTMLSplitPane.instances['"+uuid+"'];if(instance.isResizing){var obj = document.getElementById('jsjava_float_split_bar');obj.style.display='none';var uObj = document.getElementById('"+leftPaneId+"');var uObjRect=DocumentUtils.getElementRectangle(uObj);var uObjBottomX=uObjRect.getX()+document.body.scrollLeft;var formatEvent = EventUtils.getEvent();var floatBarX=formatEvent.pageX;var addWidth=floatBarX-origX;var dObj = document.getElementById('"+rightPaneId+"');var dObjRect=DocumentUtils.getElementRectangle(dObj);try{DocumentUtils.setDomWidth(uObj,uObjRect.getWidth()+addWidth);DocumentUtils.setDomWidth(dObj,dObjRect.getWidth()-addWidth);}catch(e){}var sObj= document.getElementById('"+midPaneId+"');EventUtils.releaseCapture(sObj);}instance.isResizing=false;"),false);


} else{

midPane.style.cursor="row-resize";

EventUtils.addDomEvent(midPane,"mousedown",new Function("var instance=HTMLSplitPane.instances['"+uuid+"'];var formatEvent = EventUtils.getEvent();instance.isResizing=true;var obj = document.getElementById('jsjava_float_split_bar');obj.style.display='inline';"),false);

EventUtils.addDomEvent(document,"mousemove",new Function("var instance=HTMLSplitPane.instances['"+uuid+"'];if(instance.isResizing){var formatEvent = EventUtils.getEvent();var resizeY = formatEvent.pageY;var obj = document.getElementById('jsjava_float_split_bar');var sObj= document.getElementById('"+midPaneId+"');obj.innerHTML=sObj.innerHTML;var objRect=DocumentUtils.getElementRectangle(sObj);obj.style.left=objRect.getX();obj.style.top=resizeY-objRect.getHeight()/2;obj.style.width=objRect.getWidth();}"),false);

EventUtils.addDomEvent(document,"mouseup",new Function("var instance=HTMLSplitPane.instances['"+uuid+"'];if(instance.isResizing){var obj = document.getElementById('jsjava_float_split_bar');obj.style.display='none';var uObj = document.getElementById('"+leftPaneId+"');var uObjRect=DocumentUtils.getElementRectangle(uObj);var uObjBottomY=uObjRect.getY()+uObjRect.getHeight()+document.body.scrollTop;var formatEvent = EventUtils.getEvent();var floatBarY=formatEvent.pageY;var addHeight=floatBarY-uObjBottomY;var dObj = document.getElementById('"+rightPaneId+"');var dObjRect=DocumentUtils.getElementRectangle(dObj);try{DocumentUtils.setDomHeight(uObj,uObjRect.getHeight()+addHeight);DocumentUtils.setDomHeight(dObj,dObjRect.getHeight()-addHeight);}catch(e){}var sObj= document.getElementById('"+midPaneId+"');EventUtils.releaseCapture(sObj);}instance.isResizing=false;"),false);


} HTMLSplitPane.instances[uuid]=this;


} HTMLSplitPane.HORIZONTAL_SPLIT = 0;

HTMLSplitPane.VERTICAL_SPLIT = 1;

HTMLSplitPane.instances=new Array();

HTMLSplitPane.prototype.show=function(){

if(!document.getElementById("jsjava_float_split_bar")){

var elem=null;

if(BrowserUtils.isIE()){

elem=document.createElement("<div id='jsjava_float_split_bar' style='position:absolute;left:3000;top:3000;display:none;'>&nbsp;</div>");


} else{

elem=document.createElement("div");

elem.setAttribute("id","jsjava_float_split_bar");

elem.setAttribute("style","position:absolute;left:3000;top:3000;display:none;");


} document.body.appendChild(elem);


} 

} ;

 