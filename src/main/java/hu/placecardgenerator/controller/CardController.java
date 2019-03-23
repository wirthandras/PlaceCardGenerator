package hu.placecardgenerator.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CardController {

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
		File file = new File("ultetesSalamonV1.pdf");

		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		if (mimeType == null) {
			mimeType = "application/pdf";
		}
		response.setContentType(mimeType);
		response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
		response.setContentLength((int) file.length());
		try (InputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		}
	}
}
