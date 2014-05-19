package com.skb.isqms;

oneway interface IAgentServiceToUIApp{
	void onRecvEvent(String event_id, String data);
}
