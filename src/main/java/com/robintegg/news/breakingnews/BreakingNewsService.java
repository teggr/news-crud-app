package com.robintegg.news.breakingnews;

import java.util.List;

import com.robintegg.news.journalist.Journalist;
import com.robintegg.news.journalist.NewsStory;

/**
 * Access to the latest {@link NewsStory}s being published by
 * {@link Journalist}s
 * 
 * @author robin
 *
 */
public interface BreakingNewsService {

	public List<BreakingNews> getLatestBreakingNews();

}
