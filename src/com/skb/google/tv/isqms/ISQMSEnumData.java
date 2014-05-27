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

	public enum eVOD4_VOD_ERROR {
		MODE_SUCCESS, // 1 : 성공
		MODE_FAIL, // 0 : 실패
	}

	public enum eIPTV_CH_MODE {
		MODE_DIRECT_INPUT, // 직접입력
		MODE_CH_BUTTON, // 채널버튼
		MODE_MINI_EPG, // miniEPG
		MODE_ALL_EPG, // 전체PG
		MODE_EPG, // EPG
	}

	public enum eIPTV_ERROR_CODE {
		MODE_NORMAL, // 000:정상
		MODE_WEAK_SIGNAL, // 001:신호약함
		MODE_OTHER, // 999:기타
	}

	public enum eSCS_ECODE {
		IMPOSSIBLE_TO_CONNECT, // 01:연결불가
		RECEIVE_NO_REPLY, // 02:응답없음
		RESPONSE_ERROR, // 03:응답오류
	}

	public enum eLGS_ECODE {
		IMPOSSIBLE_TO_CONNECT, // 01:연결불가
		RECEIVE_NO_REPLY, // 02:응답없음
		RESPONSE_ERROR, // 03:응답오류
	}

	public enum eWSCS_ECODE {
		IMPOSSIBLE_TO_CONNECT, // 01:연결불가
		RECEIVE_NO_REPLY, // 02:응답없음
		RESPONSE_ERROR, // 03:응답오류
	}
}
