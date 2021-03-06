package com.skb.google.tv.isqms;

public class ISQMSData {
	public static final boolean ACTION_ISQMS = false;
	public static final boolean SHOW_LOG = false;

	protected static final int THREAD_WAIT_MILLE_SECONDS = 1000;
	private static final int MESSAGE_REQUEST_AGENT = 20000;
	public static final int MESSAGE_REQUEST_AGENT_THREAD_DESTROY = MESSAGE_REQUEST_AGENT - 1;
	public static final int MESSAGE_REQUEST_AGENT_BINDING = MESSAGE_REQUEST_AGENT;

	private static final int MESSAGE_REQUEST_AGENT_EVNET_H = MESSAGE_REQUEST_AGENT + 1000;
	public static final int MESSAGE_REQUEST_AGENT_EVENT_H01 = MESSAGE_REQUEST_AGENT_EVNET_H + 1;
	public static final int MESSAGE_REQUEST_AGENT_EVENT_H02 = MESSAGE_REQUEST_AGENT_EVNET_H + 2;
	public static final int MESSAGE_REQUEST_AGENT_EVENT_H06 = MESSAGE_REQUEST_AGENT_EVNET_H + 6;
	public static final int MESSAGE_REQUEST_AGENT_EVENT_H08 = MESSAGE_REQUEST_AGENT_EVNET_H + 8;
	public static final int MESSAGE_REQUEST_AGENT_EVENT_H09 = MESSAGE_REQUEST_AGENT_EVNET_H + 9;
	public static final int MESSAGE_REQUEST_AGENT_EVENT_H10 = MESSAGE_REQUEST_AGENT_EVNET_H + 10;
	public static final int MESSAGE_REQUEST_AGENT_EVENT_H13 = MESSAGE_REQUEST_AGENT_EVNET_H + 13;
	public static final int MESSAGE_REQUEST_AGENT_EVENT_H15 = MESSAGE_REQUEST_AGENT_EVNET_H + 15;

	private static final int MESSAGE_REQUEST_AGENT_EVNET_E = MESSAGE_REQUEST_AGENT_EVNET_H + 1000;
	public static final int MESSAGE_REQUEST_AGENT_EVENT_E03 = MESSAGE_REQUEST_AGENT_EVNET_E + 3;
	public static final int MESSAGE_REQUEST_AGENT_EVENT_E09 = MESSAGE_REQUEST_AGENT_EVNET_E + 9;
	public static final int MESSAGE_REQUEST_AGENT_EVENT_E14 = MESSAGE_REQUEST_AGENT_EVNET_E + 14;
	public static final int MESSAGE_REQUEST_AGENT_EVENT_E18 = MESSAGE_REQUEST_AGENT_EVNET_E + 18;
	public static final int MESSAGE_REQUEST_AGENT_EVENT_E19 = MESSAGE_REQUEST_AGENT_EVNET_E + 19;
	public static final int MESSAGE_REQUEST_AGENT_EVENT_E20 = MESSAGE_REQUEST_AGENT_EVNET_E + 20;
	public static final int MESSAGE_REQUEST_AGENT_EVENT_E21 = MESSAGE_REQUEST_AGENT_EVNET_E + 21;

	private static final int MESSAGE_REQUEST_AGENT_EVNET_C = MESSAGE_REQUEST_AGENT_EVNET_E + 1000;
	public static final int MESSAGE_REQUEST_AGENT_EVENT_C03_RECENT_ALL_UPGRADE = MESSAGE_REQUEST_AGENT_EVNET_C + 3;
	public static final int MESSAGE_REQUEST_AGENT_EVENT_C04_AGE_LIMIT_CHANGE = MESSAGE_REQUEST_AGENT_EVNET_C + 4;
	public static final int MESSAGE_REQUEST_AGENT_EVENT_C05_AUTO_NEXT_CHANGE = MESSAGE_REQUEST_AGENT_EVNET_C + 5;
	public static final int MESSAGE_REQUEST_AGENT_EVENT_C06_ADMETA_FILE_DOWNLOAD = MESSAGE_REQUEST_AGENT_EVNET_C + 6;
	public static final int MESSAGE_REQUEST_AGENT_EVENT_C07_REBOOT = MESSAGE_REQUEST_AGENT_EVNET_C + 7;
	public static final int MESSAGE_REQUEST_AGENT_EVENT_C09_RESOLUTION_CHANGE = MESSAGE_REQUEST_AGENT_EVNET_C + 9;
	public static final int MESSAGE_REQUEST_AGENT_EVENT_C14_STB_PASSWORD_CHANGE = MESSAGE_REQUEST_AGENT_EVNET_C + 14;
	public static final int MESSAGE_REQUEST_AGENT_EVENT_C15_CHILDLIMIT_PASSWORD_CHANGE = MESSAGE_REQUEST_AGENT_EVNET_C + 15;
	public static final int MESSAGE_REQUEST_AGENT_EVENT_C17_CHILDLIMIT_TIME_CHANGE = MESSAGE_REQUEST_AGENT_EVNET_C + 17;
	public static final int MESSAGE_REQUEST_AGENT_EVENT_C18_ADULT_AUTH_CHANGE = MESSAGE_REQUEST_AGENT_EVNET_C + 18;

