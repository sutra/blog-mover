/**
 * <pre>
 * Copyright:		Copyright(C) 2005-2006, feloo.org
 * Filename:		BlogcnWriterTest.java
 * Module:			blog-mover
 * Class:			BlogcnWriterTest
 * Date:			2006-9-27 上午11:06:22
 * Author:			<a href="mailto:rory.cn@gmail.com">somebody</a>
 * Description:		
 *
 *
 * ======================================================================
 * Change History Log
 * ----------------------------------------------------------------------
 * Mod. No.	| Date		| Name			| Reason			| Change Req.
 * ----------------------------------------------------------------------
 * 			| 2006-9-27   | Rory Ye	    | Created			|
 *
 * </pre>
 **/
package com.redv.blogmover.bsps.blogcn;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.WebLog;
import com.redv.blogmover.impl.WebLogImpl;

/**
 * @author <a href="mailto:rory.cn@gmail.com">somebody</a>
 * @since 2006-9-27 上午11:06:22
 * @version $Id BlogcnWriterTest.java$
 */
public class BlogcnWriterTest {
	private BlogcnWriter blogcnWriter;
	
	/**
	 * 
	 */
	public BlogcnWriterTest() {
		blogcnWriter = new BlogcnWriter();
	}
	
	@Test
	public void testWrite() throws BlogMoverException {
		List<WebLog> webLogs = new ArrayList<WebLog>();
		WebLog webLog = new WebLogImpl();
		webLog.setTitle("今天心情不错");
		webLog.setBody("新Blog开通了。大家都来支持啊");
		webLog.setPublishedDate(new Date());
		webLog.setTags(new String[]{"First","Blog"});
		webLogs.add(webLog);
		blogcnWriter.setUsername("blog-mover");
		blogcnWriter.setPassword("jdkcn.com");
		blogcnWriter.write(webLogs);
	}
}
