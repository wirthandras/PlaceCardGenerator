package hu.placecardgenerator.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import hu.placecardgenerator.service.FileDownloader;
import hu.placecardgenerator.service.MimeTypeService;

@Controller
public class CardController {
	
	@Autowired
	private FileDownloader service;
	
	@Autowired
	private MimeTypeService serviceMime;

	@GetMapping("/")
	public String index(Model model) {
		return "cards";
	}

	@GetMapping("cards")
	public String card(Model model) {
		model.addAttribute("pageTitle", "Cards");
		return "cards";
	}

	@GetMapping("/getpdf")
	@ResponseBody
	public void getPDF1(HttpServletResponse response) throws IOException {
		File file = service.getFile();
				
		response.setContentType(serviceMime.getMime(file));
		response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
		response.setContentLength((int) file.length());
		
		try (InputStream inputStream = new FileInputStream(file)) {
			FileCopyUtils.copy(new FileInputStream(file), response.getOutputStream());
		}
	}
}
