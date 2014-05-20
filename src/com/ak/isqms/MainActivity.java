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
	private static final String LOGD = "UIApp";
	private ISQMSManager mIsQMSManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		LogUtil.initLogger(Log.DEBUG, false);

		mIsQMSManager = ISQMSManager.getInstance();

		// set Common
		mIsQMSManager.setAutoNextChangeListener(mAutoNextChangeListener);
		mIsQMSManager.setRebootListener(mRebootListener);
		// mIsQMSManager.setStbPasswordChangeListener(mStbPasswordChangeListener);
		mIsQMSManager.setStbVersion("1");
		mIsQMSManager.setAutoNext(true);

		// binding
		mIsQMSManager.bindingISQMSAgent(getApplicationContext());

		// send_event
		mIsQMSManager.recv_event(ISQMSData.EVENT_C05, null);
		mIsQMSManager.recv_event(ISQMSData.EVENT_C07, null);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mIsQMSManager.unbindingISQMSAgent();
	}

	ISQMSListener.OnRebootListener mRebootListener = new OnRebootListener() {
		@Override
		public void onReboot() {
			LogUtil.info(LOGD, "OnRebootListener.onReboot() called.");
		}
	};

	ISQMSListener.OnAutoNextChangeListener mAutoNextChangeListener = new OnAutoNextChangeListener() {
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
