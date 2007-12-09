<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<head>
<title>Blog Remover - blogremover - 发表文章 - 和讯博客</title>
		<META NAME="description" CONTENT="国内最好的博客系统之一，速度超快，功能强大，傻瓜化操作，模板和个性化定制功能极丰富，还配备了网摘和超酷的图片博客功能（容量不限）。">
		<META NAME="keywords" CONTENT="和讯,博客,blog,日志,网络日志,财经博客,财经社区,个人主页,blogger,网摘,图片,博客文章,博客日志"> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="http://home.hexun.com/manage/style/right.css" rel="stylesheet" type="text/css"/>
<SCRIPT language="JavaScript" src="/inc/globaltest.js" type="text/javascript"></SCRIPT>
<SCRIPT language="JavaScript" src="/inc/api.js" type="text/javascript"></SCRIPT>

<SCRIPT language="JavaScript" src="/inc/xmlhttprequest.js" type="text/javascript"></SCRIPT>
<SCRIPT language="javascript" src="/inc/homeway.js"></SCRIPT>
<script language="javascript" type="text/javascript">
        //输入信息的文本框
        var txtInput;
        //下拉表当前选中项的索引 
        var currentIndex = -1; 
        
        //定义一个定长数组，用于判定是否有添入重复项
        //var idList = new Array(0,0,0,0,0);
       
        //初始化参数,和下拉表位置
        function initStatus()
        {
            //alert(document.getElementById("textRelateUser"));
            txtInput = document.getElementById("textRelateUser");
            //设置下拉表 相对于 文本输入框的位置 
            setPosition();
        }
        
        //设置下拉表 相对于 文本输入框的位置
        function setPosition()
        {
            var width = document.getElementById("textRelateUser").offsetWidth;            
            var left = getLength("offsetLeft");            
            var top = getLength("offsetTop") + txtInput.offsetHeight;            
           
            document.getElementById("relateContent").style.paddingLeft = "6px";
            document.getElementById("relateContent").style.marginTop = "-1px";
            document.getElementById("relateContent").style.left = left + "px";
            document.getElementById("relateContent").style.top = top + "px"; 
            document.getElementById("relateContent").style.width = width + "px";
        } 
        
       //获取对应属性的长度 
        function getLength(attr)
        {
            var offset = 0;
            var item = txtInput;
            while (item)
            {
                offset += item[attr];
                item = item.offsetParent;
            } 
            return offset; 
        } 
        
        //移除图片显示
        function removeShowImg(id)
        {
             var removeDom = document.getElementById("dl_"+id);
             removeDom.parentNode.removeChild(removeDom);
             
             //将“引用好友是否改变”的标志位赋值，表明改变过此值
             if(document.getElementById("valueIsChanged"))
             {
                 document.getElementById("valueIsChanged").value = 10;
             }
        }

        //自动完成
        function autoComplete(userid,eve)
        {            
            //如果按下 向上, 向下 或 回车
            if (eve.keyCode == 38 || eve.keyCode == 40 || eve.keyCode == 13)
            { 
                //选择当前项 
                selItemByKey(eve);
            } 
            else //向服务器发送请求
            { 
                //如果值为空 
                if (document.getElementById("textRelateUser").value == "")
                {
                    document.getElementById("relateContent").style.display='none'; 
                    return;
                } 
                //恢复下拉选择项为 -1 
                currentIndex = -1; 
                //alert('0');
                //开始请求
                initRelateUser_div(document.getElementById("textRelateUser").value,userid);
                //alert(document.getElementById("relateContent"));
            } 
        }
        
        //通过键盘选择下拉项 
        function selItemByKey(eve)
        {
            if(document.getElementById("tblContent"))
            {
                //下拉表 
                var tbl = document.getElementById("tblContent"); 
                
                //下拉表的项数
                var maxRow =tbl.rows.length; 
             
                //向上 
                if (eve.keyCode == 38 && currentIndex > 0)
                {
                    currentIndex--;
                } 
                //向下 
                else if (eve.keyCode == 40 && currentIndex < maxRow-1)
                {
                    currentIndex++;
                }
                //回车 
                else if (eve.keyCode == 13)
                {
                    selEnterValue(event);
                    return;
                }
             
                clearColor();

                //向输入框内加入文本值以及隐藏表单
                               
                if(maxRow > 0)
                {
                    var cells = tbl.rows[currentIndex].cells;
                        
                    var showText = "";
                        
                    spanText = cells[0].innerHTML;
                        
                    if(document.getElementById("valueSpan"))
                    {
                       document.getElementById("valueSpan").innerHTML = spanText;
                    }
                    
                    showText = document.getElementById("username_"+currentIndex).value;
                        
                    document.getElementById("textRelateUser").value = showText; 
                   
                    //设置当前项背景颜色为blue 标记选中 
                    tbl.rows[currentIndex].style.backgroundColor = "InfoBackground";                     
                    
                }
            }
        }
        
        function colorLine(line)
        {
            line.className = "selected";
        }
        
        function discolorLine(line)
        {
            line.className = "";
        }
        
        function testValue(eve,userid)
        {
            //输入回车，将内容显示
            if(eve.keyCode == 13)
            {
                selEnterValue();
                insertUserLogoImg(userid);
                document.getElementById("textRelateUser").focus();
                return;
            }
            return;
        }
        
        //添加图片显示
        function insertUserLogoImg(userid)
        {            
            var showItem = document.getElementById("showItemContent");
           
            var nodelength = 0;
            var lastID = "";
            
            for(var x = 0 ; x < showItem.childNodes.length ; x++)
            {
                if(showItem.childNodes[x].nodeName == "DL")
                {
                    lastID = showItem.childNodes[x].id;
                    nodelength++;
                }
            }                     
            
            if(document.getElementById("textRelateUser").value == "")
            {
                document.getElementById("textRelateUser").focus();
                alert("请输入好友名字！");
                return;
            }
                
            var textValue = document.getElementById("textRelateUser").value;
            if(document.getElementById("valueSpan") && (document.getElementById("valueSpan").innerHTML!=""))
            {                
                var tempForm = document.getElementById("valueSpan").childNodes;
                                
                //如果子节点中包含有form值，则直接将值赋给显示模块。
                if(tempForm.length >= 5)
                {
                    //判断是否已经被添加过
                    var status = true;   
                    
                    if(nodelength > 0)
                    {
                        var array1 = new Array(nodelength);
                        //建立一个数组，比较是否有存在的username
                        var temp = new Array(nodelength);
                        var count = 0;
                        var len = document.form1.elements.length;
                        for(var y = 0 ; y < len ; y++)
                        {
                            if(document.form1.elements[y].name == "postselectbyid")
                            {
                                temp[count] = document.form1.elements[y].value;
                                count++;
                            }
                        }                        
                        
                        for(var i=0 ; i < temp.length ; i++)
                        {    
                             if(document.getElementById("postusername_"+temp[i]))  
                             {                       
                                if(textValue == document.getElementById("postusername_"+temp[i]).value)
                                {
                                    status = false;
                                    break;
                                }
                             }
                        }
                    }
                    
                    if(status == true)
                    {                        
                        var idValue = 0;
                                            
                        if(tempForm[5].nodeName == "INPUT")
                        {
                            idValue = tempForm[5].value;
                        }           
                        //生成子节点ID                        
                        var sendID = 1;
                        if(lastID != "")
                        {                            
                            sendID = lastID.substring(3,lastID.length);
                            sendID++;                            
                        }    
                        //生成子节点
                        createChild(sendID,idValue);
                            
                        //将“引用好友是否改变”的标志位赋值，表明改变过此值
                        if(document.getElementById("valueIsChanged"))
                        {
                            document.getElementById("valueIsChanged").value = 10;
                        }
                    }
                    else
                    {
                        document.getElementById("textRelateUser").value = "";
                        //隐藏生成下拉框的显示
                        document.getElementById('relateContent').style.display = "none";                        
                        alert("这个好友您已经加到列表里了。");
                        document.getElementById('textRelateUser').focus();
                        return;
                    }
                }
                else
                {
                    checkValueIsFriend(textValue,nodelength+1,userid);
                    document.getElementById("textRelateUser").focus();
                                    
                    //将“引用好友是否改变”的标志位赋值，表明改变过此值
                    if(document.getElementById("valueIsChanged"))
                    {
                        document.getElementById("valueIsChanged").value = 10;
                    }
                }
            }
            else
            {
                checkValueIsFriend(textValue,nodelength+1,userid);
                document.getElementById("textRelateUser").focus();
                                    
                //将“引用好友是否改变”的标志位赋值，表明改变过此值
                if(document.getElementById("valueIsChanged"))
                {
                    document.getElementById("valueIsChanged").value = 10;
                }
            }
            
            
            //清空valueSpan的值
            if(document.getElementById("valueSpan"))
            {
                document.getElementById("valueSpan").innerHTML = "";
            }
            
            document.getElementById("textRelateUser").value = "";
            //隐藏生成下拉框的显示
            document.getElementById('relateContent').style.display = "none";
            document.getElementById('textRelateUser').focus();
        }
        
        //回调时生成显示部分的子节点
        function createInitChild(itemid,id,username,blogname,logourl)
        {
                //生成dl子节点
                var showItem = document.getElementById("showItemContent");
                var createItem = document.createElement("dl");
                createItem.setAttribute("id","dl_"+itemid);               
                showItem.appendChild(createItem);
                document.getElementById("dl_"+itemid).style.cssText = "float:left;margin:0;padding:0";
               
                //生成dt子节点
                var dlItem = document.getElementById("dl_"+itemid);
                var dtItem1 = document.createElement("dt");
                dtItem1.setAttribute("id","dt_"+itemid);
                dlItem.appendChild(dtItem1);
               
                //生成dt下的子节点
                var dtItem = document.getElementById("dt_"+itemid);
                var dlcreateItem = document.createElement("span");
                dlcreateItem.setAttribute("id","postContent_"+itemid);
                //dlcreateItem.setAttribute("style","display:none;");
                dtItem.appendChild(dlcreateItem);
                document.getElementById("dt_"+itemid).style.cssText = "float:left;width:33px;margin:0;padding:0";
                document.getElementById("postContent_"+itemid).style.display = "none";    
                var dtcreateA = document.createElement("a");
                dtcreateA.setAttribute("id","a_"+itemid);
                dtcreateA.setAttribute("href","http://hexun.com/"+blogname+"/default.html");
                dtcreateA.setAttribute("target","_blank");
                dtItem.appendChild(dtcreateA); 
                var Acreateimg = document.createElement("img");
                Acreateimg.setAttribute("id","img_"+itemid);
                Acreateimg.setAttribute("src",logourl);
                Acreateimg.setAttribute("width","33");
                Acreateimg.setAttribute("height","33");
                Acreateimg.setAttribute("alt","");
                document.getElementById("a_"+itemid).appendChild(Acreateimg);                               
                                
                //生成dd下的子节点
                var ddItem = document.createElement("dd");
                ddItem.setAttribute("id","dd_"+itemid);
                dlItem.appendChild(ddItem);
                document.getElementById("dd_"+itemid).style.cssText = "float:left;width:50px;overflow:hidden;height:20px;line-height:20px;padding-left:8px;margin:0";
                var ddItem1 = document.createElement("dd");
                ddItem1.setAttribute("id","dd_"+itemid+"_"+itemid);
                dlItem.appendChild(ddItem1);
                document.getElementById("dd_"+itemid+"_"+itemid).style.cssText = "float:left;width:50px;overflow:hidden;height:20px;line-height:20px;padding-left:8px;margin:0";
                var textUserName = document.createTextNode(username);
                document.getElementById("dd_"+itemid).appendChild(textUserName);
                
                var dditemA = document.createElement("a");
                dditemA.setAttribute("href","javascript:removeShowImg(" + itemid + ");");
                dditemA.setAttribute("id","dd_a_"+itemid);
                document.getElementById("dd_"+itemid+"_"+itemid).appendChild(dditemA);
                var dditemImg = document.createElement("img");
                dditemImg.setAttribute("src","../images/add02.gif");                
                document.getElementById("dd_a_"+itemid).appendChild(dditemImg);
                                
                //生成提交的span下的子节点
                if(navigator.appName == "Netscape")
                {
                    var spanelement = document.getElementById("postContent_"+itemid);
                    var spaninputid = document.createElement("input");
                    spaninputid.setAttribute("type","hidden");
                    spaninputid.setAttribute("name","postid_"+itemid);
                    spaninputid.setAttribute("id","postid_"+itemid);                
                    spaninputid.setAttribute("value",id);
                    spanelement.appendChild(spaninputid);
                    var spaninputusername = document.createElement("input");
                    spaninputusername.setAttribute("type","hidden");
                    spaninputusername.setAttribute("name","postusername_"+itemid);  
                    spaninputusername.setAttribute("id","postusername_"+itemid);                
                    spaninputusername.setAttribute("value",username);
                    spanelement.appendChild(spaninputusername);
                    var spaninputblogname = document.createElement("input");
                    spaninputblogname.setAttribute("type","hidden");
                    spaninputblogname.setAttribute("name","postblogname_"+itemid);
                    spaninputblogname.setAttribute("id","postblogname_"+itemid);
                    spaninputblogname.setAttribute("value",blogname);
                    spanelement.appendChild(spaninputblogname);
                    var spaninputpostlogourl = document.createElement("input");
                    spaninputpostlogourl.setAttribute("type","hidden");
                    spaninputpostlogourl.setAttribute("name","postlogourl_"+itemid);   
                    spaninputpostlogourl.setAttribute("id","postlogourl_"+itemid);                
                    spaninputpostlogourl.setAttribute("value",logourl);
                    spanelement.appendChild(spaninputpostlogourl);
                    var spaninputselectbyid = document.createElement("input");
                    spaninputselectbyid.setAttribute("type","hidden");
                    spaninputselectbyid.setAttribute("name","postselectbyid");
                    spaninputselectbyid.setAttribute("value",itemid);
                    spanelement.appendChild(spaninputselectbyid);
                }
                else
                {
                    var spanelement = document.getElementById("postContent_"+itemid);
                    var spaninputid = document.createElement("<input name=postid_"+itemid+" />");
                    spaninputid.setAttribute("type","hidden");
                    spaninputid.setAttribute("id","postid_"+itemid);                
                    spaninputid.setAttribute("value",id);
                    spanelement.appendChild(spaninputid);
                    var spaninputusername = document.createElement("<input name=postusername_"+itemid+" />");
                    spaninputusername.setAttribute("type","hidden");
                    spaninputusername.setAttribute("id","postusername_"+itemid);
                    spaninputusername.setAttribute("value",username);
                    spanelement.appendChild(spaninputusername);
                    var spaninputblogname = document.createElement("<input name=postblogname_"+itemid+" />");
                    spaninputblogname.setAttribute("type","hidden");
                    spaninputblogname.setAttribute("id","postblogname_"+itemid);
                    spaninputblogname.setAttribute("value",blogname);
                    spanelement.appendChild(spaninputblogname);
                    var spaninputpostlogourl = document.createElement("<input name=postlogourl_"+itemid+" />");
                    spaninputpostlogourl.setAttribute("type","hidden");
                    spaninputpostlogourl.setAttribute("id","postlogourl_"+itemid);
                    spaninputpostlogourl.setAttribute("value",logourl);
                    spanelement.appendChild(spaninputpostlogourl);
                    var spaninputselectbyid = document.createElement("<input name=postselectbyid />");
                    spaninputselectbyid.setAttribute("type","hidden");
                    spaninputselectbyid.setAttribute("value",itemid);
                    spanelement.appendChild(spaninputselectbyid);
                }
        }
        
        //动态生成显示部分的子节点
        function createChild(itemid,id)  
        {              
                //生成dl子节点
                var showItem = document.getElementById("showItemContent");
                var createItem = document.createElement("dl");
                createItem.setAttribute("id","dl_"+itemid);                
                showItem.appendChild(createItem);
                document.getElementById("dl_"+itemid).style.cssText = "float:left;margin:0;padding:0";
                
                //生成dt子节点
                var dlItem = document.getElementById("dl_"+itemid);
                var dtItem1 = document.createElement("dt");
                dtItem1.setAttribute("id","dt_"+itemid);
                dlItem.appendChild(dtItem1);            
                document.getElementById("dt_"+itemid).style.cssText = "float:left;width:33px;margin:0;padding:0";
                
                //生成dt下的子节点
                var dtItem = document.getElementById("dt_"+itemid);
                var dlcreateItem = document.createElement("span");
                dlcreateItem.setAttribute("id","postContent_"+itemid);
                //dlcreateItem.setAttribute("style","display:none;");
                dtItem.appendChild(dlcreateItem);
                
                document.getElementById("postContent_"+itemid).style.display = "none";
                                                               
                var dtcreateA = document.createElement("a");
                dtcreateA.setAttribute("id","a_"+itemid);
                dtcreateA.setAttribute("href","http://hexun.com/"+document.getElementById("blogname_"+id).value+"/default.html");
                dtcreateA.setAttribute("target","_blank");
                dtItem.appendChild(dtcreateA);               
                                      
                var Acreateimg = document.createElement("img");
                Acreateimg.setAttribute("id","img_"+itemid);
                Acreateimg.setAttribute("src",document.getElementById("logourl_"+id).value);
                Acreateimg.setAttribute("width","33");
                Acreateimg.setAttribute("height","33");
                Acreateimg.setAttribute("alt","");
                document.getElementById("a_"+itemid).appendChild(Acreateimg);                               
                                
                //生成dd下的子节点
                var ddItem = document.createElement("dd");
                ddItem.setAttribute("id","dd_"+itemid);
                dlItem.appendChild(ddItem);
                document.getElementById("dd_"+itemid).style.cssText = "float:left;width:50px;overflow:hidden;height:20px;line-height:20px;padding-left:8px;margin:0";
                var ddItem1 = document.createElement("dd");
                ddItem1.setAttribute("id","dd_"+itemid+"_"+itemid);
                dlItem.appendChild(ddItem1);
                document.getElementById("dd_"+itemid+"_"+itemid).style.cssText = "float:left;width:50px;overflow:hidden;height:20px;line-height:20px;padding-left:8px;margin:0";
                var textUserName = document.createTextNode(document.getElementById("username_"+id).value);
                document.getElementById("dd_"+itemid).appendChild(textUserName);
                
                var dditemA = document.createElement("a");
                dditemA.setAttribute("href","javascript:removeShowImg(" + itemid + ");");
                dditemA.setAttribute("id","dd_a_"+itemid);
                document.getElementById("dd_"+itemid+"_"+itemid).appendChild(dditemA);
                var dditemImg = document.createElement("img");
                dditemImg.setAttribute("src","../images/add02.gif");                
                document.getElementById("dd_a_"+itemid).appendChild(dditemImg);
                               
                //生成提交的span下的子节点
                if(navigator.appName == "Netscape")
                {
                    var spanelement = document.getElementById("postContent_"+itemid);
                    var spaninputid = document.createElement("input");
                    spaninputid.setAttribute("type","hidden");
                    spaninputid.setAttribute("name","postid_"+itemid);
                    spaninputid.setAttribute("id","postid_"+itemid);                
                    spaninputid.setAttribute("value",document.getElementById("id_"+id).value);
                    spanelement.appendChild(spaninputid);
                    var spaninputusername = document.createElement("input");
                    spaninputusername.setAttribute("type","hidden");
                    spaninputusername.setAttribute("name","postusername_"+itemid);  
                    spaninputusername.setAttribute("id","postusername_"+itemid);                
                    spaninputusername.setAttribute("value",document.getElementById("username_"+id).value);
                    spanelement.appendChild(spaninputusername);
                    var spaninputblogname = document.createElement("input");
                    spaninputblogname.setAttribute("type","hidden");
                    spaninputblogname.setAttribute("name","postblogname_"+itemid);
                    spaninputblogname.setAttribute("id","postblogname_"+itemid);
                    spaninputblogname.setAttribute("value",document.getElementById("blogname_"+id).value);
                    spanelement.appendChild(spaninputblogname);
                    var spaninputpostlogourl = document.createElement("input");
                    spaninputpostlogourl.setAttribute("type","hidden");
                    spaninputpostlogourl.setAttribute("name","postlogourl_"+itemid);   
                    spaninputpostlogourl.setAttribute("id","postlogourl_"+itemid);                
                    spaninputpostlogourl.setAttribute("value",document.getElementById("logourl_"+id).value);
                    spanelement.appendChild(spaninputpostlogourl);
                    var spaninputselectbyid = document.createElement("input");
                    spaninputselectbyid.setAttribute("type","hidden");
                    spaninputselectbyid.setAttribute("name","postselectbyid");
                    spaninputselectbyid.setAttribute("value",itemid);
                    spanelement.appendChild(spaninputselectbyid);
                }
                else
                {
                    var spanelement = document.getElementById("postContent_"+itemid);
                    var spaninputid = document.createElement("<input name=postid_"+itemid+" />");
                    spaninputid.setAttribute("type","hidden");
                    spaninputid.setAttribute("id","postid_"+itemid);                
                    spaninputid.setAttribute("value",document.getElementById("id_"+id).value);
                    spanelement.appendChild(spaninputid);
                    var spaninputusername = document.createElement("<input name=postusername_"+itemid+" />");
                    spaninputusername.setAttribute("type","hidden");
                    spaninputusername.setAttribute("id","postusername_"+itemid);                
                    spaninputusername.setAttribute("value",document.getElementById("username_"+id).value);
                    spanelement.appendChild(spaninputusername);
                    var spaninputblogname = document.createElement("<input name=postblogname_"+itemid+" />");
                    spaninputblogname.setAttribute("type","hidden");
                    spaninputblogname.setAttribute("id","postblogname_"+itemid);
                    spaninputblogname.setAttribute("value",document.getElementById("blogname_"+id).value);
                    spanelement.appendChild(spaninputblogname);
                    var spaninputpostlogourl = document.createElement("<input name=postlogourl_"+itemid+" />");
                    spaninputpostlogourl.setAttribute("type","hidden");
                    spaninputpostlogourl.setAttribute("id","postlogourl_"+itemid);                
                    spaninputpostlogourl.setAttribute("value",document.getElementById("logourl_"+id).value);
                    spanelement.appendChild(spaninputpostlogourl);
                    var spaninputselectbyid = document.createElement("<input name=postselectbyid />");
                    spaninputselectbyid.setAttribute("type","hidden");
                    spaninputselectbyid.setAttribute("value",itemid);
                    spanelement.appendChild(spaninputselectbyid);
                }                           
        }              
        
        //清除下拉项的背景颜色 
        function clearColor()
        {
             var tbl = document.getElementById("tblContent");
             
             for (var i = 0; i < tbl.rows.length; i++)
             {
                 tbl.rows[i].style.backgroundColor = ""; 
             } 
        } 
        
        //选择下拉表中当前项的值 ,用于按回车选中当前项的值
        function selEnterValue()
        {
            var text = "";
            
            if(currentIndex >= 0)
            {
                document.getElementById("textRelateUser").value = document.getElementById("username_"+currentIndex).value;
                document.getElementById("valueSpan").innerHTML = document.getElementById("username_"+currentIndex).value+"<input id='id_"+currentIndex+"' name='id_"+currentIndex+"' type=hidden value="+document.getElementById("id_"+currentIndex).value+" /><input id='username_"+currentIndex+"' name='username_"+currentIndex+"' type=hidden value="+document.getElementById("username_"+currentIndex).value+" /><input id='blogname_"+currentIndex+"' name='blogname_"+currentIndex+"' type=hidden value="+document.getElementById("blogname_"+currentIndex).value+" /><input id='logourl_"+currentIndex+"' name='logourl_"+currentIndex+"' type=hidden value="+document.getElementById("logourl_"+currentIndex).value+" /><input name='selectbyid' type=hidden value="+currentIndex+" />";
                
            }
        }
        
        //选择下拉表中当前项的值 ,用于鼠标单击选中当前项的值
        function selValue(event)
        {
            var text = "";
            if(event.srcElement.innerText)
            {
                text = event.srcElement.innerText;
                document.getElementById("textRelateUser").value = text; 
                document.getElementById("valueSpan").innerHTML = event.srcElement.innerHTML;
            }
            else
            {
                text = event.srcElement.innerHTML;
                document.getElementById("valueSpan").innerHTML = text; 
                var nodes = document.getElementById("valueSpan").childNodes;
                var selectid = 0;
                
                if(nodes[5].nodeName == "INPUT")
                {
                    selectid = nodes[5].value;
                }
                                
                if(document.getElementById("username_"+selectid))
                {
                    document.getElementById("textRelateUser").value = document.getElementById("username_"+selectid).value;
                }
                else
                {
                    document.getElementById("textRelateUser").value = "";
                }                
            }
            
            document.getElementById("textRelateUser").focus();
            initList(); 
        } 
        
        //文本框失去焦点时 设置下拉表可见性 
        function setDisplay()
        {
            if(document.getElementById("textRelateUser"))
            {
                if(document.getElementById("textRelateUser").value != "")
                {
                    return;
                }
            }
            
            initList();
            
        } 
       
        function initList()
        {
            document.getElementById("relateContent").style.display='none'; 
            document.getElementById("relateContent").innerHTML = "";
            currentIndex = -1;
        } 

