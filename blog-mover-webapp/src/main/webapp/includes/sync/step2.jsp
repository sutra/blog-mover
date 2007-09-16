<%@page contentType="text/html; charset=UTF-8"%>
<div id="step2">
	<h3>
		请从下面选择一个 目标BSP ：
	</h3>
	<div style="position: relative;">
		<div id="writers">
			正在加载……
			<script type="text/javascript">
			loadWriters();
			</script>
		</div>
		<div style="position: absolute;right:0px;top:0px">
			<jsp:include flush="true" page="/ads/step3.jsp" />
		</div>
	</div>
	<div id="selectedWriterDiv">
		请从上面支持的 BSP 中选择目标 BSP。
	</div>
	<input type="button" id="syncDestReadButton" value="读入(W)"
		onclick="blogSyncDestInitAndRead()" disabled="disabled" accesskey="W" />
</div>