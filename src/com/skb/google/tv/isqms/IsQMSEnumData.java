package com.skb.google.tv.isqms;

public class IsQMSEnumData {
	// =========================================================================
	// < DEFINITION Enumerate variables
	// =========================================================================
	// < SCS mode type
	public static String PREFIX_SCV_MODE = "SCV_MODE_";
	public static String PREFIX_DISPLAY_MODE = "MODE_";
	public static String PREFIX_TV_RATE_MODE = "MODE_";
	public static String PREFIX_VIDEO_RATE_MODE = "MODE_";

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
		AGE_NON_USE, //
		AGE_7, //
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
		MODE_4_3, //
		MODE_16_9, //
	}

	public enum eVIDEO_RATE_MODE {
		MODE_ORG, //
		MODE_SCR, //
	}
}
