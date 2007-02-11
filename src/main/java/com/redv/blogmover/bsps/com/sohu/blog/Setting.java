/**
 *  Created on 2006-7-2 17:28:54
 */
package com.redv.blogmover.bsps.com.sohu.blog;

import java.io.Serializable;

/**
 * Sohu Blog 基本信息。
 * <p>
 * 参见：http://blog.sohu.com/manage/setting.do
 * </p>
 * 
 * @author Joe
 * @version 1.0
 * 
 */
public class Setting implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8202503606405390738L;

	/**
	 * 日志显示模式：摘要。
	 */
	public static final int ENTRY_SHOW_MODE_EXCERPT = 0;

	/**
	 * 日志显示模式：全文。
	 */
	public static final int ENTRY_SHOW_MODE_FULLTEXT = 1;

	/**
	 * 日志显示模式：只显示标题。
	 */
	public static final int ENTRY_SHOW_MODE_TITLE = 2;

	/**
	 * 评论权限：允许所有人。
	 */
	public static final int ALLOW_COMMENT_ALLOW_ALL = 0;

	/**
	 * 评论权限：禁止所有人。
	 */
	public static final int ALLOW_COMMENT_DENY_ALL = 1;

	/**
	 * 评论权限：只有登录用户。
	 */
	public static final int ALLOW_COMMENT_LOGGED_IN_ONLY = 2;

	/**
	 * 博客名称。
	 */
	private String name;

	/**
	 * 博客描述。
	 */
	private String desc;

	/**
	 * 日志显示模式。
	 */
	private int entryShowMode = ENTRY_SHOW_MODE_EXCERPT;

	/**
	 * 每页显示日志篇数。
	 */
	private int entryPerPage = 10;

	/**
	 * 默认评论权限。
	 */
	private int defaultAllowComment = ALLOW_COMMENT_ALLOW_ALL;

	/**
	 * @return Returns the defaultAllowComment.
	 */
	public int getDefaultAllowComment() {
		return defaultAllowComment;
	}

	/**
	 * @param defaultAllowComment
	 *            The defaultAllowComment to set.
	 */
	public void setDefaultAllowComment(int defaultAllowComment) {
		this.defaultAllowComment = defaultAllowComment;
	}

	/**
	 * @return Returns the desc.
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc
	 *            The desc to set.
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return Returns the entryPerPage.
	 */
	public int getEntryPerPage() {
		return entryPerPage;
	}

	/**
	 * @param entryPerPage
	 *            The entryPerPage to set.
	 */
	public void setEntryPerPage(int entryPerPage) {
		this.entryPerPage = entryPerPage;
	}

	/**
	 * @return Returns the entryShowMode.
	 */
	public int getEntryShowMode() {
		return entryShowMode;
	}

	/**
	 * @param entryShowMode
	 *            The entryShowMode to set.
	 */
	public void setEntryShowMode(int entryShowMode) {
		this.entryShowMode = entryShowMode;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

}
