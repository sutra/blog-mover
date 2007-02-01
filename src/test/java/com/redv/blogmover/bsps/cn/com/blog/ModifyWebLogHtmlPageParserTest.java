/**
 * 
 */
package com.redv.blogmover.bsps.cn.com.blog;

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

	@Test
	public void testParseCnComBlog() throws IOException, SAXException {
		Calendar cal = new GregorianCalendar(2006, 12 - 1, 2, 11, 29, 0);
		String[] tags = new String[] { "First,Blog" };
		testParse("user_post.asp_logid_1918251_t_0.html", "gb2312", "今天心情不错",
				"新Blog开通了。大家都来支持啊", cal.getTime(), tags);

	}

}
