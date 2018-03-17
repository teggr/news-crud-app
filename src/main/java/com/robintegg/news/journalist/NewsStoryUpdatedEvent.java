package com.robintegg.news.journalist;

import org.springframework.context.ApplicationEvent;

@SuppressWarnings("serial")
public class NewsStoryUpdatedEvent extends ApplicationEvent {

	public NewsStoryUpdatedEvent(NewsStory newsStory) {
		super(newsStory);
	}

	public NewsStory getNewsStory() {
		return (NewsStory) getSource();
	}

}
