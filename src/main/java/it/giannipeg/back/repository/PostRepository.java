package it.giannipeg.back.repository;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.giannipeg.back.entity.Post;

import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	Post findByTitle(@Param("title") String title);
}