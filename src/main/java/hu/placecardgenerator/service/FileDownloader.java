package hu.placecardgenerator.service;

import java.io.File;

import org.springframework.stereotype.Service;

@Service
public class FileDownloader {

	public File getFile() {
		File file = new File("ultetesSalamonV1.pdf");
		return file;
	}

}
