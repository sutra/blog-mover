/**
 *  Created on 2006-6-21 23:56:56
 */
package com.redv.blogremover.bsps.csdn;

import java.io.IOException;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.redv.blogremover.BlogRemoverException;
import com.redv.blogremover.BlogWriter;
import com.redv.blogremover.Status;
import com.redv.blogremover.WebLog;
import com.redv.blogremover.impl.StatusImpl;

/**
 * http://blog.csdn.net
 * <p>
 * e.g. http://blog.csdn.net/blogremover <br />
 * Username：blogremover
 * </p>
 * 
 * @author Joe
 * @version 1.0
 * 
 */
public class CSDNBlogWriter implements BlogWriter {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(CSDNBlogWriter.class);

	static final String SET_COOKIE_HEADER_NAME = "Set-Cookie";

	private Status status;

	private final CSDNBlogWriterUtil writerUtil = new CSDNBlogWriterUtil(this);

	HttpClient httpClient;

	Map<String, String> cookies;

	Document loginPageDocument;

	String username;

	String password;

	String identifyingCode;

	/**
	 * @throws SAXException
	 * @throws IOException
	 * @throws HttpException
	 * 
	 */
	public CSDNBlogWriter() throws HttpException, IOException, SAXException {
		super();
		this.status = new StatusImpl();
		this.httpClient = new HttpClient();
		// 如果没有设定cookie模式将会有警告：Cookie rejected
		httpClient.getParams().setCookiePolicy(
				CookiePolicy.BROWSER_COMPATIBILITY);
		this.cookies = new Hashtable<String, String>();
		this.loginPageDocument = this.writerUtil.getLoginPage();
	}

	/**
	 * @return Returns the identifyingCode.
	 */
	public String getIdentifyingCode() {
		return identifyingCode;
	}

	/**
	 * @param identifyingCode
	 *            The identifyingCode to set.
	 */
	public void setIdentifyingCode(String identifyingCode) {
		this.identifyingCode = identifyingCode;
	}

	/**
	 * @return Returns the password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return Returns the username.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            The username to set.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	boolean loggedIn = false;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogremover.BlogWriter#write(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	public void write(List<WebLog> webLogs) throws BlogRemoverException {
		log.debug("write(java.util.List) called. webLogs.size()="
				+ webLogs.size());
		this.status.setTotalCount(webLogs.size());
		// 排序
		Collections.sort(webLogs);

		writerUtil.checkLogin();
		int i = 0;
		for (WebLog webLog : webLogs) {
			try {
				this.status.setCurrentWebLog(webLog);
				writerUtil.write(webLog);
				this.status.setCurrentCount(++i);
			} catch (HttpException e) {
				throw new BlogRemoverException(e);
			} catch (IOException e) {
				throw new BlogRemoverException(e);
			} catch (SAXException e) {
				throw new BlogRemoverException(e);
			}
		}
	}

	public Status getStatus() {
		return status;
	}

	public byte[] getIdentifyingCodeImage() throws BlogRemoverException {
		String showExPwdUrl = "http://passport.csdn.net/"
				+ this.writerUtil.getShowExPwdUrl(loginPageDocument);
		showExPwdUrl = showExPwdUrl.replace(" ", "%20");

		try {
			return this.showExPwd(showExPwdUrl);
		} catch (HttpException e) {
			throw new BlogRemoverException(e);
		} catch (IOException e) {
			throw new BlogRemoverException(e);
		}
	}

	byte[] showExPwd(String showExPwdUrl) throws HttpException, IOException {
		GetMethod showExPwdMethod = new GetMethod(showExPwdUrl);

		log.debug(showExPwdUrl);

		httpClient.executeMethod(showExPwdMethod);

		log.debug("---- ShowExPwd ResponseHeaders start ----");
		Header[] headers = showExPwdMethod.getResponseHeaders();
		for (Header header : headers) {
			log.debug(header.getName() + ": " + header.getValue());
		}
		log.debug("---- ShowExPwd ResponseHeaders end ----");

		writerUtil.setCookie(showExPwdMethod
				.getResponseHeaders(SET_COOKIE_HEADER_NAME));

		long l = showExPwdMethod.getResponseContentLength();
		log.debug("contentLength=" + l);

		byte[] bytes = showExPwdMethod.getResponseBody();
		// FileOutputStream fos = new FileOutputStream(new File(
		// SystemUtils.JAVA_IO_TMPDIR, "ShowExPwd.jpg"));
		// fos.write(bytes);
		// fos.close();

		// byte[] buffer = new byte[5];
		// System.out.print("Please input: ");
		// System.in.read(buffer, 0, 5);
		// this.checkCode = new String(buffer);
		return bytes;
	}

}
