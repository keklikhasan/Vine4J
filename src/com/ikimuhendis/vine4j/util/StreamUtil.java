package com.ikimuhendis.vine4j.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class StreamUtil {

	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is,Charset.forName("UTF-8")));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append((line + "\n"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public static void writeStreamToFile(InputStream is, File f) {
		if (f != null) {
			if (!f.exists()) {
				try {
					if (f.createNewFile()) {
						@SuppressWarnings("resource")
						FileOutputStream outputStream = new FileOutputStream(f);
						int read = 0;
						byte[] bytes = new byte[1024];

						while ((read = is.read(bytes)) != -1) {
							outputStream.write(bytes, 0, read);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