</script>
</head>
<body onload=initStatus();>
<!--  页面:开始  -->
<form name="form1" method="post" action="../single/templete/module18/postarticle.aspx?blogname=blogremover&amp;articleid=4574623" id="form1" target="_parent">
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="/wEPDwULLTE0ODgxODY0MTEPZBYCZg9kFgICAQ9kFgpmDxYCHgRUZXh0BQznvJbovpHmlofnq6BkAgIPEA8WBh4NRGF0YVRleHRGaWVsZAUMQ2F0ZWdvcnlOYW1lHg5EYXRhVmFsdWVGaWVsZAUKQ2F0ZWdvcnlJRB4LXyFEYXRhQm91bmRnFgIeCG9uY2hhbmdlBT9qYXZhc2NyaXB0OmRvQ2hlY2tsaXN0KHRoaXMub3B0aW9uc1t0aGlzLnNlbGVjdGVkSW5kZXhdLnZhbHVlKTsQFQE2Li4uLi4u6YCJ5oup5YiG57G75oiW5Zyo5LiL5pa56L6T5YWl5paw55qE5YiG57G7Li4uLi4uFQEBMBQrAwFnZGQCAw8PZBYCHgpvbmZvY3Vzb3V0BSJqYXZhc2NyaXB0OmNoZWNrVmFsdWUodGhpcy52YWx1ZSk7ZAIEDxAPFgIeDEJyZWFrRWxlbWVudAspZkN1dGVFZGl0b3IuQnJlYWtFbGVtZW50LCBDdXRlRWRpdG9yLCBWZXJzaW9uPTUuMC4wLjAsIEN1bHR1cmU9bmV1dHJhbCwgUHVibGljS2V5VG9rZW49Mzg1OGFhNjgwMmIxMjIzYQJkBQjmtYvor5UyIBYOHgdDdWx0dXJlBQV6aC1DTh4JRmlsZXNQYXRoBRsvQ3V0ZVNvZnRfQ2xpZW50L0N1dGVFZGl0b3IeElJlbmRlclJpY2hEcm9wRG93bgUEVHJ1ZR4JVGhlbWVUeXBlBQpPZmZpY2UyMDAzHhJTZWN1cml0eVBvbGljeUZpbGUFQS9DdXRlU29mdF9DbGllbnQvQ3V0ZUVkaXRvci9Db25maWd1cmF0aW9uL1NlY3VyaXR5L2RlZmF1bHQuY29uZmlnHg1BdXRvQ29uZmlndXJlBQdEZWZhdWx0HhFDb25maWd1cmF0aW9uUGF0aGVkAg8PPCsACQEADxYEHghEYXRhS2V5cxYAHgtfIUl0ZW1Db3VudGZkZBgBBR5fX0NvbnRyb2xzUmVxdWlyZVBvc3RCYWNrS2V5X18WAgUlTmV3RWRpdEFydGljbGUxOkFjY2VwdENvbW1lbnRDaGVja2JveAUgTmV3RWRpdEFydGljbGUxOlN0aWNrT3V0Q2hlY2tib3iIuAdrC+2SnM99riY88MF+wNd9Xg==" />

