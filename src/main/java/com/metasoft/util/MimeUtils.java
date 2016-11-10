package com.metasoft.util;

public class MimeUtils {

	public static String getImageType(String mime) {
		if (mime.equals("image/jpeg")) {
			return "jpg";
		} else if (mime.equals("image/png")) {
			return "png";
		} else if (mime.equals("image/x-ms-bmp")) {
			return "bmp";
		} else if (mime.equals("image/pjpeg")) {
			return "jpg";
		} else if (mime.equals("image/x-png")) {
			return "png";
		} else if (mime.equals("image/bmp")) {
			return "bmp";
		} else if (mime.equals("image/gif")) {
			return "gif";
		}
		return null;
	}
}