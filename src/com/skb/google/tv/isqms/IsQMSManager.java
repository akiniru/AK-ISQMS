package com.skb.google.tv.isqms;

import java.util.Date;

import com.skb.google.tv.common.util.LogUtil;

public class IsQMSManager {
	private static final String LOGD = IsQMSManager.class.getSimpleName();

	private static IsQMSManager mIsQMSManager;

	private String mSTBVersion;
	private String mSTBId;

	private String mMacAddress;
	private String mSWVersion;
	private String mXPGVersion;
	private String mEPGVersion;
	private String mModelName;
	private boolean mIsSTBAuth;

	private String mSVCMode;

	public static IsQMSManager getInstance() {
		if (mIsQMSManager == null) {
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
		this.mSTBVersion = stbVersion;
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
		this.mSTBId = stbId;
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
		this.mMacAddress = macAddress;
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
		this.mSWVersion = swVersion;
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
		this.mXPGVersion = xpgVersion;
	}

	/**
	 * <pre>
	 * Data Define :
	 * YYMMDDXXXXXX
	 * 
	 * 12자리로 구성된 DVB-SI EPG Version
	 * -뒤6자리는 시간으로추정되나 의미 없음
	 * </pre>
	 */
	public void setEPGVersion(String epgVersion) {
		LogUtil.debug(LOGD, "setEPGVersion() called. epgVersion : " + epgVersion);
		this.mEPGVersion = epgVersion;
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
		this.mModelName = modelName;
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
		this.mIsSTBAuth = isSTBAuth;
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
		this.mSVCMode = iptvArea;
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
	public void setSVCMode(String svcMode) {
		LogUtil.debug(LOGD, "setSVCMode() called. svcMode : " + svcMode);
		this.mSVCMode = svcMode;
	}
}