	public static final int MESSAGE_RESPONSE_AGENT = MESSAGE_REQUEST_AGENT + 10000;
	public static final int MESSAGE_RESPONSE_AGENT_OK = MESSAGE_RESPONSE_AGENT + 1;
	public static final int MESSAGE_RESPONSE_AGENT_ERROR = MESSAGE_RESPONSE_AGENT + 2;

	protected static final int ISQMS_SUCCESS = 0;
	protected static final int ISQMS_ERROR = -1;

	protected static final String RESULT_TRUE = "1";
	protected static final String RESULT_FALSE = "0";

	protected static final String ISQMS_STRING_OPEN = "open";
	protected static final String ISQMS_STRING_CLOSE = "close";

	protected static final String ISQMS_STRING_UPG_UPGRADE_START = "000";
	protected static final String ISQMS_STRING_UPG_UPGRADE_SUCCESS = "100";
	protected static final String ISQMS_STRING_UPG_UPGRADE_FAIL = "9XX";

	protected static final String ISQMS_STRING_VOD4_VOD_ERROR_SUCCESS = "1";
	protected static final String ISQMS_STRING_VOD4_VOD_ERROR_FAIL = "0";

	protected static final String ISQMS_STRING_IPTV_CH_MODE_DIRECT_INPUT = "01";
	protected static final String ISQMS_STRING_IPTV_CH_MODE_CH_BUTTON = "02";
	protected static final String ISQMS_STRING_IPTV_CH_MODE_MINI_EPG = "03";
	protected static final String ISQMS_STRING_IPTV_CH_MODE_ALL_EPG = "04";
	protected static final String ISQMS_STRING_IPTV_CH_MODE_EPG = "05";
	protected static final String ISQMS_STRING_IPTV_CH_MODE_RESERVATION = "06";
	protected static final String ISQMS_STRING_IPTV_CH_MODE_VOICE_SEARCH = "07";
	protected static final String ISQMS_STRING_IPTV_CH_MODE_GOOGLE_TV_APP = "08";
	protected static final String ISQMS_STRING_IPTV_CH_MODE_BACK_KEY = "09";
	protected static final String ISQMS_STRING_IPTV_CH_MODE_FAVORITE_CHANNEL_KEY = "10";
	protected static final String ISQMS_STRING_IPTV_CH_MODE_SEARCH = "11";
	protected static final String ISQMS_STRING_IPTV_CH_MODE_ETC = "99";

	protected static final String ISQMS_STRING_IPTV_ERROR_CODE_NORMAL = "000";
	protected static final String ISQMS_STRING_IPTV_ERROR_CODE_WEAK_SIGNAL = "001";
	protected static final String ISQMS_STRING_IPTV_ERROR_CODE_OTHER = "999";

	protected static final String ISQMS_STRING_SCS_ECODE_IMPOSSIBLE_TO_CONNECT = "01";
	protected static final String ISQMS_STRING_SCS_ECODE_RECEIVE_NO_REPLY = "02";
	protected static final String ISQMS_STRING_SCS_ECODE_RESPONSE_ERROR = "03";

	protected static final String ISQMS_STRING_LGS_ECODE_IMPOSSIBLE_TO_CONNECT = "01";
	protected static final String ISQMS_STRING_LGS_ECODE_RECEIVE_NO_REPLY = "02";
	protected static final String ISQMS_STRING_LGS_ECODE_RESPONSE_ERROR = "03";

