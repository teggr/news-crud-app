package com.robintegg.news.rest;

import java.util.List;

import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.robintegg.news.breakingnews.BreakingNews;
import com.robintegg.news.breakingnews.BreakingNewsService;

@RestController
@RequestMapping("/api/breaking-news")
public class BreakingNewsController {

	private BreakingNewsService breakingNewsService;

	public BreakingNewsController(BreakingNewsService breakingNewsService) {
		this.breakingNewsService = breakingNewsService;
	}

	@GetMapping
	public List<Resource<BreakingNews>> get() {
		return BreakingNewsResourceFactory.breakingNewsResources(breakingNewsService.getLatestBreakingNews());
	}

}
