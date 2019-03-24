package hu.placecardgenerator.service;

import static org.junit.Assert.assertNotNull;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class FileDownloaderTest {

	private FileDownloader service;

	@Rule
	public Timeout timeout = new Timeout(10, TimeUnit.MILLISECONDS);

	@Before
	public void setUp() {
		service = new FileDownloader();
	}

	@Test
	public void testReturnFileShouldBeNotNull() {
		assertNotNull(service.getFile());
	}

}
