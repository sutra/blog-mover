/**
 *  Created on 2006-6-26 1:38:22
 */
package com.redv.blogmover;

import java.io.Serializable;

/**
 * 状态，用来表示 Reader/Writer 的处理状态。
 * 
 * @author Joe
 * @version 1.0
 * 
 */
public interface Status extends Serializable {
	/**
	 * 获取当前正在处理（读取或写入）的日志。
	 * 
	 * @return
	 */
	WebLog getCurrentWebLog();

	/**
	 * 设置当前正在处理（读取或写入）的日志。
	 * 
	 * @param webLog
	 */
	void setCurrentWebLog(WebLog webLog);

	/**
	 * 获取当前已处理（读取或写入）的日志数目。
	 * 
	 * @return
	 */
	int getCurrentCount();

	/**
	 * 设置当前已处理（读取或写入）的日志数目。
	 * 
	 * @param currentCount
	 */
	void setCurrentCount(int currentCount);

	/**
	 * 获取需要处理（读取或写入）的日志总数。
	 * 
	 * @return
	 */
	int getTotalCount();

	/**
	 * 设置需要处理（读取或写入）的日志总数。
	 * 
	 * @param totalCount
	 */
	void setTotalCount(int totalCount);

	/**
	 * 获取日志总数是否是近似数。
	 * 
	 * @return
	 */
	boolean isApproximative();

	/**
	 * 设置日志总数是否为近似。
	 * 
	 * @param approximative
	 */
	void setApproximative(boolean approximative);
}
