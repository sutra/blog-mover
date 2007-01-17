/**
 * Created on 2007-1-17 下午05:14:50
 */
package com.redv.blogmover.web.tags;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author shutra
 * 
 */
public class BSPNameMessages {
	private static final String BUNDLE_NAME = "com.redv.blogmover.bsps.messages-bsp-names"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private BSPNameMessages() {
	}

	/**
	 * 
	 * @param key
	 * @return if not found, return the key.
	 */
	public static String getString(String bspId) {
		try {
			return RESOURCE_BUNDLE
					.getString("com.redv.blogmover.bsps." + bspId);
		} catch (MissingResourceException e) {
			return bspId;
		}
	}
}
