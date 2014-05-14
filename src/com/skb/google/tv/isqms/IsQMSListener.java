package com.skb.google.tv.isqms;

import com.skb.google.tv.isqms.IsQMSData.eDISPLAY_MODE;
import com.skb.google.tv.isqms.IsQMSData.eRATE_MODE;

public class IsQMSListener {
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
	public interface OnAgeLimitChangeListener { // STBAPIManager.getInstance().setAgeLimit(STBAPIManager.INFO_VALUE_07);
		public void onAgeLimitChange(IsQMSData.eAGE_LIMIT_TYPE age_LIMIT_TYPE);
	}

	/**
	 * <pre>
	 * EVENT_ID : C05
	 * 내용 : STB 연속재생 여부 조정
	 * 
	 * STB_AUTONEXT:Y/N
	 * </pre>
	 */
	public interface OnAutoNextChangeListener { // STBAPIManager.getInstance().setConsecutivePlay(STBAPIManager.RESULT_ENABLE);
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
		public void onResolutionChange(eDISPLAY_MODE display_MODE, eRATE_MODE rate_MODE);
	}

	/**
	 * <pre>
	 * EVENT_ID : C14
	 * 내용 : STB 비밀번호 재설정
	 * 
	 * C_PSWD_STB:1111
	 * </pre>
	 */
	public interface OnSTBPasswordChangeListener {
		public void onSTBPasswordChange(String password);
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
	 * STB_AGE_LIMIT:19
	 * </pre>
	 */
	public interface OnChildLimitTimeChangeListener {
		public void onChildLimitTimeChange();
	}

	/**
	 * <pre>
	 * EVENT_ID : C18
	 * 내용 : STB_ADULT 설정(성인인증 사용여부 설정)
	 * </pre>
	 */
	public interface OnAdultAuthChangeListener {
		public void onAdultAuthChange();
	}

	/**
	 * <pre>
	 * EVENT_ID : C95
	 * 내용 : SCS 정상 접근 확인 요청
	 * </pre>
	 */
	public interface OnSCSNormalAccessListener {
		public void onSCSNormalAccess();
	}

	/**
	 * <pre>
	 * EVENT_ID : C94
	 * 내용 : LGS 정상 접근 확인 요청
	 * </pre>
	 */
	public interface OnLGSNormalAccessListener {
		public void onLGSNormalAccess();
	}
}
