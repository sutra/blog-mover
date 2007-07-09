package com.redv.blogmover;

public interface BlogFilter {
  ReadFilterResult run(WebLog blog);

  public static BlogFilter NONE = new BlogFilter() {
    public ReadFilterResult run(WebLog blog) {
      return ReadFilterResult.ACCEPT_AND_READ_MORE;
    }

  };
}
