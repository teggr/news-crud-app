package com.robintegg.news.breakingnews;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.robintegg.news.journalist.NewsStoryPublishedEvent;
import com.robintegg.news.journalist.NewsStoryRetractedEvent;
import com.robintegg.news.journalist.NewsStoryUpdatedEvent;

@Service
public class PocBreakingNewsService implements BreakingNewsService {

	private Queue<BreakingNews> breakingNews = new CircularFifoQueue<>(2);

	@Override
	public List<BreakingNews> getLatestBreakingNews() {
		return new ArrayList<>(breakingNews);
	}

	@EventListener(classes = NewsStoryPublishedEvent.class)
	public void onNewsStoryPublishedEvent(NewsStoryPublishedEvent event) {
		breakingNews.add(new BreakingNews(event.getNewsStory()));
	}

	@EventListener(classes = NewsStoryUpdatedEvent.class)
	public void onNewsStoryUpdatedEvent(NewsStoryUpdatedEvent event) {
		breakingNews.removeIf(bn -> bn.getNewsStoryId().equals(event.getNewsStory().getNewsStoryId()));
		breakingNews.add(new BreakingNews(event.getNewsStory()));
	}

	@EventListener(classes = NewsStoryRetractedEvent.class)
	public void onNewsStoryRetractedEvent(NewsStoryRetractedEvent event) {
		breakingNews.removeIf(bn -> bn.getNewsStoryId().equals(event.getNewsStoryId()));
	}

	protected void clear() {
		breakingNews.clear();
	}

}
