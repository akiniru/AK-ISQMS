package com.ak.isqms;

import android.os.Message;

import com.skb.google.tv.isqms.ISQMSData;
import com.skb.google.tv.isqms.ISQMSEnumData.eAGE_LIMIT_TYPE;
import com.skb.google.tv.isqms.ISQMSEnumData.eDISPLAY_MODE;
import com.skb.google.tv.isqms.ISQMSEnumData.eTV_RATE_MODE;
import com.skb.google.tv.isqms.ISQMSEnumData.eVIDEO_RATE_MODE;
import com.skb.google.tv.isqms.ISQMSListener;
import com.skb.google.tv.isqms.ISQMSListener.OnAdMetaFileDownloadListener;
import com.skb.google.tv.isqms.ISQMSListener.OnAdultAuthChangeListener;
import com.skb.google.tv.isqms.ISQMSListener.OnAgeLimitChangeListener;
import com.skb.google.tv.isqms.ISQMSListener.OnAutoNextChangeListener;
import com.skb.google.tv.isqms.ISQMSListener.OnChildLimitPasswordChangeListener;
import com.skb.google.tv.isqms.ISQMSListener.OnChildLimitTimeChangeListener;
import com.skb.google.tv.isqms.ISQMSListener.OnRebootListener;
import com.skb.google.tv.isqms.ISQMSListener.OnRecentAllUpgradeListener;
import com.skb.google.tv.isqms.ISQMSListener.OnResolutionChangeListener;
import com.skb.google.tv.isqms.ISQMSListener.OnStbPasswordChangeListener;
import com.skb.google.tv.isqms.ISQMSManager;
import com.skb.google.tv.isqms.ISQMSUtil;

public class ISQMSControl {
	private static final String LOGD = ISQMSControl.class.getSimpleName();

	private static ISQMSControl mISQMSControl;
	private MainActivity mMainActivity;
	private ISQMSManager mIsqmsManager;

	private ISQMSControl(MainActivity mainActivity) {
		this.mMainActivity = mainActivity;

		mIsqmsManager = ISQMSManager.getInstance();
		mIsqmsManager.setRecentAllUpgradeListener(mRecentAllUpgradeListener);
		mIsqmsManager.setAgeLimitChangeListener(mAgeLimitChangeListener);
		mIsqmsManager.setAutoNextChangeListener(mAutoNextChangeListener);
		mIsqmsManager.setAdMetaFileDownloadListener(mAdMetaFileDownloadListener);
		mIsqmsManager.setRebootListener(mRebootListener);
		mIsqmsManager.setResolutionChangeListener(mResolutionChangeListener);
		mIsqmsManager.setStbPasswordChangeListener(mStbPasswordChangeListener);
		mIsqmsManager.setChildLimitPasswordChangeListener(mChildLimitPasswordChangeListener);
		mIsqmsManager.setChildLimitTimeChangeListener(mChildLimitTimeChangeListener);
		mIsqmsManager.setAdultAuthChangeListener(mAdultAuthChangeListener);
	}

	public static ISQMSControl getInstance(MainActivity mainActivity) {
		if (null == mISQMSControl) {
			mISQMSControl = new ISQMSControl(mainActivity);
		}

		return mISQMSControl;
	}

	// =========================================================================
	// < create IsQMS Listener
	// =========================================================================
	private ISQMSListener.OnRecentAllUpgradeListener mRecentAllUpgradeListener = new OnRecentAllUpgradeListener() {
		@Override
		public void onRecentAllUpgrade(final String ctrlSeq) {
			ISQMSUtil.info(LOGD, "onRecentAllUpgrade() called.");

			Message msg = new Message();
			msg.what = ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C03_RECENT_ALL_UPGRADE;
			msg.obj = ctrlSeq;
			mIsqmsManager.sendResultHolePunchingEvent(msg);
		}
	};

	private ISQMSListener.OnAgeLimitChangeListener mAgeLimitChangeListener = new OnAgeLimitChangeListener() {
		@Override
		public void onAgeLimitChange(final eAGE_LIMIT_TYPE age_LIMIT_TYPE, final String ctrlSeq) {
			ISQMSUtil.info(LOGD, "onAgeLimitChange() called. age_LIMIT_TYPE : " + age_LIMIT_TYPE);

			Message msg = new Message();
			msg.what = ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C04_AGE_LIMIT_CHANGE;
			msg.obj = ctrlSeq;
			mIsqmsManager.sendResultHolePunchingEvent(msg);
		}
	};

