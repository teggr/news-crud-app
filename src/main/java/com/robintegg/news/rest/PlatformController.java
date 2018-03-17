package com.robintegg.news.rest;

import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PlatformController {

	@GetMapping
	public Resource<String> get() {
		return PlatformResourceFactory.platformResource("Welcome to the News Crud REST API");
	}

}
