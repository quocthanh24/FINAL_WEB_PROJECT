package thanhluu.controller;


import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import thanhluu.entity.UserEntity;
import thanhluu.service.IUserService;



@Controller
public class LoginController {

	@Autowired
	private IUserService userServiceImpl;
	


	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@PostMapping("/login")
	public String loginResp(@RequestParam("email") String email,
							@RequestParam("password") String password,
							Model model,
							HttpSession session ) {

		Optional<UserEntity> user = userServiceImpl.findByEmail(email);
		
		
		if (user == null) {
			model.addAttribute("error", "User này không tồn tại");
			return "/login";
		}
		
		UserEntity existUser = user.get();

		if (existUser.getPassword().equals(password) && existUser.isActive() && existUser.isOtpVerified()){

			session.setAttribute("role", existUser.getRole());
			session.setAttribute("user", existUser);
			
			return "redirect:/home"; // Đăng nhập thành công, chuyển hướng về trang chủ
		} else {
			if (!existUser.isActive()) {
				model.addAttribute("error", "Tài khoản chưa được kích hoạt"); // Thêm thông báo lỗi vào Model
				return "/home"; 
			}
			if (!existUser.isOtpVerified()) {
				model.addAttribute("error", "Tài khoản chưa được xác thực"); // Thêm thông báo lỗi vào Model
				return "/home"; 
			}
			model.addAttribute("error", "Email hoặc mật khẩu không chính xác!"); // Thêm thông báo lỗi vào Model
			return "/login"; // Hiển thị lại trang login cùng thông báo lỗi
		}

	}
	
	
	
	
	
}
