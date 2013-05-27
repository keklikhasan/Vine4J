package com.ikimuhendis.vine4j.util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JSONUtil {

	public static boolean getBoolean(JSONObject data, String field) {
		if (data != null && data.containsKey(field)) {
			try {
				return (Boolean) data.get(field);
			} catch (Exception e) {
				System.out.println("JSONUtil.getBoolean() W : "+e.toString());
			}
		}
		return false;
	}

	public static String getString(JSONObject data, String field) {
		if (data != null && data.containsKey(field)) {
			try {
				return (String) data.get(field);
			} catch (Exception e) {
				System.out.println("JSONUtil.getString() W : "+e.toString());
			}
		}
		return null;
	}

	public static int getInt(JSONObject data, String field) {
		if (data != null && data.containsKey(field)) {
			try {
				return (Integer) data.get(field);
			} catch (Exception e) {
				System.out.println("JSONUtil.getInt() W : "+e.toString());
			}
		}
		return 0;
	}

	public static long getLong(JSONObject data, String field) {
		if (data != null && data.containsKey(field)) {
			try {
				return (Long) data.get(field);
			} catch (Exception e) {
				System.out.println("JSONUtil.getLong() W : "+e.toString());
			}
		}
		return 0;
	}
	
	public static JSONArray getJSONArray(JSONObject data, String field) {
		if (data != null && data.containsKey(field)) {
			try {
				return (JSONArray) data.get(field);
			} catch (Exception e) {
				System.out.println("JSONUtil.getJSONArray() W : "+e.toString());
			}
		}
		return null;
	}
	
	public static JSONObject getJSONObject(JSONObject data, String field) {
		if (data != null && data.containsKey(field)) {
			try {
				return (JSONObject) data.get(field);
			} catch (Exception e) {
				System.out.println("JSONUtil.getJSONObject() W : "+e.toString());
			}
		}
		return null;
	}

}
