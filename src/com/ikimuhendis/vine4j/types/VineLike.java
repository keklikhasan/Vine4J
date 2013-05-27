package com.ikimuhendis.vine4j.types;

import org.json.simple.JSONObject;

import com.ikimuhendis.vine4j.util.JSONUtil;

public class VineLike {

	public String avatarUrl;
	public String created;
	public long likeId;
	public String location;
	public long userId;
	public String username;
	public long verified;

	public VineLike(JSONObject data) {
		if(data!=null){
			avatarUrl=JSONUtil.getString(data, "avatarUrl");
			created=JSONUtil.getString(data, "created");
			location=JSONUtil.getString(data, "location");
			userId=JSONUtil.getLong(data, "userId");
			username=JSONUtil.getString(data, "username");
			likeId=JSONUtil.getLong(data, "likeId");
			verified=JSONUtil.getLong(data, "verified");
		}
	}
}
