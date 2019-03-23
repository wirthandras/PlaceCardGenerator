package hu.placecardgenerator.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(CardController.class)
public class CardControllerTest {

	@Autowired
	private MockMvc mockMvc;

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

}
