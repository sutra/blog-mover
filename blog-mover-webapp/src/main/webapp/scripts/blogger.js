/**
 * This is the javascript for reader/writer of blogger(http://www.blogger.com/).
 * @see metaWeblog.js
 */
function Blogger(w) {
	this.w = w;
	this.reply0 = function (data) {
		if (data !== null && typeof data == "object") {
		// alert(DWRUtil.toDescriptiveString(data, 2));
			var id = w + ".blogger.blogid";
			DWRUtil.removeAllOptions(id);
			for (var i = 0; i < data.length; i += 1) {
				DWRUtil.addOptions(id, [{name:data[i].blogid, text:data[i].blogName}], "name", "text");
			}
		} else {
		//alert(DWRUtil.toDescriptiveString(data, 1));
		}
	};
	this.getUsersBlogs = function () {
		var serverURL = $(w + ".blogger.serverURL").value;
		var username = $(w + ".blogger.username").value;
		var password = $(w + ".blogger.password").value;
		if (serverURL === "" || username === "") {
			alert("ServerURL and username can't be empty.");
		} else {
			BloggerClient.getUsersBlogs(serverURL, username, password, this.reply0);
		}
		return false;
	};
}
