package com.robintegg.news.journalist;

import java.util.List;

/**
 * {@link Journalist}s publish {@link NewsStory}s to the platform.
 * 
 * @author robin
 *
 */
public interface JournalistService {

	/**
	 * Get all the {@link Journalist}s on the platform
	 */
	List<Journalist> getAllJournalists();

	/**
	 * Register a new {@link Journalist} for publishing {@link NewsStory} on the
	 * platform
	 */
	JournalistId registerJournalist(JournalistRegistration journalistRegistration);

	/**
	 * Search for a {@link Journalist}
	 */
	List<Journalist> searchJournalists(String searchTerms);

	/**
	 * Get the {@link Journalist}
	 */
	Journalist getJournalist(JournalistId journalistId);

	/**
	 * Get all the {@link NewsStory}s published by the {@link Journalist}
	 */
	List<NewsStory> getNewsStories(JournalistId journalistId);

	/**
	 * Publish the {@link Copy} as a {@link NewsStory} for the
	 * {@link Journalist}
	 */
	NewsStoryId publish(JournalistId journalistId, Copy copy);

	/**
	 * Get a {@link NewsStory} for the {@link Journalist}
	 */
	NewsStory getNewsStory(JournalistId journalistId, NewsStoryId newsStoryId);

	/**
	 * Update the {@link Copy} of a {@link NewsStory} published by the
	 * {@link Journalist}
	 */
	NewsStory updateNewsStory(JournalistId journalistId, NewsStoryId newsStoryId, Copy copy);

	/**
	 * Retract a {@link NewsStory} from the platform published by the
	 * {@link Journalist}
	 */
	void retractNewsStory(JournalistId journalistId, NewsStoryId newsStoryId);

}
