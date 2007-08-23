/**
 *  Created on 2006-6-14 21:02:53
 */
package com.redv.blogmover;

import java.io.Serializable;

/**
 * The web log entry's category.
 * 
 * @author <a href="mailto:zhoushuqun@gmail.com">Sutra</a>
 * @version 3.0
 * 
 */
public interface Category extends Serializable, Comparable<Category> {
	String getName();

	void setName(String name);
}
