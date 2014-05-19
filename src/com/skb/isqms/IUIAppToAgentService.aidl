package com.skb.isqms;
import com.skb.isqms.IAgentServiceToUIApp;

interface IUIAppToAgentService{
	int start_agent();
	int get_agent_status();
	int send_data(int category_id, int sub_category_id, String data);
	int send_event(String event_id, String data);
	void registerAgentCallback(IAgentServiceToUIApp callback);
	void unregisterAgentCallback(IAgentServiceToUIApp callback);
	
}