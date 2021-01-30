package it.giannipeg.back.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import it.giannipeg.back.entity.Post;
import it.giannipeg.back.repository.BaseImageRepository;
import it.giannipeg.back.repository.PostRepository;


@RestController
@CrossOrigin
@Transactional
@RequestMapping(path = "/post")

public class PostController {
	@Autowired
    PostRepository postRpstory;
	@Autowired
    BaseImageRepository baseImageRpstory;
	MediaType mediatype;
	
	
	@CrossOrigin
	@GetMapping("/getPosts")
	public List<Post> getPosts(){
        return postRpstory.findAll();
    }
}
