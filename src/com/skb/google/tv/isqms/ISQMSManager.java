package com.skb.google.tv.isqms;

import java.util.ArrayList;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.SparseArray;

import com.skb.google.tv.isqms.ISQMSEnumData.eAGE_LIMIT_TYPE;
import com.skb.google.tv.isqms.ISQMSEnumData.eDISPLAY_MODE;
import com.skb.google.tv.isqms.ISQMSEnumData.eIPTV_CH_MODE;
import com.skb.google.tv.isqms.ISQMSEnumData.eIPTV_ERROR_CODE;
import com.skb.google.tv.isqms.ISQMSEnumData.eLGS_ECODE;
import com.skb.google.tv.isqms.ISQMSEnumData.eSCS_ECODE;
import com.skb.google.tv.isqms.ISQMSEnumData.eTV_RATE_MODE;
import com.skb.google.tv.isqms.ISQMSEnumData.eUPG_UPGRADE;
import com.skb.google.tv.isqms.ISQMSEnumData.eVIDEO_RATE_MODE;
import com.skb.google.tv.isqms.ISQMSEnumData.eVOD4_VOD_ERROR;
import com.skb.google.tv.isqms.ISQMSEnumData.eWSCS_ECODE;
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
import com.skb.google.tv.isqms.check.ISQMSCheckERR1;
import com.skb.google.tv.isqms.check.ISQMSCheckERRORCODE;
import com.skb.google.tv.isqms.check.ISQMSCheckIPTV;
import com.skb.google.tv.isqms.check.ISQMSCheckLGS;
import com.skb.google.tv.isqms.check.ISQMSCheckNET;
import com.skb.google.tv.isqms.check.ISQMSCheckSCS;
import com.skb.google.tv.isqms.check.ISQMSCheckSVC;
import com.skb.google.tv.isqms.check.ISQMSCheckUPG;
import com.skb.google.tv.isqms.check.ISQMSCheckVOD;
import com.skb.google.tv.isqms.check.ISQMSCheckWSCS;
import com.skb.google.tv.isqms.common.ISQMSCommon;
import com.skb.google.tv.isqms.status.ISQMSStatusBBRATE;
import com.skb.google.tv.isqms.status.ISQMSStatusCONF;
import com.skb.google.tv.isqms.status.ISQMSStatusNET;
import com.skb.google.tv.isqms.status.ISQMSStatusXPG2;
import com.skb.isqms.IAgentServiceToUIApp;
import com.skb.isqms.IUIAppToAgentService;

public class ISQMSManager {
	private static final String LOGD = ISQMSManager.class.getSimpleName();

	private static ISQMSManager mISQMSManager;

	private Object mLock;
	private ArrayList<ISQMSReceiveEvent> mReceiveEventList;
	private ReceiveEventManager mReceiveEventManager;
	private AgentSendManager mAgentSendManager;

	private Context mContext;
	private IUIAppToAgentService mBinder;

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

	private int mEventKey = 0;

	/** COMMON DATA */
	protected ISQMSCommon mISQMSCommon;

	/** CURRENT STATUS DATA */
	protected ISQMSStatusNET mISQMSStatusNET;
	protected ISQMSStatusCONF mISQMSStatusCONF;
	protected ISQMSStatusXPG2 mISQMSStatusXPG2;
	protected ISQMSStatusBBRATE mISQMSStatusBBRATE;

	/** CHECK RESULT DATA */
	protected ISQMSCheckUPG mISQMSCheckUPG;
	protected ISQMSCheckSVC mISQMSCheckSVC;
	protected ISQMSCheckSCS mISQMSCheckSCS;
	protected ISQMSCheckLGS mISQMSCheckLGS;
	protected ISQMSCheckERR1 mISQMSCheckERR1;
	protected ISQMSCheckNET mISQMSCheckNET;
	protected ISQMSCheckWSCS mISQMSCheckWSCS;
	protected ISQMSCheckERRORCODE mISQMSCheckERRORCODE;

	protected SparseArray<ISQMSCheckVOD> mISQMSCheckVODList;
	protected SparseArray<ISQMSCheckIPTV> mISQMSCheckIPTVList;

	private ISQMSManager() {
		ISQMSUtil.info(LOGD, "ISQMSManager() create.");
		mLock = new Object();
		mReceiveEventList = new ArrayList<ISQMSReceiveEvent>();
		mReceiveEventManager = new ReceiveEventManager();
		mReceiveEventManager.start();

		mAgentSendManager = new AgentSendManager(mAgentSendHandler);
		mAgentSendManager.start();

		// Common data init
		mISQMSCommon = new ISQMSCommon();

		// Current Status data init
		mISQMSStatusNET = new ISQMSStatusNET();
		mISQMSStatusCONF = new ISQMSStatusCONF();
		mISQMSStatusXPG2 = new ISQMSStatusXPG2();
		mISQMSStatusBBRATE = new ISQMSStatusBBRATE();

		// Check Result data init
		mISQMSCheckUPG = new ISQMSCheckUPG();
		mISQMSCheckSVC = new ISQMSCheckSVC();
		// mISQMSCheckVOD = new ISQMSCheckVOD();
		// mISQMSCheckIPTV = new ISQMSCheckIPTV();
		mISQMSCheckSCS = new ISQMSCheckSCS();
		mISQMSCheckLGS = new ISQMSCheckLGS();
		mISQMSCheckERR1 = new ISQMSCheckERR1();
		mISQMSCheckNET = new ISQMSCheckNET();
		mISQMSCheckWSCS = new ISQMSCheckWSCS();
		mISQMSCheckERRORCODE = new ISQMSCheckERRORCODE();

		// List init
		mISQMSCheckVODList = new SparseArray<ISQMSCheckVOD>(2);
		mISQMSCheckIPTVList = new SparseArray<ISQMSCheckIPTV>(2);

		mBinder = null;
	}

	public static ISQMSManager getInstance() {
		if (null == mISQMSManager) {
			mISQMSManager = new ISQMSManager();
		}

		return mISQMSManager;
	}

	private IAgentServiceToUIApp mCallback = new IAgentServiceToUIApp.Stub() {
		/**
		 * @biref : receive event from agent
		 * @param event_id
		 *            {String} : event id (ex: C01, C02)
		 * @param data
		 *            {String} :
		 */
		@Override
		public void onRecvEvent(String event_id, String data) throws RemoteException {
			ISQMSUtil.debug(IAgentServiceToUIApp.class.getSimpleName(), "onRecvEvent() event_id = " + event_id + ", data = " + data);
			recv_event(event_id, data);
		}
	};

	private ServiceConnection mConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			ISQMSUtil.info(LOGD, "onServiceConnected() Service Binding!");
			mBinder = IUIAppToAgentService.Stub.asInterface(service);
			onBindedISQMSAgent();

