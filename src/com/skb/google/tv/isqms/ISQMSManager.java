package com.skb.google.tv.isqms;

import java.util.Date;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;

import com.skb.google.tv.common.util.LogUtil;
import com.skb.google.tv.isqms.ISQMSEnumData.eAGE_LIMIT_TYPE;
import com.skb.google.tv.isqms.ISQMSEnumData.eDISPLAY_MODE;
import com.skb.google.tv.isqms.ISQMSEnumData.eTV_RATE_MODE;
import com.skb.google.tv.isqms.ISQMSEnumData.eUPG_UPGRADE;
import com.skb.google.tv.isqms.ISQMSEnumData.eVIDEO_RATE_MODE;
import com.skb.google.tv.isqms.ISQMSListener.OnAdMetaFileDownloadListener;
import com.skb.google.tv.isqms.ISQMSListener.OnAdultAuthChangeListener;
import com.skb.google.tv.isqms.ISQMSListener.OnAgeLimitChangeListener;
import com.skb.google.tv.isqms.ISQMSListener.OnAutoNextChangeListener;
import com.skb.google.tv.isqms.ISQMSListener.OnChildLimitPasswordChangeListener;
import com.skb.google.tv.isqms.ISQMSListener.OnChildLimitTimeChangeListener;
import com.skb.google.tv.isqms.ISQMSListener.OnLgsNormalAccessListener;
import com.skb.google.tv.isqms.ISQMSListener.OnRebootListener;
import com.skb.google.tv.isqms.ISQMSListener.OnRecentAllUpgradeListener;
import com.skb.google.tv.isqms.ISQMSListener.OnResolutionChangeListener;
import com.skb.google.tv.isqms.ISQMSListener.OnScsNormalAccessListener;
import com.skb.google.tv.isqms.ISQMSListener.OnStbPasswordChangeListener;
import com.skb.google.tv.isqms.check.ISQMSCheckIPTV1;
import com.skb.google.tv.isqms.check.ISQMSCheckIPTV2;
import com.skb.google.tv.isqms.check.ISQMSCheckLGS;
import com.skb.google.tv.isqms.check.ISQMSCheckNet;
import com.skb.google.tv.isqms.check.ISQMSCheckSCS;
import com.skb.google.tv.isqms.check.ISQMSCheckSVC;
import com.skb.google.tv.isqms.check.ISQMSCheckUPG;
import com.skb.google.tv.isqms.check.ISQMSCheckVOD1;
import com.skb.google.tv.isqms.check.ISQMSCheckVOD3;
import com.skb.google.tv.isqms.check.ISQMSCheckVOD4;
import com.skb.google.tv.isqms.check.ISQMSCheckWSCS;
import com.skb.google.tv.isqms.common.ISQMSCommon;
import com.skb.google.tv.isqms.status.ISQMSStatusConf;
import com.skb.google.tv.isqms.status.ISQMSStatusNet;
import com.skb.google.tv.isqms.status.ISQMSStatusXPG2;
import com.skb.isqms.IAgentServiceToUIApp;
import com.skb.isqms.IUIAppToAgentService;

public class ISQMSManager {
	private static final String LOGD = ISQMSManager.class.getSimpleName();

	private static final int MESSAGE_STARTID = 11000;
	public static final int MESSAGE_C03_RECENT_ALL_UPGRADE = MESSAGE_STARTID + 3;
	public static final int MESSAGE_C04_AGE_LIMIT_CHANGE = MESSAGE_STARTID + 4;
	public static final int MESSAGE_C05_AUTO_NEXT_CHANGE = MESSAGE_STARTID + 5;
	public static final int MESSAGE_C06_ADMETA_FILE_DOWNLOAD = MESSAGE_STARTID + 6;
	public static final int MESSAGE_C07_REBOOT = MESSAGE_STARTID + 7;
	public static final int MESSAGE_C09_RESOLUTION_CHANGE = MESSAGE_STARTID + 9;
	public static final int MESSAGE_C14_STB_PASSWORD_CHANGE = MESSAGE_STARTID + 14;
	public static final int MESSAGE_C15_CHILDLIMIT_PASSWORD_CHANGE = MESSAGE_STARTID + 15;
	public static final int MESSAGE_C17_CHILDLIMIT_TIME_CHANGE = MESSAGE_STARTID + 17;
	public static final int MESSAGE_C18_ADULT_AUTH_CHANGE = MESSAGE_STARTID + 18;
	public static final int MESSAGE_C94_LGS_NORMAL_ACCESS = MESSAGE_STARTID + 94;
	public static final int MESSAGE_C95_SCS_NORMAL_ACCESS = MESSAGE_STARTID + 95;

	private static ISQMSManager mIsQMSManager;

	private Object mLock;
	// private ArrayList<KeyEventBundle> mKeyEventList;

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
	private OnScsNormalAccessListener mScsNormalAccessListener;
	private OnLgsNormalAccessListener mLgsNormalAccessListener;

	/** COMMON DATA */
	private ISQMSCommon mIsQMSCommon;

	/** CURRENT STATUS DATA */
	private ISQMSStatusNet mIsQMSStatusNet;
	private ISQMSStatusConf mIsQMSStatusConf;
	private ISQMSStatusXPG2 mIsQMSStatusXPG2;
	// private IsQMSStatusBbrate mIsQMSStatusBbrate; // 미사용

	/** CHECK RESULT DATA */
	private ISQMSCheckUPG mIsQMSCheckUPG;
	private ISQMSCheckSVC mIsQMSCheckSVC;
	private ISQMSCheckVOD1 mIsQMSCheckVOD1;
	private ISQMSCheckVOD3 mIsQMSCheckVOD3;
	private ISQMSCheckVOD4 mIsQMSCheckVOD4;
	private ISQMSCheckIPTV1 mIsQMSCheckIPTV1;
	private ISQMSCheckIPTV2 mIsQMSCheckIPTV2;
	private ISQMSCheckSCS mIsQMSCheckSCS;
	private ISQMSCheckLGS mIsQMSCheckLGS;
	private ISQMSCheckNet mIsQMSCheckNet;
	private ISQMSCheckWSCS mIsQMSCheckWSCS;

