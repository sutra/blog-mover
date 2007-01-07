/**
 * Created on 2006-12-29 上午12:09:09
 */
package com.redv.blogmover.logging;

import java.io.Serializable;

/**
 * @author shutra
 * 
 */
public class MovingLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1585957002093003393L;

	private String id;

	private MovingEntry from;

	private BSP toBsp;

	private Moving moving;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the from
	 */
	public MovingEntry getFrom() {
		return from;
	}

	/**
	 * @param from
	 *            the from to set
	 */
	public void setFrom(MovingEntry from) {
		this.from = from;
	}

	/**
	 * @return the toBsp
	 */
	public BSP getToBsp() {
		return toBsp;
	}

	/**
	 * @param toBsp
	 *            the toBsp to set
	 */
	public void setToBsp(BSP toBsp) {
		this.toBsp = toBsp;
	}

	/**
	 * @return the moving
	 */
	public Moving getMoving() {
		return moving;
	}

	/**
	 * @param moving
	 *            the moving to set
	 */
	public void setMoving(Moving moving) {
		this.moving = moving;
	}

}
