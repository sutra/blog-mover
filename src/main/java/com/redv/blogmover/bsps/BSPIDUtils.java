/**
 * 
 */
package com.redv.blogmover.bsps;

import java.io.IOException;
import java.util.Properties;

/**
 * @author shutrazh
 * 
 */
public class BSPIDUtils {
	private static final Properties p = new Properties();
	static {
		try {
			p.load(BSPIDUtils.class.getResourceAsStream("bsp-ids.properties"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static String getId(Class clazz) {
		return p.getProperty(clazz.getName());
	}
}
