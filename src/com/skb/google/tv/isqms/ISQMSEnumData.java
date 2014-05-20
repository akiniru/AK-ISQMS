package com.skb.google.tv.isqms;

public class ISQMSEnumData {
	public static String PREFIX_SCV_MODE = "SCV_MODE_";
	public static String PREFIX_DISPLAY_MODE = "MODE_";
	public static String PREFIX_TV_RATE_MODE = "MODE_";
	public static String PREFIX_VIDEO_RATE_MODE = "MODE_";
	public static String PREFIX_AGE_LIMIT_TYPE = "AGE_";
	public static String PREFIX_EVENT = "EVENT_";

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

	// =========================================================================
	// < EVENT DEFINITION Enumerate variables
	// =========================================================================
	public enum eEVENT_H {
		EVENT_H00, //
		EVENT_H01, // 전체 주요 정보 보고
		EVENT_H02, // 네트워크설정정보 보고
		EVENT_H06, // Upgrade진행 상태 보고(시작, 실패/성공)
		EVENT_H08, // 채널 Upgrade진행 상태 보고(시작, 진행 중,실패/성공)
		EVENT_H09, // 서비스 상태 변경 시 보고
		EVENT_H10, // VOD요청 결과 및 소요시간 정보
		EVENT_H12, // H/E 최적화 요청 시 수행 후 결과 전송
		EVENT_H13, // 채널 선택 정보 보고
		EVENT_H14, // 프로세스 리스트 정보
		EVENT_H15, // 사용자가 사용자 정보를 입력하여 인증 하는 경우
		EVENT_H30, // Stream jitter calculation
		EVENT_H31, // Round trip-time (RTT)
		EVENT_H32, // Server connection status
		EVENT_H33, // Bitrate calculation 결과 보고
		EVENT_H34, // 현재 설정되어 있는 동작 모듈 정보 확인시
		EVENT_H35, // 설정되 zconfig.conf의 파일 정보
	}

	public enum eEVENT_E {
		EVENT_E01, // CPU상태정보 보고
		EVENT_E03, // 네트워크 연결 재개 후, 재 접속 가능 시 네트워크 재 접속 시작 보고
		EVENT_E09, // 기치 않은 화면정지 감지 시 보고 (추정원인코드/메시지)
		EVENT_E13, // GTM관련 문제 내용 보고(DNS)
		EVENT_E14, // VOD 요청 시 장애 전송, VOD 재생 시 장애 전송
		EVENT_E16, // Buffer Dry out, Overflow 상태를 전달
		EVENT_E18, // WSCS관련 문제 내용 보고
		EVENT_E19, // H/E와의 통신상에서 에러 메시지 접수 시
		EVENT_E20, // SCS관련 문제 내용 보고
		EVENT_E21, // LGS관련 문제 내용 보고
		EVENT_E23, // 기타 SVC SVR관련문제 내용 보고
		EVENT_E24, // IPTV플레이 관련 문제 내용 보고
		EVENT_E25, // MEM상태정보 보고
		EVENT_E26, // DISK상태정보 보고
		EVENT_E30, // ISQMS RTT 모듈 내 Systemcall 문제 발생시
		EVENT_E31, // ISQMS Connection_checking 모듈 내 URL 문제 발생시
		EVENT_E32, // ISQMS Server connection limitation 도달 문제 발생시
		EVENT_E33, // ISQMS Connection_checking 모듈 내 rtsp 실행 문제 발생시
		EVENT_E34, // ISQMS Jitter 모듈 내 지연 문제 발생시
		EVENT_E35, // ISQMS bitrate 모듈 내 임계치 이상 rate 발생시
		EVENT_E36, // ISQMS bitrate 모듈 내 임계치 이하, Over-rate 3회 발생시
		EVENT_E37, // SDK Network error 발생시
	}

	public enum eEVENT_C {
		EVENT_C00, // Hole punching 명령어
		EVENT_C01, // 스크립트제어명령어
		EVENT_C02, // STB 인증 여부 처리
		EVENT_C03, // STB 전체 최신 Upgrade
		EVENT_C04, // STB 연령등급(시청제한나이) 조정
		EVENT_C05, // STB 연속재생 여부 조정
		EVENT_C06, // STB 광고 메타파일 재 Download
		EVENT_C07, // STB Reboot
		EVENT_C08, // STB HDD최적화 실행
		EVENT_C09, // STB 해상도 변경
		EVENT_C10, // IPTV 채널 Retune
		EVENT_C11, // CPU사용율 임계 치 설정
		EVENT_C12, // DISK사용율 임계 치 설정
		EVENT_C13, // Process감시 List설정 처리
		EVENT_C14, // STB비밀번호재설정
		EVENT_C15, // 성인 비밀번호재설정
		EVENT_C16, // MEM사용율 임계 치 설정 처리
		EVENT_C17, // 자녀시청 제한 시간 설정
		EVENT_C18, // 성인인증 사용여부 설정
		EVENT_C30, // Module 동작 여부 설정시
		EVENT_C31, // Mode 동작 여부 설정시
		EVENT_C32, // RTSP configuration 설정
		EVENT_C77, // 모든 제어 명령의 진행 강제 취소 요청
		EVENT_C92, // qi_uploader
		EVENT_C94, // LGS 정상 접근 확인 요청
		EVENT_C95, // SCS 정상 접근 확인 요청
		EVENT_C96, // IPTV시청 및 품질 상태 요청
		EVENT_C98, // VOD시청 및 광고 재생 상태 요청
		EVENT_C99; // 전체 주요 정보 보고 요청
	}
}
