package com.robintegg.news.journalist;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PocJournalistService implements JournalistService, ApplicationEventPublisherAware {

	private JournalistRepository journalistRepository;
	private ApplicationEventPublisher applicationEventPublisher;

	public PocJournalistService(JournalistRepository journalistRepository) {
		this.journalistRepository = journalistRepository;
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

	@Override
	public List<Journalist> getAllJournalists() {
		return journalistRepository.findAll();
	}

	@Override
	public JournalistId registerJournalist(JournalistRegistration journalistRegistration) {
		return journalistRepository.save(Journalist.create(journalistRegistration.getName())).getJournalistId();
	}

	@Override
	public List<Journalist> searchJournalists(String searchTerms) {
		return journalistRepository.findByNameLike(like(searchTerms));
	}

	private String like(String searchTerms) {
		return String.format("%%%s%%", searchTerms);
	}

	@Override
	public Journalist getJournalist(JournalistId journalistId) {
		Journalist journalist = journalistRepository.findOne(journalistId);
		if (journalist == null) {
			throw new JournalistNotFoundException(journalistId);
		}
		return journalist;
	}

	@Override
	public List<NewsStory> getNewsStories(JournalistId journalistId) {
		return getJournalist(journalistId).getAllPublishedNewsStories();
	}

	@Override
	public NewsStoryId publish(JournalistId journalistId, Copy copy) {
		Journalist journalist = journalistRepository.findOne(journalistId);
		NewsStory newsStory = journalist.publish(copy);
		journalistRepository.save(journalist);
		applicationEventPublisher.publishEvent(new NewsStoryPublishedEvent(newsStory));
		return newsStory.getNewsStoryId();
	}

	@Override
	public NewsStory getNewsStory(JournalistId journalistId, NewsStoryId newsStoryId) {
		return getJournalist(journalistId).getPublishedNewsStory(newsStoryId);
	}

	@Override
	public NewsStory updateNewsStory(JournalistId journalistId, NewsStoryId newsStoryId, Copy copy) {
		Journalist journalist = getJournalist(journalistId);
		NewsStory updatedNewsStory = journalist.updateNewsStory(newsStoryId, copy);
		journalistRepository.save(journalist);
		applicationEventPublisher.publishEvent(new NewsStoryUpdatedEvent(updatedNewsStory));
		return updatedNewsStory;
	}

	@Override
	public void retractNewsStory(JournalistId journalistId, NewsStoryId newsStoryId) {
		Journalist journalist = getJournalist(journalistId);
		journalist.retractNewsStory(newsStoryId);
		journalistRepository.save(journalist);
		applicationEventPublisher.publishEvent(new NewsStoryRetractedEvent(newsStoryId));
	}

}