	private ISQMSManager() {
		mLock = new Object();

		// Common data init
		mIsQMSCommon = new ISQMSCommon();

		// Current Status data init
		mIsQMSStatusNet = new ISQMSStatusNet();
		mIsQMSStatusConf = new ISQMSStatusConf();
		mIsQMSStatusXPG2 = new ISQMSStatusXPG2();
		// mIsQMSStatusBbrate = new IsQMSStatusBbrate();

		// Check Result data init
		mIsQMSCheckUPG = new ISQMSCheckUPG();
		mIsQMSCheckSVC = new ISQMSCheckSVC();
		mIsQMSCheckVOD1 = new ISQMSCheckVOD1();
		mIsQMSCheckVOD3 = new ISQMSCheckVOD3();
		mIsQMSCheckVOD4 = new ISQMSCheckVOD4();
		mIsQMSCheckIPTV1 = new ISQMSCheckIPTV1();
		mIsQMSCheckIPTV2 = new ISQMSCheckIPTV2();
		mIsQMSCheckSCS = new ISQMSCheckSCS();
		mIsQMSCheckLGS = new ISQMSCheckLGS();
		mIsQMSCheckNet = new ISQMSCheckNet();
		mIsQMSCheckWSCS = new ISQMSCheckWSCS();

		mBinder = null;
	}

	public static ISQMSManager getInstance() {
		if (null == mIsQMSManager) {
			mIsQMSManager = new ISQMSManager();
		}

		return mIsQMSManager;
	}

	private void logDebug(String tag, String msg) {
		if (ISQMSData.DEBUG) {
			LogUtil.debug(tag, msg);
		}
	}

