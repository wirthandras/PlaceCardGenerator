package hu.placecardgenerator.service;

import java.io.File;
import java.net.URLConnection;

import org.springframework.stereotype.Service;

@Service
public class MimeTypeService {

	public String getMime(File file) {
		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		if (mimeType == null) {
			mimeType = "application/pdf";
		}
		return mimeType;
	}

}
