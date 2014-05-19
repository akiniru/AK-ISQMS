package com.skb.google.tv.isqms;

import java.util.Date;

import com.skb.google.tv.common.util.LogUtil;
import com.skb.google.tv.isqms.IsQMSEnumData.eAGE_LIMIT_TYPE;
import com.skb.google.tv.isqms.IsQMSEnumData.eDISPLAY_MODE;
import com.skb.google.tv.isqms.IsQMSEnumData.eTV_RATE_MODE;
import com.skb.google.tv.isqms.IsQMSEnumData.eUPG_UPGRADE;
import com.skb.google.tv.isqms.IsQMSEnumData.eVIDEO_RATE_MODE;
import com.skb.google.tv.isqms.IsQMSListener.OnAdMetaFileDownloadListener;
import com.skb.google.tv.isqms.IsQMSListener.OnAdultAuthChangeListener;
import com.skb.google.tv.isqms.IsQMSListener.OnAgeLimitChangeListener;
import com.skb.google.tv.isqms.IsQMSListener.OnAutoNextChangeListener;
import com.skb.google.tv.isqms.IsQMSListener.OnChildLimitPasswordChangeListener;
import com.skb.google.tv.isqms.IsQMSListener.OnChildLimitTimeChangeListener;
import com.skb.google.tv.isqms.IsQMSListener.OnLgsNormalAccessListener;
import com.skb.google.tv.isqms.IsQMSListener.OnRebootListener;
import com.skb.google.tv.isqms.IsQMSListener.OnRecentAllUpgradeListener;
import com.skb.google.tv.isqms.IsQMSListener.OnResolutionChangeListener;
import com.skb.google.tv.isqms.IsQMSListener.OnScsNormalAccessListener;
import com.skb.google.tv.isqms.IsQMSListener.OnStbPasswordChangeListener;

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
	private OnStbPasswordChangeListener mStbPasswordChangeListener;
	private OnChildLimitPasswordChangeListener mChildLimitPasswordChangeListener;
	private OnChildLimitTimeChangeListener mChildLimitTimeChangeListener;
	private OnAdultAuthChangeListener mAdultAuthChangeListener;
	private OnScsNormalAccessListener mScsNormalAccessListener;
	private OnLgsNormalAccessListener mLgsNormalAccessListener;

	/** DATA */
	private IsQMSCommon mIsQMSCommon;
	private IsQMSCurrentStatus mIsQMSCurrentStatus;
	private IsQMSCheckResult mIsQMSCheckResult;

	private IsQMSManager() {
		mIsQMSCommon = new IsQMSCommon();
		mIsQMSCurrentStatus = new IsQMSCurrentStatus();
		mIsQMSCheckResult = new IsQMSCheckResult();
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
	public void setStbVersion(String stbVersion) { // IsQMSData.ISQMS_STRING_TAG_STB_VER;
		LogUtil.debug(LOGD, "setStbVersion() called. stbVersion : " + stbVersion);
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
	public void setStbId(String stbId) { // String value = STBAPIManager.getInstance().getSTBId();
		LogUtil.debug(LOGD, "setStbId() called. stbId : " + stbId);
		if (null != stbId) {
			stbId = stbId.replace("{", "");
			stbId = stbId.replace("}", "");
		}
		mIsQMSCommon.STB_ID = stbId;
	}

	/**
	 * <pre>
	 * Data Define :
	 * XXXXXXXXXXXX
	 * 
	 * Data Define Description :
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
	 * Data Define Description :
	 * STB에 적용되어 사용중인 SW버젼
	 * </pre>
	 */
	public void setSwVersion(String swVersion) {
		LogUtil.debug(LOGD, "setSwVersion() called. swVersion : " + swVersion);
		mIsQMSCommon.STB_SW_VER = swVersion;
	}

	/**
	 * <pre>
	 * Data Define :
	 * YYMMDDXXXXXX
	 * 
	 * Data Define Description :
	 * 12자리로 구성된 채널 생성 ID
	 * -뒤6자리는 시간으로추정되나 의미 없음
	 * </pre>
	 */
	public void setXpgVersion(String xpgVersion) {
		LogUtil.debug(LOGD, "setXpgVersion() called. xpgVersion : " + xpgVersion);
		mIsQMSCommon.STB_XPG_VER = xpgVersion;
	}

	/**
	 * <pre>
	 * Data Define :
	 * XXXXXXXXXX
	 * 
	 * Data Define Description :
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
	 * Data Define Description :
	 * STB의 정상인증 여부/결과
	 * {0:False|1:True}
	 * </pre>
	 */
	public void setStbAuth(boolean isSTBAuth) {
		LogUtil.debug(LOGD, "setStbAuth() called. isSTBAuth : " + isSTBAuth);
		mIsQMSCommon.STB_AUTH = Boolean.toString(isSTBAuth);
	}

	/**
	 * <pre>
	 * Data Define :
	 * XXX
	 * 
	 * Data Define Description :
	 * 지상파지역 구분 코드 값
	 * 000~999
	 * </pre>
	 */
	public void setIptvArea(String iptvArea) {
		LogUtil.debug(LOGD, "setIptvArea() called. iptvArea : " + iptvArea);
		mIsQMSCommon.STB_IPTV_AREA = iptvArea;
	}

	/**
	 * <pre>
	 * Data Define :
	 * XXX
	 * 
	 * Data Define Description :
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
	// < setter CURRENT STATUS NET
	// =========================================================================
	/**
	 * <pre>
	 * Data Define :
	 * X
	 * 
	 * Data Define Description :
	 * DHCP 사용 설정 정보
	 * {0:사용안함|1:사용함}
	 * </pre>
	 */
	public void setNetDhcpMode(boolean isDhcpMode) {
		LogUtil.debug(LOGD, "setNetDhcpMode() called. isDhcpMode : " + isDhcpMode);
		if (true == isDhcpMode) {
			mIsQMSCurrentStatus.S_NET_DHCP_MODE = IsQMSData.RESULT_TRUE;
		} else {
			mIsQMSCurrentStatus.S_NET_DHCP_MODE = IsQMSData.RESULT_FALSE;
		}
		mIsQMSCheckResult.S_NET_DHCP_MODE = mIsQMSCurrentStatus.S_NET_DHCP_MODE;
	}

	/**
	 * <pre>
	 * Data Define :
	 * XXX.XXX.XXX.XXX
	 * 
	 * Data Define Description :
	 * IPv4 Address
	 * </pre>
	 */
	public void setNetIpAddr(String netIpAddr) {
		LogUtil.debug(LOGD, "setNetIpAddr() called. netIpAddr : " + netIpAddr);
		mIsQMSCurrentStatus.S_NET_IPADDR = netIpAddr;
		mIsQMSCheckResult.S_NET_IPADDR = mIsQMSCurrentStatus.S_NET_IPADDR;
	}

	/**
	 * <pre>
	 * Data Define :
	 * XXX.XXX.XXX.XXX
	 * 
	 * Data Define Description :
	 * IPv4 Subnet Mask
	 * </pre>
	 */
	public void setNetIpMask(String netIpMask) {
		LogUtil.debug(LOGD, "setNetIpMask() called. netIpMask : " + netIpMask);
		mIsQMSCurrentStatus.S_NET_IPMASK = netIpMask;
		mIsQMSCheckResult.S_NET_IPMASK = mIsQMSCurrentStatus.S_NET_IPMASK;
	}

	/**
	 * <pre>
	 * Data Define :
	 * XXX.XXX.XXX.XXX
	 * 
	 * Data Define Description :
	 * IPv4 Default Gateway
	 * </pre>
	 */
	public void setNetIpGateway(String netIpGateway) {
		LogUtil.debug(LOGD, "setNetIpGateway() called. netIpGateway : " + netIpGateway);
		mIsQMSCurrentStatus.S_NET_IPGW = netIpGateway;
		mIsQMSCheckResult.S_NET_IPGW = mIsQMSCurrentStatus.S_NET_IPGW;
	}

	/**
	 * <pre>
	 * Data Define :
	 * XXX.XXX.XXX.XXX
	 * 
	 * Data Define Description :
	 * IPv4 DNS 1st
	 * </pre>
	 */
	public void setNetDNS1(String netDNS1) {
		LogUtil.debug(LOGD, "setNetDNS1() called. netDNS1 : " + netDNS1);
		mIsQMSCurrentStatus.S_NET_DNS1 = netDNS1;
		mIsQMSCheckResult.S_NET_DNS1 = mIsQMSCurrentStatus.S_NET_DNS1;
	}

	/**
	 * <pre>
	 * Data Define :
	 * XXX.XXX.XXX.XXX
	 * 
	 * Data Define Description :
	 * IPv4 DNS 2nd
	 * </pre>
	 */
	public void setNetDNS2(String netDNS2) {
		LogUtil.debug(LOGD, "setNetDNS2() called. netDNS2 : " + netDNS2);
		mIsQMSCurrentStatus.S_NET_DNS2 = netDNS2;
		mIsQMSCheckResult.S_NET_DNS2 = mIsQMSCurrentStatus.S_NET_DNS2;
	}

	// =========================================================================
	// < setter CURRENT STATUS CONF
	// =========================================================================
	/**
	 * <pre>
	 * Data Define :
	 * XXXXX
	 * 
	 * Data Define Description :
	 * STB의 해상도 설정 정보
	 * {1080i|720p|480p|480i}
	 * </pre>
	 */
	public void setStbScreenResolution(eDISPLAY_MODE display_MODE) {
		LogUtil.debug(LOGD, "setStbScreenResolution() called. display_MODE : " + display_MODE);
		if (null == display_MODE) {
			return;
		}

		String displayMode = display_MODE.name().replace(IsQMSEnumData.PREFIX_DISPLAY_MODE, "");
		mIsQMSCurrentStatus.STB_SCR_RESOLUTION = displayMode;
	}

	/**
	 * <pre>
	 * Data Define :
	 * XXXXX
	 * 
	 * Data Define Description :
	 * STB에 연결된 TV의 화면 비율 정보
	 * {16:9|4:3}
	 * </pre>
	 */
	public void setStbScreenTVRate(eTV_RATE_MODE tv_RATE_MODE) {
		LogUtil.debug(LOGD, "setStbScreenTVRate() called. display_MODE : " + tv_RATE_MODE);
		if (null == tv_RATE_MODE) {
			return;
		}

		String rateMode = tv_RATE_MODE.name().replace(IsQMSEnumData.PREFIX_TV_RATE_MODE, "");
		mIsQMSCurrentStatus.STB_SCR_TV = rateMode;
	}

	/**
	 * <pre>
	 * Data Define :
	 * XXX
	 * 
	 * Data Define Description :
	 * STB이 처리할 비디오 비율 (원본비그대로, 화면비 따름)
	 * {ORG|SCR}
	 * </pre>
	 */
	public void setStbScreenVideoRate(eVIDEO_RATE_MODE video_RATE_MODE) {
		LogUtil.debug(LOGD, "setStbScreenVideoRate() called. video_RATE_MODE : " + video_RATE_MODE);
		if (null == video_RATE_MODE) {
			return;
		}

		String videoRateMode = video_RATE_MODE.name().replace(IsQMSEnumData.PREFIX_VIDEO_RATE_MODE, "");
		mIsQMSCurrentStatus.STB_SCR_VIDEO = videoRateMode;
	}

	/**
	 * <pre>
	 * Data Define :
	 * X
	 * 
	 * Data Define Description :
	 * 성인인증 사용 여부
	 * {0:NotAllow|1:Allow}
	 * </pre>
	 */
	public void setAllowStbAdult(boolean isAllowSTBAdult) {
		LogUtil.debug(LOGD, "setAllowStbAdult() called. isAllowSTBAdult : " + isAllowSTBAdult);
		if (true == isAllowSTBAdult) {
			mIsQMSCurrentStatus.STB_ADULT = IsQMSData.RESULT_TRUE;
		} else {
			mIsQMSCurrentStatus.STB_ADULT = IsQMSData.RESULT_FALSE;
		}
	}

	/**
	 * <pre>
	 * Data Define :
	 * XX
	 * 
	 * Data Define Description :
	 * 시청 제한 나이 숫자
	 * 00~99
	 * 07, 12, 15, 18이상, 00는제한없음
	 * </pre>
	 */
	public void setAgeLimit(eAGE_LIMIT_TYPE age_LIMIT_TYPE) {
		LogUtil.debug(LOGD, "setAgeLimit() called. age_LIMIT_TYPE : " + age_LIMIT_TYPE);
		if (null == age_LIMIT_TYPE) {
			return;
		}

		String ageLimitType = age_LIMIT_TYPE.name().replace(IsQMSEnumData.PREFIX_AGE_LIMIT_TYPE, "");
		mIsQMSCurrentStatus.STB_AGE_LIMIT = ageLimitType;
	}

	/**
	 * <pre>
	 * Data Define :
	 * XX
	 * 
	 * Data Define Description :
	 * 자녀시청제한 시간
	 * 00~99
	 * 00:제한없음, 01~99:시간단위설정
	 * </pre>
	 */
	public void setChildLimitTime(String childLimitTime) {
		LogUtil.debug(LOGD, "setChildLimitTime() called. childLimitTime : " + childLimitTime);
		mIsQMSCurrentStatus.STB_AGE_TIME = childLimitTime;
	}

	/**
	 * <pre>
	 * Data Define :
	 * X
	 * 
	 * Data Define Description :
	 * 연속재생여부
	 * {0:No|1:Yes}
	 * </pre>
	 */
	public void setAutoNext(boolean isAutoNext) {
		LogUtil.debug(LOGD, "setAutoNext() called. isAutoNext : " + isAutoNext);
		if (true == isAutoNext) {
			mIsQMSCurrentStatus.STB_AUTONEXT = IsQMSData.RESULT_TRUE;
		} else {
			mIsQMSCurrentStatus.STB_AUTONEXT = IsQMSData.RESULT_FALSE;
		}
	}

	// =========================================================================
	// < setter CHECK RESULT XPG2
	// =========================================================================
	/**
	 * <pre>
	 * Data Define :
	 * YYMMDDhhmmss
	 * 
	 * Data Define Description :
	 * XPG FULL 버전
	 * </pre>
	 */
	public void setXPG2XpgFullVersion(String xpgFullVersion) {
		LogUtil.debug(LOGD, "setXPG2XpgFullVersion() called. xpgFullVersion : " + xpgFullVersion);
		mIsQMSCurrentStatus.XPG_FULL = xpgFullVersion;
	}

	// =========================================================================
	// < setter CHECK RESULT UPG
	// =========================================================================
	/**
	 * <pre>
	 * Data Define :
	 * XXX
	 * 
	 * Data Define Description :
	 * SW Upgrade 진행상태
	 * {000:시작|100:완료|9XX:실패}
	 * </pre>
	 */
	public void setUPGSwUpgrade(eUPG_UPGRADE upg_UPGRADE) {
		LogUtil.debug(LOGD, "setUPGSwUpgrade() called. upg_UPGRADE : " + upg_UPGRADE);
		if (null == upg_UPGRADE) {
			return;
		}

		String upgSwUpgrade = null;
		switch (upg_UPGRADE) {
			case MODE_START:
				upgSwUpgrade = IsQMSData.ISQMS_STRING_UPG_UPGRADE_START;
				break;
			case MODE_SUCCESS:
				upgSwUpgrade = IsQMSData.ISQMS_STRING_UPG_UPGRADE_SUCCESS;
				break;
			case MODE_FAIL:
				upgSwUpgrade = IsQMSData.ISQMS_STRING_UPG_UPGRADE_FAIL;
				break;
			default:
				return;
		}
		mIsQMSCheckResult.UPG_C_SW_UPGRADE = upgSwUpgrade;
	}

	/**
	 * <pre>
	 * Data Define :
	 * XXX
	 * 
	 * Data Define Description :
	 * Channel Upgrade 진행상태
	 * {000:시작|100:완료|9XX:실패}
	 * </pre>
	 */
	public void setUPGChannelUpgrade(eUPG_UPGRADE upg_UPGRADE) {
		LogUtil.debug(LOGD, "setUPGChannelUpgrade() called. upg_UPGRADE : " + upg_UPGRADE);
		if (null == upg_UPGRADE) {
			return;
		}

		String upgChannelUpgrade = null;
		switch (upg_UPGRADE) {
			case MODE_START:
				upgChannelUpgrade = IsQMSData.ISQMS_STRING_UPG_UPGRADE_START;
				break;
			case MODE_SUCCESS:
				upgChannelUpgrade = IsQMSData.ISQMS_STRING_UPG_UPGRADE_SUCCESS;
				break;
			case MODE_FAIL:
				upgChannelUpgrade = IsQMSData.ISQMS_STRING_UPG_UPGRADE_FAIL;
				break;
			default:
				return;
		}
		mIsQMSCheckResult.UPG_C_CH_UPGRADE = upgChannelUpgrade;
	}

	// =========================================================================
	// < setter CHECK RESULT SVC
	// =========================================================================
	/**
	 * <pre>
	 * Data Define :
	 * XXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXX
	 * 
	 * Data Define Description :
	 * VOD Content ID
	 * 
	 * Sample : 
	 * 2B6C4220-7FEA-40DC-AC56-5AE29F364C97
	 * </pre>
	 */
	public void setSVCVodCid(String vodCid) {
		LogUtil.debug(LOGD, "setSVCVodCid() called. vodCid : " + vodCid);
		mIsQMSCheckResult.SVC_C_VOD_CID = vodCid;
	}

	/**
	 * <pre>
	 * Data Define :
	 * XXXXXX,XXXXXX,….
	 * 
	 * Data Define Description :
	 * VOD재생용 광고 ID
	 * 
	 * Sample : 
	 * 2B6C4220-7FEA-40DC-AC56-5AE29F364C97
	 * </pre>
	 */
	public void setSVCVodAid(String vodAid) {
		LogUtil.debug(LOGD, "setSVCVodAid() called. vodAid : " + vodAid);
		mIsQMSCheckResult.SVC_C_VOD_AID = vodAid;
	}

	// =========================================================================
	// < setter CHECK RESULT VOD1
	// =========================================================================
	/**
	 * <pre>
	 * Data Define :
	 * XXX.XXX.XXX.XXX
	 * 
	 * Data Define Description :
	 * IPv4 Address - SCS Server IP
	 * </pre>
	 */
	public void setVOD1VodScsIp(String vodScsIp) {
		LogUtil.debug(LOGD, "setVOD1VodScsIp() called. vodScsIp : " + vodScsIp);
		mIsQMSCheckResult.VOD1_C_VOD_SCS_IP = vodScsIp;
	}

	/**
	 * <pre>
	 * Data Define :
	 * SSS.MMM
	 * 
	 * Data Define Description :
	 * SCS요청후 회신까지 소요시간
	 * SSS(second), XXX(msec)
	 * </pre>
	 */
	public void setVOD1VodScsRt(String vodScsRt) {
		LogUtil.debug(LOGD, "setVOD1VodScsRt() called. vodScsRt : " + vodScsRt);
		mIsQMSCheckResult.VOD1_C_VOD_SCS_RT = vodScsRt;
	}

	/**
	 * <pre>
	 * Data Define :
	 * SSS.MMM
	 * 
	 * Data Define Description :
	 * Download 요청후 회신까지 소요시간
	 * SSS(second), XXX(msec)
	 * - RTSP 인 경우 재생 요청 후 play 이벤트가 올라오는 시점으로 변경
	 * </pre>
	 */
	public void setVOD1VodDownRt(String vodDownRt) {
		LogUtil.debug(LOGD, "setVOD1VodDownRt() called. vodDownRt : " + vodDownRt);
		mIsQMSCheckResult.VOD1_C_VOD_DOWN_RT = vodDownRt;
	}

	// =========================================================================
	// < setter CHECK RESULT VOD3
	// =========================================================================
	/**
	 * <pre>
	 * Data Define :
	 * 비정형 가변 문자열
	 * 
	 * Data Define Description :
	 * 컨텐츠명  ( 부러진화살 )
	 * </pre>
	 */
	public void setVOD3VodContentName(String vodContentName) {
		LogUtil.debug(LOGD, "setVOD3VodContentName() called. vodContentName : " + vodContentName);
		mIsQMSCheckResult.VOD3_C_VOD_CONTENT_NAME = vodContentName;
	}

	/**
	 * <pre>
	 * Data Define :
	 * 비정형 가변 문자열
	 * 
	 * Data Define Description :
	 * 컨텐츠 URL ( RTSP://*** )
	 * </pre>
	 */
	public void setVOD3VodContentUrl(String vodContentUrl) {
		LogUtil.debug(LOGD, "setVOD3VodContentUrl() called. vodContentUrl : " + vodContentUrl);
		mIsQMSCheckResult.VOD3_C_VOD_CONTENT_URL = vodContentUrl;
	}

	// =========================================================================
	// < setter CHECK RESULT VOD4
	// =========================================================================
	/**
	 * <pre>
	 * Data Define :
	 * X
	 * 
	 * Data Define Description :
	 * 1:성공
	 * 0:실패 C_MSG 에러 정보
	 * </pre>
	 */
	public void setVOD4VodError(String vodError) {
		LogUtil.debug(LOGD, "setVOD4VodError() called. vodError : " + vodError);
		mIsQMSCheckResult.VOD4_C_VOD_ERR = vodError;
	}

	/**
	 * <pre>
	 * Data Define :
	 * 비정형 가변 문자열
	 * 
	 * Data Define Description :
	 * CGF:컨텐츠 정보 수집 장애
	 * OPT:OPT 장애
	 * RXXXXXXX:VOD 재생 에러(UI 에러코드)
	 * </pre>
	 */
	public void setVOD4Message(String vodMessage) {
		LogUtil.debug(LOGD, "setVOD4Message() called. vodMessage : " + vodMessage);
		mIsQMSCheckResult.VOD4_C_MSG = vodMessage;
	}

	// =========================================================================
	// < setter CHECK RESULT IPTV1
	// =========================================================================
	/**
	 * <pre>
	 * Element Description : 
	 * 채널명, 채널번호
	 * 
	 * Data Define :
	 * XXXX…[XX]
	 * 
	 * Data Define Description :
	 * 현재 또는 마지막 채널명과 채널 번호
	 * MBC[11]
	 * </pre>
	 */
	public void setIPTV1IptvChNum(String iptvChNum) {
		LogUtil.debug(LOGD, "setIPTV1IptvChNum() called. iptvChNum : " + iptvChNum);
		mIsQMSCheckResult.IPTV1_C_IPTV_CH_NUM = iptvChNum;
	}

	/**
	 * <pre>
	 * Data Define :
	 * XX
	 * 
	 * Data Define Description :
	 * 채널진입방법
	 * {01:직접입력|02:채널버튼|03:miniEPG|04:전체EPG:|05EPG}
	 * </pre>
	 */
	public void setIPTV1iptvChMode(String iptvChMode) {
		LogUtil.debug(LOGD, "setIPTV1iptvChMode() called. iptvChMode : " + iptvChMode);
		mIsQMSCheckResult.IPTV1_C_IPTV_CH_MODE = iptvChMode;
	}

	// =========================================================================
	// < setter CHECK RESULT IPTV2
	// =========================================================================
	/**
	 * <pre>
	 * Data Define :
	 * XXX
	 * 
	 * Data Define Description :
	 * 추정되는 IPTV Error상태 코드
	 * {000:정상|001:신호약함|999:기타}
	 * </pre>
	 */
	public void setIPTV2iptvErrorCode(String iptvErrorCode) {
		LogUtil.debug(LOGD, "setIPTV2iptvErrorCode() called. iptvErrorCode : " + iptvErrorCode);
		mIsQMSCheckResult.IPTV2_C_IPTV_ECODE = iptvErrorCode;
	}

	// =========================================================================
	// < setter CHECK RESULT SCS
	// =========================================================================
	/**
	 * <pre>
	 * Data Define :
	 * XXX.XXX.XXX.XXX
	 * 
	 * Data Define Description :
	 * IPv4 Address - Test Requested SCS IP
	 * </pre>
	 */
	public void setSCSscsIp(String scsIp) {
		LogUtil.debug(LOGD, "setSCSscsIp() called. scsIp : " + scsIp);
		mIsQMSCheckResult.SCS_C_SCS_IP = scsIp;
	}

	/**
	 * <pre>
	 * Data Define :
	 * XX
	 * 
	 * Data Define Description :
	 * SCS오류코드
	 * {01:연결불가|02:응답없음|03:응답오류}
	 * </pre>
	 */
	public void setSCSscsErrorCode(String scsErrorCode) {
		LogUtil.debug(LOGD, "setSCSscsErrorCode() called. scsErrorCode : " + scsErrorCode);
		mIsQMSCheckResult.SCS_C_SCS_ECODE = scsErrorCode;
	}

	// =========================================================================
	// < setter CHECK RESULT LGS
	// =========================================================================
	/**
	 * <pre>
	 * Data Define :
	 * XX
	 * 
	 * Data Define Description :
	 * LGS오류코드
	 * {01:연결불가|02:응답없음|03:응답오류}
	 * </pre>
	 */
	public void setLGSlgsErrorCode(String lgsErrorCode) {
		LogUtil.debug(LOGD, "setLGSlgsErrorCode() called. lgsErrorCode : " + lgsErrorCode);
		mIsQMSCheckResult.LGS_C_LGS_ECODE = lgsErrorCode;
	}

	// =========================================================================
	// < setter IsQMS LISTENER
	// =========================================================================

	public void setRecentAllUpgradeListener(OnRecentAllUpgradeListener recentAllUpgradeListener) {
		LogUtil.debug(LOGD, "setRecentAllUpgradeListener() called");
		this.mRecentAllUpgradeListener = recentAllUpgradeListener;
	}

	public void setAgeLimitChangeListener(OnAgeLimitChangeListener ageLimitChangeListener) {
		LogUtil.debug(LOGD, "setAgeLimitChangeListener() called");
		this.mAgeLimitChangeListener = ageLimitChangeListener;
	}

	public void setAutoNextChangeListener(OnAutoNextChangeListener autoNextChangeListener) {
		LogUtil.debug(LOGD, "setAutoNextChangeListener() called");
		this.mAutoNextChangeListener = autoNextChangeListener;
	}

	public void setAdMetaFileDownloadListener(OnAdMetaFileDownloadListener adMetaFileDownloadListener) {
		LogUtil.debug(LOGD, "setAdMetaFileDownloadListener() called");
		this.mAdMetaFileDownloadListener = adMetaFileDownloadListener;
	}

	public void setRebootListener(OnRebootListener rebootListener) {
		LogUtil.debug(LOGD, "setRebootListener() called");
		this.mRebootListener = rebootListener;
	}

	public void setResolutionChangeListener(OnResolutionChangeListener resolutionChangeListener) {
		LogUtil.debug(LOGD, "setResolutionChangeListener() called");
		this.mResolutionChangeListener = resolutionChangeListener;
	}

	public void setStbPasswordChangeListener(OnStbPasswordChangeListener stbPasswordChangeListener) {
		LogUtil.debug(LOGD, "setStbPasswordChangeListener() called");
		this.mStbPasswordChangeListener = stbPasswordChangeListener;
	}

	public void setChildLimitPasswordChangeListener(OnChildLimitPasswordChangeListener childLimitPasswordChangeListener) {
		LogUtil.debug(LOGD, "setChildLimitPasswordChangeListener() called");
		this.mChildLimitPasswordChangeListener = childLimitPasswordChangeListener;
	}

	public void setChildLimitTimeChangeListener(OnChildLimitTimeChangeListener childLimitTimeChangeListener) {
		LogUtil.debug(LOGD, "setChildLimitTimeChangeListener() called");
		this.mChildLimitTimeChangeListener = childLimitTimeChangeListener;
	}

	public void setAdultAuthChangeListener(OnAdultAuthChangeListener adultAuthChangeListener) {
		LogUtil.debug(LOGD, "setAdultAuthChangeListener() called");
		this.mAdultAuthChangeListener = adultAuthChangeListener;
	}

	public void setSCSNormalAccessListener(OnScsNormalAccessListener scsNormalAccessListener) {
		LogUtil.debug(LOGD, "setSCSNormalAccessListener() called");
		this.mScsNormalAccessListener = scsNormalAccessListener;
	}

	public void setLGSNormalAccessListener(OnLgsNormalAccessListener lgsNormalAccessListener) {
		LogUtil.debug(LOGD, "setLGSNormalAccessListener() called");
		this.mLgsNormalAccessListener = lgsNormalAccessListener;
	}

	// =========================================================================
	// < check IsQMS LISTENER
	// =========================================================================
	private boolean checkCommon() {
		LogUtil.debug(LOGD, "checkCommon() called");
		boolean result = false;
		if (null == mIsQMSCommon) {
			LogUtil.debug(LOGD, "checkCommon() mIsQMSCommon is null.");
			return result;
		}

		synchronized (mIsQMSCommon) {
			mIsQMSCommon.EVENT_ID = null;
			mIsQMSCommon.EVENT_TS = null;
			LogUtil.debug(LOGD, "checkCommon() EVENT_ID : " + mIsQMSCommon.EVENT_ID);
			LogUtil.debug(LOGD, "checkCommon() EVENT_TS : " + mIsQMSCommon.EVENT_TS);
			LogUtil.debug(LOGD, "checkCommon() STB_VER : " + mIsQMSCommon.STB_VER);
			LogUtil.debug(LOGD, "checkCommon() STB_ID : " + mIsQMSCommon.STB_ID);
			LogUtil.debug(LOGD, "checkCommon() STB_MAC : " + mIsQMSCommon.STB_MAC);
			LogUtil.debug(LOGD, "checkCommon() STB_SW_VER : " + mIsQMSCommon.STB_SW_VER);
			LogUtil.debug(LOGD, "checkCommon() STB_XPG_VER : " + mIsQMSCommon.STB_XPG_VER);
			LogUtil.debug(LOGD, "checkCommon() STB_MODEL : " + mIsQMSCommon.STB_MODEL);
			LogUtil.debug(LOGD, "checkCommon() STB_AUTH : " + mIsQMSCommon.STB_AUTH);
			LogUtil.debug(LOGD, "checkCommon() STB_IPTV_AREA : " + mIsQMSCommon.STB_IPTV_AREA);
			LogUtil.debug(LOGD, "checkCommon() STB_SVC_MODE : " + mIsQMSCommon.STB_SVC_MODE);
			if (null != mIsQMSCommon.EVENT_TS && null != mIsQMSCommon.STB_VER && null != mIsQMSCommon.STB_ID //
					&& null != mIsQMSCommon.STB_MAC && null != mIsQMSCommon.STB_SW_VER && null != mIsQMSCommon.STB_XPG_VER //
					&& null != mIsQMSCommon.STB_MODEL && null != mIsQMSCommon.STB_AUTH && null != mIsQMSCommon.STB_IPTV_AREA //
					&& null != mIsQMSCommon.STB_SVC_MODE) {
				return true;
			}
			LogUtil.debug(LOGD, "checkCommon() result : " + result);
			return result;
		}
	}

	private boolean checkStatusAll() {
		LogUtil.debug(LOGD, "checkStatusAll() called");
		boolean result = false;
		if (null == mIsQMSCurrentStatus) {
			LogUtil.debug(LOGD, "checkStatusAll() mIsQMSCurrentStatus is null.");
			return result;
		}

		synchronized (mIsQMSCurrentStatus) {
			result = checkStatusNet();
			if (false == result) {
				return result;
			}

			return result;
		}
	}

	private boolean checkStatusNet() {
		LogUtil.debug(LOGD, "checkStatusNet() called");
		boolean result = false;
		if (null == mIsQMSCurrentStatus) {
			LogUtil.debug(LOGD, "checkStatusNet() mIsQMSCurrentStatus is null.");
			return result;
		}

		synchronized (mIsQMSCurrentStatus) {
			LogUtil.debug(LOGD, "checkStatusNet() S_NET_DHCP_MODE : " + mIsQMSCurrentStatus.S_NET_DHCP_MODE);
			LogUtil.debug(LOGD, "checkStatusNet() S_NET_IPADDR : " + mIsQMSCurrentStatus.S_NET_IPADDR);
			LogUtil.debug(LOGD, "checkStatusNet() S_NET_IPMASK : " + mIsQMSCurrentStatus.S_NET_IPMASK);
			LogUtil.debug(LOGD, "checkStatusNet() S_NET_IPGW : " + mIsQMSCurrentStatus.S_NET_IPGW);
			LogUtil.debug(LOGD, "checkStatusNet() S_NET_DNS1 : " + mIsQMSCurrentStatus.S_NET_DNS1);
			LogUtil.debug(LOGD, "checkStatusNet() S_NET_DNS2 : " + mIsQMSCurrentStatus.S_NET_DNS2);
			if (null != mIsQMSCurrentStatus.S_NET_DHCP_MODE && null != mIsQMSCurrentStatus.S_NET_IPADDR && null != mIsQMSCurrentStatus.S_NET_IPMASK //
					&& null != mIsQMSCurrentStatus.S_NET_IPGW && null != mIsQMSCurrentStatus.S_NET_DNS1 && null != mIsQMSCurrentStatus.S_NET_DNS2) {
				return true;
			}
			LogUtil.debug(LOGD, "checkStatusNet() result : " + result);
			return result;
		}
	}

	// =========================================================================
	// < reques IsQMS LISTENER
	// =========================================================================
	private void requestListener(eLISTENER_TYPE type) {
		LogUtil.debug(LOGD, "requestListener() called");
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
						if (null != message.obj1 && null != message.obj2 && null != message.obj3 && //
								true == (message.obj1 instanceof eDISPLAY_MODE && true == (message.obj2 instanceof eVIDEO_RATE_MODE) && true == (message.obj3 instanceof eTV_RATE_MODE))) {
							eDISPLAY_MODE display_MODE = (eDISPLAY_MODE) message.obj1;
							eVIDEO_RATE_MODE video_RATE_MODE = (eVIDEO_RATE_MODE) message.obj2;
							eTV_RATE_MODE tv_RATE_MODE = (eTV_RATE_MODE) message.obj3;
							mResolutionChangeListener.onResolutionChange(display_MODE, video_RATE_MODE, tv_RATE_MODE);
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
				if (null != mStbPasswordChangeListener) {
					if (null != data && true == (data instanceof String) && 0 != ((String) data).length()) {
						String stbPassword = (String) data;
						mStbPasswordChangeListener.onStbPasswordChange(stbPassword);
					} else {
						LogUtil.debug(LOGD, "requestListener() Data is Incorrect data");
					}
				} else {
					LogUtil.debug(LOGD, "requestListener() mSTBPasswordChangeListener is null");
				}
				break;
			case CHILDLIMIT_PASSWORD_CHANGE:
				if (null != mChildLimitPasswordChangeListener) {
					if (null != data && true == (data instanceof String) && 0 != ((String) data).length()) {
						String childLimitPassword = (String) data;
						mChildLimitPasswordChangeListener.onChildLimitPasswordChange(childLimitPassword);
					} else {
						LogUtil.debug(LOGD, "requestListener() Data is Incorrect data");
					}
				} else {
					LogUtil.debug(LOGD, "requestListener() mChildLimitPasswordChangeListener is null");
				}
				break;
			case CHILDLIMIT_TIME_CHANGE:
				if (null != mChildLimitTimeChangeListener) {
					if (null != data && true == (data instanceof String) && 0 != ((String) data).length()) {
						String childLimitTime = (String) data;
						mChildLimitTimeChangeListener.onChildLimitTimeChange(childLimitTime);
					} else {
						LogUtil.debug(LOGD, "requestListener() Data is Incorrect data");
					}
				} else {
					LogUtil.debug(LOGD, "requestListener() mChildLimitTimeChangeListener is null");
				}
				break;
			case ADULT_AUTH_CHANGE:
				if (null != mAdultAuthChangeListener) {
					if (null != data && true == (data instanceof Boolean)) {
						Boolean result = (Boolean) data;
						mAdultAuthChangeListener.onAdultAuthChange(result);
					} else {
						LogUtil.debug(LOGD, "requestListener() mChildLimitTimeChangeListener is null");
					}
				} else {
					LogUtil.debug(LOGD, "requestListener() mAdultAuthChangeListener is null");
				}
				break;
			case SCS_NORMAL_ACCESS:
				if (null != mScsNormalAccessListener) {
					mScsNormalAccessListener.onScsNormalAccess();
				} else {
					LogUtil.debug(LOGD, "requestListener() mSCSNormalAccessListener is null");
				}
				break;
			case LGS_NORMAL_ACCESS:
				if (null != mLgsNormalAccessListener) {
					mLgsNormalAccessListener.onLgsNormalAccess();
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
