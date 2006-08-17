/**
 *  Created on 2006-7-14 0:32:35
 */
package com.redv.blogmover.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HeaderGroup;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cyberneko.html.parsers.DOMParser;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.redv.blogmover.BlogRemoverRuntimeException;

/**
 * @author Joe
 * @version 1.0
 * 
 */
public class HttpDocument implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3668507850703993669L;

	@SuppressWarnings("unused")
	private static final Log log = LogFactory.getLog(HttpDocument.class);

	private transient HttpClient httpClient;

	private HeaderGroup requestHeaderGroup;

	private boolean manualCookie;

	private String encoding;

	/**
	 * 
	 */
	public HttpDocument(final HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public HttpDocument(final HttpClient httpClient,
			final HeaderGroup requestHeaderGroup) {
		this(httpClient);
		this.requestHeaderGroup = requestHeaderGroup;
	}

	public HttpDocument(final HttpClient httpClient, final boolean manualCookie) {
		this(httpClient);
		this.manualCookie = manualCookie;
	}

	public HttpDocument(final HttpClient httpClient, final String encoding) {
		this(httpClient);
		this.encoding = encoding;
	}

	public HttpDocument(final HttpClient httpClient,
			final boolean manualCookie, final String encoding) {
		this(httpClient);
		this.encoding = encoding;
		this.manualCookie = manualCookie;
	}

	public HttpDocument(final HttpClient httpClient,
			final HeaderGroup requestHeaderGroup, final boolean manualCookie) {
		this(httpClient, requestHeaderGroup);
		this.manualCookie = manualCookie;
	}

	public HttpDocument(final HttpClient httpClient,
			final HeaderGroup requestHeaderGroup, final boolean manualCookie,
			final String encoding) {
		this(httpClient, requestHeaderGroup, manualCookie);
		this.encoding = encoding;
	}

	/**
	 * @return Returns the encoding.
	 */
	public String getEncoding() {
		return encoding;
	}

	/**
	 * @return Returns the manualCookie.
	 */
	public boolean isManualCookie() {
		return manualCookie;
	}

	/**
	 * @return Returns the requestHeaderGroup.
	 */
	public HeaderGroup getRequestHeaderGroup() {
		return requestHeaderGroup;
	}

	/**
	 * 执行 get 方法。
	 * 
	 * @param url
	 * @return
	 */
	public Document get(String url) {
		GetMethod getMethod = new GetMethod(url);
		addRequestHeaderGroup(getMethod, requestHeaderGroup);
		if (manualCookie) {
			addCookies(getMethod, httpClient.getState().getCookies());
		}
		executeMethod(httpClient, getMethod);
		return getDocument(getMethod);
	}

	/**
	 * 执行 post 方法。
	 * 
	 * @param action
	 * @param parameters
	 * @return
	 */
	public Document post(String action, NameValuePair[] parameters) {
		return post(action, parameters, null);
	}

	public Document post(String action, NameValuePair[] parameters,
			HeaderGroup requestHeaderGroup) {
		PostMethod postMethod = new PostMethod(action);
		addRequestHeaderGroup(postMethod, this.requestHeaderGroup);
		addRequestHeaderGroup(postMethod, requestHeaderGroup);
		if (manualCookie) {
			addCookies(postMethod, httpClient.getState().getCookies());
		}
		if (parameters != null) {
			postMethod.addParameters(parameters);
		}
		executeMethod(httpClient, postMethod);
		return getDocument(postMethod);
	}

	/**
	 * 执行 post 方法。
	 * 
	 * @param action
	 * @param parameters
	 * @return
	 */
	public Document post(String action, List<NameValuePair> parameters) {
		return post(action, parameters, null);
	}

	public Document post(String action, List<NameValuePair> parameters,
			HeaderGroup requestHeaderGroup) {
		NameValuePair[] nvps = null;
		if (parameters != null) {
			nvps = new NameValuePair[parameters.size()];
			parameters.toArray(nvps);
		}
		return post(action, nvps, requestHeaderGroup);
	}

	/**
	 * 
	 * @param method
	 * @return
	 */
	public Document getDocument(HttpMethod method) {
		try {
			return getDocumentInternal(method);
		} catch (SAXException e) {
			throw new BlogRemoverRuntimeException(e);
		} catch (IOException e) {
			throw new BlogRemoverRuntimeException(e);
		}
	}

	/**
	 * 获取指定方法的 Document。
	 * 
	 * @param method
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 */
	private Document getDocumentInternal(HttpMethod method)
			throws SAXException, IOException {
		Document document;
		// 检查是否重定向
		int statuscode = method.getStatusCode();
		if ((statuscode == HttpStatus.SC_MOVED_TEMPORARILY)
				|| (statuscode == HttpStatus.SC_MOVED_PERMANENTLY)
				|| (statuscode == HttpStatus.SC_SEE_OTHER)
				|| (statuscode == HttpStatus.SC_TEMPORARY_REDIRECT)) {
			// 读取新的URL地址
			Header header = method.getResponseHeader("location");
			if (header != null) {
				String newuri = header.getValue();
				if ((newuri == null) || (newuri.equals(""))) {
					newuri = "/";
				}
				org.apache.commons.httpclient.URI parentUri = method.getURI();
				if (!newuri.startsWith(parentUri.getScheme())) {
					newuri = new org.apache.commons.httpclient.URI(parentUri
							.getScheme(), parentUri.getHost(), (newuri
							.startsWith("/") ? newuri : parentUri
							.getAboveHierPath()
							+ newuri), "").toString();
				}
				log.debug("Redirect: " + newuri);
				method.releaseConnection();
				document = get(newuri);
			} else {
				throw new BlogRemoverRuntimeException("Invalid redirect");
			}
		} else {
			if (log.isDebugEnabled()) {
				String s = null;
				if (encoding != null) {
					s = new String(method.getResponseBody(), encoding);
				} else {
					s = method.getResponseBodyAsString();
				}
				log.debug("URI: " + method.getURI().toString()
						+ "\ngetResponseBodyAsString: " + s);
			}
			InputStream inputStream = method.getResponseBodyAsStream();
			try {
				DOMParser parser = new DOMParser();
				InputSource inputSource = new InputSource();
				if (encoding != null) {
					inputSource.setEncoding(encoding);
				}
				inputSource.setByteStream(inputStream);
				log.debug("encoding: " + inputSource.getEncoding());
				parser.parse(inputSource);
				document = parser.getDocument();
			} finally {
				inputStream.close();
				method.releaseConnection();
			}
		}
		return document;
	}

	/**
	 * 将 Cookies 转换成 Header 格式。
	 * 
	 * @param cookies
	 * @return
	 */
	private static String cookieToHeaderString(Cookie[] cookies) {
		StringBuffer ret = new StringBuffer();
		for (Cookie cookie : cookies) {
			ret.append(cookie.getName()).append("=").append(cookie.getValue())
					.append("; ");
		}
		String s = ret.toString();
		log.debug("Cookie string: " + s);
		return s;
	}

	/**
	 * 向 HttpMethod 中添加 Header。
	 * 
	 * @param method
	 * @param requestHeaderGroup
	 */
	public static void addRequestHeaderGroup(HttpMethod method,
			HeaderGroup requestHeaderGroup) {
		if (requestHeaderGroup != null) {
			for (Header header : requestHeaderGroup.getAllHeaders()) {
				method.addRequestHeader(header);
			}
		}
	}

	/**
	 * 执行 HttpMethod。
	 * 
	 * @param httpClient
	 * @param method
	 */
	public static void executeMethod(HttpClient httpClient, HttpMethod method) {
		try {
			httpClient.executeMethod(method);
		} catch (HttpException e) {
			throw new BlogRemoverRuntimeException(e);
		} catch (IOException e) {
			throw new BlogRemoverRuntimeException(e);
		}
	}

	/**
	 * 向方法中添加 cookie。
	 * 
	 * @param method
	 * @param cookies
	 */
	public static void addCookies(HttpMethod method, Cookie[] cookies) {
		if (cookies != null) {
			Header header = method.getRequestHeader("Cookie");
			if (header == null) {
				header = new Header();
			}
			String value = header.getValue();
			if (value == null) {
				value = "";
			}
			if (value.trim().endsWith(";") == false) {
				value += "; ";
			}
			method.addRequestHeader("Cookie", value
					+ cookieToHeaderString(cookies));
		}
	}
}
