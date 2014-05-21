package com.skb.google.tv.isqms;

import android.util.SparseArray;

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
import com.skb.google.tv.isqms.status.ISQMSStatusCONF;
import com.skb.google.tv.isqms.status.ISQMSStatusNET;
import com.skb.google.tv.isqms.status.ISQMSStatusXPG2;

public class ISQMSDataBuilder {
	private static final String LOGD = ISQMSDataBuilder.class.getSimpleName();

	// =========================================================================
	// < create IsQMS Data
	// =========================================================================
	/** COMMON */
	public static String getDataCommon() {
		ISQMSUtil.info(LOGD, "getDataCommon() called.");
		ISQMSCommon mISQMSCommon = ISQMSManager.getInstance().mISQMSCommon;
		StringBuilder builder = new StringBuilder();

		// builder.append(";");
		/** EVENT_ID */
		// if (null != mISQMSCommon.EVENT_ID) {
		// builder.append(mISQMSCommon.EVENT_ID);
		// }
		// builder.append(";");
		/** EVENT_TS */
		mISQMSCommon.EVENT_TS = ISQMSManager.getEventTS();
		if (null != mISQMSCommon.EVENT_TS) {
			builder.append(mISQMSCommon.EVENT_TS);
		}
		builder.append(";");
		/** STB_VER */
		if (null != mISQMSCommon.STB_VER) {
			builder.append(mISQMSCommon.STB_VER);
		}
		builder.append(";");
		/** STB_ID */
		if (null != mISQMSCommon.STB_ID) {
			builder.append(mISQMSCommon.STB_ID);
		}
		builder.append(";");
		/** STB_MAC */
		if (null != mISQMSCommon.STB_MAC) {
			builder.append(mISQMSCommon.STB_MAC);
		}
		builder.append(";");
		/** STB_SW_VER */
		if (null != mISQMSCommon.STB_SW_VER) {
			builder.append(mISQMSCommon.STB_SW_VER);
		}
		builder.append(";");
		/** STB_XPG_VER */
		if (null != mISQMSCommon.STB_XPG_VER) {
			builder.append(mISQMSCommon.STB_XPG_VER);
		}
		builder.append(";");
		/** STB_MODEL */
		if (null != mISQMSCommon.STB_MODEL) {
			builder.append(mISQMSCommon.STB_MODEL);
		}
		builder.append(";");
		/** STB_AUTH */
		if (null != mISQMSCommon.STB_AUTH) {
			builder.append(mISQMSCommon.STB_AUTH);
		}
		builder.append(";");
		/** STB_IPTV_AREA */
		if (null != mISQMSCommon.STB_IPTV_AREA) {
			builder.append(mISQMSCommon.STB_IPTV_AREA);
		}
		builder.append(";");
		/** STB_SVC_MODE */
		if (null != mISQMSCommon.STB_SVC_MODE) {
			builder.append(mISQMSCommon.STB_SVC_MODE);
		}

		return builder.toString();
	}

	/** STATUS_NET */
	public static String getDataStatusNET() {
		ISQMSUtil.info(LOGD, "getDataStatusNET() called.");
		ISQMSStatusNET mISQMSStatusNET = ISQMSManager.getInstance().mISQMSStatusNET;
		StringBuilder builder = new StringBuilder();

		builder.append(";");
		// /** S_NETWORK_MODE */
		// if (null != mISQMSStatusNET.S_NETWORK_MODE) {
		// builder.append(mISQMSStatusNET.S_NETWORK_MODE);
		// }
		builder.append(";");
		/** S_NET_DHCP_MODE */
		if (null != mISQMSStatusNET.S_NET_DHCP_MODE) {
			builder.append(mISQMSStatusNET.S_NET_DHCP_MODE);
		}
		builder.append(";");
		/** S_NET_IPADDR */
		if (null != mISQMSStatusNET.S_NET_IPADDR) {
			builder.append(mISQMSStatusNET.S_NET_IPADDR);
		}
		builder.append(";");
		/** S_NET_IPMASK */
		if (null != mISQMSStatusNET.S_NET_IPMASK) {
			builder.append(mISQMSStatusNET.S_NET_IPMASK);
		}
		builder.append(";");
		/** S_NET_IPGW */
		if (null != mISQMSStatusNET.S_NET_IPGW) {
			builder.append(mISQMSStatusNET.S_NET_IPGW);
		}
		builder.append(";");
		/** S_NET_DNS1 */
		if (null != mISQMSStatusNET.S_NET_DNS1) {
			builder.append(mISQMSStatusNET.S_NET_DNS1);
		}
		builder.append(";");
		/** S_NET_DNS2 */
		if (null != mISQMSStatusNET.S_NET_DNS2) {
			builder.append(mISQMSStatusNET.S_NET_DNS2);
		}

		return builder.toString();
	}

