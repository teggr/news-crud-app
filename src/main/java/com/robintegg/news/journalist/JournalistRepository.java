package com.robintegg.news.journalist;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JournalistRepository extends JpaRepository<Journalist, JournalistId> {

	List<Journalist> findByNameLike(String searchTerms);

}
