
function MetaWeblog(w) {
	this.w = w;
	this.reply0 = function (data) {
		if (data !== null && typeof data == "object") {
		// alert(DWRUtil.toDescriptiveString(data, 2));
			var id = w + ".metaWeblog.blogid";
			DWRUtil.removeAllOptions(id);
			for (var i = 0; i < data.length; i += 1) {
				DWRUtil.addOptions(id, [{name:data[i].blogid, text:data[i].blogName}], "name", "text");
			}
		} else {
		//alert(DWRUtil.toDescriptiveString(data, 1));
		}
	};
	this.getUsersBlogs = function () {
		var serverURL = $(w + ".metaWeblog.serverURL").value;
		var username = $(w + ".metaWeblog.username").value;
		var password = $(w + ".metaWeblog.password").value;
		if (serverURL === "" || username === "") {
			alert("ServerURL and username can't be empty.");
		} else {
			BloggerClient.getUsersBlogs(serverURL, username, password, this.reply0);
		}
		return false;
	};
}

