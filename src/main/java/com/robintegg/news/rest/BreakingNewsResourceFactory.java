package com.robintegg.news.rest;

import static com.robintegg.news.rest.JournalistResourceFactory.linkToJournalist;
import static com.robintegg.news.rest.JournalistResourceFactory.linkToNewsStory;
import static com.robintegg.news.rest.PlatformResourceFactory.linkToPlatform;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import com.robintegg.news.breakingnews.BreakingNews;

public class BreakingNewsResourceFactory {

	private static final String NEWS_STORY = "news-story";
	private static final String REL_PLATFORM = "platform";
	private static final String REL_JOURNALIST = "journalist";

	public static Resource<BreakingNews> breakingNewsResource(BreakingNews breakingNews) {
		Resource<BreakingNews> resource = new Resource<BreakingNews>(breakingNews);
		resource.add(linkToBreakingNews().withSelfRel());
		resource.add(linkToJournalist(breakingNews.getJournalistId()).withRel(REL_JOURNALIST));
		resource.add(
				linkToNewsStory(breakingNews.getJournalistId(), breakingNews.getNewsStoryId()).withRel(NEWS_STORY));
		resource.add(linkToPlatform().withRel(REL_PLATFORM));
		return resource;
	}

	public static ControllerLinkBuilder linkToBreakingNews() {
		return linkTo(methodOn(BreakingNewsController.class).get());
	}

	public static List<Resource<BreakingNews>> breakingNewsResources(List<BreakingNews> latestBreakingNews) {
		return latestBreakingNews.stream().map(BreakingNewsResourceFactory::breakingNewsResource)
				.collect(Collectors.toList());
	}

}
