package com.robintegg.news.journalist;

import java.time.LocalDateTime;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.robintegg.news.utils.IdGenerator;

/**
 * A {@link NewsStory} contains all the {@link Copy} and publishing metadata
 * 
 * @author robin
 *
 */
@Entity
@Table(name = "NEWS_STORY")
public class NewsStory implements Comparable<NewsStory> {

	public static NewsStory fromCopy(Copy copy, Journalist journalist) {
		NewsStory newsStory = new NewsStory();
		newsStory.newsStoryId = new NewsStoryId(IdGenerator.generate(copy.getHeadline()));
		newsStory.copy = copy;
		LocalDateTime now = LocalDateTime.now();
		newsStory.createdAt = now;
		newsStory.updatedAt = now;
		newsStory.journalist = journalist;
		return newsStory;
	}

	@EmbeddedId
	@AttributeOverride(name = "id", column = @Column(name = "NS_ID"))
	@JsonIgnore
	private NewsStoryId newsStoryId;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "headline", column = @Column(name = "NS_HEADLINE")),
			@AttributeOverride(name = "image", column = @Column(name = "NS_IMAGE")),
			@AttributeOverride(name = "body", column = @Column(name = "NS_BODY")) })
	private Copy copy;

	@Column(name = "NS_CREATED_AT")
	private LocalDateTime createdAt;

	@Column(name = "NS_UPDATED_AT")
	private LocalDateTime updatedAt;

	@ManyToOne
	@JoinColumn(name = "NS_JOURNALIST")
	private Journalist journalist;

	public Copy getCopy() {
		return copy;
	}

	public NewsStoryId getNewsStoryId() {
		return newsStoryId;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	@Override
	public int compareTo(NewsStory o) {
		return createdAt.compareTo(o.createdAt);
	}

	public NewsStory update(Copy copy) {
		this.copy = copy;
		return this;
	}

}
