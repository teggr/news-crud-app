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
		return journalistRepository.getOne(journalistId);
	}

	@Override
	public List<NewsStory> getNewsStories(JournalistId journalistId) {
		return journalistRepository.getOne(journalistId).getAllPublishedNewsStories();
	}

	@Override
	public NewsStoryId publish(JournalistId journalistId, Copy copy) {
		Journalist journalist = journalistRepository.getOne(journalistId);
		NewsStoryId newsStoryId = journalist.publish(copy);
		journalistRepository.save(journalist);
		return newsStoryId;
	}

	@Override
	public NewsStory getNewsStory(JournalistId journalistId, NewsStoryId newsStoryId) {
		return journalistRepository.getOne(journalistId).getPublishedNewsStory(newsStoryId);
	}

	@Override
	public NewsStory updateNewsStory(JournalistId journalistId, NewsStoryId newsStoryId, Copy copy) {
		Journalist journalist = journalistRepository.getOne(journalistId);
		NewsStory updatedNewsStory = journalist.updateNewsStory(newsStoryId, copy);
		journalistRepository.save(journalist);
		return updatedNewsStory;
	}

	@Override
	public void redactNewsStory(JournalistId journalistId, NewsStoryId newsStoryId) {
		Journalist journalist = journalistRepository.getOne(journalistId);
		journalist.retractNewsStory(newsStoryId);
		journalistRepository.save(journalist);
	}

}
