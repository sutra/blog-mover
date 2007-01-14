/**
 * Created on 2007-1-14 上午02:43:59
 */
package com.redv.blogmover.logging;

import java.util.List;

/**
 * @author shutra
 * 
 */
public class ReportBuilder {
	private LoggingService loggingService;

	/**
	 * @param loggingService
	 *            the loggingService to set
	 */
	public void setLoggingService(LoggingService loggingService) {
		this.loggingService = loggingService;
	}

	public void build() {
		long[][] data = new long[2][];
		List<BSP> bsps = loggingService.getBsps();
		for (int i = 0; i < bsps.size(); i++) {
			data[i] = new long[2];
			BSP bsp = bsps.get(i);
			long from = loggingService.getFromCount(bsp.getId());
			long to = loggingService.getToCount(bsp.getId());
			data[i][0] = from;
			data[i][1] = to;
		}
		
	}
}
