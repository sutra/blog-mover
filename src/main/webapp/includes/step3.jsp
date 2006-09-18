<%@page contentType="text/html; charset=UTF-8"%>
<div id="step3">
	<h3>
		请从下面选择一个 BSP 用于写入：
	</h3>
	<div id="writers">
		正在加载……
		<script type="text/javascript">
		loadWriters();
		</script>
	</div>
	<div id="selectedWriterDiv">
		请从上面支持的 BSP 中选择目标 BSP。
	</div>
	<input type="button" id="writeButton" value="写入(W)" onclick="blogWrite()" disabled="disabled" accesskey="W" />
</div>