	protected static final String ISQMS_STRING_WSCS_ECODE_IMPOSSIBLE_TO_CONNECT = "01";
	protected static final String ISQMS_STRING_WSCS_ECODE_RECEIVE_NO_REPLY = "02";
	protected static final String ISQMS_STRING_WSCS_ECODE_RESPONSE_ERROR = "03";

	protected static final int //
			COMMON = 0, //
			CURRENT_STATUS = 1, //
			CHECK_RESULT = 2, //
			RTST_INFO = 3;

	protected static final int //
			STATUS_ALL = -1, //
			STATUS_CPU = 0, //
			STATUS_MEM = 1, //
			STATUS_DISK = 2, //
			STATUS_NET = 3, //
			STATUS_CONF = 4;

	protected static final int //
			COMMON_ALL = -1, //
			COMMON_PROTOCOL_VER = 0, //
			COMMON_EVENT_ID = 1, //
			COMMON_EVENT_TS = 2, //
			COMMON_STB_VER = 3, //
			COMMON_STB_ID = 4, //
			COMMON_STB_MAC = 5, //
			COMMON_STB_SW_VER = 6, //
			COMMON_STB_EPG_VER = 7, //
			COMMON_STB_MODEL = 8, //
			COMMON_STB_AUTH = 9, //
			COMMON_STB_IPTV_AREA = 10, //
			COMMON_STB_SVC_MODE = 11;

	protected static final int //
			CHECK_ALL = -1, //
			CHECK_MSG = 0, //
			CHECK_UPG_C_SW = 1, //
			CHECK_UPG_C_CH = 2, //
			CHECK_SVC = 3, //
			CHECK_VOD1 = 4, //
			CHECK_VOD2 = 5, //
			CHECK_VOD3 = 6, //
			CHECK_VOD4 = 7, //
			CHECK_IPTV1 = 8, //
			CHECK_IPTV2 = 9, //
			CHECK_IPTV3 = 10, //
			CHECK_GTM = 11, //
			CHECK_SCS = 12, //
			CHECK_LGS = 13, //
			CHECK_CDN = 14, //
			CHECK_SVCSVR = 15, //
			CHECK_ERR1 = 16, //
			CHECK_PS = 17, //
			CHECK_NET = 18, //
			CHECK_HDD_MSG = 19, //
			CHECK_WSCS = 20, //
			CHECK_ERRORCODE = 21, // 21
			CATEGORY_CHECK_MAX = 22;

	protected static final String //
			EVENT_H01 = "H01", // 전체 주요 정보 보고
			EVENT_H02 = "H02", // 네트워크설정정보 보고
			EVENT_H06 = "H06", // Upgrade진행 상태 보고(시작, 실패/성공)
			EVENT_H08 = "H08", // 채널 Upgrade진행 상태 보고(시작, 진행 중,실패/성공)
			EVENT_H09 = "H09", // 서비스 상태 변경 시 보고
			EVENT_H10 = "H10", // VOD요청 결과 및 소요시간 정보
			EVENT_H13 = "H13", // 채널 선택 정보 보고
			EVENT_H15 = "H15"; // 사용자가 사용자 정보를 입력하여 인증 하는 경우

	protected static final String //
			EVENT_E03 = "E03", // 네트워크 연결 재개 후, 재 접속 가능 시 네트워크 재 접속 시작 보고
			EVENT_E09 = "E09", // 기치 않은 화면정지 감지 시 보고 (추정원인코드/메시지)
			EVENT_E14 = "E14", // VOD 요청 시 장애 전송, VOD 재생 시 장애 전송
			EVENT_E18 = "E18", // WSCS관련 문제 내용 보고
			EVENT_E19 = "E19", // H/E와의 통신상에서 에러 메시지 접수 시
			EVENT_E20 = "E20", // SCS관련 문제 내용 보고
			EVENT_E21 = "E21"; // LGS관련 문제 내용 보고

