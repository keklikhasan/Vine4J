package com.ikimuhendis.vine4j.types;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.ikimuhendis.vine4j.VineService;
import com.ikimuhendis.vine4j.util.Constants;
import com.ikimuhendis.vine4j.util.JSONUtil;

public class VineLikesResoponse {
	public long nextPage;
	public long count;
	public List<VineLike> likes;
	public long previousPage;
	public long size;
	public long postId;

	public VineLikesResoponse(JSONObject data,long postId) throws Exception {
		this.postId=postId;
		if (data != null) {
			nextPage = JSONUtil.getLong(data, "nextPage");
			previousPage = JSONUtil.getLong(data, "previousPage");
			size = JSONUtil.getLong(data, "size");
			count = JSONUtil.getLong(data, "count");

			JSONArray jsonLikes = JSONUtil.getJSONArray(data, "records");
			likes = new ArrayList<VineLike>();
			if (jsonLikes != null && jsonLikes.size() > 0) {
				for (int i = 0; i < jsonLikes.size(); i++) {
					try {
						JSONObject l = (JSONObject) jsonLikes.get(i);
						if (l != null) {
							VineLike like = new VineLike(l);
							likes.add(like);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	private String getBaseUrl(){
		if(postId>0){
			return Constants.VINE_API_POST_LIKES.replaceAll("\\{postId\\}", ""+postId);
		}
		return null;
	}
	
	public VineLikesResoponse next(VineService v) throws Exception{
		if(v!=null){
			if(nextPage>0){
				JSONObject data=v.api(getBaseUrl(), "get", (int)nextPage, (int)size);
				if(data!=null){
					VineLikesResoponse nextResponse = new VineLikesResoponse(
							data,postId);
					return nextResponse;
				}
			}
		}else{
			throw new Exception("(114) Vine service is empty");
		}
		return null;
	}
	
	public VineLikesResoponse prev(VineService v) throws Exception{
		if(v!=null){
			if(previousPage>0){
				JSONObject data=v.api(getBaseUrl(), "get", (int)previousPage, (int)size);
				if(data!=null){
					VineLikesResoponse nextResponse = new VineLikesResoponse(
							data,postId);
					return nextResponse;
				}
			}
		}else{
			throw new Exception("(114) Vine service is empty");
		}
		return null;
	}
}
