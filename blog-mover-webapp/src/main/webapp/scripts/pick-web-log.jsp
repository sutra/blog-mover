<%@ page contentType="text/javascript; charset=UTF-8"%>
/**
 * 用来在界面上显示索引号的变量。
 * 也用来初始化 checkbox，checkbox计数从 0 开始，显示的索引号从 1 开始。
 * 初始化时参考 currentFromIndex 的值。
 */
var count = 0;
/**
 * 当界面显示时需要调用该变量来达到显示目的。
 * @see getWebLogsReply(data)
 */
var cellFuncs = [
	function(data) {
		return "<label><input type='checkbox' id='index" + count 
		+ "' name='index' value='" + count 
		+ "' />&nbsp;" + (++count) 
		+ "</label>";
	},
	function(data) {
		return "<a href='" + data.url + "'>" + data.title + "</a>";
	},
	function(data) {return (data.publishedDate == null ? "" : data.publishedDate.toLocaleString());}
];
/**
 * 日志总数。
 */
var totalCount = 0;
/**
 * 当前界面显示的日志的偏移量。即当前界面是从第几个开始显示的。从 0 开始计数。
 */
var currentFromIndex = 0;
/**
 * 在界面上显示日志。当翻页按钮按下时调用。
 * @param p 翻页方式。-2，到最前面；-1，上一页；1；下一页。
 */
function showWebLogs(p) {
	setStatus("正在读取日志……");
	$("firstButton").disabled = true;
	$("previousButton").disabled = true;
	$("refreshButton").disabled = true;
	$("nextButton").disabled = true;
	$("lastButton").disabled = true;
	var fromIndex;
	var toIndex;
	var pageSize = parseInt($("pageSize").value);
	switch(p) {
		case -2: 
			fromIndex = 0;
			break;
		case -1: 
			fromIndex = currentFromIndex - pageSize; 
			break;
		case 0:
			fromIndex = currentFromIndex;
			break;
		case 1:
			fromIndex = currentFromIndex + pageSize;
			break;
		case 2:
			var lastPageSize = totalCount % pageSize;
			if (lastPageSize == 0) {
				lastPageSize = pageSize;
			}
			fromIndex = totalCount - lastPageSize;
			break;
	}
	getWebLogs(fromIndex, pageSize);
}
/**
 * @param firstResult 从第几个开始，从 0 开始计数。
 * @param maxResults 获取的结果集的元素个数的最大数目。
 */
function getWebLogs(firstResult, maxResults) {
	debug("firstResult: " + firstResult + ", maxResults: " + maxResults + ".");
	User.getWebLogs(
		firstResult, 
		maxResults, 
		{callback:getWebLogsReply, errorHandler:getWebLogsErrorHandler}
		);
}
/**
 * 获取日志成功时的处理。
 */
function getWebLogsReply(data) {
	totalCount = parseInt(data[0]);
	var fromIndex = parseInt(data[1]);
	var toIndex = fromIndex + data[2].length;
	debug("totalCount = " + totalCount + ", fromIndex = " + fromIndex + ", toIndex = " + toIndex);
	DWRUtil.setValue("totalCount", totalCount);
	$("index").checked = false;
	DWRUtil.removeAllRows('webLogsTbody');
	count = fromIndex;
	DWRUtil.setEscapeHtml(false);
	DWRUtil.addRows("webLogsTbody", data[2], cellFuncs);
	
	currentFromIndex = fromIndex;
	setStatus("读取成功。");
	var previousButtonDisabled = (fromIndex == 0);
	$("firstButton").disabled = previousButtonDisabled;
	$("previousButton").disabled = (fromIndex == 0);
	$("refreshButton").disabled = false;
	var nextButtonDisabled = (toIndex == totalCount);
	$("nextButton").disabled = nextButtonDisabled;
	$("lastButton").disabled = nextButtonDisabled;
}
/**
 * 获取日志发生错误时的处理。
 */
function getWebLogsErrorHandler(errorString, exception) {
	setStatus("读取失败：" + errorString + exception);
	alert("读取失败：\r\n" + errorString + exception);
	$("firstButton").disabled = false;
	$("previousButton").disabled = false;
	$("refreshButton").disabled = false;
	$("nextButton").disabled = false;
	$("lastButton").disabled = false;
}

/**
 * 选中所有/取消选中所有。
 */
function checkAll(checked) {
	var indices = $("webLogsTbody").getElementsByTagName("input");
	for (var i = 0; i < indices.length; i++) {
		var index = indices[i];
		if ((!index.disabled) && index.name == "index") {
			indices[i].checked = checked;
		}
	}
}
/**
 * 删除已经选中的日志。
 */
function deleteWebLogs() {
	$("deleteButton").disabled = true;
	var checkedIndices = getCheckedIndices();
	if (checkedIndices[1].length > 0) {
		if (confirm("确定删除？")) {
			setStatus("正在删除……");
			User.deleteWebLogs(
				checkedIndices[1], 
				{
					callback:function(data) {
						setStatus("删除成功。");
						removeFromView(checkedIndices[0]);
						$("deleteButton").disabled = false;
					}, 
					errorHandler:function(errorString, exception) {
						setStatus("删除失败：" + errorString);
						$("deleteButton").disabled = false;
					}
				}
				);
		} else {
			$("deleteButton").disabled = false;
		}		
	} else {
		alert("没有选择任何日志。");
		$("deleteButton").disabled = false;
	}
}
/**
 * @return 
 * 一个一维数组，
 * 数组元素第一个元素是一个被选中的 checkbox 对象的数组，
 * 第二个元素是被选中的 checkbox 的值的数组。
 */
function getCheckedIndices() {
	var checkedIndexElements = new Array();
	var checkedIndices = new Array();
	var indices = $("webLogsTbody").getElementsByTagName("input");
	for (var i = 0; i < indices.length; i++) {
		var index = indices[i];
		if ((!index.disabled) && index.name == "index" && index.checked) {
			checkedIndexElements.push(index);
			checkedIndices.push(index.value);
		}
	}
	return new Array(checkedIndexElements, checkedIndices);
}
/**
 * 将指定的 checkbox 设置成 disabled 状态。
 * @param indexElements 需要从视图上删除的 checkbox 元素。
 */
function removeFromView(indexElements) {
/*
	for (var i = 0; i < indexElements.length; i++) {
		indexElements[i].disabled = true;
	}
*/
	showWebLogs(0); // 还是用刷新一下替代 disabled checkbox，用户体验较好。
}
/**
 * 清除所有日志。
 */
function clearWebLogs() {
	$("clearButton").disabled = true;
	if (confirm("确定清空？")) {
		setStatus("正在清空……");
		User.clearWebLogs(
			{
				callback:function(data){
					setStatus("已经清空。");
					$("clearButton").disabled = false;
					showWebLogs(-2);
				},
				errorHandler:function(errorString, exception) {
					setStatus("清空失败：" + errorString);
					$("clearButton").disabled = false;
					showWebLogs(-2);
				}
			}
			);
	} else {
		$("clearButton").disabled = false;
	}
}