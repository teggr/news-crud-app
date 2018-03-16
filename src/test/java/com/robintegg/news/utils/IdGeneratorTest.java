package com.robintegg.news.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class IdGeneratorTest {

	@Test
	public void shouldLowercaseTextForId() {

		// given
		String text = "AbCd1234";

		// when
		String id = IdGenerator.generate(text);

		// then
		assertEquals("abcd1234", id);

	}

	@Test
	public void shouldTrimTextForId() {

		// given
		String text = "  AbCd1234  ";

		// when
		String id = IdGenerator.generate(text);

		// then
		assertEquals("abcd1234", id);

	}

	@Test
	public void shouldReplaceAllSpacesWithDashes() {

		// given
		String text = "  AbCd 1234  ";

		// when
		String id = IdGenerator.generate(text);

		// then
		assertEquals("abcd-1234", id);

	}

	@Test
	public void shouldNotAddNonAlphanumericCharactersToId() {

		// given
		String text = "AbCd&%£1234  ";

		// when
		String id = IdGenerator.generate(text);

		// then
		assertEquals("abcd1234", id);

	}

}
