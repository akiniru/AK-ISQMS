//package com.skb.google.tv.isqms;
//
//import java.util.Date;
//
//public class IsQMSManager {
//	private static final String LOGD = IsQMSManager.class.getSimpleName();
//
//	private static IsQMSManager mIsQMSManager;
//
//	public static IsQMSManager getInstance() {
//		if (mIsQMSManager == null) {
//			mIsQMSManager = new IsQMSManager();
//		}
//
//		return mIsQMSManager;
//	}
//
//	/**
//	 * <pre>
//	 * Data Define :
//	 * YYMMDDhhmmss
//	 * 
//	 * Data Define Description :
//	 * 현재 이벤트를 처리/보고한 STB시각
//	 * 12자리로 구성된 날자+시간
//	 * </pre>
//	 * 
//	 * @return EVENT_TS
//	 */
//	private String getEventTS() {
//		String eventTS = IsQMSUtil.toDateFormat("YYMMDDhhmmss", new Date());
//		return eventTS;
//	}
//
//	/**
//	 * <pre>
//	 * Data Define :
//	 * X.X
//	 * 
//	 * Data Define Description :
//	 * 현재 STB의 구성 버전 (Legacy/IPTV2.0)
//	 * </pre>
//	 * 
//	 * @return STB_VER
//	 */
//	private String getSTBVer() {
//		return IsQMSData.ISQMS_STRING_TAG_STB_VER;
//	}
//
//	/**
//	 * <pre>
//	 * Data Define :
//	 * XXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXX
//	 * 
//	 * Data Define Description :
//	 * 8-4-4-12자리구성의 문자열
//	 * </pre>
//	 * 
//	 * @return STB_ID
//	 */
//	private String getSTBId() {
//		String value = STBAPIManager.getInstance().getSTBId();
//		return value;
//	}
//
//	/**
//	 * <pre>
//	 * Data Define :
//	 * 
//	 * 
//	 * 12자리의 표준 MAC Address문자열
//	 * </pre>
//	 * 
//	 * @return STB_MAC
//	 */
//	private String getSTBMac() {
//		String value = STBAPIManager.getInstance().getMacAddress();
//		return value;
//	}
//}
