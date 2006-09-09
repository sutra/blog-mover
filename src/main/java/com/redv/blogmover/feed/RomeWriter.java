/**
 * Created on 2006-9-10 上午02:32:57
 */
package com.redv.blogmover.feed;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.redv.blogmover.Attachment;
import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.Comment;
import com.redv.blogmover.WebLog;
import com.redv.blogmover.impl.AbstractBlogWriter;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedOutput;

/**
 * @author Shutra
 * 
 */
public class RomeWriter extends AbstractBlogWriter {
	private SyndFeed feed;

	private List<SyndEntry> entries;

	private String feedType;

	private String title;

	private String link;

	private String description;

	private String language;

	private String copyright;

	private String author;

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
		this.language = language;
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
		SyndFeedImpl feed = new SyndFeedImpl();
		feed.setFeedType(this.feedType);
		feed.setTitle(this.title);
		feed.setLink(this.link);
		feed.setDescription(this.description);
		feed.setLanguage(this.language);
		feed.setCopyright(this.copyright);
		feed.setAuthor(this.author);
		entries = new ArrayList<SyndEntry>();
		feed.setEntries(entries);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.impl.AbstractBlogWriter#end()
	 */
	@Override
	protected void end() throws BlogMoverException {
		SyndFeedOutput output = new SyndFeedOutput();
		try {
			log.debug("............" + feed.getEntries().size());
			output.output(feed, new File("C:\\tmp\\test.xml"));
		} catch (IOException e) {
			throw new BlogMoverException(e);
		} catch (FeedException e) {
			throw new BlogMoverException(e);
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
		SyndEntry entry = new SyndEntryImpl();
		entry.setAuthor(this.author);
		entry.setTitle(webLog.getTitle());
		entry.setLink(webLog.getUrl());
		entry.setPublishedDate(webLog.getPublishedDate());
		entry.setUri(webLog.getUrl());
		List<String> contents = new ArrayList<String>();
		contents.add(webLog.getBody());
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

			List contents = new ArrayList();
			entry.setContents(contents);
			entries.add(entry);
		}

		SyndFeedOutput output = new SyndFeedOutput();
		List l = feed.getSupportedFeedTypes();
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