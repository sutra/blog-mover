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

import com.redv.blogmover.BlogMoverRuntimeException;

/**
 * The http utility functions for returning document.
 * 
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

	private boolean followRedirects;

	private String encoding;

	/**
	 * 
	 */
	public HttpDocument(final HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	/**
	 * 
	 * @param httpClient
	 * @param requestHeaderGroup
	 */
	public HttpDocument(final HttpClient httpClient,
			final HeaderGroup requestHeaderGroup) {
		this(httpClient);
		this.requestHeaderGroup = requestHeaderGroup;
	}

	/**
	 * 
	 * @param httpClient
	 * @param requestHeaderGroup
	 * @param encoding
	 */
	public HttpDocument(final HttpClient httpClient,
			final HeaderGroup requestHeaderGroup, String encoding) {
		this(httpClient);
		this.requestHeaderGroup = requestHeaderGroup;
		this.encoding = encoding;
	}

	/**
	 * 
	 * @param httpClient
	 * @param manualCookie
	 */
	public HttpDocument(final HttpClient httpClient, final boolean manualCookie) {
		this(httpClient);
		this.manualCookie = manualCookie;
	}

	/**
	 * 
	 * @param httpClient
	 * @param encoding
	 */
	public HttpDocument(final HttpClient httpClient, final String encoding) {
		this(httpClient);
		this.encoding = encoding;
	}

	/**
	 * 
	 * @param httpClient
	 * @param manualCookie
	 * @param encoding
	 */
	public HttpDocument(final HttpClient httpClient,
			final boolean manualCookie, final String encoding) {
		this(httpClient);
		this.encoding = encoding;
		this.manualCookie = manualCookie;
	}

	/**
	 * 
	 * @param httpClient
	 * @param requestHeaderGroup
	 * @param manualCookie
	 */
	public HttpDocument(final HttpClient httpClient,
			final HeaderGroup requestHeaderGroup, final boolean manualCookie) {
		this(httpClient, requestHeaderGroup);
		this.manualCookie = manualCookie;
	}

	/**
	 * 
	 * @param httpClient
	 * @param requestHeaderGroup
	 * @param manualCookie
	 * @param encoding
	 */
	public HttpDocument(final HttpClient httpClient,
			final HeaderGroup requestHeaderGroup, final boolean manualCookie,
			final String encoding) {
		this(httpClient, requestHeaderGroup, manualCookie);
		this.encoding = encoding;
	}

	/**
	 * @return the httpClient
	 */
	public HttpClient getHttpClient() {
		return httpClient;
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
	 * @return the followRedirects
	 */
	public boolean isFollowRedirects() {
		return followRedirects;
	}

	/**
	 * @param followRedirects
	 *            the followRedirects to set
	 */
	public void setFollowRedirects(boolean followRedirects) {
		this.followRedirects = followRedirects;
	}

	/**
	 * Execute `get' method.
	 * 
	 * @param url
	 * @return
	 */
	public Document get(String url) {
		GetMethod getMethod = new GetMethod(url);
		getMethod.setFollowRedirects(followRedirects);
		addRequestHeaderGroup(getMethod, requestHeaderGroup);
		if (manualCookie) {
			addCookies(getMethod, httpClient.getState().getCookies());
		}
		executeMethod(httpClient, getMethod);
		return getDocument(getMethod);
	}

	/**
	 * Execute `post' method.
	 * 
	 * @param action
	 * @param parameters
	 * @return
	 */
	public Document post(String action, NameValuePair[] parameters) {
		return post(action, parameters, null);
	}

	/**
	 * Execute `post' method.
	 * 
	 * @param action
	 * @param parameters
	 * @param requestHeaderGroup
	 * @return
	 */
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
	 * Execute `post' method.
	 * 
	 * @param action
	 * @param parameters
	 * @return
	 */
	public Document post(String action, List<NameValuePair> parameters) {
		return post(action, parameters, null);
	}

	/**
	 * Execute `post' method.
	 * 
	 * @param action
	 * @param parameters
	 * @param requestHeaderGroup
	 * @return
	 */
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
	 * Get response document from method.
	 * 
	 * @param method
	 * @return
	 */
	public Document getDocument(HttpMethod method) {
		try {
			return getDocumentInternal(method);
		} catch (SAXException e) {
			throw new BlogMoverRuntimeException(e);
		} catch (IOException e) {
			throw new BlogMoverRuntimeException(e);
		}
	}

	/**
	 * Get response document from a method.
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
					String s = newuri.startsWith("/") ? newuri : parentUri
							.getAboveHierPath()
							+ newuri;
					// Really need to decode?
					log.debug("before decode:" + s);
					s = java.net.URLDecoder.decode(s, "UTF-8");
					newuri = new org.apache.commons.httpclient.URI(parentUri
							.getScheme(), parentUri.getHost(), (s), "")
							.toString();
				}
				log.debug("Redirect: " + newuri);
				method.releaseConnection();
				document = get(newuri);
			} else {
				throw new BlogMoverRuntimeException("Invalid redirect");
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
	 * Convert the cookies to header format.
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
	 * Add headers to http method.
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
	 * Execute http method.
	 * 
	 * @param httpClient
	 * @param method
	 */
	public static void executeMethod(HttpClient httpClient, HttpMethod method) {
		try {
			httpClient.executeMethod(method);
		} catch (HttpException e) {
			throw new BlogMoverRuntimeException(e);
		} catch (IOException e) {
			throw new BlogMoverRuntimeException(e);
		}
	}

	/**
	 * Add cookies to http method.
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
