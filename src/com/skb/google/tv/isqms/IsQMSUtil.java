package com.skb.google.tv.isqms;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class IsQMSUtil {
	/** 날짜 변환 **/
	public static String toDateFormat(String format, Date date) {
		if (date == null) {
			return new String();
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.KOREA);
		return sdf.format(date);
	}
}
