package thanhluu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	
	@GetMapping("/home")
	public String home() {
		return "home";
	}
	
	@GetMapping("/main")
	public String main() {
		return "shoping-cart";
	}
}
