package it.giannipeg.back;

import it.giannipeg.back.entity.BaseImage;
import it.giannipeg.back.entity.Post;
import it.giannipeg.back.entity.Tag;
import it.giannipeg.back.repository.BaseImageRepository;
import it.giannipeg.back.repository.PostRepository;
import it.giannipeg.back.repository.TagRepository;
import it.giannipeg.back.service.PostService;
import it.giannipeg.back.service.TagService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = "it.*")
public class StoryApplication implements CommandLineRunner {
	
	@Autowired
	private TagRepository tagRepository;

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private BaseImageRepository baseImageRpstory;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private TagService tagService;
	
	public static void main(String[] args) {		
		SpringApplication.run(StoryApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
//		// Cleanup the tables
//		postRepository.deleteAllInBatch();
//		tagRepository.deleteAllInBatch();
//
//		// =======================================
		//BaseImage image = new BaseImage();
		
//		BaseImage image = baseImageRpstory.findByName("20150821_174147.jpg");
//		Post post = postRepository.findByTitle("Le premier pont");
//		post.addBaseImage(image);
//		postService.savePost(post);
		
//		postService.removeBaseImage("20150812_144031.jpg", 1L);
		
//		BaseImage image = baseImageRpstory.findByName("20150821_174147.jpg");
//		baseImageRpstory.delete(image);
		
//		// Create a Post
//		Post post1 = new Post("Hibernate Many to Many Example with Spring Boot",
//				"Learn how to map a many to many relationship using hibernate",
//				"Entire Post content with Sample code");
//		Post post2 = new Post("Modular Picture Database with Spring Boot",
//				"Learn how to build a full modular CMS using hibernate",
//				"Another Post content with Sample code");
//		Post post = new Post();
//
//		// Create two tags
//		Tag tag1 = new Tag("Mali");
//		Tag tag2 = new Tag("Family");
//		tagService.saveTag(tag2);
//
//		post.addTag(tag1);
//		post1.addTag(tag2);
//		
//		post2.addTag(tag1);
//		post2.addTag(tag2);
//		
//		postService.savePost(post);
//		postService.savePost(post2);
		
//		postService.addNewTag("Hibernate Many to Many Example with Spring Boot", "Spring Boots");
		// =======================================
		
//		postService.removeTag("Hibernate", 6L);
		
//		Tag tag = tagRepository.findByName("Hibernate");
//		tagRepository.delete(tag);
		
//		Post post = postRepository.findByTitle("Hibernate Many to Many Example with Spring Boot");
//		postRepository.delete(post);
		
//		tagService.deleteTagByName("Spring Boot");

		//postService.addNewTag("Hibernate Many to Many Example with Spring Boot", "JPA rocks");
		
//		System.out.println("Images: "+tagRepository.findAll());
		
		//System.out.println("Images: "+baseImageRpstory.findAll());
	}
}
