package hu.placecardgenerator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PlaceCardGeneratorApplication implements CommandLineRunner {

	public static final String pdfExtension = ".pdf";

	public static void main(String[] args) {
		SpringApplication.run(PlaceCardGeneratorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		List<String> names = getNames();

		PDDocument doc = new MyDocument(names).getDoc();
		String filename = "ultetesSalamonV1";

		save(doc, filename);
	}

	private static void save(PDDocument doc, String filename) {
		try {

			doc.save(filename + pdfExtension);
			doc.close();
		} catch (Exception io) {
			System.out.println(io);
		}
	}

	private static List<String> getNames() {
		File file = new File("src/main/resources/names");
		List<String> names = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
			String line;
			while ((line = br.readLine()) != null) {
				names.add(line);
			}
			br.close();
			return names;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return names;
	}
}
