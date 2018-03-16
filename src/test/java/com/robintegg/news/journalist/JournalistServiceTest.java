package com.robintegg.news.journalist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.robintegg.news.journalist.Copy;
import com.robintegg.news.journalist.Journalist;
import com.robintegg.news.journalist.JournalistId;
import com.robintegg.news.journalist.JournalistRegistration;
import com.robintegg.news.journalist.JournalistService;
import com.robintegg.news.journalist.NewsStory;
import com.robintegg.news.journalist.NewsStoryId;

/**
 * Test for the {@link Journalist} feature
 * 
 * @author robin
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class JournalistServiceTest {

	@Autowired
	private JournalistService journalistService;

	@Test
	public void shouldRegisterNewJournalists() {

		// given
		String name = "robin";

		// when
		JournalistId journalistId = journalistService.registerJournalist(new JournalistRegistration(name));

		// then
		assertEquals(1, journalistService.getAllJournalists().size());
		Journalist journalist = journalistService.getJournalist(journalistId);
		assertEquals(name, journalist.getName());

	}

	@Test
	public void shouldNotRegisterExistingJournalists() {

		// given
		String name = "robin";
		journalistService.registerJournalist(new JournalistRegistration(name));

		// when
		journalistService.registerJournalist(new JournalistRegistration(name));

		// then
		assertEquals(1, journalistService.getAllJournalists().size());

	}

	@Test
	public void shouldReturnJournalistsWhenNameMatchesSearchTerms() {

		// given
		JournalistId journalistId = journalistService.registerJournalist(new JournalistRegistration("robin"));
		journalistService.registerJournalist(new JournalistRegistration("tegg"));

		// when
		List<Journalist> journalists = journalistService.searchJournalists("rob");

		// then
		assertEquals(1, journalists.size());
		Journalist journalist = journalists.get(0);
		assertEquals(journalistId, journalist.getJournalistId());

	}

	@Test
	public void shouldReturnNoJournalistsWhenNoNameMatchesSearchTerms() {

		// given
		journalistService.registerJournalist(new JournalistRegistration("robin"));
		journalistService.registerJournalist(new JournalistRegistration("tegg"));

		// when
		List<Journalist> journalists = journalistService.searchJournalists("james");

		// then
		assertTrue(journalists.isEmpty());

	}

	@Test
	public void shouldPublishCopyAsJournalistNewsStory() {

		// given
		LocalDateTime now = LocalDateTime.now().minusSeconds(1);
		JournalistId journalistId = journalistService.registerJournalist(new JournalistRegistration("robin"));
		Copy copy = new Copy("title", "headlineImage", "content");

		// when
		NewsStoryId newsStoryId = journalistService.publish(journalistId, copy);

		// then
		NewsStory newsStory = journalistService.getNewsStory(journalistId, newsStoryId);
		assertEquals(copy, newsStory.getCopy());
		assertEquals(newsStoryId, newsStory.getNewsStoryId());
		assertTrue(now.isBefore(newsStory.getCreatedAt()));
		assertTrue(now.isBefore(newsStory.getUpdatedAt()));

	}

	@Test
	public void shouldAcceptUpdatedCopyToJournalistNewsStory() {

		// given
		LocalDateTime now = LocalDateTime.now().minusSeconds(1);
		JournalistId journalistId = journalistService.registerJournalist(new JournalistRegistration("robin"));
		Copy originalCopy = new Copy("title", "headlineImage", "content");
		NewsStoryId originalNewsStoryId = journalistService.publish(journalistId, originalCopy);
		Copy newCopy = new Copy("title1", "headlineImage2", "content3");

		// when
		NewsStory updatedNewsStory = journalistService.updateNewsStory(journalistId, originalNewsStoryId, newCopy);

		// then
		assertEquals(newCopy, updatedNewsStory.getCopy());
		assertEquals(originalNewsStoryId, updatedNewsStory.getNewsStoryId());
		assertTrue(now.isBefore(updatedNewsStory.getCreatedAt()));
		assertTrue(now.isBefore(updatedNewsStory.getUpdatedAt()));

		NewsStory newsStory = journalistService.getNewsStory(journalistId, originalNewsStoryId);
		assertEquals(newCopy, newsStory.getCopy());
		assertEquals(originalNewsStoryId, newsStory.getNewsStoryId());
		assertTrue(now.isBefore(newsStory.getCreatedAt()));
		assertTrue(now.isBefore(newsStory.getUpdatedAt()));

	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldRedactJournalistNewsStory() {

		// givens
		JournalistId journalistId = journalistService.registerJournalist(new JournalistRegistration("robin"));
		Copy copy = new Copy("title", "headlineImage", "content");
		NewsStoryId newsStoryId = journalistService.publish(journalistId, copy);

		// when
		journalistService.redactNewsStory(journalistId, newsStoryId);

		// then
		try {
			journalistService.getNewsStory(journalistId, newsStoryId);
		} catch (Exception e) {
			assertEquals("could not find " + journalistId, e.getMessage());
			throw e;
		}

	}

}
