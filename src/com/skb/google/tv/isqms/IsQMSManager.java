package com.skb.google.tv.isqms;

import java.util.Date;

import com.skb.google.tv.common.util.LogUtil;
import com.skb.google.tv.isqms.IsQMSData.eAGE_LIMIT_TYPE;
import com.skb.google.tv.isqms.IsQMSData.eDISPLAY_MODE;
import com.skb.google.tv.isqms.IsQMSData.eRATE_MODE;
import com.skb.google.tv.isqms.IsQMSListener.OnAdMetaFileDownloadListener;
import com.skb.google.tv.isqms.IsQMSListener.OnAdultAuthChangeListener;
import com.skb.google.tv.isqms.IsQMSListener.OnAgeLimitChangeListener;
import com.skb.google.tv.isqms.IsQMSListener.OnAutoNextChangeListener;
import com.skb.google.tv.isqms.IsQMSListener.OnChildLimitPasswordChangeListener;
import com.skb.google.tv.isqms.IsQMSListener.OnChildLimitTimeChangeListener;
import com.skb.google.tv.isqms.IsQMSListener.OnLGSNormalAccessListener;
import com.skb.google.tv.isqms.IsQMSListener.OnRebootListener;
import com.skb.google.tv.isqms.IsQMSListener.OnRecentAllUpgradeListener;
import com.skb.google.tv.isqms.IsQMSListener.OnResolutionChangeListener;
import com.skb.google.tv.isqms.IsQMSListener.OnSCSNormalAccessListener;
import com.skb.google.tv.isqms.IsQMSListener.OnSTBPasswordChangeListener;

public class IsQMSManager {
	private static final String LOGD = IsQMSManager.class.getSimpleName();

	private static IsQMSManager mIsQMSManager;

	/** Listener */
	public static enum eLISTENER_TYPE {
		RECENT_ALL_UPGRADE, //
		AGE_LIMIT_CHANGE, //
		AUTO_NEXT_CHANGE, //
		ADMETA_FILE_DOWNLOAD, //
		REBOOT, //
		RESOLUTION_CHANGE, //
		STB_PASSWORD_CHANGE, //
		CHILDLIMIT_PASSWORD_CHANGE, //
		CHILDLIMIT_TIME_CHANGE, //
		ADULT_AUTH_CHANGE, //
		SCS_NORMAL_ACCESS, //
		LGS_NORMAL_ACCESS //
	}

	private OnRecentAllUpgradeListener mRecentAllUpgradeListener;
	private OnAgeLimitChangeListener mAgeLimitChangeListener;
	private OnAutoNextChangeListener mAutoNextChangeListener;
	private OnAdMetaFileDownloadListener mAdMetaFileDownloadListener;
	private OnRebootListener mRebootListener;
	private OnResolutionChangeListener mResolutionChangeListener;
	private OnSTBPasswordChangeListener mSTBPasswordChangeListener;
	private OnChildLimitPasswordChangeListener mChildLimitPasswordChangeListener;
	private OnChildLimitTimeChangeListener mChildLimitTimeChangeListener;
	private OnAdultAuthChangeListener mAdultAuthChangeListener;
	private OnSCSNormalAccessListener mSCSNormalAccessListener;
	private OnLGSNormalAccessListener mLGSNormalAccessListener;

	/** DATA */
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

	// =========================================================================
	// < setter IsQMS LISTENER
	// =========================================================================

	public void setRecentAllUpgradeListener(OnRecentAllUpgradeListener recentAllUpgradeListener) {
		this.mRecentAllUpgradeListener = recentAllUpgradeListener;
	}

	public void setAgeLimitChangeListener(OnAgeLimitChangeListener ageLimitChangeListener) {
		this.mAgeLimitChangeListener = ageLimitChangeListener;
	}

	public void setAutoNextChangeListener(OnAutoNextChangeListener autoNextChangeListener) {
		this.mAutoNextChangeListener = autoNextChangeListener;
	}

	public void setAdMetaFileDownloadListener(OnAdMetaFileDownloadListener adMetaFileDownloadListener) {
		this.mAdMetaFileDownloadListener = adMetaFileDownloadListener;
	}

	public void setRebootListener(OnRebootListener rebootListener) {
		this.mRebootListener = rebootListener;
	}

	public void setResolutionChangeListener(OnResolutionChangeListener resolutionChangeListener) {
		this.mResolutionChangeListener = resolutionChangeListener;
	}

	public void setSTBPasswordChangeListener(OnSTBPasswordChangeListener stbPasswordChangeListener) {
		this.mSTBPasswordChangeListener = stbPasswordChangeListener;
	}

	public void setChildLimitPasswordChangeListener(OnChildLimitPasswordChangeListener childLimitPasswordChangeListener) {
		this.mChildLimitPasswordChangeListener = childLimitPasswordChangeListener;
	}

	public void setChildLimitTimeChangeListener(OnChildLimitTimeChangeListener childLimitTimeChangeListener) {
		this.mChildLimitTimeChangeListener = childLimitTimeChangeListener;
	}

	public void setAdultAuthChangeListener(OnAdultAuthChangeListener adultAuthChangeListener) {
		this.mAdultAuthChangeListener = adultAuthChangeListener;
	}

	public void setSCSNormalAccessListener(OnSCSNormalAccessListener scsNormalAccessListener) {
		this.mSCSNormalAccessListener = scsNormalAccessListener;
	}

	public void setLGSNormalAccessListener(OnLGSNormalAccessListener lgsNormalAccessListener) {
		this.mLGSNormalAccessListener = lgsNormalAccessListener;
	}

