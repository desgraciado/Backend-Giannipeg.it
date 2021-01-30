package it.giannipeg.back.controller;

//import java.awt.PageAttributes.MediaType;
import java.io.IOException;
import java.util.List;
//import org.im4java.core.IM4JavaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import it.giannipeg.back.repository.BaseImageRepository;
import it.giannipeg.back.repository.PostRepository;
import it.giannipeg.back.config.Constants;
import it.giannipeg.back.entity.BaseImage;
import it.giannipeg.back.entity.Post;
import it.giannipeg.back.service.FileSystemStorageService;
import it.giannipeg.back.service.PostService;

import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.drew.imaging.ImageProcessingException;


@RestController
@CrossOrigin
@Transactional
@RequestMapping(path = "/baseimage")

public class BaseImageController {
	@Autowired
    PostRepository postRpstory;
	@Autowired
    BaseImageRepository baseImageRpstory;
	MediaType mediatype;
	@Autowired
	private PostService postService;
	
	@Autowired
	ResourceLoader resourceLoader;
	
	@Autowired
	private FileSystemStorageService storageService;

	HttpStatus httpStatus = HttpStatus.OK;
	
	@CrossOrigin
    @PostMapping("/upload")
    public void uploadImage(
    		@RequestParam("title") String title,
    		@RequestParam("file") List<MultipartFile> file
    	) throws IOException, InterruptedException, ImageProcessingException {
		
		for(MultipartFile mf: file)
		{
			System.out.println("File - Type : "+mf.getContentType());
			Post post = new Post(title);
			BaseImage img = new BaseImage( 
					mf.getOriginalFilename(), 
					mf.getContentType(), 
					storageService.imageBytes(mf, storageService.createThumbnail(mf)),
					storageService.imageBytes(mf, storageService.createPreview(mf)),
					storageService.getDateTaken(mf)
			);
			post.addBaseImage(img);
	        try {
	        	postService.savePost(post);
				// the flush is needed to catch the sql - error
				baseImageRpstory.flush();
			} catch (Exception e) {
				sendCustomStatus(e);
				//return not neccessary bcs code below will not be exctd.
			}
	        System.out.println("Image saved");
	        storageService.store(mf);
		}		
	}
	
	private void sendCustomStatus(Exception e) {
		// I want to catch if someone wants to upload a duplicate.
		httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		Throwable throwable = e;
		while (throwable.getCause() != null) {
			throwable = throwable.getCause();
		}
		String error = throwable.getMessage();
		System.out.println("ERROR: "+error);
		throw new ResponseStatusException(
				httpStatus, error);
	}
	
	
	@CrossOrigin
	@GetMapping("/getimages")
	public List<BaseImage> getImages(){

        return baseImageRpstory.findAll();
    }

	
	@GetMapping(value = "/get-one-image")
	@ResponseBody
	public ResponseEntity<InputStreamResource> getOneImage() throws IOException {
		
		Resource resource = null;
		try {
			resource = resourceLoader.getResource("file:"+Constants.UPLOAD_LOCATION.concat("IMG_20190128_144928.jpg"));
		}catch (Exception e) {
		    System.out.println("No way");
		}
		return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(resource.getInputStream()));
	}
	
	/*
	 * @GetMapping(value = "/getUploadfiles/{id}")
	 * 
	 * @ResponseBody public byte[] getImage(@PathVariable(name="id", required=true)
	 * String id) throws IOException { String filename = "01";
	 * System.out.println("id : " + id); File serverFile = new File(rootLocation +
	 * "\\" + filename + ".jpg"); System.out.println("serverFile : " + serverFile);
	 * return Files.readAllBytes(serverFile.toPath()); }
	 */
}