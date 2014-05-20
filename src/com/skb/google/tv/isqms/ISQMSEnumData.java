package com.skb.google.tv.isqms;

public class ISQMSEnumData {
	public static String PREFIX_SCV_MODE = "SCV_MODE_";
	public static String PREFIX_AGE_LIMIT_TYPE = "AGE_";
	public static String PREFIX_DISPLAY_MODE = "MODE_";
	public static String PREFIX_TV_RATE_MODE = "MODE_";
	public static String PREFIX_VIDEO_RATE_MODE = "MODE_";
	public static String PREFIX_UPG_UPGRADE = "MODE_";

	// =========================================================================
	// < DEFINITION Enumerate variables
	// =========================================================================
	public static enum eSCV_MODE {
		SCV_MODE_NONE, //
		SCV_MODE_SLP, //
		SCV_MODE_WAK, //
		SCV_MODE_HOM, //
		SCV_MODE_0TV, //
		SCV_MODE_ITV, //
		SCV_MODE_VOD, //
		SCV_MODE_VAS, //
	}

	public enum eAGE_LIMIT_TYPE {
		AGE_00, // 제한 없음
		AGE_07, //
		AGE_12, //
		AGE_15, //
		AGE_18, //
	}

	public enum eDISPLAY_MODE {
		MODE_480i, //
		MODE_480p, //
		MODE_720p, //
		MODE_1080i, //
		MODE_1080p, //
	}

	public enum eTV_RATE_MODE {
		MODE_4_3, // 4:3
		MODE_16_9, // 16:9
	}

	public enum eVIDEO_RATE_MODE {
		MODE_ORG, // 원본비
		MODE_SCR, // 화면비
	}

	public enum eUPG_UPGRADE {
		MODE_START, // 000 : 시작
		MODE_SUCCESS, // 100 : 완료
		MODE_FAIL, // 9XX : 실패
	}
}
