package com.robintegg.news.breakingnews;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.robintegg.news.journalist.JournalistId;
import com.robintegg.news.journalist.NewsStory;
import com.robintegg.news.journalist.NewsStoryId;

/**
 * Headline focussed view on a {@link NewsStory}
 * 
 * @author robin
 *
 */
public class BreakingNews {

	private String headline;

	@JsonIgnore
	private NewsStoryId newsStoryId;

	@JsonIgnore
	private JournalistId journalistId;

	public BreakingNews(NewsStory newsStory) {
		headline = newsStory.getCopy().getHeadline();
		newsStoryId = newsStory.getNewsStoryId();
		journalistId = newsStory.getJournalistId();
	}

	public String getHeadline() {
		return headline;
	}

	public NewsStoryId getNewsStoryId() {
		return newsStoryId;
	}

	public JournalistId getJournalistId() {
		return journalistId;
	}

}
