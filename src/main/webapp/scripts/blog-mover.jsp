<%@ page contentType="text/javascript; charset=UTF-8"%>
// Step selection.
var tabs = new Array("step1", "step2", "step3", "option", "about");
function selectTab(tab) {
	doSelect(tab);
	for (var i = 0; i < tabs.length; i++) {
		if (tabs[i] != tab) {
			doUnSelect(tabs[i]);
		}
	}
	setCookie ("selectedTab", tab);
	return false;
}
function doSelect(tab) {
	$(tab).style.display = "block";
	var t = "menu_" + tab;
	var activeMenu = $(t);
	activeMenu.className = "selected";
	try { // Surround with try...catch as Netscape 8.1 is not supported.
		activeMenu.blur();
	} catch(Exception){
	}
}
function doUnSelect(tab) {
	var t = "menu_" + tab;
	$(t).className = "";
	$(tab).style.display = "none";
}

function update(url, pars, placeholder) {
	var myAjax = new Ajax.Updater(
		placeholder,
		url,
		{method: 'get', 
		parameters: pars, 
		onLoading: function(request) {
			placeholder.innerHtml = "...";
		},
		onFailure: reportError, 
		evalScripts: true, 
		asynchronous:true}
		);
}
function reportError(request) {
	alert('Sorry. There was an error.');
}
function loadReaders() {
	update("blog-mover-readers.jsp", "", "readers");
}
function loadWriters() {
	update("blog-mover-writers.jsp", "", "writers");
}

// Status.
function setStatus(s) {
	debug("setStatus: " + s);
	DWRUtil.setValue("status", s);
}

// Debug.
var debugEnabled = false;
function debug(s) {
	if (debugEnabled) {
		DWRUtil.setValue("debug", $F("debug") + "\n" + new Date() + " " + s);
	}
}
function setDebug(b) {
	debugEnabled = b;
}
function showDebug(b) {
	if (b) {
		$("debugContainer").style.display = "block";
	} else {
		$("debugContainer").style.display = "none";
	}
}

// Reader choosing.
var g_readerId;
function selectReader(readerId, readerClassName) {
	if (readerBusy) {
		alert("读取器忙，请不要修改读取器。");
		return false;
	} else {
		g_readerId = readerId;
		setReader(readerClassName);
		return true;
	}
}

// Writer choosing.
var g_writerId;
function selectWriter(writerId, writerClassName) {
	if (writerBusy) {
		alert("写入器忙，请不要修改写入器。");
		return false;
	} else {
		g_writerId = writerId;
		setWriter(writerClassName);
		return true;
	}
}
