package com.robintegg.news.journalist;

import org.springframework.context.ApplicationEvent;

@SuppressWarnings("serial")
public class NewsStoryRetractedEvent extends ApplicationEvent {

	public NewsStoryRetractedEvent(NewsStoryId newsStoryId) {
		super(newsStoryId);
	}

	public NewsStoryId getNewsStoryId() {
		return (NewsStoryId) getSource();
	}

}
