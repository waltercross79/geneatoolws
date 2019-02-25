package com.waltercross.geneatoolapp;

import com.waltercross.geneatoolcore.RegistryRecord;
import com.waltercross.geneatoolcore.RegistryRecordRepository;

import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedOutputStream;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.nio.file.Path;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;

/**
 * Handles requests for the application file upload requests
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class RecordFilesController {

    private final RegistryRecordRepository repository;

	public RecordFilesController(RegistryRecordRepository repository) {
		super();
		this.repository = repository;
	}
	/**
	 * Upload single file using Spring Controller
	 */
	@PostMapping("/recordfiles/{id}")	
	String uploadFileHandler(@PathVariable String id,
			@RequestParam MultipartFile file) {

		if (!file.isEmpty()) {
			try {
				RegistryRecord r = repository.findById(id)
					.orElseThrow(() -> new RegistryRecordNotFoundException(id));
				r.fileName = generateFilePath(id, file.getOriginalFilename());

				byte[] bytes = file.getBytes();
				
				File path = new File(r.fileName);

				// Create the file on server				
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(path));
				stream.write(bytes);
				stream.close();

				repository.save(r);
				
				return "You successfully uploaded the file.";
			} catch (Exception e) {
				return "You failed to upload the file => " + e.getMessage();
			}
		} else {
			return "You failed to upload the file" 
					+ " because the file was empty.";
		}
    }
    
    // Single item.
    @GetMapping("/recordfiles/{id}")
    ResponseEntity<InputStreamResource> download(@PathVariable String id) {
		
		// Get the record; get the file name from the record; must add file name to record.
		RegistryRecord r = repository.findById(id)
				.orElseThrow(() -> new RegistryRecordNotFoundException(id));
		
        File file = new File(r.fileName);
		HttpHeaders headers = new HttpHeaders();
		
        headers.setContentType(MediaType.parseMediaType(getMimeType(r.fileName)));
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Methods", "GET, POST, PUT");
        headers.add("Access-Control-Allow-Headers", "Content-Type");
        headers.add("Content-Disposition", "filename=" + "record_" + id + ".jpg");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

		try {
			headers.setContentLength(file.length());

			BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		
        	ResponseEntity<InputStreamResource> response = new ResponseEntity<InputStreamResource>(
            new InputStreamResource(inputStream), headers, HttpStatus.OK);
			return response;
		} catch (java.io.IOException e) {
			headers.add("Error-Message", e.getMessage());
			return new ResponseEntity<InputStreamResource>(headers, HttpStatus.NOT_FOUND);
		}
	}
	
	private String generateFilePath(String id, String fileName) {
		String rootPath = ".";
		File dir = new File(rootPath + File.separator + "files");
		if (!dir.exists())
			dir.mkdirs();

		return dir.getAbsolutePath() + File.separator + "record_" + id + getExtension(fileName);
	}

	private String getExtension(String fileName) {
		int indexOfLastDot = fileName.lastIndexOf('.');

		if(indexOfLastDot >= 0)
			return fileName.substring(indexOfLastDot);

		return "";
	}

	private String getMimeType(String fileName) {
		String ext = getExtension(fileName);

		switch (ext) {
			case ".jpg":
				return "image/jpeg";
			case ".png":
				return "image/png";
			default:
				return "image";
		}
	}
}
