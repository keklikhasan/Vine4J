package com.ikimuhendis.vine4j.types;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.ikimuhendis.vine4j.util.JSONUtil;

public class VinePost {

	public List<VineTag> tags;
	public long postFlags;
	public String videoLowURL;
	public String location;
	public String videoUrl;
	public long explicitContent;
	public boolean liked;
	public long foursquareVenueId;
	public List<VineEntity> entities;
	public long postId;
	public String username;
	public String postVerified;
	public String created;
	public long promoted;
	public String description;
	public long postToFacebook;
	public long verified;
	public long postToTwitter;
	public long userId;
	public VineLikesResoponse likesResponse;
	public String thumbnailUrl;
	public String avatarUrl;
	public VineUser user;
	public String shareUrl;
	public VineCommentsResponse commentsResponse;

	public VinePost(JSONObject data) throws Exception {
		if (data != null) {
			JSONObject jsonObject = null;
			JSONArray jsonArray = null;
			jsonArray = JSONUtil.getJSONArray(data, "tags");
			if (jsonArray != null && jsonArray.size() > 0) {
				tags = new ArrayList<VineTag>();
				for (int i = 0; i < jsonArray.size(); i++) {
					jsonObject = (JSONObject) jsonArray.get(i);
					if (jsonObject != null) {
						VineTag t = new VineTag(jsonObject);
						tags.add(t);
					}
				}
			}
			postFlags = JSONUtil.getLong(data, "postFlags");
			videoLowURL = JSONUtil.getString(data, "videoLowURL");
			location = JSONUtil.getString(data, "location");
			videoUrl = JSONUtil.getString(data, "videoUrl");
			explicitContent = JSONUtil.getLong(data, "explicitContent");
			liked = JSONUtil.getBoolean(data, "liked");
			foursquareVenueId = JSONUtil.getLong(data, "foursquareVenueId");
			jsonArray = JSONUtil.getJSONArray(data, "entities");
			if (jsonArray != null && jsonArray.size() > 0) {
				entities=new ArrayList<VineEntity>();
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
			postId = JSONUtil.getLong(data, "postId");
			username = JSONUtil.getString(data, "username");
			postVerified = JSONUtil.getString(data, "postVerified");
			created = JSONUtil.getString(data, "created");
			promoted = JSONUtil.getLong(data, "promoted");
			description = JSONUtil.getString(data, "description");
			postToFacebook = JSONUtil.getLong(data, "postToFacebook");
			verified = JSONUtil.getLong(data, "verified");
			postToTwitter = JSONUtil.getLong(data, "postToTwitter");
			userId = JSONUtil.getLong(data, "userId");
			jsonObject = JSONUtil.getJSONObject(data, "likes");
			if (jsonObject != null) {
				likesResponse = new VineLikesResoponse(jsonObject,postId);
			}
			thumbnailUrl = JSONUtil.getString(data, "thumbnailUrl");
			avatarUrl = JSONUtil.getString(data, "avatarUrl");
			jsonObject = JSONUtil.getJSONObject(data, "user");
			if (jsonObject != null) {
				user = new VineUser(jsonObject);
			}
			jsonObject = JSONUtil.getJSONObject(data, "comments");
			if (jsonObject != null) {
				commentsResponse = new VineCommentsResponse(jsonObject,postId);
			}
			shareUrl=JSONUtil.getString(data, "shareUrl");
		}
	}
}
