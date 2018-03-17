package com.robintegg.news.rest;

import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.robintegg.news.journalist.Copy;
import com.robintegg.news.journalist.JournalistId;
import com.robintegg.news.journalist.JournalistService;
import com.robintegg.news.journalist.NewsStory;
import com.robintegg.news.journalist.NewsStoryId;
import com.robintegg.news.journalist.NewsStoryNotFoundException;

@RestController
@RequestMapping("/api/journalists/{journalistId}/news-stories/{newsStoryId}")
public class JournalistNewsStoryController {

	private JournalistService journalistService;

	public JournalistNewsStoryController(JournalistService journalistService) {
		this.journalistService = journalistService;
	}

	@GetMapping
	public Resource<NewsStory> get(@PathVariable("journalistId") String journalistId,
			@PathVariable("newsStoryId") String newsStoryId) {
		return JournalistResourceFactory.newsStoryResource(
				journalistService.getNewsStory(new JournalistId(journalistId), new NewsStoryId(newsStoryId)));
	}

	@PatchMapping
	public Resource<NewsStory> patch(@PathVariable("journalistId") String journalistId,
			@PathVariable("newsStoryId") String newsStoryId, @RequestBody Copy copy) {
		return JournalistResourceFactory.newsStoryResource(
				journalistService.updateNewsStory(new JournalistId(journalistId), new NewsStoryId(newsStoryId), copy));
	}

	@DeleteMapping
	public ResponseEntity<NewsStory> delete(@PathVariable("journalistId") String journalistId,
			@PathVariable("newsStoryId") String newsStoryId) {
		journalistService.retractNewsStory(new JournalistId(journalistId), new NewsStoryId(newsStoryId));
		return ResponseEntity.noContent().build();
	}

	@ExceptionHandler(NewsStoryNotFoundException.class)
	public ResponseEntity<String> newsStoryNotFound() {
		return ResponseEntity.notFound().build();
	}

}
