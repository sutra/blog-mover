/**
 * Created on 2007-1-28 上午12:01:13
 */
package com.redv.blogmover.bsps.com.blogchinese;

import java.io.IOException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.redv.blogmover.blogengines.cn.oblog.test.AbstractListWebLogHtmlPageParserTest;
import com.redv.blogmover.util.HtmlFileToDocument;

/**
 * @author <a href="mailto:zhoushuqun@gmail.com">Sutra</a>
 * 
 */
public class ListWebLogHtmlPageParserTest extends
		AbstractListWebLogHtmlPageParserTest {

	/**
	 * Test method for
	 * {@link com.redv.blogmover.blogengines.cn.oblog.ListWebLogHtmlPageParser#parse(org.w3c.dom.Document)}.
	 * 
	 * @throws SAXException
	 * @throws IOException
	 */
	public void testParse() throws IOException, SAXException {
		testParse("user_blogmanage.asp.html", "gb2312", 1, 2, 21, new String[] {
				"939711", "939712", "939713", "939714", "939718", "939722",
				"939724", "939727", "939730", "939736", "939739", "939741",
				"939742", "939743", "939745", "939751", "939753", "939755",
				"939756", "939703" }, new String[] {
				"06091/260430/archives/2007/2007127212020.shtml",
				"06091/260430/archives/2007/2007127212644.shtml",
				"06091/260430/archives/2007/2007127212710.shtml",
				"06091/260430/archives/2007/2007127212720.shtml",
				"06091/260430/archives/2007/200712721289.shtml",
				"06091/260430/archives/2007/2007127212830.shtml",
				"06091/260430/archives/2007/2007127212839.shtml",
				"06091/260430/archives/2007/200712721299.shtml",
				"06091/260430/archives/2007/2007127212934.shtml",
				"06091/260430/archives/2007/200712721303.shtml",
				"06091/260430/archives/2007/2007127213010.shtml",
				"06091/260430/archives/2007/2007127213028.shtml",
				"06091/260430/archives/2007/2007127213056.shtml",
				"06091/260430/archives/2007/200712721312.shtml",
				"06091/260430/archives/2007/2007127213123.shtml",
				"06091/260430/archives/2007/2007127213137.shtml",
				"06091/260430/archives/2007/2007127213234.shtml",
				"06091/260430/archives/2007/2007127213255.shtml",
				"06091/260430/archives/2007/2007127213320.shtml",
				"06091/260430/archives/2007/2007127211931.shtml" });
	}

	public void testParsePage2() throws IOException, SAXException {
		testParse(
				"user_blogmanage.asp_page_2.html",
				"gb2312",
				2,
				2,
				21,
				new String[] { "939693" },
				new String[] { "06091/260430/archives/2007/2007127201826.shtml" });
	}

	public void test() throws IOException, SAXException {
		Document document = new HtmlFileToDocument().getDocument(this
				.getClass().getResource("user_blogmanage.asp.html"), "gb2312");
		junitx.framework.Assert.assertNull(document.getElementById("showpage")
				.getElementsByTagName("a").item(0));
		junitx.framework.Assert.assertNotNull(document.getElementById(
				"showpage").getElementsByTagName("A").item(0));
	}
}
