/**
 *  Created on 2006-6-24 13:19:06
 */
package com.redv.blogremover.bsps.space;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Joe
 * @version 1.0
 * 
 */
class DateTimeParser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(DateTimeParser.class);

	private static final List<SimpleDateFormat> sdfs = new ArrayList<SimpleDateFormat>();
	static {
		sdfs.add(new SimpleDateFormat("M/dd/yyyy h:mm:ss a", Locale.US));
		sdfs.add(new SimpleDateFormat("M/dd/yyyy h:mm a", Locale.US));
		sdfs.add(new SimpleDateFormat("MMMMM dd h:mm a", Locale.US));
		sdfs.add(new SimpleDateFormat("MMMMM dd h:mm:ss a", Locale.US));
		sdfs.add(new SimpleDateFormat("MMMMM, yyyy h:mm a", Locale.US));
	}

	/**
	 * 
	 */
	DateTimeParser() {
		super();
	}

	Date parse(String dateString) {
		Date date = null;
		for (SimpleDateFormat sdf : sdfs) {
			try {
				date = sdf.parse(dateString);
				break;
			} catch (ParseException e) {
				log.debug("使用“" + sdf.toPattern() + "”分解错误，继续尝试下一个分解方式。");
			}
		}
		if (date == null) {
			throw new RuntimeException("解析“" + dateString + "”失败。");
		}
		return date;
	}
}
