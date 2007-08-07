/**
 * 
 */
package com.redv.blogmover;

/**
 * RemoteUser holds the methods for remoting call such as DWR(Direct Web
 * Remoting).
 * <p>
 * It does not define the scope(singleton or session),but just the methods.
 * </p>
 * 
 * @author sutra
 * 
 */
public interface RemoteUser {
	/**
	 * Set the reader.
	 * 
	 * @param className
	 *            the class name of the reader.
	 */
	void setReader(String className);

	/**
	 * Set the writer.
	 * 
	 * @param className
	 *            the class name of the writer.
	 */
	void setWriter(String className);

	/**
	 * Get the weblog entries that read by reader(or readers).
	 * 
	 * @param firstResult
	 *            the first result index
	 * @param maxResults
	 *            the maximum count of result
	 * @return Object[] = {totalCount, fromIndex, List<WebLog>}
	 */
	Object[] getWebLogs(int firstResult, int maxResults);

	/**
	 * Set the property of the reader.
	 * 
	 * @param property
	 *            property name
	 * @param value
	 *            property value
	 * @throws ReaderNotSettedException
	 *             reader is null
	 */
	void setReaderProperty(String properties, String values)
			throws ReaderNotSettedException;

	/**
	 * Set the properties of the reader.
	 * 
	 * @param properties
	 *            property names
	 * @param values
	 *            property values
	 * @throws ReaderNotSettedException
	 *             reader is null
	 */
	void setReaderProperties(String[] properties, String[] values)
			throws ReaderNotSettedException;

	/**
	 * Set the property of the writer.
	 * 
	 * @param property
	 *            property name
	 * @param values
	 *            property value
	 * @throws WriterNotSettedException
	 *             writer is null
	 */
	void setWriterProperty(String property, String value)
			throws WriterNotSettedException;

	/**
	 * Set the properties of the writer.
	 * 
	 * @param properties
	 *            property names
	 * @param values
	 *            property values
	 * @throws WriterNotSettedException
	 *             writer is null
	 */
	void setWriterProperties(String[] properties, String[] values)
			throws WriterNotSettedException;

	/**
	 * Read weblog entries by reader setted.
	 * <p>
	 * Call the read method of the reader.
	 * </p>
	 * 
	 * @throws BlogMoverException
	 */
	void read() throws BlogMoverException;

	/**
	 * Write weblog entries by writer setted.
	 * <p>
	 * Call the writer method of the writer.
	 * </p>
	 * 
	 * @throws BlogMoverException
	 */
	void write() throws BlogMoverException;

	/**
	 * Get the current status of the reader.
	 * 
	 * @return current status of the reader
	 */
	Status getReaderStatus();

	/**
	 * Get the current status of the writer.
	 * 
	 * @return current status of the writer
	 */
	Status getWriterStatus();

	/**
	 * Delete the weblog entries at the specified indices.
	 * 
	 * @param indices
	 *            the indices of weblog entries that to delete in the session
	 */
	void deleteWebLogs(int[] indices);

	/**
	 * Remove all the weblog entries in the session.
	 * 
	 */
	void clearWebLogs();
}