	/** STATUS_CONF */
	public static String getDataStatusCONF() {
		ISQMSUtil.info(LOGD, "getDataStatusCONF() called.");
		ISQMSStatusCONF mISQMSStatusCONF = ISQMSManager.getInstance().mISQMSStatusCONF;
		StringBuilder builder = new StringBuilder();

		builder.append(";");
		/** STB_SCR_RESOLUTION */
		if (null != mISQMSStatusCONF.STB_SCR_RESOLUTION) {
			builder.append(mISQMSStatusCONF.STB_SCR_RESOLUTION);
		}
		builder.append(";");
		/** STB_SCR_TV */
		if (null != mISQMSStatusCONF.STB_SCR_TV) {
			builder.append(mISQMSStatusCONF.STB_SCR_TV);
		}
		builder.append(";");
		/** STB_SCR_VIDEO */
		if (null != mISQMSStatusCONF.STB_SCR_VIDEO) {
			builder.append(mISQMSStatusCONF.STB_SCR_VIDEO);
		}
		builder.append(";");
		/** STB_ADULT */
		if (null != mISQMSStatusCONF.STB_ADULT) {
			builder.append(mISQMSStatusCONF.STB_ADULT);
		}
		builder.append(";");
		/** STB_AGE_LIMIT */
		if (null != mISQMSStatusCONF.STB_AGE_LIMIT) {
			builder.append(mISQMSStatusCONF.STB_AGE_LIMIT);
		}
		builder.append(";");
		/** STB_AGE_TIME */
		if (null != mISQMSStatusCONF.STB_AGE_TIME) {
			builder.append(mISQMSStatusCONF.STB_AGE_TIME);
		}
		builder.append(";");
		/** STB_AUTONEXT */
		if (null != mISQMSStatusCONF.STB_AUTONEXT) {
			builder.append(mISQMSStatusCONF.STB_AUTONEXT);
		}

		return builder.toString();
	}

