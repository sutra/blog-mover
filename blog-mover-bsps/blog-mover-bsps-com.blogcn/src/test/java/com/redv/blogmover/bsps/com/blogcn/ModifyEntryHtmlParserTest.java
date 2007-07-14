/**
 * 
 */
package com.redv.blogmover.bsps.com.blogcn;

import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;

import junit.framework.TestCase;
import junitx.framework.ArrayAssert;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.redv.blogmover.WebLog;
import com.redv.blogmover.util.HtmlFileToDocument;

/**
 * @author shutrazh
 * 
 */
public class ModifyEntryHtmlParserTest extends TestCase {
	private ModifyEntryHtmlParser parser;

	/**
	 * @throws java.lang.Exception
	 */
	public void setUp() throws Exception {
		parser = new ModifyEntryHtmlParser();
	}

	/**
	 * @throws java.lang.Exception
	 */
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link com.redv.blogmover.bsps.com.blogcn.ModifyEntryHtmlParser#parse()}.
	 * 
	 * @throws SAXException
	 * @throws IOException
	 * 
	 * @throws SAXException
	 * @throws IOException
	 */
	public void testParse() throws IOException, SAXException {
		Date date = new GregorianCalendar(2007, 2 - 1, 1, 10, 3, 31).getTime();
		String[] tags = new String[] { "测试tag", "第二个tag", "第三个tag" };
		testParse("blue_log_ct.asp_action_edit_id_52108763.html", "UTF-8",
				"测试", "测试日志内容。", date, tags);
	}

	public void testParse2() throws IOException, SAXException {
		Date date = new GregorianCalendar(2007, 7 - 1, 4, 0, 0, 0).getTime();
		String[] tags = new String[] {};
		testParse(
				"blue_log_ct_html.asp.title_id_is_title_not_mytitle.html",
				"UTF-8",
				"旅游归来",
				"\n\t\t\t  <P>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 6月30号到7月4号，去了江西庐山和南昌旅游，经历了人生的许多第一次。第一次出远门旅行；第一次乘火车；第一次在火车上过夜；第一次住宾馆；第一次登名山；第一次看到真正的瀑布……<BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 参观的这些景点在同去的父母和其他老师们看来与其他旅游胜地大同小异，似乎已经提不起他们的兴趣了。但对于我这样孤陋寡闻，长期窝在家中的人来说，就颇有新鲜感了。<BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;首先是乘火车，买的是硬卧车票，狭小的车厢挤进了几十个人，不免有些闷热。好在都是一个单位熟识的人，大家说说闹闹，吃吃喝喝也很快打发了一个黄昏的时间。我睡的是中铺，虽然这种铺位又短又窄，不高的车厢居然排了三层，非常压抑，但兴奋的情绪让这一切不舒适都变得无所谓了，在火车有规律的颠簸中断断续续地睡了好几觉，所以第二天依旧很有精神。<BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 上庐山的路上，汽车曲曲弯弯地前行，从车窗外望去，山下的一切都变得越来越小，我总是担心车会一不小心翻下山去，怀着这样恐惧的心理安全抵达了宾馆，稍作休息后开始了第一天的行程。参观了白居易草堂，走了很长一段山路，我不太记忆景观的名称，只记得我们边走边看山边山下的风景，我不敢太靠外道走，总是担心会坠崖，但从山上放眼望去，这景的确壮美，而庐山的气候又十分宜人，难怪这么多名人，领袖喜欢到庐山避暑休闲。这其中导游的一句不知是玩笑还是真实的话让我印象深刻，她说毛泽东曾经在庐山某一个场景中说过：“同志们，我一手遮天啦！”我喜欢这份气势，这份豪情。在宾馆的第一个晚上，做了一个很奇怪的梦，似曾相识，关于L的，很有意思，很合我口味。<BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <BR></P>\n\t\t\t  ",
				date, tags);
	}

	private void testParse(String htmlFile, String encoding,
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
		ArrayAssert.assertEquals(expectedTags, webLog.getTags());
	}

}
