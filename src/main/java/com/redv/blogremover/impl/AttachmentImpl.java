/**
 *  Created on 2006-7-9 16:35:07
 */
package com.redv.blogremover.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.redv.blogremover.Attachment;

/**
 * @author Joe
 * @version 1.0
 * 
 */
public class AttachmentImpl implements Attachment {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4339768603586607598L;

	@SuppressWarnings("unused")
	private static final Log log = LogFactory.getLog(AttachmentImpl.class);

	private static final String PREFIX = "blogremoverattachment";

	private static final String SUFFIX = ".tmp";

	private String contentType;

	private String url;

	private File file;

	private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

	/**
	 * @throws IOException
	 * 
	 */
	public AttachmentImpl() throws IOException {
		super();
		file = File.createTempFile(PREFIX, SUFFIX);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogremover.Attachment#getContentType()
	 */
	public String getContentType() {
		return contentType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogremover.Attachment#setContentType(java.lang.String)
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogremover.Attachment#getUrl()
	 */
	public String getUrl() {
		return url;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogremover.Attachment#setUrl(java.lang.String)
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogremover.Attachment#getStream()
	 */
	public InputStream getStream() throws FileNotFoundException {
		this.readWriteLock.readLock().lock();
		try {
			FileInputStream stream = new FileInputStream(file);
			return stream;
		} finally {
			this.readWriteLock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogremover.Attachment#setStream(java.io.InputStream)
	 */
	public void setStream(InputStream stream) throws IOException {

		byte[] buffer = new byte[1024];
		int len;
		this.readWriteLock.writeLock().lock();
		try {
			FileOutputStream fos = new FileOutputStream(file);
			try {
				while ((len = stream.read(buffer)) != -1) {
					fos.write(buffer, 0, len);
				}
			} finally {
				fos.close();
			}
		} finally {
			this.readWriteLock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AttachmentImpl == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		AttachmentImpl a = (AttachmentImpl) obj;
		return new EqualsBuilder().appendSuper(super.equals(obj)).append(
				this.url, a.url).isEquals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable {
		if (!this.file.delete()) {
			this.file.deleteOnExit();
		}
		super.finalize();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		if (this.url == null) {
			return 0;
		} else {
			return this.url.hashCode();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("url", this.url).toString();
	}

}