<script language=javascript src='/CuteSoft_Client/CuteEditor/CuteEditorconstants.js'></script>
	

<script language="javascript" src="http://post.blog.hexun.com/js/1.js"></script>

	
<div style="margin-left:8px; margin-right:8px;">
  <div class="r_b">
	  <div class="r_title1">编辑文章</div>
	  <div class="r_c">
	    <div>标　　题：
	      <input name="NewEditArticle1:TitleTextbox" type="text" value="测试2" id="NewEditArticle1_TitleTextbox" class="input1" style="width:450px;" /><input type=hidden id="inputsummit" value=1 />
	    <span class="f_gray1"> 　(必填，最长250字)</span></div>
	    <div>文章分类：
	      <select name="NewEditArticle1:CategoryList" id="NewEditArticle1_CategoryList" Class="input1" onchange="javascript:doChecklist(this.options[this.selectedIndex].value);">

	<option selected="selected" value="0">......选择分类或在下方输入新的分类......</option>

</select><br />
        <span class="f_gray1" style="padding-left:67px;"><input name="NewEditArticle1:NewCategoryTextbox" type="text" id="NewEditArticle1_NewCategoryTextbox" class="input1" onfocusout="javascript:checkValue(this.value);" style="width:450px" /></span></div>
	    <div><div id="all_editor" style="float:left">

 <!-- CuteEditor NewEditArticle1:ContentSpaw Begin --> 

<input type=hidden name='NewEditArticle1:ContentSpaw:ClientState' value=''/><input type=hidden name='NewEditArticle1:ContentSpaw:PostBackHandler'/><script language=javascript src='/CuteSoft_Client/CuteEditor/CuteEditor_Gecko.js.aspx?settinghash=f30c8e2d'></script><link rel="stylesheet" href="/CuteSoft_Client/CuteEditor/Themes/Office2003/style_gecko.css.aspx?EditorID=CE_NewEditArticle1_ContentSpaw_ID" /><table Width="808px" Height="320px" _IsCuteEditor="True" _ClientID="CE_NewEditArticle1_ContentSpaw_ID" _UniqueID="NewEditArticle1:ContentSpaw" _FrameID="CE_NewEditArticle1_ContentSpaw_ID_Frame" _ToolBarID="CE_NewEditArticle1_ContentSpaw_ID_ToolBar" _HiddenID="NewEditArticle1:ContentSpaw" _StateID="NewEditArticle1:ContentSpaw:ClientState" _PostBackHandlerID="NewEditArticle1:ContentSpaw:PostBackHandler" _TemplateUrl="/CuteSoft_Client/CuteEditor/Template.aspx?Referrer=http%3a%2f%2fpost.blog.hexun.com%2fsingle%2ftemplete%2fmodule18%2fpostarticle.aspx%3fblogname%3dblogremover%26articleid%3d4574623" ThemePath="/CuteSoft_Client/CuteEditor/Themes/Office2003/" ResourceDir="/CuteSoft_Client/CuteEditor" Theme="Office2003" ActiveTab="Edit" BreakElement="P" ToggleBorder="True" RemoveServerNamesFromUrl="True" EnableBrowserContextMenu="True" EnableContextMenu="True" EnableStripScriptTags="True" AllowPasteHtml="True" FullPage="False" EncodeHiddenValue="True" ShowCodeViewToolBar="True" UseRelativeLinks="True" RenderRichDropDown="False" EditorOnPaste="ConfirmWord" EditorWysiwygModeCss="/css/editor.css" CodeViewTemplateItemList="Save,Print,Cut,Copy,Paste,Find,ToFullPage,FromFullPage,SelectAll,SelectNone" PostBackScript="__doPostBack('NewEditArticle1$ContentSpaw$PostBackHandler','')" HelpUrl="/CuteSoft_Client/CuteEditor/Help/default.aspx" EnableContextMenuEditing="True" EnableContextMenuFormat="True" EnableContextMenuInsert="True" EnableContextMenuInsertAdvanced="True" EnableContextMenuInsertFiles="True" EnableContextMenuInsertForms="True" EnableContextMenuVerbs="True" EnableContextMenuTags="True" EnableContextMenuRelative="True" PrintFullWebPage="False" EditCompleteDocument="False" cellspacing="0" cellpadding="0" id="CE_NewEditArticle1_ContentSpaw_ID" style="background-color:#F4F4F3;border-color:#DDDDDD;border-width:1px;border-style:Solid;height:320px;width:808px;">
	<tr><td class='CuteEditorToolBarContainer' colspan='2' unselectable='on'><div id="CE_NewEditArticle1_ContentSpaw_ID_ToolBar">
		<table class=CuteEditorGroupMenu cellSpacing=0 cellPadding=0 border=0><tr><td><nobr><img src='/CuteSoft_Client/CuteEditor/Themes/Office2003/Images/horizontal.start.gif' unselectable='on' class='CuteEditorGroupImage'/><img Command="Find" width="20" height="20" class="CuteEditorButton" src="/CuteSoft_Client/CuteEditor/Themes/Office2003/Images/Find.gif.aspx" title="查找和替换" /><img Command="CleanCode" width="20" height="20" class="CuteEditorButton" src="/CuteSoft_Client/CuteEditor/Themes/Office2003/Images/cleanup.gif.aspx" title="代码清洁" /><img Command="Cut" width="20" height="20" class="CuteEditorButton" src="/CuteSoft_Client/CuteEditor/Themes/Office2003/Images/Cut.gif.aspx" title="裁减" /><img Command="Copy" width="20" height="20" class="CuteEditorButton" src="/CuteSoft_Client/CuteEditor/Themes/Office2003/Images/Copy.gif.aspx" title="拷贝" /><img Command="Paste" width="20" height="20" class="CuteEditorButton" src="/CuteSoft_Client/CuteEditor/Themes/Office2003/Images/Paste.gif.aspx" title="粘贴" /><img Command="Undo" width="20" height="20" class="CuteEditorButton" src="/CuteSoft_Client/CuteEditor/Themes/Office2003/Images/Undo.gif.aspx" title="撤消" /><img Command="Redo" width="20" height="20" class="CuteEditorButton" src="/CuteSoft_Client/CuteEditor/Themes/Office2003/Images/Redo.gif.aspx" title="恢复" /><img Command="TableDropDown" onclick="var editor=CuteEditor_GetEditor(this);if(!editor.readOnly)editor.TableDropDown(this)" class="CuteEditorButton" src="/CuteSoft_Client/CuteEditor/Themes/Office2003/Images/instable.gif.aspx" title="插入/修改表格" /><img Command="Break" width="20" height="20" class="CuteEditorButton" src="/CuteSoft_Client/CuteEditor/Themes/Office2003/Images/Break.gif.aspx" title="换行" /><img Command="InsertDate" width="20" height="20" class="CuteEditorButton" src="/CuteSoft_Client/CuteEditor/Themes/Office2003/Images/insertdate.gif.aspx" title="插入日期" /><img Command="InsertTime" width="20" height="20" class="CuteEditorButton" src="/CuteSoft_Client/CuteEditor/Themes/Office2003/Images/timer.gif.aspx" title="插入时间" /><img Command="StrikeThrough" width="20" height="20" class="CuteEditorButton" src="/CuteSoft_Client/CuteEditor/Themes/Office2003/Images/strike.gif.aspx" title="删除线" /><img Command="InsertLink" width="20" height="20" class="CuteEditorButton" src="/CuteSoft_Client/CuteEditor/Themes/Office2003/Images/link.gif.aspx" title="插入超链接" /><img title="插入特殊字符" class="CuteEditorButton" onMouseOver="CuteEditor_ButtonOver(this)" onclick="window.open(&quot;http://post.blog.hexun.com/inc/InsertChar.htm&quot;,&quot;插入特殊字符&quot;, &quot;height=345, width=480, top=150, left=150, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no&quot;)" src="/CuteSoft_Client/CuteEditor/Images/specialchar.gif" border="0" /><img title="插入表情" class="CuteEditorButton" onMouseOver="CuteEditor_ButtonOver(this)" onclick="window.open(&quot;http://post.blog.hexun.com/inc/InsertEmotion.htm&quot;,&quot;插入表情&quot;, &quot;height=315, width=310, top=150, left=150, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no&quot;)" src="/CuteSoft_Client/CuteEditor/Images/emotion.gif" border="0" /><img title="插入图片" class="CuteEditorButton" onMouseOver="CuteEditor_ButtonOver(this)" onclick="window.open(&quot;http://post.photo.hexun.com/upload/singleupload.aspx?url=blog&quot;,&quot;插入图片&quot;, &quot;height=400, width=640, top=150, left=150, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no&quot;)" src="/CuteSoft_Client/CuteEditor/Images/image.gif" border="0" /><img title="插入Flash" class="CuteEditorButton" onMouseOver="CuteEditor_ButtonOver(this)" onclick="window.open(&quot;http://post.blog.hexun.com/inc/InsertFlash.htm&quot;,&quot;插入Flash&quot;, &quot;height=300, width=500, top=150, left=150, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no&quot;)" src="/CuteSoft_Client/CuteEditor/Images/flash.gif" border="0" /><img title="插入音乐、视频" class="CuteEditorButton" onMouseOver="CuteEditor_ButtonOver(this)" onclick="window.open(&quot;http://post.music.hexun.com/UploadSpaceCover.aspx&quot;,&quot;插入音乐视频&quot;, &quot;height=500, width=640, top=150, left=150, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no&quot;)" src="/CuteSoft_Client/CuteEditor/Images/media.gif" border="0" /></nobr></td></tr></table><br/><table class=CuteEditorGroupMenu cellSpacing=0 cellPadding=0 border=0><tr><td><nobr><img src='/CuteSoft_Client/CuteEditor/Themes/Office2003/Images/horizontal.start.gif' unselectable='on' class='CuteEditorGroupImage'/><img Command="InsertOrderedList" width="20" height="20" class="CuteEditorButton" src="/CuteSoft_Client/CuteEditor/Themes/Office2003/Images/numlist.gif.aspx" title="编号" /><img Command="InsertUnorderedList" width="20" height="20" class="CuteEditorButton" src="/CuteSoft_Client/CuteEditor/Themes/Office2003/Images/bullist.gif.aspx" title="项目符号" /><img Command="Indent" width="20" height="20" class="CuteEditorButton" src="/CuteSoft_Client/CuteEditor/Themes/Office2003/Images/Indent.gif.aspx" title="缩进" /><img Command="Outdent" width="20" height="20" class="CuteEditorButton" src="/CuteSoft_Client/CuteEditor/Themes/Office2003/Images/Outdent.gif.aspx" title="取消缩进" /><img Command="JustifyLeft" width="20" height="20" class="CuteEditorButton" src="/CuteSoft_Client/CuteEditor/Themes/Office2003/Images/left.gif.aspx" title="左对齐" /><img Command="JustifyCenter" width="20" height="20" class="CuteEditorButton" src="/CuteSoft_Client/CuteEditor/Themes/Office2003/Images/center.gif.aspx" title="居中" /><img Command="JustifyRight" width="20" height="20" class="CuteEditorButton" src="/CuteSoft_Client/CuteEditor/Themes/Office2003/Images/right.gif.aspx" title="右对齐" /><img Command="Bold" width="20" height="20" class="CuteEditorButton" src="/CuteSoft_Client/CuteEditor/Themes/Office2003/Images/Bold.gif.aspx" title="粗体" /><img Command="Italic" width="20" height="20" class="CuteEditorButton" src="/CuteSoft_Client/CuteEditor/Themes/Office2003/Images/Italic.gif.aspx" title="斜体" /><img Command="Underline" width="20" height="20" class="CuteEditorButton" src="/CuteSoft_Client/CuteEditor/Themes/Office2003/Images/under.gif.aspx" title="下划线" /><span class='separator'></span><img Command="ForeColor" onclick="CuteEditor_GetEditor(this).ExecCommand('ForeColor',false,CE_NewEditArticle1_ContentSpaw_ID_forecolorimg.style.backgroundColor)" id="CE_NewEditArticle1_ContentSpaw_ID_forecolorimg" Width="17" class="CuteEditorButton" src="/CuteSoft_Client/CuteEditor/Themes/Office2003/Images/fontcolor.gif.aspx" title="字体颜色" style="background-color: red;" /><img Command="SetForeColor" onclick="CuteEditor_GetEditor(this).ExecCommand('SetForeColor',false,CE_NewEditArticle1_ContentSpaw_ID_forecolorimg);" class="CuteEditorButton" src="/CuteSoft_Client/CuteEditor/Themes/Office2003/Images/tbdown.gif.aspx" title="设置字体颜色" /><img Command="ForeColor" onclick="CuteEditor_GetEditor(this).ExecCommand('BackColor',false,CE_NewEditArticle1_ContentSpaw_ID_backcolorimg.style.backgroundColor)" id="CE_NewEditArticle1_ContentSpaw_ID_backcolorimg" Width="17" class="CuteEditorButton" src="/CuteSoft_Client/CuteEditor/Themes/Office2003/Images/colorpen.gif.aspx" title="高亮颜色" style="background-color: yellow;" /><img Command="SetBackColor" onclick="CuteEditor_GetEditor(this).ExecCommand('SetBackColor',false,CE_NewEditArticle1_ContentSpaw_ID_backcolorimg);" class="CuteEditorButton" src="/CuteSoft_Client/CuteEditor/Themes/Office2003/Images/tbdown.gif.aspx" title="设置高亮颜色" /></nobr></td></tr></table><table class=CuteEditorGroupMenu cellSpacing=0 cellPadding=0 border=0><tr><td><nobr><img src='/CuteSoft_Client/CuteEditor/Themes/Office2003/Images/horizontal.start.gif' unselectable='on' class='CuteEditorGroupImage'/><select class="CuteEditorSelect" id="CE_NewEditArticle1_ContentSpaw_IDfontname" Command="FontName" onchange="CuteEditor_DropDownCommand(this,'FontName')">