	private void requestListener(eLISTENER_TYPE type) {
		requestListener(type, null);
	}

	private void requestListener(eLISTENER_TYPE type, Object data) {
		if (null == type) {
			return;
		}

		LogUtil.debug(LOGD, "requestListener() called. eLISTENER_TYPE : " + type);
		switch (type) {
			case RECENT_ALL_UPGRADE:
				if (null != mRecentAllUpgradeListener) {
					mRecentAllUpgradeListener.onRecentAllUpgrade();
				} else {
					LogUtil.debug(LOGD, "requestListener() mRecentAllUpgradeListener is null");
				}
				break;
			case AGE_LIMIT_CHANGE:
				if (null != mAgeLimitChangeListener) {
					if (null != data && true == (data instanceof eAGE_LIMIT_TYPE)) {
						eAGE_LIMIT_TYPE age_LIMIT_TYPE = (eAGE_LIMIT_TYPE) data;
						mAgeLimitChangeListener.onAgeLimitChange(age_LIMIT_TYPE);
					} else {
						LogUtil.debug(LOGD, "requestListener() Data is Incorrect data");
					}
				} else {
					LogUtil.debug(LOGD, "requestListener() mAgeLimitChangeListener is null");
				}
				break;
			case AUTO_NEXT_CHANGE:
				if (null != mAutoNextChangeListener) {
					if (null != data && true == (data instanceof Boolean)) {
						Boolean result = (Boolean) data;
						mAutoNextChangeListener.onAutoNextChange(result);
					} else {
						LogUtil.debug(LOGD, "requestListener() Data is Incorrect data");
					}
				} else {
					LogUtil.debug(LOGD, "requestListener() mAutoNextChangeListener is null");
				}
				break;
			case ADMETA_FILE_DOWNLOAD:
				if (null != mAdMetaFileDownloadListener) {
					mAdMetaFileDownloadListener.onAdMetaFileDownload();
				} else {
					LogUtil.debug(LOGD, "requestListener() mAdMetaFileDownloadListener is null");
				}
				break;
			case REBOOT:
				if (null != mRebootListener) {
					mRebootListener.onReboot();
				} else {
					LogUtil.debug(LOGD, "requestListener() mRebootListener is null");
				}
				break;
			case RESOLUTION_CHANGE:
				if (null != mResolutionChangeListener) {
					if (null != data && true == (data instanceof IsQMSMessage)) {
						IsQMSMessage message = (IsQMSMessage) data;
						if (null != message.obj1 && null != message.obj2 && //
								true == (message.obj1 instanceof eDISPLAY_MODE && true == (message.obj2 instanceof eRATE_MODE))) {
							eDISPLAY_MODE display_MODE = (eDISPLAY_MODE) message.obj1;
							eRATE_MODE rate_MODE = (eRATE_MODE) message.obj2;
							mResolutionChangeListener.onResolutionChange(display_MODE, rate_MODE);
						} else {
							LogUtil.debug(LOGD, "requestListener() Data is Incorrect data");
						}
					} else {
						LogUtil.debug(LOGD, "requestListener() Data is Incorrect data");
					}
				} else {
					LogUtil.debug(LOGD, "requestListener() mResolutionChangeListener is null");
				}
				break;
			case STB_PASSWORD_CHANGE:
				if (null != mSTBPasswordChangeListener) {
					if (null != data && true == (data instanceof String)) {
						String password = (String) data;
						mSTBPasswordChangeListener.onSTBPasswordChange(password);
					} else {
						LogUtil.debug(LOGD, "requestListener() Data is Incorrect data");
					}
				} else {
					LogUtil.debug(LOGD, "requestListener() mSTBPasswordChangeListener is null");
				}
				break;
			case CHILDLIMIT_PASSWORD_CHANGE:
				if (null != mChildLimitPasswordChangeListener) {
					if (null != data && true == (data instanceof String)) {
						String password = (String) data;
						mChildLimitPasswordChangeListener.onChildLimitPasswordChange(password);
					} else {
						LogUtil.debug(LOGD, "requestListener() Data is Incorrect data");
					}
				} else {
					LogUtil.debug(LOGD, "requestListener() mChildLimitPasswordChangeListener is null");
				}
				break;
			case CHILDLIMIT_TIME_CHANGE:
				if (null != mChildLimitTimeChangeListener) {
					mChildLimitTimeChangeListener.onChildLimitTimeChange();
				} else {
					LogUtil.debug(LOGD, "requestListener() mChildLimitTimeChangeListener is null");
				}
				break;
			case ADULT_AUTH_CHANGE:
				if (null != mAdultAuthChangeListener) {
					mAdultAuthChangeListener.onAdultAuthChange();
				} else {
					LogUtil.debug(LOGD, "requestListener() mAdultAuthChangeListener is null");
				}
				break;
			case SCS_NORMAL_ACCESS:
				if (null != mSCSNormalAccessListener) {
					mSCSNormalAccessListener.onSCSNormalAccess();
				} else {
					LogUtil.debug(LOGD, "requestListener() mSCSNormalAccessListener is null");
				}
				break;
			case LGS_NORMAL_ACCESS:
				if (null != mLGSNormalAccessListener) {
					mLGSNormalAccessListener.onLGSNormalAccess();
				} else {
					LogUtil.debug(LOGD, "requestListener() mLGSNormalAccessListener is null");
				}
				break;

			default:
				LogUtil.debug(LOGD, "requestListener() type is default");
				break;
		}
	}
}
