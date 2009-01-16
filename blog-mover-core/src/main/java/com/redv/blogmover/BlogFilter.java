package com.redv.blogmover;

public interface BlogFilter {
	/**
	 * Execute the filter.
	 * @param blog the blog
	 * @return the filter result
	 */
	ReadFilterResult run(WebLog blog);

	static BlogFilter NONE = new BlogFilter() {
		public ReadFilterResult run(WebLog blog) {
			return ReadFilterResult.ACCEPT_AND_READ_MORE;
		}

	};
}
