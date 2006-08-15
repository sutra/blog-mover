<%@page contentType="text/html; charset=UTF-8"%>
<div id="step1">
	<h3>
		请从下面选择一个 BSP 用于读取：
	</h3>
	<div id="readers">
		正在加载……
		<script type="text/javascript">
		loadReaders();
		</script>
	</div>
	<div id="selectedReaderDiv">
		请从上面支持的 BSP 中选择一个来源 BSP。
	</div>
	<input type="button" id="readButton" value="读取" onclick="blogRead()" disabled="disabled" />
</div>
