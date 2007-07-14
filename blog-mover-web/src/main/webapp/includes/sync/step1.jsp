<%@page contentType="text/html; charset=UTF-8"%>
<div id="step1">
	<h3>
		请从下面选择一个 源BSP ：
	</h3>
	<div style="position: relative;">
		<div id="readers">
			正在加载……
			<script type="text/javascript">
			loadReaders();
			</script>
		</div>
		<div style="position: absolute;right:0px;top:0px;">
			<jsp:include flush="true" page="/ads/step1.jsp" />
		</div>
	</div>
	<div id="selectedReaderDiv">
		请从上面支持的 BSP 中选择一个来源 BSP。
	</div>
	
	<div id="readerFilterDiv" style="display:none">
    <table><tr>
      <td>
        <select id="blogFilterId" onchange="return updateReaderFilterClass(this.value);" style="width:130px;">
          <option value="BlogFilterNone">无</option>
          <option value="BlogFilterByCount">按贴子数过滤</option>
          <option value="BlogFilterByPubDate">按发表日期过滤</option>
        </select>
      </td>
      <td>
        <div id="BlogFilterNone" style="display:block">&nbsp;</div>
        <div id="BlogFilterByCount" style="display:none">
        	不超过
        	<select id="maxCount">
        		<option value="10">10</option>
        		<option value="20">20</option>
        		<option value="50">50</option>
        		<option value="100">100</option>
        	</select>
        	条帖子
        </div>
        <div id="BlogFilterByPubDate" style="display:none">
        	最近
        	<select id="withinDays" style="width:60px;">
        		<option value="1">一天</option>
        		<option value="7">一周</option>
        		<option value="31">一月</option>
        		<option value="365">一年</option>
        	</select>
        	内的帖子
        </div>
      </td>  
    </tr></table>
	</div>
	<input type="button" id="initSyncSrcButton" value="读取(R)" onclick="initSyncSrc()"
		disabled="disabled" tabindex="200" accesskey="R" />
</div>