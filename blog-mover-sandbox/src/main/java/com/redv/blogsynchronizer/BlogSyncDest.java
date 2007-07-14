package com.redv.blogsynchronizer;

import com.redv.blogmover.BlogReader;
import com.redv.blogmover.BlogWriter;

public class BlogSyncDest {
  private BlogReader m_reader;
  private BlogWriter m_writer;
  
  public BlogSyncDest(BlogReader reader, BlogWriter writer) {
    m_reader = reader;
    m_writer = writer;    
  }
  
  public BlogReader getReader() {
    return m_reader;
  }
  
  public BlogWriter getWriter() {
    return m_writer;
  }
}