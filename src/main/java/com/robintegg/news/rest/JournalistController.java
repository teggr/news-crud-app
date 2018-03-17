package com.robintegg.news.rest;

import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.robintegg.news.journalist.Journalist;
import com.robintegg.news.journalist.JournalistId;
import com.robintegg.news.journalist.JournalistNotFoundException;
import com.robintegg.news.journalist.JournalistService;

@RestController
@RequestMapping("/journalists/{journalistId}")
public class JournalistController {

	private JournalistService journalistService;

	public JournalistController(JournalistService journalistService) {
		this.journalistService = journalistService;
	}

	@GetMapping
	public Resource<Journalist> get(@PathVariable("journalistId") String journalistId) {
		return JournalistResourceFactory.journalist(journalistService.getJournalist(new JournalistId(journalistId)));
	}

	@ExceptionHandler(JournalistNotFoundException.class)
	public ResponseEntity<String> journalistNotFound() {
		return ResponseEntity.notFound().build();
	}

}
