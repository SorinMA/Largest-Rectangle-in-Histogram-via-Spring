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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import msa.someApi.REST.RunningMedian;

@SpringBootTest
@AutoConfigureMockMvc
public class TestRunningMedian {
    @Autowired
	private RunningMedian controller;

	@Test
	public void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
    }
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
	public void test1() throws Exception {
        Scanner scan;
        boolean failReadingFile1 = false;
        boolean failReadingFile2 = false;
        boolean enableContorTill1000 = false;

        ArrayList<Integer> inputTest1INT = new ArrayList<Integer>();
        ArrayList<Double> outputTest1DOUBLE = new ArrayList<Double>();

        try {
            File test1IN = new File("./src/test/java/msa/someApi/TestResources/testResourcesRunningMedian/test1IN.txt");
            scan = new Scanner(test1IN);
    
            int contorTill1000 = 0;
            while(scan.hasNextInt())
            {
                inputTest1INT.add(scan.nextInt());
                if (enableContorTill1000 == true) {
                    if (contorTill1000 == 1000)
                        break;
                    contorTill1000 ++;
                }
            }
    
        } catch (FileNotFoundException e1) {
                failReadingFile1 = true;
        }

        try {
            File test1IN = new File("./src/test/java/msa/someApi/TestResources/testResourcesRunningMedian/test1OUT.txt");
            scan = new Scanner(test1IN);

            int contorTill1000 = 0;
            while(scan.hasNextDouble())
            {
                outputTest1DOUBLE.add(scan.nextDouble());
                if (enableContorTill1000 == true) {
                    if (contorTill1000 == 1000)
                        break;
                    contorTill1000 ++;
                }
            }
    
        } catch (FileNotFoundException e1) {
                failReadingFile2 = true;
        }
        
        if (failReadingFile2 == false && failReadingFile1 == false) {
            System.out.println("-- file were found --");
            this.mockMvc.perform(post("/runningmedian")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Arrays.toString(inputTest1INT.toArray())))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString(
                    (Arrays.toString(outputTest1DOUBLE.toArray())).replaceAll("\\s+","")
                )));
        } else {
            System.out.println("-!- file were NOT found -!-");
            this.mockMvc.perform(post("/runningmedian")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("[1.0,1.5,2.0,2.5,3.0,3.5,4.0,4.5,5.0,5.5]")));
        }
	}
}
