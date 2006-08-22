/**
 * Created on 2006-6-30 14:29:24
 */
package com.redv.blogmover.bsps.lbs2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;

import com.redv.blogmover.Attachment;
import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.Comment;
import com.redv.blogmover.WebLog;
import com.redv.blogmover.impl.AbstractBlogWriter;

/**
 * 下载 LBS^2 [v2.0.304]：http://www.voidland.com/blog/article.asp?id=52
 * 
 * @author Joe
 * @version 1.0
 */
public class LBS2Writer extends AbstractBlogWriter {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5644936610982137404L;

	private String dbDriver;

	private String dbUrl;

	private String dbUser;

	private String dbPassword;

	private PreparedStatement idPstmt;

	private PreparedStatement blogPstmt;

	private PreparedStatement commentPstmt;

	private String idSql = "select max(log_id) from blog_Article";

	private String blogSql = "insert into blog_Article(log_catID, log_title, log_authorID, log_author, log_editMark, log_trackbackURL, log_content0, log_content1, log_mode, log_locked, log_selected, log_ubbFlags, log_postTime, log_ip, log_commentCount, log_viewCount, log_trackbackCount"
			+ " values(1, ?, 1, 'joe', '', '', ?, 0, false, false, '', ?, ?, 0, 0)";

	private String commentSql = "insert into blog_Comment(log_id, comm_content, comm_authorID, comm_author, comm_editMark, comm_hidden, comm_ubbFlags, comm_postTime, comm_ip)"
			+ " values(?, ?, 0, ?, '', false, '', ?, '')";

	/**
	 * 
	 */
	public LBS2Writer() {
		super();
	}

	/**
	 * @return Returns the dbDriver.
	 */
	public String getDbDriver() {
		return dbDriver;
	}

	/**
	 * @param dbDriver
	 *            The dbDriver to set.
	 */
	public void setDbDriver(String dbDriver) {
		this.dbDriver = dbDriver;
	}

	/**
	 * @return Returns the dbPassword.
	 */
	public String getDbPassword() {
		return dbPassword;
	}

	/**
	 * @param dbPassword
	 *            The dbPassword to set.
	 */
	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	/**
	 * @return Returns the dbUrl.
	 */
	public String getDbUrl() {
		return dbUrl;
	}

	/**
	 * @param dbUrl
	 *            The dbUrl to set.
	 */
	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	/**
	 * @return Returns the dbUser.
	 */
	public String getDbUser() {
		return dbUser;
	}

	/**
	 * @param dbUser
	 *            The dbUser to set.
	 */
	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogremover.impl.AbstractBlogWriter#start()
	 */
	@Override
	protected void begin() throws BlogMoverException {
		try {
			this.prepareStatement();
		} catch (SQLException e) {
			throw new BlogMoverException(e);
		} catch (ClassNotFoundException e) {
			throw new BlogMoverException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogremover.impl.AbstractBlogWriter#end()
	 */
	@Override
	protected void end() throws BlogMoverException {
		try {
			this.closeStatement();
		} catch (SQLException e) {
			throw new BlogMoverException(e);
		}
	}

	@Override
	protected void writeBlog(WebLog webLog, Map<Attachment, String> attachments)
			throws BlogMoverException {
		try {
			this.blogPstmt.setString(1, webLog.getTitle());
			this.blogPstmt.setString(2, webLog.getBody());
			this.blogPstmt.setTimestamp(3, new Timestamp(webLog
					.getPublishedDate().getTime()));
			this.blogPstmt.setInt(4, webLog.getComments().size());
			this.blogPstmt.executeUpdate();
		} catch (SQLException e) {
			throw new BlogMoverException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogremover.impl.AbstractBlogWriter#writeComment(com.redv.blogremover.Comment)
	 */
	@Override
	protected void writeComment(Comment comment) throws BlogMoverException {
		try {
			int id = 0;
			ResultSet rs = this.idPstmt.executeQuery();
			while (rs.next()) {
				id = rs.getInt(1);
			}
			rs.close();
			this.commentPstmt.setInt(1, id);
			this.commentPstmt.setString(2, comment.getComment());
			this.commentPstmt.setString(3, comment.getName());
			this.commentPstmt.setTimestamp(4, new java.sql.Timestamp(comment
					.getPublishedDate().getTime()));
			this.commentPstmt.executeUpdate();
		} catch (SQLException e) {
			throw new BlogMoverException(e);
		}
	}

	@Override
	protected String writeAttachment(Attachment att) {
		return null;
	}

	private void prepareStatement() throws SQLException, ClassNotFoundException {
		Class.forName(this.dbDriver);
		Connection conn = DriverManager.getConnection(this.dbUrl, this.dbUser,
				this.dbPassword);
		this.idPstmt = conn.prepareStatement(this.idSql);
		this.blogPstmt = conn.prepareStatement(this.blogSql);
		this.commentPstmt = conn.prepareStatement(this.commentSql);
	}

	private void closeStatement() throws SQLException {
		try {
			if (this.idPstmt != null) {
				this.idPstmt.close();
			}
			if (this.blogPstmt != null) {
				this.blogPstmt.close();
			}
			if (this.commentPstmt != null) {
				this.commentPstmt.close();
			}
		} finally {
			if (this.blogPstmt != null
					&& this.blogPstmt.getConnection() != null) {
				this.blogPstmt.getConnection().close();
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
