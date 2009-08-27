package com.redv.blogmover.util;

public class IntUtil {
	/**
	 * 根据文章数获取分页总数
	 * @param totalArticleCount
	 * @return
	 */
	public static int GetPageCount(int totalArticleCount) {
		int pages = 0;
		if ((totalArticleCount / 50.0) > (totalArticleCount / 50)) {
			pages = (totalArticleCount / 50) + 1;
		} else {
			pages = (totalArticleCount / 50);
		}
		
		return pages;
	}
}
