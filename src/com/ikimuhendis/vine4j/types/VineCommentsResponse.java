package com.ikimuhendis.vine4j.types;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.ikimuhendis.vine4j.VineService;
import com.ikimuhendis.vine4j.util.Constants;
import com.ikimuhendis.vine4j.util.JSONUtil;

public class VineCommentsResponse {

	public long nextPage;
	public long count;
	public List<VineComment> comments;
	public long previousPage;
	public long size;
	public long postId;

	public VineCommentsResponse(JSONObject data,long postId) throws Exception {
		this.postId=postId;
		if (data != null) {
			nextPage = JSONUtil.getLong(data, "nextPage");
			previousPage = JSONUtil.getLong(data, "previousPage");
			size = JSONUtil.getLong(data, "size");
			count = JSONUtil.getLong(data, "count");

			JSONArray jsonComments = JSONUtil.getJSONArray(data, "records");
			comments = new ArrayList<VineComment>();
			if (jsonComments != null && jsonComments.size() > 0) {
				for (int i = 0; i < jsonComments.size(); i++) {
					try {
						JSONObject c = (JSONObject) jsonComments.get(i);
						if (c != null) {
							VineComment comment = new VineComment(c,postId);
							comments.add(comment);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public String getBaseUrl() {
		if(postId>0){
			return Constants.VINE_API_POST_COMMENTS.replaceAll("\\{postId\\}", ""+postId);
		}
		return null;
	}

	public VineCommentsResponse next(VineService v) throws Exception {
		if (v != null) {
			if (nextPage > 0) {
				JSONObject data = v.api(getBaseUrl(), "get", (int) nextPage,
						(int) size);
				if (data != null) {
					VineCommentsResponse nextResponse = new VineCommentsResponse(
							data,postId);
					return nextResponse;
				}
			}
		} else {
			throw new Exception("(114) Vine service is empty");
		}
		return null;
	}

	public VineCommentsResponse prev(VineService v) throws Exception {
		if (v != null) {
			if (previousPage > 0) {
				JSONObject data = v.api(getBaseUrl(), "get",
						(int) previousPage, (int) size);
				if (data != null) {
					VineCommentsResponse prevResponse = new VineCommentsResponse(
							data,postId);
					return prevResponse;
				}
			}
		} else {
			throw new Exception("(114) Vine service is empty");
		}
		return null;
	}

}
