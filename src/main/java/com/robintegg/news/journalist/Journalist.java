package com.robintegg.news.journalist;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.robintegg.news.utils.IdGenerator;

/**
 * A {@link Journalist} is responsible for a collection of {@link NewsStory}s
 * 
 * @author robin
 *
 */
@Entity
@Table(name = "JOURNALIST")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Journalist {

	public static Journalist create(String name) {
		Journalist journalist = new Journalist();
		journalist.journalistId = new JournalistId(IdGenerator.generate(name));
		journalist.name = name;
		return journalist;
	}

	@EmbeddedId
	@AttributeOverride(name = "id", column = @Column(name = "J_ID"))
	@JsonIgnore
	private JournalistId journalistId;

	@Column(name = "J_NAME")
	private String name;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "journalist")
	private Set<NewsStory> newsStories = new HashSet<>();

	public Journalist() {
		// for persistence
	}

	public String getName() {
		return name;
	}

	public JournalistId getJournalistId() {
		return journalistId;
	}

	public Set<NewsStory> getNewsStories() {
		return newsStories;
	}

	@JsonIgnore
	public List<NewsStory> getAllPublishedNewsStories() {
		return newsStories.stream().sorted().collect(Collectors.toList());
	}

	public NewsStory publish(Copy copy) {
		NewsStory newsStory = NewsStory.fromCopy(copy, this);
		newsStories.add(newsStory);
		return newsStory;
	}

	public NewsStory getPublishedNewsStory(NewsStoryId newsStoryId) {
		return newsStories.stream().filter(ns -> ns.getNewsStoryId().equals(newsStoryId)).findFirst()
				.orElseThrow(() -> new NewsStoryNotFoundException(journalistId, newsStoryId));
	}

	public NewsStory updateNewsStory(NewsStoryId newsStoryId, Copy copy) {
		return getPublishedNewsStory(newsStoryId).update(copy);
	}

	public void retractNewsStory(NewsStoryId newsStoryId) {
		getPublishedNewsStory(newsStoryId);
		newsStories.removeIf(ns -> ns.getNewsStoryId().equals(newsStoryId));
	}

}
