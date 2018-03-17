package com.robintegg.news.journalist;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class JournalistId implements Serializable {

	private String id;

	public JournalistId() {
		// for persistence
	}

	public JournalistId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return asString();
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
		JournalistId other = (JournalistId) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String asString() {
		return id;
	}

}
