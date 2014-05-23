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
	private ISQMSManager mIsQMSManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		LogUtil.initLogger(Log.DEBUG, false);

		mIsQMSManager = ISQMSManager.getInstance();

		// set Listener
		mIsQMSManager.setAutoNextChangeListener(mAutoNextChangeListener);
		mIsQMSManager.setRebootListener(mRebootListener);
		// mIsQMSManager.setStbPasswordChangeListener(mStbPasswordChangeListener);

		// set Common
		mIsQMSManager.setCommonStbVer("1");
		mIsQMSManager.setStatusConfStbAutoNext(true);

		// binding
		mIsQMSManager.bindingISQMSAgent(getApplicationContext());

		// << TEST CODE >>
		// send_event Hole Punching
		// mIsQMSManager.recv_event(ISQMSData.EVENT_C05, null);
		// mIsQMSManager.recv_event(ISQMSData.EVENT_C07, null);

		// Send_event
		Integer keyEventH10 = mIsQMSManager.openEvent(ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_H10);
		LogUtil.debug(LOGD, "keyEventH10_1 : " + keyEventH10);
		// 값 설정 : COMMON, CHECK_SVC, CHECK_VOD1, CHECK_VOD3
		mIsQMSManager.setCheckSvcVodAid("VOD 재생용 광고 Id");
		mIsQMSManager.setCheckSvcVodCid("VOD Content Id");
		mIsQMSManager.setCheckSvcPswdStb("SetTop 구매인증 비밀번호");
		mIsQMSManager.setCheckSvcPswdAge("성인(자녀제한) 비밀번호");

		mIsQMSManager.setCheckVod1VodScsIp(keyEventH10, "IPv4 Address - SCS Server IP");
		mIsQMSManager.setCheckVod1VodScsRt(keyEventH10, "SCS요청후 회신까지 소요시간");
		mIsQMSManager.setCheckVod1VodDownRt(keyEventH10, "Download 요청후 회신까지 소요시간");

		mIsQMSManager.setCheckVod3VodContentName(keyEventH10, "컨텐츠명  ( 부러진화살 )");
		mIsQMSManager.setCheckVod3VodContentUrl(keyEventH10, "컨텐츠 URL ( RTSP://*** )");

		mIsQMSManager.closeEvent(keyEventH10, ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_H10);

		Integer keyEventE09_1 = mIsQMSManager.openEvent(ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_E09);
		LogUtil.debug(LOGD, "keyEventE09_1 : " + keyEventE09_1);
		mIsQMSManager.closeEvent(keyEventE09_1, ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_E09);

		// Integer keyEventH10_2 = mIsQMSManager.openEvent(ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_H10);
		// LogUtil.debug(LOGD, "keyEventH10_2 : " + keyEventH10_2);
		// mIsQMSManager.closeEvent(keyEventH10_2, ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_H10);
		//
		// Integer keyEventE09_2 = mIsQMSManager.openEvent(ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_E09);
		// LogUtil.debug(LOGD, "keyEventE09_2 : " + keyEventE09_2);
		// mIsQMSManager.closeEvent(keyEventE09_2, ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_E09);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mIsQMSManager.unbindingISQMSAgent();
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
			mIsQMSManager.setStatusConfStbAutoNext(result);
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
	// mIsQMSManager.setStbVersion("2");
	// }
	// };
}
