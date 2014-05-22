package com.skb.google.tv.isqms;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.skb.google.tv.common.util.LogUtil;

public class ISQMSUtil {
	/** 날짜 변환 **/
	public static String toDateFormat(String format, Date date) {
		if (date == null) {
			return new String();
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.KOREA);
		return sdf.format(date);
	}

	public static void debug(String tag, String msg) {
		if (ISQMSData.SHOW_LOG) {
			LogUtil.debug(tag, msg);
		}
	}

	public static void info(String tag, String msg) {
		if (ISQMSData.SHOW_LOG) {
			LogUtil.info(tag, msg);
		}
	}
}
