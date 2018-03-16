package com.robintegg.news.rest;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;

import org.springframework.hateoas.Resource;

import com.robintegg.news.journalist.Journalist;
import com.robintegg.news.journalist.JournalistId;
import com.robintegg.news.journalist.NewsStory;
import com.robintegg.news.journalist.NewsStoryId;

public class JournalistResourceFactory {

	public static Resource<Journalist> journalist(Journalist journalist) {
		return new Resource<Journalist>(journalist);
	}

	public static Resource<JournalistId> journalistId(JournalistId journalistId) {
		return new Resource<JournalistId>(journalistId);
	}

	public static URI journalistResourceUri(JournalistId journalistId) {
		return linkTo(methodOn(JournalistController.class).get(journalistId.asString())).toUri();
	}

	public static Resource<NewsStory> newsStory(NewsStory newsStory) {
		return new Resource<NewsStory>(newsStory);
	}

	public static URI newsStoryUri(JournalistId journalistId, NewsStoryId newsStoryId) {
		return linkTo(
				methodOn(JournalistNewsStoryController.class).get(journalistId.asString(), newsStoryId.asString()))
						.toUri();
	}

}