			try {
				mBinder.registerAgentCallback(mCallback);
				// mBinder를 통해 서비스의 함수에 접근이 가능!
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			ISQMSUtil.info(LOGD, "onServiceDisconnected() Service UnBinding!");
			mBinder = null;
		}
	};

	/**
	 * called when completed binding.
	 * 
	 * @param callback
	 */
	private void bindingISQMSAgent() {
		if (false == ISQMSData.ACTION_ISQMS) {
			return;
		}
		bindingISQMSAgent(mContext);
	}

	public void bindingISQMSAgent(Context context) {
		ISQMSUtil.info(LOGD, "bindingISQMSAgent() called");
		mContext = context;

		if (false == ISQMSData.ACTION_ISQMS) {
			return;
		}
		Intent intent = new Intent("com.skb.isqms.AgentService"); // service Name
		mContext.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
	}

	public void unbindingISQMSAgent() {
		ISQMSUtil.info(LOGD, "unbindingISQMSAgent() called");

		if (mBinder != null) {
			mContext.unbindService(mConnection);
			mBinder = null;
			ISQMSUtil.debug(LOGD, "unbindingISQMSAgent() Service UnBinding!");
		}
	}

	private void onBindedISQMSAgent() {
		ISQMSUtil.info(LOGD, "onBindedISQMSAgent() called.");
		int nRet = agent_start();
		ISQMSUtil.debug(LOGD, "onBindedISQMSAgent() agent_start ret = " + Integer.toString(nRet));

		if (nRet == ISQMSData.ISQMS_SUCCESS) {
			ISQMSUtil.debug(LOGD, "onBindedISQMSAgent() send_data start.");
			Handler receiveHandler = mAgentSendManager.getManagerHandler();
			if (receiveHandler != null) {
				Message msg = receiveHandler.obtainMessage();
				msg.what = ISQMSData.MESSAGE_REQUEST_AGENT_BINDING;
				receiveHandler.sendMessage(msg);
			}
		}
	}

	private int agent_start() {
		ISQMSUtil.info(LOGD, "agent_start() called");
		if (mBinder != null) {
			try {
				int nRet = -1;
				if ((nRet = mBinder.start_agent()) >= 0) {
					// no handle
				}
				ISQMSUtil.debug(LOGD, "agent_start() start_agent result : " + nRet);
				return nRet;
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		} else {
			bindingISQMSAgent();
		}

		return 0;
	}

	private int agent_send_data(int category_id, int sub_category_id, String data) {
		ISQMSUtil.info(LOGD, "agent_send_data() called.");
		ISQMSUtil.debug(LOGD, "agent_send_data() category_id : " + category_id + ", sub_category_id : " + sub_category_id + ", data : " + data);
		if (mBinder != null) {
			try {
				int nRet;
				if ((nRet = mBinder.send_data(category_id, sub_category_id, data)) >= 0) {
					// no handle
				}
				return nRet;
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		} else {
			bindingISQMSAgent();
		}
		return 0;
	}

	private int agent_send_event(String event_id, String status) {
		ISQMSUtil.info(LOGD, "agent_send_event() called.");
		ISQMSUtil.debug(LOGD, "agent_send_event() event_id : " + event_id + ", status : " + status);
		if (mBinder != null) {
			try {
				int nRet;
				if ((nRet = mBinder.send_event(event_id, status)) >= 0) {
					// no handle
				}
				return nRet;
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		} else {
			bindingISQMSAgent();
		}
		return 0;
	}

	public Handler mLockWakeHandler = new Handler(new Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			ISQMSUtil.info(LOGD, "mLockWakeHandler.handleMessage() called.");
			synchronized (mLock) {
				mLock.notifyAll();
			}
			return false;
		}
	});

	private Handler mAgentSendHandler = new Handler(new Handler.Callback() {
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
				case ISQMSData.MESSAGE_RESPONSE_AGENT_OK:
					ISQMSUtil.info(LOGD, "mAgentSendHandler.handleMessage() called. MESSAGE_RESPONSE_AGENT_OK");
					break;
				case ISQMSData.MESSAGE_RESPONSE_AGENT_ERROR:
					ISQMSUtil.info(LOGD, "mAgentSendHandler.handleMessage() called. MESSAGE_RESPONSE_AGENT_ERROR");
					break;

				default:
					ISQMSUtil.info(LOGD, "mAgentSendHandler.handleMessage() called. default");
					break;
			}
			return false;
		};
	});

	private class AgentSendManager extends Thread {
		private Handler mRecvHandler = null;
		private Handler mSendHandler = null;
		private boolean mIsWait = false;

		public AgentSendManager(Handler handler) {
			this.mSendHandler = handler;
		}

		public Handler getManagerHandler() {
			try {
				if (mRecvHandler == null) {
					synchronized (this) {
						mIsWait = true;
						wait(ISQMSData.THREAD_WAIT_MILLE_SECONDS);
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			return mRecvHandler;
		}

		@Override
		public void destroy() {
			if (mRecvHandler != null) {
				mRecvHandler.sendMessage(mRecvHandler.obtainMessage(ISQMSData.MESSAGE_REQUEST_AGENT_THREAD_DESTROY, null));
			}
		}

		@Override
		public void run() {
			// < You have to prepare the looper before creating the handler.
			Looper.prepare();

			// < Create the child handler on the child thread so it is bound to the child thread's message queue.
			mRecvHandler = new Handler(new Handler.Callback() {
				@Override
				public boolean handleMessage(Message msg) {
					try {
						// < Mocking an expensive operation. It takes 100 milliseconds to complete.
						sleep(100);

						Integer key = null;
						if (null != msg.obj && (true == msg.obj instanceof Integer)) {
							key = (Integer) msg.obj;
						}

						switch (msg.what) {
							case ISQMSData.MESSAGE_REQUEST_AGENT_THREAD_DESTROY:
								Looper.myLooper().quit();
								break;

							case ISQMSData.MESSAGE_REQUEST_AGENT_BINDING:
								agent_send_data(ISQMSData.COMMON, 0, ISQMSDataBuilder.getDataCommon());
								agent_send_data(ISQMSData.CURRENT_STATUS, ISQMSData.STATUS_NET, ISQMSDataBuilder.getDataStatusNET());
								agent_send_data(ISQMSData.CURRENT_STATUS, ISQMSData.STATUS_CONF, ISQMSDataBuilder.getDataStatusCONF());
								agent_send_data(ISQMSData.CURRENT_STATUS, ISQMSData.STATUS_XPG_2, ISQMSDataBuilder.getDataStatusXPG2());
								agent_send_data(ISQMSData.CURRENT_STATUS, ISQMSData.STATUS_BBRATE, ISQMSDataBuilder.getDataStatusBBRATE());
								sendMessage(ISQMSData.MESSAGE_RESPONSE_AGENT_OK, null);
								break;

							case ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_H01:
								// 행동 : 전원ON, Reset시작 등 주요 핵심동작시
								// 메시지 내용 : COMMON, STATUS_ALL
								agent_send_event(ISQMSData.EVENT_H01, ISQMSData.ISQMS_STRING_OPEN);
								agent_send_data(ISQMSData.COMMON, 0, ISQMSDataBuilder.getDataCommon());
								agent_send_data(ISQMSData.CURRENT_STATUS, ISQMSData.STATUS_NET, ISQMSDataBuilder.getDataStatusNET());
								agent_send_data(ISQMSData.CURRENT_STATUS, ISQMSData.STATUS_CONF, ISQMSDataBuilder.getDataStatusCONF());
								agent_send_data(ISQMSData.CURRENT_STATUS, ISQMSData.STATUS_XPG_2, ISQMSDataBuilder.getDataStatusXPG2());
								agent_send_data(ISQMSData.CURRENT_STATUS, ISQMSData.STATUS_BBRATE, ISQMSDataBuilder.getDataStatusBBRATE());
								agent_send_event(ISQMSData.EVENT_H01, ISQMSData.ISQMS_STRING_CLOSE);
								sendMessage(ISQMSData.MESSAGE_RESPONSE_AGENT_OK, null);
								break;

							case ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_H02:
								// 행동 : Network 설정변경시
								// 메시지 내용 : COMMON, STATUS_NET
								agent_send_event(ISQMSData.EVENT_H02, ISQMSData.ISQMS_STRING_OPEN);
								agent_send_data(ISQMSData.COMMON, 0, ISQMSDataBuilder.getDataCommon());
								agent_send_data(ISQMSData.CURRENT_STATUS, ISQMSData.STATUS_NET, ISQMSDataBuilder.getDataStatusNET());
								agent_send_event(ISQMSData.EVENT_H02, ISQMSData.ISQMS_STRING_CLOSE);
								sendMessage(ISQMSData.MESSAGE_RESPONSE_AGENT_OK, null);
								break;

							case ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_H06:
								// 행동 : SW Upgrade 진행 중
								// 메시지 내용 : COMMON, C_SW_UPGRADE
								agent_send_event(ISQMSData.EVENT_H06, ISQMSData.ISQMS_STRING_OPEN);
								agent_send_data(ISQMSData.COMMON, 0, ISQMSDataBuilder.getDataCommon());
								agent_send_data(ISQMSData.CHECK_RESULT, ISQMSData.CHECK_UPG_C_SW, ISQMSDataBuilder.getDataCheckSwUpgrade());
								agent_send_event(ISQMSData.EVENT_H06, ISQMSData.ISQMS_STRING_CLOSE);
								sendMessage(ISQMSData.MESSAGE_RESPONSE_AGENT_OK, null);
								break;

							case ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_H08:
								// 행동 : Channel Upgrade 진행 중
								// 메시지 내용 : COMMON, C_CH_UPGRADE
								agent_send_event(ISQMSData.EVENT_H08, ISQMSData.ISQMS_STRING_OPEN);
								agent_send_data(ISQMSData.COMMON, 0, ISQMSDataBuilder.getDataCommon());
								agent_send_data(ISQMSData.CHECK_RESULT, ISQMSData.CHECK_UPG_C_CH, ISQMSDataBuilder.getDataCheckChUpgrade());
								agent_send_event(ISQMSData.EVENT_H08, ISQMSData.ISQMS_STRING_CLOSE);
								sendMessage(ISQMSData.MESSAGE_RESPONSE_AGENT_OK, null);
								break;

							case ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_H09:
								// 행동 : 서비스상태변경시 
								// 메시지 내용 : COMMON
								agent_send_event(ISQMSData.EVENT_H09, ISQMSData.ISQMS_STRING_OPEN);
								agent_send_data(ISQMSData.COMMON, 0, ISQMSDataBuilder.getDataCommon());
								agent_send_event(ISQMSData.EVENT_H09, ISQMSData.ISQMS_STRING_CLOSE);
								sendMessage(ISQMSData.MESSAGE_RESPONSE_AGENT_OK, null);
								break;

							case ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_H10:
								// 행동 : VOD요청 시
								// 메시지 내용 : COMMON, CHECK_SVC, CHECK_VOD1, CHECK_VOD3
								agent_send_event(ISQMSData.EVENT_H10, ISQMSData.ISQMS_STRING_OPEN);
								agent_send_data(ISQMSData.COMMON, 0, ISQMSDataBuilder.getDataCommon());
								agent_send_data(ISQMSData.CHECK_RESULT, ISQMSData.CHECK_SVC, ISQMSDataBuilder.getDataCheckSVC());
								agent_send_data(ISQMSData.CHECK_RESULT, ISQMSData.CHECK_VOD1, ISQMSDataBuilder.getDataCheckVOD1(key));
								agent_send_data(ISQMSData.CHECK_RESULT, ISQMSData.CHECK_VOD3, ISQMSDataBuilder.getDataCheckVOD3(key));
								agent_send_event(ISQMSData.EVENT_H10, ISQMSData.ISQMS_STRING_CLOSE);
								sendMessage(ISQMSData.MESSAGE_RESPONSE_AGENT_OK, null);
								break;

							case ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_H13:
								// 행동 : 채널집입 후 5초 이상시 1회
								// 메시지 내용 : COMMON, CHECK_IPTV1, CHECK_IPTV2
								agent_send_event(ISQMSData.EVENT_H13, ISQMSData.ISQMS_STRING_OPEN);
								agent_send_data(ISQMSData.COMMON, 0, ISQMSDataBuilder.getDataCommon());
								agent_send_data(ISQMSData.CHECK_RESULT, ISQMSData.CHECK_IPTV1, ISQMSDataBuilder.getDataCheckIPTV1(key));
								agent_send_data(ISQMSData.CHECK_RESULT, ISQMSData.CHECK_IPTV2, ISQMSDataBuilder.getDataCheckIPTV2(key));
								agent_send_event(ISQMSData.EVENT_H13, ISQMSData.ISQMS_STRING_CLOSE);
								sendMessage(ISQMSData.MESSAGE_RESPONSE_AGENT_OK, null);
								break;

							case ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_H15:
								// 행동 : STB인증 시도시
								// 메시지 내용 : COMMON
								agent_send_event(ISQMSData.EVENT_H15, ISQMSData.ISQMS_STRING_OPEN);
								agent_send_data(ISQMSData.COMMON, 0, ISQMSDataBuilder.getDataCommon());
								agent_send_event(ISQMSData.EVENT_H15, ISQMSData.ISQMS_STRING_CLOSE);
								sendMessage(ISQMSData.MESSAGE_RESPONSE_AGENT_OK, null);
								break;

							case ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_E03:
								// 행동 : 네트워크 연결 불가
								// 메시지 내용 : COMMON, STATUS_NET, CHECK_ERR1
								agent_send_event(ISQMSData.EVENT_E03, ISQMSData.ISQMS_STRING_OPEN);
								agent_send_data(ISQMSData.COMMON, 0, ISQMSDataBuilder.getDataCommon());
								agent_send_data(ISQMSData.CURRENT_STATUS, ISQMSData.STATUS_NET, ISQMSDataBuilder.getDataStatusNET());
								agent_send_data(ISQMSData.CHECK_RESULT, ISQMSData.CHECK_ERR1, ISQMSDataBuilder.getDataCheckERR1());
								agent_send_event(ISQMSData.EVENT_E03, ISQMSData.ISQMS_STRING_CLOSE);
								sendMessage(ISQMSData.MESSAGE_RESPONSE_AGENT_OK, null);
								break;

							case ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_E09:
								// 행동 : 화면정지시
								// 메시지 내용 : COMMON, CHECK_ERR1, CHECK_SVC, CHECK_VOD3, CHECK_ERRORCODE
								agent_send_event(ISQMSData.EVENT_E09, ISQMSData.ISQMS_STRING_OPEN);
								agent_send_data(ISQMSData.COMMON, 0, ISQMSDataBuilder.getDataCommon());
								agent_send_data(ISQMSData.CHECK_RESULT, ISQMSData.CHECK_ERR1, ISQMSDataBuilder.getDataCheckERR1());
								agent_send_data(ISQMSData.CHECK_RESULT, ISQMSData.CHECK_SVC, ISQMSDataBuilder.getDataCheckSVC());
								agent_send_data(ISQMSData.CHECK_RESULT, ISQMSData.CHECK_VOD3, ISQMSDataBuilder.getDataCheckVOD3(key));
								agent_send_data(ISQMSData.CHECK_RESULT, ISQMSData.CHECK_ERRORCODE, ISQMSDataBuilder.getDataCheckERRORCODE());
								agent_send_event(ISQMSData.EVENT_E09, ISQMSData.ISQMS_STRING_CLOSE);
								sendMessage(ISQMSData.MESSAGE_RESPONSE_AGENT_OK, null);
								break;

							case ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_E14:
								// 행동 : VOD 장애 발생 시 (VOD 요청 시 장애 전송, VOD 재생시 장애 전송)
								// 메시지 내용 : COMMON, CHECK_SVC, CHECK_VOD1, CHECK_VOD3, CHECK_VOD4
								agent_send_event(ISQMSData.EVENT_E14, ISQMSData.ISQMS_STRING_OPEN);
								agent_send_data(ISQMSData.COMMON, 0, ISQMSDataBuilder.getDataCommon());
								agent_send_data(ISQMSData.CHECK_RESULT, ISQMSData.CHECK_SVC, ISQMSDataBuilder.getDataCheckSVC());
								agent_send_data(ISQMSData.CHECK_RESULT, ISQMSData.CHECK_VOD1, ISQMSDataBuilder.getDataCheckVOD1(key));
								agent_send_data(ISQMSData.CHECK_RESULT, ISQMSData.CHECK_VOD3, ISQMSDataBuilder.getDataCheckVOD3(key));
								agent_send_data(ISQMSData.CHECK_RESULT, ISQMSData.CHECK_VOD4, ISQMSDataBuilder.getDataCheckVOD4(key));
								agent_send_event(ISQMSData.EVENT_E14, ISQMSData.ISQMS_STRING_CLOSE);
								sendMessage(ISQMSData.MESSAGE_RESPONSE_AGENT_OK, null);
								break;

							case ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_E18:
								// 행동 : WSCS 장애 발생
								// 메시지 내용 : COMMON, CHECK_ERR1, CHECK_WSCS
								agent_send_event(ISQMSData.EVENT_E18, ISQMSData.ISQMS_STRING_OPEN);
								agent_send_data(ISQMSData.COMMON, 0, ISQMSDataBuilder.getDataCommon());
								agent_send_data(ISQMSData.CHECK_RESULT, ISQMSData.CHECK_ERR1, ISQMSDataBuilder.getDataCheckERR1());
								agent_send_data(ISQMSData.CHECK_RESULT, ISQMSData.CHECK_WSCS, ISQMSDataBuilder.getDataCheckWSCS());
								agent_send_event(ISQMSData.EVENT_E18, ISQMSData.ISQMS_STRING_CLOSE);
								sendMessage(ISQMSData.MESSAGE_RESPONSE_AGENT_OK, null);
								break;

							case ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_E19:
								// 행동 : 네트워크 변동시 문제 발생시
								// 메시지 내용 : COMMON, CHECK_ERR1, CHECK_NET
								agent_send_event(ISQMSData.EVENT_E19, ISQMSData.ISQMS_STRING_OPEN);
								agent_send_data(ISQMSData.COMMON, 0, ISQMSDataBuilder.getDataCommon());
								agent_send_data(ISQMSData.CHECK_RESULT, ISQMSData.CHECK_ERR1, ISQMSDataBuilder.getDataCheckERR1());
								agent_send_data(ISQMSData.CHECK_RESULT, ISQMSData.CHECK_NET, ISQMSDataBuilder.getDataCheckNET());
								agent_send_event(ISQMSData.EVENT_E19, ISQMSData.ISQMS_STRING_CLOSE);
								sendMessage(ISQMSData.MESSAGE_RESPONSE_AGENT_OK, null);
								break;

							case ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_E20:
								// 행동 : SCS관련 문제 발생시
								// 메시지 내용 : COMMON, CHECK_ERR1, CHECK_SCS
								agent_send_event(ISQMSData.EVENT_E20, ISQMSData.ISQMS_STRING_OPEN);
								agent_send_data(ISQMSData.COMMON, 0, ISQMSDataBuilder.getDataCommon());
								agent_send_data(ISQMSData.CHECK_RESULT, ISQMSData.CHECK_ERR1, ISQMSDataBuilder.getDataCheckERR1());
								agent_send_data(ISQMSData.CHECK_RESULT, ISQMSData.CHECK_SCS, ISQMSDataBuilder.getDataCheckSCS());
								agent_send_event(ISQMSData.EVENT_E20, ISQMSData.ISQMS_STRING_CLOSE);
								sendMessage(ISQMSData.MESSAGE_RESPONSE_AGENT_OK, null);
								break;

							case ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_E21:
								// 행동 : LGS관련 문제 발생시
								// 메시지 내용 : COMMON, CHECK_ERR1, CHECK_LGS
								agent_send_event(ISQMSData.EVENT_E21, ISQMSData.ISQMS_STRING_OPEN);
								agent_send_data(ISQMSData.COMMON, 0, ISQMSDataBuilder.getDataCommon());
								agent_send_data(ISQMSData.CHECK_RESULT, ISQMSData.CHECK_ERR1, ISQMSDataBuilder.getDataCheckERR1());
								agent_send_data(ISQMSData.CHECK_RESULT, ISQMSData.CHECK_LGS, ISQMSDataBuilder.getDataCheckLGS());
								agent_send_event(ISQMSData.EVENT_E21, ISQMSData.ISQMS_STRING_CLOSE);
								sendMessage(ISQMSData.MESSAGE_RESPONSE_AGENT_OK, null);
								break;

							default:
								AgentSendManager.this.sendMessage(ISQMSData.MESSAGE_RESPONSE_AGENT_ERROR, null);
								break;
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
						AgentSendManager.this.sendMessage(ISQMSData.MESSAGE_RESPONSE_AGENT_ERROR, null);
					} catch (Exception e) {
						e.printStackTrace();
						AgentSendManager.this.sendMessage(ISQMSData.MESSAGE_RESPONSE_AGENT_ERROR, null);
					}

					return false;
				}
			});

			// < notify
			if (mIsWait) {
				synchronized (this) {
					notify();
					mIsWait = false;
				}
			}

			// < Start looping the message queue of this thread.
			Looper.loop();
		}

		private void sendMessage(int id, Object obj) {
			if (mSendHandler != null) {
				Message toMain = mSendHandler.obtainMessage();
				toMain.what = id;
				toMain.obj = obj;
				mSendHandler.sendMessage(toMain);
			}
		}
	}

	private class ReceiveEventManager extends Thread {
		@Override
		public void run() {
			while (true) {
				synchronized (mLock) {
					while (mReceiveEventList.size() == 0) {
						try {
							mLock.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

					final ISQMSReceiveEvent receiveEvent = mReceiveEventList.remove(0);
					if (null == receiveEvent) {
						continue;
					}
					String event_id = receiveEvent.event_id;
					if (null == event_id || event_id.length() <= 0) {
						continue;
					}
					ISQMSUtil.info(LOGD, "ReceiveEventManager.run() called. event_id : " + event_id);

					if (event_id.equalsIgnoreCase(ISQMSData.EVENT_C03)) {
						requestListener(ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C03_RECENT_ALL_UPGRADE, null);
					} else if (event_id.equalsIgnoreCase(ISQMSData.EVENT_C04)) {
						requestListener(ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C04_AGE_LIMIT_CHANGE, null);
					} else if (event_id.equalsIgnoreCase(ISQMSData.EVENT_C05)) {
						if (null != receiveEvent.data && 0 < receiveEvent.data.length()) {
							if (receiveEvent.data.equalsIgnoreCase(ISQMSData.RESULT_TRUE)) {
								requestListener(ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C05_AUTO_NEXT_CHANGE, Boolean.TRUE);
							}
							if (receiveEvent.data.equalsIgnoreCase(ISQMSData.RESULT_FALSE)) {
								requestListener(ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C05_AUTO_NEXT_CHANGE, Boolean.FALSE);
							}
						} else {
							ISQMSUtil.debug(LOGD, "ReceiveEventManager.run() receiveEvent.data : " + receiveEvent.data);
						}
					} else if (event_id.equalsIgnoreCase(ISQMSData.EVENT_C06)) {
						requestListener(ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C06_ADMETA_FILE_DOWNLOAD, null);
					} else if (event_id.equalsIgnoreCase(ISQMSData.EVENT_C07)) {
						requestListener(ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C07_REBOOT, null);
						mLockWakeHandler.sendEmptyMessage(0);
					} else if (event_id.equalsIgnoreCase(ISQMSData.EVENT_C09)) {
						requestListener(ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C09_RESOLUTION_CHANGE, null);
					} else if (event_id.equalsIgnoreCase(ISQMSData.EVENT_C14)) {
						requestListener(ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C14_STB_PASSWORD_CHANGE, null);
					} else if (event_id.equalsIgnoreCase(ISQMSData.EVENT_C15)) {
						requestListener(ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C15_CHILDLIMIT_PASSWORD_CHANGE, null);
					} else if (event_id.equalsIgnoreCase(ISQMSData.EVENT_C17)) {
						requestListener(ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C17_CHILDLIMIT_TIME_CHANGE, null);
					} else if (event_id.equalsIgnoreCase(ISQMSData.EVENT_C18)) {
						requestListener(ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C18_ADULT_AUTH_CHANGE, null);
					}
				}
			}
		}
	}

	public void recv_event(String event_id, String data) {
		ISQMSUtil.info(LOGD, "recv_event() called. event_id = " + event_id + ", data = " + data);
		if (null == event_id || event_id.length() <= 0) {
			return;
		}

		ISQMSReceiveEvent receiveEvent = new ISQMSReceiveEvent();
		receiveEvent.event_id = event_id;
		receiveEvent.data = data;
		synchronized (mLock) {
			mReceiveEventList.add(receiveEvent);
			mLock.notifyAll();
		}
	}

	/**
	 * <pre>
	 * Data Define :
	 * yyMMddHHmmss
	 * 
	 * Data Define Description :
	 * 현재 이벤트를 처리/보고한 STB시각
	 * 12자리로 구성된 날자+시간
	 * </pre>
	 * 
	 * @return EVENT_TS
	 */
	// public static String getEventTS() {
	// String eventTS = ISQMSUtil.toDateFormat("yyMMddHHmmss", new Date());
	// return eventTS;
	// }

	// =========================================================================
	// < setter IsQMS COMMON
	// =========================================================================
	/**
	 * <pre>
	 * Data Define :
	 * XX.XX
	 * 
	 * Data Define Description :
	 * STB에 적용되어 사용중인 ISQMS의 데이터 및 프로토콜 버전
	 * </pre>
	 */
	// public void setCommonProtocolVer(String protocolVer) {
	// ISQMSUtil.debug(LOGD, "setCommonProtocolVer() called. protocolVer : " + protocolVer);
	// mISQMSCommon.PROTOCOL_VER = protocolVer;
	// }

	/**
	 * <pre>
	 * Data Define :
	 * X.X
	 * 
	 * Data Define Description :
	 * 현재 STB의 구성 버전 (Legacy/IPTV2.0)
	 * </pre>
	 */
	public void setCommonStbVer(String stbVer) {
		ISQMSUtil.debug(LOGD, "setCommonStbVer() called. stbVer : " + stbVer);
		mISQMSCommon.STB_VER = stbVer;
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
	public void setCommonStbId(String stbId) {
		ISQMSUtil.debug(LOGD, "setCommonStbId() called. stbId : " + stbId);
		if (null != stbId) {
			stbId = stbId.replace("{", "");
			stbId = stbId.replace("}", "");
		}
		mISQMSCommon.STB_ID = stbId;
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
	public void setCommonStbMac(String stbMac) {
		ISQMSUtil.debug(LOGD, "setCommonStbMac() called. stbMac : " + stbMac);
		if (null != stbMac) {
			stbMac = stbMac.replace("{", "");
			stbMac = stbMac.replace("}", "");
			stbMac = stbMac.replace(":", "");
		}
		mISQMSCommon.STB_MAC = stbMac;
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
	public void setCommonStbSwVer(String stbSwVer) {
		ISQMSUtil.debug(LOGD, "setCommonStbSwVer() called. stbSwVer : " + stbSwVer);
		mISQMSCommon.STB_SW_VER = stbSwVer;
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
	// public void setCommonStbXpgVer(String stbXpgVer) {
	// ISQMSUtil.debug(LOGD, "setCommonStbXpgVer() called. stbXpgVer : " + stbXpgVer);
	// mISQMSCommon.STB_XPG_VER = stbXpgVer;
	// }

	/**
	 * <pre>
	 * Data Define :
	 * YYMMDDXXXXXX
	 * 
	 * Data Define Description :
	 * 12자리로 구성된 DVB-SI EPG Version
	 * -뒤6자리는 시간으로추정되나 의미 없음
	 * </pre>
	 */
	public void setCommonStbEpgVer(String stbEpgVer) {
		ISQMSUtil.debug(LOGD, "setCommonStbEpgVer() called. stbEpgVer : " + stbEpgVer);
		mISQMSCommon.STB_EPG_VER = stbEpgVer;
	}

	/**
	 * <pre>
	 * Data Define :
	 * YYMMDDXXXXXX
	 * 
	 * Data Define Description :
	 * </pre>
	 */
	// public void setCommonStbVasVer(String stbVasVer) {
	// ISQMSUtil.debug(LOGD, "setCommonStbVasVer() called. stbVasVer : " + stbVasVer);
	// mISQMSCommon.STB_VAS_VER = stbVasVer;
	// }

	/**
	 * <pre>
	 * Data Define :
	 * XXXXXXXXXX
	 * 
	 * Data Define Description :
	 * STB장비의 HW모델 10자리 문자
	 * </pre>
	 */
	public void setCommonStbModel(String stbModel) {
		ISQMSUtil.debug(LOGD, "setCommonStbModel() called. stbModel : " + stbModel);
		mISQMSCommon.STB_MODEL = stbModel;
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
	public void setCommonStbAuth(boolean isSTBAuth) {
		ISQMSUtil.debug(LOGD, "setCommonStbAuth() called. isSTBAuth : " + isSTBAuth);
		if (true == isSTBAuth) {
			mISQMSCommon.STB_AUTH = ISQMSData.RESULT_TRUE;
		} else {
			mISQMSCommon.STB_AUTH = ISQMSData.RESULT_FALSE;
		}
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
	public void setCommonIptvArea(String iptvArea) {
		ISQMSUtil.debug(LOGD, "setCommonIptvArea() called. iptvArea : " + iptvArea);
		mISQMSCommon.STB_IPTV_AREA = iptvArea;
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
	public void setCommonSvcMode(ISQMSEnumData.eSCV_MODE scv_MODE) {
		ISQMSUtil.debug(LOGD, "setCommonSvcMode() called. scv_MODE : " + scv_MODE);
		if (null == scv_MODE) {
			mISQMSCommon.STB_SVC_MODE = null;
			return;
		}

		String scvModeName = scv_MODE.name().replace(ISQMSEnumData.PREFIX_SCV_MODE, "");
		mISQMSCommon.STB_SVC_MODE = scvModeName;
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
	 * 사용 설정된 망 정보
	 * {0:SKB|1:기타미정의}
	 * </pre>
	 */
	public void setStatusNetNetworkMode(boolean skbNetworkMode) {
		ISQMSUtil.debug(LOGD, "setStatusNetNetworkMode() called. isNotSKBNetworkMode : " + skbNetworkMode);
		if (true == skbNetworkMode) {
			mISQMSStatusNET.S_NETWORK_MODE = ISQMSData.RESULT_FALSE;
		} else {
			mISQMSStatusNET.S_NETWORK_MODE = ISQMSData.RESULT_TRUE;
		}
		mISQMSCheckNET.S_NETWORK_MODE = mISQMSStatusNET.S_NETWORK_MODE;
	}

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
	public void setStatusNetDhcpMode(boolean isDhcpMode) {
		ISQMSUtil.debug(LOGD, "setStatusNetDhcpMode() called. isDhcpMode : " + isDhcpMode);
		if (true == isDhcpMode) {
			mISQMSStatusNET.S_NET_DHCP_MODE = ISQMSData.RESULT_TRUE;
		} else {
			mISQMSStatusNET.S_NET_DHCP_MODE = ISQMSData.RESULT_FALSE;
		}
		mISQMSCheckNET.S_NET_DHCP_MODE = mISQMSStatusNET.S_NET_DHCP_MODE;
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
	public void setStatusNetIpAddr(String netIpAddr) {
		ISQMSUtil.debug(LOGD, "setStatusNetIpAddr() called. netIpAddr : " + netIpAddr);
		mISQMSStatusNET.S_NET_IPADDR = netIpAddr;
		mISQMSCheckNET.S_NET_IPADDR = mISQMSStatusNET.S_NET_IPADDR;
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
	public void setStatusNetIpMask(String netIpMask) {
		ISQMSUtil.debug(LOGD, "setStatusNetIpMask() called. netIpMask : " + netIpMask);
		mISQMSStatusNET.S_NET_IPMASK = netIpMask;
		mISQMSCheckNET.S_NET_IPMASK = mISQMSStatusNET.S_NET_IPMASK;
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
	public void setStatusNetIpGateway(String netIpGateway) {
		ISQMSUtil.debug(LOGD, "setStatusNetIpGateway() called. netIpGateway : " + netIpGateway);
		mISQMSStatusNET.S_NET_IPGW = netIpGateway;
		mISQMSCheckNET.S_NET_IPGW = mISQMSStatusNET.S_NET_IPGW;
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
	public void setStatusNetDNS1(String netDNS1) {
		ISQMSUtil.debug(LOGD, "setNetDNS1() called. netDNS1 : " + netDNS1);
		mISQMSStatusNET.S_NET_DNS1 = netDNS1;
		mISQMSCheckNET.S_NET_DNS1 = mISQMSStatusNET.S_NET_DNS1;
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
	public void setStatusNetDNS2(String netDNS2) {
		ISQMSUtil.debug(LOGD, "setNetDNS2() called. netDNS2 : " + netDNS2);
		mISQMSStatusNET.S_NET_DNS2 = netDNS2;
		mISQMSCheckNET.S_NET_DNS2 = mISQMSStatusNET.S_NET_DNS2;
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
	public void setStatusConfStbScrResolution(eDISPLAY_MODE display_MODE) {
		ISQMSUtil.debug(LOGD, "setStatusConfStbScrResolution() called. display_MODE : " + display_MODE);
		if (null == display_MODE) {
			mISQMSStatusCONF.STB_SCR_RESOLUTION = null;
			return;
		}

		String displayMode = display_MODE.name().replace(ISQMSEnumData.PREFIX_DISPLAY_MODE, "");
		mISQMSStatusCONF.STB_SCR_RESOLUTION = displayMode;
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
	public void setStatusConfStbScrTv(eTV_RATE_MODE tv_RATE_MODE) {
		ISQMSUtil.debug(LOGD, "setStatusConfStbScrTv() called. display_MODE : " + tv_RATE_MODE);
		if (null == tv_RATE_MODE) {
			mISQMSStatusCONF.STB_SCR_TV = null;
			return;
		}

		String rateMode = tv_RATE_MODE.name().replace(ISQMSEnumData.PREFIX_TV_RATE_MODE, "");
		mISQMSStatusCONF.STB_SCR_TV = rateMode;
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
	public void setStatusConfStbScrVideo(eVIDEO_RATE_MODE video_RATE_MODE) {
		ISQMSUtil.debug(LOGD, "setStatusConfStbScrVideo() called. video_RATE_MODE : " + video_RATE_MODE);
		if (null == video_RATE_MODE) {
			mISQMSStatusCONF.STB_SCR_VIDEO = null;
			return;
		}

		String videoRateMode = video_RATE_MODE.name().replace(ISQMSEnumData.PREFIX_VIDEO_RATE_MODE, "");
		mISQMSStatusCONF.STB_SCR_VIDEO = videoRateMode;
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
	public void setStatusConfStbAdult(boolean isAllowSTBAdult) {
		ISQMSUtil.debug(LOGD, "setStatusConfStbAdult() called. isAllowSTBAdult : " + isAllowSTBAdult);
		if (true == isAllowSTBAdult) {
			mISQMSStatusCONF.STB_ADULT = ISQMSData.RESULT_TRUE;
		} else {
			mISQMSStatusCONF.STB_ADULT = ISQMSData.RESULT_FALSE;
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
	public void setStatusConfStbAgeLimit(eAGE_LIMIT_TYPE age_LIMIT_TYPE) {
		ISQMSUtil.debug(LOGD, "setStatusConfStbAgeLimit() called. age_LIMIT_TYPE : " + age_LIMIT_TYPE);
		if (null == age_LIMIT_TYPE) {
			mISQMSStatusCONF.STB_AGE_LIMIT = null;
			return;
		}

		String ageLimitType = age_LIMIT_TYPE.name().replace(ISQMSEnumData.PREFIX_AGE_LIMIT_TYPE, "");
		mISQMSStatusCONF.STB_AGE_LIMIT = ageLimitType;
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
	public void setStatusConfStbAgeTime(String childLimitTime) {
		ISQMSUtil.debug(LOGD, "setStatusConfStbAgeTime() called. childLimitTime : " + childLimitTime);
		mISQMSStatusCONF.STB_AGE_TIME = childLimitTime;
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
	public void setStatusConfStbAutoNext(boolean isAutoNext) {
		ISQMSUtil.debug(LOGD, "setStatusConfStbAutoNext() called. isAutoNext : " + isAutoNext);
		if (true == isAutoNext) {
			mISQMSStatusCONF.STB_AUTONEXT = ISQMSData.RESULT_TRUE;
		} else {
			mISQMSStatusCONF.STB_AUTONEXT = ISQMSData.RESULT_FALSE;
		}
	}

	// =========================================================================
	// < setter CURRENT STATUS XPG2
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
	public void setStatusXpg2XpgFull(String xpgFullVersion) {
		ISQMSUtil.debug(LOGD, "setStatusXpg2XpgFull() called. xpgFullVersion : " + xpgFullVersion);
		mISQMSStatusXPG2.XPG_FULL = xpgFullVersion;
	}

	/**
	 * <pre>
	 * Data Define :
	 * YYMMDDhhmmss
	 * 
	 * Data Define Description :
	 * 콘텐츠 정보 구성 버전
	 * </pre>
	 */
	// public void setStatusXpg2XpgContent(String xpgContent) {
	// ISQMSUtil.debug(LOGD, "setStatusXpg2XpgContent() called. xpgcontent : " + xpgContent);
	// mISQMSStatusXPG2.XPG_CONTENT = xpgContent;
	// }

	/**
	 * <pre>
	 * Data Define :
	 * YYMMDDhhmmss
	 * 
	 * Data Define Description :
	 * VOD 메뉴구성 버전
	 * </pre>
	 */
	// public void setStatusXpg2XpgMenu(String xpgMenu) {
	// ISQMSUtil.debug(LOGD, "setStatusXpg2XpgMenu() called. xpgMenu : " + xpgMenu);
	// mISQMSStatusXPG2.XPG_MENU = xpgMenu;
	// }

	/**
	 * <pre>
	 * Data Define :
	 * YYMMDDhhmmss
	 * 
	 * Data Define Description :
	 * IPTV 메뉴구성 버전
	 * </pre>
	 */
	// public void setStatusXpg2XpgIptvMenu(String xpgIptvMenu) {
	// ISQMSUtil.debug(LOGD, "setStatusXpg2XpgIptvMenu() called. xpgIptvMenu : " + xpgIptvMenu);
	// mISQMSStatusXPG2.XPG_IPTV_MENU = xpgIptvMenu;
	// }

	/**
	 * <pre>
	 * Data Define :
	 * YYMMDDhhmmss
	 * 
	 * Data Define Description :
	 * 이미지 구성 버전
	 * </pre>
	 */
	// public void setStatusXpg2XpgImage(String xpgImage) {
	// ISQMSUtil.debug(LOGD, "setStatusXpg2XpgImage() called. xpgImage : " + xpgImage);
	// mISQMSStatusXPG2.XPG_IMAGE = xpgImage;
	// }

	/**
	 * <pre>
	 * Data Define :
	 * YYMMDDhhmmss
	 * 
	 * Data Define Description :
	 * 포스터 구성 버전
	 * </pre>
	 */
	// public void setStatusXpg2XpgThumbnail(String xpgThumbnail) {
	// ISQMSUtil.debug(LOGD, "setStatusXpg2XpgThumbnail() called. xpgThumbnail : " + xpgThumbnail);
	// mISQMSStatusXPG2.XPG_THUMBNAIL = xpgThumbnail;
	// }

	/**
	 * <pre>
	 * Data Define :
	 * YYMMDDhhmmss
	 * 
	 * Data Define Description :
	 * 포스터 구성 버전
	 * </pre>
	 */
	// public void setStatusXpg2XpgPreContent(String preContent) {
	// ISQMSUtil.debug(LOGD, "setStatusXpg2XpgPreContent() called. preContent : " + preContent);
	// mISQMSStatusXPG2.XPG_PRECONTENT = preContent;
	// }

	// =========================================================================
	// < setter CURRENT STATUS BBRATE
	// =========================================================================
	/**
	 * <pre>
	 * Data Define :
	 * XXX
	 * 
	 * Data Define Description :
	 * 0~100
	 * 삼성 STB인경우 Bad Block Rate 정보를 얻어옴
	 * </pre>
	 */
	// public void setStatusBbratebbrate(String badBlockRate) {
	// ISQMSUtil.debug(LOGD, "setStatusBbratebbrate() called. bbrate : " + badBlockRate);
	// mISQMSStatusBBRATE.S_BBRATE = badBlockRate;
	// }

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
	public void setCheckUpgSwUpgrade(eUPG_UPGRADE upg_UPGRADE) {
		ISQMSUtil.debug(LOGD, "setCheckUpgSwUpgrade() called. upg_UPGRADE : " + upg_UPGRADE);
		if (null == upg_UPGRADE) {
			mISQMSCheckUPG.UPG_C_SW_UPGRADE = null;
			return;
		}

		String upgSwUpgrade = null;
		switch (upg_UPGRADE) {
			case MODE_START:
				upgSwUpgrade = ISQMSData.ISQMS_STRING_UPG_UPGRADE_START;
				break;
			case MODE_SUCCESS:
				upgSwUpgrade = ISQMSData.ISQMS_STRING_UPG_UPGRADE_SUCCESS;
				break;
			case MODE_FAIL:
				upgSwUpgrade = ISQMSData.ISQMS_STRING_UPG_UPGRADE_FAIL;
				break;
			default:
				return;
		}
		mISQMSCheckUPG.UPG_C_SW_UPGRADE = upgSwUpgrade;
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
	public void setCheckUpgChUpgrade(eUPG_UPGRADE upg_UPGRADE) {
		ISQMSUtil.debug(LOGD, "setCheckUpgChUpgrade() called. upg_UPGRADE : " + upg_UPGRADE);
		if (null == upg_UPGRADE) {
			mISQMSCheckUPG.UPG_C_CH_UPGRADE = null;
			return;
		}

		String upgChannelUpgrade = null;
		switch (upg_UPGRADE) {
			case MODE_START:
				upgChannelUpgrade = ISQMSData.ISQMS_STRING_UPG_UPGRADE_START;
				break;
			case MODE_SUCCESS:
				upgChannelUpgrade = ISQMSData.ISQMS_STRING_UPG_UPGRADE_SUCCESS;
				break;
			case MODE_FAIL:
				upgChannelUpgrade = ISQMSData.ISQMS_STRING_UPG_UPGRADE_FAIL;
				break;
			default:
				return;
		}
		mISQMSCheckUPG.UPG_C_CH_UPGRADE = upgChannelUpgrade;
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
	public void setCheckSvcVodCid(String vodCid) {
		ISQMSUtil.debug(LOGD, "setCheckSvcVodCid() called. vodCid : " + vodCid);
		mISQMSCheckSVC.SVC_C_VOD_CID = vodCid;
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
	public void setCheckSvcVodAid(String vodAid) {
		ISQMSUtil.debug(LOGD, "setCheckSvcVodAid() called. vodAid : " + vodAid);
		mISQMSCheckSVC.SVC_C_VOD_AID = vodAid;
	}

	/**
	 * <pre>
	 * Data Define :
	 * XXXX
	 * 
	 * Data Define Description :
	 * SetTop 구매인증 비밀번호(재설정 및 확인에 사용)
	 * </pre>
	 */
	// public void setCheckSvcPswdStb(String passwordStb) {
	// ISQMSUtil.debug(LOGD, "setCheckSvcPswdStb() called. passwordStb : " + passwordStb);
	// mISQMSCheckSVC.SVC_C_PSWD_STB = passwordStb;
	// }

	/**
	 * <pre>
	 * Data Define :
	 * XXXX
	 * 
	 * Data Define Description :
	 * 성인(자녀제한) 비밀번호(재설정 및 확인에 사용)
	 * </pre>
	 */
	// public void setCheckSvcPswdAge(String passwordAge) {
	// ISQMSUtil.debug(LOGD, "setCheckSvcPswdAge() called. vodAid : " + passwordAge);
	// mISQMSCheckSVC.SVC_C_PSWD_AGE = passwordAge;
	// }

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
	public void setCheckVod1VodScsIp(Integer key, String vodScsIp) {
		ISQMSUtil.debug(LOGD, "setCheckVod1VodScsIp() called. vodScsIp : " + vodScsIp);
		ISQMSCheckVOD mISQMSCheckVOD = mISQMSCheckVODList.get(key);
		if (null == mISQMSCheckVOD) {
			ISQMSUtil.debug(LOGD, "setCheckVod1VodScsIp() mISQMSCheckVOD is null");
			return;
		}

		mISQMSCheckVOD.VOD1_C_VOD_SCS_IP = vodScsIp;
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
	public void setCheckVod1VodScsRt(Integer key, String vodScsRt) {
		ISQMSUtil.debug(LOGD, "setCheckVod1VodScsRt() called. vodScsRt : " + vodScsRt);
		ISQMSCheckVOD mISQMSCheckVOD = mISQMSCheckVODList.get(key);
		if (null == mISQMSCheckVOD) {
			ISQMSUtil.debug(LOGD, "setCheckVod1VodScsRt() mISQMSCheckVOD is null");
			return;
		}

		mISQMSCheckVOD.VOD1_C_VOD_SCS_RT = vodScsRt;
	}

	/**
	 * <pre>
	 * Data Define :
	 * XXX.XXX.XXX.XXX
	 * 
	 * Data Define Description :
	 * IPv4 Address - Download Server IP
	 * </pre>
	 */
	public void setCheckVod1VodDownIp(Integer key, String vodDownIp) {
		ISQMSUtil.debug(LOGD, "setCheckVod1VodDownIp() called. vodDownIp : " + vodDownIp);
		ISQMSCheckVOD mISQMSCheckVOD = mISQMSCheckVODList.get(key);
		if (null == mISQMSCheckVOD) {
			ISQMSUtil.debug(LOGD, "setCheckVod1VodDownIp() mISQMSCheckVOD is null");
			return;
		}

		mISQMSCheckVOD.VOD1_C_VOD_DOWN_IP = vodDownIp;
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
	public void setCheckVod1VodDownRt(Integer key, String vodDownRt) {
		ISQMSUtil.debug(LOGD, "setCheckVod1VodDownRt() called. vodDownRt : " + vodDownRt);
		ISQMSCheckVOD mISQMSCheckVOD = mISQMSCheckVODList.get(key);
		if (null == mISQMSCheckVOD) {
			ISQMSUtil.debug(LOGD, "setCheckVod1VodDownRt() mISQMSCheckVOD is null");
			return;
		}

		mISQMSCheckVOD.VOD1_C_VOD_DOWN_RT = vodDownRt;
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
	public void setCheckVod3VodContentName(Integer key, String vodContentName) {
		ISQMSUtil.debug(LOGD, "setCheckVod3VodContentName() called. vodContentName : " + vodContentName);
		ISQMSCheckVOD mISQMSCheckVOD = mISQMSCheckVODList.get(key);
		if (null == mISQMSCheckVOD) {
			ISQMSUtil.debug(LOGD, "setCheckVod3VodContentName() mISQMSCheckVOD is null");
			return;
		}

		mISQMSCheckVOD.VOD3_C_VOD_CONTENT_NAME = vodContentName;
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
	public void setCheckVod3VodContentUrl(Integer key, String vodContentUrl) {
		ISQMSUtil.debug(LOGD, "setCheckVod3VodContentUrl() called. vodContentUrl : " + vodContentUrl);
		ISQMSCheckVOD mISQMSCheckVOD = mISQMSCheckVODList.get(key);
		if (null == mISQMSCheckVOD) {
			ISQMSUtil.debug(LOGD, "setCheckVod3VodContentUrl() mISQMSCheckVOD is null");
			return;
		}

		mISQMSCheckVOD.VOD3_C_VOD_CONTENT_URL = vodContentUrl;
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
	 * 0:실패
	 * 실패 시 C_MSG 에러 정보
	 * </pre>
	 */
	public void setCheckVod4VodErr(Integer key, eVOD4_VOD_ERROR vod4_VOD_ERROR) {
		ISQMSUtil.debug(LOGD, "setCheckVod4VodErr() called. vod4_VOD_ERROR : " + vod4_VOD_ERROR);
		ISQMSCheckVOD mISQMSCheckVOD = mISQMSCheckVODList.get(key);
		if (null == mISQMSCheckVOD) {
			ISQMSUtil.debug(LOGD, "setCheckVod4VodErr() mISQMSCheckVOD is null");
			return;
		}
		if (null == vod4_VOD_ERROR) {
			ISQMSUtil.debug(LOGD, "setCheckVod4VodErr() vod4_VOD_ERROR is null");
			mISQMSCheckVOD.VOD4_C_VOD_ERR = null;
			return;
		}

		String vodError = null;
		switch (vod4_VOD_ERROR) {
			case MODE_SUCCESS:
				vodError = ISQMSData.ISQMS_STRING_VOD4_VOD_ERROR_SUCCESS;
				break;
			case MODE_FAIL:
				vodError = ISQMSData.ISQMS_STRING_VOD4_VOD_ERROR_FAIL;
				break;
			default:
				break;
		}
		mISQMSCheckVOD.VOD4_C_VOD_ERR = vodError;
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
	public void setCheckVod4Msg(Integer key, String vodMessage) {
		ISQMSUtil.debug(LOGD, "setCheckVod4Msg() called. vodMessage : " + vodMessage);
		ISQMSCheckVOD mISQMSCheckVOD = mISQMSCheckVODList.get(key);
		if (null == mISQMSCheckVOD) {
			ISQMSUtil.debug(LOGD, "setCheckVod4Msg() mISQMSCheckVOD is null");
			return;
		}

		mISQMSCheckVOD.VOD4_C_MSG = vodMessage;
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
	public void setCheckIptv1IptvChNum(Integer key, String iptvChNum) {
		ISQMSUtil.debug(LOGD, "setCheckIptv1IptvChNum() called. iptvChNum : " + iptvChNum);
		ISQMSCheckIPTV mISQMSCheckIPTV = mISQMSCheckIPTVList.get(key);
		if (null == mISQMSCheckIPTV) {
			ISQMSUtil.debug(LOGD, "setCheckIptv1IptvChNum() mISQMSCheckIPTV is null");
			return;
		}

		mISQMSCheckIPTV.IPTV1_C_IPTV_CH_NUM = iptvChNum;
	}

	/**
	 * <pre>
	 * Data Define :
	 * XX
	 * 
	 * Data Define Description :
	 * 채널진입방법
	 * {01:직접입력|02:채널버튼|03:miniEPG|04:전체EPG|05:EPG}
	 * </pre>
	 */
	public void setCheckIptv1iptvChMode(Integer key, eIPTV_CH_MODE iptv_CH_MODE) {
		ISQMSUtil.debug(LOGD, "setCheckIptv1iptvChMode() called. iptv_CH_MODE : " + iptv_CH_MODE);
		ISQMSCheckIPTV mISQMSCheckIPTV = mISQMSCheckIPTVList.get(key);
		if (null == mISQMSCheckIPTV) {
			ISQMSUtil.debug(LOGD, "setCheckIptv1iptvChMode() mISQMSCheckIPTV is null");
			return;
		}
		if (null == iptv_CH_MODE) {
			ISQMSUtil.debug(LOGD, "setCheckIptv1iptvChMode() iptv_CH_MODE is null");
			mISQMSCheckIPTV.IPTV1_C_IPTV_CH_MODE = null;
			return;
		}

		String iptvChMode = null;
		switch (iptv_CH_MODE) {
			case MODE_DIRECT_INPUT:
				iptvChMode = ISQMSData.ISQMS_STRING_IPTV_CH_MODE_DIRECT_INPUT;
				break;
			case MODE_CH_BUTTON:
				iptvChMode = ISQMSData.ISQMS_STRING_IPTV_CH_MODE_CH_BUTTON;
				break;
			case MODE_MINI_EPG:
				iptvChMode = ISQMSData.ISQMS_STRING_IPTV_CH_MODE_MINI_EPG;
				break;
			case MODE_ALL_EPG:
				iptvChMode = ISQMSData.ISQMS_STRING_IPTV_CH_MODE_ALL_EPG;
				break;
			case MODE_EPG:
				iptvChMode = ISQMSData.ISQMS_STRING_IPTV_CH_MODE_EPG;
				break;
			case MODE_RESERVATION:
				iptvChMode = ISQMSData.ISQMS_STRING_IPTV_CH_MODE_RESERVATION;
				break;
			case MODE_ETC:
				iptvChMode = ISQMSData.ISQMS_STRING_IPTV_CH_MODE_ETC;
				break;
			default:
				break;
		}
		mISQMSCheckIPTV.IPTV1_C_IPTV_CH_MODE = iptvChMode;
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
	public void setCheckIptv2iptvEcode(Integer key, eIPTV_ERROR_CODE iptv_ERROR_CODE) {
		ISQMSUtil.debug(LOGD, "setCheckIptv2iptvEcode() called. iptv_ERROR_CODE : " + iptv_ERROR_CODE);
		ISQMSCheckIPTV mISQMSCheckIPTV = mISQMSCheckIPTVList.get(key);
		if (null == mISQMSCheckIPTV) {
			ISQMSUtil.debug(LOGD, "setCheckIptv2iptvEcode() mISQMSCheckIPTV is null");
			return;
		}
		if (null == iptv_ERROR_CODE) {
			ISQMSUtil.debug(LOGD, "setCheckIptv2iptvEcode() iptv_ERROR_CODE is null");
			mISQMSCheckIPTV.IPTV2_C_IPTV_ECODE = null;
			return;
		}

		String iptvErrorCode = null;
		switch (iptv_ERROR_CODE) {
			case MODE_NORMAL:
				iptvErrorCode = ISQMSData.ISQMS_STRING_IPTV_ERROR_CODE_NORMAL;
				break;
			case MODE_WEAK_SIGNAL:
				iptvErrorCode = ISQMSData.ISQMS_STRING_IPTV_ERROR_CODE_WEAK_SIGNAL;
				break;
			case MODE_OTHER:
				iptvErrorCode = ISQMSData.ISQMS_STRING_IPTV_ERROR_CODE_OTHER;
				break;
			default:
				break;
		}
		mISQMSCheckIPTV.IPTV2_C_IPTV_ECODE = iptvErrorCode;
	}

	public void setCheckIptv2Msg(Integer key, String msg) {
		ISQMSUtil.debug(LOGD, "setCheckIptv2Msg() called. msg : " + msg);
		ISQMSCheckIPTV mISQMSCheckIPTV = mISQMSCheckIPTVList.get(key);
		if (null == mISQMSCheckIPTV) {
			ISQMSUtil.debug(LOGD, "setCheckIptv2Msg() msg is null");
			return;
		}

		mISQMSCheckIPTV.IPTV2_C_MSG = msg;
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
	public void setCheckScsScsIp(String scsIp) {
		ISQMSUtil.debug(LOGD, "setCheckScsScsIp() called. scsIp : " + scsIp);
		mISQMSCheckSCS.SCS_C_SCS_IP = scsIp;
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
	public void setCheckScsScsEcode(eSCS_ECODE scs_ECODE) {
		ISQMSUtil.debug(LOGD, "setCheckScsScsEcode() called. scs_ECODE : " + scs_ECODE);
		if (null == scs_ECODE) {
			ISQMSUtil.debug(LOGD, "setCheckScsScsEcode() scs_ECODE is null");
			mISQMSCheckSCS.SCS_C_SCS_ECODE = null;
			return;
		}

		String scsErrorCode = null;
		switch (scs_ECODE) {
			case IMPOSSIBLE_TO_CONNECT:
				scsErrorCode = ISQMSData.ISQMS_STRING_SCS_ECODE_IMPOSSIBLE_TO_CONNECT;
				break;
			case RECEIVE_NO_REPLY:
				scsErrorCode = ISQMSData.ISQMS_STRING_SCS_ECODE_RECEIVE_NO_REPLY;
				break;
			case RESPONSE_ERROR:
				scsErrorCode = ISQMSData.ISQMS_STRING_SCS_ECODE_RESPONSE_ERROR;
				break;
			default:
				break;
		}
		mISQMSCheckSCS.SCS_C_SCS_ECODE = scsErrorCode;
	}

	/**
	 * <pre>
	 * Data Define :
	 * 비정형 가변 문자열
	 * 
	 * Data Define Description :
	 * SCS소그룹 작업 결과의 회신된 결과가 있는 경우 
	 * 비정형 메시지
	 * </pre>
	 */
	public void setCheckScsMsg(String msg) {
		ISQMSUtil.debug(LOGD, "setCheckScsMsg() called. msg : " + msg);
		mISQMSCheckSCS.SCS_C_MSG = msg;
	}

	// =========================================================================
	// < setter CHECK RESULT LGS
	// =========================================================================
	/**
	 * <pre>
	 * Data Define :
	 * XXX.XXX.XXX.XXX
	 * 
	 * Data Define Description :
	 * IPv4 Address - Requested LGS IP
	 * </pre>
	 */
	public void setCheckLgslgsIp(String lgsIp) {
		ISQMSUtil.debug(LOGD, "setCheckLgslgsIp() called. lgsIp : " + lgsIp);
		mISQMSCheckLGS.LGS_C_LGS_IP = lgsIp;
	}

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
	public void setCheckLgslgsEcode(eLGS_ECODE lgs_ECODE) {
		ISQMSUtil.debug(LOGD, "setCheckLgslgsEcode() called. lgs_ECODE : " + lgs_ECODE);
		if (null == lgs_ECODE) {
			ISQMSUtil.debug(LOGD, "setCheckLgslgsEcode() lgsErrorCode is null");
			mISQMSCheckLGS.LGS_C_LGS_ECODE = null;
			return;
		}

		String lgsErrorCode = null;
		switch (lgs_ECODE) {
			case IMPOSSIBLE_TO_CONNECT:
				lgsErrorCode = ISQMSData.ISQMS_STRING_LGS_ECODE_IMPOSSIBLE_TO_CONNECT;
				break;
			case RECEIVE_NO_REPLY:
				lgsErrorCode = ISQMSData.ISQMS_STRING_LGS_ECODE_RECEIVE_NO_REPLY;
				break;
			case RESPONSE_ERROR:
				lgsErrorCode = ISQMSData.ISQMS_STRING_LGS_ECODE_RESPONSE_ERROR;
				break;
			default:
				break;
		}
		mISQMSCheckLGS.LGS_C_LGS_ECODE = lgsErrorCode;
	}

	/**
	 * <pre>
	 * Data Define :
	 * 비정형 가변 문자열
	 * 
	 * Data Define Description :
	 * LGS소그룹 작업 결과의 회신된 결과가 있는 경우 
	 * 비정형 메시지
	 * </pre>
	 */
	public void setCheckLgsMsg(String msg) {
		ISQMSUtil.debug(LOGD, "setCheckLgsMsg() called. msg : " + msg);
		mISQMSCheckLGS.LGS_C_MSG = msg;
	}

	// =========================================================================
	// < setter CHECK RESULT ERR1
	// =========================================================================
	/**
	 * <pre>
	 * Data Define :
	 * XXXXX
	 * 
	 * Data Define Description :
	 * 동일에러에 대한 지속시간
	 * 초단위, 최초 발생시는 0
	 * </pre>
	 */
	public void setCheckErr1ErrDuration(String errDuration) {
		ISQMSUtil.debug(LOGD, "setCheckErr1ErrDuration() called. errDuration : " + errDuration);
		mISQMSCheckERR1.ERR1_ERR_DURATION = errDuration;
	}

	/**
	 * <pre>
	 * Data Define :
	 * XXX
	 * 
	 * Data Define Description :
	 * 동일에러에 대한 지속 시간동안 반복 횟수
	 * 최초 발생시는 0
	 * </pre>
	 */
	public void setCheckErr1ErrRepeat(String errRepeat) {
		ISQMSUtil.debug(LOGD, "setCheckErr1ErrRepeat() called. errRepeat : " + errRepeat);
		mISQMSCheckERR1.ERR1_ERR_REPEAT = errRepeat;
	}

	/**
	 * <pre>
	 * Data Define :
	 * yyMMddHHmmss
	 * 
	 * Data Define Description :
	 * 12자리로 구성된 날자+시간
	 * </pre>
	 */
	public void setCheckErr1Ts(String ts) {
		ISQMSUtil.debug(LOGD, "setCheckErr1Ts() called. ts : " + ts);
		mISQMSCheckERR1.ERR1_C_TS = ts;
	}

	// =========================================================================
	// < setter CHECK RESULT WSCS
	// =========================================================================
	/**
	 * <pre>
	 * Data Define :
	 * XXX.XXX.XXX.XXX
	 * 
	 * Data Define Description :
	 * IPv4 Address - Test Requested WSCS IP
	 * </pre>
	 */
	public void setCheckWscsWscsIp(String wscsIp) {
		ISQMSUtil.debug(LOGD, "setCheckWscsWscsIp() called. wscsIp : " + wscsIp);
		mISQMSCheckWSCS.WSCS_C_WSCS_IP = wscsIp;
	}

	/**
	 * <pre>
	 * Data Define :
	 * XX
	 * 
	 * Data Define Description :
	 * WSCS오류코드
	 * {01:연결불가|02:응답없음|03:응답오류}
	 * </pre>
	 */
	public void setCheckWscsWscsEcode(eWSCS_ECODE wscs_ECODE) {
		ISQMSUtil.debug(LOGD, "setCheckWscsWscsEcode() called. wscs_ECODE : " + wscs_ECODE);
		if (null == wscs_ECODE) {
			ISQMSUtil.debug(LOGD, "setCheckWscsWscsEcode() wscs_ECODE is null");
			mISQMSCheckWSCS.WSCS_C_WSCS_ECODE = null;
			return;
		}

		String wscsErrorCode = null;
		switch (wscs_ECODE) {
			case IMPOSSIBLE_TO_CONNECT:
				wscsErrorCode = ISQMSData.ISQMS_STRING_WSCS_ECODE_IMPOSSIBLE_TO_CONNECT;
				break;
			case RECEIVE_NO_REPLY:
				wscsErrorCode = ISQMSData.ISQMS_STRING_WSCS_ECODE_RECEIVE_NO_REPLY;
				break;
			case RESPONSE_ERROR:
				wscsErrorCode = ISQMSData.ISQMS_STRING_WSCS_ECODE_RESPONSE_ERROR;
				break;
			default:
				break;
		}
		mISQMSCheckWSCS.WSCS_C_WSCS_ECODE = wscsErrorCode;
	}

	/**
	 * <pre>
	 * Data Define :
	 * 
	 * Data Define Description :
	 * SCS소그룹 작업 결과의 회신된 결과가 있는 경우 
	 * 비정형 메시지
	 * </pre>
	 */
	public void setCheckWscsMsg(String msg) {
		ISQMSUtil.debug(LOGD, "setCheckWscsMsg() called. msg : " + msg);
		mISQMSCheckWSCS.WSCS_C_MSG = msg;
	}

	// =========================================================================
	// < setter IsQMS LISTENER
	// =========================================================================

	public void setRecentAllUpgradeListener(OnRecentAllUpgradeListener recentAllUpgradeListener) {
		ISQMSUtil.debug(LOGD, "setRecentAllUpgradeListener() called");
		this.mRecentAllUpgradeListener = recentAllUpgradeListener;
	}

	public void setAgeLimitChangeListener(OnAgeLimitChangeListener ageLimitChangeListener) {
		ISQMSUtil.debug(LOGD, "setAgeLimitChangeListener() called");
		this.mAgeLimitChangeListener = ageLimitChangeListener;
	}

	public void setAutoNextChangeListener(OnAutoNextChangeListener autoNextChangeListener) {
		ISQMSUtil.debug(LOGD, "setAutoNextChangeListener() called");
		this.mAutoNextChangeListener = autoNextChangeListener;
	}

	public void setAdMetaFileDownloadListener(OnAdMetaFileDownloadListener adMetaFileDownloadListener) {
		ISQMSUtil.debug(LOGD, "setAdMetaFileDownloadListener() called");
		this.mAdMetaFileDownloadListener = adMetaFileDownloadListener;
	}

	public void setRebootListener(OnRebootListener rebootListener) {
		ISQMSUtil.debug(LOGD, "setRebootListener() called");
		this.mRebootListener = rebootListener;
	}

	public void setResolutionChangeListener(OnResolutionChangeListener resolutionChangeListener) {
		ISQMSUtil.debug(LOGD, "setResolutionChangeListener() called");
		this.mResolutionChangeListener = resolutionChangeListener;
	}

	public void setStbPasswordChangeListener(OnStbPasswordChangeListener stbPasswordChangeListener) {
		ISQMSUtil.debug(LOGD, "setStbPasswordChangeListener() called");
		this.mStbPasswordChangeListener = stbPasswordChangeListener;
	}

	public void setChildLimitPasswordChangeListener(OnChildLimitPasswordChangeListener childLimitPasswordChangeListener) {
		ISQMSUtil.debug(LOGD, "setChildLimitPasswordChangeListener() called");
		this.mChildLimitPasswordChangeListener = childLimitPasswordChangeListener;
	}

	public void setChildLimitTimeChangeListener(OnChildLimitTimeChangeListener childLimitTimeChangeListener) {
		ISQMSUtil.debug(LOGD, "setChildLimitTimeChangeListener() called");
		this.mChildLimitTimeChangeListener = childLimitTimeChangeListener;
	}

	public void setAdultAuthChangeListener(OnAdultAuthChangeListener adultAuthChangeListener) {
		ISQMSUtil.debug(LOGD, "setAdultAuthChangeListener() called");
		this.mAdultAuthChangeListener = adultAuthChangeListener;
	}

	// =========================================================================
	// < reques IsQMS LISTENER
	// =========================================================================
	private void requestListener(int type, Object data) {
		if (0 == type) {
			return;
		}

		ISQMSUtil.debug(LOGD, "requestListener() called. eLISTENER_TYPE : " + type);
		switch (type) {
			case ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C03_RECENT_ALL_UPGRADE:
				if (null != mRecentAllUpgradeListener) {
					mRecentAllUpgradeListener.onRecentAllUpgrade();
				} else {
					ISQMSUtil.debug(LOGD, "requestListener() mRecentAllUpgradeListener is null");
				}
				break;
			case ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C04_AGE_LIMIT_CHANGE:
				if (null != mAgeLimitChangeListener) {
					if (null != data && true == (data instanceof eAGE_LIMIT_TYPE)) {
						eAGE_LIMIT_TYPE age_LIMIT_TYPE = (eAGE_LIMIT_TYPE) data;
						mAgeLimitChangeListener.onAgeLimitChange(age_LIMIT_TYPE);
					} else {
						ISQMSUtil.debug(LOGD, "requestListener() Data is Incorrect data");
					}
				} else {
					ISQMSUtil.debug(LOGD, "requestListener() mAgeLimitChangeListener is null");
				}
				break;
			case ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C05_AUTO_NEXT_CHANGE:
				if (null != mAutoNextChangeListener) {
					if (null != data && true == (data instanceof Boolean)) {
						Boolean result = (Boolean) data;
						mAutoNextChangeListener.onAutoNextChange(result);
					} else {
						ISQMSUtil.debug(LOGD, "requestListener() Data is Incorrect data");
					}
				} else {
					ISQMSUtil.debug(LOGD, "requestListener() mAutoNextChangeListener is null");
				}
				break;
			case ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C06_ADMETA_FILE_DOWNLOAD:
				if (null != mAdMetaFileDownloadListener) {
					mAdMetaFileDownloadListener.onAdMetaFileDownload();
				} else {
					ISQMSUtil.debug(LOGD, "requestListener() mAdMetaFileDownloadListener is null");
				}
				break;
			case ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C07_REBOOT:
				if (null != mRebootListener) {
					mRebootListener.onReboot();
				} else {
					ISQMSUtil.debug(LOGD, "requestListener() mRebootListener is null");
				}
				break;
			case ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C09_RESOLUTION_CHANGE:
				if (null != mResolutionChangeListener) {
					if (null != data && true == (data instanceof ISQMSMessage)) {
						ISQMSMessage message = (ISQMSMessage) data;
						if (null != message.obj1 && null != message.obj2 && null != message.obj3 && //
								true == (message.obj1 instanceof eDISPLAY_MODE && true == (message.obj2 instanceof eVIDEO_RATE_MODE) && true == (message.obj3 instanceof eTV_RATE_MODE))) {
							eDISPLAY_MODE display_MODE = (eDISPLAY_MODE) message.obj1;
							eVIDEO_RATE_MODE video_RATE_MODE = (eVIDEO_RATE_MODE) message.obj2;
							eTV_RATE_MODE tv_RATE_MODE = (eTV_RATE_MODE) message.obj3;
							mResolutionChangeListener.onResolutionChange(display_MODE, video_RATE_MODE, tv_RATE_MODE);
						} else {
							ISQMSUtil.debug(LOGD, "requestListener() Data is Incorrect data");
						}
					} else {
						ISQMSUtil.debug(LOGD, "requestListener() Data is Incorrect data");
					}
				} else {
					ISQMSUtil.debug(LOGD, "requestListener() mResolutionChangeListener is null");
				}
				break;
			case ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C14_STB_PASSWORD_CHANGE:
				if (null != mStbPasswordChangeListener) {
					if (null != data && true == (data instanceof String) && 0 != ((String) data).length()) {
						String stbPassword = (String) data;
						mStbPasswordChangeListener.onStbPasswordChange(stbPassword);
					} else {
						ISQMSUtil.debug(LOGD, "requestListener() Data is Incorrect data");
					}
				} else {
					ISQMSUtil.debug(LOGD, "requestListener() mSTBPasswordChangeListener is null");
				}
				break;
			case ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C15_CHILDLIMIT_PASSWORD_CHANGE:
				if (null != mChildLimitPasswordChangeListener) {
					if (null != data && true == (data instanceof String) && 0 != ((String) data).length()) {
						String childLimitPassword = (String) data;
						mChildLimitPasswordChangeListener.onChildLimitPasswordChange(childLimitPassword);
					} else {
						ISQMSUtil.debug(LOGD, "requestListener() Data is Incorrect data");
					}
				} else {
					ISQMSUtil.debug(LOGD, "requestListener() mChildLimitPasswordChangeListener is null");
				}
				break;
			case ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C17_CHILDLIMIT_TIME_CHANGE:
				if (null != mChildLimitTimeChangeListener) {
					if (null != data && true == (data instanceof String) && 0 != ((String) data).length()) {
						String childLimitTime = (String) data;
						mChildLimitTimeChangeListener.onChildLimitTimeChange(childLimitTime);
					} else {
						ISQMSUtil.debug(LOGD, "requestListener() Data is Incorrect data");
					}
				} else {
					ISQMSUtil.debug(LOGD, "requestListener() mChildLimitTimeChangeListener is null");
				}
				break;
			case ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C18_ADULT_AUTH_CHANGE:
				if (null != mAdultAuthChangeListener) {
					if (null != data && true == (data instanceof Boolean)) {
						Boolean result = (Boolean) data;
						mAdultAuthChangeListener.onAdultAuthChange(result);
					} else {
						ISQMSUtil.debug(LOGD, "requestListener() mAdultAuthChangeListener is null");
					}
				} else {
					ISQMSUtil.debug(LOGD, "requestListener() mAdultAuthChangeListener is null");
				}
				break;
			default:
				ISQMSUtil.debug(LOGD, "requestListener() type is default");
				Message msg = new Message();
				sendResultHolePunchingEvent(msg);
				break;
		}
	}

	/** Key */
	public Integer openEvent(int eventMessage) {
		ISQMSUtil.info(LOGD, "openEvent() called. eventMessage : " + eventMessage);
		++mEventKey;

		switch (eventMessage) {
			case ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_H10:
			case ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_E09:
			case ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_E14:
				if (2 <= mISQMSCheckVODList.size()) {
					mISQMSCheckVODList.removeAt(0);
				}
				ISQMSCheckVOD mISQMSCheckVOD = new ISQMSCheckVOD();
				mISQMSCheckVODList.put(mEventKey, mISQMSCheckVOD);
				break;

			case ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_H13:
				if (2 <= mISQMSCheckIPTVList.size()) {
					mISQMSCheckIPTVList.removeAt(0);
				}
				ISQMSCheckIPTV mISQMSCheckIPTV = new ISQMSCheckIPTV();
				mISQMSCheckIPTVList.put(mEventKey, mISQMSCheckIPTV);
				break;
		}

		return mEventKey;
	}

	/** UI에서 Event_H, Event_E 발생 시, 값 설정 후에 호출 */
	public void sendEvent(int eventMessage) {
		closeEvent(null, eventMessage);
	}

	public void closeEvent(Integer key, int eventMessage) {
		if (null != key) {
			ISQMSUtil.info(LOGD, "sendEvent() called. key : " + key + ", eventMessage : " + eventMessage);
		} else {
			ISQMSUtil.info(LOGD, "closeEvent() called. eventMessage : " + eventMessage);
		}

		Handler receiveHandler = mAgentSendManager.getManagerHandler();
		if (receiveHandler != null) {
			Message msg = receiveHandler.obtainMessage();
			msg.what = eventMessage;
			msg.obj = key;
			receiveHandler.sendMessage(msg);
		}
	}

	/** UI에서 해당 HolePunching 이벤트 처리 후 호출 */
	public void sendResultHolePunchingEvent(Message msg) {
		ISQMSUtil.info(LOGD, "sendResultHolePunchingEvent() called. msg : " + msg);
		if (null == msg) {
			mLockWakeHandler.sendEmptyMessage(0);
			return;
		}

		String CtrlSeq = (String) msg.obj;
		switch (msg.what) {
			case ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C03_RECENT_ALL_UPGRADE:
				agent_send_data(ISQMSData.COMMON, 0, ISQMSDataBuilder.getDataCommon());
				agent_send_data(ISQMSData.CURRENT_STATUS, ISQMSData.STATUS_NET, ISQMSDataBuilder.getDataStatusNET());
				agent_send_data(ISQMSData.CURRENT_STATUS, ISQMSData.STATUS_CONF, ISQMSDataBuilder.getDataStatusCONF());
				agent_send_data(ISQMSData.CURRENT_STATUS, ISQMSData.STATUS_XPG_2, ISQMSDataBuilder.getDataStatusXPG2());
				agent_send_data(ISQMSData.CURRENT_STATUS, ISQMSData.STATUS_BBRATE, ISQMSDataBuilder.getDataStatusBBRATE());
				agent_send_event(ISQMSData.EVENT_C03, CtrlSeq);
				break;

			case ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C04_AGE_LIMIT_CHANGE:
				agent_send_data(ISQMSData.CURRENT_STATUS, ISQMSData.STATUS_CONF, ISQMSDataBuilder.getDataStatusCONF());
				agent_send_event(ISQMSData.EVENT_C04, CtrlSeq);
				break;

			case ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C05_AUTO_NEXT_CHANGE:
				agent_send_data(ISQMSData.CURRENT_STATUS, ISQMSData.STATUS_CONF, ISQMSDataBuilder.getDataStatusCONF());
				agent_send_event(ISQMSData.EVENT_C05, CtrlSeq);
				break;

			case ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C06_ADMETA_FILE_DOWNLOAD:
				break;

			case ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C09_RESOLUTION_CHANGE:
				break;

			case ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C14_STB_PASSWORD_CHANGE:
				break;

			case ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C15_CHILDLIMIT_PASSWORD_CHANGE:
				break;

			case ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C17_CHILDLIMIT_TIME_CHANGE:
				break;

			case ISQMSData.MESSAGE_REQUEST_AGENT_EVENT_C18_ADULT_AUTH_CHANGE:
				break;
			default:
				mLockWakeHandler.sendEmptyMessage(0);
				break;
		}
	}
}
