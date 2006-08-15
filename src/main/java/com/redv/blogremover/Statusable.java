/**
 * Created on 2006-6-30 15:05:50
 */
package com.redv.blogremover;

/**
 * 有状态的。
 * 
 * @author Joe
 * @version 1.0
 */
public interface Statusable {

	/**
	 * 获取当前状态（读取/写入）。
	 * 
	 * @return
	 */
	Status getStatus();
}
