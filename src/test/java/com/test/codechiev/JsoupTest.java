package com.test.codechiev;

import org.jsoup.Jsoup;

public class JsoupTest {

	public static void main(String[] args) {
		String html = "<font size=\"88\">还不够大</font>";
		System.out.println(Jsoup.parse(html).text());
	}

}
