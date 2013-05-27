package com.ikimuhendis.vine4j.types;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.ikimuhendis.vine4j.VineService;
import com.ikimuhendis.vine4j.util.JSONUtil;

public class VineNotificationsResponse {

	public long nextPage;
	public long count;
	public List<VineNotification> notfications;
	public long previousPage;
	public long size;
	public long anchor;
	private String baseURL;

	public VineNotificationsResponse(JSONObject data,String baseURL) throws Exception {
		this.baseURL=baseURL;
		if (data != null) {
			nextPage = JSONUtil.getLong(data, "nextPage");
			previousPage = JSONUtil.getLong(data, "previousPage");
			size = JSONUtil.getLong(data, "size");
			anchor = JSONUtil.getLong(data, "anchor");
			count = JSONUtil.getLong(data, "count");

			JSONArray jsonNotifications = JSONUtil
					.getJSONArray(data, "records");
			notfications = new ArrayList<VineNotification>();
			if (jsonNotifications != null && jsonNotifications.size() > 0) {
				for (int i = 0; i < jsonNotifications.size(); i++) {
					try {
						JSONObject n = (JSONObject) jsonNotifications.get(i);
						if (n != null) {
							VineNotification notf = new VineNotification(n);
							notfications.add(notf);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public VineNotificationsResponse next(VineService v) throws Exception{
		if(v!=null){
			if(nextPage>0){
				JSONObject data=v.api(baseURL, "get", (int)nextPage, (int)size);
				if(data!=null){
					VineNotificationsResponse nextResponse = new VineNotificationsResponse(
							data, baseURL);
					return nextResponse;
				}
			}
		}else{
			throw new Exception("(114) Vine service is empty");
		}
		return null;
	}
	
	public VineNotificationsResponse prev(VineService v) throws Exception{
		if(v!=null){
			if(previousPage>0){
				JSONObject data=v.api(baseURL, "get", (int)previousPage, (int)size);
				if(data!=null){
					VineNotificationsResponse prevResponse = new VineNotificationsResponse(
							data, baseURL);
					return prevResponse;
				}
			}
		}else{
			throw new Exception("(114) Vine service is empty");
		}
		return null;
	}
}
