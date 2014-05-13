package com.skb.google.tv.isqms;

public class IsQMSEventIdE {
	// =========================================================================
	// < EVENT_ID_EXX
	// =========================================================================
	/**
	 * <pre>
	 * 정기(부팅시점기준) : 발생 시
	 * 비정기 : 네트워크 연결 불가
	 * 내용 : 연결 재개 후, 재 접속 가능시 네트워크 재접속 시작 보고
	 * 메시지 내용 : COMMON, STATUS_NET, CHECK_ERR1
	 * </pre>
	 */
	public static final String ISQMS_STRING_EVENT_ID_E03 = "E03";
	/**
	 * <pre>
	 * 정기(부팅시점기준) : 발생 시
	 * 비정기 : 화면정지 시
	 * 내용 : 예기치 않은 화면정지 감지시 보고 (추정원인코드/메시지)
	 * 메시지 내용 : COMMON, CHECK_ERR1, CHECK_SVC, CHECK_VOD3, ERRORCODE
	 * </pre>
	 */
	public static final String ISQMS_STRING_EVENT_ID_E09 = "E09";
	/**
	 * <pre>
	 * 정기(부팅시점기준) : 발생 시
	 * 비정기 : VOD 장애 발생 시
	 * 내용 : VOD 요청 시 장애 전송, VOD 재생 시  장애 전송
	 * 메시지 내용 : COMMON, CHECK_SVC, CHECK_VOD1, CHECK_VOD3, CHECK_VOD4
	 * </pre>
	 */
	public static final String ISQMS_STRING_EVENT_ID_E14 = "E14";
	/**
	 * <pre>
	 * 정기(부팅시점기준) : 발생 시
	 * 비정기 : WSCS 장애 발생
	 * 내용 : WSCS관련 문제 내용 보고
	 * 메시지 내용 : COMMON, CHECK_ERR1, CHECK_WSCS
	 * </pre>
	 */
	public static final String ISQMS_STRING_EVENT_ID_E18 = "E18";
	/**
	 * <pre>
	 * 비정기 : 네트워크 변동시 문제 발생시
	 * 내용 : H/E와의 통신상에서 에러 메시지 접수 시
	 * 메시지 내용 : COMMON, CHECK_ERR1, CHECK_NET
	 * </pre>
	 */
	public static final String ISQMS_STRING_EVENT_ID_E19 = "E19";
	/**
	 * <pre>
	 * 정기(부팅시점기준) : 발생시+중복(10분)후
	 * 비정기 : SCS관련 문제 발생 시
	 * 내용 : SCS관련 문제 내용 보고
	 * 메시지 내용 : COMMON, CHECK_ERR1, CHECK_SCS
	 * </pre>
	 */
	public static final String ISQMS_STRING_EVENT_ID_E20 = "E20";
	/**
	 * <pre>
	 * 정기(부팅시점기준) : 발생시+중복(10분)후
	 * 비정기 : LGS관련 문제 발생 시
	 * 내용 : LGS관련 문제 내용 보고
	 * 메시지 내용 : COMMON, CHECK_ERR1, CHECK_LGS
	 * </pre>
	 */
	public static final String ISQMS_STRING_EVENT_ID_E21 = "E21";
}