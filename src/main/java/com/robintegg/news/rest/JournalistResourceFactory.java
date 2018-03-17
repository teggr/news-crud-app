package com.robintegg.news.rest;

import static com.robintegg.news.rest.PlatformResourceFactory.linkToPlatform;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import com.robintegg.news.journalist.Journalist;
import com.robintegg.news.journalist.JournalistId;
import com.robintegg.news.journalist.NewsStory;
import com.robintegg.news.journalist.NewsStoryId;

public class JournalistResourceFactory {

	private static final String REL_JOURNALISTS = "journalists";
	private static final String REL_NEWS_STORIES = "news-stories";
	private static final String REL_PLATFORM = "platform";

	public static Resource<Journalist> journalistResource(Journalist journalist) {
		Resource<Journalist> resource = new Resource<Journalist>(journalist);
		resource.add(linkToJournalist(journalist.getJournalistId()).withSelfRel());
		resource.add(linkToJournalistNewsStories(journalist.getJournalistId()).withRel(REL_NEWS_STORIES));
		resource.add(linkToJournalists().withRel(REL_JOURNALISTS));
		resource.add(linkToPlatform().withRel(REL_PLATFORM));
		return resource;
	}

	private static ControllerLinkBuilder linkToJournalistNewsStories(JournalistId journalistId) {
		return linkTo(methodOn(JournalistNewsStoriesController.class).getAll(journalistId.asString()));
	}

	public static ControllerLinkBuilder linkToJournalists() {
		return linkTo(methodOn(JournalistsController.class).getAll());
	}

	public static URI journalistResourceUri(JournalistId journalistId) {
		return linkToJournalist(journalistId).toUri();
	}

	private static ControllerLinkBuilder linkToJournalist(JournalistId journalistId) {
		return linkTo(methodOn(JournalistController.class).get(journalistId.asString()));
	}

	public static Resource<NewsStory> newsStoryResource(NewsStory newsStory) {
		Resource<NewsStory> resource = new Resource<NewsStory>(newsStory);
		resource.add(linkToNewsStory(newsStory.getJournalistId(), newsStory.getNewsStoryId()).withSelfRel());
		resource.add(linkToJournalistNewsStories(newsStory.getJournalistId()).withRel(REL_NEWS_STORIES));
		resource.add(linkToJournalists().withRel(REL_JOURNALISTS));
		resource.add(linkToPlatform().withRel(REL_PLATFORM));
		return resource;
	}

	private static ControllerLinkBuilder linkToNewsStory(JournalistId journalistId, NewsStoryId newsStoryId) {
		return linkTo(
				methodOn(JournalistNewsStoryController.class).get(journalistId.asString(), newsStoryId.asString()));
	}

	public static URI newsStoryUri(JournalistId journalistId, NewsStoryId newsStoryId) {
		return linkTo(
				methodOn(JournalistNewsStoryController.class).get(journalistId.asString(), newsStoryId.asString()))
						.toUri();
	}

}