	private ISQMSListener.OnAutoNextChangeListener mAutoNextChangeListener = new OnAutoNextChangeListener() {
		@Override
		public void onAutoNextChange(final boolean result, final String ctrlSeq) {
			ISQMSUtil.info(LOGD, "onAutoNextChange() called. result : " + result);

			Message msg = new Message();
			msg.what = ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C05_AUTO_NEXT_CHANGE;
			msg.obj = ctrlSeq;
			mIsqmsManager.sendResultHolePunchingEvent(msg);
		}
	};

	private ISQMSListener.OnAdMetaFileDownloadListener mAdMetaFileDownloadListener = new OnAdMetaFileDownloadListener() {
		@Override
		public void onAdMetaFileDownload(final String ctrlSeq) {
			ISQMSUtil.info(LOGD, "onAdMetaFileDownload() called.");

			Message msg = new Message();
			msg.what = ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C06_ADMETA_FILE_DOWNLOAD;
			msg.obj = ctrlSeq;
			mIsqmsManager.sendResultHolePunchingEvent(msg);
		}
	};

	private ISQMSListener.OnRebootListener mRebootListener = new OnRebootListener() {
		@Override
		public void onReboot(final String ctrlSeq) {
			ISQMSUtil.info(LOGD, "onReboot() called.");
		}
	};

	private ISQMSListener.OnResolutionChangeListener mResolutionChangeListener = new OnResolutionChangeListener() {
		@Override
		public void onResolutionChange(final eDISPLAY_MODE display_MODE, final eVIDEO_RATE_MODE video_RATE_MODE, final eTV_RATE_MODE tv_Rate_MODE, final String ctrlSeq) {
			ISQMSUtil.info(LOGD, "mResolutionChangeListener() called.");

			Message msg = new Message();
			msg.what = ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C09_RESOLUTION_CHANGE;
			msg.obj = ctrlSeq;
			mIsqmsManager.sendResultHolePunchingEvent(msg);
		}
	};

	private ISQMSListener.OnStbPasswordChangeListener mStbPasswordChangeListener = new OnStbPasswordChangeListener() {
		@Override
		public void onStbPasswordChange(final String stbPassword, final String ctrlSeq) {
			ISQMSUtil.info(LOGD, "onStbPasswordChange() called.");

			Message msg = new Message();
			msg.what = ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C14_STB_PASSWORD_CHANGE;
			msg.obj = ctrlSeq;
			mIsqmsManager.sendResultHolePunchingEvent(msg);
		}
	};

	private ISQMSListener.OnChildLimitPasswordChangeListener mChildLimitPasswordChangeListener = new OnChildLimitPasswordChangeListener() {
		@Override
		public void onChildLimitPasswordChange(final String childLimitPassword, final String ctrlSeq) {
			ISQMSUtil.info(LOGD, "onChildLimitPasswordChange() called.");

			Message msg = new Message();
			msg.what = ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C15_CHILDLIMIT_PASSWORD_CHANGE;
			msg.obj = ctrlSeq;
			mIsqmsManager.sendResultHolePunchingEvent(msg);
		}
	};

	private ISQMSListener.OnChildLimitTimeChangeListener mChildLimitTimeChangeListener = new OnChildLimitTimeChangeListener() {
		@Override
		public void onChildLimitTimeChange(final String childLimitTime, final String ctrlSeq) {
			ISQMSUtil.info(LOGD, "onChildLimitTimeChange() called. childLimitTime : " + childLimitTime);

			Message msg = new Message();
			msg.what = ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C17_CHILDLIMIT_TIME_CHANGE;
			msg.obj = ctrlSeq;
			mIsqmsManager.sendResultHolePunchingEvent(msg);
		}
	};

	private ISQMSListener.OnAdultAuthChangeListener mAdultAuthChangeListener = new OnAdultAuthChangeListener() {
		@Override
		public void onAdultAuthChange(final boolean result, final String ctrlSeq) {
			ISQMSUtil.info(LOGD, "onAdultAuthChange() called.");

			Message msg = new Message();
			msg.what = ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C18_ADULT_AUTH_CHANGE;
			msg.obj = ctrlSeq;
			mIsqmsManager.sendResultHolePunchingEvent(msg);
		}
	};
}
