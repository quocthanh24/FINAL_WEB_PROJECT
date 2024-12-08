package thanhluu.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import thanhluu.entity.UserEntity;
import thanhluu.service.IUserService;


@Controller
public class RegisterController {

    @Autowired
    private IUserService userService;



    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register"; // Tên file HTML
    }

    @PostMapping("/register")
    public String handleRegistration(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("address") String address,
            @RequestParam("role") String role,
            Model model) {

        // Tạo UserEntity
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAddress(address);
        user.setRole(role);
        user.setActive(false); // Mặc định chưa kích hoạt

        // Generate OTP
        String otpCode = userService.generateOTP(); 
        user.setOtpCode(otpCode);
        user.setOtpExpirationTime(LocalDateTime.now().plusMinutes(5));
        user.setOtpVerified(false);

        // Lưu User vào CSDL
        userService.save(user);

        // Gửi OTP qua email
        userService.sendOtp(email, otpCode);

        model.addAttribute("message", "OTP has been sent to your email. Please verify to complete registration.");
        return "redirect:/verify-otp"; // Hiển thị lại trang để nhập OTP nếu cần
    }
    
    @GetMapping("/verify-otp")
    public String verifyOTP() {
        return "otp-authentication";
    }
    
    @PostMapping("/verify-otp")
    public String handleOtpVerification(
            @RequestParam("otp1") String otp1,
            @RequestParam("otp2") String otp2,
            @RequestParam("otp3") String otp3,
            @RequestParam("otp4") String otp4,
            @RequestParam("otp5") String otp5,
            @RequestParam("otp6") String otp6,
            Model model) {

        // Ghép OTP lại từ các phần nhập vào
        String otpEntered = otp1 + otp2 + otp3 + otp4 + otp5 + otp6;

        // Tìm user theo OTP code (có thể tìm qua email nếu cần)
        Optional<UserEntity> user = userService.findByOtpCode(otpEntered);
        
        UserEntity exsitUser = new UserEntity();
        
        if (user.isPresent()){
        	exsitUser = user.get();
        }
        else {
        	return "redirect:/register";
        }
        
        if (exsitUser != null && exsitUser.getOtpExpirationTime().isAfter(LocalDateTime.now()) && !exsitUser.isOtpVerified()) {
            // Xác thực OTP
        	exsitUser.setOtpVerified(true);
        	exsitUser.setActive(true);  // Kích hoạt tài khoản

            // Cập nhật thông tin user
            userService.save(exsitUser);

            model.addAttribute("message", "OTP verified successfully. Your account is now active.");
            return "redirect:/login";  // Chuyển hướng đến trang đăng nhập sau khi xác thực thành công
        } else {
            model.addAttribute("message", "Invalid OTP or OTP expired.");
            return "otp-verification";  // Quay lại trang OTP nếu OTP không hợp lệ hoặc hết hạn
        }
    }
}
