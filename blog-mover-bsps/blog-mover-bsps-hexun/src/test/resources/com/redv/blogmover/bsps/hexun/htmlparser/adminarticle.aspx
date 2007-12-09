
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<HTML>
	<HEAD>
	    <title>文章管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link href="http://home.hexun.com/manage/style/right.css" rel="stylesheet" type="text/css"/>		
	</HEAD>
  <body>
 <form name="Form1" method="post" action="adminarticle.aspx?blogname=blogremover&amp;categoryid=0&amp;page=1" id="Form1">
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="/wEPDwUKMTI4MTI2NzYwMGRk/Is9BJW08Mrl46J8MFHsHTy66KE=" />

<div class="right">
	
<script>
        function dolocation(categoryid,categoryname)
        {
            document.location.href = "http://post.blog.hexun.com/inc/adminarticle.aspx?blogname=blogremover&categoryid="+categoryid+"&categoryname="+escape(categoryname);
        }
	    function isDel()
	    {
		    var x = window.confirm('是否删除？');
		    if (x)
		    {
			    return true;
		    }
		    else
		    {
			    return false;
		    }
	    }
	    function funSelectAll(checked)
	    {
		    var len = document.Form1.elements.length;
		    var i;
		    for(i=0;i<len;i++)
		    {
			    if(document.Form1.elements[i].name == "ArticleID")
			    {
					    document.Form1.elements[i].checked = checked;
			    }
		    }
	    }
	    
	    function doInsert()
	    {
	        var alertMessage = "请选择一条要转移的文章！";
	        var alertMessage1 = "请选择要转移到的分类！";
			
			var oTable,oTr,oTd,oCheck;
			var i;
			var checkNum = 0;
			var chkStr = "";
	
			len = document.Form1.elements.length;
			var i;
			for (i=0; i < len; i++) 
			{
				if (document.Form1.elements[i].name=="ArticleID") 
				{ 
					if(document.Form1.elements[i].checked== true)
					{
						chkStr += document.Form1.elements[i].value +",";
						checkNum++;
					}
				}
			}
			
			var sel = document.getElementById("select2");
			if(sel.value == 0)
			{
			    alert(alertMessage);
			    return;
			}
   
			if(checkNum ==0)
			{
				alert(alertMessage);
				return ;
			}
			
			document.getElementById("chkSelected").value = chkStr;
			
			
			var deleteMessage = "";		
			deleteMessage = "选中将被转移，确定？";
	
			if (confirm(deleteMessage))
			{	
				document.Form1.action="http://post.blog.hexun.com/inc/adminarticle.aspx?blogname=blogremover&deltype=2&page=1";
				document.Form1.submit();
				return true;			
			}
	    }

        function doCheck()
		{
			var alertMessage = "请选择一条要删除的纪录！";
			
			var oTable,oTr,oTd,oCheck;
			var i;
			var checkNum = 0;
			var chkStr = "";
	
			len = document.Form1.elements.length;
			var i;
			for (i=0; i < len; i++) 
			{
				if (document.Form1.elements[i].name=="ArticleID") 
				{ 
					if(document.Form1.elements[i].checked== true)
					{
						chkStr += document.Form1.elements[i].value +",";
						checkNum++;
					}
				}
			}
   
			if(checkNum ==0)
			{
				alert(alertMessage);
				return ;
			}
			
			document.getElementById("chkSelected").value = chkStr;
			
			
			var deleteMessage = "";		
			deleteMessage = "选中将被删除，确定？";
	
			if (confirm(deleteMessage))
			{	
				document.Form1.action="http://post.blog.hexun.com/inc/adminarticle.aspx?blogname=blogremover&deltype=1&page=1";
				document.Form1.submit();
				return true;			
			}	
		}
</script>

