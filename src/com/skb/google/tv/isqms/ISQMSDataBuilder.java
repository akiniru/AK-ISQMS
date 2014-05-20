package com.skb.google.tv.isqms;

import com.skb.google.tv.common.util.LogUtil;
import com.skb.google.tv.isqms.check.ISQMSCheckUPG;
import com.skb.google.tv.isqms.common.ISQMSCommon;
import com.skb.google.tv.isqms.status.ISQMSStatusConf;
import com.skb.google.tv.isqms.status.ISQMSStatusNet;
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
		ISQMSStatusNet mISQMSStatusNet = ISQMSManager.getInstance().mISQMSStatusNet;
		StringBuilder builder = new StringBuilder();

		builder.append(";");
		// /** S_NETWORK_MODE */
		// if (null != mIsQMSCurrentStatusNet.S_NETWORK_MODE) {
		// builder.append(mIsQMSCurrentStatusNet.S_NETWORK_MODE);
		// }
		builder.append(";");
		/** S_NET_DHCP_MODE */
		if (null != mISQMSStatusNet.S_NET_DHCP_MODE) {
			builder.append(mISQMSStatusNet.S_NET_DHCP_MODE);
		}
		builder.append(";");
		/** S_NET_IPADDR */
		if (null != mISQMSStatusNet.S_NET_IPADDR) {
			builder.append(mISQMSStatusNet.S_NET_IPADDR);
		}
		builder.append(";");
		/** S_NET_IPMASK */
		if (null != mISQMSStatusNet.S_NET_IPMASK) {
			builder.append(mISQMSStatusNet.S_NET_IPMASK);
		}
		builder.append(";");
		/** S_NET_IPGW */
		if (null != mISQMSStatusNet.S_NET_IPGW) {
			builder.append(mISQMSStatusNet.S_NET_IPGW);
		}
		builder.append(";");
		/** S_NET_DNS1 */
		if (null != mISQMSStatusNet.S_NET_DNS1) {
			builder.append(mISQMSStatusNet.S_NET_DNS1);
		}
		builder.append(";");
		/** S_NET_DNS2 */
		if (null != mISQMSStatusNet.S_NET_DNS2) {
			builder.append(mISQMSStatusNet.S_NET_DNS2);
		}

		return builder.toString();
	}

	/** STATUS_CONF */
	public static String getDataStatusConf() {
		logInfo(LOGD, "getDataStatusConf() called.");
		ISQMSStatusConf mISQMSStatusConf = ISQMSManager.getInstance().mISQMSStatusConf;
		StringBuilder builder = new StringBuilder();

		builder.append(";");
		/** STB_SCR_RESOLUTION */
		if (null != mISQMSStatusConf.STB_SCR_RESOLUTION) {
			builder.append(mISQMSStatusConf.STB_SCR_RESOLUTION);
		}
		builder.append(";");
		/** STB_SCR_TV */
		if (null != mISQMSStatusConf.STB_SCR_TV) {
			builder.append(mISQMSStatusConf.STB_SCR_TV);
		}
		builder.append(";");
		/** STB_SCR_VIDEO */
		if (null != mISQMSStatusConf.STB_SCR_VIDEO) {
			builder.append(mISQMSStatusConf.STB_SCR_VIDEO);
		}
		builder.append(";");
		/** STB_ADULT */
		if (null != mISQMSStatusConf.STB_ADULT) {
			builder.append(mISQMSStatusConf.STB_ADULT);
		}
		builder.append(";");
		/** STB_AGE_LIMIT */
		if (null != mISQMSStatusConf.STB_AGE_LIMIT) {
			builder.append(mISQMSStatusConf.STB_AGE_LIMIT);
		}
		builder.append(";");
		/** STB_AGE_TIME */
		if (null != mISQMSStatusConf.STB_AGE_TIME) {
			builder.append(mISQMSStatusConf.STB_AGE_TIME);
		}
		builder.append(";");
		/** STB_AUTONEXT */
		if (null != mISQMSStatusConf.STB_AUTONEXT) {
			builder.append(mISQMSStatusConf.STB_AUTONEXT);
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
	public static String getDataStatusBbrate() {
		logInfo(LOGD, "getDataStatusBbrate() called.");
		// ISQMSStatusBbrate mISQMSStatusBbrate = ISQMSManager.getInstance().mISQMSStatusBbrate;
		StringBuilder builder = new StringBuilder();

		builder.append(";");
		/** BBRATE */
		// if (null != mISQMSStatusBbrate.BBRATE) {
		// builder.append(mISQMSStatusBbrate.BBRATE);
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
		builder.append(getDataStatusBbrate());

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

	/** CHECK_UPG */
	public static String getDataCheckUPG() {
		logInfo(LOGD, "getDataCheckUPG() called.");
		StringBuilder builder = new StringBuilder();

		builder.append(getDataCheckSwUpgrade());
		builder.append(getDataCheckChUpgrade());

		return builder.toString();
	}
}