<option value="" selected="True">	字体</option><option value="黑体">	黑体</option><option value="宋体">	宋体</option><option value="新宋体">	新宋体</option><option value="仿宋_GB2312">	仿宋_GB2312</option><option value="宋体-方正超大字符集">	宋体-方正超大字符集</option><option value="隶书">	隶书</option><option value="幼圆">	幼圆</option><option value="楷体_GB2312">	楷体_GB2312</option><option value="华文行楷">	华文行楷</option><option value="华文新魏">	华文新魏</option><option value="华文中宋">	华文中宋</option><option value="华文仿宋">	华文仿宋</option><option value="华文细黑">	华文细黑</option><option value="华文彩云">	华文彩云</option><option value="方正舒体">	方正舒体</option><option value="方正姚体">	方正姚体</option><option value="Arial">	Arial</option><option value="Verdana">	Verdana</option><option value="Comic Sans MS">	Comic Sans MS</option><option value="Courier">	Courier</option><option value="Georgia">	Georgia</option><option value="Impact">	Impact</option><option value="Lucida Console">	Lucida Console</option><option value="Tahoma">	Tahoma</option><option value="Times New Roman">	Times New Roman</option><option value="Wingdings">	Wingdings</option>

		</select><span style='font-size:0px;width:0px;'></span><select class="CuteEditorSelect" id="CE_NewEditArticle1_ContentSpaw_IDfontsize" Command="FontSize" onchange="CuteEditor_DropDownCommand(this,'FontSize')">
<option value="" selected="True">	字号</option><option value="null">	未设置</option><option value="1">	1 (8pt)</option><option value="2">	2 (10pt)</option><option value="3">	3 (12pt)</option><option value="4">	4 (14pt)</option><option value="5">	5 (18pt)</option><option value="6">	6 (24pt)</option><option value="7">	7 (36pt)</option>

		</select><span style='font-size:0px;width:0px;'></span></nobr></td></tr></table>
	</div></td></tr><tr><td colspan='2' class='CuteEditorFrameContainer' height='100%'><iframe id="CE_NewEditArticle1_ContentSpaw_ID_Frame" FrameBorder="0" class="CuteEditorFrame" style="background-color:White;border-color:#DDDDDD;border-width:1px;border-style:Solid;height:100%;width:100%;">

	</iframe><textarea name="NewEditArticle1:ContentSpaw" id="NewEditArticle1:ContentSpaw" rows="14" cols="50" class="CuteEditorTextArea" style="DISPLAY: none; WIDTH: 100%; HEIGHT: 100%">测试2  </textarea></td></tr><tr><td class='CuteEditorBottomBarContainer'><img Command="TabEdit" class="CuteEditorButton" src="/CuteSoft_Client/CuteEditor/Themes/Office2003/Images/design.gif" title="普通" /><img Command="TabCode" class="CuteEditorButton" src="/CuteSoft_Client/CuteEditor/Themes/Office2003/Images/htmlview.gif" title="代码" /><img Command="TabView" class="CuteEditorButton" src="/CuteSoft_Client/CuteEditor/Themes/Office2003/Images/preview.gif" title="预览状态" /></td><td align='right'><img Command="SizePlus" class="CuteEditorButton" src="/CuteSoft_Client/CuteEditor/Themes/Office2003/Images/plus.gif" title="扩大编辑窗口" /><img Command="SizeMinus" class="CuteEditorButton" src="/CuteSoft_Client/CuteEditor/Themes/Office2003/Images/minus.gif" title="缩小编辑窗口" /></td></tr>
</table>
<script language=javascript>
CuteEditor_Gecko_Initialize('CE_NewEditArticle1_ContentSpaw_ID','NewEditArticle1:ContentSpaw','CE_NewEditArticle1_ContentSpaw_ID_Frame','CE_NewEditArticle1_ContentSpaw_ID_TIF');
function CE_NewEditArticle1_ContentSpaw_ID_load(){document.getElementById('CE_NewEditArticle1_ContentSpaw_ID').LoadTemplate();}
</script>
<iframe id="CE_NewEditArticle1_ContentSpaw_ID_TIF" src="/CuteSoft_Client/CuteEditor/Template.aspx?Referrer=http%3a%2f%2fpost.blog.hexun.com%2fsingle%2ftemplete%2fmodule18%2fpostarticle.aspx%3fblogname%3dblogremover%26articleid%3d4574623" onload="CE_NewEditArticle1_ContentSpaw_ID_load()" style="display:none;">

</iframe>

 <!-- CuteEditor NewEditArticle1:ContentSpaw End --> 

