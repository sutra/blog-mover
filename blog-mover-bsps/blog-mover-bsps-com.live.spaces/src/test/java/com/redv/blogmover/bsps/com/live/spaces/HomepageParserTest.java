/**
 * Created on 2007-4-21 下午10:14:13
 */
package com.redv.blogmover.bsps.com.live.spaces;

import java.io.IOException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.redv.blogmover.util.HtmlFileToDocument;

import junit.framework.TestCase;

/**
 * @author Sutra Zhou
 * 
 */
public class HomepageParserTest extends TestCase {
	private HomepageParser hp;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		hp = new HomepageParser();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for
	 * {@link com.redv.blogmover.bsps.com.live.spaces.HomepageParser#parse()}.
	 * 
	 * @throws SAXException
	 * @throws IOException
	 */
	public void testParse() throws IOException, SAXException {
		Document document = new HtmlFileToDocument().getDocument(this
				.getClass().getResource(
						"homepage_zhoushuqun.spaces.live.com.html"), "UTF-8");

		hp.setDocument(document);
		hp.parse();
		String expected = "http://zhoushuqun.spaces.live.com/?_c11_BlogPart_n=1&_c11_BlogPart_handle=cns!2B070A76FD6627CE!924&_c11_BlogPart_FullView=1&_c11_BlogPart_BlogPart=blogview&_c=BlogPart";
		assertEquals(expected, hp.getSecondPage());
	}
}
