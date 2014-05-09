package com.skb.google.tv.isqms;

public class IsQMSEventIdH {
	// =========================================================================
	// < EVENT_ID_HXX
	// =========================================================================
	/**
	 * <pre>
	 * 정기(부팅시점기준) : 매10분
	 * 비정기 : 전원 ON, Reset 시작 등 주요 핵심동작 시
	 * 내용 : 전체 주요 정보 보고
	 * 메시지 내용 : COMMON, STATUS_ALL
	 * </pre>
	 */
	public static final String ISQMS_STRING_EVENT_ID_H01 = "H01";
	/**
	 * <pre>
	 * 비정기 : Network 설정 변경 시
	 * 내용 : 네트워크설정정보 보고
	 *   - UI 에서 사용자가 네트웍 설정을변경 했을 때
	 * 메시지 내용 : COMMON, STATUS_NET
	 * </pre>
	 */
	public static final String ISQMS_STRING_EVENT_ID_H02 = "H02";
	/**
	 * <pre>
	 * 비정기 : SW Upgrade 진행 중
	 * 내용 : Upgrade진행 상태 보고(시작,실패/성공)
	 * 메시지 내용 : COMMON, C_SW_UPGRADE
	 * </pre>
	 */
	public static final String ISQMS_STRING_EVENT_ID_H06 = "H06";
	// /**
	// * <pre>
	// * 비정기 : Channel Upgrade 진행 중
	// * 내용 : Upgrade진행 상태 보고(시작,진행중,실패/성공)
	// * - 진행 중 데이터는 업데이트 된 항목 정보를 전달
	// * 메시지 내용 : COMMON, C_CH_UPGRADE
	// * </pre>
	// */
	// public static final String ISQMS_STRING_EVENT_ID_H08 = "H08";
	/**
	 * <pre>
	 * 비정기 : 서비스상태 변경 시
	 * 내용 : 현재 서비스 상태 정보 보고
	 * 메시지 내용 : COMMON
	 * </pre>
	 */
	public static final String ISQMS_STRING_EVENT_ID_H09 = "H09";
	/**
	 * <pre>
	 * 비정기 : VOD요청 시
	 * 내용 : VOD요청 결과 및 소요시간 정보
	 * 메시지 내용 : COMMON, CHECK_SVC, CHECK_VOD1, CHECK_VOD3
	 * </pre>
	 */
	public static final String ISQMS_STRING_EVENT_ID_H10 = "H10";
	/**
	 * <pre>
	 * 정기(부팅시점기준) : 
	 * 비정기 : 채널집입 후 5초 이상시 1회 
	 * 내용 : 채널 선택 정보
	 * 메시지 내용 : COMMON, CHECK_IPTV1, CHECK_IPTV2
	 * </pre>
	 */
	public static final String ISQMS_STRING_EVENT_ID_H13 = "H13";
	/**
	 * <pre>
	 * 비정기 : STB인증 시도 시
	 * 내용 : 사용자가 사용자 정보를 입력하여 인증 하는 경우
	 * 메시지 내용 : COMMON
	 * </pre>
	 */
	public static final String ISQMS_STRING_EVENT_ID_H15 = "H15";
}
