package com.skb.google.tv.isqms;

public class IsQMSEventIdC {
	// =========================================================================
	// < EVENT_ID_CXX
	// =========================================================================
	// /**
	// * <pre>
	// * 비정기 : STB 인증 여부 처리
	// * 내용 : STB이 가용한 상태로 무조건 처리, 작업후 작업 후 H01 보고
	// * 메시지 내용 : COMMON, STATUS_ALL
	// * </pre>
	// */
	// public static final String ISQMS_STRING_EVENT_ID_C02 = "C02";
	/**
	 * <pre>
	 * 비정기 : STB 전체 최신 Upgrade
	 * 내용 : SW,XPG,EPG정보 모두 Upgrade처리, H06, H08 보고
	 * S:SW/X:XPG/E:EPG
	 * 0:Normal/1:Force
	 * 메시지 내용 : COMMON, STATUS_ALL
	 * </pre>
	 */
	public static final String ISQMS_STRING_EVENT_ID_C03 = "C03";
	/**
	 * <pre>
	 * 비정기 : STB 연령등급(시청제한나이) 조정
	 * 내용 : STB_AGE_LIMIT 값 설정 처리, 작업후 H01 보고
	 * 메시지 내용 : COMMON, STATUS_ALL
	 * </pre>
	 */
	public static final String ISQMS_STRING_EVENT_ID_C04 = "C04";
	/**
	 * <pre>
	 * 비정기 : STB 연속재생 여부 조정
	 * 내용 : STB_AUTONEXT 값 설정 처리, 작업 후 H01 보고
	 * 메시지 내용 : COMMON, STATUS_ALL
	 * </pre>
	 */
	public static final String ISQMS_STRING_EVENT_ID_C05 = "C05";
	/**
	 * <pre>
	 * 비정기 : STB 광고 메타파일 재 Download
	 * 내용 : 광고메타파일 재 Download 처리, 작업 후 H01 보고
	 * 메시지 내용 : COMMON, STATUS_ALL
	 * </pre>
	 */
	public static final String ISQMS_STRING_EVENT_ID_C06 = "C06";
	/**
	 * <pre>
	 * 비정기 : STB Reboot
	 * 내용 : STB Reboot
	 * 메시지 내용 : x
	 * </pre>
	 */
	public static final String ISQMS_STRING_EVENT_ID_C07 = "C07";
	// /**
	// * <pre>
	// * 비정기 : STB HDD최적화 실행
	// * 내용 : HDD최적화 작업 처리, 작업 후 H12보고
	// * 메시지 내용 : COMMON, C_HDD_MSG
	// * </pre>
	// */
	// public static final String ISQMS_STRING_EVENT_ID_C08 = "C08";
	/**
	 * <pre>
	 * 비정기 : STB 해상도 변경
	 * 내용 : STB_SCR_RESOLUTION, STB_SCR_TV, STB_SCR_VIDEO 정보 수정 처리, 작업 후 H01보고
	 * 메시지 내용 : COMMON, STATUS_ALL
	 * </pre>
	 */
	public static final String ISQMS_STRING_EVENT_ID_C09 = "C09";
	/**
	 * <pre>
	 * 비정기 : STB비밀번호 재설정
	 * 내용 : STB비밀번호 재설정 처리, 작업후 H01보고
	 * 메시지 내용 : COMMON, STATUS_ALL
	 * </pre>
	 */
	public static final String ISQMS_STRING_EVENT_ID_C14 = "C14";
	/**
	 * <pre>
	 * 비정기 : 성인(자녀제한)비밀번호 재설정
	 * 내용 : STB비밀번호재설정 처리, 작업후 H01보고
	 * 메시지 내용 : COMMON, STATUS_ALL
	 * </pre>
	 */
	public static final String ISQMS_STRING_EVENT_ID_C15 = "C15";
	/**
	 * <pre>
	 * 비정기 : STB_AGE_TIME 설정
	 * 내용 : 자녀시청 제한 시간 설정 후, H01보고
	 * 메시지 내용 : COMMON, STATUS_ALL
	 * </pre>
	 */
	public static final String ISQMS_STRING_EVENT_ID_C17 = "C17";
	/**
	 * <pre>
	 * 비정기 : STB_ADULT 설정
	 * 내용 : 성인인증 사용여부 설정 후, H01보고
	 * 메시지 내용 : COMMON, STATUS_ALL
	 * </pre>
	 */
	public static final String ISQMS_STRING_EVENT_ID_C18 = "C18";
	// /**
	// * <pre>
	// * 비정기 : VOD 시청 및 광고 재생 상태 요청
	// * 내용 : H10과 동일한 Action/마지막 정보 요청
	// * 메시지 내용 : COMMON, CHECK_SVC, CHECK_VOD1, CHECK_VOD3
	// * </pre>
	// */
	// public static final String ISQMS_STRING_EVENT_ID_C98 = "C98";
	// /**
	// * <pre>
	// * 비정기 : IPTV시청 및 품질 상태 요청
	// * 내용 : H13과 동일한 Action/마지막 정보 요청
	// * 메시지 내용 : COMMON, CHECK_IPTV1, CHECK_IPTV2
	// * </pre>
	// */
	// public static final String ISQMS_STRING_EVENT_ID_C96 = "C96";
	/**
	 * <pre>
	 * 비정기 : SCS 정상 접근 확인 요청
	 * 내용 : SCS 접근 확인 후 결과 보고
	 * 메시지 내용 : COMMON, CHECK_ERR1, CHECK_SCS
	 * </pre>
	 */
	public static final String ISQMS_STRING_EVENT_ID_C95 = "C95";
	/**
	 * <pre>
	 * 비정기 : LGS 정상 접근 확인 요청
	 * 내용 : LGS 접근 확인 후 결과 보고
	 * 메시지 내용 : COMMON, CHECK_ERR1, CHECK_LGS
	 * </pre>
	 */
	public static final String ISQMS_STRING_EVENT_ID_C94 = "C94";
}
