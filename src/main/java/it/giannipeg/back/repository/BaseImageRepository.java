package it.giannipeg.back.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.giannipeg.back.entity.BaseImage;



public interface BaseImageRepository extends JpaRepository<BaseImage, Long> {
	
	BaseImage findByName(@Param("name") String name);
}
