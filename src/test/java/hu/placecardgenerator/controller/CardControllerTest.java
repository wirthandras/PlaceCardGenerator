package hu.placecardgenerator.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import hu.placecardgenerator.service.FileDownloader;
import hu.placecardgenerator.service.MimeTypeService;

@RunWith(SpringRunner.class)
@WebMvcTest(CardController.class)
public class CardControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private FileDownloader mockFileDownloaderService;
	
	@MockBean
	private MimeTypeService mockMimeTypeService;
	
	@Rule
	public TemporaryFolder temp = new TemporaryFolder();
	
	@Before
	public void setUp() throws IOException {
		File file = temp.newFile("file");
		when(mockFileDownloaderService.getFile()).thenReturn(file);
		when(mockMimeTypeService.getMime(file)).thenReturn("application/pdf");
	}

	@Test
	public void homeEndpointShouldNavigateToCardsView() throws Exception {
		mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("cards"))
				.andExpect(model().hasNoErrors());
	}

	@Test
	public void cardsEndpointShouldNavigateToCardsView() throws Exception {
		mockMvc.perform(get("/cards")).andExpect(status().isOk()).andExpect(view().name("cards"))
				.andExpect(model().hasNoErrors());
	}
	
	@Test
	public void getpdfEndpointShouldOpenPdf() throws Exception {
		mockMvc.perform(get("/getpdf")).andExpect(status().isOk());
	}

}
