package com.skb.google.tv.isqms;

import java.util.Date;

import com.skb.google.tv.common.util.LogUtil;

public class IsQMSManager {
	private static final String LOGD = IsQMSManager.class.getSimpleName();

	private static IsQMSManager mIsQMSManager;
	private IsQMSCommon mIsQMSCommon;
	private IsQMSCurrentStatus mIsQMSCurrentStatus;

	public IsQMSManager() {
		mIsQMSCommon = new IsQMSCommon();
		mIsQMSCurrentStatus = new IsQMSCurrentStatus();
	}

	public static IsQMSManager getInstance() {
		if (null == mIsQMSManager) {
			mIsQMSManager = new IsQMSManager();
		}

		return mIsQMSManager;
	}

	/**
	 * <pre>
	 * Data Define :
	 * YYMMDDhhmmss
	 * 
	 * Data Define Description :
	 * 현재 이벤트를 처리/보고한 STB시각
	 * 12자리로 구성된 날자+시간
	 * </pre>
	 * 
	 * @return EVENT_TS
	 */
	private String getEventTS() {
		String eventTS = IsQMSUtil.toDateFormat("YYMMDDhhmmss", new Date());
		return eventTS;
	}

	// =========================================================================
	// < setter IsQMS COMMON
	// =========================================================================
	/**
	 * <pre>
	 * Data Define :
	 * X.X
	 * 
	 * Data Define Description :
	 * 현재 STB의 구성 버전 (Legacy/IPTV2.0)
	 * </pre>
	 */
	public void setSTBVersion(String stbVersion) { // IsQMSData.ISQMS_STRING_TAG_STB_VER;
		LogUtil.debug(LOGD, "setSTBVersion() called. stbVersion : " + stbVersion);
		mIsQMSCommon.STB_VER = stbVersion;
	}

	/**
	 * <pre>
	 * Data Define :
	 * XXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXX
	 * 
	 * Data Define Description :
	 * 8-4-4-12자리구성의 문자열
	 * </pre>
	 */
	public void setSTBId(String stbId) { // String value = STBAPIManager.getInstance().getSTBId();
		if (null != stbId) {
			stbId = stbId.replace("{", "");
			stbId = stbId.replace("}", "");
		}
		LogUtil.debug(LOGD, "setSTBId() called. stbId : " + stbId);
		mIsQMSCommon.STB_ID = stbId;
	}

	/**
	 * <pre>
	 * Data Define :
	 * XXXXXXXXXXXX
	 * 
	 * 12자리의 표준 MAC Address 문자열
	 * </pre>
	 */
	public void setMacAddress(String macAddress) { // String value = STBAPIManager.getInstance().getMacAddress();
		LogUtil.debug(LOGD, "setMacAddress() called. macAddress : " + macAddress);
		mIsQMSCommon.STB_MAC = macAddress;
	}

	/**
	 * <pre>
	 * Data Define :
	 * XX.XX.XX-XXXX
	 * 
	 * STB에 적용되어 사용중인 SW버젼
	 * </pre>
	 */
	public void setSWVersion(String swVersion) {
		LogUtil.debug(LOGD, "setSWVersion() called. swVersion : " + swVersion);
		mIsQMSCommon.STB_SW_VER = swVersion;
	}

	/**
	 * <pre>
	 * Data Define :
	 * YYMMDDXXXXXX
	 * 
	 * 12자리로 구성된 채널 생성 ID
	 * -뒤6자리는 시간으로추정되나 의미 없음
	 * </pre>
	 */
	public void setXPGVersion(String xpgVersion) {
		LogUtil.debug(LOGD, "setXPGVersion() called. xpgVersion : " + xpgVersion);
		mIsQMSCommon.STB_XPG_VER = xpgVersion;
	}

	/**
	 * <pre>
	 * Data Define :
	 * XXXXXXXXXX
	 * 
	 * STB장비의 HW모델 10자리 문자
	 * </pre>
	 */
	public void setModelName(String modelName) {
		LogUtil.debug(LOGD, "setModelName() called. modelName : " + modelName);
		mIsQMSCommon.STB_MODEL = modelName;
	}

	/**
	 * <pre>
	 * Data Define :
	 * X
	 * 
	 * STB의 정상인증 여부/결과
	 * {0:False|1:True}
	 * </pre>
	 */
	public void setSTBAuth(boolean isSTBAuth) {
		LogUtil.debug(LOGD, "setSTBAuth() called. isSTBAuth : " + isSTBAuth);
		mIsQMSCommon.STB_AUTH = Boolean.toString(isSTBAuth);
	}

	/**
	 * <pre>
	 * Data Define :
	 * XXX
	 * 
	 * 지상파지역 구분 코드 값
	 * 000~999
	 * </pre>
	 */
	public void setIPTVArea(String iptvArea) {
		LogUtil.debug(LOGD, "setIPTVArea() called. iptvArea : " + iptvArea);
		mIsQMSCommon.STB_IPTV_AREA = iptvArea;
	}

	/**
	 * <pre>
	 * Data Define :
	 * XXX
	 * 
	 * STB의 서비스 제공 상태 모드 정보
	 * 3자리의 상태 코드 값
	 * </pre>
	 */
	public void setSVCMode(IsQMSEnumData.eSCV_MODE scv_MODE) {
		LogUtil.debug(LOGD, "setSVCMode() called. scv_MODE : " + scv_MODE);
		if (null == scv_MODE) {
			return;
		}

		String scvModeName = scv_MODE.name();
		scvModeName = scvModeName.replace(IsQMSEnumData.PREFIX_SCV_MODE, "");
		mIsQMSCommon.STB_SVC_MODE = scvModeName;
	}

	// =========================================================================
	// < setter IsQMS CURRENT STATUS
	// =========================================================================
	/**
	 * <pre>
	 * Data Define :
	 * XXX
	 * 
	 * STB의 서비스 제공 상태 모드 정보
	 * 3자리의 상태 코드 값
	 * </pre>
	 */
	public void setNetDhcpMode(boolean isDhcpMode) {
		LogUtil.debug(LOGD, "setNetDhcpMode() called. isDhcpMode : " + isDhcpMode);
		mIsQMSCurrentStatus.S_NET_DHCP_MODE = Boolean.toString(isDhcpMode);
	}
}
