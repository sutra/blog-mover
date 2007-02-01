/**
 * 
 */
package com.redv.blogmover.blogengines.cn.oblog;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.redv.blogmover.WebLog;
import com.redv.blogmover.util.HtmlFileToDocument;

/**
 * @author <a href="mail:zhoushuqun@gmail.com">Sutra</a>
 * 
 */
public abstract class AbstractModifyWebLogHtmlPageParserTest {

	private ModifyWebLogHtmlPageParser parser;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.parser = new ModifyWebLogHtmlPageParser();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	protected void testParse(String htmlFile, String encoding,
			String expectedTitle, String expectedBody,
			Date expectedPublishedDate, String[] expectedTags)
			throws IOException, SAXException {
		Document document = new HtmlFileToDocument().getDocument(this
				.getClass().getResource(htmlFile), encoding);
		parser.setDocument(document);
		parser.parse();
		WebLog webLog = parser.getWebLog();
		assertEquals(expectedTitle, webLog.getTitle());
		assertEquals(expectedBody, webLog.getBody());
		assertEquals(expectedPublishedDate, webLog.getPublishedDate());
		assertEquals(expectedTags, webLog.getTags());
	}

}
