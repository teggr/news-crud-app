package com.robintegg.news.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.robintegg.news.journalist.Copy;
import com.robintegg.news.journalist.JournalistId;
import com.robintegg.news.journalist.JournalistService;
import com.robintegg.news.journalist.NewsStory;
import com.robintegg.news.journalist.NewsStoryId;

@RestController
@RequestMapping("/journalists/{journalistId}/news-stories")
public class JournalistNewsStoriesController {

	private JournalistService journalistService;

	public JournalistNewsStoriesController(JournalistService journalistService) {
		this.journalistService = journalistService;
	}

	@GetMapping
	public List<Resource<NewsStory>> getAll(@PathVariable("journalistId") String journalistId) {
		return journalistService.getNewsStories(new JournalistId(journalistId)).stream()
				.map(JournalistResourceFactory::newsStory).collect(Collectors.toList());
	}

	@PostMapping
	public ResponseEntity<Resource<NewsStoryId>> postCopy(@PathVariable("journalistId") String journalistId,
			@RequestBody Copy copy) {
		JournalistId ji = new JournalistId(journalistId);
		return ResponseEntity.created(JournalistResourceFactory.newsStoryUri(ji, journalistService.publish(ji, copy)))
				.build();
	}

}
