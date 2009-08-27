/**
 * Created on 2007-09-23 00:26
 */
package com.redv.blogmover.bsps.sina;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.redv.blogmover.Comment;
import com.redv.blogmover.impl.CommentImpl;
import com.redv.blogmover.util.StringUtil;

/**
 * The comment page parser for sina.
 * 
 * @author sutra
 */
public class CommentPageParser {
	private static final Log log = LogFactory.getLog(CommentPageParser.class);

	private String document;

	private List<Comment> comments;

	public CommentPageParser(String document) {
		this.document = document;
		this.comments = new ArrayList<Comment>();

		parse();
	}

	/**
	 * @return comments
	 */
	public List<Comment> getComments() {
		return comments;
	}

	/**
	 * 解析一个评论页面
	 */
	private void parse() {
		String doc = this.document;

		doc = doc.replaceAll("\\\n", "")
		.replaceAll("\\n", "")
		.replaceAll("\r", "")
		.replaceAll("\r\n", "")
		.replaceAll("&quot;", "\"")
		.replaceAll("\\&quot;", "\"");
		
		doc = doc.replace("\n", "")
		.replace("\\\"", "\"")
		.replace("\"\"", "\"")
		.replace("&lt;", "<")
		.replace("&gt;", ">")
		.replace("\\/", "/")
		.replace("\\n", "");

		String p = "";
		Pattern pattern = null;
		Matcher matcher = null;
		boolean rs = false;
		
		p = "\\{\"code\":\"A00006\",\"data\":\\{\"result\":\"(.*)\"commenter\"";
		pattern = Pattern.compile(p);
		matcher = pattern.matcher(doc);
		rs = matcher.find();
		if(!rs) {;
			return;
		}
		
		doc = matcher.group(1);
		
		p = "<li\\s+class=\"CP_litem\\s+CP_j_linedot1\"\\s+id=\"cmt_(\\d+)\">(.*?)</li>";
		pattern = Pattern.compile(p);
		matcher = pattern.matcher(doc);
		Comment comment = new CommentImpl();
		String li = "";
		while(matcher.find()) {
			li = matcher.group(2);
			
			// 评论者名称
			Pattern p2 = Pattern.compile("<p\\s+class=\\\"CP_pf_nm\\\"><a(.*?)><strong>(.*?)</strong>"); // 新浪注册用户
			Matcher m = p2.matcher(li);
			if(m.find()) {
				comment.setName(StringUtil.unicodeToString(m.group(2)));
				log.debug("已注册：" + comment.getName());
			} else {
				p2 = Pattern.compile("<p\\s+class=\"CP_pf_nm\"><strong>(.*)</strong>：</p>"); // 未登录用户
				m = p2.matcher(li);
				if(m.find()) {
					comment.setName(StringUtil.unicodeToString(m.group(1)));
					log.debug("未注册：" + comment.getName());
				} else {
					log.debug("未找到评论者名称:" + li);
				}
			}
			//comment.setName(StringUtil.unicodeToString(StringUtil.getContent("<p class=\"CP_pf_nm\"><strong>", "</strong", li)));
			//log.debug(comment.getName());
			
			// 评论时间
			String strDate = StringUtil.getContent("<em class=\"CP_txtc\">", "</em>", li);
			if(!strDate.isEmpty()) {
				comment.setPublishedDate(StringUtil.strToDate(strDate));
				log.debug("评论日期：" + comment.getPublishedDate().toString());
			}
			
			// 评论内容
			comment.setContent(StringUtil.unicodeToString(StringUtil.getContent("<span href=\"#\">", "</span>", li)));
			log.debug("评论内容：" + comment.getContent());
			
			this.comments.add(comment);
		}
	}
}
