package com.ikimuhendis.vine4j.types;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.ikimuhendis.vine4j.VineService;
import com.ikimuhendis.vine4j.util.JSONUtil;

public class VinePostsResponse {

	public long nextPage;
	public long previousPage;
	public long count;
	public List<VinePost> posts;
	public long size;
	private String baseURL;

	public VinePostsResponse(JSONObject data,String baseURL) throws Exception {
		this.baseURL=baseURL;
		if (data != null) {
			nextPage = JSONUtil.getLong(data, "nextPage");
			previousPage = JSONUtil.getLong(data, "previousPage");
			size = JSONUtil.getLong(data, "size");
			count = JSONUtil.getLong(data, "count");

			JSONArray jsonPosts = JSONUtil.getJSONArray(data, "records");
			posts = new ArrayList<VinePost>();
			if (jsonPosts != null && jsonPosts.size() > 0) {
				for (int i = 0; i < jsonPosts.size(); i++) {
					try {
						JSONObject p = (JSONObject) jsonPosts.get(i);
						if (p != null) {
							VinePost post = new VinePost(p);
							posts.add(post);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	

	public VinePostsResponse next(VineService v) throws Exception {
		if(v!=null){
			if(nextPage>0){
				JSONObject data=v.api(baseURL, "get", (int)nextPage, (int)size);
				if(data!=null){
					VinePostsResponse nextResponse = new VinePostsResponse(
							data, baseURL);
					return nextResponse;
				}
			}
		}else{
			throw new Exception("(114) Vine service is empty");
		}
		return null;
	}

	public VinePostsResponse prev(VineService v) throws Exception {
		if(v!=null){
			if(previousPage>0){
				JSONObject data=v.api(baseURL, "get", (int)previousPage, (int)size);
				if(data!=null){
					VinePostsResponse prevResponse = new VinePostsResponse(
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
