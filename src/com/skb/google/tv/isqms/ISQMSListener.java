package com.skb.google.tv.isqms;

import com.skb.google.tv.isqms.ISQMSEnumData.eAGE_LIMIT_TYPE;
import com.skb.google.tv.isqms.ISQMSEnumData.eDISPLAY_MODE;
import com.skb.google.tv.isqms.ISQMSEnumData.eTV_RATE_MODE;
import com.skb.google.tv.isqms.ISQMSEnumData.eVIDEO_RATE_MODE;

public interface ISQMSListener {
	/**
	 * <pre>
	 * EVENT_ID : C03
	 * 내용 : STB 전체 최신 Upgrade
	 * 
	 * UPDATE_TYPE:S/X ,UPDATE_MODE:0/1 
	 * TYPE=S:SW버전 , X:XPG버전 , E:EPG버전(미사용)
	 * MODE=0:NORMAL, 1:FORCE
	 * </pre>
	 */
	public interface OnRecentAllUpgradeListener {
		public void onRecentAllUpgrade();
	}

	/**
	 * <pre>
	 * EVENT_ID : C04
	 * 내용 : STB 연령등급(시청제한나이) 조정
	 * 
	 * STB_AGE_LIMIT:14
	 * </pre>
	 */
	public interface OnAgeLimitChangeListener {
		public void onAgeLimitChange(eAGE_LIMIT_TYPE age_LIMIT_TYPE);
	}

	/**
	 * <pre>
	 * EVENT_ID : C05
	 * 내용 : STB 연속재생 여부 조정
	 * 
	 * STB_AUTONEXT:Y/N
	 * </pre>
	 */
	public interface OnAutoNextChangeListener {
		public void onAutoNextChange(boolean result);
	}

	/**
	 * <pre>
	 * EVENT_ID : C06
	 * 내용 : STB 광고 메타파일 재 Download
	 * </pre>
	 */
	public interface OnAdMetaFileDownloadListener {
		public void onAdMetaFileDownload();
	}

	/**
	 * <pre>
	 * EVENT_ID : C07
	 * 내용 : STB Reboot
	 * </pre>
	 */
	public interface OnRebootListener {
		public void onReboot();
	}

	/**
	 * <pre>
	 * EVENT_ID : C09
	 * 내용 : STB 해상도 변경
	 * 
	 * STB_SCR_RESOLUTION:1080i,
	 * STB_SCR_VIDEO:SCR
	 * STB_SCR_TV:16/9
	 * </pre>
	 */
	public interface OnResolutionChangeListener {
		public void onResolutionChange(eDISPLAY_MODE display_MODE, eVIDEO_RATE_MODE video_RATE_MODE, eTV_RATE_MODE rate_MODE);
	}

	/**
	 * <pre>
	 * EVENT_ID : C14
	 * 내용 : STB 비밀번호 재설정
	 * 
	 * C_PSWD_STB:1111
	 * </pre>
	 */
	public interface OnStbPasswordChangeListener {
		public void onStbPasswordChange(String stbPassword);
	}

	/**
	 * <pre>
	 * EVENT_ID : C15
	 * 내용 : 성인(자녀제한)비밀번호 재설정
	 * 
	 * C_PSWD_AGE:1111
	 * </pre>
	 */
	public interface OnChildLimitPasswordChangeListener {
		public void onChildLimitPasswordChange(String childLimitPassword);
	}

	/**
	 * <pre>
	 * EVENT_ID : C17
	 * 내용 : STB_AGE_TIME 설정(자녀시청 제한 시간 설정)
	 * 
	 * STB_AGE_TIME:19
	 * </pre>
	 */
	public interface OnChildLimitTimeChangeListener {
		public void onChildLimitTimeChange(String childLimitTime);
	}

	/**
	 * <pre>
	 * EVENT_ID : C18
	 * 내용 : STB_ADULT 설정(성인인증 사용여부 설정)
	 * 
	 * STB_ADULT:0
	 * </pre>
	 */
	public interface OnAdultAuthChangeListener {
		public void onAdultAuthChange(boolean result);
	}

	/**
	 * <pre>
	 * EVENT_ID : C94
	 * 내용 : LGS 정상 접근 확인 요청
	 * </pre>
	 */
	public interface OnLgsNormalAccessListener {
		public void onLgsNormalAccess();
	}

	/**
	 * <pre>
	 * EVENT_ID : C95
	 * 내용 : SCS 정상 접근 확인 요청
	 * </pre>
	 */
	public interface OnScsNormalAccessListener {
		public void onScsNormalAccess();
	}

	/**
	 * <pre>
	 * EVENT_ID : C96
	 * 내용 : IPTV시청 및 품질 상태 요청
	 * </pre>
	 */
	public interface OnIptvQualityStatusListener {
		public void onIptvQualityStatus();
	}

	/**
	 * <pre>
	 * EVENT_ID : C98
	 * 내용 : VOD시청 및 광고 재생 상태 요청
	 * </pre>
	 */
	public interface OnVodQualityStatusListener {
		public void onVodQualityStatus();
	}
}
