package com.redv.blogmover.impl;

import com.redv.blogmover.WebLog;

public class SimpleWebLogImpl extends WebLogImpl {

  /**
   * 
   */
  private static final long serialVersionUID = -8888642716297844445L;

  @Override
  public boolean equals(Object o) {
    if (o instanceof WebLog) {
      WebLog obj = (WebLog) o;
      if (this.getTitle() == null && obj.getTitle() == null) {
        return true;
      }
      return this.getTitle().equals(obj.getTitle());
    }
    return super.equals(o);
  }
}