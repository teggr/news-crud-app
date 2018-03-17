package com.robintegg.news.journalist;

@SuppressWarnings("serial")
public class NewsStoryNotFoundException extends RuntimeException {

	public NewsStoryNotFoundException(JournalistId journalistId, NewsStoryId newsStoryId) {
		super(String.format("Could not find NewsStory %s by %s", newsStoryId, journalistId));
	}

}
