package hu.placecardgenerator;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import hu.placecardgenerator.domain.GuestHandler;
import hu.placecardgenerator.domain.PlaceCardDocument;

public class SaveTest {
	
	private static final String FILENAME = "test.pdf";
	
	@Rule
	public TemporaryFolder temp = new TemporaryFolder();
	
	private GuestHandler gh; 
	
	@Before
	public void setup() throws IOException {
		gh = new GuestHandler();
		gh.load(new File("src/main/resources/names"));	
	}

	@Test
	public void saveTest() throws Exception {
		File folder = temp.newFolder();

		PDDocument doc = new PlaceCardDocument(gh).getDoc();
		
		File resultFile = new File(folder, FILENAME);
		
		doc.save(resultFile);
		doc.close();
		
		Assert.assertNotNull(resultFile);
		Assert.assertTrue(resultFile.exists());
	}
	
}
