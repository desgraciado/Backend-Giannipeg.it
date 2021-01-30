package it.giannipeg.back.service;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifSubIFDDirectory;

import it.giannipeg.back.config.Constants;

import java.io.ByteArrayOutputStream;
import java.io.File;


@Service("fileSystemStorageService")
public class FileSystemStorageService {
	
private Path uploadLocation;


	@PostConstruct
	public void init() {
		this.uploadLocation = Paths.get(Constants.UPLOAD_LOCATION);
		try {
			Files.createDirectories(uploadLocation);
			System.out.println("Created files - Dir!");
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize storage", e);
		}
	}
	
	
	public BufferedImage createThumbnail(MultipartFile file) throws IOException, InterruptedException {
		//get path of upload location from environments
		// thumbnail Creation
		// Reading an image from a file
		BufferedImage originalBufferedImage = null;
		try {
		    originalBufferedImage = ImageIO.read(file.getInputStream());
		}   
		catch(IOException ioe) {
		    System.out.println("IO exception occurred while trying to read image.");
		    throw ioe;
		}
		// Scaling the image to a size which is slightly larger than the thumbnail
		//int thumbnailWidth = 150;	
		int thumbnailWidth = 80;
		int thumbnailHeight = 60;
		int widthToScale, heightToScale;
		if (originalBufferedImage.getWidth() > originalBufferedImage.getHeight()) {	 
		    //heightToScale = (int)(1.1 * thumbnailWidth);
			heightToScale = (int)(1.1 * thumbnailHeight);
		    //widthToScale = (int)((heightToScale * 1.0) / originalBufferedImage.getHeight() * originalBufferedImage.getWidth());	
		    widthToScale = (int)((thumbnailWidth * 1.0) / originalBufferedImage.getHeight() * originalBufferedImage.getWidth());	
		} else {
		    //widthToScale = (int)(1.1 * thumbnailWidth);
			widthToScale = (int)(1.1 * thumbnailWidth);
		    //heightToScale = (int)((widthToScale * 1.0) / originalBufferedImage.getWidth() * originalBufferedImage.getHeight());
			heightToScale = (int)((thumbnailHeight * 1.0) / originalBufferedImage.getWidth() * originalBufferedImage.getHeight());
		}
		// scale the original image down to another one with the same aspect ratio
		BufferedImage resizedImage = new BufferedImage(widthToScale, heightToScale, originalBufferedImage.getType());
			Graphics2D g = resizedImage.createGraphics();
			 
			g.setComposite(AlphaComposite.Src);
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			 
			g.drawImage(originalBufferedImage, 0, 0, widthToScale, heightToScale, null);
			g.dispose();
		// Cropping the center of the resultant image
		int x = (resizedImage.getWidth() - thumbnailWidth) / 2;
		//int y = (resizedImage.getHeight() - thumbnailWidth) / 2;
		int y = (resizedImage.getHeight() - thumbnailHeight) / 2;
		 
		if (x < 0 || y < 0) {
		    throw new IllegalArgumentException("Width of new thumbnail is bigger than original image");
		}
		// When we have a valid x and y coordinate, we call the getSubImage function to get the thumbnail:
		//BufferedImage thumbnailBufferedImage = resizedImage.getSubimage(x, y, thumbnailWidth, thumbnailWidth);
		BufferedImage thumbnailBufferedImage = resizedImage.getSubimage(x, y, thumbnailWidth, thumbnailHeight);
		// Extract the ending to create a new name
		// TODO png images
		return thumbnailBufferedImage;
	}
	
	
	public BufferedImage createPreview(MultipartFile file) throws IOException, InterruptedException {
		BufferedImage originalBufferedImage = null;
		try {
		    originalBufferedImage = ImageIO.read(file.getInputStream());
		}   
		catch(IOException ioe) {
		    System.out.println("IO exception occurred while trying to read image.");
		    throw ioe;
		}
		// Scaling the image to a size which is slightly larger than the thumbnail
		//int thumbnailWidth = 150;	
		int previewWidth = 800;
		int previewHeight = 600;
		int widthToScale, heightToScale;
		if (originalBufferedImage.getWidth() > originalBufferedImage.getHeight()) {	 
		    //heightToScale = (int)(1.1 * thumbnailWidth);
			heightToScale = (int)(1.1 * previewHeight);
		    //widthToScale = (int)((heightToScale * 1.0) / originalBufferedImage.getHeight() * originalBufferedImage.getWidth());	
		    widthToScale = (int)((previewWidth * 1.0) / originalBufferedImage.getHeight() * originalBufferedImage.getWidth());	
		} else {
		    //widthToScale = (int)(1.1 * thumbnailWidth);
			widthToScale = (int)(1.1 * previewWidth);
		    //heightToScale = (int)((widthToScale * 1.0) / originalBufferedImage.getWidth() * originalBufferedImage.getHeight());
			heightToScale = (int)((previewHeight * 1.0) / originalBufferedImage.getWidth() * originalBufferedImage.getHeight());
		}
		// scale the original image down to another one with the same aspect ratio
		BufferedImage resizedImage = new BufferedImage(widthToScale, heightToScale, originalBufferedImage.getType());
			Graphics2D g = resizedImage.createGraphics();
			 
			g.setComposite(AlphaComposite.Src);
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			 
			g.drawImage(originalBufferedImage, 0, 0, widthToScale, heightToScale, null);
			g.dispose();
		// Cropping the center of the resultant image
		int x = (resizedImage.getWidth() - previewWidth) / 2;
		//int y = (resizedImage.getHeight() - thumbnailWidth) / 2;
		int y = (resizedImage.getHeight() - previewHeight) / 2;
		 
		if (x < 0 || y < 0) {
		    throw new IllegalArgumentException("Width of new thumbnail is bigger than original image");
		}
		// When we have a valid x and y coordinate, we call the getSubImage function to get the thumbnail:
		//BufferedImage thumbnailBufferedImage = resizedImage.getSubimage(x, y, thumbnailWidth, thumbnailWidth);
		BufferedImage previewBufferedImage = resizedImage.getSubimage(x, y, previewWidth, previewHeight);
		// Extract the ending to create a new name
		// TODO png images
		return previewBufferedImage;
	}
	
	
	public byte[] imageBytes(MultipartFile file, BufferedImage bufferedImage) throws IOException {
		String imageType = file.getContentType().split("/")[1];
		String extension = new String();
		
		if ("jpeg".contentEquals(imageType)) {
			extension = "jpg";
		}
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, extension, baos);
			return baos.toByteArray();
		}
		catch (IOException ioe) {
		    System.out.println("Error writing image to file");
		    throw ioe;
		}
	}
	
	
	/*
	 * public String createThmName(MultipartFile file) { String thumb = new
	 * String(); try { String imageType = file.getContentType().split("/")[1];
	 * String filename = StringUtils.cleanPath(file.getOriginalFilename()); String
	 * extensionRemoved = filename.split("\\.")[0];
	 * 
	 * if ("jpeg".contentEquals(imageType)) { thumb =
	 * extensionRemoved.concat("_thm.jpg"); }
	 * System.out.println("Name Created for Thumbnail: " + thumb); } catch
	 * (Exception ex) { System.out.println("Error creating Thumbnail name");
	 * ex.printStackTrace(); } return thumb; }
	 */
	
	
	public void store(MultipartFile file) throws IOException, InterruptedException {
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			if (file.isEmpty()) {
				throw new RuntimeException("Failed to store empty file " + filename);
			}
			// This is a security check
			if (filename.contains("..")) {
				throw new RuntimeException("Cannot store file with relative path outside current directory " + filename);
			}
			try (InputStream inputStream = file.getInputStream()) {
				Path filePath = this.uploadLocation.resolve(filename);
				Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (IOException e) {
			throw new RuntimeException("Failed to store file " + filename, e);
		}
	}

	
	public Resource loadAsResource(String filename) {
		try {
			Path file = uploadLocation.resolve(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("Could not read file: " + filename);
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("Could not read file: " + filename, e);
		}
	}
	
	
	public List<Path> listSourceFiles(Path dir) throws IOException {
		List<Path> result = new ArrayList<>();
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.{txt}")) {
			for (Path entry : stream) {
				result.add(entry);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
	
	public LocalDate getDateTaken(MultipartFile file) throws IOException, ImageProcessingException {
		Metadata metadata = ImageMetadataReader.readMetadata(file.getInputStream());
		ExifSubIFDDirectory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);

		Date date = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
		Instant instant = date.toInstant();
		ZoneId zoneId = ZoneId.of ( "Europe/Rome" );
		ZonedDateTime zdt = ZonedDateTime.ofInstant ( instant , zoneId );
		LocalDate localDate = zdt.toLocalDate();
		return localDate;
	}
}
