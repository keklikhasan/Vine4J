package com.ikimuhendis.vine4j.util;

public class Constants {
	/*
	 * Url
	 */
	public static final String VINE_API_BASE_URL="https://api.vineapp.com";
	public static final String VINE_API_LOGIN=VINE_API_BASE_URL+"/users/authenticate";
	public static final String VINE_API_SIGNUP=VINE_API_BASE_URL+"/users";
	public static final String VINE_API_ME=VINE_API_BASE_URL+"/users/me";
	public static final String VINE_API_PROFILE=VINE_API_BASE_URL+"/users/profiles/{userId}";
	public static final String VINE_API_NOTIFICATIONS=VINE_API_BASE_URL+"/users/{userId}/notifications";
	public static final String VINE_API_PENDING_NOTIFICATIONS=VINE_API_BASE_URL+"/users/{userId}/pendingNotificationsCount";
	public static final String VINE_API_SEARCH_USER=VINE_API_BASE_URL+"/users/search/{term}";
	public static final String VINE_API_USER_TIMELINE=VINE_API_BASE_URL+"/timelines/users/{userId}";
	public static final String VINE_API_TAG_TIMELINE=VINE_API_BASE_URL+"/timelines/{tag}";
	public static final String VINE_API_POST_COMMENTS=VINE_API_BASE_URL+"/posts/{postId}/comments";
	public static final String VINE_API_POST_COMMENT=VINE_API_BASE_URL+"/posts/{postId}/comments/{commentId}";
	public static final String VINE_API_USER_LIKES=VINE_API_BASE_URL+"/timelines/users/{userId}/likes";
	public static final String VINE_API_POST_LIKES=VINE_API_BASE_URL+"/posts/{postId}/likes";
	public static final String VINE_API_USER_FOLLOW=VINE_API_BASE_URL+"/users/{userId}/followers";
	public static final String VINE_API_USER_BLOCK=VINE_API_BASE_URL+"/users/{myUserId}/blocked/{userId}";
	public static final String VINE_API_POST_REPORT=VINE_API_BASE_URL+"/posts/{postId}/complaints";
	public static final String VINE_API_POST_LIKE=VINE_API_BASE_URL+"/posts/{postId}/likes";
	public static final String VINE_API_POST_ADD_COMMENT=VINE_API_BASE_URL+"/posts/{postId}/comments";
	public static final String VINE_API_SEACRH_TAG=VINE_API_BASE_URL+"/tags/search/{tag}";

	/*
	 * Http Header
	 */ 
	public static final String VINE_HTTPHEADER_USER_AGENT_TEXT="User-Agent";
	public static final String VINE_HTTPHEADER_USER_AGENT="com.vine.iphone/1.0.5 (unknown, iPhone OS 5.1.1, iPhone, Scale/2.000000)";
	public static final String VINE_HTTPHEADER_ACCEPT_TEXT="Accept";
	public static final String VINE_HTTPHEADER_ACCEPT="*/*";
	public static final String VINE_HTTPHEADER_ACCEPT_LANGUAGE_TEXT="Accept-Language";
	public static final String VINE_HTTPHEADER_ACCEPT_LANGUAGE="en, fr, de, ja, nl, it, es, pt, pt-PT, da, fi, nb, sv, ko, zh-Hans, zh-Hant, ru, pl, tr, uk, ar, hr, cs, el, he, ro, sk, th, id, ms, en-GB, ca, hu, vi, en-us;q=0.8";
	public static final String VINE_HTTPHEADER_ACCEPT_ENCODING_TEXT="Accept-Encoding";
	public static final String VINE_HTTPHEADER_ACCEPT_ENCODING="gzip";
	public static final String VINE_HTTPHEADER_VINE_SESSION_ID="vine-session-id";
	
	/*
	 * Http Body
	 */
	public static final String VINE_HTTPBODY_USERNAME_TEXT="username";
	public static final String VINE_HTTPBODY_PASSWORD_TEXT="password";
	public static final String VINE_HTTPBODY_DEVICE_TOKEN_TEXT="deviceToken";
	public static final String VINE_HTTPBODY_DEVICE_TOKEN="1111111111111111111111111111111111111111111111111111111111111111";
	
	
	/*
	 * Regex
	 */
	public static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
}
