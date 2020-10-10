package msa.someApi.REST;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.*;

@RestController
public class GetWelcome {

	@RequestMapping("/")
	public String helloHomePage(
			@RequestParam(value = "name", defaultValue = "user") String name) {
		return "Hello " + name + " ! Welcome to Largest Rectangle in Histogram: Given an array of integers A of size N. A represents a histogram i.e A[i] denotes height of the ith histogram s bar. Width of each bar is 1.";
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public int largestRectangleInHistogram(@RequestBody ArrayList<Integer> A) {
		int maxArea = 0;
        int lenA = A.size();
        
        HashMap<Integer, List<Integer>> mapping = new HashMap<Integer, List<Integer>>();
        
        for (int i = 0 ; i < A.size() ; i += 1) {
            if (mapping.get(A.get(i)) == null) {
                List<Integer> aux = new ArrayList<Integer>();
                aux.add(i);
                mapping.put(A.get(i), aux);
            } else {
                List<Integer> aux = mapping.get(A.get(i));
                aux.add(i);
                mapping.put(A.get(i), aux);
            }
        }
        
        for (int key : mapping.keySet()) {
            HashMap<Integer, Integer> visited = new HashMap<Integer, Integer>();
            
            for (int i : mapping.get(key)) {
                if (visited.get(i) == null) {
                    int count = 1;
                    
                    for (int j = i + 1 ; j < lenA ; j += 1) {
                        if (key > A.get(j)) {
                            break;
                        } else {
                            count += 1;
                            if (key == A.get(j))
                                visited.put(j, 1);
                        }
                    }
                    
                    for (int j = i - 1 ; j >= 0 ; j -= 1) {
                        if (key > A.get(j)) {
                            break;
                        } else {
                            count += 1;
                            if (key == A.get(j))
                                visited.put(j, 1);
                        }
                    }
                    
                    count *= key;
                    
                    if (count > maxArea)
                        maxArea = count;
                    
                } else {
                    continue;
                }
            }
        }
        
        return maxArea;
	}

}
