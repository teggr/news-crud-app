package com.robintegg.news.journalist;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class NewsStoryId implements Serializable {

	private String id;

	public NewsStoryId() {
		// for persistence
	}

	public NewsStoryId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public String asString() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		NewsStoryId other = (NewsStoryId) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
