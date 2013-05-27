package com.ikimuhendis.vine4j.types;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.ikimuhendis.vine4j.VineService;
import com.ikimuhendis.vine4j.util.JSONUtil;

public class VineTagsResponse {

	public long nextPage;
	public long count;
	public List<VineTag> tags;
	public long previousPage;
	public long size;
	private String baseUrl;

	public VineTagsResponse(JSONObject data, String baseUrl) throws Exception {
		this.baseUrl = baseUrl;
		if (data != null) {
			nextPage = JSONUtil.getLong(data, "nextPage");
			previousPage = JSONUtil.getLong(data, "previousPage");
			size = JSONUtil.getLong(data, "size");
			count = JSONUtil.getLong(data, "count");

			JSONArray jsonTags = JSONUtil.getJSONArray(data, "records");
			tags = new ArrayList<VineTag>();
			if (jsonTags != null && jsonTags.size() > 0) {
				for (int i = 0; i < jsonTags.size(); i++) {
					try {
						JSONObject c = (JSONObject) jsonTags.get(i);
						if (c != null) {
							VineTag t = new VineTag(c);
							tags.add(t);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public VineTagsResponse next(VineService v) throws Exception {
		if (v != null) {
			if (nextPage > 0) {
				JSONObject data = v.api(baseUrl, "get", (int) nextPage,
						(int) size);
				if (data != null) {
					VineTagsResponse nextResponse = new VineTagsResponse(data,
							baseUrl);
					return nextResponse;
				}
			}
		} else {
			throw new Exception("(114) Vine service is empty");
		}
		return null;
	}

	public VineTagsResponse prev(VineService v) throws Exception {
		if (v != null) {
			if (previousPage > 0) {
				JSONObject data = v.api(baseUrl ,"get",
						(int) previousPage, (int) size);
				if (data != null) {
					VineTagsResponse prevResponse = new VineTagsResponse(
							data, baseUrl);
					return prevResponse;
				}
			}
		} else {
			throw new Exception("(114) Vine service is empty");
		}
		return null;
	}
}