<div class="r_b">
	  <div class="r_title1">博客文章</div><input id="chkSelected" type="hidden" name="chkSelected" />
    <div class="r_d">
	    <div>
	      
        </div>
		<div class="table_bg1">
			<div class="t_1_1">标 题</div>
			<div style="color:#969696;">|</div>
			<div class="t_2">属 性</div>
			<div style="color:#969696; ">|</div>
			<div class="t_2">分类</div>
			<div style="color:#969696; ">|</div>
			<div class="t_2">发表时间</div>
			<div style="color:#969696;">|</div>
			<div class="t_2">功能</div>
		</div>
		<div class='table_bg3'><div class='t_3_1'><input type='checkbox' name='ArticleID' value='4574698' /><a href='http://blogremover.blog.hexun.com/4574698_d.html' target='_blank'>测试21</a></div><div class='t_4'>原创/公开</div><div class='t_4'></div><div class='t_4'>07-12 19:14 </div><div class='t_5'><a href='http://post.blog.hexun.com/blogremover/postarticle.aspx?articleid=4574698' class='f_red1' target='_blank'>修改</a>/<a href='http://post.blog.hexun.com/blogremover/RecommendArticle.aspx?articleid=4574698' class='f_red1' target='_blank'>推荐</a></div></div><div class='table_bg3'><div class='t_3_1'><input type='checkbox' name='ArticleID' value='4574694' /><a href='http://blogremover.blog.hexun.com/4574694_d.html' target='_blank'>测试20</a></div><div class='t_4'>原创/公开</div><div class='t_4'></div><div class='t_4'>07-12 19:13 </div><div class='t_5'><a href='http://post.blog.hexun.com/blogremover/postarticle.aspx?articleid=4574694' class='f_red1' target='_blank'>修改</a>/<a href='http://post.blog.hexun.com/blogremover/RecommendArticle.aspx?articleid=4574694' class='f_red1' target='_blank'>推荐</a></div></div><div class='table_bg3'><div class='t_3_1'><input type='checkbox' name='ArticleID' value='4574691' /><a href='http://blogremover.blog.hexun.com/4574691_d.html' target='_blank'>测试19</a></div><div class='t_4'>原创/公开</div><div class='t_4'></div><div class='t_4'>07-12 19:13 </div><div class='t_5'><a href='http://post.blog.hexun.com/blogremover/postarticle.aspx?articleid=4574691' class='f_red1' target='_blank'>修改</a>/<a href='http://post.blog.hexun.com/blogremover/RecommendArticle.aspx?articleid=4574691' class='f_red1' target='_blank'>推荐</a></div></div><div class='table_bg3'><div class='t_3_1'><input type='checkbox' name='ArticleID' value='4574687' /><a href='http://blogremover.blog.hexun.com/4574687_d.html' target='_blank'>测试18</a></div><div class='t_4'>原创/公开</div><div class='t_4'></div><div class='t_4'>07-12 19:13 </div><div class='t_5'><a href='http://post.blog.hexun.com/blogremover/postarticle.aspx?articleid=4574687' class='f_red1' target='_blank'>修改</a>/<a href='http://post.blog.hexun.com/blogremover/RecommendArticle.aspx?articleid=4574687' class='f_red1' target='_blank'>推荐</a></div></div><div class='table_bg3'><div class='t_3_1'><input type='checkbox' name='ArticleID' value='4574685' /><a href='http://blogremover.blog.hexun.com/4574685_d.html' target='_blank'>测试17</a></div><div class='t_4'>原创/公开</div><div class='t_4'></div><div class='t_4'>07-12 19:13 </div><div class='t_5'><a href='http://post.blog.hexun.com/blogremover/postarticle.aspx?articleid=4574685' class='f_red1' target='_blank'>修改</a>/<a href='http://post.blog.hexun.com/blogremover/RecommendArticle.aspx?articleid=4574685' class='f_red1' target='_blank'>推荐</a></div></div><div class='table_bg3'><div class='t_3_1'><input type='checkbox' name='ArticleID' value='4574681' /><a href='http://blogremover.blog.hexun.com/4574681_d.html' target='_blank'>测试16</a></div><div class='t_4'>原创/公开</div><div class='t_4'></div><div class='t_4'>07-12 19:13 </div><div class='t_5'><a href='http://post.blog.hexun.com/blogremover/postarticle.aspx?articleid=4574681' class='f_red1' target='_blank'>修改</a>/<a href='http://post.blog.hexun.com/blogremover/RecommendArticle.aspx?articleid=4574681' class='f_red1' target='_blank'>推荐</a></div></div><div class='table_bg3'><div class='t_3_1'><input type='checkbox' name='ArticleID' value='4574673' /><a href='http://blogremover.blog.hexun.com/4574673_d.html' target='_blank'>测试15</a></div><div class='t_4'>原创/公开</div><div class='t_4'></div><div class='t_4'>07-12 19:12 </div><div class='t_5'><a href='http://post.blog.hexun.com/blogremover/postarticle.aspx?articleid=4574673' class='f_red1' target='_blank'>修改</a>/<a href='http://post.blog.hexun.com/blogremover/RecommendArticle.aspx?articleid=4574673' class='f_red1' target='_blank'>推荐</a></div></div><div class='table_bg3'><div class='t_3_1'><input type='checkbox' name='ArticleID' value='4574667' /><a href='http://blogremover.blog.hexun.com/4574667_d.html' target='_blank'>测试14</a></div><div class='t_4'>原创/公开</div><div class='t_4'></div><div class='t_4'>07-12 19:12 </div><div class='t_5'><a href='http://post.blog.hexun.com/blogremover/postarticle.aspx?articleid=4574667' class='f_red1' target='_blank'>修改</a>/<a href='http://post.blog.hexun.com/blogremover/RecommendArticle.aspx?articleid=4574667' class='f_red1' target='_blank'>推荐</a></div></div><div class='table_bg3'><div class='t_3_1'><input type='checkbox' name='ArticleID' value='4574666' /><a href='http://blogremover.blog.hexun.com/4574666_d.html' target='_blank'>测试13</a></div><div class='t_4'>原创/公开</div><div class='t_4'></div><div class='t_4'>07-12 19:12 </div><div class='t_5'><a href='http://post.blog.hexun.com/blogremover/postarticle.aspx?articleid=4574666' class='f_red1' target='_blank'>修改</a>/<a href='http://post.blog.hexun.com/blogremover/RecommendArticle.aspx?articleid=4574666' class='f_red1' target='_blank'>推荐</a></div></div><div class='table_bg3'><div class='t_3_1'><input type='checkbox' name='ArticleID' value='4574662' /><a href='http://blogremover.blog.hexun.com/4574662_d.html' target='_blank'>测试12</a></div><div class='t_4'>原创/公开</div><div class='t_4'></div><div class='t_4'>07-12 19:12 </div><div class='t_5'><a href='http://post.blog.hexun.com/blogremover/postarticle.aspx?articleid=4574662' class='f_red1' target='_blank'>修改</a>/<a href='http://post.blog.hexun.com/blogremover/RecommendArticle.aspx?articleid=4574662' class='f_red1' target='_blank'>推荐</a></div></div><div class='table_bg3'><div class='t_3_1'><input type='checkbox' name='ArticleID' value='4574657' /><a href='http://blogremover.blog.hexun.com/4574657_d.html' target='_blank'>测试11</a></div><div class='t_4'>原创/公开</div><div class='t_4'></div><div class='t_4'>07-12 19:11 </div><div class='t_5'><a href='http://post.blog.hexun.com/blogremover/postarticle.aspx?articleid=4574657' class='f_red1' target='_blank'>修改</a>/<a href='http://post.blog.hexun.com/blogremover/RecommendArticle.aspx?articleid=4574657' class='f_red1' target='_blank'>推荐</a></div></div><div class='table_bg3'><div class='t_3_1'><input type='checkbox' name='ArticleID' value='4574653' /><a href='http://blogremover.blog.hexun.com/4574653_d.html' target='_blank'>测试10</a></div><div class='t_4'>原创/公开</div><div class='t_4'></div><div class='t_4'>07-12 19:11 </div><div class='t_5'><a href='http://post.blog.hexun.com/blogremover/postarticle.aspx?articleid=4574653' class='f_red1' target='_blank'>修改</a>/<a href='http://post.blog.hexun.com/blogremover/RecommendArticle.aspx?articleid=4574653' class='f_red1' target='_blank'>推荐</a></div></div><div class='table_bg3'><div class='t_3_1'><input type='checkbox' name='ArticleID' value='4574647' /><a href='http://blogremover.blog.hexun.com/4574647_d.html' target='_blank'>测试9</a></div><div class='t_4'>原创/公开</div><div class='t_4'></div><div class='t_4'>07-12 19:11 </div><div class='t_5'><a href='http://post.blog.hexun.com/blogremover/postarticle.aspx?articleid=4574647' class='f_red1' target='_blank'>修改</a>/<a href='http://post.blog.hexun.com/blogremover/RecommendArticle.aspx?articleid=4574647' class='f_red1' target='_blank'>推荐</a></div></div><div class='table_bg3'><div class='t_3_1'><input type='checkbox' name='ArticleID' value='4574646' /><a href='http://blogremover.blog.hexun.com/4574646_d.html' target='_blank'>测试8</a></div><div class='t_4'>原创/公开</div><div class='t_4'></div><div class='t_4'>07-12 19:11 </div><div class='t_5'><a href='http://post.blog.hexun.com/blogremover/postarticle.aspx?articleid=4574646' class='f_red1' target='_blank'>修改</a>/<a href='http://post.blog.hexun.com/blogremover/RecommendArticle.aspx?articleid=4574646' class='f_red1' target='_blank'>推荐</a></div></div><div class='table_bg3'><div class='t_3_1'><input type='checkbox' name='ArticleID' value='4574643' /><a href='http://blogremover.blog.hexun.com/4574643_d.html' target='_blank'>测试7</a></div><div class='t_4'>原创/公开</div><div class='t_4'></div><div class='t_4'>07-12 19:10 </div><div class='t_5'><a href='http://post.blog.hexun.com/blogremover/postarticle.aspx?articleid=4574643' class='f_red1' target='_blank'>修改</a>/<a href='http://post.blog.hexun.com/blogremover/RecommendArticle.aspx?articleid=4574643' class='f_red1' target='_blank'>推荐</a></div></div><div class='table_bg3'><div class='t_3_1'><input type='checkbox' name='ArticleID' value='4574640' /><a href='http://blogremover.blog.hexun.com/4574640_d.html' target='_blank'>测试6</a></div><div class='t_4'>转贴/公开</div><div class='t_4'></div><div class='t_4'>07-12 19:10 </div><div class='t_5'><a href='http://post.blog.hexun.com/blogremover/postarticle.aspx?articleid=4574640' class='f_red1' target='_blank'>修改</a>/<a href='http://post.blog.hexun.com/blogremover/RecommendArticle.aspx?articleid=4574640' class='f_red1' target='_blank'>推荐</a></div></div><div class='table_bg3'><div class='t_3_1'><input type='checkbox' name='ArticleID' value='4574637' /><a href='http://blogremover.blog.hexun.com/4574637_d.html' target='_blank'>测试5</a></div><div class='t_4'>转贴/公开</div><div class='t_4'></div><div class='t_4'>07-12 19:10 </div><div class='t_5'><a href='http://post.blog.hexun.com/blogremover/postarticle.aspx?articleid=4574637' class='f_red1' target='_blank'>修改</a>/<a href='http://post.blog.hexun.com/blogremover/RecommendArticle.aspx?articleid=4574637' class='f_red1' target='_blank'>推荐</a></div></div><div class='table_bg3'><div class='t_3_1'><input type='checkbox' name='ArticleID' value='4574635' /><a href='http://blogremover.blog.hexun.com/4574635_d.html' target='_blank'>测试4</a></div><div class='t_4'>原创/公开</div><div class='t_4'></div><div class='t_4'>07-12 19:10 </div><div class='t_5'><a href='http://post.blog.hexun.com/blogremover/postarticle.aspx?articleid=4574635' class='f_red1' target='_blank'>修改</a>/<a href='http://post.blog.hexun.com/blogremover/RecommendArticle.aspx?articleid=4574635' class='f_red1' target='_blank'>推荐</a></div></div><div class='table_bg3'><div class='t_3_1'><input type='checkbox' name='ArticleID' value='4574628' /><a href='http://blogremover.blog.hexun.com/4574628_d.html' target='_blank'>测试3</a></div><div class='t_4'>转贴/公开</div><div class='t_4'></div><div class='t_4'>07-12 19:09 </div><div class='t_5'><a href='http://post.blog.hexun.com/blogremover/postarticle.aspx?articleid=4574628' class='f_red1' target='_blank'>修改</a>/<a href='http://post.blog.hexun.com/blogremover/RecommendArticle.aspx?articleid=4574628' class='f_red1' target='_blank'>推荐</a></div></div><div class='table_bg3'><div class='t_3_1'><input type='checkbox' name='ArticleID' value='4574623' /><a href='http://blogremover.blog.hexun.com/4574623_d.html' target='_blank'>测试2</a></div><div class='t_4'>原创/公开</div><div class='t_4'></div><div class='t_4'>07-12 19:09 </div><div class='t_5'><a href='http://post.blog.hexun.com/blogremover/postarticle.aspx?articleid=4574623' class='f_red1' target='_blank'>修改</a>/<a href='http://post.blog.hexun.com/blogremover/RecommendArticle.aspx?articleid=4574623' class='f_red1' target='_blank'>推荐</a></div></div>
		<div class="r_e"><div class='r_e'> 共21篇　共2页　<font color='#974a5c'>1</font>|<a href='http://post.blog.hexun.com/inc/adminarticle.aspx?blogname=blogremover&categoryid=0&page=2'>2</a> </div></div>
	    <div class="r_e"><input type="button" name="Submit2" onclick="funSelectAll(true)" value="全  选" class="input2" />　  
	    <input type="button" onclick="funSelectAll(false);" name="Submit2" value="取  消" class="input2" />　  
	    <input type="button" name="Submit2" onclick="doCheck();" value="删  除" class="input2" />　
	    
	        <span class="f_gray1">
	        <input type="button" name="Submit22" onclick="doInsert();" value="转移到分类" class="input2" />
          </span>
          </div>
    </div>
  </div>
</div>
</form>
  </body>
</html>
