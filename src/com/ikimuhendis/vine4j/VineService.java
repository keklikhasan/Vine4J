package com.ikimuhendis.vine4j;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.ikimuhendis.vine4j.types.VineComment;
import com.ikimuhendis.vine4j.types.VineEntity;
import com.ikimuhendis.vine4j.types.VineNotificationsResponse;
import com.ikimuhendis.vine4j.types.VinePost;
import com.ikimuhendis.vine4j.types.VinePostsResponse;
import com.ikimuhendis.vine4j.types.VineTagsResponse;
import com.ikimuhendis.vine4j.types.VineUser;
import com.ikimuhendis.vine4j.util.Constants;
import com.ikimuhendis.vine4j.util.HttpUtil;
import com.ikimuhendis.vine4j.util.JSONUtil;
import com.ikimuhendis.vine4j.util.TextUtil;

public class VineService {

	/*
	 * Variables
	 */
	private String userName;
	private long userId;
	private String password;
	private String key;
	private boolean authenticated;

	/*
	 * 
	 */
	public VineService() {

	}

	public VineService(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	public VineService(String key) {
		this.key = key;
	}

	/*
	 * Comment
	 */

	/*
	 * type : mention,tag(#) text title olaibilir
	 */
	public boolean comment(VinePost post, String comment,
			List<VineEntity> entities) throws Exception {
		if (post != null) {
			return comment(post.postId, comment, entities);
		}
		return false;
	}

	public boolean comment(long postId, String comment,
			List<VineEntity> entities) throws Exception {
		if (postId > 0) {
			if (!TextUtil.isempty(comment)
					|| (entities != null && entities.size() > 0)) {
				if (this.authenticated && !TextUtil.isempty(key)) {
					String url = Constants.VINE_API_POST_ADD_COMMENT
							.replaceAll("\\{postId\\}", "" + postId);
					HashMap<String, String> params = new HashMap<String, String>();
					params.put("comment", comment);
					List<String> entitiesJSONList = new ArrayList<String>();
					if (entities != null && entities.size() > 0) {
						for (int i = 0; i < entities.size(); i++) {
							VineEntity ent = entities.get(i);
							HashMap<String, String> entObj = new HashMap<String, String>();
							entObj.put("type", ent.type);
							entObj.put("id", ent.id + "");
							entObj.put("text", ent.title);
							String range = "[]";
							if (ent.range != null && ent.range.size() >= 2) {
								range = "[" + ent.range.get(0) + ","
										+ ent.range.get(1) + "]";
							}
							entObj.put("range", range);
							String json = JSONValue.toJSONString(entObj);
							entitiesJSONList.add(json);
						}
					}
					String entitiesJSON = JSONValue
							.toJSONString(entitiesJSONList);
					params.put("entities", entitiesJSON);

					HttpResponse response = HttpUtil
							.post(url, this.key, params);

					InputStream is = HttpUtil
							.getInputStreamHttpResponse(response);
					JSONObject jsonResponse = (JSONObject) JSONValue
							.parse(new InputStreamReader(is, Charset
									.forName("UTF-8")));
					if (JSONUtil.getBoolean(jsonResponse, "success")) {
						return true;
					} else {
						String errorText = JSONUtil.getString(jsonResponse,
								"error");
						long errorCode = JSONUtil.getLong(jsonResponse, "code");
						throw new Exception(
								"(105) Server returns Error => Error Code : '"
										+ errorCode + "' Error Detail :'"
										+ errorText + "'");
					}
				} else {
					throw new Exception("(103) Authenticate first");
				}
			}
		}
		return false;
	}

	/*
	 * Comment
	 */

	/*
	 * Tags
	 */
	public VinePostsResponse popular(int page, int size) throws Exception {
		return tag("popular", page, size);
	}

	public VinePostsResponse promoted(int page, int size) throws Exception {
		return tag("promoted", page, size);
	}

	@SuppressWarnings("deprecation")
	public VinePostsResponse getTagPosts(String tag, int page, int size)
			throws Exception {
		if (this.authenticated && !TextUtil.isempty(key)) {
			if (!TextUtil.isempty(tag)) {
				tag = "tags/" + URLEncoder.encode(tag);
				return tag(tag, page, size);
			} else {
				new Exception("(102) Tag seacrh term is empty");
			}
		} else {
			throw new Exception("(103) Authenticate first");
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	public VineTagsResponse searchTag(String tag, int page, int size)
			throws Exception {
		if (this.authenticated && !TextUtil.isempty(key)) {
			if (!TextUtil.isempty(tag)) {
				tag = URLEncoder.encode(tag);
				String url = Constants.VINE_API_SEACRH_TAG.replaceAll(
						"\\{tag\\}", tag);
				HashMap<String, String> params = new HashMap<String, String>();
				if (page > 0) {
					params.put("page", page + "");
				}
				if (size > 0) {
					params.put("size", size + "");
				}
				HttpResponse response = HttpUtil.get(url, this.key, params);

				InputStream is = HttpUtil.getInputStreamHttpResponse(response);
				JSONObject jsonResponse = (JSONObject) JSONValue
						.parse(new InputStreamReader(is, Charset
								.forName("UTF-8")));
				if (JSONUtil.getBoolean(jsonResponse, "success")) {
					JSONObject data = (JSONObject) jsonResponse.get("data");
					if (data != null) {
						VineTagsResponse tags = new VineTagsResponse(data, url);
						return tags;
					} else {
						throw new Exception("(104) Server return empty data");
					}
				} else {
					String errorText = JSONUtil
							.getString(jsonResponse, "error");
					long errorCode = JSONUtil.getLong(jsonResponse, "code");
					throw new Exception(
							"(105) Server returns Error => Error Code : '"
									+ errorCode + "' Error Detail :'"
									+ errorText + "'");
				}
			} else {
				new Exception("(102) Tag seacrh term is empty");
			}
		} else {
			throw new Exception("(103) Authenticate first");
		}
		return null;
	}

	private VinePostsResponse tag(String path, int page, int size)
			throws Exception {
		if (this.authenticated && !TextUtil.isempty(key)) {
			if (TextUtil.isempty(path)) {
				path = this.userId + "";
			}
			String url = Constants.VINE_API_TAG_TIMELINE.replaceAll(
					"\\{tag\\}", path);
			HashMap<String, String> params = new HashMap<String, String>();
			if (page > 0) {
				params.put("page", page + "");
			}
			if (size > 0) {
				params.put("size", size + "");
			}
			HttpResponse response = HttpUtil.get(url, this.key, params);

			InputStream is = HttpUtil.getInputStreamHttpResponse(response);
			JSONObject jsonResponse = (JSONObject) JSONValue
					.parse(new InputStreamReader(is, Charset.forName("UTF-8")));
			if (JSONUtil.getBoolean(jsonResponse, "success")) {
				JSONObject data = (JSONObject) jsonResponse.get("data");
				if (data != null) {
					VinePostsResponse posts = new VinePostsResponse(data, url);
					return posts;
				} else {
					throw new Exception("(104) Server return empty data");
				}
			} else {
				String errorText = JSONUtil.getString(jsonResponse, "error");
				long errorCode = JSONUtil.getLong(jsonResponse, "code");
				throw new Exception(
						"(105) Server returns Error => Error Code : '"
								+ errorCode + "' Error Detail :'" + errorText
								+ "'");
			}
		} else {
			throw new Exception("(103) Authenticate first");
		}
	}

	/*
	 * Tags
	 */

	/*
	 * Report post
	 */

	public boolean reportPost(VinePost post) throws Exception {
		if (post != null) {
			return reportPost(post.postId);
		}
		return false;
	}

	public boolean reportPost(long postId) throws Exception {
		if (postId > 0) {
			if (this.authenticated && !TextUtil.isempty(key)) {
				String url = Constants.VINE_API_POST_REPORT.replaceAll(
						"\\{postId\\}", "" + postId);
				HttpResponse response = HttpUtil.get(url, this.key, null);

				InputStream is = HttpUtil.getInputStreamHttpResponse(response);
				JSONObject jsonResponse = (JSONObject) JSONValue
						.parse(new InputStreamReader(is, Charset
								.forName("UTF-8")));
				if (JSONUtil.getBoolean(jsonResponse, "success")) {
					return true;
				} else {
					String errorText = JSONUtil
							.getString(jsonResponse, "error");
					long errorCode = JSONUtil.getLong(jsonResponse, "code");
					throw new Exception(
							"(105) Server returns Error => Error Code : '"
									+ errorCode + "' Error Detail :'"
									+ errorText + "'");
				}
			} else {
				throw new Exception("(103) Authenticate first");
			}
		}
		return false;
	}

	/*
	 * Report post
	 */

	/*
	 * follow / unfollow
	 */

	public boolean follow(VineUser user) throws Exception {
		if (user != null) {
			return follow(user.userId);
		}
		return false;
	}

	public boolean follow(long userId) throws Exception {
		if (userId > 0) {
			if (this.authenticated && !TextUtil.isempty(key)) {
				String url = Constants.VINE_API_USER_FOLLOW.replaceAll(
						"\\{userId\\}", "" + userId);
				HttpResponse response = HttpUtil.post(url, this.key, null);

				InputStream is = HttpUtil.getInputStreamHttpResponse(response);
				JSONObject jsonResponse = (JSONObject) JSONValue
						.parse(new InputStreamReader(is, Charset
								.forName("UTF-8")));
				if (JSONUtil.getBoolean(jsonResponse, "success")) {
					return true;
				} else {
					String errorText = JSONUtil
							.getString(jsonResponse, "error");
					long errorCode = JSONUtil.getLong(jsonResponse, "code");
					throw new Exception(
							"(105) Server returns Error => Error Code : '"
									+ errorCode + "' Error Detail :'"
									+ errorText + "'");
				}
			} else {
				throw new Exception("(103) Authenticate first");
			}
		}
		return false;
	}

	public boolean unfollow(VineUser user) throws Exception {
		if (user != null) {
			return unfollow(user.userId);
		}
		return false;
	}

	public boolean unfollow(long userId) throws Exception {
		if (userId > 0) {
			if (this.authenticated && !TextUtil.isempty(key)) {
				String url = Constants.VINE_API_USER_FOLLOW.replaceAll(
						"\\{userId\\}", "" + userId);
				HttpResponse response = HttpUtil.delete(url, this.key, null);

				InputStream is = HttpUtil.getInputStreamHttpResponse(response);
				JSONObject jsonResponse = (JSONObject) JSONValue
						.parse(new InputStreamReader(is, Charset
								.forName("UTF-8")));
				if (JSONUtil.getBoolean(jsonResponse, "success")) {
					return true;
				} else {
					String errorText = JSONUtil
							.getString(jsonResponse, "error");
					long errorCode = JSONUtil.getLong(jsonResponse, "code");
					throw new Exception(
							"(105) Server returns Error => Error Code : '"
									+ errorCode + "' Error Detail :'"
									+ errorText + "'");
				}
			} else {
				throw new Exception("(103) Authenticate first");
			}
		}
		return false;
	}

	/*
	 * follow / unfollow
	 */

	/*
	 * block / unblock
	 */
	public boolean blockUser(VineUser user) throws Exception {
		if (user != null) {
			return blockUser(user.userId);
		}
		return false;
	}

	public boolean blockUser(long userId) throws Exception {
		if (userId > 0) {
			if (this.authenticated && !TextUtil.isempty(key)) {
				String url = Constants.VINE_API_USER_BLOCK.replaceAll(
						"\\{myUserId\\}", "" + this.userId);
				url = url.replaceAll("\\{userId\\}", "" + userId);
				HttpResponse response = HttpUtil.post(url, this.key, null);

				InputStream is = HttpUtil.getInputStreamHttpResponse(response);
				JSONObject jsonResponse = (JSONObject) JSONValue
						.parse(new InputStreamReader(is, Charset
								.forName("UTF-8")));
				if (JSONUtil.getBoolean(jsonResponse, "success")) {
					return true;
				} else {
					String errorText = JSONUtil
							.getString(jsonResponse, "error");
					long errorCode = JSONUtil.getLong(jsonResponse, "code");
					throw new Exception(
							"(105) Server returns Error => Error Code : '"
									+ errorCode + "' Error Detail :'"
									+ errorText + "'");
				}
			} else {
				throw new Exception("(103) Authenticate first");
			}
		}
		return false;
	}

	public boolean unblockUser(VineUser user) throws Exception {
		if (user != null) {
			return unblockUser(user.userId);
		}
		return false;
	}

	public boolean unblockUser(long userId) throws Exception {
		if (userId > 0) {
			if (this.authenticated && !TextUtil.isempty(key)) {
				String url = Constants.VINE_API_USER_BLOCK.replaceAll(
						"\\{myUserId\\}", "" + this.userId);
				url = url.replaceAll("\\{userId\\}", "" + userId);
				HttpResponse response = HttpUtil.delete(url, this.key, null);
				InputStream is = HttpUtil.getInputStreamHttpResponse(response);
				JSONObject jsonResponse = (JSONObject) JSONValue
						.parse(new InputStreamReader(is, Charset
								.forName("UTF-8")));
				if (JSONUtil.getBoolean(jsonResponse, "success")) {
					return true;
				} else {
					String errorText = JSONUtil
							.getString(jsonResponse, "error");
					long errorCode = JSONUtil.getLong(jsonResponse, "code");
					throw new Exception(
							"(105) Server returns Error => Error Code : '"
									+ errorCode + "' Error Detail :'"
									+ errorText + "'");
				}
			} else {
				throw new Exception("(103) Authenticate first");
			}
		}
		return false;
	}

	/*
	 * block / unblock
	 */

	/*
	 * Delete comment
	 */

	public boolean deleteComment(VineComment comment) throws Exception {
		if (comment != null) {
			return deleteComment(comment.postId, comment.commentId);
		}
		return false;
	}

	public boolean deleteComment(long postId, long commentId) throws Exception {
		if (postId > 0 && commentId > 0) {
			if (this.authenticated && !TextUtil.isempty(key)) {
				String url = Constants.VINE_API_POST_COMMENT.replaceAll(
						"\\{postId\\}", "" + postId);
				url = url.replaceAll("\\{commentId\\}", "" + commentId);
				HttpResponse response = HttpUtil.delete(url, this.key, null);
				InputStream is = HttpUtil.getInputStreamHttpResponse(response);
				JSONObject jsonResponse = (JSONObject) JSONValue
						.parse(new InputStreamReader(is, Charset
								.forName("UTF-8")));
				if (JSONUtil.getBoolean(jsonResponse, "success")) {
					return true;
				} else {
					String errorText = JSONUtil
							.getString(jsonResponse, "error");
					long errorCode = JSONUtil.getLong(jsonResponse, "code");
					throw new Exception(
							"(105) Server returns Error => Error Code : '"
									+ errorCode + "' Error Detail :'"
									+ errorText + "'");
				}
			} else {
				throw new Exception("(103) Authenticate first");
			}
		}
		return false;
	}

	/*
	 * general api
	 */
	public JSONObject api(String path, String method, int page, int size)
			throws Exception {
		if (!TextUtil.isempty(path)) {
			if (!path.startsWith("http") && !path.startsWith("www")) {
				if (!path.startsWith("/")) {
					path = "/" + path;
				}
				path = Constants.VINE_API_BASE_URL + path;
			} else {
				if (!path.contains("api.vineapp.com")) {
					throw new Exception(
							"(113) Url doesn't belong to vine check your url");
				}
			}

			if (this.authenticated && !TextUtil.isempty(key)) {
				HashMap<String, String> params = new HashMap<String, String>();
				if (page > 0) {
					params.put("page", page + "");
				}
				if (size > 0) {
					params.put("size", size + "");
				}

				HttpResponse response = null;
				if (!TextUtil.isempty(method)) {
					if (method.toLowerCase().equals("get")) {
						response = HttpUtil.get(path, this.key, params);
					} else if (method.toLowerCase().equals("post")) {
						response = HttpUtil.post(path, this.key, params);
					} else if (method.toLowerCase().equals("delete")) {
						response = HttpUtil.delete(path, this.key, params);
					} else if (method.toLowerCase().equals("put")) {
						response = HttpUtil.put(path, this.key, params);
					} else {
						response = HttpUtil.get(path, this.key, params);
					}
				} else {
					response = HttpUtil.get(path, this.key, params);
				}

				InputStream is = HttpUtil.getInputStreamHttpResponse(response);
				JSONObject jsonResponse = (JSONObject) JSONValue
						.parse(new InputStreamReader(is, Charset
								.forName("UTF-8")));
				if (JSONUtil.getBoolean(jsonResponse, "success")) {
					JSONObject data = (JSONObject) jsonResponse.get("data");
					if (data != null) {
						return data;
					} else {
						throw new Exception("(104) Server return empty data");
					}
				} else {
					String errorText = JSONUtil
							.getString(jsonResponse, "error");
					long errorCode = JSONUtil.getLong(jsonResponse, "code");
					throw new Exception(
							"(105) Server returns Error => Error Code : '"
									+ errorCode + "' Error Detail :'"
									+ errorText + "'");
				}
			} else {
				throw new Exception("(103) Authenticate first");
			}
		} else {
			throw new Exception("(101) URL is empty ");
		}
	}

	/*
	 * like / unlike post
	 */
	public boolean likePost(VinePost post) throws Exception {
		if (post != null) {
			return likePost(post.postId);
		}
		return false;
	}

	public boolean likePost(long postId) throws Exception {
		if (postId > 0) {
			if (this.authenticated && !TextUtil.isempty(key)) {
				String url = Constants.VINE_API_POST_LIKE.replaceAll(
						"\\{postId\\}", "" + postId);
				HttpResponse response = HttpUtil.post(url, this.key, null);
				InputStream is = HttpUtil.getInputStreamHttpResponse(response);
				JSONObject jsonResponse = (JSONObject) JSONValue
						.parse(new InputStreamReader(is, Charset
								.forName("UTF-8")));
				if (JSONUtil.getBoolean(jsonResponse, "success")) {
					return true;
				} else {
					String errorText = JSONUtil
							.getString(jsonResponse, "error");
					long errorCode = JSONUtil.getLong(jsonResponse, "code");
					throw new Exception(
							"(105) Server returns Error => Error Code : '"
									+ errorCode + "' Error Detail :'"
									+ errorText + "'");
				}
			} else {
				throw new Exception("(103) Authenticate first");
			}
		}
		return false;
	}

	public boolean unlikePost(VinePost post) throws Exception {
		if (post != null) {
			return unlikePost(post.postId);
		}
		return false;
	}

	public boolean unlikePost(long postId) throws Exception {
		if (postId > 0) {
			if (this.authenticated && !TextUtil.isempty(key)) {
				String url = Constants.VINE_API_POST_LIKE.replaceAll(
						"\\{postId\\}", "" + postId);
				HttpResponse response = HttpUtil.delete(url, this.key, null);
				InputStream is = HttpUtil.getInputStreamHttpResponse(response);
				JSONObject jsonResponse = (JSONObject) JSONValue
						.parse(new InputStreamReader(is, Charset
								.forName("UTF-8")));
				if (JSONUtil.getBoolean(jsonResponse, "success")) {
					return true;
				} else {
					String errorText = JSONUtil
							.getString(jsonResponse, "error");
					long errorCode = JSONUtil.getLong(jsonResponse, "code");
					throw new Exception(
							"(105) Server returns Error => Error Code : '"
									+ errorCode + "' Error Detail :'"
									+ errorText + "'");
				}
			} else {
				throw new Exception("(103) Authenticate first");
			}
		}
		return false;
	}

	/*
	 * like / unlike post
	 */

	/*
	 * get user likes
	 */

	public VinePostsResponse userLikes(int page, int size) throws Exception {
		return userLikes(this.userId + "", page, size);
	}

	public VinePostsResponse userLikes(String userId, int page, int size)
			throws Exception {
		if (this.authenticated && !TextUtil.isempty(key)) {
			if (TextUtil.isempty(userId)) {
				userId = this.userId + "";
			} else {
				try {
					Long.parseLong(userId);
				} catch (Exception e) {
					throw new Exception("(115) User id is not valid ");
				}
			}
			String url = Constants.VINE_API_USER_LIKES.replaceAll(
					"\\{userId\\}", userId + "");
			HashMap<String, String> params = new HashMap<String, String>();
			if (page > 0) {
				params.put("page", page + "");
			}
			if (size > 0) {
				params.put("size", size + "");
			}
			HttpResponse response = HttpUtil.get(url, this.key, params);
			InputStream is = HttpUtil.getInputStreamHttpResponse(response);
			JSONObject jsonResponse = (JSONObject) JSONValue
					.parse(new InputStreamReader(is, Charset.forName("UTF-8")));
			if (JSONUtil.getBoolean(jsonResponse, "success")) {
				JSONObject data = (JSONObject) jsonResponse.get("data");
				if (data != null) {
					VinePostsResponse likes = new VinePostsResponse(data, url);
					return likes;
				} else {
					throw new Exception("(104) Server return empty data");
				}
			} else {
				String errorText = JSONUtil.getString(jsonResponse, "error");
				long errorCode = JSONUtil.getLong(jsonResponse, "code");
				throw new Exception(
						"(105) Server returns Error => Error Code : '"
								+ errorCode + "' Error Detail :'" + errorText
								+ "'");
			}
		} else {
			throw new Exception("(103) Authenticate first");
		}
	}

	/*
	 * user timelines
	 */

	public VinePostsResponse userTimeline(int page, int size) throws Exception {
		return userTimeline(this.userId + "", page, size);
	}

	public VinePostsResponse userTimeline(String userId, int page, int size)
			throws Exception {
		if (this.authenticated && !TextUtil.isempty(key)) {
			if (TextUtil.isempty(userId)) {
				userId = this.userId + "";
			}
			String url = Constants.VINE_API_USER_TIMELINE.replaceAll(
					"\\{userId\\}", userId + "");
			HashMap<String, String> params = new HashMap<String, String>();
			if (page > 0) {
				params.put("page", page + "");
			}
			if (size > 0) {
				params.put("size", size + "");
			}
			HttpResponse response = HttpUtil.get(url, this.key, params);
			InputStream is = HttpUtil.getInputStreamHttpResponse(response);
			JSONObject jsonResponse = (JSONObject) JSONValue
					.parse(new InputStreamReader(is, Charset.forName("UTF-8")));
			if (JSONUtil.getBoolean(jsonResponse, "success")) {
				JSONObject data = (JSONObject) jsonResponse.get("data");
				if (data != null) {
					VinePostsResponse posts = new VinePostsResponse(data, url);
					return posts;
				} else {
					throw new Exception("(104) Server return empty data");
				}
			} else {
				String errorText = JSONUtil.getString(jsonResponse, "error");
				long errorCode = JSONUtil.getLong(jsonResponse, "code");
				throw new Exception(
						"(105) Server returns Error => Error Code : '"
								+ errorCode + "' Error Detail :'" + errorText
								+ "'");
			}
		} else {
			throw new Exception("(103) Authenticate first");
		}
	}

	/*
	 * Search user
	 */

	@SuppressWarnings("deprecation")
	public List<VineUser> searchUser(String term) throws Exception {
		if (this.authenticated && !TextUtil.isempty(key)) {
			if (!TextUtil.isempty(term)) {
				term = URLEncoder.encode(term);
				HttpResponse response = HttpUtil.get(
						Constants.VINE_API_SEARCH_USER.replaceAll("\\{term\\}",
								term), this.key, null);
				InputStream is = HttpUtil.getInputStreamHttpResponse(response);
				JSONObject jsonResponse = (JSONObject) JSONValue
						.parse(new InputStreamReader(is, Charset
								.forName("UTF-8")));
				if (JSONUtil.getBoolean(jsonResponse, "success")) {
					JSONObject data = (JSONObject) jsonResponse.get("data");
					if (data != null) {
						List<VineUser> users = new ArrayList<VineUser>();
						JSONArray records = JSONUtil.getJSONArray(data,
								"records");
						if (records != null && records.size() > 0) {
							for (int i = 0; i < records.size(); i++) {
								try {
									JSONObject u = (JSONObject) records.get(i);
									VineUser user = new VineUser(u);
									users.add(user);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
						return users;
					} else {
						throw new Exception("(104) Server return empty data");
					}
				} else {
					String errorText = JSONUtil
							.getString(jsonResponse, "error");
					long errorCode = JSONUtil.getLong(jsonResponse, "code");
					throw new Exception(
							"(105) Server returns Error => Error Code : '"
									+ errorCode + "' Error Detail :'"
									+ errorText + "'");
				}
			} else {
				throw new Exception("(109) User seacrh term is empty");
			}
		} else {
			throw new Exception("(103) Authenticate first");
		}
	}

	/*
	 * check notf
	 */

	public long checkNotification() throws Exception {
		if (this.authenticated && !TextUtil.isempty(key)) {
			String url = Constants.VINE_API_PENDING_NOTIFICATIONS.replaceAll(
					"\\{userId\\}", this.userId + "");
			HttpResponse response = HttpUtil.get(url, this.key, null);
			InputStream is = HttpUtil.getInputStreamHttpResponse(response);
			JSONObject jsonResponse = (JSONObject) JSONValue
					.parse(new InputStreamReader(is, Charset.forName("UTF-8")));
			if (JSONUtil.getBoolean(jsonResponse, "success")) {
				return JSONUtil.getLong(jsonResponse, "data");
			} else {
				String errorText = JSONUtil.getString(jsonResponse, "error");
				long errorCode = JSONUtil.getLong(jsonResponse, "code");
				throw new Exception(
						"(105) Server returns Error => Error Code : '"
								+ errorCode + "' Error Detail :'" + errorText
								+ "'");
			}
		} else {
			throw new Exception("(103) Authenticate first");
		}
	}

	/*
	 * get notifications
	 */

	public VineNotificationsResponse notifications(int page, int size)
			throws Exception {
		if (this.authenticated && !TextUtil.isempty(key)) {
			if (this.userId > 0) {
				String url = Constants.VINE_API_NOTIFICATIONS.replaceAll(
						"\\{userId\\}", this.userId + "");
				HashMap<String, String> params = new HashMap<String, String>();
				if (page > 0) {
					params.put("page", page + "");
				}
				if (size > 0) {
					params.put("size", size + "");
				}
				HttpResponse response = HttpUtil.get(url, this.key, params);
				InputStream is = HttpUtil.getInputStreamHttpResponse(response);
				JSONObject jsonResponse = (JSONObject) JSONValue
						.parse(new InputStreamReader(is, Charset
								.forName("UTF-8")));
				if (JSONUtil.getBoolean(jsonResponse, "success")) {
					JSONObject data = (JSONObject) jsonResponse.get("data");
					if (data != null) {
						VineNotificationsResponse vineNotifications = new VineNotificationsResponse(
								data, url);
						return vineNotifications;
					} else {
						throw new Exception("(104) Server return empty data");
					}
				} else {
					String errorText = JSONUtil
							.getString(jsonResponse, "error");
					long errorCode = JSONUtil.getLong(jsonResponse, "code");
					throw new Exception(
							"(105) Server returns Error => Error Code : '"
									+ errorCode + "' Error Detail :'"
									+ errorText + "'");
				}
			} else {
				throw new Exception("(110) User Id is empty");
			}
		} else {
			throw new Exception("(103) Authenticate first");
		}
	}

	/*
	 * notf
	 */

	/*
	 * my profile
	 */
	public VineUser me() throws Exception {
		if (this.authenticated && !TextUtil.isempty(key)) {
			HttpResponse response = HttpUtil.get(Constants.VINE_API_ME,
					this.key, null);
			InputStream is = HttpUtil.getInputStreamHttpResponse(response);
			JSONObject jsonResponse = (JSONObject) JSONValue
					.parse(new InputStreamReader(is, Charset.forName("UTF-8")));
			if (JSONUtil.getBoolean(jsonResponse, "success")) {
				JSONObject data = (JSONObject) jsonResponse.get("data");
				if (data != null) {
					VineUser vineUser = new VineUser(data);
					return vineUser;
				} else {
					throw new Exception("(104) Server return empty data");
				}
			} else {
				String errorText = JSONUtil.getString(jsonResponse, "error");
				long errorCode = JSONUtil.getLong(jsonResponse, "code");
				throw new Exception(
						"(105) Server returns Error => Error Code : '"
								+ errorCode + "' Error Detail :'" + errorText
								+ "'");
			}
		} else {
			throw new Exception("(103) Authenticate first");
		}
	}

	/*
	 * users profile
	 */

	public VineUser profile(long userId) throws Exception {
		if (this.authenticated && !TextUtil.isempty(key)) {
			if (userId <= 0) {
				return me();
			}
			HttpResponse response = HttpUtil.get(Constants.VINE_API_PROFILE
					.replaceAll("\\{userId\\}", userId + ""), this.key, null);
			InputStream is = HttpUtil.getInputStreamHttpResponse(response);
			JSONObject jsonResponse = (JSONObject) JSONValue
					.parse(new InputStreamReader(is, Charset.forName("UTF-8")));
			if (JSONUtil.getBoolean(jsonResponse, "success")) {
				JSONObject data = (JSONObject) jsonResponse.get("data");
				if (data != null) {
					VineUser vineUser = new VineUser(data);
					return vineUser;
				} else {
					throw new Exception("(104) Server return empty data");
				}
			} else {
				String errorText = JSONUtil.getString(jsonResponse, "error");
				long errorCode = JSONUtil.getLong(jsonResponse, "code");
				throw new Exception(
						"(105) Server returns Error => Error Code : '"
								+ errorCode + "' Error Detail :'" + errorText
								+ "'");
			}
		} else {
			throw new Exception("(103) Authenticate first");
		}
	}

	/*
	 * Signup
	 */
	public VineUser signup(String password, String username, int authenticate,
			String email) throws Exception {
		if (TextUtil.isempty(password)) {
			throw new Exception("(116) Password is empty");
		}
		if (password.length() < 4) {
			throw new Exception("(117) Password must at least 4 character");
		}
		if (TextUtil.isempty(username)) {
			throw new Exception("(118) Username is empty");
		}
		if (username.length() < 4) {
			throw new Exception("(119) Username must at least 4 character");
		}
		if (TextUtil.isempty(email)) {
			throw new Exception("(120) Email is empty");
		}
		if (!TextUtil.validateEmail(email)) {
			throw new Exception("(121) Email is valid");
		}
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("username", username);
		params.put("password", password);
		params.put("authenticate", authenticate + "");
		params.put("email", email);
		HttpResponse response = HttpUtil.post(Constants.VINE_API_SIGNUP,
				this.key, params);
		InputStream is = HttpUtil.getInputStreamHttpResponse(response);
		JSONObject jsonResponse = (JSONObject) JSONValue
				.parse(new InputStreamReader(is, Charset.forName("UTF-8")));
		if (JSONUtil.getBoolean(jsonResponse, "success")) {
			JSONObject data = (JSONObject) jsonResponse.get("data");
			if (data != null) {
				long userId = JSONUtil.getLong(data, "userId");
				String key = JSONUtil.getString(data, "key");
				if (userId > 0 && !TextUtil.isempty(key)) {
					this.userId = userId;
					this.key = key;
					this.authenticated = true;
				} else {
					throw new Exception("(112) Authenticate error");
				}
			} else {
				throw new Exception("(104) Server return empty data");
			}
		} else {
			String errorText = JSONUtil.getString(jsonResponse, "error");
			long errorCode = JSONUtil.getLong(jsonResponse, "code");
			throw new Exception("(105) Server returns Error => Error Code : '"
					+ errorCode + "' Error Detail :'" + errorText + "'");
		}
		return null;
	}

	/*
	 * logout
	 */

	public void logout() throws Exception {
		if (this.authenticated && !TextUtil.isempty(key)) {
			HttpResponse response = HttpUtil.delete(Constants.VINE_API_LOGIN,
					this.key, null);
			InputStream is = HttpUtil.getInputStreamHttpResponse(response);
			JSONObject jsonResponse = (JSONObject) JSONValue
					.parse(new InputStreamReader(is, Charset.forName("UTF-8")));
			if (JSONUtil.getBoolean(jsonResponse, "success")) {
				this.authenticated = false;
				this.userId = 0;
				this.userName = null;
				this.password = null;
			} else {
				String errorText = JSONUtil.getString(jsonResponse, "error");
				long errorCode = JSONUtil.getLong(jsonResponse, "code");
				throw new Exception(
						"(105) Server returns Error => Error Code : '"
								+ errorCode + "' Error Detail :'" + errorText
								+ "'");
			}
		} else {
			throw new Exception("(103) Authenticate first");
		}
	}

	/*
	 * authenticate app
	 */

	public String authenticate() throws Exception {
		if (!TextUtil.isempty(userName) && !TextUtil.isempty(password)) {
			login();
		} else if (!TextUtil.isempty(key)) {
			checkKey();
		} else {
			throw new Exception(
					"(111) (username and password) or (key) must be set");
		}
		return key;
	}

	/*
	 * check key is valid
	 */

	private void checkKey() throws Exception {
		if (!TextUtil.isempty(key)) {
			HttpResponse response = HttpUtil.get(Constants.VINE_API_ME,
					this.key, null);
			InputStream is = HttpUtil.getInputStreamHttpResponse(response);
			JSONObject jsonResponse = (JSONObject) JSONValue
					.parse(new InputStreamReader(is, Charset.forName("UTF-8")));
			if (JSONUtil.getBoolean(jsonResponse, "success")) {
				JSONObject data = (JSONObject) jsonResponse.get("data");
				if (data != null) {
					String userName = JSONUtil.getString(data, "username");
					long userId = JSONUtil.getLong(data, "userId");
					if (userId > 0 && !TextUtil.isempty(userName)
							&& !TextUtil.isempty(key)) {
						this.userId = userId;
						this.authenticated = true;
					} else {
						throw new Exception("(112) Authenticate error");
					}
				} else {
					throw new Exception("(104) Server return empty data");
				}
			} else {
				String errorText = JSONUtil.getString(jsonResponse, "error");
				long errorCode = JSONUtil.getLong(jsonResponse, "code");
				throw new Exception(
						"(105) Server returns Error => Error Code : '"
								+ errorCode + "' Error Detail :'" + errorText
								+ "'");
			}
		} else {
			new Exception("(103) Authenticate first");
		}
	}

	/*
	 * login with username
	 */

	private void login() throws Exception {
		if (!TextUtil.isempty(userName) && !TextUtil.isempty(password)) {
			HashMap<String, String> params = new HashMap<String, String>();
			params.put(Constants.VINE_HTTPBODY_USERNAME_TEXT, userName);
			params.put(Constants.VINE_HTTPBODY_PASSWORD_TEXT, password);
			params.put(Constants.VINE_HTTPBODY_DEVICE_TOKEN_TEXT,
					Constants.VINE_HTTPBODY_DEVICE_TOKEN);
			HttpResponse response = HttpUtil.post(Constants.VINE_API_LOGIN,
					this.key, params);
			InputStream is = HttpUtil.getInputStreamHttpResponse(response);
			JSONObject jsonResponse = (JSONObject) JSONValue
					.parse(new InputStreamReader(is, Charset.forName("UTF-8")));
			if (JSONUtil.getBoolean(jsonResponse, "success")) {
				JSONObject data = (JSONObject) jsonResponse.get("data");
				if (data != null) {
					String username = JSONUtil.getString(data, "username");
					long userId = JSONUtil.getLong(data, "userId");
					String key = JSONUtil.getString(data, "key");
					if (userId > 0 && !TextUtil.isempty(username)
							&& !TextUtil.isempty(key)) {
						this.userId = userId;
						this.key = key;
						this.authenticated = true;
					} else {
						throw new Exception("(112) Authenticate error");
					}
				} else {
					throw new Exception("(104) Server return empty data");
				}
			} else {
				String errorText = JSONUtil.getString(jsonResponse, "error");
				long errorCode = JSONUtil.getLong(jsonResponse, "code");
				throw new Exception(
						"(105) Server returns Error => Error Code : '"
								+ errorCode + "' Error Detail :'" + errorText
								+ "'");
			}
		} else {
			new Exception("(103) Authenticate first");
		}
	}

	/*
	 * getter setter
	 */

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public boolean isAuthenticated() {
		return authenticated;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
