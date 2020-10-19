package msa.someApi;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import msa.someApi.REST.CastleOnTheGrid;

@SpringBootTest
@AutoConfigureMockMvc
public class TestCastleOnTheGrid {
    @Autowired
	private CastleOnTheGrid controller;

	@Test
	public void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
    }
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
	public void test1() throws Exception {
        JSONParser parser = new JSONParser();
        String testInput;
        boolean wasCatch = false;
        try {
			Object testData = parser.parse(new FileReader("./src/test/java/msa/someApi/TestResources/test1TestCastleOnTheGrid.json"));
            System.out.println("Read0");
			// A JSON object. Key value pairs are unordered. JSONObject supports java.util.Map interface.
			JSONObject jsonTestData = (JSONObject) testData;
            System.out.println("Read");
			// A JSON array. JSONObject supports java.util.List interface.
			testInput = (String) jsonTestData.toString();
		} catch (Exception e) {
            System.out.println(e);
            System.out.println("Working Directory = " + System.getProperty("user.dir"));
			testInput = "{ \"grid\": [\".X.\", \".X.\", \"...\"], \"startX\": 0, \"startY\": 0, \"goalX\": 0, \"goalY\": 2}";
            wasCatch = true;
        }

        System.out.println("Catch:" + wasCatch);
        
		this.mockMvc.perform(post("/castleOnTheGrid")
            .contentType(MediaType.APPLICATION_JSON)
            .content(testInput))
			.andDo(print())
			.andExpect(status().is2xxSuccessful())
			.andExpect(content().string(containsString(wasCatch == true ? "3" : "73")));
	}
}
