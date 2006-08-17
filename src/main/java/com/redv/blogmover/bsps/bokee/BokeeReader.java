/**
 * Created on 2006-7-6 14:41:58
 */
package com.redv.blogmover.bsps.bokee;

import java.io.IOException;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.xml.sax.SAXException;

import com.redv.blogmover.BlogRemoverException;
import com.redv.blogmover.WebLog;
import com.redv.blogmover.impl.AbstractBlogReader;

/**
 * @author Joe
 * @version 1.0
 */
public class BokeeReader extends AbstractBlogReader {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5088575804415839973L;

	private HttpClient httpClient;

	public BokeeReader() {
		httpClient = new HttpClient();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogremover.BlogReader#read()
	 */
	@Override
	public List<WebLog> read() throws BlogRemoverException {
		try {
			BokeeLogin.login(httpClient, "blogremover", "wangjing");
		} catch (HttpException e) {
			throw new BlogRemoverException(e);
		} catch (IOException e) {
			throw new BlogRemoverException(e);
		} catch (SAXException e) {
			throw new BlogRemoverException(e);
		} catch (BlogRemoverException e) {
			throw new BlogRemoverException(e);
		}
		return null;
	}

}
