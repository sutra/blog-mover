/**
 * Created on 2007-4-21 上午12:50:12
 */
package com.redv.blogmover.bsps.com.live.spaces;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
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

	private final Set<String> readedPageUrls = new HashSet<String>();

	private HttpDocument httpDocument;

	private String homepageUrl;

	public SpaceReader() {
		final HttpClient httpClient = new HttpClient();
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
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.impl.AbstractBlogReader#read()
	 */
	@Override
	public List<WebLog> read() throws BlogMoverException {
		List<WebLog> webLogs = new ArrayList<WebLog>();
		Document document = httpDocument.get(this.homepageUrl);
		HomepageParser hp = new HomepageParser();
		hp.setDocument(document);
		hp.parse();

		// Second page.
		// 从 homepage 得到的是第二页，首先把第二页读取完成。
		String secondPage = hp.getSecondPage();
		ListParser lp = new ListParser();
		lp.setDocument(httpDocument.get(secondPage));
		lp.parse();
		List<WebLog> secondListPageWebLogs = detail(lp.getPermalinks());

		this.readedPageUrls.add(secondPage);

		String firstPage = lp.getPreviousPageUrl();
		// 从第二页解析后得到的第三页的地址存起来。
		String thirdPage = lp.getNextPageUrl();

		// First page.
		// 从第二页得到第一页的 URL 读取第一页。
		if (!this.readedPageUrls.contains(firstPage)) {
			lp = new ListParser();
			lp.setDocument(httpDocument.get(firstPage));
			lp.parse();
			List<WebLog> listWebLogs = detail(lp.getPermalinks());
			webLogs.addAll(listWebLogs);
			this.status.setCurrentCount(webLogs.size());
			this.readedPageUrls.add(firstPage);
		}

		// 加入第二页的日志。
		webLogs.addAll(secondListPageWebLogs);
		this.status.setCurrentCount(webLogs.size());

		// 从第三页开始，然后第四页，直到最后一页。
		String nextPage = thirdPage;
		while (nextPage != null) {
			if (!this.readedPageUrls.contains(nextPage)) {
				lp = new ListParser();
				lp.setDocument(httpDocument.get(nextPage));
				lp.parse();

				List<WebLog> listWebLogs = detail(lp.getPermalinks());
				webLogs.addAll(listWebLogs);
				this.status.setCurrentCount(webLogs.size());
				this.readedPageUrls.add(nextPage);

				// Next page.
				nextPage = lp.getNextPageUrl();
			} else {
				break;
			}
		}
		return webLogs;
	}

	private List<WebLog> detail(List<String> permalinks) {
		List<WebLog> webLogs = new ArrayList<WebLog>(permalinks.size());
		for (String permalink : permalinks) {
			log.debug("Permalink: " + permalink);
			EntryParser ep = new EntryParser();
			ep.setDocument(httpDocument.get(permalink));
			ep.setId(EntryParser.parseId(permalink));
			ep.parse();

			WebLog webLog = ep.getWebLog();
			webLogs.add(webLog);
			this.status.setCurrentWebLog(webLog);
		}
		return webLogs;
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
