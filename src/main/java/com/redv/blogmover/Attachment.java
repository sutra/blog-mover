/**
 *  Created on 2006-7-9 16:03:04
 */
package com.redv.blogmover;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

/**
 * @author Joe
 * @version 1.0
 * 
 */
public interface Attachment extends Serializable {
	String getContentType();

	void setContentType(String contentType);

	String getUrl();

	void setUrl(String url);

	InputStream getStream() throws IOException;

	void setStream(InputStream stream) throws IOException;
}
