package thanhluu.service.Impl;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import thanhluu.entity.UserEntity;
import thanhluu.repository.IUserRepository;
import thanhluu.service.IUserService;



@Service
public class UserServiceImpl implements IUserService{
		
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private EmailService emailService;
	
	
	
	public void sendOtp(String email, String otpCode) {
        try {
        
            emailService.sendOtpEmail(email, otpCode);
        } catch (MessagingException e) {
            // Xử lý lỗi gửi email nếu cần
            e.printStackTrace();
        }
    }

    public void sendPasswordResetLink(String email, String otpCode) {
        try {
        	
            emailService.sendPasswordResetEmail(email, otpCode);
        } catch (MessagingException e) {
            // Xử lý lỗi gửi email nếu cần
            e.printStackTrace();
        }
    }

	@Override
	public Optional<UserEntity> findById(Long id) {
		// TODO Auto-generated method stub
		Optional<UserEntity> user = userRepository.findById(id);
		
		if (user.isPresent()){
			return user;
		}
		return null;
	}

	@Override
	public Optional<UserEntity> findByEmail(String email) {
		// TODO Auto-generated method stub
		Optional<UserEntity> user = userRepository.findByEmail(email);
		
		if (user.isPresent()){
			return user;
		}
		return null;		
	}

	@Override
	public UserEntity save(UserEntity user) {
		// TODO Auto-generated method stub
		return userRepository.save(user);
	}

	@Override
	public String generateOTP() {
		// TODO Auto-generated method stub
		Random rnd = new Random();
		int number = rnd.nextInt(999999);
		// dòng dưới để fill số 0 ở đầu nếu số k đủ 6 chữ số
		return String.format("%06d", number);
		
	}

	@Override
	public Optional<UserEntity> findByOtpCode(String otpCode) {
		// TODO Auto-generated method stub
		return userRepository.findByOtpCode(otpCode);
	}
    
    
}
