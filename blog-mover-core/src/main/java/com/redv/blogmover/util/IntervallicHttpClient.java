/**
 * Created on 2008-9-29 下午10:46:00
 */
package com.redv.blogmover.util;

import java.io.IOException;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This HttpClient try to simulate the human's actions.
 * <p>
 * It add a random interval between HTTP request.
 * </p>
 * 
 * @author Sutra Zhou
 * 
 */
public class IntervallicHttpClient extends HttpClient {
	private static final Log log = LogFactory
			.getLog(IntervallicHttpClient.class);

	public static final String MINIMUM_INTERVAL = IntervallicHttpClient.class
			.getName()
			+ ".MINIMUM_INTERVAL";
	public static final String MAXIMUM_INTERVAL = IntervallicHttpClient.class
			.getName()
			+ ".MAXIMUM_INTERVAL";

	private long lastExecutionTime = 0L;

	/**
	 * 
	 */
	public IntervallicHttpClient() {
		super();
	}

	/**
	 * @param params
	 * @param httpConnectionManager
	 */
	public IntervallicHttpClient(HttpClientParams params,
			HttpConnectionManager httpConnectionManager) {
		super(params, httpConnectionManager);
	}

	/**
	 * @param params
	 */
	public IntervallicHttpClient(HttpClientParams params) {
		super(params);
	}

	/**
	 * @param httpConnectionManager
	 */
	public IntervallicHttpClient(HttpConnectionManager httpConnectionManager) {
		super(httpConnectionManager);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.commons.httpclient.HttpClient#executeMethod(org.apache.commons
	 * .httpclient.HostConfiguration, org.apache.commons.httpclient.HttpMethod,
	 * org.apache.commons.httpclient.HttpState)
	 */
	@Override
	public int executeMethod(HostConfiguration hostconfig, HttpMethod method,
			HttpState state) throws IOException, HttpException {
		long interval = calcInterval();
		if (interval >= 0L) {
			log.debug(String.format("Sleep %1$s milliseconds.", interval));
			try {
				Thread.sleep(interval);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		try {
			return super.executeMethod(hostconfig, method, state);
		} finally {
			lastExecutionTime = System.currentTimeMillis();
		}
	}

	private long calcInterval() {
		long randomInterval = randomInterval();
		long currentTimeMillis = System.currentTimeMillis();
		long interval = lastExecutionTime + randomInterval - currentTimeMillis;
		if (log.isDebugEnabled()) {
			String format = "calc interval: lastExecutionTime + randomInterval - currentTimeMillis = interval: %1$s + %2$s - %3$s = %4$s.";
			String s = String.format(format, lastExecutionTime, randomInterval,
					currentTimeMillis, interval);
			log.debug(s);
		}
		return interval;
	}

	private long randomInterval() {
		long minimumInterval = this.getParams().getLongParameter(
				MINIMUM_INTERVAL, 0L);
		long maximumInterval = this.getParams().getLongParameter(
				MAXIMUM_INTERVAL, 0L);
		double d = (double) minimumInterval + RandomUtils.nextDouble()
				* (maximumInterval - minimumInterval);
		return (long) d;
	}
}
