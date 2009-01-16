/**
 *  Created on 2006-7-9 16:03:04
 */
package com.redv.blogmover;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

/**
 * The attachment of a web log entry.
 * 
 * @author Joe
 * @version 1.0
 * 
 */
public interface Attachment extends Serializable {
	/**
	 * Get the content type of the attachment file.
	 * @return the content type
	 */
	String getContentType();

	/**
	 * Set the content type of the attachment file.
	 * @param contentType the content type
	 */
	void setContentType(String contentType);

	/**
	 * Get the url of the attachment.
	 * @return the url
	 */
	String getUrl();

	/**
	 * Set the url of the attachment.
	 * @param url the url to set
	 */
	void setUrl(String url);

	/**
	 * Get the stream of the attachment file.
	 * @return the input stream
	 * @throws IOException if an I/O error occurs
	 */
	InputStream getStream() throws IOException;

	/**
	 * Set the stream of the attachment file.
	 * @param stream the input stream to set
	 * @throws IOException if an I/O error occurs
	 */
	void setStream(InputStream stream) throws IOException;
}