	/** STATUS_XPG2 */
	public static String getDataStatusXPG2() {
		ISQMSUtil.info(LOGD, "getDataStatusXPG2() called.");
		ISQMSStatusXPG2 mISQMSStatusXPG2 = ISQMSManager.getInstance().mISQMSStatusXPG2;
		StringBuilder builder = new StringBuilder();

		builder.append(";");
		/** XPG_FULL */
		if (null != mISQMSStatusXPG2.XPG_FULL) {
			builder.append(mISQMSStatusXPG2.XPG_FULL);
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
	public static String getDataStatusBBRATE() {
		ISQMSUtil.info(LOGD, "getDataStatusBBRATE() called.");
		// ISQMSStatusBBRATE mISQMSStatusBBRATE = ISQMSManager.getInstance().mISQMSStatusBBRATE;
		StringBuilder builder = new StringBuilder();

		builder.append(";");
		/** BBRATE */
		// if (null != mISQMSStatusBBRATE.BBRATE) {
		// builder.append(mISQMSStatusBBRATE.BBRATE);
		// }

		return builder.toString();
	}

	/** STATUS_ALL */
	public static String getDataStatusAll() {
		ISQMSUtil.info(LOGD, "getDataStatusAll() called.");
		StringBuilder builder = new StringBuilder();

		builder.append(getDataStatusNET());
		builder.append(getDataStatusCONF());
		builder.append(getDataStatusXPG2());
		builder.append(getDataStatusBBRATE());

		return builder.toString();
	}

	/** CHECK_UPG_C_SW_UPGRADE */
	public static String getDataCheckSwUpgrade() {
		ISQMSUtil.info(LOGD, "getDataCheckSwUpgrade() called.");
		ISQMSCheckUPG mISQMSCheckUPG = ISQMSManager.getInstance().mISQMSCheckUPG;
		StringBuilder builder = new StringBuilder();

		builder.append(";");
		/** UPG_C_SW_UPGRADE */
		if (null != mISQMSCheckUPG.UPG_C_SW_UPGRADE) {
			builder.append(mISQMSCheckUPG.UPG_C_SW_UPGRADE);
		}

		return builder.toString();
	}

	/** CHECK_UPG_C_CH_UPGRADE */
	public static String getDataCheckChUpgrade() {
		ISQMSUtil.info(LOGD, "getDataCheckChUpgrade() called.");
		ISQMSCheckUPG mISQMSCheckUPG = ISQMSManager.getInstance().mISQMSCheckUPG;
		StringBuilder builder = new StringBuilder();

		builder.append(";");
		/** UPG_C_CH_UPGRADE */
		if (null != mISQMSCheckUPG.UPG_C_CH_UPGRADE) {
			builder.append(mISQMSCheckUPG.UPG_C_CH_UPGRADE);
		}

		return builder.toString();
	}

	/** CHECK_SVC */
	public static String getDataCheckSVC() {
		ISQMSUtil.info(LOGD, "getDataCheckSVC() called.");
		ISQMSCheckSVC mISQMSCheckSVC = ISQMSManager.getInstance().mISQMSCheckSVC;
		StringBuilder builder = new StringBuilder();

		builder.append(";");
		/** SVC_C_VOD_CID */
		if (null != mISQMSCheckSVC.SVC_C_VOD_CID) {
			builder.append(mISQMSCheckSVC.SVC_C_VOD_CID);
		}
		builder.append(";");
		/** SVC_C_VOD_AID */
		if (null != mISQMSCheckSVC.SVC_C_VOD_AID) {
			builder.append(mISQMSCheckSVC.SVC_C_VOD_AID);
		}
		builder.append(";");
		/** SVC_C_PSWD_STB */
		// if (null != mISQMSCheckSVC.SVC_C_PSWD_STB) {
		// builder.append(mISQMSCheckSVC.SVC_C_PSWD_STB);
		// }
		builder.append(";");
		/** SVC_C_PSWD_AGE */
		// if (null != mISQMSCheckSVC.SVC_C_PSWD_AGE) {
		// builder.append(mISQMSCheckSVC.SVC_C_PSWD_AGE);
		// }

		return builder.toString();
	}

	/** CHECK_VOD1 */
	public static String getDataCheckVOD1(Integer key) {
		ISQMSUtil.info(LOGD, "getDataCheckVOD1() called.");
		StringBuilder builder = new StringBuilder();
		SparseArray<ISQMSCheckVOD> mISQMSCheckVODList = ISQMSManager.getInstance().mISQMSCheckVODList;
		ISQMSCheckVOD mISQMSCheckVOD = mISQMSCheckVODList.get(key);
		if (null == mISQMSCheckVOD) {
			ISQMSUtil.debug(LOGD, "getDataCheckVOD1() mISQMSCheckVOD is null");
			builder.append(";");
			builder.append(";");
			builder.append(";");
			builder.append(";");
			return builder.toString();
		}

		builder.append(";");
		/** VOD1_C_VOD_SCS_IP */
		if (null != mISQMSCheckVOD.VOD1_C_VOD_SCS_IP) {
			builder.append(mISQMSCheckVOD.VOD1_C_VOD_SCS_IP);
		}
		builder.append(";");
		/** VOD1_C_VOD_SCS_RT */
		if (null != mISQMSCheckVOD.VOD1_C_VOD_SCS_RT) {
			builder.append(mISQMSCheckVOD.VOD1_C_VOD_SCS_RT);
		}
		builder.append(";");
		/** VOD1_C_VOD_DOWN_IP */
		// if (null != mISQMSCheckVOD.VOD1_C_VOD_DOWN_IP) {
		// builder.append(mISQMSCheckVOD.VOD1_C_VOD_DOWN_IP);
		// }
		builder.append(";");
		/** VOD1_C_VOD_DOWN_RT */
		if (null != mISQMSCheckVOD.VOD1_C_VOD_DOWN_RT) {
			builder.append(mISQMSCheckVOD.VOD1_C_VOD_DOWN_RT);
		}

		return builder.toString();
	}

	/** CHECK_VOD3 */
	public static String getDataCheckVOD3(Integer key) {
		ISQMSUtil.info(LOGD, "getDataCheckVOD3() called.");
		StringBuilder builder = new StringBuilder();
		SparseArray<ISQMSCheckVOD> mISQMSCheckVODList = ISQMSManager.getInstance().mISQMSCheckVODList;
		ISQMSCheckVOD mISQMSCheckVOD = mISQMSCheckVODList.get(key);
		if (null == mISQMSCheckVOD) {
			ISQMSUtil.debug(LOGD, "getDataCheckVOD3() mISQMSCheckVOD is null");
			builder.append(";");
			builder.append(";");
			return builder.toString();
		}

		builder.append(";");
		/** VOD3_C_VOD_CONTENT_NAME */
		if (null != mISQMSCheckVOD.VOD3_C_VOD_CONTENT_NAME) {
			builder.append(mISQMSCheckVOD.VOD3_C_VOD_CONTENT_NAME);
		}
		builder.append(";");
		/** VOD3_C_VOD_CONTENT_URL */
		if (null != mISQMSCheckVOD.VOD3_C_VOD_CONTENT_URL) {
			builder.append(mISQMSCheckVOD.VOD3_C_VOD_CONTENT_URL);
		}

		return builder.toString();
	}

	/** CHECK_VOD4 */
	public static String getDataCheckVOD4(Integer key) {
		ISQMSUtil.info(LOGD, "getDataCheckVOD4() called.");
		StringBuilder builder = new StringBuilder();
		SparseArray<ISQMSCheckVOD> mISQMSCheckVODList = ISQMSManager.getInstance().mISQMSCheckVODList;
		ISQMSCheckVOD mISQMSCheckVOD = mISQMSCheckVODList.get(key);
		if (null == mISQMSCheckVOD) {
			ISQMSUtil.debug(LOGD, "getDataCheckVOD4() mISQMSCheckVOD is null");
			builder.append(";");
			builder.append(";");
			return builder.toString();
		}

		builder.append(";");
		/** VOD4_C_VOD_ERR */
		if (null != mISQMSCheckVOD.VOD4_C_VOD_ERR) {
			builder.append(mISQMSCheckVOD.VOD4_C_VOD_ERR);
		}
		builder.append(";");
		/** VOD4_C_MSG */
		if (null != mISQMSCheckVOD.VOD4_C_MSG) {
			builder.append(mISQMSCheckVOD.VOD4_C_MSG);
		}

		return builder.toString();
	}

	/** CHECK_IPTV1 */
	public static String getDataCheckIPTV1(Integer key) {
		ISQMSUtil.info(LOGD, "getDataCheckIPTV1() called.");
		StringBuilder builder = new StringBuilder();
		SparseArray<ISQMSCheckIPTV> mISQMSCheckIPTVList = ISQMSManager.getInstance().mISQMSCheckIPTVList;
		ISQMSCheckIPTV mISQMSCheckIPTV = mISQMSCheckIPTVList.get(key);
		if (null == mISQMSCheckIPTV) {
			ISQMSUtil.debug(LOGD, "getDataCheckIPTV1() mISQMSCheckIPTV is null");
			builder.append(";");
			builder.append(";");
			return builder.toString();
		}

		builder.append(";");
		/** IPTV1_C_IPTV_CH_NUM */
		if (null != mISQMSCheckIPTV.IPTV1_C_IPTV_CH_NUM) {
			builder.append(mISQMSCheckIPTV.IPTV1_C_IPTV_CH_NUM);
		}
		builder.append(";");
		/** IPTV1_C_IPTV_CH_MODE */
		if (null != mISQMSCheckIPTV.IPTV1_C_IPTV_CH_MODE) {
			builder.append(mISQMSCheckIPTV.IPTV1_C_IPTV_CH_MODE);
		}

		return builder.toString();
	}

	/** CHECK_IPTV2 */
	public static String getDataCheckIPTV2(Integer key) {
		ISQMSUtil.info(LOGD, "getDataCheckIPTV2() called.");
		StringBuilder builder = new StringBuilder();
		SparseArray<ISQMSCheckIPTV> mISQMSCheckIPTVList = ISQMSManager.getInstance().mISQMSCheckIPTVList;
		ISQMSCheckIPTV mISQMSCheckIPTV = mISQMSCheckIPTVList.get(key);
		if (null == mISQMSCheckIPTV) {
			ISQMSUtil.debug(LOGD, "getDataCheckIPTV2() mISQMSCheckIPTV is null");
			builder.append(";");
			builder.append(";");
			return builder.toString();
		}

		builder.append(";");
		/** IPTV2_C_IPTV_ECODE */
		if (null != mISQMSCheckIPTV.IPTV2_C_IPTV_ECODE) {
			builder.append(mISQMSCheckIPTV.IPTV2_C_IPTV_ECODE);
		}
		builder.append(";");
		/** IPTV2_C_MSG */
		// if (null != mISQMSCheckIPTV2.IPTV2_C_MSG) {
		// builder.append(mISQMSCheckIPTV2.IPTV2_C_MSG);
		// }

		return builder.toString();
	}

	/** CHECK_ERR1 */
	public static String getDataCheckERR1() {
		ISQMSUtil.info(LOGD, "getDataCheckERR1() called.");
		ISQMSCheckERR1 mISQMSCheckERR1 = ISQMSManager.getInstance().mISQMSCheckERR1;
		StringBuilder builder = new StringBuilder();

		builder.append(";");
		/** ERR1_ERR_DURATION */
		// if (null != mISQMSCheckERR1.ERR1_ERR_DURATION) {
		// builder.append(mISQMSCheckERR1.ERR1_ERR_DURATION);
		// }
		builder.append(";");
		/** ERR1_ERR_REPEAT */
		// if (null != mISQMSCheckERR1.ERR1_ERR_REPEAT) {
		// builder.append(mISQMSCheckERR1.ERR1_ERR_REPEAT);
		// }
		builder.append(";");
		/** ERR1_C_TS */
		// if (null != mISQMSCheckERR1.ERR1_C_TS) {
		// builder.append(mISQMSCheckERR1.ERR1_C_TS);
		// }

		return builder.toString();
	}

	/** CHECK_ERRORCODE */
	public static String getDataCheckERRORCODE() {
		ISQMSUtil.info(LOGD, "getDataCheckERRORCODE() called.");
		ISQMSCheckERRORCODE mISQMSCheckERRORCODE = ISQMSManager.getInstance().mISQMSCheckERRORCODE;
		StringBuilder builder = new StringBuilder();

		builder.append(";");
		/** ERRORCODE_C_MSG */
		// if (null != mISQMSCheckERRORCODE.ERRORCODE_C_MSG) {
		// builder.append(mISQMSCheckERRORCODE.ERRORCODE_C_MSG);
		// }

		return builder.toString();
	}

	/** CHECK_SCS */
	public static String getDataCheckSCS() {
		ISQMSUtil.info(LOGD, "getDataCheckSCS() called.");
		ISQMSCheckSCS mISQMSCheckSCS = ISQMSManager.getInstance().mISQMSCheckSCS;
		StringBuilder builder = new StringBuilder();

		builder.append(";");
		/** SCS_C_SCS_IP */
		if (null != mISQMSCheckSCS.SCS_C_SCS_IP) {
			builder.append(mISQMSCheckSCS.SCS_C_SCS_IP);
		}
		builder.append(";");
		/** SCS_C_SCS_ECODE */
		if (null != mISQMSCheckSCS.SCS_C_SCS_ECODE) {
			builder.append(mISQMSCheckSCS.SCS_C_SCS_ECODE);
		}
		builder.append(";");
		/** SCS_C_MSG */
		// if (null != mISQMSCheckSCS.SCS_C_MSG) {
		// builder.append(mISQMSCheckSCS.SCS_C_MSG);
		// }

		return builder.toString();
	}

	/** CHECK_LGS */
	public static String getDataCheckLGS() {
		ISQMSUtil.info(LOGD, "getDataCheckLGS() called.");
		ISQMSCheckLGS mISQMSCheckLGS = ISQMSManager.getInstance().mISQMSCheckLGS;
		StringBuilder builder = new StringBuilder();

		builder.append(";");
		/** LGS_C_LGS_ECODE */
		if (null != mISQMSCheckLGS.LGS_C_LGS_ECODE) {
			builder.append(mISQMSCheckLGS.LGS_C_LGS_ECODE);
		}
		builder.append(";");
		/** LGS_C_LGS_IP */
		// if (null != mISQMSCheckLGS.LGS_C_LGS_IP) {
		// builder.append(mISQMSCheckLGS.LGS_C_LGS_IP);
		// }
		builder.append(";");
		/** LGS_C_MSG */
		// if (null != mISQMSCheckLGS.LGS_C_MSG) {
		// builder.append(mISQMSCheckLGS.LGS_C_MSG);
		// }

		return builder.toString();
	}

	/** CHECK_WSCS */
	public static String getDataCheckWSCS() {
		ISQMSUtil.info(LOGD, "getDataCheckWSCS() called.");
		ISQMSCheckWSCS mISQMSCheckWSCS = ISQMSManager.getInstance().mISQMSCheckWSCS;
		StringBuilder builder = new StringBuilder();

		builder.append(";");
		/** WSCS_C_WSCS_ECODE */
		if (null != mISQMSCheckWSCS.WSCS_C_WSCS_ECODE) {
			builder.append(mISQMSCheckWSCS.WSCS_C_WSCS_ECODE);
		}
		builder.append(";");
		/** WSCS_C_WSCS_IP */
		// if (null != mISQMSCheckWSCS.WSCS_C_WSCS_IP) {
		// builder.append(mISQMSCheckWSCS.WSCS_C_WSCS_IP);
		// }
		builder.append(";");
		/** WSCS_C_MSG */
		// if (null != mISQMSCheckWSCS.WSCS_C_MSG) {
		// builder.append(mISQMSCheckWSCS.WSCS_C_MSG);
		// }

		return builder.toString();
	}

	/** CHECK_NET */
	public static String getDataCheckNET() {
		ISQMSUtil.info(LOGD, "getDataCheckNET() called.");
		ISQMSCheckNET mISQMSCheckNET = ISQMSManager.getInstance().mISQMSCheckNET;
		StringBuilder builder = new StringBuilder();

		builder.append(";");
		// /** S_NETWORK_MODE */
		// if (null != mISQMSCheckNET.S_NETWORK_MODE) {
		// builder.append(mISQMSCheckNET.S_NETWORK_MODE);
		// }
		builder.append(";");
		/** S_NET_DHCP_MODE */
		if (null != mISQMSCheckNET.S_NET_DHCP_MODE) {
			builder.append(mISQMSCheckNET.S_NET_DHCP_MODE);
		}
		builder.append(";");
		/** S_NET_IPADDR */
		if (null != mISQMSCheckNET.S_NET_IPADDR) {
			builder.append(mISQMSCheckNET.S_NET_IPADDR);
		}
		builder.append(";");
		/** S_NET_IPMASK */
		if (null != mISQMSCheckNET.S_NET_IPMASK) {
			builder.append(mISQMSCheckNET.S_NET_IPMASK);
		}
		builder.append(";");
		/** S_NET_IPGW */
		if (null != mISQMSCheckNET.S_NET_IPGW) {
			builder.append(mISQMSCheckNET.S_NET_IPGW);
		}
		builder.append(";");
		/** S_NET_DNS1 */
		if (null != mISQMSCheckNET.S_NET_DNS1) {
			builder.append(mISQMSCheckNET.S_NET_DNS1);
		}
		builder.append(";");
		/** S_NET_DNS2 */
		if (null != mISQMSCheckNET.S_NET_DNS2) {
			builder.append(mISQMSCheckNET.S_NET_DNS2);
		}

		return builder.toString();
	}
}
