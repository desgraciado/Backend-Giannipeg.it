package it.giannipeg.back.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.giannipeg.back.entity.BaseImage;
import it.giannipeg.back.repository.BaseImageRepository;


@Service("baseImageService")
public class BaseImageService {
	
	@Autowired
	private BaseImageRepository baseImageRepository;

	@PersistenceContext
	private EntityManager entityManager;
	
	public BaseImage deleteBaseImageByName(String name) {
		BaseImage image = baseImageRepository.findByName(name);
		baseImageRepository.delete(image);
		return image;
	}
}
