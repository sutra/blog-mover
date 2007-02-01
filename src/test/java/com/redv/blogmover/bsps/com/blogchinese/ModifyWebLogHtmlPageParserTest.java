/**
 * 
 */
package com.redv.blogmover.bsps.com.blogchinese;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.redv.blogmover.blogengines.cn.oblog.AbstractModifyWebLogHtmlPageParserTest;

/**
 * @author <a href="mailto:zhoushuqun@gmail.com">Sutra</a>
 * 
 */
public class ModifyWebLogHtmlPageParserTest extends
		AbstractModifyWebLogHtmlPageParserTest {

	/**
	 * Test method for
	 * {@link com.redv.blogmover.blogengines.cn.oblog.ModifyWebLogHtmlPageParser#parse(org.w3c.dom.Document)}.
	 * 
	 * @throws SAXException
	 * @throws IOException
	 * 
	 * @throws SAXException
	 * @throws IOException
	 */
	@Test
	public void testParse() throws IOException, SAXException {
		Calendar cal = new GregorianCalendar(2007, 1 - 1, 27, 20, 18, 0);
		String[] tags = new String[] { "测试" };
		testParse("user_post.asp_logid_939693.html", "gb2312", "测试",
				"我测试测试blahblahblah", cal.getTime(), tags);
	}

}
