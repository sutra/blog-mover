/**
 * 
 */
package com.redv.blogmover;

import java.util.List;

/**
 * @author sutra
 *
 */
public interface UserFacade {

	public abstract void setToken(String token);

	/**
	 * @return Returns the reader.
	 */
	public abstract BlogReader getReader();

	/**
	 * @return Returns the writer.
	 */
	public abstract BlogWriter getWriter();

	/**
	 * @param reader
	 *            The reader to set.
	 */
	public abstract void setReader(BlogReader reader);

	/**
	 * @param writer
	 *            The writer to set.
	 */
	public abstract void setWriter(BlogWriter writer);

	/**
	 * @return Returns the webLogs.
	 */
	public abstract List<WebLog> getWebLogs();

	/**
	 * 
	 * @param property
	 *            The property to set.
	 * @param value
	 *            The value to set.
	 * @throws ReaderNotSettedException
	 */
	public abstract void setReaderProperty(String property, String value)
			throws ReaderNotSettedException;

	/**
	 * 
	 * @param property
	 *            The property to set.
	 * @param value
	 *            The value to set.
	 * @throws BlogMoverException
	 */
	public abstract void setWriterProperty(String property, String value)
			throws WriterNotSettedException;

	public abstract void read() throws BlogMoverException;

	public abstract void write() throws BlogMoverException;

}