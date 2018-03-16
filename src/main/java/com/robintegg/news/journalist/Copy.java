package com.robintegg.news.journalist;

import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * {@link Copy} represents the written and image based parts that could be used
 * as part of a {@link NewsStory}
 * 
 * @author robin
 *
 */
@Embeddable
public class Copy {

	private String headline;
	private String image;
	private String body;

	public Copy() {
		// for persistence
	}

	@JsonCreator
	public Copy(@JsonProperty("headline") String headline, @JsonProperty("image") String image,
			@JsonProperty("body") String body) {
		this.headline = headline;
		this.image = image;
		this.body = body;
	}

	public String getHeadline() {
		return headline;
	}

	public String getImage() {
		return image;
	}

	public String getBody() {
		return body;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((body == null) ? 0 : body.hashCode());
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result + ((headline == null) ? 0 : headline.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Copy other = (Copy) obj;
		if (body == null) {
			if (other.body != null)
				return false;
		} else if (!body.equals(other.body))
			return false;
		if (image == null) {
			if (other.image != null)
				return false;
		} else if (!image.equals(other.image))
			return false;
		if (headline == null) {
			if (other.headline != null)
				return false;
		} else if (!headline.equals(other.headline))
			return false;
		return true;
	}

}
