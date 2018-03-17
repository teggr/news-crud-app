package com.robintegg.news.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.robintegg.news.journalist.Journalist;
import com.robintegg.news.journalist.JournalistId;
import com.robintegg.news.journalist.JournalistRegistration;
import com.robintegg.news.journalist.JournalistService;

@RestController
@RequestMapping("/api/journalists")
public class JournalistsController {

	private JournalistService journalistService;

	public JournalistsController(JournalistService journalistService) {
		this.journalistService = journalistService;
	}

	@GetMapping
	public List<Resource<Journalist>> get() {
		return journalistService.getAllJournalists().stream().map(JournalistResourceFactory::journalistResource)
				.collect(Collectors.toList());
	}

	@GetMapping(params = "name")
	public List<Resource<Journalist>> getWithNameQuery(@RequestParam("name") String name) {
		return JournalistResourceFactory.journalistResources(journalistService.searchJournalists(name));
	}

	@PostMapping
	public ResponseEntity<Resource<JournalistId>> postJournalistRegistration(
			@RequestBody JournalistRegistration journalistRegistration) {
		JournalistId journalistId = journalistService.registerJournalist(journalistRegistration);
		return ResponseEntity.created(JournalistResourceFactory.journalistResourceUri(journalistId)).build();
	}

}
