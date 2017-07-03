package com.szyciov.carservice.service;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service("fileUtilService")
public class FileUtilService {
	
	public File multipartToFile(MultipartFile multipartFile) throws IllegalStateException, IOException {
		File convFile = new File(multipartFile.getOriginalFilename());
		multipartFile.transferTo(convFile);
		return convFile;
	}

}
