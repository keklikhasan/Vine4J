package com.ikimuhendis.vine4j.types;

import org.json.simple.JSONObject;

import com.ikimuhendis.vine4j.util.JSONUtil;

public class VineUser {

	public long userId;
	public String username;
	public String phoneNumber;
	public String email;
	public String description;
	public String avatarUrl;
	public long verified;
	public long followingCount;
	public long likeCount;
	public long following;
	public long explicitContent;
	public String location;
	public String twitterId;
	public long blocking;
	public long facebookConnected;
	public long twitterConnected;
	public long blocked;
	public long postCount;
	public long followerCount;
	public long includePromoted;

	public VineUser(JSONObject data) throws Exception {
		if (data != null) {
			userId = JSONUtil.getLong(data, "userId");
			if (userId > 0) {
				followingCount = JSONUtil.getLong(data, "followingCount");
				likeCount = JSONUtil.getLong(data, "likeCount");
				location = JSONUtil.getString(data, "location");
				twitterId = JSONUtil.getString(data, "twitterId");
				blocking = JSONUtil.getLong(data, "blocking");
				username = JSONUtil.getString(data, "username");
				following = JSONUtil.getLong(data, "following");
				facebookConnected = JSONUtil.getLong(data, "facebookConnected");
				phoneNumber = JSONUtil.getString(data, "phoneNumber");
				description = JSONUtil.getString(data, "description");
				avatarUrl = JSONUtil.getString(data, "avatarUrl");
				email = JSONUtil.getString(data, "email");
				verified = JSONUtil.getLong(data, "verified");
				twitterConnected = JSONUtil.getLong(data, "twitterConnected");
				blocked = JSONUtil.getLong(data, "blocked");
				postCount = JSONUtil.getLong(data, "postCount");
				followerCount = JSONUtil.getLong(data, "followerCount");
				includePromoted = JSONUtil.getLong(data, "includePromoted");
			}
		}
	}

}
