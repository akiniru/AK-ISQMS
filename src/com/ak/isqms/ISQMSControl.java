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
import com.skb.google.tv.isqms.ISQMSListener.OnIptvQualityStatusListener;
import com.skb.google.tv.isqms.ISQMSListener.OnLgsNormalAccessListener;
import com.skb.google.tv.isqms.ISQMSListener.OnRebootListener;
import com.skb.google.tv.isqms.ISQMSListener.OnRecentAllUpgradeListener;
import com.skb.google.tv.isqms.ISQMSListener.OnResolutionChangeListener;
import com.skb.google.tv.isqms.ISQMSListener.OnScsNormalAccessListener;
import com.skb.google.tv.isqms.ISQMSListener.OnStbPasswordChangeListener;
import com.skb.google.tv.isqms.ISQMSListener.OnVodQualityStatusListener;
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
		mIsqmsManager.setSCSNormalAccessListener(mScsNormalAccessListener);
		mIsqmsManager.setLGSNormalAccessListener(mLgsNormalAccessListener);
		mIsqmsManager.setIptvQualityStatusListener(mIptvQualityStatusListener);
		mIsqmsManager.setVodQualityStatusListener(mVodQualityStatusListener);
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
		public void onRecentAllUpgrade() {
			// TODO Auto-generated method stub
			ISQMSUtil.info(LOGD, "OnRecentAllUpgradeListener.onRecentAllUpgrade() called.");

			Message msg = new Message();
			msg.what = ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C03_RECENT_ALL_UPGRADE;
			mIsqmsManager.sendResultHolePunchingEvent(msg);
		}
	};

	private ISQMSListener.OnAgeLimitChangeListener mAgeLimitChangeListener = new OnAgeLimitChangeListener() {
		@Override
		public void onAgeLimitChange(eAGE_LIMIT_TYPE age_LIMIT_TYPE) {
			ISQMSUtil.info(LOGD, "OnAgeLimitChangeListener.onAgeLimitChange() called. age_LIMIT_TYPE : " + age_LIMIT_TYPE);

			Message msg = new Message();
			msg.what = ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C04_AGE_LIMIT_CHANGE;
			mIsqmsManager.sendResultHolePunchingEvent(msg);
		}
	};

	private ISQMSListener.OnAutoNextChangeListener mAutoNextChangeListener = new OnAutoNextChangeListener() {
		@Override
		public void onAutoNextChange(boolean result) {
			ISQMSUtil.info(LOGD, "OnAutoNextChangeListener.onAutoNextChange() called. result : " + result);
			mIsqmsManager.setStatusConfStbAutoNext(result);

			Message msg = new Message();
			msg.what = ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C05_AUTO_NEXT_CHANGE;
			mIsqmsManager.sendResultHolePunchingEvent(msg);
		}
	};

	private ISQMSListener.OnAdMetaFileDownloadListener mAdMetaFileDownloadListener = new OnAdMetaFileDownloadListener() {
		@Override
		public void onAdMetaFileDownload() {
			// TODO Auto-generated method stub
			ISQMSUtil.info(LOGD, "OnAdMetaFileDownloadListener.onAdMetaFileDownload() called.");

			Message msg = new Message();
			msg.what = ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C06_ADMETA_FILE_DOWNLOAD;
			mIsqmsManager.sendResultHolePunchingEvent(msg);
		}
	};

	private ISQMSListener.OnRebootListener mRebootListener = new OnRebootListener() {
		@Override
		public void onReboot() {
			ISQMSUtil.info(LOGD, "OnRebootListener.onReboot() called.");
		}
	};

	private ISQMSListener.OnResolutionChangeListener mResolutionChangeListener = new OnResolutionChangeListener() {
		@Override
		public void onResolutionChange(eDISPLAY_MODE display_MODE, eVIDEO_RATE_MODE video_RATE_MODE, eTV_RATE_MODE tv_Rate_MODE) {
			ISQMSUtil.info(LOGD, "OnResolutionChangeListener.mResolutionChangeListener() called.");
			ISQMSUtil.debug(LOGD, "display_MODE : " + display_MODE + ", video_RATE_MODE : " + video_RATE_MODE + ", rate_MODE : " + tv_Rate_MODE);

			Message msg = new Message();
			msg.what = ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C09_RESOLUTION_CHANGE;
			mIsqmsManager.sendResultHolePunchingEvent(msg);
		}
	};

	private ISQMSListener.OnStbPasswordChangeListener mStbPasswordChangeListener = new OnStbPasswordChangeListener() {
		@Override
		public void onStbPasswordChange(String stbPassword) {
			// TODO Auto-generated method stub
			ISQMSUtil.info(LOGD, "OnStbPasswordChangeListener.onStbPasswordChange() called.");

			Message msg = new Message();
			msg.what = ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C14_STB_PASSWORD_CHANGE;
			mIsqmsManager.sendResultHolePunchingEvent(msg);
		}
	};

	private ISQMSListener.OnChildLimitPasswordChangeListener mChildLimitPasswordChangeListener = new OnChildLimitPasswordChangeListener() {
		@Override
		public void onChildLimitPasswordChange(String childLimitPassword) {
			// TODO Auto-generated method stub
			ISQMSUtil.info(LOGD, "OnChildLimitPasswordChangeListener.onChildLimitPasswordChange() called.");

			Message msg = new Message();
			msg.what = ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C15_CHILDLIMIT_PASSWORD_CHANGE;
			mIsqmsManager.sendResultHolePunchingEvent(msg);
		}
	};

	private ISQMSListener.OnChildLimitTimeChangeListener mChildLimitTimeChangeListener = new OnChildLimitTimeChangeListener() {
		@Override
		public void onChildLimitTimeChange(String childLimitTime) {
			ISQMSUtil.info(LOGD, "OnChildLimitTimeChangeListener.onChildLimitTimeChange() called. childLimitTime : " + childLimitTime);
			if (null != childLimitTime && 0 < childLimitTime.length()) {
				try {
					Integer.parseInt(childLimitTime);
					mIsqmsManager.setStatusConfStbAgeTime(childLimitTime);
				} catch (NumberFormatException numberFormatException) {
					numberFormatException.printStackTrace();
					ISQMSUtil.debug(LOGD, "OnChildLimitTimeChangeListener.onChildLimitTimeChange() NumberFormatException!");
				} catch (Exception e) {
					e.printStackTrace();
					ISQMSUtil.debug(LOGD, "OnChildLimitTimeChangeListener.onChildLimitTimeChange() Exception!");
				}
			}

			Message msg = new Message();
			msg.what = ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C17_CHILDLIMIT_TIME_CHANGE;
			mIsqmsManager.sendResultHolePunchingEvent(msg);
		}
	};

	private ISQMSListener.OnAdultAuthChangeListener mAdultAuthChangeListener = new OnAdultAuthChangeListener() {
		@Override
		public void onAdultAuthChange(boolean result) {
			// TODO Auto-generated method stub
			ISQMSUtil.info(LOGD, "OnAdultAuthChangeListener.onAdultAuthChange() called.");

			Message msg = new Message();
			msg.what = ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C18_ADULT_AUTH_CHANGE;
			mIsqmsManager.sendResultHolePunchingEvent(msg);
		}
	};

	private ISQMSListener.OnScsNormalAccessListener mScsNormalAccessListener = new OnScsNormalAccessListener() {
		@Override
		public void onScsNormalAccess() {
			// TODO Auto-generated method stub
			ISQMSUtil.info(LOGD, "OnScsNormalAccessListener.onScsNormalAccess() called.");

			Message msg = new Message();
			msg.what = ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C95_SCS_NORMAL_ACCESS;
			mIsqmsManager.sendResultHolePunchingEvent(msg);
		}
	};

	private ISQMSListener.OnLgsNormalAccessListener mLgsNormalAccessListener = new OnLgsNormalAccessListener() {
		@Override
		public void onLgsNormalAccess() {
			// TODO Auto-generated method stub
			ISQMSUtil.info(LOGD, "OnLgsNormalAccessListener.onLgsNormalAccess() called.");

			Message msg = new Message();
			msg.what = ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C94_LGS_NORMAL_ACCESS;
			mIsqmsManager.sendResultHolePunchingEvent(msg);
		}
	};

	private ISQMSListener.OnIptvQualityStatusListener mIptvQualityStatusListener = new OnIptvQualityStatusListener() {
		@Override
		public void onIptvQualityStatus() {
			// TODO Auto-generated method stub
			ISQMSUtil.info(LOGD, "OnIptvQualityStatusListener.onIptvQualityStatus() called.");

			Message msg = new Message();
			msg.what = ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C96_IPTV_QUALITY_STATUS;
			mIsqmsManager.sendResultHolePunchingEvent(msg);
		}
	};

	private ISQMSListener.OnVodQualityStatusListener mVodQualityStatusListener = new OnVodQualityStatusListener() {
		@Override
		public void onVodQualityStatus() {
			// TODO Auto-generated method stub
			ISQMSUtil.info(LOGD, "OnVodQualityStatusListener.onVodQualityStatus() called.");

			Message msg = new Message();
			msg.what = ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C98_VOD_QUALITY_STATUS;
			mIsqmsManager.sendResultHolePunchingEvent(msg);
		}
	};
}
