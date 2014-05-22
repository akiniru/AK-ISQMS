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
		mIsQMSManager.setStbVersion("1");
		mIsQMSManager.setAutoNext(true);

		// binding
		mIsQMSManager.bindingISQMSAgent(getApplicationContext());

		// << TEST CODE >>
		// send_event Hole Punching
		// mIsQMSManager.recv_event(ISQMSData.EVENT_C05, null);
		// mIsQMSManager.recv_event(ISQMSData.EVENT_C07, null);

		// Send_event

		Integer keyEventH10_1 = mIsQMSManager.openEvent(ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_H10);
		LogUtil.debug(LOGD, "keyEventH10_1 : " + keyEventH10_1);
		// mIsQMSManager.closeEvent(keyEventH10_1, ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_H10);

		Integer keyEventE09_1 = mIsQMSManager.openEvent(ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_E09);
		LogUtil.debug(LOGD, "keyEventE09_1 : " + keyEventE09_1);
		mIsQMSManager.closeEvent(keyEventE09_1, ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_E09);

		Integer keyEventH10_2 = mIsQMSManager.openEvent(ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_H10);
		LogUtil.debug(LOGD, "keyEventH10_2 : " + keyEventH10_2);
		// mIsQMSManager.closeEvent(keyEventH10_2, ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_H10);

		Integer keyEventE09_2 = mIsQMSManager.openEvent(ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_E09);
		LogUtil.debug(LOGD, "keyEventE09_2 : " + keyEventE09_2);
		mIsQMSManager.closeEvent(keyEventE09_2, ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_E09);

		mIsQMSManager.closeEvent(keyEventH10_1, ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_H10);
		// mIsQMSManager.closeEvent(keyEventE09_1, ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_E09);
		mIsQMSManager.closeEvent(keyEventH10_2, ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_H10);

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
			mIsQMSManager.setAutoNext(result);
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
