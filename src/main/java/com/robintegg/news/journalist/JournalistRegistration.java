package com.robintegg.news.journalist;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JournalistRegistration {

	private String name;

	@JsonCreator
	public JournalistRegistration(@JsonProperty("name") String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
