/**
 * Created on 2007-4-21 上午12:50:12
 */
package com.redv.blogmover.bsps.com.live.spaces;

import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;

import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.WebLog;
import com.redv.blogmover.impl.AbstractBlogReader;
import com.redv.blogmover.util.HttpClientUtils;
import com.redv.blogmover.util.HttpDocument;

/**
 * Blog Mover Reader for http:///spaces.live.com.
 * <p>
 * It's bsp id is "spaces.live.com".
 * </p>
 * 
 * @author Sutra Zhou
 * 
 */
public class SpaceReader extends AbstractBlogReader {
	private final Log log = LogFactory.getLog(SpaceReader.class);

	private static final Pattern homepageUrlPattern = Pattern
			.compile("http://.*.spaces.live.com[/]{0,1}");

	protected HttpDocument httpDocument;

	protected String homepageUrl;

	protected String blogHomepageUrl;

	public SpaceReader() {
		final HttpClient httpClient = new HttpClient();

		// 如果没有设定cookie模式将会有警告：WARN
		// [org.apache.commons.httpclient.HttpMethodBase] - Cookie rejected:
		// "$Version=0;
		// wlru=http%3a%2f%2feyeth.spaces.live.com%2fblog%2fcns!DDA97AA9E929B1C4!325.entry;
		// $Path=/; $Domain=spaces.live.com". Domain attribute "spaces.live.com"
		// violates RFC 2109: domain must start with a dot
		httpClient.getParams().setCookiePolicy(
				CookiePolicy.BROWSER_COMPATIBILITY);

		httpDocument = new HttpDocument(httpClient, HttpClientUtils
				.buildInternetExplorerHeader("zh-cn", false));
	}

	/**
	 * @param homepageUrl
	 *            the homepageUrl to set
	 */
	public void setHomepageUrl(String homepageUrl) {
		this.homepageUrl = homepageUrl;
	}

	/*
	 * /* (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.impl.AbstractBlogReader#read()
	 */
	@Override
	public List<WebLog> read() throws BlogMoverException {
		if (!homepageUrlPattern.matcher(this.homepageUrl).matches()) {
			throw new BlogMoverException("输入的首页地址不正确。");
		}
		if (this.homepageUrl.endsWith("/")) {
			this.blogHomepageUrl = this.homepageUrl + "blog/";
		} else {
			this.blogHomepageUrl = this.homepageUrl + "/blog/";
		}

		Document document = httpDocument.get(this.blogHomepageUrl);

		while (true) {
			ListParser lp = new ListParser();
			lp.setDocument(document);
			lp.parse();

			if (!detail(lp.getPermalinks()))
				break;

			String nextPageUrl = lp.getNextPageUrl();
			if (nextPageUrl == null)
				break;

			document = httpDocument.get(nextPageUrl);
		}

		return getWebLogs();
	}

	private boolean detail(List<String> permalinks) throws BlogMoverException {
		for (String permalink : permalinks) {
			WebLog webLog = readPostByPermalink(permalink);
			if (!processNewBlog(webLog))
				return false;
		}

		return true;
	}

	protected WebLog readPostByPermalink(String permalink)
			throws BlogMoverException {
		log.debug("Permalink: " + permalink);
		EntryParser ep = new EntryParser();
		ep.setDocument(httpDocument.get(permalink));
		ep.setId(EntryParser.parseId(permalink));
		ep.parse();

		WebLog webLog = ep.getWebLog();
		webLog.setUrl(permalink);
		return webLog;
	}

	/**
	 * @param args
	 * @throws BlogMoverException
	 */
	public static void main(String[] args) throws BlogMoverException {
		SpaceReader sr = new SpaceReader();
		sr.setHomepageUrl("http://zhoushuqun.spaces.live.com");
		List<WebLog> webLogs = sr.read();
		for (WebLog webLog : webLogs) {
			System.out.println(webLog.getTitle());
		}
	}
}
