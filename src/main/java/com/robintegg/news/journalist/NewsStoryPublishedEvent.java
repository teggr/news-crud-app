package com.robintegg.news.journalist;

import org.springframework.context.ApplicationEvent;

@SuppressWarnings("serial")
public class NewsStoryPublishedEvent extends ApplicationEvent {

	public NewsStoryPublishedEvent(NewsStory newsStory) {
		super(newsStory);
	}

	public NewsStory getNewsStory() {
		return (NewsStory) getSource();
	}

}
