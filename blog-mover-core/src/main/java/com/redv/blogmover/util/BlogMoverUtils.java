/**
 *  Created on 2006-7-2 17:19:49
 */
package com.redv.blogmover.util;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Joe
 * @version 1.0
 * 
 */
public class BlogMoverUtils {
	@SuppressWarnings("unused")
	private static final Log log = LogFactory.getLog(BlogMoverUtils.class);

	// Deletes all files and subdirectories under dir.
	// Returns true if all deletions were successful.
	// If a deletion fails, the method stops attempting to delete and returns
	// false.
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}

		// The directory is now empty so delete it
		return dir.delete();
	}
}
