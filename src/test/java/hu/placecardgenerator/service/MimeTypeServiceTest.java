package hu.placecardgenerator.service;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.rules.Timeout;

public class MimeTypeServiceTest {

	private MimeTypeService service;

	@Rule
	public Timeout timeout = new Timeout(10, TimeUnit.MILLISECONDS);

	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	private File testFile;

	@Before
	public void setUp() throws IOException {
		testFile = tempFolder.newFile("foo.pdf");

		service = new MimeTypeService();
	}

	@Test
	public void mimeTypeServiceTestWithFile() {
		assertNotNull(service.getMime(testFile));
	}

}
