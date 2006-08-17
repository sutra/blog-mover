/**
 *  Created on 2006-7-2 5:31:58
 */
package com.redv.blogmover.bsps.sohu;

import java.io.IOException;
import java.io.Serializable;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Joe
 * @version 1.0
 * 
 */
class SohuBlogLogin implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2738863475426731485L;

	private static final Log log = LogFactory.getLog(SohuBlogLogin.class);

	private HttpClient httpClient;

	/**
	 * 
	 */
	public SohuBlogLogin(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public boolean login(String username, String maildomain, String passwd)
			throws HttpException, IOException {
		boolean ret = false;
		PostMethod pm = new PostMethod("http://passport.sohu.com/login.jsp");
		httpClient.getParams().setParameter("User-Agent",
				ManageUrlConstants.USER_AGENT);
		pm.addRequestHeader("User-Agent", ManageUrlConstants.USER_AGENT);
		// pm.addRequestHeader("Cookie", BlogRemoverUtils
		// .cookieToHeaderString(httpClient.getState().getCookies()));
		pm.addParameter("loginid", username + maildomain);
		pm.addParameter("username", username);
		pm.addParameter("maildomain", maildomain);
		pm.addParameter("passwd", passwd);
		pm
				.addParameter(
						"extend",
						"function%28object%29+%7B%0D%0A++return+Object.extend.apply%28this%2C+%5Bthis%2C+object%5D%29%3B%0D%0A%7D");
		pm.addParameter("appid", "1019");
		pm
				.addParameter("ru",
						"http://blog.sohu.com/login/logon.do?bru=http://blog-remover.blog.sohu.com/");
		pm.addParameter("fl", "0");
		pm.addParameter("vr", "1|1");
		pm.addParameter("ct", "1151790259469000");
		pm.addParameter("sg", "b179d8b2f1680b23b35d3bed335b9280");
		pm.addParameter("eru", "http://blog.sohu.com/login/logon.do");
		httpClient.executeMethod(pm);

		log.debug("StatusCode = " + pm.getStatusCode());
		if (pm.getStatusCode() == 302) {
			if (pm.getResponseBodyAsString().indexOf("errMsg") != -1) {
				ret = false;
			}
		}

		Header header = pm.getResponseHeader("location");
		pm.releaseConnection();
		if (header != null) {
			String newuri = header.getValue();
			if (newuri == null || newuri.equals("")) {
			} else {
				log.debug("Redirect target: " + newuri);
				GetMethod redirect = new GetMethod(newuri);
				redirect.addRequestHeader("User-Agent",
						ManageUrlConstants.USER_AGENT);
				// redirect.addRequestHeader("Cookie", BlogRemoverUtils
				// .cookieToHeaderString(httpClient.getState()
				// .getCookies()));
				int statusCode = httpClient.executeMethod(redirect);
				// log.debug(redirect.getResponseBodyAsString());
				redirect.releaseConnection();
				if (statusCode == HttpStatus.SC_FORBIDDEN) {
					ret = false;
				} else {
					ret = true;
				}
			}
		}

		return ret;
	}

}
