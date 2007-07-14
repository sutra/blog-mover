package com.redv.blogmover;

public enum ReadFilterResult {
  ACCEPT_AND_READ_MORE, REJECT_AND_READ_MORE, ACCEPT_AND_STOP_READ, REJECT_AND_STOP_READ;

  public boolean readMore() {
    return (this == ACCEPT_AND_READ_MORE) || (this == REJECT_AND_READ_MORE);
  }

  public boolean accepted() {
    return (this == ACCEPT_AND_READ_MORE) || (this == ACCEPT_AND_STOP_READ);
  }
}