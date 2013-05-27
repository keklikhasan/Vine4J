package com.ikimuhendis.vine4j.types;

import org.json.simple.JSONObject;

import com.ikimuhendis.vine4j.util.JSONUtil;

public class VineTag {

	public long tagId;
	public String tag;
	
	public VineTag(JSONObject data){
		if(data!=null){
			tagId=JSONUtil.getLong(data, "tagId");
			tag=JSONUtil.getString(data, "tag");
		}
	}
	
}
