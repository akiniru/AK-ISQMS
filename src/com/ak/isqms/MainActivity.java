package com.ak.isqms;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.skb.google.tv.common.util.LogUtil;
import com.skb.google.tv.isqms.ISQMSData;
import com.skb.google.tv.isqms.ISQMSListener;
import com.skb.google.tv.isqms.ISQMSListener.OnAutoNextChangeListener;
import com.skb.google.tv.isqms.ISQMSListener.OnRebootListener;
import com.skb.google.tv.isqms.ISQMSManager;

public class MainActivity extends Activity {
	private static final String LOGD = "ISQMS_UIApp";
	private ISQMSManager mIsqmsManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		LogUtil.initLogger(Log.DEBUG, false);

		ISQMSControl.getInstance(MainActivity.this);
		mIsqmsManager = ISQMSManager.getInstance();
		mIsqmsManager.bindingISQMSAgent(getApplicationContext());

		// set Listener
		mIsqmsManager.setAutoNextChangeListener(mAutoNextChangeListener);
		mIsqmsManager.setRebootListener(mRebootListener);
		// mIsqmsManager.setStbPasswordChangeListener(mStbPasswordChangeListener);

		// set Common
		mIsqmsManager.setCommonStbVer("1");
		mIsqmsManager.setStatusConfStbAutoNext(true);

		// binding

		// << TEST CODE >>
		// send_event Hole Punching
		// mIsqmsManager.recv_event(ISQMSData.EVENT_C05, null);
		// mIsqmsManager.recv_event(ISQMSData.EVENT_C07, null);

		// Send_event
		Integer keyEventH10 = mIsqmsManager.openEvent(ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_H10);
		LogUtil.debug(LOGD, "keyEventH10_1 : " + keyEventH10);
		// 값 설정 : COMMON, CHECK_SVC, CHECK_VOD1, CHECK_VOD3
		mIsqmsManager.setCheckSvcVodAid("VOD 재생용 광고 Id");
		mIsqmsManager.setCheckSvcVodCid("VOD Content Id");
		mIsqmsManager.setCheckSvcPswdStb("SetTop 구매인증 비밀번호");
		mIsqmsManager.setCheckSvcPswdAge("성인(자녀제한) 비밀번호");

		mIsqmsManager.setCheckVod1VodScsIp(keyEventH10, "IPv4 Address - SCS Server IP");
		mIsqmsManager.setCheckVod1VodScsRt(keyEventH10, "SCS요청후 회신까지 소요시간");
		mIsqmsManager.setCheckVod1VodDownRt(keyEventH10, "Download 요청후 회신까지 소요시간");

		mIsqmsManager.setCheckVod3VodContentName(keyEventH10, "컨텐츠명  ( 부러진화살 )");
		mIsqmsManager.setCheckVod3VodContentUrl(keyEventH10, "컨텐츠 URL ( RTSP://*** )");

		mIsqmsManager.closeEvent(keyEventH10, ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_H10);

		Integer keyEventE09_1 = mIsqmsManager.openEvent(ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_E09);
		LogUtil.debug(LOGD, "keyEventE09_1 : " + keyEventE09_1);
		mIsqmsManager.closeEvent(keyEventE09_1, ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_E09);

		// Integer keyEventH10_2 = mIsqmsManager.openEvent(ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_H10);
		// LogUtil.debug(LOGD, "keyEventH10_2 : " + keyEventH10_2);
		// mIsqmsManager.closeEvent(keyEventH10_2, ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_H10);
		//
		// Integer keyEventE09_2 = mIsqmsManager.openEvent(ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_E09);
		// LogUtil.debug(LOGD, "keyEventE09_2 : " + keyEventE09_2);
		// mIsqmsManager.closeEvent(keyEventE09_2, ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_E09);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mIsqmsManager.unbindingISQMSAgent();
	}

	private ISQMSListener.OnRebootListener mRebootListener = new OnRebootListener() {
		@Override
		public void onReboot() {
			LogUtil.info(LOGD, "OnRebootListener.onReboot() called.");
		}
	};

	private ISQMSListener.OnAutoNextChangeListener mAutoNextChangeListener = new OnAutoNextChangeListener() {
		@Override
		public void onAutoNextChange(boolean result) {
			LogUtil.info(LOGD, "OnAutoNextChangeListener.onAutoNextChange() called.");
			mIsqmsManager.setStatusConfStbAutoNext(result);
			try {
				Thread.sleep(5 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	};

	// ISQMSListener.OnStbPasswordChangeListener mStbPasswordChangeListener = new OnStbPasswordChangeListener() {
	// @Override
	// public void onStbPasswordChange(String stbPassword) {
	// mIsqmsManager.setStbVersion("2");
	// }
	// };
}
