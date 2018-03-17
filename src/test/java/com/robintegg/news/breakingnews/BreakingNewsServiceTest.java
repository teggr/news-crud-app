package com.robintegg.news.breakingnews;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit4.SpringRunner;

import com.robintegg.news.journalist.Copy;
import com.robintegg.news.journalist.Journalist;
import com.robintegg.news.journalist.NewsStory;
import com.robintegg.news.journalist.NewsStoryPublishedEvent;
import com.robintegg.news.journalist.NewsStoryRetractedEvent;
import com.robintegg.news.journalist.NewsStoryUpdatedEvent;

/**
 * Test for the {@link BreakingNews} feature
 * 
 * @author robin
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BreakingNewsServiceTest {

	@Autowired
	private PocBreakingNewsService breakingNewsService;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Before
	public void setup() {
		breakingNewsService.clear();
	}

	@Test
	public void shouldStartWithNoBreakingNews() {

		// given no news stories

		// when
		List<BreakingNews> latestBreakingNews = breakingNewsService.getLatestBreakingNews();

		// assert
		assertTrue(latestBreakingNews.isEmpty());

	}

	@Test
	public void shouldPublishBreakingNewsAsNewsStoriesComeIn() {

		// given
		String headline = "Robin builds api";
		Journalist jouralist = jouralist();
		NewsStory newsStory = newsStory(headline, jouralist);
		publisher.publishEvent(new NewsStoryPublishedEvent(newsStory));

		// when
		List<BreakingNews> latestBreakingNews = breakingNewsService.getLatestBreakingNews();

		// then
		assertEquals(1, latestBreakingNews.size());
		BreakingNews breakingNews = latestBreakingNews.get(0);
		assertEquals(headline, breakingNews.getHeadline());
		assertEquals(newsStory.getNewsStoryId(), breakingNews.getNewsStoryId());
		assertEquals(jouralist.getJournalistId(), breakingNews.getJournalistId());

	}

	private Journalist jouralist() {
		return Journalist.create("robin");
	}

	private NewsStory newsStory(String headline, Journalist journalist) {
		return NewsStory.fromCopy(new Copy(headline, "", ""), journalist);
	}

	@Test
	public void shouldPublishBreakingNewsAsNewsStoriesAreUpdated() {

		// given
		String headline = "Robin builds api";
		Journalist jouralist = jouralist();
		NewsStory newsStory = newsStory(headline, jouralist);
		publisher.publishEvent(new NewsStoryPublishedEvent(newsStory));
		String headline2 = "Robin updates api";
		newsStory.update(new Copy(headline2, "", ""));
		publisher.publishEvent(new NewsStoryUpdatedEvent(newsStory));

		// when
		List<BreakingNews> latestBreakingNews = breakingNewsService.getLatestBreakingNews();

		// then
		assertEquals(1, latestBreakingNews.size());
		BreakingNews breakingNews = latestBreakingNews.get(0);
		assertEquals(headline2, breakingNews.getHeadline());
		assertEquals(newsStory.getNewsStoryId(), breakingNews.getNewsStoryId());
		assertEquals(jouralist.getJournalistId(), breakingNews.getJournalistId());

	}

	@Test
	public void shouldStopPublishingBreakingNewsIfNewsStoriesAreRetracted() {

		// given
		String headline = "Robin builds api";
		Journalist jouralist = jouralist();
		NewsStory newsStory = newsStory(headline, jouralist);
		publisher.publishEvent(new NewsStoryPublishedEvent(newsStory));
		String headline2 = "Robin updates api";
		newsStory.update(new Copy(headline2, "", ""));
		publisher.publishEvent(new NewsStoryRetractedEvent(newsStory.getNewsStoryId()));

		// when
		List<BreakingNews> latestBreakingNews = breakingNewsService.getLatestBreakingNews();

		// then
		assertTrue(latestBreakingNews.isEmpty());

	}

	@Test
	public void shouldOnlyPublishishAMaximumOfTwoBreakingNews() {

		// given
		publisher.publishEvent(new NewsStoryPublishedEvent(newsStory("Robin builds api", jouralist())));
		publisher.publishEvent(new NewsStoryPublishedEvent(newsStory("Robin builds another api", jouralist())));
		publisher.publishEvent(new NewsStoryPublishedEvent(newsStory("Robin builds last api", jouralist())));

		// when
		List<BreakingNews> latestBreakingNews = breakingNewsService.getLatestBreakingNews();

		// then
		assertEquals(2, latestBreakingNews.size());

	}

}