</div><div class="n_n01">
		  &nbsp;文中提到哪些好友？<br />

		  &nbsp;请输入好友名字：<br />
		    &nbsp;<input type="text" name="textRelateUser" id="textRelateUser" class="input1" style="width:95px;" onkeyup="autoComplete(4068581,event)" onblur="setDisplay();" autocomplete="off" onkeydown="testValue(event,4068581);" /><span id="valueSpan" style="display:none;"></span><input type=hidden id="valueIsChanged" name="valueIsChanged" />
		    <img src="../images/add01.gif" alt="添加" onclick="insertUserLogoImg(4068581);" style="cursor:pointer" width="11" height="11" border="0" />
		    <div id="relateContent" style="display:none;"></div>
			<div class="n_n02" id="showItemContent" style="margin-bottom:0;">
			<!--单元开始 -->
			
			<!--单元结束 -->			
			</div>
		  </div>

		</div>
		<div class="clear"></div>   
	    <div>标　　签：
	      <input name="NewEditArticle1:TagTextbox" type="text" value=" 必须要标签？" id="NewEditArticle1_TagTextbox" class="input1" style="width:450px" />
	    </div>
	    <!--收缩部分-->
	    <div id="recentTags" style="margin-left:60px;">已有标签：<A href="javascript:selectTag('必须要标签？')" class="f_black">必须要标签？</A>&nbsp;&nbsp;<span style="cursor:pointer;"  onclick="showAlls(0);" class="f_org">更多&gt;&gt;</span>&nbsp;&nbsp;&nbsp;&nbsp;<span style="cursor:pointer;" onclick="showAlls(1);" class="f_org">推荐标签&gt;&gt;</span></div>
	  <!--收缩部分-->

		<div id="allTags" class="tag" style="display:none;">
			<div class="tag_1"><div class="tag_1_1" id="showMine" onclick="showMyTags(0);">我的标签</div><div id="showRecommend" class="tag_1_2" onclick="showMyTags(1);">推荐标签</div><div align="right"><a href="javascript:showPersonTags();"><img src="http://blog.hexun.com/single/templete/module18/img/h01_43.gif" border="0" /></a></div></div>
			<div class="tag_2" id="recommendTags">
				<div class="table_bg1_2"><a id="a_1877" style="font-weight:bolder;" href="javascript:showRecommendAllTags(1877);">天下</a>|<a id="a_1920" href="javascript:showRecommendAllTags(1920);">社会</a>|<a id="a_1882" href="javascript:showRecommendAllTags(1882);">军事</a>|<a id="a_1891" href="javascript:showRecommendAllTags(1891);">历史</a>|<a id="a_1930" href="javascript:showRecommendAllTags(1930);">财经</a>|<a id="a_1917" href="javascript:showRecommendAllTags(1917);">股票</a>|<a id="a_1909" href="javascript:showRecommendAllTags(1909);">理财</a>|<a id="a_1940" href="javascript:showRecommendAllTags(1940);">车房</a>|<a id="a_1961" href="javascript:showRecommendAllTags(1961);">传媒</a>|<a id="a_2740" href="javascript:showRecommendAllTags(2740);">科技</a>|<a id="a_1900" href="javascript:showRecommendAllTags(1900);">文学</a>|<a id="a_2744" href="javascript:showRecommendAllTags(2744);">体育</a>|<a id="a_1947" href="javascript:showRecommendAllTags(1947);">旅游</a>|<a id="a_1954" href="javascript:showRecommendAllTags(1954);">娱乐</a>|<a id="a_2736" href="javascript:showRecommendAllTags(2736);">情感</a>|<a id="a_2732" href="javascript:showRecommendAllTags(2732);">大杂烩</a>|<a id="a_2818" href="javascript:showRecommendAllTags(2818);">美食</a>|<a id="a_2823" href="javascript:showRecommendAllTags(2823);">亲子</a>|<a id="a_2829" href="javascript:showRecommendAllTags(2829);">书画</a>|<a id="a_2833" href="javascript:showRecommendAllTags(2833);">健康</a></div>

				<div class="tag_2_1" id="recommend_1877"><A href="javascript:selectTag('大话美国')" class="f_black">大话美国</A>&nbsp;&nbsp;<A href="javascript:selectTag('中日关系')" class="f_black">中日关系</A>&nbsp;&nbsp;<A href="javascript:selectTag('靖国神社')" class="f_black">靖国神社</A>&nbsp;&nbsp;<A href="javascript:selectTag('朝核危机')" class="f_black">朝核危机</A>&nbsp;&nbsp;<A href="javascript:selectTag('天下')" class="f_black">天下</A>&nbsp;&nbsp;<A href="javascript:selectTag('布什')" class="f_black">布什</A>&nbsp;&nbsp;<A href="javascript:selectTag('普京')" class="f_black">普京</A>&nbsp;&nbsp;<A href="javascript:selectTag('两会')" class="f_black">两会</A>&nbsp;&nbsp;<A href="javascript:selectTag('民生')" class="f_black">民生</A>&nbsp;&nbsp;<A href="javascript:selectTag('全球变暖')" class="f_black">全球变暖</A>&nbsp;&nbsp;<A href="javascript:selectTag('台海')" class="f_black">台海</A>&nbsp;&nbsp;<br/><A href="javascript:selectTag(' 陈水扁')" class="f_black"> 陈水扁</A>&nbsp;&nbsp;<A href="javascript:selectTag('赖昌星')" class="f_black">赖昌星</A>&nbsp;&nbsp;<A href="javascript:selectTag('法律')" class="f_black">法律</A>&nbsp;&nbsp;<A href="javascript:selectTag('伊朗')" class="f_black">伊朗</A>&nbsp;&nbsp;<A href="javascript:selectTag('伊拉克战争')" class="f_black">伊拉克战争</A>&nbsp;&nbsp;<A href="javascript:selectTag('中东')" class="f_black">中东</A>&nbsp;&nbsp;<A href="javascript:selectTag('恐怖主义')" class="f_black">恐怖主义</A>&nbsp;&nbsp;<A href="javascript:selectTag('马英九')" class="f_black">马英九</A>&nbsp;&nbsp;<A href="javascript:selectTag('大选')" class="f_black">大选</A>&nbsp;&nbsp;<A href="javascript:selectTag('政策')" class="f_black">政策</A>&nbsp;&nbsp;<A href="javascript:selectTag('官员')" class="f_black">官员</A>&nbsp;&nbsp;<br/><A href="javascript:selectTag('监督')" class="f_black">监督</A>&nbsp;&nbsp;<A href="javascript:selectTag('人大政协')" class="f_black">人大政协</A>&nbsp;&nbsp;<A href="javascript:selectTag('改革开放')" class="f_black">改革开放</A>&nbsp;&nbsp;<A href="javascript:selectTag('三农政策')" class="f_black">三农政策</A>&nbsp;&nbsp;<A href="javascript:selectTag('福利')" class="f_black">福利</A>&nbsp;&nbsp;<A href="javascript:selectTag('外交政策')" class="f_black">外交政策</A>&nbsp;&nbsp;</div><div class="tag_2_1" id="recommend_1920" style="display:none;"><A href="javascript:selectTag('暴雨')" class="f_black">暴雨</A>&nbsp;&nbsp;<A href="javascript:selectTag('鼠患')" class="f_black">鼠患</A>&nbsp;&nbsp;<A href="javascript:selectTag('老师')" class="f_black">老师</A>&nbsp;&nbsp;<A href="javascript:selectTag('警察')" class="f_black">警察</A>&nbsp;&nbsp;<A href="javascript:selectTag('小偷')" class="f_black">小偷</A>&nbsp;&nbsp;<A href="javascript:selectTag('亲历')" class="f_black">亲历</A>&nbsp;&nbsp;<A href="javascript:selectTag('社会')" class="f_black">社会</A>&nbsp;&nbsp;<A href="javascript:selectTag('医院')" class="f_black">医院</A>&nbsp;&nbsp;<A href="javascript:selectTag('医生')" class="f_black">医生</A>&nbsp;&nbsp;<A href="javascript:selectTag('车祸')" class="f_black">车祸</A>&nbsp;&nbsp;<A href="javascript:selectTag('户口')" class="f_black">户口</A>&nbsp;&nbsp;<br/><A href="javascript:selectTag('暂住证')" class="f_black">暂住证</A>&nbsp;&nbsp;<A href="javascript:selectTag('户口')" class="f_black">户口</A>&nbsp;&nbsp;<A href="javascript:selectTag('学生')" class="f_black">学生</A>&nbsp;&nbsp;<A href="javascript:selectTag('黑幕')" class="f_black">黑幕</A>&nbsp;&nbsp;<A href="javascript:selectTag('农民')" class="f_black">农民</A>&nbsp;&nbsp;<A href="javascript:selectTag('工人')" class="f_black">工人</A>&nbsp;&nbsp;<A href="javascript:selectTag('小姐')" class="f_black">小姐</A>&nbsp;&nbsp;<A href="javascript:selectTag('国人')" class="f_black">国人</A>&nbsp;&nbsp;<A href="javascript:selectTag('工资')" class="f_black">工资</A>&nbsp;&nbsp;<A href="javascript:selectTag('公务员')" class="f_black">公务员</A>&nbsp;&nbsp;<A href="javascript:selectTag('骚扰')" class="f_black">骚扰</A>&nbsp;&nbsp;<br/><A href="javascript:selectTag('宾馆')" class="f_black">宾馆</A>&nbsp;&nbsp;<A href="javascript:selectTag('抢劫')" class="f_black">抢劫</A>&nbsp;&nbsp;<A href="javascript:selectTag('交通')" class="f_black">交通</A>&nbsp;&nbsp;<A href="javascript:selectTag('诈骗')" class="f_black">诈骗</A>&nbsp;&nbsp;</div><div class="tag_2_1" id="recommend_1882" style="display:none;"><A href="javascript:selectTag('军事')" class="f_black">军事</A>&nbsp;&nbsp;<A href="javascript:selectTag('战争')" class="f_black">战争</A>&nbsp;&nbsp;<A href="javascript:selectTag('抗战')" class="f_black">抗战</A>&nbsp;&nbsp;<A href="javascript:selectTag('解放战争')" class="f_black">解放战争</A>&nbsp;&nbsp;<A href="javascript:selectTag('林彪')" class="f_black">林彪</A>&nbsp;&nbsp;<A href="javascript:selectTag('红军')" class="f_black">红军</A>&nbsp;&nbsp;<A href="javascript:selectTag('国军')" class="f_black">国军</A>&nbsp;&nbsp;<A href="javascript:selectTag('西路军')" class="f_black">西路军</A>&nbsp;&nbsp;<A href="javascript:selectTag('二战')" class="f_black">二战</A>&nbsp;&nbsp;<A href="javascript:selectTag('台海')" class="f_black">台海</A>&nbsp;&nbsp;<A href="javascript:selectTag('弹道导弹')" class="f_black">弹道导弹</A>&nbsp;&nbsp;<br/><A href="javascript:selectTag('二炮')" class="f_black">二炮</A>&nbsp;&nbsp;<A href="javascript:selectTag('中越战争')" class="f_black">中越战争</A>&nbsp;&nbsp;<A href="javascript:selectTag('国防教育')" class="f_black">国防教育</A>&nbsp;&nbsp;<A href="javascript:selectTag('海军')" class="f_black">海军</A>&nbsp;&nbsp;<A href="javascript:selectTag('航母')" class="f_black">航母</A>&nbsp;&nbsp;<A href="javascript:selectTag('核武器')" class="f_black">核武器</A>&nbsp;&nbsp;<A href="javascript:selectTag('歼十')" class="f_black">歼十</A>&nbsp;&nbsp;<A href="javascript:selectTag('军事安全')" class="f_black">军事安全</A>&nbsp;&nbsp;<A href="javascript:selectTag('军事技术')" class="f_black">军事技术</A>&nbsp;&nbsp;<A href="javascript:selectTag('强国')" class="f_black">强国</A>&nbsp;&nbsp;<A href="javascript:selectTag('特种部队')" class="f_black">特种部队</A>&nbsp;&nbsp;<br/><A href="javascript:selectTag('战斗机')" class="f_black">战斗机</A>&nbsp;&nbsp;<A href="javascript:selectTag('战略武器')" class="f_black">战略武器</A>&nbsp;&nbsp;<A href="javascript:selectTag('中国陆军')" class="f_black">中国陆军</A>&nbsp;&nbsp;<A href="javascript:selectTag('中国空军')" class="f_black">中国空军</A>&nbsp;&nbsp;<A href="javascript:selectTag('攻台')" class="f_black">攻台</A>&nbsp;&nbsp;<A href="javascript:selectTag('自卫队')" class="f_black">自卫队</A>&nbsp;&nbsp;</div><div class="tag_2_1" id="recommend_1891" style="display:none;"><A href="javascript:selectTag('历史')" class="f_black">历史</A>&nbsp;&nbsp;<A href="javascript:selectTag('读史')" class="f_black">读史</A>&nbsp;&nbsp;<A href="javascript:selectTag('图说历史')" class="f_black">图说历史</A>&nbsp;&nbsp;<A href="javascript:selectTag('历史名人')" class="f_black">历史名人</A>&nbsp;&nbsp;<A href="javascript:selectTag('读书探史')" class="f_black">读书探史</A>&nbsp;&nbsp;<A href="javascript:selectTag('古今历史')" class="f_black">古今历史</A>&nbsp;&nbsp;<A href="javascript:selectTag('苏联')" class="f_black">苏联</A>&nbsp;&nbsp;<A href="javascript:selectTag('蒋介石')" class="f_black">蒋介石</A>&nbsp;&nbsp;<A href="javascript:selectTag('法西斯')" class="f_black">法西斯</A>&nbsp;&nbsp;<A href="javascript:selectTag('清朝')" class="f_black">清朝</A>&nbsp;&nbsp;<A href="javascript:selectTag('成吉思汗')" class="f_black">成吉思汗</A>&nbsp;&nbsp;<br/><A href="javascript:selectTag('国民党')" class="f_black">国民党</A>&nbsp;&nbsp;<A href="javascript:selectTag('国民政府')" class="f_black">国民政府</A>&nbsp;&nbsp;<A href="javascript:selectTag('民国')" class="f_black">民国</A>&nbsp;&nbsp;<A href="javascript:selectTag('史海回顾')" class="f_black">史海回顾</A>&nbsp;&nbsp;<A href="javascript:selectTag('斯大林')" class="f_black">斯大林</A>&nbsp;&nbsp;<A href="javascript:selectTag('三国')" class="f_black">三国</A>&nbsp;&nbsp;<A href="javascript:selectTag('春秋')" class="f_black">春秋</A>&nbsp;&nbsp;<A href="javascript:selectTag('孔子')" class="f_black">孔子</A>&nbsp;&nbsp;<A href="javascript:selectTag('千秋将帅')" class="f_black">千秋将帅</A>&nbsp;&nbsp;<A href="javascript:selectTag('史海钩沉')" class="f_black">史海钩沉</A>&nbsp;&nbsp;<A href="javascript:selectTag('杂谈历史')" class="f_black">杂谈历史</A>&nbsp;&nbsp;<br/></div><div class="tag_2_1" id="recommend_1930" style="display:none;"><A href="javascript:selectTag('反垄断法')" class="f_black">反垄断法</A>&nbsp;&nbsp;<A href="javascript:selectTag('财经')" class="f_black">财经</A>&nbsp;&nbsp;<A href="javascript:selectTag('经济')" class="f_black">经济</A>&nbsp;&nbsp;<A href="javascript:selectTag('管理')" class="f_black">管理</A>&nbsp;&nbsp;<A href="javascript:selectTag('职场')" class="f_black">职场</A>&nbsp;&nbsp;<A href="javascript:selectTag('手机银行')" class="f_black">手机银行</A>&nbsp;&nbsp;<A href="javascript:selectTag('银行')" class="f_black">银行</A>&nbsp;&nbsp;<A href="javascript:selectTag('并购')" class="f_black">并购</A>&nbsp;&nbsp;<A href="javascript:selectTag('财富')" class="f_black">财富</A>&nbsp;&nbsp;<A href="javascript:selectTag('财富人生')" class="f_black">财富人生</A>&nbsp;&nbsp;<A href="javascript:selectTag('财经博览')" class="f_black">财经博览</A>&nbsp;&nbsp;<br/><A href="javascript:selectTag('产业经济')" class="f_black">产业经济</A>&nbsp;&nbsp;<A href="javascript:selectTag('产业观察')" class="f_black">产业观察</A>&nbsp;&nbsp;<A href="javascript:selectTag('出口')" class="f_black">出口</A>&nbsp;&nbsp;<A href="javascript:selectTag('传销')" class="f_black">传销</A>&nbsp;&nbsp;<A href="javascript:selectTag('福布斯')" class="f_black">福布斯</A>&nbsp;&nbsp;<A href="javascript:selectTag('个税')" class="f_black">个税</A>&nbsp;&nbsp;<A href="javascript:selectTag('工程管理')" class="f_black">工程管理</A>&nbsp;&nbsp;<A href="javascript:selectTag('公关')" class="f_black">公关</A>&nbsp;&nbsp;<A href="javascript:selectTag('营销')" class="f_black">营销</A>&nbsp;&nbsp;<A href="javascript:selectTag('广告')" class="f_black">广告</A>&nbsp;&nbsp;<A href="javascript:selectTag('国际贸易')" class="f_black">国际贸易</A>&nbsp;&nbsp;<br/><A href="javascript:selectTag('国有资产')" class="f_black">国有资产</A>&nbsp;&nbsp;<A href="javascript:selectTag('上市')" class="f_black">上市</A>&nbsp;&nbsp;<A href="javascript:selectTag('合同')" class="f_black">合同</A>&nbsp;&nbsp;<A href="javascript:selectTag('面试')" class="f_black">面试</A>&nbsp;&nbsp;<A href="javascript:selectTag('信用卡')" class="f_black">信用卡</A>&nbsp;&nbsp;<A href="javascript:selectTag('加息')" class="f_black">加息</A>&nbsp;&nbsp;</div><div class="tag_2_1" id="recommend_1917" style="display:none;"><A href="javascript:selectTag('印花税')" class="f_black">印花税</A>&nbsp;&nbsp;<A href="javascript:selectTag('股票')" class="f_black">股票</A>&nbsp;&nbsp;<A href="javascript:selectTag('股票操作')" class="f_black">股票操作</A>&nbsp;&nbsp;<A href="javascript:selectTag('股市')" class="f_black">股市</A>&nbsp;&nbsp;<A href="javascript:selectTag('股市分析')" class="f_black">股市分析</A>&nbsp;&nbsp;<A href="javascript:selectTag('证券')" class="f_black">证券</A>&nbsp;&nbsp;<A href="javascript:selectTag('操盘日记')" class="f_black">操盘日记</A>&nbsp;&nbsp;<A href="javascript:selectTag('股市交易')" class="f_black">股市交易</A>&nbsp;&nbsp;<A href="javascript:selectTag('证券市场')" class="f_black">证券市场</A>&nbsp;&nbsp;<A href="javascript:selectTag('股市评论')" class="f_black">股市评论</A>&nbsp;&nbsp;<A href="javascript:selectTag('中国股市')" class="f_black">中国股市</A>&nbsp;&nbsp;<br/><A href="javascript:selectTag('炒股')" class="f_black">炒股</A>&nbsp;&nbsp;<A href="javascript:selectTag('大势分析')" class="f_black">大势分析</A>&nbsp;&nbsp;<A href="javascript:selectTag('持仓结构')" class="f_black">持仓结构</A>&nbsp;&nbsp;<A href="javascript:selectTag('大盘')" class="f_black">大盘</A>&nbsp;&nbsp;<A href="javascript:selectTag('个股')" class="f_black">个股</A>&nbsp;&nbsp;<A href="javascript:selectTag('股改')" class="f_black">股改</A>&nbsp;&nbsp;<A href="javascript:selectTag('股民')" class="f_black">股民</A>&nbsp;&nbsp;<A href="javascript:selectTag('股价')" class="f_black">股价</A>&nbsp;&nbsp;<A href="javascript:selectTag('股评')" class="f_black">股评</A>&nbsp;&nbsp;<A href="javascript:selectTag('投资')" class="f_black">投资</A>&nbsp;&nbsp;<A href="javascript:selectTag('大盘预测')" class="f_black">大盘预测</A>&nbsp;&nbsp;<br/><A href="javascript:selectTag('股指期货')" class="f_black">股指期货</A>&nbsp;&nbsp;</div><div class="tag_2_1" id="recommend_1909" style="display:none;"><A href="javascript:selectTag('理财')" class="f_black">理财</A>&nbsp;&nbsp;<A href="javascript:selectTag('期货')" class="f_black">期货</A>&nbsp;&nbsp;<A href="javascript:selectTag('期货预测')" class="f_black">期货预测</A>&nbsp;&nbsp;<A href="javascript:selectTag('外汇')" class="f_black">外汇</A>&nbsp;&nbsp;<A href="javascript:selectTag('期货日志')" class="f_black">期货日志</A>&nbsp;&nbsp;<A href="javascript:selectTag('期货投资')" class="f_black">期货投资</A>&nbsp;&nbsp;<A href="javascript:selectTag('投资理财')" class="f_black">投资理财</A>&nbsp;&nbsp;<A href="javascript:selectTag('金属')" class="f_black">金属</A>&nbsp;&nbsp;<A href="javascript:selectTag('基金')" class="f_black">基金</A>&nbsp;&nbsp;<A href="javascript:selectTag('汇市分析')" class="f_black">汇市分析</A>&nbsp;&nbsp;<A href="javascript:selectTag('期市杂谈')" class="f_black">期市杂谈</A>&nbsp;&nbsp;<br/><A href="javascript:selectTag('期货评论')" class="f_black">期货评论</A>&nbsp;&nbsp;<A href="javascript:selectTag('期市感悟')" class="f_black">期市感悟</A>&nbsp;&nbsp;<A href="javascript:selectTag('保险')" class="f_black">保险</A>&nbsp;&nbsp;<A href="javascript:selectTag('保险费')" class="f_black">保险费</A>&nbsp;&nbsp;<A href="javascript:selectTag('炒单技巧')" class="f_black">炒单技巧</A>&nbsp;&nbsp;<A href="javascript:selectTag('家庭理财')" class="f_black">家庭理财</A>&nbsp;&nbsp;<A href="javascript:selectTag('交易日记')" class="f_black">交易日记</A>&nbsp;&nbsp;<A href="javascript:selectTag('期货分析')" class="f_black">期货分析</A>&nbsp;&nbsp;<A href="javascript:selectTag('期货交易')" class="f_black">期货交易</A>&nbsp;&nbsp;<A href="javascript:selectTag('操作提示
