package com.ikimuhendis.vine4j.types;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.ikimuhendis.vine4j.util.JSONUtil;

public class VineEntity {

	public long id;
	public String title;
	public List<Long> range;
	public String link;
	public String type;
	
	public VineEntity(JSONObject data){
		if(data!=null){
			id=JSONUtil.getLong(data, "id");
			link=JSONUtil.getString(data, "link");
			type=JSONUtil.getString(data, "type");
			title=JSONUtil.getString(data, "title");
			JSONArray jsonArray=JSONUtil.getJSONArray(data, "range");
			if(jsonArray!=null && jsonArray.size()>0){
				range=new ArrayList<Long>();
				for(int i=0;i<jsonArray.size();i++){
					try {
						long value=(Long) jsonArray.get(i);
						range.add(value);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
}
