package com.ikimuhendis.vine4j.types;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.ikimuhendis.vine4j.util.JSONUtil;

public class VineComment {

	public String avatarUrl;
	public String comment;
	public long commentId;
	public String created;
	public String location;
	public long userId;
	public long verified;
	public String username;
	public long postId;
	public List<VineEntity> entities;
	public VineUser user;

	public VineComment(JSONObject data, long postId) {
		this.postId = postId;
		if (data != null) {
			avatarUrl = JSONUtil.getString(data, "avatarUrl");
			comment = JSONUtil.getString(data, "comment");
			commentId = JSONUtil.getLong(data, "commentId");
			created = JSONUtil.getString(data, "created");
			location = JSONUtil.getString(data, "location");
			userId = JSONUtil.getLong(data, "userId");
			username = JSONUtil.getString(data, "username");
			verified = JSONUtil.getLong(data, "verified");
			JSONObject jsonObject = null;
			JSONArray jsonArray = null;
			jsonArray = JSONUtil.getJSONArray(data, "entities");
			if (jsonArray != null && jsonArray.size() > 0) {
				entities = new ArrayList<VineEntity>();
				for (int i = 0; i < jsonArray.size(); i++) {
					try {
						jsonObject = (JSONObject) jsonArray.get(i);
						if (jsonObject != null) {
							VineEntity e = new VineEntity(jsonObject);
							entities.add(e);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}
			
			jsonObject=JSONUtil.getJSONObject(data, "user");
			if(jsonObject!=null){
				try {
					user=new VineUser(jsonObject);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}