')" class="f_black">操作提示

</A>&nbsp;&nbsp;<A href="javascript:selectTag('外汇交易')" class="f_black">外汇交易</A>&nbsp;&nbsp;<br/><A href="javascript:selectTag('外汇保证金')" class="f_black">外汇保证金</A>&nbsp;&nbsp;<A href="javascript:selectTag('彩票')" class="f_black">彩票</A>&nbsp;&nbsp;</div><div class="tag_2_1" id="recommend_1940" style="display:none;"><A href="javascript:selectTag('房产')" class="f_black">房产</A>&nbsp;&nbsp;<A href="javascript:selectTag('房价')" class="f_black">房价</A>&nbsp;&nbsp;<A href="javascript:selectTag('楼市')" class="f_black">楼市</A>&nbsp;&nbsp;<A href="javascript:selectTag('汽车')" class="f_black">汽车</A>&nbsp;&nbsp;<A href="javascript:selectTag('房地产')" class="f_black">房地产</A>&nbsp;&nbsp;<A href="javascript:selectTag('房奴')" class="f_black">房奴</A>&nbsp;&nbsp;<A href="javascript:selectTag('车模')" class="f_black">车模</A>&nbsp;&nbsp;<A href="javascript:selectTag('车市')" class="f_black">车市</A>&nbsp;&nbsp;<A href="javascript:selectTag('买车')" class="f_black">买车</A>&nbsp;&nbsp;<A href="javascript:selectTag('房子')" class="f_black">房子</A>&nbsp;&nbsp;<A href="javascript:selectTag('我为车狂')" class="f_black">我为车狂</A>&nbsp;&nbsp;<br/><A href="javascript:selectTag('装修')" class="f_black">装修</A>&nbsp;&nbsp;<A href="javascript:selectTag('车祸')" class="f_black">车祸</A>&nbsp;&nbsp;<A href="javascript:selectTag('爱车一族')" class="f_black">爱车一族</A>&nbsp;&nbsp;<A href="javascript:selectTag('地产分析')" class="f_black">地产分析</A>&nbsp;&nbsp;<A href="javascript:selectTag('驾驶')" class="f_black">驾驶</A>&nbsp;&nbsp;<A href="javascript:selectTag('任志强')" class="f_black">任志强</A>&nbsp;&nbsp;<A href="javascript:selectTag('潘石屹')" class="f_black">潘石屹</A>&nbsp;&nbsp;<A href="javascript:selectTag('按揭')" class="f_black">按揭</A>&nbsp;&nbsp;<A href="javascript:selectTag('房产策划')" class="f_black">房产策划</A>&nbsp;&nbsp;<A href="javascript:selectTag('二手车')" class="f_black">二手车</A>&nbsp;&nbsp;<A href="javascript:selectTag('二手房')" class="f_black">二手房</A>&nbsp;&nbsp;<br/><A href="javascript:selectTag('购房')" class="f_black">购房</A>&nbsp;&nbsp;<A href="javascript:selectTag('奔驰')" class="f_black">奔驰</A>&nbsp;&nbsp;<A href="javascript:selectTag('宝马')" class="f_black">宝马</A>&nbsp;&nbsp;<A href="javascript:selectTag('地产')" class="f_black">地产</A>&nbsp;&nbsp;<A href="javascript:selectTag('车展')" class="f_black">车展</A>&nbsp;&nbsp;<A href="javascript:selectTag('车友')" class="f_black">车友</A>&nbsp;&nbsp;<A href="javascript:selectTag('车价')" class="f_black">车价</A>&nbsp;&nbsp;<A href="javascript:selectTag('车迷会')" class="f_black">车迷会</A>&nbsp;&nbsp;<A href="javascript:selectTag('不动产')" class="f_black">不动产</A>&nbsp;&nbsp;<A href="javascript:selectTag('炒房团')" class="f_black">炒房团</A>&nbsp;&nbsp;<A href="javascript:selectTag('汽车知识')" class="f_black">汽车知识</A>&nbsp;&nbsp;<br/><A href="javascript:selectTag('爱车')" class="f_black">爱车</A>&nbsp;&nbsp;<A href="javascript:selectTag('驾照')" class="f_black">驾照</A>&nbsp;&nbsp;<A href="javascript:selectTag('房贷')" class="f_black">房贷</A>&nbsp;&nbsp;<A href="javascript:selectTag('物业')" class="f_black">物业</A>&nbsp;&nbsp;<A href="javascript:selectTag('高房价')" class="f_black">高房价</A>&nbsp;&nbsp;<A href="javascript:selectTag('豪宅')" class="f_black">豪宅</A>&nbsp;&nbsp;<A href="javascript:selectTag('集资建房')" class="f_black">集资建房</A>&nbsp;&nbsp;</div><div class="tag_2_1" id="recommend_1961" style="display:none;"><A href="javascript:selectTag('新闻人')" class="f_black">新闻人</A>&nbsp;&nbsp;<A href="javascript:selectTag('采访手记')" class="f_black">采访手记</A>&nbsp;&nbsp;<A href="javascript:selectTag('报社')" class="f_black">报社</A>&nbsp;&nbsp;<A href="javascript:selectTag('媒体')" class="f_black">媒体</A>&nbsp;&nbsp;<A href="javascript:selectTag('cctv')" class="f_black">cctv</A>&nbsp;&nbsp;<A href="javascript:selectTag('央视')" class="f_black">央视</A>&nbsp;&nbsp;<A href="javascript:selectTag('杂志')" class="f_black">杂志</A>&nbsp;&nbsp;<A href="javascript:selectTag('报纸')" class="f_black">报纸</A>&nbsp;&nbsp;<A href="javascript:selectTag('纸媒')" class="f_black">纸媒</A>&nbsp;&nbsp;<A href="javascript:selectTag('传统媒体')" class="f_black">传统媒体</A>&nbsp;&nbsp;<A href="javascript:selectTag('电视台')" class="f_black">电视台</A>&nbsp;&nbsp;<br/><A href="javascript:selectTag('多媒体')" class="f_black">多媒体</A>&nbsp;&nbsp;<A href="javascript:selectTag('新闻媒体')" class="f_black">新闻媒体</A>&nbsp;&nbsp;<A href="javascript:selectTag('报业')" class="f_black">报业</A>&nbsp;&nbsp;<A href="javascript:selectTag('采访')" class="f_black">采访</A>&nbsp;&nbsp;<A href="javascript:selectTag('传媒江湖')" class="f_black">传媒江湖</A>&nbsp;&nbsp;<A href="javascript:selectTag('主持人')" class="f_black">主持人</A>&nbsp;&nbsp;<A href="javascript:selectTag('名记')" class="f_black">名记</A>&nbsp;&nbsp;<A href="javascript:selectTag('美联社')" class="f_black">美联社</A>&nbsp;&nbsp;<A href="javascript:selectTag('发行量')" class="f_black">发行量</A>&nbsp;&nbsp;<A href="javascript:selectTag('舆论监督')" class="f_black">舆论监督</A>&nbsp;&nbsp;<A href="javascript:selectTag('第四权力')" class="f_black">第四权力</A>&nbsp;&nbsp;<br/><A href="javascript:selectTag('封口费')" class="f_black">封口费</A>&nbsp;&nbsp;<A href="javascript:selectTag('车马费')" class="f_black">车马费</A>&nbsp;&nbsp;</div><div class="tag_2_1" id="recommend_2740" style="display:none;"><A href="javascript:selectTag('互联网')" class="f_black">互联网</A>&nbsp;&nbsp;<A href="javascript:selectTag('IT')" class="f_black">IT</A>&nbsp;&nbsp;<A href="javascript:selectTag('web2.0')" class="f_black">web2.0</A>&nbsp;&nbsp;<A href="javascript:selectTag('IT业界')" class="f_black">IT业界</A>&nbsp;&nbsp;<A href="javascript:selectTag('Google')" class="f_black">Google</A>&nbsp;&nbsp;<A href="javascript:selectTag('科学')" class="f_black">科学</A>&nbsp;&nbsp;<A href="javascript:selectTag('IT观察')" class="f_black">IT观察</A>&nbsp;&nbsp;<A href="javascript:selectTag('科技')" class="f_black">科技</A>&nbsp;&nbsp;<A href="javascript:selectTag('Web 2.0')" class="f_black">Web 2.0</A>&nbsp;&nbsp;<A href="javascript:selectTag('WEB2.O')" class="f_black">WEB2.O</A>&nbsp;&nbsp;<A href="javascript:selectTag('Windows')" class="f_black">Windows</A>&nbsp;&nbsp;<br/><A href="javascript:selectTag('windows技巧')" class="f_black">windows技巧</A>&nbsp;&nbsp;<A href="javascript:selectTag('windows系统')" class="f_black">windows系统</A>&nbsp;&nbsp;<A href="javascript:selectTag('博客')" class="f_black">博客</A>&nbsp;&nbsp;<A href="javascript:selectTag('电脑')" class="f_black">电脑</A>&nbsp;&nbsp;<A href="javascript:selectTag('科技')" class="f_black">科技</A>&nbsp;&nbsp;<A href="javascript:selectTag('联通')" class="f_black">联通</A>&nbsp;&nbsp;<A href="javascript:selectTag('移动')" class="f_black">移动</A>&nbsp;&nbsp;<A href="javascript:selectTag('软件')" class="f_black">软件</A>&nbsp;&nbsp;</div><div class="tag_2_1" id="recommend_1900" style="display:none;"><A href="javascript:selectTag('读书')" class="f_black">读书</A>&nbsp;&nbsp;<A href="javascript:selectTag('随笔')" class="f_black">随笔</A>&nbsp;&nbsp;<A href="javascript:selectTag('古典文学')" class="f_black">古典文学</A>&nbsp;&nbsp;<A href="javascript:selectTag('古诗')" class="f_black">古诗</A>&nbsp;&nbsp;<A href="javascript:selectTag('古诗词')" class="f_black">古诗词</A>&nbsp;&nbsp;<A href="javascript:selectTag('散文')" class="f_black">散文</A>&nbsp;&nbsp;<A href="javascript:selectTag('日记')" class="f_black">日记</A>&nbsp;&nbsp;<A href="javascript:selectTag('诗词')" class="f_black">诗词</A>&nbsp;&nbsp;<A href="javascript:selectTag('诗歌')" class="f_black">诗歌</A>&nbsp;&nbsp;<A href="javascript:selectTag('书评')" class="f_black">书评</A>&nbsp;&nbsp;<A href="javascript:selectTag('宋词')" class="f_black">宋词</A>&nbsp;&nbsp;<br/><A href="javascript:selectTag('童话')" class="f_black">童话</A>&nbsp;&nbsp;<A href="javascript:selectTag('网络小说')" class="f_black">网络小说</A>&nbsp;&nbsp;<A href="javascript:selectTag('小说')" class="f_black">小说</A>&nbsp;&nbsp;<A href="javascript:selectTag('文化')" class="f_black">文化</A>&nbsp;&nbsp;<A href="javascript:selectTag('作家')" class="f_black">作家</A>&nbsp;&nbsp;<A href="javascript:selectTag('文学')" class="f_black">文学</A>&nbsp;&nbsp;<A href="javascript:selectTag('评论')" class="f_black">评论</A>&nbsp;&nbsp;<A href="javascript:selectTag('武侠小说')" class="f_black">武侠小说</A>&nbsp;&nbsp;<A href="javascript:selectTag('原创作品')" class="f_black">原创作品</A>&nbsp;&nbsp;</div><div class="tag_2_1" id="recommend_2744" style="display:none;"><A href="javascript:selectTag('NBA')" class="f_black">NBA</A>&nbsp;&nbsp;<A href="javascript:selectTag('体育')" class="f_black">体育</A>&nbsp;&nbsp;<A href="javascript:selectTag('北京奥运')" class="f_black">北京奥运</A>&nbsp;&nbsp;<A href="javascript:selectTag('奥运会')" class="f_black">奥运会</A>&nbsp;&nbsp;<A href="javascript:selectTag('篮球')" class="f_black">篮球</A>&nbsp;&nbsp;<A href="javascript:selectTag('火箭')" class="f_black">火箭</A>&nbsp;&nbsp;<A href="javascript:selectTag('健康')" class="f_black">健康</A>&nbsp;&nbsp;<A href="javascript:selectTag('奥运')" class="f_black">奥运</A>&nbsp;&nbsp;<A href="javascript:selectTag('运动')" class="f_black">运动</A>&nbsp;&nbsp;<A href="javascript:selectTag('球星')" class="f_black">球星</A>&nbsp;&nbsp;<A href="javascript:selectTag('世界杯')" class="f_black">世界杯</A>&nbsp;&nbsp;<br/><A href="javascript:selectTag('体育')" class="f_black">体育</A>&nbsp;&nbsp;<A href="javascript:selectTag('体育评论')" class="f_black">体育评论</A>&nbsp;&nbsp;<A href="javascript:selectTag('武术')" class="f_black">武术</A>&nbsp;&nbsp;<A href="javascript:selectTag('姚明')" class="f_black">姚明</A>&nbsp;&nbsp;<A href="javascript:selectTag('意甲')" class="f_black">意甲</A>&nbsp;&nbsp;<A href="javascript:selectTag('英超')" class="f_black">英超</A>&nbsp;&nbsp;<A href="javascript:selectTag('足球，易建联
')" class="f_black">足球，易建联

