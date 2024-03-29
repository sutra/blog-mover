/**
 * Created on 2006-9-10 上午02:32:57
 */
package com.redv.blogmover.feed;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;

import com.redv.blogmover.Attachment;
import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.Comment;
import com.redv.blogmover.Constants;
import com.redv.blogmover.WebLog;
import com.redv.blogmover.impl.AbstractBlogWriter;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedOutput;

/**
 * 使用 <a href="https://rome.dev.java.net/">Rome</a> 将日志导出成下列格式：RSS 0.90, RSS
 * 0.91 Netscape, RSS 0.91 Userland, RSS 0.92, RSS 0.93, RSS 0.94, RSS 1.0, RSS
 * 2.0, Atom 0.3, and Atom 1.0。
 * <p>
 * <a href="http://wiki.java.net/bin/view/Javawsxml/PoweredByRome">Powered by
 * ROME</a>
 * </p>
 * 
 * @author <a href="mailto:zhoushuqun@gmail.com">Shutra Zhou</a>
 * 
 */
public class RomeWriter extends AbstractBlogWriter {
	private final Pattern pattern = Constants
			.getDownloadFileTempFileNamePattern();

	private final String encoding = "UTF-8";

	private SyndFeed feed;

	private List<SyndEntry> entries;

	private File outputFile;

	private String feedType;

	private String title;

	private String link;

	private String description;

	private String language;

	private String copyright;

	private String author;

	public RomeWriter() {
		super();
	}

	/**
	 * @return the outputFile
	 */
	public File getOutputFile() {
		return outputFile;
	}

	/**
	 * @param outputFile
	 *            the outputFile to set
	 */
	public void setOutputFile(File outputFile) {
		this.outputFile = outputFile;
	}

	/**
	 * 
	 * @param filePath
	 *            the outputFile's name to set
	 */
	public void setFilename(String filename) throws BlogMoverException {
		if (!pattern.matcher(filename).matches()) {
			throw new BlogMoverException("Parameter filename is not matched "
					+ this.pattern.pattern() + ".");
		}

		File tmpdir = new File(SystemUtils.JAVA_IO_TMPDIR);
		this.outputFile = new File(tmpdir, filename);
	}

	/**
	 * @param author
	 *            the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @param copyright
	 *            the copyright to set
	 */
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param feedType
	 *            the feedType to set
	 */
	public void setFeedType(String feedType) {
		this.feedType = feedType;
	}

	/**
	 * @param language
	 *            the language to set
	 */
	public void setLanguage(String language) {
		this.language = StringUtils.replace(language, "_", "-");
	}

	/**
	 * @param link
	 *            the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.impl.AbstractBlogWriter#begin()
	 */
	@Override
	protected void begin() throws BlogMoverException {
		this.feed = new SyndFeedImpl();
		this.feed.setEncoding(this.encoding);
		this.feed.setFeedType(this.feedType);
		this.feed.setTitle(this.title);
		this.feed.setLink(this.link);
		this.feed.setDescription(this.description);
		this.feed.setLanguage(this.language);
		this.feed.setCopyright(this.copyright);
		this.feed.setAuthor(this.author);
		this.entries = new ArrayList<SyndEntry>();
		this.feed.setEntries(this.entries);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.impl.AbstractBlogWriter#end()
	 */
	@Override
	protected void end() throws BlogMoverException {
		SyndFeedOutput output = new SyndFeedOutput();
		OutputStream outputStream = null;
		Writer writer = null;
		try {
			// mkdirs of its parent.
			this.outputFile.getParentFile().mkdirs();

			outputStream = new FileOutputStream(this.outputFile);
			writer = new OutputStreamWriter(outputStream, this.encoding);
			output.output(feed, writer);
		} catch (IOException e) {
			throw new BlogMoverException(e);
		} catch (FeedException e) {
			throw new BlogMoverException(e);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					log.warn(e);
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					log.warn(e);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.impl.AbstractBlogWriter#writeAttachment(com.redv.blogmover.Attachment)
	 */
	@Override
	protected String writeAttachment(Attachment att) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.impl.AbstractBlogWriter#writeBlog(com.redv.blogmover.WebLog,
	 *      java.util.Map)
	 */
	@Override
	protected void writeBlog(WebLog webLog, Map<Attachment, String> attachments)
			throws BlogMoverException {
		// 注意：格式 RSS 0.90, RSS 0.91 Netscape, RSS 0.91 Userland 的日志数量只能在1～15之间。
		if ((this.feedType.equals("rss_0.9")
				|| this.feedType.equals("rss_0.91N") || this.feedType
				.equals("rss_0.91U"))
				&& this.entries.size() >= 15) {
			return;
		}
		SyndEntry entry = new SyndEntryImpl();
		entry.setAuthor(this.author);
		entry.setTitle(webLog.getTitle());
		entry.setLink(webLog.getUrl());
		entry.setPublishedDate(webLog.getPublishedDate());
		entry.setUri(webLog.getUrl());

		if (webLog.getBody() != null) {
			SyndContent description = new SyndContentImpl();
			description.setType("text/html");
			description.setValue(webLog.getBody());
			entry.setDescription(description);
		} else if (this.feedType.equals("rss_2.0")) {
			SyndContent description = new SyndContentImpl();
			description.setType("text/html");
			description.setValue("");
			entry.setDescription(description);
		} else {
			// Do nothing.
		}

		List<SyndContent> contents = new ArrayList<SyndContent>();
		SyndContentImpl sc = new SyndContentImpl();
		sc.setType("text/html");
		sc.setValue(webLog.getBody());
		contents.add(sc);
		entry.setContents(contents);

		this.entries.add(entry);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.impl.AbstractBlogWriter#writeComment(com.redv.blogmover.Comment)
	 */
	@Override
	protected void writeComment(Comment comment) throws BlogMoverException {

	}

	public static void main(String[] args) throws IOException, FeedException {
		SyndFeedImpl feed = new SyndFeedImpl();
		feed.setFeedType("rss_0.90");
		feed.setTitle("site title");
		feed.setLink("http://shutra.xpert.cn");
		feed.setDescription("site description");
		feed.setLanguage("zh-CN");
		feed.setCopyright("copyright");
		feed.setAuthor("author");

		List<SyndEntry> entries = new ArrayList<SyndEntry>();
		feed.setEntries(entries);
		for (int i = 0; i < 2; i++) {
			SyndEntry entry = new SyndEntryImpl();
			entry.setAuthor("author");
			entry.setTitle("title");
			entry.setLink("http://xpert.cn/" + i);

			List<?> contents = new ArrayList<Object>();
			entry.setContents(contents);
			entries.add(entry);
		}

		SyndFeedOutput output = new SyndFeedOutput();
		List<?> l = feed.getSupportedFeedTypes();
		for (Object s : l) {
			feed.setFeedType(s.toString());
			try {
				output.output(feed, new File("C:\\tmp\\t_" + s + ".xml"));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}