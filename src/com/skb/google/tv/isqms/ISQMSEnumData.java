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
		SCV_MODE_SLP, // SLP
		SCV_MODE_HOM, // HOM
		SCV_MODE_ITV, // ITV : IPTV
		SCV_MODE_VOD, // VOD : 로딩/엔딩광고 포함
		SCV_MODE_VAS, // VAS : 펀&조이에서 App 진입
		SCV_MODE_APP, // APP : 위젯, All Apps에서 App 진입
	}

	public enum eAGE_LIMIT_TYPE {
		AGE_00, // 00 : 제한 없음
		AGE_07, // 07 : 7세
		AGE_12, // 12 : 12세
		AGE_15, // 15 : 15세
		AGE_18, // 18 : 18세이상
	}

	public enum eDISPLAY_MODE {
		MODE_480i, // 480i
		MODE_480p, // 480p
		MODE_720p, // 720p
		MODE_1080i, // 1080i
		MODE_1080p, // 1080p
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
		MODE_DIRECT_INPUT, // 01 : 직접입력
		MODE_CH_BUTTON, // 02 : 채널버튼
		MODE_MINI_EPG, // 03 : miniEPG
		MODE_ALL_EPG, // 04 : 편성표 메뉴, 실시간TV 메뉴
		MODE_EPG, // 05 : 전체 메뉴
		MODE_RESERVATION, // 06 : 예약
		MODE_VOICE_SEARCH, // 07 : 음성검색
		MODE_GOOGLE_TV_APP, // 08 : 구글TV 앱
		MODE_BACK_KEY, // 09 : 이전 키
		MODE_FAVORITE_CHANNEL_KEY, // 10 : 선호채널 키
		MODE_SEARCH, // 11 : 일반검색
		MODE_ETC, // 99 : 기타
	}

	public enum eIPTV_ERROR_CODE {
		MODE_NORMAL, // 000 : 정상
		MODE_WEAK_SIGNAL, // 001 : 신호약함
		MODE_OTHER, // 999 : 기타
	}

	public enum eSCS_ECODE {
		IMPOSSIBLE_TO_CONNECT, // 01 : 연결불가
		RECEIVE_NO_REPLY, // 02 : 응답없음
		RESPONSE_ERROR, // 03 : 응답오류
	}

	public enum eLGS_ECODE {
		IMPOSSIBLE_TO_CONNECT, // 01 : 연결불가
		RECEIVE_NO_REPLY, // 02 : 응답없음
		RESPONSE_ERROR, // 03 : 응답오류
	}

	public enum eWSCS_ECODE {
		IMPOSSIBLE_TO_CONNECT, // 01 : 연결불가
		RECEIVE_NO_REPLY, // 02 : 응답없음
		RESPONSE_ERROR, // 03 : 응답오류
	}
}