</A>&nbsp;&nbsp;</div><div class="tag_2_1" id="recommend_1947" style="display:none;"><A href="javascript:selectTag('旅游')" class="f_black">旅游</A>&nbsp;&nbsp;<A href="javascript:selectTag('摄影')" class="f_black">摄影</A>&nbsp;&nbsp;<A href="javascript:selectTag('旅行')" class="f_black">旅行</A>&nbsp;&nbsp;<A href="javascript:selectTag('游记')" class="f_black">游记</A>&nbsp;&nbsp;<A href="javascript:selectTag('美景')" class="f_black">美景</A>&nbsp;&nbsp;<A href="javascript:selectTag('大自然')" class="f_black">大自然</A>&nbsp;&nbsp;<A href="javascript:selectTag('风景')" class="f_black">风景</A>&nbsp;&nbsp;<A href="javascript:selectTag('风景名胜')" class="f_black">风景名胜</A>&nbsp;&nbsp;<A href="javascript:selectTag('旅行杂记')" class="f_black">旅行杂记</A>&nbsp;&nbsp;<A href="javascript:selectTag('旅游攻略')" class="f_black">旅游攻略</A>&nbsp;&nbsp;<A href="javascript:selectTag('旅游摄影')" class="f_black">旅游摄影</A>&nbsp;&nbsp;<br/><A href="javascript:selectTag('名胜古迹')" class="f_black">名胜古迹</A>&nbsp;&nbsp;<A href="javascript:selectTag('自然风景')" class="f_black">自然风景</A>&nbsp;&nbsp;<A href="javascript:selectTag('风光')" class="f_black">风光</A>&nbsp;&nbsp;<A href="javascript:selectTag('驴友')" class="f_black">驴友</A>&nbsp;&nbsp;<A href="javascript:selectTag('自然')" class="f_black">自然</A>&nbsp;&nbsp;<A href="javascript:selectTag('摄影日记')" class="f_black">摄影日记</A>&nbsp;&nbsp;<A href="javascript:selectTag('行行摄摄')" class="f_black">行行摄摄</A>&nbsp;&nbsp;<A href="javascript:selectTag('我的摄影')" class="f_black">我的摄影</A>&nbsp;&nbsp;<A href="javascript:selectTag('风光')" class="f_black">风光</A>&nbsp;&nbsp;<A href="javascript:selectTag('人在旅途')" class="f_black">人在旅途</A>&nbsp;&nbsp;<A href="javascript:selectTag('旅途')" class="f_black">旅途</A>&nbsp;&nbsp;<br/><A href="javascript:selectTag('户外')" class="f_black">户外</A>&nbsp;&nbsp;<A href="javascript:selectTag('影像中国')" class="f_black">影像中国</A>&nbsp;&nbsp;</div><div class="tag_2_1" id="recommend_1954" style="display:none;"><A href="javascript:selectTag('电影')" class="f_black">电影</A>&nbsp;&nbsp;<A href="javascript:selectTag('电视')" class="f_black">电视</A>&nbsp;&nbsp;<A href="javascript:selectTag('影碟')" class="f_black">影碟</A>&nbsp;&nbsp;<A href="javascript:selectTag('大片')" class="f_black">大片</A>&nbsp;&nbsp;<A href="javascript:selectTag('文艺片')" class="f_black">文艺片</A>&nbsp;&nbsp;<A href="javascript:selectTag('商业片')" class="f_black">商业片</A>&nbsp;&nbsp;<A href="javascript:selectTag('艺术片')" class="f_black">艺术片</A>&nbsp;&nbsp;<A href="javascript:selectTag('超级女声')" class="f_black">超级女声</A>&nbsp;&nbsp;<A href="javascript:selectTag('好男儿')" class="f_black">好男儿</A>&nbsp;&nbsp;<A href="javascript:selectTag('我型我秀')" class="f_black">我型我秀</A>&nbsp;&nbsp;<A href="javascript:selectTag('快乐男声')" class="f_black">快乐男声</A>&nbsp;&nbsp;<br/><A href="javascript:selectTag('影视')" class="f_black">影视</A>&nbsp;&nbsp;<A href="javascript:selectTag('电视剧')" class="f_black">电视剧</A>&nbsp;&nbsp;<A href="javascript:selectTag('日剧')" class="f_black">日剧</A>&nbsp;&nbsp;<A href="javascript:selectTag('韩剧')" class="f_black">韩剧</A>&nbsp;&nbsp;<A href="javascript:selectTag('明星')" class="f_black">明星</A>&nbsp;&nbsp;<A href="javascript:selectTag('模特')" class="f_black">模特</A>&nbsp;&nbsp;<A href="javascript:selectTag('导演')" class="f_black">导演</A>&nbsp;&nbsp;<A href="javascript:selectTag('快乐大本营')" class="f_black">快乐大本营</A>&nbsp;&nbsp;<A href="javascript:selectTag('湖南卫视')" class="f_black">湖南卫视</A>&nbsp;&nbsp;<A href="javascript:selectTag('TVB')" class="f_black">TVB</A>&nbsp;&nbsp;<A href="javascript:selectTag('康熙来了')" class="f_black">康熙来了</A>&nbsp;&nbsp;<br/><A href="javascript:selectTag('变形金刚')" class="f_black">变形金刚</A>&nbsp;&nbsp;<A href="javascript:selectTag('色戒')" class="f_black">色戒</A>&nbsp;&nbsp;<A href="javascript:selectTag('越狱')" class="f_black">越狱</A>&nbsp;&nbsp;<A href="javascript:selectTag('太阳照常升起')" class="f_black">太阳照常升起</A>&nbsp;&nbsp;<A href="javascript:selectTag('影评')" class="f_black">影评</A>&nbsp;&nbsp;<A href="javascript:selectTag('奋斗')" class="f_black">奋斗</A>&nbsp;&nbsp;<A href="javascript:selectTag('金婚')" class="f_black">金婚</A>&nbsp;&nbsp;</div><div class="tag_2_1" id="recommend_2736" style="display:none;"><A href="javascript:selectTag('心情日记')" class="f_black">心情日记</A>&nbsp;&nbsp;<A href="javascript:selectTag('女人')" class="f_black">女人</A>&nbsp;&nbsp;<A href="javascript:selectTag('两性')" class="f_black">两性</A>&nbsp;&nbsp;<A href="javascript:selectTag('心情')" class="f_black">心情</A>&nbsp;&nbsp;<A href="javascript:selectTag('爱情')" class="f_black">爱情</A>&nbsp;&nbsp;<A href="javascript:selectTag('情感')" class="f_black">情感</A>&nbsp;&nbsp;<A href="javascript:selectTag('情色男女')" class="f_black">情色男女</A>&nbsp;&nbsp;<A href="javascript:selectTag('婚姻')" class="f_black">婚姻</A>&nbsp;&nbsp;<A href="javascript:selectTag('心情随笔')" class="f_black">心情随笔</A>&nbsp;&nbsp;<A href="javascript:selectTag('LOVE')" class="f_black">LOVE</A>&nbsp;&nbsp;<A href="javascript:selectTag('情感两性')" class="f_black">情感两性</A>&nbsp;&nbsp;<br/><A href="javascript:selectTag('爱情故事')" class="f_black">爱情故事</A>&nbsp;&nbsp;<A href="javascript:selectTag('性感')" class="f_black">性感</A>&nbsp;&nbsp;<A href="javascript:selectTag('love')" class="f_black">love</A>&nbsp;&nbsp;<A href="javascript:selectTag('两性健康')" class="f_black">两性健康</A>&nbsp;&nbsp;<A href="javascript:selectTag('性情男女')" class="f_black">性情男女</A>&nbsp;&nbsp;<A href="javascript:selectTag('同性恋')" class="f_black">同性恋</A>&nbsp;&nbsp;<A href="javascript:selectTag('情感世界')" class="f_black">情感世界</A>&nbsp;&nbsp;</div><div class="tag_2_1" id="recommend_2732" style="display:none;"><A href="javascript:selectTag('生活知识')" class="f_black">生活知识</A>&nbsp;&nbsp;<A href="javascript:selectTag('爱猫')" class="f_black">爱猫</A>&nbsp;&nbsp;<A href="javascript:selectTag('保健美容')" class="f_black">保健美容</A>&nbsp;&nbsp;<A href="javascript:selectTag('北漂一族')" class="f_black">北漂一族</A>&nbsp;&nbsp;<A href="javascript:selectTag('菜谱')" class="f_black">菜谱</A>&nbsp;&nbsp;<A href="javascript:selectTag('宠物世界')" class="f_black">宠物世界</A>&nbsp;&nbsp;<A href="javascript:selectTag('大学生活')" class="f_black">大学生活</A>&nbsp;&nbsp;<A href="javascript:selectTag('护肤美容')" class="f_black">护肤美容</A>&nbsp;&nbsp;<A href="javascript:selectTag('化妆品')" class="f_black">化妆品</A>&nbsp;&nbsp;<A href="javascript:selectTag('家居生活')" class="f_black">家居生活</A>&nbsp;&nbsp;<A href="javascript:selectTag('家庭生活小知识')" class="f_black">家庭生活小知识</A>&nbsp;&nbsp;<br/><A href="javascript:selectTag('减肥')" class="f_black">减肥</A>&nbsp;&nbsp;<A href="javascript:selectTag('简单生活')" class="f_black">简单生活</A>&nbsp;&nbsp;<A href="javascript:selectTag('健康话题')" class="f_black">健康话题</A>&nbsp;&nbsp;<A href="javascript:selectTag('交友')" class="f_black">交友</A>&nbsp;&nbsp;<A href="javascript:selectTag('生活')" class="f_black">生活</A>&nbsp;&nbsp;<A href="javascript:selectTag('精品图书')" class="f_black">精品图书</A>&nbsp;&nbsp;<A href="javascript:selectTag('开心生活')" class="f_black">开心生活</A>&nbsp;&nbsp;</div><div class="tag_2_1" id="recommend_2818" style="display:none;"><A href="javascript:selectTag('美食')" class="f_black">美食</A>&nbsp;&nbsp;<A href="javascript:selectTag('美食DIY')" class="f_black">美食DIY</A>&nbsp;&nbsp;<A href="javascript:selectTag('菜谱')" class="f_black">菜谱</A>&nbsp;&nbsp;<A href="javascript:selectTag('厨艺')" class="f_black">厨艺</A>&nbsp;&nbsp;<A href="javascript:selectTag('私房菜')" class="f_black">私房菜</A>&nbsp;&nbsp;<A href="javascript:selectTag('健康美味')" class="f_black">健康美味</A>&nbsp;&nbsp;<A href="javascript:selectTag('家常')" class="f_black">家常</A>&nbsp;&nbsp;<A href="javascript:selectTag('烹饪')" class="f_black">烹饪</A>&nbsp;&nbsp;<A href="javascript:selectTag('食记')" class="f_black">食记</A>&nbsp;&nbsp;<A href="javascript:selectTag('家常菜')" class="f_black">家常菜</A>&nbsp;&nbsp;<A href="javascript:selectTag('做菜')" class="f_black">做菜</A>&nbsp;&nbsp;<br/><A href="javascript:selectTag('餐馆')" class="f_black">餐馆</A>&nbsp;&nbsp;<A href="javascript:selectTag('吃喝')" class="f_black">吃喝</A>&nbsp;&nbsp;</div><div class="tag_2_1" id="recommend_2823" style="display:none;"><A href="javascript:selectTag('宝宝')" class="f_black">宝宝</A>&nbsp;&nbsp;<A href="javascript:selectTag('亲子')" class="f_black">亲子</A>&nbsp;&nbsp;<A href="javascript:selectTag('育儿')" class="f_black">育儿</A>&nbsp;&nbsp;<A href="javascript:selectTag('成长日志')" class="f_black">成长日志</A>&nbsp;&nbsp;<A href="javascript:selectTag('快乐成长')" class="f_black">快乐成长</A>&nbsp;&nbsp;<A href="javascript:selectTag('宝宝日记')" class="f_black">宝宝日记</A>&nbsp;&nbsp;<A href="javascript:selectTag('育儿心得')" class="f_black">育儿心得</A>&nbsp;&nbsp;<A href="javascript:selectTag('初为人母')" class="f_black">初为人母</A>&nbsp;&nbsp;<A href="javascript:selectTag('亲亲宝贝')" class="f_black">亲亲宝贝</A>&nbsp;&nbsp;<A href="javascript:selectTag('亲子日记')" class="f_black">亲子日记</A>&nbsp;&nbsp;<A href="javascript:selectTag('养育子女')" class="f_black">养育子女</A>&nbsp;&nbsp;<br/></div><div class="tag_2_1" id="recommend_2829" style="display:none;"><A href="javascript:selectTag('字画')" class="f_black">字画</A>&nbsp;&nbsp;<A href="javascript:selectTag('涂鸦')" class="f_black">涂鸦</A>&nbsp;&nbsp;<A href="javascript:selectTag('书法')" class="f_black">书法</A>&nbsp;&nbsp;<A href="javascript:selectTag('漫画')" class="f_black">漫画</A>&nbsp;&nbsp;<A href="javascript:selectTag('动漫')" class="f_black">动漫</A>&nbsp;&nbsp;<A href="javascript:selectTag('画画')" class="f_black">画画</A>&nbsp;&nbsp;<A href="javascript:selectTag('版画')" class="f_black">版画</A>&nbsp;&nbsp;<A href="javascript:selectTag('水粉')" class="f_black">水粉</A>&nbsp;&nbsp;<A href="javascript:selectTag('鼠绘作品')" class="f_black">鼠绘作品</A>&nbsp;&nbsp;<A href="javascript:selectTag('国画')" class="f_black">国画</A>&nbsp;&nbsp;<A href="javascript:selectTag('山水画')" class="f_black">山水画</A>&nbsp;&nbsp;<br/><A href="javascript:selectTag('油画')" class="f_black">油画</A>&nbsp;&nbsp;<A href="javascript:selectTag('艺术作品')" class="f_black">艺术作品</A>&nbsp;&nbsp;<A href="javascript:selectTag('签名设计')" class="f_black">签名设计</A>&nbsp;&nbsp;<A href="javascript:selectTag('花鸟画')" class="f_black">花鸟画</A>&nbsp;&nbsp;<A href="javascript:selectTag('插图')" class="f_black">插图</A>&nbsp;&nbsp;</div><div class="tag_2_1" id="recommend_2833" style="display:none;"><A href="javascript:selectTag('中医')" class="f_black">中医</A>&nbsp;&nbsp;<A href="javascript:selectTag('西医')" class="f_black">西医</A>&nbsp;&nbsp;<A href="javascript:selectTag('保健')" class="f_black">保健</A>&nbsp;&nbsp;<A href="javascript:selectTag('补品')" class="f_black">补品</A>&nbsp;&nbsp;<A href="javascript:selectTag('发烧')" class="f_black">发烧</A>&nbsp;&nbsp;<A href="javascript:selectTag('减肥')" class="f_black">减肥</A>&nbsp;&nbsp;<A href="javascript:selectTag('健康')" class="f_black">健康</A>&nbsp;&nbsp;<A href="javascript:selectTag('健康话题')" class="f_black">健康话题</A>&nbsp;&nbsp;<A href="javascript:selectTag('健身')" class="f_black">健身</A>&nbsp;&nbsp;<A href="javascript:selectTag('经络')" class="f_black">经络</A>&nbsp;&nbsp;<A href="javascript:selectTag('偏方')" class="f_black">偏方</A>&nbsp;&nbsp;<br/><A href="javascript:selectTag('生病')" class="f_black">生病</A>&nbsp;&nbsp;<A href="javascript:selectTag('养生')" class="f_black">养生</A>&nbsp;&nbsp;<A href="javascript:selectTag('药补')" class="f_black">药补</A>&nbsp;&nbsp;<A href="javascript:selectTag('医学')" class="f_black">医学</A>&nbsp;&nbsp;<A href="javascript:selectTag('乙肝')" class="f_black">乙肝</A>&nbsp;&nbsp;<A href="javascript:selectTag('针灸')" class="f_black">针灸</A>&nbsp;&nbsp;</div>                   		
			</div>

			<div class="tag_2" id="myTags" style="display:none;">
				<div class="tag_2_1"><A href="javascript:selectTag('必须要标签？')" class="f_black">必须要标签？</A>&nbsp;&nbsp;</div>
			</div>
		</div>
	    <div>文章来源：
	      <input type="radio" id="PostType"  name="PostType" value="原创"  checked=checked  >原创
	      <input type="radio" id="Radio1" name="PostType" value="转贴"  >转贴
	      <input type="radio" id="Radio2" name="PostType" value="引用"  >引用
	      <input type="radio" id="Radio3" name="PostType" value="翻译"  >翻译
	    </div>
		
	  </div>

  </div>
  <div class="r_b">
	  <!--收缩部分-->
	  <div class="r_title1"><A onclick="ToggleVisible('Editor_Edit_Advanced_Contents','Editor_Edit_Advanced_LinkImage','/images/dot_up.gif','/images/dot_down.gif'); return false;" href="#"><span style="float:left">更多选项：</span><span style=" float:right; margin-right:30px;"><img src="../images/dot_down.gif" id="Editor_Edit_Advanced_LinkImage" alt="" width="15" height="15" /></span></A></div>
	  <div id="Editor_Edit_Advanced_Contents" style="display:none;" class="r_c">
	    <div>文章来源：
	      <input name="NewEditArticle1:SourceTextbox" type="text" id="NewEditArticle1_SourceTextbox" class="input1" style="width:650px" /><br />
	    <span class="f_gray1" style="margin-left:60px;"> (转载他人文章请在此注明来源媒体)</span>

		</div>
	    <div>转载地址：
	      <input name="NewEditArticle1:SourceUrlTextbox" type="text" id="NewEditArticle1_SourceUrlTextbox" class="input1" style="width:650px" /><br />
	    <span class="f_gray1" style="margin-left:60px;"> (转载他人文章请在此注明来源网络地址)</span>		
		</div>
	    <div><span style="vertical-align:top">摘　　要：</span>
	      <textarea name="NewEditArticle1:BriefTextbox" rows="3" id="NewEditArticle1_BriefTextbox" style="width:650px;height:54px">测试2 </textarea><br />
	    <span class="f_gray1" style="margin-left:60px;"> (最长1000字, 如果不写系统将自动截取正文内容的前255字做为摘要)</span>		
		</div>

	    <div><span style="vertical-align:top">引用地址：</span>
	      <textarea name="NewEditArticle1:TrackbackTextbox" rows="3" id="NewEditArticle1_TrackbackTextbox" style="width:650px;height:54px">
