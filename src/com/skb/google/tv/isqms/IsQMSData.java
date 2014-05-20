package com.skb.google.tv.isqms;

public class IsQMSData {
	public static final boolean DEBUG = true;

	public static final String ISQMS_STRING_STB_VER = "3.0";

	public static final int ISQMS_SUCCESS = 0;
	public static final int ISQMS_ERROR = -1;

	public static final String RESULT_TRUE = "1"; // RESULT_TRUE
	public static final String RESULT_FALSE = "0"; // RESULT_FALSE

	public static final String ISQMS_STRING_UPG_UPGRADE_START = "000";
	public static final String ISQMS_STRING_UPG_UPGRADE_SUCCESS = "100";
	public static final String ISQMS_STRING_UPG_UPGRADE_FAIL = "9XX";

	public static final int //
			COMMON = 0, //
			CURRENT_STATUS = 1, //
			CHECK_RESULT = 2, //
			RTST_INFO = 3;

	public static final int //
			STATUS_ALL = -1, //
			STATUS_CPU = 0, //
			STATUS_MEM = 1, //
			STATUS_DISK = 2, //
			STATUS_NET = 3, //
			STATUS_CONF = 4, //
			STATUS_XPG_2 = 5, //
			STATUS_BBRATE = 6;

	public static final int //
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
			CATEGORY_CHECK_MAX = 21;

	public static final String //
			// EVENT_H00 = "H00", //
			EVENT_H01 = "H01", // 전체 주요 정보 보고
			EVENT_H02 = "H02", // 네트워크설정정보 보고
			EVENT_H06 = "H06", // Upgrade진행 상태 보고(시작, 실패/성공)
			EVENT_H08 = "H08", // 채널 Upgrade진행 상태 보고(시작, 진행 중,실패/성공)
			EVENT_H09 = "H09", // 서비스 상태 변경 시 보고
			EVENT_H10 = "H10", // VOD요청 결과 및 소요시간 정보
			// EVENT_H12 = "H12", // H/E 최적화 요청 시 수행 후 결과 전송
			EVENT_H13 = "H13", // 채널 선택 정보 보고
			// EVENT_H14 = "H14", // 프로세스 리스트 정보
			EVENT_H15 = "H15"; // 사용자가 사용자 정보를 입력하여 인증 하는 경우
	// EVENT_H30 = "H30", // Stream jitter calculation
	// EVENT_H31 = "H31", // Round trip-time (RTT)
	// EVENT_H32 = "H32", // Server connection status
	// EVENT_H33 = "H33", // Bitrate calculation 결과 보고
	// EVENT_H34 = "H34", // 현재 설정되어 있는 동작 모듈 정보 확인시
	// EVENT_H35 = "H35", // 설정되 zconfig.conf의 파일 정보

	public static final String //
			// EVENT_E01 = "E01", // CPU상태정보 보고
			EVENT_E03 = "E03", // 네트워크 연결 재개 후, 재 접속 가능 시 네트워크 재 접속 시작 보고
			EVENT_E09 = "E09", // 기치 않은 화면정지 감지 시 보고 (추정원인코드/메시지)
			// EVENT_E13 = "E13", // GTM관련 문제 내용 보고(DNS)
			EVENT_E14 = "E14", // VOD 요청 시 장애 전송, VOD 재생 시 장애 전송
			// EVENT_E16 = "E16", // Buffer Dry out, Overflow 상태를 전달
			EVENT_E18 = "E18", // WSCS관련 문제 내용 보고
			EVENT_E19 = "E19", // H/E와의 통신상에서 에러 메시지 접수 시
			EVENT_E20 = "E20", // SCS관련 문제 내용 보고
			EVENT_E21 = "E21"; // LGS관련 문제 내용 보고
	// EVENT_E23 = "E23", // 기타 SVC SVR관련문제 내용 보고
	// EVENT_E24 = "E24", // IPTV플레이 관련 문제 내용 보고
	// EVENT_E25 = "E25", // MEM상태정보 보고
	// EVENT_E26 = "E26", // DISK상태정보 보고
	// EVENT_E30 = "E30", // ISQMS RTT 모듈 내 Systemcall 문제 발생시
	// EVENT_E31 = "E31", // ISQMS Connection_checking 모듈 내 URL 문제 발생시
	// EVENT_E32 = "E32", // ISQMS Server connection limitation 도달 문제 발생시
	// EVENT_E33 = "E33", // ISQMS Connection_checking 모듈 내 rtsp 실행 문제 발생시
	// EVENT_E34 = "E34", // ISQMS Jitter 모듈 내 지연 문제 발생시
	// EVENT_E35 = "E35", // ISQMS bitrate 모듈 내 임계치 이상 rate 발생시
	// EVENT_E36 = "E36", // ISQMS bitrate 모듈 내 임계치 이하, Over-rate 3회 발생시
	// EVENT_E37 = "E37"; // SDK Network error 발생시

	public static final String //
			// EVENT_C00 = "C00", // Hole punching 명령어
			// EVENT_C01 = "C01", // 스크립트제어명령어
			EVENT_C02 = "C02", // STB 인증 여부 처리
			EVENT_C03 = "C03", // STB 전체 최신 Upgrade
			EVENT_C04 = "C04", // STB 연령등급(시청제한나이) 조정
			EVENT_C05 = "C05", // STB 연속재생 여부 조정
			EVENT_C06 = "C06", // STB 광고 메타파일 재 Download
			EVENT_C07 = "C07", // STB Reboot
			// EVENT_C08 = "C08", // STB HDD최적화 실행
			EVENT_C09 = "C09", // STB 해상도 변경
			// EVENT_C10 = "C10", // IPTV 채널 Retune
			// EVENT_C11 = "C11", // CPU사용율 임계 치 설정
			// EVENT_C12 = "C12", // DISK사용율 임계 치 설정
			EVENT_C13 = "C13", // Process감시 List설정 처리
			EVENT_C14 = "C14", // STB비밀번호재설정
			EVENT_C15 = "C15", // 성인 비밀번호재설정
			// EVENT_C16 = "C16", // MEM사용율 임계 치 설정 처리
			EVENT_C17 = "C17", // 자녀시청 제한 시간 설정
			EVENT_C18 = "C18", // 성인인증 사용여부 설정
			// EVENT_C30 = "C30", // Module 동작 여부 설정시
			// EVENT_C31 = "C31", // Mode 동작 여부 설정시
			// EVENT_C32 = "C32", // RTSP configuration 설정
			// EVENT_C77 = "C77", // 모든 제어 명령의 진행 강제 취소 요청
			// EVENT_C92 = "C92", // qi_uploader
			EVENT_C94 = "C94", // LGS 정상 접근 확인 요청
			EVENT_C95 = "C95", // SCS 정상 접근 확인 요청
			EVENT_C96 = "C96", // IPTV시청 및 품질 상태 요청
			EVENT_C98 = "C98"; // VOD시청 및 광고 재생 상태 요청
	// EVENT_C99 = "C99"; // 전체 주요 정보 보고 요청
}