	protected static final String //
			EVENT_C03 = "C03", // STB 전체 최신 Upgrade
			EVENT_C04 = "C04", // STB 연령등급(시청제한나이) 조정
			EVENT_C05 = "C05", // STB 연속재생 여부 조정
			EVENT_C06 = "C06", // STB 광고 메타파일 재 Download
			EVENT_C07 = "C07", // STB Reboot
			EVENT_C09 = "C09", // STB 해상도 변경
			EVENT_C14 = "C14", // STB비밀번호재설정(구매인증)
			EVENT_C15 = "C15", // 성인 비밀번호재설정
			EVENT_C17 = "C17", // 자녀시청 제한 시간 설정
			EVENT_C18 = "C18"; // 성인인증 사용여부 설정(성인메뉴표기 사용여부 설정)
	// EVENT_C08 = "C08", // STB HDD최적화 실행
	// EVENT_C10 = "C10", // IPTV 채널 Retune

	// protected static final String //
	// EVENT_H01 = "H01", // 전체 주요 정보 보고
	// EVENT_H02 = "H02", // 네트워크설정정보 보고
	// EVENT_H06 = "H06", // Upgrade진행 상태 보고(시작, 실패/성공)
	// EVENT_H08 = "H08", // 채널 Upgrade진행 상태 보고(시작, 진행 중,실패/성공)
	// EVENT_H09 = "H09", // 서비스 상태 변경 시 보고
	// EVENT_H10 = "H10", // VOD요청 결과 및 소요시간 정보
	// EVENT_H13 = "H13", // 채널 선택 정보 보고
	// EVENT_H15 = "H15"; // 사용자가 사용자 정보를 입력하여 인증 하는 경우
	// // EVENT_H00 = "H00", //
	// // EVENT_H12 = "H12", // H/E 최적화 요청 시 수행 후 결과 전송
	// // EVENT_H14 = "H14", // 프로세스 리스트 정보
	// // EVENT_H30 = "H30", // Stream jitter calculation
	// // EVENT_H31 = "H31", // Round trip-time (RTT)
	// // EVENT_H32 = "H32", // Server connection status
	// // EVENT_H33 = "H33", // Bitrate calculation 결과 보고
	// // EVENT_H34 = "H34", // 현재 설정되어 있는 동작 모듈 정보 확인시
	// // EVENT_H35 = "H35", // 설정되 zconfig.conf의 파일 정보
	//
	// protected static final String //
	// EVENT_E03 = "E03", // 네트워크 연결 재개 후, 재 접속 가능 시 네트워크 재 접속 시작 보고
	// EVENT_E09 = "E09", // 기치 않은 화면정지 감지 시 보고 (추정원인코드/메시지)
	// EVENT_E14 = "E14", // VOD 요청 시 장애 전송, VOD 재생 시 장애 전송
	// EVENT_E18 = "E18", // WSCS관련 문제 내용 보고
	// EVENT_E19 = "E19", // H/E와의 통신상에서 에러 메시지 접수 시
	// EVENT_E20 = "E20", // SCS관련 문제 내용 보고
	// EVENT_E21 = "E21"; // LGS관련 문제 내용 보고
	// // EVENT_E01 = "E01", // CPU상태정보 보고
	// // EVENT_E13 = "E13", // GTM관련 문제 내용 보고(DNS)
	// // EVENT_E16 = "E16", // Buffer Dry out, Overflow 상태를 전달
	// // EVENT_E23 = "E23", // 기타 SVC SVR관련문제 내용 보고
	// // EVENT_E24 = "E24", // IPTV플레이 관련 문제 내용 보고
	// // EVENT_E25 = "E25", // MEM상태정보 보고
	// // EVENT_E26 = "E26", // DISK상태정보 보고
	// // EVENT_E30 = "E30", // ISQMS RTT 모듈 내 Systemcall 문제 발생시
	// // EVENT_E31 = "E31", // ISQMS Connection_checking 모듈 내 URL 문제 발생시
	// // EVENT_E32 = "E32", // ISQMS Server connection limitation 도달 문제 발생시
	// // EVENT_E33 = "E33", // ISQMS Connection_checking 모듈 내 rtsp 실행 문제 발생시
	// // EVENT_E34 = "E34", // ISQMS Jitter 모듈 내 지연 문제 발생시
	// // EVENT_E35 = "E35", // ISQMS bitrate 모듈 내 임계치 이상 rate 발생시
	// // EVENT_E36 = "E36", // ISQMS bitrate 모듈 내 임계치 이하, Over-rate 3회 발생시
	// // EVENT_E37 = "E37"; // SDK Network error 발생시
}