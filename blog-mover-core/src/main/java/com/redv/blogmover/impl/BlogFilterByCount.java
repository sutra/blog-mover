package com.redv.blogmover.impl;

import com.redv.blogmover.BlogFilter;
import com.redv.blogmover.ReadFilterResult;
import com.redv.blogmover.WebLog;

public class BlogFilterByCount implements BlogFilter {
  private int m_nCount;

  public BlogFilterByCount(int limit) {
    m_nCount = limit;
  }
  
  public int getLimit() {
    return m_nCount;
  }

  public ReadFilterResult run(WebLog blog) {
    m_nCount--;

    return (m_nCount > 0) ? ReadFilterResult.ACCEPT_AND_READ_MORE
        : ReadFilterResult.ACCEPT_AND_STOP_READ;
  }
}