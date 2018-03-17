package com.robintegg.news.journalist;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

@Service
@Transactional
public class PocJournalistService implements JournalistService {

	private JournalistRepository journalistRepository;

	public PocJournalistService(JournalistRepository journalistRepository) {
		this.journalistRepository = journalistRepository;
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
		NewsStoryId newsStoryId = journalist.publish(copy);
		journalistRepository.save(journalist);
		return newsStoryId;
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
		return updatedNewsStory;
	}

	@Override
	public void redactNewsStory(JournalistId journalistId, NewsStoryId newsStoryId) {
		Journalist journalist = getJournalist(journalistId);
		journalist.retractNewsStory(newsStoryId);
		journalistRepository.save(journalist);
	}

}
