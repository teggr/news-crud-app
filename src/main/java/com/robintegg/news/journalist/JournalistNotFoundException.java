package com.robintegg.news.journalist;

@SuppressWarnings("serial")
public class JournalistNotFoundException extends RuntimeException {

	public JournalistNotFoundException(JournalistId journalistId) {
		super(String.format("Journalist %s not found", journalistId));
	}

}