	private void logInfo(String tag, String msg) {
		if (ISQMSData.DEBUG) {
			LogUtil.info(tag, msg);
		}
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
			logDebug(IAgentServiceToUIApp.class.getSimpleName(), "onRecvEvent() event_id = " + event_id + " data = " + data);
			recv_event(event_id, data);
		}
	};

	private ServiceConnection mConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			logInfo(LOGD, "onServiceConnected() Service Binding!");
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
			logInfo(LOGD, "onServiceDisconnected() Service UnBinding!");
			mBinder = null;
		}
	};

	/**
	 * called when completed binding.
	 * 
	 * @param callback
	 */
	public void bindingISQMSAgent() {
		bindingISQMSAgent(mContext);
	}

	public void bindingISQMSAgent(Context context) {
		logInfo(LOGD, "bindingISQMSAgent() called");
		mContext = context;

		Intent intent = new Intent("com.skb.isqms.AgentService"); // service Name
		mContext.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
	}

	public void unbindingISQMSAgent() {
		logInfo(LOGD, "unbindingISQMSAgent() called");
		if (mBinder != null) {
			mContext.unbindService(mConnection);
			mBinder = null;
			logDebug(LOGD, "unbindingISQMSAgent() Service UnBinding!");
		}
	}

	private void onBindedISQMSAgent() {
		logInfo(LOGD, "onBindedISQMSAgent() called.");
		int nRet = start_agent();
		logDebug(LOGD, "onBindedISQMSAgent() start_agent ret = " + Integer.toString(nRet));

		if (nRet == ISQMSData.ISQMS_SUCCESS) {
			logDebug(LOGD, "onBindedISQMSAgent() send_data start.");

			// String pTemp = ";1.0;{EEDD354B-2B4C-11E3-AA84-C500C85E324C};78abbb7f806b;3.2.56-0024;100527102151;100527102152;100527102153;SMT_E5030;1;001;ITV";
			send_data(ISQMSData.COMMON, 0, getDataCommon());

			// pTemp = ";0;1;192.168.0.1;255.255.255.0;192.168.0.1;168.126.0.1;168.126.0.2";
			send_data(ISQMSData.CURRENT_STATUS, ISQMSData.STATUS_NET, getDataStatusNet());

			// pTemp = ";1080i;16:9;ORG;1;18;00;1";
			send_data(ISQMSData.CURRENT_STATUS, ISQMSData.STATUS_CONF, getDataStatusConf());

			// pTemp = ";100610140920;100504235913;100614202529;100617104141;100616052242;100609112111;100325102000";
			send_data(ISQMSData.CURRENT_STATUS, ISQMSData.STATUS_XPG_2, getDataStatusXPG2());

			// pTemp = ";3";
			send_data(ISQMSData.CURRENT_STATUS, ISQMSData.STATUS_BBRATE, getDataStatusBbrate());
		}
	}

	private int start_agent() {
		logInfo(LOGD, "start_agent() called");
		if (mBinder != null) {
			try {
				int nRet = -1;
				if ((nRet = mBinder.start_agent()) >= 0) {
					// no handle
				}
				logDebug(LOGD, "start_agent() start_agent result : " + nRet);
				return nRet;
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		} else {
			bindingISQMSAgent();
		}

		return 0;
	}

	public int send_data(int category_id, int sub_category_id, String data) {
		logInfo(LOGD, "send_data() called.");
		logDebug(LOGD, "send_data() category_id : " + category_id + ", sub_category_id : " + sub_category_id + ", data : " + data);
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

	public int send_event(String event_id, String status) {
		logInfo(LOGD, "send_event() called.");
		logDebug(LOGD, "send_event() event_id : " + event_id + ", status : " + status);
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

	public void recv_event(String event_id, String data) {
		logInfo(LOGD, "onRecvEvent() called. event_id = " + event_id + "data = " + data);
		if (null == event_id || event_id.length() <= 0) {
			return;
		}

		/**
		 * <pre>
		 * data 내의 입력 파라이터가 있는경우 K:V표시 K사이의 구분자는 “,”
		 * 입력예) STB_SCR_RESOLUTION;STB_SCR_TV;STB_SCR_VIDEO;CtrlSeq 1080;16/9;ORG;201405161457580030
		 * </pre>
		 */

		/**
		 * data에 CtrlSeq가 포함되어 있는 경우에는 CtrlSeq의 value를 send_event의 두번째 파라미터로 전달해야함
		 */

		String CtrlSeq = null;
		if (event_id.equalsIgnoreCase(ISQMSData.EVENT_C02)) {
			// STB 인증 여부 처리
			send_data(ISQMSData.COMMON, 0, getDataCommon());
			send_event(event_id, CtrlSeq);
		} else if (event_id.equalsIgnoreCase(ISQMSData.EVENT_C03)) {
			// STB 전체 최신 Upgrade
			// 업그래이드 완료후 -> COMMON, STATUS_ALLF 업데이트 수행-> C02
			// 전부다 전달해줄 필요 없이 아래 함수들중에서 바뀐 부분들이 있는것들만 내려주면됨
			// 최신 Upgrade후 재부팅이면 전달할 필요없음.

			// ";S;0;201405161457580030"
			send_data(ISQMSData.COMMON, 0, getDataCommon());
			send_data(ISQMSData.CURRENT_STATUS, ISQMSData.STATUS_NET, getDataStatusNet());
			send_data(ISQMSData.CURRENT_STATUS, ISQMSData.STATUS_CONF, getDataStatusConf());
			send_data(ISQMSData.CURRENT_STATUS, ISQMSData.STATUS_XPG_2, getDataStatusXPG2());
			send_data(ISQMSData.CURRENT_STATUS, ISQMSData.STATUS_BBRATE, getDataStatusBbrate());
			send_event(event_id, CtrlSeq);
		} else if (event_id.equalsIgnoreCase(ISQMSData.EVENT_C04)) {
			// 604 STB 연령등급(시청제한나이) 조정
			// 등급 조정 완료 -> STATUS_CONF 업데이트 -> C04 전달
			// ";7;201405161536220046"
			// ";12;201405161536220046"
			// ";15;201405161536220046"
			// ";19;201405161536220046"
			// ";00;201405161537090047"
			send_data(ISQMSData.CURRENT_STATUS, ISQMSData.STATUS_CONF, getDataStatusConf());
			send_event(event_id, CtrlSeq);
		} else if (event_id.equalsIgnoreCase(ISQMSData.EVENT_C05)) {
			// STB 연속재생 여부 조정
			// 등급 조정 완료 -> STATUS_CONF 업데이트 -> C05 전달
			// ";0;201405161538390052" => 연속 설정 안함
			// ";1;201405161538390052" => 연속 설정
			send_data(ISQMSData.CURRENT_STATUS, ISQMSData.STATUS_CONF, getDataStatusConf());
			send_event(event_id, CtrlSeq);
		} else if (event_id.equalsIgnoreCase(ISQMSData.EVENT_C06)) {
			// STB 광고 메타파일 재 Download
			// 다운로드 완료 -> COMMON, STATUS_ALL중에서 변경된정보 업데이트 -> C06 전달
			// ";201405161538390052"

			send_event(event_id, CtrlSeq);
		} else if (event_id.equalsIgnoreCase(ISQMSData.EVENT_C07)) {
			// STB Reboot
			// ";201405161538390052"
			requestListener(MESSAGE_C07_REBOOT);
			// } else if (event_id == IsQMSData.EVENT_C08) {
			// STB HDD최적화 실행
			// 리부팅 이면 데이터/이벤트 전달 하지 않아도됨
			// ";201405161538390052"
		} else if (event_id.equalsIgnoreCase(ISQMSData.EVENT_C09)) {
			// STB 해상도 변경
			// 리부팅 이면 데이터/이벤트 전달 하지 않아도됨
			// 해상도 변경후 -> STATUS_CONF 업데이트 -> C09 전달
			// ";480i;ORG;4/3;201405161541540061"

			send_data(ISQMSData.CURRENT_STATUS, ISQMSData.STATUS_CONF, "");
			send_event(event_id, CtrlSeq);
		} else if (event_id.equalsIgnoreCase(ISQMSData.EVENT_C14)) {
			// STB비밀번호재설정
			// 번호 재설정후 -> STATUS_CONF 업데이트 -> C09 전달
			// ";3333;201405161542280062"

			send_data(ISQMSData.CURRENT_STATUS, ISQMSData.STATUS_CONF, "");
			send_event(event_id, CtrlSeq);
		} else if (event_id.equalsIgnoreCase(ISQMSData.EVENT_C15)) {
			// 성인 비밀번호재설정
			// 번호설정후 -> STATUS_CONF 업데이트 -> C09 전달
			// ";3333;201405161542280062"
			send_data(ISQMSData.CURRENT_STATUS, ISQMSData.STATUS_CONF, "");
			send_event(event_id, CtrlSeq);
		} else if (event_id.equalsIgnoreCase(ISQMSData.EVENT_C17)) {
			// 자녀시청 제한 시간 설정
			// 시간설정후 -> STATUS_CONF 업데이트 -> C09 전달
			// ";00;201405161544020066" => 설정 안함
			// ";05;201405161543390065"

			send_data(ISQMSData.CURRENT_STATUS, ISQMSData.STATUS_CONF, "");
			send_event(event_id, CtrlSeq);
		} else if (event_id.equalsIgnoreCase(ISQMSData.EVENT_C18)) {
			// 성인 메뉴 표시 여부
			// 사용여부 설정후 -> STATUS_CONF 업데이트 -> C09 전달
			// ";0;201405161546330068"
			// ";1;201405161546330068"
			send_data(ISQMSData.CURRENT_STATUS, ISQMSData.STATUS_CONF, "");
			send_event(event_id, CtrlSeq);
		}
		// else if (event_id == IsQMSData.EVENT_C77) {
		// // 모든 제어 명령의 진행 강제 취소 요청
		// }
	}

	// =========================================================================
	// < create IsQMS Data
	// =========================================================================
	/** COMMON */
	private String getDataCommon() {
		logInfo(LOGD, "getDataCommon() called.");
		StringBuilder builder = new StringBuilder();

		// builder.append(";");
		/** EVENT_ID */
		// if (null != mIsQMSCommon.EVENT_ID) {
		// builder.append(mIsQMSCommon.EVENT_ID);
		// }
		// builder.append(";");
		/** EVENT_TS */
		// if (null != mIsQMSCommon.EVENT_TS) {
		// builder.append(mIsQMSCommon.EVENT_TS);
		// }
		builder.append(";");
		/** STB_VER */
		if (null != mIsQMSCommon.STB_VER) {
			builder.append(mIsQMSCommon.STB_VER);
		}
		builder.append(";");
		/** STB_ID */
		if (null != mIsQMSCommon.STB_ID) {
			builder.append(mIsQMSCommon.STB_ID);
		}
		builder.append(";");
		/** STB_MAC */
		if (null != mIsQMSCommon.STB_MAC) {
			builder.append(mIsQMSCommon.STB_MAC);
		}
		builder.append(";");
		/** STB_SW_VER */
		if (null != mIsQMSCommon.STB_SW_VER) {
			builder.append(mIsQMSCommon.STB_SW_VER);
		}
		builder.append(";");
		/** STB_XPG_VER */
		if (null != mIsQMSCommon.STB_XPG_VER) {
			builder.append(mIsQMSCommon.STB_XPG_VER);
		}
		builder.append(";");
		/** STB_MODEL */
		if (null != mIsQMSCommon.STB_MODEL) {
			builder.append(mIsQMSCommon.STB_MODEL);
		}
		builder.append(";");
		/** STB_AUTH */
		if (null != mIsQMSCommon.STB_AUTH) {
			builder.append(mIsQMSCommon.STB_AUTH);
		}
		builder.append(";");
		/** STB_IPTV_AREA */
		if (null != mIsQMSCommon.STB_IPTV_AREA) {
			builder.append(mIsQMSCommon.STB_IPTV_AREA);
		}
		builder.append(";");
		/** STB_SVC_MODE */
		if (null != mIsQMSCommon.STB_SVC_MODE) {
			builder.append(mIsQMSCommon.STB_SVC_MODE);
		}

		return builder.toString();
	}

	/** STATUS_NET */
	private String getDataStatusNet() {
		logInfo(LOGD, "getDataStatusNet() called.");
		StringBuilder builder = new StringBuilder();

		builder.append(";");
		// /** S_NETWORK_MODE */
		// if (null != mIsQMSCurrentStatusNet.S_NETWORK_MODE) {
		// builder.append(mIsQMSCurrentStatusNet.S_NETWORK_MODE);
		// }
		builder.append(";");
		/** S_NET_DHCP_MODE */
		if (null != mIsQMSStatusNet.S_NET_DHCP_MODE) {
			builder.append(mIsQMSStatusNet.S_NET_DHCP_MODE);
		}
		builder.append(";");
		/** S_NET_IPADDR */
		if (null != mIsQMSStatusNet.S_NET_IPADDR) {
			builder.append(mIsQMSStatusNet.S_NET_IPADDR);
		}
		builder.append(";");
		/** S_NET_IPMASK */
		if (null != mIsQMSStatusNet.S_NET_IPMASK) {
			builder.append(mIsQMSStatusNet.S_NET_IPMASK);
		}
		builder.append(";");
		/** S_NET_IPGW */
		if (null != mIsQMSStatusNet.S_NET_IPGW) {
			builder.append(mIsQMSStatusNet.S_NET_IPGW);
		}
		builder.append(";");
		/** S_NET_DNS1 */
		if (null != mIsQMSStatusNet.S_NET_DNS1) {
			builder.append(mIsQMSStatusNet.S_NET_DNS1);
		}
		builder.append(";");
		/** S_NET_DNS2 */
		if (null != mIsQMSStatusNet.S_NET_DNS2) {
			builder.append(mIsQMSStatusNet.S_NET_DNS2);
		}

		return builder.toString();
	}

	/** STATUS_CONF */
	private String getDataStatusConf() {
		logInfo(LOGD, "getDataStatusConf() called.");
		StringBuilder builder = new StringBuilder();

		builder.append(";");
		/** STB_SCR_RESOLUTION */
		if (null != mIsQMSStatusConf.STB_SCR_RESOLUTION) {
			builder.append(mIsQMSStatusConf.STB_SCR_RESOLUTION);
		}
		builder.append(";");
		/** STB_SCR_TV */
		if (null != mIsQMSStatusConf.STB_SCR_TV) {
			builder.append(mIsQMSStatusConf.STB_SCR_TV);
		}
		builder.append(";");
		/** STB_SCR_VIDEO */
		if (null != mIsQMSStatusConf.STB_SCR_VIDEO) {
			builder.append(mIsQMSStatusConf.STB_SCR_VIDEO);
		}
		builder.append(";");
		/** STB_ADULT */
		if (null != mIsQMSStatusConf.STB_ADULT) {
			builder.append(mIsQMSStatusConf.STB_ADULT);
		}
		builder.append(";");
		/** STB_AGE_LIMIT */
		if (null != mIsQMSStatusConf.STB_AGE_LIMIT) {
			builder.append(mIsQMSStatusConf.STB_AGE_LIMIT);
		}
		builder.append(";");
		/** STB_AGE_TIME */
		if (null != mIsQMSStatusConf.STB_AGE_TIME) {
			builder.append(mIsQMSStatusConf.STB_AGE_TIME);
		}
		builder.append(";");
		/** STB_AUTONEXT */
		if (null != mIsQMSStatusConf.STB_AUTONEXT) {
			builder.append(mIsQMSStatusConf.STB_AUTONEXT);
		}

		return builder.toString();
	}

	/** STATUS_XPG2 */
	private String getDataStatusXPG2() {
		logInfo(LOGD, "getDataStatusXPG2() called.");
		StringBuilder builder = new StringBuilder();

		builder.append(";");
		/** XPG_FULL */
		if (null != mIsQMSStatusXPG2.XPG_FULL) {
			builder.append(mIsQMSStatusXPG2.XPG_FULL);
		}
		builder.append(";");
		// /** XPG_CONTENT */
		// if (null != mIsQMSCurrentStatusXPG2.XPG_CONTENT) {
		// builder.append(mIsQMSCurrentStatusXPG2.XPG_CONTENT);
		// }
		builder.append(";");
		// /** XPG_MENU */
		// if (null != mIsQMSCurrentStatusXPG2.XPG_MENU) {
		// builder.append(mIsQMSCurrentStatusXPG2.XPG_MENU);
		// }
		builder.append(";");
		// /** XPG_IPTV_MENU */
		// if (null != mIsQMSCurrentStatusXPG2.XPG_IPTV_MENU) {
		// builder.append(mIsQMSCurrentStatusXPG2.XPG_IPTV_MENU);
		// }
		builder.append(";");
		// /** XPG_IMAGE */
		// if (null != mIsQMSCurrentStatusXPG2.XPG_IMAGE) {
		// builder.append(mIsQMSCurrentStatusXPG2.XPG_IMAGE);
		// }
		builder.append(";");
		// /** XPG_THUMBNAIL */
		// if (null != mIsQMSCurrentStatusXPG2.XPG_THUMBNAIL) {
		// builder.append(mIsQMSCurrentStatusXPG2.XPG_THUMBNAIL);
		// }
		builder.append(";");
		// /** XPG_PRECONTENT */
		// if (null != mIsQMSCurrentStatusXPG2.XPG_PRECONTENT) {
		// builder.append(mIsQMSCurrentStatusXPG2.XPG_PRECONTENT);
		// }

		return builder.toString();
	}

	/** STATUS_BBRATE */
	private String getDataStatusBbrate() {
		logInfo(LOGD, "getDataStatusBbrate() called.");
		StringBuilder builder = new StringBuilder();

		builder.append(";");
		/** BBRATE */
		// if (null != mIsQMSStatusBbrate.BBRATE) {
		// builder.append(mIsQMSStatusBbrate.BBRATE);
		// }

		return builder.toString();
	}

	/** STATUS_ALL */
	private String getDataStatusAll() {
		logInfo(LOGD, "getDataStatusAll() called.");
		StringBuilder builder = new StringBuilder();

		builder.append(getDataStatusNet());
		builder.append(getDataStatusConf());
		builder.append(getDataStatusXPG2());
		builder.append(getDataStatusBbrate());

		return builder.toString();
	}

	/** CHECK_UPG_C_SW_UPGRADE */
	private String getDataCheckSwUpgrade() {
		logInfo(LOGD, "getDataCheckSwUpgrade() called.");
		StringBuilder builder = new StringBuilder();

		builder.append(";");
		/** UPG_C_SW_UPGRADE */
		if (null != mIsQMSCheckUPG.UPG_C_SW_UPGRADE) {
			builder.append(mIsQMSCheckUPG.UPG_C_SW_UPGRADE);
		}

		return builder.toString();
	}

	/** CHECK_UPG_C_CH_UPGRADE */
	private String getDataCheckChUpgrade() {
		logInfo(LOGD, "getDataCheckChUpgrade() called.");
		StringBuilder builder = new StringBuilder();

		builder.append(";");
		/** UPG_C_CH_UPGRADE */
		if (null != mIsQMSCheckUPG.UPG_C_CH_UPGRADE) {
			builder.append(mIsQMSCheckUPG.UPG_C_CH_UPGRADE);
		}

		return builder.toString();
	}

	/** CHECK_UPG */
	private String getDataCheckUPG() {
		logInfo(LOGD, "getDataCheckUPG() called.");
		StringBuilder builder = new StringBuilder();

		builder.append(getDataCheckSwUpgrade());
		builder.append(getDataCheckChUpgrade());

		return builder.toString();
	}

	/**
	 * <pre>
	 * Data Define :
	 * YYMMDDhhmmss
	 * 
	 * Data Define Description :
	 * 현재 이벤트를 처리/보고한 STB시각
	 * 12자리로 구성된 날자+시간
	 * </pre>
	 * 
	 * @return EVENT_TS
	 */
	private String getEventTS() {
		String eventTS = ISQMSUtil.toDateFormat("YYMMDDhhmmss", new Date());
		return eventTS;
	}

	// =========================================================================
	// < setter IsQMS COMMON
	// =========================================================================
	/**
	 * <pre>
	 * Data Define :
	 * X.X
	 * 
	 * Data Define Description :
	 * 현재 STB의 구성 버전 (Legacy/IPTV2.0)
	 * </pre>
	 */
	public void setStbVersion(String stbVersion) { // IsQMSData.ISQMS_STRING_TAG_STB_VER;
		logDebug(LOGD, "setStbVersion() called. stbVersion : " + stbVersion);
		mIsQMSCommon.STB_VER = stbVersion;
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
	public void setStbId(String stbId) { // String value = STBAPIManager.getInstance().getSTBId();
		logDebug(LOGD, "setStbId() called. stbId : " + stbId);
		if (null != stbId) {
			stbId = stbId.replace("{", "");
			stbId = stbId.replace("}", "");
		}
		mIsQMSCommon.STB_ID = stbId;
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
	public void setMacAddress(String macAddress) { // String value = STBAPIManager.getInstance().getMacAddress();
		logDebug(LOGD, "setMacAddress() called. macAddress : " + macAddress);
		if (null != macAddress) {
			macAddress = macAddress.replace("{", "");
			macAddress = macAddress.replace("}", "");
			macAddress = macAddress.replace(":", "");
		}
		mIsQMSCommon.STB_MAC = macAddress;
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
	public void setSwVersion(String swVersion) {
		logDebug(LOGD, "setSwVersion() called. swVersion : " + swVersion);
		mIsQMSCommon.STB_SW_VER = swVersion;
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
	public void setXpgVersion(String xpgVersion) {
		logDebug(LOGD, "setXpgVersion() called. xpgVersion : " + xpgVersion);
		mIsQMSCommon.STB_XPG_VER = xpgVersion;
	}

	/**
	 * <pre>
	 * Data Define :
	 * XXXXXXXXXX
	 * 
	 * Data Define Description :
	 * STB장비의 HW모델 10자리 문자
	 * </pre>
	 */
	public void setModelName(String modelName) {
		logDebug(LOGD, "setModelName() called. modelName : " + modelName);
		mIsQMSCommon.STB_MODEL = modelName;
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
	public void setStbAuth(boolean isSTBAuth) {
		logDebug(LOGD, "setStbAuth() called. isSTBAuth : " + isSTBAuth);
		mIsQMSCommon.STB_AUTH = Boolean.toString(isSTBAuth);
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
	public void setIptvArea(String iptvArea) {
		logDebug(LOGD, "setIptvArea() called. iptvArea : " + iptvArea);
		mIsQMSCommon.STB_IPTV_AREA = iptvArea;
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
	public void setSVCMode(ISQMSEnumData.eSCV_MODE scv_MODE) {
		logDebug(LOGD, "setSVCMode() called. scv_MODE : " + scv_MODE);
		if (null == scv_MODE) {
			return;
		}

		String scvModeName = scv_MODE.name();
		scvModeName = scvModeName.replace(ISQMSEnumData.PREFIX_SCV_MODE, "");
		mIsQMSCommon.STB_SVC_MODE = scvModeName;
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
	 * DHCP 사용 설정 정보
	 * {0:사용안함|1:사용함}
	 * </pre>
	 */
	public void setNetDhcpMode(boolean isDhcpMode) {
		logDebug(LOGD, "setNetDhcpMode() called. isDhcpMode : " + isDhcpMode);
		if (true == isDhcpMode) {
			mIsQMSStatusNet.S_NET_DHCP_MODE = ISQMSData.RESULT_TRUE;
		} else {
			mIsQMSStatusNet.S_NET_DHCP_MODE = ISQMSData.RESULT_FALSE;
		}
		mIsQMSCheckNet.S_NET_DHCP_MODE = mIsQMSStatusNet.S_NET_DHCP_MODE;
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
	public void setNetIpAddr(String netIpAddr) {
		logDebug(LOGD, "setNetIpAddr() called. netIpAddr : " + netIpAddr);
		mIsQMSStatusNet.S_NET_IPADDR = netIpAddr;
		mIsQMSCheckNet.S_NET_IPADDR = mIsQMSStatusNet.S_NET_IPADDR;
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
	public void setNetIpMask(String netIpMask) {
		logDebug(LOGD, "setNetIpMask() called. netIpMask : " + netIpMask);
		mIsQMSStatusNet.S_NET_IPMASK = netIpMask;
		mIsQMSCheckNet.S_NET_IPMASK = mIsQMSStatusNet.S_NET_IPMASK;
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
	public void setNetIpGateway(String netIpGateway) {
		logDebug(LOGD, "setNetIpGateway() called. netIpGateway : " + netIpGateway);
		mIsQMSStatusNet.S_NET_IPGW = netIpGateway;
		mIsQMSCheckNet.S_NET_IPGW = mIsQMSStatusNet.S_NET_IPGW;
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
	public void setNetDNS1(String netDNS1) {
		logDebug(LOGD, "setNetDNS1() called. netDNS1 : " + netDNS1);
		mIsQMSStatusNet.S_NET_DNS1 = netDNS1;
		mIsQMSCheckNet.S_NET_DNS1 = mIsQMSStatusNet.S_NET_DNS1;
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
	public void setNetDNS2(String netDNS2) {
		logDebug(LOGD, "setNetDNS2() called. netDNS2 : " + netDNS2);
		mIsQMSStatusNet.S_NET_DNS2 = netDNS2;
		mIsQMSCheckNet.S_NET_DNS2 = mIsQMSStatusNet.S_NET_DNS2;
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
	public void setStbScreenResolution(eDISPLAY_MODE display_MODE) {
		logDebug(LOGD, "setStbScreenResolution() called. display_MODE : " + display_MODE);
		if (null == display_MODE) {
			return;
		}

		String displayMode = display_MODE.name().replace(ISQMSEnumData.PREFIX_DISPLAY_MODE, "");
		mIsQMSStatusConf.STB_SCR_RESOLUTION = displayMode;
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
	public void setStbScreenTVRate(eTV_RATE_MODE tv_RATE_MODE) {
		logDebug(LOGD, "setStbScreenTVRate() called. display_MODE : " + tv_RATE_MODE);
		if (null == tv_RATE_MODE) {
			return;
		}

		String rateMode = tv_RATE_MODE.name().replace(ISQMSEnumData.PREFIX_TV_RATE_MODE, "");
		mIsQMSStatusConf.STB_SCR_TV = rateMode;
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
	public void setStbScreenVideoRate(eVIDEO_RATE_MODE video_RATE_MODE) {
		logDebug(LOGD, "setStbScreenVideoRate() called. video_RATE_MODE : " + video_RATE_MODE);
		if (null == video_RATE_MODE) {
			return;
		}

		String videoRateMode = video_RATE_MODE.name().replace(ISQMSEnumData.PREFIX_VIDEO_RATE_MODE, "");
		mIsQMSStatusConf.STB_SCR_VIDEO = videoRateMode;
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
	public void setAllowStbAdult(boolean isAllowSTBAdult) {
		logDebug(LOGD, "setAllowStbAdult() called. isAllowSTBAdult : " + isAllowSTBAdult);
		if (true == isAllowSTBAdult) {
			mIsQMSStatusConf.STB_ADULT = ISQMSData.RESULT_TRUE;
		} else {
			mIsQMSStatusConf.STB_ADULT = ISQMSData.RESULT_FALSE;
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
	public void setAgeLimit(eAGE_LIMIT_TYPE age_LIMIT_TYPE) {
		logDebug(LOGD, "setAgeLimit() called. age_LIMIT_TYPE : " + age_LIMIT_TYPE);
		if (null == age_LIMIT_TYPE) {
			return;
		}

		String ageLimitType = age_LIMIT_TYPE.name().replace(ISQMSEnumData.PREFIX_AGE_LIMIT_TYPE, "");
		mIsQMSStatusConf.STB_AGE_LIMIT = ageLimitType;
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
	public void setChildLimitTime(String childLimitTime) {
		logDebug(LOGD, "setChildLimitTime() called. childLimitTime : " + childLimitTime);
		mIsQMSStatusConf.STB_AGE_TIME = childLimitTime;
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
	public void setAutoNext(boolean isAutoNext) {
		logDebug(LOGD, "setAutoNext() called. isAutoNext : " + isAutoNext);
		if (true == isAutoNext) {
			mIsQMSStatusConf.STB_AUTONEXT = ISQMSData.RESULT_TRUE;
		} else {
			mIsQMSStatusConf.STB_AUTONEXT = ISQMSData.RESULT_FALSE;
		}
	}

	// =========================================================================
	// < setter CHECK RESULT XPG2
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
	public void setXPG2XpgFullVersion(String xpgFullVersion) {
		logDebug(LOGD, "setXPG2XpgFullVersion() called. xpgFullVersion : " + xpgFullVersion);
		mIsQMSStatusXPG2.XPG_FULL = xpgFullVersion;
	}

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
	public void setUPGSwUpgrade(eUPG_UPGRADE upg_UPGRADE) {
		logDebug(LOGD, "setUPGSwUpgrade() called. upg_UPGRADE : " + upg_UPGRADE);
		if (null == upg_UPGRADE) {
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
		mIsQMSCheckUPG.UPG_C_SW_UPGRADE = upgSwUpgrade;
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
	public void setUPGChannelUpgrade(eUPG_UPGRADE upg_UPGRADE) {
		logDebug(LOGD, "setUPGChannelUpgrade() called. upg_UPGRADE : " + upg_UPGRADE);
		if (null == upg_UPGRADE) {
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
		mIsQMSCheckUPG.UPG_C_CH_UPGRADE = upgChannelUpgrade;
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
	public void setSVCVodCid(String vodCid) {
		logDebug(LOGD, "setSVCVodCid() called. vodCid : " + vodCid);
		mIsQMSCheckSVC.SVC_C_VOD_CID = vodCid;
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
	public void setSVCVodAid(String vodAid) {
		logDebug(LOGD, "setSVCVodAid() called. vodAid : " + vodAid);
		mIsQMSCheckSVC.SVC_C_VOD_AID = vodAid;
	}

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
	public void setVOD1VodScsIp(String vodScsIp) {
		logDebug(LOGD, "setVOD1VodScsIp() called. vodScsIp : " + vodScsIp);
		mIsQMSCheckVOD1.VOD1_C_VOD_SCS_IP = vodScsIp;
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
	public void setVOD1VodScsRt(String vodScsRt) {
		logDebug(LOGD, "setVOD1VodScsRt() called. vodScsRt : " + vodScsRt);
		mIsQMSCheckVOD1.VOD1_C_VOD_SCS_RT = vodScsRt;
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
	public void setVOD1VodDownRt(String vodDownRt) {
		logDebug(LOGD, "setVOD1VodDownRt() called. vodDownRt : " + vodDownRt);
		mIsQMSCheckVOD1.VOD1_C_VOD_DOWN_RT = vodDownRt;
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
	public void setVOD3VodContentName(String vodContentName) {
		logDebug(LOGD, "setVOD3VodContentName() called. vodContentName : " + vodContentName);
		mIsQMSCheckVOD3.VOD3_C_VOD_CONTENT_NAME = vodContentName;
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
	public void setVOD3VodContentUrl(String vodContentUrl) {
		logDebug(LOGD, "setVOD3VodContentUrl() called. vodContentUrl : " + vodContentUrl);
		mIsQMSCheckVOD3.VOD3_C_VOD_CONTENT_URL = vodContentUrl;
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
	 * 0:실패 C_MSG 에러 정보
	 * </pre>
	 */
	public void setVOD4VodError(String vodError) {
		logDebug(LOGD, "setVOD4VodError() called. vodError : " + vodError);
		mIsQMSCheckVOD4.VOD4_C_VOD_ERR = vodError;
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
	public void setVOD4Message(String vodMessage) {
		logDebug(LOGD, "setVOD4Message() called. vodMessage : " + vodMessage);
		mIsQMSCheckVOD4.VOD4_C_MSG = vodMessage;
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
	public void setIPTV1IptvChNum(String iptvChNum) {
		logDebug(LOGD, "setIPTV1IptvChNum() called. iptvChNum : " + iptvChNum);
		mIsQMSCheckIPTV1.IPTV1_C_IPTV_CH_NUM = iptvChNum;
	}

	/**
	 * <pre>
	 * Data Define :
	 * XX
	 * 
	 * Data Define Description :
	 * 채널진입방법
	 * {01:직접입력|02:채널버튼|03:miniEPG|04:전체EPG:|05EPG}
	 * </pre>
	 */
	public void setIPTV1iptvChMode(String iptvChMode) {
		logDebug(LOGD, "setIPTV1iptvChMode() called. iptvChMode : " + iptvChMode);
		mIsQMSCheckIPTV1.IPTV1_C_IPTV_CH_MODE = iptvChMode;
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
	public void setIPTV2iptvErrorCode(String iptvErrorCode) {
		logDebug(LOGD, "setIPTV2iptvErrorCode() called. iptvErrorCode : " + iptvErrorCode);
		mIsQMSCheckIPTV2.IPTV2_C_IPTV_ECODE = iptvErrorCode;
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
	public void setSCSscsIp(String scsIp) {
		logDebug(LOGD, "setSCSscsIp() called. scsIp : " + scsIp);
		mIsQMSCheckSCS.SCS_C_SCS_IP = scsIp;
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
	public void setSCSscsErrorCode(String scsErrorCode) {
		logDebug(LOGD, "setSCSscsErrorCode() called. scsErrorCode : " + scsErrorCode);
		mIsQMSCheckSCS.SCS_C_SCS_ECODE = scsErrorCode;
	}

	// =========================================================================
	// < setter CHECK RESULT LGS
	// =========================================================================
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
	public void setLGSlgsErrorCode(String lgsErrorCode) {
		logDebug(LOGD, "setLGSlgsErrorCode() called. lgsErrorCode : " + lgsErrorCode);
		mIsQMSCheckLGS.LGS_C_LGS_ECODE = lgsErrorCode;
	}

	// =========================================================================
	// < setter IsQMS LISTENER
	// =========================================================================

	public void setRecentAllUpgradeListener(OnRecentAllUpgradeListener recentAllUpgradeListener) {
		logDebug(LOGD, "setRecentAllUpgradeListener() called");
		this.mRecentAllUpgradeListener = recentAllUpgradeListener;
	}

	public void setAgeLimitChangeListener(OnAgeLimitChangeListener ageLimitChangeListener) {
		logDebug(LOGD, "setAgeLimitChangeListener() called");
		this.mAgeLimitChangeListener = ageLimitChangeListener;
	}

	public void setAutoNextChangeListener(OnAutoNextChangeListener autoNextChangeListener) {
		logDebug(LOGD, "setAutoNextChangeListener() called");
		this.mAutoNextChangeListener = autoNextChangeListener;
	}

	public void setAdMetaFileDownloadListener(OnAdMetaFileDownloadListener adMetaFileDownloadListener) {
		logDebug(LOGD, "setAdMetaFileDownloadListener() called");
		this.mAdMetaFileDownloadListener = adMetaFileDownloadListener;
	}

	public void setRebootListener(OnRebootListener rebootListener) {
		logDebug(LOGD, "setRebootListener() called");
		this.mRebootListener = rebootListener;
	}

	public void setResolutionChangeListener(OnResolutionChangeListener resolutionChangeListener) {
		logDebug(LOGD, "setResolutionChangeListener() called");
		this.mResolutionChangeListener = resolutionChangeListener;
	}

	public void setStbPasswordChangeListener(OnStbPasswordChangeListener stbPasswordChangeListener) {
		logDebug(LOGD, "setStbPasswordChangeListener() called");
		this.mStbPasswordChangeListener = stbPasswordChangeListener;
	}

	public void setChildLimitPasswordChangeListener(OnChildLimitPasswordChangeListener childLimitPasswordChangeListener) {
		logDebug(LOGD, "setChildLimitPasswordChangeListener() called");
		this.mChildLimitPasswordChangeListener = childLimitPasswordChangeListener;
	}

	public void setChildLimitTimeChangeListener(OnChildLimitTimeChangeListener childLimitTimeChangeListener) {
		logDebug(LOGD, "setChildLimitTimeChangeListener() called");
		this.mChildLimitTimeChangeListener = childLimitTimeChangeListener;
	}

	public void setAdultAuthChangeListener(OnAdultAuthChangeListener adultAuthChangeListener) {
		logDebug(LOGD, "setAdultAuthChangeListener() called");
		this.mAdultAuthChangeListener = adultAuthChangeListener;
	}

	public void setSCSNormalAccessListener(OnScsNormalAccessListener scsNormalAccessListener) {
		logDebug(LOGD, "setSCSNormalAccessListener() called");
		this.mScsNormalAccessListener = scsNormalAccessListener;
	}

	public void setLGSNormalAccessListener(OnLgsNormalAccessListener lgsNormalAccessListener) {
		logDebug(LOGD, "setLGSNormalAccessListener() called");
		this.mLgsNormalAccessListener = lgsNormalAccessListener;
	}

	// =========================================================================
	// < reques IsQMS LISTENER
	// =========================================================================
	private void requestListener(int type) {
		logDebug(LOGD, "requestListener() called");
		requestListener(type, null);
	}

	private void requestListener(int type, Object data) {
		if (0 == type) {
			return;
		}

		logDebug(LOGD, "requestListener() called. eLISTENER_TYPE : " + type);
		switch (type) {
			case MESSAGE_C03_RECENT_ALL_UPGRADE:
				if (null != mRecentAllUpgradeListener) {
					mRecentAllUpgradeListener.onRecentAllUpgrade();
				} else {
					logDebug(LOGD, "requestListener() mRecentAllUpgradeListener is null");
				}
				break;
			case MESSAGE_C04_AGE_LIMIT_CHANGE:
				if (null != mAgeLimitChangeListener) {
					if (null != data && true == (data instanceof eAGE_LIMIT_TYPE)) {
						eAGE_LIMIT_TYPE age_LIMIT_TYPE = (eAGE_LIMIT_TYPE) data;
						mAgeLimitChangeListener.onAgeLimitChange(age_LIMIT_TYPE);
					} else {
						logDebug(LOGD, "requestListener() Data is Incorrect data");
					}
				} else {
					logDebug(LOGD, "requestListener() mAgeLimitChangeListener is null");
				}
				break;
			case MESSAGE_C05_AUTO_NEXT_CHANGE:
				if (null != mAutoNextChangeListener) {
					if (null != data && true == (data instanceof Boolean)) {
						Boolean result = (Boolean) data;
						mAutoNextChangeListener.onAutoNextChange(result);
					} else {
						logDebug(LOGD, "requestListener() Data is Incorrect data");
					}
				} else {
					logDebug(LOGD, "requestListener() mAutoNextChangeListener is null");
				}
				break;
			case MESSAGE_C06_ADMETA_FILE_DOWNLOAD:
				if (null != mAdMetaFileDownloadListener) {
					mAdMetaFileDownloadListener.onAdMetaFileDownload();
				} else {
					logDebug(LOGD, "requestListener() mAdMetaFileDownloadListener is null");
				}
				break;
			case MESSAGE_C07_REBOOT:
				if (null != mRebootListener) {
					mRebootListener.onReboot();
				} else {
					logDebug(LOGD, "requestListener() mRebootListener is null");
				}
				break;
			case MESSAGE_C09_RESOLUTION_CHANGE:
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
							logDebug(LOGD, "requestListener() Data is Incorrect data");
						}
					} else {
						logDebug(LOGD, "requestListener() Data is Incorrect data");
					}
				} else {
					logDebug(LOGD, "requestListener() mResolutionChangeListener is null");
				}
				break;
			case MESSAGE_C14_STB_PASSWORD_CHANGE:
				if (null != mStbPasswordChangeListener) {
					if (null != data && true == (data instanceof String) && 0 != ((String) data).length()) {
						String stbPassword = (String) data;
						mStbPasswordChangeListener.onStbPasswordChange(stbPassword);
					} else {
						logDebug(LOGD, "requestListener() Data is Incorrect data");
					}
				} else {
					logDebug(LOGD, "requestListener() mSTBPasswordChangeListener is null");
				}
				break;
			case MESSAGE_C15_CHILDLIMIT_PASSWORD_CHANGE:
				if (null != mChildLimitPasswordChangeListener) {
					if (null != data && true == (data instanceof String) && 0 != ((String) data).length()) {
						String childLimitPassword = (String) data;
						mChildLimitPasswordChangeListener.onChildLimitPasswordChange(childLimitPassword);
					} else {
						logDebug(LOGD, "requestListener() Data is Incorrect data");
					}
				} else {
					logDebug(LOGD, "requestListener() mChildLimitPasswordChangeListener is null");
				}
				break;
			case MESSAGE_C17_CHILDLIMIT_TIME_CHANGE:
				if (null != mChildLimitTimeChangeListener) {
					if (null != data && true == (data instanceof String) && 0 != ((String) data).length()) {
						String childLimitTime = (String) data;
						mChildLimitTimeChangeListener.onChildLimitTimeChange(childLimitTime);
					} else {
						logDebug(LOGD, "requestListener() Data is Incorrect data");
					}
				} else {
					logDebug(LOGD, "requestListener() mChildLimitTimeChangeListener is null");
				}
				break;
			case MESSAGE_C18_ADULT_AUTH_CHANGE:
				if (null != mAdultAuthChangeListener) {
					if (null != data && true == (data instanceof Boolean)) {
						Boolean result = (Boolean) data;
						mAdultAuthChangeListener.onAdultAuthChange(result);
					} else {
						logDebug(LOGD, "requestListener() mChildLimitTimeChangeListener is null");
					}
				} else {
					logDebug(LOGD, "requestListener() mAdultAuthChangeListener is null");
				}
				break;
			case MESSAGE_C95_SCS_NORMAL_ACCESS:
				if (null != mScsNormalAccessListener) {
					mScsNormalAccessListener.onScsNormalAccess();
				} else {
					logDebug(LOGD, "requestListener() mSCSNormalAccessListener is null");
				}
				break;
			case MESSAGE_C94_LGS_NORMAL_ACCESS:
				if (null != mLgsNormalAccessListener) {
					mLgsNormalAccessListener.onLgsNormalAccess();
				} else {
					logDebug(LOGD, "requestListener() mLGSNormalAccessListener is null");
				}
				break;

			default:
				logDebug(LOGD, "requestListener() type is default");
				break;
		}
	}

	public boolean handleMessage(Message msg) {
		logDebug(LOGD, "handleMessage() called. msg : " + msg);
		String CtrlSeq = (String) msg.obj;
		switch (msg.what) {
			case MESSAGE_C03_RECENT_ALL_UPGRADE:
				send_data(ISQMSData.COMMON, 0, getDataCommon());
				send_data(ISQMSData.CURRENT_STATUS, ISQMSData.STATUS_NET, getDataStatusNet());
				send_data(ISQMSData.CURRENT_STATUS, ISQMSData.STATUS_CONF, getDataStatusConf());
				send_data(ISQMSData.CURRENT_STATUS, ISQMSData.STATUS_XPG_2, getDataStatusXPG2());
				send_data(ISQMSData.CURRENT_STATUS, ISQMSData.STATUS_BBRATE, getDataStatusBbrate());
				send_event(ISQMSData.EVENT_C03, CtrlSeq);
				break;

			case MESSAGE_C04_AGE_LIMIT_CHANGE:
				send_data(ISQMSData.CURRENT_STATUS, ISQMSData.STATUS_CONF, getDataStatusConf());
				send_event(ISQMSData.EVENT_C04, CtrlSeq);
				break;

			case MESSAGE_C05_AUTO_NEXT_CHANGE:
				send_data(ISQMSData.CURRENT_STATUS, ISQMSData.STATUS_CONF, getDataStatusConf());
				send_event(ISQMSData.EVENT_C05, CtrlSeq);
				break;

			case MESSAGE_C06_ADMETA_FILE_DOWNLOAD:
				break;

			// case MESSAGE_C07_REBOOT:
			// break;

			case MESSAGE_C09_RESOLUTION_CHANGE:
				break;

			case MESSAGE_C14_STB_PASSWORD_CHANGE:
				break;

			case MESSAGE_C15_CHILDLIMIT_PASSWORD_CHANGE:
				break;

			case MESSAGE_C17_CHILDLIMIT_TIME_CHANGE:
				break;

			case MESSAGE_C18_ADULT_AUTH_CHANGE:
				break;

			case MESSAGE_C94_LGS_NORMAL_ACCESS:
				break;

			case MESSAGE_C95_SCS_NORMAL_ACCESS:
				break;

			default:
				break;
		}

		return false;
	}
}