package it.giannipeg.back.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.giannipeg.back.entity.Post;
import it.giannipeg.back.entity.Tag;
import it.giannipeg.back.repository.TagRepository;


@Service("tagServiceImpl")
public class TagService {
	
	@Autowired
	private TagRepository tagRepository;

	@PersistenceContext
	private EntityManager entityManager;
	
	public Tag deleteTagByName(String tagName) {
		Tag tag = tagRepository.findByName(tagName);
		tagRepository.delete(tag);
		return tag;
	}
	
	@Transactional
	public Tag saveTag(Tag tag) {
		Tag response = tagRepository.save(tag);
		return response;
	}
}
