package com.robintegg.news.rest;

import static com.robintegg.news.rest.BreakingNewsResourceFactory.linkToBreakingNews;
import static com.robintegg.news.rest.JournalistResourceFactory.linkToJournalists;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

public class PlatformResourceFactory {

	private static final String REL_DOCUMENTATION = "documentation";
	private static final String REL_PROJECT = "project";
	private static final String REL_BREAKING_NEWS = "breaking-news";
	private static final String REL_JOURNALISTS = "journalists";

	public static Resource<String> platformResource(String message) {
		Resource<String> resource = new Resource<String>(message);
		resource.add(linkToPlatform().withSelfRel());
		resource.add(linkToJournalists().withRel(REL_JOURNALISTS));
		resource.add(linkToBreakingNews().withRel(REL_BREAKING_NEWS));
		resource.add(new Link(githubUri(), REL_PROJECT));
		resource.add(new Link(documentationUri()).withRel(REL_DOCUMENTATION));
		return resource;
	}

	private static String githubUri() {
		return "https://github.com/teggr/news-crud-app";
	}

	private static String documentationUri() {
		return linkToPlatform().toUriComponentsBuilder().replacePath("/").build().toUriString();
	}

	public static ControllerLinkBuilder linkToPlatform() {
		return linkTo(methodOn(PlatformController.class).get());
	}

}
