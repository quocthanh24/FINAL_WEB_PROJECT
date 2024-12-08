package thanhluu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import thanhluu.entity.UserEntity;
import thanhluu.service.IUserService;

import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/user")
public class UserController {

	
	@Autowired
	private IUserService userService;
	
	@GetMapping("/info")
	public String info(HttpSession session, Model model) {
		
		UserEntity user = (UserEntity) session.getAttribute("user");
		
		model.addAttribute("user", user);
		
		return "edit-info";
	}
	
	@PostMapping("/edit-info")
	public String postMethodName(@RequestParam String username,
								 @RequestParam String email,
								 @RequestParam String phone,
								 @RequestParam String address,
								 HttpSession session) {
		
		UserEntity user = (UserEntity) session.getAttribute("user");
		user.setUsername(username);
		user.setEmail(email);
		user.setPhone(phone);
		user.setAddress(address);
			
		userService.save(user);

		return "redirect:/home";
	}
	
	

	
}
