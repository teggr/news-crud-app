package com.robintegg.news.rest;

import java.util.List;

import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.robintegg.news.breakingnews.BreakingNews;
import com.robintegg.news.breakingnews.BreakingNewsService;

@Controller
@RequestMapping("/api/breaking-news")
public class BreakingNewsController {

	private BreakingNewsService breakingNewsService;

	public BreakingNewsController(BreakingNewsService breakingNewsService) {
		this.breakingNewsService = breakingNewsService;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Resource<BreakingNews>> get() {
		return BreakingNewsResourceFactory.breakingNewsResources(breakingNewsService.getLatestBreakingNews());
	}

	@GetMapping(produces = MediaType.TEXT_HTML_VALUE)
	public String get(Model model) {
		model.addAttribute("breakingNews", get());
		return "breaking-news";
	}

}
