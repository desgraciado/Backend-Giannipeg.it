package it.giannipeg.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.giannipeg.back.entity.Tag;


@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
	
	Tag findByName(@Param("name") String name);
}
