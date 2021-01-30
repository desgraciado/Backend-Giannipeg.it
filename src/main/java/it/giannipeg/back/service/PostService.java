package it.giannipeg.back.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.giannipeg.back.entity.BaseImage;
import it.giannipeg.back.entity.Post;
import it.giannipeg.back.entity.Tag;
import it.giannipeg.back.repository.BaseImageRepository;
import it.giannipeg.back.repository.PostRepository;
import it.giannipeg.back.repository.TagRepository;


@Service("postService")
public class PostService {
	
	@Autowired
	private BaseImageRepository baseImageRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private TagRepository tagRepository;

	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	public Post savePost(Post post) {
		Post response = postRepository.save(post);
		return response;
	}
	
	@Transactional
	public void removeTag(String tagName, Long postId) {
		Tag tag = new Tag(tagName);
	    Post post = entityManager.find(Post.class, postId);
	    post.removeTag(tag);
	}
	
	@Transactional
	public void removeBaseImage(String baseImageName, Long postId) {
		BaseImage image = new BaseImage();
		image = baseImageRepository.findByName(baseImageName);
	    Post post = entityManager.find(Post.class, postId);
	    post.removeBaseImage(image);
	}
	
	@Transactional
	public void deletePostByTitle(String title) {
		Post post = postRepository.findByTitle(title);
		postRepository.delete(post);
	}
	
	@Transactional
	public Post addNewTag(String postTitle, String newTagName) {
		Post post = new Post();
		if (postRepository.findByTitle(postTitle) == null) {
			return post;
		} else {
			post = postRepository.findByTitle(postTitle);
			Tag tag = new Tag();
			
			if (tagRepository.findByName(newTagName) == null) {
				tag.setName(newTagName);
				post.addTag(tag);
			}
			
			//long lastPollTime = Optional.ofNullable(object).map(YouObjectClass::getTime).orElse(0L);
			
			return postRepository.save(post);
		}
	}
	
	@Transactional
	public Post addNewBaseImage(String postTitle, BaseImage image) {
		Post post = new Post();
		if (postRepository.findByTitle(postTitle) == null) {
			return post;
		} else {
			post = postRepository.findByTitle(postTitle);
			post.addBaseImage(image);
		}
		return postRepository.save(post);
	}
}
