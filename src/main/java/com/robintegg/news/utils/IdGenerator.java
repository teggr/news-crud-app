package com.robintegg.news.utils;

public class IdGenerator {

	public static String generate(String text) {
		return text.trim().toLowerCase().replaceAll(" ", "-").replaceAll("[^\\-a-z0-9]", "");
	}

}
