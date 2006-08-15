package com.redv.blogremover.bsps.sina;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import junit.framework.TestCase;

public class SinaReaderTest extends TestCase {
	private Log log = LogFactory.getLog(SinaReaderTest.class);

	private SinaReader sinaReader;

	protected void setUp() throws Exception {
		super.setUp();
		sinaReader = new SinaReader();
	}

	/*
	 * Test method for
	 * 'com.redv.blogremover.bsps.sina.SinaReader.parse(java.lang.String)'
	 */
	public void testParse() throws IOException {
		InputStream is = this.getClass().getResourceAsStream("t.txt");
		InputStreamReader reader = new InputStreamReader(is, "UTF-8");
		char[] c = new char[1024];
		int len;
		StringBuffer sb = new StringBuffer();
		while ((len = reader.read(c)) != -1) {
			sb.append(new String(c, 0, len));
		}
		String str = sb.toString();
		log.debug(str);
		sinaReader.parse(str);
	}

}
