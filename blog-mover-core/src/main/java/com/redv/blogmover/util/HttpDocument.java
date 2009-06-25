/**
 *  Created on 2006-7-14 0:32:35
 */
package com.redv.blogmover.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HeaderGroup;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.IOUtils;
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

	private static final Log log = LogFactory.getLog(HttpDocument.class);

	private transient HttpClient httpClient;

	private HeaderGroup requestHeaderGroup;

	private boolean manualCookie;

	private boolean followRedirects;

	private String encoding;

	private String requestCharSet;

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
	 * @return requestCharSet
	 */
	public String getRequestCharSet() {
		return requestCharSet;
	}

	/**
	 * @param requestCharSet
	 *            要设置的 requestCharSet
	 * @see CharSetPostMethod
	 */
	public void setRequestCharSet(String requestCharSet) {
		this.requestCharSet = requestCharSet;
	}

	/**
	 * Execute `get' method.
	 * 
	 * @param url
	 * @return
	 */
	public Document get(String url) {
		return get(url, null);
	}

	/**
	 * Execute `get' method.
	 * 
	 * @param url
	 * @param requestHeaderGroup
	 * @return
	 */
	public Document get(String url, HeaderGroup requestHeaderGroup) {
		GetMethod getMethod = new GetMethod(url);
		getMethod.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
		getMethod.setFollowRedirects(followRedirects);
		addRequestHeaderGroup(getMethod, this.requestHeaderGroup);
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
	@SuppressWarnings("unchecked")
	public Document post(String action, NameValuePair[] parameters,
			HeaderGroup requestHeaderGroup) {
		if (log.isDebugEnabled()) {
			log.debug("action: " + action);
			log.debug("parameters: ");
			for (NameValuePair parameter : parameters) {
				log.debug(parameter.getName() + ":" + parameter.getValue());
			}
			log.debug("requestHeaderGroup: ");

			if (requestHeaderGroup != null) {
				for (Iterator<Header> iter = requestHeaderGroup.getIterator(); iter
						.hasNext();) {
					Header header = iter.next();
					log.debug(header.getName() + ":" + header.getValue());
				}
			}
		}
		PostMethod postMethod;
		if (this.requestCharSet == null) {
			postMethod = new PostMethod(action);
		} else {
			postMethod = new CharSetPostMethod(action, this.requestCharSet);
		}
		postMethod.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
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
	public Document post(String action, Collection<NameValuePair> parameters) {
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
	public Document post(String action, Collection<NameValuePair> parameters,
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
		if (log.isDebugEnabled()) {
			Header[] headers = method.getRequestHeaders();
			log.debug("---- request header ----");
			for (Header header : headers) {
				log.debug(header.getName() + ":" + header.getValue());
			}
		}
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
			InputStream inputStream;
			Header contentEncodingHeader = method
					.getResponseHeader("Content-Encoding");
			if (contentEncodingHeader != null
					&& "deflate".equals(contentEncodingHeader.getValue())) {
				InputStream body = method.getResponseBodyAsStream();
				try {
					inputStream = new ByteArrayInputStream(decompress(body));
				} catch (DataFormatException e) {
					throw new IOException(e.getMessage());
				} finally {
					body.close();
				}
			} else {
				inputStream = method.getResponseBodyAsStream();
			}
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
			String url = null;
			try {
				url = method.getURI().toString();
			} catch (URIException e1) {
			}
			throw new BlogMoverRuntimeException("HttpException: url=" + url, e);
		} catch (IOException e) {
			String url = null;
			try {
				url = method.getURI().toString();
			} catch (URIException e1) {
			}
			throw new BlogMoverRuntimeException("HttpException: url=" + url, e);
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

	public static byte[] decompress(InputStream inputStream)
			throws IOException, DataFormatException {
		// Decompress the bytes
		Inflater decompresser = new Inflater(true);
		decompresser.setInput(IOUtils.toByteArray(inputStream));
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			byte[] result = new byte[2048];
			do {
				int resultLength = decompresser.inflate(result);
				os.write(result, 0, resultLength);
			} while (!decompresser.finished());
		} finally {
			decompresser.end();
		}
		return os.toByteArray();
	}
}
