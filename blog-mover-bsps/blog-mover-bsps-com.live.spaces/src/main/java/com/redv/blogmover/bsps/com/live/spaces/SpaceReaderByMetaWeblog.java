/**
 * Created on 2007-4-21 上午12:50:12
 */
package com.redv.blogmover.bsps.com.live.spaces;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.lang.ObjectUtils;
import org.apache.ws.commons.util.NamespaceContextImpl;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.common.TypeFactoryImpl;
import org.apache.xmlrpc.common.XmlRpcController;
import org.apache.xmlrpc.common.XmlRpcStreamConfig;
import org.apache.xmlrpc.parser.DateParser;
import org.apache.xmlrpc.parser.TypeParser;
import org.apache.xmlrpc.serializer.DateSerializer;
import org.apache.xmlrpc.serializer.TypeSerializer;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.WebLog;
import com.redv.blogmover.impl.AbstractBlogReader;
import com.redv.blogmover.impl.WebLogImpl;
import com.redv.blogmover.util.HttpClientUtils;
import com.redv.blogmover.util.HttpDocument;

/**
 * Blog Mover Reader for http:///spaces.live.com by metaWeblog.
 * <p>
 * It's bsp id is "spaces.live.com-metaWeblog".
 * </p>
 * <p>
 * SpaceReaderByMetaWeblog 的工作原理是：
 * </p>
 * <ul>
 * <li>通过网页分析得到permalinks</li>
 * <li>通过metaWeblog API获取单个permalink的blog条目</li>
 * <ul>
 * <li>读取所有最新更新blog条目时会有数目限制（好像是15或20）</li>
 * <li>但读取单个一个条目没有问题</li>
 * </ul>
 * </ul>
 * <p>
 * 通过metaWeblog的好处有：
 * </p>
 * <ul>
 * <li>减少网页分析量</li>
 * <li>绕过对网页上发表时间的格式要求</li>
 * </ul>
 * <p>
 * 坏处是：
 * </p>
 * <ul>
 * <li>得要开通email publishing (和修改时间格式一样，可能需要提供帮助指令或链接)</li>
 * <li>需要提交用户密码</li>
 * </ul>
 * 
 * @author Ziru Zhou
 * @author Sutra Zhou
 * 
 */
public class SpaceReaderByMetaWeblog extends AbstractBlogReader {
	private HttpDocument httpDocument;

	private String homepageUrl;

	private String username;

	private String password;

	public SpaceReaderByMetaWeblog() {
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

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;

		setHomepageUrl("http://" + username + ".spaces.live.com/blog/");
	}

	/*
	 * /* (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.impl.AbstractBlogReader#read()
	 */
	@Override
	public List<WebLog> read() throws BlogMoverException {
		Document document = httpDocument.get(this.homepageUrl);

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
			WebLog webLog = readOnePost(parsePostId(permalink));
			if (!processNewBlog(webLog))
				return false;
		}

		return true;
	}

	private static class MyTypeFactory extends TypeFactoryImpl {
		public MyTypeFactory(XmlRpcController pController) {
			super(pController);
		}

		private DateFormat newFormat() {
			return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		}

		public TypeParser getParser(XmlRpcStreamConfig pConfig,
				NamespaceContextImpl pContext, String pURI, String pLocalName) {
			if (DateSerializer.DATE_TAG.equals(pLocalName)) {
				return new DateParser(newFormat());
			} else {
				return super.getParser(pConfig, pContext, pURI, pLocalName);
			}
		}

		public TypeSerializer getSerializer(XmlRpcStreamConfig pConfig,
				Object pObject) throws SAXException {
			if (pObject instanceof Date) {
				return new DateSerializer(newFormat());
			} else {
				return super.getSerializer(pConfig, pObject);
			}
		}
	}

	private String parsePostId(String permaLink) throws BlogMoverException {
		String prefix = homepageUrl + "cns!";
		String suffix = ".entry";
		if (permaLink.startsWith(prefix) && permaLink.endsWith(suffix)) {
			return permaLink.substring(prefix.length(), permaLink.length()
					- suffix.length());
		} else {
			throw new BlogMoverException("Invalid PermaLink :<" + permaLink
					+ ">");
		}
	}

	@SuppressWarnings("unchecked")
	public WebLog readOnePost(String postId) throws BlogMoverException {
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		try {
			config.setServerURL(new URL(
					"https://storage.msn.com/storageservice/MetaWeblog.rpc"));
		} catch (MalformedURLException e) {
			throw new BlogMoverException(e);
		}
		config.setEnabledForExtensions(Boolean.TRUE);
		XmlRpcClient client = new XmlRpcClient();
		client.setConfig(config);
		client.setTypeFactory(new MyTypeFactory(client));
		Object[] params = new Object[3];
		params[0] = postId;
		params[1] = username;
		params[2] = password;
		Object o;
		try {
			o = client.execute("metaWeblog.getPost", params);
		} catch (XmlRpcException e) {
			throw new BlogMoverException(e);
		}
		// log.debug(o);
		Map<String, Object> m = (Map<String, Object>) o;
		WebLog webLog = new WebLogImpl();
		webLog.setUrl(ObjectUtils.toString(m.get("permaLink"), null));
		// userid
		webLog.setTitle(m.get("title").toString());
		webLog.setBody(m.get("description").toString());
		// log.debug(webLog.getBody());
		webLog.setPublishedDate((Date) m.get("dateCreated"));
		return webLog;
	}

	/**
	 * @param args
	 * @throws BlogMoverException
	 * @throws MalformedURLException
	 */
	public static void main(String[] args) throws BlogMoverException,
			MalformedURLException {
		SpaceReaderByMetaWeblog reader = new SpaceReaderByMetaWeblog();
		reader.setUsername("username");
		reader.setPassword("xxxxxxxx");
		// reader.readOnePost("67972F30B4F9D8FB!173");
		List<WebLog> list = reader.read();
		System.out.println("# of blogs : " + list.size());
	}

}
