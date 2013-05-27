package com.ikimuhendis.vine4j.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtil {

	public static boolean isempty(String str) {
		if (str != null && !str.equals("")) {
			return false;
		}
		return true;
	}

	public static boolean validateEmail(final String email) {
		if (!isempty(email)) {
			Pattern pattern = Pattern.compile(Constants.EMAIL_PATTERN);
			Matcher matcher = pattern.matcher(email);
			return matcher.matches();
		}
		return false;
	}
}
