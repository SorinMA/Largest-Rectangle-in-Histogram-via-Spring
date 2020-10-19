package msa.someApi.REST;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Home {
    @RequestMapping("/")
	public String hi() {
		return "Hi!";
	}
}
