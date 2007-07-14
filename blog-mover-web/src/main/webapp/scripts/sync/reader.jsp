<%@ page contentType="text/javascript; charset=UTF-8"%>
// read
var readerBusy = false;
var reading = false;
function showDivElem(id) {
  var elem = document.getElementById(id);
  if (elem)
    elem.setAttribute("style", "display:block");
}

function hideDivElem(id) {
  var elem = document.getElementById(id);
  if (elem)
    elem.setAttribute("style", "display:none");
}

function setReader(className) {
	$("initSyncSrcButton").disabled = true;
  hideDivElem("readerFilterDiv");
  
	setStatus("设置读取器……");
	debug("设置读取器：" + className);
	// DWREngine.setReverseAjax(true);
	SyncUser.setSyncSrc(
		className, 
		{
			callback:setReaderReply, 
			errorHandler:function(errorString, exception)
			{
				// DWREngine.setReverseAjax(false);
				setStatus("设置读取器失败：" + errorString);
				alert("设置读取器失败：\n\n" + errorString);
				$("initSyncSrcButton").disabled = false;
			}
		}
		);
}
function setReaderReply(data) {
	// DWREngine.setReverseAjax(false);
	var readerId = g_readerId;
	var url = 'readers/' + readerId + '.jsp?now=' + new Date().getTime();
	var myAjax = new Ajax.Updater(
		'selectedReaderDiv',
		url,
		{method: 'get', 
		parameters: '', 
		onLoading: function(request) {
			placeholder.innerHtml = "...";
		},
		onComplete: function(request) {
			setStatus("读取器设置完毕。");
			$('initSyncSrcButton').disabled = false;
      showDivElem("readerFilterDiv");
		},
		onFailure: reportError, 
		evalScripts: true, 
		asynchronous:true}
		);
}
function initSyncSrc() {
	if (writerBusy) {
		alert("正在写入，请勿读取。");
	} else {
		$("initSyncSrcButton").disabled = true;
		// DWREngine.setErrorHandler(readErrorHandler);
		setStatus("初始化读取器……");
		initReader();
	}
}
function initReader() {
	readerBusy = true;
	setStatus("正在初始化读取器……");
	var replyed = false;
	var propertyName, lpropertyValue;
	var inputLen, selectLen;
	
	var inputItems = $("selectedReaderDiv").getElementsByTagName("input");
	var selectItems = $("selectedReaderDiv").getElementsByTagName("select");
	inputLen = inputItems.length;
	selectLen = selectItems.length;
  
  // set the read filter
  var filterClassId = $("blogFilterId").value;
  if (filterClassId == "BlogFilterByCount") {
    SyncUser.setSyncSrcProperty("blogFilterId", filterClassId + ":" + $("maxCount").value,
      {callback:function(data){}, errorHandler:initReaderErrorHandler});
  } else if (filterClassId == "BlogFilterByPubDate") {
    SyncUser.setFilter("blogFilterId", filterClassId + ":" + $("withinDays").value,
      {callback:function(data){}, errorHandler:initReaderErrorHandler});
  }
      
	for (var i = 0; i < inputLen; i++) {
		//User.setReaderProperty(items[i].name, items[i].value);
		propertyName = inputItems[i].name;
		propertyValue = DWRUtil.getValue(propertyName)
		debug("SetReaderProperty: " + propertyName + "=" + propertyValue);
		if (i == inputLen - 1 && selectLen == 0) {
			debug("将会调用initReaderReply。");
			SyncUser.setSyncSrcProperty(
				propertyName, 
				propertyValue, 
				{callback:initReaderReply, errorHandler:initReaderErrorHandler}
				);
		} else {
			SyncUser.setSyncSrcProperty(
				propertyName, 
				propertyValue,
				{callback:function(data){}, errorHandler:initReaderErrorHandler}
				);
		}
		
	}
	for (var i = 0; i< selectLen; i++) {
		propertyName = selectItems[i].name;
		propertyValue = DWRUtil.getValue(propertyName)
		debug("SetReaderProperty: " + propertyName + "=" + propertyValue);
		if (i == selectLen - 1) {
			debug("将会调用initReaderReply。");
			SyncUser.setSyncSrcProperty(
				propertyName, 
				propertyValue, 
				{callback:initReaderReply, errorHandler:initReaderErrorHandler}
				);
		} else {
			SyncUser.setSyncSrcProperty(
				propertyName, 
				propertyValue,
				{callback:function(data){}, errorHandler:initReaderErrorHandler}
				);
		}
	}
	
}
function initReaderReply() {
	reading = false;
	readerBusy = false;
	setStatus("设置完毕");
	$("initSyncSrcButton").disabled = false;
	
	// setStatus("正在读取……");
	// reading = true;
	// setTimeout("readStatus()", 1000 * parseInt($F("statusRefreshInterval")));
	// SyncUser.read({callback:readReply, errorHandler:readErrorHandler});
}
function initReaderErrorHandler(errorString, exception) {
	setStatus("初始化读取器失败：" + errorString);
	alert("初始化读取器失败：\r\n" + errorString);
	readerBusy = false;
	$("initSyncSrcButton").disabled = false;
}
function readReply() {
	// alert("读取完毕。");
	reading = false;
	readerBusy = false;
	setStatus("读取完毕。");

	// Refresh the web log list.
	setStatus("正在刷新 Step 3 中的日志列表……");
	showWebLogs(0)

	$("initSyncSrcButton").disabled = false;
	$("syncDestWriteButton").disabled = false;
}
function readErrorHandler(msg, exception) {
	reading = false;
	readerBusy = false;
	setStatus("读取时发生如下错误：" + msg);
	alert("读取时发生如下错误：\r\n" + msg);
	$("initSyncSrcButton").disabled = false;
}
function readStatus() {
	debug("调用读取状态函数。");
	if (reading) {
		SyncUser.getSyncSrcReaderStatus(
			{callback:readStatusReply, errorHandler:readStatusErrorHandler}
			);
	}
}
function readStatusReply(data) {
	// 读取尚未结束
	if (reading) {
		var s = "正在读取…… 当前读取数目/总数： " + data.currentCount + "/" 
			+ (data.totalCount == -1 ? "未知" : data.totalCount) + "，当前正在处理的日志：" 
			+ (data.currentWebLog.title === null ? "" : data.currentWebLog.title) 
			+ "(" + data.currentWebLog.publishedDate + ")。";
		setStatus(s);
		debug(s);
		// 继续查询读取状态。
		setTimeout("readStatus()", 1000 * parseInt($F("statusRefreshInterval")));
	}
}
function readStatusErrorHandler(errorString, exception) {
	if (reading) {
		setStatus("获取读取状态失败：" + errorString);
		debug("获取读取状态失败：" + errorString);
		// 继续查询读取状态。
		setTimeout("readStatus()", 1000 * parseInt($F("statusRefreshInterval")));		
	}
}

var lastFilterClassId = "BlogFilterNone";
function updateReaderFilterClass(newFilterClassId) {
  if (lastFilterClassId == newFilterClassId)
    return;
  
  hideDivElem(lastFilterClassId);
  showDivElem(newFilterClassId);
  lastFilterClassId = newFilterClassId; 
}