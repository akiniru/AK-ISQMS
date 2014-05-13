package com.skb.google.tv.isqms;

public class IsQMSListener {
	/**
	 * <pre>
	 * EVENT_ID : C03
	 * 내용 : STB 전체 최신 Upgrade
	 * </pre>
	 */
	public interface OnRecentAllUpgradeListener {
		public void onRecentAllUpgrade();
	}

	/**
	 * <pre>
	 * EVENT_ID : C04
	 * 내용 : STB 연령등급(시청제한나이) 조정
	 * </pre>
	 */
	public interface OnAgeLimitChangeListener {
		public void onAgeLimitChange();
	}

	/**
	 * <pre>
	 * EVENT_ID : C05
	 * 내용 : STB 연속재생 여부 조정
	 * </pre>
	 */
	public interface OnAutoNextChangeListener {
		public void onAutoNextChange();
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
	 * </pre>
	 */
	public interface OnResolutionChangeListener {
		public void onResolutionChange();
	}

	/**
	 * <pre>
	 * EVENT_ID : C14
	 * 내용 : STB 비밀번호 재설정
	 * </pre>
	 */
	public interface OnSTBPasswordChangeListener {
		public void onSTBPasswordChange();
	}

	/**
	 * <pre>
	 * EVENT_ID : C15
	 * 내용 : 성인(자녀제한)비밀번호 재설정
	 * </pre>
	 */
	public interface OnChildLimitPasswordChangeListener {
		public void onChildLimitPasswordChange();
	}

	/**
	 * <pre>
	 * EVENT_ID : C17
	 * 내용 : STB_AGE_TIME 설정(자녀시청 제한 시간 설정)
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
