package com.ikimuhendis.vine4j.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

public class HttpUtil {

	@SuppressWarnings("deprecation")
	public static HttpResponse post(String url, String key,
			HashMap<String, String> params) throws Exception {
		if (TextUtil.isempty(url)) {
			throw new Exception("(101) URL is empty");
		}
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);

		httpPost.addHeader(Constants.VINE_HTTPHEADER_USER_AGENT_TEXT,
				Constants.VINE_HTTPHEADER_USER_AGENT);
		httpPost.addHeader(Constants.VINE_HTTPHEADER_ACCEPT_TEXT,
				Constants.VINE_HTTPHEADER_ACCEPT);
		httpPost.addHeader(Constants.VINE_HTTPHEADER_ACCEPT_LANGUAGE_TEXT,
				Constants.VINE_HTTPHEADER_ACCEPT_LANGUAGE);
		httpPost.addHeader(Constants.VINE_HTTPHEADER_ACCEPT_ENCODING_TEXT,
				Constants.VINE_HTTPHEADER_ACCEPT_ENCODING);
		if (!TextUtil.isempty(key)) {
			httpPost.addHeader(Constants.VINE_HTTPHEADER_VINE_SESSION_ID, key);
		}

		if (params != null && params.size() > 0) {
			List<NameValuePair> bodyParams = new ArrayList<NameValuePair>();
			Iterator<Entry<String, String>> itt = params.entrySet().iterator();
			while (itt.hasNext()) {
				Entry<String, String> entry = itt.next();
				String key_ = entry.getKey();
				String value = entry.getValue();
				if (!TextUtil.isempty(key_) && !TextUtil.isempty(value)) {
					bodyParams.add(new BasicNameValuePair(key_, value));
				}
			}
			httpPost.setEntity(new UrlEncodedFormEntity(bodyParams, HTTP.UTF_8));
		}
		HttpResponse response = httpClient.execute(httpPost);
		return response;
	}

	public static HttpResponse get(String url, String key,
			HashMap<String, String> params) throws Exception {
		if (TextUtil.isempty(url)) {
			throw new Exception("(101) URL is empty");
		}
		HttpClient httpClient = new DefaultHttpClient();

		List<NameValuePair> bodyParams = new ArrayList<NameValuePair>();
		if (params != null && params.size() > 0) {
			Iterator<Entry<String, String>> itt = params.entrySet().iterator();
			while (itt.hasNext()) {
				Entry<String, String> entry = itt.next();
				String key_ = entry.getKey();
				String value = entry.getValue();
				if (!TextUtil.isempty(key_) && !TextUtil.isempty(value)) {
					bodyParams.add(new BasicNameValuePair(key_, value));
				}
			}
		}
		String getParams = URLEncodedUtils.format(bodyParams, "UTF-8");
		if (!TextUtil.isempty(getParams)) {
			url += "?" + getParams;
		}

		HttpGet httpGet = new HttpGet(url);

		httpGet.addHeader(Constants.VINE_HTTPHEADER_USER_AGENT_TEXT,
				Constants.VINE_HTTPHEADER_USER_AGENT);
		httpGet.addHeader(Constants.VINE_HTTPHEADER_ACCEPT_TEXT,
				Constants.VINE_HTTPHEADER_ACCEPT);
		httpGet.addHeader(Constants.VINE_HTTPHEADER_ACCEPT_LANGUAGE_TEXT,
				Constants.VINE_HTTPHEADER_ACCEPT_LANGUAGE);
		httpGet.addHeader(Constants.VINE_HTTPHEADER_ACCEPT_ENCODING_TEXT,
				Constants.VINE_HTTPHEADER_ACCEPT_ENCODING);
		if (!TextUtil.isempty(key)) {
			httpGet.addHeader(Constants.VINE_HTTPHEADER_VINE_SESSION_ID, key);
		}

		HttpResponse response = httpClient.execute(httpGet);
		return response;
	}

	public static HttpResponse put(String url, String key,
			HashMap<String, String> params) throws Exception {
		if (TextUtil.isempty(url)) {
			throw new Exception("(101) URL is empty");
		}
		HttpClient httpClient = new DefaultHttpClient();

		List<NameValuePair> bodyParams = new ArrayList<NameValuePair>();
		if (params != null && params.size() > 0) {
			Iterator<Entry<String, String>> itt = params.entrySet().iterator();
			while (itt.hasNext()) {
				Entry<String, String> entry = itt.next();
				String key_ = entry.getKey();
				String value = entry.getValue();
				if (!TextUtil.isempty(key_) && !TextUtil.isempty(value)) {
					bodyParams.add(new BasicNameValuePair(key_, value));
				}
			}
		}
		String getParams = URLEncodedUtils.format(bodyParams, "UTF-8");
		if (!TextUtil.isempty(getParams)) {
			url += "?" + getParams;
		}

		HttpPut httpPut = new HttpPut(url);

		httpPut.addHeader(Constants.VINE_HTTPHEADER_USER_AGENT_TEXT,
				Constants.VINE_HTTPHEADER_USER_AGENT);
		httpPut.addHeader(Constants.VINE_HTTPHEADER_ACCEPT_TEXT,
				Constants.VINE_HTTPHEADER_ACCEPT);
		httpPut.addHeader(Constants.VINE_HTTPHEADER_ACCEPT_LANGUAGE_TEXT,
				Constants.VINE_HTTPHEADER_ACCEPT_LANGUAGE);
		httpPut.addHeader(Constants.VINE_HTTPHEADER_ACCEPT_ENCODING_TEXT,
				Constants.VINE_HTTPHEADER_ACCEPT_ENCODING);
		if (!TextUtil.isempty(key)) {
			httpPut.addHeader(Constants.VINE_HTTPHEADER_VINE_SESSION_ID, key);
		}

		HttpResponse response = httpClient.execute(httpPut);
		return response;
	}

	public static HttpResponse delete(String url, String key,
			HashMap<String, String> params) throws Exception {
		if (TextUtil.isempty(url)) {
			throw new Exception("(101) URL is empty");
		}
		HttpClient httpClient = new DefaultHttpClient();

		List<NameValuePair> bodyParams = new ArrayList<NameValuePair>();
		if (params != null && params.size() > 0) {
			Iterator<Entry<String, String>> itt = params.entrySet().iterator();
			while (itt.hasNext()) {
				Entry<String, String> entry = itt.next();
				String key_ = entry.getKey();
				String value = entry.getValue();
				if (!TextUtil.isempty(key_) && !TextUtil.isempty(value)) {
					bodyParams.add(new BasicNameValuePair(key_, value));
				}
			}
		}
		String getParams = URLEncodedUtils.format(bodyParams, "UTF-8");
		if (!TextUtil.isempty(getParams)) {
			url += "?" + getParams;
		}

		HttpDelete httpDelete = new HttpDelete(url);

		httpDelete.addHeader(Constants.VINE_HTTPHEADER_USER_AGENT_TEXT,
				Constants.VINE_HTTPHEADER_USER_AGENT);
		httpDelete.addHeader(Constants.VINE_HTTPHEADER_ACCEPT_TEXT,
				Constants.VINE_HTTPHEADER_ACCEPT);
		httpDelete.addHeader(Constants.VINE_HTTPHEADER_ACCEPT_LANGUAGE_TEXT,
				Constants.VINE_HTTPHEADER_ACCEPT_LANGUAGE);
		httpDelete.addHeader(Constants.VINE_HTTPHEADER_ACCEPT_ENCODING_TEXT,
				Constants.VINE_HTTPHEADER_ACCEPT_ENCODING);
		if (!TextUtil.isempty(key)) {
			httpDelete
					.addHeader(Constants.VINE_HTTPHEADER_VINE_SESSION_ID, key);
		}

		HttpResponse response = httpClient.execute(httpDelete);
		return response;
	}

	public static InputStream getInputStreamHttpResponse(HttpResponse response)
			throws Exception {
		if (response != null) {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream is = entity.getContent();
				if (is != null) {
					boolean isgzip = false;
					if (response.containsHeader("Content-Encoding")) {
						Header val = response
								.getFirstHeader("Content-Encoding");
						if (val != null && val.getValue() != null
								&& val.getValue().equals("gzip")) {
							isgzip = true;
						}
					}
					if (isgzip) {
						GZIPInputStream gzip = new GZIPInputStream(is);
						return gzip;
					} else {
						return is;
					}
				} else {
					new Exception("(106) Server return empty data");
				}
			} else {
				new Exception("(107) Server return empty data");
			}
		} else {
			new Exception("(108) Server return empty data");
		}
		return null;
	}

}
