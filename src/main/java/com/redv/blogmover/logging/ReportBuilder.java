/**
 * Created on 2007-1-14 上午02:43:59
 */
package com.redv.blogmover.logging;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public Map<BSP, long[]> buildStatistic() {
		List<BSP> bsps = loggingService.getBsps();
		Map<BSP, long[]> statistic = new HashMap<BSP, long[]>(bsps.size());
		for (int i = 0; i < bsps.size(); i++) {
			long[] movedInOut = new long[2];
			BSP bsp = bsps.get(i);
			movedInOut[0] = loggingService.getMovedInCount(bsp.getId());
			movedInOut[1] = loggingService.getMovedOutCount(bsp.getId());
			statistic.put(bsp, movedInOut);
		}
		return statistic;
	}
}
