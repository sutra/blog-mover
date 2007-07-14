/**
 * Created on 2006-9-22 22:55:32
 */
package com.redv.blogmover;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.opensymphony.oscache.base.Cache;
import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

/**
 * @author Shutra
 * 
 */
public class RecentWebLogsCache {
	private static GeneralCacheAdministrator gca = new GeneralCacheAdministrator();

	private static Cache cache;

	private static Queue<String> tokens = new LinkedList<String>();

	static {
		cache = gca.getCache();
	}

	public void put(String key, List<WebLog> webLogs) {
		cache.putInCache(key, webLogs);
		if (tokens.size() >= 100) {
			tokens.remove();
		}
		tokens.offer(key);
	}

	@SuppressWarnings("unchecked")
	public List<WebLog> getAll() {
		List<WebLog> webLogs = new ArrayList<WebLog>();
		String[] strs = new String[tokens.size()];
		tokens.toArray(strs);
		for (int i = strs.length - 1; i >= 0; i--) {
			String token = strs[i];
			try {
				webLogs.addAll((List<WebLog>) cache.getFromCache(token));
			} catch (NeedsRefreshException e) {
			}
		}
		return webLogs;
	}
}
