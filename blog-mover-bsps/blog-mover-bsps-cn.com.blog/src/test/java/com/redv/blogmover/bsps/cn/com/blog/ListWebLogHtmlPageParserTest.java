/**
 * Created on 2007-1-28 上午12:01:13
 */
package com.redv.blogmover.bsps.cn.com.blog;

import java.io.IOException;

import org.xml.sax.SAXException;

import com.redv.blogmover.blogengines.cn.oblog.test.AbstractListWebLogHtmlPageParserTest;

/**
 * @author <a href="mailto:zhoushuqun@gmail.com">Sutra</a>
 * 
 */
public class ListWebLogHtmlPageParserTest extends
		AbstractListWebLogHtmlPageParserTest {

	public void testOblog() throws IOException, SAXException {
		testParse(
				"user_blogmanage.asp.html",
				"gb2312",
				1,
				10,
				192,
				new String[] { "2065864", "2020479", "2019280", "2000688",
						"1937355", "1932606", "1919374", "1919354", "1919318",
						"1919281", "1919270", "1919262", "1919217", "1918473",
						"1918343", "1918342", "1918276", "1918251", "1918207",
						"1918131" },
				new String[] {
						"http://blog-mover.blog.com.cn/archives/2007/2065864.shtml",
						"http://blog-mover.blog.com.cn/archives/2007/2020479.shtml",
						"http://blog-mover.blog.com.cn/archives/2007/2019280.shtml",
						"http://blog-mover.blog.com.cn/archives/2006/2000688.shtml",
						"http://blog-mover.blog.com.cn/archives/2006/1937355.shtml",
						"http://blog-mover.blog.com.cn/archives/2006/1932606.shtml",
						"http://blog-mover.blog.com.cn/archives/2006/1919374.shtml",
						"http://blog-mover.blog.com.cn/archives/2006/1919354.shtml",
						"http://blog-mover.blog.com.cn/archives/2006/1919318.shtml",
						"http://blog-mover.blog.com.cn/archives/2006/1919281.shtml",
						"http://blog-mover.blog.com.cn/archives/2006/1919270.shtml",
						"http://blog-mover.blog.com.cn/archives/2006/1919262.shtml",
						"http://blog-mover.blog.com.cn/archives/2006/1919217.shtml",
						"http://blog-mover.blog.com.cn/archives/2006/1918473.shtml",
						"http://blog-mover.blog.com.cn/archives/2006/1918343.shtml",
						"http://blog-mover.blog.com.cn/archives/2006/1918342.shtml",
						"http://blog-mover.blog.com.cn/archives/2006/1918276.shtml",
						"http://blog-mover.blog.com.cn/archives/2006/1918251.shtml",
						"http://blog-mover.blog.com.cn/archives/2006/1918207.shtml",
						"http://blog-mover.blog.com.cn/archives/2006/1918131.shtml" });
	}

}
