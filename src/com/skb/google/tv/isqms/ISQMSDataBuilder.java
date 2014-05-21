package com.skb.google.tv.isqms;

import com.skb.google.tv.common.util.LogUtil;
import com.skb.google.tv.isqms.check.ISQMSCheckERR1;
import com.skb.google.tv.isqms.check.ISQMSCheckIPTV1;
import com.skb.google.tv.isqms.check.ISQMSCheckIPTV2;
import com.skb.google.tv.isqms.check.ISQMSCheckSVC;
import com.skb.google.tv.isqms.check.ISQMSCheckUPG;
import com.skb.google.tv.isqms.check.ISQMSCheckVOD1;
import com.skb.google.tv.isqms.check.ISQMSCheckVOD3;
import com.skb.google.tv.isqms.common.ISQMSCommon;
import com.skb.google.tv.isqms.status.ISQMSStatusCONF;
import com.skb.google.tv.isqms.status.ISQMSStatusNET;
import com.skb.google.tv.isqms.status.ISQMSStatusXPG2;

public class ISQMSDataBuilder {
	private static final String LOGD = ISQMSDataBuilder.class.getSimpleName();

	private static void logInfo(String tag, String msg) {
		if (ISQMSData.DEBUG) {
			LogUtil.info(tag, msg);
		}
	}

	// =========================================================================
	// < create IsQMS Data
	// =========================================================================
	/** COMMON */
	public static String getDataCommon() {
		logInfo(LOGD, "getDataCommon() called.");
		ISQMSCommon mISQMSCommon = ISQMSManager.getInstance().mISQMSCommon;
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
	public static String getDataStatusNet() {
		logInfo(LOGD, "getDataStatusNet() called.");
		ISQMSStatusNET mISQMSStatusNET = ISQMSManager.getInstance().mISQMSStatusNET;
		StringBuilder builder = new StringBuilder();

		builder.append(";");
		// /** S_NETWORK_MODE */
		// if (null != mIsQMSCurrentStatusNet.S_NETWORK_MODE) {
		// builder.append(mIsQMSCurrentStatusNet.S_NETWORK_MODE);
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
	public static String getDataStatusConf() {
		logInfo(LOGD, "getDataStatusConf() called.");
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
		logInfo(LOGD, "getDataStatusXPG2() called.");
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
		logInfo(LOGD, "getDataStatusBBRATE() called.");
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
		logInfo(LOGD, "getDataStatusAll() called.");
		StringBuilder builder = new StringBuilder();

		builder.append(getDataStatusNet());
		builder.append(getDataStatusConf());
		builder.append(getDataStatusXPG2());
		builder.append(getDataStatusBBRATE());

		return builder.toString();
	}

	/** CHECK_UPG_C_SW_UPGRADE */
	public static String getDataCheckSwUpgrade() {
		logInfo(LOGD, "getDataCheckSwUpgrade() called.");
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
		logInfo(LOGD, "getDataCheckChUpgrade() called.");
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
		logInfo(LOGD, "getDataCheckSVC() called.");
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
	public static String getDataCheckVOD1() {
		logInfo(LOGD, "getDataCheckVOD1() called.");
		ISQMSCheckVOD1 mISQMSCheckVOD1 = ISQMSManager.getInstance().mISQMSCheckVOD1;
		StringBuilder builder = new StringBuilder();

		builder.append(";");
		/** VOD1_C_VOD_SCS_IP */
		if (null != mISQMSCheckVOD1.VOD1_C_VOD_SCS_IP) {
			builder.append(mISQMSCheckVOD1.VOD1_C_VOD_SCS_IP);
		}
		builder.append(";");
		/** VOD1_C_VOD_SCS_RT */
		if (null != mISQMSCheckVOD1.VOD1_C_VOD_SCS_RT) {
			builder.append(mISQMSCheckVOD1.VOD1_C_VOD_SCS_RT);
		}
		builder.append(";");
		/** VOD1_C_VOD_DOWN_IP */
		// if (null != mISQMSCheckVOD1.VOD1_C_VOD_DOWN_IP) {
		// builder.append(mISQMSCheckVOD1.VOD1_C_VOD_DOWN_IP);
		// }
		builder.append(";");
		/** VOD1_C_VOD_DOWN_RT */
		if (null != mISQMSCheckVOD1.VOD1_C_VOD_DOWN_RT) {
			builder.append(mISQMSCheckVOD1.VOD1_C_VOD_DOWN_RT);
		}

		return builder.toString();
	}

	/** CHECK_VOD3 */
	public static String getDataCheckVOD3() {
		logInfo(LOGD, "getDataCheckVOD3() called.");
		ISQMSCheckVOD3 mISQMSCheckVOD3 = ISQMSManager.getInstance().mISQMSCheckVOD3;
		StringBuilder builder = new StringBuilder();

		builder.append(";");
		/** VOD3_C_VOD_CONTENT_NAME */
		if (null != mISQMSCheckVOD3.VOD3_C_VOD_CONTENT_NAME) {
			builder.append(mISQMSCheckVOD3.VOD3_C_VOD_CONTENT_NAME);
		}
		builder.append(";");
		/** VOD3_C_VOD_CONTENT_URL */
		if (null != mISQMSCheckVOD3.VOD3_C_VOD_CONTENT_URL) {
			builder.append(mISQMSCheckVOD3.VOD3_C_VOD_CONTENT_URL);
		}

		return builder.toString();
	}

	/** CHECK_IPTV1 */
	public static String getDataCheckIPTV1() {
		logInfo(LOGD, "getDataCheckIPTV1() called.");
		ISQMSCheckIPTV1 mISQMSCheckIPTV1 = ISQMSManager.getInstance().mISQMSCheckIPTV1;
		StringBuilder builder = new StringBuilder();

		builder.append(";");
		/** IPTV1_C_IPTV_CH_NUM */
		if (null != mISQMSCheckIPTV1.IPTV1_C_IPTV_CH_NUM) {
			builder.append(mISQMSCheckIPTV1.IPTV1_C_IPTV_CH_NUM);
		}
		builder.append(";");
		/** IPTV1_C_IPTV_CH_MODE */
		if (null != mISQMSCheckIPTV1.IPTV1_C_IPTV_CH_MODE) {
			builder.append(mISQMSCheckIPTV1.IPTV1_C_IPTV_CH_MODE);
		}

		return builder.toString();
	}

	/** CHECK_IPTV2 */
	public static String getDataCheckIPTV2() {
		logInfo(LOGD, "getDataCheckIPTV2() called.");
		ISQMSCheckIPTV2 mISQMSCheckIPTV2 = ISQMSManager.getInstance().mISQMSCheckIPTV2;
		StringBuilder builder = new StringBuilder();

		builder.append(";");
		/** IPTV2_C_IPTV_ECODE */
		if (null != mISQMSCheckIPTV2.IPTV2_C_IPTV_ECODE) {
			builder.append(mISQMSCheckIPTV2.IPTV2_C_IPTV_ECODE);
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
		logInfo(LOGD, "getDataCheckERR1() called.");
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
}
