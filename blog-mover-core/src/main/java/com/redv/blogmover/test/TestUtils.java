/**
 * Created on 2006-12-29 下午10:56:40
 */
package com.redv.blogmover.test;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

/**
 * @author shutra
 * 
 */
public class TestUtils {
	public static String[] getConfigLocations() {
		String[] paths;
		try {
			paths = new String[] {
					TestUtils.class.getResource("/applicationContext.xml")
							.toURI().toURL().toExternalForm(),
					TestUtils.class.getResource(
							"/dataAccessContext-hibernate.xml").toURI().toURL()
							.toExternalForm() };
			return paths;
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

}
