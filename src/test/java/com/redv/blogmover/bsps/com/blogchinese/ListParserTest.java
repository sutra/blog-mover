/**
 * Created on 2007-1-28 上午12:01:13
 */
package com.redv.blogmover.bsps.com.blogchinese;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.cyberneko.html.parsers.DOMParser;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author shutra
 * 
 */
public class ListParserTest {
	private ListParser listParser;

	private Document document;

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
		listParser = new ListParser();
		InputStream inputStream = this.getClass().getResourceAsStream(
				"user_blogmanage.asp");
		try {
			Reader reader = new InputStreamReader(inputStream, "UTF-8");
			document = getDocument(reader);
		} finally {
			inputStream.close();
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link com.redv.blogmover.bsps.com.blogchinese.ListParser#parse(org.w3c.dom.Document)}.
	 */
	@Test
	public void testParse() {
		listParser.parse(document);
		assertEquals(2, listParser.getLastPageNumber());
		assertEquals(21, listParser.getTotalCount());
		List<String> webLogIds = listParser.getWebLogIds();
		assertEquals(20, webLogIds.size());
		String[] ids = new String[] { "939711", "939712", "939713", "939714",
				"939718", "939722", "939724", "939727", "939730", "939736",
				"939739", "939741", "939742", "939743", "939745", "939751",
				"939753", "939755", "939756", "939703" };
		for (int i = 0; i < ids.length; i++) {
			assertEquals(ids[i], webLogIds.get(i));
		}
	}

	/**
	 * Get the xml dom document from a reader.
	 * 
	 * @param characterStream
	 * @return the xml dom of the character stream.
	 * @throws SAXException
	 * @throws IOException
	 */
	public static Document getDocument(Reader characterStream)
			throws SAXException, IOException {
		DOMParser parser = new DOMParser();
		parser
				.setFeature(
						"http://cyberneko.org/html/features/scanner/notify-builtin-refs",
						true);
		parser.setProperty("http://cyberneko.org/html/properties/names/elems",
				"lower");
		InputSource inputSource = new InputSource();
		inputSource.setCharacterStream(characterStream);
		parser.parse(inputSource);
		return parser.getDocument();
	}
}
