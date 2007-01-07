/**
 * <pre>
 * Copyright:		Copyright(C) 2005-2006, feloo.org
 * Filename:		SimpleTest.java
 * Module:			blog-mover
 * Class:			SimpleTest
 * Date:			19/10/2006 10:53:12 AM
 * Author:			<a href="mailto:rory.cn@gmail.com">somebody</a>
 * Description:		
 *
 *
 * ======================================================================
 * Change History Log
 * ----------------------------------------------------------------------
 * Mod. No.	| Date		| Name			| Reason			| Change Req.
 * ----------------------------------------------------------------------
 * 			| 19/10/2006   | Rory Ye	    | Created			|
 *
 * </pre>
 **/
package com.redv.blogmover.simple;

import junit.framework.TestCase;

/**
 * @author <a href="rory.cn@gmail.com">somebody</a>
 * @since 19/10/2006 10:53:12 AM
 * @version $Id: SimpleTest.java, 95 19/10/06 10:57 Rory.CN $
 */
public class SimpleTest extends TestCase {
	public void testGetClassName() {
		String className = com.redv.blogmover.bsps.baidu.BaiduWriter.class
				.getName();
		assertEquals("com.redv.blogmover.bsps.baidu.BaiduWriter", className);
	}
}
