package com.ikimuhendis.vine4j.types;

import org.json.simple.JSONObject;

import com.ikimuhendis.vine4j.util.JSONUtil;

public class VineNotification {

	    public String body;
	    public long displayUserId;
	    public String thumbnailUrl;
	    public long verified;
	    public String avatarUrl;
	    public long notificationTypeId;
	    public String created;
	    public long userId;
	    public String displayAvatarUrl;
	    public long notificationId;
	    public long postId;
	
	public VineNotification(JSONObject data) throws Exception {
		if (data != null) {
			body=JSONUtil.getString(data, "body");
			thumbnailUrl=JSONUtil.getString(data, "thumbnailUrl");
			avatarUrl=JSONUtil.getString(data, "avatarUrl");
			created=JSONUtil.getString(data, "created");
			displayAvatarUrl=JSONUtil.getString(data, "displayAvatarUrl");
			displayUserId=JSONUtil.getLong(data, "displayUserId");
			verified=JSONUtil.getLong(data, "verified");
			notificationTypeId=JSONUtil.getLong(data, "notificationTypeId");
			userId=JSONUtil.getLong(data, "userId");
			notificationId=JSONUtil.getLong(data, "notificationId");
			postId=JSONUtil.getLong(data, "postId");
		}
	}
}