</textarea><br />
	    <span class="f_gray1" style="margin-left:60px;"> (TrackBack Ping URL, 填写您需要发送引用通告的地址, 可以输入多个，每行一个)</span>		
		</div>
	    <div>浏览权限： 
	    <table id="NewEditArticle1_HideCheckbox" border="0" style="font-size:12px;">
	<tr>
		<td><input id="NewEditArticle1_HideCheckbox_0" type="radio" name="NewEditArticle1:HideCheckbox" value="1" checked="checked" /><label for="NewEditArticle1_HideCheckbox_0">完全公开</label></td><td><input id="NewEditArticle1_HideCheckbox_1" type="radio" name="NewEditArticle1:HideCheckbox" value="4" /><label for="NewEditArticle1_HideCheckbox_1">公开但不在公共页面展示</label></td><td><input id="NewEditArticle1_HideCheckbox_2" type="radio" name="NewEditArticle1:HideCheckbox" value="2" /><label for="NewEditArticle1_HideCheckbox_2">仅好友可见</label></td><td><input id="NewEditArticle1_HideCheckbox_3" type="radio" name="NewEditArticle1:HideCheckbox" value="3" /><label for="NewEditArticle1_HideCheckbox_3">隐藏</label></td>

	</tr>
</table>
	    </div>
	    <div>评　　论：
	      <input id="NewEditArticle1_AcceptCommentCheckbox" type="checkbox" name="NewEditArticle1:AcceptCommentCheckbox" checked="checked" /><label for="NewEditArticle1_AcceptCommentCheckbox">允许评论</label>
		 </div>
	    <div>置　　顶：<span style="color:#7D7D7D;font-size:12px;"><input id="NewEditArticle1_StickOutCheckbox" type="checkbox" name="NewEditArticle1:StickOutCheckbox" /></span>(选中此项则文章将按照以下的两个参数进行置顶, 本选项对隐藏的文章无效)<br />
	    </div>

		<div style="margin-left:60px;">置顶过期时间：<input name="NewEditArticle1:StickOutExpiredTimeTextbox" type="text" id="NewEditArticle1_StickOutExpiredTimeTextbox" class="input1" style="width:125px;" /><span class="f_gray1"> (格式示例: 2005-01-01 12:00:00, 不填表示永不过期)</span></div>
		<div style="margin-left:60px;">置顶排序系数：<input name="NewEditArticle1:StickOutOrderNumberTextbox" type="text" id="NewEditArticle1_StickOutOrderNumberTextbox" class="input1" style="width:50px;" /><span class="f_gray1"> (数字越大则置顶越靠前,如果数字相同则发表时间晚的靠前)</span></div>
	  </div>
  </div>
  <div class="r_b">
	  <!--收缩部分-->

	  <div class="r_title1"><A onclick="ToggleVisible('Editor_Recommend_Circles','Editor_Recommend_Circles_LinkImage','/images/dot_up.gif','/images/dot_down.gif'); return false;" href="#"><span style="float:left">推荐到圈子：</span><span style=" float:right; margin-right:30px;"><img id="Editor_Recommend_Circles_LinkImage" src="../images/dot_down.gif" width="15" height="15" alt="" /></span></A></div>
	  <div id="Editor_Recommend_Circles" style="display:none;" class="r_c">
	    <div>请选择要将文章推荐到哪些圈子里去：        </div>
	    
			<span id="grouptag" style="font-color:ff0000"></span>
    </div>
  </div>
  <div style="clear:both;"></div>
  <div style="padding-left:20px;margin-bottom:10px;">

    <div style="margin-bottom:4px;">
	    <input type="submit" name="NewEditArticle1:PostButton" value="发表" onclick="if(event.srcElement){if(event.srcElement.tagName=='INPUT'){if(document.getElementById('inputsummit').value == 1){return false;}}};if(document.Form1){document.Form1.target='_self';} CopyToClipboard('CE_NewEditArticle1_ContentSpaw_ID_Frame');var title=document.all['NewEditArticle1_TitleTextbox']; if(title.value==''){alert('请输入文章标题。');title.focus();return false;} var tags=document.all['NewEditArticle1_TagTextbox'];if(tags.value==''){alert('请输入或选择标签。');tags.focus();return false;} var regex = / |,|，|　/;var ss=tags.value.replace(/(^\s*)|(\s*$)/g, '').split(regex);if(ss.length>10){alert('标签数不能超过10个。');tags.focus();return false;} for(var x=0; x&lt;ss.length; x++){var len=0;for(var i=0; i&lt;ss[x].length; i++){if(ss[x].substring(i,i+1)>String.fromCharCode(255)){len+=2;}else{len++;}}if(len>20){alert('单个标签名不能超过10个字。');tags.focus();return false;}};" language="javascript" id="NewEditArticle1_PostButton" Class="input2" onsubmit="return checkclick();" onmousedown="document.getElementById('inputsummit').value = 0;" /><input type="hidden" name="chkSelected" ID="chkSelected">&nbsp;&nbsp;&nbsp;&nbsp;  	   
    </div>
	<div class="f_gray1">(文章内容将在提交同时将送到剪贴板，如果您在提交时遇到错误，可立即粘贴出来进行恢复。)</div>
  </div>
</div>
<script language="javascript">
	//初始化编辑器	
	//InitEditor();	
	function showRecommendTags()
{
	eval("r = document.getElementById('norecommendTags')");
	r.style.display = "";
	document.getElementById("myclasstag").className = "tag_btn bg";
	eval("a = document.getElementById('recommendTags')");
	a.style.display = "none";
	document.getElementById("myrecommtag").className = "tag_btn";
	
}
function showNoRecommendTags()
{
	eval("r = document.getElementById('norecommendTags')");
	r.style.display = "none";
	document.getElementById("myclasstag").className = "tag_btn";
	eval("a = document.getElementById('recommendTags')");
	a.style.display = "";
	document.getElementById("myrecommtag").className = "tag_btn bg";
}
function showPersonTags()
{
	eval("r = document.getElementById('recentTags')");
	r.style.display = "";
	eval("a = document.getElementById('allTags')");
	a.style.display = "none";
}
</script>

<input type="hidden" name="__EVENTTARGET" id="__EVENTTARGET" value="" />
<input type="hidden" name="__EVENTARGUMENT" id="__EVENTARGUMENT" value="" />
<script type="text/javascript">
<!--
var theForm = document.forms['form1'];
if (!theForm) {
    theForm = document.form1;
}
function __doPostBack(eventTarget, eventArgument) {
    if (!theForm.onsubmit || (theForm.onsubmit() != false)) {
        theForm.__EVENTTARGET.value = eventTarget;
        theForm.__EVENTARGUMENT.value = eventArgument;
        theForm.submit();
    }
}
// -->

</script>

</form>
<script src="http://utrack.hexun.com/track/track.js"></script>
<!--  页面:结束  -->
</body>
</html>
