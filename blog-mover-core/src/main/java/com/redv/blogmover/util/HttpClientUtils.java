/**
 * 
 */
package com.redv.blogmover.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HeaderGroup;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.redv.blogmover.BlogMoverRuntimeException;

/**
 * @author Sutra Zhou
 */
public class HttpClientUtils {
	private static final Log LOG = LogFactory.getLog(HttpClientUtils.class);

	public static HeaderGroup buildInternetExplorerHeader(String acceptLanguage) {
		return buildInternetExplorerHeader(acceptLanguage, true);
	}

	public static HeaderGroup buildInternetExplorerHeader(
			String acceptLanguage, boolean acceptEncoding) {
		HeaderGroup hg = new HeaderGroup();
		hg
				.addHeader(new Header(
						"Accept",
						"image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, application/x-shockwave-flash, */*"));
		hg.addHeader(new Header("Accept-Language", acceptLanguage));
		hg.addHeader(new Header("UA-CPU", "x86"));
		hg
				.addHeader(new Header("User-Agent",
						"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.2; SV1; .NET CLR 1.1.4322)"));
		if (acceptEncoding) {
			hg.addHeader(new Header("Accept-Encoding", "gzip, deflate"));
		}
		hg.addHeader(new Header("Connection", "Keep-Alive"));

		return hg;
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

	/**
	 * Execute HTTP method.
	 * 
	 * @param httpClient
	 *            the HTTP client
	 * @param method
	 *            the HTTP method to execute
	 * @param maximumRetryTimes
	 *            maximum retry times if timed out
	 */
	static void executeMethod(final HttpClient httpClient,
			final HttpMethod method, final int maximumRetryTimes) {
		try {
			httpClient.executeMethod(method);
		} catch (SocketTimeoutException e) {
			retry(httpClient, method, maximumRetryTimes, e);
		} catch (ConnectTimeoutException e) {
			retry(httpClient, method, maximumRetryTimes, e);
		} catch (HttpException e) {
			throw convertException(method, e);
		} catch (IOException e) {
			throw convertException(method, e);
		}
	}

	static void retry(final HttpClient httpClient, HttpMethod method,
			final int maximumRetryTimes, final Exception e) {
		if (maximumRetryTimes > 0) {
			LOG.debug("Connect timed out, retrying..." + maximumRetryTimes);
			executeMethod(httpClient, method, maximumRetryTimes - 1);
		} else {
			throw convertException(method, e);
		}
	}

	static BlogMoverRuntimeException convertException(
			HttpMethod method, Exception e) {
		String url = null;
		try {
			url = method.getURI().toString();
		} catch (URIException e1) {
		}
		return new BlogMoverRuntimeException("HttpException: url=" + url, e);
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
		LOG.debug("Cookie string: " + s);
		return s;
	}
}
