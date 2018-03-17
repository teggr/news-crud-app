package com.robintegg.news.rest;

import static com.robintegg.news.rest.JournalistResourceFactory.linkToJournalists;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

public class PlatformResourceFactory {

	public static Resource<String> platformResource(String message) {
		Resource<String> resource = new Resource<String>(message);
		resource.add(linkToPlatform().withSelfRel());
		resource.add(linkToJournalists().withRel("journalists"));
		resource.add(new Link(githubUri(), "github"));
		resource.add(new Link(documentationUri()).withRel("documentation"));
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
