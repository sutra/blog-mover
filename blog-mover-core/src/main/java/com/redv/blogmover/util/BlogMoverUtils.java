/**
 *  Created on 2006-7-2 17:19:49
 */
package com.redv.blogmover.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
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

	/**
	 * e.g. Parse this "test1, test2,test3" to String array{"test1", "test2",
	 * "test3"}.
	 * 
	 * @param tags
	 * @param splitter
	 * @return
	 */
	public static String[] parseTags(String tags, String splitter) {
		String[] tagArray = StringUtils.split(tags, splitter);
		List<String> ret = new ArrayList<String>(tagArray.length);
		for (int i = 0; i < tagArray.length; i++) {
			if (tagArray[i] != null && tagArray[i].trim().length() > 0) {
				ret.add(tagArray[i].trim());
			}
		}
		String retArray[] = new String[ret.size()];
		ret.toArray(retArray);
		return retArray;
	}
}
