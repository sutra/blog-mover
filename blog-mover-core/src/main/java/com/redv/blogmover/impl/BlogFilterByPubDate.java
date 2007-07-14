package com.redv.blogmover.impl;

import java.util.Date;

import com.redv.blogmover.BlogFilter;
import com.redv.blogmover.ReadFilterResult;
import com.redv.blogmover.WebLog;

public class BlogFilterByPubDate implements BlogFilter {
  private Date m_sinceDate;

  public BlogFilterByPubDate(Date sinceDate) {
    m_sinceDate = sinceDate;
  }

  public ReadFilterResult run(WebLog blog) {
    return blog.getPublishedDate().before(m_sinceDate) ? ReadFilterResult.REJECT_AND_STOP_READ
        : ReadFilterResult.ACCEPT_AND_READ_MORE;
  }
}