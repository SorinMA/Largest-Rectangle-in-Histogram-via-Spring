package msa.someApi;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import msa.someApi.REST.GetWelcome;

@SpringBootTest
@AutoConfigureMockMvc
public class TestApi {

	@Autowired
	private GetWelcome controller;

	@Test
	public void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testGetWithName() throws Exception {
		this.mockMvc.perform(get("/?name=Sorin")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString(
						"Hello Sorin ! Welcome to Largest Rectangle in Histogram: Given an array of integers A of size N. A represents a histogram i.e A[i] denotes height of the ith histogram s bar. Width of each bar is 1.")));
	}

	@Test
	public void testGetWithoutName() throws Exception {
		this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString(
						"Hello user ! Welcome to Largest Rectangle in Histogram: Given an array of integers A of size N. A represents a histogram i.e A[i] denotes height of the ith histogram s bar. Width of each bar is 1.")));
	}

	@Test
	public void testPost() throws Exception {
		    
		this.mockMvc.perform(post("/")
            .contentType(MediaType.APPLICATION_JSON)
            .content("[1, 2, 3]"))
			.andDo(print())
			.andExpect(status().is2xxSuccessful())
			.andExpect(content().string(containsString("4")));
	}

	@Test
	public void testPost2() throws Exception {
		    
		this.mockMvc.perform(post("/")
            .contentType(MediaType.APPLICATION_JSON)
            .content("[1, 2, 3, 4, 1 , 1, 2, 2, 1, 2]"))
			.andDo(print())
			.andExpect(status().is2xxSuccessful())
			.andExpect(content().string(containsString("10")));
	}
}